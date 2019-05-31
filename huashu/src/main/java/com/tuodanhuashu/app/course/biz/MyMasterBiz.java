package com.tuodanhuashu.app.course.biz;

import android.content.Context;

import com.company.common.net.OkNetUtils;
import com.company.common.net.OnRequestListener;
import com.tuodanhuashu.app.Constants.Constants;

import java.util.HashMap;
import java.util.Map;

public class MyMasterBiz {
    private Context context;

    private OnRequestListener listener;

    public MyMasterBiz(Context context, OnRequestListener listener) {
        this.context = context;
        this.listener = listener;
    }


    public void requestMyMaster(int tag, String accessToken) {
        requestMyMaster(tag,accessToken,"1");
    }

    public void requestMyMaster(int tag, String accessToken, String page) {
        Map<String,String> params = new HashMap<>();
        params.put("access_token",accessToken);
        params.put("page",page);
        OkNetUtils.get(tag, Constants.URL.BASE_URL+Constants.URL.COLLEGE_COURSE_MY_MASTER_URL,params,context,listener);
    }

    public void unrecordMaster(int tag,String accessToken,String masterId){
        Map<String,String> params = new HashMap<>();
        params.put("access_token",accessToken);
        params.put("master_id",masterId);
        OkNetUtils.get(tag,Constants.URL.BASE_URL+Constants.URL.COLLEGE_COURSE_UNRECORD_MASTER_URL,params,context,listener);
    }

}
