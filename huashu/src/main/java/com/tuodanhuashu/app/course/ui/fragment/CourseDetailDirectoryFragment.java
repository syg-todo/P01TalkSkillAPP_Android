package com.tuodanhuashu.app.course.ui.fragment;


import android.arch.lifecycle.ViewModelProviders;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.tuodanhuashu.app.R;
import com.tuodanhuashu.app.base.HuaShuBaseFragment;
import com.tuodanhuashu.app.base.SimpleItemDecoration;
import com.tuodanhuashu.app.course.bean.CourseDetailBean;
import com.tuodanhuashu.app.course.bean.CourseDetailModel;
import com.tuodanhuashu.app.course.bean.SectionBean;
import com.tuodanhuashu.app.course.ui.AudioPlayActivity;


import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class CourseDetailDirectoryFragment extends HuaShuBaseFragment {
    @BindView(R.id.rv_course_detail_directory)
    RecyclerView recyclerView;

    private CourseDetailModel model;
    private List<CourseDetailBean.SectionsBean> sectionsBeanList = new ArrayList<>();
    private String isAudition;//是否可以试听 1可以
    private String isPay;//是否已购买
    private CourseDetailBean courseDetailBean;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_course_detail_directory;
    }

    @Override
    protected void initView(View view) {
        super.initView(view);


        model = ViewModelProviders.of(getActivity()).get(CourseDetailModel.class);
        courseDetailBean = model.getCourseDetail().getValue();

        sectionsBeanList = courseDetailBean.getSections();
        isPay = courseDetailBean.getCourse().getIs_pay();

        DirectoryAdapter adapter = new DirectoryAdapter(sectionsBeanList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.addItemDecoration(new SimpleItemDecoration());

    }

    public CourseDetailDirectoryFragment() {
    }

    class DirectoryAdapter extends RecyclerView.Adapter<DirectoryAdapter.DirectoryHolder> {
        private List<CourseDetailBean.SectionsBean> directoryList;

        public DirectoryAdapter(List<CourseDetailBean.SectionsBean> directoryList) {
            this.directoryList = directoryList;
        }

        @NonNull
        @Override
        public DirectoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_course_detail_directory, parent, false);
            DirectoryHolder holder = new DirectoryHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull final DirectoryHolder holder, int position) {
            final CourseDetailBean.SectionsBean directory = directoryList.get(position);
            holder.tvName.setText(directory.getSection_name());
            int duration = Integer.parseInt(directory.getDuration());
            int minutes = duration / 60;
            int seconds = duration % 60;

            isAudition = directory.getIs_audition();
            String time = String.format("%02d:%02d", minutes, seconds);
            holder.tvTime.setText(time);


            if (isPay.equals("1")) {
                holder.ivPause.setVisibility(View.GONE);
                holder.ivPlay.setVisibility(View.VISIBLE);
                holder.ivLock.setVisibility(View.GONE);
                holder.tvAudition.setVisibility(View.GONE);

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        goToAudio(directory);
                    }
                });

            } else {
                if (isAudition.equals("1")) {
                    holder.ivPause.setVisibility(View.GONE);
                    holder.ivPlay.setVisibility(View.GONE);
                    holder.ivLock.setVisibility(View.GONE);
                    holder.tvAudition.setVisibility(View.VISIBLE);

                    holder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            goToAudio(directory);
                        }
                    });

                    holder.tvAudition.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            v.setVisibility(View.GONE);
                            holder.ivPause.setVisibility(View.VISIBLE);
                        }
                    });

                } else {
                    holder.ivPause.setVisibility(View.GONE);
                    holder.ivPlay.setVisibility(View.GONE);
                    holder.ivLock.setVisibility(View.VISIBLE);
                    holder.tvAudition.setVisibility(View.GONE);
                    holder.tvName.setTextColor(Color.parseColor("#ff999999"));
                }
            }
        }

        private void goToAudio(CourseDetailBean.SectionsBean directory) {
            Bundle bundle = new Bundle();
            bundle.putString(AudioPlayActivity.EXTRA_SECTION_ID, directory.getId());
            bundle.putString(AudioPlayActivity.EXTAR_COURSE_ID, directory.getCourse_id());
            readyGo(AudioPlayActivity.class, bundle);
        }

        @Override
        public int getItemCount() {
            return directoryList.size();
        }

        class DirectoryHolder extends RecyclerView.ViewHolder {
            TextView tvName;
            TextView tvTime;
            ImageView ivPlay;
            ImageView ivPause;
            ImageView ivLock;
            TextView tvAudition;

            public DirectoryHolder(View itemView) {
                super(itemView);
                tvName = itemView.findViewById(R.id.tv_item_course_detail_directory_name);
                tvTime = itemView.findViewById(R.id.tv_item_course_detail_directory_time);
                ivPlay = itemView.findViewById(R.id.iv_course_detail_section_play);
                ivPause = itemView.findViewById(R.id.iv_course_detail_section_pause);
                ivLock = itemView.findViewById(R.id.iv_course_detail_section_lock);
                tvAudition = itemView.findViewById(R.id.tv_course_detail_section_audition);
            }
        }
    }

    private void goToAudio(){

    }
}
