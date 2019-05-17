package com.tuodanhuashu.app.home.ui;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.alibaba.android.vlayout.layout.LinearLayoutHelper;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseViewHolder;
import com.company.common.utils.DisplayUtil;
import com.company.common.utils.KeyboardUtils;
import com.company.common.utils.RandomUntil;
import com.company.common.utils.StringUtils;
import com.ms.banner.listener.OnBannerClickListener;
import com.tuodanhuashu.app.R;
import com.tuodanhuashu.app.base.HuaShuBaseFragment;
import com.tuodanhuashu.app.home.adapter.HomeAdapter;
import com.tuodanhuashu.app.home.adapter.HomeBannerViewHolder;
import com.tuodanhuashu.app.home.bean.HomeAdvertisingBean;
import com.tuodanhuashu.app.home.bean.HomeArticelBean;
import com.tuodanhuashu.app.home.bean.HomeArticelClassBean;
import com.tuodanhuashu.app.home.bean.HomeArticleBean;
import com.tuodanhuashu.app.home.bean.HomeArticleCatRec;
import com.tuodanhuashu.app.home.bean.HomeBannerBean;
import com.tuodanhuashu.app.home.bean.HomeZhuanLanPageBean;
import com.tuodanhuashu.app.home.presenter.HomeZhuanLanPresenter;
import com.tuodanhuashu.app.home.view.HomeZhuanLanView;
import com.tuodanhuashu.app.huashu.ui.HuaShuaListActivity;
import com.tuodanhuashu.app.web.CommonWebActivity;
import com.tuodanhuashu.app.zhuanlan.ui.ZhuanLanDetailActivity;
import com.tuodanhuashu.app.zhuanlan.ui.ZhuanLanListActivity;
import com.tuodanhuashu.app.zhuanlan.ui.ZhuanLanSearchActivity;
import com.ms.banner.Banner;
import com.ms.banner.BannerConfig;
import com.ms.banner.holder.BannerViewHolder;
import com.ms.banner.holder.HolderCreator;
import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class ZhuanLanFragment extends HuaShuBaseFragment implements HomeZhuanLanView {


    @BindView(R.id.zhuanlan_refresheader)
    MaterialHeader zhuanlanRefresheader;
    @BindView(R.id.zhuanlan_rlv)
    RecyclerView zhuanlanRlv;
    @BindView(R.id.zhuanlan_refreshLayout)
    SmartRefreshLayout zhuanlanRefreshLayout;

    private DelegateAdapter delegateAdapter;

    private List<DelegateAdapter.Adapter> adapterList;

    private static final int TYPE_BANNER = 1;

    private static final int TYPE_SEARCH = 2;

    private static final int TYPE_ARTICLE = 3;

    private static final int TYPE_CATEGORY = 4;

    private static final int TYPE_AD = 5;

    private static final int TYPE_LIST = 6;

    private HomeZhuanLanPresenter zhuanLanPresenter;

    private HomeZhuanLanPageBean zhuanLanPageBean;

    private boolean isFirstRefresh = true;

    int arr[] = new int[3];

    public ZhuanLanFragment() {
        // Required empty public constructor
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_zhuan_lan;
    }

    @Override
    protected int getRootLayoutId() {
        return R.id.zhuanlan_refreshLayout;
    }

    @Override
        protected void initView(View view) {
            super.initView(view);
            zhuanLanPresenter = new HomeZhuanLanPresenter(this, mContext);
            adapterList = new ArrayList<>();
            zhuanlanRefresheader.setColorSchemeColors(mContext.getResources().getColor(R.color.colorAccent));
            VirtualLayoutManager layoutManager = new VirtualLayoutManager(mContext);
            zhuanlanRlv.setLayoutManager(layoutManager);
            RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
            zhuanlanRlv.setRecycledViewPool(viewPool);
            viewPool.setMaxRecycledViews(0, 10);
            delegateAdapter = new DelegateAdapter(layoutManager, true);
            zhuanlanRlv.setAdapter(delegateAdapter);
//        initBanner();
//        initSearch();
//        initArticle();
//        initCategory();
//        initAD();
//        initArticleList();
//        delegateAdapter.setAdapters(adapterList);
        zhuanLanPresenter.requestZhuanLanData();
    }

    private void initBanner() {
        HomeAdapter bannerAdapter = new HomeAdapter(mContext, new LinearLayoutHelper(), 1, TYPE_BANNER, R.layout.zhuanlan_banner_layout) {
            @Override
            public void onBindViewHolder(BaseViewHolder holder, int position) {
                super.onBindViewHolder(holder, position);
                final List<HomeBannerBean> homeBannerBeanList = zhuanLanPageBean.getBanners();
                // 绑定数据
                Banner mBanner = holder.getView(R.id.zhuanlan_banner);
                mBanner.setAutoPlay(true)
                        .setOffscreenPageLimit(homeBannerBeanList.size())
                        .setPages(homeBannerBeanList, new HolderCreator<BannerViewHolder>() {
                            @Override
                            public BannerViewHolder createViewHolder() {
                                return new HomeBannerViewHolder();
                            }
                        })
                        .setBannerStyle(BannerConfig.CIRCLE_INDICATOR)
                        .start();
                mBanner.setOnBannerClickListener(new OnBannerClickListener() {
                    @Override
                    public void onBannerClick(int position) {
                        Intent i = new Intent();
                        Bundle b = new Bundle();
                        switch (homeBannerBeanList.get(position).getBanner_type()){
                            case "2":
                                i.setClass(mContext,HuaShuaListActivity.class);
                                b.putString(HuaShuaListActivity.EXTRA_KEY_WORDS, homeBannerBeanList.get(position).getKeyword());
                                i.putExtras(b);
                                startActivity(i);
                                break;
                            case "3":
                                i.setClass(mContext,ZhuanLanDetailActivity.class);
                                b.putString(ZhuanLanDetailActivity.EXTRA_ARTICLE_ID, homeBannerBeanList.get(position).getArticle_id());
                                i.putExtras(b);
                                startActivity(i);
                                break;
                        }
                    }
                });

            }
        };

        adapterList.add(bannerAdapter);
    }

    private void initSearch() {
        HomeAdapter searchAdapter = new HomeAdapter(mContext, new LinearLayoutHelper(), 1, TYPE_SEARCH, R.layout.zhuanlan_search_layout) {
            @Override
            public void onBindViewHolder(BaseViewHolder holder, int position) {
                super.onBindViewHolder(holder, position);
                final EditText et = holder.getView(R.id.zhuanlan_search_et);
                et.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                    @Override
                    public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                        if (i == EditorInfo.IME_ACTION_SEARCH) {
                            String keywords = et.getText().toString();
                            if(StringUtils.isEmpty(keywords)){
                                showToast("请输入搜索内容");

                            }else{
                                KeyboardUtils.hideSoftInput(getActivity());
                                Bundle bundle = new Bundle();

                                bundle.putString(ZhuanLanSearchActivity.EXTRA_KEY_WORDS,keywords);
                                readyGo(ZhuanLanSearchActivity.class,bundle);
                            }

                            return true;
                        }
                        return false;
                    }
                });


            }
        };

        adapterList.add(searchAdapter);
    }

    private void initArticle() {
        HomeAdapter searchAdapter = new HomeAdapter(mContext, new LinearLayoutHelper(), 1, TYPE_ARTICLE, R.layout.zhuanlan_article_layout) {
            @Override
            public void onBindViewHolder(BaseViewHolder holder, int position) {
                super.onBindViewHolder(holder, position);
                final List<HomeArticelBean> articelBeanList = zhuanLanPageBean.getRecommendedArticles();
                final ImageView iv1 = holder.getView(R.id.zhuanlan_articel_rcmd_iv_1);
                final TextView tv1 = holder.getView(R.id.zhuanlan_articel_rcmd_tv_1);
                final ImageView iv2 = holder.getView(R.id.zhuanlan_articel_rcmd_iv_2);
                final TextView tv2 = holder.getView(R.id.zhuanlan_articel_rcmd_tv_2);
                final ImageView iv3 = holder.getView(R.id.zhuanlan_articel_rcmd_iv_3);
                final TextView tv3 = holder.getView(R.id.zhuanlan_articel_rcmd_tv_3);

                Glide.with(mContext).load(articelBeanList.get(arr[0]).getImage_url()).into(iv1);
                Glide.with(mContext).load(articelBeanList.get(arr[1]).getImage_url()).into(iv2);
                Glide.with(mContext).load(articelBeanList.get(arr[2]).getImage_url()).into(iv3);
                tv1.setText(articelBeanList.get(arr[0]).getTitle());
                tv2.setText(articelBeanList.get(arr[1]).getTitle());
                tv3.setText(articelBeanList.get(arr[2]).getTitle());
                holder.getView(R.id.zhuanlan_keywords_switch_ll).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        arr = RandomUntil.randomArr(new int[3],zhuanLanPageBean.getRecommendedArticles().size()-1);
                        Glide.with(mContext).load(articelBeanList.get(arr[0]).getImage_url()).into(iv1);
                        Glide.with(mContext).load(articelBeanList.get(arr[1]).getImage_url()).into(iv2);
                        Glide.with(mContext).load(articelBeanList.get(arr[2]).getImage_url()).into(iv3);
                        tv1.setText(articelBeanList.get(arr[0]).getTitle());
                        tv2.setText(articelBeanList.get(arr[1]).getTitle());
                        tv3.setText(articelBeanList.get(arr[2]).getTitle());
                    }
                });
                holder.getView(R.id.zhuanlan_articel_rcmd_ll_1).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Bundle bundle = new Bundle();
                        bundle.putString(ZhuanLanDetailActivity.EXTRA_ARTICLE_ID,articelBeanList.get(arr[0]).getId());
                        readyGo(ZhuanLanDetailActivity.class,bundle);
                    }
                });
                holder.getView(R.id.zhuanlan_articel_rcmd_ll_2).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Bundle bundle = new Bundle();
                        bundle.putString(ZhuanLanDetailActivity.EXTRA_ARTICLE_ID,articelBeanList.get(arr[1]).getId());
                        readyGo(ZhuanLanDetailActivity.class,bundle);
                    }
                });
                holder.getView(R.id.zhuanlan_articel_rcmd_ll_3).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Bundle bundle = new Bundle();
                        bundle.putString(ZhuanLanDetailActivity.EXTRA_ARTICLE_ID,articelBeanList.get(arr[2]).getId());
                        readyGo(ZhuanLanDetailActivity.class,bundle);
                    }
                });
            }
        };

        adapterList.add(searchAdapter);
    }


    private void initArticle2(){
        HomeAdapter searchAdapter = new HomeAdapter(mContext, new LinearLayoutHelper(), 1, TYPE_ARTICLE, R.layout.zhuanlan_muti_pic_layout) {
            @Override
            public void onBindViewHolder(BaseViewHolder holder, int position) {
                super.onBindViewHolder(holder, position);
                int screenWidth = DisplayUtil.getScreenRelatedInformation(mContext).get("width");
                int screenheight = DisplayUtil.getScreenRelatedInformation(mContext).get("height");
                final String homeArticleCatRecs = zhuanLanPageBean.getArticle_cat_rec_list();
                ImageView iv1 = holder.getView(R.id.zhuanlan_mutipic_iv1);
                ImageView iv2 = holder.getView(R.id.zhuanlan_mutipic_iv2);
                ImageView iv3 = holder.getView(R.id.zhuanlan_mutipic_iv3);
                ImageView iv4 = holder.getView(R.id.zhuanlan_mutipic_iv4);
                Map maps = (Map) JSON.parse(homeArticleCatRecs);
                final List<HomeArticleCatRec> articleCatRecs = new ArrayList<>();
                for (Object map : maps.entrySet()){
                    String str = ((Map.Entry)map).getValue().toString();
                    JSONObject jsonObject = (JSONObject) JSONObject.parse(str);
                    HomeArticleCatRec homeArticleCatRec = new HomeArticleCatRec(jsonObject.get("article_cat").toString(),jsonObject.get("pic_url").toString(),jsonObject.get("title").toString());
                    articleCatRecs.add(homeArticleCatRec);
                }

                Glide.with(mContext).load(articleCatRecs.get(0).getPic_url()).into(iv1);
                Glide.with(mContext).load(articleCatRecs.get(1).getPic_url()).into(iv2);
                Glide.with(mContext).load(articleCatRecs.get(2).getPic_url()).into(iv3);
                Glide.with(mContext).load(articleCatRecs.get(3).getPic_url()).into(iv4);
                holder.getView(R.id.zhuanlan_mutipic_iv1).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Bundle bundle = new Bundle();
                        bundle.putInt(ZhuanLanListActivity.EXTRA_ENTER_TYPE,3);
                        bundle.putString(ZhuanLanListActivity.EXTRA_CLASS_ID,articleCatRecs.get(0).getArticle_cat());
                        readyGo(ZhuanLanListActivity.class,bundle);
                    }
                });
                holder.getView(R.id.zhuanlan_mutipic_iv2).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Bundle bundle = new Bundle();
                        bundle.putInt(ZhuanLanListActivity.EXTRA_ENTER_TYPE,3);
                        bundle.putString(ZhuanLanListActivity.EXTRA_CLASS_ID,articleCatRecs.get(1).getArticle_cat());
                        readyGo(ZhuanLanListActivity.class,bundle);
                    }
                });
                holder.getView(R.id.zhuanlan_mutipic_iv3).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Bundle bundle = new Bundle();
                        bundle.putInt(ZhuanLanListActivity.EXTRA_ENTER_TYPE,3);
                        bundle.putString(ZhuanLanListActivity.EXTRA_CLASS_ID,articleCatRecs.get(2).getArticle_cat());
                        readyGo(ZhuanLanListActivity.class,bundle);
                    }
                });
                holder.getView(R.id.zhuanlan_mutipic_iv4).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Bundle bundle = new Bundle();
                        bundle.putInt(ZhuanLanListActivity.EXTRA_ENTER_TYPE,3);
                        bundle.putString(ZhuanLanListActivity.EXTRA_CLASS_ID,articleCatRecs.get(3).getArticle_cat());
                        readyGo(ZhuanLanListActivity.class,bundle);
                    }
                });


            }
        };

        adapterList.add(searchAdapter);
    }

    private void initCategory() {
        HomeAdapter categoryAdapter = new HomeAdapter(mContext, new LinearLayoutHelper(), 1, TYPE_CATEGORY, R.layout.zhuanlan_category_layout) {
            @Override
            public void onBindViewHolder(BaseViewHolder holder, int position) {
                super.onBindViewHolder(holder, position);
               final List<HomeArticelClassBean> articelClassBeanList = zhuanLanPageBean.getArticleClasses();
                ImageView iv1 = holder.getView(R.id.home_zhuanlan_class_iv_1);
                TextView tv1 = holder.getView(R.id.home_zhuanlan_class_tv_1);
                ImageView iv2 = holder.getView(R.id.home_zhuanlan_class_iv_2);
                TextView tv2 = holder.getView(R.id.home_zhuanlan_class_tv_2);
                ImageView iv3 = holder.getView(R.id.home_zhuanlan_class_iv_3);
                TextView tv3 = holder.getView(R.id.home_zhuanlan_class_tv_3);
                ImageView iv4 = holder.getView(R.id.home_zhuanlan_class_iv_4);
                TextView tv4 = holder.getView(R.id.home_zhuanlan_class_tv_4);
                Glide.with(mContext).load(articelClassBeanList.get(0).getClass_image_url()).into(iv1);
                Glide.with(mContext).load(articelClassBeanList.get(1).getClass_image_url()).into(iv2);
                Glide.with(mContext).load(articelClassBeanList.get(2).getClass_image_url()).into(iv3);
                Glide.with(mContext).load(articelClassBeanList.get(3).getClass_image_url()).into(iv4);
                tv1.setText(articelClassBeanList.get(0).getClass_name());
                tv2.setText(articelClassBeanList.get(1).getClass_name());
                tv3.setText(articelClassBeanList.get(2).getClass_name());
                tv4.setText(articelClassBeanList.get(3).getClass_name());
                holder.getView(R.id.home_zhuanlan_class_fr_1).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Bundle bundle = new Bundle();
                        bundle.putInt(ZhuanLanListActivity.EXTRA_ENTER_TYPE,3);
                        bundle.putString(ZhuanLanListActivity.EXTRA_CLASS_ID,articelClassBeanList.get(0).getId());
                        readyGo(ZhuanLanListActivity.class,bundle);
                    }
                });
                holder.getView(R.id.home_zhuanlan_class_fr_2).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Bundle bundle = new Bundle();
                        bundle.putInt(ZhuanLanListActivity.EXTRA_ENTER_TYPE,3);
                        bundle.putString(ZhuanLanListActivity.EXTRA_CLASS_ID,articelClassBeanList.get(1).getId());
                        readyGo(ZhuanLanListActivity.class,bundle);
                    }
                });
                holder.getView(R.id.home_zhuanlan_class_fr_3).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Bundle bundle = new Bundle();
                        bundle.putInt(ZhuanLanListActivity.EXTRA_ENTER_TYPE,3);
                        bundle.putString(ZhuanLanListActivity.EXTRA_CLASS_ID,articelClassBeanList.get(2).getId());
                        readyGo(ZhuanLanListActivity.class,bundle);
                    }
                });
                holder.getView(R.id.home_zhuanlan_class_fr_4).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Bundle bundle = new Bundle();
                        bundle.putInt(ZhuanLanListActivity.EXTRA_ENTER_TYPE,3);
                        bundle.putString(ZhuanLanListActivity.EXTRA_CLASS_ID,articelClassBeanList.get(3).getId());
                        readyGo(ZhuanLanListActivity.class,bundle);
                    }
                });
            }
        };

        adapterList.add(categoryAdapter);
    }

    private void initAD() {
        HomeAdapter categoryAdapter = new HomeAdapter(mContext, new LinearLayoutHelper(), 1, TYPE_AD, R.layout.huashu_ad_layout) {
            @Override
            public void onBindViewHolder(BaseViewHolder holder, int position) {
                super.onBindViewHolder(holder, position);
                HomeAdvertisingBean advertisingBean = zhuanLanPageBean.getAdvertising();
                ImageView adIv = holder.getView(R.id.huashu_ad_iv);
                Glide.with(mContext).load(advertisingBean.getImage_url()).into(adIv);
                adIv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Bundle bundle = new Bundle();
                        bundle.putString(CommonWebActivity.EXTRA_WV_TITLE,"广告详情");
                        bundle.putString(CommonWebActivity.EXTRA_WV_URL,zhuanLanPageBean.getAdvertising().getUrl());
                        readyGo(CommonWebActivity.class,bundle);
                    }
                });

            }
        };

        adapterList.add(categoryAdapter);
    }

    private void initArticleList() {
        HomeAdapter categoryAdapter = new HomeAdapter(mContext, new LinearLayoutHelper(), 1, TYPE_LIST, R.layout.home_new_article_layout) {
            @Override
            public void onBindViewHolder(BaseViewHolder holder, int position) {
                super.onBindViewHolder(holder, position);
                final List<HomeArticelBean> articelBeanList = zhuanLanPageBean.getChoicenessArticles();
                TextView topTv = holder.getView(R.id.home_new_article_top_tv);
                topTv.setText("精选文章");
                ListView lv = holder.getView(R.id.home_new_article_lv);
                ViewGroup.LayoutParams layoutParams = lv.getLayoutParams();
                TextView moreTv = holder.getView(R.id.home_article_more_tv);
                holder.getView(R.id.home_new_article_time_tv).setVisibility(View.GONE);
                moreTv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Bundle bundle = new Bundle();
                        bundle.putInt(ZhuanLanListActivity.EXTRA_ENTER_TYPE,2);
                        readyGo(ZhuanLanListActivity.class,bundle);
                    }
                });
                layoutParams.height = DisplayUtil.dip2px(mContext, (float) articelBeanList.size() * 120);
                lv.setLayoutParams(layoutParams);
                lv.setAdapter(new BaseAdapter() {
                    @Override
                    public int getCount() {
                        return articelBeanList.size();
                    }

                    @Override
                    public Object getItem(int position) {
                        return articelBeanList.size();
                    }

                    @Override
                    public long getItemId(int position) {
                        return position;
                    }

                    @Override
                    public View getView(int position, View convertView, ViewGroup parent) {
                        HomeArticelBean articleBean = articelBeanList.get(position);
                        convertView = LayoutInflater.from(mContext).inflate(R.layout.item_home_new_article_layout, parent, false);
                        ImageView iv = convertView.findViewById(R.id.item_home_article_iv_1);
                        TextView titleTv = convertView.findViewById(R.id.item_home_article_title_1);
                        TextView descTv = convertView.findViewById(R.id.item_home_article_desc_1);
                        TextView catTv = convertView.findViewById(R.id.item_home_article_cat_1);
                        TextView timeTv = convertView.findViewById(R.id.item_home_article_time_1);
                        titleTv.setText(articleBean.getTitle());
                        descTv.setText(articleBean.getDescription());
                        catTv.setText(articleBean.getArticle_class_name());
                        timeTv.setText(articleBean.getSource());
                        Glide.with(mContext).load(articleBean.getImage_url()).into(iv);
                        return convertView;
                    }
                });
                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Bundle bundle = new Bundle();
                        bundle.putString(ZhuanLanDetailActivity.EXTRA_ARTICLE_ID, articelBeanList.get(position).getId());
                        readyGo(ZhuanLanDetailActivity.class, bundle);
                    }
                });
            }
        };

        adapterList.add(categoryAdapter);
    }

    @Override
    public void getZhuanLanDataSuccess(HomeZhuanLanPageBean zhuanLanPageBean) {
        this.zhuanLanPageBean = zhuanLanPageBean;
        if(zhuanLanPageBean.getRecommendedArticles()!=null&&zhuanLanPageBean.getRecommendedArticles().size()>=3){
            arr = RandomUntil.randomArr(new int[3],zhuanLanPageBean.getRecommendedArticles().size()-1);

        }else if(zhuanLanPageBean.getRecommendedArticles().size()==1){
            arr[0] = 0;
            arr[1] = 0;
        }else if(zhuanLanPageBean.getRecommendedArticles().size()==2){
            arr[0] = 0;
            arr[1] = 1;
            arr[2] = 0;
        }
        if (isFirstRefresh) {

            initBanner();
            initSearch();
            //initArticle();
            initArticle2();
            initCategory();
            initAD();
            initArticleList();
            delegateAdapter.setAdapters(adapterList);
            isFirstRefresh = false;
        } else {

        }
    }

    @Override
    public void getZhuanLanDataFail() {

    }
}
