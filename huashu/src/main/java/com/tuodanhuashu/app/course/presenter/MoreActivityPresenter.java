package com.tuodanhuashu.app.course.presenter;

import android.content.Context;

import com.company.common.base.BasePresenter;
import com.company.common.base.BaseView;
import com.company.common.net.ServerResponse;
import com.company.common.utils.JsonUtils;
import com.tuodanhuashu.app.course.biz.MoreActivityBiz;
import com.tuodanhuashu.app.home.bean.CollegeActivityBean;
import com.tuodanhuashu.app.home.ui.MoreActivity;
import com.tuodanhuashu.app.home.view.MoreActivityView;

import java.util.List;

public class MoreActivityPresenter extends BasePresenter {

    private Context context;

    private MoreActivityView moreActivityView;

    private MoreActivityBiz moreActivityBiz;

    private static final int TAG_ACTIVITY = 1;
    public MoreActivityPresenter(Context context, MoreActivityView moreActivityView){
        this.moreActivityView = moreActivityView;
        this.context = context;
        moreActivityBiz = new MoreActivityBiz(context,this);
    }

    public void requestActivityList(String page, String pageSize) {
        moreActivityBiz.requestActivityList(TAG_ACTIVITY, page, pageSize);
    }

    @Override
    public void OnSuccess(ServerResponse serverResponse, int tag) {
        super.OnSuccess(serverResponse, tag);
        List<CollegeActivityBean> collegeActivityBeans = JsonUtils.getJsonToList(serverResponse.getData(),CollegeActivityBean.class);
        moreActivityView.getMoreActivitySuccess(collegeActivityBeans);
    }

    @Override
    public void onFail(String msg, int code, int tag) {
        super.onFail(msg, code, tag);
        moreActivityView.getMoreActivityFail(msg);
    }

    @Override
    public BaseView getBaseView() {
        return moreActivityView;
    }


}
