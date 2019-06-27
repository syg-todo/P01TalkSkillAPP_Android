package com.tuodanhuashu.app.zhuanlan.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.company.common.utils.StringUtils;
import com.tuodanhuashu.app.R;
import com.tuodanhuashu.app.base.HuaShuBaseActivity;
import com.tuodanhuashu.app.zhuanlan.adapter.ZhuanLanListAdapter;
import com.tuodanhuashu.app.zhuanlan.bean.ArticleClassBean;
import com.tuodanhuashu.app.zhuanlan.bean.ArticleListItemBean;
import com.tuodanhuashu.app.zhuanlan.presenter.ArticleListPresenter;
import com.tuodanhuashu.app.zhuanlan.view.ZhuanLanListView;
import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class ZhuanLanSearchActivity extends HuaShuBaseActivity implements ZhuanLanListView {


    @BindView(R.id.huashu_list_back_iv)
    ImageView huashuListBackIv;
    @BindView(R.id.huashu_list_search_btn)
    Button huashuListSearchBtn;
    @BindView(R.id.zhuanlan_search_refresheader)
    MaterialHeader zhuanlanSearchRefresheader;
    @BindView(R.id.zhuanlan_search_rlv)
    RecyclerView zhuanlanSearchRlv;
    @BindView(R.id.zhuanlan_search_refresfooter)
    ClassicsFooter zhuanlanSearchRefresfooter;
    @BindView(R.id.zhuanlan_search_refreshLayout)
    SmartRefreshLayout zhuanlanSearchRefreshLayout;
    @BindView(R.id.huashu_list_search_et)
    EditText huashuListSearchEt;

    private ZhuanLanListAdapter adapter;

    private ArticleListPresenter articleListPresenter;

    public static final String EXTRA_KEY_WORDS = "key_words";



    private String keyWords = "";

    private String classId = "";

    private int page = 1;

    private int page_size = 10;

    private List<ArticleListItemBean> articleListItemBeanList;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_zhuan_lan_search;
    }

    @Override
    protected void getBundleExtras(Bundle extras) {
        super.getBundleExtras(extras);

        keyWords = extras.getString(EXTRA_KEY_WORDS, "");
    }


    @Override
    protected void initView() {
        super.initView();
        zhuanlanSearchRefreshLayout.setEnableAutoLoadMore(false);
        zhuanlanSearchRefresheader.setColorSchemeColors(mContext.getResources().getColor(R.color.colorAccent));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        zhuanlanSearchRlv.setLayoutManager(linearLayoutManager);
    }

    @Override
    protected void bindListener() {
        super.bindListener();
        huashuListBackIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        zhuanlanSearchRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                articleListPresenter.requestAticleListByKeywords(keyWords, page + "", page_size + "");
            }
        });
        zhuanlanSearchRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
               // articleListItemBeanList.clear();
                page = 1;
                articleListPresenter.requestAticleListByKeywords(keyWords,page+"",page_size+"");

            }
        });
        huashuListSearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                keyWords = huashuListSearchEt.getText().toString().trim();
                if(!StringUtils.isEmpty(keyWords)){
                   // articleListItemBeanList.clear();
                    page = 1;
                    articleListPresenter.requestAticleListByKeywords(keyWords,page+"",page_size+"");
                }
            }
        });
    }

    @Override
    protected void initData() {
        super.initData();
        huashuListSearchEt.setText(keyWords);

        articleListPresenter = new ArticleListPresenter(mContext, this);
        articleListItemBeanList = new ArrayList<>();

        adapter = new ZhuanLanListAdapter(mContext, R.layout.item_zhuanlan_list_layout, articleListItemBeanList);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ArticleListItemBean bean = articleListItemBeanList.get(position);
                Bundle bundle = new Bundle();
                bundle.putString(ZhuanLanDetailActivity.EXTRA_ARTICLE_ID, bean.getId());
                readyGo(ZhuanLanDetailActivity.class, bundle);
            }
        });
        zhuanlanSearchRlv.setAdapter(adapter);
        articleListPresenter.requestAticleListByKeywords(keyWords, page + "", page_size + "");

    }

    @Override
    public void getArticleListSuccess(List<ArticleListItemBean> articleListItemBeanList) {
        zhuanlanSearchRefreshLayout.finishLoadMore(200);
        zhuanlanSearchRefreshLayout.finishRefresh(200);
        if (articleListItemBeanList != null && articleListItemBeanList.size() > 0) {
            if(page==1){
                this.articleListItemBeanList.clear();

            }
            this.articleListItemBeanList.addAll(articleListItemBeanList);
            adapter.setNewData(this.articleListItemBeanList);
            page++;
        } else {

            showToast("已无更多");
        }
    }

    @Override
    public void getArticleListFail(String msg) {

    }

    @Override
    public void getClassListSuccess(List<ArticleClassBean> classBeanList) {

    }

    @Override
    public void getClassListFail(String msg) {

    }


}
