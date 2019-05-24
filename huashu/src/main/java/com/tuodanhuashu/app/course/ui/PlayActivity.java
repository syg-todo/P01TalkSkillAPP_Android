package com.tuodanhuashu.app.course.ui;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
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
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.alibaba.android.vlayout.layout.LinearLayoutHelper;
import com.chad.library.adapter.base.BaseViewHolder;
import com.tuodanhuashu.app.R;
import com.tuodanhuashu.app.base.HuaShuBaseActivity;
import com.tuodanhuashu.app.base.SimpleItemDecoration;
import com.tuodanhuashu.app.course.AudioPlayService;
import com.tuodanhuashu.app.course.bean.CommentBean;
import com.tuodanhuashu.app.course.bean.CourseDetailBean;
import com.tuodanhuashu.app.course.bean.SectionBean;
import com.tuodanhuashu.app.course.presenter.PlayPresenter;
import com.tuodanhuashu.app.course.ui.adapter.CommentAdapter;
import com.tuodanhuashu.app.course.view.PlayView;
import com.tuodanhuashu.app.home.adapter.HomeAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PlayActivity extends AppCompatActivity implements PlayView {

    private static final int TYPE_TOP = 0;
    private static final int TYPE_COMMENT = 1;
    @BindView(R.id.rv_play)
    RecyclerView recyclerView;


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
    private MyConnection coon;
    private AudioPlayService.MyBinder audioController;

    private Context mContext;

    private DelegateAdapter delegateAdapter;

    private List<DelegateAdapter.Adapter> adapterList;

    private List<CommentBean> commentsBeanList = new ArrayList<>();

    private PlayPresenter playPresenter;

    private CommentAdapter adapter;

    private RecyclerView rvPlayComment;
    public static final String EXTRA_SECTION_ID = "section_id";

    public static final String EXTAR_ACCESS_TOKEN = "access_token";


    private String section_id = "1";
    private String access_token = "1";

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
//        return R.layout.activity_play;
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        ButterKnife.bind(this);
        mContext = this;

        Bundle bundle = getIntent().getExtras();

        section_id = bundle.getString(EXTRA_SECTION_ID);
        access_token = bundle.getString(EXTAR_ACCESS_TOKEN);
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
        Log.d("111", "initData");
        playPresenter = new PlayPresenter(mContext, this);
        playPresenter.requestCourseClassList("0", "1");

    }


    protected void initView() {
//        mediaPlayer = MediaPlayer.create(this, R.raw.honor);

        adapterList = new ArrayList<>();
        VirtualLayoutManager layoutManager = new VirtualLayoutManager(mContext);
        recyclerView.setLayoutManager(layoutManager);
        RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
        recyclerView.setRecycledViewPool(viewPool);
        viewPool.setMaxRecycledViews(0, 10);
        delegateAdapter = new DelegateAdapter(layoutManager, true);
        recyclerView.setAdapter(delegateAdapter);

        initPlayTop();
        initComment();
        delegateAdapter.setAdapters(adapterList);


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


                ivPlayController = holder.getView(R.id.iv_play_controller);
                ivPlayShare = holder.getView(R.id.iv_play_share);
                ivDownload = holder.getView(R.id.iv_play_download);
                seekBar = holder.getView(R.id.seekbar_play);
                tvPlayDuration = holder.getView(R.id.tv_play_duration);
                tvPlayCurrent = holder.getView(R.id.tv_play_current);
                ivPlayBack = holder.getView(R.id.iv_play_head_back);
                tvPlayCourseName = holder.getView(R.id.tv_play_course_name);


                ivDownload.getDrawable().setTint(getResources().getColor(R.color.white));
                ivPlayShare.getDrawable().setTint(getResources().getColor(R.color.white));
                Intent intent = new Intent(mContext, AudioPlayService.class);
                coon = new MyConnection();
                startService(intent);
                bindService(intent, coon, BIND_AUTO_CREATE);


                ivPlayBack.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onBackPressed();
                    }
                });
                ivPlayController.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        play();
                    }
                });


                seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        //进度条改变
                        if (fromUser) {
                            audioController.seekTo(progress);
                        }
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {
                        //开始触摸进度条
                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                        //停止触摸进度条
                    }
                });

            }
        };
        adapterList.add(adapterTop);


    }

    @Override
    public void getSectionSuccess(SectionBean section) {
        Log.d("111", "success");
        Log.d("111", "section:" + section.getComments().get(0).getContent());
        commentsBeanList = section.getComments();
        adapter = new CommentAdapter(mContext, commentsBeanList);
        rvPlayComment.setAdapter(adapter);

        rvPlayComment.addItemDecoration(new SimpleItemDecoration());
        rvPlayComment.setLayoutManager(new LinearLayoutManager(mContext));



        tvPlayCourseName.setText(section.getSection_name());
    }

    @Override
    public void getSectionFail(String msg) {
        Log.d("111", msg);
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


    private class MyConnection implements ServiceConnection {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d("111", "onServiceConnected");

            audioController = (AudioPlayService.MyBinder) service;
            updatePlayText();
            //设置进度条的最大值
            int duration = audioController.getDuration();
            String time = transTimeIntToString(duration);

            tvPlayDuration.setText("/" + time);
            tvPlayCurrent.setText("00:00");
            seekBar.setMax(audioController.getDuration());

            seekBar.setProgress(audioController.getCurrentPostion());

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }

    }

    private String transTimeIntToString(int time) {
        int minutes = time / 60_000;
        int seconds = (time % 60_000) / 1000;

        return String.format("%02d:%02d", minutes, seconds);
    }

    @Override
    protected void onResume() {
        super.onResume();


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(coon);
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

    public void updatePlayText() {
        if (audioController.isPlaying()) {

            ivPlayController.setSelected(true);

            handler.sendEmptyMessage(UPDATE_PROGRESS);
        } else {
            ivPlayController.setSelected(false);

        }


    }

    public void play() {

        audioController.play();
        if (audioController != null) {
            handler.sendEmptyMessage(UPDATE_PROGRESS);
        }
        updatePlayText();
    }
}
