package com.tuodanhuashu.app.user.presenter;

import android.content.Context;
import android.util.Log;

import com.company.common.CommonConstants;
import com.company.common.base.BasePresenter;
import com.company.common.base.BaseView;
import com.company.common.net.ServerResponse;
import com.company.common.utils.JsonUtils;
import com.company.common.utils.MD5Utils;
import com.company.common.utils.PreferencesUtils;
import com.company.common.utils.StringUtils;
import com.tuodanhuashu.app.Constants.Constants;
import com.tuodanhuashu.app.user.bean.LoginSuccessBean;
import com.tuodanhuashu.app.user.biz.LoginBiz;
import com.tuodanhuashu.app.user.view.ILoginView;
import com.hyphenate.chat.ChatClient;
import com.hyphenate.helpdesk.callback.Callback;
import com.tuodanhuashu.app.wxapi.bean.WeChatUserInfoBean;

import java.util.regex.Pattern;

public class LoginPresenter extends BasePresenter {

    private ILoginView loginView;

    private LoginBiz loginBiz;

    private Context context;

    private final int TAG_REQUEST_LOGIN = 1;

    public LoginPresenter(ILoginView loginView, Context context) {
        this.loginView = loginView;
        this.context = context;
        this.loginBiz = new LoginBiz(this,context);
    }

    @Override
    public BaseView getBaseView() {
        return loginView;
    }

    public void requestLogin(String mobile,String password){
        if(StringUtils.isEmpty(mobile)){
            loginView.showToast("手机号不能为空");
            return;
        }
        if(StringUtils.isEmpty(password)){
            loginView.showToast("密码不能为空");
            return;
        }
        if(!Pattern.matches(Constants.REG.REG_PWD,password)){
            loginView.showToast("请输入6-12位包含大小写字母和数字的密码!");
            return;
        }
        loginView.showLoadingDialog();
        loginBiz.requestPwdLogin(TAG_REQUEST_LOGIN,mobile,password);
    }

    public void requestWXLogin(WeChatUserInfoBean weChatUserInfoBean){
        loginBiz.requestWxLogin(TAG_REQUEST_LOGIN,weChatUserInfoBean.getOpenid(),weChatUserInfoBean.getname(),weChatUserInfoBean.getSex()+"",weChatUserInfoBean.getHeadimgurl());
    }

    @Override
    public void OnSuccess(ServerResponse serverResponse, int tag) {
        super.OnSuccess(serverResponse, tag);
        loginView.cancalLoadingDialog();
        switch (tag){
            case TAG_REQUEST_LOGIN:
                LoginSuccessBean loginSuccessBean = JsonUtils.getJsonToBean(serverResponse.getData(),LoginSuccessBean.class);
                Log.d("111",loginSuccessBean.getAccess_token());
                saveUserInfo(loginSuccessBean);
                registerHuanXin();
                loginView.onLoginSuccess();
                break;
        }
    }

    @Override
    public void onFail(String msg, int code, int tag) {
        super.onFail(msg, code, tag);
        loginView.cancalLoadingDialog();
        loginView.onLiginFail(msg);
    }

    @Override
    public void onError(int tag) {
        super.onError(tag);
        loginView.cancalLoadingDialog();
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
