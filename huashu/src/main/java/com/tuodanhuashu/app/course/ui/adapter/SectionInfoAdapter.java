package com.tuodanhuashu.app.course.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.tuodanhuashu.app.R;
import com.tuodanhuashu.app.course.bean.CourseDetailBean;
import com.tuodanhuashu.app.course.bean.SectionBean;
import com.tuodanhuashu.app.course.ui.AudioPlayActivity;
import com.tuodanhuashu.app.course.ui.AudioPlayerActivity;
import com.tuodanhuashu.app.utils.TimeFormater;

import java.util.List;

public class SectionInfoAdapter extends RecyclerView.Adapter<SectionInfoAdapter.CourseTabHolder> {

    private AudioPlayerActivity context;
    private List<SectionBean.SectionInfo> sectionsList;
    private String currentSectionId;
    private String courseId;
    public SectionInfoAdapter(AudioPlayerActivity context, List<SectionBean.SectionInfo> sectionsList,String currentSectionId,String courseId) {
        this.context = context;
        this.sectionsList = sectionsList;
        this.currentSectionId = currentSectionId;
        this.courseId = courseId;
    }

    @NonNull
    @Override
    public CourseTabHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_course_tab,parent,false);
        CourseTabHolder holder = new CourseTabHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull CourseTabHolder holder, int position) {
        final SectionBean.SectionInfo sectionInfo = sectionsList.get(position);
        if (sectionInfo.getId().equals(currentSectionId)){
            Log.d("111",currentSectionId+"-----------"+sectionInfo.getId());
            holder.layout.setSelected(true);
            holder.tvCoursePlaying.setVisibility(View.VISIBLE);
            holder.tvCourseDuration.setVisibility(View.INVISIBLE);
            holder.tvCourseType.setTextColor(context.getResources().getColor(R.color.white));
            holder.tvCourseType.setBackground(context.getResources().getDrawable(R.drawable.shape_text_red_solid_border));
            holder.tvCourseName.setTextColor(context.getResources().getColor(R.color.colorAccent));
        }else {
            holder.layout.setSelected(false);
            holder.tvCoursePlaying.setVisibility(View.INVISIBLE);
            holder.tvCourseDuration.setVisibility(View.VISIBLE);
            holder.tvCourseType.setTextColor(context.getResources().getColor(R.color.text_gray));
            holder.tvCourseType.setBackground(context.getResources().getDrawable(R.drawable.shape_text_gray_solid_border));
            holder.tvCourseName.setTextColor(context.getResources().getColor(R.color.text_gray));
        }
        if (sectionInfo.getIs_audition().equals("1")){
            holder.ivCourseLock.setVisibility(View.INVISIBLE);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, AudioPlayerActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString(AudioPlayActivity.EXTRA_SECTION_ID, sectionInfo.getId());
                    bundle.putString(AudioPlayActivity.EXTAR_COURSE_ID, courseId);
                    intent.putExtras(bundle);
                    context.startActivity(intent);
                    context.finish();
                }
            });
        }else {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context,"非试听内容，请先购买",Toast.LENGTH_SHORT).show();
                }
            });
            holder.ivCourseLock.setVisibility(View.VISIBLE);
        }
//        for (SectionBean.SectionInfo sectionInfo:sectionsList){
//            if (sectionInfo.getId().equals(currentId)){
//                holder.layout.setSelected(true);
//            }else {
//                holder.layout.setSelected(false);
//            }
//        }
        holder.tvCourseName.setText(sectionInfo.getSection_name());
        holder.tvCourseDuration.setText(TimeFormater.formatMs(Long.parseLong(sectionInfo.getDuration())*1000));
    }

    @Override
    public int getItemCount() {
        return sectionsList.size();
    }

    class CourseTabHolder extends RecyclerView.ViewHolder{
        TextView tvCourseName;
        ConstraintLayout layout;
        TextView tvCourseType;
        TextView tvCoursePlaying;
        TextView tvCourseDuration;
        ImageView ivCourseLock;
        public CourseTabHolder(View itemView) {
            super(itemView);
            tvCourseName =itemView.findViewById(R.id.tv_item_course_tab_name);
            layout = itemView.findViewById(R.id.layout_item_course_tab);
            tvCourseType = itemView.findViewById(R.id.tv_item_course_tab_type);
            tvCoursePlaying = itemView.findViewById(R.id.tv_item_course_tab_playing);
            tvCourseDuration = itemView.findViewById(R.id.tv_item_course_tab_duration);
            ivCourseLock = itemView.findViewById(R.id.iv_item_course_tab_lock);
        }
    }
}
