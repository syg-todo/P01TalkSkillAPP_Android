package com.company.common.utils;

import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.view.View;

public class DrawableGetUtil {
    /**
     *
     * @param radius 四个角的半径
     * @param colors 渐变的颜色
     * @return
     */
    public static GradientDrawable getNeedDrawable(float []radius,int []colors) {
        //TODO:判断版本是否大于16  项目中默认的都是Linear散射 都是从左到右 都是只有开始颜色和结束颜色
        GradientDrawable drawable;
        if(android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
        {
            drawable=new GradientDrawable();
            drawable.setOrientation(GradientDrawable.Orientation.LEFT_RIGHT);
            drawable.setColors(colors);
        }else{
            drawable = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, colors);
        }

        drawable.setCornerRadii(radius);
        drawable.setGradientType(GradientDrawable.LINEAR_GRADIENT);
        return drawable;
    }

    /**
     *
     * @param radius 四个角的半径
     * @param bgColor 背景颜色
     * @param strokeWidth 边框宽度
     * @param strokeColor 边框颜色
     * @return
     */
    public static GradientDrawable getNeedDrawable(float []radius, int bgColor,int strokeWidth, int strokeColor) {
        GradientDrawable drawable = new GradientDrawable();
        drawable.setCornerRadii(radius);
        drawable.setStroke(strokeWidth, strokeColor);
        drawable.setColor(bgColor);
        return drawable;
    }

    /**
     *
     * @param radis 四个角的半径
     * @param bgColor 背景颜色
     * @return
     */
    public static GradientDrawable getNeedDrawable(float []radis, int bgColor)
    {
        GradientDrawable drawable=new GradientDrawable();
        drawable.setCornerRadii(radis);
        drawable.setColor(bgColor);
        return drawable;
    }


    /**
     *
     * @param drawable 生成的背景
     * @param view 需要添加背景的View
     */
    public static void setBackground(GradientDrawable drawable,View view)
    {
        //判断当前版本号，版本号大于等于16，使用setBackground；版本号小于16，使用setBackgroundDrawable。
        if(android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
        {
            view.setBackground(drawable);
        }else{
            view.setBackgroundDrawable(drawable);
        }
    }

}
