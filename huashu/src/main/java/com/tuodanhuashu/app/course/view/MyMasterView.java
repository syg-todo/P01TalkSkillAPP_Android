package com.tuodanhuashu.app.course.view;

import com.company.common.base.BaseView;
import com.tuodanhuashu.app.course.bean.MasterBean;

import java.util.List;

public interface MyMasterView extends BaseView {

    void geMyMasterSuccess(List<MasterBean> masterBeanList);

    void getMyMasterFail(String msg);

}
