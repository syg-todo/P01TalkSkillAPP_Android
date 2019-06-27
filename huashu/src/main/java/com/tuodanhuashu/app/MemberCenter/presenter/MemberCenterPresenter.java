package com.tuodanhuashu.app.MemberCenter.presenter;

import android.content.Context;

import com.company.common.base.BasePresenter;
import com.company.common.base.BaseView;
import com.company.common.net.ServerResponse;
import com.company.common.utils.JsonUtils;
import com.tuodanhuashu.app.MemberCenter.bean.MemberCenterBean;
import com.tuodanhuashu.app.MemberCenter.biz.MemberCenterBiz;
import com.tuodanhuashu.app.MemberCenter.view.MemberCenterView;

public class MemberCenterPresenter extends BasePresenter {

    private Context context;

    private MemberCenterView memberCenterView;

    private MemberCenterBiz memberCenterBiz;

    private static final int TAG_USER_INFO = 1;

    private static final int TAG_ABOUT_URL = 2;

    public MemberCenterPresenter(Context context, MemberCenterView memberCenterView) {
        this.context = context;
        this.memberCenterView = memberCenterView;
        memberCenterBiz = new MemberCenterBiz(this,context);
    }

    public void requestUserInfo(){
        //memberCenterView.showLoadingView();
        memberCenterBiz.requestUserInfo(TAG_USER_INFO);
    }

    public void requestAboutUsUrl(){
        memberCenterBiz.requestAboutUsUrl(TAG_ABOUT_URL);
    }

    @Override
    public void OnSuccess(ServerResponse serverResponse, int tag) {
        super.OnSuccess(serverResponse, tag);
        switch (tag){
            case TAG_USER_INFO:
                //memberCenterView.showOriginView();
                MemberCenterBean memberCenterBean = JsonUtils.getJsonToBean(serverResponse.getData(),MemberCenterBean.class);
                memberCenterView.getUserInfoSucess(memberCenterBean);
                break;
            case TAG_ABOUT_URL:
                String data = serverResponse.getData();
                memberCenterView.getAboutUsUrlSuccess(data);
                break;
        }
    }


    @Override
    public void onFail(String msg, int code, int tag) {
        super.onFail(msg, code, tag);
        memberCenterView.showToast(msg);
    }

    @Override
    public BaseView getBaseView() {
        return memberCenterView;
    }
}
