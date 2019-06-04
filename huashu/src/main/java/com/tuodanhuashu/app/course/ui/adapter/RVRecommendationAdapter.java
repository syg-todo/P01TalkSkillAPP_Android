package com.tuodanhuashu.app.course.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.company.common.utils.DisplayUtil;
import com.tuodanhuashu.app.R;
import com.tuodanhuashu.app.course.bean.CourseDetailBean;
import com.tuodanhuashu.app.course.ui.CourseDetailActivity;
import com.tuodanhuashu.app.home.bean.HomeCourseBean;

import java.util.List;

public class RVRecommendationAdapter extends RecyclerView.Adapter<RVRecommendationAdapter.RecommendationHolder> {
    private List<CourseDetailBean.RecommendCoursesBean> courseBeanList;
    private Context mContext;

    public RVRecommendationAdapter(Context context, List<CourseDetailBean.RecommendCoursesBean> list) {
        this.courseBeanList = list;
        this.mContext = context;
    }

    @NonNull
    @Override
    public RecommendationHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_course_detail_recommendation_layout, parent, false);
        RecommendationHolder holder = new RecommendationHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final RecommendationHolder holder, final int position) {
        RequestOptions options = new RequestOptions().override(DisplayUtil.dp2px(163), DisplayUtil.dp2px(93));
        Glide.with(mContext).load(courseBeanList.get(position).getImage_url())
                .apply(options)
                .into(holder.imgImage);
        holder.tvPrice.setText("￥" + courseBeanList.get(position).getPrice());
        holder.tvPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        holder.tvSalePrice.setText("￥" + courseBeanList.get(position).getSale_price());
        holder.tvJoinCount.setText(courseBeanList.get(position).getJoin_count() + "人参加");
        holder.tvCourseName.setText(courseBeanList.get(position).getCourse_name());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString(CourseDetailActivity.EXTRA_COURSE_ID, courseBeanList.get(position).getId());
                bundle.putString(CourseDetailActivity.EXTRA_COURSE_NAME, courseBeanList.get(position).getCourse_name());
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

    class RecommendationHolder extends RecyclerView.ViewHolder {
        ImageView imgImage;
        TextView tvPrice;
        TextView tvSalePrice;
        TextView tvJoinCount;
        TextView tvCourseName;

        public RecommendationHolder(View itemView) {
            super(itemView);
            imgImage = itemView.findViewById(R.id.img_course_detail_recommend_image);
            tvPrice = itemView.findViewById(R.id.tv_course_detail_recommend_price);
            tvSalePrice = itemView.findViewById(R.id.tv_course_detail_recommend_sale_price);
            tvJoinCount = itemView.findViewById(R.id.tv_course_detail_recommend_join);
            tvCourseName = itemView.findViewById(R.id.tv_course_detail_recommend_course_name);
        }
    }
}
