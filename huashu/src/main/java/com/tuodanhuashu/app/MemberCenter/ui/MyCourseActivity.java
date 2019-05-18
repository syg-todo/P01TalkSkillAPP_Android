package com.tuodanhuashu.app.MemberCenter.ui;

import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.TextView;

import com.tuodanhuashu.app.R;
import com.tuodanhuashu.app.base.HuaShuBaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MyCourseActivity extends HuaShuBaseActivity {
    @BindView(R.id.tab_my_course)
    TabLayout tabLayout;
    @BindView(R.id.common_head_title_tv)
    TextView txtTitle;
    @BindView(R.id.vp_my_course)
    ViewPager viewPager;
    private List<String> titles = new ArrayList<>();
    private List<Fragment> fragments = new ArrayList<>();

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_my_course;
    }

    @Override
    protected void initView() {
        super.initView();
        txtTitle.setText("我的课程");
        titles.add("我的课程");
        titles.add("我的导师");
        MyCourseFragment fragment = new MyCourseFragment();
        MyMasterFragment masterFragment = new MyMasterFragment();

        fragments.add(fragment);
        fragments.add(masterFragment);
        for (int i = 0; i < titles.size(); i++) {
            tabLayout.addTab(tabLayout.newTab().setText(titles.get(i)));
        }

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

        tabLayout.setupWithViewPager(viewPager);
    }
}
