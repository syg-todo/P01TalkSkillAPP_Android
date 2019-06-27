package com.tuodanhuashu.app.home.bean;

import java.util.List;

public class HomeTalkSkillItemBean {
    private String talkSkillId;
    private List<String> keywords;
    private String cat_name;
    private HomeQuestionBean question;
    private List<HomeAnswerBean> answers;

    public void setTalkSkillId(String talkSkillId) {
        this.talkSkillId = talkSkillId;
    }

    public String getTalkSkillId() {
        return talkSkillId;
    }

    public void setKeywords(List<String> keywords) {
        this.keywords = keywords;
    }

    public List<String> getKeywords() {
        return keywords;
    }

    public void setCat_name(String cat_name) {
        this.cat_name = cat_name;
    }

    public String getCat_name() {
        return cat_name;
    }

    public void setQuestion(HomeQuestionBean question) {
        this.question = question;
    }

    public HomeQuestionBean getQuestion() {
        return question;
    }

    public void setAnswers(List<HomeAnswerBean> answers) {
        this.answers = answers;
    }

    public List<HomeAnswerBean> getAnswers() {
        return answers;
    }
}
