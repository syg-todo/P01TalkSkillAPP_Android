package com.tuodanhuashu.app.pay.bean;

import java.util.List;

public class GoodsListItemBean {

    private String id = "";

    private String price = "";

    private String months = "";

    private String token = "";

    private String name = "";

    private List<String> flag;

    public GoodsListItemBean() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getMonths() {
        return months;
    }

    public void setMonths(String months) {
        this.months = months;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getFlag() {
        return flag;
    }

    public void setFlag(List<String> flag) {
        this.flag = flag;
    }
}
