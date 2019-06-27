package com.tuodanhuashu.app.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.tuodanhuashu.app.Constants.Constants;
import com.tuodanhuashu.app.eventbus.EventMessage;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;

public class WXEntryActivity extends Activity implements IWXAPIEventHandler {

    private IWXAPI api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        api = WXAPIFactory.createWXAPI(this, Constants.WEIXIN.WX_APP_ID, true);
        api.registerApp(Constants.WEIXIN.WX_APP_ID);
        boolean isSuccess = api.handleIntent(this.getIntent(), this);
        //ToastUtils.showToast(this, "onCreate !!!!!" + isSuccess);

    }

    @Override
    protected void onStart() {
        super.onStart();
        api = WXAPIFactory.createWXAPI(this, Constants.WEIXIN.WX_APP_ID,true);
        api.registerApp(Constants.WEIXIN.WX_APP_ID);
        api.handleIntent(this.getIntent(), this);
//        ToastUtils.showToast(this,"onstart !!!!!");
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        setIntent(intent);
        api = WXAPIFactory.createWXAPI(this, Constants.WEIXIN.WX_APP_ID, true);
        api.registerApp(Constants.WEIXIN.WX_APP_ID);
        boolean isSuccess = api.handleIntent(intent, this);
        //ToastUtils.showToast(this,"onNewIntent !!!!!");
    }


    @Override
    public void onReq(BaseReq baseReq) {

    }

    @Override
    public void onResp(BaseResp baseResp) {
        if (baseResp.getType() == ConstantsAPI.COMMAND_SENDAUTH) {
            if (baseResp.errCode == 0) {
                Map<String, String> params = new HashMap<>();
                params.put("appid", Constants.WEIXIN.WX_APP_ID);
                params.put("secret", Constants.WEIXIN.WX_APP_SECRET);
                params.put("code", ((SendAuth.Resp) baseResp).code + "");
                params.put("grant_type", "authorization_code");
                EventBus.getDefault().post(new EventMessage<Map>(Constants.EVENT_TAG.TAG_WX_AUTH_SUCCESS, params));
                finish();
            }else{
                EventBus.getDefault().post(new EventMessage<Map>(Constants.EVENT_TAG.TAG_WX_AUTH_FAIL, null));
                finish();
            }
        }

        if (baseResp.getType() == ConstantsAPI.COMMAND_SENDMESSAGE_TO_WX) {
            if (baseResp.errCode == 0) {

                EventBus.getDefault().post(new EventMessage<Map>(Constants.EVENT_TAG.TAG_WX_SHARE_SUCCESS, null));
                finish();
            }else{
                EventBus.getDefault().post(new EventMessage<Map>(Constants.EVENT_TAG.TAG_WX_SHARE_FAIL, null));
                finish();
            }
        }

        if (baseResp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            if (baseResp.errCode == 0) {

                EventBus.getDefault().post(new EventMessage<Map>(Constants.EVENT_TAG.TAG_WX_PAY_SUCCESS, null));
                finish();
            }else{
                EventBus.getDefault().post(new EventMessage<Map>(Constants.EVENT_TAG.TAG_WX_PAY_FAIL, null));
                finish();
            }
        }
    }

}
