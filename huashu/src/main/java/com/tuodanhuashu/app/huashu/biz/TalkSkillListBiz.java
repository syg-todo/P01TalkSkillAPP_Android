package com.tuodanhuashu.app.huashu.biz;

import android.content.Context;

import com.company.common.net.OkNetUtils;
import com.company.common.net.OnRequestListener;
import com.tuodanhuashu.app.Constants.Constants;

import java.util.HashMap;
import java.util.Map;

public class TalkSkillListBiz {

    private OnRequestListener listener;

    private Context context;

    public TalkSkillListBiz(OnRequestListener listener, Context context) {
        this.listener = listener;
        this.context = context;
    }

    public void requestTalkSkillList(int tag,String keywords,String talkSkillClassId,String currentPage,String pageSize){
        Map<String,String> params = new HashMap<>();
        params.put("keywords",keywords);
        params.put("talkSkillClassId",talkSkillClassId);
        params.put("currentPage",currentPage);
        params.put("pageSize",pageSize);
        OkNetUtils.get(tag,Constants.URL.BASE_URL+Constants.URL.TALK_SKILL_LIST_URL,params,context,listener);
    }

    public void requestMyList(int tag,String page,String page_size){
        Map<String,String> params = new HashMap<>();
        params.put("page",page);
        params.put("page_size",page_size);
        OkNetUtils.get(tag,Constants.URL.BASE_URL+Constants.URL.MY_TALKSKILL_URL,params,context,listener);
    }

    public void getMiji(int tag){
        Map<String,String> params = new HashMap<>();
        OkNetUtils.get(tag,Constants.URL.BASE_URL+Constants.URL.GET_MIJI_URL,params,context,listener);
    }
}
