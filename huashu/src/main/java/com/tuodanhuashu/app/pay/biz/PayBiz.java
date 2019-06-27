package com.tuodanhuashu.app.pay.biz;

import android.content.Context;

import com.company.common.net.OkNetUtils;
import com.company.common.net.OnRequestListener;
import com.tuodanhuashu.app.Constants.Constants;

import java.util.HashMap;
import java.util.Map;

public class PayBiz {

    private OnRequestListener listener;

    private Context context;

    public PayBiz(OnRequestListener listener, Context context) {
        this.listener = listener;
        this.context = context;
    }

    public void requestGoodsList(int tag){

        Map<String,String> params = new HashMap<>();
        OkNetUtils.get(tag, Constants.URL.BASE_URL+Constants.URL.GOODS_LIST_URL,params,context,listener);
    }

    public void requestAddOrder(int tag,String goods_id){
        Map<String,String> params = new HashMap<>();
        params.put("goods_id",goods_id);
        OkNetUtils.post(tag,Constants.URL.BASE_URL+Constants.URL.ADD_ORDER_URL,params,context,listener);
    }


    public void requestWXPay(int tag,String osn){
        Map<String,String> params = new HashMap<>();
        params.put("osn",osn);
        OkNetUtils.get(tag,Constants.URL.BASE_URL+Constants.URL.WX_PAY_URL,params,context,listener);
    }

    public void requestAliPay(int tag,String osn){
        Map<String,String> params = new HashMap<>();
        params.put("osn",osn);
        OkNetUtils.get(tag,Constants.URL.BASE_URL+Constants.URL.ALI_PAY_URL,params,context,listener);
    }
}
