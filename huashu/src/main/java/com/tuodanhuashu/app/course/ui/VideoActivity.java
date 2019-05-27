package com.tuodanhuashu.app.course.ui;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;

import com.company.common.utils.DisplayUtil;
import com.tuodanhuashu.app.R;
import com.tuodanhuashu.app.base.HuaShuBaseActivity;
import com.tuodanhuashu.app.base.SimpleItemDecoration;
import com.tuodanhuashu.app.course.bean.VideoBean;
import com.tuodanhuashu.app.course.ui.adapter.VideoAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class VideoActivity extends HuaShuBaseActivity {

    @BindView(R.id.rv_video)
    RecyclerView rvVideo;


    private List<VideoBean> videoBeanList = new ArrayList<>();


    private VideoAdapter adapter;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_video;
    }


    @Override
    protected void initData() {
        super.initData();

        VideoBean videoBean = new VideoBean("风浪科技", "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1558757671584&di=c5b61404bf83891a662a031437f5335f&imgtype=0&src=http%3A%2F%2Fb-ssl.duitang.com%2Fuploads%2Fitem%2F201806%2F11%2F20180611091852_Gt2ik.jpeg",
                "很皮很优神", 10000, "http://img1.imgtn.bdimg.com/it/u=3049673303,4028148324&fm=26&gp=0.jpg");


        VideoBean videoBean1 = new VideoBean("风浪科技", "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1558757671584&di=c5b61404bf83891a662a031437f5335f&imgtype=0&src=http%3A%2F%2Fb-ssl.duitang.com%2Fuploads%2Fitem%2F201806%2F11%2F20180611091852_Gt2ik.jpeg",
                "很皮很优秀的小套路，让你撩到女神", 10000, "http://img1.imgtn.bdimg.com/it/u=3049673303,4028148324&fm=26&gp=0.jpg");
        VideoBean videoBean2 = new VideoBean("风浪科技", "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1558757671584&di=c5b61404bf83891a662a031437f5335f&imgtype=0&src=http%3A%2F%2Fb-ssl.duitang.com%2Fuploads%2Fitem%2F201806%2F11%2F20180611091852_Gt2ik.jpeg",
                "很皮很优秀的小套路，让你撩到优秀的小套皮神", 10000, "http://img1.imgtn.bdimg.com/it/u=3049673303,4028148324&fm=26&gp=0.jpg");
        VideoBean videoBean3 = new VideoBean("风浪科技", "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1558757671584&di=c5b61404bf83891a662a031437f5335f&imgtype=0&src=http%3A%2F%2Fb-ssl.duitang.com%2Fuploads%2Fitem%2F201806%2F11%2F20180611091852_Gt2ik.jpeg",
                "很皮很优秀的小套路，让你撩到女很皮很优秀的小套路神", 10000, "http://img1.imgtn.bdimg.com/it/u=3049673303,4028148324&fm=26&gp=0.jpg");
        VideoBean videoBean4 = new VideoBean("风浪科技", "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1558757671584&di=c5b61404bf83891a662a031437f5335f&imgtype=0&src=http%3A%2F%2Fb-ssl.duitang.com%2Fuploads%2Fitem%2F201806%2F11%2F20180611091852_Gt2ik.jpeg",
                "很皮很很优秀的女神", 10000, "http://img1.imgtn.bdimg.com/it/u=3049673303,4028148324&fm=26&gp=0.jpg");
        VideoBean videoBean5 = new VideoBean("风浪科技", "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1558757671584&di=c5b61404bf83891a662a031437f5335f&imgtype=0&src=http%3A%2F%2Fb-ssl.duitang.com%2Fuploads%2Fitem%2F201806%2F11%2F20180611091852_Gt2ik.jpeg",
                "很皮很优秀的小套路，让你撩到很皮很优秀的小套路很皮很优秀的小套路女神", 10000, "http://img1.imgtn.bdimg.com/it/u=3049673303,4028148324&fm=26&gp=0.jpg");
        VideoBean videoBean6 = new VideoBean("风浪科技",
                "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1558757671584&di=c5b61404bf83891a662a031437f5335f&imgtype=0&src=http%3A%2F%2Fb-ssl.duitang.com%2Fuploads%2Fitem%2F201806%2F11%2F20180611091852_Gt2ik.jpeg",
                "很皮很优秀的小套路，让你撩到女很皮很优秀的小套路很皮很优秀的小套路很皮很优秀的很皮很优秀的小套路很皮很优秀的小套路很皮很优秀的很皮很优秀的小套路很皮很优秀的小套路很皮很优秀的很皮很优秀的小套路很皮很优秀的小套路很皮很优秀的小套路很皮很优秀的小套路神",
                10000, "http://img1.imgtn.bdimg.com/it/u=3049673303,4028148324&fm=26&gp=0.jpg");


        videoBeanList.add(videoBean);
        videoBeanList.add(videoBean1);
        videoBeanList.add(videoBean2);
        videoBeanList.add(videoBean3);
        videoBeanList.add(videoBean4);
        videoBeanList.add(videoBean5);
        videoBeanList.add(videoBean6);
    }

    @Override
    protected void initView() {
        super.initView();

        adapter = new VideoAdapter(mContext, videoBeanList);
        rvVideo.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        rvVideo.setAdapter(adapter);
        rvVideo.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);

                int childCount = parent.getChildCount();
                for (int i = 0; i < childCount; i++) {
                    if (i / 2 == 0) {
                        Log.d("111","i:"+i);
                        outRect.set(0, 0, DisplayUtil.dp2px(10), 0);
                    }
                }
            }
        });

    }
}
