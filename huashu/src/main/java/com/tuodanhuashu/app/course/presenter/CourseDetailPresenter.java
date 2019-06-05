package com.tuodanhuashu.app.course.presenter;

import android.content.Context;
import android.util.Log;

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
import com.tuodanhuashu.app.course.bean.CourseDetailBean;
import com.tuodanhuashu.app.course.biz.CourseDetailBiz;
import com.tuodanhuashu.app.course.biz.CourseListBiz;
import com.tuodanhuashu.app.course.view.CourseDetailView;
import com.tuodanhuashu.app.course.view.CourseListView;

public class CourseDetailPresenter extends BasePresenter {

    private Context context;

    private CourseDetailView courseDetailView;

    private CourseDetailBiz courseDetailBiz;

    private static final int TAG_COURSE_DETAIL = 1;

    private static final int TAG_RECORD_MASTER = 2;

    private static final int TAG_UNRECORD_MASTER = 3;


    public CourseDetailPresenter(Context context, CourseDetailView courseDetailView) {
        this.context = context;
        this.courseDetailView = courseDetailView;
        courseDetailBiz = new CourseDetailBiz(context, this);
    }

    public void requestCourseDetail(String accessToken, String courseId) {
        courseDetailBiz.requestCourseDetailByCourseId(TAG_COURSE_DETAIL, accessToken, courseId);
    }

    public void recordMaster(String accessToken, String masterId) {
        courseDetailBiz.recordMaster(TAG_RECORD_MASTER, accessToken, masterId);
    }

    public void unrecordMaster(String accessToken, String masterId) {
        courseDetailBiz.unrecordMaster(TAG_UNRECORD_MASTER, accessToken, masterId);
    }

    @Override
    public void OnSuccess(ServerResponse serverResponse, int tag) {
        super.OnSuccess(serverResponse, tag);
        switch (tag) {
            case TAG_COURSE_DETAIL:
                CourseDetailBean courseDetailBean = JsonUtils.getJsonToBean(serverResponse.getData(), CourseDetailBean.class);
                Log.d("CourseDetailActivity","presenter:"+courseDetailBean.getCourse().getIs_checkout());
                courseDetailView.getCourseDetailSuccess(courseDetailBean);
                break;
            case TAG_RECORD_MASTER:
                Log.d("111",serverResponse.getCode()+"");
//                CourseDetailBean courseDetailRecord = JsonUtils.getJsonToBean(serverResponse.getData(),CourseDetailBean.class);
//                courseDetailView.getCourseDetailSuccess(courseDetailRecord);
                break;
            case TAG_UNRECORD_MASTER:
                Log.d("111",serverResponse.getCode()+"");
//                CourseDetailBean courseDetailUnrecord = JsonUtils.getJsonToBean(serverResponse.getData(),CourseDetailBean.class);
//                courseDetailView.getCourseDetailSuccess(courseDetailUnrecord);
                break;
        }
    }

    public void shareArticle(String url,String title,String desc,String type){
        IWXAPI wxApi = WXAPIFactory.createWXAPI(context, Constants.WEIXIN.WX_APP_ID);
        wxApi.registerApp( Constants.WEIXIN.WX_APP_ID);
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
        if(type.equals("quan")){
            req.scene = SendMessageToWX.Req.WXSceneTimeline;
        }else{
            req.scene = SendMessageToWX.Req.WXSceneSession;
        }
        //req.scene = SendMessageToWX.Req.WXSceneTimeline;    //设置发送到朋友圈
        //设置发送给朋友
        req.transaction = "设置一个tag";  //用于在回调中区分是哪个分享请求
        boolean successed = wxApi.sendReq(req);   //如果调用成功微信,会返回true
        getBaseView().showToast(successed+"");
    }


    @Override
    public void onFail(String msg, int code, int tag) {
        super.onFail(msg, code, tag);
        courseDetailView.getCourseDetailFail(msg);
    }

    @Override
    public BaseView getBaseView() {
        return courseDetailView;
    }
}
