package com.csk.epay.utils.timeUtil;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.*;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * @program: Cheng
 * @description: Joda日期工具类
 * 主要包括:1、获取,2、转换,3、比较
 * @author: Mr.Cheng
 * @create: 2018-08-15 14:16
 **/
public class JodaTimeUtil {

    private static final DateTimeFormatter DATETIMEFORMATTER_Y_M_D_H_M_S = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
    private static final DateTimeFormatter TIMEFORMAT_ENGLISH_YMDHMS = DateTimeFormat.forPattern("MM/dd/yyyy HH:mm:ss");
    private static final DateTimeFormatter DATETIMEFORMATTER_YMDHMS = DateTimeFormat.forPattern("yyyyMMddHHmmss");

    private static final DateTimeFormatter DATETIMEFORMATTER_Y_M_D = DateTimeFormat.forPattern("yyyy-MM-dd");
    private static final DateTimeFormatter DATETIMEFORMATTER_YMD = DateTimeFormat.forPattern("yyyyMMdd");

    private static final DateTimeFormatter DATETIMEFORMATTER_H_M_S = DateTimeFormat.forPattern("HH:mm:ss");
    private static final DateTimeFormatter DATETIMEFORMATTER_HMS = DateTimeFormat.forPattern("HHmmss");

    private static final ResourceBundle MULTILANG = ResourceBundle.getBundle("i18n.timeUtil", Locale.getDefault());

    private static final String a1 = "[0-9]{4}[0-9]{2}[0-9]{2}[0-9]{2}[0-9]{2}[0-9]{2}";//yyyyMMddHHmmss
    private static final String a2 = "[0-9]{4}[0-9]{2}[0-9]{2}";//yyyyMMdd
    private static final String a3 = "[0-9]{4}-[0-9]{2}-[0-9]{2} [0-9]{2}:[0-9]{2}:[0-9]{2}";//yyyy-MM-dd HH:mm:ss
    private static final String a4 = "[0-9]{4}-[0-9]{2}-[0-9]{2}";//yyyy-MM-dd
    private static final String a5 = "[0-9]{2}:[0-9]{2}:[0-9]{2}";//HH:mm:ss

    private static final String NORMAL_YMDHMS = "\\d{4}-\\d{1,2}-\\d{1,2} \\d{1,2}:\\d{1,2}:\\d{1,2}";
    private static final String ENGLISH_YMDHMS = "\\d{1,2}/\\d{1,2}/\\d{4} \\d{1,2}:\\d{1,2}:\\d{1,2}";
    private static final String SHORT_YMDHMS = "\\d{14}";//yyyyMMddHHmmss
    private static final String SHORT_DATE = "\\d{8}"; //"yyyyMMdd";
    private static final String NORMAL_DATE = "\\d{4}-\\d{1,2}-\\d{1,2}";//yyyy-MM-dd
    private static final String TIME_FORMAT = "\\d{1,2}:\\d{1,2}:\\d{1,2}";//HH:mm:ss

    /**
     * @Description:
     * @param: dateTimeStr
     * @return: org.joda.time.DateTime
     * @Author: Mr.Cheng
     * @Date: 18:27 2019/1/22
     */
    public static DateTime strToDateTime (String dateTimeStr) {
        dateTimeStr = dateTimeStr.trim();
        DateTimeFormatter dtf = null;
        //yyyyMMddHHmmss
        if (dateTimeStr.matches(SHORT_YMDHMS)) {//Pattern.compile(a1).matcher(dateTimeStr).matches()
            return DATETIMEFORMATTER_YMDHMS.parseDateTime(dateTimeStr);//DateTime.parse(dateTimeStr)
            //yyyy-MM-dd HH:mm:ss
        } else if (dateTimeStr.matches(NORMAL_YMDHMS)) {
            return DATETIMEFORMATTER_Y_M_D_H_M_S.parseDateTime(dateTimeStr);
            //MM/dd/yyyy HH:mm:ss
        } else if (dateTimeStr.matches(ENGLISH_YMDHMS)) {
            return TIMEFORMAT_ENGLISH_YMDHMS.parseDateTime(dateTimeStr);
            //yyyyMMdd
        } else if (dateTimeStr.matches(SHORT_DATE)) {
            return DATETIMEFORMATTER_YMD.parseDateTime(dateTimeStr);
            //yyyy-MM-dd
        } else if (dateTimeStr.matches(NORMAL_DATE)) {
            return DATETIMEFORMATTER_Y_M_D.parseDateTime(dateTimeStr);
            //HH:mm:ss
        } else if (dateTimeStr.matches(TIME_FORMAT)) {
            return DATETIMEFORMATTER_H_M_S.parseDateTime(dateTimeStr);
        } else if (dateTimeStr.matches(
                "\\d{4}" + MULTILANG.getString("multi.year")
                        + "\\d{1,2}" + MULTILANG.getString("multi.month")
                        + "\\d{1,2}" + MULTILANG.getString("multi.day")
                        + " \\d{1,2}" + MULTILANG.getString("multi.hour")
                        + "\\d{1,2}" + MULTILANG.getString("multi.minute")
                        + "\\d{1,2}" + MULTILANG.getString("multi.second"))) {
            dtf = DateTimeFormat.forPattern(MULTILANG.getString("multi.TIME_FORMAT_CHINA"));
            return dtf.parseDateTime(dateTimeStr);
        } else if (dateTimeStr.matches(
                "\\d{4}" + MULTILANG.getString("multi.year")
                        + "\\d{1,2}" + MULTILANG.getString("multi.month")
                        + "\\d{1,2}" + MULTILANG.getString("multi.day"))) {
            dtf = DateTimeFormat.forPattern(MULTILANG.getString("multi.DATE_FORMAT_CHINA"));
            return dtf.parseDateTime(dateTimeStr);
        } else {
            return DATETIMEFORMATTER_Y_M_D_H_M_S.parseDateTime(getCurrentTimeStrY_M_D_H_M_S());
        }
    }

    /**
     * @Description: 时间戳转换为标准时间格式字符串
     * @param: timestamp
     * @return: java.lang.String
     * @Author: Mr.Cheng
     * @Date: 10:48 2019/1/22
     */
    public static String timestampToDateTime (Long timestamp) {
        return getTimeStr_Y_M_D_H_M_S(new DateTime(timestamp));
    }

    /**
     * @Description: 标准时间格式字符串转换为时间戳
     * @param: timestamp
     * @return: java.lang.String
     * @Author: Mr.Cheng
     * @Date: 10:48 2019/1/22
     */
    public static Long dateTimeToTimestamp (String dateTime) {
        DateTime time = strToDateTime(dateTime);
        return time.getMillis();
    }

    /**
     * @Description: 装换时间格式为： yyyy-MM-dd HH:mm:ss
     * @param: dateTime
     * @return: java.lang.String
     * @Author: Mr.Cheng
     * @Date: 14:57 2018/8/15
     */
    public static String getTimeStr_Y_M_D_H_M_S (DateTime dateTime) {
        return dateTime.toString(DATETIMEFORMATTER_Y_M_D_H_M_S);
    }

    /**
     * @Description: 装换时间格式为： yyyyMMddHHmmss
     * @param: dateTime
     * @return: java.lang.String
     * @Author: Mr.Cheng
     * @Date: 14:57 2018/8/15
     */
    public static String getTimeStr_YMDHMS (DateTime dateTime) {
        return dateTime.toString(DATETIMEFORMATTER_YMDHMS);
    }

    /**
     * @Description: 装换时间格式为： yyyy-MM-dd
     * @param: dateTime
     * @return: java.lang.String
     * @Author: Mr.Cheng
     * @Date: 14:57 2018/8/15
     */
    public static String getTimeStr_Y_M_D (DateTime dateTime) {
        return dateTime.toString(DATETIMEFORMATTER_Y_M_D);
    }

    /**
     * @Description: 装换时间格式为：yyyyMMdd
     * @param: dateTime
     * @return: java.lang.String
     * @Author: Mr.Cheng
     * @Date: 14:57 2018/8/15
     */
    public static String getTimeStr_YMD (DateTime dateTime) {
        return dateTime.toString(DATETIMEFORMATTER_YMD);
    }

    /**
     * @Description: 装换时间格式为： HH:mm:ss
     * @param: dateTime
     * @return: java.lang.String
     * @Author: Mr.Cheng
     * @Date: 14:57 2018/8/15
     */
    public static String getTimeStr_H_M_S (DateTime dateTime) {
        return dateTime.toString(DATETIMEFORMATTER_H_M_S);
    }

    /**
     * @Description: 装换时间格式为： HHmmss
     * @param: dateTime
     * @return: java.lang.String
     * @Author: Mr.Cheng
     * @Date: 14:57 2018/8/15
     */
    public static String getTimeStr_HMS (DateTime dateTime) {
        return dateTime.toString(DATETIMEFORMATTER_HMS);
    }

    /**
     * @Description:
     * @param: date
     * @param: formatStr
     * @return: java.lang.String
     * @Author: Mr.Cheng
     * @Date: 16:02 2018/8/15
     */
    public static String dateTimeToStr (DateTime dateTime, String formatStr) {
        if (dateTime == null) {
            return StringUtils.EMPTY;
        }
        return dateTime.toString(formatStr);
    }

    /**
     * @Description: 获取当前系统时间
     * 格式 :yyyy-MM-dd HH:mm:ss
     * @param:
     * @return: java.lang.String
     * @Author: Mr.Cheng
     * @Date: 15:05 2018/8/15
     */
    public static String getCurrentTimeStrY_M_D_H_M_S () {
        return getTimeStr_Y_M_D_H_M_S(DateTime.now());
    }

    /**
     * @Description: 获取当前系统时间
     * 格式 :yyyyMMddHHmmss
     * @param:
     * @return: java.lang.String
     * @Author: Mr.Cheng
     * @Date: 15:05 2018/8/15
     */
    public static String getCurrentTimeStr_YMDHMS () {
        return getTimeStr_YMDHMS(DateTime.now());
    }

    /**
     * @Description: 获取当前系统时间
     * 格式 :yyyy-MM-dd
     * @param:
     * @return: java.lang.String
     * @Author: Mr.Cheng
     * @Date: 15:05 2018/8/15
     */
    public static String getCurrentTimeStr_Y_M_D () {
        return getTimeStr_Y_M_D(DateTime.now());
    }

    /**
     * @Description: 获取当前系统时间
     * 格式 :HH:mm:ss
     * @param:
     * @return: java.lang.String
     * @Author: Mr.Cheng
     * @Date: 15:05 2018/8/15
     */
    public static String getCurrentTimeStr_H_M_S () {
        return getTimeStr_H_M_S(DateTime.now());
    }

    /**
     * @Description: 根据日期String获得【星期】
     * @param: date
     * @return: java.lang.String
     * @Author: Mr.Cheng
     * @Date: 15:25 2018/8/15
     */
    public static String getWeekOfDateStr (String dateTimeStr) {
        String res = "";
        DateTime dateTime = null;
        if (StringUtils.isNotBlank(dateTimeStr)) {
            dateTime = DATETIMEFORMATTER_Y_M_D_H_M_S.parseDateTime(dateTimeStr);
        }
        // Get the day of week field value.
        int dayOfWeek = dateTime.getDayOfWeek();
        switch (dayOfWeek) {
            case 1:
                res = "周一";
                break;
            case 2:
                res = "周二";
                break;
            case 3:
                res = "周三";
                break;
            case 4:
                res = "周四";
                break;
            case 5:
                res = "周五";
                break;
            case 6:
                res = "周六";
                break;
            case 7:
                res = "周日";
                break;

            default:
                break;
        }
        return res;
    }

    /**
     * @Description: 根据日期，获得【月份】
     * @param: date
     * @return: java.lang.String
     * @Author: Mr.Cheng
     * @Date: 14:25 2018/8/15
     */
    public static String getMonthOfDateStr (String dateTimeStr) {
        String res = "";
        DateTime dateTime = null;
        if (StringUtils.isNotBlank(dateTimeStr)) {
            dateTime = DATETIMEFORMATTER_Y_M_D_H_M_S.parseDateTime(dateTimeStr);
        }
        // Get the month of year field value
        int monthOfYear = dateTime.getMonthOfYear();
        res = String.valueOf(monthOfYear);
        return res;
    }

    /**
     * @Description: 根据日期;获得【年份】
     * @param: date
     * @return: java.lang.String
     * @Author: Mr.Cheng
     * @Date: 15:31 2018/8/15
     */
    public static String getYearOfDateStr (String dateTimeStr) {
        String res = "";
        DateTime dateTime = null;
        if (StringUtils.isNotBlank(dateTimeStr)) {
            dateTime = DATETIMEFORMATTER_Y_M_D_H_M_S.parseDateTime(dateTimeStr);
        }
        int year = dateTime.getYear();
        res = String.valueOf(year);
        return res;
    }

    /**
     * @Description: 根据日期年份月份;获得【年份】
     * @param: date
     * @return: java.lang.String
     * @Author: Mr.Cheng
     * @Date: 15:32 2018/8/15
     */
    public static String getYearOfYearMonthStr (String dateTimeStr) {
        String res = "";
        DateTime dateTime = null;
        if (StringUtils.isNotBlank(dateTimeStr)) {
            dateTime = DATETIMEFORMATTER_Y_M_D.parseDateTime(dateTimeStr);
        }
        int year = dateTime.getYear();
        res = String.valueOf(year);
        return res;
    }

    /**
     * @Description: 根据日期String;获得【月-日】
     * @param: date
     * @return: java.lang.String
     * @Author: Mr.Cheng
     * @Date: 15:33 2018/8/15
     */
    public static String getMonthAndDayOfDateStr (String dateTimeStr) {
        String res = "";
        DateTime dateTime = null;
        if (StringUtils.isNotBlank(dateTimeStr)) {
            dateTime = DATETIMEFORMATTER_Y_M_D_H_M_S.parseDateTime(dateTimeStr);
        }
        int monthOfYear = dateTime.getMonthOfYear();
        int dayOfMonth = dateTime.getDayOfMonth();
        res = monthOfYear + "-" + dayOfMonth;
        return res;
    }

    /**
     * @Description: 根据日期;String;获得【年-月】
     * @param: date
     * @return: java.lang.String
     * @Author: Mr.Cheng
     * @Date: 15:35 2018/8/15
     */
    public static String getYearAndMonthOfDateStr (String dateTimeStr) {
        String res = "";
        String month = "";
        DateTime dateTime = null;
        if (StringUtils.isNotBlank(dateTimeStr)) {
            dateTime = DATETIMEFORMATTER_Y_M_D_H_M_S.parseDateTime(dateTimeStr);
        }
        int year = dateTime.getYear();
        int monthOfYear = dateTime.getMonthOfYear();
        if (monthOfYear < 10) {
            month = "0" + monthOfYear;
        } else {
            month = String.valueOf(monthOfYear);
        }
        res = year + "年" + month + "月";
        return res;
    }

    /**
     * @Description: 当前时间的毫秒数
     * @param:
     * @return: long
     * @Author: Mr.Cheng
     * @Date: 16:16 2018/8/15
     */
    public static long timestamp () {
        //long currentTimeMillis = System.currentTimeMillis();
        DateTime dateTime = new DateTime();
        return dateTime.getMillis();
    }

    /**
     * @Description: 和当前系统时间相隔的分钟数
     * @param: date
     * @return: int
     * @Author: Mr.Cheng
     * @Date: 16:16 2018/8/15
     */
    public static int minutesBetween (DateTime dateTime) {
        DateTime SysTime = new DateTime();
        int minutes = Minutes.minutesBetween(dateTime, SysTime).getMinutes();
        return minutes;
    }

    /**
     * @Description: 判断两个日期是否在同一天
     * @param: date
     * @param: anotherDate
     * @return: boolean
     * @Author: Mr.Cheng
     * @Date: 14:21 2018/8/15
     */
    public static boolean isSameDay (String dateTimeStr, String anotherDate) {
        boolean res = false;
        DateTime dateTime = new DateTime();
        DateTime dt1 = new DateTime(dateTimeStr);
        DateTime dt2 = new DateTime(anotherDate);
        int intervalDays = Days.daysBetween(dt1, dt2).getDays();
        if (intervalDays == 0) {
            res = true;
        }
        return res;
    }

    public static void main (String[] args) {

    }
}
