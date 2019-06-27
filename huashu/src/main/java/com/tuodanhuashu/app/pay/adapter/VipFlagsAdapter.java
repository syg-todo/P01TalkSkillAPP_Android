package com.tuodanhuashu.app.pay.adapter;

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

public class VipFlagsAdapter extends TagAdapter<String> {

    private Context mContext;

    private LayoutInflater layoutInflater;

    private TagFlowLayout flowLayout;

    private List<String> datas;

    public  VipFlagsAdapter(Context mContext, List<String> datas) {
        super(datas);
        this.mContext = mContext;
        this.datas = datas;
        layoutInflater = LayoutInflater.from(this.mContext);
    }

    @Override
    public View getView(FlowLayout parent, int position, String s) {
        FrameLayout frameLayout = (FrameLayout) layoutInflater.inflate(R.layout.item_hotwords_layout,
                parent, false);
        TextView tv = frameLayout.findViewById(R.id.item_home_hotwords_tv);
        tv.setText(s);
        return frameLayout;
    }
}
