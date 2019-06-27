package com.tuodanhuashu.app.huashu.bean;

import com.tuodanhuashu.app.home.bean.HomeAnswerBean;
import com.tuodanhuashu.app.home.bean.HomeQuestionBean;

import java.util.List;

public class TalkSkillListItemBean {
    private String talkSkillId;
    private List<String> keywords;
    private String cat_name;
    private HomeQuestionBean question;
    private List<HomeAnswerBean> answers;

    public TalkSkillListItemBean() {
    }

    public String getTalkSkillId() {
        return talkSkillId;
    }

    public void setTalkSkillId(String talkSkillId) {
        this.talkSkillId = talkSkillId;
    }

    public List<String> getKeywords() {
        return keywords;
    }

    public void setKeywords(List<String> keywords) {
        this.keywords = keywords;
    }

    public String getCat_name() {
        return cat_name;
    }

    public void setCat_name(String cat_name) {
        this.cat_name = cat_name;
    }

    public HomeQuestionBean getQuestion() {
        return question;
    }

    public void setQuestion(HomeQuestionBean question) {
        this.question = question;
    }

    public List<HomeAnswerBean> getAnswers() {
        return answers;
    }

    public void setAnswers(List<HomeAnswerBean> answers) {
        this.answers = answers;
    }
}
