package com.tuodanhuashu.app.zhuanlan.biz;

import android.content.Context;

import com.company.common.net.OkNetUtils;
import com.company.common.net.OnRequestListener;
import com.tuodanhuashu.app.Constants.Constants;

import java.util.HashMap;
import java.util.Map;

public class ArticleDetailBiz {

    private OnRequestListener listener;

    private Context context;

    public ArticleDetailBiz(OnRequestListener listener, Context context) {
        this.listener = listener;
        this.context = context;
    }

    public void requestArticleDetailbean(int tag,String news_id){
        Map<String,String> params = new HashMap<>();
        params.put("news_id",news_id);
        OkNetUtils.get(tag, Constants.URL.BASE_URL+Constants.URL.ARTICLE_DETAIL_URL,params,context,listener);
    }

    public void collectArticle(int tag,String news_id){
        Map<String,String> params = new HashMap<>();
        params.put("news_id",news_id);
        OkNetUtils.get(tag, Constants.URL.BASE_URL+Constants.URL.COLLECT_ARTICLE_URL,params,context,listener);

    }

    public void unCollectArticle(int tag,String news_id){
        Map<String,String> params = new HashMap<>();
        params.put("news_id",news_id);
        OkNetUtils.get(tag, Constants.URL.BASE_URL+Constants.URL.UNCOLLECT_ARTICLE_URL,params,context,listener);

    }

    public void commentArticle(int tag,String content,String article_id){
        Map<String,String> params = new HashMap<>();
        params.put("content",content);
        params.put("article_id",article_id);
        OkNetUtils.post(tag,Constants.URL.BASE_URL+Constants.URL.COMMENT_ARTICLE_URL,params,context,listener);
    }

    public void getMiji(int tag){
        Map<String,String> params = new HashMap<>();
        OkNetUtils.get(tag,Constants.URL.BASE_URL+Constants.URL.GET_MIJI_URL,params,context,listener);
    }
}
