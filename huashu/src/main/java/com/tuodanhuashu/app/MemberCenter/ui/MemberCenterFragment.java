package com.tuodanhuashu.app.MemberCenter.ui;


import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.company.common.CommonConstants;
import com.company.common.utils.PreferencesUtils;
import com.company.common.utils.StringUtils;
import com.tuodanhuashu.app.Constants.Constants;
import com.tuodanhuashu.app.MemberCenter.bean.MemberCenterBean;
import com.tuodanhuashu.app.MemberCenter.presenter.MemberCenterPresenter;
import com.tuodanhuashu.app.MemberCenter.view.MemberCenterView;
import com.tuodanhuashu.app.R;
import com.tuodanhuashu.app.base.HuaShuBaseFragment;
import com.tuodanhuashu.app.huashu.ui.MyHuaShuListActivity;
import com.tuodanhuashu.app.pay.ui.VipPayActivity;
import com.tuodanhuashu.app.user.ui.LoginActivity;
import com.tuodanhuashu.app.web.CommonWebActivity;
import com.tuodanhuashu.app.zhuanlan.ui.ZhuanLanListActivity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 */
public class MemberCenterFragment extends HuaShuBaseFragment implements MemberCenterView {


    @BindView(R.id.user_avatar_iv)
    CircleImageView userAvatarIv;
    @BindView(R.id.user_name_tv)
    TextView userNameTv;
    @BindView(R.id.member_setting_iv)
    ImageView memberSettingIv;
    @BindView(R.id.member_center_vip_rl)
    RelativeLayout memberCenterVipRl;
    @BindView(R.id.my_talkskill_fr)
    FrameLayout myTalkskillFr;
    @BindView(R.id.my_article_fr)
    FrameLayout myArticleFr;
    @BindView(R.id.my_course_fr)
    FrameLayout myCourseFr;
    @BindView(R.id.member_center_login_btn)
    Button memberCenterLoginBtn;

    @BindView(R.id.member_center_ad_iv)
    ImageView memberCenterAdIv;
    @BindView(R.id.advice_rl)
    RelativeLayout adviceRl;
    @BindView(R.id.about_us_rl)
    RelativeLayout aboutUsRl;
    @BindView(R.id.join_us_rl)
    RelativeLayout joinUsRl;
    @BindView(R.id.member_setting_fr)
    FrameLayout memberSettingFr;
    @BindView(R.id.member_center_root_ll)
    LinearLayout memberCenterRootLl;
    @BindView(R.id.member_love_count_tv)
    TextView memberCenterLoveCountTv;
    @BindView(R.id.user_level_tv)
    TextView userLevelTv;
    @BindView(R.id.layout_user_love_count)
    LinearLayout layoutUesrLoveCount;


    private MemberCenterBean memberCenterBean;

    private MemberCenterPresenter memberCenterPresenter;

    private static final int REQUEST_TAKE_PHOTO_CODE = 1;
    private Uri mUri;

    private String accessToken;
    public MemberCenterFragment() {
        // Required empty public constructor
    }

    @Override
    protected int getRootLayoutId() {
        return R.id.member_center_root_ll;
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_member_center;
    }

    @Override
    protected void initData() {
        super.initData();
        memberCenterPresenter = new MemberCenterPresenter(mContext, this);
    }

    @Override
    public void onResume() {
        super.onResume();
        memberCenterPresenter.requestUserInfo();
    }

    @Override
    protected void initView(View view) {
        super.initView(view);
    }

    @Override
    protected void bindListener() {
        super.bindListener();
        userAvatarIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //readyGo(LoginActivity.class);
            }
        });

        memberSettingFr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isLogin()) {
                    readyGo(MemberSettingActivity.class);
                } else {
                    goToLogin();
                }
            }
        });
        memberCenterVipRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isLogin()) {
                    readyGo(VipPayActivity.class);
                } else {
                    goToLogin();
                }

            }
        });
        myArticleFr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isLogin()) {
                    Bundle bundle = new Bundle();
                    bundle.putInt(ZhuanLanListActivity.EXTRA_ENTER_TYPE, 4);
                    readyGo(ZhuanLanListActivity.class, bundle);
                } else {
                    goToLogin();
                }

            }
        });

        myCourseFr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isLogin()) {
                readyGo(MyCourseActivity.class);
                } else {
                    goToLogin();
                }
            }
        });
        myTalkskillFr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isLogin()) {
                    readyGo(MyHuaShuListActivity.class);
                } else {
                    goToLogin();
                }

            }
        });
        aboutUsRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                memberCenterPresenter.requestAboutUsUrl();
            }
        });
        memberCenterLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readyGo(LoginActivity.class);
            }
        });
        joinUsRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                readyGo(JoinUsActivity.class);


            }
        });
        adviceRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isLogin()) {
                    readyGo(FeedBackActivity.class);
                } else {
                    goToLogin();
                }
            }
        });
        memberCenterAdIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString(CommonWebActivity.EXTRA_WV_TITLE, "广告详情");
                bundle.putString(CommonWebActivity.EXTRA_WV_URL, memberCenterBean.getAdvertising().getUrl());
                readyGo(CommonWebActivity.class, bundle);
            }
        });
    }


    @Override
    public void getUserInfoSucess(MemberCenterBean memberCenterBean) {
        this.memberCenterBean = memberCenterBean;
        Glide.with(mContext).load(memberCenterBean.getAdvertising().getImage_url()).into(memberCenterAdIv);
        if (StringUtils.isEmpty(PreferencesUtils.getString(mContext, CommonConstants.KEY_TOKEN, ""))) {
            getInfoWithNoLogin();

        } else {
            getInfoWithLogin();
        }
    }

    private void takePhoto() {

        String path = Environment.getExternalStorageDirectory() + File.separator + Constants.FILE_PATH.PHOTO_PATH;
        String fileName = System.currentTimeMillis() + ".jpg";
        File file = new File(path, fileName);
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {

            mUri = FileProvider.getUriForFile(mContext, Constants.FILE_PATH.FILE_PROVIDER_AUTHORITIES, file);
        } else {

            mUri = Uri.fromFile(file);
        }

        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, mUri);
        startActivityForResult(intent, REQUEST_TAKE_PHOTO_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        InputStream input = null;
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_TAKE_PHOTO_CODE) {//获取系统照片上传
            try {

                input = mContext.getContentResolver().openInputStream(mUri);

            }
//            catch (FileNotFoundException e) {
//                e.printStackTrace();
//            }
            catch (IOException e) {
                e.printStackTrace();
            }
//            }catch (Exception e){
//                e.printStackTrace();
//            }
            userAvatarIv.setImageBitmap(BitmapFactory.decodeStream(input));
        }
    }

    @Override
    public void getInfoWithLogin() {
        userLevelTv.setVisibility(View.VISIBLE);
        memberCenterLoginBtn.setVisibility(View.GONE);
        userLevelTv.setText(memberCenterBean.getUserinfo().getLevel());
        if (StringUtils.isEmpty(memberCenterBean.getUserinfo().getname())) {
            userNameTv.setText(memberCenterBean.getUserinfo().getMobile());
        } else {
            userNameTv.setText(memberCenterBean.getUserinfo().getname());
        }

        Glide.with(mContext).load(memberCenterBean.getUserinfo().getHeade_img()).into(userAvatarIv);
        memberCenterLoveCountTv.setText(memberCenterBean.getUserinfo().getLove_count());
    }

    @Override
    public void getInfoWithNoLogin() {
        userNameTv.setVisibility(View.GONE);
        userLevelTv.setVisibility(View.GONE);
        memberCenterLoginBtn.setVisibility(View.VISIBLE);
        layoutUesrLoveCount.setVisibility(View.GONE);
    }

    @Override
    public void getUserInfoFail(String msg) {

    }

    @Override
    public void getAboutUsUrlSuccess(String url) {
        Bundle bundle = new Bundle();
        bundle.putString(CommonWebActivity.EXTRA_WV_TITLE, "关于我们");
        bundle.putString(CommonWebActivity.EXTRA_WV_DATA, url);
        readyGo(CommonWebActivity.class, bundle);
    }


}
