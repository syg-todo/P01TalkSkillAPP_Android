package com.tuodanhuashu.app.home.bean;

import java.util.List;

public class TalkSkillClassBean {

    private String cat_id = "";

    private String cat_name = "";

    private String cat_icon = "";

    private String cat_icon2 = "";

    private List<TalkSkillClassBean> subclasses;

    public TalkSkillClassBean() {
    }

    public String getCat_id() {
        return cat_id;
    }

    public void setCat_id(String cat_id) {
        this.cat_id = cat_id;
    }

    public String getCat_name() {
        return cat_name;
    }

    public void setCat_name(String cat_name) {
        this.cat_name = cat_name;
    }

    public String getCat_icon() {
        return cat_icon;
    }

    public void setCat_icon(String cat_icon) {
        this.cat_icon = cat_icon;
    }

    public String getCat_icon2() {
        return cat_icon2;
    }

    public void setCat_icon2(String cat_icon2) {
        this.cat_icon2 = cat_icon2;
    }

    public List<TalkSkillClassBean> getSubclasses() {
        return subclasses;
    }

    public void setSubclasses(List<TalkSkillClassBean> subclasses) {
        this.subclasses = subclasses;
    }
}
