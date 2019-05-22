package com.tuodanhuashu.app.course.ui.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
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
import com.tuodanhuashu.app.course.ui.fragment.CourseDetailCommentFragment;
import com.tuodanhuashu.app.widget.RoundRectImageView;

import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentHolder> {

    private Context mContext;
    private List<CourseDetailBean.CommentsBean> commentList;

    public CommentAdapter(Context mContext, List<CourseDetailBean.CommentsBean> commentList) {
        this.mContext = mContext;
        this.commentList = commentList;
    }

    @NonNull
    @Override
    public CommentAdapter.CommentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_course_detail_comment, parent, false);
        CommentHolder holder = new CommentHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final CommentHolder holder, int position) {
        CourseDetailBean.CommentsBean comment = commentList.get(position);
        RequestOptions options = new RequestOptions()
                .override(DisplayUtil.dp2px(30), DisplayUtil.dp2px(30))
                .centerCrop();
        Glide.with(mContext).load(comment.getHeade_img())
                .apply(options)
                .into(new SimpleTarget<Drawable>() {
                    @Override
                    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                        holder.imgAvatar.setDrawable(resource);
                    }
                });
        holder.tvCommenterName.setText(comment.getNickname());
        holder.tvTime.setText(comment.getCreate_date());
        holder.tvLikeCount.setText(comment.getLike_count() + "");
        holder.tvComment.setText(comment.getContent());
        List<CourseDetailBean.CommentsBean.ReplyBean> replyBeans = comment.getReply();
        if ( replyBeans == null) {
            holder.layoutReply.setVisibility(View.GONE);
        } else {
            holder.tvReplyContent.setText(replyBeans.get(0).getContent());
            holder.tvReplyName.setText(replyBeans.get(0).getNickname());
            holder.tvReplyTime.setText(replyBeans.get(0).getCreate_date());
        }

    }

    @Override
    public int getItemCount() {
        return commentList.size();
    }

    class CommentHolder extends RecyclerView.ViewHolder {
        RoundRectImageView imgAvatar;
        TextView tvCommenterName;
        TextView tvTime;
        TextView tvLikeCount;
        TextView tvComment;
        ImageView imgThumbUp;
        TextView tvReplyName;
        TextView tvReplyContent;
        TextView tvReplyTime;
        ConstraintLayout layoutReply;

        public CommentHolder(View itemView) {
            super(itemView);
            imgAvatar = itemView.findViewById(R.id.iv_item_course_detail_comment_avatar);
            tvCommenterName = itemView.findViewById(R.id.tv_item_course_detail_comment_commenter_name);
            tvTime = itemView.findViewById(R.id.tv_item_course_detail_comment_time);
            tvLikeCount = itemView.findViewById(R.id.tv_item_course_detail_comment_commenter_like_count);
            tvComment = itemView.findViewById(R.id.tv_item_course_detail_comment_comment);
            imgThumbUp = itemView.findViewById(R.id.iv_item_course_detail_comment_thumb_up);
            tvReplyName = itemView.findViewById(R.id.tv_item_course_detail_comment_reply_name);
            tvReplyContent = itemView.findViewById(R.id.tv_item_course_detail_comment_reply_content);
            tvReplyTime = itemView.findViewById(R.id.tv_item_course_detail_comment_reply_time);
            layoutReply = itemView.findViewById(R.id.layout_item_course_detail_reply);
        }
    }
}
