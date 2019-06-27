package com.tuodanhuashu.app.course.view;

import com.company.common.base.BaseView;
import com.tuodanhuashu.app.course.bean.LoveGoodBean;
import com.tuodanhuashu.app.course.bean.OrderBean;
import com.tuodanhuashu.app.pay.bean.WXPayBean;

import java.util.List;

public interface OrderConfirmView extends BaseView {

    void initDialog();

    void getLoveGoodsListSuccess(List<LoveGoodBean> loveGoodBeans);

    void getLoveGoodsListFail(String msg);

    void getOrderIdSuccess(OrderBean order);

    void getOrderIdFail(String msg);

    void getWXPayParamsSuccess(WXPayBean wxPayBean);

    void getWXPayParamsFail(String msg);

    void getAliPayParamsSuccess(String data);

    void getAliPayParamsFail(String msg);

    void getCorderOsnSuccess(OrderBean orderBean);

    void getCorderOsnFail(String msg);
}
