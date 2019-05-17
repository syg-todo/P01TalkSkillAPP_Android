package com.tuodanhuashu.app.course.bean;

import com.tuodanhuashu.app.home.bean.HomeCourseBean;

import java.util.List;

public class CourseWithBannerBean {
    private String banner;
    private List<HomeCourseBean> courses;

    public String getBanner() {
        return banner;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }

    public List<HomeCourseBean> getCourses() {
        return courses;
    }

    public void setCourses(List<HomeCourseBean> courses) {
        this.courses = courses;
    }
}
