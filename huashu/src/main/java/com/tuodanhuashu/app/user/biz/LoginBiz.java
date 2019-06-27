package com.tuodanhuashu.app.user.biz;

import android.content.Context;

import com.company.common.net.OkNetUtils;
import com.company.common.net.OnRequestListener;
import com.tuodanhuashu.app.Constants.Constants;

import java.util.HashMap;
import java.util.Map;

public class LoginBiz {

    private OnRequestListener listener;

    private Context context;

    public LoginBiz(OnRequestListener listener,Context context) {
        this.listener = listener;
        this.context = context;
    }

    public void requestPwdLogin(int tag, String mobile,String password){
        Map<String,String> params = new HashMap<>();
        params.put("mobile",mobile);
        params.put("password",password);
        params.put("logintype","1");
        OkNetUtils.post(tag, Constants.URL.BASE_URL+Constants.URL.LOGIN_URL,params,context,listener);
    }

    public void requestWxLogin(int tag,String openid,String name,String sex,String heade_img){
        Map<String,String> params = new HashMap<>();
        params.put("openid",openid);
        params.put("name",name);
        params.put("sex",sex);
        params.put("heade_img",heade_img);
        OkNetUtils.post(tag,Constants.URL.BASE_URL+Constants.URL.WX_LOGIN_URL,params,context,listener);
    }
}
