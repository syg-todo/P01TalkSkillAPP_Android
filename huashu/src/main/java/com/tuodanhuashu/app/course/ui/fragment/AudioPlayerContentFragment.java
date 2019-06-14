package com.tuodanhuashu.app.course.ui.fragment;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.alibaba.android.vlayout.layout.LinearLayoutHelper;
import com.chad.library.adapter.base.BaseViewHolder;
import com.tuodanhuashu.app.R;
import com.tuodanhuashu.app.base.HuaShuBaseFragment;
import com.tuodanhuashu.app.base.SimpleItemDecoration;
import com.tuodanhuashu.app.course.bean.CommentBean;
import com.tuodanhuashu.app.course.bean.CourseDetailBean;
import com.tuodanhuashu.app.course.bean.SectionBean;
import com.tuodanhuashu.app.course.bean.SectionInfoModel;
import com.tuodanhuashu.app.course.ui.AudioPlayerActivity;
import com.tuodanhuashu.app.course.ui.CourseDetailActivity;
import com.tuodanhuashu.app.course.ui.adapter.CommentAdapter;
import com.tuodanhuashu.app.course.ui.adapter.SectionInfoAdapter;
import com.tuodanhuashu.app.home.adapter.HomeAdapter;
import com.tuodanhuashu.app.home.ui.HuaShuFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class AudioPlayerContentFragment extends HuaShuBaseFragment {
    private static final String TAG = AudioPlayerContentFragment.class.getSimpleName();
    @BindView(R.id.edit)
    TextView tvAudioComment;
    @BindView(R.id.tv_audio_play_send)
    TextView tvAudioPlaySend;
    @BindView(R.id.layout_send)
    LinearLayout layoutSend;
    @BindView(R.id.rv_play)
    RecyclerView recyclerView;


    public static final String EXTAR_SECTION_ID = "section_id";
    RecyclerView rvCourseTab;
    TextView tvAudioPlayShowAll;
    TextView tvAudioPlayTotalCourse;
    WebView webView;
    TextView tvPlayCourseName;
    private RecyclerView rvPlayComment;

    private DelegateAdapter delegateAdapter;
    private SectionInfoModel model;
    private String isPay;
    private boolean isPlaying = false;
    private String content;
    private String sectionIntro;
    private String sectionName;
    private String audioUrl;
    private String audioDutaion;
    private String currentSectionId;
    private String courseId;

    private CommentAdapter adapterComment;
    private SectionInfoAdapter adapterSectionInfo;
    private List<SectionBean.SectionInfo> sectionInfoList = new ArrayList<>();
    private List<CommentBean> commentsBeanList = new ArrayList<>();
    private List<DelegateAdapter.Adapter> adapterList;
    private static final int TYPE_TOP = 0;
    private static final int TYPE_COMMENT = 1;
    private static final int TYPE_TAB = 2;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_audio_player_content;
    }


    private void initPlayTop() {
        Log.d(TAG,"inirTop");
        HomeAdapter adapterTop = new HomeAdapter(mContext, new LinearLayoutHelper(), 1, TYPE_TOP, R.layout.play_top_layout) {
            @Override
            public void onBindViewHolder(BaseViewHolder holder, int position) {
                super.onBindViewHolder(holder, position);
                webView = holder.getView(R.id.webview_audio);
                rvCourseTab = holder.getView(R.id.rv_audio_play_tab);
                tvAudioPlayShowAll = holder.getView(R.id.tv_audio_play_show_all);
                tvAudioPlayTotalCourse = holder.getView(R.id.tv_audio_play_tab_total_course);
                String content = "<p><font color='red'>hello baidu!</font></p>";
                String htmll = "<html><header>" + sectionIntro + "</header></body></html>";

                webView.loadData(htmll, "text/html", "uft-8");
                Log.d(TAG,currentSectionId);
                initRvCourseTab(holder);
//                ivPlayShare = holder.getView(R.id.iv_play_share);
//                ivDownload = holder.getView(R.id.iv_play_download);

                tvPlayCourseName = holder.getView(R.id.tv_play_course_name);
                tvPlayCourseName.setText(sectionName);

                tvAudioPlayTotalCourse.setText("共" + sectionInfoList.size() + "个课时");
                tvAudioPlayShowAll.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(mContext, CourseDetailActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString(CourseDetailActivity.EXTRA_COURSE_ID,courseId);
                        intent.putExtras(bundle);
                        getActivity().startActivity(intent);
                    }
                });
            }
        };
        adapterList.add(adapterTop);

    }

    private void initComment() {
        final HomeAdapter homeAdapterComment = new HomeAdapter(mContext, new LinearLayoutHelper(), 1, TYPE_COMMENT, R.layout.play_comment_layout) {
            @Override
            public void onBindViewHolder(BaseViewHolder holder, int position) {
                super.onBindViewHolder(holder, position);
                rvPlayComment = holder.getView(R.id.rv_play_comment);
                adapterComment = new CommentAdapter(mContext, commentsBeanList);
                rvPlayComment.setAdapter(adapterComment);
                rvPlayComment.addItemDecoration(new SimpleItemDecoration());
                rvPlayComment.setLayoutManager(new LinearLayoutManager(mContext));
            }
        };
        adapterList.add(homeAdapterComment);
    }

    @Override
    protected void initView(View view) {
        super.initView(view);
        Log.d(TAG,"initView");
        model = ViewModelProviders.of(getActivity()).get(SectionInfoModel.class);

        SectionBean section = model.getSection().getValue();

        isPay = section.getIs_pay();
        sectionIntro = section.getSection_intro();
        sectionName = section.getSection_name();
        audioUrl = section.getUrl();
        audioDutaion = section.getDuration();
        sectionInfoList = section.getSection_list();
        commentsBeanList = section.getComments();
        courseId = section.getCourse_id();
        currentSectionId = getArguments().getString(EXTAR_SECTION_ID);


        adapterList = new ArrayList<>();
        VirtualLayoutManager layoutManager = new VirtualLayoutManager(mContext);
        recyclerView.setLayoutManager(layoutManager);
        RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
        recyclerView.setRecycledViewPool(viewPool);
        viewPool.setMaxRecycledViews(0, 10);
        delegateAdapter = new DelegateAdapter(layoutManager, true);
        recyclerView.setAdapter(delegateAdapter);

        tvAudioComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isLogin()) {
                    goToLogin();
                } else {
                    CommentDialogFragment dialogFragment = new CommentDialogFragment();
//                    dialogFragment.show(getFragmentManager(), "tag");
                }
            }
        });

        initPlayTop();
        initComment();
        delegateAdapter.setAdapters(adapterList);

    }

    @Override
    protected void initData() {
        super.initData();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    private void initRvCourseTab(BaseViewHolder holder) {
        CourseDetailBean.SectionsBean section = new CourseDetailBean.SectionsBean();
        section.setSection_name("发刊词:测试测试测试测试测试测试测试测试测试123456789");
        section.setDuration("26:39");
        adapterSectionInfo = new SectionInfoAdapter((AudioPlayerActivity) getActivity(), sectionInfoList,currentSectionId,courseId);
        rvCourseTab.setAdapter(adapterSectionInfo);
//        smoothMoveToPosition(rvCourseTab, 2);
//        rvCourseTab.smoothScrollToPosition(3);
    }

    public AudioPlayerContentFragment() {
    }


}
