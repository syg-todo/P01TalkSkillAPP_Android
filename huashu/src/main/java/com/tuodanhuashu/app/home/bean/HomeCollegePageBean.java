package com.tuodanhuashu.app.home.bean;

import java.util.List;

public class HomeCollegePageBean {
    private List<HomeBannerBean> banners;

    private List<HomeCourseBean> choicenessCourses;
    private List<HomeCourseBean> recommendCourses;
    public List<HomeBannerBean> getBanners() {
        return banners;
    }

    public void setBanners(List<HomeBannerBean> banners) {
        this.banners = banners;
    }

    public List<HomeCourseBean> getChoicenessCourses() {
        return choicenessCourses;
    }

    public void setChoicenessCourses(List<HomeCourseBean> choicenessCourses) {
        this.choicenessCourses = choicenessCourses;
    }

    public List<HomeCourseBean> getRecommendCourses() {
        return recommendCourses;
    }

    public void setRecommendCourses(List<HomeCourseBean> recommendCourses) {
        this.recommendCourses = recommendCourses;
    }
}
