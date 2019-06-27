package com.tuodanhuashu.app.course.presenter;

import android.content.Context;

import com.company.common.base.BasePresenter;
import com.company.common.base.BaseView;
import com.company.common.net.ServerResponse;
import com.company.common.utils.JsonUtils;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.tuodanhuashu.app.Constants.Constants;
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

    public void shareArticle(String url, String title, String desc, String type) {
        IWXAPI wxApi = WXAPIFactory.createWXAPI(context, Constants.WEIXIN.WX_APP_ID);
        wxApi.registerApp(Constants.WEIXIN.WX_APP_ID);
        WXWebpageObject webpage = new WXWebpageObject();
        webpage.webpageUrl = url;
        final WXMediaMessage msg = new WXMediaMessage(webpage);
        msg.title = title;
        msg.description = desc;

//        Bitmap thumb = BitmapFactory.decodeResource(context.getResources(), R.drawable.xxx);
//        if(thumb != null) {
//            Bitmap mBp = Bitmap.createScaledBitmap(thumb, 120, 120, true);
//            thumb.recycle();
//
//        }
        SendMessageToWX.Req req = new SendMessageToWX.Req();    //创建一个请求对象
        req.message = msg;
        if (type.equals("quan")) {
            req.scene = SendMessageToWX.Req.WXSceneTimeline;
        } else {
            req.scene = SendMessageToWX.Req.WXSceneSession;
        }
        //req.scene = SendMessageToWX.Req.WXSceneTimeline;    //设置发送到朋友圈
        //设置发送给朋友
        req.transaction = "设置一个tag";  //用于在回调中区分是哪个分享请求
        boolean successed = wxApi.sendReq(req);   //如果调用成功微信,会返回true
//        getBaseView().showToast(successed+"");
    }

}
