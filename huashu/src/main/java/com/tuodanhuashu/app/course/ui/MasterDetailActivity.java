package com.tuodanhuashu.app.course.ui;

import android.app.Dialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.alibaba.android.vlayout.layout.LinearLayoutHelper;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseViewHolder;
import com.company.common.CommonConstants;
import com.company.common.utils.DisplayUtil;
import com.company.common.utils.PreferencesUtils;
import com.tuodanhuashu.app.MemberCenter.ui.MyCourseFragment;
import com.tuodanhuashu.app.R;
import com.tuodanhuashu.app.base.HuaShuBaseActivity;
import com.tuodanhuashu.app.course.bean.MasterBean;
import com.tuodanhuashu.app.course.bean.MasterDetailModel;
import com.tuodanhuashu.app.course.presenter.MasterDetailPresenter;
import com.tuodanhuashu.app.course.ui.fragment.CourseListFragment;
import com.tuodanhuashu.app.course.ui.fragment.WebViewFragment;
import com.tuodanhuashu.app.course.view.MasterDetailView;
import com.tuodanhuashu.app.home.adapter.HomeAdapter;
import com.tuodanhuashu.app.home.bean.HomeCourseBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MasterDetailActivity extends HuaShuBaseActivity implements View.OnClickListener, MasterDetailView {

    private static final String TAG = MasterDetailActivity.class.getSimpleName();
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

    private MasterDetailPresenter masterDetailPresenter;

    private static final int TYPE_MASTER = 1;
    private static final int TYPE_TAB = 2;

    private DelegateAdapter delegateAdapter;

    private String accessToken;
    private int isRecord;//是否关注 1已关注 0未关注
    private String masterName;//导师名字
    private String signature;//导师签名
    private String bannerUrl;
    private List<HomeCourseBean> courseBeanList = new ArrayList<>();
    private List<DelegateAdapter.Adapter> adapterList = new ArrayList<>();

    private MasterBean masterBean;
    public static final String EXTRA_MASTER_NAME = "master_name";
    private String master_name = "";

    public static final String EXTRA_MASTER_ID = "master_id";
    private String master_id = "";
    private Dialog shareDialog;

    private MasterDetailModel model;
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
        initShareDialog();

        VirtualLayoutManager layoutManager = new VirtualLayoutManager(mContext);
        recyclerView.setLayoutManager(layoutManager);
        RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
        recyclerView.setRecycledViewPool(viewPool);
        viewPool.setMaxRecycledViews(0, 10);
        delegateAdapter = new DelegateAdapter(layoutManager, true);

        recyclerView.setAdapter(delegateAdapter);

        ivHeadBack.getDrawable().setTint(Color.parseColor("#000000"));
        ivHeadBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }
    private void initShareDialog() {
        shareDialog = new Dialog(mContext, R.style.BottomDialog_Animation);
        View view = getLayoutInflater().inflate(R.layout.dialog_share_layout, null);
        shareDialog.setContentView(view);
        LinearLayout youIv = view.findViewById(R.id.share_you_ll);
        LinearLayout quanLl = view.findViewById(R.id.share_quan_ll);
        TextView cancelTv = view.findViewById(R.id.share_cancel_tv);
        cancelTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareDialog.dismiss();
            }
        });
        youIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (masterBean != null) {
                    shareDialog.dismiss();
                    masterDetailPresenter.shareArticle(masterBean.getShare_url(), masterBean.getName(), masterBean.getIntro(), "you");
                }
            }
        });
        quanLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (masterBean != null) {
                    shareDialog.dismiss();
                    masterDetailPresenter.shareArticle(masterBean.getShare_url(), masterBean.getName(), masterBean.getIntro(), "quan");
                }
            }
        });
        shareDialog.setCanceledOnTouchOutside(true);
        Window window = shareDialog.getWindow();
        window.setGravity(Gravity.BOTTOM);
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = DisplayUtil.dip2px(mContext, 166.0f);
        window.setAttributes(params);
    }

    private void initTab() {
        HomeAdapter adapterTab = new HomeAdapter(mContext, new LinearLayoutHelper(), 1, TYPE_TAB, R.layout.master_detail_tab_layout) {
            @Override
            public void onBindViewHolder(BaseViewHolder holder, int position) {
                super.onBindViewHolder(holder, position);
                titles.add("个人介绍");
                titles.add("相关课程");

                WebViewFragment fragment = new WebViewFragment();

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
                TextView tvMasterName = holder.getView(R.id.tv_master_detail_name);
                TextView tvMasterSignature = holder.getView(R.id.tv_master_detail_brief);
                ImageView ivMasterBanner = holder.getView(R.id.iv_master_detail_image);
                tvMasterName.setText(masterName);
                tvMasterSignature.setText(signature);
                Glide.with(mContext).load(bannerUrl).into(ivMasterBanner);

                final TextView txtFollow = holder.getView(R.id.tv_master_detail_follow);
//                txtFollow.setEnabled(true);
                txtFollow.setClickable(true);
                txtFollow.setText(isRecord == 1 ? "已关注" : "关注");
                txtFollow.setSelected(isRecord == 1);
                txtFollow.setTextColor(isRecord == 1 ? Color.parseColor("#BFBFBF") : Color.parseColor("#FF6969"));
                txtFollow.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (isLogin()) {
                            switch (isRecord) {
                                case 1:
                                    final AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                                    builder.setMessage("取消关注？");
                                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            masterDetailPresenter.unrecordMaster(accessToken, master_id);
                                            txtFollow.setText("关注");
                                            txtFollow.setTextColor(Color.parseColor("#FF6969"));
                                            txtFollow.setSelected(false);
                                            isRecord = 0;

                                        }
                                    });
                                    builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                        }
                                    });
                                    builder.show();
                                    break;
                                case 0:
                                    masterDetailPresenter.recordMaster(accessToken, master_id);
                                    txtFollow.setText("已关注");
                                    txtFollow.setTextColor(Color.parseColor("#BFBFBF"));
                                    txtFollow.setSelected(true);
                                    isRecord = 1;
                                    break;
                            }
//                            }
//                            isCheckout = isCheckout.equals("1") ? "2" : "1";
//                            Toast.makeText(mContext, "click" + isCheckout, Toast.LENGTH_SHORT).show();
//                            txtFollow.setText(isCheckout.equals("1") ? "已关注" : "关注");
//                            txtFollow.setSelected(isCheckout.equals("1"));
//                            txtFollow.setTextColor(isCheckout.equals("1") ? Color.parseColor("#BFBFBF") : Color.parseColor("#FF6969"));
                        }else {
                            goToLogin();
                        }
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

        model = ViewModelProviders.of(this).get(MasterDetailModel.class);
        accessToken = PreferencesUtils.getString(mContext, CommonConstants.KEY_TOKEN,"");
        Log.d(TAG,accessToken);
        masterDetailPresenter = new MasterDetailPresenter(mContext, this);
        masterDetailPresenter.requestMasterDetail(accessToken, master_id);
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
                share();
                break;
        }
    }

    private void share() {
        shareDialog.show();
    }

    @Override
    public void getMasterDetailSuccess(MasterBean masterBean) {
        this.masterBean = masterBean;
        masterName = masterBean.getName();
        signature = masterBean.getP_signature();
        isRecord = masterBean.getIs_record();
        bannerUrl = masterBean.getBanner_url();

        model.setMasterDetail(masterBean);
        initMaster();
        initTab();
        delegateAdapter.setAdapters(adapterList);

    }

    @Override
    public void getMasterDetailFail(String msg) {

    }

    @Override
    public void getRecordSuccess() {
        Toast.makeText(mContext,"关注成功",Toast.LENGTH_SHORT).show();

    }

    @Override
    public void getUnrecordSuccess() {
        Toast.makeText(mContext,"取消关注成功",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void getRecordFail(String msg) {

    }
}
