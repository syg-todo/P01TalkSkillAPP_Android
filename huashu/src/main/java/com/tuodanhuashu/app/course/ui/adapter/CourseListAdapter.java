package com.tuodanhuashu.app.course.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
import com.tuodanhuashu.app.R;
import com.tuodanhuashu.app.course.ui.CourseDetailActivity;
import com.tuodanhuashu.app.course.ui.CourseListActivity;
import com.tuodanhuashu.app.home.bean.HomeCourseBean;

import java.util.ArrayList;
import java.util.List;

public class CourseListAdapter extends RecyclerView.Adapter<CourseListAdapter.CourseListHolder> {
    private Context mContext;
    private List<HomeCourseBean> courseBeanList;

    public CourseListAdapter(Context context, List<HomeCourseBean> list) {
        this.mContext = context;
        this.courseBeanList = list;
    }

    @NonNull
    @Override
    public CourseListAdapter.CourseListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_course_list_layout, parent, false);
        final CourseListAdapter.CourseListHolder holder = new CourseListAdapter.CourseListHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final CourseListAdapter.CourseListHolder holder, int position) {
        final HomeCourseBean course = courseBeanList.get(position);

        Glide.with(mContext).load(course.getImage_url()).into(holder.imgItemCourseImage);
        holder.tvItemCourseName.setText(course.getCourse_name());
        holder.tvItemCoursePrice.setText(String.valueOf(course.getPrice()));
        holder.tvItemCourseSalePrice.setText(String.valueOf(course.getSale_price()));
        holder.tvItemCourseJoinCount.setText(course.getJoin_count() + "人参加");

        holder.tvItemCoursePrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString(CourseDetailActivity.EXTRA_COURSE_NAME, course.getCourse_name());
                Intent intent = new Intent(mContext, CourseDetailActivity.class);
                intent.putExtras(bundle);
                mContext.startActivity(intent);

            }
        });

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

