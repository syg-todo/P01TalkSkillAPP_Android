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

import com.bumptech.glide.Glide;
import com.tuodanhuashu.app.R;
import com.tuodanhuashu.app.base.HuaShuBaseActivity;
import com.tuodanhuashu.app.course.presenter.MoreActivityPresenter;
import com.tuodanhuashu.app.home.bean.CollegeActivityBean;
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



    private int page = 1;
    private int pageSize = 1;
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
        ivHeadBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    protected void initData() {
        super.initData();
        moreActivityPresenter = new MoreActivityPresenter(mContext,this);
        moreActivityPresenter.requestActivityList(page+"",pageSize+"");
    }



    @Override
    public void getMoreActivitySuccess(List<CollegeActivityBean> collegeActivityBeans) {
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
            Log.d("111",url);

            Glide.with(mContext).load(url).into(holder.imageView);
        }

        @Override
        public int getItemCount() {
            return data.size();
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
