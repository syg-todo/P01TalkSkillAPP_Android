package com.tuodanhuashu.app.teacher.view;

import com.company.common.base.BaseView;
import com.tuodanhuashu.app.teacher.bean.TeacherListBean;

public interface TeacherListView extends BaseView {

    public void getTeacherListSuccess(TeacherListBean teacherListBean);

    public void getTeacherListFail(String msg);
}
