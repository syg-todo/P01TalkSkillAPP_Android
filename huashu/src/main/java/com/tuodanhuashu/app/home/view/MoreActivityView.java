package com.tuodanhuashu.app.home.view;

import com.company.common.base.BaseView;
import com.tuodanhuashu.app.home.bean.CollegeActivityBean;

import java.util.List;

public interface MoreActivityView extends BaseView {
    void getMoreActivitySuccess(List<CollegeActivityBean> collegeActivityBeans);
    void getMoreActivityFail(String msg);
}
