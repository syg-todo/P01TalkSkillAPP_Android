package com.tuodanhuashu.app.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.company.common.utils.JsonUtils;
import com.company.common.utils.ToastUtils;
import com.tuodanhuashu.app.course.ui.CourseDetailActivity;
import com.tuodanhuashu.app.huashu.ui.HuaShuaListActivity;
import com.tuodanhuashu.app.user.ui.LoginActivity;
import com.tuodanhuashu.app.zhuanlan.ui.ZhuanLanDetailActivity;
import com.tuodanhuashu.app.zhuanlan.ui.ZhuanLanListActivity;

import org.json.JSONObject;

import java.util.logging.Logger;

import cn.jpush.android.api.JPushInterface;

public class HuaShuReceiver extends BroadcastReceiver {
    private static final String TAG = "MyReceiver";
    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();


        if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {


        } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {


        } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {




        } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {

            ToastUtils.showToast(context,"用户点击打开了通知");
            openNotification(context,bundle);

        } else {

        }
    }

    private void openNotification(Context context, Bundle bundle){
        String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
        Intent i = new Intent();
        Bundle b = new Bundle();
        PushMessageBean pushMessageBean = JsonUtils.getJsonToBean(extras,PushMessageBean.class);
        switch (pushMessageBean.getType()){
            case 2:     //进入话术
                i.setClass(context,HuaShuaListActivity.class);
                b.putString(HuaShuaListActivity.EXTRA_KEY_WORDS, pushMessageBean.getKeywords());
                i.putExtras(b);
                context.startActivity(i);
                break;
            case 3:     //进入文章
                i.setClass(context,ZhuanLanDetailActivity.class);
                b.putString(ZhuanLanDetailActivity.EXTRA_ARTICLE_ID, pushMessageBean.getArticleId());
                i.putExtras(b);
                context.startActivity(i);
                break;
            case 4:     //进入课程详情
                i.setClass(context, CourseDetailActivity.class);
                b.putString(CourseDetailActivity.EXTRA_COURSE_ID,pushMessageBean.getCourseId());
                i.putExtras(b);
                context.startActivity(i);
                break;
            default:
                break;
        }
    }
}
