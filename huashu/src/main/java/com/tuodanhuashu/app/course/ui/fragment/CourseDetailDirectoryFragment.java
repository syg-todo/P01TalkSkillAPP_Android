package com.tuodanhuashu.app.course.ui.fragment;


import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tuodanhuashu.app.R;
import com.tuodanhuashu.app.base.HuaShuBaseFragment;
import com.tuodanhuashu.app.base.SimpleItemDecoration;
import com.tuodanhuashu.app.course.bean.CourseDetailBean;
import com.tuodanhuashu.app.course.bean.CourseDetailModel;
import com.tuodanhuashu.app.course.ui.PlayActivity;


import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class CourseDetailDirectoryFragment extends HuaShuBaseFragment {
    @BindView(R.id.rv_course_detail_directory)
    RecyclerView recyclerView;

    private CourseDetailModel model;
    private List<CourseDetailBean.SectionsBean> sectionsBeanList = new ArrayList<>();
    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_course_detail_directory;
    }

    @Override
    protected void initView(View view) {
        super.initView(view);

        model = ViewModelProviders.of(getActivity()).get(CourseDetailModel.class);


        sectionsBeanList = model.getCourseDetail().getValue().getSections();
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
        public void onBindViewHolder(@NonNull DirectoryHolder holder, int position) {
            CourseDetailBean.SectionsBean directory = directoryList.get(position);
            holder.tvName.setText(directory.getSection_name());
            int duration = directory.getDuration();
            int minutes= duration / 60;
            int seconds = duration % 60;
            holder.tvTime.setText(minutes+"'"+seconds+"''");

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    readyGo(PlayActivity.class);
                }
            });

        }

        @Override
        public int getItemCount() {
            return directoryList.size();
        }

        class DirectoryHolder extends RecyclerView.ViewHolder {
            TextView tvName;
            TextView tvTime;

            public DirectoryHolder(View itemView) {
                super(itemView);
                tvName = itemView.findViewById(R.id.tv_item_course_detail_directory_name);
                tvTime = itemView.findViewById(R.id.tv_item_course_detail_directory_time);
            }
        }
    }
}
