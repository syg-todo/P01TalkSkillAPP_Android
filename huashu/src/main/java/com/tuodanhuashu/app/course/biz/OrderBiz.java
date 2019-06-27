package com.tuodanhuashu.app.course.biz;

import android.content.Context;
import android.util.Log;

import com.company.common.net.OkNetUtils;
import com.company.common.net.OnRequestListener;
import com.tuodanhuashu.app.Constants.Constants;

import java.util.HashMap;
import java.util.Map;

public class OrderBiz {
    private OnRequestListener listener;

    private Context context;


    public OrderBiz(OnRequestListener listener, Context context) {
        this.listener = listener;
        this.context = context;
    }

    public void requestWXPay(int tag, String accessToken, String osn) {
        Map<String, String> params = new HashMap<>();
        params.put("access_token", accessToken);
        params.put("osn", osn);
        OkNetUtils.get(tag, Constants.URL.BASE_URL + Constants.URL.WX_PAY_URL, params, context, listener);
    }

    public void requestAliPay(int tag, String accessToken, String osn) {
        Map<String, String> params = new HashMap<>();
        params.put("access_token", accessToken);
        params.put("osn", osn);
        OkNetUtils.get(tag, Constants.URL.BASE_URL + Constants.URL.ALI_PAY_URL, params, context, listener);
    }

    public void requestAddCorder(int tag, String accessToken, String courseId) {
        Map<String, String> params = new HashMap<>();
        params.put("access_token", accessToken);
        params.put("course_id", courseId);
        OkNetUtils.get(tag, Constants.URL.BASE_URL + Constants.URL.ADD_CORDER, params, context, listener);
    }

    public void requestAddOrder(int tag,String accessToken,String goodsId){
        Map<String,String> params = new HashMap<>();
        params.put("access_token",accessToken);
        params.put("goods_id",goodsId);
        OkNetUtils.get(tag,Constants.URL.BASE_URL+Constants.URL.ADD_LGOOLDS_URL,params,context,listener);
    }

    public void requestLoveGoods(int tag, String accessToken) {
        Map<String, String> params = new HashMap<>();
        params.put("access_token", accessToken);
        OkNetUtils.post(tag, Constants.URL.BASE_URL + Constants.URL.GET_LOVE_GOODS_LIST_URL, params, context, listener);
    }


}
