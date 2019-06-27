package com.tuodanhuashu.app;

import com.company.common.base.BaseApp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.tuodanhuashu.app.Constants.Constants;
import com.hyphenate.chat.ChatClient;
import com.hyphenate.helpdesk.easeui.UIProvider;
import com.lzy.okgo.model.HttpHeaders;

import cn.jpush.android.api.JPushInterface;

public class HuaShuApplication extends BaseApp {

    private static IWXAPI api;

    private static HuaShuApplication instance;
    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        initChat();
        regToWx();
        initPush();


    }

    public  static HuaShuApplication getInstance(){
        return instance;
    }


    private void initPush() {
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
    }

    @Override
    protected HttpHeaders initHttpHeaders() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.put("Accept","application/json");
        httpHeaders.put("appVersion","1");
        httpHeaders.put("platform","android");

        return httpHeaders;
    }

    private void initChat() {
        ChatClient.Options options = new ChatClient.Options();
        options.setAppkey(Constants.IM.IM_APP_KEY);//必填项，appkey获取地址：kefu.easemob.com，“管理员模式 > 渠道管理 > 手机APP”页面的关联的“AppKey”
        options.setTenantId(Constants.IM.IM_TENANT_ID);//必填项，tenantId获取地址：kefu.easemob.com，“管理员模式 > 设置 > 企业信息”页面的“租户ID”

        // Kefu SDK 初始化
        if (!ChatClient.getInstance().init(this, options)){
            return;
        }
        // Kefu EaseUI的初始化
        UIProvider.getInstance().init(this);
        //后面可以设置其他属性
    }

    private void regToWx() {
        // 通过WXAPIFactory工厂，获取IWXAPI的实例
        api = WXAPIFactory.createWXAPI(this, Constants.WEIXIN.WX_APP_ID, true);

        // 将应用的appId注册到微信
        api.registerApp(Constants.WEIXIN.WX_APP_ID);
    }

    public static IWXAPI getWXapi(){
        return  api;
    }
}
