package com.tuodanhuashu.app.course.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.bumptech.glide.Glide;
import com.company.common.CommonConstants;
import com.company.common.utils.PreferencesUtils;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.OnItemClickListener;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.tuodanhuashu.app.Constants.Constants;
import com.tuodanhuashu.app.R;
import com.tuodanhuashu.app.base.HuaShuBaseActivity;
import com.tuodanhuashu.app.course.bean.LoveGoodBean;
import com.tuodanhuashu.app.course.bean.OrderBean;
import com.tuodanhuashu.app.course.presenter.OrderPresenter;
import com.tuodanhuashu.app.course.view.OrderConfirmView;
import com.tuodanhuashu.app.pay.bean.WXPayBean;

import java.util.List;
import java.util.Map;

import butterknife.BindView;
//import cn.pedant.SweetAlert.SweetAlertDialog;

public class OrderConfirmActivity extends HuaShuBaseActivity implements View.OnClickListener, OrderConfirmView, CompoundButton.OnCheckedChangeListener {

    private final String TAG = OrderConfirmActivity.class.getSimpleName();


    @BindView(R.id.common_head_back_iv)
    ImageView ivHeadBack;
    @BindView(R.id.common_head_title_tv)
    TextView tvHeadTitle;
    @BindView(R.id.tv_order_confirm_pay)
    TextView tvOrderConfirmPay;

    @BindView(R.id.tv_order_confirm_course_name)
    TextView getTvOrderConfirmCourseName;
    @BindView(R.id.iv_order_confirm_course_image)
    ImageView ivOrderConfirmImage;
    @BindView(R.id.tv_order_confirm_course_final_price)
    TextView tvOrderConfirmFinalPrice;
    @BindView(R.id.tv_love_count)
    TextView getTvOrderConfirmLoveCount;

    @BindView(R.id.cb_ali)
    CheckBox cbAli;
    @BindView(R.id.cb_wechat)
    CheckBox cbWechat;
    @BindView(R.id.cb_balance)
    CheckBox cbBalance;

    @BindView(R.id.tv_wechat_pay)
    TextView tvWechatPay;
    @BindView(R.id.tv_ali_pay)
    TextView tvAliPay;
    @BindView(R.id.tv_balance_pay)
    TextView tvBalancePay;

    public static final String EXTRA_FINAL_PRICE = "final_price";
    private String finalPrice = "";

    public static final String EXTRA_COURSE_NAME = "course_name";
    private String courseName = "";

    public static final String EXTRA_COURSE_IMAGE_URL = "course_image";
    private String courseImageUrl = "";

    public static final String EXTRA_LOVE_COUNT = "loce_count";
    private String loveCount = "";

    public static final String EXTRA_COURSE_ID = "course_id";
    private String courseId = "";

    private String accessToken;
    private OrderPresenter orderPresenter;
    private String osn;


    private PayHandler payHandler;
    private boolean isLoveCountEnough;//余额是否不足 true 余额够 fales 余额不足

    private CheckBox[] checkBoxes = new CheckBox[3];

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
        return R.layout.activity_order_confirm;
    }

    @Override
    protected void initView() {
        super.initView();

        ivHeadBack.getDrawable().setTint(getResources().getColor(R.color.black));
        tvHeadTitle.setText(R.string.order_confirm);

        checkBoxes[0] = cbAli;
        checkBoxes[1] = cbWechat;
        checkBoxes[2] = cbBalance;

        tvOrderConfirmPay.setText("支付" + finalPrice + getResources().getString(R.string.love_money));
        getTvOrderConfirmCourseName.setText(courseName);
        tvOrderConfirmFinalPrice.setText(finalPrice + getResources().getString(R.string.love_money));
        Glide.with(mContext).load(courseImageUrl).into(ivOrderConfirmImage);
        getTvOrderConfirmLoveCount.setText("(当前余额：" + loveCount + " "+getResources().getString(R.string.love_money) + ")");

        cbAli.setChecked(true);
        tvOrderConfirmPay.setTag(R.id.cb_ali);
        checkBoxes[0].setOnCheckedChangeListener(this);
        checkBoxes[1].setOnCheckedChangeListener(this);
        checkBoxes[2].setOnCheckedChangeListener(this);

        ivHeadBack.setOnClickListener(this);
        tvOrderConfirmPay.setOnClickListener(this);
        tvAliPay.setOnClickListener(this);
        tvWechatPay.setOnClickListener(this);
        tvBalancePay.setOnClickListener(this);


        int love = Integer.parseInt(loveCount);
        float price = Float.parseFloat(finalPrice);
        if (love < price) {//余额不足
//            checkBoxes[2].setVisibility(View.GONE);
//            ivBalanceArrow.setVisibility(View.VISIBLE);
            isLoveCountEnough = false;
        } else {
//            checkBoxes[2].setVisibility(View.VISIBLE);
//            ivBalanceArrow.setVisibility(View.GONE);
            isLoveCountEnough = true;
        }


//        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup group, int checkedId) {
//                switch (checkedId) {
//                    case R.id.radio_ali:
//                        tvOrderConfirmPay.setTag(checkedId);
//
//                        break;
//                    case R.id.radio_wechat:
//                        tvOrderConfirmPay.setTag(checkedId);
//                        break;
//                    case R.id.radio_balance:
//                        tvOrderConfirmPay.setTag(checkedId);
//                        break;
//                }
//            }
//        });


    }

    @Override
    protected void initData() {
        super.initData();
        accessToken = PreferencesUtils.getString(mContext, CommonConstants.KEY_TOKEN);
        payHandler = new PayHandler();
        orderPresenter = new OrderPresenter(mContext, this);
        orderPresenter.requestAddCorder(accessToken, courseId);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            for (int i = 0; i < checkBoxes.length; i++) {
                //不等于当前选中的就变成false
                if (checkBoxes[i].getId() == buttonView.getId()) {
                    checkBoxes[i].setChecked(true);
                    tvOrderConfirmPay.setTag(checkBoxes[i].getId());
                } else {
                    checkBoxes[i].setChecked(false);
                }
            }

        }
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.common_head_back_iv:
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                onBackPressed();
                break;
            case R.id.tv_order_confirm_pay:
//                orderPresenter.requestAddCorder(accessToken,courseId);

                if ((int) tvOrderConfirmPay.getTag() == R.id.cb_balance) {
                    if (isLoveCountEnough) {
                        balancePay();
                    } else {

//                        new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
//                                .setTitleText("当前余额不足，是否充值？")
////                                .setContentText("Won't be able to recover this file!")
//                                .setConfirmText("充值")
//                                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
//                                    @Override
//                                    public void onClick(SweetAlertDialog sDialog) {
//                                        Bundle bundle = new Bundle();
//                                        bundle.putString(LoveCountRechargeActivity.EXTRA_LOVE_COUNT,loveCount);
//                                        readyGo(LoveCountRechargeActivity.class,bundle);
//                                    }
//                                })
//                                .setCancelText("取消")
//                                .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
//                                    @Override
//                                    public void onClick(SweetAlertDialog sweetAlertDialog) {
//                                        sweetAlertDialog.cancel();
//                                    }
//                                })
//                                .show();

                        DialogPlus dialog = DialogPlus.newDialog(this)
//                                .setAdapter(adapter)
                                .setGravity(Gravity.CENTER)
                                .setOnItemClickListener(new OnItemClickListener() {
                                    @Override
                                    public void onItemClick(DialogPlus dialog, Object item, View view, int position) {
                                    }
                                })
                                .setExpanded(true)  // This will enable the expand feature, (similar to android L share dialog)
                                .create();
                        dialog.show();


                    }
                } else if ((int) tvOrderConfirmPay.getTag() == R.id.cb_wechat) {
                    wechatPay(osn);
                } else {
                    aliPay(osn);
                }
                break;
            case R.id.tv_balance_pay:

                cbBalance.setChecked(true);
                break;
            case R.id.tv_ali_pay:
                cbAli.setChecked(true);
                break;
            case R.id.tv_wechat_pay:
                cbWechat.setChecked(true);
                break;
        }
    }

    private void balancePay() {
//        orderPresenter.re
    }

    private void wechatPay(String osn) {
        orderPresenter.requestWXPay(accessToken, osn);

    }

    private void aliPay(String osn) {
        orderPresenter.requestAliPay(accessToken, osn);

    }


    @Override
    protected void getBundleExtras(Bundle extras) {
        super.getBundleExtras(extras);
        finalPrice = extras.getString(EXTRA_FINAL_PRICE);
        courseName = extras.getString(EXTRA_COURSE_NAME);
        courseImageUrl = extras.getString(EXTRA_COURSE_IMAGE_URL);
        loveCount = extras.getString(EXTRA_LOVE_COUNT);
        courseId = extras.getString(EXTRA_COURSE_ID);
    }

    @Override
    public void initDialog() {

    }

    @Override
    public void getLoveGoodsListSuccess(List<LoveGoodBean> loveGoodBeans) {

    }

    @Override
    public void getLoveGoodsListFail(String msg) {

    }

    @Override
    public void getOrderIdSuccess(OrderBean order) {

    }

    @Override
    public void getOrderIdFail(String msg) {

    }

    @Override
    public void getWXPayParamsSuccess(WXPayBean wxPayBean) {
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
        Log.d(TAG, msg);
    }

    @Override
    public void getAliPayParamsSuccess(final String data) {
        Log.d(TAG, data);
        Runnable payRunnable = new Runnable() {
            @Override
            public void run() {
                //新建任务
                PayTask alipay = new PayTask(OrderConfirmActivity.this);
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
        osn = orderBean.getOsn();
        Log.d(TAG, osn);
    }

    @Override
    public void getCorderOsnFail(String msg) {
        Log.d(TAG, msg);
    }
}
