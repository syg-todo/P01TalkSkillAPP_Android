package com.company.common.utils;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import java.util.HashMap;
import java.util.Map;

public class DisplayUtil {
    private DisplayUtil() {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    /**
     * 将px值转换为dip或dp值，保证尺寸大小不变
     * DisplayMetrics类中属性density
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 将dip或dp值转换为px值，保证尺寸大小不变
     * DisplayMetrics类中属性density
     */
    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }
    public static int dp2px(float dipValue) {
        final float scale = Resources.getSystem().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }
    /**
     * 将px值转换为sp值，保证文字大小不变
     * DisplayMetrics类中属性scaledDensity
     */
    public static int px2sp(Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     * DisplayMetrics类中属性scaledDensity
     */
    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    public static Map<String,Integer> getScreenRelatedInformation(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Map<String,Integer> map = new HashMap<>();
        if (windowManager != null) {
            DisplayMetrics outMetrics = new DisplayMetrics();
            windowManager.getDefaultDisplay().getMetrics(outMetrics);
            int widthPixels = outMetrics.widthPixels;
            int heightPixels = outMetrics.heightPixels;
            int densityDpi = outMetrics.densityDpi;
            float density = outMetrics.density;
            float scaledDensity = outMetrics.scaledDensity;
            map.put("width",widthPixels);
            map.put("height",heightPixels);
            //可用显示大小的绝对宽度（以像素为单位）。
            //可用显示大小的绝对高度（以像素为单位）。
            //屏幕密度表示为每英寸点数。
            //显示器的逻辑密度。
            //显示屏上显示的字体缩放系数。

        }
        return map;
    }

    public static Map<String,Integer> getRealScreenRelatedInformation(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Map<String,Integer> map = new HashMap<>();
        if (windowManager != null) {
            DisplayMetrics outMetrics = new DisplayMetrics();
            windowManager.getDefaultDisplay().getRealMetrics(outMetrics);
            int widthPixels = outMetrics.widthPixels;
            int heightPixels = outMetrics.heightPixels;
            int densityDpi = outMetrics.densityDpi;
            float density = outMetrics.density;
            float scaledDensity = outMetrics.scaledDensity;
            //可用显示大小的绝对宽度（以像素为单位）。
            //可用显示大小的绝对高度（以像素为单位）。
            //屏幕密度表示为每英寸点数。
            //显示器的逻辑密度。
            //显示屏上显示的字体缩放系数。
            map.put("width",widthPixels);
            map.put("height",heightPixels);
        }
        return map;
    }

}
