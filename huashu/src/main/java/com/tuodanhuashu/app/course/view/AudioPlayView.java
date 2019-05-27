package com.tuodanhuashu.app.course.view;

import com.company.common.base.BaseView;
import com.tuodanhuashu.app.course.bean.SectionBean;

public interface AudioPlayView extends BaseView {

    void getSectionSuccess(SectionBean section);
    void getSectionFail(String msg);
}
