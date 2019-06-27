package com.tuodanhuashu.app.user.ui;

import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.company.common.utils.PermissionsUtils;
import com.company.common.utils.PreferencesUtils;
import com.tuodanhuashu.app.Constants.Constants;
import com.tuodanhuashu.app.R;
import com.tuodanhuashu.app.base.HuaShuBaseActivity;
import com.tuodanhuashu.app.home.ui.HomeActivity;

public class SplashActivity extends HuaShuBaseActivity {

    private SplashHandler handler;

    private SplashThread thread;

    PermissionsUtils.IPermissionsResult permissionsResult = new PermissionsUtils.IPermissionsResult() {
        @Override
        public void passPermissons() {
            Log.e("PermissionsResult","权限都有了");
            handler = new SplashHandler();
            thread = new SplashThread();
            thread.start();


        }

        @Override
        public void forbitPermissons() {
            showToast("禁用该权限可能导致此APP无法使用");
            finish();
        }

        @Override
        public void permissionGranted() {
            handler = new SplashHandler();
            thread = new SplashThread();
            thread.start();
        }
    };

    @Override
    protected int getContentViewLayoutID() {

        return R.layout.activity_splash;
    }

    @Override
    protected void initView() {
        super.initView();

        PermissionsUtils.getInstance().chekPermissions(this, Constants.permissions,permissionsResult);

    }

    class SplashThread extends Thread{
        @Override
        public void run() {
            super.run();
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                handler.sendMessage(Message.obtain());
            }

        }
    }

    class SplashHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            readyGoThenKill(HomeActivity.class);
        }
    }

    @Override
    protected void onDestroy() {
        handler.removeCallbacks(thread);
        handler = null;
        super.onDestroy();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //就多一个参数this
        PermissionsUtils.getInstance().onRequestPermissionsResult(this, requestCode, permissions, grantResults);
    }
}
