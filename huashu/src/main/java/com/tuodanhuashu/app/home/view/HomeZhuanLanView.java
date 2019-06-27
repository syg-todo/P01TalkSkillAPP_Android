package com.tuodanhuashu.app.home.view;

import com.company.common.base.BaseView;
import com.tuodanhuashu.app.home.bean.HomeZhuanLanPageBean;

public interface HomeZhuanLanView extends BaseView {

    public void getZhuanLanDataSuccess(HomeZhuanLanPageBean zhuanLanPageBean);

    public void getZhuanLanDataFail();
}
