package com.tuodanhuashu.app.teacher.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.tuodanhuashu.app.R;
import com.tuodanhuashu.app.teacher.bean.TeacherAdBean;
import com.tuodanhuashu.app.teacher.bean.TeacherListItemBean;
import com.tuodanhuashu.app.teacher.bean.TeacherMutiTypeBean;
import com.tuodanhuashu.app.teacher.ui.TeacherIntroduceActivity;
import com.tuodanhuashu.app.web.CommonWebActivity;

import java.util.List;

public class TeacherListAdapter  extends BaseMultiItemQuickAdapter<TeacherMutiTypeBean, BaseViewHolder> {

    private Context context;

    public TeacherListAdapter(Context context,List<TeacherMutiTypeBean> data) {
        super(data);
        this.context = context;
        addItemType(TeacherMutiTypeBean.TYPE_AD, R.layout.teacher_ad_layout);
        addItemType(TeacherMutiTypeBean.TYPE_TEACHER, R.layout.teacher_pic_layout);
    }

    @Override
    protected void convert(BaseViewHolder helper, TeacherMutiTypeBean item) {
        switch (helper.getItemViewType()){
            case TeacherMutiTypeBean.TYPE_AD:
               final List<TeacherAdBean> adBeanList = item.getAdBeanList();
                ImageView adIv1 = helper.getView(R.id.teacher_ad_iv1);
                ImageView adIv2 = helper.getView(R.id.teacher_ad_iv2);
                ImageView adIv3 = helper.getView(R.id.teacher_ad_iv3);
                Glide.with(context).load(adBeanList.get(0).getImage_url()).into(adIv1);
                Glide.with(context).load(adBeanList.get(1).getImage_url()).into(adIv2);
                Glide.with(context).load(adBeanList.get(2).getImage_url()).into(adIv3);
                adIv1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(context,CommonWebActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString(CommonWebActivity.EXTRA_WV_URL,adBeanList.get(0).getUrl());
                        bundle.putString(CommonWebActivity.EXTRA_WV_TITLE,"广告详情");
                        i.putExtras(bundle);
                        context.startActivity(i);
                    }
                });
                adIv2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(context,CommonWebActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString(CommonWebActivity.EXTRA_WV_URL,adBeanList.get(1).getUrl());
                        bundle.putString(CommonWebActivity.EXTRA_WV_TITLE,"广告详情");
                        i.putExtras(bundle);
                        context.startActivity(i);
                    }
                });
                adIv3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(context,CommonWebActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString(CommonWebActivity.EXTRA_WV_URL,adBeanList.get(2).getUrl());
                        bundle.putString(CommonWebActivity.EXTRA_WV_TITLE,"广告详情");
                        i.putExtras(bundle);
                        context.startActivity(i);
                    }
                });
                break;
            case TeacherMutiTypeBean.TYPE_TEACHER:
                final TeacherListItemBean itemBean = item.getTeacherListItemBean();
                ImageView teacherIv = helper.getView(R.id.teacher_pic_iv);
                Glide.with(context).load(itemBean.getThumb()).into(teacherIv);
                teacherIv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(context,TeacherIntroduceActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString(TeacherIntroduceActivity.EXTRA_TEACHER_LIST,itemBean.getDetails());
                        i.putExtras(bundle);
                        context.startActivity(i);
                    }
                });
                break;
        }


    }
}
