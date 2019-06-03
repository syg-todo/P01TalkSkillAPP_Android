package com.tuodanhuashu.app.course.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.alibaba.android.vlayout.layout.LinearLayoutHelper;
import com.aliyun.vodplayer.media.AliyunLocalSource;
import com.aliyun.vodplayer.media.IAliyunVodPlayer;
import com.chad.library.adapter.base.BaseViewHolder;
import com.company.common.CommonConstants;
import com.company.common.utils.PreferencesUtils;
import com.company.common.utils.StringUtils;
import com.tuodanhuashu.app.R;
import com.tuodanhuashu.app.base.SimpleItemDecoration;
import com.tuodanhuashu.app.course.AudioPlayService;
import com.tuodanhuashu.app.course.bean.CommentBean;
import com.tuodanhuashu.app.course.bean.SectionBean;
import com.tuodanhuashu.app.course.presenter.AudioPlayPresenter;
import com.tuodanhuashu.app.course.ui.adapter.CommentAdapter;
import com.tuodanhuashu.app.course.view.AudioPlayView;
import com.tuodanhuashu.app.home.adapter.HomeAdapter;
import com.tuodanhuashu.app.user.ui.LoginActivity;
import com.tuodanhuashu.app.widget.player.VideoPlayerView;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AudioPlayActivity extends AppCompatActivity implements AudioPlayView {

    private static final String TAG = AudioPlayActivity.class.getSimpleName();
    private static final int TYPE_TOP = 0;
    private static final int TYPE_COMMENT = 1;
    @BindView(R.id.rv_play)
    RecyclerView recyclerView;
    @BindView(R.id.edit)
    EditText etAudioComment;
    @BindView(R.id.tv_audio_play_send)
    TextView tvAudioPlaySend;
    @BindView(R.id.layout_send)
    LinearLayout layoutSend;
    VideoPlayerView playerView;

    ImageView ivPlayController;
    ImageView ivPlayShare;
    ImageView ivDownload;
    SeekBar seekBar;
    TextView tvPlayDuration;
    TextView tvPlayCurrent;
    ImageView ivPlayBack;
    TextView tvPlayCourseName;
    private boolean isPlaying = false;
    //    private MediaPlayer mediaPlayer;
//    private MyConnection coon;
    private AudioPlayService.MyBinder audioController;

    private Context mContext;

    private DelegateAdapter delegateAdapter;

    private List<DelegateAdapter.Adapter> adapterList;

    private List<CommentBean> commentsBeanList = new ArrayList<>();

    private AudioPlayPresenter audioPlayPresenter;

    private CommentAdapter adapter;

    private RecyclerView rvPlayComment;
    public static final String EXTRA_SECTION_ID = "section_id";

    public static final String EXTAR_COURSE_ID = "course_id";


    private String section_id = "";
    private String access_token = "";

    private String course_id = "";
    private final int UPDATE_PROGRESS = 1;
    //        使用handler定时更新进度条
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case UPDATE_PROGRESS:
                    updateProgress();
                    break;
            }
        }
    };


//    @Override
//    protected int getContentViewLayoutID() {
//        return R.layout.activity_audio_play;
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio_play);
        ButterKnife.bind(this);
        mContext = this;
        access_token = PreferencesUtils.getString(mContext, CommonConstants.KEY_TOKEN);
        Bundle bundle = getIntent().getExtras();

        section_id = bundle.getString(EXTRA_SECTION_ID);
        course_id = bundle.getString(EXTAR_COURSE_ID);
        initData();
        initView();

        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

    }

    private void initData() {
        access_token = PreferencesUtils.getString(mContext, CommonConstants.KEY_TOKEN);
        audioPlayPresenter = new AudioPlayPresenter(mContext, this);
        audioPlayPresenter.requestCourseClassList(access_token, section_id);

    }


    protected void initView() {

        adapterList = new ArrayList<>();
        VirtualLayoutManager layoutManager = new VirtualLayoutManager(mContext);
        recyclerView.setLayoutManager(layoutManager);
        RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
        recyclerView.setRecycledViewPool(viewPool);
        viewPool.setMaxRecycledViews(0, 10);
        delegateAdapter = new DelegateAdapter(layoutManager, true);
        recyclerView.setAdapter(delegateAdapter);
        final String content;

        tvAudioPlaySend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isLogin()) {
                    goToLogin();
                } else {

                    String content = etAudioComment.getText().toString().trim();
                    if (TextUtils.isEmpty(content)) {
                        Toast.makeText(mContext, "评论内容不能为空", Toast.LENGTH_SHORT).show();
                    } else {
//                        Toast.makeText(mContext, content, Toast.LENGTH_SHORT).show();
                        audioPlayPresenter.sendComment(access_token, course_id, content);
                        tvAudioPlaySend.setEnabled(false);
                        etAudioComment.setEnabled(false);
                        etAudioComment.setText("");
                        Toast.makeText(mContext,"评论成功",Toast.LENGTH_SHORT).show();
                        hideSoftKeyboard();
                        layoutSend.requestFocus();
                    }
//                Toast.makeText(mContext,content,Toast.LENGTH_SHORT).show();
//                Log.d(TAG,content);
                }
            }
        });

//        tvEdit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                audioPlayPresenter.sendComment(access_token, course_id, "挺好的啊");
//            }
//        });

        initPlayTop();
        initComment();
        delegateAdapter.setAdapters(adapterList);


    }

    private void hideSoftKeyboard() {
        View view = getCurrentFocus();
        if (view != null) {
            IBinder binder = view.getWindowToken();
            InputMethodManager manager = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
            manager.hideSoftInputFromWindow(binder, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    private boolean isLogin() {
        String token = PreferencesUtils.getString(mContext, CommonConstants.KEY_TOKEN, "");

        return !StringUtils.isEmpty(token);
    }

    private void goToLogin() {
        PreferencesUtils.putString(mContext, CommonConstants.KEY_ACCOUNT_ID, "");
        PreferencesUtils.putString(mContext, CommonConstants.KEY_TOKEN, "");

        Intent intent = new Intent(mContext, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private void initComment() {
        HomeAdapter adapterComment = new HomeAdapter(mContext, new LinearLayoutHelper(), 1, TYPE_COMMENT, R.layout.play_comment_layout) {
            @Override
            public void onBindViewHolder(BaseViewHolder holder, int position) {
                super.onBindViewHolder(holder, position);

                rvPlayComment = holder.getView(R.id.rv_play_comment);


//                rvPlayComment.setAdapter(adapter);
//                rvPlayComment.addItemDecoration(new SimpleItemDecoration());
//                rvPlayComment.setLayoutManager(new LinearLayoutManager(mContext));
            }
        };
        adapterList.add(adapterComment);
    }

    private void initPlayTop() {
        HomeAdapter adapterTop = new HomeAdapter(mContext, new LinearLayoutHelper(), 1, TYPE_TOP, R.layout.play_top_layout) {

            @Override
            public void onBindViewHolder(BaseViewHolder holder, int position) {
                super.onBindViewHolder(holder, position);
                playerView = holder.getView(R.id.iv_play_iamge);

                playerView.onResume();
                playerView.setOnPreparedListener(new AudioPrepareListener((AudioPlayActivity) mContext));
                String url = "http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4";

//                String url = "http://m10.music.126.net/20190528134504/5d4b7996319188b6c32679ad16598d0e/ymusic/c42b/91c2/697a/6ce5fa08bda832ee45a32e2d7087fbbc.mp3";
                AliyunLocalSource.AliyunLocalSourceBuilder alsb = new AliyunLocalSource.AliyunLocalSourceBuilder();
                alsb.setSource(url);
                AliyunLocalSource localSource = alsb.build();
                playerView.setLocalSource(localSource);
                playerView.setAudio(true);
                playerView.setAutoPlay(true);//设置自动播放
//                playerView.setBackground(R.drawable.test);
//                ivPlayController = holder.getView(R.id.iv_play_controller);
                ivPlayShare = holder.getView(R.id.iv_play_share);
                ivDownload = holder.getView(R.id.iv_play_download);
//                seekBar = holder.getView(R.id.seekbar_play);
//                tvPlayDuration = holder.getView(R.id.tv_play_duration);
//                tvPlayCurrent = holder.getView(R.id.tv_play_current);
//                ivPlayBack = holder.getView(R.id.iv_play_head_back);
                tvPlayCourseName = holder.getView(R.id.tv_play_course_name);


                ivDownload.getDrawable().setTint(getResources().getColor(R.color.white));
                ivPlayShare.getDrawable().setTint(getResources().getColor(R.color.white));
                Intent intent = new Intent(mContext, AudioPlayService.class);
//                coon = new MyConnection();
                startService(intent);
//                bindService(intent, coon, BIND_AUTO_CREATE);


//                ivPlayBack.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        onBackPressed();
//                    }
//                });
//                ivPlayController.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//
//                        playerView.switchPlayerState();
//                        updatePlayText();
////                        play();
//                    }
//                });


//                seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
//                    @Override
//                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
//                        //进度条改变
//                        if (fromUser) {
//                            audioController.seekTo(progress);
//                        }
//                    }
//
//                    @Override
//                    public void onStartTrackingTouch(SeekBar seekBar) {
//                        //开始触摸进度条
//                    }
//
//                    @Override
//                    public void onStopTrackingTouch(SeekBar seekBar) {
//                        //停止触摸进度条
//                    }
//                });

            }
        };
        adapterList.add(adapterTop);


    }

    @Override
    public void getSectionSuccess(SectionBean section) {
        commentsBeanList = section.getComments();
        adapter = new CommentAdapter(mContext, commentsBeanList);
        rvPlayComment.setAdapter(adapter);

        rvPlayComment.addItemDecoration(new SimpleItemDecoration());
        rvPlayComment.setLayoutManager(new LinearLayoutManager(mContext));


        tvPlayCourseName.setText(section.getSection_name());
    }

    @Override
    public void getSectionFail(String msg) {
    }

    @Override
    public void showToast(String msg) {

    }

    @Override
    public void showErrorView(String msg) {

    }

    @Override
    public void showEmptyView(String msg) {

    }

    @Override
    public void showOriginView() {

    }

    @Override
    public void showLoadingView() {

    }

    @Override
    public void showLoadingDialog() {

    }

    @Override
    public void cancalLoadingDialog() {

    }


    private static class AudioPrepareListener implements IAliyunVodPlayer.OnPreparedListener {
        private WeakReference<AudioPlayActivity> activityWeakReference;

        public AudioPrepareListener(AudioPlayActivity activityWeakReference) {
            this.activityWeakReference = new WeakReference<>(activityWeakReference);
        }

        @Override
        public void onPrepared() {
            AudioPlayActivity activity = activityWeakReference.get();
            if (activity != null) {
                activity.onPrepared();
            }
        }
    }

    private void onPrepared() {
//        logStrs.add(format.format(new Date()) + getString(R.string.log_prepare_success));
//
//        for (String log : logStrs) {
//            tvLogs.append(log + "\n");
//        }
//        FixedToastUtils.show(AliyunPlayerSkinActivity.this.getApplicationContext(),R.string.toast_prepare_success);
    }

//    private class MyConnection implements ServiceConnection {
//
//        @Override
//        public void onServiceConnected(ComponentName name, IBinder service) {
//            Log.d("111", "onServiceConnected");
//
//            audioController = (AudioPlayService.MyBinder) service;
//            updatePlayText();
//            //设置进度条的最大值
//            int duration = audioController.getDuration();
//            String time = transTimeIntToString(duration);
//
//            tvPlayDuration.setText("/" + time);
//            tvPlayCurrent.setText("00:00");
//            seekBar.setMax(audioController.getDuration());
//
//            seekBar.setProgress(audioController.getCurrentPostion());
//
//        }
//
//        @Override
//        public void onServiceDisconnected(ComponentName name) {
//
//        }
//
//    }


    private String transTimeIntToString(int time) {
        int minutes = time / 60_000;
        int seconds = (time % 60_000) / 1000;

        return String.format("%02d:%02d", minutes, seconds);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
        if (playerView != null) {
            Log.d(TAG, "playerView != null");
            playerView.onResume();
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        unbindService(coon);
    }

    @Override
    protected void onStop() {
        super.onStop();
        handler.removeCallbacksAndMessages(null);
    }

    private void updateProgress() {
        int currentPosition = audioController.getCurrentPostion();
        seekBar.setProgress(currentPosition);
        tvPlayCurrent.setText(transTimeIntToString(currentPosition));
        handler.sendEmptyMessageDelayed(UPDATE_PROGRESS, 500);

    }

//    public void updatePlayText() {
//        if (playerView.isPlaying()) {
//
//            ivPlayController.setSelected(true);
//
//            handler.sendEmptyMessage(UPDATE_PROGRESS);
//        } else {
//            ivPlayController.setSelected(false);
//
//        }
//
//
//    }

//    public void play() {
//
//        audioController.play();
//        if (audioController != null) {
//            handler.sendEmptyMessage(UPDATE_PROGRESS);
//        }
//        updatePlayText();
//    }
}
