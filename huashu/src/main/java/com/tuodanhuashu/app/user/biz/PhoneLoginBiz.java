package com.tuodanhuashu.app.user.biz;

import android.content.Context;

import com.company.common.net.OkNetUtils;
import com.company.common.net.OnRequestListener;
import com.tuodanhuashu.app.Constants.Constants;

import java.util.HashMap;
import java.util.Map;

public class PhoneLoginBiz {

    private Context context;

    private OnRequestListener listener;

    public PhoneLoginBiz(Context context, OnRequestListener listener) {
        this.context = context;
        this.listener = listener;
    }

    public void requestSMS(int tag, String mobile,String udid){
        Map<String,String> params = new HashMap<>();
        params.put("mobile",mobile);
        params.put("udid",udid);
        OkNetUtils.get(tag, Constants.URL.BASE_URL+Constants.URL.SEND_SMS_URL,params,context,listener);
    }

    public void loginByPhone(int tag,String mobile,String logintype,String yzcode){
        Map<String,String> params = new HashMap<>();
        params.put("mobile",mobile);
        params.put("logintype",logintype);
        params.put("yzcode",yzcode);
        OkNetUtils.post(tag, Constants.URL.BASE_URL+Constants.URL.LOGIN_URL,params,context,listener);
    }

    public void requestWxLogin(int tag,String openid,String name,String sex,String heade_img){
        Map<String,String> params = new HashMap<>();
        params.put("openid",openid);
        params.put("name",name);
        params.put("sex",sex);
        params.put("heade_img",heade_img);
        OkNetUtils.get(tag,Constants.URL.BASE_URL+Constants.URL.WX_LOGIN_URL,params,context,listener);
    }
}
