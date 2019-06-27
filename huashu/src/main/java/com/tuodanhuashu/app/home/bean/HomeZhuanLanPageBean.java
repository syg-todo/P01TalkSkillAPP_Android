package com.tuodanhuashu.app.home.bean;


import java.util.List;
import java.util.Map;

public class HomeZhuanLanPageBean {

    private List<HomeBannerBean> banners;

    private List<HomeArticelBean> recommendedArticles;

    private List<HomeArticelClassBean> articleClasses;

    private List<HomeArticelBean> choicenessArticles;

    private HomeAdvertisingBean advertising;

    private String article_cat_rec_list;

    public HomeZhuanLanPageBean() {
    }

    public List<HomeBannerBean> getBanners() {
        return banners;
    }

    public void setBanners(List<HomeBannerBean> banners) {
        this.banners = banners;
    }

    public List<HomeArticelBean> getRecommendedArticles() {
        return recommendedArticles;
    }

    public void setRecommendedArticles(List<HomeArticelBean> recommendedArticles) {
        this.recommendedArticles = recommendedArticles;
    }

    public List<HomeArticelClassBean> getArticleClasses() {
        return articleClasses;
    }

    public void setArticleClasses(List<HomeArticelClassBean> articleClasses) {
        this.articleClasses = articleClasses;
    }

    public List<HomeArticelBean> getChoicenessArticles() {
        return choicenessArticles;
    }

    public void setChoicenessArticles(List<HomeArticelBean> choicenessArticles) {
        this.choicenessArticles = choicenessArticles;
    }

    public HomeAdvertisingBean getAdvertising() {
        return advertising;
    }

    public void setAdvertising(HomeAdvertisingBean advertising) {
        this.advertising = advertising;
    }

    public String getArticle_cat_rec_list() {
        return article_cat_rec_list;
    }

    public void setArticle_cat_rec_list(String article_cat_rec_list) {
        this.article_cat_rec_list = article_cat_rec_list;
    }
}
