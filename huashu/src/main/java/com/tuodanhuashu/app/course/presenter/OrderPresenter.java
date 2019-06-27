package com.tuodanhuashu.app.course.presenter;

import android.content.Context;
import android.util.Log;

import com.company.common.base.BasePresenter;
import com.company.common.base.BaseView;
import com.company.common.net.ServerResponse;
import com.company.common.utils.JsonUtils;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.tuodanhuashu.app.Constants.Constants;
import com.tuodanhuashu.app.course.bean.LoveGoodBean;
import com.tuodanhuashu.app.course.bean.OrderBean;
import com.tuodanhuashu.app.course.biz.OrderBiz;
import com.tuodanhuashu.app.course.view.OrderConfirmView;
import com.tuodanhuashu.app.course.view.OrderView;
import com.tuodanhuashu.app.pay.bean.WXPayBean;

import java.util.List;
import java.util.Map;

public class OrderPresenter extends BasePresenter {

    private Context context;

    private OrderConfirmView orderView;

    private OrderBiz orderBiz;


    private static final int TAG_GOODS_LIST = 1;

    private static final int TAG_ADD_ORDER = 2;

    private static final int TAG_WX_PAY = 3;

    private static final int TAG_ALI_PAY = 4;

    private static final int TAG_ADD_CORDER = 5;

    public OrderPresenter(Context context, OrderConfirmView orderView) {
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

    public void requestAddCorder(String accessToken, String courseId) {
        orderBiz.requestAddCorder(TAG_ADD_CORDER, accessToken, courseId);
    }

    public void requestAddOrder(String accessToken, String courseId) {
        orderBiz.requestAddOrder(TAG_ADD_ORDER, accessToken, courseId);
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
                break;
            case TAG_ALI_PAY:
                orderView.getAliPayParamsSuccess(serverResponse.getData());
                break;

            case TAG_ADD_CORDER:
                OrderBean corderBean = JsonUtils.getJsonToBean(serverResponse.getData(),OrderBean.class);
                orderView.getCorderOsnSuccess(corderBean);
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
            case TAG_ADD_CORDER:
                orderView.getCorderOsnFail(msg);
                break;
        }
    }

}
