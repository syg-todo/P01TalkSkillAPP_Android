package com.tuodanhuashu.app.web;


import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.company.common.utils.StringUtils;
import com.tuodanhuashu.app.R;
import com.tuodanhuashu.app.base.HuaShuBaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CommonWebActivity extends HuaShuBaseActivity {


    @BindView(R.id.common_head_back_iv)
    ImageView commonHeadBackIv;
    @BindView(R.id.common_head_title_tv)
    TextView commonHeadTitleTv;
    @BindView(R.id.common_wv)
    WebView commonWv;

    public static final String EXTRA_WV_TITLE = "extra_wv_title";

    public static final String EXTRA_WV_URL = "extra_wv_url";

    public static final String EXTRA_WV_DATA = "extra_wv_data";

    private String title = "";

    private String url = "";

    private String data = "";

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_common_web;
    }

    @Override
    protected void initView() {
        super.initView();
        commonHeadTitleTv.setText(title);
        commonHeadBackIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        WebSettings webSettings = commonWv.getSettings();
        webSettings.setJavaScriptEnabled(true);

    }

    @Override
    protected void initData() {
        super.initData();
        if(StringUtils.isEmpty(url)){
            commonWv.loadData(data, "text/html; charset=UTF-8", null);
        }else{
            commonWv.loadUrl(url);
        }

    }

    @Override
    protected void getBundleExtras(Bundle extras) {
        super.getBundleExtras(extras);
        title = extras.getString(EXTRA_WV_TITLE,"");
        url = extras.getString(EXTRA_WV_URL,"");
        data = extras.getString(EXTRA_WV_DATA,"");
    }
}
