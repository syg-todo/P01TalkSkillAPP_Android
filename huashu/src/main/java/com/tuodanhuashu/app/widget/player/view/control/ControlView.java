package com.tuodanhuashu.app.widget.player.view.control;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.aliyun.vodplayer.media.AliyunMediaInfo;
import com.tuodanhuashu.app.R;
import com.tuodanhuashu.app.utils.TimeFormater;

public class ControlView extends RelativeLayout {

    private ImageView ivTitleBack;
    private ImageView ivLike;
    private ImageView btnPlayState;
    //标题返回按钮监听
    private OnBackClickListener mOnBackClickListener;
    //进度条
    private SeekBar mSeekbar;
    //播放按钮点击监听
    private OnPlayStateClickListener mOnPlayStateClickListener;
    //当前时间
    private TextView mTvPosition;
    //总时长
    private TextView mTvDuration;

    //视频信息，info显示用。
    private AliyunMediaInfo mAliyunMediaInfo;
    //播放的进度
    private int mVideoPosition = 0;


    //视频播放状态
    private PlayState mPlayState = PlayState.NotPlaying;
    //seekbar拖动状态
    private boolean isSeekbarTouching = false;


    private OnStarClickListener mOnStarClickListener;
    //进度条拖动监听
    private OnSeekListener mOnSeekListener;

    public ControlView(Context context) {
        super(context);
        init();
    }

    public ControlView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ControlView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.video_player_control, this, true);

        findAllViews(); //找到所有的view

        setViewListener(); //设置view的监听事件
//
//        updateAllViews(); //更新view的显示
    }

    private void findAllViews() {
        ivTitleBack = findViewById(R.id.iv_player_title_back);
        ivTitleBack.getDrawable().setTint(getResources().getColor(R.color.white));
        btnPlayState = findViewById(R.id.btn_player_state);
//        ivLike = findViewById(R.id.iv_player_like);

        mSeekbar = findViewById(R.id.player_info_seekbar);
        mTvDuration = findViewById(R.id.player_info_duration);
        mTvPosition = findViewById(R.id.player_info_position);
    }

    private void setViewListener() {
        ivTitleBack.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnBackClickListener != null) {
                    mOnBackClickListener.onClick();
                }
            }
        });

//        ivLike.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (mOnStarClickListener != null){
//                    mOnStarClickListener.onClick();
//                }
//            }
//        });

        btnPlayState.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnPlayStateClickListener != null) {
                    mOnPlayStateClickListener.onPlayStateClick();
                }
            }
        });


        SeekBar.OnSeekBarChangeListener seekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    //这里是用户拖动，直接设置文字进度就行，
                    // 无需去updateAllViews() ， 因为不影响其他的界面。

                    mTvPosition.setText(TimeFormater.formatMs(progress));
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                isSeekbarTouching = true;
//                mHideHandler.removeMessages(WHAT_HIDE);
                if (mOnSeekListener != null) {
                    mOnSeekListener.onSeekStart();
                }
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

                if (mOnSeekListener != null) {
                    mOnSeekListener.onSeekEnd(seekBar.getProgress());
                }

                isSeekbarTouching = false;
//                mHideHandler.removeMessages(WHAT_HIDE);
//                mHideHandler.sendEmptyMessageDelayed(WHAT_HIDE, DELAY_TIME);
            }
        };
        mSeekbar.setOnSeekBarChangeListener(seekBarChangeListener);
    }

    public void setPlayState(PlayState playState) {
        mPlayState = playState;
        updatePlayStateBtn();
    }



    /**
     * 更新控制条信息
     */
    private void updateInfoBar() {
        //先设置小屏的info数据
        if (mAliyunMediaInfo != null) {
            mTvDuration.setText("/" + TimeFormater.formatMs(mAliyunMediaInfo.getDuration()));
            mSeekbar.setMax((int) mAliyunMediaInfo.getDuration());
        } else {
            mTvDuration.setText("/" + TimeFormater.formatMs(0));
            mSeekbar.setMax(0);
        }

        if (isSeekbarTouching) {
            //用户拖动的时候，不去更新进度值，防止跳动。
        } else {
//            mSeekbar.setSecondaryProgress(mVideoBufferPosition);
            mSeekbar.setProgress(mVideoPosition);
            mTvPosition.setText(TimeFormater.formatMs(mVideoPosition));
        }

    }


    public void setOnSeekListener(OnSeekListener onSeekListener) {
        mOnSeekListener = onSeekListener;
    }

    /**
     * 更新播放按钮的状态
     */
    private void updatePlayStateBtn() {

        if (mPlayState != PlayState.NotPlaying) {

            btnPlayState.setSelected(true);
        } else {
            btnPlayState.setSelected(false);
        }
    }

    /**
     * 更新视频进度
     *
     * @param position 位置，ms
     */
    public void setVideoPosition(int position) {
        mVideoPosition = position;
        updateInfoBar();
    }

    /**
     * 获取视频进度
     *
     * @return 视频进度
     */
    public int getVideoPosition() {
        return mVideoPosition;
    }



    /**
     * 设置视频信息
     *
     * @param aliyunMediaInfo 媒体信息
     * @param currentQuality  当前清晰度
     */
    public void setMediaInfo(AliyunMediaInfo aliyunMediaInfo, String currentQuality) {
        mAliyunMediaInfo = aliyunMediaInfo;
//        mCurrentQuality = currentQuality;
//        updateLargeInfoBar();
//        updateChangeQualityBtn();
    }

    public void setOnBackClickListener(OnBackClickListener l) {
        mOnBackClickListener = l;
    }


    public void setOnStarClickListener(OnStarClickListener l) {
        mOnStarClickListener = l;
    }

    public interface OnBackClickListener {
        /**
         * 返回按钮点击事件
         */
        void onClick();
    }

    public interface OnStarClickListener {

        void onClick();
    }

    public interface OnSeekListener {
        /**
         * seek结束事件
         */
        void onSeekEnd(int position);

        /**
         * seek开始事件
         */
        void onSeekStart();
    }


    /**
     * 播放状态
     */
    public static enum PlayState {
        /**
         * Playing:正在播放
         * NotPlaying: 停止播放
         */
        Playing, NotPlaying
    }


    public interface OnPlayStateClickListener {
        /**
         * 播放按钮点击事件
         */
        void onPlayStateClick();

    }

    public void setOnPlayStateClickListener(OnPlayStateClickListener onPlayStateClickListener) {
        mOnPlayStateClickListener = onPlayStateClickListener;
    }

}
