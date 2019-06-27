package com.tuodanhuashu.app.home.presenter;

import android.content.Context;

import com.company.common.base.BasePresenter;
import com.company.common.base.BaseView;
import com.company.common.net.ServerResponse;
import com.company.common.utils.JsonUtils;
import com.tuodanhuashu.app.home.bean.HomeTalkSkillPageBean;
import com.tuodanhuashu.app.home.biz.HomeTalkSkillFragmentBiz;
import com.tuodanhuashu.app.home.view.HomeTalkSkillView;

public class HomeTalkSkillPresenter extends BasePresenter {

    private HomeTalkSkillView talkSkillView;

    private Context context;

    private HomeTalkSkillFragmentBiz talkSkillFragmentBiz;

    private static final int TAG_TALK_SKILL = 1;

    public HomeTalkSkillPresenter(HomeTalkSkillView talkSkillView, Context context) {
        this.talkSkillView = talkSkillView;
        this.context = context;
        talkSkillFragmentBiz = new HomeTalkSkillFragmentBiz(this,context);
    }

    @Override
    public BaseView getBaseView() {
        return talkSkillView;
    }

    public void requestTalkSkillPage(){
        talkSkillFragmentBiz.requestHomeTalkSkillPage(TAG_TALK_SKILL);
    }


    @Override
    public void OnSuccess(ServerResponse serverResponse, int tag) {
        super.OnSuccess(serverResponse, tag);
        HomeTalkSkillPageBean homeTalkSkillPageBean = JsonUtils.getJsonToBean(serverResponse.getData(),HomeTalkSkillPageBean.class);
        talkSkillView.getDataSuccess(homeTalkSkillPageBean);
    }

    @Override
    public void onFail(String msg, int code, int tag) {
        super.onFail(msg, code, tag);
        talkSkillView.getDataFail(msg);
    }

    @Override
    public void onError(int tag) {
        super.onError(tag);
    }
}
