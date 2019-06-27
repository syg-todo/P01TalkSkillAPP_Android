package com.tuodanhuashu.app.home.biz;

import android.content.Context;

import com.company.common.net.OkNetUtils;
import com.company.common.net.OnRequestListener;
import com.tuodanhuashu.app.Constants.Constants;

import java.util.HashMap;
import java.util.Map;

public class HomeBiz {

    private OnRequestListener listener;

    private Context context;

    public HomeBiz(OnRequestListener listener, Context context) {
        this.listener = listener;
        this.context = context;
    }

    public void requestCheckVersion(int tag,String version_code){
        Map<String,String> params = new HashMap<>();
        params.put("version_code",version_code);
        OkNetUtils.post(tag, Constants.URL.BASE_URL+Constants.URL.CHECK_VERSION_URL,params,context,listener);
    }
}
