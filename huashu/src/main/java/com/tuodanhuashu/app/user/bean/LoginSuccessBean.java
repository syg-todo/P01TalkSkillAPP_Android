package com.tuodanhuashu.app.user.bean;

public class LoginSuccessBean {


    private String access_token;
    private String accountid;
    private String mobile;
    private String wxopenid;
    private String realname;
    private String name;
    private String heade_img;
    private String vip_end_time;
    private String is_vip;

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccountid(String accountid) {
        this.accountid = accountid;
    }

    public String getAccountid() {
        return accountid;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getMobile() {
        return mobile;
    }

    public void setWxopenid(String wxopenid) {
        this.wxopenid = wxopenid;
    }

    public String getWxopenid() {
        return wxopenid;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getRealname() {
        return realname;
    }

    public void setname(String name) {
        this.name = name;
    }

    public String getname() {
        return name;
    }

    public void setHeade_img(String heade_img) {
        this.heade_img = heade_img;
    }

    public String getHeade_img() {
        return heade_img;
    }

    public void setVip_end_time(String vip_end_time) {
        this.vip_end_time = vip_end_time;
    }

    public String getVip_end_time() {
        return vip_end_time;
    }

    public void setIs_vip(String is_vip) {
        this.is_vip = is_vip;
    }

    public String getIs_vip() {
        return is_vip;
    }
}
