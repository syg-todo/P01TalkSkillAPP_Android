package com.tuodanhuashu.app.home.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutHelper;
import com.chad.library.adapter.base.BaseViewHolder;

public class HomeAdapter extends DelegateAdapter.Adapter<BaseViewHolder> {

    private Context mContext;

    private LayoutHelper layoutHelper;

    private int itemCount;

    private int itemType;

    private int layoutResId;

    public HomeAdapter(Context mContext, LayoutHelper layoutHelper, int itemCount, int itemType, int layoutResId) {
        this.mContext = mContext;
        this.layoutHelper = layoutHelper;
        this.itemCount = itemCount;
        this.itemType = itemType;
        this.layoutResId = layoutResId;
    }

    @Override
    public LayoutHelper onCreateLayoutHelper() {
        return layoutHelper;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == this.itemType) {
            return new BaseViewHolder(LayoutInflater.from(mContext).inflate(layoutResId, parent, false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return itemCount;
    }


    @Override
    public int getItemViewType(int position) {
        return itemType;
    }


}

