package com.tuodanhuashu.app.home.ui;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.tuodanhuashu.app.R;
import com.tuodanhuashu.app.base.HuaShuBaseActivity;
import com.tuodanhuashu.app.course.presenter.MoreActivityPresenter;
import com.tuodanhuashu.app.home.bean.CollegeActivityBean;
import com.tuodanhuashu.app.home.bean.HomeCourseBean;
import com.tuodanhuashu.app.home.view.MoreActivityView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MoreActivity extends HuaShuBaseActivity implements MoreActivityView {

    @BindView(R.id.rv_more_activity)
    RecyclerView recyclerView;
    @BindView(R.id.common_head_title_tv)
    TextView tvHeadTitle;
    @BindView(R.id.common_head_back_iv)
    ImageView ivHeadBack;
    @BindView(R.id.refresh_more_activity)
    SmartRefreshLayout refreshLayout;


    private int page = 1;
    private int pageSize = 10;
    private ActivityAdapter adapter;
    private List<CollegeActivityBean> collegeActivityList = new ArrayList<>();
    private MoreActivityPresenter moreActivityPresenter;
    @Override
    protected int getContentViewLayoutID() {
        return R.layout.more_activity_layout;
    }

    @Override
    protected void initView() {
        super.initView();
        tvHeadTitle.setText("最新活动");
        ivHeadBack.getDrawable().setTint(Color.parseColor("#000000"));

        refreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                loadMore();
                refreshLayout.finishLoadMore();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                refresh();
                refreshLayout.setEnableLoadMore(true);
            }
        });
        ivHeadBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void refresh() {
        page =1;
        moreActivityPresenter.requestActivityList(String.valueOf(page),String.valueOf(pageSize));
    }

    private void loadMore() {
        page++;
        moreActivityPresenter.requestActivityList(String.valueOf(page),String.valueOf(pageSize));
    }

    @Override
    protected void initData() {
        super.initData();
        moreActivityPresenter = new MoreActivityPresenter(mContext,this);
        moreActivityPresenter.requestActivityList(page+"",pageSize+"");
    }



    @Override
    public void getMoreActivitySuccess(List<CollegeActivityBean> collegeActivityBeans) {
        Log.d("111",collegeActivityBeans.size()+"");
        Log.d("111",""+page);
        if (page == 1) {
            this.collegeActivityList.clear();
            this.collegeActivityList.addAll(collegeActivityBeans);
            adapter = new ActivityAdapter(mContext,this.collegeActivityList);
            adapter.setCourseBeanList(this.collegeActivityList);
            recyclerView.setAdapter(adapter);
            refreshLayout.finishRefresh();
        } else {
            if (collegeActivityBeans.size()==0){
                Toast.makeText(mContext,"已经没有更多内容了!",Toast.LENGTH_SHORT).show();
                refreshLayout.setEnableLoadMore(false);
            }else {

                adapter.loadMore(collegeActivityBeans);
                refreshLayout.finishLoadMore();
            }
        }

        Log.d("111",collegeActivityBeans.size()+collegeActivityBeans.get(0).getActivity_image_url());
        this.collegeActivityList = collegeActivityBeans;
        adapter = new ActivityAdapter(mContext,collegeActivityList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));

    }

    @Override
    public void getMoreActivityFail(String msg) {

    }

    class ActivityAdapter extends RecyclerView.Adapter<ActivityAdapter.ActivityHolder> {

        private Context mContext;
        private List<CollegeActivityBean> data;

        public ActivityAdapter(Context mContext, List<CollegeActivityBean> collegeActivityList) {

            this.mContext = mContext;
            this.data = collegeActivityList;

        }

        @NonNull
        @Override
        public ActivityHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_course_activity, parent, false);
            ActivityHolder holder = new ActivityHolder(view);
            return holder;


        }

        @Override
        public void onBindViewHolder(@NonNull ActivityHolder holder, int position) {
            CollegeActivityBean activityBean = data.get(position);
            String url = activityBean.getActivity_image_url();

            Glide.with(mContext).load(url).into(holder.imageView);
        }

        @Override
        public int getItemCount() {
            return data.size();
        }


        public void setCourseBeanList(List<CollegeActivityBean> list) {
            this.data = list == null ? new ArrayList<CollegeActivityBean>() : list;
            notifyDataSetChanged();
        }

        public void loadMore(List<CollegeActivityBean> list){
            this.data.addAll(list);
            notifyDataSetChanged();
        }
        class ActivityHolder extends RecyclerView.ViewHolder {
            ImageView imageView;

            public ActivityHolder(View itemView) {
                super(itemView);
                imageView = itemView.findViewById(R.id.iv_activity);
            }
        }
    }

}
