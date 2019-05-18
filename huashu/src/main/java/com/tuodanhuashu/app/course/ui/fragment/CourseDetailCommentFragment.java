package com.tuodanhuashu.app.course.ui.fragment;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
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
import com.tuodanhuashu.app.base.HuaShuBaseFragment;
import com.tuodanhuashu.app.course.bean.CommentBean;
import com.tuodanhuashu.app.widget.RoundRectImageView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class CourseDetailCommentFragment extends HuaShuBaseFragment {

    @BindView(R.id.rv_course_detail_comment)
    RecyclerView recyclerView;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_course_detail_comment;
    }

    @Override
    protected void initView(View view) {
        super.initView(view);
        List<CommentBean> commentList = new ArrayList<>();
        commentList.add(new CommentBean("https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=1671816861,451680427&fm=27&gp=0.jpg",
                "风浪科技", "2018-02-05", "看过周梵老师很多文章，看过周梵老师很多文章，看过周梵老师很多文章，看过周梵老师很多文章，看过周梵老师很多文章，看过周梵老师很多文章。",
                20));

        commentList.add(new CommentBean("https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=1671816861,451680427&fm=27&gp=0.jpg",
                "风浪科技", "2018-02-05", "看过周梵老师很多文章，看过周梵老师很多文章，看过周梵老师很多文章，看过周梵老师很多文章，看过周梵老师很多文章，看过周梵老师很多文章。",
                20));

        commentList.add(new CommentBean("https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=1671816861,451680427&fm=27&gp=0.jpg",
                "风浪科技", "2018-02-05", "看过周梵老师很多文章，看过周梵老师很多文章，看过周梵老师很多文章，看过周梵老师很多文章，看过周梵老师很多文章，看过周梵老师很多文章。",
                20));

        commentList.add(new CommentBean("https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=1671816861,451680427&fm=27&gp=0.jpg",
                "风浪科技", "2018-02-05", "看过周梵老师很多文章，看过周梵老师很多文章，看过周梵老师很多文章，看过周梵老师很多文章，看过周梵老师很多文章，看过周梵老师很多文章。",
                20));

        commentList.add(new CommentBean("https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=1671816861,451680427&fm=27&gp=0.jpg",
                "风浪科技", "2018-02-05", "看过周梵老师很多文章，看过周梵老师很多文章，看过周梵老师很多文章，看过周梵老师很多文章，看过周梵老师很多文章，看过周梵老师很多文章。",
                20));

        commentList.add(new CommentBean("https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=1671816861,451680427&fm=27&gp=0.jpg",
                "风浪科技", "2018-02-05", "看过周梵老师很多文章，看过周梵老师很多文章，看过周梵老师很多文章，看过周梵老师很多文章，看过周梵老师很多文章，看过周梵老师很多文章。",
                20));
        CommentAdapter adapter = new CommentAdapter(commentList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));

        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
                super.onDraw(c, parent, state);
                int childCount = parent.getChildCount();
                Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
                paint.setColor(Color.parseColor("#EEEEEE"));
                paint.setStrokeWidth(DisplayUtil.dip2px(mContext, 1));
                for (int i = 0; i < childCount; i++) {
                    View child = parent.getChildAt(i);
                    c.drawLine(0, child.getBottom(), child.getRight(), child.getBottom(), paint);
                }

            }
        });


    }

    public CourseDetailCommentFragment() {
    }


    class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentHolder> {

        private List<CommentBean> commentList;

        public CommentAdapter(List<CommentBean> commentList) {
            this.commentList = commentList;
        }

        @NonNull
        @Override
        public CommentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_course_detail_comment, parent, false);
            CommentHolder holder = new CommentHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull final CommentHolder holder, int position) {
            CommentBean comment = commentList.get(position);
            RequestOptions options = new RequestOptions()
                    .override(DisplayUtil.dp2px(30),DisplayUtil.dp2px(30))
                    .centerCrop();
            Glide.with(mContext).load(comment.getAvatar())
                    .apply(options)
                    .into(new SimpleTarget<Drawable>() {
                @Override
                public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                    holder.imgAvatar.setDrawable(resource);
                }
            });

            holder.txtCommenterName.setText(comment.getName());
            holder.txtTime.setText(comment.getTime());
            holder.txtLikeCount.setText(comment.getLike_count() + "");
            holder.txtComment.setText(comment.getComment());

        }

        @Override
        public int getItemCount() {
            return commentList.size();
        }

        class CommentHolder extends RecyclerView.ViewHolder {
            RoundRectImageView imgAvatar;
            TextView txtCommenterName;
            TextView txtTime;
            TextView txtLikeCount;
            TextView txtComment;
            ImageView imgThumbUp;

            public CommentHolder(View itemView) {
                super(itemView);
                imgAvatar = itemView.findViewById(R.id.iv_item_course_detail_comment_avatar);
                txtCommenterName = itemView.findViewById(R.id.tv_item_course_detail_comment_commenter_name);
                txtTime = itemView.findViewById(R.id.tv_item_course_detail_comment_time);
                txtLikeCount = itemView.findViewById(R.id.tv_item_course_detail_comment_commenter_like_count);
                txtComment = itemView.findViewById(R.id.tv_item_course_detail_comment_comment);
                imgThumbUp = itemView.findViewById(R.id.iv_item_course_detail_comment_thumb_up);
            }
        }
    }

}
