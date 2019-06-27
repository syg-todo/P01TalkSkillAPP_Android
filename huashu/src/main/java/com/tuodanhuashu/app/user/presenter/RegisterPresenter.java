package com.tuodanhuashu.app.user.presenter;

import android.content.Context;
import android.util.Log;

import com.company.common.CommonConstants;
import com.company.common.base.BasePresenter;
import com.company.common.base.BaseView;
import com.company.common.net.ServerResponse;
import com.company.common.utils.DeviceUtils;
import com.company.common.utils.JsonUtils;
import com.company.common.utils.MD5Utils;
import com.company.common.utils.PreferencesUtils;
import com.company.common.utils.StringUtils;
import com.hyphenate.chat.ChatClient;
import com.hyphenate.helpdesk.callback.Callback;
import com.tuodanhuashu.app.Constants.Constants;
import com.tuodanhuashu.app.user.bean.LoginSuccessBean;
import com.tuodanhuashu.app.user.biz.RegisterBiz;
import com.tuodanhuashu.app.user.view.IRegisterView;
import com.tuodanhuashu.app.wxapi.bean.WeChatUserInfoBean;

import java.util.regex.Pattern;

public class RegisterPresenter extends BasePresenter{

    private IRegisterView registerView;

    private Context context;

    private RegisterBiz registerBiz;

    private final int TAG_MSM = 1;

    private final int TAG_REGISTER = 2;

    private final int TAG_REGISTER_MESSAGE = 3;

    public RegisterPresenter(IRegisterView registerView, Context context) {
        this.registerView = registerView;
        this.context = context;
        this.registerBiz = new RegisterBiz(this,context);
    }

    @Override
    public BaseView getBaseView() {
        return registerView;
    }

    public void requestSMS(String mobile){
        if(StringUtils.isEmpty(mobile)){
            registerView.showToast("请输入手机号");
            return;
        }
        registerView.showLoadingDialog();
        registerBiz.requestSMS(TAG_MSM,mobile,DeviceUtils.getUniqueId(context));
//        registerBiz.requestSMS(TAG_MSM,mobile, "8848");
    }

    public void requestUserRegister(String mobile,String password,String yzcode){
        if(StringUtils.isEmpty(password)){
            registerView.showToast("请输入密码");
            return;
        }
        if(StringUtils.isEmpty(yzcode)){
            registerView.showToast("请输入验证码");
            return;
        }
        if(StringUtils.isEmpty(mobile)){
            registerView.showToast("请输入手机号");
            return;
        }
        if(!Pattern.matches(Constants.REG.REG_PWD,password)){
           registerView.showToast("请输入6-12位包含大小写字母和数字的密码!");
            return;
        }
        registerBiz.requestUserRegister(TAG_REGISTER,mobile,password,yzcode);
    }

    public void requestWXLogin(WeChatUserInfoBean weChatUserInfoBean){
        registerBiz.requestWxLogin(TAG_REGISTER,weChatUserInfoBean.getOpenid(),weChatUserInfoBean.getname(),weChatUserInfoBean.getSex()+"",weChatUserInfoBean.getHeadimgurl());
    }

    public void requestMessageUrl(){
        registerBiz.requestRegisterMessageUrl(TAG_REGISTER_MESSAGE);
    }

    @Override
    public void OnSuccess(ServerResponse serverResponse, int tag) {
        super.OnSuccess(serverResponse, tag);
        registerView.cancalLoadingDialog();
        switch (tag){
            case TAG_MSM:
                registerView.onReceiveMsgSuccess();
                break;
            case TAG_REGISTER:
                LoginSuccessBean loginSuccessBean = JsonUtils.getJsonToBean(serverResponse.getData(),LoginSuccessBean.class);
                saveUserInfo(loginSuccessBean);
                registerHuanXin();
                registerView.onRegisterSuccess();
                break;
            case TAG_REGISTER_MESSAGE:
                String data = serverResponse.getData();
                registerView.getMessageUrlSuccess(data);
                break;

        }
    }

    @Override
    public void onFail(String msg, int code, int tag) {
        super.onFail(msg, code, tag);
        registerView.cancalLoadingDialog();
        switch (tag){
            case TAG_MSM:
                registerView.onReceiveMsgFail(msg);
                break;
            case TAG_REGISTER:
                registerView.onRegisterFail(msg);
                break;

        }
    }

    @Override
    public void onError(int tag) {
        super.onError(tag);
        registerView.cancalLoadingDialog();
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
