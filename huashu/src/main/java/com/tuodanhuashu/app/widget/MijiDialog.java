package com.tuodanhuashu.app.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.tuodanhuashu.app.R;

public class MijiDialog extends Dialog {

    private String imgUrl;

    private ImageView mijiIv;

    private ImageView cancelIv;

    private Context context;

    public MijiDialog(@NonNull Context context,String imgUrl) {
        super(context, R.style.dialogTransparent);
        this.imgUrl = imgUrl;
        this.context = context;
    }

    public MijiDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    protected MijiDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_miji_layout);
        initView();
    }

    private void initView(){
        mijiIv = (ImageView)findViewById(R.id.miji_iv);
        cancelIv = (ImageView)findViewById(R.id.miji_cancel_iv);
        Glide.with(context).load(imgUrl).into(mijiIv);
        cancelIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }
}
