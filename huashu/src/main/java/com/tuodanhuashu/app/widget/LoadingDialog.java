package com.tuodanhuashu.app.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.tuodanhuashu.app.R;

public class LoadingDialog extends Dialog {
    public LoadingDialog(@NonNull Context context) {
        super(context, R.style.dialogTransparent);
    }

    public LoadingDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    protected LoadingDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_progress_layout);
        getWindow().setDimAmount(0f);
    }
}
