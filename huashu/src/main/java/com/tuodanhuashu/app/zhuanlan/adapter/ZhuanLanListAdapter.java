package com.tuodanhuashu.app.zhuanlan.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.Nullable;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.company.common.utils.DisplayUtil;
import com.company.common.utils.DrawableGetUtil;
import com.tuodanhuashu.app.R;
import com.tuodanhuashu.app.zhuanlan.bean.ArticleListItemBean;

import java.util.List;

public class ZhuanLanListAdapter extends BaseQuickAdapter<ArticleListItemBean, BaseViewHolder> {
    private Context context;
    private List<ArticleListItemBean> data;
    public ZhuanLanListAdapter(Context context,int layoutResId, @Nullable List<ArticleListItemBean> data) {
        super(layoutResId, data);
        this.context = context;
        this.data = data;
    }



    @Override
    protected void convert(BaseViewHolder helper, ArticleListItemBean item) {
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) helper.getView(R.id.item_zhuanlan_layout_container).getLayoutParams();
        int px = DisplayUtil.dip2px(context,10);
        FrameLayout container = helper.getView(R.id.item_zhuanlan_layout_container);
        GradientDrawable gd;
        if(helper.getLayoutPosition()==0){
            layoutParams.setMargins(px,px,px,0);
            gd = DrawableGetUtil.getNeedDrawable(new float[]{10.0F,10.0F,10.0F,10.0F,0.0F,0.0F,0.0F,0.0F}, Color.argb(255 , 255 , 255 ,255));

        }else if(helper.getLayoutPosition() >= getData().size()-1){
            layoutParams.setMargins(px,0,px,px);
            gd = DrawableGetUtil.getNeedDrawable(new float[]{0.0F,0.0F,0.0F,0.0F,10.0F,10.0F,10.0F,10.0F}, Color.argb(255 , 255 , 255 ,255));

        }else{
            layoutParams.setMargins(px,0,px,0);
            //container.setBackgroundColor(mContext.getResources().getColor(R.color.white));
            gd = DrawableGetUtil.getNeedDrawable(new float[]{0.0F,0.0F,0.0F,0.0F,0.0F,0.0F,0.0F,0.0F}, Color.argb(255 , 255 , 255 ,255));
        }
        DrawableGetUtil.setBackground(gd,container);
        ImageView iv = helper.getView(R.id.item_home_article_iv_1);
        TextView title = helper.getView(R.id.item_home_article_title_1);
        TextView desc = helper.getView(R.id.item_home_article_desc_1);
        TextView sourceTv = helper.getView(R.id.item_home_article_source_tv);
        sourceTv.setText(item.getSource());
        Glide.with(context).load(item.getImage_url()).into(iv);
        title.setText(item.getTitle());
        desc.setText(item.getDescription());
    }
}
