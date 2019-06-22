package com.tuodanhuashu.app.course.ui.fragment;


import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.tuodanhuashu.app.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PaymentChooseFragment extends DialogFragment implements View.OnClickListener {

    RadioButton radioAlipay;
    RadioButton radioWechat;
    RadioButton radioBalance;
    TextView tvPaymentBuy;
    TextView tvPaymentCancel;
    RadioGroup radioGroup;


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        Dialog dialog = new Dialog(getActivity(), R.style.dialog_style);

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.payment_choose_fragment_layout);
        dialog.setCanceledOnTouchOutside(true);


        radioAlipay = dialog.findViewById(R.id.radio_ali);
        radioWechat = dialog.findViewById(R.id.radio_wechat);
        radioBalance = dialog.findViewById(R.id.radio_balance);
        tvPaymentBuy = dialog.findViewById(R.id.tv_payment_buy);
        tvPaymentCancel = dialog.findViewById(R.id.tv_payment_cancel);
        radioGroup = dialog.findViewById(R.id.radio_group);
        tvPaymentBuy.setTag(radioBalance.getId());
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radio_balance:
                        tvPaymentBuy.setTag(checkedId);
                        break;
                    case R.id.radio_wechat:
                        tvPaymentBuy.setTag(checkedId);
                        break;
                    case R.id.radio_ali:
                        tvPaymentBuy.setTag(checkedId);
                        break;
                }
            }
        });

        tvPaymentBuy.setOnClickListener(this);
        tvPaymentCancel.setOnClickListener(this);

        Window window = dialog.getWindow();
        WindowManager.LayoutParams layoutParams;
        if (window != null) {
            layoutParams = window.getAttributes();
            layoutParams.gravity = Gravity.BOTTOM;
            layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
            window.setAttributes(layoutParams);
        }

        return dialog;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_payment_buy:
                if ((int)v.getTag()==R.id.radio_balance){
                    Toast.makeText(getActivity(), "余额支付", Toast.LENGTH_SHORT).show();
                }else if ((int)v.getTag() == R.id.radio_wechat){
                    Toast.makeText(getActivity(), "微信支付", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(getActivity(), "支付宝支付", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.tv_payment_cancel:
                dismiss();
                break;
        }
    }
}
