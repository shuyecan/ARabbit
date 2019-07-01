package com.arabbit.utils;

import java.util.Random;

/**
 * Created by net8 on 2017/5/26.
 */
public class RandomUtils {
    //生成随机数字和字母,
    public static String getStringRandom(int length) {
        String val = "";
        Random random = new Random();
        //参数length，表示生成几位随机数
        for (int i = 0; i < length; i++) {
            String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num";
            //输出字母还是数字
            if ("char".equalsIgnoreCase(charOrNum)) {
                //输出是大写字母还是小写字母
                int temp = random.nextInt(2) % 2 == 0 ? 65 : 97;
                val += (char) (random.nextInt(26) + temp);
            } else if ("num".equalsIgnoreCase(charOrNum)) {
                val += String.valueOf(random.nextInt(10));
            }
        }
        return val;
    }


    /**
     * 随机密码生成(大写字母加数字 )
     */
    public static String makeRandomPassword(int len) {
        char charr[] = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890".toCharArray();
        //System.out.println("字符数组长度:" + charr.length); //可以看到调用此方法多少次
        StringBuilder sb = new StringBuilder();
        Random r = new Random();

        for (int x = 0; x < len; ++x) {
            sb.append(charr[r.nextInt(charr.length)]);
        }
        return sb.toString();
    }

}
