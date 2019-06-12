package com.tuodanhuashu.app.course.ui;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.os.Bundle;
import android.os.IBinder;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.alibaba.android.vlayout.layout.LinearLayoutHelper;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseViewHolder;
import com.company.common.CommonConstants;
import com.company.common.utils.PreferencesUtils;
import com.company.common.utils.StatusBarUtil;
import com.tuodanhuashu.app.Constants.Constants;
import com.tuodanhuashu.app.R;
import com.tuodanhuashu.app.base.HuaShuBaseActivity;
import com.tuodanhuashu.app.base.SimpleItemDecoration;
import com.tuodanhuashu.app.course.AudioPlayService;
import com.tuodanhuashu.app.course.bean.CommentBean;
import com.tuodanhuashu.app.course.bean.CourseDetailBean;
import com.tuodanhuashu.app.course.bean.SectionBean;
import com.tuodanhuashu.app.course.presenter.AudioPlayPresenter;
import com.tuodanhuashu.app.course.ui.adapter.CommentAdapter;
import com.tuodanhuashu.app.course.ui.adapter.SectionInfoAdapter;
import com.tuodanhuashu.app.course.ui.fragment.CommentDialogFragment;
import com.tuodanhuashu.app.course.view.AudioPlayView;
import com.tuodanhuashu.app.eventbus.EventMessage;
import com.tuodanhuashu.app.home.adapter.HomeAdapter;
import com.tuodanhuashu.app.utils.TimeFormater;
import com.tuodanhuashu.app.widget.player.VideoPlayerView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

public class AudioPlayerActivity extends HuaShuBaseActivity implements AudioPlayView, View.OnClickListener {


    private static final String TAG = AudioPlayerActivity.class.getSimpleName();
    private static final int TYPE_TOP = 0;
    private static final int TYPE_COMMENT = 1;
    private static final int TYPE_TAB = 2;
    @BindView(R.id.rv_play)
    RecyclerView recyclerView;
    @BindView(R.id.iv_play_image)
    ImageView ivAudioPlayImage;
    @BindView(R.id.edit)
    TextView tvAudioComment;
    @BindView(R.id.tv_audio_play_send)
    TextView tvAudioPlaySend;
    @BindView(R.id.layout_send)
    LinearLayout layoutSend;
    @BindView(R.id.iv_audio_play_back)
    ImageView ivAudioPlayBack;
    @BindView(R.id.iv_audio_play_download)
    ImageView ivAudioPlayDownload;
    @BindView(R.id.iv_audio_play_share)
    ImageView ivAudioPlayShare;
    @BindView(R.id.fl_audio_play_controller)
    FrameLayout flAudioPlayController;
    @BindView(R.id.tv_audio_play_current)
    TextView tvAudioPlayCurrent;
    @BindView(R.id.tv_audio_play_duration)
    TextView tvAudioPlayDuration;
    @BindView(R.id.iv_audio_play_play)
    ImageView ivAudioPlayPlay;
    @BindView(R.id.iv_audio_play_pause)
    ImageView ivAudioPlayPause;
    @BindView(R.id.seekbar_audio_play)
    SeekBar seekBar;


    RecyclerView rvCourseTab;
    TextView tvAudioPlayShowAll;
    TextView tvAudioPlayTotalCourse;


    VideoPlayerView playerView;


    WebView webView;

    TextView tvPlayCourseName;

    private String isPay;
    private boolean isPlaying = false;

    private String content;
    private String html;
    private String sectionName;
    private String bannerUrl;
    private String audioUrl;
    private String audioDutaion;
    //    private MediaPlayer mediaPlayer;
//    private MyConnection coon;
    private AudioPlayService.AudioBinder audioController;


    private DelegateAdapter delegateAdapter;

    private List<DelegateAdapter.Adapter> adapterList;

    private List<CommentBean> commentsBeanList = new ArrayList<>();
    private List<SectionBean.SectionInfo> sectionInfoList = new ArrayList<>();
    private AudioPlayPresenter audioPlayPresenter;

    private CommentAdapter adapter;
    SectionInfoAdapter adapterSectionInfo;
    private RecyclerView rvPlayComment;
    public static final String EXTRA_SECTION_ID = "section_id";

    public static final String EXTAR_COURSE_ID = "course_id";


    private String section_id = "";
    private String access_token = "";

    private String course_id = "";
    private final int UPDATE_PROGRESS = 1;

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
    protected void initData() {
        super.initData();
//        isPay =




        access_token = PreferencesUtils.getString(mContext, CommonConstants.KEY_TOKEN, "0");
        audioPlayPresenter = new AudioPlayPresenter(mContext, this);
        Log.d(TAG, section_id);
        audioPlayPresenter.requestAudioSectionInfo(access_token, section_id);
    }

    @Override
    protected void initView() {
        super.initView();

        adapterList = new ArrayList<>();



        VirtualLayoutManager layoutManager = new VirtualLayoutManager(mContext);
        recyclerView.setLayoutManager(layoutManager);
        RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
        recyclerView.setRecycledViewPool(viewPool);
        viewPool.setMaxRecycledViews(0, 10);
        delegateAdapter = new DelegateAdapter(layoutManager, true);
        recyclerView.setAdapter(delegateAdapter);

        ivAudioPlayDownload.setOnClickListener(this);
        ivAudioPlayShare.setOnClickListener(this);
        ivAudioPlayBack.setOnClickListener(this);
        flAudioPlayController.setOnClickListener(this);

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

        tvAudioComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isLogin()) {
                    goToLogin();
                } else {
                    CommentDialogFragment dialogFragment = new CommentDialogFragment();
                    dialogFragment.show(getFragmentManager(), "tag");
                }
            }
        });
        Intent intent = new Intent(AudioPlayerActivity.this, AudioPlayService.class);
//        Bundle bundle = new Bundle();
//        bundle.putString(AudioPlayService.EXTAR_AUDIO_URL,audioUrl);
//        intent.putExtras(bundle);
        bindService(intent, coon, BIND_AUTO_CREATE);

//        initPlayTop();
//        initTab();
//        initComment();
        delegateAdapter.setAdapters(adapterList);

//        Map<String, String> params = new HashMap<>();
//        params.put(AudioPlayService.EXTAR_AUDIO_URL,audioUrl);
//        EventBus.getDefault().post(new EventMessage<Map>(Constants.EVENT_TAG.TAG_PLAYER_AUDIO_URL, params));

    }

    private void play() {
        mBinder.play();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        StatusBarUtil.setTranslucentStatus(this);
        StatusBarUtil.setStatusBarColor(this, Color.TRANSPARENT);

//        ActionBar actionBar = getSupportActionBar();
//        if (actionBar != null) {
//            actionBar.hide();
//        }
    }

    private void initPlayTop() {
        HomeAdapter adapterTop = new HomeAdapter(mContext, new LinearLayoutHelper(), 1, TYPE_TOP, R.layout.play_top_layout) {
            @Override
            public void onBindViewHolder(BaseViewHolder holder, int position) {
                super.onBindViewHolder(holder, position);
                webView = holder.getView(R.id.webview_audio);
                rvCourseTab = holder.getView(R.id.rv_audio_play_tab);
                tvAudioPlayShowAll = holder.getView(R.id.tv_audio_play_show_all);
                tvAudioPlayTotalCourse = holder.getView(R.id.tv_audio_play_tab_total_course);
                String content = "<p><font color='red'>hello baidu!</font></p>";
                String htmll = "<html><header>" + html + "</header></body></html>";

                webView.loadData(htmll, "text/html", "uft-8");

                initRvCourseTab(holder);
//                ivPlayShare = holder.getView(R.id.iv_play_share);
//                ivDownload = holder.getView(R.id.iv_play_download);

                tvPlayCourseName = holder.getView(R.id.tv_play_course_name);
                tvPlayCourseName.setText(sectionName);

                tvAudioPlayTotalCourse.setText("共"+sectionInfoList.size()+"个课时");
                tvAudioPlayShowAll.setOnClickListener(AudioPlayerActivity.this);
//                ivDownload.getDrawable().setTint(getResources().getColor(R.color.white));
//                ivPlayShare.getDrawable().setTint(getResources().getColor(R.color.white));
//                Intent intent = new Intent(mContext, AudioPlayService.class);
//                coon = new MyConnection();
//                startService(intent);
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

    private void initRvCourseTab(BaseViewHolder holder) {


        CourseDetailBean.SectionsBean section = new CourseDetailBean.SectionsBean();
        section.setSection_name("发刊词:测试测试测试测试测试测试测试测试测试123456789");
        section.setDuration("26:39");


//       adapterSectionInfo = new SectionInfoAdapter(mContext, sectionInfoList);
        rvCourseTab.setAdapter(adapterSectionInfo);
        smoothMoveToPosition(rvCourseTab, 2);
//        rvCourseTab.smoothScrollToPosition(3);
    }

    private int mToPosition;
    private boolean mShouldScroll;

    private void smoothMoveToPosition(RecyclerView mRecyclerView, final int position) {
        // 第一个可见位置
        int firstItem = mRecyclerView.getChildLayoutPosition(mRecyclerView.getChildAt(0));
        // 最后一个可见位置
        int lastItem = mRecyclerView.getChildLayoutPosition(mRecyclerView.getChildAt(mRecyclerView.getChildCount() - 1));
        if (position < firstItem) {
            // 第一种可能:跳转位置在第一个可见位置之前，使用smoothScrollToPosition
            mRecyclerView.smoothScrollToPosition(position);
        } else if (position <= lastItem) {
            // 第二种可能:跳转位置在第一个可见位置之后，最后一个可见项之前
            int movePosition = position - firstItem;
            if (movePosition >= 0 && movePosition < mRecyclerView.getChildCount()) {
                int top = mRecyclerView.getChildAt(movePosition).getTop();
                // smoothScrollToPosition 不会有效果，此时调用smoothScrollBy来滑动到指定位置
                mRecyclerView.smoothScrollBy(0, top);
            }
        } else {
            // 第三种可能:跳转位置在最后可见项之后，则先调用smoothScrollToPosition将要跳转的位置滚动到可见位置
            // 再通过onScrollStateChanged控制再次调用smoothMoveToPosition，执行上一个判断中的方法
            mRecyclerView.smoothScrollToPosition(position);
            mToPosition = position;
            mShouldScroll = true;
        }
    }


    private void initComment() {
        HomeAdapter adapterComment = new HomeAdapter(mContext, new LinearLayoutHelper(), 1, TYPE_COMMENT, R.layout.play_comment_layout) {
            @Override
            public void onBindViewHolder(BaseViewHolder holder, int position) {
                super.onBindViewHolder(holder, position);
                rvPlayComment = holder.getView(R.id.rv_play_comment);
                adapter = new CommentAdapter(mContext, commentsBeanList);
                rvPlayComment.setAdapter(adapter);
                rvPlayComment.addItemDecoration(new SimpleItemDecoration());
                rvPlayComment.setLayoutManager(new LinearLayoutManager(mContext));
            }
        };
        adapterList.add(adapterComment);
    }

    @Override
    protected void getBundleExtras(Bundle extras) {
        super.getBundleExtras(extras);
        section_id = extras.getString(EXTRA_SECTION_ID);
        course_id = extras.getString(EXTAR_COURSE_ID);
    }



    @Override
    public void getSectionSuccess(SectionBean section) {
        Log.d(TAG, section.toString());

        commentsBeanList = section.getComments();
        sectionInfoList = section.getSection_list();
        for (CommentBean comment : commentsBeanList
        ) {
            Log.d(TAG, comment.getContent());
        }
        Log.d(TAG, commentsBeanList.toString());
        html = section.getSection_intro();
        sectionName = section.getSection_name();
        bannerUrl = section.getBanner();
        audioUrl = section.getUrl();
        audioDutaion = section.getDuration();


        Map<String, String> params = new HashMap<>();
        params.put("audio_url", audioUrl);

        EventBus.getDefault().post(new EventMessage<Map>(Constants.EVENT_TAG.TAG_PLAYER_AUDIO_URL, params));

        Glide.with(mContext).load(bannerUrl).into(ivAudioPlayImage);

        tvAudioPlayDuration.setText("/"+TimeFormater.formatMs(Long.parseLong(audioDutaion)*1000));
        seekBar.setMax(Integer.parseInt(audioDutaion)*1000);
        initPlayTop();
        initComment();
//        adapterSectionInfo.notifyDataSetChanged();
        Collections.reverse(sectionInfoList);
        adapterSectionInfo = new SectionInfoAdapter(AudioPlayerActivity.this, sectionInfoList, section_id,course_id);
        delegateAdapter.setAdapters(adapterList);
    }

    @Override
    public void getSectionFail(String msg) {
        Log.d(TAG, msg);
        sectionName = msg;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_audio_play_back:
                onBackPressed();
                break;
            case R.id.iv_audio_play_share:
                share();
                break;
            case R.id.iv_audio_play_download:
                download();
                break;
            case R.id.fl_audio_play_controller:
                control();
                break;
            case R.id.tv_audio_play_show_all:
                showAll();
        }
    }

    private void showAll() {
        Bundle bundle = new Bundle();
        bundle.putString(CourseDetailActivity.EXTRA_COURSE_ID,course_id);
        readyGo(CourseDetailActivity.class,bundle);
    }

    @Override
    public void onEvent(EventMessage message) {
        super.onEvent(message);
        switch (message.getTag()) {
            case Constants.EVENT_TAG.TAG_PLAYER_CURRENT:
                String current = (String) ((HashMap) message.getData()).get("current");
                tvAudioPlayCurrent.setText(current);
                long currentLong = Long.parseLong((String) ((HashMap) message.getData()).get("current_long"));
                Log.d(TAG, "current:" + currentLong);
                seekBar.setProgress((int) currentLong);
                break;
//            case Constants.EVENT_TAG.TAG_PLAYER_DURATION:
//                String duraion = (String) ((HashMap) message.getData()).get("duration");
//                tvAudioPlayDuration.setText("/" + duraion);
//                long durationLong = Long.parseLong((String) ((HashMap) message.getData()).get("duration_long"));
//                Log.d(TAG, "duration:" + durationLong);
//                seekBar.setMax((int) durationLong);
//                break;
        }
    }

    private void control() {
        mBinder.play();
        changeControl();
    }

    private void changeControl() {
        if (isPlaying()) {
            ivAudioPlayPlay.setVisibility(View.INVISIBLE);
            ivAudioPlayPause.setVisibility(View.VISIBLE);
        } else {
            ivAudioPlayPlay.setVisibility(View.VISIBLE);
            ivAudioPlayPause.setVisibility(View.INVISIBLE);
        }
    }

    private void download() {
    }

    private void share() {
    }

    private boolean isPlaying() {
        return mBinder.isPlaying();
    }
}
