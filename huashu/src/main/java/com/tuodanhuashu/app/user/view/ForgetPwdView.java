package com.tuodanhuashu.app.user.view;

import com.company.common.base.BaseView;

public interface ForgetPwdView extends BaseView {

    public void resetPwdSuccess(String msg);

    public void resetPwdFail(String msg);

    public void getSMSSuccess(String msg);

    public void getSMSFail(String msg);
}
