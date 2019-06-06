package com.tuodanhuashu.app.course.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.company.common.CommonConstants;
import com.company.common.utils.PreferencesUtils;
import com.tuodanhuashu.app.R;
import com.tuodanhuashu.app.base.HuaShuBaseActivity;

import butterknife.BindView;

public class OrderActivity extends HuaShuBaseActivity implements View.OnClickListener {

    @BindView(R.id.common_head_back_iv)
    ImageView ivHeadBack;
    @BindView(R.id.common_head_title_tv)
    TextView tvHeadTitle;
    @BindView(R.id.tv_order_course_join_count)
    TextView tvOrderCourseJoinCount;
    @BindView(R.id.tv_order_course_name)
    TextView tvOrderCourseName;
    @BindView(R.id.tv_order_course_price)
    TextView tvOrderCoursePrice;
    @BindView(R.id.iv_order_course_master)
    ImageView ivOrderMasterImage;


    public static final String EXTAR_COURSE_ID = "course_id";
    private String courseId = "";

    public static final String EXTAR_COURSE_NAME = "course_name";
    private String courseName = "";

    public static final String EXTAR_COURSE_PRICE = "course_price";
    private String coursePrice = "";

    public static final String EXTRA_COURSE_MASTER_IMAGE = "course_master";
    private String courseImageUrl = "";

    private static final String EXTRA_COURSE_JOIN_COUNT = "course_join_count";
    private String courseJoinCount;

    private String accessToken = "";
    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_order;
    }

    @Override
    protected void initView() {
        super.initView();

        ivHeadBack.setOnClickListener(this);
        ivHeadBack.getDrawable().setTint(getResources().getColor(R.color.black));

        tvHeadTitle.setText("创建订单");

        tvOrderCourseJoinCount.setText(courseJoinCount+"人已参加");
        tvOrderCourseName.setText(courseName);
        tvOrderCoursePrice.setText("￥"+coursePrice);
        Glide.with(mContext).load(courseImageUrl).into(ivOrderMasterImage);
    }


    @Override
    protected void initData() {
        super.initData();
        accessToken = PreferencesUtils.getString(mContext, CommonConstants.KEY_TOKEN);
    }

    @Override
    protected void getBundleExtras(Bundle extras) {
        super.getBundleExtras(extras);
        courseId = extras.getString(EXTAR_COURSE_ID);
        courseName = extras.getString(EXTAR_COURSE_NAME);
        courseImageUrl = extras.getString(EXTRA_COURSE_MASTER_IMAGE);
        coursePrice = extras.getString(EXTAR_COURSE_PRICE);
        courseJoinCount = extras.getString(EXTRA_COURSE_JOIN_COUNT);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.common_head_back_iv:
                onBackPressed();
                break;
        }
    }
}
