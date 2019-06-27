package com.tuodanhuashu.app.huashu.adapter;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.company.common.CommonConstants;
import com.company.common.utils.PreferencesUtils;
import com.company.common.utils.StringUtils;
import com.tuodanhuashu.app.R;
import com.tuodanhuashu.app.home.bean.HomeAnswerBean;
import com.tuodanhuashu.app.huashu.bean.MutiTypeBean;
import com.tuodanhuashu.app.huashu.ui.HuaShuaListActivity;
import com.tuodanhuashu.app.pay.ui.VipPayActivity;
import com.tuodanhuashu.app.teacher.ui.TeacherListActivity;
import com.tuodanhuashu.app.user.ui.LoginActivity;
import com.tuodanhuashu.app.web.CommonWebActivity;
import com.tuodanhuashu.app.widget.ClipDialog;

import java.util.List;

public class HuaShuListAdapter extends BaseMultiItemQuickAdapter<MutiTypeBean, BaseViewHolder> {


    private Context context;

    //private List<String> keywords;

    public HuaShuListAdapter(Context context,@Nullable List<MutiTypeBean> data) {
        super(data);
        this.context = context;
        addItemType(MutiTypeBean.COMMON, R.layout.item_huashu_list_layout);
        addItemType(MutiTypeBean.NO_VIP, R.layout.item_huashu_list_novip_layout);
        addItemType(MutiTypeBean.AD, R.layout.item_huashu_list_ad_layout);
    }

//    public HuaShuListAdapter(Context context,@Nullable List<MutiTypeBean> data,List<String> keywords) {
//        super(data);
//        this.context = context;
//        this.keywords = keywords;
//        addItemType(MutiTypeBean.COMMON, R.layout.item_huashu_list_layout);
//        addItemType(MutiTypeBean.NO_VIP, R.layout.item_huashu_list_novip_layout);
//        addItemType(MutiTypeBean.AD, R.layout.item_huashu_list_ad_layout);
//    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    protected void convert(BaseViewHolder helper,final MutiTypeBean item) {
        switch (helper.getItemViewType()){
            case MutiTypeBean.COMMON:
                TextView headTv = helper.getView(R.id.item_huashu_list_head_tv);
                LinearLayout answerLl = helper.getView(R.id.talk_skill_answer_ll);
                ImageView questionIv = helper.getView(R.id.item_huashu_list_question_iv);
                questionIv.setImageResource(item.talkSkillItemBean.getQuestion().getSex().equals("1")?R.drawable.nanxing:R.drawable.question_nv);
                TextView questionTv = helper.getView(R.id.item_huashu_list_question_tv);
                questionTv.setText(item.talkSkillItemBean.getQuestion().getContent());
                if(helper.getLayoutPosition()==0){
                    headTv.setVisibility(View.VISIBLE);
                }else{
                    headTv.setVisibility(View.GONE);
                }
                headTv.setText("搜索结果");
                for(final HomeAnswerBean bean:item.talkSkillItemBean.getAnswers()){
                    View answerView = LayoutInflater.from(context).inflate(R.layout.item_home_answer_layout,null);
                    ImageView answerIv = answerView.findViewById(R.id.item_home_answer_sex_iv);
                    TextView answerTv = answerView.findViewById(R.id.item_home_answer_tv);
                    ImageView clipiv = answerView.findViewById(R.id.home_talkskill_clip_iv);
                    clipiv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ClipboardManager cm = (ClipboardManager)mContext.getSystemService(Context.CLIPBOARD_SERVICE);
                            ClipData mClipData = ClipData.newPlainText("Label", bean.getContent());
                            cm.setPrimaryClip(mClipData);
                            ClipDialog clipDialog = new ClipDialog(mContext);
                            clipDialog.show();
                        }
                    });
                    SpannableString spannableString = new SpannableString(bean.getContent());
                    if(item.talkSkillItemBean.getKeywords()!=null&&item.talkSkillItemBean.getKeywords().size()>0&&!StringUtils.isEmpty(bean.getContent())){
                        for(String keyword:item.talkSkillItemBean.getKeywords()){
                            if(!StringUtils.isEmpty(keyword)){
                                int index = bean.getContent().indexOf(keyword);
                                if(index!=-1){
                                    while(index!=-1) {
                                        //Log.e("span",index+"=index "+keyword+"=keowrd  "+bean.getContent().length()+" content="+bean.getContent());
                                        spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#FF6666")), index,index+keyword.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);

                                        index = bean.getContent().indexOf(keyword,index+1);
                                    }
                                }
                            }

                        }
                    }
                    answerTv.setText(spannableString);
                    answerIv.setImageResource(bean.getSex().equals("1")?R.drawable.nanxing:R.drawable.question_nv);
                    answerLl.addView(answerView);
                }
                break;
            case MutiTypeBean.NO_VIP:
                TextView noVipHeadTv = helper.getView(R.id.item_huashu_list_novip_head_tv);
                ImageView questionIv2 = helper.getView(R.id.item_huashu_list_novip_question_iv);
                questionIv2.setImageResource(item.talkSkillItemBean.getQuestion().getSex().equals("1")?R.drawable.nanxing:R.drawable.question_nv);
                TextView questionTv2 = helper.getView(R.id.item_huashu_list_novip_question_tv);
                questionTv2.setText(item.talkSkillItemBean.getQuestion().getContent());
                Button buyBtn = helper.getView(R.id.huashu_list_buy_vip_btn);
                buyBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String token = PreferencesUtils.getString(mContext,CommonConstants.KEY_TOKEN,"");
                        if(StringUtils.isEmpty(token)){
                            PreferencesUtils.putString(mContext, CommonConstants.KEY_ACCOUNT_ID,"");
                            PreferencesUtils.putString(mContext, CommonConstants.KEY_TOKEN,"");
                            Intent intent = new Intent(mContext, LoginActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(intent);
                        }else{
                            Intent intent = new Intent(mContext, VipPayActivity.class);
                            context.startActivity(intent);
                        }
                    }
                });
                if(helper.getLayoutPosition()==0){
                     noVipHeadTv.setVisibility(View.VISIBLE);
                }else{
                     noVipHeadTv.setVisibility(View.GONE);
                }

                break;
            case MutiTypeBean.AD:
                ImageView adIv = helper.getView(R.id.huashu_list_ad_iv);
                Glide.with(context).load(item.advertisingBean.getImage_url()).into(adIv);
                LinearLayout getMijiLl = helper.getView(R.id.huashu_list_miji_ll);
                LinearLayout masterLl = helper.getView(R.id.huashu_list_master_ll);
                getMijiLl.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ((HuaShuaListActivity)context).getMiji();
                    }
                });
                masterLl.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i =new Intent(context, TeacherListActivity.class);
                        context.startActivity(i);
                    }
                });
                adIv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(context,CommonWebActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString(CommonWebActivity.EXTRA_WV_URL,item.advertisingBean.getUrl());
                        bundle.putString(CommonWebActivity.EXTRA_WV_TITLE,"广告详情");
                        i.putExtras(bundle);
                        context.startActivity(i);
                    }
                });
                break;
        }

    }


}
