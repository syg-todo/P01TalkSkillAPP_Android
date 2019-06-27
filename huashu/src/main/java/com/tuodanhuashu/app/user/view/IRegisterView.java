package com.tuodanhuashu.app.user.view;

import com.company.common.base.BaseView;

public interface IRegisterView extends BaseView {

    public void onRegisterSuccess();

    public void onRegisterFail(String msg);

    public void onReceiveMsgSuccess();

    public void onReceiveMsgFail(String msg);

    public void getMessageUrlSuccess(String data);

}
