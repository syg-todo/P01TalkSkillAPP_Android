package com.tuodanhuashu.app.huashu.ui;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.company.common.net.OnRequestListener;
import com.company.common.net.ServerResponse;
import com.company.common.utils.JsonUtils;
import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.tuodanhuashu.app.R;
import com.tuodanhuashu.app.base.HuaShuBaseActivity;
import com.tuodanhuashu.app.huashu.adapter.HuaShuListAdapter;
import com.tuodanhuashu.app.huashu.bean.MutiTypeBean;
import com.tuodanhuashu.app.huashu.bean.TalkSkillListItemBean;
import com.tuodanhuashu.app.huashu.biz.TalkSkillListBiz;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyHuaShuListActivity extends HuaShuBaseActivity implements OnRequestListener {

    @BindView(R.id.common_head_back_iv)
    ImageView commonHeadBackIv;
    @BindView(R.id.common_head_title_tv)
    TextView commonHeadTitleTv;
    @BindView(R.id.my_huashu_list_refresheader)
    MaterialHeader myHuashuListRefresheader;
    @BindView(R.id.my_huashu_list_rlv)
    RecyclerView myHuashuListRlv;
    @BindView(R.id.my_huashu_list_refresfooter)
    ClassicsFooter myHuashuListRefresfooter;
    @BindView(R.id.my_huashu_list_refreshLayout)
    SmartRefreshLayout myHuashuListRefreshLayout;

    private int currentPage = 1;

    private int pageSize = 10;

    private List<TalkSkillListItemBean> talkSkillList;

    private List<MutiTypeBean> itemlist;

    private TalkSkillListBiz talkSkillListBiz;

    private HuaShuListAdapter adapter;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_my_hua_shu_list;
    }

    @Override
    protected void initView() {
        super.initView();
        commonHeadTitleTv.setText("我的话术");
        commonHeadBackIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    protected void initData() {
        super.initData();
        itemlist = new ArrayList<>();
        myHuashuListRefreshLayout.setEnableAutoLoadMore(false);
        myHuashuListRefresheader.setColorSchemeColors(mContext.getResources().getColor(R.color.colorAccent));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        myHuashuListRlv.setLayoutManager(linearLayoutManager);
        talkSkillListBiz = new TalkSkillListBiz(this,mContext);
        talkSkillListBiz.requestMyList(1,currentPage+"",pageSize+"");
    }

    @Override
    public void OnSuccess(ServerResponse serverResponse, int tag) {
        talkSkillList = JsonUtils.getJsonToList(serverResponse.getData(),TalkSkillListItemBean.class);
        if(talkSkillList!=null&&talkSkillList.size()>0) {
            for (int i = 0; i < talkSkillList.size(); i++) {
                itemlist.add(new MutiTypeBean(MutiTypeBean.COMMON, talkSkillList.get(i)));
            }
            adapter = new HuaShuListAdapter(mContext,itemlist);
            myHuashuListRlv.setAdapter(adapter);
        }
    }

    @Override
    public void onFail(String msg, int code, int tag) {
        showToast(msg);
    }

    @Override
    public void onError(int tag) {

    }
}
