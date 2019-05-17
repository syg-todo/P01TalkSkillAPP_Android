package com.tuodanhuashu.app.course.ui;

import android.support.v7.widget.RecyclerView;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.alibaba.android.vlayout.layout.LinearLayoutHelper;
import com.chad.library.adapter.base.BaseViewHolder;
import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.tuodanhuashu.app.R;
import com.tuodanhuashu.app.base.HuaShuBaseActivity;
import com.tuodanhuashu.app.home.adapter.HomeAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class CourseDetailActivity extends HuaShuBaseActivity {
    @BindView(R.id.rv_course_detail)
    RecyclerView recyclerView;

    @BindView(R.id.refresheader_course_detail)
    MaterialHeader refresheaderCourseDetail;
    @BindView(R.id.refresh_course_detail)
    SmartRefreshLayout refreshLayoutCourseDetail;

    private DelegateAdapter delegateAdapter;

    private List<DelegateAdapter.Adapter> adapterList;


    private static final int TYPE_TOP = 1;

    private static final int TYPE_SEARCH = 2;

    private static final int TYPE_ARTICLE = 3;

    private static final int TYPE_CATEGORY = 4;

    private static final int TYPE_AD = 5;

    private static final int TYPE_LIST = 6;
    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_course_datail;
    }

    @Override
    protected void initView() {
        super.initView();

        adapterList = new ArrayList<>();
        refresheaderCourseDetail.setColorSchemeColors(mContext.getResources().getColor(R.color.colorAccent));
        VirtualLayoutManager layoutManager = new VirtualLayoutManager(mContext);
        recyclerView.setLayoutManager(layoutManager);
        RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
        recyclerView.setRecycledViewPool(viewPool);
        viewPool.setMaxRecycledViews(0, 10);
        delegateAdapter = new DelegateAdapter(layoutManager, true);
        recyclerView.setAdapter(delegateAdapter);
        initCourseTop();
//        initSearch();
//        initArticle();
//        initCategory();
//        initAD();
//        initArticleList();
        delegateAdapter.setAdapters(adapterList);

    }

    private void initCourseTop() {
        HomeAdapter adapterCourseTop = new HomeAdapter(mContext,new LinearLayoutHelper(),1,TYPE_TOP,R.layout.course_detail_top_layout){
            @Override
            public void onBindViewHolder(BaseViewHolder holder, int position) {
                super.onBindViewHolder(holder, position);
            }
        };
        adapterList.add(adapterCourseTop);
    }
}
