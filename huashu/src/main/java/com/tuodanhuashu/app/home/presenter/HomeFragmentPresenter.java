package com.tuodanhuashu.app.home.presenter;

import android.content.Context;

import com.company.common.base.BasePresenter;
import com.company.common.base.BaseView;
import com.company.common.net.ServerResponse;
import com.company.common.utils.JsonUtils;
import com.tuodanhuashu.app.home.HomeFragmenBiz;
import com.tuodanhuashu.app.home.bean.HomePageBean;
import com.tuodanhuashu.app.home.view.HomeFragmentView;

public class HomeFragmentPresenter extends BasePresenter {

    private HomeFragmentView homeFragmentView;

    private Context mContext;

    private HomeFragmenBiz homeFragmenBiz;


    private static final int TAG_REQUEST_HOME_DATA = 1;

    public HomeFragmentPresenter(HomeFragmentView homeFragmentView, Context mContext) {
        this.homeFragmentView = homeFragmentView;
        this.mContext = mContext;
        homeFragmenBiz = new HomeFragmenBiz(this,mContext);
    }

    @Override
    public BaseView getBaseView() {
        return homeFragmentView;
    }

    public void requestHomeFragmentData(){
        homeFragmenBiz.requestHomePage(TAG_REQUEST_HOME_DATA);
    }

    @Override
    public void OnSuccess(ServerResponse serverResponse, int tag) {
        super.OnSuccess(serverResponse, tag);
        switch (tag){
            case TAG_REQUEST_HOME_DATA:
                HomePageBean homePageBean = JsonUtils.getJsonToBean(serverResponse.getData(),HomePageBean.class);
                homeFragmentView.getHomeDataSuccess(homePageBean);
                break;
        }
    }
}
