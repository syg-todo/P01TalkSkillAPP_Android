package com.tuodanhuashu.app.home.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.tuodanhuashu.app.R;
import com.tuodanhuashu.app.home.bean.HomeAnswerBean;

import java.util.List;

public class HomeAnswerAdapter extends BaseAdapter {

    private List<HomeAnswerBean> homeAnswerBeanList;

    private Context context;

    public HomeAnswerAdapter(List<HomeAnswerBean> homeAnswerBeanList, Context context) {
        this.homeAnswerBeanList = homeAnswerBeanList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return homeAnswerBeanList.size();
    }

    @Override
    public HomeAnswerBean getItem(int position) {
        return homeAnswerBeanList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_home_answer_layout,parent,false);

        return v;
    }
}
