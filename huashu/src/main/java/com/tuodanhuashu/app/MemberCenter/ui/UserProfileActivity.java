package com.tuodanhuashu.app.MemberCenter.ui;


import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.company.common.net.OnRequestListener;
import com.company.common.net.ServerResponse;
import com.company.common.utils.BitmapUtils;
import com.company.common.utils.DisplayUtil;
import com.company.common.utils.JsonUtils;
import com.tuodanhuashu.app.Constants.Constants;
import com.tuodanhuashu.app.MemberCenter.bean.MemberCenterBean;
import com.tuodanhuashu.app.MemberCenter.biz.MemberCenterBiz;
import com.tuodanhuashu.app.R;
import com.tuodanhuashu.app.base.HuaShuBaseActivity;

import java.io.File;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class UserProfileActivity extends HuaShuBaseActivity implements OnRequestListener {


    @BindView(R.id.common_head_back_iv)
    ImageView commonHeadBackIv;
    @BindView(R.id.common_head_title_tv)
    TextView commonHeadTitleTv;
    @BindView(R.id.update_head_rl)
    RelativeLayout updateHeadRl;
    @BindView(R.id.update_name_et)
    EditText updatenameEt;

    private static final int REQUEST_TAKE_PHOTO_CODE = 1;
    private static final int REQUEST_ALBUM_CODE = 2;
    @BindView(R.id.update_head_civ)
    CircleImageView updateHeadCiv;
    @BindView(R.id.update_sex_nv_cb)
    CheckBox updateSexNvCb;
    @BindView(R.id.update_sex_nan_cb)
    CheckBox updateSexNanCb;
    @BindView(R.id.update_sign_et)
    EditText updateSignEt;
    @BindView(R.id.update_sign_rl)
    RelativeLayout updateSignRl;
    @BindView(R.id.update_info_submit_btn)
    Button updateInfoSubmitBtn;

    private File photoFile;

    private MemberCenterBiz memberCenterBiz;

    private static final int TAG_USER_INFO = 3;

    private static final int TAG_EDIT_HEAD = 1;

    private static final int TAG_EDIT_INFO = 2;

    private String url = "";

    private String sex = "1";

    private MemberCenterBean memberCenterBean;

    private Dialog editAvatarDialog;
    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_user_profile;
    }

    @Override
    protected void initView() {
        super.initView();
        commonHeadTitleTv.setText("个人信息");
        commonHeadBackIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        initEditAvatarDialog();
        updateHeadRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               editAvatarDialog.show();
            }
        });
        updateSexNvCb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                updateSexNanCb.setChecked(!isChecked);
                sex = "2";
            }
        });
        updateSexNanCb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                updateSexNvCb.setChecked(!isChecked);
                sex = "1";
            }
        });
        updateInfoSubmitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = updatenameEt.getText().toString();
                String sign = updateSignEt.getText().toString();
                memberCenterBiz.updateUserInfo(TAG_EDIT_INFO,name,"",sex,sign);
            }
        });
    }

    @Override
    protected void initData() {
        super.initData();
        memberCenterBiz = new MemberCenterBiz(this, mContext);
        memberCenterBiz.requestUserInfo(TAG_USER_INFO);
    }

    private void initEditAvatarDialog(){
        editAvatarDialog = new Dialog(mContext, R.style.BottomDialog_Animation);
        View view = getLayoutInflater().inflate(R.layout.dialog_edit_avatar_layout, null);
        editAvatarDialog.setContentView(view);
        editAvatarDialog.setCanceledOnTouchOutside(true);
        Window window = editAvatarDialog.getWindow();
        window.setGravity(Gravity.BOTTOM);
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = DisplayUtil.dip2px(mContext, 137.0f);
        window.setAttributes(params);
        TextView takePhotoTv = view.findViewById(R.id.edit_avatar_take_photo_tv);
        TextView albumTv = view.findViewById(R.id.edit_avatar_album_tv);
        TextView cancelTv = view.findViewById(R.id.edit_avatar_cencel_tv);
        takePhotoTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editAvatarDialog.dismiss();
                takePhoto();
            }
        });
        albumTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editAvatarDialog.dismiss();
                openCamera();
            }
        });
        cancelTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editAvatarDialog.dismiss();
            }
        });
    }

    private void takePhoto() {

        String path = Environment.getExternalStorageDirectory() + File.separator + Constants.FILE_PATH.PHOTO_PATH;
        String fileName = System.currentTimeMillis() + ".jpg";
        photoFile = new File(path, fileName);
        if (!photoFile.getParentFile().exists()) {
            photoFile.getParentFile().mkdirs();
        }
        Uri mUri;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {

            mUri = FileProvider.getUriForFile(mContext, Constants.FILE_PATH.FILE_PROVIDER_AUTHORITIES, photoFile);
        } else {

            mUri = Uri.fromFile(photoFile);
        }

        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, mUri);
        startActivityForResult(intent, REQUEST_TAKE_PHOTO_CODE);
    }

    private void openCamera(){
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, REQUEST_ALBUM_CODE);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_TAKE_PHOTO_CODE) {//获取系统照片上传
            if (photoFile != null && photoFile.exists()) {
                String imgStr = BitmapUtils.bitmapToString(photoFile.getAbsolutePath());
                memberCenterBiz.updateUserImg(1, imgStr);
            }
        }

        if (requestCode == REQUEST_ALBUM_CODE && resultCode == Activity.RESULT_OK) {
            //调用相册
            Cursor cursor = getContentResolver().query(data.getData(), new String[]{MediaStore.Images.Media.DATA}, null, null, null);
            //游标移到第一位，即从第一位开始读取
            if (cursor != null) {
                cursor.moveToFirst();
               String mPhotoPath = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
                cursor.close();
                //调用系统裁剪
                String imgStr = BitmapUtils.bitmapToString(mPhotoPath);
                memberCenterBiz.updateUserImg(1, imgStr);
            }
        }
    }

    @Override
    public void OnSuccess(ServerResponse serverResponse, int tag) {
        switch (tag) {
            case TAG_EDIT_HEAD:
                showToast(serverResponse.getMsg());
                Map data = JsonUtils.getJsonToBean(serverResponse.getData(), Map.class);
                url = (String) data.get("heade_img");
                Glide.with(mContext).load(url).into(updateHeadCiv);
                break;
            case TAG_EDIT_INFO:
                showToast(serverResponse.getMsg());
                onBackPressed();
                break;
            case TAG_USER_INFO:
                this.memberCenterBean = JsonUtils.getJsonToBean(serverResponse.getData(),MemberCenterBean.class);
                updatenameEt.setText(memberCenterBean.getUserinfo().getname());
                Glide.with(mContext).load(memberCenterBean.getUserinfo().getHeade_img()).into(updateHeadCiv);
                updateSexNanCb.setChecked(memberCenterBean.getUserinfo().getSex().equals("1"));
                updateSexNvCb.setChecked(memberCenterBean.getUserinfo().getSex().equals("2"));
                updateSignEt.setText(memberCenterBean.getUserinfo().getP_signature());
                break;
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
    protected void onDestroy() {
        if (editAvatarDialog.isShowing()) {
            editAvatarDialog.dismiss();
        }
        editAvatarDialog = null;
        super.onDestroy();
    }
}
