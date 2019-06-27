package com.tuodanhuashu.app.teacher.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.List;

public class TeacherMutiTypeBean implements MultiItemEntity {

    public static final int TYPE_AD = 1;

    public static final int TYPE_TEACHER = 2;

    private int itemType = 1;

    private List<TeacherAdBean> adBeanList;

    private TeacherListItemBean teacherListItemBean;

    public TeacherMutiTypeBean(int itemType, List<TeacherAdBean> adBeanList) {
        this.itemType = itemType;
        this.adBeanList = adBeanList;
    }

    public TeacherMutiTypeBean(int itemType, TeacherListItemBean teacherListItemBean) {
        this.itemType = itemType;
        this.teacherListItemBean = teacherListItemBean;
    }

    public List<TeacherAdBean> getAdBeanList() {
        return adBeanList;
    }

    public void setAdBeanList(List<TeacherAdBean> adBeanList) {
        this.adBeanList = adBeanList;
    }

    public TeacherListItemBean getTeacherListItemBean() {
        return teacherListItemBean;
    }

    public void setTeacherListItemBean(TeacherListItemBean teacherListItemBean) {
        this.teacherListItemBean = teacherListItemBean;
    }

    @Override
    public int getItemType() {
        return itemType;
    }
}
