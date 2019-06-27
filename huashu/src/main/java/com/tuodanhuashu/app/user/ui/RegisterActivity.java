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

import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.tuodanhuashu.app.Constants.Constants;
import com.tuodanhuashu.app.R;
import com.tuodanhuashu.app.base.HuaShuBaseActivity;
import com.tuodanhuashu.app.eventbus.EventMessage;
import com.tuodanhuashu.app.home.ui.HomeActivity;
import com.tuodanhuashu.app.user.presenter.RegisterPresenter;
import com.tuodanhuashu.app.user.view.IRegisterView;
import com.tuodanhuashu.app.web.CommonWebActivity;
import com.tuodanhuashu.app.wxapi.WeChatRequestListener;
import com.tuodanhuashu.app.wxapi.WeChatUtils;
import com.tuodanhuashu.app.wxapi.bean.WeChatUserInfoBean;

import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RegisterActivity extends HuaShuBaseActivity implements IRegisterView {

    @BindView(R.id.user_head_back_iv)
    ImageView userHeadBackIv;
    @BindView(R.id.user_head_title_tv)
    TextView userHeadTitleTv;
    @BindView(R.id.user_head_right_tv)
    TextView userHeadRightTv;
    @BindView(R.id.register_code_iv)
    ImageView registerCodeIv;
    @BindView(R.id.register_get_msg_tv)
    TextView registerGetMsgTv;
    @BindView(R.id.register_pwd_iv)
    ImageView registerPwdIv;
    @BindView(R.id.register_switch_pwd_iv)
    ImageView registerSwitchPwdIv;
    @BindView(R.id.register_protocal_tv)
    TextView registerProtocalTv;
    @BindView(R.id.register_pwd_et)
    EditText registerPwdEt;
    @BindView(R.id.register_mobile_et)
    EditText registerMobileEt;
    @BindView(R.id.register_btn)
    Button registerBtn;
    @BindView(R.id.register_yzcode_et)
    EditText registerYzcodeEt;
    @BindView(R.id.register_wechat)
    ImageView registerWechat;

    private boolean isPwdVisible = false;

    private RegisterPresenter registerPresenter;

    private int downCount = 60;

    SMSCountDownTask countDownTask;

    private CountDownHandler countDownHandler;

    private Timer timer;

    private boolean isReceivedSMS = false;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_register;
    }

    @Override
    protected void initView() {
        super.initView();
        userHeadTitleTv.setText("注 册");
        userHeadRightTv.setVisibility(View.GONE);
        registerPwdEt.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD | InputType.TYPE_CLASS_TEXT);
    }

    @Override
    protected void initData() {
        super.initData();
        countDownHandler = new CountDownHandler();
        timer = new Timer();
        countDownTask = new SMSCountDownTask();
    }

    @Override
    protected void bindListener() {
        super.bindListener();
        registerPresenter = new RegisterPresenter(this, mContext);
        userHeadBackIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        registerSwitchPwdIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isPwdVisible = !isPwdVisible;
                registerSwitchPwdIv.setImageResource(isPwdVisible ? R.drawable.user_show_pwd : R.drawable.user_hide_pwd);
                registerPwdEt.setInputType(isPwdVisible ? InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD : InputType.TYPE_TEXT_VARIATION_PASSWORD | InputType.TYPE_CLASS_TEXT);
            }
        });

        registerGetMsgTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mobile = registerMobileEt.getText().toString();
                registerPresenter.requestSMS(mobile);
            }
        });

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mobile = registerMobileEt.getText().toString();
                String password = registerPwdEt.getText().toString();
                String yzcode = registerYzcodeEt.getText().toString();
                registerPresenter.requestUserRegister(mobile, password, yzcode);
            }
        });
        registerWechat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IWXAPI api = WXAPIFactory.createWXAPI(mContext, Constants.WEIXIN.WX_APP_ID);
                api.registerApp(Constants.WEIXIN.WX_APP_ID);
                SendAuth.Req req = new SendAuth.Req();
                req.scope = "snsapi_userinfo";
                req.state = "wechat_sdk_demo_test";
                req.openId = "123";
                api.sendReq(req);
            }
        });
        registerProtocalTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerPresenter.requestMessageUrl();
            }
        });
    }


    @Override
    public void onRegisterSuccess() {
        showToast("注册成功");
        readyGoThenKill(HomeActivity.class);
    }

    @Override
    public void onRegisterFail(String msg) {
        showToast(msg);
    }

    @Override
    public void onReceiveMsgSuccess() {
        switchButtonState();
        timer = new Timer();
        countDownTask = new SMSCountDownTask();
        timer.schedule(countDownTask, 1000, 1000);
    }

    @Override
    public void onReceiveMsgFail(String msg) {
        showToast(msg);
    }

    @Override
    public void getMessageUrlSuccess(String data) {
        Bundle bundle = new Bundle();
        bundle.putString(CommonWebActivity.EXTRA_WV_TITLE,"用户注册协议");
        bundle.putString(CommonWebActivity.EXTRA_WV_DATA, data);
        readyGo(CommonWebActivity.class,bundle);
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
            registerGetMsgTv.setText(downCount + "秒");
            if (downCount == 0 && timer != null) {
                timer.cancel();
                downCount = 60;
                switchButtonState();
            }
        }
    }

    private void switchButtonState() {
        isReceivedSMS = !isReceivedSMS;
        registerGetMsgTv.setTextColor(getResources().getColor(isReceivedSMS ? R.color.color_unselected : R.color.colorAccent));
        registerGetMsgTv.setText(isReceivedSMS ? "60秒" : "发送验证码");
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
        switch (message.getTag()){
            case Constants.EVENT_TAG.TAG_WX_AUTH_SUCCESS:
                Map<String,String> params = (Map<String, String>) message.getData();
                WeChatUtils weChatUtils = new WeChatUtils(mContext, new WeChatRequestListener() {
                    @Override
                    public void OnWeChatSuccess(WeChatUserInfoBean rsp) {
                        registerPresenter.requestWXLogin(rsp);
                    }

                    @Override
                    public void OnWeChatFail(String msg) {
                        showToast(msg);
                    }

                    @Override
                    public void OnError() {

                    }
                });
                weChatUtils.requestWechatToken(1,params);
                break;
        }
    }
}
