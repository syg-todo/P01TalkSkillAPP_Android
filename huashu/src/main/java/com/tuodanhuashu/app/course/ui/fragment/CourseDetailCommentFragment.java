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
import com.tuodanhuashu.app.course.ui.adapter.CommentAdapter;
import com.tuodanhuashu.app.widget.RoundRectImageView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class CourseDetailCommentFragment extends HuaShuBaseFragment {

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


        CommentAdapter adapter = new CommentAdapter(mContext,commentsBeanList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));

        recyclerView.addItemDecoration(new SimpleItemDecoration());

    }

    public CourseDetailCommentFragment() {
    }


}
