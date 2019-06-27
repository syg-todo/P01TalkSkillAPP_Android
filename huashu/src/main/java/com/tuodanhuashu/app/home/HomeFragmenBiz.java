package com.tuodanhuashu.app.home;

import android.content.Context;

import com.company.common.net.OkNetUtils;
import com.company.common.net.OnRequestListener;
import com.tuodanhuashu.app.Constants.Constants;

import java.util.HashMap;
import java.util.Map;

public class HomeFragmenBiz {

    private OnRequestListener listener;

    private Context context;

    public HomeFragmenBiz(OnRequestListener listener,Context context) {
        this.listener = listener;
        this.context = context;
    }

    public void requestHomePage(int tag){
        Map<String,String> params = new HashMap<>();
        OkNetUtils.get(tag, Constants.URL.BASE_URL+Constants.URL.HOME_PAGE_URL,params,context,listener);
    }
}
