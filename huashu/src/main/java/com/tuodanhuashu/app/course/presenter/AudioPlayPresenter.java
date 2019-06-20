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

    private static final int TAG_SEND_COMMENT = 2;
    private static final int TAG_LIKE_COMMENT = 3;
    private static final int TAG_UNLIKE_COMMENT =4;

    private static final int TAG_BUY_COURSE = 5;

    public AudioPlayPresenter(Context context, AudioPlayView playView) {
        this.context = context;
        this.playView = playView;
        playBiz = new PlayBiz(context, this);
    }

    public void requestAudioSectionInfo(String accessToken, String sectionId) {
        playBiz.requestPlaySectionInfo(TAG_PLAY_SECTION, accessToken, sectionId);
    }

    public void sendComment(String accessToken, String courseId, String content) {
        playBiz.sendComment(TAG_SEND_COMMENT, accessToken, courseId, content);
    }

    @Override
    public void OnSuccess(ServerResponse serverResponse, int tag) {
        super.OnSuccess(serverResponse, tag);
        switch (tag) {
            case TAG_PLAY_SECTION:
                SectionBean sectionBean = JsonUtils.getJsonToBean(serverResponse.getData(), SectionBean.class);
                playView.getSectionSuccess(sectionBean);
                break;
            case TAG_BUY_COURSE:
                playView.getBuyCourseSuccess();
                break;
            case TAG_LIKE_COMMENT:
                playView.getLikeCommentSuccess();
                break;
            case TAG_UNLIKE_COMMENT:
                playView.getUnlikeCommentSuccess();
                break;
        }
    }

    public void likeComment(String accessToken, String commentId) {
        playBiz.likeComment(TAG_LIKE_COMMENT, accessToken, commentId);
    }

    public void unlikeComment(String accessToken, String commentId) {
        playBiz.unlikeComment(TAG_UNLIKE_COMMENT, accessToken, commentId);
    }


    public void requesetBuyCourse(String accessToken,String courseId){
        playBiz.requestBuyCourse(TAG_BUY_COURSE,accessToken,courseId);
    }


    @Override
    public void onFail(String msg, int code, int tag) {
        super.onFail(msg, code, tag);
        switch (tag){
            case TAG_BUY_COURSE:
                playView.getBuyCourseFail(msg);
                break;
            case TAG_LIKE_COMMENT:
                playView.getLikeCommentFail(msg);
                break;
            case TAG_UNLIKE_COMMENT:
                playView.getUnlikeCommentFail(msg);
                break;
            case TAG_PLAY_SECTION:
                playView.getSectionFail(msg);
                break;
        }
    }

    @Override
    public BaseView getBaseView() {
        return playView;
    }
}
