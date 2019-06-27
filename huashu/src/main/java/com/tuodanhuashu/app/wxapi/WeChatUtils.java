package com.tuodanhuashu.app.wxapi;

import android.content.Context;

import com.company.common.net.OnRequestListener;
import com.company.common.net.ServerResponse;
import com.company.common.utils.JsonUtils;
import com.company.common.utils.StringUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.tuodanhuashu.app.Constants.Constants;
import com.tuodanhuashu.app.wxapi.bean.WeChatTokenRespBean;
import com.tuodanhuashu.app.wxapi.bean.WeChatUserInfoBean;

import java.util.HashMap;
import java.util.Map;

public class WeChatUtils {

    private Context context;

    private WeChatRequestListener listener;

    public WeChatUtils(Context context, WeChatRequestListener listener) {
        this.context = context;
        this.listener = listener;
    }

    public void requestWechatToken(final int tag, Map<String,String> params){
        OkGo.<String>get(Constants.WEIXIN.WX_TOEKN_URL)
             .params(params)
              .execute(new StringCallback() {
                  @Override
                  public void onSuccess(Response<String> response) {
                      WeChatTokenRespBean tokenRespBean = JsonUtils.getJsonToBean(response.body(),WeChatTokenRespBean.class);
                      if(StringUtils.isEmpty(tokenRespBean.getErrcode())){
                          Map<String,String> params = new HashMap<>();
                          params.put("access_token",tokenRespBean.getAccess_token());
                          params.put("openid",tokenRespBean.getOpenid());
                          params.put("lang","zh_CN");
                          requestWechatUserInfo(2,params);
                      }else{
                          listener.OnWeChatFail(tokenRespBean.getErrmsg());
                      }
                  }

                  @Override
                  public void onError(Response<String> response) {
                      super.onError(response);
                  }
              });
    }

    public void requestWechatUserInfo(final int tag, Map<String,String> params){
        OkGo.<String>get(Constants.WEIXIN.WX_INFO_URL)
                .params(params)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        WeChatUserInfoBean userInfoBean = JsonUtils.getJsonToBean(response.body(),WeChatUserInfoBean.class);
                        if(StringUtils.isEmpty(userInfoBean.getErrcode())){
                            listener.OnWeChatSuccess(userInfoBean);
                        }else{
                            listener.OnWeChatFail(userInfoBean.getErrmsg());
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                    }
                });
    }
}
