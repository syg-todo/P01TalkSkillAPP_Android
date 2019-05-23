package com.tuodanhuashu.app.course.ui.fragment;


import android.arch.lifecycle.ViewModelProviders;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

        for (int i = 0; i < recommendCoursesBeanList.size(); i++) {
            recommendCoursesBeanList.get(i).setImage_url("http://img1.imgtn.bdimg.com/it/u=3049673303,4028148324&fm=26&gp=0.jpg");
        }
        recyclerView.setAdapter(new RVRecommendationAdapter(mContext, recommendCoursesBeanList));
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));

        recyclerView.addItemDecoration(new SimpleItemDecoration());

    }

    public CourseDetailAspectFragment() {
    }

}
