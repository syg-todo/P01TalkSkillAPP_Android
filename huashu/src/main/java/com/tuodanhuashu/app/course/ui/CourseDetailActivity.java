package com.tuodanhuashu.app.course.ui;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.alibaba.android.vlayout.layout.LinearLayoutHelper;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.chad.library.adapter.base.BaseViewHolder;
import com.company.common.utils.DisplayUtil;
import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.tuodanhuashu.app.R;
import com.tuodanhuashu.app.base.HuaShuBaseActivity;
import com.tuodanhuashu.app.home.adapter.HomeAdapter;
import com.tuodanhuashu.app.widget.RoundRectImageView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class CourseDetailActivity extends HuaShuBaseActivity {
    @BindView(R.id.rv_course_detail)
    RecyclerView recyclerView;

    @BindView(R.id.refresheader_course_detail)
    MaterialHeader refresheaderCourseDetail;
    @BindView(R.id.refresh_course_detail)
    SmartRefreshLayout refreshLayoutCourseDetail;

    private DelegateAdapter delegateAdapter;

    private List<DelegateAdapter.Adapter> adapterList;


    private static final int TYPE_TOP = 1;

    private static final int TYPE_MASTER_ROW = 2;

    private static final int TYPE_ARTICLE = 3;

    private static final int TYPE_CATEGORY = 4;

    private static final int TYPE_AD = 5;

    private static final int TYPE_LIST = 6;
    private boolean isFollowed = false;
    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_course_datail;
    }

    @Override
    protected void initView() {
        super.initView();

        adapterList = new ArrayList<>();
        refresheaderCourseDetail.setColorSchemeColors(mContext.getResources().getColor(R.color.colorAccent));
        VirtualLayoutManager layoutManager = new VirtualLayoutManager(mContext);
        recyclerView.setLayoutManager(layoutManager);
        RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
        recyclerView.setRecycledViewPool(viewPool);
        viewPool.setMaxRecycledViews(0, 10);
        delegateAdapter = new DelegateAdapter(layoutManager, true);
        recyclerView.setAdapter(delegateAdapter);


        initCourseTop();
        initMasterRow();
        delegateAdapter.setAdapters(adapterList);

    }

    private void initMasterRow() {
        HomeAdapter adapterMasterRow = new HomeAdapter(mContext,new LinearLayoutHelper(),1,TYPE_MASTER_ROW,R.layout.course_detail_master_row_layout){
            @Override
            public void onBindViewHolder(final BaseViewHolder holder, int position) {
                super.onBindViewHolder(holder, position);
                final RoundRectImageView imgMasterAvatar = holder.getView(R.id.img_course_detail_master);
                RequestOptions options = new RequestOptions()
                        .override(DisplayUtil.dp2px(40),DisplayUtil.dp2px(40))
                        .centerCrop();
                Glide.with(mContext)
                        .load(R.mipmap.avatar)
                        .apply(options)
                        .into(new SimpleTarget<Drawable>() {
                    @Override
                    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                       imgMasterAvatar.setDrawable(resource);
                    }
                });
                final RoundRectImageView imgMasterTag = holder.getView(R.id.img_course_detail_master_tag);
                RequestOptions optionsTag = new RequestOptions()
                        .override(DisplayUtil.dp2px(15),DisplayUtil.dp2px(15))
                        .centerCrop();
                Glide.with(mContext)
                        .load(R.mipmap.vip_blue)
                        .apply(optionsTag)
                        .into(new SimpleTarget<Drawable>() {
                            @Override
                            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                                imgMasterTag.setDrawable(resource);
                            }
                        });


                final TextView txtFollow = holder.getView(R.id.tv_course_detail_follow);
                txtFollow.setText(isFollowed== true?"已关注":"关注");
                txtFollow.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        isFollowed = !isFollowed;
                        Toast.makeText(mContext,"click"+isFollowed,Toast.LENGTH_SHORT).show();
                        txtFollow.setText(isFollowed== true?"已关注":"关注");
                        txtFollow.setSelected(isFollowed);
                        txtFollow.setTextColor(isFollowed == true? Color.parseColor("#BFBFBF"):Color.parseColor("#FF6969"));
                        txtFollow.invalidate();
                    }
                });
            }
        };
        adapterList.add(adapterMasterRow);

    }

    private void initCourseTop() {
        HomeAdapter adapterCourseTop = new HomeAdapter(mContext,new LinearLayoutHelper(),1,TYPE_TOP,R.layout.course_detail_top_layout){
            @Override
            public void onBindViewHolder(BaseViewHolder holder, int position) {
                super.onBindViewHolder(holder, position);
            }
        };
        adapterList.add(adapterCourseTop);
    }
}
