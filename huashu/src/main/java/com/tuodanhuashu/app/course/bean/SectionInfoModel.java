package com.tuodanhuashu.app.course.bean;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

public class SectionInfoModel extends ViewModel {
    private MutableLiveData<SectionBean> section = new MutableLiveData<>();

    public MutableLiveData<SectionBean> getSection() {
//        Log.d("111",section.getValue().getUrl());

        return section;
    }

    public void setSection(SectionBean sectionBean) {
        section.setValue(sectionBean);
        Log.d("111",section.getValue().getUrl());

    }


}
