package com.tuodanhuashu.app.home.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.Glide;
import com.chaychan.library.BottomBarItem;
import com.chaychan.library.BottomBarLayout;
import com.company.common.CommonConstants;
import com.company.common.net.OkNetUtils;
import com.company.common.net.OnDownloadListener;
import com.company.common.net.OnRequestListener;
import com.company.common.net.ServerResponse;
import com.company.common.utils.MD5Utils;
import com.company.common.utils.PackageUtils;
import com.company.common.utils.PreferencesUtils;
import com.company.common.utils.StringUtils;
import com.hyphenate.chat.ChatClient;
import com.hyphenate.helpdesk.callback.Callback;
import com.tuodanhuashu.app.Constants.Constants;
import com.tuodanhuashu.app.MemberCenter.ui.MemberCenterFragment;
import com.tuodanhuashu.app.R;
import com.tuodanhuashu.app.base.HuaShuBaseActivity;
import com.tuodanhuashu.app.eventbus.EventMessage;
import com.tuodanhuashu.app.home.biz.HomeBiz;
import com.tuodanhuashu.app.service.MyService;


import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;

public class HomeActivity extends HuaShuBaseActivity implements OnRequestListener,OnDownloadListener{

    @BindView(R.id.main_header_left_iv)
    ImageView mainHeaderLeftIv;
    @BindView(R.id.main_header_right_iv)
    ImageView mainHeaderRightIv;
    @BindView(R.id.head_huashu_tv)
    TextView headHuashuTv;
    @BindView(R.id.head_zhuanlan_tv)
    TextView headZhuanlanTv;
    @BindView(R.id.main_container_frl)
    FrameLayout mainContainerFrl;
    @BindView(R.id.bbl)
    BottomBarLayout bbl;
    @BindView(R.id.main_head_container)
    RelativeLayout mainHeadContainer;

    private Fragment mCurrentFragment;

    private FragmentManager mFragmentManager;

    private List<Fragment> mfragmentList;

    private HomeFragment homeFragment;

    private HuaShuFragment huaShuFragment;

    private MemberCenterFragment memberCenterFragment;

//    private ZhuanLanFragment zhuanLanFragment;
    private CollegeFragment collegeFragment;

    private HomeBiz homeBiz;
    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_home;
    }


    @Override
    protected void initData() {
        homeBiz = new HomeBiz(this,mContext);
        mfragmentList = new ArrayList<>();
        homeFragment = new HomeFragment();
        huaShuFragment = new HuaShuFragment();
        memberCenterFragment = new MemberCenterFragment();
//        zhuanLanFragment = new ZhuanLanFragment();
        collegeFragment = new CollegeFragment();
        mfragmentList.add(homeFragment);
        mfragmentList.add(huaShuFragment);
        mfragmentList.add(memberCenterFragment);
//        mfragmentList.add(zhuanLanFragment);
        mfragmentList.add(collegeFragment);
        mFragmentManager = getSupportFragmentManager();
        mFragmentManager.beginTransaction().add(R.id.main_container_frl, homeFragment)
                .commitAllowingStateLoss();
        mCurrentFragment = homeFragment;
        if(!StringUtils.isEmpty(PreferencesUtils.getString(mContext,CommonConstants.KEY_ACCOUNT_ID))){
            registerHuanXin();
        }
        String versionCode = PackageUtils.getVersionCode(mContext)+"";
        homeBiz.requestCheckVersion(1,versionCode);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(isWxLogin()){
            String url = PreferencesUtils.getString(mContext,CommonConstants.KEY_IMG_URL,"");

            Glide.with(mContext).load(url).into(mainHeaderLeftIv);
        }else{
            mainHeaderLeftIv.setVisibility(View.GONE);
        }

    }

    @Override
    protected void initView() {
        super.initView();
    }

    @Override
    protected void bindListener() {
        bbl.setOnItemSelectedListener(new BottomBarLayout.OnItemSelectedListener() {
            @Override
            public void onItemSelected(BottomBarItem bottomBarItem, int i, int i1) {
                if (i1 == 1) {
                    if (mCurrentFragment != huaShuFragment && mCurrentFragment != collegeFragment) {
//                    if (mCurrentFragment != huaShuFragment && mCurrentFragment != zhuanLanFragment) {
                        switchContent(mCurrentFragment, huaShuFragment);
                        headHuashuTv.setTextColor(getResources().getColor(R.color.colorAccent));
                    }
                } else {
                    switchContent(mCurrentFragment, mfragmentList.get(i1));
                    headHuashuTv.setTextColor(getResources().getColor(R.color.black));
                    headZhuanlanTv.setTextColor(getResources().getColor(R.color.black));
                }

                if(i1 == 2){
                    mainHeadContainer.setVisibility(View.GONE);
                }else{
                    mainHeadContainer.setVisibility(View.VISIBLE);
                }

            }
        });
        headZhuanlanTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                switchContent(mCurrentFragment, zhuanLanFragment);
                switchContent(mCurrentFragment, collegeFragment);
                bbl.setCurrentItem(1);
                headZhuanlanTv.setTextColor(getResources().getColor(R.color.colorAccent));
                headHuashuTv.setTextColor(getResources().getColor(R.color.black));
            }
        });
        headHuashuTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchContent(mCurrentFragment, huaShuFragment);
                bbl.setCurrentItem(1);
                headHuashuTv.setTextColor(getResources().getColor(R.color.colorAccent));
                headZhuanlanTv.setTextColor(getResources().getColor(R.color.black));
            }
        });
        mainHeaderRightIv.setOnClickListener(new View.OnClickListener() {
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

    @Override
    protected void getBundleExtras(Bundle extras) {

    }

    @Override
    protected boolean isEnableDoubleClickExit() {
        return true;
    }


    private void switchContent(Fragment from, Fragment to) {
        if (mCurrentFragment != to) {
            mCurrentFragment = to;
            FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
            if (!to.isAdded()) {    // 先判断是否被add过

                fragmentTransaction.hide(from).add(R.id.main_container_frl, to).commitAllowingStateLoss(); // 隐藏当前的fragment，add下一个到Activity中
            } else {
                fragmentTransaction.hide(from).show(to).commitAllowingStateLoss(); // 隐藏当前的fragment，显示下一个
            }
        }
    }

    private void registerHuanXin(){
        String accountId = PreferencesUtils.getString(mContext, CommonConstants.KEY_ACCOUNT_ID,"");
        String md5AccoundId = MD5Utils.MD5Encode(accountId,"utf-8");
        ChatClient.getInstance().register(accountId, md5AccoundId, new Callback() {
            @Override
            public void onSuccess() {
                Log.e("huanxin","环信注册成功！！");
            }

            @Override
            public void onError(int i, String s) {
                Log.e("huanxin",s);
            }

            @Override
            public void onProgress(int i, String s) {
                Log.e("huanxin",s);
            }
        });
    }


    @Override
    public void onEvent(EventMessage message) {
        super.onEvent(message);
        switch (message.getTag()){
            case Constants.EVENT_TAG.TAG_WX_PAY_SUCCESS:
                showToast("支付成功");
                break;
            case Constants.EVENT_TAG.TAG_WX_PAY_FAIL:
                showToast("支付失败");
                break;
        }
    }

    @Override
    public void OnSuccess(ServerResponse serverResponse, int tag) {
        final JSONObject jsonObject = JSON.parseObject(serverResponse.getData());
        int isVersion = jsonObject.getInteger("is_version");
        if(isVersion==0){
            return;
        }else{

            AlertDialog dialog = new AlertDialog.Builder(this).create();
            dialog.setIcon(R.mipmap.huashu_icon);//设置图标
            dialog.setTitle("检测到更新");//设置标题
            dialog.setMessage(jsonObject.getString("describe"));//添加布局

            dialog.setButton(AlertDialog.BUTTON_POSITIVE, "下载", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    String destDir = Environment.getExternalStorageDirectory() + File.separator + Constants.FILE_PATH.APK_PATH;
                    String fileName = "tuodanhuashu_"+jsonObject.getString("verison_no")+".apk";
                    File destFile = new File(destDir,fileName);
                    if (!destFile.getParentFile().exists()) {
                        destFile.getParentFile().mkdirs();
                    }
                    Intent intent = new Intent(mContext, MyService.class);
                    intent.putExtra(MyService.EXTRA_DOWN_URL,jsonObject.getString("url"));
                    intent.putExtra(MyService.EXTRA_FILE_DIR,destDir);
                    intent.putExtra(MyService.EXTRA_FILE_NAME,fileName);
                    startService(intent);
                }
            });
            dialog.setButton(AlertDialog.BUTTON_NEGATIVE, "以后再说", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                }
            });
            dialog.show();


            //OkNetUtils.downLoadFile(jsonObject.getString("url"),destFile.getParent(),destFile.getName(),this);
        }
    }

    @Override
    public void onFail(String msg, int code, int tag) {
        showToast(msg);
    }

    @Override
    public void onError(int tag) {

    }

    @Override
    public void onTaskStart() {

    }

    @Override
    public void onProgeress(float frac) {
            Log.e("download progress",frac+"");
    }

    @Override
    public void onSuccess() {

    }

    @Override
    public void onError() {

    }
}
