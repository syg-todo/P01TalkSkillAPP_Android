package com.tuodanhuashu.app.course.biz;

import android.content.Context;

import com.company.common.net.OkNetUtils;
import com.company.common.net.OnRequestListener;
import com.tuodanhuashu.app.Constants.Constants;

import java.util.HashMap;
import java.util.Map;

public class PlayBiz {

    private Context context;

    private OnRequestListener listener;

    public PlayBiz(Context context, OnRequestListener listener) {
        this.context = context;
        this.listener = listener;
    }

    public void requestPlaySectionInfo(int tag, String accessToken, String sectionId) {
        Map<String, String> params = new HashMap<>();
        params.put("access_token", accessToken);
        params.put("section_id", sectionId);
        OkNetUtils.get(tag, Constants.URL.BASE_URL + Constants.URL.COLLEGE_COURSE_SECTION_INFO_URL, params, context, listener);
    }

    public void sendComment(int tag, String accessToken, String courseId, String content) {
        Map<String,String> params = new HashMap<>();
        params.put("access_token",accessToken);
        params.put("course_id",courseId);
        params.put("content",content);
        OkNetUtils.post(tag,Constants.URL.BASE_URL+Constants.URL.COLLEGE_COURSE_SECTION_SEND_COMMENT,params,context,listener);
    }

    public void likeComment(int tag, String accessToken, String commentId) {
        Map<String,String> params = new HashMap<>();
        params.put("access_token",accessToken);
        params.put("comment_id",commentId);
        OkNetUtils.get(tag,Constants.URL.BASE_URL+Constants.URL.COLLEGE_COURSE_UNRECORD_MASTER_URL,params,context,listener);
    }
}
