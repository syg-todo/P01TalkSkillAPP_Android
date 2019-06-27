package com.tuodanhuashu.app.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.company.common.CommonConstants;
import com.company.common.base.BaseFragment;
import com.company.common.utils.MD5Utils;
import com.company.common.utils.PreferencesUtils;
import com.company.common.utils.StringUtils;
import com.hyphenate.chat.ChatClient;
import com.hyphenate.helpdesk.callback.Callback;
import com.hyphenate.helpdesk.easeui.util.IntentBuilder;
import com.tuodanhuashu.app.Constants.Constants;
import com.tuodanhuashu.app.R;
import com.tuodanhuashu.app.user.ui.LoginActivity;
import com.tuodanhuashu.app.widget.LoadingDialog;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public class HuaShuBaseFragment extends BaseFragment {

    private Unbinder unbinder;

    private LoadingDialog loadingDialog;

    @Override
    protected int getContentViewLayoutID() {
        return 0;
    }

    @Override
    protected int getRootLayoutId() {
        return 0;
    }

    @Override
    protected void initView(View view) {
       unbinder= ButterKnife.bind(this,view);
       loadingDialog = new LoadingDialog(mContext);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void bindListener() {

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
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }


    @Override
    public void onDestroyView() {
        if (loadingDialog.isShowing()) {
            loadingDialog.dismiss();
        }
        loadingDialog = null;
        super.onDestroyView();
        unbinder.unbind();
    }


    @Override
    public void showLoadingDialog() {
        if(loadingDialog!=null&&!loadingDialog.isShowing()){
            loadingDialog.show();
        }
    }

    @Override
    public void cancalLoadingDialog() {
        if(loadingDialog!=null&&loadingDialog.isShowing()){
            loadingDialog.dismiss();
        }
    }

    protected boolean isLogin(){
        String token = PreferencesUtils.getString(mContext,CommonConstants.KEY_TOKEN,"");

        return !StringUtils.isEmpty(token);
    }

    protected void goToLogin(){
        PreferencesUtils.putString(mContext, CommonConstants.KEY_ACCOUNT_ID,"");
        PreferencesUtils.putString(mContext, CommonConstants.KEY_TOKEN,"");
        readyGoClearTop(LoginActivity.class);
    }

    public void goToIm() {
        String accountId = PreferencesUtils.getString(mContext, CommonConstants.KEY_ACCOUNT_ID,"");
        String md5AccoundId = MD5Utils.MD5Encode(accountId,"utf-8");
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
}
