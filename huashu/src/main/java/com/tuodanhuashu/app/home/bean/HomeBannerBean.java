package com.tuodanhuashu.app.home.bean;

public class HomeBannerBean {

    private String banner_type = "";//1:无需跳转;2:跳转话术搜索;3:跳转文章

    private String image_url = "";

    private String article_id = "";

    private String keyword = "";

    private String courseId = "";

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public HomeBannerBean() {
    }

    public String getBanner_type() {
        return banner_type;
    }

    public void setBanner_type(String banner_type) {
        this.banner_type = banner_type;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getArticle_id() {
        return article_id;
    }

    public void setArticle_id(String article_id) {
        this.article_id = article_id;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
}
