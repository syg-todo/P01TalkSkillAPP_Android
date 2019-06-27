package com.tuodanhuashu.app.home.bean;

import java.util.List;
import java.util.Map;

public class HomePageBean {

    private List<HomeBannerBean> banners;

    private List<HomeRecommendedArticleClass> recommendedArticleClasses;

    private List<HotKeywordsBean> hotKeywords;

    private HomeAdvertisingBean advertising;

    private Map<String,List<HomeArticleBean>> newArticles;

    private List<String> newArticle_date;

    public HomePageBean() {
    }

    public List<HomeBannerBean> getBanners() {
        return banners;
    }

    public void setBanners(List<HomeBannerBean> banners) {
        this.banners = banners;
    }

    public List<HomeRecommendedArticleClass> getRecommendedArticleClasses() {
        return recommendedArticleClasses;
    }

    public void setRecommendedArticleClasses(List<HomeRecommendedArticleClass> recommendedArticleClasses) {
        this.recommendedArticleClasses = recommendedArticleClasses;
    }

    public List<HotKeywordsBean> getHotKeywords() {
        return hotKeywords;
    }

    public void setHotKeywords(List<HotKeywordsBean> hotKeywords) {
        this.hotKeywords = hotKeywords;
    }

    public HomeAdvertisingBean getAdvertising() {
        return advertising;
    }

    public void setAdvertising(HomeAdvertisingBean advertising) {
        this.advertising = advertising;
    }

    public Map<String, List<HomeArticleBean>> getNewArticles() {
        return newArticles;
    }

    public void setNewArticles(Map<String, List<HomeArticleBean>> newArticles) {
        this.newArticles = newArticles;
    }

    public List<String> getNewArticle_date() {
        return newArticle_date;
    }

    public void setNewArticle_date(List<String> newArticle_date) {
        this.newArticle_date = newArticle_date;
    }
}
