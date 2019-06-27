package com.tuodanhuashu.app.pay.bean;

import com.alibaba.fastjson.annotation.JSONField;

public class WXPayBean {


    private String appid;
    private String noncestr;
    @JSONField(name="package")
    private String packageValue;
    private String partnerid;
    private String prepayid;
    private long timestamp;
    private String sign;

    public WXPayBean() {
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getAppid() {
        return appid;
    }

    public void setNoncestr(String noncestr) {
        this.noncestr = noncestr;
    }

    public String getNoncestr() {
        return noncestr;
    }

    public void setPackageValue(String packageValue) {
        this.packageValue = packageValue;
    }

    public String getPackageValue() {
        return packageValue;
    }

    public void setPartnerid(String partnerid) {
        this.partnerid = partnerid;
    }

    public String getPartnerid() {
        return partnerid;
    }

    public void setPrepayid(String prepayid) {
        this.prepayid = prepayid;
    }

    public String getPrepayid() {
        return prepayid;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getSign() {
        return sign;
    }

    @Override
    public String toString() {
        return "WXPayBean{" +
                "appid='" + appid + '\'' +
                ", noncestr='" + noncestr + '\'' +
                ", packageValue='" + packageValue + '\'' +
                ", partnerid='" + partnerid + '\'' +
                ", prepayid='" + prepayid + '\'' +
                ", timestamp=" + timestamp +
                ", sign='" + sign + '\'' +
                '}';
    }
}
