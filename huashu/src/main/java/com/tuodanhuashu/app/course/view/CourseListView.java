package com.tuodanhuashu.app.course.view;

import com.company.common.base.BaseView;
import com.tuodanhuashu.app.course.bean.CourseClassBean;
import com.tuodanhuashu.app.course.bean.CourseWithBannerBean;
import com.tuodanhuashu.app.course.bean.MasterBean;
import com.tuodanhuashu.app.home.bean.HomeCourseBean;

import java.util.List;

public interface CourseListView extends BaseView {

    public void getCourseListSuccess(List<HomeCourseBean> courseBeanList);

    public void getCourseListFail(String msg);

    public void getCourseWithBannerSuccess(CourseWithBannerBean courseWithBannerBean);

    public void getCourseCommunityFail(String msg);

    public void getClassListSuccess(List<CourseClassBean> courseClassList);

    public void getClassListFail(String msg);

    public void getMasterListSuccess(List<MasterBean> masterBeans);

    public void getMasterListFail(String msg);

}
