package com.tuodanhuashu.app.course.ui.fragment;

import android.arch.lifecycle.ViewModelProviders;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.tuodanhuashu.app.R;
import com.tuodanhuashu.app.base.HuaShuBaseFragment;
import com.tuodanhuashu.app.base.SimpleItemDecoration;
import com.tuodanhuashu.app.course.bean.MasterBean;
import com.tuodanhuashu.app.course.bean.MasterDetailModel;
import com.tuodanhuashu.app.course.ui.adapter.CourseListAdapter;
import com.tuodanhuashu.app.home.bean.HomeCourseBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class CourseListFragment extends HuaShuBaseFragment {

    @BindView(R.id.rv_course_list)
    RecyclerView recyclerView;

    private MasterDetailModel model;
    private List<HomeCourseBean> courseBeanList = new ArrayList<>();


    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_course_list;
    }

    @Override
    protected void initView(View view) {
        super.initView(view);

        model = ViewModelProviders.of(getActivity()).get(MasterDetailModel.class);
        courseBeanList = model.getCourseDetail().getValue().getCourse();
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.setAdapter(new CourseListAdapter(mContext, courseBeanList));
        recyclerView.addItemDecoration(new SimpleItemDecoration());

    }
}
