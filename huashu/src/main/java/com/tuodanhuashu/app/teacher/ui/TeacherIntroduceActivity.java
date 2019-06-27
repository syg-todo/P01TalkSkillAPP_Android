package com.tuodanhuashu.app.teacher.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.company.common.utils.StringUtils;
import com.tuodanhuashu.app.R;
import com.tuodanhuashu.app.base.HuaShuBaseActivity;
import com.tuodanhuashu.app.teacher.adapter.TeacherIntroAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TeacherIntroduceActivity extends HuaShuBaseActivity {

    @BindView(R.id.common_head_back_iv)
    ImageView commonHeadBackIv;
    @BindView(R.id.common_head_title_tv)
    TextView commonHeadTitleTv;
    @BindView(R.id.teacher_intro_lv)
    ListView teacherIntroLv;

    public static final String EXTRA_TEACHER_LIST = "extra_teacher_list";
    @BindView(R.id.ask_teacher_tv)
    TextView askTeacherTv;

    private TeacherIntroAdapter adapter;

    private String teacher[];

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_teacher_introduce;
    }

    @Override
    protected void initView() {
        super.initView();
        commonHeadTitleTv.setText("导师介绍");
        commonHeadBackIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void initData() {
        super.initData();
        if (teacher != null && teacher.length > 0) {
            adapter = new TeacherIntroAdapter(mContext, teacher);
            teacherIntroLv.setAdapter(adapter);
        }

    }

    @Override
    protected void getBundleExtras(Bundle extras) {
        super.getBundleExtras(extras);
        String str = extras.getString(EXTRA_TEACHER_LIST, "");
        if (!StringUtils.isEmpty(str)) {
            teacher = str.split(",");

        }

    }

    @Override
    protected void bindListener() {
        super.bindListener();
        askTeacherTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isLogin()){
                    goToIm();
                }else{
                    goToLogin();
                }
            }
        });
    }
}
