package com.tuodanhuashu.app.course.ui;

import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.company.common.CommonConstants;
import com.company.common.utils.DisplayUtil;
import com.company.common.utils.PreferencesUtils;
import com.company.common.utils.StringUtils;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.tuodanhuashu.app.Constants.Constants;
import com.tuodanhuashu.app.R;
import com.tuodanhuashu.app.base.HuaShuBaseActivity;
import com.tuodanhuashu.app.course.bean.LoveGoodBean;
import com.tuodanhuashu.app.course.bean.OrderBean;
import com.tuodanhuashu.app.course.presenter.OrderPresenter;
import com.tuodanhuashu.app.course.ui.adapter.LoveGoodAdapter;
import com.tuodanhuashu.app.course.view.OrderConfirmView;
import com.tuodanhuashu.app.pay.bean.WXPayBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

public class LoveCountRechargeActivity extends HuaShuBaseActivity implements View.OnClickListener, OrderConfirmView {

    private final String TAG = LoveCountRechargeActivity.class.getSimpleName();
    @BindView(R.id.common_head_back_iv)
    ImageView ivHeadBack;
    @BindView(R.id.common_head_title_tv)
    TextView tvHeadTitle;
    @BindView(R.id.tv_love_count_balance)
    TextView tvLoveCountBalance;
    @BindView(R.id.pay_btn)
    Button btnPay;
    @BindView(R.id.rv_love_goods)
    RecyclerView rvLoveGoods;


    private String currentGoodId;
    public static final String EXTAR_COURSE_ID = "course_id";
    private String courseId = "";

    public static final String EXTRA_LOVE_COUNT = "love_count";
    private String loveCount;


    private String accessToken = "";

    private String osn;

    private Dialog dialog;

    private OrderPresenter orderPresenter;

    private LoveGoodAdapter adapterLoveGood;

    private List<LoveGoodBean> loveGoodBeanList = new ArrayList<>();
    private PayHandler payHandler;

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
        return R.layout.activity_order;
    }

    @Override
    protected void initView() {
        super.initView();

        initDialog();
        ivHeadBack.setOnClickListener(this);
        btnPay.setOnClickListener(this);
        ivHeadBack.getDrawable().setTint(getResources().getColor(R.color.black));
        rvLoveGoods.setLayoutManager(new GridLayoutManager(mContext, 3));
        tvLoveCountBalance.setText(loveCount +" " +getResources().getString(R.string.love_money));
        tvHeadTitle.setText("我的恋爱币");

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
                    Toast.makeText(mContext, osn, Toast.LENGTH_LONG).show();
                    orderPresenter.requestWXPay(accessToken, osn);
                }
                dialog.dismiss();
            }
        });
        aliPayTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!StringUtils.isEmpty(osn)) {
                    orderPresenter.requestAliPay(accessToken, osn);
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

    }

    @Override
    public void getLoveGoodsListSuccess(List<LoveGoodBean> loveGoodBeans) {
        loveGoodBeanList = loveGoodBeans;
        setCurrentGoodId(loveGoodBeanList.get(0).getId());

        adapterLoveGood = new LoveGoodAdapter(LoveCountRechargeActivity.this, loveGoodBeanList);

        rvLoveGoods.setAdapter(adapterLoveGood);
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
    public void getLoveGoodsListFail(String msg) {

    }

    @Override
    public void getOrderIdSuccess(OrderBean orderBean) {
        this.osn = orderBean.getOsn();
        Log.d(TAG, osn);
        dialog.show();
    }

    @Override
    public void getOrderIdFail(String msg) {
        Log.d(TAG, msg);
    }

    @Override
    public void getWXPayParamsSuccess(WXPayBean wxPayBean) {
        Toast.makeText(mContext, "wxsuccess", Toast.LENGTH_LONG).show();
        IWXAPI api = WXAPIFactory.createWXAPI(mContext, Constants.WEIXIN.WX_APP_ID);

        // 将应用的appId注册到微信

        PayReq request = new PayReq();
        request.appId = wxPayBean.getAppid();
        request.partnerId = wxPayBean.getPartnerid();
        request.prepayId = wxPayBean.getPrepayid();
        request.packageValue = wxPayBean.getPackageValue();
        request.nonceStr = wxPayBean.getNoncestr();
        request.timeStamp = wxPayBean.getTimestamp() + "";
        request.sign = wxPayBean.getSign();
        api.sendReq(request);
    }

    @Override
    public void getWXPayParamsFail(String msg) {
        Toast.makeText(mContext, "wxfail:" + msg, Toast.LENGTH_LONG).show();
    }

    @Override
    public void getAliPayParamsSuccess(final String data) {
        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                //新建任务
                PayTask alipay = new PayTask(LoveCountRechargeActivity.this);
                //获取支付结果
                Map<String, String> result = alipay.payV2(data, true);
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
    public void getAliPayParamsFail(String msg) {

    }

    @Override
    public void getCorderOsnSuccess(OrderBean orderBean) {

    }

    @Override
    public void getCorderOsnFail(String msg) {

    }


    @Override
    protected void initData() {
        super.initData();
        payHandler = new PayHandler();
        accessToken = PreferencesUtils.getString(mContext, CommonConstants.KEY_TOKEN);
        orderPresenter = new OrderPresenter(mContext, this);
        orderPresenter.requestLoveGoods(accessToken);


    }

    @Override
    protected void getBundleExtras(Bundle extras) {
        super.getBundleExtras(extras);
        courseId = extras.getString(EXTAR_COURSE_ID);
        loveCount = extras.getString(EXTRA_LOVE_COUNT);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.common_head_back_iv:
                onBackPressed();
                break;
            case R.id.pay_btn:
                pay();
                break;
        }
    }

    public void setCurrentGoodId(String goodId) {
        this.currentGoodId = goodId;
    }

    private void pay() {
        orderPresenter.requestAddOrder(accessToken, currentGoodId);
    }
}
