package com.csk.epay.utils.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * @program: 数学计算工具类
 * @description: 提供常用的数值加减乘除计算 , 及多个数值的求和 , 平均值 , 最大最小值计算
 * @author: Mr.Cheng
 * @create: 2018-08-14 15:12
 **/
public class MathUtil {

    /**
     * 默认的除法精确度
     */
    private static final int DEF_DIV_SCALE = 2;
    private static final Double MONEY_RANGE = 0.01;

    /**
     * @Description: 精确加法运算
     * @param: v1 被加数
     * @param: v2 加数
     * @return: java.math.BigDecimal 两个参数的和
     * @Author: Mr.Cheng
     * @Date: 15:27 2018/8/14
     */
    public static BigDecimal add (BigDecimal v1, BigDecimal v2) {
        if (null == v1) {
            v1 = BigDecimal.ZERO;
        }
        if (null == v2) {
            v2 = BigDecimal.ZERO;
        }
        return v1.add(v2);
    }

    /**
     * @Description: 精确减法运算
     * @param: v1 被减数
     * @param: v2 减数
     * @return: java.math.BigDecimal 两个参数的差
     * @Author: Mr.Cheng
     * @Date: 15:27 2018/8/14
     */
    public static BigDecimal subtract (BigDecimal v1, BigDecimal v2) {
        if (null == v1) {
            v1 = BigDecimal.ZERO;
        }
        if (null == v2) {
            v2 = BigDecimal.ZERO;
        }
        return v1.subtract(v2);
    }

    /**
     * @Description: 精确乘法运算
     * @param: v1 被乘数
     * @param: v2 乘数
     * @return: java.math.BigDecimal 两个参数的积
     * @Author: Mr.Cheng
     * @Date: 15:28 2018/8/14
     */
    public static BigDecimal multiply (BigDecimal v1, BigDecimal v2) {
        if (null == v1) {
            v1 = BigDecimal.ONE;
        }
        if (null == v2) {
            v2 = BigDecimal.ONE;
        }
        return v1.multiply(v2);
    }

    /**
     * @Description: (相对)精确除法运算 , 当发生除不尽情况时 , 精确到 小数点以后2位 , 以后数字四舍五入
     * @param: v1 被除数
     * @param: v2 除数
     * @return: java.math.BigDecimal 两个参数的商
     * @Author: Mr.Cheng
     * @Date: 15:31 2018/8/14
     */
    public static BigDecimal divide (BigDecimal v1, BigDecimal v2) {
        return v1.divide(v2, DEF_DIV_SCALE, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * @Description: (相对)精确除法运算 . 当发生除不尽情况时 , 由scale参数指 定精度 , 以后数字四舍五入
     * @param: v1 被除数
     * @param: v2 除数
     * @param: scale 表示表示需要精确到小数点以后几位
     * @return: java.math.BigDecimal 两个参数的商
     * @Author: Mr.Cheng
     * @Date: 15:33 2018/8/14
     */
    public static BigDecimal divide (BigDecimal v1, BigDecimal v2, Integer scale) {
        if (null == v1) {
            return BigDecimal.ZERO;
        }
        if (null == v2) {
            v2 = BigDecimal.ONE;
        }

        if (v2.compareTo(BigDecimal.ZERO) == 0) {
            throw new IllegalArgumentException("除数不能为0");
        }

        if (scale < 0) {
            throw new IllegalArgumentException("精确度不能小于0");
        }

        return v1.divide(v2, scale, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * @Description: 精确加法运算
     * @param: v1 被加数
     * @param: v2 加数
     * @return: java.lang.String 两个参数的和
     * @Author: Mr.Cheng
     * @Date: 15:36 2018/8/14
     */
    public static String add (String v1, String v2) {
        if (isBlank(v1)) {
            v1 = "0";
        }
        if (isBlank(v2)) {
            v2 = "0";
        }
        BigDecimal b1 = new BigDecimal(v1.trim());
        BigDecimal b2 = new BigDecimal(v2.trim());
        return String.valueOf(add(b1, b2));
    }

    /**
     * @Description: 精确减法运算
     * @param: v1 被减数
     * @param: v2 减数
     * @return: java.lang.String 两个参数的差
     * @Author: Mr.Cheng
     * @Date: 15:37 2018/8/14
     */
    public static String subtract (String v1, String v2) {
        if (isBlank(v1)) {
            v1 = "0";
        }
        if (isBlank(v2)) {
            v2 = "0";
        }
        BigDecimal b1 = new BigDecimal(v1.trim());
        BigDecimal b2 = new BigDecimal(v2.trim());
        return String.valueOf(subtract(b1, b2));
    }

    /**
     * @Description: 精确乘法运算
     * @param: v1 被乘数
     * @param: v2 乘数
     * @return: java.lang.String 两个参数的积
     * @Author: Mr.Cheng
     * @Date: 15:38 2018/8/14
     */
    public static String multiply (String v1, String v2) {
        if (isBlank(v1)) {
            v1 = "1";
        }
        if (isBlank(v2)) {
            v2 = "1";
        }
        BigDecimal b1 = new BigDecimal(v1.trim());
        BigDecimal b2 = new BigDecimal(v2.trim());
        return String.valueOf(multiply(b1, b2));
    }

    /**
     * @Description: (相对)精确除法运算 , 当发生除不尽情况时 , 精确到 小数点以后2位 , 以后数字四舍五入
     * @param: v1 被除数
     * @param: v2 除数
     * @return: java.lang.String 两个参数的商
     * @Author: Mr.Cheng
     * @Date: 15:40 2018/8/14
     */
    public static String divide (String v1, String v2) {
        return divide(v1, v2, DEF_DIV_SCALE);
    }

    /**
     * @Description: (相对)精确除法运算 . 当发生除不尽情况时 , 由scale参数指 定精度 , 以后数字四舍五入
     * @param: v1 被除数
     * @param: v2 除数
     * @param: scale 表示表示需要精确到小数点以后几位
     * @return: java.lang.String 两个参数的商
     * @Author: Mr.Cheng
     * @Date: 15:41 2018/8/14
     */
    public static String divide (String v1, String v2, Integer scale) {
        if (null == v1) {
            return "0";
        }
        if (null == v2) {
            v2 = "1";
        }
        BigDecimal b1 = new BigDecimal(v1.trim());
        BigDecimal b2 = new BigDecimal(v2.trim());
        return String.valueOf(divide(b1, b2, scale));
    }

    /**
     * @Description: 精确加法运算 , 计算多个数值总和 , 若其中有null值则忽略
     * @param: v1
     * @param: valList 被加数集合
     * @return: java.math.BigDecimal  两个参数的和
     * @Author: Mr.Cheng
     * @Date: 15:42 2018/8/14
     */
    public static BigDecimal sum (BigDecimal v1, BigDecimal... valList) {
        if (null == v1) {
            v1 = BigDecimal.ZERO;
        }
        if (null == valList || valList.length == 0) {
            return v1;
        }
        for (BigDecimal val : valList) {
            if (null != val) {
                v1 = v1.add(val);
            }
        }
        return v1;
    }

    /**
     * @Description: 精确加法运算 , 计算多个数值总和 , 若其中有null值则忽略
     * @param: v1
     * @param: valList 被加数集合
     * @return: java.lang.String 两个参数的和
     * @Author: Mr.Cheng
     * @Date: 15:43 2018/8/14
     */
    public static String sum (String v1, String... valList) {
        if (isBlank(v1)) {
            v1 = "0";
        }
        if (null == valList || valList.length == 0) {
            return v1;
        }
        BigDecimal b1 = new BigDecimal(v1.trim());
        for (String val : valList) {
            if (!isBlank(val)) {
                b1 = add(b1, new BigDecimal(val.trim()));
            }
        }
        return String.valueOf(b1);
    }

    /**
     * @Description: 平均数
     * @param: valList
     * @return: java.math.BigDecimal
     * @Author: Mr.Cheng
     * @Date: 15:44 2018/8/14
     */
    public static BigDecimal avg (BigDecimal... valList) {
        if (null != valList && valList.length != 0) {
            return divide(sum(BigDecimal.ZERO, valList), new BigDecimal(valList.length));
        }
        return BigDecimal.ZERO;
    }

    /**
     * @Description: 平均数
     * @param: valList
     * @return: java.lang.String
     * @Author: Mr.Cheng
     * @Date: 15:45 2018/8/14
     */
    public static String avg (String... valList) {
        if (null != valList && valList.length != 0) {
            return divide(sum("0", valList), String.valueOf(valList.length));
        }
        return "0";
    }

    /**
     * @Description: 最大值
     * @param: v1
     * @param: valList
     * @return: java.math.BigDecimal
     * @Author: Mr.Cheng
     * @Date: 15:45 2018/8/14
     */
    public static BigDecimal max (BigDecimal v1, BigDecimal... valList) {
        BigDecimal max = v1;
        if (null == valList || valList.length == 0) {
            return max;
        }
        for (BigDecimal val : valList) {
            if (null != val && val.compareTo(max) > 0) {
                max = val;
            }
        }
        return max;
    }

    /**
     * @Description: 最大值
     * @param: valList
     * @return: java.math.BigDecimal
     * @Author: Mr.Cheng
     * @Date: 15:46 2018/8/14
     */
    public static BigDecimal maxArr (BigDecimal... valList) {
        if (null == valList || valList.length == 0) {
            return null;
        }
        return max(valList[0], valList);
    }

    /**
     * @Description: 最小值
     * @param: v1
     * @param: valList
     * @return: java.math.BigDecimal
     * @Author: Mr.Cheng
     * @Date: 15:46 2018/8/14
     */
    public static BigDecimal min (BigDecimal v1, BigDecimal... valList) {
        BigDecimal min = v1;
        if (null == valList || valList.length == 0) {
            return min;
        }
        for (BigDecimal val : valList) {
            if (null != val && val.compareTo(min) > 0) {
                min = val;
            }
        }
        return min;
    }

    /**
     * @Description: 最小值
     * @param: valList
     * @return: java.math.BigDecimal
     * @Author: Mr.Cheng
     * @Date: 15:47 2018/8/14
     */
    public static BigDecimal minArr (BigDecimal... valList) {
        if (null == valList || valList.length == 0) {
            return null;
        }
        return min(valList[0], valList);
    }

    /**
     * @Description: 最大值
     * @param: v1
     * @param: valList
     * @return: java.lang.String
     * @Author: Mr.Cheng
     * @Date: 15:47 2018/8/14
     */
    public static String max (String v1, String... valList) {
        if (isBlank(v1)) {
            return null;
        }
        if (null == valList || valList.length == 0) {
            return v1;
        }
        BigDecimal maxBd = new BigDecimal(v1.trim());

        for (String val : valList) {
            if (!isBlank(val) && new BigDecimal(val).compareTo(maxBd) > 0) {
                maxBd = new BigDecimal(val);
            }
        }
        return String.valueOf(maxBd);
    }

    /**
     * @Description:  最大值
     * @param: valList
     * @return: java.lang.String
     * @Author: Mr.Cheng
     * @Date: 15:48 2018/8/14
     */
    public static String maxArr (String... valList) {
        if (null == valList || valList.length == 0) {
            return null;
        }
        return max(valList[0], valList);
    }

    /**
     * @Description:  最小值
     * @param: v1
     * @param: valList
     * @return: java.lang.String
     * @Author: Mr.Cheng
     * @Date: 15:48 2018/8/14
     */
    public static String min (String v1, String... valList) {
        if (isBlank(v1)) {
            return null;
        }
        if (null == valList || valList.length == 0) {
            return v1;
        }
        BigDecimal minBd = new BigDecimal(v1.trim());

        for (String val : valList) {
            if (!isBlank(val) && new BigDecimal(val).compareTo(minBd) > 0) {
                minBd = new BigDecimal(val);
            }
        }
        return String.valueOf(minBd);
    }

    /**
     * @Description:  最小值
     * @param: valList
     * @return: java.lang.String
     * @Author: Mr.Cheng
     * @Date: 15:48 2018/8/14
     */
    public static String minArr (String... valList) {
        if (null == valList || valList.length == 0) {
            return null;
        }
        return min(valList[0], valList);
    }

    /**
     * @Description: 比较2个数值是否相等
     * @param: d1
     * @param: d2
     * @return: java.lang.Boolean
     * @Author: Mr.Cheng
     * @Date: 15:06 2018/8/14
     */
    public static Boolean equals (Double d1, Double d2) {
        Double result = Math.abs(d1 - d2);
        if (result < MONEY_RANGE) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * @Description:
     * @param: db
     * @return: java.lang.String
     * @Author: Mr.Cheng
     * @Date: 15:05 2018/8/14
     */
    public static String formatDb2Str (Double db) {
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        return decimalFormat.format(db);
    }

    /**
     * @Description: 将一个double类型的金额四舍五入
     * @param: d  你需要四舍五入的金额
     * @return: double 四舍五入后的金额
     * @Author: Mr.Cheng
     * @Date: 15:20 2018/8/14
     */
    public static double round (double d) {
        return new BigDecimal(d).setScale(2, 4).doubleValue();
    }

    /**
     * @Description: 将一个BigDecimal类型的金额四舍五入
     * @param: b  你需要四舍五入的金额
     * @return: java.math.BigDecimal  四舍五入后的金额
     * @Author: Mr.Cheng
     * @Date: 15:21 2018/8/14
     */
    public static BigDecimal round (BigDecimal b) {
        if (b == null) {
            throw new NullPointerException("b");
        }
        return b.setScale(2, 4);
    }

    /**
     * @Description: 将一个float类型的金额四舍五入
     * @param: f  你需要四舍五入的金额
     * @return: float  四舍五入后的金额
     * @Author: Mr.Cheng
     * @Date: 15:25 2018/8/14
     */
    public static float round (float f) {
        return new BigDecimal(f).setScale(2, 4).floatValue();
    }

    /**
     * @Description: 判断字符串是否为空(不依赖第三方)
     * @param: str
     * @return: boolean
     * @Author: Mr.Cheng
     * @Date: 15:34 2018/8/14
     */
    private static boolean isBlank (String str) {
        return null == str || str.trim().length() == 0 || "".equals(str) || str.length() == 0;
    }
}
