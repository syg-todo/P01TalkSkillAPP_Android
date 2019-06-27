package com.tuodanhuashu.app.user.ui;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.company.common.utils.StringUtils;
import com.tuodanhuashu.app.Constants.Constants;
import com.tuodanhuashu.app.R;
import com.tuodanhuashu.app.base.HuaShuBaseActivity;
import com.tuodanhuashu.app.user.presenter.ForgetPwdPresenter;
import com.tuodanhuashu.app.user.view.ForgetPwdView;

import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ForgetPasswordActivity extends HuaShuBaseActivity implements ForgetPwdView {

    @BindView(R.id.user_head_back_iv)
    ImageView userHeadBackIv;
    @BindView(R.id.user_head_title_tv)
    TextView userHeadTitleTv;
    @BindView(R.id.user_head_right_tv)
    TextView userHeadRightTv;
    @BindView(R.id.forget_pwd_code_iv)
    ImageView forgetPwdCodeIv;
    @BindView(R.id.forget_pwd_get_msg_tv)
    TextView forgetPwdGetMsgTv;
    @BindView(R.id.forget_pwd_pwd_iv)
    ImageView forgetPwdPwdIv;
    @BindView(R.id.forget_pwd_switch_pwd_iv)
    ImageView forgetPwdSwitchPwdIv;
    @BindView(R.id.forget_pwd_pwd_et)
    EditText forgetPwdPwdEt;
    @BindView(R.id.forget_confirm_pwd_et)
    EditText forgetConfirmPwdEt;
    @BindView(R.id.forget_confirm_switch_pwd_iv)
    ImageView forgetConfirmSwitchPwdIv;
    @BindView(R.id.forget_pwd_mobile_iv)
    EditText forgetPwdMobileIv;
    @BindView(R.id.forget_confirm_pwd_iv)
    ImageView forgetConfirmPwdIv;
    @BindView(R.id.forget_pwd_submit_btn)
    Button forgetPwdSubmitBtn;
    @BindView(R.id.forget_pwd_code_et)
    EditText forgetPwdCodeEt;

    private ForgetPwdPresenter forgetPwdPresenter;

    private int downCount = 60;

    SMSCountDownTask countDownTask;

    private CountDownHandler countDownHandler;

    private Timer timer;

    private boolean isReceivedSMS = false;

    private boolean isPwdVisible = false;

    private boolean isConfirmVisible = false;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_forget_password;
    }


    @Override
    protected void initView() {
        super.initView();
        userHeadTitleTv.setText("找回密码");
        userHeadRightTv.setVisibility(View.GONE);
        forgetPwdPwdEt.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD | InputType.TYPE_CLASS_TEXT);
        forgetConfirmPwdEt.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD | InputType.TYPE_CLASS_TEXT);

    }


    @Override
    protected void initData() {
        super.initData();
        forgetPwdPresenter = new ForgetPwdPresenter(mContext, this);
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
        forgetPwdSwitchPwdIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isPwdVisible = !isPwdVisible;
                forgetPwdSwitchPwdIv.setImageResource(isPwdVisible ? R.drawable.user_show_pwd : R.drawable.user_hide_pwd);
                forgetPwdPwdEt.setInputType(isPwdVisible ? InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD : InputType.TYPE_TEXT_VARIATION_PASSWORD | InputType.TYPE_CLASS_TEXT);
            }
        });
        forgetConfirmSwitchPwdIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isConfirmVisible = !isConfirmVisible;
                forgetConfirmSwitchPwdIv.setImageResource(isConfirmVisible ? R.drawable.user_show_pwd : R.drawable.user_hide_pwd);
                forgetConfirmPwdEt.setInputType(isConfirmVisible ? InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD : InputType.TYPE_TEXT_VARIATION_PASSWORD | InputType.TYPE_CLASS_TEXT);
            }
        });
        forgetPwdGetMsgTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isReceivedSMS) {
                    String mobile = forgetPwdMobileIv.getText().toString();
                    if (!StringUtils.isEmpty(mobile)) {
                        forgetPwdPresenter.sendSMS(mobile);
                    } else {
                        showToast("手机号不能为空");
                    }
                }
            }
        });
        forgetPwdSubmitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mobile = forgetPwdMobileIv.getText().toString();
                String yzcode = forgetPwdCodeEt.getText().toString();
                String pwd1 = forgetPwdPwdEt.getText().toString();
                String pwd2 = forgetConfirmPwdEt.getText().toString();
                if(StringUtils.isEmpty(mobile)){
                    showToast("手机号不能为空");
                    return;
                }

                if(StringUtils.isEmpty(yzcode)){
                    showToast("验证码不能为空");
                    return;
                }
                if(StringUtils.isEmpty(pwd1)){
                    showToast("请输入新密码");
                    return;
                }
                if(StringUtils.isEmpty(mobile)){
                    showToast("请确认新密码");
                    return;
                }
                if(!pwd1.equals(pwd2)){
                    showToast("两次密码输入不一致");
                    return;
                }
                if(!Pattern.matches(Constants.REG.REG_PWD,pwd1)){
                    showToast("请输入6-12位包含大小写字母和数字的密码!");
                    return;
                }
                if(!Pattern.matches(Constants.REG.REG_PWD,pwd2)){
                    showToast("请输入6-12位包含大小写字母和数字的密码!");
                    return;
                }
                forgetPwdPresenter.resetPwd(mobile,yzcode,pwd1,pwd2);
            }
        });
    }

    private void switchButtonState() {
        isReceivedSMS = !isReceivedSMS;
        forgetPwdGetMsgTv.setTextColor(getResources().getColor(isReceivedSMS ? R.color.color_unselected : R.color.colorAccent));
        forgetPwdGetMsgTv.setText(isReceivedSMS ? "60秒" : "发送验证码");
    }

    @Override
    public void resetPwdSuccess(String msg) {
        showToast(msg);
        onBackPressed();
    }

    @Override
    public void resetPwdFail(String msg) {
        showToast(msg);
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
            forgetPwdGetMsgTv.setText(downCount + "秒");
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
