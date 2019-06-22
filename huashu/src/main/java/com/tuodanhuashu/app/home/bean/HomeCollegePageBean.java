package com.tuodanhuashu.app.home.bean;

import java.util.List;

public class HomeCollegePageBean {
    private List<HomeBannerBean> banners;
    private ActivityCourse activityCourse;


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
    public ActivityCourse getActivityCourse() {
        return activityCourse;
    }

    public void setActivityCourse(ActivityCourse activityCourse) {
        this.activityCourse = activityCourse;
    }
    public static class ActivityCourse{

        /**
         * activity_image_url : http://huashu.zhongpin.me/uploads/activity/201905231715502222.png
         * id : 1
         * activity_price : 100.00
         * sale_price : 23.00
         * price : 123.00
         */

        private String activity_image_url;
        private String id;
        private String activity_price;
        private String sale_price;
        private String price;

        public String getActivity_image_url() {
            return activity_image_url;
        }

        public void setActivity_image_url(String activity_image_url) {
            this.activity_image_url = activity_image_url;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getActivity_price() {
            return activity_price;
        }

        public void setActivity_price(String activity_price) {
            this.activity_price = activity_price;
        }

        public String getSale_price() {
            return sale_price;
        }

        public void setSale_price(String sale_price) {
            this.sale_price = sale_price;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }
    }
}
