package com.tuodanhuashu.app.home.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.tuodanhuashu.app.R;
import com.tuodanhuashu.app.home.bean.HomeBannerBean;
import com.ms.banner.holder.BannerViewHolder;


/**
 * author:yzy
 *
 * 2019-1-31
 */
public class HomeBannerViewHolder implements BannerViewHolder<HomeBannerBean> {

    private ImageView bannerItemIv;


    @Override
    public View createView(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_banner_layout, null);

        bannerItemIv = (ImageView) view.findViewById(R.id.banner_item_iv);
        return view;

    }

    @Override
    public void onBind(Context context, int position, HomeBannerBean data) {
        RoundedCorners roundedCorners= new RoundedCorners(10);
        RequestOptions options= RequestOptions.bitmapTransform(roundedCorners);
        Glide.with(context)
                .load(data.getImage_url())
//                .placeholder(R.drawable.ic_image_default)
                .apply(options)
                .into(bannerItemIv);
    }
}
