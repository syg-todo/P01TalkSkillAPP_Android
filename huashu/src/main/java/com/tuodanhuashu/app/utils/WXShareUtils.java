package com.tuodanhuashu.app.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.hyphenate.helpdesk.easeui.Constant;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXImageObject;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXTextObject;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.tuodanhuashu.app.Constants.Constants;
import com.tuodanhuashu.app.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class WXShareUtils {
    private static IWXAPI wxApi;
    private static RelativeLayout wx_share_friend;
    private static RelativeLayout wx_share_pengyouquan;

    private static Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            SendMessageToWX.Req req = (SendMessageToWX.Req) msg.obj;

            //调用api接口发送数据到微信
            wxApi.sendReq(req);
        }
    };

    private static void init(Context context) {
        //实例化
        wxApi = WXAPIFactory.createWXAPI(context, Constants.WEIXIN.WX_APP_ID);
        wxApi.registerApp( Constants.WEIXIN.WX_APP_ID);
        //判断是否有安装微信
        if (!wxApi.isWXAppInstalled()) {
            Toast.makeText(context, "您还未安装微信客户端", Toast.LENGTH_SHORT).show();
            return;
        }
    }

//    //显示微信分享选择页面
//    public static void showShareSelect(final Context context, final String title, final String description,
//                                       final String icon, final String webUrl, final int contentType) {
//        View view = LayoutInflater.from(context).inflate(R.layout.share_window, null);
//        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.DialogTheme);
//        builder.setView(view);
//        final AlertDialog shareDialog = builder.create();
//        //显示在底部
//        Window window = shareDialog.getWindow();
//        window.setGravity(Gravity.BOTTOM);
//        window.setWindowAnimations(R.style.dialog_animation);
//        window.getDecorView().setPadding(0, 0, 0, 0);
//        WindowManager.LayoutParams lp = window.getAttributes();
//        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
//        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
//        window.setAttributes(lp);
//        shareDialog.show();
//
//        wx_share_friend = (RelativeLayout) view.findViewById(R.id.wx_share_friend);
//        wx_share_pengyouquan = (RelativeLayout) view.findViewById(R.id.wx_share_pengyouquan);
//
//        //分享给好友
//        wx_share_friend.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                switch (contentType) {
//                    case Constant.wxText:
//                        WXShareUtils.shareText(context, "分享", Constant.wxFriend);
//                        break;
//                    case Constant.wxVideo:
//                        WXShareUtils.shareVideo(context, null, Constant.wxFriend);
//                        break;
//                    case Constant.wxWeb:
//                        WXShareUtils.shareWeb(context, title, description, icon, webUrl, Constant.wxFriend);
//                        break;
//                }
//                shareDialog.dismiss();
//            }
//        });
//
//        //分享到朋友圈
//        wx_share_pengyouquan.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                switch (contentType) {
//                    case Constant.wxText:
//                        WXShareUtils.shareText(context, "分享", Constant.wxQuan);
//                        break;
//                    case Constant.wxVideo:
//                        WXShareUtils.shareVideo(context, null, Constant.wxQuan);
//                        break;
//                    case Constant.wxWeb:
//                        WXShareUtils.shareWeb(context, title, description, icon, webUrl, Constant.wxQuan);
//                        break;
//                }
//                shareDialog.dismiss();
//            }
//        });
//    }




    public static String buildTransaction(final String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
    }



    /*
    分享网页
     */
    public static void shareWeb(final Context context, String title, String description, final String icon, String webUrl, String type) {
        init(context);
        //初始化一个WXWebpageObject对象，填写URL
        WXWebpageObject webpage = new WXWebpageObject();
        webpage.webpageUrl = webUrl;

        //用WXWebpageObject对象初始化一个WXMediaMessage对象，填写标题、描述
        final WXMediaMessage msg = new WXMediaMessage(webpage);
        msg.title = title;
        msg.description = description;
        //构造一个Req
        final SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("webpage");
        if (type.equals("you")) {
            req.scene = SendMessageToWX.Req.WXSceneSession;
        } else if (type.equals("quan")) {
            req.scene = SendMessageToWX.Req.WXSceneTimeline;
        }
        wxApi.sendReq(req);

//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                Bitmap bitmap = loadBitmap(icon);
//                Bitmap thumbBmp = Bitmap.createScaledBitmap(bitmap, 120, 120, true);
//                msg.thumbData = Util.bmpToByteArray(thumbBmp, true);
//
//                req.message = msg;
//                Message message = mHandler.obtainMessage();
//                message.obj = req;
//                mHandler.sendMessage(message);
//            }
//        }).start();
    }

    //下载网络图片
    private static Bitmap loadBitmap(String icon) {
        try {
            URL url = new URL(icon);
            InputStream is = url.openStream();
            Bitmap bitmap = BitmapFactory.decodeStream(is);
            return bitmap;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
