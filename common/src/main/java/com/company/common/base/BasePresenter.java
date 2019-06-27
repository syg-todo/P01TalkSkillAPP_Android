package com.company.common.base;

import com.company.common.net.OnRequestListener;
import com.company.common.net.ServerResponse;

public abstract class BasePresenter implements OnRequestListener{

    public abstract BaseView getBaseView();


    @Override
    public void OnSuccess(ServerResponse serverResponse, int tag) {

    }


    @Override
    public void onFail(String msg, int code, int tag) {

    }


    @Override
    public void onError(int tag) {
        getBaseView().showToast("网络连接异常，请稍后再试");
    }

}
