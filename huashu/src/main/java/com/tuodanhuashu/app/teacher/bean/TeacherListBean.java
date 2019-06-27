package com.tuodanhuashu.app.teacher.bean;

import java.util.List;

public class TeacherListBean {

    private List<TeacherAdBean> advt_list;

    private List<TeacherListItemBean> expert_list;

    public TeacherListBean() {
    }

    public List<TeacherAdBean> getAdvt_list() {
        return advt_list;
    }

    public void setAdvt_list(List<TeacherAdBean> advt_list) {
        this.advt_list = advt_list;
    }

    public List<TeacherListItemBean> getExpert_list() {
        return expert_list;
    }

    public void setExpert_list(List<TeacherListItemBean> expert_list) {
        this.expert_list = expert_list;
    }
}
