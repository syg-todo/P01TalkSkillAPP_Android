package com.tuodanhuashu.app.pay.view;

import com.company.common.base.BaseView;
import com.tuodanhuashu.app.pay.bean.GoodsListItemBean;
import com.tuodanhuashu.app.pay.bean.WXPayBean;

import java.util.List;
import java.util.Map;

public interface PayVipView extends BaseView {

    public void getGoodsListSuccess(List<GoodsListItemBean>goodsListItemBeanList);

    public void getGoodsListFail(String msg);

    public void getOrderIdSuccess(String orderId);

    public void getOrderIdFail(String msg);

    public void initDialog();

    public void getWXPayParamsSuccess(WXPayBean wxPayBean);

    public void getWXPayParamsFail(String msg);

    public void getAliPayParamsSuccess(String data);



}
