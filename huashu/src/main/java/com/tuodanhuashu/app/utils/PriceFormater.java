package com.tuodanhuashu.app.utils;

public class PriceFormater {

    public static String formatPrice(String activityPrice, String salePrice, String price) {
        String finalPrice;


        if (tranStringToInt(activityPrice) <= 0) {//无活动 走销售价
            if (tranStringToInt(salePrice) == 0) {//免费
                finalPrice = "免费";

            } else {//不免费
                finalPrice = String.valueOf(Math.min(tranStringToInt(salePrice),tranStringToInt(price)));
            }
        } else {//有活动 走活动价,且不免费

            finalPrice = String.valueOf(Math.min(tranStringToInt(salePrice),tranStringToInt(activityPrice)));
        }
        return finalPrice;
    }

    public static int tranStringToInt(String price){
        int result;
        if (!price.contains(".")){
            result = Integer.parseInt(price);
        }else {

            result = Integer.parseInt(price.substring(0,price.indexOf(".")));
        }
        return result;
    }
}
