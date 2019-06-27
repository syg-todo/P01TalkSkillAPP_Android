package com.tuodanhuashu.app.course.ui.fragment;

import android.arch.lifecycle.ViewModelProviders;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.LinearLayoutManager;
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
import com.company.common.utils.DisplayUtil;
import com.tuodanhuashu.app.R;
import com.tuodanhuashu.app.base.HuaShuBaseFragment;
import com.tuodanhuashu.app.base.SimpleItemDecoration;
import com.tuodanhuashu.app.course.bean.CommentBean;
import com.tuodanhuashu.app.course.bean.CourseDetailBean;
import com.tuodanhuashu.app.course.bean.CourseDetailModel;
import com.tuodanhuashu.app.course.ui.AudioPlayerActivity;
import com.tuodanhuashu.app.course.ui.CourseDetailActivity;
import com.tuodanhuashu.app.course.ui.adapter.CommentAdapter;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class CourseDetailCommentFragment extends HuaShuBaseFragment {

    private static final String TAG = CourseDetailCommentFragment.class.getSimpleName();
    @BindView(R.id.rv_course_detail_comment)
    RecyclerView recyclerView;

    private CourseDetailModel model;
    private List<CommentBean> commentsBeanList = new ArrayList<>();

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_course_detail_comment;
    }

    @Override
    protected void initView(View view) {
        super.initView(view);

        model = ViewModelProviders.of(getActivity()).get(CourseDetailModel.class);
        commentsBeanList = model.getCourseDetail().getValue().getComments();

//
//        //测试数据
//        for (int i = 0; i < 3; i++) {
//            commentsBeanList.get(i).setContent("看过周梵老师很多文章，看过周梵老师很多文章，看过周梵老师很多文章，看过周梵老师很多文章，看过周梵老师很多文章，看过周梵老师很多文章。");
//            commentsBeanList.get(i).setCreate_date("2019-03-27");
//            commentsBeanList.get(i).setHeade_img("https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=1671816861,451680427&fm=27&gp=0.jpg");
//
//        }
//
//
////        commentsBeanList.get(1).setReply(null);
//        commentsBeanList.get(0).getReply().get(0).setContent("看过周梵老师很多文章，看过周梵老师很多文章，看过周梵老师很多文章，看过周梵老师很多文章，看过周梵老师很多文章，看过周梵老师很多文章。");
//        //测试数据


        CommentAdapter adapter = new CommentAdapter((CourseDetailActivity) getActivity(), commentsBeanList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));

        recyclerView.addItemDecoration(new SimpleItemDecoration());

    }

    public CourseDetailCommentFragment() {
    }


    class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentHolder> {

        private List<CommentBean> commentList;
        private CourseDetailActivity mContextActivity;
        //    private CourseDetailActivity mContextCourseDetailActivity;
        private boolean isLike = false;//是否点赞，默认为点赞

//    public CommentAdapter(Context mContext, List<CommentBean> commentList) {
//        this.mContext = mContext;
//        this.commentList = commentList;
//    }

        public CommentAdapter(CourseDetailActivity mContext, List<CommentBean> commentList){
            this.mContextActivity =  mContext;
            this.commentList = commentList;
        }

        @NonNull
        @Override
        public CommentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
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

            holder.tvCommenterName.setText(comment.getNickname().equals("")?comment.getMobile():comment.getNickname());
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
            final String commentId = comment.getId();
            holder.imgThumbUp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    isLike = !isLike;
                    v.setSelected(isLike);
                    int like = Integer.parseInt(likeCount);
                    int now = isLike ? like + 1 : like;
                    if (isLike){
                        mContextActivity.likeComment(commentId);
                    }else {
                        mContextActivity.unlikeComment(commentId);
                    }
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


}
