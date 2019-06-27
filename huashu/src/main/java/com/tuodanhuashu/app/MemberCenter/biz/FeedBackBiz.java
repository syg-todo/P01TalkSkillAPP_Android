package com.tuodanhuashu.app.MemberCenter.biz;

import android.content.Context;

import com.company.common.net.OkNetUtils;
import com.company.common.net.OnRequestListener;
import com.tuodanhuashu.app.Constants.Constants;

import java.util.HashMap;
import java.util.Map;

public class FeedBackBiz {

    private Context context;

    private OnRequestListener listener;

    public FeedBackBiz(Context context, OnRequestListener listener) {
        this.context = context;
        this.listener = listener;
    }

    public void submitFeedBack(int tag,String content){
        Map<String,String> params = new HashMap<>();
        params.put("content",content);
        OkNetUtils.post(tag, Constants.URL.BASE_URL+Constants.URL.FEED_BACK_URL,params,context,listener);
    }
}
