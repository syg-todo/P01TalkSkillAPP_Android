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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.company.common.utils.DisplayUtil;
import com.tuodanhuashu.app.MemberCenter.ui.MyCourseFragment;
import com.tuodanhuashu.app.R;
import com.tuodanhuashu.app.base.HuaShuBaseActivity;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MasterDetailActivity extends HuaShuBaseActivity {

    @BindView(R.id.tablayout_master_detail)
    TabLayout tabLayout;
    @BindView(R.id.viewpager_master_detail)
    ViewPager viewPager;
    @BindView(R.id.common_head_title_tv)
    TextView txtTitle;
    @BindView(R.id.tv_master_detail_follow)
    TextView txtFollow;

    private MyPagerAdapter adapter;
    private List<String> titles = new ArrayList<>();
    private List<Fragment> fragments = new ArrayList<>();


    public static final String EXTRA_MASTER_NAME = "master_name";

    private String master_name = "";
    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_master_detail;
    }

    @Override
    protected void initView() {
        super.initView();

        txtTitle.setText(master_name);

        txtFollow.setSelected(true);
        titles.add("个人介绍");
        titles.add("相关课程");

        MyCourseFragment fragment = new MyCourseFragment();
        MyCourseFragment fragment1 = new MyCourseFragment();


        fragments.add(fragment);
        fragments.add(fragment1);

        adapter = new MyPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
//        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setupWithViewPager(viewPager);

        for (int i = 0; i < titles.size(); i++) {
            View customView = getTabView(this, titles.get(i), DisplayUtil.dp2px(18), DisplayUtil.dp2px(2));
            tabLayout.getTabAt(i).setCustomView(customView);
        }

        tabLayout.getTabAt(0).select();
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
    }
    private void changeTabStatus(TabLayout.Tab tab, boolean selected) {
        View view = tab.getCustomView();
        TextView txtTabTitle =  view.findViewById(R.id.tab_item_text);
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

    class MyPagerAdapter extends FragmentPagerAdapter {

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return 2;
        }

    }

    @Override
    protected void getBundleExtras(Bundle extras) {
        super.getBundleExtras(extras);
        master_name = extras.getString(master_name);
    }
}
