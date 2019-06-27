package com.tuodanhuashu.app.home.presenter;

import android.content.Context;
import android.util.Log;

import com.company.common.base.BasePresenter;
import com.company.common.base.BaseView;
import com.company.common.net.ServerResponse;
import com.company.common.utils.JsonUtils;
import com.tuodanhuashu.app.home.bean.HomeZhuanLanPageBean;
import com.tuodanhuashu.app.home.biz.HomeZhuanLanFragmentBiz;
import com.tuodanhuashu.app.home.view.HomeZhuanLanView;

public class HomeZhuanLanPresenter extends BasePresenter {

    private HomeZhuanLanView zhuanLanView;

    private Context context;

    private HomeZhuanLanFragmentBiz zhuanLanFragmentBiz;

    private static final int TAG_ZHUANLAN = 1;

    public HomeZhuanLanPresenter(HomeZhuanLanView zhuanLanView, Context context) {
        this.zhuanLanView = zhuanLanView;
        this.context = context;
        zhuanLanFragmentBiz = new HomeZhuanLanFragmentBiz(this,context);
    }

    public void requestZhuanLanData(){
        zhuanLanFragmentBiz.requestHomeZhuanLanPage(TAG_ZHUANLAN);
    }

    @Override
    public void OnSuccess(ServerResponse serverResponse, int tag) {
        super.OnSuccess(serverResponse, tag);
        HomeZhuanLanPageBean zhuanLanPageBean = JsonUtils.getJsonToBean(serverResponse.getData(),HomeZhuanLanPageBean.class);
        zhuanLanView.getZhuanLanDataSuccess(zhuanLanPageBean);
    }

    @Override
    public void onFail(String msg, int code, int tag) {
        super.onFail(msg, code, tag);
        zhuanLanView.showToast(msg);
    }

    @Override
    public void onError(int tag) {
        super.onError(tag);
    }

    @Override
    public BaseView getBaseView() {
        return zhuanLanView;
    }
}
