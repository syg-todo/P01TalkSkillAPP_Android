package com.tuodanhuashu.app.zhuanlan.bean;

import java.util.List;

public class ArticleDetailBean {

    private String id = "";

    private String article_class_id = "";

    private String title = "";

    private String url = "";

    private int is_collection = 0;

    private String description = "";

    private String content="";

    private String image_url="";

    private String source="";

    private int is_recommend=0;

    private int is_choiceness=0;

    private String create_time="";

    private int is_enable=0;

    private List<ArticleCommentBean> comment;


    public ArticleDetailBean() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getArticle_class_id() {
        return article_class_id;
    }

    public void setArticle_class_id(String article_class_id) {
        this.article_class_id = article_class_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getIs_collection() {
        return is_collection;
    }

    public void setIs_collection(int is_collection) {
        this.is_collection = is_collection;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public int getIs_recommend() {
        return is_recommend;
    }

    public void setIs_recommend(int is_recommend) {
        this.is_recommend = is_recommend;
    }

    public int getIs_choiceness() {
        return is_choiceness;
    }

    public void setIs_choiceness(int is_choiceness) {
        this.is_choiceness = is_choiceness;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public int getIs_enable() {
        return is_enable;
    }

    public void setIs_enable(int is_enable) {
        this.is_enable = is_enable;
    }

    public List<ArticleCommentBean> getComment() {
        return comment;
    }

    public void setComment(List<ArticleCommentBean> comment) {
        this.comment = comment;
    }
}
