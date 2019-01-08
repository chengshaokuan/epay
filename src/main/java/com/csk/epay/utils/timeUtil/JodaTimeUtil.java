package com.csk.epay.utils.timeUtil;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Minutes;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @program: Cheng
 * @description: Joda日期工具类
 * @author: Mr.Cheng
 * @create: 2018-08-15 14:16
 **/
public class JodaTimeUtil {

    private static final DateTimeFormatter DATETIMEFORMATTER_Y_M_D_H_M_S = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
    private static final DateTimeFormatter DATETIMEFORMATTER_YMDHMS = DateTimeFormat.forPattern("yyyyMMddHHmmss");

    private static final DateTimeFormatter DATETIMEFORMATTER_Y_M_D = DateTimeFormat.forPattern("yyyy-MM-dd");
    private static final DateTimeFormatter DATETIMEFORMATTER_YMD = DateTimeFormat.forPattern("yyyyMMdd");

    private static final DateTimeFormatter DATETIMEFORMATTER_H_M_S = DateTimeFormat.forPattern("HH:mm:ss");
    private static final DateTimeFormatter DATETIMEFORMATTER_HMS = DateTimeFormat.forPattern("HHmmss");


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
     * 格式 :yyyyMMdd
     * @param:
     * @return: java.lang.String
     * @Author: Mr.Cheng
     * @Date: 15:05 2018/8/15
     */
    public static String getCurrentTimeStr_YMD () {
        return getTimeStr_YMD(DateTime.now());
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
     * @Description: 获取当前系统时间
     * 格式 :HHmmss
     * @param:
     * @return: java.lang.String
     * @Author: Mr.Cheng
     * @Date: 15:05 2018/8/15
     */
    public static String getCurrentTimeStr_HMS () {
        return getTimeStr_HMS(DateTime.now());
    }

    /**
     * @Description: 根据日期获得星期
     * @param: date
     * @return: java.lang.String
     * @Author: Mr.Cheng
     * @Date: 14:24 2018/8/15
     */
    public static String getWeekOfDateStr (Date date) {
        String[] weekDaysName = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        String[] weekDaysCode = {"0", "1", "2", "3", "4", "5", "6"};
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int intWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        return weekDaysName[intWeek];
        // return weekDaysCode[intWeek];
    }

    /**
     * @Description: 根据日期String获得【星期】
     * @param: date
     * @return: java.lang.String
     * @Author: Mr.Cheng
     * @Date: 15:25 2018/8/15
     */
    public static String getWeekOfDateStr (String date) {
        // 返回值
        String res = "";
        DateTime dateTime = null;
        if (StringUtils.isNotBlank(date)) {
            dateTime = DATETIMEFORMATTER_Y_M_D_H_M_S.parseDateTime(date);
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
    public static String getMonthOfDateStr (String date) {
        String res = "";
        DateTime dateTime = null;
        if (StringUtils.isNotBlank(date)) {
            dateTime = DATETIMEFORMATTER_Y_M_D_H_M_S.parseDateTime(date);
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
    public static String getYearOfDateStr (String date) {
        String res = "";
        DateTime dateTime = null;
        if (StringUtils.isNotBlank(date)) {
            dateTime = DATETIMEFORMATTER_Y_M_D_H_M_S.parseDateTime(date);
        }
        // Get the year field value.
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
    public static String getYearOfYearMonthStr (String date) {
        String res = "";
        DateTime dateTime = null;
        if (StringUtils.isNotBlank(date)) {
            dateTime = DATETIMEFORMATTER_Y_M_D.parseDateTime(date);
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
    public static String getMonthAndDayOfDateStr (String date) {
        // 返回值
        String res = "";
        DateTime dateTime = null;
        if (StringUtils.isNotBlank(date)) {
            dateTime = DATETIMEFORMATTER_Y_M_D_H_M_S.parseDateTime(date);
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
    public static String getYearAndMonthOfDateStr (String date) {
        // 返回值
        String res = "";
        String month = "";
        DateTime dateTime = null;
        if (StringUtils.isNotBlank(date)) {
            dateTime = DATETIMEFORMATTER_Y_M_D_H_M_S.parseDateTime(date);
        }
        // Get the year field value.
        int year = dateTime.getYear();
        // Get the month of year field value
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
     * @Description: 时间字符串转化为Date格式
     * @param: dateTimeStr
     * @return: java.util.Date
     * @Author: Mr.Cheng
     * @Date: 15:47 2018/8/15
     */
    public static Date strToDate (String dateTimeStr) {
        DateTime dateTime = null;
        if (StringUtils.isNotBlank(dateTimeStr)) {
            dateTime = DATETIMEFORMATTER_Y_M_D_H_M_S.parseDateTime(dateTimeStr);
        }
        return dateTime.toDate();
    }

    /**
     * @Description:
     * @param: date
     * @param: formatStr
     * @return: java.lang.String
     * @Author: Mr.Cheng
     * @Date: 16:02 2018/8/15
     */
    public static String dateToStr (Date date, String formatStr) {
        if (date == null) {
            return StringUtils.EMPTY;
        }
        DateTime dateTime = new DateTime(date);
        return dateTime.toString(formatStr);
    }

    /**
     * @Description:
     * @param: date
     * @return: java.lang.String
     * @Author: Mr.Cheng
     * @Date: 16:12 2018/8/15
     */
    public static String dateToStr (Date date) {
        if (date == null) {
            return StringUtils.EMPTY;
        }
        DateTime dateTime = new DateTime(date);
        return getTimeStr_Y_M_D_H_M_S(dateTime);
    }

    /**
     * @Description:  当前时间的毫秒数
     * @param:
     * @return: long
     * @Author: Mr.Cheng
     * @Date: 16:16 2018/8/15
     */
    public static long timestamp () {
        DateTime dateTime = new DateTime();
        long millis = dateTime.getMillis();
        return millis;
    }

    /**
     * @Description:  和当前系统时间相隔的分钟数
     * @param: date
     * @return: int
     * @Author: Mr.Cheng
     * @Date: 16:16 2018/8/15
     */
    public static int minutesBetween (Date date) {
        // 系统当前时间
        DateTime SysTime = new DateTime();
        DateTime dateTime = null;
        if (date != null) {
            dateTime = new DateTime(date);
        }
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
    public static boolean isSameDay (String date, String anotherDate) {
        boolean res = false;
        DateTime dateTime = new DateTime();
        DateTime dt1 = new DateTime(date);
        DateTime dt2 = new DateTime(anotherDate);
        int intervalDays = Days.daysBetween(dt1, dt2).getDays();
        if (intervalDays == 0) {
            res = true;
        }
        return res;
    }

    public static void main (String[] args) throws Exception {
        System.out.println(JodaTimeUtil.dateToStr(new Date(), "yyyy-MM-dd HH:mm:ss"));
        System.out.println(JodaTimeUtil.dateToStr(new Date(), "yyyy-MM-dd"));
        System.out.println(JodaTimeUtil.dateToStr(DateTime.now().toDate()));
        System.out.println(JodaTimeUtil.dateTimeToStr(DateTime.now(), "yyyy-MM-dd"));
        System.out.println(new Date() + "|" + DateTime.now() + "|" + new DateTime());
        System.err.println(new Date());
        System.err.println(JodaTimeUtil.getCurrentTimeStr_YMDHMS() + 222);
        System.err.println(JodaTimeUtil.getCurrentTimeStr_YMDHMS());
        Thread.sleep(8000);
        System.err.println(new Date());
        System.err.println(JodaTimeUtil.getCurrentTimeStr_YMDHMS() + 222);
        System.err.println(JodaTimeUtil.getCurrentTimeStr_YMDHMS());


        System.out.println("系统当前时间： " + JodaTimeUtil.getCurrentTimeStr_HMS());
        String dateString = "2017-06-29 14:11:28";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date2 = sdf.parse(dateString);

        System.out.println("String 转 Date" + date2);
        int minutesBetween = minutesBetween(date2);
        System.out.println(minutesBetween + " 分钟 ");
        System.out.println(minutesBetween - 15 + " 分钟 ");
        System.out.println("是否小于15分钟： ");
        System.out.println(minutesBetween <= 15);
        System.out.println("时间戳：" + timestamp());
    }
}
