package com.tuodanhuashu.app.course.ui.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.tuodanhuashu.app.R;
import com.tuodanhuashu.app.home.bean.HomeCourseBean;
import com.tuodanhuashu.app.home.ui.CollegeFragment;
import com.tuodanhuashu.app.widget.RoundRectImageView;

import java.util.List;

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
