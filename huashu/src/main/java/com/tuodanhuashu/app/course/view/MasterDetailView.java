package com.tuodanhuashu.app.course.view;

import com.company.common.base.BaseView;
import com.tuodanhuashu.app.course.bean.MasterBean;

public interface MasterDetailView extends BaseView {

    void getMasterDetailSuccess(MasterBean masterBean);

    void getMasterDetailFail(String msg);

    void getRecordSuccess();

    void getUnrecordSuccess();

    void getRecordFail(String msg);
}
