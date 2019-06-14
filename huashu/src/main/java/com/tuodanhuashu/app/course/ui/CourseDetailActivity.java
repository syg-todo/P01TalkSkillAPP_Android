package com.tuodanhuashu.app.course.ui;

import android.app.Dialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.alibaba.android.vlayout.layout.LinearLayoutHelper;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseViewHolder;
import com.company.common.CommonConstants;
import com.company.common.utils.DisplayUtil;
import com.company.common.utils.PreferencesUtils;
import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.tuodanhuashu.app.Constants.Constants;
import com.tuodanhuashu.app.R;
import com.tuodanhuashu.app.base.HuaShuBaseActivity;
import com.tuodanhuashu.app.course.bean.CourseDetailBean;
import com.tuodanhuashu.app.course.bean.CourseDetailModel;
import com.tuodanhuashu.app.course.presenter.CourseDetailPresenter;
import com.tuodanhuashu.app.course.ui.fragment.CourseDetailAspectFragment;
import com.tuodanhuashu.app.course.ui.fragment.CourseDetailCommentFragment;
import com.tuodanhuashu.app.course.ui.fragment.CourseDetailDirectoryFragment;
import com.tuodanhuashu.app.course.view.CourseDetailView;
import com.tuodanhuashu.app.eventbus.EventMessage;
import com.tuodanhuashu.app.home.adapter.HomeAdapter;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;

public class CourseDetailActivity extends HuaShuBaseActivity implements CourseDetailView, View.OnClickListener {
    private static final String TAG = CourseDetailActivity.class.getSimpleName();

    @BindView(R.id.rv_course_detail)
    RecyclerView recyclerView;
    @BindView(R.id.iv_course_detail_head_back)
    ImageView ivBack;
    @BindView(R.id.refresheader_course_detail)
    MaterialHeader refresheaderCourseDetail;
    @BindView(R.id.refresh_course_detail)
    SmartRefreshLayout refreshLayoutCourseDetail;
    @BindView(R.id.common_head_title_tv)
    TextView tvTitle;
    @BindView(R.id.iv_course_detail_head_download)
    ImageView ivDownload;
    @BindView(R.id.iv_course_detail_head_share)
    ImageView ivShare;
    @BindView(R.id.tv_course_detail_join_now)
    TextView tvJoinNow;
    @BindView(R.id.layout_audition)
    LinearLayout layoutAudiion;
    @BindView(R.id.layout_float)
    ConstraintLayout layoutFloat;
    @BindView(R.id.iv_float_course_play)
    ImageView ivFloatPlay;
    @BindView(R.id.tv_float_course_duration)
    TextView tvFloatDuration;
    @BindView(R.id.tv_float_course_current)
    TextView tvFloatCurrent;

    private DelegateAdapter delegateAdapter;

    private CourseDetailPresenter courseDetailPresenter;
    private List<DelegateAdapter.Adapter> adapterList;

    private String mCourseSalePrice;
    private String isPay; //1已支付
    private String isCheckout; //是否关注
    private String masterAvatarUrl;//导师头像
    private String mediaType;//文件类型 1音频 2视频
    private String imageUrl;//课程图片
    private String masterId;//导师ID
    private String shareUrl;
    private String salePrice;

    private Dialog shareDialog;
    public static final String EXTRA_COURSE_NAME = "course_name";

    private String courseName = "";
    public static final String EXTRA_COURSE_ID = "course_id";

    private String courseId = "";


    private String accessToken = "";
    private CourseDetailModel model;


    private CourseDetailBean.CourseBean courseBean;
    private List<CourseDetailBean.RecommendCoursesBean> recommendCoursesBeanList = new ArrayList<>();

    private static final int TYPE_TOP = 1;

    private static final int TYPE_MASTER_ROW = 2;

    private static final int TYPE_COURSE_TAB = 3;

    private static final int TYPE_RECOMMEND = 4;


    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_course_datail;
    }

    @Override
    protected void onResume() {
        super.onResume();
        refresh();
        initData();
        initView();
    }

    private void refresh() {
        courseDetailPresenter.requestCourseDetail(accessToken, courseId);
    }

    @Override
    protected void initView() {
        super.initView();

        initShareDialog();
        tvTitle.setText(courseName);

        ivDownload.getDrawable().setTint(getResources().getColor(R.color.black));
        ivShare.getDrawable().setTint(getResources().getColor(R.color.black));

        tvJoinNow.setOnClickListener(this);

        ivDownload.setOnClickListener(this);

        ivShare.setOnClickListener(this);

        layoutAudiion.setOnClickListener(this);

        ivFloatPlay.setOnClickListener(this);

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        ivBack.getDrawable().setTint(getResources().getColor(R.color.black));

        adapterList = new ArrayList<>();
        refresheaderCourseDetail.setColorSchemeColors(mContext.getResources().getColor(R.color.colorAccent));
        VirtualLayoutManager layoutManager = new VirtualLayoutManager(mContext);
        recyclerView.setLayoutManager(layoutManager);
        RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
        recyclerView.setRecycledViewPool(viewPool);
        viewPool.setMaxRecycledViews(0, 10);
        delegateAdapter = new DelegateAdapter(layoutManager, true);
        recyclerView.setAdapter(delegateAdapter);

//        initCourseTop();
//        initMasterRow();
//      initCourseTab();
//        initRecommendation();
//        delegateAdapter.setAdapters(adapterList);

    }

//    private void initRecommendation() {
//
//        HomeAdapter adapterRecommendation = new HomeAdapter(mContext, new LinearLayoutHelper(), 1, TYPE_RECOMMEND, R.layout.course_detail_recommendation_layout) {
//            @Override
//            public void onBindViewHolder(BaseViewHolder holder, int position) {
//                super.onBindViewHolder(holder, position);
//                if (position != 0) {
//                    TextView tv = holder.getView(R.id.college_recommendation_top_tv);
//                    tv.setVisibility(View.GONE);
//                }
//
//                TextView moreTv = holder.getView(R.id.college_recommendation_course_more_tv);
//                moreTv.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        Toast.makeText(mContext, "more", Toast.LENGTH_SHORT).show();
//                    }
//                });
//
//                final List<HomeCourseBean> recommendCourses = new ArrayList<>();
//
//                RecyclerView recyclerView = holder.getView(R.id.college_more_recommendation_rv);
//                recyclerView.setAdapter(new RVRecommendationAdapter(mContext, recommendCoursesBeanList));
//                recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
//
//                recyclerView.addItemDecoration(new SimpleItemDecoration());
//
//
//
//
//            }
//        };
//        adapterList.add(adapterRecommendation);
//
//    }


    @Override
    public void onEvent(EventMessage message) {
        super.onEvent(message);
        switch (message.getTag()) {
            case Constants.EVENT_TAG.TAG_FLOAT_PLAY:
                layoutFloat.setVisibility(View.VISIBLE);
//                TextView textView = layoutFloat.findViewById(R.id.tv_float_course_name);
//                textView.setText((String)message.getData());

                break;
            case Constants.EVENT_TAG.TAG_PLAYER_DURATION:
                String duration = (String) ((HashMap) message.getData()).get("duration");
                tvFloatDuration.setText("/" + duration);
                break;
            case Constants.EVENT_TAG.TAG_PLAYER_CURRENT:

                String current = (String) ((HashMap) message.getData()).get("current");
                tvFloatCurrent.setText(current);
                break;
        }
    }

    @Override
    protected void initData() {
        super.initData();

        accessToken = PreferencesUtils.getString(mContext, CommonConstants.KEY_TOKEN, "0");
        model = ViewModelProviders.of(this).get(CourseDetailModel.class);
        courseDetailPresenter = new CourseDetailPresenter(mContext, this);
//        layoutFloat.getBackground().setAlpha(10);

    }

    private void initCourseTab() {
        HomeAdapter adapterCourseTab = new HomeAdapter(mContext, new LinearLayoutHelper(), 1, TYPE_COURSE_TAB, R.layout.course_detail_tab_layout) {
            @Override
            public void onBindViewHolder(BaseViewHolder holder, int position) {
                super.onBindViewHolder(holder, position);
                TabLayout tabLayout = holder.getView(R.id.tablayout_course_detail);

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

                for (int i = 0; i < titles.size(); i++) {
                    if (i == 0) {
                        tabLayout.getTabAt(i).select();
                    }
                    View customView = getTabView(mContext, titles.get(i), DisplayUtil.dp2px(18), DisplayUtil.dp2px(2), tabLayout.getTabAt(i).isSelected());
                    tabLayout.getTabAt(i).setCustomView(customView);

                }

                final FragmentManager fragmentManager = getSupportFragmentManager();
                final FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.add(R.id.frame_course_detail, fragments.get(0)).commit();
                tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                    @Override
                    public void onTabSelected(TabLayout.Tab tab) {
                        changeTabStatus(tab, true);
                        fragmentManager.beginTransaction().replace(R.id.frame_course_detail, fragments.get(tab.getPosition())).commit();
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


    private void initMasterRow() {

        HomeAdapter adapterMasterRow = new HomeAdapter(mContext, new LinearLayoutHelper(), 1, TYPE_MASTER_ROW, R.layout.course_detail_master_row_layout) {
            @Override
            public void onBindViewHolder(final BaseViewHolder holder, int position) {
                super.onBindViewHolder(holder, position);

                masterAvatarUrl = courseBean.getMaster_avatar_url();
                masterId = courseBean.getMaster_id();
                final CircleImageView imgMasterAvatar = holder.getView(R.id.img_course_detail_master);

                RequestOptions options = new RequestOptions()
                        .override(DisplayUtil.dp2px(40), DisplayUtil.dp2px(40))
                        .centerCrop();

                Glide.with(mContext)
                        .load(masterAvatarUrl)
                        .apply(options)
                        .into(imgMasterAvatar);

                final CircleImageView imgMasterTag = holder.getView(R.id.img_course_detail_master_tag);
                RequestOptions optionsTag = new RequestOptions()
                        .override(DisplayUtil.dp2px(15), DisplayUtil.dp2px(15))
                        .centerCrop();
                Glide.with(mContext)
                        .load(R.mipmap.vip_blue)
                        .apply(optionsTag)
                        .into(imgMasterTag);

                final TextView txtMasterName = holder.getView(R.id.tv_course_detail_course_master_name);
                txtMasterName.setText(courseBean.getMaster_name());
                txtMasterName.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Bundle bundle = new Bundle();
                        bundle.putString(MasterDetailActivity.EXTRA_MASTER_NAME, courseBean.getMaster_name());
                        bundle.putString(MasterDetailActivity.EXTRA_MASTER_ID, courseBean.getMaster_id());
                        readyGo(MasterDetailActivity.class, bundle);
                    }
                });
                final TextView txtFollow = holder.getView(R.id.tv_course_detail_follow);
                txtFollow.setText(isCheckout.equals("1") ? "已关注" : "关注");
                txtFollow.setSelected(isCheckout.equals("1"));
                txtFollow.setTextColor(isCheckout.equals("1") ? Color.parseColor("#BFBFBF") : Color.parseColor("#FF6969"));
                txtFollow.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (isLogin()) {
                            switch (isCheckout) {
                                case "1":
                                    final AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                                    builder.setMessage("取消关注？");
                                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            courseDetailPresenter.unrecordMaster(accessToken, masterId);
                                            txtFollow.setText("关注");
                                            txtFollow.setTextColor(Color.parseColor("#FF6969"));
                                            txtFollow.setSelected(false);
                                            isCheckout = "2";

                                        }
                                    });
                                    builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                        }
                                    });
                                    builder.show();


                                    break;
                                case "2":
                                    courseDetailPresenter.recordMaster(accessToken, masterId);
                                    txtFollow.setText("已关注");
                                    txtFollow.setTextColor(Color.parseColor("#BFBFBF"));
                                    txtFollow.setSelected(true);
                                    isCheckout = "1";
                                    break;
                            }
//                            }
//                            isCheckout = isCheckout.equals("1") ? "2" : "1";
//                            Toast.makeText(mContext, "click" + isCheckout, Toast.LENGTH_SHORT).show();
//                            txtFollow.setText(isCheckout.equals("1") ? "已关注" : "关注");
//                            txtFollow.setSelected(isCheckout.equals("1"));
//                            txtFollow.setTextColor(isCheckout.equals("1") ? Color.parseColor("#BFBFBF") : Color.parseColor("#FF6969"));
                        } else {
                            goToLogin();
                        }


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

                masterAvatarUrl = courseBean.getMaster_avatar_url();
                mediaType = courseBean.getMedia_type();
                mCourseSalePrice = courseBean.getSale_price();
                isPay = courseBean.getIs_pay();
                imageUrl = courseBean.getImage_url();
                FrameLayout flPurchase = holder.getView(R.id.fl_course_detail_purchase_status);
                ImageView ivCourseDetailImage = holder.getView(R.id.iv_course_detail_top);
                TextView tvCourseDetailPrice = holder.getView(R.id.tv_course_detail_course_price);
                TextView tvCourseDetailSalePrice = holder.getView(R.id.tv_course_detail_course_sale_price);
                TextView tvCourseDetailJoin = holder.getView(R.id.tv_course_detail_course_join);
                TextView tvCourseDetailBrief = holder.getView(R.id.tv_course_detail_course_brief);
                TextView tvCourseDetailCourseName = holder.getView(R.id.tv_course_detail_course_name);
                TextView tvCourseDetailCourseBought = holder.getView(R.id.tv_course_detail_course_bought);
                TextView tvCourseDetailCourseFree = holder.getView(R.id.tv_course_detail_course_free);
                TextView tvCourseDetailCourseBuy = holder.getView(R.id.tv_course_detail_course_buy);
                TextView tvCourseDetailMediaType = holder.getView(R.id.tv_course_detail_course_audio);


                if (mCourseSalePrice.equals("0.00")) {
                    tvCourseDetailSalePrice.setVisibility(View.GONE);
                    tvCourseDetailCourseFree.setVisibility(View.VISIBLE);
                    tvCourseDetailCourseBought.setVisibility(View.GONE);
                    tvCourseDetailCourseBuy.setVisibility(View.GONE);
                } else if (isPay.equals("1")) {
                    tvCourseDetailCourseFree.setVisibility(View.GONE);
                    tvCourseDetailCourseBought.setVisibility(View.VISIBLE);
                    tvCourseDetailCourseBuy.setVisibility(View.GONE);
                } else {
                    tvCourseDetailCourseFree.setVisibility(View.GONE);
                    tvCourseDetailCourseBought.setVisibility(View.GONE);
                    tvCourseDetailCourseBuy.setVisibility(View.VISIBLE);
                }

                Glide.with(mContext).load(imageUrl).into(ivCourseDetailImage);
                tvCourseDetailSalePrice.setText("￥" + courseBean.getSale_price());
                tvCourseDetailJoin.setText(courseBean.getJoin_count() + "人参加");
                tvCourseDetailBrief.setText(courseBean.getCourse_intro());
                tvCourseDetailCourseName.setText(courseBean.getCourse_name());
                tvCourseDetailPrice.setText("￥" + courseBean.getPrice());
                tvCourseDetailPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                tvCourseDetailMediaType.setText(mediaType.equals("1") ? "音频" : "视频");
                flPurchase.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        createOrder();
                    }
                });
            }
        };
        adapterList.add(adapterCourseTop);
    }

    @Override
    protected void getBundleExtras(Bundle extras) {
        super.getBundleExtras(extras);
//        courseName = extras.getString(EXTRA_COURSE_NAME);
        courseId = extras.getString(EXTRA_COURSE_ID);
    }

    @Override
    public void getCourseDetailSuccess(CourseDetailBean courseDetailBean) {
        if (courseBean != null) {
            courseBean = null;
        }
        recommendCoursesBeanList = courseDetailBean.getRecommendCourses();
        courseBean = courseDetailBean.getCourse();
        isCheckout = courseBean.getIs_checkout();
        salePrice = courseBean.getSale_price();
        shareUrl = courseBean.getShare_url();
        courseName = courseBean.getCourse_name();
        Log.d(TAG, accessToken + "\n" + courseId);
        model.setCourseDetail(courseDetailBean);
        tvJoinNow.setText("立即参加:" + salePrice + "元");
        initCourseTop();
        initMasterRow();
        initCourseTab();
//        initRecommendation();
        delegateAdapter.setAdapters(adapterList);
//        initCourseTab();

    }

    @Override
    public void getCourseDetailFail(String msg) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_course_detail_join_now:
                createOrder();
                break;
            case R.id.iv_course_detail_head_download:
                download();
                break;
            case R.id.iv_course_detail_head_share:
                share();
                break;
            case R.id.layout_audition:
                audition();
                break;
            case R.id.iv_float_course_play:
                play();
                break;
        }

    }


    private void play() {
        Log.d(TAG, "play");
        Map<String, String> params = new HashMap<>();
        params.put("play", "play");
        EventBus.getDefault().post(new EventMessage<Map>(Constants.EVENT_TAG.TAG_FLOAT_PLAY, params));
    }

    private void audition() {
        Toast.makeText(mContext, "audition", Toast.LENGTH_SHORT).show();
    }

    private void share() {

        shareDialog.show();
//        courseDetailPresenter.requestCourseDetail();
//        Toast.makeText(mContext, shareUrl, Toast.LENGTH_SHORT).show();
    }

    private void download() {
        Toast.makeText(mContext, "download", Toast.LENGTH_SHORT).show();
    }

    private void createOrder() {
        if (isLogin()) {

            Bundle bundle = new Bundle();
            bundle.putString(OrderActivity.EXTAR_COURSE_PRICE, salePrice);
            bundle.putString(OrderActivity.EXTAR_COURSE_NAME, courseName);
            bundle.putString(OrderActivity.EXTRA_COURSE_MASTER_IMAGE, courseBean.getMaster_avatar_url());
            bundle.putString(OrderActivity.EXTAR_COURSE_PRICE, salePrice);
            readyGo(OrderActivity.class, bundle);
        } else {
            goToLogin();
        }
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
                if (courseBean != null) {
                    shareDialog.dismiss();

                    courseDetailPresenter.shareArticle(courseBean.getShare_url(), courseBean.getCourse_name(), courseBean.getCourse_intro(), "you");
                }
            }
        });
        quanLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (courseBean != null) {
                    shareDialog.dismiss();

                    courseDetailPresenter.shareArticle(courseBean.getShare_url(), courseBean.getCourse_name(), courseBean.getCourse_intro(), "quan");
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

}
