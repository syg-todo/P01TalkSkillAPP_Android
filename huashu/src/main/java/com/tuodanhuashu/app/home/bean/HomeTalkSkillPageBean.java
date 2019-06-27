package com.tuodanhuashu.app.home.bean;

import java.util.List;

public class HomeTalkSkillPageBean {

    private List<HomeTalkSkillClassBean> talkSkillClasses;

    private List<HomeTalkSkillItemBean> choicenessTalkSkills;

    private HomeAdvertisingBean advertising;

    public HomeTalkSkillPageBean() {
    }

    public List<HomeTalkSkillClassBean> getTalkSkillClasses() {
        return talkSkillClasses;
    }

    public void setTalkSkillClasses(List<HomeTalkSkillClassBean> talkSkillClasses) {
        this.talkSkillClasses = talkSkillClasses;
    }

    public List<HomeTalkSkillItemBean> getChoicenessTalkSkills() {
        return choicenessTalkSkills;
    }

    public void setChoicenessTalkSkills(List<HomeTalkSkillItemBean> choicenessTalkSkills) {
        this.choicenessTalkSkills = choicenessTalkSkills;
    }

    public HomeAdvertisingBean getAdvertising() {
        return advertising;
    }

    public void setAdvertising(HomeAdvertisingBean advertising) {
        this.advertising = advertising;
    }
}
