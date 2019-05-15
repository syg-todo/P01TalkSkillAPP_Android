package com.tuodanhuashu.app.home.biz;

import android.content.Context;

import com.company.common.net.OkNetUtils;
import com.company.common.net.OnRequestListener;
import com.tuodanhuashu.app.Constants.Constants;

import java.util.HashMap;
import java.util.Map;

public class HomeCollegeFragmentBiz {
    private OnRequestListener listener;

    private Context context;

    public HomeCollegeFragmentBiz(OnRequestListener listener,Context context) {
        this.listener = listener;
        this.context = context;
    }

    public void requestHomeCollegePage(int tag){
        Map<String,String> params = new HashMap<>();
        OkNetUtils.get(tag, Constants.URL.BASE_URL+Constants.URL.HOME_COLLEGE_URL,params,context,listener);
    }
}
