package com.tuodanhuashu.app.MemberCenter.bean;

import com.tuodanhuashu.app.home.bean.HomeAdvertisingBean;

import java.util.Map;

public class MemberCenterBean {

    private HomeAdvertisingBean advertising;

    private PersonInfoBean userinfo;

    public MemberCenterBean() {
    }

    public HomeAdvertisingBean getAdvertising() {
        return advertising;
    }

    public void setAdvertising(HomeAdvertisingBean advertising) {
        this.advertising = advertising;
    }

    public PersonInfoBean getUserinfo() {
        return userinfo;
    }

    public void setUserinfo(PersonInfoBean userinfo) {
        this.userinfo = userinfo;
    }
}
