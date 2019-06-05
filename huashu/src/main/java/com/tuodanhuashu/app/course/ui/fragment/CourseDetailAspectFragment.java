package com.tuodanhuashu.app.course.ui.fragment;


import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;

import com.tuodanhuashu.app.R;
import com.tuodanhuashu.app.base.HuaShuBaseFragment;
import com.tuodanhuashu.app.base.SimpleItemDecoration;
import com.tuodanhuashu.app.course.bean.CourseDetailBean;
import com.tuodanhuashu.app.course.bean.CourseDetailModel;
import com.tuodanhuashu.app.course.ui.CourseListActivity;
import com.tuodanhuashu.app.course.ui.adapter.RVRecommendationAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class CourseDetailAspectFragment extends HuaShuBaseFragment {
    private static final String TAG = CourseDetailAspectFragment.class.getSimpleName();
    @BindView(R.id.rv_course_detail_asspect)
    RecyclerView recyclerView;
    @BindView(R.id.aspect)
    WebView webView;
    @BindView(R.id.tv_course_detail_asspect_mroe)
    TextView tvMore;

    private List<CourseDetailBean.RecommendCoursesBean> recommendCoursesBeanList = new ArrayList<>();
    private CourseDetailModel model;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_course_detail_aspect;
    }


    @Override
    protected void initView(View view) {
        super.initView(view);

        model = ViewModelProviders.of(getActivity()).get(CourseDetailModel.class);
        recommendCoursesBeanList = model.getCourseDetail().getValue().getRecommendCourses();

        String html = model.getCourseDetail().getValue().getCourse().getCourse_detail();

        webView.loadData(html, "text/html", "uft-8");
        recyclerView.setAdapter(new RVRecommendationAdapter(mContext, recommendCoursesBeanList));
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.addItemDecoration(new SimpleItemDecoration());

        tvMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt(CourseListActivity.EXTRA_ENTER_TYPE,5);
                readyGo(CourseListActivity.class,bundle);
            }
        });
    }

    public CourseDetailAspectFragment() {
    }

}
