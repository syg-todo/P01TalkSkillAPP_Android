package com.tuodanhuashu.app.course.ui.fragment;

import android.arch.lifecycle.ViewModelProviders;
import android.view.View;
import android.webkit.WebView;

import com.tuodanhuashu.app.R;
import com.tuodanhuashu.app.base.HuaShuBaseFragment;
import com.tuodanhuashu.app.course.bean.MasterDetailModel;

import butterknife.BindView;

public class WebViewFragment extends HuaShuBaseFragment {

    @BindView(R.id.webview)
    WebView webView;

    MasterDetailModel model;
    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_webview;
    }

    @Override
    protected void initView(View view) {
        super.initView(view);

    }

    @Override
    protected void initData() {
        super.initData();
        model = ViewModelProviders.of(getActivity()).get(MasterDetailModel.class);
        String html = model.getCourseDetail().getValue().getIntro();

        webView.loadData(html, "text/html", "uft-8");
    }
}
