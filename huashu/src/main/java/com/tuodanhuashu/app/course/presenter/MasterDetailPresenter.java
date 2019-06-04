package com.tuodanhuashu.app.course.presenter;

import android.content.Context;

import com.company.common.base.BasePresenter;
import com.company.common.base.BaseView;
import com.company.common.net.ServerResponse;
import com.company.common.utils.JsonUtils;
import com.tuodanhuashu.app.course.bean.MasterBean;
import com.tuodanhuashu.app.course.biz.MasterDetailBiz;
import com.tuodanhuashu.app.course.view.MasterDetailView;

public class MasterDetailPresenter extends BasePresenter {

    private Context context;

    private MasterDetailView masterDetailView;

    private MasterDetailBiz masterDetailBiz;

    private static final int TAG_MASTER_DETAIL = 1;
    private static final int TAG_RECORD_MASTER = 2;
    private static final int TAG_UNRECORD_MASTER = 3;


    public MasterDetailPresenter(Context context, MasterDetailView masterDetailView) {
        this.context = context;
        this.masterDetailView = masterDetailView;
        masterDetailBiz = new MasterDetailBiz(context, this);
    }


    @Override
    public BaseView getBaseView() {
        return masterDetailView;
    }

    @Override
    public void OnSuccess(ServerResponse serverResponse, int tag) {
        super.OnSuccess(serverResponse, tag);
        switch (tag) {
            case TAG_MASTER_DETAIL:
                MasterBean master = JsonUtils.getJsonToBean(serverResponse.getData(), MasterBean.class);
                masterDetailView.getMasterDetailSuccess(master);
                break;
            case TAG_RECORD_MASTER:
                if (serverResponse.getCode()==0){
                    masterDetailView.getRecordSuccess();
                }else {
                    masterDetailView.getRecordFail("关注失败");
                }
                break;
            case TAG_UNRECORD_MASTER:
                if (serverResponse.getCode()==0){
                    masterDetailView.getRecordSuccess();
                }else {
                    masterDetailView.getRecordFail("取消关注失败");
                }
                break;
        }
    }

    public void requestMasterDetail(String accessToken, String masterId) {
        masterDetailBiz.requestMasterDetail(TAG_MASTER_DETAIL, accessToken, masterId);
    }

    public void recordMaster(String accessToken, String masterId) {
        masterDetailBiz.recordMaster(TAG_RECORD_MASTER, accessToken, masterId);
    }

    public void unrecordMaster(String accessToken, String masterId) {
        masterDetailBiz.unrecordMaster(TAG_UNRECORD_MASTER, accessToken, masterId);
    }
}
