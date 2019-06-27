package com.tuodanhuashu.app.user.view;

import com.company.common.base.BaseView;

public interface LoginByPhoneView extends BaseView {

    public void onLoginSuccess();

    public void onLiginFail(String msg);

    public void getSMSSuccess(String msg);

    public void getSMSFail(String msg);
}
