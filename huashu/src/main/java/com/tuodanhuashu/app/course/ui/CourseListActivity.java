package com.tuodanhuashu.app.course.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.company.common.utils.DisplayUtil;
import com.ms.banner.Banner;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.tuodanhuashu.app.R;
import com.tuodanhuashu.app.base.HuaShuBaseActivity;
import com.tuodanhuashu.app.course.bean.CourseClassBean;
import com.tuodanhuashu.app.course.bean.CourseWithBannerBean;
import com.tuodanhuashu.app.course.bean.MasterBean;
import com.tuodanhuashu.app.course.presenter.CourseListPresenter;
import com.tuodanhuashu.app.course.view.CourseListView;
import com.tuodanhuashu.app.home.bean.HomeCourseBean;
import com.tuodanhuashu.app.widget.RoundRectImageView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class CourseListActivity extends HuaShuBaseActivity implements CourseListView {

    @BindView(R.id.common_head_back_iv)
    ImageView commonHeadBackIv;
    @BindView(R.id.common_head_title_tv)
    TextView commonHeadTitleTv;
    @BindView(R.id.course_list_tab)
    TabLayout tablayout;
    @BindView(R.id.rv_course_list)
    RecyclerView recyclerView;
    @BindView(R.id.smart_refresh_course_list)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.course_list_banner)
    Banner banner;


    public static final String EXTRA_ENTER_TYPE = "enter_type";//1:分类 2社群 3：私教 4:我的文章


    private int enterType = 1;

    private int page_size = 10;
    private int page = 1;
    private int classId;
    private String masterId;
    private List<HomeCourseBean> courseBeanList;
    private CourseListPresenter courseListPresenter;
    private List<CourseClassBean> courseClassList;
    private List<MasterBean> masterList;
    private CourseListAdapter adapter;
    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_course_list;
    }


    @Override
    protected void initView() {
        super.initView();
        commonHeadBackIv.getDrawable().setTint(getResources().getColor(R.color.black));
    }

    @Override
    protected void initData() {
        super.initData();
        courseListPresenter = new CourseListPresenter(mContext, this);
        switch (enterType) {
            case 1:
                commonHeadTitleTv.setText(R.string.course_sorting);
                banner.setVisibility(View.GONE);
                courseListPresenter.requestCourseClassList();
                tablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                    @Override
                                  public void onTabSelected(TabLayout.Tab tab) {
//                        page = 1;
                        courseBeanList.clear();
                        classId = courseClassList.get(tab.getPosition()).getId();
                        courseListPresenter.requestCourseListByClassId(classId + "", page + "", page_size + "");

                    }

                    @Override
                    public void onTabUnselected(TabLayout.Tab tab) {

                    }

                    @Override
                    public void onTabReselected(TabLayout.Tab tab) {
//                        courseListPresenter.requestCourseClassList();
                    }
                });
                break;

            case 2:
                commonHeadTitleTv.setText(R.string.course_community);
                courseListPresenter.requestCommunityCourseList(page + "", page_size + "");
                tablayout.setVisibility(View.GONE);
                break;

            case 3:
                commonHeadTitleTv.setText(R.string.course_private);
                courseListPresenter.requestMasterList();
                tablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                    @Override
                    public void onTabSelected(TabLayout.Tab tab) {
                        courseBeanList.clear();
                        masterId = masterList.get(tab.getPosition()).getId();
                        courseListPresenter.requestCourseListByMaterId(masterId + "", page + "", page_size + "");

                    }

                    @Override
                    public void onTabUnselected(TabLayout.Tab tab) {

                    }

                    @Override
                    public void onTabReselected(TabLayout.Tab tab) {

                    }
                });


                break;
        }
        courseBeanList = new ArrayList<>();
//        courseListPresenter.requestCourseListByClassId("1", "1", "10");
        adapter = new CourseListAdapter(mContext, courseBeanList);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
                super.onDraw(c, parent, state);
                int childCount = parent.getChildCount();
                Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
                paint.setColor(Color.parseColor("#EEEEEE"));
                paint.setStrokeWidth(DisplayUtil.dip2px(mContext, 1));
                for (int i = 0; i < childCount; i++) {
                    View child = parent.getChildAt(i);
                    c.drawLine(0, child.getBottom(), child.getRight(), child.getBottom(), paint);
                }

            }
        });
        recyclerView.setAdapter(adapter);
//        if (enterType != 2) {
//        courseListPresenter.requestCourseClassList();
//        }
//        if (enterType != 1){
//            courseListPresenter.requestCourseClassList();
//        }
//        requestArticleList(false);
//        articleListItemBeanList = new ArrayList<>();
//
//        adapter = new ZhuanLanListAdapter(mContext, R.layout.item_zhuanlan_list_layout, articleListItemBeanList);
//        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
//                ArticleListItemBean bean = articleListItemBeanList.get(position);
//                Bundle bundle = new Bundle();
//                bundle.putString(ZhuanLanDetailActivity.EXTRA_ARTICLE_ID, bean.getId());
//                readyGo(ZhuanLanDetailActivity.class, bundle);
//            }
//        });
//        zhuanlanListRlv.setAdapter(adapter);
    }

    @Override
    protected void bindListener() {
        super.bindListener();
        commonHeadBackIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

    }

    @Override
    public void getCourseListSuccess(List<HomeCourseBean> courseBeanList) {
        if (page == 1) {
            this.courseBeanList.clear();

        }
        this.courseBeanList.addAll(courseBeanList);

        adapter.setCourseBeanList(this.courseBeanList);
    }

    @Override
    public void getCourseListFail(String msg) {
    }

    @Override
    public void getCourseWithBannerSuccess(CourseWithBannerBean courseWithBannerBean) {
//        if (page == 1) {
            this.courseBeanList.clear();

//        }
        this.courseBeanList.addAll(courseWithBannerBean.getCourses());
        adapter.setCourseBeanList(this.courseBeanList);
    }

    @Override
    public void getCourseCommunityFail(String msg) {

    }

    @Override
    public void getClassListSuccess(List<CourseClassBean> courseClassList) {
        tablayout.removeAllTabs();
        this.courseClassList = courseClassList;
        for (CourseClassBean courseClass : courseClassList) {
            tablayout.addTab(tablayout.newTab().setText(courseClass.getClass_name()));
        }
    }

    @Override
    public void getClassListFail(String msg) {

    }

    @Override
    public void getMasterListSuccess(List<MasterBean> masterList) {
        this.masterList = masterList;
        tablayout.removeAllTabs();
        for (MasterBean masterBean : masterList) {
            tablayout.addTab(tablayout.newTab().setText(masterBean.getName()));
        }
    }

    @Override
    public void getMasterListFail(String msg) {

    }

    @Override
    protected void getBundleExtras(Bundle extras) {
        super.getBundleExtras(extras);
        enterType = extras.getInt(EXTRA_ENTER_TYPE, 1);
    }


    class CourseListAdapter extends RecyclerView.Adapter<CourseListAdapter.CourseListHolder> {
        private Context mContext;
        private List<HomeCourseBean> courseBeanList;

        public CourseListAdapter(Context context, List<HomeCourseBean> list) {
            this.mContext = context;
            this.courseBeanList = list;
        }

        public void setCourseBeanList(List<HomeCourseBean> list) {
            this.courseBeanList = list == null ? new ArrayList<HomeCourseBean>() : list;
            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public CourseListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_course_list_layout, parent, false);
            final CourseListHolder holder = new CourseListHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull final CourseListHolder holder, int position) {
            final HomeCourseBean course = courseBeanList.get(position);

            Glide.with(mContext).load(course.getImage_url()).into(new SimpleTarget<Drawable>() {
                @Override
                public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                    holder.imgItemCourseImage.setDrawable(resource);
                }
            });
            holder.tvItemCourseName.setText(course.getCourse_name());
            holder.tvItemCoursePrice.setText(String.valueOf(course.getPrice()));
            holder.tvItemCourseSalePrice.setText(String.valueOf(course.getSale_price()));
            holder.tvItemCourseJoinCount.setText(course.getJoin_count() + "人参加");

            holder.tvItemCoursePrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle bundle = new Bundle();
                    bundle.putString(CourseDetailActivity.EXTRA_COURSE_NAME,course.getCourse_name());
                    readyGo(CourseDetailActivity.class,bundle);
                }
            });

        }

        @Override
        public int getItemCount() {
            return courseBeanList.size();
        }

        class CourseListHolder extends RecyclerView.ViewHolder {
            RoundRectImageView imgItemCourseImage;
            TextView tvItemCourseName;
            TextView tvItemCoursePrice;
            TextView tvItemCourseSalePrice;
            TextView tvItemCourseJoinCount;

            public CourseListHolder(final View itemView) {
                super(itemView);

                imgItemCourseImage = itemView.findViewById(R.id.iv_item_course_list_image);
                tvItemCourseName = itemView.findViewById(R.id.tv_item_course_list_name);
                tvItemCoursePrice = itemView.findViewById(R.id.tv_item_course_list_price);
                tvItemCourseSalePrice = itemView.findViewById(R.id.tv_item_course_list_sale_price);
                tvItemCourseJoinCount = itemView.findViewById(R.id.tv_item_course_list_join);

            }
        }
    }
}
