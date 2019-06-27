package com.tuodanhuashu.app.zhuanlan.presenter;

import android.content.Context;

import com.company.common.base.BasePresenter;
import com.company.common.base.BaseView;
import com.company.common.net.ServerResponse;
import com.company.common.utils.JsonUtils;
import com.tuodanhuashu.app.zhuanlan.bean.ArticleClassBean;
import com.tuodanhuashu.app.zhuanlan.bean.ArticleListItemBean;
import com.tuodanhuashu.app.zhuanlan.biz.ArticleListBiz;
import com.tuodanhuashu.app.zhuanlan.view.ZhuanLanListView;

import java.util.List;

public class ArticleListPresenter extends BasePresenter {

    private Context context;

    private ZhuanLanListView zhuanLanListView;

    private ArticleListBiz articleListBiz;

    private static final int TAG_KEYWORDS = 1;

    private static final int TAG_CLASS = 2;
    private static final int TAG_CLASS_LIST = 3;

    private static final int TAG_MY_LIST = 4;

    public ArticleListPresenter(Context context, ZhuanLanListView zhuanLanListView) {
        this.context = context;
        this.zhuanLanListView = zhuanLanListView;
        articleListBiz = new ArticleListBiz(context,this);
    }


    public void requestAticleListByKeywords(String key_words,String page,String page_size){
        articleListBiz.requestArticleListByKeywords(TAG_KEYWORDS,key_words,page,page_size);
    }

    public void requestArticleListByCatId(String cat_id,String page,String page_size){
        articleListBiz.requestArticleListByCatId(TAG_CLASS,cat_id,page,page_size);
    }

    public void requestNewArticleList(String page,String page_size){
        articleListBiz.requestNewArticle(TAG_KEYWORDS,page,page_size);
    }

    public void requestChoiceArticleList(String page,String page_size){
        articleListBiz.requestChoicesList(TAG_KEYWORDS,page,page_size);
    }

    public void requestClassList(){
        articleListBiz.requestArticleClassList(TAG_CLASS_LIST);
    }

    public void requestMyList(String page,String page_size){
        articleListBiz.requestMyList(TAG_MY_LIST,page,page_size);
    }

    @Override
    public void OnSuccess(ServerResponse serverResponse, int tag) {
        super.OnSuccess(serverResponse, tag);
        switch (tag){
            case TAG_CLASS_LIST:
                List<ArticleClassBean> classBeanList = JsonUtils.getJsonToList(serverResponse.getData(),ArticleClassBean.class);
                zhuanLanListView.getClassListSuccess(classBeanList);
                break;
            default:
                List<ArticleListItemBean> articleListItemBeanList = JsonUtils.getJsonToList(serverResponse.getData(),ArticleListItemBean.class);
                zhuanLanListView.getArticleListSuccess(articleListItemBeanList);
                break;
        }

    }

    @Override
    public void onFail(String msg, int code, int tag) {
        super.onFail(msg, code, tag);
        zhuanLanListView.getArticleListFail(msg);
    }

    @Override
    public BaseView getBaseView() {
        return zhuanLanListView;
    }
}
