package com.tuodanhuashu.app.home.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.tuodanhuashu.app.R;
import com.tuodanhuashu.app.home.bean.HomeTalkSkillClassBean;

import java.util.List;

public class HuaShuSubMenuAdapter extends BaseAdapter {

    private Context mContext;

    private LayoutInflater inflater;

    private List<HomeTalkSkillClassBean> data;

    public HuaShuSubMenuAdapter(Context mContext, List<HomeTalkSkillClassBean> data) {
        this.mContext = mContext;
        this.data = data;
        inflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = inflater.inflate(R.layout.gridview_item,null);
        TextView subTv = v.findViewById(R.id.tv2);
        subTv.setText(data.get(position).getCat_name());
        return v;
    }

    public void refreshGrid(List<HomeTalkSkillClassBean> data){
        this.data = data;
        notifyDataSetChanged();
    }
}
