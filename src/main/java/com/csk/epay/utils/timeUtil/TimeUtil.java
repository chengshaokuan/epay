package com.csk.epay.utils.timeUtil;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @program: Cheng
 * @description: 时间工具类
 * @author: Mr.Cheng
 * @create: 2018-08-15 14:00
 **/
public abstract class TimeUtil {
    private final static Logger logger = LoggerFactory.getLogger(TimeUtil.class);

    private static final String TIMEFORMAT_NORMAL_YMDHMS = "yyyy-MM-dd HH:mm:ss";
    private static final String TIMEFORMAT_ENGLISH_YMDHMS = "MM/dd/yyyy HH:mm:ss";
    private static final String TIMEFORMAT_SHORT_YMDHMS = "yyyyMMddHHmmss";
    private static final String DATE_FORMAT_MINUTE = "yyyyMMddHHmm";
    private static final String DATE_FORMAT_SHORT = "yyyyMMdd";
    private static final String DATE_FORMAT_NORMAL = "yyyy-MM-dd";
    private static final String DATE_FORMAT_ENGLISH = "MM/dd/yyyy";
    private static final String MONTH_FORMAT = "yyyyMM";
    private static final String MONTH_DAY_FORMAT = "MM-dd";

    private static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat(DATE_FORMAT_NORMAL);
    private static final ResourceBundle MULTILANG = ResourceBundle.getBundle("i18n.timeUtil", Locale.getDefault());

    /**
     * @Description: 获取当前时间，返回格式为Date
     * @param:
     * @return: java.util.Date 返回时间类型 yyyy-MM-dd HH:mm:ss
     * @Author: Mr.Cheng
     * @Date: 19:12 2018/8/16
     */
    public static Date getNowDate () {
        SimpleDateFormat sdf = new SimpleDateFormat(TIMEFORMAT_NORMAL_YMDHMS);
        String dateString = sdf.format(new Date());
        ParsePosition pos = new ParsePosition(0);
        Date date = sdf.parse(dateString, pos);
        return date;
    }

    /**
     * @Description: 把日期字符串转换为日期类型
     * @param: dateStr 日期字符串
     * @return: java.util.Date
     * @Author: Mr.Cheng
     * @Date: 17:42 2018/8/16
     */
    public static Date convertAsDate (String dateStr) {
        SimpleDateFormat sdf = null;
        if (dateStr.matches("\\d{14}")) {
            sdf = new SimpleDateFormat(TIMEFORMAT_SHORT_YMDHMS);
        } else if (dateStr.matches("\\d{4}-\\d{1,2}-\\d{1,2} \\d{1,2}:\\d{1,2}:\\d{1,2}")) {
            sdf = new SimpleDateFormat(TIMEFORMAT_NORMAL_YMDHMS);
        } else if (dateStr.matches("\\d{1,2}/\\d{1,2}/\\d{4} \\d{1,2}:\\d{1,2}:\\d{1,2}")) {
            sdf = new SimpleDateFormat(TIMEFORMAT_ENGLISH_YMDHMS);
        } else if (dateStr.matches("\\d{4}" + MULTILANG.getString("multi.year")
                + "\\d{1,2}" + MULTILANG.getString("multi.month")
                + "\\d{1,2}" + MULTILANG.getString("multi.day")
                + " \\d{1,2}" + MULTILANG.getString("multi.hour")
                + "\\d{1,2}" + MULTILANG.getString("multi.minute")
                + "\\d{1,2}" + MULTILANG.getString("multi.second"))) {
            sdf = new SimpleDateFormat(MULTILANG.getString("multi.TIME_FORMAT_CHINA"));
        } else if (dateStr.matches("\\d{8}")) {
            sdf = new SimpleDateFormat(DATE_FORMAT_SHORT);
        } else if (dateStr.matches("\\d{4}-\\d{1,2}-\\d{1,2}")) {
            sdf = new SimpleDateFormat(DATE_FORMAT_NORMAL);
        } else if (dateStr.matches("\\d{1,2}/\\d{1,2}/\\d{4}")) {
            sdf = new SimpleDateFormat(DATE_FORMAT_ENGLISH);
        } else if (dateStr.matches("\\d{4}"
                + MULTILANG.getString("multi.year") + "\\d{1,2}"
                + MULTILANG.getString("multi.month") + "\\d{1,2}"
                + MULTILANG.getString("multi.day"))) {
            sdf = new SimpleDateFormat(MULTILANG.getString("multi.DATE_FORMAT_CHINA"));
        } else if (dateStr.matches("\\d{4}\\d{1,2}\\d{1,2}\\d{1,2}\\d{1,2}")) {
            sdf = new SimpleDateFormat(DATE_FORMAT_MINUTE);
        } else {
            return new Date();
        }
        ParsePosition pos = new ParsePosition(0);
        Date date = sdf.parse(dateStr, pos);
        return date;
    }

    /**
     * @Description: 字符串转日期格式
     * @param: strDate 日期
     * @param: pattern 格式
     * @return: java.util.Date
     * @Author: Mr.Cheng
     * @Date: 15:06 2018/8/17
     */
    public static synchronized Date parseDateFormat (String strDate, String pattern) {
        synchronized (SIMPLE_DATE_FORMAT) {
            SIMPLE_DATE_FORMAT.applyPattern(pattern);
            ParsePosition parsePosition = new ParsePosition(0);
            Date date = SIMPLE_DATE_FORMAT.parse(strDate, parsePosition);
            return date;
        }
    }

    /**
     * @Description: 得到时间字符串, 格式为 yyyy年MM月dd日 HH时mm分ss秒
     * @param: date 待格式化日期
     * @return: java.lang.String  返回格式化后的时间字符串
     * @Author: Mr.Cheng
     * @Date: 17:45 2018/8/16
     */
    public static String getTimeChinaString (Date date) {
        DateFormat fmt = new SimpleDateFormat(MULTILANG.getString("multi.TIME_FORMAT_CHINA"));
        return fmt.format(date);
    }

    /**
     * @Description: 得到日期字符串, 格式为 yyyy年MM月dd日 HH时mm分ss秒
     * @param: date 日期
     * @return: java.lang.String  中文日期，带时分秒
     * @Author: Mr.Cheng
     * @Date: 17:15 2018/8/16
     */
    public static String getTimeChinaStringS (Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(MULTILANG.getString("multi.TIME_FORMAT_CHINA_S"));
        return sdf.format(date);
    }

    /**
     * @Description: 得到日期字符串, 格式为 yyyy年MM月dd日
     * @param: date 待格式化日期
     * @return: java.lang.String 返回格式化后的日期字符串
     * @Author: Mr.Cheng
     * @Date: 10:39 2018/8/16
     */
    public static String getDateChinaString (Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(MULTILANG.getString("multi.DATE_FORMAT_CHINA"));
        return sdf.format(date);
    }

    /**
     * @Description: 得到时间字符串, 格式为 yyyy-MM-dd HH:mm:ss
     * @param: date 待格式化日期
     * @return: java.lang.String 返回格式化后的时间字符串
     * @Author: Mr.Cheng
     * @Date: 10:13 2018/8/16
     */
    public static String getTimeNormalString (Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(TIMEFORMAT_NORMAL_YMDHMS);
        return sdf.format(date);
    }

    /**
     * @Description: 得到时间字符串, 格式为 MM/dd/yyyy HH:mm:ss
     * @param: date 待格式化日期
     * @return: java.lang.String 返回格式化后的时间字符串
     * @Author: Mr.Cheng
     * @Date: 10:25 2018/8/16
     */
    public static String getTimeEnglishString (Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(TIMEFORMAT_ENGLISH_YMDHMS);
        return sdf.format(date);
    }

    /**
     * @Description: 得到时间字符串, 格式为 yyyyMMddHHmmss
     * @param: date 待格式化日期
     * @return: java.lang.String 返回格式化后的时间字符串
     * @Author: Mr.Cheng
     * @Date: 10:28 2018/8/16
     */
    public static String getTimeShortString (Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(TIMEFORMAT_SHORT_YMDHMS);
        return sdf.format(date);
    }

    /**
     * @Description: 将短时间格式时间转换为字符串 yyyy-MM-dd
     * @param: date
     * @return: java.lang.String
     * @Author: Mr.Cheng
     * @Date: 10:32 2018/8/16
     */
    public static String getDateString (Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NORMAL);
        return sdf.format(date);
    }

    /**
     * @Description: 得到时间字符串, 格式为 yyyyMM
     * @param: date
     * @return: java.lang.String
     * @Author: Mr.Cheng
     * @Date: 10:32 2018/8/16
     */
    public static String getMonthString (Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(MONTH_FORMAT);
        return sdf.format(date);
    }

    /**
     * @Description: 得到日期字符串, 格式为 MM/dd/yyyy
     * @param: date 待格式化日期
     * @return: java.lang.String 返回格式化后的日期字符串
     * @Author: Mr.Cheng
     * @Date: 10:40 2018/8/16
     */
    public static String getDateEnglishString (Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_ENGLISH);
        return sdf.format(date);
    }

    /**
     * @Description: 得到日期字符串, 格式为 yyyy-MM-dd
     * @param: date 待格式化日期
     * @return: java.lang.String 返回格式化后的日期字符串
     * @Author: Mr.Cheng
     * @Date: 10:41 2018/8/16
     */
    public static String getDateNormalString (Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NORMAL);
        return sdf.format(date);
    }

    /**
     * @Description: 得到日期字符串, 格式为 MM-dd
     * @param: date 待格式化日期
     * @return: java.lang.String 返回格式化后的日期字符串
     * @Author: Mr.Cheng
     * @Date: 10:42 2018/8/16
     */
    public static String getMonthDayDateNormalString (Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(MONTH_DAY_FORMAT);
        return sdf.format(date);
    }

    /**
     * @Description: 得到日期字符串, 格式为 yyyyMMdd
     * @param: date 待格式化日期
     * @return: java.lang.String 返回格式化后的日期字符串
     * @Author: Mr.Cheng
     * @Date: 10:42 2018/8/16
     */
    public static String getDateShortString (Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_SHORT);
        return sdf.format(date);
    }

    /**
     * @Description: 得到日期字符串
     * @param: date 待格式化日期
     * @param: string 格式
     * @return: java.lang.String 返回格式化后的日期字符串
     * @Author: Mr.Cheng
     * @Date: 11:05 2018/8/16
     */
    public static String getFormatString (Date date, String string) {
        SimpleDateFormat sdf = new SimpleDateFormat(string);
        return sdf.format(date);
    }

    /**
     * @Description: 根据用户传入的时间表示格式，返回当前时间的格式 如果是yyyyMMdd，注意字母y不能大写。
     * @param: sformat yyyyMMddhhmmss
     * @return: java.lang.String
     * @Author: Mr.Cheng
     * @Date: 16:12 2018/8/16
     */
    public static String getUserDate (String sformat) {
        Date currentTime = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat(sformat);
        String dateString = sdf.format(currentTime);
        return dateString;
    }

    /**
     * @Description: 获得当前日期，精确到毫秒
     * @param:
     * @return: java.sql.Timestamp
     * @Author: Mr.Cheng
     * @Date: 19:14 2018/8/15
     */
    public static Timestamp getNowInMillis () {
        Timestamp timeStamep = new Timestamp(System.currentTimeMillis());
        return timeStamep;
    }

    /**
     * 得到日期字符串,格式为 yyyy年MM月dd日
     *
     * @return 返回当前日期的字符串
     */
    public static String getNowDateChinaString () {
        return getDateChinaString(new Date());
    }

    /**
     * 得到日期字符串,格式为 MM/dd/yyyy
     *
     * @return 返回当前日期的字符串
     */
    public static String getNowDateEnglishString () {
        return getDateEnglishString(new Date());
    }

    /**
     * 得到日期字符串,格式为 yyyy-MM-dd
     *
     * @return 返回当前日期的字符串
     */
    public static String getNowDateNormalString () {
        return getDateNormalString(new Date());
    }

    /**
     * 得到时间字符串,格式为 yyyy年MM月dd日 HH时mm分ss秒
     *
     * @return 返回当前时间的字符串
     */
    public static String getNowTimeChinaString () {
        return getTimeChinaString(new Date());
    }

    /**
     * 得到时间字符串,格式为 MM/dd/yyyy HH:mm:ss
     *
     * @return 返回当前时间的字符串
     */
    public static String getNowTimeEnglishString () {
        return getTimeEnglishString(new Date());
    }

    /**
     * 得到时间字符串,格式为 yyyy-MM-dd HH:mm:ss
     *
     * @return 返回当前时间的字符串
     */
    public static String getNowTimeNormalString () {
        return getTimeNormalString(new Date());
    }

    /**
     * 得到时间字符串,格式为 yyyyMMddHHmmss
     *
     * @return 返回当前时间的字符串
     */
    public static String getNowTimeShortString () {
        return getTimeShortString(new Date());
    }

    /**
     * @Description: 以当前日期为准，往后推间隔天数，
     * 例如：当前时间2018-08-31，间隔时间2，返回2018-09-02
     * @param: interval 间隔天数
     * @return: java.lang.String
     * @Author: Mr.Cheng
     * @Date: 11:10 2018/8/16
     */
    public static String getNowDateNormalString (int interval) {
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.DATE, interval);
        return getDateNormalString(c.getTime());
    }

    /**
     * @Description: 以当前日期为准，往后推间隔天数，
     * 例如：当前时间2018-08-31，间隔时间2，返回09-02
     * @param: interval
     * @return: java.lang.String 日期字符串,格式为 MM-dd
     * @Author: Mr.Cheng
     * @Date: 11:15 2018/8/16
     */
    public static String getNowMonthNormalString (int interval) {
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.DATE, interval);
        return getMonthDayDateNormalString(c.getTime());
//        return getNowDateNormalString(interval).substring(5, 10);
    }

    /**
     * @Description: 根据输入日期向后累加interval天
     * @param: day 日期
     * @param: interval 天数
     * @return: java.lang.String
     * @Author: Mr.Cheng
     * @Date: 11:23 2018/8/16
     */
    public static String getIntervalDaysLater (String day, int interval) {
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(SIMPLE_DATE_FORMAT.parse(day));
        } catch (ParseException e) {
            logger.info("格式化日期发生异常:{}", e);
            return null;
        }
        c.add(Calendar.DATE, interval);
        return getDateNormalString(c.getTime());
    }

    /**
     * @Description: 获得日期集合
     * @param: beginDate 开始日期
     * @param: endDate 结束日期
     * @return: java.util.List<java.lang.String>
     * @Author: Mr.Cheng
     * @Date: 13:45 2018/8/16
     */
    public static List<String> getDateList (String beginDate, String endDate) {
        List<String> list = new ArrayList<String>();
        ParsePosition parsePosition = new ParsePosition(0);
        Calendar c1 = Calendar.getInstance(Locale.CHINA);
        c1.setTime(SIMPLE_DATE_FORMAT.parse(beginDate, parsePosition));
        Calendar c2 = Calendar.getInstance(Locale.CHINA);
        c2.setTime(SIMPLE_DATE_FORMAT.parse(endDate, parsePosition));
        long l1 = c1.getTimeInMillis();
        long l2 = c2.getTimeInMillis();
        int interval = (int) ((l2 - l1) / (1000 * 60 * 60 * 24));
        for (int i = 0; i <= interval; i++) {
            Calendar c = Calendar.getInstance();
            c.setTime(c1.getTime());
            c.add(Calendar.DATE, i);
            list.add(SIMPLE_DATE_FORMAT.format(c.getTime()));
        }
        return list;
    }

    /**
     * @Description: 获得本周开始的第一天的日期，规定一周开始的第一天为星期日
     * @param:
     * @return: java.lang.String
     * @Author: Mr.Cheng
     * @Date: 15:49 2018/8/16
     */
    public static String getFirstDayOfWeek () {
        Calendar calendar = Calendar.getInstance();
        int minus = calendar.get(GregorianCalendar.DAY_OF_WEEK);
        calendar.add(GregorianCalendar.DATE, -minus + 1);
        String begin = new java.sql.Date(calendar.getTime().getTime()).toString();
        return begin;
    }

    /*********************************************************/
    /**
     * @Description:
     * @param: date 日期
     * @param: field
     * @param: amount
     * @return: java.util.Date
     * @Author: Mr.Cheng
     * @Date: 17:59 2018/8/14
     */
    public static synchronized Date addDate (Date date, int field, int amount) {
        synchronized (SIMPLE_DATE_FORMAT) {
            Calendar c = Calendar.getInstance(Locale.CHINA);
            c.setTime(date);
            c.add(field, amount);
            return c.getTime();
        }
    }

    /**
     * @Description: 改变时间 譬如： changeDate(new Date(),Calendar.DATE, 2) 就是把当前时间加两天
     * @param: originDate 原始时间
     * @param: field 改变类型
     * @param: distance 长度
     * @return: java.util.Date 改变后的时间
     * @Author: Mr.Cheng
     * @Date: 18:19 2018/8/15
     */
    public static Date changeDate (Date originDate, int field, int distance) {
        Calendar c = Calendar.getInstance(Locale.CHINA);
        c.setTime(originDate);
        c.add(field, distance);
        return c.getTime();
    }

    /**
     * @Description: 比较两个日期大小
     * @param: date1 日期
     * @param: date2 日期
     * @return: int
     * @Author: Mr.Cheng
     * @Date: 18:21 2018/8/15
     */
    public static int compareDate (String date1, String date2) {
        SimpleDateFormat TIME_NORMAL = new SimpleDateFormat(TIMEFORMAT_NORMAL_YMDHMS);
        int i = 0;
        try {
            if (StringUtils.isNotBlank(date1) && StringUtils.isNotBlank(date2)) {
                long ldate1 = TIME_NORMAL.parse(date1).getTime();
                long ldate2 = TIME_NORMAL.parse(date2).getTime();
                if (ldate1 > ldate2) {
                    i = 1;
                } else if (ldate1 == ldate2) {
                    i = 0;
                } else {
                    i = -1;
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return i;
    }

    /**
     * @Description: 把 2010-02-04 12:12:34 这样的时间转换为 20100204121234
     * @param: origin 原始时间字符串
     * @return: java.lang.String  转换后的字符串
     * @Author: Mr.Cheng
     * @Date: 18:11 2018/8/16
     */
    public static String convertAsShortDateString (String origin) {
        return origin == null ? origin : origin.replaceAll("[-|:|\\s|年|月|日|时|分|秒|/]", "");
    }

    /**
     * @Description: 获得输入日期所在周的第一天（周一为第一天）
     * @param: day 日期
     * @return: java.lang.String
     * @Author: Mr.Cheng
     * @Date: 14:44 2018/8/16
     */
    public static String getFirstDayOfWeek (String day) {
        Calendar c1 = Calendar.getInstance(Locale.CHINA);
        ParsePosition parsePosition = new ParsePosition(0);
        c1.setTime(SIMPLE_DATE_FORMAT.parse(day, parsePosition));
        c1.setFirstDayOfWeek(Calendar.MONDAY);
        c1.set(Calendar.DAY_OF_WEEK, c1.getFirstDayOfWeek());
        return SIMPLE_DATE_FORMAT.format(c1.getTime());
    }

    /**
     * @Description: 获得输入日期所在周的最后一天（周日为最后一天）
     * @param: day 日期
     * @return: java.lang.String
     * @Author: Mr.Cheng
     * @Date: 14:44 2018/8/16
     */
    public static String getLastDayOfWeek (String day) {
        Calendar c1 = Calendar.getInstance(Locale.CHINA);
        ParsePosition parsePosition = new ParsePosition(0);
        c1.setTime(SIMPLE_DATE_FORMAT.parse(day,parsePosition));
        c1.setFirstDayOfWeek(Calendar.MONDAY);
        c1.set(Calendar.DAY_OF_WEEK, c1.getFirstDayOfWeek() + 6);
        return SIMPLE_DATE_FORMAT.format(c1.getTime());
    }

    /**
     * @Description: 计算输入日期所在月的最后一天
     * @param: day 日期
     * @return: java.lang.String
     * @Author: Mr.Cheng
     * @Date: 14:44 2018/8/16
     */
    public static String getLastDayOfMonth (String day) throws ParseException {
        Calendar c1 = Calendar.getInstance(Locale.CHINA);
        c1.setTime(SIMPLE_DATE_FORMAT.parse(day));
        c1.set(Calendar.DAY_OF_MONTH, 1);
        c1.add(Calendar.MONTH, 1);
        c1.add(Calendar.DAY_OF_MONTH, -1);
        return SIMPLE_DATE_FORMAT.format(c1.getTime());
    }

    /**
     * @Description: 计算输入日期所在月的第一天
     * @param: day 日期
     * @return: java.lang.String
     * @Author: Mr.Cheng
     * @Date: 14:44 2018/8/16
     */
    public static String getFirstDayOfMonth (String day) throws ParseException {
        Calendar c1 = Calendar.getInstance(Locale.CHINA);
        c1.setTime(SIMPLE_DATE_FORMAT.parse(day));
        c1.set(Calendar.DAY_OF_MONTH, 1);
        return SIMPLE_DATE_FORMAT.format(c1.getTime());
    }

    /**
     * @Description: 是否是当月的第一天
     * @param: day 日期
     * @return: java.lang.String
     * @Author: Mr.Cheng
     * @Date: 14:44 2018/8/16
     */
    public static boolean isFirstDayOfMonth (String day) throws ParseException {
        return day.equals(getFirstDayOfMonth(day)) ? true : false;
    }

    /**
     * @Description: 是否是当月的最后一天
     * @param: day 日期
     * @return: java.lang.String
     * @Author: Mr.Cheng
     * @Date: 14:44 2018/8/16
     */
    public static boolean isLastDayOfMonth (String day) throws ParseException {
        return day.equals(getLastDayOfMonth(day)) ? true : false;
    }

    /**
     * @Description: 是否是当前周的第一天
     * @param: day 日期
     * @return: boolean
     * @Author: Mr.Cheng
     * @Date: 14:55 2018/8/16
     */
    public static boolean isFirstDayOfWeek (String day) throws ParseException {
        return day.equals(getFirstDayOfWeek(day)) ? true : false;
    }

    /**
     * @Description: 是否是当前周的最后一天
     * @param: day 日期
     * @return: boolean
     * @Author: Mr.Cheng
     * @Date: 14:55 2018/8/16
     */
    public static boolean isLastDayOfWeek (String day) throws ParseException {
        return day.equals(getLastDayOfWeek(day)) ? true : false;
    }

    /**
     * 计算两个日期之间相差的小时数
     *
     * @param date1
     * @param date2
     * @return date1>date2时结果<0,  date1=date2时结果=0, date2>date1时结果>0
     */
    public static int hoursBetween (Date date1, Date date2) {
        DateFormat sdf = new SimpleDateFormat("yyyyMMddHH");
        Calendar cal = Calendar.getInstance();
        try {
            Date d1 = sdf.parse(TimeUtil.getFormatString(date1, "yyyyMMddHH"));
            Date d2 = sdf.parse(TimeUtil.getFormatString(date2, "yyyyMMddHH"));
            cal.setTime(d1);
            long time1 = cal.getTimeInMillis();
            cal.setTime(d2);
            long time2 = cal.getTimeInMillis();
            return Integer.parseInt(String.valueOf(((time2 - time1) / 3600000L)));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 计算两个日期相差的时间
     */
    public static void getBetweenDate (String dateStart, String dateStop) {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHH");
        Date d1 = null;
        Date d2 = null;
        try {
            d1 = format.parse(dateStart);
            d2 = format.parse(dateStop);
            //毫秒ms
            long diff = d2.getTime() - d1.getTime();
            long diffSeconds = diff / 1000 % 60;
            long diffMinutes = diff / (60 * 1000) % 60;
            long diffHours = diff / (60 * 60 * 1000) % 24;
            long diffDays = diff / (24 * 60 * 60 * 1000);

            System.out.print("两个时间相差：");
            System.out.print(diffDays + " 天, ");
            System.out.print(diffHours + " 小时, ");
            System.out.print(diffMinutes + " 分钟, ");
            System.out.print(diffSeconds + " 秒.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 计算两个日期之间相差的分钟数
     *
     * @param date1
     * @param date2
     * @return date1>date2时结果<0,  date1=date2时结果=0, date2>date1时结果>0
     */
    public static int minuteBetweenByDateStr (Date date1, Date date2) {
        DateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm");
        Calendar cal = Calendar.getInstance();
        try {
            Date d1 = sdf.parse(TimeUtil.getFormatString(date1, "yyyyMMddHHmm"));
            Date d2 = sdf.parse(TimeUtil.getFormatString(date2, "yyyyMMddHHmm"));
            cal.setTime(d1);
            long time1 = cal.getTimeInMillis();
            cal.setTime(d2);
            long time2 = cal.getTimeInMillis();
            return Integer.parseInt(String.valueOf(((time2 - time1) / 60000L)));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 计算两个日期之间相差的秒数
     *
     * @param date1
     * @param date2
     * @return date1>date2时结果<0,  date1=date2时结果=0, date2>date1时结果>0
     */
    public static int secondBetweenByDateStr (Date date1, Date date2) {
        DateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        Calendar cal = Calendar.getInstance();
        try {
            Date d1 = sdf.parse(TimeUtil.getFormatString(date1, "yyyyMMddHHmmss"));
            Date d2 = sdf.parse(TimeUtil.getFormatString(date2, "yyyyMMddHHmmss"));
            cal.setTime(d1);
            long time1 = cal.getTimeInMillis();
            cal.setTime(d2);
            long time2 = cal.getTimeInMillis();
            return Integer.parseInt(String.valueOf(((time2 - time1) / 1000L)));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * @Description: 输入一个整数类型的字符串, 然后转换成时分秒的形式输出
     * 例如：输入568
     * 返回结果为：00:09:28
     * 输入null或者“”
     * 返回结果为:00:00:00
     * @param: value
     * @return: java.lang.String
     * @Author: Mr.Cheng
     * @Date: 16:10 2018/8/16
     */
    public static String getHHMMSS (String value) {
        String hour = "00";
        String minute = "00";
        String second = "00";
        if (value != null && !value.trim().equals("")) {
            int v_int = Integer.valueOf(value);
            hour = v_int / 3600 + "";//获得小时;
            minute = v_int % 3600 / 60 + "";//获得小时;
            second = v_int % 3600 % 60 + "";//获得小时;
        }
        return (hour.length() > 1 ? hour : "0" + hour) + ":" + (minute.length() > 1 ? minute : "0" + minute) + ":" + (second.length() > 1 ? second : "0" + second);
    }

    /**
     * @Description: 判断是否润年
     * @param: ddate
     * @return: boolean
     * @Author: Mr.Cheng
     * @Date: 19:55 2018/8/15
     */
    public static boolean isLeapYear (String ddate) {
        /**
         * 详细设计： 1.被400整除是闰年，否则： 2.不能被4整除则不是闰年 3.能被4整除同时不能被100整除则是闰年
         * 3.能被4整除同时能被100整除则不是闰年
         */
        Date d = convertAsDate(ddate);
        GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
        gc.setTime(d);
        int year = gc.get(Calendar.YEAR);
        if ((year % 400) == 0) {
            return true;
        } else if ((year % 4) == 0) {
            if ((year % 100) == 0) {
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

    /**
     * @Description: 产生周序列, 即得到当前时间所在的年度是第几周
     * @param:
     * @return: java.lang.String
     * @Author: Mr.Cheng
     * @Date: 16:20 2018/8/16
     */
    public static String getSeqWeek () {
        Calendar c = Calendar.getInstance(Locale.CHINA);
        String week = Integer.toString(c.get(Calendar.WEEK_OF_YEAR));
        if (week.length() == 1) {
            week = "0" + week;
        }
        String year = Integer.toString(c.get(Calendar.YEAR));
        return year + week;
    }

    /**
     * @Description: 根据一个日期，返回是星期几的字符串
     * @param: sdate
     * @return: java.lang.String
     * @Author: Mr.Cheng
     * @Date: 16:02 2018/8/16
     */
    public static String getWeek (String sdate) {
        // 再转换为时间
        Date date = convertAsDate(sdate);
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        // int hour=c.get(Calendar.DAY_OF_WEEK);
        // hour中存的就是星期几了，其范围 1~7
        // 1=星期日 7=星期六，其他类推
        return new SimpleDateFormat("EEEE").format(c.getTime());
    }

    /**
     * @Description: 通过一个日期，和第几天，来返回这个日期所在周的第几天
     * @param: sdate 日期
     * @param: num 第几天
     * @return: java.lang.String
     * @Author: Mr.Cheng
     * @Date: 16:26 2018/8/16
     */
    public static String getWeek (String sdate, String num) {
        // 再转换为时间
        Date dd = convertAsDate(sdate);
        Calendar c = Calendar.getInstance();
        c.setTime(dd);
        if (num.equals("1")) // 返回星期一所在的日期
        {
            c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        } else if (num.equals("2")) // 返回星期二所在的日期
        {
            c.set(Calendar.DAY_OF_WEEK, Calendar.TUESDAY);
        } else if (num.equals("3")) // 返回星期三所在的日期
        {
            c.set(Calendar.DAY_OF_WEEK, Calendar.WEDNESDAY);
        } else if (num.equals("4")) // 返回星期四所在的日期
        {
            c.set(Calendar.DAY_OF_WEEK, Calendar.THURSDAY);
        } else if (num.equals("5")) // 返回星期五所在的日期
        {
            c.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
        } else if (num.equals("6")) // 返回星期六所在的日期
        {
            c.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
        } else if (num.equals("0")) // 返回星期日所在的日期
        {
            c.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        }
        return new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
    }

    /**
     * @Description: 判断二个时间是否在同一个周
     * @param: date1
     * @param: date2
     * @return: boolean
     * @Author: Mr.Cheng
     * @Date: 19:54 2018/8/15
     */
    public static boolean isSameWeekDates (Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal1.setTime(date1);
        cal2.setTime(date2);
        int subYear = cal1.get(Calendar.YEAR) - cal2.get(Calendar.YEAR);
        if (0 == subYear) {
            if (cal1.get(Calendar.WEEK_OF_YEAR) == cal2.get(Calendar.WEEK_OF_YEAR)) {
                return true;
            }
        } else if (1 == subYear && 11 == cal2.get(Calendar.MONTH)) {
            // 如果12月的最后一周横跨来年第一周的话则最后一周即算做来年的第一周
            if (cal1.get(Calendar.WEEK_OF_YEAR) == cal2.get(Calendar.WEEK_OF_YEAR)) {
                return true;
            }
        } else if (-1 == subYear && 11 == cal1.get(Calendar.MONTH)) {
            if (cal1.get(Calendar.WEEK_OF_YEAR) == cal2.get(Calendar.WEEK_OF_YEAR)) {
                return true;
            }
        }
        return false;
    }

    /**
     * @Description: 返回一个随机数
     * @param: i
     * @return: java.lang.String
     * @Author: Mr.Cheng
     * @Date: 19:51 2018/8/15
     */
    public static String getRandom (int i) {
        Random jjj = new Random();
        if (i == 0) {
            return "";
        }
        String jj = "";
        for (int k = 0; k < i; k++) {
            jj = jj + jjj.nextInt(9);
        }
        return jj;
    }

    /**
     * @Description: 取得数据库主键 生成格式为yyyymmddhhmmss+k位随机数
     * @param: k 表示是取几位随机数，可以自己定
     * @return: java.lang.String
     * @Author: Mr.Cheng
     * @Date: 16:32 2018/8/16
     */
    public static String getNo (int k) {
        return getUserDate("yyyyMMddhhmmss") + getRandom(k);
    }

    /**
     * @Description: 距离现在几天的时间是多少
     * 获得一个时间字符串，格式为：yyyy-MM-dd HH:mm:ss
     * day  如果为整数，表示未来时间
     * 如果为负数，表示过去时间
     * @param: day
     * @return: java.lang.String
     * @Author: Mr.Cheng
     * @Date: 17:09 2018/8/16
     */
    public static String getFromNow (int day) {
        SimpleDateFormat TIME_NORMAL = new SimpleDateFormat(TIMEFORMAT_NORMAL_YMDHMS);
        Date date = new Date();
        long dateTime = (date.getTime() / 1000) + day * 24 * 60 * 60;
        date.setTime(dateTime * 1000);
        return TIME_NORMAL.format(date);
    }

    /**
     * @Description: 获取当前时间是上午还是下午
     * @param:
     * @return: java.lang.String
     * @Author: Mr.Cheng
     * @Date: 19:02 2018/8/15
     */
    private static String getDateAMPM () {
        GregorianCalendar ca = new GregorianCalendar();
        //结果为“0”是上午     结果为“1”是下午
        int i = ca.get(GregorianCalendar.AM_PM);
        return i == 0 ? "AM" : "PM";
    }

    /**
     * 二个小时时间间的差值,必须保证二个时间都是"HH:MM"的格式，返回字符型的分钟
     */
    public static String getTwoHour (String st1, String st2) {
        String[] kk = null;
        String[] jj = null;
        kk = st1.split(":");
        jj = st2.split(":");
        if (Integer.parseInt(kk[0]) < Integer.parseInt(jj[0])) {
            return "0";
        } else {
            double y = Double.parseDouble(kk[0]) + Double.parseDouble(kk[1]) / 60;
            double u = Double.parseDouble(jj[0]) + Double.parseDouble(jj[1]) / 60;
            if ((y - u) > 0) {
                return y - u + "";
            } else {
                return "0";
            }
        }
    }

    /**
     * 时间前推或后推分钟,其中JJ表示分钟.
     */
    public static String getPreTime (String sj1, String jj) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String mydate1 = "";
        try {
            Date date1 = format.parse(sj1);
            long Time = (date1.getTime() / 1000) + Integer.parseInt(jj) * 60;
            date1.setTime(Time * 1000);
            mydate1 = format.format(date1);
        } catch (Exception e) {
        }
        return mydate1;
    }

    /**
     * 得到一个时间延后或前移几天的时间,nowdate(yyyy-mm-dd)为时间,delay为前移或后延的天数
     */
    public static String getNextDay (String nowdate, String delay) {
        try {
            String mdate = "";
            Date d = convertAsDate(nowdate);
            long myTime = (d.getTime() / 1000) + Integer.parseInt(delay) * 24 * 60 * 60;
            d.setTime(myTime * 1000);
            mdate = SIMPLE_DATE_FORMAT.format(d);
            return mdate;
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * 返回美国时间格式 26 Apr 2006
     *
     * @param str
     * @return
     */
    public static String getEDate (String str) {
        ParsePosition pos = new ParsePosition(0);
        Date strtodate = SIMPLE_DATE_FORMAT.parse(str, pos);
        String j = strtodate.toString();
        String[] k = j.split(" ");
        return k[2] + k[1].toUpperCase() + k[5].substring(2, 4);
    }

    /**
     * 获取当前时间的前一天时间
     *
     * @param cl
     * @return
     */
    private static String getBeforeDay (Calendar cl) {
        //使用roll方法进行向前回滚
        //cl.roll(Calendar.DATE, -1);
        //使用set方法直接进行设置
        // int day = cl.get(Calendar.DATE);
        cl.add(Calendar.DATE, -1);
        return SIMPLE_DATE_FORMAT.format(cl.getTime());
    }

    /**
     * 获取当前时间的后一天时间
     *
     * @param cl
     * @return
     */
    private static String getAfterDay (Calendar cl) {
        //使用roll方法进行回滚到后一天的时间
        //cl.roll(Calendar.DATE, 1);
        //使用set方法直接设置时间值
        cl.add(Calendar.DATE, 1);
        return SIMPLE_DATE_FORMAT.format(cl.getTime());
    }

    /**
     * Description:得到时间字符串,格式为 M月d日
     *
     * @param day 相隔几天
     * @return 时间字符串, 格式为 M月d日
     * @Author WangShutao Create Date: 2013-1-16 下午4:19:18
     */
    public static String getMonthDay (int day) {
        DateFormat fmt = new SimpleDateFormat("M" + MULTILANG.getString("multi.month") + "d"
                + MULTILANG.getString("multi.day"));
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.DATE, day);
        return fmt.format(c.getTime());
    }

    /**
     * Description: 计算输入日期所在周是否跨月
     *
     * @param day 日期
     * @return boolean
     * @throws ParseException 异常。
     * @Author WangShutao Create Date: 2012-12-20 下午4:33:21
     */
    public static boolean isDayBetweenMonthAndWeek (String day) throws ParseException {
        String firstDayOfMonth = getFirstDayOfMonth(day);
        String firstDayOfWeek = getFirstDayOfWeek(day);
        Pattern p = Pattern.compile("\\d{4}-\\d{2}-01");
        Matcher m = p.matcher(day);
        if (firstDayOfWeek.compareTo(firstDayOfMonth) < 0 && !m.matches()) {
            return true;
        } else {
            return false;
        }
    }

    /***************************************************************************
     * //nd=1表示返回的值中包含年度 //yf=1表示返回的值中包含月份 //rq=1表示返回的值中包含日期 //format表示返回的格式 1
     * 以年月日中文返回 2 以横线-返回 // 3 以斜线/返回 4 以缩写不带其它符号形式返回 // 5 以点号.返回
     **************************************************************************/
    public static String getStringDateMonth (String sdate, String nd, String yf, String rq, String format) {
        Date currentTime = new Date();
        String dateString = SIMPLE_DATE_FORMAT.format(currentTime);
        String s_nd = dateString.substring(0, 4); // 年份
        String s_yf = dateString.substring(5, 7); // 月份
        String s_rq = dateString.substring(8, 10); // 日期
        String sreturn = "";
        //roc.util.MyChar mc = new roc.util.MyChar();
        if (sdate == null || sdate.equals("")) {
            if (nd.equals("1")) {
                sreturn = s_nd;
                // 处理间隔符
                if (format.equals("1")) {
                    sreturn = sreturn + "年";
                } else if (format.equals("2")) {
                    sreturn = sreturn + "-";
                } else if (format.equals("3")) {
                    sreturn = sreturn + "/";
                } else if (format.equals("5")) {
                    sreturn = sreturn + ".";
                }
            }
            // 处理月份
            if (yf.equals("1")) {
                sreturn = sreturn + s_yf;
                if (format.equals("1")) {
                    sreturn = sreturn + "月";
                } else if (format.equals("2")) {
                    sreturn = sreturn + "-";
                } else if (format.equals("3")) {
                    sreturn = sreturn + "/";
                } else if (format.equals("5")) {
                    sreturn = sreturn + ".";
                }
            }
            // 处理日期
            if (rq.equals("1")) {
                sreturn = sreturn + s_rq;
                if (format.equals("1")) {
                    sreturn = sreturn + "日";
                }
            }
        } else {
            // 不是空值，也是一个合法的日期值，则先将其转换为标准的时间格式
            s_nd = sdate.substring(0, 4); // 年份
            s_yf = sdate.substring(5, 7); // 月份
            s_rq = sdate.substring(8, 10); // 日期
            if (nd.equals("1")) {
                sreturn = s_nd;
                // 处理间隔符
                if (format.equals("1")) {
                    sreturn = sreturn + "年";
                } else if (format.equals("2")) {
                    sreturn = sreturn + "-";
                } else if (format.equals("3")) {
                    sreturn = sreturn + "/";
                } else if (format.equals("5")) {
                    sreturn = sreturn + ".";
                }
            }
            // 处理月份
            if (yf.equals("1")) {
                sreturn = sreturn + s_yf;
                if (format.equals("1")) {
                    sreturn = sreturn + "月";
                } else if (format.equals("2")) {
                    sreturn = sreturn + "-";
                } else if (format.equals("3")) {
                    sreturn = sreturn + "/";
                } else if (format.equals("5")) {
                    sreturn = sreturn + ".";
                }
            }
            // 处理日期
            if (rq.equals("1")) {
                sreturn = sreturn + s_rq;
                if (format.equals("1")) {
                    sreturn = sreturn + "日";
                }
            }
        }
        return sreturn;
    }

    /**
     * @Description: 比较两个日期天数
     * @param: strBegin 开始时间
     * @param: strEnd 结束时间
     * @return: int
     * @Author: Mr.Cheng
     * @Date: 18:59 2018/8/15
     */
    public int getDifferDays (String strBegin, String strEnd) {
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        Date date1 = null, date2 = null;
        int days = 0;
        try {
            date1 = f.parse(strBegin);
            date2 = f.parse(strEnd);
            long day = 24L * 60L * 60L * 1000L;
            days = (int) ((date2.getTime() - date1.getTime()) / day);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return days;
    }

    public static void main (String[] args) throws ParseException {
//        for (int i = 1; i < 3000; i++) {
//            if (i % 2 == 1) {
//                System.out.println("A");
//            } else {
//                System.out.println("B");
//            }
//        }
            String str = "ch en  g ";
            String trim = str.trim();
            System.out.println(trim);

    }

}
