package com.tuodanhuashu.app.course.ui;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.alibaba.android.vlayout.layout.LinearLayoutHelper;
import com.chad.library.adapter.base.BaseViewHolder;
import com.company.common.utils.DisplayUtil;
import com.tuodanhuashu.app.MemberCenter.ui.MyCourseFragment;
import com.tuodanhuashu.app.R;
import com.tuodanhuashu.app.base.HuaShuBaseActivity;
import com.tuodanhuashu.app.course.ui.fragment.CourseListFragment;
import com.tuodanhuashu.app.home.adapter.HomeAdapter;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MasterDetailActivity extends HuaShuBaseActivity implements View.OnClickListener {

    @BindView(R.id.tv_master_detail_head_title)
    TextView txtTitle;

    @BindView(R.id.iv_master_detail_head_back)
    ImageView ivHeadBack;
    @BindView(R.id.iv_master_detail_head_share)
    ImageView ivHeadShare;
    @BindView(R.id.rv_master_detail)
    RecyclerView recyclerView;
    private List<String> titles = new ArrayList<>();
    private List<Fragment> fragments = new ArrayList<>();

    private static final int TYPE_MASTER = 1;
    private static final int TYPE_TAB = 2;

    private DelegateAdapter delegateAdapter;

    private String isRecord;//是否关注 1已关注 2未关注

    private List<DelegateAdapter.Adapter> adapterList = new ArrayList<>();

    public static final String EXTRA_MASTER_NAME = "master_name";
    private String master_name = "";

    public static final String EXTRA_MASTER_ID = "master_id";
    private String master_id = "";

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_master_detail;
    }

    @Override
    protected void initView() {
        super.initView();
        txtTitle.setText(master_name);

        ivHeadShare.getDrawable().setTint(getResources().getColor(R.color.black));
        ivHeadShare.setOnClickListener(this);


        VirtualLayoutManager layoutManager = new VirtualLayoutManager(mContext);
        recyclerView.setLayoutManager(layoutManager);
        RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
        recyclerView.setRecycledViewPool(viewPool);
        viewPool.setMaxRecycledViews(0, 10);
        delegateAdapter = new DelegateAdapter(layoutManager, true);

        initMaster();
        initTab();
        delegateAdapter.setAdapters(adapterList);
        recyclerView.setAdapter(delegateAdapter);
        ivHeadBack.getDrawable().setTint(Color.parseColor("#000000"));
        ivHeadBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    private void initTab() {
        HomeAdapter adapterTab = new HomeAdapter(mContext, new LinearLayoutHelper(), 1, TYPE_TAB, R.layout.master_detail_tab_layout) {
            @Override
            public void onBindViewHolder(BaseViewHolder holder, int position) {
                super.onBindViewHolder(holder, position);
                titles.add("个人介绍");
                titles.add("相关课程");

                MyCourseFragment fragment = new MyCourseFragment();

                CourseListFragment courseListFragment = new CourseListFragment();
                fragments.add(fragment);
                fragments.add(courseListFragment);

                TabLayout tabLayout = holder.getView(R.id.tablayout_master_detail);
                for (int i = 0; i < titles.size(); i++) {
                    tabLayout.addTab(tabLayout.newTab().setText(titles.get(i)));
                }
                for (int i = 0; i < titles.size(); i++) {
                    if (i == 0) {
                        tabLayout.getTabAt(i).select();
                    }
                    View customView = getTabView(mContext, titles.get(i), DisplayUtil.dp2px(18), DisplayUtil.dp2px(2), tabLayout.getTabAt(i).isSelected());
                    tabLayout.getTabAt(i).setCustomView(customView);
                }


                final FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction().add(R.id.frame_master_detail, fragment).commit();

                tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                    @Override
                    public void onTabSelected(TabLayout.Tab tab) {
                        changeTabStatus(tab, true);
                        fragmentManager.beginTransaction().replace(R.id.frame_master_detail, fragments.get(tab.getPosition())).commit();
                    }

                    @Override
                    public void onTabUnselected(TabLayout.Tab tab) {
                        changeTabStatus(tab, false);
                    }

                    @Override
                    public void onTabReselected(TabLayout.Tab tab) {

                    }
                });
            }
        };
        adapterList.add(adapterTab);
    }

    private void initMaster() {
        HomeAdapter adapterMaster = new HomeAdapter(mContext, new LinearLayoutHelper(), 1, TYPE_MASTER, R.layout.master_detail_master_layout) {
            @Override
            public void onBindViewHolder(BaseViewHolder holder, int position) {
                super.onBindViewHolder(holder, position);
                final TextView txtFollow = holder.getView(R.id.tv_master_detail_follow);
                txtFollow.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        txtFollow.setSelected(true);
                    }
                });

            }
        };

        adapterList.add(adapterMaster);
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

    @Override
    protected void initData() {
        super.initData();

    }

    public static View getTabView(Context context, String text, int indicatorWidth, int indicatorHeight, boolean isSelected) {
        View view = LayoutInflater.from(context).inflate(R.layout.tab_item_layout, null);
        TextView tabText = view.findViewById(R.id.tab_item_text);
        if (indicatorWidth > 0) {
            View indicator = view.findViewById(R.id.tab_item_indicator);
            ViewGroup.LayoutParams layoutParams = indicator.getLayoutParams();
            layoutParams.width = indicatorWidth;
            layoutParams.height = indicatorHeight;
            indicator.setLayoutParams(layoutParams);
            indicator.setVisibility(isSelected ? View.VISIBLE : View.INVISIBLE);
        }
//        tabText.setTextSize(textSize);
        tabText.setText(text);
        return view;
    }


    @Override
    protected void getBundleExtras(Bundle extras) {
        super.getBundleExtras(extras);
        master_name = extras.getString(EXTRA_MASTER_NAME);
        master_id = extras.getString(EXTRA_MASTER_ID);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_master_detail_head_share:
                Toast.makeText(mContext, "分享", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
