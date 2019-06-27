package com.tuodanhuashu.app.MemberCenter.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.tuodanhuashu.app.R;
import com.tuodanhuashu.app.base.HuaShuBaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class JoinUsActivity extends HuaShuBaseActivity {

    @BindView(R.id.common_head_back_iv)
    ImageView commonHeadBackIv;
    @BindView(R.id.common_head_title_tv)
    TextView commonHeadTitleTv;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_join_us;
    }

    @Override
    protected void initView() {
        super.initView();
        commonHeadTitleTv.setText("入驻联系");
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
    }
}
