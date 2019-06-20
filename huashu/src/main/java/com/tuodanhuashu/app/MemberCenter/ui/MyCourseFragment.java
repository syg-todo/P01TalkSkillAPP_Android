package com.tuodanhuashu.app.MemberCenter.ui;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
import com.company.common.CommonConstants;
import com.company.common.utils.PreferencesUtils;
import com.tuodanhuashu.app.R;
import com.tuodanhuashu.app.base.HuaShuBaseFragment;
import com.tuodanhuashu.app.base.SimpleItemDecoration;
import com.tuodanhuashu.app.course.presenter.MyCoursePresenter;
import com.tuodanhuashu.app.course.ui.CourseDetailActivity;
import com.tuodanhuashu.app.course.view.MyCourseView;
import com.tuodanhuashu.app.home.bean.HomeCourseBean;
import com.tuodanhuashu.app.home.bean.MyCourseBean;
import com.tuodanhuashu.app.utils.PriceFormater;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MyCourseFragment extends HuaShuBaseFragment implements MyCourseView {
    private static final String TAG = MyCourseFragment.class.getSimpleName();
    @BindView(R.id.rv_my_course)
    RecyclerView recyclerView;

    private MyCoursePresenter myCoursePresenter;
    private MyCourseAdapter adapter;
    private String accessToken;
    private List<MyCourseBean> courseList = new ArrayList<>();

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_my_course;
    }

    @Override
    protected void initView(View view) {
        super.initView(view);
        courseList = new ArrayList<>();

        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.addItemDecoration(new SimpleItemDecoration());
    }


    @Override
    protected void initData() {
        super.initData();
        accessToken = PreferencesUtils.getString(mContext, CommonConstants.KEY_TOKEN);
        myCoursePresenter = new MyCoursePresenter(mContext, this);
        myCoursePresenter.requestMyCourse(accessToken);
    }

    @Override
    public void geMyCourseSuccess(List<MyCourseBean> courseBeans) {
        courseList = courseBeans;
        adapter = new MyCourseAdapter(courseList);
        recyclerView.setAdapter(adapter);

    }

    @Override
    public void getMyCourseFail(String msg) {

    }

    class MyCourseAdapter extends RecyclerView.Adapter<MyCourseAdapter.MyCourseHolder> {

        private List<MyCourseBean> courseList;

        public MyCourseAdapter(List<MyCourseBean> courseList) {
            this.courseList = courseList;
        }

        @NonNull
        @Override
        public MyCourseHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_my_course_layout, parent, false);
            MyCourseHolder holder = new MyCourseHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull final MyCourseHolder holder, int position) {
            final MyCourseBean course = courseList.get(position);
            Glide.with(mContext).load("http://huashu.zhongpin.me"+course.getImage_url()).into(holder.imgImage);
            holder.txtNmae.setText(course.getCourse_name());

            String salePrice = course.getSale_price();
            String activityPrice = course.getActivity_price();
            String pirce = course.getPrice();
            String finalPrice = PriceFormater.formatPrice(activityPrice,salePrice,pirce);

            holder.txtPrice.setText(finalPrice);
            if (course.getIs_buy().equals("1")) {
                holder.txtTag.setText("已付费");
            } else {
                holder.txtTag.setText("听过的");
            }

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    bundle.putString(CourseDetailActivity.EXTRA_COURSE_ID, course.getId());
                    bundle.putString(CourseDetailActivity.EXTRA_COURSE_NAME, course.getCourse_name());
                    readyGo(CourseDetailActivity.class, bundle);
                }
            });
        }

        @Override
        public int getItemCount() {
            return courseList.size();
        }

        class MyCourseHolder extends RecyclerView.ViewHolder {

            ImageView imgImage;
            private TextView txtNmae;
            private TextView txtTag;
            private TextView txtPrice;

            public MyCourseHolder(View itemView) {
                super(itemView);

                imgImage = itemView.findViewById(R.id.iv_my_course_image);
                txtNmae = itemView.findViewById(R.id.tv_my_course_name);
                txtTag = itemView.findViewById(R.id.tv_my_course_tag);
                txtPrice = itemView.findViewById(R.id.tv_my_course_price);
            }
        }

    }
}
