package com.tuodanhuashu.app.huashu.presenter;

import android.content.Context;

import com.company.common.base.BasePresenter;
import com.company.common.base.BaseView;
import com.company.common.net.ServerResponse;
import com.company.common.utils.JsonUtils;
import com.tuodanhuashu.app.huashu.bean.TalkSkillResultBean;
import com.tuodanhuashu.app.huashu.biz.TalkSkillListBiz;
import com.tuodanhuashu.app.huashu.view.TalkSkillListView;


public class TalkSkillListPresenter extends BasePresenter {

    private TalkSkillListView talkSkillListView;

    private Context context;

    private TalkSkillListBiz talkSkillListBiz;


    private static final int TAG_TALKSKILL_LIST = 1;

    private static final int TAG_GET_MIJI = 2;

    public TalkSkillListPresenter(TalkSkillListView talkSkillListView, Context context) {
        this.talkSkillListView = talkSkillListView;
        this.context = context;
        talkSkillListBiz = new TalkSkillListBiz(this,context);
    }

    public void requestTalkSkillList(String keywords, String talkSkillClassId, String currentPage, String pageSize){
        talkSkillListBiz.requestTalkSkillList(TAG_TALKSKILL_LIST,keywords,talkSkillClassId,currentPage,pageSize);
    }

    public void getMiji(){
        talkSkillListBiz.getMiji(TAG_GET_MIJI);
    }

    @Override
    public void OnSuccess(ServerResponse serverResponse, int tag) {
        super.OnSuccess(serverResponse, tag);
        switch (tag){
            case TAG_TALKSKILL_LIST:
                TalkSkillResultBean talkSkillResultBean = JsonUtils.getJsonToBean(serverResponse.getData(),TalkSkillResultBean.class);
                talkSkillListView.getTalkListSuccess(talkSkillResultBean);
                break;
            case TAG_GET_MIJI:
                String url = serverResponse.getData();
                talkSkillListView.getMijiSuccess(url);
                break;
        }

    }

    @Override
    public void onFail(String msg, int code, int tag) {
        super.onFail(msg, code, tag);
        talkSkillListView.getTalkListFail(msg);
    }

    @Override
    public void onError(int tag) {
        super.onError(tag);
    }

    @Override
    public BaseView getBaseView() {
        return talkSkillListView;
    }
}
