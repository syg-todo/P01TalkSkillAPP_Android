package com.tuodanhuashu.app.home.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.alibaba.android.vlayout.layout.GridLayoutHelper;
import com.alibaba.android.vlayout.layout.LinearLayoutHelper;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.chad.library.adapter.base.BaseViewHolder;
import com.company.common.utils.DisplayUtil;
import com.company.common.utils.KeyboardUtils;
import com.company.common.utils.RandomUntil;
import com.company.common.utils.StringUtils;
import com.ms.banner.Banner;
import com.ms.banner.BannerConfig;
import com.ms.banner.holder.BannerViewHolder;
import com.ms.banner.holder.HolderCreator;
import com.ms.banner.listener.OnBannerClickListener;
import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.tuodanhuashu.app.R;
import com.tuodanhuashu.app.base.HuaShuBaseFragment;
import com.tuodanhuashu.app.course.ui.CourseListActivity;
import com.tuodanhuashu.app.home.adapter.HomeAdapter;
import com.tuodanhuashu.app.home.adapter.HomeBannerViewHolder;
import com.tuodanhuashu.app.home.bean.HomeBannerBean;
import com.tuodanhuashu.app.home.bean.HomeCollegePageBean;
import com.tuodanhuashu.app.home.bean.HomeCourseBean;
import com.tuodanhuashu.app.home.bean.HomeZhuanLanPageBean;
import com.tuodanhuashu.app.home.presenter.HomeCollegePresenter;
import com.tuodanhuashu.app.home.presenter.HomeZhuanLanPresenter;
import com.tuodanhuashu.app.home.view.HomeCollegeView;
import com.tuodanhuashu.app.huashu.ui.HuaShuaListActivity;
import com.tuodanhuashu.app.widget.RoundRectImageView;
import com.tuodanhuashu.app.zhuanlan.ui.ZhuanLanDetailActivity;
import com.tuodanhuashu.app.zhuanlan.ui.ZhuanLanListActivity;
import com.tuodanhuashu.app.zhuanlan.ui.ZhuanLanSearchActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

public class CollegeFragment extends HuaShuBaseFragment implements HomeCollegeView {
    @BindView(R.id.college_refresheader)
    MaterialHeader collegeRefresheader;
    @BindView(R.id.college_rlv)
    RecyclerView collegeRlv;
    @BindView(R.id.college_refreshlayout)
    SmartRefreshLayout collegeRefreshLayout;

    private DelegateAdapter delegateAdapter;

    private List<DelegateAdapter.Adapter> adapterList;

    private static final int TYPE_BANNER = 1;

    private static final int TYPE_SEARCH = 2;

    private static final int TYPE_COURSE = 3;

    private static final int TYPE_ACTIVITY = 4;

    private static final int TYPE_CHOICENESS = 5;

    private static final int TYPE_RECOMMENDATION = 6;

    private HomeCollegePresenter collegePresenter;

    private HomeCollegePageBean collegePageBean;

    private boolean isFirstRefresh = true;

    int arr[] = new int[3];

    public CollegeFragment() {
        // Required empty public constructor
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_college;
    }

    @Override
    protected void initView(View view) {
        super.initView(view);
        collegePresenter = new HomeCollegePresenter(this, mContext);
        adapterList = new ArrayList<>();
        collegeRefresheader.setColorSchemeColors(mContext.getResources().getColor(R.color.colorAccent));
        VirtualLayoutManager layoutManager = new VirtualLayoutManager(mContext);
        collegeRlv.setLayoutManager(layoutManager);
        RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
        collegeRlv.setRecycledViewPool(viewPool);
        viewPool.setMaxRecycledViews(0, 10);
        delegateAdapter = new DelegateAdapter(layoutManager, true);
        collegeRlv.setAdapter(delegateAdapter);
//        initBanner();
//        initSearch();
//        initArticle();
//        initCategory();
//        initAD();
//        initArticleList();
//        delegateAdapter.setAdapters(adapterList);
        collegePresenter.requestZhuanLanData();
    }

    @Override
    public void getCollegeDataSuccess(HomeCollegePageBean collegePageBean) {
        this.collegePageBean = collegePageBean;
//        if(collegePageBean.getRecommendedArticles()!=null&&zhuanLanPageBean.getRecommendedArticles().size()>=3){
//            arr = RandomUntil.randomArr(new int[3],zhuanLanPageBean.getRecommendedArticles().size()-1);
//
//        }else if(collegePageBean.getRecommendedArticles().size()==1){
//            arr[0] = 0;
//            arr[1] = 0;
//        }else if(collegePageBean.getRecommendedArticles().size()==2){
//            arr[0] = 0;
//            arr[1] = 1;
//            arr[2] = 0;
//        }
        if (isFirstRefresh) {
            initBanner();
            initSearch();
            initCourse();
            initActivity();
            initChoicenessCourses();
            initRecommendations();
//            initCategory();
//            initAD();
//            initArticleList();
            delegateAdapter.setAdapters(adapterList);
            isFirstRefresh = false;
        } else {

        }
    }

    private void initActivity() {
        HomeAdapter activityAdapter = new HomeAdapter(mContext,new LinearLayoutHelper(),1,TYPE_ACTIVITY,R.layout.college_activity_layout){
            @Override
            public void onBindViewHolder(BaseViewHolder holder, int position) {
                super.onBindViewHolder(holder, position);
                ImageView imageView = holder.getView(R.id.iv_college_activity);
                imageView.setImageDrawable(getResources().getDrawable(R.mipmap.activity));
            }
        };
        adapterList.add(activityAdapter);
    }

    private void initRecommendations() {
        HomeAdapter recommendationsAdapter = new HomeAdapter(mContext, new LinearLayoutHelper(), 1, TYPE_RECOMMENDATION, R.layout.college_recommendation_layout) {
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

                final List<HomeCourseBean> recommendCourses = collegePageBean.getRecommendCourses();
                RecyclerView recyclerView = holder.getView(R.id.college_more_recommendation_rv);
                recyclerView.setAdapter(new RVRecommendationAdapter(mContext, recommendCourses));
                recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
                recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
                    @Override
                    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                        super.getItemOffsets(outRect, view, parent, state);
                        int childCount = parent.getChildCount();
                        int actualCount = parent.getAdapter().getItemCount();
                        for (int i = 0; i < childCount; i++) {
                            View child = parent.getChildAt(i);
                            int index = parent.getChildAdapterPosition(child);

                            if (index != actualCount - 1) {
                                outRect.set(DisplayUtil.dip2px(mContext, 10), DisplayUtil.dip2px(mContext, 20),
                                        DisplayUtil.dip2px(mContext, 10), DisplayUtil.dip2px(mContext, 0));
                            } else {
                                outRect.set(DisplayUtil.dip2px(mContext, 10), DisplayUtil.dip2px(mContext, 20),
                                        DisplayUtil.dip2px(mContext, 10), DisplayUtil.dip2px(mContext, 20));
                            }
                        }

                    }
                });
            }
        };
        adapterList.add(recommendationsAdapter);
    }

    private void initChoicenessCourses() {

        HomeAdapter ChoicenessAdapter = new HomeAdapter(mContext, new LinearLayoutHelper(), 1, TYPE_CHOICENESS, R.layout.college_choiceness_layout) {
            @Override
            public void onBindViewHolder(BaseViewHolder holder, final int position) {
                super.onBindViewHolder(holder, position);

                if (position != 0) {
                    TextView tv = holder.getView(R.id.college_choiceness_course_top_tv);
                    tv.setVisibility(View.GONE);
                }

                TextView moreTv = holder.getView(R.id.college_choiceness_course_more_tv);
                moreTv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(mContext, "more", Toast.LENGTH_SHORT).show();
                    }
                });
                final List<HomeCourseBean> choicenessCourses = collegePageBean.getChoicenessCourses();
                RecyclerView recyclerView = holder.getView(R.id.college_choiceness_course_rv);
                recyclerView.setAdapter(new RVChoicenessAdapter(mContext, choicenessCourses));
            }
        };
        adapterList.add(ChoicenessAdapter);
    }

    class RVChoicenessAdapter extends RecyclerView.Adapter<RVChoicenessAdapter.ChoicenessHolder> {
        private List<HomeCourseBean> courseBeanList;
        private Context mContext;

        public RVChoicenessAdapter(Context context, List<HomeCourseBean> list) {
            this.courseBeanList = list;
            this.mContext = context;
        }

        @NonNull
        @Override
        public ChoicenessHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_college_course_layout, parent, false);
            ChoicenessHolder holder = new ChoicenessHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull final ChoicenessHolder holder, int position) {
            Glide.with(mContext).load(courseBeanList.get(position).getImage_url()).into(new SimpleTarget<Drawable>() {
                @Override
                public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                    holder.imgImage.setDrawable(resource);
                }
            });
            holder.txtCourseName.setText(courseBeanList.get(position).getCourse_name());
            holder.txtCourseMasterName.setText(courseBeanList.get(position).getMaster_name());
            holder.txtCoursePrice.setText("￥" + courseBeanList.get(position).getPrice());
        }

        @Override
        public int getItemCount() {
            return 4;
        }

        class ChoicenessHolder extends RecyclerView.ViewHolder {
            RoundRectImageView imgImage;
            TextView txtCourseName;
            TextView txtCourseMasterName;
            TextView txtCoursePrice;

            public ChoicenessHolder(View itemView) {
                super(itemView);
                imgImage = itemView.findViewById(R.id.img_course_image);
                txtCourseName = itemView.findViewById(R.id.tv_course_name);
                txtCourseMasterName = itemView.findViewById(R.id.tv_course_master_name);
                txtCoursePrice = itemView.findViewById(R.id.tv_course_price);
            }
        }
    }

    public class RVRecommendationAdapter extends RecyclerView.Adapter<RVRecommendationAdapter.RecommendationHolder> {
        private List<HomeCourseBean> courseBeanList;
        private Context mContext;

        public RVRecommendationAdapter(Context context, List<HomeCourseBean> list) {
            this.courseBeanList = list;
            this.mContext = context;
        }

        @NonNull
        @Override
        public RecommendationHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_college_recommendation_layout, parent, false);
            RecommendationHolder holder = new RecommendationHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull final RecommendationHolder holder, int position) {
            Glide.with(mContext).load(courseBeanList.get(position).getImage_url()).into(new SimpleTarget<Drawable>() {
                @Override
                public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                    holder.imgImage.setDrawable(resource);
                }
            });
            holder.txtCourseName.setText(courseBeanList.get(position).getCourse_name());
            holder.txtCourseMasterName.setText(courseBeanList.get(position).getMaster_name());
            holder.txtCoursePrice.setText("￥" + courseBeanList.get(position).getPrice());
        }

        @Override
        public int getItemCount() {
            return courseBeanList.size();
        }

        class RecommendationHolder extends RecyclerView.ViewHolder {
            RoundRectImageView imgImage;
            TextView txtCourseName;
            TextView txtCourseMasterName;
            TextView txtCoursePrice;

            public RecommendationHolder(View itemView) {
                super(itemView);
                imgImage = itemView.findViewById(R.id.img_recommend_image);
                txtCourseName = itemView.findViewById(R.id.tv_recommend_course_name);
                txtCourseMasterName = itemView.findViewById(R.id.tv_recommend_master_name);
                txtCoursePrice = itemView.findViewById(R.id.tv_recommend_price);
            }
        }
    }


    private void initCourse() {
        HomeAdapter courseAdapter = new HomeAdapter(mContext, new LinearLayoutHelper(), 1, TYPE_COURSE, R.layout.college_course_layout) {
            @Override
            public void onBindViewHolder(BaseViewHolder holder, int position) {
                super.onBindViewHolder(holder, position);

                final ImageView iv1 = holder.getView(R.id.college_course_iv_1);
                final TextView tv1 = holder.getView(R.id.college_course_tv_1);
                final ImageView iv2 = holder.getView(R.id.college_course_iv_2);
                final TextView tv2 = holder.getView(R.id.college_course_tv_2);
                final ImageView iv3 = holder.getView(R.id.college_course_iv_3);
                final TextView tv3 = holder.getView(R.id.college_course_tv_3);

                Glide.with(mContext).load(R.mipmap.sorting_classes).into(iv1);
                tv1.setText(R.string.course_sorting);
                Glide.with(mContext).load(R.mipmap.community_class).into(iv2);
                tv2.setText(R.string.course_community);
                Glide.with(mContext).load(R.mipmap.private_class).into(iv3);
                tv3.setText(R.string.course_private);

                holder.getView(R.id.college_course_sorting).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Bundle bundle = new Bundle();
                        bundle.putInt(CourseListActivity.EXTRA_ENTER_TYPE, 1);
                        readyGo(CourseListActivity.class, bundle);
                    }
                });

                holder.getView(R.id.college_course_community).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Bundle bundle = new Bundle();
                        bundle.putInt(CourseListActivity.EXTRA_ENTER_TYPE, 2);
                        readyGo(CourseListActivity.class, bundle);
                    }
                });


                holder.getView(R.id.college_course_private).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Bundle bundle = new Bundle();
                        bundle.putInt(CourseListActivity.EXTRA_ENTER_TYPE, 3);
                        readyGo(CourseListActivity.class, bundle);
                    }
                });

            }
        };
        adapterList.add(courseAdapter);
    }

    private void initBanner() {
        HomeAdapter bannerAdapter = new HomeAdapter(mContext, new LinearLayoutHelper(), 1, TYPE_BANNER, R.layout.zhuanlan_banner_layout) {
            @Override
            public void onBindViewHolder(BaseViewHolder holder, int position) {
                super.onBindViewHolder(holder, position);
                final List<HomeBannerBean> homeBannerBeanList = collegePageBean.getBanners();

                // 绑定数据
                Banner mBanner = holder.getView(R.id.zhuanlan_banner);
                mBanner.setAutoPlay(true)
                        .setOffscreenPageLimit(homeBannerBeanList.size())
                        .setPages(homeBannerBeanList, new HolderCreator<BannerViewHolder>() {
                            @Override
                            public BannerViewHolder createViewHolder() {
                                return new HomeBannerViewHolder();
                            }
                        })
                        .setBannerStyle(BannerConfig.CIRCLE_INDICATOR)
                        .start();
                mBanner.setOnBannerClickListener(new OnBannerClickListener() {
                    @Override
                    public void onBannerClick(int position) {
                        Intent i = new Intent();
                        Bundle b = new Bundle();
                        switch (homeBannerBeanList.get(position).getBanner_type()) {
                            case "2":
                                i.setClass(mContext, HuaShuaListActivity.class);
                                b.putString(HuaShuaListActivity.EXTRA_KEY_WORDS, homeBannerBeanList.get(position).getKeyword());
                                i.putExtras(b);
                                startActivity(i);
                                break;
                            case "3":
                                i.setClass(mContext, ZhuanLanDetailActivity.class);
                                b.putString(ZhuanLanDetailActivity.EXTRA_ARTICLE_ID, homeBannerBeanList.get(position).getArticle_id());
                                i.putExtras(b);
                                startActivity(i);
                                break;
                        }
                    }
                });
            }
        };
        adapterList.add(bannerAdapter);
    }

    private void initSearch() {
        HomeAdapter searchAdapter = new HomeAdapter(mContext, new LinearLayoutHelper(), 1, TYPE_SEARCH, R.layout.college_search_layout) {
            @Override
            public void onBindViewHolder(BaseViewHolder holder, int position) {
                super.onBindViewHolder(holder, position);
                final EditText et = holder.getView(R.id.zhuanlan_search_et);
                et.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                    @Override
                    public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                        if (i == EditorInfo.IME_ACTION_SEARCH) {
                            String keywords = et.getText().toString();
                            if (StringUtils.isEmpty(keywords)) {
                                showToast("请输入搜索内容");

                            } else {
                                KeyboardUtils.hideSoftInput(getActivity());
                                Bundle bundle = new Bundle();

                                bundle.putString(ZhuanLanSearchActivity.EXTRA_KEY_WORDS, keywords);
                                readyGo(ZhuanLanSearchActivity.class, bundle);
                            }

                            return true;
                        }
                        return false;
                    }
                });


            }
        };
        adapterList.add(searchAdapter);

    }

}
