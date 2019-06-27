package com.tuodanhuashu.app.user.ui;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.company.common.utils.StringUtils;
import com.tuodanhuashu.app.Constants.Constants;
import com.tuodanhuashu.app.R;
import com.tuodanhuashu.app.base.HuaShuBaseActivity;
import com.tuodanhuashu.app.eventbus.EventMessage;
import com.tuodanhuashu.app.home.ui.HomeActivity;
import com.tuodanhuashu.app.user.presenter.LoginByPhonePresenter;
import com.tuodanhuashu.app.user.view.LoginByPhoneView;
import com.tuodanhuashu.app.wxapi.WeChatRequestListener;
import com.tuodanhuashu.app.wxapi.WeChatUtils;
import com.tuodanhuashu.app.wxapi.bean.WeChatUserInfoBean;

import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PhoneNumberLoginActivity extends HuaShuBaseActivity implements LoginByPhoneView {

    @BindView(R.id.user_head_back_iv)
    ImageView userHeadBackIv;
    @BindView(R.id.user_head_title_tv)
    TextView userHeadTitleTv;
    @BindView(R.id.user_head_right_tv)
    TextView userHeadRightTv;
    @BindView(R.id.login_pwd_iv)
    ImageView loginPwdIv;
    @BindView(R.id.login_get_msg_tv)
    TextView loginGetMsgTv;
    @BindView(R.id.login_message_login_tv)
    TextView loginMessageLoginTv;
    @BindView(R.id.login_forget_pwd_tv)
    TextView loginForgetPwdTv;
    @BindView(R.id.login_by_phone_btn)
    Button loginByPhoneBtn;
    @BindView(R.id.login_by_phone_mobile_et)
    EditText loginByPhoneMobileEt;
    @BindView(R.id.login_by_phone_yzcode_et)
    EditText loginByPhoneYzcodeEt;

    private LoginByPhonePresenter loginByPhonePresenter;

    private int downCount = 60;

    SMSCountDownTask countDownTask;

    private CountDownHandler countDownHandler;

    private Timer timer;

    private boolean isReceivedSMS = false;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_phone_number_login;
    }

    @Override
    protected void initView() {
        super.initView();
        userHeadTitleTv.setText("短信登录");
    }

    @Override
    protected void initData() {
        super.initData();
        loginByPhonePresenter = new LoginByPhonePresenter(mContext, this);
        countDownHandler = new CountDownHandler();
        timer = new Timer();
        countDownTask = new SMSCountDownTask();
    }

    @Override
    protected void bindListener() {
        super.bindListener();
        userHeadBackIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        userHeadRightTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                readyGo(RegisterActivity.class);
            }
        });

        loginForgetPwdTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                readyGo(ForgetPasswordActivity.class);
            }
        });
        loginGetMsgTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isReceivedSMS) {
                    String mobile = loginByPhoneMobileEt.getText().toString();
                    if (!StringUtils.isEmpty(mobile)) {
                        loginByPhonePresenter.sendSMS(mobile);
                    } else {
                        showToast("手机号不能为空");
                    }
                }
            }
        });
        loginByPhoneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mobile = loginByPhoneMobileEt.getText().toString();
                String yzcode = loginByPhoneYzcodeEt.getText().toString();
                if (StringUtils.isEmpty(mobile)) {
                    showToast("请输入手机号");
                    return;
                }
                if (StringUtils.isEmpty(yzcode)) {
                    showToast("请输入验证码");
                    return;
                }
                loginByPhonePresenter.loginByPhone(mobile, yzcode);
            }
        });
    }

    @Override
    public void onLoginSuccess() {
        showToast("登录成功");
        readyGoThenKill(HomeActivity.class);
    }

    @Override
    public void onLiginFail(String msg) {

    }

    @Override
    public void getSMSSuccess(String msg) {
        switchButtonState();
        timer = new Timer();
        countDownTask = new SMSCountDownTask();
        timer.schedule(countDownTask, 1000, 1000);
    }

    @Override
    public void getSMSFail(String msg) {

    }

    private void switchButtonState() {
        isReceivedSMS = !isReceivedSMS;
        loginGetMsgTv.setTextColor(getResources().getColor(isReceivedSMS ? R.color.color_unselected : R.color.colorAccent));
        loginGetMsgTv.setText(isReceivedSMS ? "60秒" : "发送验证码");
    }


    class SMSCountDownTask extends TimerTask {

        @Override
        public void run() {
            Message msg = Message.obtain();
            msg.what = 1;
            countDownHandler.sendMessage(msg);
        }
    }


    private class CountDownHandler extends Handler {


        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            downCount--;
            loginGetMsgTv.setText(downCount + "秒");
            if (downCount == 0 && timer != null) {
                timer.cancel();
                downCount = 60;
                switchButtonState();
            }
        }
    }

    @Override
    protected void onDestroy() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
        if (countDownHandler != null) {
            countDownHandler = null;
        }
        super.onDestroy();

    }

    @Override
    public void onEvent(EventMessage message) {
        super.onEvent(message);
        switch (message.getTag()) {
            case Constants.EVENT_TAG.TAG_WX_AUTH_SUCCESS:
                Map<String, String> params = (Map<String, String>) message.getData();
                WeChatUtils weChatUtils = new WeChatUtils(mContext, new WeChatRequestListener() {
                    @Override
                    public void OnWeChatSuccess(WeChatUserInfoBean rsp) {
                        loginByPhonePresenter.requestWxLogin(rsp);
                    }

                    @Override
                    public void OnWeChatFail(String msg) {
                        showToast(msg);
                    }

                    @Override
                    public void OnError() {

                    }
                });
                weChatUtils.requestWechatToken(1, params);
                break;
        }
    }
}