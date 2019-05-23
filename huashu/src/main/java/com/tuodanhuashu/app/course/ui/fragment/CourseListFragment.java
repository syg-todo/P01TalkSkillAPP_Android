package com.tuodanhuashu.app.course.ui.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.tuodanhuashu.app.R;
import com.tuodanhuashu.app.base.HuaShuBaseFragment;
import com.tuodanhuashu.app.base.SimpleItemDecoration;
import com.tuodanhuashu.app.course.ui.adapter.CourseListAdapter;
import com.tuodanhuashu.app.home.bean.HomeCourseBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class CourseListFragment extends HuaShuBaseFragment {

    @BindView(R.id.rv_course_list)
    RecyclerView recyclerView;

    private List<HomeCourseBean> courseBeanList = new ArrayList<>();

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_course_list;
    }

    @Override
    protected void initView(View view) {
        super.initView(view);


        HomeCourseBean courseBean = new HomeCourseBean();

        courseBean.setCourse_name("施琪嘉创伤30讲：走出心理阴影，重塑强大内心");
        courseBean.setSale_price(99);
        courseBean.setSale_price(199);
        courseBean.setJoin_count(10000);
        courseBean.setImage_url("https://imgsa.baidu.com//forum//w%3D580%3B//sign=96008d2a6e2762d0803ea4b790d709fa//6159252dd42a28340b77040256b5c9ea14cebfe1.jpg");

        courseBeanList.add(courseBean);
        courseBeanList.add(courseBean);
        courseBeanList.add(courseBean);
        courseBeanList.add(courseBean);

        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.setAdapter(new CourseListAdapter(mContext, courseBeanList));
        recyclerView.addItemDecoration(new SimpleItemDecoration());

    }
}
