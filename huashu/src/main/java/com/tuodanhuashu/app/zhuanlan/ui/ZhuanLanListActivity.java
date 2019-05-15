package com.tuodanhuashu.app.zhuanlan.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;

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
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class ZhuanLanListActivity extends HuaShuBaseActivity implements ZhuanLanListView {

    @BindView(R.id.common_head_back_iv)
    ImageView commonHeadBackIv;
    @BindView(R.id.common_head_title_tv)
    TextView commonHeadTitleTv;
    @BindView(R.id.tablayout)
    TabLayout tablayout;
    @BindView(R.id.zhuanlan_list_refresheader)
    MaterialHeader zhuanlanListRefresheader;
    @BindView(R.id.zhuanlan_list_rlv)
    RecyclerView zhuanlanListRlv;
    @BindView(R.id.zhuanlan_list_refreshLayout)
    SmartRefreshLayout zhuanlanListRefreshLayout;

    private ZhuanLanListAdapter adapter;

    //  private List<String> zhuanlanList;

    private ArticleListPresenter articleListPresenter;

    public static final String EXTRA_ENTER_TYPE = "enter_type";//1:最新 2精选 3：分类 4:我的文章

    public static final String EXTRA_KEY_WORDS = "key_words";

    public static final String EXTRA_CLASS_ID = "class_id";

    private int enterType = 1;

    private String keyWords = "";

    private String classId = "";

    private int page = 1;

    private int page_size = 10;

    private List<ArticleListItemBean> articleListItemBeanList;

    private List<ArticleClassBean> classBeanList;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_zhuan_lan_list;
    }

    @Override
    protected void initView() {
        super.initView();

        zhuanlanListRefreshLayout.setEnableAutoLoadMore(false);
        zhuanlanListRefresheader.setColorSchemeColors(mContext.getResources().getColor(R.color.colorAccent));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        zhuanlanListRlv.setLayoutManager(linearLayoutManager);
        tablayout.setVisibility(enterType == 3 ? View.VISIBLE : View.GONE);

    }

    private void requestArticleList(boolean isLoadMoreOrRefresh) {
        if (enterType == 1) {
            articleListPresenter.requestNewArticleList(page + "", page_size + "");
        } else if (enterType == 2) {
            articleListPresenter.requestChoiceArticleList(page + "", page_size + "");
        } else if (enterType == 3) {
            //articleListPresenter.requestClassList();
            if (isLoadMoreOrRefresh) {
                articleListPresenter.requestArticleListByCatId(classId, page + "", page_size + "");

            }
        } else {
            articleListPresenter.requestMyList(page + "", page_size + "");
        }
    }

    @Override
    protected void initData() {
        super.initData();
        articleListPresenter = new ArticleListPresenter(mContext, this);
        if (enterType == 3) {
            articleListPresenter.requestClassList();
        }
        requestArticleList(false);
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
        zhuanlanListRlv.setAdapter(adapter);

    }


    @Override
    protected void bindListener() {
        super.bindListener();
        commonHeadBackIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        String title = "文章分类";
        switch (enterType) {
            case 1:
                title = "最新文章";
                break;
            case 2:
                title = "精选文章";
                break;
            case 3:
                title = "文章分类";
                break;
            case 4:
                title = "我的文章";
                break;
        }
        commonHeadTitleTv.setText(title);

        zhuanlanListRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {

                requestArticleList(true);
            }
        });
        zhuanlanListRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page = 1;
                requestArticleList(true);
            }
        });
        tablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(final TabLayout.Tab tab) {
                page = 1;
                articleListItemBeanList.clear();
                classId = classBeanList.get(tab.getPosition()).getId();
                articleListPresenter.requestArticleListByCatId(classId, page + "", page_size + "");
                tablayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                    tab.select();
                    }
                }, 100);

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    @Override
    public void getArticleListSuccess(List<ArticleListItemBean> articleListItemBeanList) {
        zhuanlanListRefreshLayout.finishLoadMore(200);
        zhuanlanListRefreshLayout.finishRefresh(200);
        if (page == 1) {
            this.articleListItemBeanList.clear();

        }
        this.articleListItemBeanList.addAll(articleListItemBeanList);
        adapter.setNewData(this.articleListItemBeanList);
        if (articleListItemBeanList != null && articleListItemBeanList.size() > 0) {


            page++;
        } else {

            showToast("已无更多");
        }


    }

    @Override
    public void getArticleListFail(String msg) {

    }

    @Override
    public void getClassListSuccess(final List<ArticleClassBean> classBeanList) {
        this.classBeanList = classBeanList;
        for (ArticleClassBean bean : classBeanList) {
            tablayout.addTab(tablayout.newTab().setText(bean.getClass_name()), bean.getId().equals(classId));
        }


    }

    @Override
    public void getClassListFail(String msg) {

    }


    @Override
    protected void getBundleExtras(Bundle extras) {
        super.getBundleExtras(extras);
        enterType = extras.getInt(EXTRA_ENTER_TYPE, 1);
        keyWords = extras.getString(EXTRA_KEY_WORDS, "");
        classId = extras.getString(EXTRA_CLASS_ID, "");
    }
}
