package com.tuodanhuashu.app.MemberCenter.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.company.common.net.OnRequestListener;
import com.company.common.net.ServerResponse;
import com.company.common.utils.StringUtils;
import com.tuodanhuashu.app.MemberCenter.biz.FeedBackBiz;
import com.tuodanhuashu.app.R;
import com.tuodanhuashu.app.base.HuaShuBaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FeedBackActivity extends HuaShuBaseActivity implements OnRequestListener {


    @BindView(R.id.common_head_back_iv)
    ImageView commonHeadBackIv;
    @BindView(R.id.common_head_title_tv)
    TextView commonHeadTitleTv;
    @BindView(R.id.login_btn)
    Button loginBtn;
    @BindView(R.id.feed_back_et)
    EditText feedBackEt;

    private FeedBackBiz feedBackBiz;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_feed_back;
    }

    @Override
    protected void bindListener() {
        super.bindListener();
    }

    @Override
    protected void initView() {
        super.initView();
        commonHeadBackIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        commonHeadTitleTv.setText("意见反馈");
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = feedBackEt.getText().toString();
                if(StringUtils.isEmpty(content)){
                    showToast("请输入意见反馈");
                    return;
                }else{
                    feedBackBiz.submitFeedBack(1,content);
                }

            }
        });
    }

    @Override
    protected void initData() {
        super.initData();
        feedBackBiz = new FeedBackBiz(mContext, this);
    }

    @Override
    public void OnSuccess(ServerResponse serverResponse, int tag) {
        showToast(serverResponse.getMsg());
        finish();
    }

    @Override
    public void onFail(String msg, int code, int tag) {
        showToast(msg);
    }

    @Override
    public void onError(int tag) {

    }


}
