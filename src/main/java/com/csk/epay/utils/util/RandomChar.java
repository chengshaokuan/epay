package com.csk.epay.utils.util;

import java.io.UnsupportedEncodingException;
import java.util.Random;

/**
 * @program: cheng
 * @description: 生成随机汉字
 * @author: Mr.Cheng
 * @create: 2019-01-14 15:15
 **/
public class RandomChar {

    /**
     * @Description: 随机生成0-10个汉字
     * @param:
     * @return: java.lang.String
     * @Author: Mr.Cheng
     * @Date: 11:01 2019/1/15
     */
    public static String getRandomChar (int i) {
        String result = "";
        Random random = new Random();
        int r = random.nextInt(i);
        for (int j = 0; j < r; j++) {
            result += randomChar();
        }
        return result;
    }

    /**
     * @Description: 随机生成常见汉字
     * @param:
     * @return: java.lang.String
     * @Author: Mr.Cheng
     * @Date: 11:01 2019/1/15
     */
    public static String randomChar () {
        String str = "";
        int highCode;
        int lowCode;
        Random random = new Random();
        highCode = (176 + Math.abs(random.nextInt(39)));
//     B0 + 0~39(16~55) 一级汉字所占区
        lowCode = (161 + Math.abs(random.nextInt(93)));
//     A1 + 0~93 每区有94个汉字
        byte[] b = new byte[2];
        b[0] = (Integer.valueOf(highCode)).byteValue();
        b[1] = (Integer.valueOf(lowCode)).byteValue();
        try {
            str = new String(b, "GBK");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return str;
    }
}
