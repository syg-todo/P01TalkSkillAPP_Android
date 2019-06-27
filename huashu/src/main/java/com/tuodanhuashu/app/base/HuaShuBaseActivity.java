package com.tuodanhuashu.app.base;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;

import com.company.common.CommonConstants;
import com.company.common.base.BaseActivity;
import com.company.common.utils.MD5Utils;
import com.company.common.utils.PreferencesUtils;
import com.company.common.utils.StatusBarUtil;
import com.company.common.utils.StringUtils;
import com.hyphenate.chat.ChatClient;
import com.hyphenate.helpdesk.callback.Callback;
import com.hyphenate.helpdesk.easeui.util.IntentBuilder;
import com.tuodanhuashu.app.Constants.Constants;
import com.tuodanhuashu.app.R;
import com.tuodanhuashu.app.eventbus.EventMessage;
import com.tuodanhuashu.app.user.ui.LoginActivity;
import com.tuodanhuashu.app.widget.LoadingDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public class HuaShuBaseActivity extends BaseActivity {


    private Unbinder unbinder;

    private LoadingDialog loadingDialog;

    /**
     * exit time
     */
    private long mExitTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        //当FitsSystemWindows设置 true 时，会在屏幕最上方预留出状态栏高度的 padding
        StatusBarUtil.setRootViewFitsSystemWindows(this, true);
        //设置状态栏透明
        //StatusBarUtil.setTranslucentStatus(this);
        //一般的手机的状态栏文字和图标都是白色的, 可如果你的应用也是纯白色的, 或导致状态栏文字看不清
        //所以如果你是这种情况,请使用以下代码, 设置状态使用深色文字图标风格, 否则你可以选择性注释掉这个if内容
        if (!StatusBarUtil.setStatusBarDarkTheme(this, true)) {
            //如果不支持设置深色风格 为了兼容总不能让状态栏白白的看不清, 于是设置一个状态栏颜色为半透明,
            //这样半透明+白=灰, 状态栏的文字能看得清

            StatusBarUtil.setStatusBarColor(this, R.color.black);
        }
    }

    @Override
    protected int getRootLayoutId() {
        return 0;
    }

    @Override
    protected int getEmptyLayoutResId() {
        return 0;
    }

    @Override
    protected int getErrorLayoutResId() {
        return 0;
    }

    @Override
    protected int getLoadingLayoutResId() {
        return R.layout.loading_layout;
    }


    @Override
    protected int getContentViewLayoutID() {
        return 0;
    }

    @Override
    protected void initView() {
        unbinder = ButterKnife.bind(this);
        loadingDialog = new LoadingDialog(mContext);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void bindListener() {

    }

    @Override
    protected void getBundleExtras(Bundle extras) {

    }

    @Override
    protected boolean isEnableDoubleClickExit() {
        return false;
    }

    @Override
    protected void onDestroy() {
        if (loadingDialog.isShowing()) {
            loadingDialog.dismiss();
        }
        loadingDialog = null;
        super.onDestroy();
        unbinder.unbind();
        EventBus.getDefault().unregister(this);
    }


    @Override
    public void showLoadingDialog() {
        if (loadingDialog != null && !loadingDialog.isShowing()) {
            loadingDialog.show();
        }
    }

    @Override
    public void cancalLoadingDialog() {
        if (loadingDialog != null && loadingDialog.isShowing()) {
            loadingDialog.dismiss();
        }
    }

    protected boolean isLogin() {
        String token = PreferencesUtils.getString(mContext, CommonConstants.KEY_TOKEN, "");

        return !StringUtils.isEmpty(token);
    }

    protected boolean isWxLogin() {
        if (!isLogin()) {
            return false;
        } else {
            String openid = PreferencesUtils.getString(mContext, CommonConstants.KEY_WXOPEN_ID, "");
            return !StringUtils.isEmpty(openid);
        }
    }


    protected void goToLogin() {
        PreferencesUtils.putString(mContext, CommonConstants.KEY_ACCOUNT_ID, "");
        PreferencesUtils.putString(mContext, CommonConstants.KEY_TOKEN, "");
        readyGoClearTop(LoginActivity.class);
    }

    public void goToIm() {
        String accountId = PreferencesUtils.getString(mContext, CommonConstants.KEY_ACCOUNT_ID, "");
        String md5AccoundId = MD5Utils.MD5Encode(accountId, "utf-8");
        ChatClient.getInstance().login(accountId, md5AccoundId, new Callback() {
            @Override
            public void onSuccess() {
                Intent intent = new IntentBuilder(mContext)
                        .setServiceIMNumber(Constants.IM.IM_SERVICE_ID)
                        .build();
                startActivity(intent);
            }

            @Override
            public void onError(int i, String s) {

            }

            @Override
            public void onProgress(int i, String s) {

            }
        });

    }

    /**
     * 声明订阅方法：当接收到事件的时候，会调用该方法
     * 使用@Subscribe注解
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(EventMessage message) {
        Log.d("eventBusThread", "ThreadMode.MAIN " + Thread.currentThread().getName());
        if (message.getTag() == Constants.EVENT_TAG.TAG_FINISH) {
            finish();
        }
    }


    @Override
    public void onBackPressed() {
        if (isEnableDoubleClickExit()) {
            long now = System.currentTimeMillis();
            long dif = now - mExitTime;
            if (dif < 2000) {
                EventBus.getDefault().post(new EventMessage(Constants.EVENT_TAG.TAG_FINISH, null));
            } else {
                mExitTime = now;
                showToast("");
            }
        } else {
            finish();
        }

    }
}
