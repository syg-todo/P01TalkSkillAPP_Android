package com.tuodanhuashu.app.huashu.bean;

import com.tuodanhuashu.app.home.bean.HomeAdvertisingBean;

import java.util.List;

public class TalkSkillResultBean {
    private boolean isVip = false;

    private int surplus = 0;

    private HomeAdvertisingBean advertising;

    private List<TalkSkillListItemBean> list;

    public TalkSkillResultBean() {
    }

    public boolean isVip() {
        return isVip;
    }

    public void setVip(boolean vip) {
        isVip = vip;
    }

    public int getSurplus() {
        return surplus;
    }

    public void setSurplus(int surplus) {
        this.surplus = surplus;
    }

    public HomeAdvertisingBean getAdvertising() {
        return advertising;
    }

    public void setAdvertising(HomeAdvertisingBean advertising) {
        this.advertising = advertising;
    }

    public List<TalkSkillListItemBean> getList() {
        return list;
    }

    public void setList(List<TalkSkillListItemBean> list) {
        this.list = list;
    }
}
