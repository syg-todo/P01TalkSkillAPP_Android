package com.tuodanhuashu.app.widget.player;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.RelativeLayout;

import com.aliyun.vodplayer.media.AliyunLocalSource;
import com.aliyun.vodplayer.media.AliyunVodPlayer;
import com.aliyun.vodplayer.media.IAliyunVodPlayer;
import com.tuodanhuashu.app.widget.player.view.control.ControlView;
import com.tuodanhuashu.app.widget.player.view.gesture.GestureView;


public class VideoPlayerView extends RelativeLayout {

    //视频画面
    private SurfaceView mSurfaceView;
    //手势操作view
    private GestureView mGestureView;
    //播放器
    private AliyunVodPlayer mAliyunVodPlayer;

    private ControlView mControlView;
    //对外的各种事件监听
    private IAliyunVodPlayer.OnPreparedListener mOutPreparedListener = null;


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

        //初始化播放用的surfaceView
        initSurfaceView();
//        初始化播放器
        initAliVcPlayer();
//        //初始化封面
//        initCoverView();
        //初始化手势view
        initGestureView();
//        //初始化清晰度view
//        initQualityView();
        //初始化控制栏
        initControlView();
//        //初始化倍速view
//        initSpeedView();
//        //初始化指引view
//        initGuideView();
//        //初始化提示view
//        initTipsView();
//        //初始化网络监听器
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

    private void initGestureView() {
        mGestureView = new GestureView(getContext());
        addSubView(mGestureView);

        //设置手势监听
        mGestureView.setOnGestureListener(new GestureView.GestureListener() {

            @Override
            public void onHorizontalDistance(float downX, float nowX) {
                //水平滑动调节seek。
                // seek需要在手势结束时操作。
                long duration = mAliyunVodPlayer.getDuration();
                long position = mAliyunVodPlayer.getCurrentPosition();
                long deltaPosition = 0;

                if (mAliyunVodPlayer.getPlayerState() == IAliyunVodPlayer.PlayerState.Prepared ||
                        mAliyunVodPlayer.getPlayerState() == IAliyunVodPlayer.PlayerState.Paused ||
                        mAliyunVodPlayer.getPlayerState() == IAliyunVodPlayer.PlayerState.Started) {
                    //在播放时才能调整大小
                    deltaPosition = (long) (nowX - downX) * duration / getWidth();
                }

//                if (mGestureDialogManager != null) {
//                    mGestureDialogManager.showSeekDialog(AliyunVodPlayerView.this, (int) position);
//                    mGestureDialogManager.updateSeekDialog(duration, position, deltaPosition);
//                }
            }

            @Override
            public void onLeftVerticalDistance(float downY, float nowY) {
                //左侧上下滑动调节亮度
                int changePercent = (int) ((nowY - downY) * 100 / getHeight());

//                if (mGestureDialogManager != null) {
//                    mGestureDialogManager.showBrightnessDialog(AliyunVodPlayerView.this);
//                    int brightness = mGestureDialogManager.updateBrightnessDialog(changePercent);
//                    mAliyunVodPlayer.setScreenBrightness(brightness);
//                }
            }

            @Override
            public void onRightVerticalDistance(float downY, float nowY) {
                //右侧上下滑动调节音量
                int changePercent = (int) ((nowY - downY) * 100 / getHeight());
                int volume = mAliyunVodPlayer.getVolume();

//                if (mGestureDialogManager != null) {
//                    mGestureDialogManager.showVolumeDialog(AliyunVodPlayerView.this, volume);
//                    int targetVolume = mGestureDialogManager.updateVolumeDialog(changePercent);
//                    currentVolume = targetVolume;
//                    mAliyunVodPlayer.setVolume(targetVolume);//通过返回值改变音量
//                }
            }

            @Override
            public void onGestureEnd() {
                //手势结束。
                //seek需要在结束时操作。
//                if (mGestureDialogManager != null) {
//                    mGestureDialogManager.dismissBrightnessDialog();
//                    mGestureDialogManager.dismissVolumeDialog();
//
//                    int seekPosition = mGestureDialogManager.dismissSeekDialog();
//                    if (seekPosition >= mAliyunVodPlayer.getDuration()) {
//                        seekPosition = (int) (mAliyunVodPlayer.getDuration() - 1000);
//                    }
//
//                    if (seekPosition >= 0) {
//                        seekTo(seekPosition);
//                        inSeek = true;
//                    }
//                }
            }

            @Override
            public void onSingleTap() {
                //单击事件，显示控制栏
//                if (mControlView != null) {
//                    if (mControlView.getVisibility() != VISIBLE) {
//                        mControlView.show();
//                    } else {
//                        mControlView.hide(ControlView.HideType.Normal);
//                    }
//                }

                switchPlayerState();

                Log.d("111", "singleTap");
            }

            @Override
            public void onDoubleTap() {
                //双击事件，控制暂停播放
//                switchPlayerState();
                Log.d("111", "doubleTap");

            }
        });
    }


    private void switchPlayerState() {
        IAliyunVodPlayer.PlayerState playerState = mAliyunVodPlayer.getPlayerState();
        if (playerState == IAliyunVodPlayer.PlayerState.Started) {
            pause();
        } else if (playerState == IAliyunVodPlayer.PlayerState.Paused || playerState == IAliyunVodPlayer.PlayerState.Prepared) {
            start();
        }
//        if (onPlayStateBtnClickListener != null) {
//            onPlayStateBtnClickListener.onPlayBtnClick(playerState);
//        }
    }

    /**
     * 开始播放
     */
    public void start() {
//        if (mControlView != null) {
//            mControlView.setPlayState(ControlView.PlayState.Playing);
//        }
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
        }

    }


    //暂停播放
    public void pause() {
//        if (mControlView != null) {
//            mControlView.setPlayState(ControlView.PlayState.NotPlaying);
//        }

        if (mAliyunVodPlayer == null) {
            return;
        }

        IAliyunVodPlayer.PlayerState playerState = mAliyunVodPlayer.getPlayerState();
        if (playerState == IAliyunVodPlayer.PlayerState.Started || mAliyunVodPlayer.isPlaying()) {
            mAliyunVodPlayer.pause();
        }
    }


    private void initControlView() {
        mControlView = new ControlView(getContext());
        addSubView(mControlView);

        mControlView.setOnBackClickListener(new ControlView.OnBackClickListener() {
            @Override
            public void onClick() {
                Context context = getContext();
                if (context instanceof Activity) {
                    ((Activity) context).finish();
                }
            }
        });
    }


    private void initSurfaceView() {
        mSurfaceView = new SurfaceView(getContext().getApplicationContext());
        addSubView(mSurfaceView);

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
        prepare();
        //设置准备回调
        mAliyunVodPlayer.setOnPreparedListener(new IAliyunVodPlayer.OnPreparedListener() {
            @Override
            public void onPrepared() {
                if (mAliyunVodPlayer == null) {
                    return;
                }
//
//                mAliyunMediaInfo = mAliyunVodPlayer.getMediaInfo();
//                if (mAliyunMediaInfo == null) {
//                    return;
//                }
//                //防止服务器信息和实际不一致
//                mAliyunMediaInfo.setDuration((int) mAliyunVodPlayer.getDuration());
//                //使用用户设置的标题
//                mAliyunMediaInfo.setTitle(getTitle(mAliyunMediaInfo.getTitle()));
//                mAliyunMediaInfo.setPostUrl(getPostUrl(mAliyunMediaInfo.getPostUrl()));
//                mControlView.setMediaInfo(mAliyunMediaInfo, mAliyunVodPlayer.getCurrentQuality());
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
//        mAliyunVodPlayer.setOnAutoPlayListener(new IAliyunVodPlayer.OnAutoPlayListener() {
//            @Override
//            public void onAutoPlayStarted() {
//                //自动播放开始,需要设置播放状态
//                if (mControlView != null) {
//                    mControlView.setPlayState(ControlView.PlayState.Playing);
//                }
//                if (mOutAutoPlayListener != null) {
//                    mOutAutoPlayListener.onAutoPlayStarted();
//                }
//            }
//        });
        //seek结束事件
//        mAliyunVodPlayer.setOnSeekCompleteListener(new IAliyunVodPlayer.OnSeekCompleteListener() {
//            @Override
//            public void onSeekComplete() {
//                inSeek = false;
//
//                if (mOuterSeekCompleteListener != null) {
//                    mOuterSeekCompleteListener.onSeekComplete();
//                }
//            }
//        });
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
//        mAliyunVodPlayer.setOnFirstFrameStartListener(new IAliyunVodPlayer.OnFirstFrameStartListener() {
//            @Override
//            public void onFirstFrameStart() {
//                //开始启动更新进度的定时器
//                startProgressUpdateTimer();
//
//                mCoverView.setVisibility(GONE);
//                if (mOutFirstFrameStartListener != null) {
//                    mOutFirstFrameStartListener.onFirstFrameStart();
//                }
//            }
//        });
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

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d("111", "onTouch");
        mAliyunVodPlayer.start();
        return true;
    }

    private void prepare() {

//        String url = getIntent().getStringExtra("url");
//        String url = "http://player.alicdn.com/video/aliyunmedia.mp4";
        String url = "http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4";
        AliyunLocalSource.AliyunLocalSourceBuilder asb = new AliyunLocalSource.AliyunLocalSourceBuilder();
        asb.setSource(url);
        //aliyunVodPlayer.setLocalSource(asb.build());
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


}
