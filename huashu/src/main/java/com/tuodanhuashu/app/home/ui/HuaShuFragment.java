package com.tuodanhuashu.app.home.ui;


import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.alibaba.android.vlayout.layout.LinearLayoutHelper;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseViewHolder;
import com.company.common.utils.DrawableGetUtil;
import com.company.common.utils.StringUtils;
import com.tuodanhuashu.app.R;
import com.tuodanhuashu.app.base.HuaShuBaseFragment;
import com.tuodanhuashu.app.home.adapter.HomeAdapter;
import com.tuodanhuashu.app.home.adapter.HuaShuSubMenuAdapter;
import com.tuodanhuashu.app.home.bean.HomeAnswerBean;
import com.tuodanhuashu.app.home.bean.HomeTalkSkillItemBean;
import com.tuodanhuashu.app.home.bean.HomeTalkSkillPageBean;
import com.tuodanhuashu.app.home.presenter.HomeTalkSkillPresenter;
import com.tuodanhuashu.app.home.view.HomeTalkSkillView;
import com.tuodanhuashu.app.huashu.ui.HuaShuaListActivity;
import com.tuodanhuashu.app.web.CommonWebActivity;
import com.tuodanhuashu.app.widget.ClipDialog;
import com.tuodanhuashu.app.widget.HuaShuManuHelper;
import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class HuaShuFragment extends HuaShuBaseFragment implements HomeTalkSkillView {

    @BindView(R.id.huashu_refresheader)
    MaterialHeader huashuRefresheader;
    @BindView(R.id.huashu_rlv)
    RecyclerView huashuRlv;
    @BindView(R.id.huashu_refreshLayout)
    SmartRefreshLayout huashuRefreshLayout;

    private DelegateAdapter delegateAdapter;

    private List<DelegateAdapter.Adapter> adapterList;

    private HomeTalkSkillPresenter talkSkillPresenter;

    private HomeTalkSkillPageBean homeTalkSkillPageBean;

    private static final int TYPE_HEAD = 1;

    private static final int TYPE_GRID = 2;

    private static final int TYPE_AD = 3;

    private static final int TYPE_QUESTION = 4;

    private boolean isFirstInit = true;

    public HuaShuFragment() {
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_hua_shu;
    }

    @Override
    protected void initView(View view) {
        super.initView(view);

        adapterList = new ArrayList<>();
        talkSkillPresenter = new HomeTalkSkillPresenter(this,mContext);
        huashuRefresheader.setColorSchemeColors(mContext.getResources().getColor(R.color.colorAccent));
        VirtualLayoutManager layoutManager = new VirtualLayoutManager(mContext);
        huashuRlv.setLayoutManager(layoutManager);
        RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
        huashuRlv.setRecycledViewPool(viewPool);
        viewPool.setMaxRecycledViews(0, 10);
        delegateAdapter = new DelegateAdapter(layoutManager,true);
        huashuRlv.setAdapter(delegateAdapter);
        huashuRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                talkSkillPresenter.requestTalkSkillPage();
            }
        });
        talkSkillPresenter.requestTalkSkillPage();
        showLoadingView();
    }


    private void initHeader(){
        HomeAdapter huashuHeadAdapter = new HomeAdapter(mContext,new LinearLayoutHelper(),1,TYPE_HEAD,R.layout.home_huashu_head_layout){
            @Override
            public void onBindViewHolder(BaseViewHolder holder, int position) {
                super.onBindViewHolder(holder, position);
//                FrameLayout goSearchFr = holder.getView(R.id.huashu_go_search_fr);
//                goSearchFr.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        Bundle bundle = new Bundle();
//                        bundle.putBoolean(HuaShuaListActivity.EXTRA_IS_VIP,true);
//                        readyGo(HuaShuaListActivity.class,bundle);
//                    }
//                });
                final EditText searchEt = holder.getView(R.id.home_talkskill_search_et);
                Button searchBtn = holder.getView(R.id.home_talkskill_search_btn);
                searchBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String keywords = searchEt.getText().toString();
                        if (StringUtils.isEmpty(keywords)) {
                            showToast("请输入关键字");
                            return;
                        }
                        Bundle bundle = new Bundle();
                        bundle.putString(HuaShuaListActivity.EXTRA_KEY_WORDS, keywords);
                        readyGo(HuaShuaListActivity.class, bundle);
                    }
                });
                searchEt.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                    @Override
                    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                            String keywords = searchEt.getText().toString();
                            if (StringUtils.isEmpty(keywords)) {
                                showToast("请输入关键字");

                            }
                            Bundle bundle = new Bundle();
                            bundle.putString(HuaShuaListActivity.EXTRA_KEY_WORDS, keywords);
                            readyGo(HuaShuaListActivity.class, bundle);

                            return true;
                        }
                        return false;
                    }
                });

            }
        };

        adapterList.add(huashuHeadAdapter);
    }



    private void initGrid(){
        HomeAdapter gridAdapter = new HomeAdapter(mContext,new LinearLayoutHelper(),1,TYPE_GRID,R.layout.huashu_grid_layout){
            @Override
            public void onBindViewHolder(BaseViewHolder holder, int position) {
                super.onBindViewHolder(holder, position);
                GridView gv = holder.getView(R.id.gv);
                List<ImageView> ivList = new ArrayList<>();
                ivList.add((ImageView) holder.getView(R.id.huashu_menu_1_iv));
                Glide.with(mContext).load(homeTalkSkillPageBean.getTalkSkillClasses().get(0).getCat_icon()).into(ivList.get(0));

                ivList.add((ImageView) holder.getView(R.id.huashu_menu_2_iv));
                Glide.with(mContext).load(homeTalkSkillPageBean.getTalkSkillClasses().get(1).getCat_icon()).into(ivList.get(1));

                ivList.add((ImageView) holder.getView(R.id.huashu_menu_3_iv));
                Glide.with(mContext).load(homeTalkSkillPageBean.getTalkSkillClasses().get(2).getCat_icon()).into(ivList.get(2));

                ivList.add((ImageView) holder.getView(R.id.huashu_menu_4_iv));
                Glide.with(mContext).load(homeTalkSkillPageBean.getTalkSkillClasses().get(3).getCat_icon()).into(ivList.get(3));

                ivList.add((ImageView) holder.getView(R.id.huashu_menu_5_iv));
                Glide.with(mContext).load(homeTalkSkillPageBean.getTalkSkillClasses().get(4).getCat_icon()).into(ivList.get(4));

                ivList.add((ImageView) holder.getView(R.id.huashu_menu_6_iv));
                Glide.with(mContext).load(homeTalkSkillPageBean.getTalkSkillClasses().get(5).getCat_icon()).into(ivList.get(5));

                ivList.add((ImageView) holder.getView(R.id.huashu_menu_7_iv));
                Glide.with(mContext).load(homeTalkSkillPageBean.getTalkSkillClasses().get(6).getCat_icon()).into(ivList.get(6));

                ivList.add((ImageView) holder.getView(R.id.huashu_menu_8_iv));
                Glide.with(mContext).load(homeTalkSkillPageBean.getTalkSkillClasses().get(7).getCat_icon()).into(ivList.get(7));

                List<FrameLayout> manuList = new ArrayList<>();
                manuList.add((FrameLayout) holder.getView(R.id.huashu_menu_1));
                manuList.add((FrameLayout) holder.getView(R.id.huashu_menu_2));
                manuList.add((FrameLayout) holder.getView(R.id.huashu_menu_3));
                manuList.add((FrameLayout) holder.getView(R.id.huashu_menu_4));
                manuList.add((FrameLayout) holder.getView(R.id.huashu_menu_5));
                manuList.add((FrameLayout) holder.getView(R.id.huashu_menu_6));
                manuList.add((FrameLayout) holder.getView(R.id.huashu_menu_7));
                manuList.add((FrameLayout) holder.getView(R.id.huashu_menu_8));
                List<FrameLayout> coverList = new ArrayList<>();
                coverList.add((FrameLayout) holder.getView(R.id.huashu_menu_1_selected_fr));
                coverList.add((FrameLayout) holder.getView(R.id.huashu_menu_2_selected_fr));
                coverList.add((FrameLayout) holder.getView(R.id.huashu_menu_3_selected_fr));
                coverList.add((FrameLayout) holder.getView(R.id.huashu_menu_4_selected_fr));
                coverList.add((FrameLayout) holder.getView(R.id.huashu_menu_5_selected_fr));
                coverList.add((FrameLayout) holder.getView(R.id.huashu_menu_6_selected_fr));
                coverList.add((FrameLayout) holder.getView(R.id.huashu_menu_7_selected_fr));
                coverList.add((FrameLayout) holder.getView(R.id.huashu_menu_8_selected_fr));
                List<TextView> tvList = new ArrayList<>();

                tvList.add((TextView) holder.getView(R.id.huashu_menu_1_tv));
                tvList.add((TextView) holder.getView(R.id.huashu_menu_2_tv));
                tvList.add((TextView) holder.getView(R.id.huashu_menu_3_tv));
                tvList.add((TextView) holder.getView(R.id.huashu_menu_4_tv));
                tvList.add((TextView) holder.getView(R.id.huashu_menu_5_tv));
                tvList.add((TextView) holder.getView(R.id.huashu_menu_6_tv));
                tvList.add((TextView) holder.getView(R.id.huashu_menu_7_tv));
                tvList.add((TextView) holder.getView(R.id.huashu_menu_8_tv));
                for(int i=0;i<tvList.size();i++){
                    tvList.get(i).setText(homeTalkSkillPageBean.getTalkSkillClasses().get(i).getCat_name());
                }
                List<FrameLayout> divList = new ArrayList<>();
                divList.add((FrameLayout) holder.getView(R.id.huashu_menu_1_div));
                divList.add((FrameLayout) holder.getView(R.id.huashu_menu_2_div));
                divList.add((FrameLayout) holder.getView(R.id.huashu_menu_3_div));
                divList.add((FrameLayout) holder.getView(R.id.huashu_menu_4_div));
                HuaShuSubMenuAdapter adapter = new HuaShuSubMenuAdapter(mContext,homeTalkSkillPageBean.getTalkSkillClasses());


                HuaShuManuHelper manuHelper = new HuaShuManuHelper(manuList,coverList,divList,tvList,ivList,gv,adapter,mContext,homeTalkSkillPageBean);
                gv.setAdapter(adapter);

            }
        };

        adapterList.add(gridAdapter);
    }


//    private List<String> initGridData() {
//        List<String> list = new ArrayList<>();
//        for (int i = 0; i < 6; i++) {
//
//
//            list.add("数据" + i);
//        }
//        return list;
//    }



    private void initAdvertisment(){
        HomeAdapter adAdpter = new HomeAdapter(mContext,new LinearLayoutHelper(),1,TYPE_AD,R.layout.huashu_ad_layout){
            @Override
            public void onBindViewHolder(BaseViewHolder holder, int position) {
                super.onBindViewHolder(holder, position);
                ImageView adIv = holder.getView(R.id.huashu_ad_iv);
                Glide.with(mContext).load(homeTalkSkillPageBean.getAdvertising().getImage_url()).into(adIv);
                adIv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Bundle bundle = new Bundle();
                        bundle.putString(CommonWebActivity.EXTRA_WV_TITLE,"广告详情");
                        bundle.putString(CommonWebActivity.EXTRA_WV_URL,homeTalkSkillPageBean.getAdvertising().getUrl());
                        readyGo(CommonWebActivity.class,bundle);
                    }
                });
            }
        };

        adapterList.add(adAdpter);
    }


    private void initHotQuestion(){
        HomeAdapter questionAdpter = new HomeAdapter(mContext,new LinearLayoutHelper(),homeTalkSkillPageBean.getChoicenessTalkSkills().size(),TYPE_QUESTION,R.layout.huashu_hot_question_layout){
            @Override
            public void onBindViewHolder(BaseViewHolder holder, int position) {
                HomeTalkSkillItemBean talkSkillItemBean = homeTalkSkillPageBean.getChoicenessTalkSkills().get(position);
                super.onBindViewHolder(holder, position);
                LinearLayout ll = holder.getView(R.id.home_huashu_question_container_ll);
                FrameLayout fr= holder.getView(R.id.home_huashu_question_divide_fr);
                TextView titleTv = holder.getView(R.id.home_huashu_question_title_tv);
                View bottomView = holder.getView(R.id.home_huashu_question_bottom_view);
                LinearLayout answerLl = holder.getView(R.id.home_huashu_answer_ll);
                ImageView questionIv = holder.getView(R.id.home_huashu_question_iv);
                questionIv.setImageResource(talkSkillItemBean.getQuestion().equals("1")?R.drawable.nanxing:R.drawable.question_nv);
                GradientDrawable gd = null;
                if(position==0){
                    titleTv.setVisibility(View.VISIBLE);
                    gd = DrawableGetUtil.getNeedDrawable(new float[]{12.0F,12.0F,12.0F,12.0F,0.0F,0.0F,0.0F,0.0F}, Color.argb(255 , 255 , 255 ,255));
                    DrawableGetUtil.setBackground(gd,ll);
                }

                if(position==homeTalkSkillPageBean.getChoicenessTalkSkills().size()-1) {
                    gd = DrawableGetUtil.getNeedDrawable(new float[]{0.0F, 0.0F, 0.0F, 0.0F, 12.0F, 12.0F, 12.0F, 12.0F}, Color.argb(255, 255, 255, 255));
                    DrawableGetUtil.setBackground(gd, ll);
                    fr.setBackground(getResources().getDrawable(R.color.white));
                    bottomView.setVisibility(View.VISIBLE);
                }

                TextView questionTv = holder.getView(R.id.home_huashu_question_tv);
                questionTv.setText(talkSkillItemBean.getQuestion().getContent());
                //HomeAnswerAdapter answerAdapter = new HomeAnswerAdapter(talkSkillItemBean.getAnswers(),mContext);
                answerLl.removeAllViews();
                for(final HomeAnswerBean answerBean:talkSkillItemBean.getAnswers()){
                    View v = LayoutInflater.from(mContext).inflate(R.layout.item_home_answer_layout,null);
                    TextView tv = v.findViewById(R.id.item_home_answer_tv);
                    ImageView answerIv = v.findViewById(R.id.item_home_answer_sex_iv);
                    ImageView clipIv = v.findViewById(R.id.home_talkskill_clip_iv);
                    clipIv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ClipboardManager cm = (ClipboardManager)mContext.getSystemService(Context.CLIPBOARD_SERVICE);
                            ClipData mClipData = ClipData.newPlainText("Label", answerBean.getContent());
                            cm.setPrimaryClip(mClipData);
                            ClipDialog clipDialog = new ClipDialog(mContext);
                            clipDialog.show();
                        }
                    });
                    answerIv.setImageResource(answerBean.getSex().equals("1")?R.drawable.nanxing:R.drawable.question_nv);
                    tv.setText(answerBean.getContent());
                    answerLl.addView(v);
                }
            }
        };

        adapterList.add(questionAdpter);
    }


    @Override
    public void getDataSuccess(HomeTalkSkillPageBean homeTalkSkillPageBean) {
        this.homeTalkSkillPageBean = homeTalkSkillPageBean;
        if(homeTalkSkillPageBean!=null) {
            if(isFirstInit){
                initHeader();
                initGrid();
                initAdvertisment();
                initHotQuestion();
                delegateAdapter.setAdapters(adapterList);
                showOriginView();
                isFirstInit = false;
            }else{
                huashuRefreshLayout.finishRefresh(300);
                delegateAdapter.notifyItemChanged(1);
                delegateAdapter.notifyItemChanged(2);
                delegateAdapter.notifyItemChanged(3);
            }

        }
    }

    @Override
    public void getDataFail(String msg) {
        showToast(msg);
    }

    @Override
    protected int getRootLayoutId() {
        return R.id.huashu_refreshLayout;
    }
}
