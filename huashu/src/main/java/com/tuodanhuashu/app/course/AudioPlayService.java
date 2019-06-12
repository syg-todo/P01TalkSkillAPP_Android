package com.tuodanhuashu.app.course;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import com.aliyun.vodplayer.media.AliyunLocalSource;
import com.tuodanhuashu.app.Constants.Constants;
import com.tuodanhuashu.app.eventbus.EventMessage;
import com.tuodanhuashu.app.widget.player.VideoPlayerView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Map;

public class AudioPlayService extends Service {
    private static final String TAG = AudioPlayService.class.getSimpleName();
//    private String path = "mnt/sdcard/123.mp3";
//    private MediaPlayer player;
    private VideoPlayerView playerView;

    public static final String EXTAR_AUDIO_URL = "audio_url";
    private AudioBinder mBinder = new AudioBinder();
    private String audioUrl;
    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG,"onBind");
        return mBinder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //这里只执行一次，用于准备播放器
        Log.d(TAG,"onCreate");
        EventBus.getDefault().register(this);
       initPlayer(this);
//        player = MediaPlayer.create(this, R.raw.honor);
//        try {
//            player.setDataSource(path);
//            //准备资源
//            player.prepare();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
//            @Override
//            public void onCompletion(MediaPlayer mp) {
//
//            }
//        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getEvent(EventMessage eventMessage){
        switch (eventMessage.getTag()){
            case Constants.EVENT_TAG.TAG_PLAYER_AUDIO_URL:
                audioUrl = (String) ((Map)eventMessage.getData()).get("audio_url");
                Log.d(TAG,audioUrl);
                prepraePlayer(audioUrl);
                break;
        }
    }

    private void initPlayer(AudioPlayService audioPlayService) {
        playerView = new VideoPlayerView(this);

        playerView.onResume();
//        playerView.setOnPreparedListener(new AudioPlayActivity.AudioPrepareListener((AudioPlayActivity) audioPlayService));
//        String url = "http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4";
//        String url = "http://video.zhongpin.me/sv/486550a8-16b030e63aa/486550a8-16b030e63aa.mp3";
        playerView.setAudio(true);
        playerView.setAutoPlay(true);//设置自动播放
}

    private void prepraePlayer(String audioUrl) {
        AliyunLocalSource.AliyunLocalSourceBuilder alsb = new AliyunLocalSource.AliyunLocalSourceBuilder();
        alsb.setSource(audioUrl);
        AliyunLocalSource localSource = alsb.build();
        playerView.setLocalSource(localSource);
//        playerView.start();
    }

    //该方法包含关于歌曲的操作
        public class AudioBinder extends Binder {

            //判断是否处于播放状态
            public boolean isPlaying() {
                return playerView.isPlaying();
            }

            //播放或暂停歌曲
            public void play() {
            if (isPlaying()) {
                Log.d("111","pause");
                playerView.pause();
            } else {
                Log.d("111","start");
                playerView.start();
            }
        }



        //返回歌曲的长度，单位为毫秒
        public int getDuration() {
            Log.d(TAG,"getDuration");
            return playerView.getDuration();
        }

        //返回歌曲目前的进度，单位为毫秒
        public int getCurrentPostion() {
            return playerView.getCurrentPosition();
        }

        //设置歌曲播放的进度，单位为毫秒
        public void seekTo(int mesc) {
            playerView.seekTo(mesc);

        }
    }

}
