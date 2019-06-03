package com.tuodanhuashu.app.course.biz;

import android.content.Context;

import com.company.common.net.OkNetUtils;
import com.company.common.net.OnRequestListener;
import com.tuodanhuashu.app.Constants.Constants;

import java.util.HashMap;
import java.util.Map;

public class CourseListBiz {
    private Context context;

    private OnRequestListener listener;

    public CourseListBiz(Context context, OnRequestListener listener) {
        this.context = context;
        this.listener = listener;
    }

    public void requestCourseClassList(int tag){
        Map<String,String> params = new HashMap<>();
        OkNetUtils.get(tag, Constants.URL.BASE_URL+Constants.URL.HOME_COLLEGE_CATEGORY_URL,params,context,listener);
    }

    public void requestCourseListByClassId(int tag, String class_id, String page, String page_size) {
        Map<String,String> params = new HashMap<>();
        params.put("class_id",class_id);
        params.put("page",page);
        params.put("page_size",page_size);
        OkNetUtils.get(tag, Constants.URL.BASE_URL+Constants.URL.COLLEGE_COURSE_CAT_LIST_URL,params,context,listener);
    }


    public void requestCourseCommunityList(int tag, String page, String page_size) {
        Map<String,String> params = new HashMap<>();
        params.put("page",page);
        params.put("page_size",page_size);
        OkNetUtils.get(tag, Constants.URL.BASE_URL+Constants.URL.COLLEGE_COURSE_COMMUNITY_URL,params,context,listener);
    }

    public void requestMasterList(int tag) {
        Map<String,String> params = new HashMap<>();
        OkNetUtils.get(tag, Constants.URL.BASE_URL+Constants.URL.COLLEGE_COURSE_MASTER_LIST_URL,params,context,listener);
    }

    public void requestCourseListByMasterId(int tag,String master_id,String page,String page_size){
        Map<String,String> params = new HashMap<>();
        params.put("master_id",master_id);
        params.put("page",page);
        params.put("page_size",page_size);
        OkNetUtils.get(tag, Constants.URL.BASE_URL+Constants.URL.COLLEGE_COURSE_SJK_LIST_URL,params,context,listener);
    }

    public void requestChoicenessList(int tag,String page,String page_size){
        Map<String,String> params = new HashMap<>();
        params.put("page",page);
        params.put("page_size",page_size);
        OkNetUtils.get(tag, Constants.URL.BASE_URL+Constants.URL.COLLEGE_COURSE_CHOICENESS_LIST,params,context,listener);
    }


}
