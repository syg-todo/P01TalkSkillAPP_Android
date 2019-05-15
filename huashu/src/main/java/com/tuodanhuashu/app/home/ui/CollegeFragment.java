package com.tuodanhuashu.app.home.ui;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.alibaba.android.vlayout.layout.LinearLayoutHelper;
import com.chad.library.adapter.base.BaseViewHolder;
import com.company.common.utils.RandomUntil;
import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.tuodanhuashu.app.R;
import com.tuodanhuashu.app.base.HuaShuBaseFragment;
import com.tuodanhuashu.app.home.adapter.HomeAdapter;
import com.tuodanhuashu.app.home.bean.HomeBannerBean;
import com.tuodanhuashu.app.home.bean.HomeCollegePageBean;
import com.tuodanhuashu.app.home.bean.HomeZhuanLanPageBean;
import com.tuodanhuashu.app.home.presenter.HomeCollegePresenter;
import com.tuodanhuashu.app.home.presenter.HomeZhuanLanPresenter;
import com.tuodanhuashu.app.home.view.HomeCollegeView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class CollegeFragment extends HuaShuBaseFragment implements HomeCollegeView {
//    @BindView(R.id.college_refresheader)
//    MaterialHeader collegeRefresheader;
//    @BindView(R.id.college_rlv)
//    RecyclerView collegeRlv;
//    @BindView(R.id.college_refreshLayout)
//    SmartRefreshLayout collegeRefreshLayout;

    private DelegateAdapter delegateAdapter;

    private List<DelegateAdapter.Adapter> adapterList;

    private static final int TYPE_HEADER = 1;

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

//    @Override
//    protected void initView(View view) {
//        super.initView(view);
//        collegePresenter = new HomeCollegePresenter(this, mContext);
//        adapterList = new ArrayList<>();
//        collegeRefresheader.setColorSchemeColors(mContext.getResources().getColor(R.color.colorAccent));
//        VirtualLayoutManager layoutManager = new VirtualLayoutManager(mContext);
//        collegeRlv.setLayoutManager(layoutManager);
//        RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
//        collegeRlv.setRecycledViewPool(viewPool);
//        viewPool.setMaxRecycledViews(0, 10);
//        delegateAdapter = new DelegateAdapter(layoutManager, true);
//        collegeRlv.setAdapter(delegateAdapter);
////        initBanner();
////        initSearch();
////        initArticle();
////        initCategory();
////        initAD();
////        initArticleList();
////        delegateAdapter.setAdapters(adapterList);
//        collegePresenter.requestZhuanLanData();
//    }

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
//            initArticle();
//            initArticle2();
//            initCategory();
//            initAD();
//            initArticleList();
            delegateAdapter.setAdapters(adapterList);
            isFirstRefresh = false;
        } else {

        }
    }

    private void initBanner() {
    }

    private void initSearch() {
    }




}
