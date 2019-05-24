package com.tuodanhuashu.app.course.view;

import com.company.common.base.BaseView;
import com.tuodanhuashu.app.course.bean.SectionBean;

public interface PlayView extends BaseView {

    void getSectionSuccess(SectionBean section);
    void getSectionFail(String msg);
}
