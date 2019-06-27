package com.tuodanhuashu.app.widget;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.company.common.utils.DisplayUtil;
import com.company.common.utils.DrawableGetUtil;
import com.tuodanhuashu.app.R;
import com.tuodanhuashu.app.home.adapter.HuaShuSubMenuAdapter;
import com.tuodanhuashu.app.home.bean.HomeTalkSkillClassBean;
import com.tuodanhuashu.app.home.bean.HomeTalkSkillPageBean;
import com.tuodanhuashu.app.huashu.ui.HuaShuaListActivity;

import java.util.ArrayList;
import java.util.List;

public class HuaShuManuHelper {

    private List<FrameLayout> frameLayoutList;

    private List<FrameLayout> coverList;

    private List<FrameLayout> divList;

    private List<TextView> textViewList;

    private List<ImageView> ivList;

    private GridView gv;

    private HuaShuSubMenuAdapter adapter;

    private Context context;

    private HomeTalkSkillPageBean homeTalkSkillPageBean;


    public HuaShuManuHelper(List<FrameLayout> frameLayoutList,List<FrameLayout> coverList,List<FrameLayout> divList,List<TextView> textViewList,List<ImageView> ivList,GridView gv,HuaShuSubMenuAdapter adapter,Context context,HomeTalkSkillPageBean homeTalkSkillPageBean) {
        this.frameLayoutList = frameLayoutList;
        this.coverList = coverList;
        this.context = context;
        this.divList = divList;
        this.textViewList = textViewList;
        this.ivList = ivList;
        this.gv = gv;
        this.adapter = adapter;
        this.homeTalkSkillPageBean = homeTalkSkillPageBean;
        init();
    }

    private void init(){
        for(int i=0;i<frameLayoutList.size();i++){
            frameLayoutList.get(i).setOnClickListener(new onManuClickListener(i));
        }
    }

    private void performMenuClick(int position){
        for(int i=0;i<coverList.size();i++){
            if(i==position){
                coverList.get(i).setVisibility(View.VISIBLE);
                textViewList.get(i).setTextColor(ContextCompat.getColor(context,R.color.white));
                Glide.with(context).load(homeTalkSkillPageBean.getTalkSkillClasses().get(i).getCat_icon2()).into(ivList.get(i));
                if(i<=3){
                    divList.get(i).setVisibility(View.GONE);
                }
            }else{
                coverList.get(i).setVisibility(View.GONE);
                textViewList.get(i).setTextColor(ContextCompat.getColor(context,R.color.colorAccent));
                Glide.with(context).load(homeTalkSkillPageBean.getTalkSkillClasses().get(i).getCat_icon()).into(ivList.get(i));

                if(i<=3){
                    divList.get(i).setVisibility(View.VISIBLE);
                }
            }
        }

        gv.setVisibility(View.VISIBLE);
        refreshSubGrid(position);
        refreshGridBg(position);
    }


    private void refreshGridBg(int position){
        GradientDrawable gd;
        switch (position){
            case 0:
                 gd = DrawableGetUtil.getNeedDrawable(new float[]{0.0F,0.0F,8.0F,8.0F,8.0F,8.0F,8.0F,8.0F}, Color.argb(255 , 255 , 102 ,102));
                break;
            case 3:
                gd = DrawableGetUtil.getNeedDrawable(new float[]{8.0F,8.0F,0.0F,0.0F,8.0F,8.0F,8.0F,8.0F},Color.argb(255 , 255 , 102 ,102));
                break;
            case 4:
                gd = DrawableGetUtil.getNeedDrawable(new float[]{8.0F,8.0F,8.0F,8.0F,8.0F,8.0F,0.0F,0.0F},Color.argb(255 , 255 , 102 ,102));
                break;
            case 7:
                gd = DrawableGetUtil.getNeedDrawable(new float[]{8.0F,8.0F,8.0F,8.0F,0.0F,0.0F,8.0F,8.0F},Color.argb(255 , 255 , 102 ,102));
                break;
            default:
                gd = DrawableGetUtil.getNeedDrawable(new float[]{8.0F,8.0F,8.0F,8.0F,8.0F,8.0F,8.0F,8.0F},Color.argb(255 , 255 , 102 ,102));
                break;
        }
        DrawableGetUtil.setBackground(gd,gv);
    }


    private void refreshSubGrid(final int position){

        List<HomeTalkSkillClassBean> classBeanList = homeTalkSkillPageBean.getTalkSkillClasses().get(position).getSubclasses();
        adapter.refreshGrid(classBeanList);
        ViewGroup.LayoutParams layoutParams = gv.getLayoutParams();
        layoutParams.height = DisplayUtil.dip2px(context, (float) (Math.ceil(classBeanList.size()/4.0)*50));
        gv.setLayoutParams(layoutParams);
        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int subPosition, long id) {
                String className = homeTalkSkillPageBean.getTalkSkillClasses().get(position).getSubclasses().get(subPosition).getCat_name();
                String classId = homeTalkSkillPageBean.getTalkSkillClasses().get(position).getSubclasses().get(subPosition).getCat_id();
                Intent i = new Intent(context, HuaShuaListActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString(HuaShuaListActivity.EXTRA_KEY_WORDS,className);
                bundle.putString(HuaShuaListActivity.EXTRA_KEY_CLASSID,classId);
                i.putExtras(bundle);
                context.startActivity(i);
            }
        });
    }

    class onManuClickListener implements View.OnClickListener{

        private int pos;

        public onManuClickListener(int pos) {
            this.pos = pos;
        }

        @Override
        public void onClick(View v) {
            performMenuClick(pos);
        }
    }
}
