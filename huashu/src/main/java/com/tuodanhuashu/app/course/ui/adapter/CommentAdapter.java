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
import com.company.common.base.BasePresenter;
import com.company.common.utils.DisplayUtil;
import com.tuodanhuashu.app.Constants.Constants;
import com.tuodanhuashu.app.R;
import com.tuodanhuashu.app.base.HuaShuBaseActivity;
import com.tuodanhuashu.app.course.bean.CommentBean;
import com.tuodanhuashu.app.course.ui.AudioPlayerActivity;
import com.tuodanhuashu.app.course.ui.CourseDetailActivity;
import com.tuodanhuashu.app.eventbus.EventMessage;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;
import java.util.Map;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentHolder> {
    private static final String TAG = CommentAdapter.class.getSimpleName();
    public static final String LIKE_COMMENT_SUCCESS = "like_comment_success";
    private List<CommentBean> commentList;
    private AudioPlayerActivity mContextActivity;
    //    private CourseDetailActivity mContextCourseDetailActivity;
    private String isLike;
    //    private boolean isLike = false;//是否点赞，默认为点赞
    private String accessToken;

//    public CommentAdapter(Context mContext, List<CommentBean> commentList) {
//        this.mContext = mContext;
//        this.commentList = commentList;
//    }

    public CommentAdapter(AudioPlayerActivity mContext, List<CommentBean> commentList, String accessToken) {
        this.mContextActivity = mContext;
        this.commentList = commentList;
        this.accessToken = accessToken;
    }

    @NonNull
    @Override
    public CommentAdapter.CommentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContextActivity).inflate(R.layout.item_course_detail_comment, parent, false);
        CommentHolder holder = new CommentHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final CommentHolder holder, int position) {
        final CommentBean comment = commentList.get(position);
        RequestOptions options = new RequestOptions()
                .override(DisplayUtil.dp2px(30), DisplayUtil.dp2px(30))
                .centerCrop();
        Glide.with(mContextActivity).load(comment.getHeade_img())
                .apply(options)
                .into(holder.imgAvatar);
//        holder.tvCommenterName.setText(comment.getNickname());
        holder.tvCommenterName.setText(comment.getIs_like() + "id:" + comment.getId());
        holder.tvTime.setText(comment.getCreate_date());
        holder.tvLikeCount.setText(comment.getLike_count() + "");
        holder.tvComment.setText(comment.getContent());
        isLike = comment.getIs_like();
        List<CommentBean.ReplyBean> replyBeans = comment.getReply();
        if (replyBeans.size() == 0) {
            holder.layoutReply.setVisibility(View.GONE);
        } else {
            holder.tvReplyContent.setText(replyBeans.get(0).getContent());
            holder.tvReplyName.setText(replyBeans.get(0).getNickname());
            holder.tvReplyTime.setText(replyBeans.get(0).getCreate_date());
        }
        final String likeCount = comment.getLike_count();
        final String commentId = comment.getId();
        holder.imgThumbUp.setSelected(isLike.equals("1"));
        holder.imgThumbUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int like = Integer.parseInt(likeCount);
                int now = like;
                if (isLike.equals("1")) {//一点赞
                    v.setSelected(false);
                    mContextActivity.unlikeComment(commentId);
                    now = now-1;
                    holder.tvLikeCount.setText(now+"");
                }else if (isLike.equals("2")){
                    mContextActivity.likeComment(commentId);
                    if (!accessToken.equals("")){
                        v.setSelected(true);
                        now = now+1;
                        holder.tvLikeCount.setText(like+1+"");
                    }
                }

                isLike = isLike.equals("1")?"2":"1";

            }
        });
    }

//    @Subscribe(threadMode = ThreadMode.MAIN)
//    public void onEvent(EventMessage<Map<String, String>> eventMessage) {
//        switch (eventMessage.getTag()) {
//            case Constants.EVENT_TAG.TAG_COMMENT_LIKE_SUCCESS:
//
//
//
//        }
//    }

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
