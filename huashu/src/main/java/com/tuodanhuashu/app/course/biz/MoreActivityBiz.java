package com.tuodanhuashu.app.course.biz;

import android.content.Context;

import com.company.common.net.OkNetUtils;
import com.company.common.net.OnRequestListener;
import com.tuodanhuashu.app.Constants.Constants;

import java.util.HashMap;
import java.util.Map;

public class MoreActivityBiz {
    private Context context;

    private OnRequestListener listener;

    public MoreActivityBiz(Context context, OnRequestListener listener) {
        this.context = context;
        this.listener = listener;
    }


    public void requestActivityList(int tag,String page,String page_size){
        Map<String,String> params = new HashMap<>();
        params.put("page",page);
        params.put("page_size",page_size);
        OkNetUtils.get(tag, Constants.URL.BASE_URL+Constants.URL.COLLEGE_COURSE_ACTIVITY_LIST,params,context,listener);
    }
}
