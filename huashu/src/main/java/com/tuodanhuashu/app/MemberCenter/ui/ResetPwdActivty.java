package com.tuodanhuashu.app.MemberCenter.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.company.common.net.OkNetUtils;
import com.company.common.net.OnRequestListener;
import com.company.common.net.ServerResponse;
import com.company.common.utils.StringUtils;
import com.tuodanhuashu.app.Constants.Constants;
import com.tuodanhuashu.app.MemberCenter.biz.MemberCenterBiz;
import com.tuodanhuashu.app.R;
import com.tuodanhuashu.app.base.HuaShuBaseActivity;
import com.tuodanhuashu.app.user.biz.RegisterBiz;

import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ResetPwdActivty extends HuaShuBaseActivity implements OnRequestListener {

    @BindView(R.id.common_head_back_iv)
    ImageView commonHeadBackIv;
    @BindView(R.id.common_head_title_tv)
    TextView commonHeadTitleTv;
    @BindView(R.id.reset_pwd_btn)
    Button resetPwdBtn;
    @BindView(R.id.old_pwd_et)
    EditText oldPwdEt;
    @BindView(R.id.new_pwd_et)
    EditText newPwdEt;
    @BindView(R.id.confirm_new_pwd_et)
    EditText confirmNewPwdEt;

    private MemberCenterBiz memberCenterBiz;

    private static final int TAG_RESET_PWD = 1;





    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_reset_pwd_activty;
    }

    @Override
    protected void initView() {
        super.initView();
        commonHeadTitleTv.setText("修改密码");
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
        memberCenterBiz = new MemberCenterBiz(this,mContext);

    }

    @Override
    protected void bindListener() {
        super.bindListener();
        resetPwdBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String oldpwd = oldPwdEt.getText().toString();
                String newpwd = newPwdEt.getText().toString();
                String confirmpwd = confirmNewPwdEt.getText().toString();
                if(StringUtils.isEmpty(oldpwd)){
                    showToast("请输入旧密码");
                    return;
                }
                if(StringUtils.isEmpty(newpwd)){
                    showToast("请输入新密码");
                    return;
                }
                if(StringUtils.isEmpty(confirmpwd)){
                    showToast("请确认新密码");
                    return;
                }
                if(!newpwd.equals(confirmpwd)){
                    showToast("两次密码输入不一致");
                    return;
                }
                if(!Pattern.matches(Constants.REG.REG_PWD,newpwd)){
                    showToast("请输入6-12位包含大小写字母和数字的密码!");
                    return;
                }
                if(!Pattern.matches(Constants.REG.REG_PWD,confirmpwd)){
                    showToast("请输入6-12位包含大小写字母和数字的密码!");
                    return;
                }
                memberCenterBiz.resetPwd(TAG_RESET_PWD,oldpwd,newpwd,confirmpwd);
            }
        });
    }

    @Override
    public void OnSuccess(ServerResponse serverResponse, int tag) {
        showToast(serverResponse.getMsg());
        onBackPressed();
    }

    @Override
    public void onFail(String msg, int code, int tag) {

    }

    @Override
    public void onError(int tag) {

    }


}
