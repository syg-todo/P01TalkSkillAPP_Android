package com.tuodanhuashu.app.course.presenter;

import android.content.Context;

import com.company.common.base.BasePresenter;
import com.company.common.base.BaseView;
import com.company.common.net.ServerResponse;
import com.company.common.utils.JsonUtils;
import com.tuodanhuashu.app.course.bean.LoveGoodBean;
import com.tuodanhuashu.app.course.bean.OrderBean;
import com.tuodanhuashu.app.course.biz.OrderBiz;
import com.tuodanhuashu.app.course.view.OrderView;
import com.tuodanhuashu.app.pay.bean.WXPayBean;

import java.util.List;

public class OrderConfirmPresenter extends BasePresenter {
    private Context context;

    private OrderView orderView;

    private OrderBiz orderBiz;

    private static final int TAG_GOODS_LIST = 1;

    private static final int TAG_ADD_ORDER = 2;

    private static final int TAG_WX_PAY = 3;

    private static final int TAG_ALI_PAY = 4;

    public OrderConfirmPresenter(Context context, OrderView orderView) {
        this.context = context;
        this.orderView = orderView;
        orderBiz = new OrderBiz(this, context);
    }

    @Override
    public BaseView getBaseView() {
        return orderView;
    }

    public void requestWXPay(String accessToken, String osn) {
        //payVipView.showLoadingDialog();
        orderBiz.requestWXPay(TAG_WX_PAY, accessToken, osn);
    }

    public void requestAliPay(String accessToken, String osn) {
        //payVipView.showLoadingDialog();
        orderBiz.requestAliPay(TAG_ALI_PAY, accessToken, osn);
    }


    public void requestLoveGoods(String accessToken) {
        orderBiz.requestLoveGoods(TAG_GOODS_LIST, accessToken);
    }

    public void requestAddOrder(String accessToken, String goodsId) {
        orderBiz.requestAddCorder(TAG_ADD_ORDER, accessToken, goodsId);
    }

    @Override
    public void OnSuccess(ServerResponse serverResponse, int tag) {
        super.OnSuccess(serverResponse, tag);
        switch (tag) {
            case TAG_GOODS_LIST:
                List<LoveGoodBean> loveGoodBeans = JsonUtils.getJsonToList(serverResponse.getData(), LoveGoodBean.class);
                orderView.getLoveGoodsListSuccess(loveGoodBeans);
                break;
            case TAG_ADD_ORDER:
//                orderView.cancalLoadingDialog();
                OrderBean orderBean = JsonUtils.getJsonToBean(serverResponse.getData(), OrderBean.class);
                orderView.getOrderIdSuccess(orderBean);
                break;
            case TAG_WX_PAY:
                WXPayBean wxPayBean = JsonUtils.getJsonToBean(serverResponse.getData(), WXPayBean.class);
                orderView.getWXPayParamsSuccess(wxPayBean);

//                IWXAPI api = WXAPIFactory.createWXAPI(context, Constants.WEIXIN.WX_APP_ID);
//
//                // 将应用的appId注册到微信
//
//                PayReq request = new PayReq();
//                request.appId = wxPayBean.getAppid();
//                request.partnerId = wxPayBean.getPartnerid();
//                request.prepayId= wxPayBean.getPrepayid();
//                request.packageValue = wxPayBean.getPackageValue();
//                request.nonceStr= wxPayBean.getNoncestr();
//                request.timeStamp= wxPayBean.getTimestamp()+"";
//                request.sign= wxPayBean.getSign();
//                api.sendReq(request);
                break;
            case TAG_ALI_PAY:
                orderView.getAliPayParamsSuccess(serverResponse.getData());
                break;

        }
    }

    @Override
    public void onFail(String msg, int code, int tag) {
        super.onFail(msg, code, tag);
        switch (tag) {
            case TAG_ADD_ORDER:
                orderView.getOrderIdFail(msg);
                break;
            case TAG_ALI_PAY:
                orderView.getAliPayParamsFail(msg);
                break;
        }
    }
}
