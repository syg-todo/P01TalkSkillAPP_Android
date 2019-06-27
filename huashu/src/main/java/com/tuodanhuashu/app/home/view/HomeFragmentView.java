package com.tuodanhuashu.app.home.view;

import com.company.common.base.BaseView;
import com.tuodanhuashu.app.home.bean.HomePageBean;

public interface HomeFragmentView extends BaseView {

    public void getHomeDataSuccess(HomePageBean homePageBean);

    public void getHomeDataFail(String msg);

    public void goToIm();
}
