package com.tuodanhuashu.app.zhuanlan.presenter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.company.common.base.BasePresenter;
import com.company.common.base.BaseView;
import com.company.common.net.ServerResponse;
import com.company.common.utils.JsonUtils;
import com.company.common.utils.ToastUtils;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.tuodanhuashu.app.Constants.Constants;
import com.tuodanhuashu.app.zhuanlan.bean.ArticleDetailBean;
import com.tuodanhuashu.app.zhuanlan.biz.ArticleDetailBiz;
import com.tuodanhuashu.app.zhuanlan.view.ZhuanLanDetailView;

public class ArticleDetailPresenter extends BasePresenter {

    private ZhuanLanDetailView zhuanLanDetailView;

    private Context context;

    private ArticleDetailBiz articleDetailBiz;

    private static final int TAG_DETAIL = 1;

    private static final int TAG_COLLECT_ARTICLE = 2;

    private static final int TAG_UNCOLLECT_ARTICLE = 3;

    private static final int TAG_COMMENT_ARTICLE = 4;

    private static final int TAG_GET_MIJI = 5;

    public ArticleDetailPresenter(ZhuanLanDetailView zhuanLanDetailView, Context context) {
        this.zhuanLanDetailView = zhuanLanDetailView;
        this.context = context;
        this.articleDetailBiz = new ArticleDetailBiz(this,context);
    }


    public void requestArticleDetail(String news_id){
        articleDetailBiz.requestArticleDetailbean(TAG_DETAIL,news_id);
    }

    public void collectArticle(String news_id){
        articleDetailBiz.collectArticle(TAG_COLLECT_ARTICLE,news_id);
    }

    public void unCollectArticle(String news_id){
        articleDetailBiz.unCollectArticle(TAG_UNCOLLECT_ARTICLE,news_id);
    }

    public void commentArticle(String content,String article_id){
        articleDetailBiz.commentArticle(TAG_COMMENT_ARTICLE,content,article_id);
    }

    public void getMiji(){
        articleDetailBiz.getMiji(TAG_GET_MIJI);
    }

    @Override
    public void OnSuccess(ServerResponse serverResponse, int tag) {
        super.OnSuccess(serverResponse, tag);
        switch (tag){
            case TAG_DETAIL:
                ArticleDetailBean articleDetailBean = JsonUtils.getJsonToBean(serverResponse.getData(),ArticleDetailBean.class);
                zhuanLanDetailView.getArticleDetailSuccess(articleDetailBean);
                break;
            case TAG_COLLECT_ARTICLE:
                zhuanLanDetailView.collectSuccess(serverResponse.getMsg());
                break;
            case TAG_UNCOLLECT_ARTICLE:
                zhuanLanDetailView.unCollectSuccess(serverResponse.getMsg());
                break;
            case TAG_GET_MIJI:
                String url = serverResponse.getData();
                zhuanLanDetailView.getMijiSuccess(url);
                break;
            case TAG_COMMENT_ARTICLE:
                zhuanLanDetailView.CommentSuccess(serverResponse.getMsg());
                break;
        }
    }

    @Override
    public void onFail(String msg, int code, int tag) {
        super.onFail(msg, code, tag);
        getBaseView().cancalLoadingDialog();
        zhuanLanDetailView.showToast(msg);
    }

    @Override
    public BaseView getBaseView() {
        return zhuanLanDetailView;
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
//        getBaseView().showToast(successed+"");
    }
}
