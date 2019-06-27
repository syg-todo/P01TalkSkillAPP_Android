package com.tuodanhuashu.app.teacher.presenter;

import android.content.Context;

import com.company.common.base.BasePresenter;
import com.company.common.base.BaseView;
import com.company.common.net.ServerResponse;
import com.company.common.utils.JsonUtils;
import com.tuodanhuashu.app.teacher.bean.TeacherListBean;
import com.tuodanhuashu.app.teacher.biz.TeacherListBiz;
import com.tuodanhuashu.app.teacher.view.TeacherListView;

public class TeacherListPresenter extends BasePresenter {
    private Context context;

    private TeacherListView teacherListView;

    private TeacherListBiz teacherListBiz;

    private static final int TAG_TEACHER_LIST = 1;

    public TeacherListPresenter(Context context, TeacherListView teacherListView) {
        this.context = context;
        this.teacherListView = teacherListView;
        this.teacherListBiz = new TeacherListBiz(this,context);
    }

    public void requestTeacherList(String page,String page_size){
        teacherListBiz.requestTeacherList(TAG_TEACHER_LIST,page,page_size);
    }

    @Override
    public void OnSuccess(ServerResponse serverResponse, int tag) {
        super.OnSuccess(serverResponse, tag);
        TeacherListBean teacherListBean = JsonUtils.getJsonToBean(serverResponse.getData(),TeacherListBean.class);
        teacherListView.getTeacherListSuccess(teacherListBean);
    }

    @Override
    public void onFail(String msg, int code, int tag) {
        super.onFail(msg, code, tag);
        teacherListView.getTeacherListFail(msg);
    }

    @Override
    public BaseView getBaseView() {
        return teacherListView;
    }
}
