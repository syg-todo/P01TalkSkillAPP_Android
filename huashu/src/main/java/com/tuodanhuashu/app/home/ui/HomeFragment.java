package com.tuodanhuashu.app.home.ui;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutHelper;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.alibaba.android.vlayout.layout.LinearLayoutHelper;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseViewHolder;
import com.company.common.CommonConstants;
import com.company.common.utils.DisplayUtil;
import com.company.common.utils.KeyboardUtils;
import com.company.common.utils.MD5Utils;
import com.company.common.utils.PreferencesUtils;
import com.company.common.utils.RandomUntil;
import com.company.common.utils.StringUtils;
import com.ms.banner.listener.OnBannerClickListener;
import com.tuodanhuashu.app.Constants.Constants;
import com.tuodanhuashu.app.R;
import com.tuodanhuashu.app.base.HuaShuBaseFragment;
import com.tuodanhuashu.app.home.adapter.HomeAdapter;
import com.tuodanhuashu.app.home.adapter.HomeBannerViewHolder;
import com.tuodanhuashu.app.home.adapter.HotWordsAdapter;
import com.tuodanhuashu.app.home.bean.HomeArticleBean;
import com.tuodanhuashu.app.home.bean.HomePageBean;
import com.tuodanhuashu.app.home.bean.HotKeywordsBean;
import com.tuodanhuashu.app.home.presenter.HomeFragmentPresenter;
import com.tuodanhuashu.app.home.view.HomeFragmentView;
import com.tuodanhuashu.app.huashu.ui.HuaShuaListActivity;
import com.tuodanhuashu.app.teacher.ui.TeacherListActivity;
import com.tuodanhuashu.app.web.CommonWebActivity;
import com.tuodanhuashu.app.zhuanlan.ui.ZhuanLanDetailActivity;
import com.tuodanhuashu.app.zhuanlan.ui.ZhuanLanListActivity;
import com.hyphenate.chat.ChatClient;
import com.hyphenate.helpdesk.callback.Callback;
import com.hyphenate.helpdesk.easeui.util.IntentBuilder;
import com.ms.banner.Banner;
import com.ms.banner.holder.BannerViewHolder;
import com.ms.banner.holder.HolderCreator;
import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.tuodanhuashu.app.zhuanlan.ui.ZhuanLanSearchActivity;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends HuaShuBaseFragment implements HomeFragmentView {


    @BindView(R.id.home_refreshLayout)
    SmartRefreshLayout homeRefreshLayout;

    @BindView(R.id.home_refresheader)
    MaterialHeader homeRefresheader;
    @BindView(R.id.home_rlv)
    RecyclerView homeRlv;

    private DelegateAdapter delegateAdapter;

    private List<DelegateAdapter.Adapter> adapterList;

    private static final int TYPE_BANNER = 1;

    private static final int TYPE_FUNCTION = 2;

    private static final int TYPE_FLOW = 3;

    private static final int TYPE_AD = 4;

    private static final int TYPE_ARTICLE = 5;

    private List<String> hotwordsList;

    private HomeFragmentPresenter homeFragmentPresenter;

    private boolean isFirstLoad = true;

    private HomePageBean homePageBean;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_home;
    }


    @Override
    protected void initView(View view) {
        super.initView(view);
        showLoadingView();
        homeFragmentPresenter = new HomeFragmentPresenter(this, mContext);
        homeFragmentPresenter.requestHomeFragmentData();
        adapterList = new ArrayList<>();
        homeRefresheader.setColorSchemeColors(mContext.getResources().getColor(R.color.colorAccent));
        VirtualLayoutManager layoutManager = new VirtualLayoutManager(mContext);
        homeRlv.setLayoutManager(layoutManager);
        RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
        homeRlv.setRecycledViewPool(viewPool);
        viewPool.setMaxRecycledViews(3, 5);
        delegateAdapter = new DelegateAdapter(layoutManager, true);

        homeRlv.setAdapter(delegateAdapter);
//        initBanner();
//        initFunctionArea();
//        initFlowArea();
//        initAD();
//        initArticleArea();
        homeRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                homeFragmentPresenter.requestHomeFragmentData();
            }
        });

    }


    @Override
    protected int getRootLayoutId() {
        return R.id.home_root;
    }

    private void initAD() {
        HomeAdapter homeADAdapter = new HomeAdapter(mContext, new LinearLayoutHelper(), 1, TYPE_AD, R.layout.home_ad_layout) {
            @Override
            public void onBindViewHolder(BaseViewHolder holder, int position) {
                super.onBindViewHolder(holder, position);
                ImageView adIv = holder.getView(R.id.item_ad_iv);
                Glide.with(mContext).load(homePageBean.getAdvertising().getImage_url()).into(adIv);
                adIv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Bundle bundle = new Bundle();
                        bundle.putString(CommonWebActivity.EXTRA_WV_TITLE,"广告详情");
                        bundle.putString(CommonWebActivity.EXTRA_WV_URL,homePageBean.getAdvertising().getUrl());
                        readyGo(CommonWebActivity.class,bundle);
                    }
                });
            }
        };

        adapterList.add(homeADAdapter);
    }


    private void initBanner() {
        HomeAdapter homeBannerAdapter = new HomeAdapter(mContext, new LinearLayoutHelper(), 1, TYPE_BANNER, R.layout.home_banner_layout) {
            @Override
            public void onBindViewHolder(BaseViewHolder holder, int position) {
                super.onBindViewHolder(holder, position);
                // 绑定数据
                Banner mBanner = holder.getView(R.id.home_banner);
                mBanner.setAutoPlay(true)
                        .setOffscreenPageLimit(homePageBean.getBanners().size())
                        .setPages(homePageBean.getBanners(), new HolderCreator<BannerViewHolder>() {
                            @Override
                            public BannerViewHolder createViewHolder() {
                                return new HomeBannerViewHolder();
                            }
                        })
                        .setBannerStyle(com.ms.banner.BannerConfig.NOT_INDICATOR)
                        .setBannerAnimation(com.ms.banner.Transformer.Scale)
                        .start();
                mBanner.setOnBannerClickListener(new OnBannerClickListener() {
                    @Override
                    public void onBannerClick(int position) {
                        Intent i = new Intent();
                        Bundle b = new Bundle();
                        switch (homePageBean.getBanners().get(position).getBanner_type()){
                            case "2":
                                i.setClass(mContext,HuaShuaListActivity.class);
                                b.putString(HuaShuaListActivity.EXTRA_KEY_WORDS, homePageBean.getBanners().get(position).getKeyword());
                                i.putExtras(b);
                                startActivity(i);
                                break;
                            case "3":
                                i.setClass(mContext,ZhuanLanDetailActivity.class);
                                b.putString(ZhuanLanDetailActivity.EXTRA_ARTICLE_ID, homePageBean.getBanners().get(position).getArticle_id());
                                i.putExtras(b);
                                startActivity(i);
                                break;
                        }
                    }
                });


            }
        };

        adapterList.add(homeBannerAdapter);
    }


    private void initFunctionArea() {
        HomeAdapter homeFunctionAdapter = new HomeAdapter(mContext, new LinearLayoutHelper(), 1, TYPE_FUNCTION, R.layout.home_function_layout) {
            @Override
            public void onBindViewHolder(BaseViewHolder holder, int position) {
                super.onBindViewHolder(holder, position);

                TextView tv1 = holder.getView(R.id.home_function_tv_1);
                TextView tv2 = holder.getView(R.id.home_function_tv_2);
                TextView tv3 = holder.getView(R.id.home_function_tv_3);

                tv1.setText(homePageBean.getRecommendedArticleClasses().get(0).getClass_name());
                tv2.setText(homePageBean.getRecommendedArticleClasses().get(1).getClass_name());
                tv3.setText(homePageBean.getRecommendedArticleClasses().get(2).getClass_name());
                holder.getView(R.id.home_function_fr_1).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Bundle bundle = new Bundle();
                        bundle.putString(ZhuanLanListActivity.EXTRA_CLASS_ID,homePageBean.getRecommendedArticleClasses().get(0).getId());
                        bundle.putInt(ZhuanLanListActivity.EXTRA_ENTER_TYPE,3);
                        readyGo(ZhuanLanListActivity.class,bundle);
                    }
                });
                holder.getView(R.id.home_function_fr_2).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Bundle bundle = new Bundle();
                        bundle.putString(ZhuanLanListActivity.EXTRA_CLASS_ID,homePageBean.getRecommendedArticleClasses().get(1).getId());
                        bundle.putInt(ZhuanLanListActivity.EXTRA_ENTER_TYPE,3);
                        readyGo(ZhuanLanListActivity.class,bundle);
                    }
                });
                holder.getView(R.id.home_function_fr_3).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Bundle bundle = new Bundle();
                        bundle.putString(ZhuanLanListActivity.EXTRA_CLASS_ID,homePageBean.getRecommendedArticleClasses().get(2).getId());
                        bundle.putInt(ZhuanLanListActivity.EXTRA_ENTER_TYPE,3);
                        readyGo(ZhuanLanListActivity.class,bundle);
                    }
                });
                FrameLayout searchFr = holder.getView(R.id.home_frag_search_fr);
                final EditText searchEt = holder.getView(R.id.home_frag_search_et);
                RelativeLayout askTeacherLl = holder.getView(R.id.home_ask_teacher_ll);
                RelativeLayout imLl = holder.getView(R.id.home_im_ll);
                searchEt.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                    @Override
                    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                            String keywords = searchEt.getText().toString();
                            if (StringUtils.isEmpty(keywords)) {
                                showToast("请输入关键字");

                            }
                            Bundle bundle = new Bundle();
                            bundle.putString(HuaShuaListActivity.EXTRA_KEY_WORDS, keywords);
                            readyGo(HuaShuaListActivity.class, bundle);

                            return true;
                        }
                        return false;
                    }
                });
                searchFr.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String keywords = searchEt.getText().toString();
                        if (StringUtils.isEmpty(keywords)) {
                            showToast("请输入关键字");
                            return;
                        }
                        Bundle bundle = new Bundle();
                        bundle.putString(HuaShuaListActivity.EXTRA_KEY_WORDS, keywords);
                        readyGo(HuaShuaListActivity.class, bundle);
                    }
                });
                imLl.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(isLogin()){
                            goToIm();
                        }else{
                            goToLogin();
                        }

                    }
                });
                TextView jhjjTv = holder.getView(R.id.home_jhjj_tv);
                jhjjTv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(isLogin()){
                            goToIm();
                        }else{
                            goToLogin();
                        }
                    }
                });
                askTeacherLl.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        readyGo(TeacherListActivity.class);
                    }
                });
            }
        };

        adapterList.add(homeFunctionAdapter);
    }


    private void initFlowArea() {

        HomeAdapter homeFlowAdapter = new HomeAdapter(mContext, new LinearLayoutHelper(), 1, TYPE_FLOW, R.layout.home_hotwords_layout) {
            @Override
            public void onBindViewHolder(BaseViewHolder holder, int position) {
                super.onBindViewHolder(holder, position);

                final TagFlowLayout tagFlowLayout = holder.getView(R.id.home_fll);
                final HotWordsAdapter adapter = new HotWordsAdapter(mContext, refreshDatas(homePageBean.getHotKeywords()));
                tagFlowLayout.setAdapter(adapter);
                LinearLayout switchLl = holder.getView(R.id.home_keywords_switch_ll);

                switchLl.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        HotWordsAdapter adapter = new HotWordsAdapter(mContext, refreshDatas(homePageBean.getHotKeywords()));
                        tagFlowLayout.setAdapter(adapter);

                    }
                });
                tagFlowLayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
                    @Override
                    public boolean onTagClick(View view, int position, FlowLayout parent) {
                        Bundle bundle = new Bundle();
                        bundle.putString(HuaShuaListActivity.EXTRA_KEY_WORDS,adapter.getDatas().get(position).getName());
                        readyGo(HuaShuaListActivity.class,bundle);
                        return false;
                    }
                });
            }
        };

        adapterList.add(homeFlowAdapter);

    }

    public List<HotKeywordsBean> refreshDatas(List<HotKeywordsBean> datas) {
        List<HotKeywordsBean> radomList = new ArrayList<>();
        int arr[] = RandomUntil.randomArr(new int[6], datas.size() - 1);
        for (int i : arr) {
            radomList.add(datas.get(i));
        }
        return radomList;
    }


    private void initArticleArea() {
        LayoutHelper layoutHelper = new LinearLayoutHelper();
        HomeAdapter homeArticleAdapter = new HomeAdapter(mContext, layoutHelper, homePageBean.getNewArticle_date().size(), TYPE_ARTICLE, R.layout.home_new_article_layout) {
            @Override
            public void onBindViewHolder(BaseViewHolder holder, int position) {

                super.onBindViewHolder(holder, position);
                if (position != 0) {
                    TextView tv = holder.getView(R.id.home_new_article_top_tv);
                    tv.setVisibility(View.GONE);
                }
                if (position != getItemCount() - 1) {
                    FrameLayout layout = holder.getView(R.id.home_new_article_zhanwei_fr);
                    layout.setVisibility(View.GONE);
                }
                TextView moreTv = holder.getView(R.id.home_article_more_tv);
                moreTv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Bundle bundle = new Bundle();
                        bundle.putInt(ZhuanLanListActivity.EXTRA_ENTER_TYPE,1);
                        readyGo(ZhuanLanListActivity.class,bundle);
                    }
                });
                TextView newTimeTv = holder.getView(R.id.home_new_article_time_tv);
                newTimeTv.setText(homePageBean.getNewArticle_date().get(position));
                final List<HomeArticleBean> articleBeanList = homePageBean.getNewArticles().get(homePageBean.getNewArticle_date().get(position));

                ListView lv = holder.getView(R.id.home_new_article_lv);
                ViewGroup.LayoutParams layoutParams = lv.getLayoutParams();
                layoutParams.height = DisplayUtil.dip2px(mContext, (float) articleBeanList.size() * 120);
                lv.setLayoutParams(layoutParams);

                lv.setAdapter(new BaseAdapter() {
                    @Override
                    public int getCount() {
                        return articleBeanList.size();
                    }

                    @Override
                    public Object getItem(int position) {
                        return articleBeanList.size();
                    }

                    @Override
                    public long getItemId(int position) {
                        return position;
                    }

                    @Override
                    public View getView(int position, View convertView, ViewGroup parent) {
                        HomeArticleBean articleBean = articleBeanList.get(position);
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
                        bundle.putString(ZhuanLanDetailActivity.EXTRA_ARTICLE_ID, articleBeanList.get(position).getId());
                        readyGo(ZhuanLanDetailActivity.class, bundle);
                    }
                });
                //LinearLayout containerL=holder.getView(R.id.home_new_article_ll);
//                containerL.removeAllViews();
//                List<HomeArticleBean> articleBeanList = homePageBean.getNewArticles().get(homePageBean.getNewArticle_date().get(position));
//                for(HomeArticleBean articleBean:articleBeanList){
//                    LinearLayout linearLayout = (LinearLayout) LayoutInflater.from(mContext).inflate(R.layout.item_home_new_article_layout,null);
//                    ImageView iv = linearLayout.findViewById(R.id.item_home_article_iv_1);
//                    TextView titleTv = linearLayout.findViewById(R.id.item_home_article_title_1);
//                    TextView descTv = linearLayout.findViewById(R.id.item_home_article_desc_1);
//                    TextView timeTv = linearLayout.findViewById(R.id.item_home_article_time_1);
//                    timeTv.setText(articleBean.getCreate_date());
//                    descTv.setText(articleBean.getDescription());
//                    titleTv.setText(articleBean.getTitle());
//                    Glide.with(mContext).load(articleBean.getImage_url()).into(iv);
//                    containerL.addView(linearLayout);
//
//                }
            }
        };
        adapterList.add(homeArticleAdapter);
    }


    @Override
    public void getHomeDataSuccess(HomePageBean homePageBean) {
        this.homePageBean = homePageBean;
        if (isFirstLoad) {
            initBanner();
            initFunctionArea();
            initFlowArea();
            initAD();
            initArticleArea();
            delegateAdapter.setAdapters(adapterList);
            showOriginView();
            isFirstLoad = false;
        } else {
            homeRefreshLayout.finishRefresh(300);
            delegateAdapter.notifyItemChanged(0);
            delegateAdapter.notifyItemChanged(2);
            delegateAdapter.notifyItemChanged(3);
        }

    }

    @Override
    public void getHomeDataFail(String msg) {

    }

    @Override
    public void goToIm() {
        String accountId = PreferencesUtils.getString(mContext, CommonConstants.KEY_ACCOUNT_ID,"");
        String md5AccoundId = MD5Utils.MD5Encode(accountId,"utf-8");
        ChatClient.getInstance().login(accountId, md5AccoundId, new Callback() {
            @Override
            public void onSuccess() {
                Intent intent = new IntentBuilder(mContext)
                        .setServiceIMNumber(Constants.IM.IM_SERVICE_ID)
                        .build();
                startActivity(intent);
            }

            @Override
            public void onError(int i, String s) {

            }

            @Override
            public void onProgress(int i, String s) {

            }
        });

    }
}
