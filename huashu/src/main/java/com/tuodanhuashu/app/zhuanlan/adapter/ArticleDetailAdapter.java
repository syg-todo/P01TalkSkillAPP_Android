package com.tuodanhuashu.app.zhuanlan.adapter;

import android.content.Context;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.tuodanhuashu.app.R;
import com.tuodanhuashu.app.zhuanlan.bean.ArticleDetailMutiTypeBean;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ArticleDetailAdapter extends BaseMultiItemQuickAdapter<ArticleDetailMutiTypeBean, BaseViewHolder> {

    private Context context;


    public ArticleDetailAdapter(Context context,List<ArticleDetailMutiTypeBean> data) {
        super(data);
        this.context = context;
        addItemType(ArticleDetailMutiTypeBean.WEB, R.layout.article_web_layout);
        addItemType(ArticleDetailMutiTypeBean.COMMON, R.layout.item_article_detail_comment_layout);
    }

    @Override
    protected void convert(BaseViewHolder helper, ArticleDetailMutiTypeBean item) {
        switch (helper.getItemViewType()){
            case ArticleDetailMutiTypeBean.WEB:
                WebView wv = helper.getView(R.id.article_detail_wv);

                wv.loadUrl(item.getUrl());
                //wv.loadData(item.get);
                break;
            case ArticleDetailMutiTypeBean.COMMON:
                CircleImageView headIv = helper.getView(R.id.comment_head_iv);
                TextView userTv = helper.getView(R.id.comment_user_name_tv);
                TextView contentTv = helper.getView(R.id.comment_content_tv);
                Glide.with(context).load(item.getArticleCommentBean().getHeade_img()).into(headIv);
                userTv.setText(item.getArticleCommentBean().getname());
                contentTv.setText(item.getArticleCommentBean().getContent());
                break;
        }
    }
}
