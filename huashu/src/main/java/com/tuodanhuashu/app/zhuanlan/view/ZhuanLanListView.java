package com.tuodanhuashu.app.zhuanlan.view;

import com.company.common.base.BaseView;
import com.tuodanhuashu.app.zhuanlan.bean.ArticleClassBean;
import com.tuodanhuashu.app.zhuanlan.bean.ArticleListItemBean;

import java.util.List;

public interface ZhuanLanListView extends BaseView {

    public void getArticleListSuccess(List<ArticleListItemBean> articleListItemBeanList);

    public void getArticleListFail(String msg);

    public void getClassListSuccess(List<ArticleClassBean> classBeanList);

    public void getClassListFail(String msg);

    
}
