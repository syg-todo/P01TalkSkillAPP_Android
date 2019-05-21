package com.tuodanhuashu.app.course.view;

import com.company.common.base.BaseView;
import com.tuodanhuashu.app.course.bean.CourseDetailBean;

public interface CourseDetailView extends BaseView {

    void getCourseDetailSuccess(CourseDetailBean courseDetailBean);

    void getCourseDetailFail(String msg);

}
