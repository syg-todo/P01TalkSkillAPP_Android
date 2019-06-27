package com.tuodanhuashu.app.huashu.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.company.common.utils.StringUtils;
import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.tuodanhuashu.app.R;
import com.tuodanhuashu.app.base.HuaShuBaseActivity;
import com.tuodanhuashu.app.huashu.adapter.HuaShuListAdapter;
import com.tuodanhuashu.app.huashu.bean.MutiTypeBean;
import com.tuodanhuashu.app.huashu.bean.TalkSkillListItemBean;
import com.tuodanhuashu.app.huashu.bean.TalkSkillResultBean;
import com.tuodanhuashu.app.huashu.presenter.TalkSkillListPresenter;
import com.tuodanhuashu.app.huashu.view.TalkSkillListView;
import com.tuodanhuashu.app.widget.MijiDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class HuaShuaListActivity extends HuaShuBaseActivity implements TalkSkillListView {

    @BindView(R.id.huashu_list_back_iv)
    ImageView huashuListBackIv;
    @BindView(R.id.huashu_list_search_btn)
    Button huashuListSearchBtn;
    @BindView(R.id.huashu_list_refresheader)
    MaterialHeader huashuListRefresheader;
    @BindView(R.id.huashu_list_rlv)
    RecyclerView huashuListRlv;
    @BindView(R.id.huashu_list_refreshLayout)
    SmartRefreshLayout huashuListRefreshLayout;
    @BindView(R.id.huashu_list_search_et)
    EditText huashuListSearchEt;

    private HuaShuListAdapter adapter;

    private List<MutiTypeBean> list;

    private boolean isVip = false;

    private String keywords = "";

    private String classId = "0";

    private int currentPage = 1;

    private int pageSize = 10;

    public static final String EXTRA_IS_VIP = "is_vip";

    public static final String EXTRA_KEY_WORDS = "key_words";

    public static final String EXTRA_KEY_CLASSID = "key_classid";

    private TalkSkillListPresenter talkSkillListPresenter;

    private TalkSkillResultBean talkSkillResultBean;

    private boolean isRefresh = true;

    private MijiDialog dialog;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_hua_shua_list;
    }

    @Override
    protected void getBundleExtras(Bundle extras) {
        super.getBundleExtras(extras);
        isVip = extras.getBoolean(EXTRA_IS_VIP, false);
        keywords = extras.getString(EXTRA_KEY_WORDS, "");
        classId = extras.getString(EXTRA_KEY_CLASSID,"");

    }

    @Override
    protected void initData() {
        super.initData();
        talkSkillListPresenter = new TalkSkillListPresenter(this, mContext);
        talkSkillListPresenter.requestTalkSkillList(keywords, classId + "", currentPage + "", pageSize + "");
        list = new ArrayList<>();

    }

    @Override
    protected void initView() {
        super.initView();
        huashuListRefreshLayout.setEnableAutoLoadMore(false);
        huashuListRefresheader.setColorSchemeColors(mContext.getResources().getColor(R.color.colorAccent));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        huashuListRlv.setLayoutManager(linearLayoutManager);
        if (!isVip) {
            huashuListRefreshLayout.setEnableLoadMore(false);
        }
        huashuListSearchEt.setText(keywords);
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
        huashuListRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                for (int i = 0; i < 2; i++) {
                    list.add(new MutiTypeBean(1));
                    adapter.setNewData(list);
                }

                huashuListRefreshLayout.finishLoadMore(2000);
            }
        });
        huashuListSearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                keywords = huashuListSearchEt.getText().toString();
                if(StringUtils.isEmpty(keywords)){
                    showToast("请输入搜索内容");
                    return;
                }
                talkSkillListPresenter.requestTalkSkillList(keywords, classId + "", currentPage + "", pageSize + "");


            }
        });
    }

    @Override
    public void getTalkListSuccess(TalkSkillResultBean talkSkillResultBean) {
        this.talkSkillResultBean = talkSkillResultBean;
        List<TalkSkillListItemBean> talkSkillListItemBeanList = talkSkillResultBean.getList();
        boolean isVip = talkSkillResultBean.isVip();
        if (isRefresh) {
            list.clear();
            if (talkSkillListItemBeanList == null || talkSkillListItemBeanList.size() <= 0) {
                list.add(new MutiTypeBean(MutiTypeBean.AD, talkSkillResultBean.getAdvertising()));
            } else if (talkSkillListItemBeanList.size() == 1) {
                list.add(new MutiTypeBean(MutiTypeBean.COMMON, talkSkillListItemBeanList.get(0)));
                list.add(new MutiTypeBean(MutiTypeBean.AD, talkSkillResultBean.getAdvertising()));
            } else if (talkSkillListItemBeanList.size() == 2) {
                list.add(new MutiTypeBean(MutiTypeBean.COMMON, talkSkillListItemBeanList.get(0)));
                list.add(new MutiTypeBean(MutiTypeBean.AD, talkSkillResultBean.getAdvertising()));
                list.add(new MutiTypeBean(MutiTypeBean.COMMON, talkSkillListItemBeanList.get(1)));
            } else if (talkSkillListItemBeanList.size() >= 3) {
                list.add(new MutiTypeBean(MutiTypeBean.COMMON, talkSkillListItemBeanList.get(0)));
                list.add(new MutiTypeBean(MutiTypeBean.COMMON, talkSkillListItemBeanList.get(1)));
                list.add(new MutiTypeBean(MutiTypeBean.AD, talkSkillResultBean.getAdvertising()));
                for (int i = 2; i < talkSkillListItemBeanList.size(); i++) {
                    list.add(new MutiTypeBean(isVip ? MutiTypeBean.COMMON : MutiTypeBean.NO_VIP, talkSkillListItemBeanList.get(i)));
                }
            }
            adapter = new HuaShuListAdapter(mContext, list);
            huashuListRlv.setAdapter(adapter);
        } else {

        }
    }

    @Override
    public void getTalkListFail(String msg) {

    }

    public void getMiji() {
        talkSkillListPresenter.getMiji();
    }

    @Override
    public void getMijiSuccess(String url) {
        dialog = new MijiDialog(mContext, url);
        dialog.show();
    }

    @Override
    protected void onDestroy() {
        if (dialog != null) {
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
            dialog = null;
        }
        super.onDestroy();
    }


}
