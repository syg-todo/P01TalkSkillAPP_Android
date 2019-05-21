package com.tuodanhuashu.app.course.presenter;

import android.content.Context;

import com.company.common.base.BasePresenter;
import com.company.common.base.BaseView;
import com.company.common.net.ServerResponse;
import com.company.common.utils.JsonUtils;
import com.tuodanhuashu.app.course.bean.CourseDetailBean;
import com.tuodanhuashu.app.course.biz.CourseDetailBiz;
import com.tuodanhuashu.app.course.biz.CourseListBiz;
import com.tuodanhuashu.app.course.view.CourseDetailView;
import com.tuodanhuashu.app.course.view.CourseListView;

public class CourseDetailPresenter extends BasePresenter {

    private Context context;

    private CourseDetailView courseDetailView;

    private CourseDetailBiz courseDetailBiz;

    private static final int TAG_COURSE_DETAIL = 1;

    public CourseDetailPresenter(Context context, CourseDetailView courseDetailView) {
        this.context = context;
        this.courseDetailView = courseDetailView;
        courseDetailBiz = new CourseDetailBiz(context, this);
    }

    public void requestCourseDetail(String accessToken, String courseId) {
        courseDetailBiz.requestCourseDetailByCourseId(TAG_COURSE_DETAIL, accessToken, courseId);
    }

    @Override
    public void OnSuccess(ServerResponse serverResponse, int tag) {

        super.OnSuccess(serverResponse, tag);
        switch (tag) {
            case TAG_COURSE_DETAIL:
                CourseDetailBean courseDetailBean = JsonUtils.getJsonToBean(serverResponse.getData(), CourseDetailBean.class);
                courseDetailView.getCourseDetailSuccess(courseDetailBean);
                break;
        }
    }

    @Override
    public void onFail(String msg, int code, int tag) {
        super.onFail(msg, code, tag);
        courseDetailView.getCourseDetailFail(msg);
    }

    @Override
    public BaseView getBaseView() {
        return courseDetailView;
    }
}
