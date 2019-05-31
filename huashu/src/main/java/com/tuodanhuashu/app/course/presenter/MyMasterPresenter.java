package com.tuodanhuashu.app.course.presenter;

import android.content.Context;

import com.company.common.base.BasePresenter;
import com.company.common.base.BaseView;
import com.company.common.net.ServerResponse;
import com.company.common.utils.JsonUtils;
import com.tuodanhuashu.app.course.bean.MasterBean;
import com.tuodanhuashu.app.course.biz.MyMasterBiz;
import com.tuodanhuashu.app.course.view.MyMasterView;

import java.util.List;

public class MyMasterPresenter extends BasePresenter {

    private Context context;

    private MyMasterView myMasterView;

    private MyMasterBiz myMasterBiz;

    private static final int TAG_MY_MASTER = 1;

    private static final int TAG_UNRECORD_MASTER = 2;


    public MyMasterPresenter(Context context, MyMasterView myMasterView) {
        this.context = context;
        this.myMasterView = myMasterView;
        myMasterBiz = new MyMasterBiz(context, this);
    }

    public void requestMyMaster(String accessToken) {
        myMasterBiz.requestMyMaster(TAG_MY_MASTER, accessToken);
    }

    public void requestMyMaster(String accessToken, String page) {
        myMasterBiz.requestMyMaster(TAG_MY_MASTER, accessToken, page);
    }

    public void unrecordMaster(String accessToken,String masterId){
        myMasterBiz.unrecordMaster(TAG_UNRECORD_MASTER,accessToken,masterId);
    }

    @Override
    public void OnSuccess(ServerResponse serverResponse, int tag) {

        super.OnSuccess(serverResponse, tag);
        switch (tag) {
            case TAG_MY_MASTER:
                List<MasterBean> masterBeanList = JsonUtils.getJsonToList(serverResponse.getData(), MasterBean.class);
                myMasterView.geMyMasterSuccess(masterBeanList);
                break;
        }
    }

    @Override
    public void onFail(String msg, int code, int tag) {
        super.onFail(msg, code, tag);
        myMasterView.getMyMasterFail(msg);
    }

    @Override
    public BaseView getBaseView() {
        return myMasterView;
    }
}
