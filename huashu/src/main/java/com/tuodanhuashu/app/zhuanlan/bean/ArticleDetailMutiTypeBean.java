package com.tuodanhuashu.app.zhuanlan.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;

public class ArticleDetailMutiTypeBean implements MultiItemEntity {

    public static final int WEB = 1;
    public static final int COMMON = 2;

    private int itemType = 1;

    private String url="";

    private ArticleCommentBean articleCommentBean;

    public ArticleDetailMutiTypeBean(int itemType) {
        this.itemType = itemType;
    }

    public ArticleDetailMutiTypeBean(int itemType, String url) {
        this.itemType = itemType;
        this.url = url;
    }

    public ArticleDetailMutiTypeBean(int itemType, ArticleCommentBean articleCommentBean) {
        this.itemType = itemType;
        this.articleCommentBean = articleCommentBean;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public ArticleCommentBean getArticleCommentBean() {
        return articleCommentBean;
    }

    public void setArticleCommentBean(ArticleCommentBean articleCommentBean) {
        this.articleCommentBean = articleCommentBean;
    }

    @Override
    public int getItemType() {
        return itemType;
    }
}
