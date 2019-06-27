package com.tuodanhuashu.app.MemberCenter.biz;

import android.content.Context;

import com.company.common.net.OkNetUtils;
import com.company.common.net.OnRequestListener;
import com.tuodanhuashu.app.Constants.Constants;

import java.util.HashMap;
import java.util.Map;

public class MemberCenterBiz {

    private OnRequestListener listener;

    private Context context;



    public MemberCenterBiz(OnRequestListener listener, Context context) {
        this.listener = listener;
        this.context = context;
    }

    public void requestUserInfo(int tag){
        Map<String,String> params = new HashMap<>();
        OkNetUtils.get(tag,Constants.URL.BASE_URL+Constants.URL.PERSON_INFO_URL,params,context,listener);

    }

    public void updateUserImg(int tag,String head_img){
        Map<String,String> params = new HashMap<>();
        params.put("heade_img",head_img);
        OkNetUtils.post(tag,Constants.URL.BASE_URL+Constants.URL.EDIT_HEAD_URL,params,context,listener);
    }

    public void updateUserInfo(int tag,String name,String realname,String sex,String p_signature){
        Map<String,String> params = new HashMap<>();
        params.put("name",name);
        params.put("realname",realname);
        params.put("sex",sex);
        params.put("p_signature",p_signature);
        OkNetUtils.post(tag,Constants.URL.BASE_URL+Constants.URL.EDIT_INFO_URL,params,context,listener);

    }

    public void resetPwd(int tag,String old_pass,String new_pass1,String new_pass2){
        Map<String,String> params = new HashMap<>();
        params.put("old_passwd",old_pass);
        params.put("new_passwd1",new_pass1);
        params.put("new_passwd2",new_pass2);
        OkNetUtils.post(tag,Constants.URL.BASE_URL+Constants.URL.RESET_PWD_URL,params,context,listener);
    }

    public void resetMobile(int tag,String mobile,String yzcode,String passwd){
        Map<String,String> params = new HashMap<>();
        params.put("mobile",mobile);
        params.put("yzcode",yzcode);
        params.put("passwd",passwd);
        OkNetUtils.post(tag,Constants.URL.BASE_URL+Constants.URL.RESET_MOBILE_URL,params,context,listener);
    }

    public void requestAboutUsUrl(int tag){
        Map<String,String> params = new HashMap<>();
        OkNetUtils.get(tag,Constants.URL.BASE_URL+Constants.URL.ABOUT_US_URL,params,context,listener);
    }
}
