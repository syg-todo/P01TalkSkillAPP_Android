package com.tuodanhuashu.app.course.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
import com.company.common.utils.DisplayUtil;
import com.ms.banner.Banner;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.tuodanhuashu.app.R;
import com.tuodanhuashu.app.base.HuaShuBaseActivity;
import com.tuodanhuashu.app.course.bean.CourseClassBean;
import com.tuodanhuashu.app.course.presenter.CourseListPresenter;
import com.tuodanhuashu.app.course.view.CourseListView;
import com.tuodanhuashu.app.home.bean.HomeCourseBean;
import com.tuodanhuashu.app.zhuanlan.bean.ArticleClassBean;

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
    private List<HomeCourseBean> courseBeanList;
    private CourseListPresenter courseListPresenter;
    private List<CourseClassBean> courseClassList;
    private CourseListAdapter adapter;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_course_list;
    }


    @Override
    protected void initView() {
        super.initView();


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
                        page = 1;
                        courseBeanList.clear();
                        classId = courseClassList.get(tab.getPosition()).getId();
                        courseListPresenter.requestCourseListByClassId(classId + "", page + "", page_size + "");

                    }

                    @Override
                    public void onTabUnselected(TabLayout.Tab tab) {

                    }

                    @Override
                    public void onTabReselected(TabLayout.Tab tab) {
                        courseListPresenter.requestCourseClassList();
                    }
                });
                break;

            case 2:
                commonHeadTitleTv.setText(R.string.course_community);
                tablayout.setVisibility(View.GONE);
                break;

            case 3:
                commonHeadTitleTv.setText(R.string.course_private);

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


        Log.d("111", "getCourSuccess   size:" + this.courseBeanList.size());
        adapter.setCourseBeanList(this.courseBeanList);
    }

    @Override
    public void getCourseListFail(String msg) {
    }

    @Override
    public void getClassListSuccess(List<CourseClassBean> courseClassList) {
        this.courseClassList = courseClassList;
        for (CourseClassBean courseClass : courseClassList) {
            tablayout.addTab(tablayout.newTab().setText(courseClass.getClass_name()));
        }
    }

    @Override
    public void getClassListFail(String msg) {

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
            Log.d("111", "CourseAdapter:size:" + list.size() + list.get(0).getCourse_name());
            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public CourseListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_course_list_layout, parent, false);
            CourseListHolder holder = new CourseListHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull CourseListHolder holder, int position) {
            Log.d("111", courseBeanList.get(position).getCourse_name());
            Glide.with(mContext).load(courseBeanList.get(position).getImage_url()).into(holder.imgItemCourseImage);
            holder.tvItemCourseName.setText(courseBeanList.get(position).getCourse_name());
            holder.tvItemCoursePrice.setText(String.valueOf(courseBeanList.get(position).getPrice()));
            holder.tvItemCourseSalePrice.setText(String.valueOf(courseBeanList.get(position).getSale_price()));
            holder.tvItemCourseJoinCount.setText(courseBeanList.get(position).getJoin_count() + "人参加");
        }

        @Override
        public int getItemCount() {
            return courseBeanList.size();
        }

        class CourseListHolder extends RecyclerView.ViewHolder {
            ImageView imgItemCourseImage;
            TextView tvItemCourseName;
            TextView tvItemCoursePrice;
            TextView tvItemCourseSalePrice;
            TextView tvItemCourseJoinCount;

            public CourseListHolder(View itemView) {
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
