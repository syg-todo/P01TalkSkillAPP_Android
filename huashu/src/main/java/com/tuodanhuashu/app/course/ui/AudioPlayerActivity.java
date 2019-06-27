package com.tuodanhuashu.app.course.ui;

import android.arch.lifecycle.ViewModelProviders;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.os.IBinder;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.alivc.player.VcPlayerLog;
import com.aliyun.vodplayer.downloader.AliyunDownloadConfig;
import com.aliyun.vodplayer.downloader.AliyunDownloadInfoListener;
import com.aliyun.vodplayer.downloader.AliyunDownloadManager;
import com.aliyun.vodplayer.downloader.AliyunDownloadMediaInfo;
import com.aliyun.vodplayer.downloader.AliyunRefreshStsCallback;
import com.aliyun.vodplayer.downloader.AliyunRefreshVidSourceCallback;
import com.aliyun.vodplayer.media.AliyunPlayAuth;
import com.aliyun.vodplayer.media.AliyunVidSource;
import com.aliyun.vodplayer.media.AliyunVidSts;
import com.bumptech.glide.Glide;
import com.company.common.CommonConstants;
import com.company.common.utils.PreferencesUtils;
import com.company.common.utils.StatusBarUtil;
import com.tuodanhuashu.app.Constants.Constants;
import com.tuodanhuashu.app.R;
import com.tuodanhuashu.app.base.HuaShuBaseActivity;
import com.tuodanhuashu.app.course.AudioPlayService;
import com.tuodanhuashu.app.course.bean.SectionBean;
import com.tuodanhuashu.app.course.bean.SectionInfoModel;
import com.tuodanhuashu.app.course.presenter.AudioPlayPresenter;
import com.tuodanhuashu.app.course.ui.adapter.CommentAdapter;
import com.tuodanhuashu.app.course.ui.adapter.SectionInfoAdapter;
import com.tuodanhuashu.app.course.ui.fragment.AudioPlayerContentFragment;
import com.tuodanhuashu.app.course.view.AudioPlayView;
import com.tuodanhuashu.app.download.VidStsUtil;
import com.tuodanhuashu.app.eventbus.EventMessage;
import com.tuodanhuashu.app.utils.NetWatchdog;
import com.tuodanhuashu.app.utils.PriceFormater;
import com.tuodanhuashu.app.utils.TimeFormater;
import com.tuodanhuashu.app.widget.player.VideoPlayerView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.File;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

public class AudioPlayerActivity extends HuaShuBaseActivity implements AudioPlayView, View.OnClickListener, DialogFragmentDataCallback {


    private static final String TAG = AudioPlayerActivity.class.getSimpleName();
    private static final int TYPE_TOP = 0;
    private static final int TYPE_COMMENT = 1;
    private static final int TYPE_TAB = 2;
    //    @BindView(R.id.rv_play)
//    RecyclerView recyclerView;
    @BindView(R.id.iv_play_image)
    ImageView ivAudioPlayImage;

    @BindView(R.id.iv_audio_play_back)
    ImageView ivAudioPlayBack;
    @BindView(R.id.iv_audio_play_download)
    ImageView ivAudioPlayDownload;

    @BindView(R.id.fl_audio_play_controller)
    FrameLayout flAudioPlayController;
    @BindView(R.id.tv_audio_play_current)
    TextView tvAudioPlayCurrent;
    @BindView(R.id.tv_audio_play_duration)
    TextView tvAudioPlayDuration;
    @BindView(R.id.iv_audio_play_play)
    ImageView ivAudioPlayPlay;

    @BindView(R.id.seekbar_audio_play)
    SeekBar seekBar;
    @BindView(R.id.fl_audio_play_container)
    FrameLayout flAudioPlayContainer;

    VideoPlayerView playerView;
    SectionInfoModel model;
    AudioPlayerContentFragment fragment;

    private String bannerUrl;
    private String sectionName;
    private String sectionDuration;
    private String currentSectionId;

    private String isPay;
    private String finalPrice;
    public boolean isPlaying = false;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;


    private String audioUrl;
    private String audioDutaion;

    private NetWatchdog mNetWatchdog;
    private AudioPlayService.AudioBinder audioController;

    //下载相关
    private AliyunDownloadMediaInfo downloadTag;
    private AliyunDownloadManager downloadManager;
    private AliyunDownloadConfig config;

    private List<SectionBean.SectionInfo> sectionInfoList = new ArrayList<>();
    private AudioPlayPresenter audioPlayPresenter;

    private CommentAdapter adapter;
    public static final String EXTRA_SECTION_ID = "section_id";

    public static final String EXTRA_COURSE_ID = "course_id";

    public static final String EXTRA_IS_PLAYING = "is_playing";
    private String section_id = "";
    private String accessToken = "";

    private String course_id = "";
    private final int UPDATE_PROGRESS = 1;
    AliyunDownloadMediaInfo info;
    private AudioPlayService.AudioBinder mBinder;
    private ServiceConnection coon = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mBinder = (AudioPlayService.AudioBinder) service;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_audio_play;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Log.d(TAG, "onDestory");
    }

    @Override
    protected void initData() {
        super.initData();
        accessToken = PreferencesUtils.getString(mContext, CommonConstants.KEY_TOKEN, "0");
        model = ViewModelProviders.of(this).get(SectionInfoModel.class);
        audioPlayPresenter = new AudioPlayPresenter(mContext, this);
        currentSectionId = PreferencesUtils.getString(mContext, CommonConstants.KEY_CURRENT_SECTION, "");
//        if (!section_id.equals(currentSectionId)){
        audioPlayPresenter.requestAudioSectionInfo(accessToken, section_id);
        PreferencesUtils.putString(mContext, CommonConstants.KEY_CURRENT_SECTION, section_id);
//        }else {
//
//        }
    }

    @Override
    protected void initView() {
        super.initView();
        mNetWatchdog = new NetWatchdog(mContext);
        mNetWatchdog.startWatch();
        if (NetWatchdog.is4GConnected(mContext)){
            AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
            builder.setTitle("4g")
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .setPositiveButton("继续", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            startService();
                        }
                    });
            builder.create().show();
            Toast.makeText(mContext,"4g",Toast.LENGTH_SHORT).show();
        }else {
            startService();
        }
        fragmentManager = getSupportFragmentManager();
        downloadManager = AliyunDownloadManager.getInstance(this);

        downloadTag = new AliyunDownloadMediaInfo();

        config = new AliyunDownloadConfig();
//        config.setSecretImagePath(
//                Environment.getExternalStorageDirectory().getAbsolutePath() + "/aliyun/encryptedApp.dat");
        //        config.setDownloadPassword("123456789");
        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/test_save/");
        if (!file.exists()) {
            file.mkdir();
        }
        config.setDownloadDir(file.getAbsolutePath());

        //设置同时下载个数
        config.setMaxNums(2);

        AliyunDownloadManager.getInstance(this).setDownloadConfig(config);
        downloadManager.setRefreshStsCallback(new MyRefreshStsCallback());


        downloadManager.setDownloadInfoListener(new AliyunDownloadInfoListener() {
            @Override
            public void onPrepared(List<AliyunDownloadMediaInfo> list) {
                Log.d(TAG,"onPrepared");
            }

            @Override
            public void onStart(AliyunDownloadMediaInfo aliyunDownloadMediaInfo) {
                Log.d(TAG,"onStart");
            }

            @Override
            public void onProgress(AliyunDownloadMediaInfo aliyunDownloadMediaInfo, int i) {
                Log.d(TAG,"onProgress");
            }

            @Override
            public void onStop(AliyunDownloadMediaInfo aliyunDownloadMediaInfo) {
                Log.d(TAG,"onStop");
            }

            @Override
            public void onCompletion(AliyunDownloadMediaInfo aliyunDownloadMediaInfo) {
                Log.d(TAG,"onCompletion");
            }

            @Override
            public void onError(AliyunDownloadMediaInfo aliyunDownloadMediaInfo, int i, String s, String s1) {
                Log.d(TAG,"onError"+s+"----"+s1+"----"+aliyunDownloadMediaInfo.getTitle());
            }

            @Override
            public void onWait(AliyunDownloadMediaInfo aliyunDownloadMediaInfo) {
                Log.d(TAG,"onWait");
            }

            @Override
            public void onM3u8IndexUpdate(AliyunDownloadMediaInfo aliyunDownloadMediaInfo, int i) {
                Log.d(TAG,"onM3u8IndexUpdate");
            }
        });

        Log.d(TAG,AliyunDownloadManager.getInstance(this).getSaveDir());

//        prepareDownload();


        ivAudioPlayDownload.getDrawable().setTint(getResources().getColor(R.color.white));

        ivAudioPlayBack.getDrawable().setTint(getResources().getColor(R.color.white));
        ivAudioPlayDownload.setOnClickListener(this);
        ivAudioPlayBack.setOnClickListener(this);
        flAudioPlayController.setOnClickListener(this);

        Glide.with(mContext).load(isPlaying ? R.drawable.vector_pause : R.drawable.vector_start).into(ivAudioPlayPlay);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    //这里是用户拖动，直接设置文字进度就行，
                    // 无需去updateAllViews() ， 因为不影响其他的界面。

                    tvAudioPlayCurrent.setText(TimeFormater.formatMs(progress));
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mBinder.seekTo(seekBar.getProgress());
            }
        });


    }
    private static class MyRefreshStsCallback implements AliyunRefreshStsCallback {

        @Override
        public AliyunVidSts refreshSts(String vid, String quality, String format, String title, boolean encript) {
            //NOTE: 注意：这个不能启动线程去请求。因为这个方法已经在线程中调用了。
            AliyunVidSts vidSts = VidStsUtil.getVidSts(vid);
            if (vidSts == null) {
                return null;
            } else {
                Log.d(TAG,vid+"---"+quality+"---"+format+"---"+title);
                vidSts.setVid(vid);
                vidSts.setQuality(quality);
                vidSts.setTitle(title);
                return vidSts;
            }
        }
    }


    private void setConfig() {
        config = new AliyunDownloadConfig();
//        config.setSecretImagePath(
//                Environment.getExternalStorageDirectory().getAbsolutePath() + "/aliyun/encryptedApp.dat");
        //        config.setDownloadPassword("123456789");
        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/test_save/");
        if (!file.exists()) {
            file.mkdir();
        }
        config.setDownloadDir(file.getAbsolutePath());
        //设置同时下载个数
        config.setMaxNums(2);
    }


    private void play() {
        mBinder.play();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        StatusBarUtil.setTranslucentStatus(this);
        StatusBarUtil.setStatusBarColor(this, Color.TRANSPARENT);


    }






//    private void initComment() {
//        HomeAdapter adapterComment = new HomeAdapter(mContext, new LinearLayoutHelper(), 1, TYPE_COMMENT, R.layout.play_comment_layout) {
//            @Override
//            public void onBindViewHolder(BaseViewHolder holder, int position) {
//                super.onBindViewHolder(holder, position);
//                rvPlayComment = holder.getView(R.id.rv_play_comment);
//                adapter = new CommentAdapter(mContext, commentsBeanList);
//                rvPlayComment.setAdapter(adapter);
//                rvPlayComment.addItemDecoration(new SimpleItemDecoration());
//                rvPlayComment.setLayoutManager(new LinearLayoutManager(mContext));
//            }
//        };
//        adapterList.add(adapterComment);
//    }

    @Override
    protected void getBundleExtras(Bundle extras) {
        super.getBundleExtras(extras);
        section_id = extras.getString(EXTRA_SECTION_ID);
        course_id = extras.getString(EXTRA_COURSE_ID);
        isPlaying = extras.getBoolean(EXTRA_IS_PLAYING);
    }


    @Override
    public void getSectionSuccess(SectionBean section) {
//        swithController();

        model.setSection(section);
        sectionInfoList = section.getSection_list();

        bannerUrl = section.getBanner();
        sectionName = section.getSection_name();
        sectionDuration = section.getDuration();

        audioUrl = section.getUrl();
        audioDutaion = section.getDuration();
        isPay = section.getIs_pay();
        finalPrice = PriceFormater.formatPrice(section.getActivity_price(),section.getSale_price(),section.getPrice());

        setAudioUrl(audioUrl);
        Glide.with(mContext).load(bannerUrl).into(ivAudioPlayImage);
        switchFragment(section_id);

        tvAudioPlayDuration.setText("/" + TimeFormater.formatMs(Long.parseLong(audioDutaion) * 1000));
        seekBar.setMax(Integer.parseInt(audioDutaion) * 1000);

//        initPlayTop();
//        initComment();
//        adapterSectionInfo.notifyDataSetChanged();
//        Collections.reverse(sectionInfoList);
//        adapterSectionInfo = new SectionInfoAdapter(AudioPlayerActivity.this, sectionInfoList, section_id, course_id);
//        delegateAdapter.setAdapters(adapterList);
    }

    private void startService() {
        Log.d(TAG,"startService");
        Intent intent = new Intent(AudioPlayerActivity.this, AudioPlayService.class);
        Bundle bundle = new Bundle();
        bundle.putString(AudioPlayService.EXTAR_AUDIO_URL, audioUrl);
        intent.putExtras(bundle);
        startService(intent);
        bindService(intent, coon, BIND_AUTO_CREATE);
    }

    private void setAudioUrl(String audioUrl) {
        Map<String, String> params = new HashMap<>();
        params.put(Constants.EVENT_TAG.TAG_SECTION_ID, section_id);
        params.put("audio_url", audioUrl);
        EventBus.getDefault().post(new EventMessage<Map>(Constants.EVENT_TAG.TAG_PLAYER_CURRENT_SECTION, params));


    }

    @Override
    public void getSectionFail(String msg) {
        Log.d(TAG, msg);
    }

    @Override
    public void getBuyCourseSuccess() {
        Toast.makeText(mContext, "购买成功", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void getBuyCourseFail(String msg) {

    }

    @Override
    public void getLikeCommentSuccess() {
        Map<String, String> params = new HashMap<>();
        params.put(CommentAdapter.LIKE_COMMENT_SUCCESS, getResources().getString(R.string.comment_like_success));
        EventBus.getDefault().post(new EventMessage<Map>(Constants.EVENT_TAG.TAG_COMMENT_LIKE_SUCCESS, params));

        Toast.makeText(mContext, "点赞成功", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void getLikeCommentFail(String msg) {

    }

    @Override
    public void getUnlikeCommentSuccess() {
        Toast.makeText(mContext, "取消点赞成功", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void getUnlikeCommentFail(String msg) {

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_audio_play_back:
                onBackPressed();
                break;

            case R.id.iv_audio_play_download:
                downloadTest();
//                download();
                break;
            case R.id.fl_audio_play_controller:
                control();
                break;
            case R.id.tv_audio_play_show_all:
                showAll();
                break;

        }
    }

    private void downloadTest() {

        info  = new AliyunDownloadMediaInfo();
        info.setQuality("FD");
        info.setCoverUrl("https://vod-test2.cn-shanghai.aliyuncs.com/snapshot/9fb028c29acb421cb634c77cf4ebe07800003.jpg");
        info.setTitle("zhe");
//        info.setStatus(AliyunDownloadMediaInfo.Status.Idle);
        info.setVid("14b625146e614be7b150d67e5cc2573b");
//        info.setVid("50e27b06729c471582e9dc8cff4e0b01");
        info.setFormat("m3u8");
//        info.setEncripted(1);
//        info.setSavePath(file.getPath());
        addNewInfo(info);
//        Log.d(TAG,info.getTitle());
//        downloadManager.startDownloadMedia(info);
    }

    private void addNewInfo(AliyunDownloadMediaInfo info){
        if (downloadManager != null && info != null) {
//            downloadManager.setRefreshStsCallback(new MyRefreshStsCallback());

            callDownloadPrepare(info.getVid(),info.getTitle());
            downloadManager.addDownloadMedia(info);
            downloadManager.startDownloadMedia(info);
        }
//        if (downloadView != null && info != null) {
//            downloadView.addDownloadMediaInfo(info);
//        }
    }

    private void callDownloadPrepare(String vid,String title){
        AliyunVidSts vidSts =VidStsUtil.getVidSts(vid);

//        AliyunVidSts vidSts = new AliyunVidSts();
//        vidSts.setVid(vid);
//        vidSts.setAcId("STS.NHEL6H8NDgNGh2MubBsPLvnxs");
//        vidSts.setAkSceret("Cyzs2smtv92QPz3B4jGvzbbaKoFuHc2AttgVMGY2s5CF");
//        vidSts.setSecurityToken("CAISjgJ1q6Ft5B2yfSjIr4vwB4z81ZFl0IysahT8kWIXf99gmavTkTz2IHtKenZsCegav/Q3nW1V7vsdlrBtTJNJSEnDKNF36pkS6g66eIvGvYmz5LkJ0BxUqZBCTkyV5tTbRsmkZu6/E67fUzKpvyt3xqSAO1fGdle5MJqPpId6Z9AMJGeRZiZHA9EkQGkPtsgWZzmzWPG2KUyo+B2yanBloQ1hk2hyxL2iy8mHkHrkgUb91/UeqvaaQPHmTbE1Z88kAofpgrcnJvKfiHYI0XUQqvcq1p4j0Czco9SQD2NW5xi7KOfO+rVtVlQiOPZlR/4c8KmszaQl57SOyJ6U1RFBMexQVD7YQI2wGDdS2XJ/9rwagAE5co5MqLUGrWT0G2Eq86k/AmecBF1e3QqBFBkx17Pnetdue5cr3ObzaSJ83vG0X9TqsETfS6DdOY/aX4sHtgmMhSZhdXffmk4IwClqfHdgc5CetEROCOAuhyWs/JbxTG0WI4Ivs3VBcix6Fhc64y13oNt5qzpbOnWrUovGQyXvNw==");
//        vidSts.setAcId("");
//        vidSts.setAkSceret("");
//        vidSts.setSecurityToken("");
//        vidSts.setTitle(title);
        downloadManager.prepareDownloadMedia(vidSts);
    }


    private void showAll() {
        Bundle bundle = new Bundle();
        bundle.putString(CourseDetailActivity.EXTRA_COURSE_ID, course_id);
        readyGo(CourseDetailActivity.class, bundle);
    }

    @Override
    public void onEvent(EventMessage message) {
        super.onEvent(message);
        switch (message.getTag()) {
            case Constants.EVENT_TAG.TAG_PLAYER_CURRENT:
                String current = (String) ((HashMap) message.getData()).get("current");
                tvAudioPlayCurrent.setText(current);
                long currentLong = Long.parseLong((String) ((HashMap) message.getData()).get("current_long"));
                seekBar.setProgress((int) currentLong);
                break;
            case Constants.EVENT_TAG.TAG_SECTION_STATE_CHANGED:
                String state = (String) ((HashMap) message.getData()).get(Constants.EVENT_TAG.TAG_SECTION_STATE);
                if (state.equals("start")) {
                    Glide.with(mContext).load(R.drawable.vector_pause).into(ivAudioPlayPlay);
//                    ivAudioPlayPlay.setVisibility(View.INVISIBLE);
//                    ivAudioPlayPause.setVisibility(View.VISIBLE);
                    isPlaying = true;
                } else if (state.equals("pause")) {
                    Glide.with(mContext).load(R.drawable.vector_start).into(ivAudioPlayPlay);
//                    ivAudioPlayPlay.setVisibility(View.VISIBLE);
//                    ivAudioPlayPause.setVisibility(View.INVISIBLE);
                    isPlaying = false;
                }
                break;
            case Constants.EVENT_TAG.TAG_STOP_SERVICE:
                Toast.makeText(mContext, "stop", Toast.LENGTH_SHORT).show();
                break;

//            case Constants.EVENT_TAG.TAG_PLAYER_DURATION:
//                String duraion = (String) ((HashMap) message.getData()).get("duration");
//                tvAudioPlayDuration.setText("/" + duraion);
//                long durationLong = Long.parseLong((String) ((HashMap) message.getData()).get("duration_long"));
//                seekBar.setMax((int) durationLong);
//                break;
        }
    }


    public void buy() {
        audioPlayPresenter.requesetBuyCourse(accessToken, course_id);

    }

    private void control() {
        mBinder.play();
//        Map<String, String> params = new HashMap<>();
//        if (!isPlaying()){
//            params.put(Constants.EVENT_TAG.TAG_SECTION_STATE, "start");
//        }else {
//            params.put(Constants.EVENT_TAG.TAG_SECTION_STATE, "pause");
//
//        }
//        EventBus.getDefault().post(new EventMessage<Map>(Constants.EVENT_TAG.TAG_SECTION_STATE_CHANGING, params));


//        swithController();
    }

    private void swithController() {
        if (isPlaying()) {
            ivAudioPlayPlay.setVisibility(View.INVISIBLE);

        } else {
            ivAudioPlayPlay.setVisibility(View.VISIBLE);
        }
    }

    private void download() {
        if (!isLogin()) {
            goToLogin();
        } else {
            if (!(isPay.equals("1") || finalPrice.equals("免费"))) {
                Toast.makeText(mContext, "请先购买课程", Toast.LENGTH_SHORT).show();
            } else {
                if (NetWatchdog.is4GConnected(mContext)) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                    final AlertDialog dialogNoWifi = builder.create();
                    View dialogView = View.inflate(mContext, R.layout.dialog_no_wifi, null);
                    builder.setView(dialogView);
                    builder.show();

                    dialogView.findViewById(R.id.btn_no_wifi_cancel).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialogNoWifi.dismiss();
                        }
                    });
                    dialogView.findViewById(R.id.btn_no_wifi_continue).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(mContext, "已开始下载课程", Toast.LENGTH_SHORT).show();
                        }
                    });
                    //                    builder.setTitle("当前无Wi-Fi，继续下载将使用流量")
//                            .setNegativeButton("取消下载", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            dialog.dismiss();
//                        }
//                    }).setPositiveButton("继续下载", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            Toast.makeText(mContext, "已开始下载课程", Toast.LENGTH_SHORT).show();
//                        }
//                    });
//                    builder.create().show();
                } else {
                    Toast.makeText(mContext, "已开始下载课程", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }



    private boolean isPlaying() {
        return mBinder.isPlaying();
    }

    private String currentTag;


    private void switchFragment(String sectionId) {
        Map<String, String> params = new HashMap<>();
        params.put(Constants.EVENT_TAG.TAG_SECTION_ID, sectionId);
        params.put(Constants.EVENT_TAG.TAG_SECTION_NAME, sectionName);
        params.put(Constants.EVENT_TAG.TAG_SECTION_DURATION, TimeFormater.formatMs(Long.parseLong(sectionDuration) * 1000));
        params.put(Constants.EVENT_TAG.TAG_SECTION_BANNER_URL, bannerUrl);
        EventBus.getDefault().post(new EventMessage<Map>(Constants.EVENT_TAG.TAG_SECTION_CHOSEN, params));

        AudioPlayerContentFragment fragment = new AudioPlayerContentFragment();
        Bundle bundle = new Bundle();
        bundle.putString(AudioPlayerContentFragment.EXTAR_SECTION_ID, sectionId);
        fragment.setArguments(bundle);


        String tag = fragment.getClass().getSimpleName();
        Fragment fragFindByTag = fragmentManager.findFragmentByTag(currentTag);

        if (fragFindByTag != null && fragFindByTag.isAdded() && !fragFindByTag.isHidden()) {
            fragmentManager.beginTransaction().hide(fragFindByTag).commit();
        }
        if (fragment.isAdded()) {
            fragmentManager.beginTransaction().show(fragment).commit();
        } else {
            fragmentManager.beginTransaction().add(R.id.fl_audio_play_container, fragment, tag).commit();
        }
        currentTag = tag;
    }

    public void changeFragment(String sectionId) {
        section_id = sectionId;
        audioPlayPresenter.requestAudioSectionInfo(accessToken, section_id);
        PreferencesUtils.putString(mContext, CommonConstants.KEY_CURRENT_SECTION, sectionId);
//        switchFragment(sectionId);
    }

    public void likeComment(String commentId) {
        if (isLogin()) {
            audioPlayPresenter.likeComment(accessToken, commentId);
        } else {
            goToLogin();
        }
    }

    public void unlikeComment(String commentId) {
        if (isLogin()) {
            audioPlayPresenter.unlikeComment(accessToken, commentId);
        } else {
            goToLogin();
        }

    }

    @Override
    public void setCommentText(String content) {
        audioPlayPresenter.sendComment(accessToken, course_id, content);

    }


}
