package com.tuodanhuashu.app.course.ui;

import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.tuodanhuashu.app.R;
import com.tuodanhuashu.app.base.HuaShuBaseActivity;

import butterknife.BindView;

public class CourseDetailActivity2 extends HuaShuBaseActivity {
    private static final String TAG = CourseDetailActivity2.class.getSimpleName();


    @BindView(R.id.iv_course_detail_head_back)
    ImageView ivBack;
    @BindView(R.id.refresheader_course_detail)
    MaterialHeader refresheaderCourseDetail;
    @BindView(R.id.refresh_course_detail)
    SmartRefreshLayout refreshLayoutCourseDetail;
    @BindView(R.id.common_head_title_tv)
    TextView tvTitle;
    @BindView(R.id.iv_course_detail_head_download)
    ImageView ivDownload;
    @BindView(R.id.iv_course_detail_head_share)
    ImageView ivShare;
    @BindView(R.id.tv_course_detail_join_now)
    TextView tvJoinNow;
    @BindView(R.id.layout_audition)
    LinearLayout layoutAudiion;
    @BindView(R.id.layout_float)
    ConstraintLayout layoutFloat;
    @BindView(R.id.iv_float_course_play)
    ImageView ivFloatPlay;
    @BindView(R.id.tv_float_section_duration)
    TextView tvFloatSectionDuration;
    @BindView(R.id.tv_float_section_name)
    TextView tvFloatSectionName;
    @BindView(R.id.tv_float_course_current)
    TextView tvFloatCurrent;
    @BindView(R.id.iv_float_course_image)
    ImageView ivFloatImage;
    @BindView(R.id.iv_float_close)
    ImageView ivFloatClose;
    @BindView(R.id.layout_course_detail_bottom)
    LinearLayout layoutCourseDetailBottom;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_course_datail2;
    }
}
