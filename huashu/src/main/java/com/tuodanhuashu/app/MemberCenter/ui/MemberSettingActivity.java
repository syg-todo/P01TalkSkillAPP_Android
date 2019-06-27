package com.tuodanhuashu.app.MemberCenter.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.company.common.CommonConstants;
import com.company.common.net.OkNetUtils;
import com.company.common.net.OnRequestListener;
import com.company.common.net.ServerResponse;
import com.company.common.utils.PreferencesUtils;
import com.tuodanhuashu.app.Constants.Constants;
import com.tuodanhuashu.app.MemberCenter.presenter.MemberCenterPresenter;
import com.tuodanhuashu.app.R;
import com.tuodanhuashu.app.base.HuaShuBaseActivity;
import com.tuodanhuashu.app.user.ui.LoginActivity;
import com.tuodanhuashu.app.web.CommonWebActivity;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MemberSettingActivity extends HuaShuBaseActivity implements OnRequestListener {

    @BindView(R.id.common_head_back_iv)
    ImageView commonHeadBackIv;
    @BindView(R.id.common_head_title_tv)
    TextView commonHeadTitleTv;
    @BindView(R.id.logout_btn)
    Button logoutBtn;
    @BindView(R.id.update_profile_rl)
    RelativeLayout updateProfileRl;
    @BindView(R.id.update_pwd_rl)
    RelativeLayout updatePwdRl;
    @BindView(R.id.update_mobile_rl)
    RelativeLayout updateMobileRl;

    private static final int TAG_ANBOUT_US = 1;
    @BindView(R.id.setting_about_us_rl)
    RelativeLayout settingAboutUsRl;

    private MemberCenterPresenter memberCenterPresenter;


    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_member_setting;
    }

    @Override
    protected void initData() {
        super.initData();

    }

    @Override
    protected void initView() {
        super.initView();
        commonHeadTitleTv.setText("设 置");
        commonHeadBackIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }


    @Override
    protected void bindListener() {
        super.bindListener();
        updateProfileRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readyGo(UserProfileActivity.class);
            }
        });
        updatePwdRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readyGo(ResetPwdActivty.class);
            }
        });
        updateMobileRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readyGo(ResetPhoneNumberActivity.class);
            }
        });
        settingAboutUsRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String,String> params = new HashMap<>();
                OkNetUtils.get(TAG_ANBOUT_US,Constants.URL.BASE_URL+Constants.URL.ABOUT_US_URL,params,mContext,MemberSettingActivity.this);
            }
        });
        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PreferencesUtils.putString(mContext, CommonConstants.KEY_ACCOUNT_ID, "");
                PreferencesUtils.putString(mContext, CommonConstants.KEY_TOKEN, "");
                readyGoThenKill(LoginActivity.class);
            }
        });
    }


    @Override
    public void OnSuccess(ServerResponse serverResponse, int tag) {
        switch (tag) {
            case TAG_ANBOUT_US:
                String url = serverResponse.getData();
                Bundle bundle = new Bundle();
                bundle.putString(CommonWebActivity.EXTRA_WV_TITLE,"关于我们");
                bundle.putString(CommonWebActivity.EXTRA_WV_DATA, url);
                readyGo(CommonWebActivity.class,bundle);
                break;
        }
    }

    @Override
    public void onFail(String msg, int code, int tag) {

    }

    @Override
    public void onError(int tag) {

    }


}
