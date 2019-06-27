package com.tuodanhuashu.app.teacher.ui;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.tuodanhuashu.app.R;
import com.tuodanhuashu.app.base.HuaShuBaseActivity;
import com.tuodanhuashu.app.teacher.adapter.TeacherListAdapter;
import com.tuodanhuashu.app.teacher.bean.TeacherListBean;
import com.tuodanhuashu.app.teacher.bean.TeacherListItemBean;
import com.tuodanhuashu.app.teacher.bean.TeacherMutiTypeBean;
import com.tuodanhuashu.app.teacher.presenter.TeacherListPresenter;
import com.tuodanhuashu.app.teacher.view.TeacherListView;
import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class TeacherListActivity extends HuaShuBaseActivity implements TeacherListView {


    @BindView(R.id.common_head_back_iv)
    ImageView commonHeadBackIv;
    @BindView(R.id.common_head_title_tv)
    TextView commonHeadTitleTv;
    @BindView(R.id.teacher_refresheader)
    MaterialHeader teacherRefresheader;
    @BindView(R.id.teacher_rlv)
    RecyclerView teacherRlv;
    @BindView(R.id.teacher_refreshLayout)
    SmartRefreshLayout teacherRefreshLayout;

    private TeacherListPresenter teacherListPresenter;

    private TeacherListBean teacherListBean;

    private TeacherListAdapter adapter;

    private List<TeacherMutiTypeBean> mutiTypeBeanList;

    private int page = 1;

    private int page_size = 10;

    @Override
    protected void initView() {
        super.initView();
        commonHeadTitleTv.setText("咨询导师");
        commonHeadBackIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        teacherRefresheader.setColorSchemeColors(mContext.getResources().getColor(R.color.colorAccent));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        teacherRefreshLayout.setEnableLoadMore(true);
        teacherRlv.setLayoutManager(linearLayoutManager);
    }

    @Override
    protected void initData() {
        super.initData();
        mutiTypeBeanList = new ArrayList<>();
        teacherListPresenter = new TeacherListPresenter(mContext,this);
        adapter = new TeacherListAdapter(mContext,mutiTypeBeanList);
        teacherRlv.setAdapter(adapter);
        teacherListPresenter.requestTeacherList(page+"",page_size+"");
    }

    @Override
    protected void bindListener() {
        super.bindListener();
        teacherRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page = 1;
                mutiTypeBeanList.clear();
                teacherListPresenter.requestTeacherList(page+"",page_size+"");
            }
        });
        teacherRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                teacherListPresenter.requestTeacherList(page+"",page_size+"");
            }
        });

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_teacher_list;
    }

    @Override
    public void getTeacherListSuccess(TeacherListBean teacherListBean) {
        this.teacherListBean = teacherListBean;

        if(teacherListBean.getExpert_list()!=null&&teacherListBean.getExpert_list().size()>0) {
            if(page<=1){
                mutiTypeBeanList.add(new TeacherMutiTypeBean(TeacherMutiTypeBean.TYPE_AD, teacherListBean.getAdvt_list()));
            }

            for (TeacherListItemBean itemBean : teacherListBean.getExpert_list()) {
                mutiTypeBeanList.add(new TeacherMutiTypeBean(TeacherMutiTypeBean.TYPE_TEACHER, itemBean));
            }
            adapter.setNewData(mutiTypeBeanList);
            page++;
        }else{
            showToast("已无更多");
        }
        teacherRefreshLayout.finishRefresh();
        teacherRefreshLayout.finishLoadMore();
    }

    @Override
    public void getTeacherListFail(String msg) {
        showToast(msg);
    }


}
