package com.tuodanhuashu.app.teacher.biz;

import android.content.Context;

import com.company.common.net.OkNetUtils;
import com.company.common.net.OnRequestListener;
import com.tuodanhuashu.app.Constants.Constants;

import java.util.HashMap;
import java.util.Map;

public class TeacherListBiz {

    private OnRequestListener listener;

    private Context context;

    public TeacherListBiz(OnRequestListener listener, Context context) {
        this.listener = listener;
        this.context = context;
    }

    public void requestTeacherList(int tag,String page,String page_size){
        Map<String,String> params = new HashMap<>();
        params.put("page",page);
        params.put("page_size",page_size);
        OkNetUtils.get(tag,Constants.URL.BASE_URL+Constants.URL.TEACHER_LIST_URL,params,context,listener);
    }
}
