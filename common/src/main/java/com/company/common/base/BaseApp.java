package com.company.common.base;

import android.app.Application;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.interceptor.HttpLoggingInterceptor;
import com.lzy.okgo.model.HttpHeaders;

import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import okhttp3.OkHttpClient;

public class BaseApp extends Application {
    private int TIME_OUT = 15000;
    @Override
    public void onCreate() {
        super.onCreate();
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.readTimeout(TIME_OUT, TimeUnit.MILLISECONDS);
        builder.readTimeout(TIME_OUT,TimeUnit.MILLISECONDS);
        builder.connectTimeout(TIME_OUT,TimeUnit.MILLISECONDS);
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor("OkGo");
        loggingInterceptor.setPrintLevel(HttpLoggingInterceptor.Level.BODY);        //log打印级别，决定了log显示的详细程度
        loggingInterceptor.setColorLevel(Level.INFO);                               //log颜色级别，决定了log在控制台显示的颜色
        builder.addInterceptor(loggingInterceptor);

        OkGo.getInstance().init(this).setOkHttpClient(builder.build())
                .addCommonHeaders(initHttpHeaders())
                .setCacheMode(CacheMode.NO_CACHE)
                .setRetryCount(0);
    }

    protected HttpHeaders initHttpHeaders(){
        return null;
    }
}
