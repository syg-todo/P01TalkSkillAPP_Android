package com.tuodanhuashu.app.pay.ui;

import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alipay.sdk.app.PayTask;
import com.company.common.utils.DisplayUtil;
import com.company.common.utils.StringUtils;
import com.tuodanhuashu.app.Constants.Constants;
import com.tuodanhuashu.app.R;
import com.tuodanhuashu.app.base.HuaShuBaseActivity;
import com.tuodanhuashu.app.eventbus.EventMessage;
import com.tuodanhuashu.app.pay.adapter.VipFlagsAdapter;
import com.tuodanhuashu.app.pay.bean.GoodsListItemBean;
import com.tuodanhuashu.app.pay.bean.WXPayBean;
import com.tuodanhuashu.app.pay.presenter.PayPresenter;
import com.tuodanhuashu.app.pay.view.PayVipView;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class VipPayActivity extends HuaShuBaseActivity implements PayVipView {


    @BindView(R.id.common_head_back_iv)
    ImageView commonHeadBackIv;
    @BindView(R.id.common_head_title_tv)
    TextView commonHeadTitleTv;
    @BindView(R.id.pay_goods_ll_1)
    LinearLayout payGoodsLl1;
    @BindView(R.id.pay_goods_ll_2)
    LinearLayout payGoodsLl2;
    @BindView(R.id.pay_goods_ll_3)
    LinearLayout payGoodsLl3;
    @BindView(R.id.pay_btn)
    Button payBtn;
    @BindView(R.id.pay_goods_name_tv_1)
    TextView payGoodsNameTv1;
    @BindView(R.id.pay_goods_time_tv_1)
    TextView payGoodsTimeTv1;
    @BindView(R.id.pay_goods_price_tv_1)
    TextView payGoodsPriceTv1;
    @BindView(R.id.pay_goods_name_tv_2)
    TextView payGoodsNameTv2;
    @BindView(R.id.pay_goods_time_tv_2)
    TextView payGoodsTimeTv2;
    @BindView(R.id.pay_goods_price_tv_2)
    TextView payGoodsPriceTv2;
    @BindView(R.id.pay_goods_name_tv_3)
    TextView payGoodsNameTv3;
    @BindView(R.id.pay_goods_time_tv_3)
    TextView payGoodsTimeTv3;
    @BindView(R.id.pay_goods_price_tv_3)
    TextView payGoodsPriceTv3;
    @BindView(R.id.pay_fll)
    TagFlowLayout homeFll;

    private PayPresenter payPresenter;

    private String goodsid = "";

    private List<GoodsListItemBean> goodsList;

    private String osn;

    private Dialog dialog;

    private PayHandler payHandler;

    private VipFlagsAdapter adapter;
    private class PayHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Map<String, String> result = (Map<String, String>) msg.obj;
            if (result != null && result.containsKey("resultStatus")) {
                String code = result.get("resultStatus");
                if (code.equals("9000")) {
                    showToast("支付成功");
                } else {
                    showToast("支付失败");
                }
            }
        }
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_vip_pay;
    }

    @Override
    protected void initView() {
        super.initView();
        initDialog();
        commonHeadTitleTv.setText("VIP会员特权");
        commonHeadBackIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    protected void initData() {
        super.initData();
        payHandler = new PayHandler();
        payPresenter = new PayPresenter(mContext, this);
        payPresenter.requestGoodsList();
    }

    @Override
    protected void bindListener() {
        super.bindListener();
        payGoodsLl1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goodsid = goodsList.get(0).getId();
                payGoodsLl1.setBackgroundResource(R.drawable.shape_vip_selected);
                payGoodsLl2.setBackgroundResource(R.drawable.shape_vip_unselected);
                payGoodsLl3.setBackgroundResource(R.drawable.shape_vip_unselected);
                adapter = new VipFlagsAdapter(mContext,goodsList.get(0).getFlag());
                homeFll.setAdapter(adapter);
            }
        });
        payGoodsLl2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goodsid = goodsList.get(1).getId();
                payGoodsLl1.setBackgroundResource(R.drawable.shape_vip_unselected);
                payGoodsLl2.setBackgroundResource(R.drawable.shape_vip_selected);
                payGoodsLl3.setBackgroundResource(R.drawable.shape_vip_unselected);
                adapter = new VipFlagsAdapter(mContext,goodsList.get(1).getFlag());
                homeFll.setAdapter(adapter);
            }
        });
        payGoodsLl3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goodsid = goodsList.get(2).getId();
                payGoodsLl1.setBackgroundResource(R.drawable.shape_vip_unselected);
                payGoodsLl2.setBackgroundResource(R.drawable.shape_vip_unselected);
                payGoodsLl3.setBackgroundResource(R.drawable.shape_vip_selected);
                adapter = new VipFlagsAdapter(mContext,goodsList.get(2).getFlag());
                homeFll.setAdapter(adapter);
            }
        });

        payBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Dialog dialog = new Dialog(mContext,R.style.BottomDialog_Animation);
//                View view = getLayoutInflater().inflate(R.layout.common_head_layout, null);
//                dialog.setContentView(view);
//                Window window = dialog.getWindow();
//                window.setGravity(Gravity.BOTTOM);
//                WindowManager.LayoutParams params = window.getAttributes();
//                params.width = WindowManager.LayoutParams.MATCH_PARENT;
//                params.height = DisplayUtil.dip2px(mContext,50.0f);
//                window.setAttributes(params);
//                dialog.show();
                payPresenter.requestAddOrder(goodsid);
            }
        });
    }

    @Override
    public void getGoodsListSuccess(List<GoodsListItemBean> goodsListItemBeanList) {
        this.goodsList = goodsListItemBeanList;
        this.goodsid = goodsListItemBeanList.get(0).getId();
        payGoodsNameTv1.setText(goodsList.get(0).getName());
        payGoodsNameTv2.setText(goodsList.get(1).getName());
        payGoodsNameTv3.setText(goodsList.get(2).getName());
        payGoodsTimeTv1.setText(goodsList.get(0).getMonths());
        payGoodsTimeTv2.setText(goodsList.get(1).getMonths());
        payGoodsTimeTv3.setText(goodsList.get(2).getMonths());
        payGoodsPriceTv1.setText("￥" + goodsList.get(0).getPrice());
        payGoodsPriceTv2.setText("￥" + goodsList.get(1).getPrice());
        payGoodsPriceTv3.setText("￥" + goodsList.get(2).getPrice());
        adapter = new VipFlagsAdapter(mContext,goodsList.get(0).getFlag());
        homeFll.setAdapter(adapter);
    }

    @Override
    public void getGoodsListFail(String msg) {

    }

    @Override
    public void getOrderIdSuccess(String orderId) {
        this.osn = orderId;
        Log.e("osn", orderId);

        dialog.show();
    }

    @Override
    public void getOrderIdFail(String msg) {

    }

    @Override
    public void initDialog() {

        dialog = new Dialog(mContext, R.style.BottomDialog_Animation);
        View view = getLayoutInflater().inflate(R.layout.dialog_pay_type_layout, null);
        dialog.setContentView(view);
        TextView wxPayTv = view.findViewById(R.id.wx_pay_tv);
        TextView aliPayTv = view.findViewById(R.id.ali_pay_tv);
        wxPayTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!StringUtils.isEmpty(osn)) {
                    payPresenter.requestWXPay(osn);
                }
                dialog.dismiss();
            }
        });
        aliPayTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!StringUtils.isEmpty(osn)) {
                    payPresenter.requestAliPay(osn);
                }
                dialog.dismiss();

            }
        });
        dialog.setCanceledOnTouchOutside(true);
        Window window = dialog.getWindow();
        window.setGravity(Gravity.BOTTOM);
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = DisplayUtil.dip2px(mContext, 120.0f);
        window.setAttributes(params);
        //dialog.show();
    }

    @Override
    public void getWXPayParamsSuccess(WXPayBean wxPayBean) {
//       IWXAPI api = WXAPIFactory.createWXAPI(this, Constants.WEIXIN.WX_APP_ID, true);
//
//        // 将应用的appId注册到微信
//        api.registerApp(Constants.WEIXIN.WX_APP_ID);
//        PayReq request = new PayReq();
//        request.appId = wxPayBean.getAppid();
//        request.partnerId = wxPayBean.getPartnerid();
//        request.prepayId= wxPayBean.getPrepayid();
//        request.packageValue = wxPayBean.getSign();
//        request.nonceStr= wxPayBean.getNoncestr();
//        request.timeStamp= wxPayBean.getTimestamp()+"";
//        request.sign= wxPayBean.getSign();
//        api.sendReq(request);
    }

    @Override
    public void getWXPayParamsFail(String msg) {

    }

    @Override
    public void getAliPayParamsSuccess(final String payData) {
        Log.e("alipay", payData);
        //异步处理
        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                //新建任务
                PayTask alipay = new PayTask(VipPayActivity.this);
                //获取支付结果
                Map<String, String> result = alipay.payV2(payData, true);
                Message msg = new Message();
                msg.what = 1;
                msg.obj = result;
                payHandler.sendMessage(msg);
            }
        };
        Thread payThread = new Thread(payRunnable);
        payThread.start();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (dialog.isShowing()) {
            dialog.dismiss();
        }
        if (payHandler != null) {
            payHandler = null;
        }
        dialog = null;
    }

    @Override
    public void onEvent(EventMessage message) {
        super.onEvent(message);
        switch (message.getTag()){
            case Constants.EVENT_TAG.TAG_WX_PAY_SUCCESS:
                showToast("支付成功");
                break;
            case Constants.EVENT_TAG.TAG_WX_PAY_FAIL:
                showToast("支付失败");
                break;
        }
    }
}
