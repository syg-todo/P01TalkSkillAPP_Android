package com.tuodanhuashu.app.widget.player.view.control;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.tuodanhuashu.app.R;

public class ControlView extends RelativeLayout {

    private ImageView ivTitleBack;
    private ImageView ivLike;
    //标题返回按钮监听
    private OnBackClickListener mOnBackClickListener;


    private OnStarClickListener mOnStarClickListener;

    public ControlView(Context context) {
        super(context);
        init();
    }

    public ControlView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ControlView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.video_player_control, this, true);

        findAllViews(); //找到所有的view

        setViewListener(); //设置view的监听事件
//
//        updateAllViews(); //更新view的显示
    }

    private void findAllViews() {
        ivTitleBack = findViewById(R.id.iv_player_title_back);

        ivLike = findViewById(R.id.iv_player_like);


    }

    private void setViewListener() {
        ivTitleBack.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnBackClickListener != null) {
                    mOnBackClickListener.onClick();
                }
            }
        });

        ivLike.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnStarClickListener != null){
                    mOnStarClickListener.onClick();
                }
            }
        });
    }


    public void setOnBackClickListener(OnBackClickListener l) {
        mOnBackClickListener = l;
    }


    public void setOnStarClickListener(OnStarClickListener l){ mOnStarClickListener = l;}
    public interface OnBackClickListener {
        /**
         * 返回按钮点击事件
         */
        void onClick();
    }

    public interface OnStarClickListener{

        void onClick();
    }
}
