package com.tuodanhuashu.app.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.FileProvider;
import android.util.Log;

import com.company.common.net.OkNetUtils;
import com.company.common.net.OnDownloadListener;
import com.tuodanhuashu.app.Constants.Constants;
import com.tuodanhuashu.app.HuaShuApplication;
import com.tuodanhuashu.app.R;

import java.io.File;

public class MyService extends Service {


    private NotificationManager notificationManager;
    private Notification notification;
    private NotificationCompat.Builder builder;
    private boolean flag = false;
    public static boolean isUpdate = false;

    public static String EXTRA_DOWN_URL = "down_url";

    public static String EXTRA_FILE_DIR = "file_dir";

    public static String EXTRA_FILE_NAME = "file_name";

    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private void initNotification() {
        notificationManager = (NotificationManager) HuaShuApplication.getInstance().getSystemService(Context.NOTIFICATION_SERVICE);
        builder = new NotificationCompat.Builder(HuaShuApplication.getInstance());
        builder.setContentTitle("正在下载安装包...")
                .setSmallIcon(R.mipmap.huashu_icon)
                .setLargeIcon(BitmapFactory.decodeResource(this.getResources(), R.mipmap.huashu_icon)) //设置通知的大图标
                .setDefaults(Notification.DEFAULT_LIGHTS)
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setAutoCancel(false)
                .setContentText("下载进度:" + "0%")
                .setProgress(100, 0, false);
        notification = builder.build();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String url = intent.getStringExtra(EXTRA_DOWN_URL);
        String fileDir = intent.getStringExtra(EXTRA_FILE_DIR);
        String fileName = intent.getStringExtra(EXTRA_FILE_NAME);
        downloadApk(url,fileDir,fileName);
        return super.onStartCommand(intent, flags, startId);
    }

    private void downloadApk(String url, final String destDir, final String fileName){
        OkNetUtils.downLoadFile(url, destDir, fileName, new OnDownloadListener() {
            @Override
            public void onTaskStart() {
                initNotification();

            }

            @Override
            public void onProgeress(float frac) {
              //  Log.e("update", "downloadProgress: " + frac);
                builder.setProgress(100, (int) (frac * 100), false);
                builder.setContentText("下载进度:" + (int) (frac * 100) + "%");
                notification = builder.build();
                notificationManager.notify(1, notification);

            }

            @Override
            public void onSuccess() {
                Log.e("File",destDir+File.separator+fileName);
                builder.setContentTitle("下载完成")
                        .setContentText("点击安装")
                        .setAutoCancel(true);
                PendingIntent pi = PendingIntent.getActivity(MyService.this, 0, installNormal(MyService.this,destDir+File.separator+fileName), 0);
                notification = builder.setContentIntent(pi).build();

                notification = builder.build();
                notificationManager.notify(1, notification);

            }

            @Override
            public void onError() {
                builder.setContentTitle("下载失败")
                        .setContentText("点击取消")
                        .setAutoCancel(true);
                notification = builder.build();
                notificationManager.notify(1, notification);

            }
        });
    }



    private static Intent installNormal(Context context,String apkPath) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        //版本在7.0以上是不能直接通过uri访问的
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            File file = (new File(apkPath));
            // 由于没有在Activity环境下启动Activity,设置下面的标签
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            //参数1 上下文, 参数2 Provider主机地址 和配置文件中保持一致   参数3  共享的文件
            Uri apkUri = FileProvider.getUriForFile(context, Constants.FILE_PATH.FILE_PROVIDER_AUTHORITIES, file);
            //添加这一句表示对目标应用临时授权该Uri所代表的文件
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
        } else {
            intent.setDataAndType(Uri.fromFile(new File(apkPath)),
                    "application/vnd.android.package-archive");
        }
       return intent;
    }





}
