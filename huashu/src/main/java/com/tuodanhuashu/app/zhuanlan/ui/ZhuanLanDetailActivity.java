package com.tuodanhuashu.app.zhuanlan.ui;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.company.common.CommonConstants;
import com.company.common.utils.DisplayUtil;
import com.company.common.utils.PreferencesUtils;
import com.company.common.utils.StringUtils;
import com.tuodanhuashu.app.Constants.Constants;
import com.tuodanhuashu.app.R;
import com.tuodanhuashu.app.base.HuaShuBaseActivity;
import com.tuodanhuashu.app.eventbus.EventMessage;
import com.tuodanhuashu.app.widget.MijiDialog;
import com.tuodanhuashu.app.zhuanlan.adapter.ArticleDetailAdapter;
import com.tuodanhuashu.app.zhuanlan.bean.ArticleCommentBean;
import com.tuodanhuashu.app.zhuanlan.bean.ArticleDetailBean;
import com.tuodanhuashu.app.zhuanlan.bean.ArticleDetailMutiTypeBean;
import com.tuodanhuashu.app.zhuanlan.presenter.ArticleDetailPresenter;
import com.tuodanhuashu.app.zhuanlan.view.ZhuanLanDetailView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ZhuanLanDetailActivity extends HuaShuBaseActivity implements ZhuanLanDetailView {

    @BindView(R.id.common_head_back_iv)
    ImageView commonHeadBackIv;
    @BindView(R.id.common_head_title_tv)
    TextView commonHeadTitleTv;
    @BindView(R.id.article_detail_rlv)
    RecyclerView articleDetailRlv;

    public static final String EXTRA_ARTICLE_ID = "article_id";
    @BindView(R.id.article_comment_et)
    EditText articleCommentEt;
    @BindView(R.id.article_comment_collect_iv)
    ImageView articleCommentCollectIv;
    @BindView(R.id.article_comment_share_iv)
    ImageView articleCommentShareIv;
    @BindView(R.id.article_comment_wechat_iv)
    ImageView articleCommentWechatIv;
    @BindView(R.id.get_miji_ll)
    LinearLayout getMijiLl;

    private String articleId = "";

    private ArticleDetailPresenter articleDetailPresenter;

    private ArticleDetailAdapter adapter;

    private int isCollect = 0;

    private ArticleDetailBean articleDetailBean;

    private MijiDialog mijiDialog;

    private Dialog shareDialog;

    private String commentStr = "";

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_zhuan_lan_detail;
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
        commonHeadTitleTv.setText("");
        initShareDialog();
        commonHeadTitleTv.setTextColor(getResources().getColor(R.color.colorAccent));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        articleDetailRlv.setLayoutManager(linearLayoutManager);
    }

    @Override
    protected void initData() {
        super.initData();
        articleDetailPresenter = new ArticleDetailPresenter(this, mContext);
        articleDetailPresenter.requestArticleDetail(articleId);
    }

    @Override
    protected void bindListener() {
        super.bindListener();
        articleCommentCollectIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isLogin()){
                    if (isCollect == 1) {
                        articleDetailPresenter.unCollectArticle(articleId);
                    } else {
                        articleDetailPresenter.collectArticle(articleId);
                    }
                }else{
                    goToLogin();
                }

            }
        });
        articleCommentEt.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {

                if (isLogin()) {
                    commentStr = articleCommentEt.getText().toString();
                    if (!StringUtils.isEmpty(commentStr)) {
                        showLoadingDialog();
                        articleDetailPresenter.commentArticle(commentStr, articleId);
                    } else {
                        showToast("评论内容不能为空");
                    }
                } else {
                    goToLogin();
                }


                return true;


            }
        });

        articleCommentShareIv.getDrawable().setTint(getResources().getColor(R.color.black));
        articleCommentShareIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (articleDetailBean != null) {
//                    articleDetailPresenter.shareArticle(articleDetailBean.getUrl(),articleDetailBean.getTitle(),articleDetailBean.getDescription());
//                }
                shareDialog.show();
            }
        });
        getMijiLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                articleDetailPresenter.getMiji();
            }
        });
    }

    @Override
    protected void getBundleExtras(Bundle extras) {
        super.getBundleExtras(extras);
        articleId = extras.getString(EXTRA_ARTICLE_ID, "");
    }

    @Override
    public void getArticleDetailSuccess(ArticleDetailBean articleDetailBean) {
        this.articleDetailBean = articleDetailBean;
        commonHeadTitleTv.setText(articleDetailBean.getSource());
        List<ArticleDetailMutiTypeBean> mutiTypeBeanList = new ArrayList<>();
        mutiTypeBeanList.add(new ArticleDetailMutiTypeBean(ArticleDetailMutiTypeBean.WEB, articleDetailBean.getUrl()));
        for (ArticleCommentBean commentBean : articleDetailBean.getComment()) {
            mutiTypeBeanList.add(new ArticleDetailMutiTypeBean(ArticleDetailMutiTypeBean.COMMON, commentBean));
        }
        adapter = new ArticleDetailAdapter(mContext, mutiTypeBeanList);
        articleDetailRlv.setAdapter(adapter);
        isCollect = articleDetailBean.getIs_collection();
        articleCommentCollectIv.setImageResource(articleDetailBean.getIs_collection() == 1 ? R.drawable.collected : R.drawable.uncollect);
    }

    @Override
    public void getArticleDetailFail(String msg) {

    }

    @Override
    public void collectSuccess(String msg) {
        showToast(msg);
        isCollect = 1;
        articleCommentCollectIv.setImageResource(R.drawable.collected);
    }

    @Override
    public void collectFail(String msg) {

    }

    @Override
    public void unCollectSuccess(String msg) {
        showToast(msg);
        isCollect = 0;
        articleCommentCollectIv.setImageResource(R.drawable.uncollect);

    }

    @Override
    public void unCollectFail(String msg) {

    }

    @Override
    public void getMijiSuccess(String url) {
        mijiDialog = new MijiDialog(mContext, url);
        mijiDialog.show();
    }

    @Override
    public void getMijiFail() {

    }

    @Override
    public void CommentSuccess(String msg) {
        cancalLoadingDialog();
        showToast(msg);
        ArticleCommentBean articleCommentBean = new ArticleCommentBean();
        articleCommentBean.setContent(commentStr);
        articleCommentBean.setHeade_img(PreferencesUtils.getString(mContext, CommonConstants.KEY_IMG_URL, ""));
        articleCommentBean.setname(PreferencesUtils.getString(mContext, CommonConstants.KEY_NICK_NAME, ""));
        adapter.addData(new ArticleDetailMutiTypeBean(ArticleDetailMutiTypeBean.COMMON, articleCommentBean));
        adapter.notifyDataSetChanged();

    }

    @Override
    protected void onDestroy() {
        if (mijiDialog != null) {
            if (mijiDialog.isShowing()) {
                mijiDialog.dismiss();
            }
            mijiDialog = null;
        }
        if (shareDialog != null) {
            if (shareDialog.isShowing()) {
                shareDialog.dismiss();
            }
            shareDialog = null;
        }
        super.onDestroy();
    }

    private void initShareDialog() {
        shareDialog = new Dialog(mContext, R.style.BottomDialog_Animation);
        View view = getLayoutInflater().inflate(R.layout.dialog_share_layout, null);
        shareDialog.setContentView(view);
        LinearLayout youIv = (LinearLayout) view.findViewById(R.id.share_you_ll);
        LinearLayout quanLl = (LinearLayout) view.findViewById(R.id.share_quan_ll);
        TextView cancelTv = (TextView) view.findViewById(R.id.share_cancel_tv);
        cancelTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareDialog.dismiss();
            }
        });
        youIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (articleDetailBean != null) {
                    shareDialog.dismiss();

                    articleDetailPresenter.shareArticle(articleDetailBean.getUrl(), articleDetailBean.getTitle(), articleDetailBean.getDescription(), "you");
                }
            }
        });
        quanLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (articleDetailBean != null) {
                    shareDialog.dismiss();

                    articleDetailPresenter.shareArticle(articleDetailBean.getUrl(), articleDetailBean.getTitle(), articleDetailBean.getDescription(), "quan");
                }
            }
        });
        shareDialog.setCanceledOnTouchOutside(true);
        Window window = shareDialog.getWindow();
        window.setGravity(Gravity.BOTTOM);
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = DisplayUtil.dip2px(mContext, 166.0f);
        window.setAttributes(params);
    }

    @Override
    public void onEvent(EventMessage message) {
        super.onEvent(message);
        switch (message.getTag()){
            case Constants.EVENT_TAG.TAG_WX_SHARE_SUCCESS:
                showToast("分享成功");
                break;
            case Constants.EVENT_TAG.TAG_WX_SHARE_FAIL:
                showToast("分享失败");
                break;
        }
    }
}
