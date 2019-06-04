package com.tuodanhuashu.app.course.biz;

import android.content.Context;

import com.company.common.net.OkNetUtils;
import com.company.common.net.OnRequestListener;
import com.tuodanhuashu.app.Constants.Constants;

import java.util.HashMap;
import java.util.Map;

public class MasterDetailBiz {
    private Context context;

    private OnRequestListener listener;

    public MasterDetailBiz(Context context, OnRequestListener listener) {
        this.context = context;
        this.listener = listener;
    }

    public void requestMasterDetail(int tag, String accessToken,String masterId) {
        Map<String,String> params = new HashMap<>();
        params.put("access_token",accessToken);
        params.put("master_id",masterId);
        OkNetUtils.get(tag, Constants.URL.BASE_URL+Constants.URL.COLLEGE_MASTER_DETAIL_URL,params,context,listener);
    }

    public void recordMaster(int tag,String accseeToken,String masterId){
        Map<String,String> params = new HashMap<>();
        params.put("access_token",accseeToken);
        params.put("master_id",masterId);
        OkNetUtils.get(tag,Constants.URL.BASE_URL+Constants.URL.COLLEGE_COURSE_RECORD_MASTER_URL,params,context,listener);
    }

    public void unrecordMaster(int tag,String accseeToken,String masterId){
        Map<String,String> params = new HashMap<>();
        params.put("access_token",accseeToken);
        params.put("master_id",masterId);
        OkNetUtils.get(tag,Constants.URL.BASE_URL+Constants.URL.COLLEGE_COURSE_UNRECORD_MASTER_URL,params,context,listener);
    }
}
