package com.tuodanhuashu.app.course.presenter;

import android.content.Context;

import com.company.common.base.BasePresenter;
import com.company.common.base.BaseView;
import com.company.common.net.ServerResponse;
import com.company.common.utils.JsonUtils;
import com.tuodanhuashu.app.course.bean.MasterBean;
import com.tuodanhuashu.app.course.biz.MyCourseBiz;
import com.tuodanhuashu.app.course.biz.MyMasterBiz;
import com.tuodanhuashu.app.course.view.MyCourseView;
import com.tuodanhuashu.app.course.view.MyMasterView;
import com.tuodanhuashu.app.home.bean.MyCourseBean;

import java.util.List;

public class MyCoursePresenter extends BasePresenter {

    private Context context;

    private MyCourseView myCourseView;

    private MyCourseBiz myCourseBiz;

    private static final int TAG_MY_COURSE = 1;



    public MyCoursePresenter(Context context, MyCourseView myCourseView) {
        this.context = context;
        this.myCourseView = myCourseView;
        myCourseBiz = new MyCourseBiz(context, this);
    }

    public void requestMyCourse(String accessToken) {
        requestMyCourse(accessToken, "1");
    }

    public void requestMyCourse(String accessToken, String page) {
        myCourseBiz.requestMyCourse(TAG_MY_COURSE, accessToken, page);
    }


    @Override
    public void OnSuccess(ServerResponse serverResponse, int tag) {

        super.OnSuccess(serverResponse, tag);
        switch (tag) {
            case TAG_MY_COURSE:
                List<MyCourseBean> courseBeanList = JsonUtils.getJsonToList(serverResponse.getData(), MyCourseBean.class);
                myCourseView.geMyCourseSuccess(courseBeanList);
                break;
        }
    }

    @Override
    public void onFail(String msg, int code, int tag) {
        super.onFail(msg, code, tag);
        myCourseView.getMyCourseFail(msg);
    }

    @Override
    public BaseView getBaseView() {
        return myCourseView;
    }
}
