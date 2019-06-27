package com.tuodanhuashu.app.user.presenter;

import android.content.Context;
import android.provider.ContactsContract;
import android.util.Log;

import com.company.common.CommonConstants;
import com.company.common.base.BasePresenter;
import com.company.common.base.BaseView;
import com.company.common.net.ServerResponse;
import com.company.common.utils.DeviceUtils;
import com.company.common.utils.JsonUtils;
import com.company.common.utils.MD5Utils;
import com.company.common.utils.PreferencesUtils;
import com.hyphenate.chat.ChatClient;
import com.hyphenate.helpdesk.callback.Callback;
import com.tuodanhuashu.app.user.bean.LoginSuccessBean;
import com.tuodanhuashu.app.user.biz.PhoneLoginBiz;
import com.tuodanhuashu.app.user.view.LoginByPhoneView;
import com.tuodanhuashu.app.wxapi.bean.WeChatUserInfoBean;

public class LoginByPhonePresenter extends BasePresenter {

    private Context context;

    private LoginByPhoneView loginByPhoneView;

    private PhoneLoginBiz phoneLoginBiz;

    private static final int TAG_SEND_SMS = 1;

    private static final int TAG_PHONE_LOGIN = 2;


    public LoginByPhonePresenter(Context context, LoginByPhoneView loginByPhoneView) {
        this.context = context;
        this.loginByPhoneView = loginByPhoneView;
        this.phoneLoginBiz = new PhoneLoginBiz(context,this);
    }

    public void sendSMS(String mobile){
        phoneLoginBiz.requestSMS(TAG_SEND_SMS,mobile, DeviceUtils.getUniqueId(context));
    }

    public void loginByPhone(String mobile,String yzcode){
        phoneLoginBiz.loginByPhone(TAG_PHONE_LOGIN,mobile,"2",yzcode);
    }

    public void requestWxLogin(WeChatUserInfoBean weChatUserInfoBean){
        phoneLoginBiz.requestWxLogin(TAG_PHONE_LOGIN,weChatUserInfoBean.getOpenid(),weChatUserInfoBean.getname(),weChatUserInfoBean.getSex()+"",weChatUserInfoBean.getHeadimgurl());
    }


    @Override
    public void OnSuccess(ServerResponse serverResponse, int tag) {
        super.OnSuccess(serverResponse, tag);
        switch (tag){
            case TAG_SEND_SMS:
                loginByPhoneView.getSMSSuccess(serverResponse.getMsg());
                break;
            case TAG_PHONE_LOGIN:
                LoginSuccessBean loginSuccessBean = JsonUtils.getJsonToBean(serverResponse.getData(),LoginSuccessBean.class);
                saveUserInfo(loginSuccessBean);
                registerHuanXin();
                loginByPhoneView.onLoginSuccess();
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
        return loginByPhoneView;
    }

    private void saveUserInfo(LoginSuccessBean loginSuccessBean){
        PreferencesUtils.putString(context,CommonConstants.KEY_TOKEN,loginSuccessBean.getAccess_token());
        PreferencesUtils.putString(context,CommonConstants.KEY_ACCOUNT_ID,loginSuccessBean.getAccountid());
        PreferencesUtils.putString(context,CommonConstants.KEY_WXOPEN_ID,loginSuccessBean.getWxopenid());
        PreferencesUtils.putString(context,CommonConstants.KEY_IMG_URL,loginSuccessBean.getHeade_img());
        PreferencesUtils.putString(context,CommonConstants.KEY_MOBILE,loginSuccessBean.getMobile());
        PreferencesUtils.putString(context,CommonConstants.KEY_NICK_NAME,loginSuccessBean.getname());
    }

    private void registerHuanXin(){
        String accountId = PreferencesUtils.getString(context,CommonConstants.KEY_ACCOUNT_ID,"");
        String md5AccoundId = MD5Utils.MD5Encode(accountId,"utf-8");
        ChatClient.getInstance().register(accountId, md5AccoundId, new Callback() {
            @Override
            public void onSuccess() {
                Log.e("huanxin","环信注册成功！！");
            }

            @Override
            public void onError(int i, String s) {
                Log.e("huanxin",s);
            }

            @Override
            public void onProgress(int i, String s) {
                Log.e("huanxin",s);
            }
        });
    }
}
