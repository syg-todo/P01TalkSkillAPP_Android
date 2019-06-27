package com.tuodanhuashu.app.MemberCenter.ui;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.company.common.CommonConstants;
import com.company.common.net.OnRequestListener;
import com.company.common.net.ServerResponse;
import com.company.common.utils.DeviceUtils;
import com.company.common.utils.PreferencesUtils;
import com.company.common.utils.StringUtils;
import com.tuodanhuashu.app.Constants.Constants;
import com.tuodanhuashu.app.MemberCenter.biz.MemberCenterBiz;
import com.tuodanhuashu.app.R;
import com.tuodanhuashu.app.base.HuaShuBaseActivity;
import com.tuodanhuashu.app.user.biz.RegisterBiz;

import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ResetPhoneNumberActivity extends HuaShuBaseActivity implements OnRequestListener {


    @BindView(R.id.common_head_back_iv)
    ImageView commonHeadBackIv;
    @BindView(R.id.common_head_title_tv)
    TextView commonHeadTitleTv;
    @BindView(R.id.reset_pwd_send_sms_tv)
    TextView resetPwdSendSmsTv;
    @BindView(R.id.reset_pwd_yzcode_et)
    EditText resetPwdYzcodeEt;
    @BindView(R.id.reset_pwd_btn)
    Button resetPwdBtn;
    @BindView(R.id.mobile_et)
    EditText mobileEt;
    @BindView(R.id.reset_mobile_pwd_et)
    EditText resetMobilePwdEt;


    private CountDownHandler countDownHandler;

    private MemberCenterBiz memberCenterBiz;

    private static final int TAG_RESET_MOBILE = 1;

    private static final int TAG_SEND_SMS = 2;

    private int downCount = 60;

    private RegisterBiz registerBiz;

    private Timer timer;

    SMSCountDownTask countDownTask;

    private boolean isReceivedSMS = false;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_reset_phone_number;
    }

    @Override
    protected void initView() {
        super.initView();
        String mobile = PreferencesUtils.getString(mContext, CommonConstants.KEY_MOBILE, "");
        if (StringUtils.isEmpty(mobile)) {
            commonHeadTitleTv.setText("绑定手机号码");
        } else {
            commonHeadTitleTv.setText("修改手机号码");
        }

        commonHeadBackIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    protected void initData() {
        super.initData();
        countDownHandler = new CountDownHandler();
        registerBiz = new RegisterBiz(this, mContext);
        memberCenterBiz = new MemberCenterBiz(this, mContext);
        timer = new Timer();
        countDownTask = new SMSCountDownTask();
    }

    @Override
    protected void bindListener() {
        super.bindListener();
        resetPwdSendSmsTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isReceivedSMS) {
                    String mobile = mobileEt.getText().toString();
                    if (!StringUtils.isEmpty(mobile)) {
                        registerBiz.requestSMS(TAG_SEND_SMS, mobile, DeviceUtils.getUniqueId(mContext));
                    } else {
                        showToast("手机号不能为空");
                    }
                }
            }
        });

        resetPwdBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mobile = mobileEt.getText().toString();
                String yzcode = resetPwdYzcodeEt.getText().toString();
                String pwd = resetMobilePwdEt.getText().toString();
                if (StringUtils.isEmpty(mobile)) {
                    showToast("请输入手机号码");
                    return;
                }
                if (StringUtils.isEmpty(yzcode)) {
                    showToast("请输入验证码");
                    return;
                }
                if(StringUtils.isEmpty(pwd)){
                    showToast("请输入密码");
                    return;
                }
                if(!Pattern.matches(Constants.REG.REG_PWD,pwd)){
                    showToast("请输入6-12位包含大小写字母和数字的密码!");
                    return;
                }
                memberCenterBiz.resetMobile(TAG_RESET_MOBILE, mobile, yzcode,pwd);
            }
        });
    }

    @Override
    public void OnSuccess(ServerResponse serverResponse, int tag) {
        switch (tag) {
            case TAG_SEND_SMS:
                switchButtonState();
                timer = new Timer();
                countDownTask = new SMSCountDownTask();
                timer.schedule(countDownTask, 1000, 1000);
                break;
            case TAG_RESET_MOBILE:
                showToast(serverResponse.getMsg());
                onBackPressed();
                break;
        }
    }

    private void switchButtonState() {
        isReceivedSMS = !isReceivedSMS;
        resetPwdSendSmsTv.setTextColor(getResources().getColor(isReceivedSMS ? R.color.color_unselected : R.color.colorAccent));
        resetPwdSendSmsTv.setText(isReceivedSMS ? "60秒" : "发送验证码");
    }

    @Override
    public void onFail(String msg, int code, int tag) {
        showToast(msg);
    }

    @Override
    public void onError(int tag) {

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
            resetPwdSendSmsTv.setText(downCount + "秒");
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
}
