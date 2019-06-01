package com.tuodanhuashu.app.course.ui.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.company.common.base.BasePresenter;
import com.company.common.net.OkNetUtils;
import com.company.common.utils.DisplayUtil;
import com.tuodanhuashu.app.R;
import com.tuodanhuashu.app.course.bean.CommentBean;
import com.tuodanhuashu.app.course.bean.CourseDetailBean;
import com.tuodanhuashu.app.course.presenter.AudioPlayPresenter;
import com.tuodanhuashu.app.course.presenter.CourseDetailPresenter;
import com.tuodanhuashu.app.course.ui.AudioPlayActivity;
import com.tuodanhuashu.app.course.ui.fragment.CourseDetailCommentFragment;

import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentHolder> {

    private Context mContext;
    private List<CommentBean> commentList;
    private AudioPlayActivity mContextActivity;

    private BasePresenter presenter;
    private boolean isLike = false;//是否点赞，默认为点赞

    public CommentAdapter(Context mContext, List<CommentBean> commentList) {
        this.mContext = mContext;
        this.commentList = commentList;
    }

//    public CommentAdapter(AudioPlayActivity mContext,List<CommentBean> commentList){
//        this.mContextActivity = mContext;
//        this.commentList = commentList;
//    }

    @NonNull
    @Override
    public CommentAdapter.CommentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_course_detail_comment, parent, false);
        CommentHolder holder = new CommentHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final CommentHolder holder, int position) {
        final CommentBean comment = commentList.get(position);
        RequestOptions options = new RequestOptions()
                .override(DisplayUtil.dp2px(30), DisplayUtil.dp2px(30))
                .centerCrop();
        Glide.with(mContext).load(comment.getHeade_img())
                .apply(options)
                .into(holder.imgAvatar);
        holder.tvCommenterName.setText(comment.getNickname());
        holder.tvTime.setText(comment.getCreate_date());
        holder.tvLikeCount.setText(comment.getLike_count() + "");
        holder.tvComment.setText(comment.getContent());
        List<CommentBean.ReplyBean> replyBeans = comment.getReply();
        if (replyBeans.size() == 0) {
            holder.layoutReply.setVisibility(View.GONE);
        } else {
            holder.tvReplyContent.setText(replyBeans.get(0).getContent());
            holder.tvReplyName.setText(replyBeans.get(0).getNickname());
            holder.tvReplyTime.setText(replyBeans.get(0).getCreate_date());
        }
        final String likeCount = comment.getLike_count();
        holder.imgThumbUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isLike = !isLike;

                v.setSelected(isLike);
                int like = Integer.parseInt(likeCount);
                int now = isLike ? like + 1 : like;
                holder.tvLikeCount.setText(now + "");
            }
        });

    }

    @Override
    public int getItemCount() {
        return commentList.size();
    }

    class CommentHolder extends RecyclerView.ViewHolder {
        ImageView imgAvatar;
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
