package com.tuodanhuashu.app.user.ui;

import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.modelmsg.WXTextObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.tuodanhuashu.app.Constants.Constants;
import com.tuodanhuashu.app.R;
import com.tuodanhuashu.app.base.HuaShuBaseActivity;
import com.tuodanhuashu.app.eventbus.EventMessage;
import com.tuodanhuashu.app.home.ui.HomeActivity;
import com.tuodanhuashu.app.user.presenter.LoginPresenter;
import com.tuodanhuashu.app.user.view.ILoginView;
import com.tuodanhuashu.app.wxapi.WeChatRequestListener;
import com.tuodanhuashu.app.wxapi.WeChatUtils;
import com.tuodanhuashu.app.wxapi.bean.WeChatUserInfoBean;

import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends HuaShuBaseActivity implements ILoginView {


    @BindView(R.id.user_head_back_iv)
    ImageView userHeadBackIv;
    @BindView(R.id.user_head_title_tv)
    TextView userHeadTitleTv;
    @BindView(R.id.user_head_right_tv)
    TextView userHeadRightTv;
    @BindView(R.id.login_pwd_iv)
    ImageView loginPwdIv;
    @BindView(R.id.login_switch_pwd_iv)
    ImageView loginSwitchPwdIv;
    @BindView(R.id.login_message_login_tv)
    TextView loginMessageLoginTv;
    @BindView(R.id.login_forget_pwd_tv)
    TextView loginForgetPwdTv;
    @BindView(R.id.login_pwd_et)
    EditText loginPwdEt;
    @BindView(R.id.login_btn)
    Button loginBtn;
    @BindView(R.id.login_mobile_et)
    EditText loginMobileEt;
    @BindView(R.id.login_wechat)
    ImageView loginWechat;

    private LoginPresenter loginPresenter;


    private boolean isPwdVisible = false;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {
        super.initView();
        userHeadTitleTv.setText("登 录");
        loginPwdEt.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD | InputType.TYPE_CLASS_TEXT);
    }


    @Override
    protected void initData() {
        super.initData();
        loginPresenter = new LoginPresenter(this, mContext);
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

        loginMessageLoginTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                readyGo(PhoneNumberLoginActivity.class);
            }
        });

        loginForgetPwdTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                readyGo(ForgetPasswordActivity.class);
            }
        });

        loginSwitchPwdIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isPwdVisible = !isPwdVisible;
                loginSwitchPwdIv.setImageResource(isPwdVisible ? R.drawable.user_show_pwd : R.drawable.user_hide_pwd);
                loginPwdEt.setInputType(isPwdVisible ? InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD : InputType.TYPE_TEXT_VARIATION_PASSWORD | InputType.TYPE_CLASS_TEXT);
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mobile = loginMobileEt.getText().toString();
                String pwd = loginPwdEt.getText().toString();
                loginPresenter.requestLogin(mobile, pwd);
            }
        });
        loginWechat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IWXAPI api = WXAPIFactory.createWXAPI(mContext, Constants.WEIXIN.WX_APP_ID);
                api.registerApp(Constants.WEIXIN.WX_APP_ID);


                SendAuth.Req req = new SendAuth.Req();
                req.scope = "snsapi_userinfo";

                req.state = "wechat_sdk_demo_test";
//                req.openId = Constants.WEIXIN.WX_APP_ID;
                req.openId = "123";
                api.sendReq(req);
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
        showToast(msg);
    }

    @Override
    protected boolean isEnableDoubleClickExit() {
        return false;
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
                        loginPresenter.requestWXLogin(rsp);
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
