package com.tuodanhuashu.app.course.ui.fragment;


import android.arch.lifecycle.ViewModelProviders;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.tuodanhuashu.app.R;
import com.tuodanhuashu.app.base.HuaShuBaseFragment;
import com.tuodanhuashu.app.base.SimpleItemDecoration;
import com.tuodanhuashu.app.course.bean.CourseDetailBean;
import com.tuodanhuashu.app.course.bean.CourseDetailModel;
import com.tuodanhuashu.app.course.ui.adapter.RVRecommendationAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class CourseDetailAspectFragment extends HuaShuBaseFragment {
    private static final String TAG = CourseDetailAspectFragment.class.getSimpleName();
    @BindView(R.id.rv_course_detail_asspect)
    RecyclerView recyclerView;

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

        String data = model.getCourseDetail().getValue().getSections().get(0).getSection_intro();
        Log.d(TAG,recommendCoursesBeanList.get(0).getImage_url());
        recyclerView.setAdapter(new RVRecommendationAdapter(mContext, recommendCoursesBeanList));
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));

        recyclerView.addItemDecoration(new SimpleItemDecoration());

    }

    public CourseDetailAspectFragment() {
    }

}
