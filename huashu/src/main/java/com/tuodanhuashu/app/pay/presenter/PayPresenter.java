package com.tuodanhuashu.app.pay.presenter;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.util.Log;

import com.alibaba.fastjson.JSONObject;
import com.company.common.base.BasePresenter;
import com.company.common.base.BaseView;
import com.company.common.net.ServerResponse;
import com.company.common.utils.JsonUtils;
import com.company.common.utils.OSUtils;
import com.hyphenate.helpdesk.model.Content;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.tuodanhuashu.app.Constants.Constants;
import com.tuodanhuashu.app.pay.bean.GoodsListItemBean;
import com.tuodanhuashu.app.pay.bean.WXPayBean;
import com.tuodanhuashu.app.pay.biz.PayBiz;
import com.tuodanhuashu.app.pay.view.PayVipView;

import java.util.List;
import java.util.Map;

public class PayPresenter extends BasePresenter  {

    private Context context;

    private PayVipView payVipView;

    private PayBiz payBiz;

    private static final int TAG_GOODS_LIST = 1;

    private static final int TAG_ADD_ORDER = 2;

    private static final int TAG_WX_PAY  = 3;

    private static final int TAG_ALI_PAY = 4;

    public PayPresenter(Context context, PayVipView payVipView) {
        this.context = context;
        this.payVipView = payVipView;
        payBiz = new PayBiz(this,context);
    }

    public void requestGoodsList(){
        payBiz.requestGoodsList(TAG_GOODS_LIST);

    }

    public void requestAddOrder(String goodsId){
        //payVipView.showLoadingDialog();
        payBiz.requestAddOrder(TAG_ADD_ORDER,goodsId);
    }

    public void requestWXPay(String osn){
        //payVipView.showLoadingDialog();
        payBiz.requestWXPay(TAG_WX_PAY,osn);
    }

    public void requestAliPay(String osn){
        //payVipView.showLoadingDialog();
        payBiz.requestAliPay(TAG_ALI_PAY,osn);
    }

    @Override
    public void OnSuccess(ServerResponse serverResponse, int tag) {
        super.OnSuccess(serverResponse, tag);
        switch (tag){
            case TAG_GOODS_LIST:
                List<GoodsListItemBean> goodsList = JsonUtils.getJsonToList(serverResponse.getData(),GoodsListItemBean.class);
                payVipView.getGoodsListSuccess(goodsList);
                break;
            case TAG_ADD_ORDER:
                payVipView.cancalLoadingDialog();
                Map<String,String> data = JsonUtils.getJsonToBean(serverResponse.getData(),Map.class);
                payVipView.getOrderIdSuccess(data.get("osn"));
                break;
            case TAG_WX_PAY:
                WXPayBean wxPayBean = JsonUtils.getJsonToBean(serverResponse.getData(),WXPayBean.class);
                payVipView.getWXPayParamsSuccess(wxPayBean);
                Log.e("wxpay",wxPayBean.toString());
                IWXAPI api = WXAPIFactory.createWXAPI(context, Constants.WEIXIN.WX_APP_ID);

                // 将应用的appId注册到微信

                PayReq request = new PayReq();
                request.appId = wxPayBean.getAppid();
                request.partnerId = wxPayBean.getPartnerid();
                request.prepayId= wxPayBean.getPrepayid();
                request.packageValue = wxPayBean.getPackageValue();
                request.nonceStr= wxPayBean.getNoncestr();
                request.timeStamp= wxPayBean.getTimestamp()+"";
                request.sign= wxPayBean.getSign();
                api.sendReq(request);
                break;
            case TAG_ALI_PAY:
                payVipView.getAliPayParamsSuccess(serverResponse.getData());
                break;
        }
    }

    @Override
    public void onFail(String msg, int code, int tag) {
        super.onFail(msg, code, tag);
        payVipView.cancalLoadingDialog();
        payVipView.showToast(msg);
    }

    @Override
    public BaseView getBaseView() {
        return payVipView;
    }
}
