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
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.company.common.utils.DisplayUtil;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tuodanhuashu.app.R;
import com.tuodanhuashu.app.course.bean.VideoBean;
import com.tuodanhuashu.app.widget.RoundRectImageView;

import java.util.List;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.VideoHolder> {

    private Context mContext;
    private List<VideoBean> videoBeanList;

    public VideoAdapter(Context mContext, List<VideoBean> videoBeanList) {
        this.mContext = mContext;
        this.videoBeanList = videoBeanList;
    }

    @NonNull
    @Override
    public VideoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_video,parent,false);
        VideoHolder holder = new VideoHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final VideoHolder holder, int position) {
        VideoBean videoBean = videoBeanList.get(position);
        holder.tvItemName.setText(videoBean.getName());
        holder.tvItemTitle.setText(videoBean.getTitle());
        holder.tvItemPlayVolume.setText(videoBean.getVolume()+"");

        RequestOptions options1 = new RequestOptions().override(DisplayUtil.dp2px(175),DisplayUtil.dp2px(233)).centerCrop();
        Glide.with(mContext).load(videoBean.getImage())
                .apply(options1)
                .into(new SimpleTarget<Drawable>() {
            @Override
            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                holder.ivItemImage.setDrawable(resource);
            }
        });

        RequestOptions options = new RequestOptions().override(DisplayUtil.dp2px(18),DisplayUtil.dp2px(18))
                .centerCrop();

        Glide.with(mContext).load(videoBean.getAvatar())
                .apply(options)
                .into(new SimpleTarget<Drawable>() {
            @Override
            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                holder.ivItemAvatar.setDrawable(resource);
            }
        });
    }

    @Override
    public int getItemCount() {
        return videoBeanList.size();
    }

    class VideoHolder extends RecyclerView.ViewHolder{

        TextView tvItemTitle;
        TextView tvItemName;
        TextView tvItemPlayVolume;
        RoundRectImageView ivItemImage;
        RoundRectImageView ivItemAvatar;

        public VideoHolder(View itemView) {
            super(itemView);

            tvItemTitle = itemView.findViewById(R.id.tv_video_item_title);
            tvItemName = itemView.findViewById(R.id.tv_video_item_name);
            tvItemPlayVolume = itemView.findViewById(R.id.tv_video_item_play_volume);
            ivItemImage = itemView.findViewById(R.id.iv_video_item_image);
            ivItemAvatar = itemView.findViewById(R.id.iv_video_item_avatar);
        }
    }
}
