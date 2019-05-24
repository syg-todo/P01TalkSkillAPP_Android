package com.tuodanhuashu.app.course.view;

import com.company.common.base.BaseView;
import com.tuodanhuashu.app.course.bean.CourseClassBean;
import com.tuodanhuashu.app.course.bean.CourseWithBannerBean;
import com.tuodanhuashu.app.course.bean.MasterBean;
import com.tuodanhuashu.app.home.bean.HomeCourseBean;

import java.util.List;

public interface CourseListView extends BaseView {

    void getCourseListSuccess(List<HomeCourseBean> courseBeanList);

    void getCourseListFail(String msg);

    void getCourseWithBannerSuccess(CourseWithBannerBean courseWithBannerBean);

    void getCourseCommunityFail(String msg);

    void getClassListSuccess(List<CourseClassBean> courseClassList);

    void getClassListFail(String msg);

    void getMasterListSuccess(List<MasterBean> masterBeans);

    void getMasterListFail(String msg);

}
