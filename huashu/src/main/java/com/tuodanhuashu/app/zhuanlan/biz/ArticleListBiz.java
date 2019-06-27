package com.tuodanhuashu.app.zhuanlan.biz;

import android.content.Context;

import com.company.common.net.OkNetUtils;
import com.company.common.net.OnRequestListener;
import com.tuodanhuashu.app.Constants.Constants;

import java.util.HashMap;
import java.util.Map;

public class ArticleListBiz {

    private Context context;

    private OnRequestListener listener;

    public ArticleListBiz(Context context, OnRequestListener listener) {
        this.context = context;
        this.listener = listener;
    }

    public void requestArticleListByKeywords(int tag, String key_words, String page, String page_size){
        Map<String,String> params = new HashMap<>();
        params.put("key_words",key_words);
        params.put("page",page);
        params.put("page_size",page_size);
        OkNetUtils.get(tag, Constants.URL.BASE_URL+Constants.URL.ARTICLE_KEYWORDS_URL,params,context,listener);
    }

    public void requestArticleListByCatId(int tag,String cat_id,String page,String page_size){
        Map<String,String> params = new HashMap<>();
        params.put("cat_id",cat_id);
        params.put("page",page);
        params.put("page_size",page_size);
        OkNetUtils.get(tag, Constants.URL.BASE_URL+Constants.URL.ARTICLE_CATID_URL,params,context,listener);
    }

    public void requestArticleClassList(int tag){
        Map<String,String> params = new HashMap<>();
        OkNetUtils.get(tag, Constants.URL.BASE_URL+Constants.URL.ARTICLE_CAT_LIST_URL,params,context,listener);
    }

    public void requestNewArticle(int tag,String page,String page_size){
        Map<String,String> params = new HashMap<>();

        params.put("page",page);
        params.put("page_size",page_size);
        OkNetUtils.get(tag, Constants.URL.BASE_URL+Constants.URL.ARTICLE_NEW_URL,params,context,listener);
    }

    public void requestChoicesList(int tag,String page,String page_size){
        Map<String,String> params = new HashMap<>();

        params.put("page",page);
        params.put("page_size",page_size);
        OkNetUtils.get(tag, Constants.URL.BASE_URL+Constants.URL.ARTICLE_CHOICE_URL,params,context,listener);
    }

    public void requestMyList(int tag,String page,String page_size){
        Map<String,String> params = new HashMap<>();

        params.put("page",page);
        params.put("page_size",page_size);
        OkNetUtils.get(tag, Constants.URL.BASE_URL+Constants.URL.MY_ARTICLE_URL,params,context,listener);
    }
}
