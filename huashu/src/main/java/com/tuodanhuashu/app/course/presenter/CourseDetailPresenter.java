package com.tuodanhuashu.app.course.presenter;

import android.content.Context;
import android.util.Log;

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

    private static final int TAG_RECORD_MASTER = 2;

    private static final int TAG_UNRECORD_MASTER = 3;


    public CourseDetailPresenter(Context context, CourseDetailView courseDetailView) {
        this.context = context;
        this.courseDetailView = courseDetailView;
        courseDetailBiz = new CourseDetailBiz(context, this);
    }

    public void requestCourseDetail(String accessToken, String courseId) {
        courseDetailBiz.requestCourseDetailByCourseId(TAG_COURSE_DETAIL, accessToken, courseId);
    }

    public void recordMaster(String accessToken, String masterId) {
        courseDetailBiz.recordMaster(TAG_RECORD_MASTER, accessToken, masterId);
    }

    public void unrecordMaster(String accessToken, String masterId) {
        courseDetailBiz.unrecordMaster(TAG_UNRECORD_MASTER, accessToken, masterId);
    }

    @Override
    public void OnSuccess(ServerResponse serverResponse, int tag) {
        super.OnSuccess(serverResponse, tag);
        switch (tag) {
            case TAG_COURSE_DETAIL:
                CourseDetailBean courseDetailBean = JsonUtils.getJsonToBean(serverResponse.getData(), CourseDetailBean.class);
                Log.d("CourseDetailActivity","presenter:"+courseDetailBean.getCourse().getIs_checkout());
                courseDetailView.getCourseDetailSuccess(courseDetailBean);
                break;
            case TAG_RECORD_MASTER:
                Log.d("111",serverResponse.getCode()+"");
//                CourseDetailBean courseDetailRecord = JsonUtils.getJsonToBean(serverResponse.getData(),CourseDetailBean.class);
//                courseDetailView.getCourseDetailSuccess(courseDetailRecord);
                break;
            case TAG_UNRECORD_MASTER:
                Log.d("111",serverResponse.getCode()+"");
//                CourseDetailBean courseDetailUnrecord = JsonUtils.getJsonToBean(serverResponse.getData(),CourseDetailBean.class);
//                courseDetailView.getCourseDetailSuccess(courseDetailUnrecord);
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
