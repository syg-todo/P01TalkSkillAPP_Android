package com.company.common.net;

import android.content.Context;
import android.util.Log;

import com.company.common.CommonConstants;
import com.company.common.utils.JsonUtils;
import com.company.common.utils.PackageUtils;
import com.company.common.utils.PreferencesUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.FileCallback;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.HttpHeaders;
import com.lzy.okgo.model.Progress;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;

import java.io.File;
import java.util.Map;

public class OkNetUtils {

    public static void post(final int tag, String url, Map<String, String> params,Context context, final OnRequestListener listener) {
        params.put("access_token",getUserToken(context));
        OkGo.<String>post(url)
                .headers(initHeaders(context))
                .params(params)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        //LogUtils.i(TAG,response.body());
                        ServerResponse serverResponse = (ServerResponse) JsonUtils.getJsonToBean(response.body(), ServerResponse.class);
                        if (serverResponse.getCode() == 0) {
                            listener.OnSuccess(serverResponse, tag);
                        } else {
                            listener.onFail(serverResponse.getMsg(), serverResponse.getCode(), tag);
                        }


                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);

                        listener.onError(tag);
                    }
                });

    }

    public static void get(final int tag, String url, Map<String, String> params, Context context,final OnRequestListener listener){
        params.put("access_token",getUserToken(context));
        OkGo.<String>get(url)
                .headers(initHeaders(context))
                .params(params)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        //LogUtils.i(TAG,response.body());
                        ServerResponse serverResponse = (ServerResponse) JsonUtils.getJsonToBean(response.body(), ServerResponse.class);
                        if (serverResponse.getCode() == 0) {
                            listener.OnSuccess(serverResponse, tag);
                        } else {
                            listener.onFail(serverResponse.getMsg(), serverResponse.getCode(), tag);
                        }


                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);

                        listener.onError(tag);
                    }
                });
    }

   public static void downLoadFile(String url,String fileDir, String fileName, final OnDownloadListener listener){
       OkGo.<File>get(url)//
               .execute(new FileCallback(fileDir,fileName) {

                   @Override
                   public void onStart(Request<File, ? extends Request> request) {
                        listener.onTaskStart();
                   }

                   @Override
                   public void onSuccess(Response<File> response) {

                        listener.onSuccess();
                   }

                   @Override
                   public void onError(Response<File> response) {
                        listener.onError();
                   }

                   @Override
                   public void downloadProgress(Progress progress) {

                        listener.onProgeress(progress.fraction);
                   }
               });

   }


    private static HttpHeaders initHeaders(Context context){
        HttpHeaders headers = new HttpHeaders();
        headers.put("Accept","application/json");
        headers.put("appVersion",PackageUtils.getVersionName(context));
        headers.put("platform","android");
        return headers;

    }

    private static String getUserToken(Context context){
        return PreferencesUtils.getString(context,CommonConstants.KEY_TOKEN,"");
    }
}
