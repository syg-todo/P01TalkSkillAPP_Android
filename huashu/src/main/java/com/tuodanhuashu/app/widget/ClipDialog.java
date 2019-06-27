package com.tuodanhuashu.app.widget;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.company.common.utils.ToastUtils;
import com.tuodanhuashu.app.R;

import java.util.List;

public class ClipDialog extends Dialog {

    private LinearLayout qqLl;

    private LinearLayout wechatLl;

    private ImageView closeIv;

    private Context context;



    public ClipDialog(@NonNull Context context) {
        super(context,R.style.dialogTransparent);
        this.context = context;
    }

    public ClipDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    protected ClipDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_clip_layout);
        initView();
    }

    private void initView() {
        qqLl = findViewById(R.id.open_qq_ll);
        wechatLl = findViewById(R.id.open_wx_ll);
        closeIv = findViewById(R.id.clip_close_iv);
        qqLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isQQClientAvailable(context)){
                    Intent i = context.getPackageManager().getLaunchIntentForPackage("com.tencent.mobileqq");
                    context.startActivity(i);
                }else{
                    ToastUtils.showToast(context,"QQ未安装");
                }
            }
        });

        wechatLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isWeixinAvilible(context)){
                    Intent i = context.getPackageManager().getLaunchIntentForPackage("com.tencent.mm");
                    context.startActivity(i);
                }else{
                    ToastUtils.showToast(context,"微信未安装");
                }
            }
        });
        closeIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    private boolean isQQClientAvailable(Context context) {
        final PackageManager packageManager = context.getPackageManager();
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                if (pn.equals("com.tencent.mobileqq")) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean isWeixinAvilible(Context context) {
        final PackageManager packageManager = context.getPackageManager();// 获取packagemanager
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);// 获取所有已安装程序的包信息
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                if (pn.equals("com.tencent.mm")) {
                    return true;
                }
            }
        }
        return false;
    }

}
