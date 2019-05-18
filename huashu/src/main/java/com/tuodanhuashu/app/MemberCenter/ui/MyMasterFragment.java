package com.tuodanhuashu.app.MemberCenter.ui;

import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.company.common.utils.DisplayUtil;
import com.tuodanhuashu.app.R;
import com.tuodanhuashu.app.base.HuaShuBaseFragment;
import com.tuodanhuashu.app.base.SimpleItemDecoration;
import com.tuodanhuashu.app.course.bean.MasterBean;
import com.tuodanhuashu.app.widget.RoundRectImageView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MyMasterFragment extends HuaShuBaseFragment {

    @BindView(R.id.rv_my_course)
    RecyclerView recyclerView;

    private List<MasterBean> masterList = new ArrayList<>();

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_my_course;
    }

    @Override
    protected void initView(View view) {
        super.initView(view);

        MasterBean master = new MasterBean();
        master.setId("1");
        master.setName("施琪嘉111");

        masterList.add(master);
        masterList.add(master);
        masterList.add(master);
        masterList.add(master);
        masterList.add(master);
        masterList.add(master);
        masterList.add(master);
        masterList.add(master);
        masterList.add(master);
        MyMasterAdapter adapter = new MyMasterAdapter(masterList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.addItemDecoration(new SimpleItemDecoration());
    }


    class MyMasterAdapter extends RecyclerView.Adapter<MyMasterAdapter.MyMasterHolder> {
        private List<MasterBean> masterList;

        public MyMasterAdapter(List<MasterBean> masterList) {
            this.masterList = masterList;
        }

        @NonNull
        @Override
        public MyMasterHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_my_master_layout, parent, false);
            MyMasterHolder holder = new MyMasterHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull final MyMasterHolder holder, final int position) {

            MasterBean master = masterList.get(position);
            RequestOptions optionsAvatar = new RequestOptions()
                    .override(DisplayUtil.dp2px(50), DisplayUtil.dp2px(50))
                    .centerCrop();

            Glide.with(mContext).load("https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=1671816861,451680427&fm=27&gp=0.jpg")
                    .apply(optionsAvatar)
                    .into(new SimpleTarget<Drawable>() {
                        @Override
                        public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                            holder.imgAvatar.setDrawable(resource);
                        }
                    });


            RequestOptions optionsVip = new RequestOptions()
                    .override(DisplayUtil.dp2px(15), DisplayUtil.dp2px(15))
                    .centerCrop();

            Glide.with(mContext).load(R.mipmap.vip_blue)
                    .apply(optionsVip)
                    .into(new SimpleTarget<Drawable>() {
                        @Override
                        public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                            holder.imgVip.setDrawable(resource);
                        }
                    });

            holder.txtNmae.setText("施琪嘉111");
            holder.txtBrief.setText("一幅画千言万语，一棵树别样人生一幅画千言万语，一棵树别样人生");
            holder.txtFollow.setSelected(true);
            holder.txtFollow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(mContext, "取消关注？", Toast.LENGTH_SHORT).show();
                }
            });
        }

        @Override
        public int getItemCount() {
            return masterList.size();
        }

        class MyMasterHolder extends RecyclerView.ViewHolder {

            RoundRectImageView imgAvatar;
            RoundRectImageView imgVip;
            private TextView txtNmae;
            private TextView txtBrief;
            private TextView txtFollow;


            public MyMasterHolder(View itemView) {
                super(itemView);

                imgAvatar = itemView.findViewById(R.id.iv_my_master_avatar);
                imgVip = itemView.findViewById(R.id.iv_my_master_vip);
                txtNmae = itemView.findViewById(R.id.tv_my_master_name);
                txtBrief = itemView.findViewById(R.id.tv_my_master_brief);
                txtFollow = itemView.findViewById(R.id.tv_my_master_follow);
            }
        }
    }
}
