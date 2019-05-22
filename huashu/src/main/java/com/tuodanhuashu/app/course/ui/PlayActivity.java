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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.alibaba.android.vlayout.layout.LinearLayoutHelper;
import com.chad.library.adapter.base.BaseViewHolder;
import com.tuodanhuashu.app.R;
import com.tuodanhuashu.app.base.SimpleItemDecoration;
import com.tuodanhuashu.app.course.AudioPlayService;
import com.tuodanhuashu.app.course.bean.CourseDetailBean;
import com.tuodanhuashu.app.course.ui.adapter.CommentAdapter;
import com.tuodanhuashu.app.home.adapter.HomeAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PlayActivity extends AppCompatActivity {

    private static final int TYPE_TOP = 1;
    private static final int TYPE_COMMENT = 2;
    @BindView(R.id.rv_play)
    RecyclerView recyclerView;


    ImageView ivPlayController;
    ImageView ivPlayShare;
    ImageView ivDownload;
    SeekBar seekBar;
    TextView tvPlayDuration;
    TextView tvPlayCurrent;


    private boolean isPlaying = false;
    //    private MediaPlayer mediaPlayer;
    private MyConnection coon;
    private AudioPlayService.MyBinder audioController;

    private Context mContext;

    private DelegateAdapter delegateAdapter;

    private List<DelegateAdapter.Adapter> adapterList;

    private List<CourseDetailBean.CommentsBean> commentsBeanList = new ArrayList<>();

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

                RecyclerView rvPlayComment = holder.getView(R.id.rv_play_comment);


                //模拟数据
                CourseDetailBean.CommentsBean commentsBean = new CourseDetailBean.CommentsBean();
                commentsBean.setCreate_date("2019-03-27");
                commentsBean.setHeade_img("http://img1.imgtn.bdimg.com/it/u=3049673303,4028148324&fm=26&gp=0.jpg");
                commentsBean.setNickname("fenglang");
                commentsBean.setContent("看过许多周凡老师的文章");
                commentsBean.setLike_count(10);

                CourseDetailBean.CommentsBean.ReplyBean replyBean = new CourseDetailBean.CommentsBean.ReplyBean();
                replyBean.setContent("谢谢你的肯定");
                replyBean.setCreate_date("2019-05-22");
                replyBean.setNickname("keji");

                List<CourseDetailBean.CommentsBean.ReplyBean> replyBeans = new ArrayList<>();
                replyBeans.add(replyBean);
                commentsBean.setReply(replyBeans);


                CourseDetailBean.CommentsBean commentsBean2 = new CourseDetailBean.CommentsBean();
                commentsBean2.setCreate_date("2019-03-27");
                commentsBean2.setHeade_img("http://img1.imgtn.bdimg.com/it/u=3049673303,4028148324&fm=26&gp=0.jpg");
                commentsBean2.setNickname("fenglang");
                commentsBean2.setContent("看过许多周凡老师的文章");
                commentsBean2.setLike_count(10);
                CourseDetailBean.CommentsBean.ReplyBean replyBean2 = new CourseDetailBean.CommentsBean.ReplyBean();
                replyBean2.setContent("谢谢你ahaaaaaaahhh的肯定");
                replyBean2.setCreate_date("2019-05-22");
                replyBean2.setNickname("keagasgaji");

                List<CourseDetailBean.CommentsBean.ReplyBean> replyBeans2 = new ArrayList<>();
                replyBeans2.add(replyBean2);
                commentsBean2.setReply(replyBeans2);


                CourseDetailBean.CommentsBean commentsBean1 = new CourseDetailBean.CommentsBean();
                commentsBean1.setCreate_date("2019-03-27");
                commentsBean1.setHeade_img("http://img1.imgtn.bdimg.com/it/u=3049673303,4028148324&fm=26&gp=0.jpg");
                commentsBean1.setNickname("fenglang");
                commentsBean1.setContent("看过许多周凡老师的文章");
                commentsBean1.setLike_count(10);

                commentsBeanList.add(commentsBean);
                commentsBeanList.add(commentsBean2);
                commentsBeanList.add(commentsBean1);
                commentsBeanList.add(commentsBean1);
                commentsBeanList.add(commentsBean1);
                commentsBeanList.add(commentsBean1);

//模拟数据

                CommentAdapter adapter = new CommentAdapter(mContext, commentsBeanList);
                rvPlayComment.setAdapter(adapter);
                rvPlayComment.addItemDecoration(new SimpleItemDecoration());
                rvPlayComment.setLayoutManager(new LinearLayoutManager(mContext));
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


                ivDownload.getDrawable().setTint(getResources().getColor(R.color.white));
                ivPlayShare.getDrawable().setTint(getResources().getColor(R.color.white));
                Intent intent = new Intent(mContext, AudioPlayService.class);
                coon = new MyConnection();
                startService(intent);
                bindService(intent, coon, BIND_AUTO_CREATE);

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
        Log.d("111", "PlayActivity-play()");
        audioController.play();
        if (audioController != null) {
            handler.sendEmptyMessage(UPDATE_PROGRESS);
        }
        updatePlayText();
    }
}
