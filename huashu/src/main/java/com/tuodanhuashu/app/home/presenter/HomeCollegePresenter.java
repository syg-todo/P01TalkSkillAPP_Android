package com.tuodanhuashu.app.home.presenter;

import android.content.Context;

import com.company.common.base.BasePresenter;
import com.company.common.base.BaseView;
import com.company.common.net.ServerResponse;
import com.company.common.utils.JsonUtils;
import com.tuodanhuashu.app.home.bean.HomeCollegePageBean;
import com.tuodanhuashu.app.home.biz.HomeCollegeFragmentBiz;
import com.tuodanhuashu.app.home.view.HomeCollegeView;

public class HomeCollegePresenter extends BasePresenter {
    private HomeCollegeView collegeView;

    private Context context;

    private HomeCollegeFragmentBiz collegeFragmentBiz;

    private static final int TAG_COLLEGE = 1;

    public HomeCollegePresenter(HomeCollegeView collegeView, Context context) {
        this.collegeView = collegeView;
        this.context = context;
        collegeFragmentBiz = new HomeCollegeFragmentBiz(this,context);
    }

    public void requestZhuanLanData(){
        collegeFragmentBiz.requestHomeCollegePage(TAG_COLLEGE);
    }

    @Override
    public void OnSuccess(ServerResponse serverResponse, int tag) {
        super.OnSuccess(serverResponse, tag);
        HomeCollegePageBean zhuanLanPageBean = JsonUtils.getJsonToBean(serverResponse.getData(), HomeCollegePageBean.class);
        collegeView.getCollegeDataSuccess(zhuanLanPageBean);
    }

    @Override
    public void onFail(String msg, int code, int tag) {
        super.onFail(msg, code, tag);
        collegeView.showToast(msg);
    }

    @Override
    public void onError(int tag) {
        super.onError(tag);
    }

    @Override
    public BaseView getBaseView() {
        return collegeView;
    }
}
