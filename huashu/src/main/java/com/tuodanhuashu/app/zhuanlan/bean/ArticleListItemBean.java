package com.tuodanhuashu.app.zhuanlan.bean;

public class ArticleListItemBean {
    private String id = "";

    private String title = "";

    private String description = "";

    private String image_url = "";

    private String article_class_id = "";

    private String article_class_name = "";

    private String create_time = "";

    private String create_date = "";

    private String  read_counts = "";

    private String source = "";

    public ArticleListItemBean() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getArticle_class_id() {
        return article_class_id;
    }

    public void setArticle_class_id(String article_class_id) {
        this.article_class_id = article_class_id;
    }

    public String getArticle_class_name() {
        return article_class_name;
    }

    public void setArticle_class_name(String article_class_name) {
        this.article_class_name = article_class_name;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getCreate_date() {
        return create_date;
    }

    public void setCreate_date(String create_date) {
        this.create_date = create_date;
    }

    public String getRead_counts() {
        return read_counts;
    }

    public void setRead_counts(String read_counts) {
        this.read_counts = read_counts;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
}
