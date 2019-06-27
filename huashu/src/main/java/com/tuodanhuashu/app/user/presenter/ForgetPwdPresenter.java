package com.tuodanhuashu.app.user.presenter;

import android.content.Context;

import com.company.common.base.BasePresenter;
import com.company.common.base.BaseView;
import com.company.common.net.ServerResponse;
import com.company.common.utils.DeviceUtils;
import com.tuodanhuashu.app.user.biz.ForgetPwdBiz;
import com.tuodanhuashu.app.user.view.ForgetPwdView;

public class ForgetPwdPresenter extends BasePresenter {

    private Context context;

    private ForgetPwdView forgetPwdView;

    private ForgetPwdBiz forgetPwdBiz;

    private final int TAG_SEND_SMS = 1;

    private  final int TAG_RESET_PWD = 2;

    public ForgetPwdPresenter(Context context, ForgetPwdView forgetPwdView) {
        this.context = context;
        this.forgetPwdView = forgetPwdView;
        this.forgetPwdBiz  = new ForgetPwdBiz(this,context);
    }

    public void sendSMS(String mobile){
        forgetPwdBiz.requestSMS(TAG_SEND_SMS,mobile,DeviceUtils.getUniqueId(context));
    }

    public void resetPwd(String mobile,String yzcode,String pwd1,String pwd2){
        forgetPwdBiz.resetPwd(TAG_RESET_PWD,mobile,yzcode,pwd1,pwd2);
    }

    @Override
    public void OnSuccess(ServerResponse serverResponse, int tag) {
        super.OnSuccess(serverResponse, tag);
        switch (tag){
            case TAG_SEND_SMS:
                forgetPwdView.getSMSSuccess(serverResponse.getMsg());

                break;
            case TAG_RESET_PWD:
                forgetPwdView.resetPwdSuccess(serverResponse.getMsg());
                break;
        }
    }

    @Override
    public void onFail(String msg, int code, int tag) {
        super.onFail(msg, code, tag);
        getBaseView().showToast(msg);
    }

    @Override
    public BaseView getBaseView() {
        return forgetPwdView;
    }
}
