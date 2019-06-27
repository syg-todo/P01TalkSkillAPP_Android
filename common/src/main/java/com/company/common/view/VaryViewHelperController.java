package com.company.common.view;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.company.common.R;

public class VaryViewHelperController {

    private IVaryViewHelper helper;

    public VaryViewHelperController(View view) {
        this(new VaryViewHelper(view));
    }

    public VaryViewHelperController(IVaryViewHelper helper) {
        super();
        this.helper = helper;
    }

    public void showNetworkError(int layoutRes,View.OnClickListener onClickListener) {
        View layout = helper.inflate(layoutRes);
//        Button againBtn = (Button) layout.findViewById(R.id.pager_error_loadingAgain);
//        if (null != onClickListener) {
//            againBtn.setOnClickListener(onClickListener);
//        }
        helper.showLayout(layout);
    }

    public void showEmpty(int layoutRes,String emptyMsg) {
        View layout = helper.inflate(layoutRes);
//        TextView textView = (TextView) layout.findViewById(R.id.tv_no_data);
//        if (!TextUtils.isEmpty(emptyMsg)) {
//            textView.setText(emptyMsg);
//        }
        helper.showLayout(layout);
    }

    public void showLoading(int layoutRes) {
        View layout = helper.inflate(layoutRes);
        helper.showLayout(layout);
    }

    public void restore() {
        helper.restoreView();
    }
}
