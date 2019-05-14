package com.tuodanhuashu.app.home.bean;

public class HomeArticleCatRec {


    private String article_cat;
    private String pic_url;
    private String title;

    public HomeArticleCatRec(String article_cat, String pic_url, String title) {
        this.article_cat = article_cat;
        this.pic_url = pic_url;
        this.title = title;
    }

    public String getArticle_cat() {
        return article_cat;
    }

    public void setArticle_cat(String article_cat) {
        this.article_cat = article_cat;
    }

    public String getPic_url() {
        return pic_url;
    }

    public void setPic_url(String pic_url) {
        this.pic_url = pic_url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
