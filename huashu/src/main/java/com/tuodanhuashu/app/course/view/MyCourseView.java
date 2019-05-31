package com.tuodanhuashu.app.course.view;

import com.company.common.base.BaseView;
import com.tuodanhuashu.app.course.bean.MasterBean;
import com.tuodanhuashu.app.home.bean.MyCourseBean;

import java.util.List;

public interface MyCourseView extends BaseView {

    void geMyCourseSuccess(List<MyCourseBean> courseBeans);

    void getMyCourseFail(String msg);

}
