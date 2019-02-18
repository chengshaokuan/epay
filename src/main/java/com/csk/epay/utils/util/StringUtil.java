package com.csk.epay.utils.util;

import org.apache.commons.lang.StringUtils;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @program: Cheng
 * @description: 工具类。
 * @author: Mr.Cheng
 * @create: 2018-09-05 17:03
 **/
public class StringUtil {
    private static final String TIME_FORMAT_SHORT = "yyyyMMddHHmmss";

    private static final String TIME_FORMAT_NORMAL = "yyyy-MM-dd HH:mm:ss";

    private static final String TIME_FORMAT_ENGLISH = "MM/dd/yyyy HH:mm:ss";

    private static final String DATE_FORMAT_SHORT = "yyyyMMdd";

    private static final String DATE_FORMAT_NORMAL = "yyyy-MM-dd";

    private static final String DATE_FORMAT_ENGLISH = "MM/dd/yyyy";

    private static final ResourceBundle MULTILANG = ResourceBundle.getBundle("i18n.timeUtil", Locale.getDefault());

    /**
     * @Description: 求字符串中某个子串的位置（自字符串orig的i位开始将orig按组长度len分隔为若干段，
     * 求第一个indexOf(段,sub)=0 的段首字符出现的位置）
     * @param: orig 原字符串
     * @param: sub 查找的子串
     * @param: len 每组长度
     * @param: i 开始查找位置
     * @return: int
     * @Author: Mr.Cheng
     * @Date: 18:09 2019/2/18
     */
    public static int indexOf (String orig, String sub, int len, int i) {
        if (orig == null) {
            return -1;
        }
        int idx = orig.indexOf(sub, i);
        if (idx == -1) {
            return idx;
        }
        if (idx % len == 0) {
            return idx;
        }
        i = (idx / len + 1) * len;
        int tmp = -1;
        if ((tmp = indexOf(orig, sub, len, i)) > -1) {
            return tmp;
        } else {
            return -1;
        }
    }

    /**
     * Description:求字符串中某个子串的位置（将字符串按组长度len分隔为若干段，求第一个indexOf(段,sub)=0的段首字符出现的位置
     * ）
     *
     * @param orig 原字符串
     * @param sub  查找的子串
     * @param len  每组长度
     * @return int
     * @throws
     * @Author chengshaokuan  Create Date: 2012-12-20 下午4:21:59
     */
    public static int indexOf (String orig, String sub, int len) {
        return indexOf(orig, sub, len, 0);
    }

    /**
     * Description:求字符串中某个子串的位置（将字符串按子串长度分隔为若干段，求第一个同子串相同的段 首字符出现的位置）
     *
     * @param orig 原字符串
     * @param sub  查找的子串
     * @return int
     * @throws
     * @Author chengshaokuan  Create Date: 2012-12-20 下午4:21:46
     */
    public static int indexOf (String orig, String sub) {
        return indexOf(orig, sub, sub.length(), 0);
    }

    /**
     * Description:截取字符串
     *
     * @param orig   源字符串
     * @param length 字符串长度
     * @return String
     * @throws
     * @Author chengshaokuan  Create Date: 2012-12-20 下午4:21:21
     */
    public static String substr (String orig, int length) {
        if (orig == null) {
            return "";
        }
        if (orig.length() <= length) {
            return orig;
        }
        return orig.substring(0, length - 1) + "...";
    }

    /**
     * Description:首字母大写
     *
     * @param input 输入字符串
     * @return String
     * @throws
     * @Author chengshaokuan  Create Date: 2012-12-20 下午4:21:06
     */
    public static String toFirstUpperCase (String input) {
        return StringUtils.isBlank(input) ? input : input.substring(0, 1)
                .toUpperCase() + input.substring(1);
    }

    /**
     * Description:填充字符串到固定长度
     *
     * @param orig          源字符串
     * @param num           填充后字符串长度几位
     * @param fillCharacter 要填充的字符
     * @param location      填充位置。
     * @return String
     * @throws
     * @Author chengshaokuan  Create Date: 2012-12-20 下午4:20:09
     */
    public static String fillCharacter (String orig, int num, String fillCharacter, boolean location) {
        if (orig == null) {
            return null;
        }
        if (orig.length() >= num) {
            return orig;
        }
        StringBuffer sb = new StringBuffer("");
        for (int i = 0; ; i++) {
            if (orig.length() + i * fillCharacter.length() >= num) {
                break;
            }
            sb.append(fillCharacter);
        }
        if (location) {
            orig = orig + sb.substring(0, num - orig.length());
        } else {
            orig = sb.substring(0, num - orig.length()) + orig;
        }
        return orig;
    }

    /**
     * Description:字符转换方法
     *
     * @param orig  原字符串
     * @param clazz 转换类型
     * @return Object
     * @throws
     * @Author chengshaokuan  Create Date: 2012-12-20 下午4:19:16
     */
    public static Object convert (String orig, Class<?> clazz) {
        if (orig == null) {
            return null;
        }
        if (clazz == String.class) {
            return orig;
        }
        if (clazz == short.class) {
            return Short.parseShort(orig);
        }
        if (clazz == Short.class) {
            return new Short(orig);
        }
        if (clazz == int.class) {
            return Integer.parseInt(orig);
        }
        if (clazz == Integer.class) {
            return new Integer(orig);
        }
        if (clazz == long.class) {
            return Long.parseLong(orig);
        }
        if (clazz == Long.class) {
            return new Long(orig);
        }
        if (clazz == float.class) {
            return Float.parseFloat(orig);
        }
        if (clazz == Float.class) {
            return new Float(orig);
        }
        if (clazz == double.class) {
            return Double.parseDouble(orig);
        }
        if (clazz == Double.class) {
            return new Double(orig);
        }
        if ("t".equalsIgnoreCase(orig) || "ture".equalsIgnoreCase(orig)
                || "y".equalsIgnoreCase(orig) || "yes".equalsIgnoreCase(orig)) {
            if (clazz == boolean.class) {
                return true;
            }
            if (clazz == Boolean.class) {
                return new Boolean(true);
            }
        } else {
            if (clazz == boolean.class) {
                return false;
            }
            if (clazz == Boolean.class) {
                return new Boolean(false);
            }
        }
        try {
            if (clazz == Date.class) {
                DateFormat fmt = null;
                if (orig.matches("\\d{14}")) {
                    fmt = new SimpleDateFormat(TIME_FORMAT_SHORT);
                } else if (orig
                        .matches("\\d{4}-\\d{1,2}-\\d{1,2} \\d{1,2}:\\d{1,2}:\\d{1,2}")) {
                    fmt = new SimpleDateFormat(TIME_FORMAT_NORMAL);
                } else if (orig
                        .matches("\\d{1,2}/\\d{1,2}/\\d{4} \\d{1,2}:\\d{1,2}:\\d{1,2}")) {
                    fmt = new SimpleDateFormat(TIME_FORMAT_ENGLISH);
                } else if (orig.matches("\\d{8}")) {
                    fmt = new SimpleDateFormat(DATE_FORMAT_SHORT);
                } else if (orig.matches("\\d{4}-\\d{1,2}-\\d{1,2}")) {
                    fmt = new SimpleDateFormat(DATE_FORMAT_NORMAL);
                } else if (orig.matches("\\d{1,2}/\\d{1,2}/\\d{4}")) {
                    fmt = new SimpleDateFormat(DATE_FORMAT_ENGLISH);
                }
                return fmt.parse(orig);
            }
            if (clazz == Calendar.class) {
                Calendar cal = Calendar.getInstance();
                DateFormat fmt = null;
                if (orig.matches("\\d{14}")) {
                    fmt = new SimpleDateFormat(TIME_FORMAT_SHORT);
                } else if (orig
                        .matches("\\d{4}-\\d{1,2}-\\d{1,2} \\d{1,2}:\\d{1,2}:\\d{1,2}")) {
                    fmt = new SimpleDateFormat(TIME_FORMAT_NORMAL);
                } else if (orig
                        .matches("\\d{1,2}/\\d{1,2}/\\d{4} \\d{1,2}:\\d{1,2}:\\d{1,2}")) {
                    fmt = new SimpleDateFormat(TIME_FORMAT_ENGLISH);
                } else if (orig.matches("\\d{8}")) {
                    fmt = new SimpleDateFormat(DATE_FORMAT_SHORT);
                } else if (orig.matches("\\d{4}-\\d{1,2}-\\d{1,2}")) {
                    fmt = new SimpleDateFormat(DATE_FORMAT_NORMAL);
                } else if (orig.matches("\\d{1,2}/\\d{1,2}/\\d{4}")) {
                    fmt = new SimpleDateFormat(DATE_FORMAT_ENGLISH);
                }
                cal.setTime(fmt.parse(orig));
                return cal;
            }
        } catch (ParseException e) {
            throw new IllegalArgumentException("字符串不能转换为" + clazz.getName()
                    + "类型.");
        }
        throw new IllegalArgumentException("字符串不能转换为" + clazz.getName() + "类型.");
    }

    /**
     * Description:字符转换方法
     *
     * @param orig 原字符串
     * @return String
     * @throws
     * @Author chengshaokuan  Create Date: 2012-12-20 下午4:18:51
     */
    public static String convert (Object orig) {
        if (orig == null) {
            return null;
        }
        if (orig instanceof String) {
            return (String) orig;
        }
        if (orig instanceof Short) {
            return Short.toString((Short) orig);
        }
        if (orig instanceof Integer) {
            return Integer.toString((Integer) orig);
        }
        if (orig instanceof Long) {
            return Long.toString((Long) orig);
        }
        if (orig instanceof Float) {
            return Float.toString((Float) orig);
        }
        if (orig instanceof Double) {
            return Double.toString((Double) orig);
        }
        if (orig instanceof Boolean) {
            return Boolean.toString((Boolean) orig);
        }
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        if (orig instanceof Date) {
            return format.format((Date) orig);
        }
        if (orig instanceof java.sql.Date) {
            return format.format((java.sql.Date) orig);
        }
        if (orig instanceof Calendar) {
            return format.format(((Calendar) orig).getTime());
        }
        throw new IllegalArgumentException("参数类型不支持.");
    }

    /**
     * 从map字符串获取value值
     *
     * @param orig  字符串 例:a=1&b=2&c=3
     * @param param key 例:a
     * @param split 例:&
     * @return 例:1
     * @since 0.1
     */
    public static String[] getValueFromString (String orig, String param,
                                               String split) {
        String[] result = new String[]{};
        if (StringUtils.isBlank(orig)) {
            return result;
        }
        List<String> list = new ArrayList<String>();
        String[] values = orig.split(split);
        for (String tmp : values) {
            String key = StringUtils.substringBefore(tmp, "=");
            String value = StringUtils.substringAfter(tmp, "=");
            if (param.equals(key)) {
                list.add(value);
            }
        }
        return list.toArray(result);
    }

    /**
     * Description:反射根据Method获得类名、方法名
     *
     * @param method 方法名。
     * @return String
     * @throws
     * @Author chengshaokuan  Create Date: 2012-12-20 下午4:17:07
     */
    public static String methodToClass (Method method) {
        String methodName = method.getName();
        String className = method.getDeclaringClass().getName();
        Class<?>[] typeNames = method.getParameterTypes();
        StringBuffer buffer = new StringBuffer();
        for (Class<?> type : typeNames) {
            buffer.append(type.getName()).append(",");
        }
        buffer.deleteCharAt(buffer.length() - 1);
        return className + "." + methodName + "(" + buffer.toString() + ")";
    }

    /**
     * Description:根据起始时间、类型、间隔算出结束时间
     *
     * @param startDate 起始时间。
     * @param type      类型
     * @param interval  间隔
     * @return String
     * @throws
     * @Author chengshaokuan  Create Date: 2012-12-20 下午4:16:07
     */
    public static String getEndDate (String startDate, String type, int interval) {
        if (StringUtils.isBlank(startDate) || StringUtils.isBlank(type)) {
            return null;
        }
        Calendar c1 = Calendar.getInstance(Locale.CHINA);
        SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT_NORMAL);
        try {
            Date d1 = format.parse(startDate);
            c1.setTime(d1);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if ("1".equals(type)) {// 间隔为天
            c1.add(Calendar.DAY_OF_MONTH, interval);
        } else if ("2".equals(type)) {// 间隔为整月
            c1.add(Calendar.MONTH, interval);
        } else if ("3".equals(type)) {// 间隔为自然月
            c1.set(Calendar.DAY_OF_MONTH, 1);
            c1.add(Calendar.MONTH, interval);
            c1.add(Calendar.DAY_OF_MONTH, -1);
        }
        return format.format(c1.getTime());
    }

    /**
     * Description:将中文数字，转为数字。
     *
     * @param normal 中文
     * @return 返回数字。
     * @Author chengshaokuan  Create Date: 2013-1-16 下午4:39:23
     */
    public static String cnToNumber (String normal) {
        Map<String, Integer> map = new HashMap<String, Integer>();

        map.put(MULTILANG.getString("multi.one"), 1);
        map.put(MULTILANG.getString("multi.two"), 2);
        map.put(MULTILANG.getString("multi.three"), 3);
        map.put(MULTILANG.getString("multi.four"), 4);
        map.put(MULTILANG.getString("multi.five"), 5);
        map.put(MULTILANG.getString("multi.six"), 6);
        map.put(MULTILANG.getString("multi.seven"), 7);
        map.put(MULTILANG.getString("multi.eight"), 8);
        map.put(MULTILANG.getString("multi.nine"), 9);
        map.put(MULTILANG.getString("multi.ten"), 10);
        char[] charArray = normal.toCharArray();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < charArray.length; i++) {
            char t = normal.charAt(i);
            if (map.containsKey(String.valueOf(t))) {
                sb.append(map.get(String.valueOf(t)));
            } else {
                sb.append(String.valueOf(t));
            }
        }
        return sb.toString();
    }

    /**
     * Description:过滤js标签，防止js注入漏洞
     *
     * @param str 输入字符串。
     * @return String
     * @throws
     * @Author chengshaokuan  Create Date: 2012-12-20 下午4:14:26
     */
    public static String replaceJsTag (String str) {
        str = str
                .replaceAll("<", "&lt;")
                .replaceAll(">", "&gt;")
                .replaceAll("\\(", "(")
                .replaceAll("\\)", ")")
                .replaceAll("eval\\((.*)\\)", "")
                .replaceAll("[\\\"\\\'][\\s]*javascript:(.*)[\\\"\\\']", "\"\"");
        return str;
    }

    /**
     * Description:BigDecimal转为字符串
     *
     * @param dec 字符串。
     * @return String
     * @throws
     * @Author chengshaokuan  Create Date: 2012-12-20 下午4:14:22
     */
    public static String bigDecimalToString (BigDecimal dec) {
        String str = "";
        if (dec != null) {
            str = dec.toString();
        }
        return str;
    }

    /**
     * Description:拆分字符串并放入list
     *
     * @param string 字符串。
     * @param flag   字符。
     * @return List<String>
     * @throws
     * @Author chengshaokuan  Create Date: 2012-12-20 下午4:14:20
     */
    public static List<String> processStrings (String string, String flag) {
        List<String> list = new ArrayList<String>();
        if (string != null) {
            String[] strs = string.split(flag);
            for (String str : strs) {
                list.add(str);
            }
        }
        return list;
    }

    /**
     * Description:字符串数组连接成字符串
     *
     * @param strs 字符串数组。
     * @param flag 字符。
     * @return String
     * @throws
     * @Author chengshaokuan  Create Date: 2012-12-20 下午4:13:07
     */
    public static String processStrings (String[] strs, String flag) {
        String string = "";
        if (strs != null && strs.length > 0) {
            for (int i = 0; i < strs.length; i++) {
                if (i > 0) {
                    string += flag;
                }
                string += strs[i];
            }
        }
        return string;
    }

    /**
     * Description:字符串List连接字符串
     *
     * @param strs 字符串集合。
     * @param flag 字符。
     * @return String
     * @throws
     * @Author chengshaokuan  Create Date: 2012-12-20 下午4:12:43
     */
    public static String processStrings (List<String> strs, String flag) {
        String string = "";
        if (strs != null && strs.size() > 0) {
            for (int i = 0; i < strs.size(); i++) {
                if (i > 0) {
                    string += flag;
                }
                string += strs.get(i);
            }
        }
        return string;
    }

    /**
     * <p>
     * 加密字符串</br> {@code example: encryptStr(3, 6, "18523756788") ：185****6788 }
     * </p>
     *
     * @param beginIndex 加密起始索引位置
     * @param endIndex   加密结束索引位置
     * @param str        字符串
     * @return String
     * @throws Exception 执行过程中出现异常
     * @author ShawnYao
     * @date 2013-3-4 上午10:09:31
     */
    public static String encryptStr (int beginIndex, int endIndex, String str)
            throws Exception {
        StringBuffer tmpStr = new StringBuffer("");
        StringBuffer signStr = new StringBuffer("");
        if (str != null && !"".equals(str.trim())) {
            str = str.trim();
            if (beginIndex < 0) {
                beginIndex = 0;
            }
            if (endIndex < 0) {
                endIndex = 0;
            }
            if (beginIndex == 0 && endIndex == 0) {
                return str;
            }
            if (endIndex >= str.length()) {
                endIndex = str.length() - 1;
            }
            int count = (endIndex - beginIndex) + 1;
            if (count < 0) {
                return str;
            }
            if (str.length() <= count) {
                for (int i = 0; i < str.length(); i++) {
                    signStr.append("*");
                }
            } else {
                for (int i = 0; i < count; i++) {
                    signStr.append("*");
                }
            }
            tmpStr = tmpStr.append(str.substring(0, beginIndex))
                    .append(signStr)
                    .append(str.substring(endIndex + 1, str.length()));
            return tmpStr.toString();
        } else {
            return "";
        }
    }

    /**
     * 合并字符窜，逗号分隔
     *
     * @param objs 字符窜组
     * @return String
     * @author Huzhiliang
     * @Date 2013-4-27 下午5:52:41
     */
    public static String mergeByComma (String... objs) {
        StringBuilder mergedObj = new StringBuilder(objs[0]);
        for (int i = 1; i < objs.length; i++) {
            if (objs[i] == null || "".equals(objs[i])) {
                continue;
            } else {
                mergedObj.append(",").append(objs[i]);
            }
        }
        return mergedObj.toString();
    }

    /**
     * <p>
     * 替换字符串中的回车
     * </p>
     *
     * @param str 目标串
     * @return String
     * @author ShawnYao
     * @date 2013-6-24 下午5:23:39
     */
    public static String replaceEnter (String str, String replacement) {
        if (str != null && replacement != null) {
            StringBuffer tmpStr = new StringBuffer();
            tmpStr = tmpStr.append(str.replaceAll("\r\n", replacement));
            StringBuffer sbstr = new StringBuffer();
            sbstr = sbstr.append(tmpStr.toString().replaceAll("\n", replacement));
            return sbstr.toString();
        }
        return str;
    }

    public static void main (String[] args) {
        String ori = "sbstr = sbstr.append(tmpStr.toString().replaceAll(\"\\n\", replacement))";

        int sb = StringUtil.indexOf(ori, "tr", 2, 1);
        System.out.println(sb);
    }

}
