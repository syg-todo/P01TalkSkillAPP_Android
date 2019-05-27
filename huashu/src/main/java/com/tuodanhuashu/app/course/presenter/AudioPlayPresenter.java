package com.tuodanhuashu.app.course.presenter;

import android.content.Context;
import android.util.Log;

import com.company.common.base.BasePresenter;
import com.company.common.base.BaseView;
import com.company.common.net.ServerResponse;
import com.company.common.utils.JsonUtils;
import com.tuodanhuashu.app.course.bean.SectionBean;
import com.tuodanhuashu.app.course.biz.PlayBiz;
import com.tuodanhuashu.app.course.view.AudioPlayView;

public class AudioPlayPresenter extends BasePresenter {

    private Context context;

    private AudioPlayView playView;

    private PlayBiz playBiz;

    private static final int TAG_PLAY_SECTION = 1;


    public AudioPlayPresenter(Context context, AudioPlayView playView) {
        this.context = context;
        this.playView = playView;
        playBiz = new PlayBiz(context, this);
    }

    public void requestCourseClassList(String accessToken,String sectionId) {
        Log.d("111","AudioPlayPresenter-requestCourseClassList");
        playBiz.requestPlaySectionInfo(TAG_PLAY_SECTION,accessToken,sectionId);
    }


    @Override
    public void OnSuccess(ServerResponse serverResponse, int tag) {
        super.OnSuccess(serverResponse, tag);
        Log.d("111","AudioPlayPresenter-OnSuccess");

        SectionBean sectionBean = JsonUtils.getJsonToBean(serverResponse.getData(),SectionBean.class);
        playView.getSectionSuccess(sectionBean);


    }


    @Override
    public void onFail(String msg, int code, int tag) {
        super.onFail(msg, code, tag);
        playView.getSectionFail(msg);
    }

    @Override
    public BaseView getBaseView() {
        return playView;
    }
}
