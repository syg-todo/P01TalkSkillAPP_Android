package com.tuodanhuashu.app.course.biz;

import android.content.Context;

import com.company.common.net.OkNetUtils;
import com.company.common.net.OnRequestListener;
import com.tuodanhuashu.app.Constants.Constants;
import com.tuodanhuashu.app.receiver.PushMessageBean;

import java.util.HashMap;
import java.util.Map;

public class MyCourseBiz {
    private Context context;

    private OnRequestListener listener;

    public MyCourseBiz(Context context, OnRequestListener listener) {
        this.context = context;
        this.listener = listener;
    }

    public void requestMyCourse(int tag,String accessToken){
        requestMyCourse(tag,accessToken,"1");
    }

    public void requestMyCourse(int tag, String accessToken,String page) {
        Map<String,String> params = new HashMap<>();
        params.put("access_token",accessToken);
        params.put("page",page);
        OkNetUtils.get(tag, Constants.URL.BASE_URL+Constants.URL.COLLEGE_COURSE_MY_COURSE_URL,params,context,listener);
    }



}
