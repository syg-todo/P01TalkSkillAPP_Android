package com.tuodanhuashu.app.user.view;

import com.company.common.base.BaseView;

public interface ILoginView extends BaseView {

    public void onLoginSuccess();

    public void onLiginFail(String msg);
}
