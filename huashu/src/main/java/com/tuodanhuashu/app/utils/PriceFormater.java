package com.tuodanhuashu.app.utils;

public class PriceFormater {

    public static String formatPrice(String activityPrice, String salePrice, String price) {
        String finalPrice;


        if (Float.parseFloat(activityPrice) <= 0f) {//无活动 走销售价
            if (Float.parseFloat(salePrice) == 0f) {//免费
                finalPrice = "免费";

            } else {//不免费
                finalPrice = String.valueOf(Math.min(Float.parseFloat(salePrice),Float.parseFloat(price)));
            }
        } else {//有活动 走活动价,且不免费

            finalPrice = String.valueOf(Math.min(Float.parseFloat(salePrice),Float.parseFloat(activityPrice)));
        }
        return finalPrice;
    }
}
