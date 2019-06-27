package com.tuodanhuashu.app.widget.player;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.alivc.player.VcPlayerLog;
import com.aliyun.vodplayer.media.AliyunLocalSource;
import com.aliyun.vodplayer.media.AliyunMediaInfo;
import com.aliyun.vodplayer.media.AliyunPlayAuth;
import com.aliyun.vodplayer.media.AliyunVidSts;
import com.aliyun.vodplayer.media.AliyunVodPlayer;
import com.aliyun.vodplayer.media.IAliyunVodPlayer;
import com.tuodanhuashu.app.Constants.Constants;
import com.tuodanhuashu.app.R;
import com.tuodanhuashu.app.eventbus.EventMessage;
import com.tuodanhuashu.app.utils.NetWatchdog;
import com.tuodanhuashu.app.utils.TimeFormater;
import com.tuodanhuashu.app.widget.player.view.control.ControlView;
import com.tuodanhuashu.app.widget.player.view.gesture.GestureView;
import com.tuodanhuashu.app.widget.player.view.tips.TipsView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

import static com.github.ybq.android.spinkit.animation.AnimationUtils.stop;


public class VideoPlayerView extends RelativeLayout {

    private static final String TAG = VideoPlayerView.class.getSimpleName();
    //视频画面
    private SurfaceView mSurfaceView;
    //手势操作view
    private GestureView mGestureView;
    //播放器
    private AliyunVodPlayer mAliyunVodPlayer;
    //Tips view
    private TipsView mTipsView;

    //网络状态监听
    private NetWatchdog mNetWatchdog;
    //媒体信息
    private AliyunMediaInfo mAliyunMediaInfo;
    //播放是否完成
    private boolean isCompleted = false;
    //是不是在seek中
    private boolean inSeek = false;
    private ControlView mControlView;

    private boolean isAudio = false;

    private int id;

    //进度更新计时器
    private ProgressUpdateTimer mProgressUpdateTimer = new ProgressUpdateTimer(this);

    //目前支持的几种播放方式
    private AliyunPlayAuth mAliyunPlayAuth;
    private AliyunLocalSource mAliyunLocalSource;
    private AliyunVidSts mAliyunVidSts;


    //对外的各种事件监听
    private IAliyunVodPlayer.OnPreparedListener mOutPreparedListener = null;
    private IAliyunVodPlayer.OnAutoPlayListener mOutAutoPlayListener = null;

    private OnPlayStateBtnClickListener onPlayStateBtnClickListener;
    private OnSeekStartListener onSeekStartListener;


    // 连网断网监听
    private NetConnectedListener mNetConnectedListener = null;

    public VideoPlayerView(Context context) {
        super(context);
        initVideoView();
    }

    public VideoPlayerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initVideoView();
    }

    public VideoPlayerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initVideoView();
    }

    private void initVideoView() {

        EventBus.getDefault().register(this);
        //初始化播放用的surfaceView
        initSurfaceView();


//        初始化播放器
        initAliVcPlayer();
//        //初始化封面
//        initCoverView();
        //初始化控制栏
        initControlView();

        if (isAudio) {
            return;
        }
        //初始化手势view
//        initGestureView();
//        //初始化清晰度view
//        initQualityView();
//        //初始化倍速view
//        initSpeedView();
//        //初始化指引view
//        initGuideView();
        //初始化提示view
        initTipsView();
        //初始化网络监听器
//        initNetWatchdog();
//        //初始化屏幕方向监听
//        initOrientationWatchdog();
//        //初始化手势对话框控制
//        initGestureDialogManager();
//        //默认为蓝色主题
//        setTheme(Theme.Blue);
//        //先隐藏手势和控制栏，防止在没有prepare的时候做操作。
//        hideGestureAndControlViews();
    }

    private void initNetWatchdog() {
        Context context = getContext();
        mNetWatchdog = new NetWatchdog(context);
        mNetWatchdog.setNetChangeListener(new MyNetChangeListener(this));
        mNetWatchdog.setNetConnectedListener(new MyNetConnectedListener(this));

    }


//    private void initGestureView() {
//        mGestureView = new GestureView(getContext());
//        addSubView(mGestureView);
//
//        //设置手势监听
//        mGestureView.setOnGestureListener(new GestureView.GestureListener() {
//
//            @Override
//            public void onHorizontalDistance(float downX, float nowX) {
//                //水平滑动调节seek。
//                // seek需要在手势结束时操作。
//                long duration = mAliyunVodPlayer.getDuration();
//                long position = mAliyunVodPlayer.getCurrentPosition();
//                long deltaPosition = 0;
//
//                if (mAliyunVodPlayer.getPlayerState() == IAliyunVodPlayer.PlayerState.Prepared ||
//                        mAliyunVodPlayer.getPlayerState() == IAliyunVodPlayer.PlayerState.Paused ||
//                        mAliyunVodPlayer.getPlayerState() == IAliyunVodPlayer.PlayerState.Started) {
//                    //在播放时才能调整大小
//                    deltaPosition = (long) (nowX - downX) * duration / getWidth();
//                }
//
////                if (mGestureDialogManager != null) {
////                    mGestureDialogManager.showSeekDialog(AliyunVodPlayerView.this, (int) position);
////                    mGestureDialogManager.updateSeekDialog(duration, position, deltaPosition);
////                }
//            }
//
//            @Override
//            public void onLeftVerticalDistance(float downY, float nowY) {
//                //左侧上下滑动调节亮度
//                int changePercent = (int) ((nowY - downY) * 100 / getHeight());
//
////                if (mGestureDialogManager != null) {
////                    mGestureDialogManager.showBrightnessDialog(AliyunVodPlayerView.this);
////                    int brightness = mGestureDialogManager.updateBrightnessDialog(changePercent);
////                    mAliyunVodPlayer.setScreenBrightness(brightness);
////                }
//            }
//
//            @Override
//            public void onRightVerticalDistance(float downY, float nowY) {
//                //右侧上下滑动调节音量
//                int changePercent = (int) ((nowY - downY) * 100 / getHeight());
//                int volume = mAliyunVodPlayer.getVolume();
//
////                if (mGestureDialogManager != null) {
////                    mGestureDialogManager.showVolumeDialog(AliyunVodPlayerView.this, volume);
////                    int targetVolume = mGestureDialogManager.updateVolumeDialog(changePercent);
////                    currentVolume = targetVolume;
////                    mAliyunVodPlayer.setVolume(targetVolume);//通过返回值改变音量
////                }
//            }
//
//            @Override
//            public void onGestureEnd() {
//                //手势结束。
//                //seek需要在结束时操作。
////                if (mGestureDialogManager != null) {
////                    mGestureDialogManager.dismissBrightnessDialog();
////                    mGestureDialogManager.dismissVolumeDialog();
////
////                    int seekPosition = mGestureDialogManager.dismissSeekDialog();
////                    if (seekPosition >= mAliyunVodPlayer.getDuration()) {
////                        seekPosition = (int) (mAliyunVodPlayer.getDuration() - 1000);
////                    }
////
////                    if (seekPosition >= 0) {
////                        seekTo(seekPosition);
////                        inSeek = true;
////                    }
////                }
//            }
//
//            @Override
//            public void onSingleTap() {
//                //单击事件，显示控制栏
////                if (mControlView != null) {
////                    if (mControlView.getVisibility() != VISIBLE) {
////                        mControlView.show();
////                    } else {
////                        mControlView.hide(ControlView.HideType.Normal);
////                    }
////                }
//
////                switchPlayerState();
//
//            }
//
//            @Override
//            public void onDoubleTap() {
//                //双击事件，控制暂停播放
////                switchPlayerState();
//                star();
//
//            }
//        });
//    }

    /**
     * 初始化提示view
     */
    private void initTipsView() {

        mTipsView = new TipsView(getContext());
        //设置tip中的点击监听事件
        mTipsView.setOnTipClickListener(new TipsView.OnTipClickListener() {
            @Override
            public void onContinuePlay() {
//                VcPlayerLog.d(TAG, "playerState = " + mAliyunVodPlayer.getPlayerState());
                //继续播放。如果没有prepare或者stop了，需要重新prepare
                mTipsView.hideAll();
                if (mAliyunVodPlayer.getPlayerState() == IAliyunVodPlayer.PlayerState.Idle ||
                        mAliyunVodPlayer.getPlayerState() == IAliyunVodPlayer.PlayerState.Stopped) {
//                    if (mAliyunPlayAuth != null) {
//                        prepareAuth(mAliyunPlayAuth);
//                    } else if (mAliyunVidSts != null) {
//                        prepareVidsts(mAliyunVidSts);
//                    } else if (mAliyunLocalSource != null) {
                    //setLocalSource(mAliyunLocalSource);
                    prepareLocalSource(mAliyunLocalSource);
//                    }
                } else {
                    start();
                }

            }

            @Override
            public void onStopPlay() {
                // 结束播放
                mTipsView.hideAll();
                stop();

                Context context = getContext();
                if (context instanceof Activity) {
                    ((Activity) context).finish();
                }
            }

            @Override
            public void onRetryPlay() {
                //重试
                reTry();
            }

            @Override
            public void onReplay() {
                //重播
                rePlay();
            }
        });
        addSubView(mTipsView);
    }

    public void reTry() {

        isCompleted = false;
        inSeek = false;

        int currentPosition = mControlView.getVideoPosition();

        if (mTipsView != null) {
            mTipsView.hideAll();
//        }
//        if (mControlView != null) {
//            mControlView.reset();
//            //防止被reset掉，下次还可以获取到这些值
//            mControlView.setVideoPosition(currentPosition);
//        }
//        if (mGestureView != null) {
//            mGestureView.reset();
//        }

            if (mAliyunVodPlayer != null) {

                //显示网络加载的loading。。
                if (mTipsView != null) {
                    mTipsView.showNetLoadingTipView();
                }
                //seek到当前的位置再播放
            /*
                isLocalSource()判断不够,有可能是sts播放,也有可能是url播放,还有可能是sd卡的视频播放,
                如果是后两者,需要走if,否则走else
             */
//                if (isLocalSource() || isUrlSource()) {
                mAliyunVodPlayer.prepareAsync(mAliyunLocalSource);
//                } else {
//                    mAliyunVodPlayer.prepareAsync(mAliyunVidSts);
//                }
                mAliyunVodPlayer.seekTo(currentPosition);
            }

        }
    }

    /**
     * 重播，将会从头开始播放
     */
    public void rePlay() {

        isCompleted = false;
        inSeek = false;

        if (mTipsView != null) {
            mTipsView.hideAll();
        }
//        if (mControlView != null) {
//            mControlView.reset();
//        }
//        if (mGestureView != null) {
//            mGestureView.reset();
//        }

        if (mAliyunVodPlayer != null) {
            //显示网络加载的loading。。
            if (mTipsView != null) {
                mTipsView.showNetLoadingTipView();
            }
            //重播是从头开始播
            mAliyunVodPlayer.replay();
        }

    }

    private void switchPlayerState() {
        IAliyunVodPlayer.PlayerState playerState = mAliyunVodPlayer.getPlayerState();
        if (playerState == IAliyunVodPlayer.PlayerState.Started) {
            pause();
        } else if (playerState == IAliyunVodPlayer.PlayerState.Paused || playerState == IAliyunVodPlayer.PlayerState.Prepared) {
            start();
        }
        if (onPlayStateBtnClickListener != null) {
            onPlayStateBtnClickListener.onPlayBtnClick(playerState);
        }
    }


    /**
     * 开始播放
     */
    public void start() {
        if (mControlView != null) {
            mControlView.setPlayState(ControlView.PlayState.Playing);
        }
        //显示其他的动作
        //mControlView.setHideType(ViewAction.HideType.Normal);
        //mGestureView.setHideType(ViewAction.HideType.Normal);
//        mGestureView.show();
//        mControlView.show();

        if (mAliyunVodPlayer == null) {
            return;
        }

        IAliyunVodPlayer.PlayerState playerState = mAliyunVodPlayer.getPlayerState();
        if (playerState == IAliyunVodPlayer.PlayerState.Paused || playerState == IAliyunVodPlayer.PlayerState.Prepared || mAliyunVodPlayer.isPlaying()) {
            mAliyunVodPlayer.start();
            Map<String, String> params = new HashMap<>();
            params.put(Constants.EVENT_TAG.TAG_SECTION_STATE, "start");
            EventBus.getDefault().post(new EventMessage<Map>(Constants.EVENT_TAG.TAG_SECTION_STATE_CHANGED, params));
        }

    }



    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
    public void goToPause(EventMessage<Map<String,String>> eventMessage){
        switch (eventMessage.getTag()){
            case Constants.EVENT_TAG.TAG_SECTION_STATE_CHANGING:
                if (eventMessage.getData().get(Constants.EVENT_TAG.TAG_SECTION_STATE).equals("start")){
                    Log.d(TAG,"pause");
                    pause();
                }else {
                    start();
                }
        }

    }

    //暂停播放
    public void pause() {
        if (mControlView != null) {
            mControlView.setPlayState(ControlView.PlayState.NotPlaying);
        }

        if (mAliyunVodPlayer == null) {
            return;
        }

        IAliyunVodPlayer.PlayerState playerState = mAliyunVodPlayer.getPlayerState();
        if (playerState == IAliyunVodPlayer.PlayerState.Started || mAliyunVodPlayer.isPlaying()) {
            mAliyunVodPlayer.pause();
            Map<String, String> params = new HashMap<>();
            params.put(Constants.EVENT_TAG.TAG_SECTION_STATE, "pause");
            EventBus.getDefault().post(new EventMessage<Map>(Constants.EVENT_TAG.TAG_SECTION_STATE_CHANGED, params));

        }
    }

    /**
     * 获取视频时长
     *
     * @return 视频时长
     */
    public int getDuration() {
        if (mAliyunVodPlayer != null && mAliyunVodPlayer.isPlaying()) {

            return (int) mAliyunVodPlayer.getDuration();
        }

        return 0;
    }

    /**
     * 获取当前位置
     *
     * @return 当前位置
     */
    public int getCurrentPosition() {
        if (mAliyunVodPlayer != null && mAliyunVodPlayer.isPlaying()) {
            return (int) mAliyunVodPlayer.getCurrentPosition();
        }

        return 0;
    }

    /**
     * seek操作
     *
     * @param position 目标位置
     */
    public void seekTo(int position) {
        if (mAliyunVodPlayer == null) {
            return;
        }
        inSeek = true;
        Log.d(TAG,"inSeek:"+inSeek);
        mAliyunVodPlayer.seekTo(position);
        mAliyunVodPlayer.start();

        Map<String, String> params = new HashMap<>();
        params.put(Constants.EVENT_TAG.TAG_SECTION_STATE, "start");
        EventBus.getDefault().post(new EventMessage<Map>(Constants.EVENT_TAG.TAG_SECTION_STATE_CHANGED, params));
        if (mControlView != null) {
            mControlView.setPlayState(ControlView.PlayState.Playing);
        }
    }


    private void initControlView() {
        mControlView = new ControlView(getContext());
        addSubView(mControlView);

        //设置播放按钮点击
        mControlView.setOnPlayStateClickListener(new ControlView.OnPlayStateClickListener() {
            @Override
            public void onPlayStateClick() {
                switchPlayerState();
            }
        });

        mControlView.setOnBackClickListener(new ControlView.OnBackClickListener() {
            @Override
            public void onClick() {
                Context context = getContext();
                if (context instanceof Activity) {
                    ((Activity) context).finish();
                }
            }
        });
        mControlView.setOnSeekListener(new ControlView.OnSeekListener() {
            @Override
            public void onSeekEnd(int position) {
                if (mControlView != null) {
                    mControlView.setVideoPosition(position);
                }
                if (isCompleted) {
                    //播放完成了，不能seek了
                    inSeek = false;
                } else {

                    //拖动结束后，开始seek
                    seekTo(position);

                    inSeek = true;
                    if (onSeekStartListener != null) {
                        onSeekStartListener.onSeekStart();
                    }
                }
            }

            @Override
            public void onSeekStart() {
                //拖动开始
                inSeek = true;
            }
        });


        mControlView.setOnStarClickListener(new ControlView.OnStarClickListener() {
            @Override
            public void onClick() {
                star();
            }
        });
    }


    //喜欢
    private void star() {
        Toast.makeText(getContext(), "star", Toast.LENGTH_SHORT).show();
    }


    private void initSurfaceView() {
        mSurfaceView = new SurfaceView(getContext().getApplicationContext());
        addSubView(mSurfaceView);
//        if (id != 0){
//            mSurfaceView.setBackgroundResource(R.drawable.test);
//        }
        SurfaceHolder holder = mSurfaceView.getHolder();
        //增加surfaceView的监听
        holder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder surfaceHolder) {
                mAliyunVodPlayer.setDisplay(surfaceHolder);
            }

            @Override
            public void surfaceChanged(SurfaceHolder surfaceHolder, int format, int width,
                                       int height) {

                mAliyunVodPlayer.surfaceChanged();
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
            }
        });

    }


    private void initAliVcPlayer() {
        mAliyunVodPlayer = new AliyunVodPlayer(getContext());
        mAliyunVodPlayer.enableNativeLog();
//        prepareLocalSource(mAliyunLocalSource);
//        prepare();
        //设置准备回调
        mAliyunVodPlayer.setOnPreparedListener(new IAliyunVodPlayer.OnPreparedListener() {
            @Override
            public void onPrepared() {
                if (mAliyunVodPlayer == null) {
                    return;
                }
//
                mAliyunMediaInfo = mAliyunVodPlayer.getMediaInfo();
                if (mAliyunMediaInfo == null) {
                    return;
                }
                //防止服务器信息和实际不一致
                mAliyunMediaInfo.setDuration((int) mAliyunVodPlayer.getDuration());
//                //使用用户设置的标题
//                mAliyunMediaInfo.setTitle(getTitle(mAliyunMediaInfo.getTitle()));
//                mAliyunMediaInfo.setPostUrl(getPostUrl(mAliyunMediaInfo.getPostUrl()));
                mControlView.setMediaInfo(mAliyunMediaInfo, mAliyunVodPlayer.getCurrentQuality());
//                mControlView.setHideType(ViewAction.HideType.Normal);
//                mGestureView.setHideType(ViewAction.HideType.Normal);
//                mControlView.show();
//                mGestureView.show();
//                if (mTipsView != null) {
//                    mTipsView.hideNetLoadingTipView();
//                }
//
//                setCoverUri(mAliyunMediaInfo.getPostUrl());
                //准备成功之后可以调用start方法开始播放
                if (mOutPreparedListener != null) {
                    mOutPreparedListener.onPrepared();
                }
            }
        });
        //播放器出错监听
//        mAliyunVodPlayer.setOnErrorListener(new IAliyunVodPlayer.OnErrorListener() {
//            @Override
//            public void onError(int errorCode, int errorEvent, String errorMsg) {
//                if (errorCode == AliyunErrorCode.ALIVC_ERR_INVALID_INPUTFILE.getCode()) {
//                    //当播放本地报错4003的时候，可能是文件地址不对，也有可能是没有权限。
//                    //如果是没有权限导致的，就做一个权限的错误提示。其他还是正常提示：
//                    int storagePermissionRet = ContextCompat.checkSelfPermission(
//                            AliyunVodPlayerView.this.getContext().getApplicationContext(),
//                            Manifest.permission.WRITE_EXTERNAL_STORAGE);
//                    if (storagePermissionRet != PackageManager.PERMISSION_GRANTED) {
//                        errorMsg = AliyunErrorCode.ALIVC_ERR_NO_STORAGE_PERMISSION.getDescription(getContext());
//                    } else if (!NetWatchdog.hasNet(getContext())) {
//                        //也可能是网络不行
//                        errorCode = AliyunErrorCode.ALIVC_ERR_NO_NETWORK.getCode();
//                        errorMsg = AliyunErrorCode.ALIVC_ERR_NO_NETWORK.getDescription(getContext());
//                    }
//                }
//
//                //关闭定时器
//                stopProgressUpdateTimer();
//
//                if (mTipsView != null) {
//                    mTipsView.hideAll();
//                }
//                //出错之后解锁屏幕，防止不能做其他操作，比如返回。
//                lockScreen(false);
//
//                showErrorTipView(errorCode, errorEvent, errorMsg);
//
//                if (mOutErrorListener != null) {
//                    mOutErrorListener.onError(errorCode, errorEvent, errorMsg);
//                }
//
//            }
//        });

        //播放器加载回调
//        mAliyunVodPlayer.setOnLoadingListener(new IAliyunVodPlayer.OnLoadingListener() {
//            @Override
//            public void onLoadStart() {
//                if (mTipsView != null) {
//                    mTipsView.showBufferLoadingTipView();
//                }
//            }
//
//            @Override
//            public void onLoadEnd() {
//                if (mTipsView != null) {
//                    mTipsView.hideBufferLoadingTipView();
//                }
//                if (mAliyunVodPlayer.isPlaying()) {
//                    mTipsView.hideErrorTipView();
//                }
//                hasLoadEnd.put(mAliyunMediaInfo, true);
//                vodPlayerLoadEndHandler.sendEmptyMessage(1);
//            }
//
//            @Override
//            public void onLoadProgress(int percent) {
//                if (mTipsView != null) {
//                    mTipsView.updateLoadingPercent(percent);
//                }
//            }
//        });
        //播放结束
//        mAliyunVodPlayer.setOnCompletionListener(new IAliyunVodPlayer.OnCompletionListener() {
//            @Override
//            public void onCompletion() {
//                inSeek = false;
//                //关闭定时器
//                stopProgressUpdateTimer();
//
//                //如果当前播放资源是本地资源时, 再显示replay
//                if (mTipsView != null && isLocalSource()) {
//                    //隐藏其他的动作,防止点击界面去进行其他操作
//                    mGestureView.hide(ViewAction.HideType.End);
//                    mControlView.hide(ViewAction.HideType.End);
//                    mTipsView.showReplayTipView();
//                }
//
//                if (mOutCompletionListener != null) {
//                    mOutCompletionListener.onCompletion();
//                }
//            }
//        });
//        mAliyunVodPlayer.setOnBufferingUpdateListener(new IAliyunVodPlayer.OnBufferingUpdateListener() {
//            @Override
//            public void onBufferingUpdate(int percent) {
//                mCurrentBufferPercentage = percent;
//            }
//        });
        //播放信息监听
//        mAliyunVodPlayer.setOnInfoListener(new IAliyunVodPlayer.OnInfoListener() {
//            @Override
//            public void onInfo(int arg0, int arg1) {
//                if (mOutInfoListener != null) {
//                    mOutInfoListener.onInfo(arg0, arg1);
//                }
//            }
//        });
        //切换清晰度结果事件
//        mAliyunVodPlayer.setOnChangeQualityListener(new IAliyunVodPlayer.OnChangeQualityListener() {
//            @Override
//            public void onChangeQualitySuccess(String finalQuality) {
//                //切换成功后就开始播放
//                mControlView.setCurrentQuality(finalQuality);
//                start();
//
//                startProgressUpdateTimer();
//
//                if (mTipsView != null) {
//                    mTipsView.hideNetLoadingTipView();
//                }
//                if (mOutChangeQualityListener != null) {
//                    mOutChangeQualityListener.onChangeQualitySuccess(finalQuality);
//                }
//            }
//
//            @Override
//            public void onChangeQualityFail(int code, String msg) {
//                //失败的话，停止播放，通知上层
//                if (mTipsView != null) {
//                    mTipsView.hideNetLoadingTipView();
//                }
//                if (code == CODE_SAME_QUALITY) {
//                    if (mOutChangeQualityListener != null) {
//                        mOutChangeQualityListener.onChangeQualitySuccess(mAliyunVodPlayer.getCurrentQuality());
//                    }
//                } else {
//                    stop();
//                    if (mOutChangeQualityListener != null) {
//                        mOutChangeQualityListener.onChangeQualityFail(code, msg);
//                    }
//                }
//            }
//        });
        //重播监听
//        mAliyunVodPlayer.setOnRePlayListener(new IAliyunVodPlayer.OnRePlayListener() {
//            @Override
//            public void onReplaySuccess() {
//                //重播、重试成功
//                mTipsView.hideAll();
//                mGestureView.show();
//                mControlView.show();
//                mControlView.setMediaInfo(mAliyunMediaInfo, mAliyunVodPlayer.getCurrentQuality());
//                //重播自动开始播放,需要设置播放状态
//                if (mControlView != null) {
//                    mControlView.setPlayState(ControlView.PlayState.Playing);
//                }
//                //开始启动更新进度的定时器
//                startProgressUpdateTimer();
//                if (mOutRePlayListener != null) {
//                    mOutRePlayListener.onReplaySuccess();
//                }
//            }
//        });
        //自动播放
        mAliyunVodPlayer.setOnAutoPlayListener(new IAliyunVodPlayer.OnAutoPlayListener() {
            @Override
            public void onAutoPlayStarted() {
                //自动播放开始,需要设置播放状态
                if (mControlView != null) {
                    mControlView.setPlayState(ControlView.PlayState.Playing);
                }
                if (mOutAutoPlayListener != null) {
                    mOutAutoPlayListener.onAutoPlayStarted();
                }
            }
        });
        //seek结束事件
        mAliyunVodPlayer.setOnSeekCompleteListener(new IAliyunVodPlayer.OnSeekCompleteListener() {
            @Override
            public void onSeekComplete() {
                inSeek = false;

//                if (mOuterSeekCompleteListener != null) {
//                    mOuterSeekCompleteListener.onSeekComplete();
//                }
            }
        });
        //PCM原始数据监听
//        mAliyunVodPlayer.setOnPcmDataListener(new IAliyunVodPlayer.OnPcmDataListener() {
//            @Override
//            public void onPcmData(byte[] data, int size) {
//                if (mOutPcmDataListener != null) {
//                    mOutPcmDataListener.onPcmData(data, size);
//                }
//            }
//        });
        //第一帧显示
        mAliyunVodPlayer.setOnFirstFrameStartListener(new IAliyunVodPlayer.OnFirstFrameStartListener() {
            @Override
            public void onFirstFrameStart() {
                //开始启动更新进度的定时器
                startProgressUpdateTimer();
//
//                mCoverView.setVisibility(GONE);
//                if (mOutFirstFrameStartListener != null) {
//                    mOutFirstFrameStartListener.onFirstFrameStart();
//                }
            }
        });
        //url过期监听
//        mAliyunVodPlayer.setOnUrlTimeExpiredListener(new IAliyunVodPlayer.OnUrlTimeExpiredListener() {
//            @Override
//            public void onUrlTimeExpired(String vid, String quality) {
//                System.out.println("abc : onUrlTimeExpired");
//                if (mOutUrlTimeExpiredListener != null) {
//                    mOutUrlTimeExpiredListener.onUrlTimeExpired(vid, quality);
//                }
//            }
//        });

        //请求源过期信息
//        mAliyunVodPlayer.setOnTimeExpiredErrorListener(new IAliyunVodPlayer.OnTimeExpiredErrorListener() {
//            @Override
//            public void onTimeExpiredError() {
//                System.out.println("abc : onTimeExpiredError");
//                Log.e("radish : ", "onTimeExpiredError: " + mAliyunMediaInfo.getVideoId());
//                if (mTipsView != null) {
//                    mTipsView.hideAll();
//                    mTipsView.showErrorTipViewWithoutCode("鉴权过期");
//                    mTipsView.setOnTipClickListener(new TipsView.OnTipClickListener() {
//                        @Override
//                        public void onContinuePlay() {
//                        }
//
//                        @Override
//                        public void onStopPlay() {
//                        }
//
//                        @Override
//                        public void onRetryPlay() {
//                            if (mOutTimeExpiredErrorListener != null) {
//                                mOutTimeExpiredErrorListener.onTimeExpiredError();
//                            }
//                        }
//
//                        @Override
//                        public void onReplay() {
//                        }
//                    });
//                }
//            }
//        });

        mAliyunVodPlayer.setDisplay(mSurfaceView.getHolder());
    }


    /**
     * addSubView 添加子view到布局中
     *
     * @param view 子view
     */

    private void addSubView(View view) {
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        addView(view, params);//添加到布局中
    }

//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        Log.d("111", "onTouch");
//        mAliyunVodPlayer.start();
//        return true;
//    }

    private void prepare() {

//        String url = getIntent().getStringExtra("url");
//        String url = "http://player.alicdn.com/video/aliyunmedia.mp4";
        String url = "http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4";
//        String url = "http://m10.music.126.net/20190528134504/5d4b7996319188b6c32679ad16598d0e/ymusic/c42b/91c2/697a/6ce5fa08bda832ee45a32e2d7087fbbc.mp3";
        AliyunLocalSource.AliyunLocalSourceBuilder asb = new AliyunLocalSource.AliyunLocalSourceBuilder();
        asb.setSource(url);
//        mAliyunVodPlayer.setLocalSource(asb.build());
        AliyunLocalSource mLocalSource = asb.build();
        mAliyunVodPlayer.prepareAsync(mLocalSource);

    }

    private void prepareLocalSource(AliyunLocalSource aliyunLocalSource) {
//        if (mControlView != null) {
//            mControlView.setForceQuality(true);
//        }
//        if (mControlView != null) {
//            mControlView.setIsMtsSource(false);
//        }
//
//        if (mQualityView != null) {
//            mQualityView.setIsMtsSource(false);
//        }

        mAliyunVodPlayer.prepareAsync(aliyunLocalSource);
    }


    /**
     * 设置准备事件监听
     *
     * @param onPreparedListener 准备事件
     */
    public void setOnPreparedListener(IAliyunVodPlayer.OnPreparedListener onPreparedListener) {
        mOutPreparedListener = onPreparedListener;
    }

    /**
     * 设置自动播放
     *
     * @param auto true 自动播放
     */
    public void setAutoPlay(boolean auto) {
        if (mAliyunVodPlayer != null) {
            mAliyunVodPlayer.setAutoPlay(auto);
        }
    }


    /**
     * 设置自动播放事件监听
     *
     * @param l 自动播放事件监听
     */
    public void setOnAutoPlayListener(IAliyunVodPlayer.OnAutoPlayListener l) {
        mOutAutoPlayListener = l;
    }

    /**
     * 播放按钮点击listener
     */
    public interface OnPlayStateBtnClickListener {
        void onPlayBtnClick(IAliyunVodPlayer.PlayerState playerState);
    }

    /**
     * seek开始监听
     */

    public interface OnSeekStartListener {
        void onSeekStart();
    }


    /**
     * 设置播放状态点击监听
     *
     * @param listener
     */
    public void setOnPlayStateBtnClickListener(OnPlayStateBtnClickListener listener) {
        this.onPlayStateBtnClickListener = listener;
    }

    /**
     * 判断是否有网络的监听
     */
    public interface NetConnectedListener {
        /**
         * 网络已连接
         */
        void onReNetConnected(boolean isReconnect);

        /**
         * 网络未连接
         */
        void onNetUnConnected();
    }

    public void setLocalSource(AliyunLocalSource aliyunLocalSource) {
        if (mAliyunVodPlayer == null) {
            return;
        }

//        clearAllSource();
        reset();

        mAliyunLocalSource = aliyunLocalSource;

//        if (mControlView != null) {
//            mControlView.setForceQuality(true);
//        }

        if (
//                        !isLocalSource() &&
                NetWatchdog.is4GConnected(getContext())) {
            if (mTipsView != null) {
                mTipsView.showNetChangeTipView();
            }
        } else {
            prepareLocalSource(aliyunLocalSource);
        }
//        mAliyunVodPlayer.prepareAsync(aliyunLocalSource);
    }


    public void setBackground(int id) {
        this.id = id;
    }

    public void setAudio(boolean isAudio) {
        this.isAudio = isAudio;
    }

    private void clearAllSource() {
//        mAliyunPlayAuth = null;
//        mAliyunVidSts = null;
        mAliyunLocalSource = null;
    }


    /**
     * 在activity调用onResume的时候调用。 解决home回来后，画面方向不对的问题
     */
    public void onResume() {
        Log.d(TAG,"onResume");
//        if (mIsFullScreenLocked) {
//            int orientation = getResources().getConfiguration().orientation;
//            if (orientation == Configuration.ORIENTATION_PORTRAIT) {
//                changeScreenMode(AliyunScreenMode.Small);
//            } else if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
//                changeScreenMode(AliyunScreenMode.Full);
//            }
//        }

//        if (mNetWatchdog != null) {
//            Log.d(TAG,"mNetWatchDog!=null");
//            mNetWatchdog.startWatch();
//        }

//        if (mOrientationWatchDog != null) {
//            mOrientationWatchDog.startWatch();
//        }

        //从其他界面过来的话，也要show。
//        if (mControlView != null) {
//            mControlView.show();
//        }

        //onStop中记录下来的状态，在这里恢复使用
//        resumePlayerState();
    }


    public boolean isPlaying() {
        IAliyunVodPlayer.PlayerState playerState = mAliyunVodPlayer.getPlayerState();
        return playerState == IAliyunVodPlayer.PlayerState.Started;
//         mAliyunVodPlayer.isPlaying();
    }

    /**
     * 重置。包括一些状态值，view的状态等
     */
    private void reset() {
        isCompleted = false;
        inSeek = false;

//        if (mTipsView != null) {
//            mTipsView.hideAll();
//        }
//        if (mControlView != null) {
//            mControlView.reset();
//        }
//        if (mGestureView != null) {
//            mGestureView.reset();
//        }
//        stop();
    }


    /**
     * 进度更新计时器
     */
    private static class ProgressUpdateTimer extends Handler {

        private WeakReference<VideoPlayerView> viewWeakReference;

        ProgressUpdateTimer(VideoPlayerView aliyunVodPlayerView) {
            viewWeakReference = new WeakReference<VideoPlayerView>(aliyunVodPlayerView);
        }

        @Override
        public void handleMessage(Message msg) {
            VideoPlayerView aliyunVodPlayerView = viewWeakReference.get();
            if (aliyunVodPlayerView != null) {

                aliyunVodPlayerView.handleProgressUpdateMessage(msg);
            }
            super.handleMessage(msg);
        }
    }

    /**
     * 处理进度更新消息
     *
     * @param msg
     */
    private void handleProgressUpdateMessage(Message msg) {
        Log.d(TAG,"inSeek:"+inSeek);
        if (mAliyunVodPlayer != null && !inSeek) {

            Map<String, String> params = new HashMap<>();
            params.put("current", TimeFormater.formatMs(mAliyunVodPlayer.getCurrentPosition()));
            params.put("current_long", String.valueOf(mAliyunVodPlayer.getCurrentPosition()));
            EventBus.getDefault().post(new EventMessage<Map>(Constants.EVENT_TAG.TAG_PLAYER_CURRENT, params));

            mControlView.setVideoPosition((int) mAliyunVodPlayer.getCurrentPosition());
        }
        //解决bug：在Prepare中开始更新的时候，不会发送更新消息。
        startProgressUpdateTimer();
    }

    /**
     * 开始进度条更新计时器
     */
    private void startProgressUpdateTimer() {
        if (mProgressUpdateTimer != null) {
            mProgressUpdateTimer.removeMessages(0);
            mProgressUpdateTimer.sendEmptyMessageDelayed(0, 1000);
        }
    }


    private static class MyNetChangeListener implements NetWatchdog.NetChangeListener {

        private WeakReference<VideoPlayerView> viewWeakReference;

        public MyNetChangeListener(VideoPlayerView aliyunVodPlayerView) {
            Log.d(TAG, "MyNetChangeListener");
            viewWeakReference = new WeakReference<VideoPlayerView>(aliyunVodPlayerView);
        }

        @Override
        public void onWifiTo4G() {
            Log.d(TAG, "MyNetChangeListener-onWifiTo4G");
            VideoPlayerView aliyunVodPlayerView = viewWeakReference.get();
            if (aliyunVodPlayerView != null) {
                aliyunVodPlayerView.onWifiTo4G();
            }
        }

        @Override
        public void on4GToWifi() {
            Log.d(TAG, "MyNetChangeListener-4gtowifi");
            VideoPlayerView aliyunVodPlayerView = viewWeakReference.get();
            if (aliyunVodPlayerView != null) {
                aliyunVodPlayerView.on4GToWifi();
            }
        }

        @Override
        public void onNetDisconnected() {
            VideoPlayerView aliyunVodPlayerView = viewWeakReference.get();
            if (aliyunVodPlayerView != null) {
                aliyunVodPlayerView.onNetDisconnected();
            }
        }
    }

    private void on4GToWifi() {
        Log.d(TAG, "4gtoWifi");
        //如果已经显示错误了，那么就不用显示网络变化的提示了。
        if (mTipsView.isErrorShow()) {
            return;
        }

        //隐藏网络变化的提示
        if (mTipsView != null) {
            Log.d(TAG,"mTipsView != null");
            mTipsView.hideNetChangeTipView();
            start();
        }

    }

    private void onNetDisconnected() {
//        VcPlayerLog.d(TAG, "onNetDisconnected");
        //网络断开。
        // NOTE： 由于安卓这块网络切换的时候，有时候也会先报断开。所以这个回调是不准确的。
    }

    private void onWifiTo4G() {
        Log.d(TAG, "onWifiTo4G");
        //如果已经显示错误了，那么就不用显示网络变化的提示了。
        if (mTipsView.isErrorShow()) {
            return;
        }

        //wifi变成4G，先暂停播放
        pause();

        //隐藏其他的动作,防止点击界面去进行其他操作
//        mGestureView.hide(ControlView.HideType.Normal);
//        mControlView.hide(ControlView.HideType.Normal);
//
//        //显示网络变化的提示
//        if (!isLocalSource() && mTipsView != null) {
        mTipsView.showNetChangeTipView();
//        }
    }


    /**
     * 断网/连网监听
     */
    private class MyNetConnectedListener implements NetWatchdog.NetConnectedListener {
        public MyNetConnectedListener(VideoPlayerView aliyunVodPlayerView) {
        }

        @Override
        public void onReNetConnected(boolean isReconnect) {
            if (mNetConnectedListener != null) {
                mNetConnectedListener.onReNetConnected(isReconnect);
            }
        }

        @Override
        public void onNetUnConnected() {
            if (mNetConnectedListener != null) {
                mNetConnectedListener.onNetUnConnected();
            }
        }
    }

    public void setNetConnectedListener(NetConnectedListener listener) {
        this.mNetConnectedListener = listener;
    }

    public static enum Theme {
        /**
         * 蓝色主题
         */
        Blue,
        /**
         * 绿色主题
         */
        Green,
        /**
         * 橙色主题
         */
        Orange,
        /**
         * 红色主题
         */
        Red
    }

}
