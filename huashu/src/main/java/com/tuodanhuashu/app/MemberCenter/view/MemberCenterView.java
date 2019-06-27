package com.tuodanhuashu.app.MemberCenter.view;

import com.company.common.base.BaseView;
import com.tuodanhuashu.app.MemberCenter.bean.MemberCenterBean;

public interface MemberCenterView extends BaseView {

    public void getUserInfoSucess(MemberCenterBean memberCenterBean);

    public void getInfoWithLogin();

    public void getInfoWithNoLogin();

    public void getUserInfoFail(String msg);

    public void getAboutUsUrlSuccess(String url);
}
