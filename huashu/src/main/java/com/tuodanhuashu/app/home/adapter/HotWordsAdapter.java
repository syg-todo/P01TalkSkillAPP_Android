package com.tuodanhuashu.app.home.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.tuodanhuashu.app.R;
import com.tuodanhuashu.app.home.bean.HotKeywordsBean;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.List;

public class HotWordsAdapter extends TagAdapter<HotKeywordsBean> {

    private Context mContext;

    private LayoutInflater layoutInflater;

    private TagFlowLayout flowLayout;

    private List<HotKeywordsBean> datas;
    public HotWordsAdapter(Context mContext,List<HotKeywordsBean> datas) {
        super(datas);
        this.mContext = mContext;
        this.datas = datas;
        layoutInflater = LayoutInflater.from(this.mContext);
    }

    @Override
    public View getView(FlowLayout parent, int position, HotKeywordsBean keywordsBean) {
        FrameLayout frameLayout = (FrameLayout) layoutInflater.inflate(R.layout.item_hotwords_layout,
                parent, false);
        TextView tv = frameLayout.findViewById(R.id.item_home_hotwords_tv);
        tv.setText(keywordsBean.getName());
        return frameLayout;
    }


    public void refreshTagList(List<HotKeywordsBean> datas){
        this.datas = datas;
        notifyDataChanged();
    }

    public List<HotKeywordsBean> getDatas() {
        return datas;
    }

    public void setDatas(List<HotKeywordsBean> datas) {
        this.datas = datas;
    }
}
