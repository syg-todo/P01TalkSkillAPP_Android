package com.tuodanhuashu.app.course.view;

import com.company.common.base.BaseView;
import com.tuodanhuashu.app.course.bean.CourseDetailBean;

public interface CourseDetailView extends BaseView {

    void getCourseDetailSuccess(CourseDetailBean courseDetailBean);

    void getCourseDetailFail(String msg);

    void getBuyCourseSuccess();

    void getBuyCourseFail(String msg);

    void getLikeCommentSuccess();
    void getLikeCommentFail(String msg);

    void getUnlikeCommentSuccess();
    void getUnlikeCommentFail(String msg);
}
