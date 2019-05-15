package com.tuodanhuashu.app.home.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.alibaba.android.vlayout.layout.LinearLayoutHelper;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseViewHolder;
import com.company.common.utils.KeyboardUtils;
import com.company.common.utils.RandomUntil;
import com.company.common.utils.StringUtils;
import com.ms.banner.Banner;
import com.ms.banner.BannerConfig;
import com.ms.banner.holder.BannerViewHolder;
import com.ms.banner.holder.HolderCreator;
import com.ms.banner.listener.OnBannerClickListener;
import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.tuodanhuashu.app.R;
import com.tuodanhuashu.app.base.HuaShuBaseFragment;
import com.tuodanhuashu.app.home.adapter.HomeAdapter;
import com.tuodanhuashu.app.home.adapter.HomeBannerViewHolder;
import com.tuodanhuashu.app.home.bean.HomeBannerBean;
import com.tuodanhuashu.app.home.bean.HomeCollegePageBean;
import com.tuodanhuashu.app.home.bean.HomeZhuanLanPageBean;
import com.tuodanhuashu.app.home.presenter.HomeCollegePresenter;
import com.tuodanhuashu.app.home.presenter.HomeZhuanLanPresenter;
import com.tuodanhuashu.app.home.view.HomeCollegeView;
import com.tuodanhuashu.app.huashu.ui.HuaShuaListActivity;
import com.tuodanhuashu.app.zhuanlan.ui.ZhuanLanDetailActivity;
import com.tuodanhuashu.app.zhuanlan.ui.ZhuanLanSearchActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

public class CollegeFragment extends HuaShuBaseFragment implements HomeCollegeView {
    @BindView(R.id.college_refresheader)
    MaterialHeader collegeRefresheader;
    @BindView(R.id.college_rlv)
    RecyclerView collegeRlv;
    @BindView(R.id.college_refreshlayout)
    SmartRefreshLayout collegeRefreshLayout;

    private DelegateAdapter delegateAdapter;

    private List<DelegateAdapter.Adapter> adapterList;

    private static final int TYPE_BANNER = 1;

    private static final int TYPE_SEARCH = 2;

    private static final int TYPE_COURSE = 3;

    private static final int TYPE_ACTIVITY = 4;

    private static final int TYPE_BOUTIQUE = 5;

    private static final int TYPE_RECOMMEND = 6;

    private HomeCollegePresenter collegePresenter;

    private HomeCollegePageBean collegePageBean;

    private boolean isFirstRefresh = true;

    int arr[] = new int[3];

    public CollegeFragment() {
        // Required empty public constructor
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_college;
    }

    @Override
    protected void initView(View view) {
        super.initView(view);
        collegePresenter = new HomeCollegePresenter(this, mContext);
        adapterList = new ArrayList<>();
        collegeRefresheader.setColorSchemeColors(mContext.getResources().getColor(R.color.colorAccent));
        VirtualLayoutManager layoutManager = new VirtualLayoutManager(mContext);
        collegeRlv.setLayoutManager(layoutManager);
        RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
        collegeRlv.setRecycledViewPool(viewPool);
        viewPool.setMaxRecycledViews(0, 10);
        delegateAdapter = new DelegateAdapter(layoutManager, true);
        collegeRlv.setAdapter(delegateAdapter);
//        initBanner();
//        initSearch();
//        initArticle();
//        initCategory();
//        initAD();
//        initArticleList();
//        delegateAdapter.setAdapters(adapterList);
        collegePresenter.requestZhuanLanData();
    }

    @Override
    public void getCollegeDataSuccess(HomeCollegePageBean collegePageBean) {
        this.collegePageBean = collegePageBean;
//        if(collegePageBean.getRecommendedArticles()!=null&&zhuanLanPageBean.getRecommendedArticles().size()>=3){
//            arr = RandomUntil.randomArr(new int[3],zhuanLanPageBean.getRecommendedArticles().size()-1);
//
//        }else if(collegePageBean.getRecommendedArticles().size()==1){
//            arr[0] = 0;
//            arr[1] = 0;
//        }else if(collegePageBean.getRecommendedArticles().size()==2){
//            arr[0] = 0;
//            arr[1] = 1;
//            arr[2] = 0;
//        }
        if (isFirstRefresh) {
            initBanner();
            initSearch();
            initCourse();
//            initArticle2();
//            initCategory();
//            initAD();
//            initArticleList();
            delegateAdapter.setAdapters(adapterList);
            isFirstRefresh = false;
        } else {

        }
    }

    private void initCourse() {
        HomeAdapter courseAdapter = new HomeAdapter(mContext,new LinearLayoutHelper(),1,TYPE_COURSE,R.layout.college_course_layout){
            @Override
            public void onBindViewHolder(BaseViewHolder holder, int position) {
                super.onBindViewHolder(holder, position);


                final ImageView iv1 = holder.getView(R.id.college_course_iv_1);
                final TextView tv1 = holder.getView(R.id.college_course_tv_1);
                final ImageView iv2 = holder.getView(R.id.college_course_iv_2);
                final TextView tv2 = holder.getView(R.id.college_course_tv_2);
                final ImageView iv3 = holder.getView(R.id.college_course_iv_3);
                final TextView tv3 = holder.getView(R.id.college_course_tv_3);

                Glide.with(mContext).load(R.mipmap.sorting_classes).into(iv1);
                tv1.setText("课程分类");
                Glide.with(mContext).load(R.mipmap.community_class).into(iv2);
                tv2.setText("社群课");
                Glide.with(mContext).load(R.mipmap.private_class).into(iv3);
                tv3.setText("私教课");

            }
        };
        adapterList.add(courseAdapter);
    }

    private void initBanner() {
        HomeAdapter bannerAdapter = new HomeAdapter(mContext, new LinearLayoutHelper(), 1, TYPE_BANNER, R.layout.zhuanlan_banner_layout) {
            @Override
            public void onBindViewHolder(BaseViewHolder holder, int position) {
                super.onBindViewHolder(holder, position);
                final List<HomeBannerBean> homeBannerBeanList = collegePageBean.getBanners();

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
                                i.setClass(mContext, HuaShuaListActivity.class);
                                b.putString(HuaShuaListActivity.EXTRA_KEY_WORDS, homeBannerBeanList.get(position).getKeyword());
                                i.putExtras(b);
                                startActivity(i);
                                break;
                            case "3":
                                i.setClass(mContext, ZhuanLanDetailActivity.class);
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
        HomeAdapter searchAdapter = new HomeAdapter(mContext, new LinearLayoutHelper(), 1, TYPE_SEARCH, R.layout.college_search_layout) {
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




}
