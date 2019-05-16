package com.tuodanhuashu.app.course.view;

import com.company.common.base.BaseView;
import com.tuodanhuashu.app.course.bean.CourseClassBean;
import com.tuodanhuashu.app.home.bean.HomeCourseBean;
import com.tuodanhuashu.app.zhuanlan.bean.ArticleClassBean;
import com.tuodanhuashu.app.zhuanlan.bean.ArticleListItemBean;

import java.util.List;

public interface CourseListView extends BaseView {

    public void getCourseListSuccess(List<HomeCourseBean> courseBeanList);

    public void getCourseListFail(String msg);


    public void getClassListSuccess(List<CourseClassBean> courseClassList);

    public void getClassListFail(String msg);

}
