package com.tuodanhuashu.app.course.bean;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

public class MasterDetailModel extends ViewModel {
    private MutableLiveData<MasterBean> master = new MutableLiveData<>();


    public MutableLiveData<MasterBean> getCourseDetail() {
        return master;
    }

    public void setMasterDetail(MasterBean master) {
        this.master.setValue(master);
    }
}
