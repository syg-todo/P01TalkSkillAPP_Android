package com.tuodanhuashu.app.zhuanlan.view;

import com.company.common.base.BaseView;
import com.tuodanhuashu.app.zhuanlan.bean.ArticleDetailBean;

public interface ZhuanLanDetailView extends BaseView {

    public void getArticleDetailSuccess(ArticleDetailBean articleDetailBean);

    public void getArticleDetailFail(String msg);

    public void collectSuccess(String msg);

    public void collectFail(String msg);

    public void unCollectSuccess(String msg);

    public void unCollectFail(String msg);

    public void getMijiSuccess(String url);

    public void getMijiFail();

    public void CommentSuccess(String msg);
}
