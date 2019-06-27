package com.tuodanhuashu.app.user.biz;

import android.content.Context;

import com.company.common.net.OkNetUtils;
import com.company.common.net.OnRequestListener;
import com.tuodanhuashu.app.Constants.Constants;

import java.util.HashMap;
import java.util.Map;

public class ForgetPwdBiz {

    private OnRequestListener listener;

    private Context context;

    public ForgetPwdBiz(OnRequestListener listener, Context context) {
        this.listener = listener;
        this.context = context;
    }

    public void requestSMS(int tag, String mobile,String udid){
        Map<String,String> params = new HashMap<>();
        params.put("mobile",mobile);
        params.put("udid",udid);
        OkNetUtils.get(tag, Constants.URL.BASE_URL+Constants.URL.SEND_SMS_URL,params,context,listener);
    }

    public void resetPwd(int tag,String mobile, String yzcode, String new_passwd1,String new_passwd2){
        Map<String,String> params = new HashMap<>();
        params.put("mobile",mobile);
        params.put("yzcode",yzcode);
        params.put("new_passwd1",new_passwd1);
        params.put("new_passwd2",new_passwd2);
        OkNetUtils.post(tag,Constants.URL.BASE_URL+Constants.URL.USER_FORGET_PWD_URL,params,context,listener);
    }
}
