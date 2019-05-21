package com.tuodanhuashu.app.course.biz;

import android.content.Context;

import com.company.common.net.OkNetUtils;
import com.company.common.net.OnRequestListener;
import com.tuodanhuashu.app.Constants.Constants;

import java.util.HashMap;
import java.util.Map;

public class CourseDetailBiz {

    private Context context;

    private OnRequestListener listener;

    public CourseDetailBiz(Context context, OnRequestListener listener) {
        this.context = context;
        this.listener = listener;
    }



    public void requestCourseDetailByCourseId(int tag, String accessToken, String courseId) {
        Map<String,String> params = new HashMap<>();
        params.put("course_id",courseId);
        params.put("access_token",accessToken);
        OkNetUtils.get(tag, Constants.URL.BASE_URL+Constants.URL.COLLEGE_COURSE_DETAIL_URL,params,context,listener);
    }
}
