package com.tuodanhuashu.app.course.ui.fragment;


import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tuodanhuashu.app.R;
import com.tuodanhuashu.app.base.HuaShuBaseFragment;
import com.tuodanhuashu.app.base.SimpleItemDecoration;
import com.tuodanhuashu.app.course.bean.DirectoryBean;


import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class CourseDetailDirectoryFragment extends HuaShuBaseFragment {
    @BindView(R.id.rv_course_detail_directory)
    RecyclerView recyclerView;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_course_detail_directory;
    }

    @Override
    protected void initView(View view) {
        super.initView(view);
        List<DirectoryBean> directoryList = new ArrayList<>();
        directoryList.add(new DirectoryBean("施琪嘉创伤30讲：走出心理阴影，重塑强大内心", "4'13''"));
        directoryList.add(new DirectoryBean("施琪嘉创伤30讲：走出心理阴影，重塑强大内心", "4'13''"));
        directoryList.add(new DirectoryBean("施琪嘉创伤30讲：走出心理阴影，重塑强大内心", "4'13''"));
        directoryList.add(new DirectoryBean("施琪嘉创伤30讲：走出心理阴影，重塑强大内心", "4'13''"));
        directoryList.add(new DirectoryBean("施琪嘉创伤30讲：走出心理阴影，重塑强大内心", "4'13''"));
        directoryList.add(new DirectoryBean("施琪嘉创伤30讲：走出心理阴影，重塑强大内心", "4'13''"));

        DirectoryAdapter adapter = new DirectoryAdapter(directoryList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.addItemDecoration(new SimpleItemDecoration());

    }

    public CourseDetailDirectoryFragment() {
    }

    class DirectoryAdapter extends RecyclerView.Adapter<DirectoryAdapter.DirectoryHolder> {
        private List<DirectoryBean> directoryList;

        public DirectoryAdapter(List<DirectoryBean> directoryList) {
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
            DirectoryBean directory = directoryList.get(position);
            Log.d("111",directory.getName());
            holder.tvName.setText(directory.getName());
            holder.tvTime.setText(directory.getTime());
        }

        @Override
        public int getItemCount() {
            Log.d("111","size:"+directoryList.size());
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
