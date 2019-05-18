package com.tuodanhuashu.app.MemberCenter.ui;

import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.tuodanhuashu.app.R;
import com.tuodanhuashu.app.base.HuaShuBaseFragment;
import com.tuodanhuashu.app.base.SimpleItemDecoration;
import com.tuodanhuashu.app.home.bean.HomeCourseBean;
import com.tuodanhuashu.app.widget.RoundRectImageView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MyCourseFragment extends HuaShuBaseFragment {
    @BindView(R.id.rv_my_course)
    RecyclerView recyclerView;

    private List<HomeCourseBean> courseList;
    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_my_course;
    }

    @Override
    protected void initView(View view) {
        super.initView(view);
        courseList = new ArrayList<>();
        HomeCourseBean course = new HomeCourseBean();
        course.setImage_url("https://imgsa.baidu.com//forum//w%3D580%3B//sign=6537f28142086e066aa83f4332337af4//0b46f21fbe096b632b8a30e002338744eaf8aceb.jpg");
        course.setCourse_name("爱人先爱己，女性自爱力36堂心理提升课");
        course.setSale_price(99);
        course.setMaster_name("听过的");

        HomeCourseBean course1 = new HomeCourseBean();
        course1.setImage_url("https://imgsa.baidu.com//forum//w%3D580%3B//sign=6537f28142086e066aa83f4332337af4//0b46f21fbe096b632b8a30e002338744eaf8aceb.jpg");
        course1.setCourse_name("爱人先爱己，女性自爱力36堂心理提升课");
        course1.setSale_price(99);
        course1.setMaster_name("已付费");

        HomeCourseBean course2 = new HomeCourseBean();
        course2.setImage_url("https://imgsa.baidu.com//forum//w%3D580%3B//sign=6537f28142086e066aa83f4332337af4//0b46f21fbe096b632b8a30e002338744eaf8aceb.jpg");
        course2.setCourse_name("爱人先爱己，女性自爱力36堂心理提升课");
        course2.setSale_price(99);
        course2.setMaster_name("听过的");

        courseList.add(course);
        courseList.add(course1);
        courseList.add(course2);

        MyCourseAdapter adapter = new MyCourseAdapter(courseList);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.addItemDecoration(new SimpleItemDecoration());
    }

    class MyCourseAdapter extends RecyclerView.Adapter<MyCourseAdapter.MyCourseHolder> {

        private List<HomeCourseBean> courseList;

        public MyCourseAdapter(List<HomeCourseBean> courseList) {
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
            HomeCourseBean course = courseList.get(position);

            Glide.with(mContext).load(course.getImage_url()).into(new SimpleTarget<Drawable>() {
                @Override
                public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                    holder.imgImage.setDrawable(resource);
                }
            });
            holder.txtNmae.setText(course.getCourse_name());
            holder.txtPrice.setText("¥"+course.getSale_price());
            holder.txtTag.setText(course.getMaster_name());
        }

        @Override
        public int getItemCount() {
            return courseList.size()
                    ;
        }

        class MyCourseHolder extends RecyclerView.ViewHolder {

            RoundRectImageView imgImage;
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
