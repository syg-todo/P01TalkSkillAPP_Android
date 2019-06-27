package com.company.common.utils;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

/**
 * Created by Administrator on 2018/8/19 0019.
 */
public class ToastUtils {
    public static void showToast(Context context, String msg) {
        Toast toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }
}
