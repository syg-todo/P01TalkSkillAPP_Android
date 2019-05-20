package com.tuodanhuashu.app.course.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.tuodanhuashu.app.course.ui.adapter.RVRecommendationAdapter;
import com.tuodanhuashu.app.course.ui.fragment.CourseDetailAspectFragment;
import com.tuodanhuashu.app.course.ui.fragment.CourseDetailCommentFragment;
import com.tuodanhuashu.app.course.ui.fragment.CourseDetailDirectoryFragment;
import com.tuodanhuashu.app.home.adapter.HomeAdapter;
import com.tuodanhuashu.app.home.bean.HomeCourseBean;
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
    @BindView(R.id.common_head_title_tv)
    TextView tvTitle;
    private DelegateAdapter delegateAdapter;

    private List<DelegateAdapter.Adapter> adapterList;

    public static final String EXTRA_COURSE_NAME = "course_name";

    private String course_name = "";

    private static final int TYPE_TOP = 1;

    private static final int TYPE_MASTER_ROW = 2;

    private static final int TYPE_COURSE_TAB = 3;

    private static final int TYPE_RECOMMEND = 4;

    private boolean isFollowed = false;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_course_datail;
    }


    @Override
    protected void initView() {
        super.initView();
        tvTitle.setText(course_name);
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
        initCourseTab();
        initRecommendation();
        delegateAdapter.setAdapters(adapterList);

    }

    private void initRecommendation() {

        HomeAdapter adapterRecommendation = new HomeAdapter(mContext, new LinearLayoutHelper(), 1, TYPE_RECOMMEND, R.layout.course_detail_recommendation_layout) {
            @Override
            public void onBindViewHolder(BaseViewHolder holder, int position) {
                super.onBindViewHolder(holder, position);
                if (position != 0) {
                    TextView tv = holder.getView(R.id.college_recommendation_top_tv);
                    tv.setVisibility(View.GONE);
                }

                TextView moreTv = holder.getView(R.id.college_recommendation_course_more_tv);
                moreTv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(mContext, "more", Toast.LENGTH_SHORT).show();
                    }
                });

//                final List<HomeCourseBean> recommendCourses = collegePageBean.getRecommendCourses();
                final List<HomeCourseBean> recommendCourses = new ArrayList<>();
                HomeCourseBean courseBean = new HomeCourseBean();
                courseBean.setCourse_name("施琪嘉创伤30讲：走出心理阴影，重塑强大内心");
                courseBean.setPrice(199f);
                courseBean.setSale_price(99f);
                courseBean.setJoin_count(99);
                courseBean.setImage_url("https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=1671816861,451680427&fm=27&gp=0.jpg");
                recommendCourses.add(courseBean);

                HomeCourseBean courseBean1 = new HomeCourseBean();
                courseBean1.setCourse_name("施琪嘉创伤30讲：走出心理阴影，重塑强大内心");
                courseBean1.setPrice(199f);
                courseBean1.setSale_price(99f);
                courseBean1.setJoin_count(924159);
                courseBean1.setImage_url("https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=1671816861,451680427&fm=27&gp=0.jpg");
                recommendCourses.add(courseBean1);

                HomeCourseBean courseBean2 = new HomeCourseBean();
                courseBean2.setCourse_name("施琪嘉创伤30讲：走出心理阴影，重塑强大内心");
                courseBean2.setPrice(199f);
                courseBean2.setSale_price(99f);
                courseBean2.setJoin_count(1151599);
                courseBean2.setImage_url("https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=1671816861,451680427&fm=27&gp=0.jpg");
                recommendCourses.add(courseBean2);

                RecyclerView recyclerView = holder.getView(R.id.college_more_recommendation_rv);
                recyclerView.setAdapter(new RVRecommendationAdapter(mContext, recommendCourses));
                recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
                recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
                    @Override
                    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
                        super.onDraw(c, parent, state);
                        int childCount = parent.getChildCount();
                        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
                        paint.setColor(Color.parseColor("#EEEEEE"));
                        paint.setStrokeWidth(DisplayUtil.dp2px(1));
                        for (int i = 0; i < childCount; i++) {
                            View child = parent.getChildAt(i);
                            c.drawLine(DisplayUtil.dp2px(0), child.getBottom(),
                                    child.getRight(), child.getBottom(), paint);
                        }

                    }

//                    @Override
//                    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
//                        super.getItemOffsets(outRect, view, parent, state);
//                        int childCount = parent.getChildCount();
//                        int actualCount = parent.getAdapter().getItemCount();
//                        for (int i = 0; i < childCount; i++) {
//                            View child = parent.getChildAt(i);
//                            int index = parent.getChildAdapterPosition(child);
//
//                            outRect.set(DisplayUtil.dip2px(mContext, 10), DisplayUtil.dip2px(mContext, 20),
//                                    DisplayUtil.dip2px(mContext, 10), DisplayUtil.dip2px(mContext, 20));
////
////                            if (index != actualCount - 1) {
////                                outRect.set(DisplayUtil.dip2px(mContext, 10), DisplayUtil.dip2px(mContext, 20),
////                                        DisplayUtil.dip2px(mContext, 10), DisplayUtil.dip2px(mContext, 0));
////                            }
////                            else {
////                                outRect.set(DisplayUtil.dip2px(mContext, 10), DisplayUtil.dip2px(mContext, 20),
////                                        DisplayUtil.dip2px(mContext, 10), DisplayUtil.dip2px(mContext, 20));
////                            }
//                        }
//
//                    }

                });

            }
        };
        adapterList.add(adapterRecommendation);

    }

    private void initCourseTab() {
        HomeAdapter adapterCourseTab = new HomeAdapter(mContext, new LinearLayoutHelper(), 1, TYPE_COURSE_TAB, R.layout.course_detail_tab_layout) {
            @Override
            public void onBindViewHolder(BaseViewHolder holder, int position) {
                super.onBindViewHolder(holder, position);
                TabLayout tabLayout = holder.getView(R.id.tablayout_course_detail);

                ViewPager viewPager = holder.getView(R.id.viewpage_course_detail);
                final List<String> titles = new ArrayList<>();
                titles.add("详情");
                titles.add("目录");
                titles.add("评论");
                final List<Fragment> fragments = new ArrayList<>();
                fragments.add(new CourseDetailAspectFragment());
                fragments.add(new CourseDetailDirectoryFragment());
                fragments.add(new CourseDetailCommentFragment());

                for (String title : titles) {
                    tabLayout.addTab(tabLayout.newTab().setText(title));
                }

                tabLayout.setupWithViewPager(viewPager);

                viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
                    @Override
                    public Fragment getItem(int position) {
                        return fragments.get(position);
                    }

                    @Override
                    public int getCount() {
                        return fragments.size();
                    }

                    @Nullable
                    @Override
                    public CharSequence getPageTitle(int position) {
                        return titles.get(position);
                    }
                });


                for (int i = 0; i < titles.size(); i++) {
                    View customView = getTabView(mContext, titles.get(i), DisplayUtil.dp2px(18), DisplayUtil.dp2px(2));
                    tabLayout.getTabAt(i).setCustomView(customView);
                    if (i == 0) {
                        tabLayout.getTabAt(i).select();
                    }
                }


                tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                    @Override
                    public void onTabSelected(TabLayout.Tab tab) {
                        changeTabStatus(tab, true);
                    }

                    @Override
                    public void onTabUnselected(TabLayout.Tab tab) {
                        changeTabStatus(tab, false);
                    }

                    @Override
                    public void onTabReselected(TabLayout.Tab tab) {

                    }
                });

                viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                    @Override
                    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                        Log.d("111", "position:" + position);
                    }

                    @Override
                    public void onPageSelected(int position) {

                    }

                    @Override
                    public void onPageScrollStateChanged(int state) {

                    }
                });

            }
        };
        adapterList.add(adapterCourseTab);

    }

    private void changeTabStatus(TabLayout.Tab tab, boolean selected) {
        View view = tab.getCustomView();
        TextView txtTabTitle = view.findViewById(R.id.tab_item_text);
        View indicator = view.findViewById(R.id.tab_item_indicator);

        txtTabTitle.setVisibility(View.VISIBLE);
        if (selected) {
            txtTabTitle.setTextColor(Color.parseColor("#FF6666"));
            indicator.setVisibility(View.VISIBLE);
//            startPropertyAnim(imgTitle);
        } else {
            txtTabTitle.setTextColor(Color.parseColor("#000000"));
            indicator.setVisibility(View.INVISIBLE);
//            imgTitle.setVisibility(View.INVISIBLE);
        }
    }


    public static View getTabView(Context context, String text, int indicatorWidth, int indicatorHeight) {
        View view = LayoutInflater.from(context).inflate(R.layout.tab_item_layout, null);
        TextView tabText = view.findViewById(R.id.tab_item_text);
        if (indicatorWidth > 0) {
            View indicator = view.findViewById(R.id.tab_item_indicator);
            ViewGroup.LayoutParams layoutParams = indicator.getLayoutParams();
            Log.d("111", "indicatorWidth:" + indicatorWidth);
            layoutParams.width = indicatorWidth;
            layoutParams.height = indicatorHeight;
            indicator.setLayoutParams(layoutParams);
        }
        Log.d("111", text);
//        tabText.setTextSize(textSize);
        tabText.setText(text);
        return view;
    }


    private void initMasterRow() {
        HomeAdapter adapterMasterRow = new HomeAdapter(mContext, new LinearLayoutHelper(), 1, TYPE_MASTER_ROW, R.layout.course_detail_master_row_layout) {
            @Override
            public void onBindViewHolder(final BaseViewHolder holder, int position) {
                super.onBindViewHolder(holder, position);
                final RoundRectImageView imgMasterAvatar = holder.getView(R.id.img_course_detail_master);
                RequestOptions options = new RequestOptions()
                        .override(DisplayUtil.dp2px(40), DisplayUtil.dp2px(40))
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
                        .override(DisplayUtil.dp2px(15), DisplayUtil.dp2px(15))
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

                final TextView txtMasterName = holder.getView(R.id.tv_course_detail_course_master_name);
                txtMasterName.setText("施琪嘉");

                txtMasterName.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Bundle bundle = new Bundle();
                        bundle.putString(MasterDetailActivity.EXTRA_MASTER_NAME, "施琪嘉");
                        readyGo(MasterDetailActivity.class);
                    }
                });
                final TextView txtFollow = holder.getView(R.id.tv_course_detail_follow);
                txtFollow.setText(isFollowed == true ? "已关注" : "关注");
                txtFollow.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        isFollowed = !isFollowed;
                        Toast.makeText(mContext, "click" + isFollowed, Toast.LENGTH_SHORT).show();
                        txtFollow.setText(isFollowed == true ? "已关注" : "关注");
                        txtFollow.setSelected(isFollowed);
                        txtFollow.setTextColor(isFollowed == true ? Color.parseColor("#BFBFBF") : Color.parseColor("#FF6969"));
                        txtFollow.invalidate();
                    }
                });
            }
        };
        adapterList.add(adapterMasterRow);

    }

    private void initCourseTop() {
        HomeAdapter adapterCourseTop = new HomeAdapter(mContext, new LinearLayoutHelper(), 1, TYPE_TOP, R.layout.course_detail_top_layout) {
            @Override
            public void onBindViewHolder(BaseViewHolder holder, int position) {
                super.onBindViewHolder(holder, position);
                TextView txtCoursePrice = holder.getView(R.id.tv_course_detail_course_price);
                txtCoursePrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);

            }
        };
        adapterList.add(adapterCourseTop);
    }

    @Override
    protected void getBundleExtras(Bundle extras) {
        super.getBundleExtras(extras);
        course_name = extras.getString(EXTRA_COURSE_NAME);
    }
}
