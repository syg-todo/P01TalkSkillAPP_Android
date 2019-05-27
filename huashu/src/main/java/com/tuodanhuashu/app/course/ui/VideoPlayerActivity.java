package com.tuodanhuashu.app.course.ui;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.aliyun.vodplayer.media.AliyunLocalSource;
import com.aliyun.vodplayer.media.AliyunVodPlayer;
import com.aliyun.vodplayer.media.IAliyunVodPlayer;
import com.tuodanhuashu.app.R;
import com.tuodanhuashu.app.widget.player.VideoPlayerView;

import java.lang.ref.WeakReference;

public class VideoPlayerActivity extends AppCompatActivity {

    AliyunVodPlayer aliyunVodPlayer;

    private VideoPlayerView mVideoPlayerView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_player);


//        if (Build.VERSION.SDK_INT >= 21) {
//            View decorView = getWindow().getDecorView();
//            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
//            decorView.setSystemUiVisibility(option);
//            getWindow().setStatusBarColor(Color.TRANSPARENT);
//        }
//        ActionBar actionBar = getSupportActionBar();
//        if (actionBar != null) {
//            actionBar.hide();
//        }


        initAliyunPlayerView();

    }

    private void initAliyunPlayerView() {

        mVideoPlayerView = findViewById(R.id.video_plater_view);


        mVideoPlayerView.setOnPreparedListener(new VideoPrepareListener(this));

    }

    private void prepare() {

//        String url = getIntent().getStringExtra("url");
//        String url = "http://player.alicdn.com/video/aliyunmedia.mp4";
        String url = "http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4";
        AliyunLocalSource.AliyunLocalSourceBuilder asb = new AliyunLocalSource.AliyunLocalSourceBuilder();
        asb.setSource(url);
        //aliyunVodPlayer.setLocalSource(asb.build());
        AliyunLocalSource mLocalSource = asb.build();
        aliyunVodPlayer.prepareAsync(mLocalSource);

    }

    private static class VideoPrepareListener implements IAliyunVodPlayer.OnPreparedListener {

        private WeakReference<VideoPlayerActivity> activityWeakReference;

        public VideoPrepareListener(VideoPlayerActivity skinActivity) {
            activityWeakReference = new WeakReference<VideoPlayerActivity>(skinActivity);
        }

        @Override
        public void onPrepared() {
            VideoPlayerActivity activity = activityWeakReference.get();
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

}