package com.company.common.utils;

import java.util.Random;

public class RandomUntil {
    /**
     * 生成一个0 到 count 之间的随机数
     *
     * @param endNum
     * @return
     */
    public static int getNum(int endNum) {
        if (endNum > 0) {
            Random random = new Random();
            return random.nextInt(endNum);
        }
        return 0;
    }

    /**
     * 生成一个startNum 到 endNum之间的随机数(不包含endNum的随机数)
     *
     * @param startNum
     * @param endNum
     * @return
     */
    public static int getNum(int startNum, int endNum) {
        if (endNum > startNum) {
            Random random = new Random();
            return random.nextInt(endNum - startNum) + startNum;
        }
        return 0;
    }

    /**
     * 生成随机大写字母
     *
     * @return
     */
    public static String getLargeLetter() {
        Random random = new Random();
        return String.valueOf((char) (random.nextInt(27) + 'A'));
    }

    /**
     * 生成随机大写字母字符串
     *
     * @return
     */
    public static String getLargeLetter(int size) {
        StringBuffer buffer = new StringBuffer();
        Random random = new Random();
        for (int i = 0; i < size; i++) {
            buffer.append((char) (random.nextInt(27) + 'A'));
        }
        return buffer.toString();
    }

    /**
     * 生成随机小写字母
     *
     * @return
     */
    public static String getSmallLetter() {
        Random random = new Random();
        return String.valueOf((char) (random.nextInt(27) + 'a'));
    }

    /**
     * 生成随机小写字母字符串
     *
     * @return
     */
    public static String getSmallLetter(int size) {
        StringBuffer buffer = new StringBuffer();
        Random random = new Random();
        for (int i = 0; i < size; i++) {
            buffer.append((char) (random.nextInt(27) + 'a'));
        }
        return buffer.toString();
    }

    /**
     * 数字与小写字母混编字符串
     *
     * @param size
     * @return
     */
    public static String getNumSmallLetter(int size) {
        StringBuffer buffer = new StringBuffer();
        Random random = new Random();
        for (int i = 0; i < size; i++) {
            if (random.nextInt(2) % 2 == 0) {//字母
                buffer.append((char) (random.nextInt(27) + 'a'));
            } else {//数字
                buffer.append(random.nextInt(10));
            }
        }
        return buffer.toString();
    }

    /**
     * 数字与大写字母混编字符串
     *
     * @param size
     * @return
     */
    public static String getNumLargeLetter(int size) {
        StringBuffer buffer = new StringBuffer();
        Random random = new Random();
        for (int i = 0; i < size; i++) {
            if (random.nextInt(2) % 2 == 0) {//字母
                buffer.append((char) (random.nextInt(27) + 'A'));
            } else {//数字
                buffer.append(random.nextInt(10));
            }
        }
        return buffer.toString();
    }

    /**
     * 数字与大小写字母混编字符串
     *
     * @param size
     * @return
     */
    public static String getNumLargeSmallLetter(int size) {
        StringBuffer buffer = new StringBuffer();
        Random random = new Random();
        for (int i = 0; i < size; i++) {
            if (random.nextInt(2) % 2 == 0) {//字母
                if (random.nextInt(2) % 2 == 0) {
                    buffer.append((char) (random.nextInt(27) + 'A'));
                } else {
                    buffer.append((char) (random.nextInt(27) + 'a'));
                }
            } else {//数字
                buffer.append(random.nextInt(10));
            }
        }
        return buffer.toString();
    }

    public static int[] randomArr(int[] arr,int num) {
        Random rd = new Random();
        for (int i = 0; i < arr.length; i++) {
            int count = 0;
//            arr[i]=rd.nextInt(10)+1;//常规来说是这样.但是我想输出不重复的随机数
            int temp = rd.nextInt(num);//先将随机数赋值给一个中间变量
            //然后再遍历一遍数组,如果有相同的,就不算,并且i--重新随机.
            for (int j = 0; j < arr.length; j++) {
                if (arr[j] == temp) {
                    count++;//在外循环定义一个计数器,在内循环内判断是否重复.否则循环结束
                    //内循环中的变量不能用于外循环
                }
            }if (count>0){//内循环结束,在外循环判断是否计数器>0,如果是,就i--,重新随机.
                //否则赋值给数组
                i--;
            }else{
                arr[i] = temp;
            }
        }
        return arr;
    }

}

