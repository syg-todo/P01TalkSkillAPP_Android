package com.tuodanhuashu.app.course.bean;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

public class CourseDetailModel extends ViewModel {
    private MutableLiveData<CourseDetailBean> courseDetail = new MutableLiveData<>();

    public MutableLiveData<CourseDetailBean> getCourseDetail() {

        return courseDetail;
    }

    public void setCourseDetail(CourseDetailBean course) {
        courseDetail.setValue(course);

    }
}
