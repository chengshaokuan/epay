package com.csk.epay.utils.timeUtil;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import static java.time.temporal.ChronoField.DAY_OF_MONTH;
import static java.time.temporal.ChronoField.DAY_OF_YEAR;
import static java.time.temporal.ChronoUnit.MONTHS;
import static java.time.temporal.ChronoUnit.YEARS;
import static java.time.temporal.TemporalAdjusters.*;

/**
 * @program: epay
 * @description: 基于Java8以后，时间工具类
 * @author: Mr.Cheng
 * @create: 2019-01-23 18:39
 **/
public class NewTimeUtil {


    //获取默认时间格式: yyyy-MM-dd HH:mm:ss
    private static final DateTimeFormatter DEFAULT_DATETIME_FORMATTER = TimeFormat.LONG_DATE_PATTERN_LINE.formatter;
    //获取短的时间格式: yyyy-MM-dd
    private static final DateTimeFormatter SHORT_DATETIME_FORMATTER = TimeFormat.SHORT_DATE_PATTERN_LINE.formatter;

    /**
     * @Description: String字符串转化为LocalDateTime
     * @param: timeStr
     * @return: java.time.LocalDateTime
     * @Author: Mr.Cheng
     * @Date: 9:42 2019/1/24
     */
    public static LocalDateTime parseTime (String timeStr) {
        return LocalDateTime.parse(timeStr, DEFAULT_DATETIME_FORMATTER);
    }

    /**
     * @Description:
     * @param: timeStr
     * @param: format
     * @return: java.time.LocalDateTime
     * @Author: Mr.Cheng
     * @Date: 10:15 2019/1/24
     */
    public static LocalDateTime parseTime (String timeStr, TimeFormat format) {
        return LocalDateTime.parse(timeStr, format.formatter);
    }

    /**
     * @Description: LocalDateTime转化为字符串
     * @param: time
     * @return: java.lang.String
     * @Author: Mr.Cheng
     * @Date: 14:33 2019/1/24
     */
    public static String parseTime (LocalDateTime time) {
        //直接.toString()方法，会生成默认格式
        return DEFAULT_DATETIME_FORMATTER.format(time);
    }

    /**
     * @Description:  转化为自定义字符串
     * @param: time
     * @param: format 时间格式
     * @return: java.lang.String
     * @Author: Mr.Cheng
     * @Date: 14:34 2019/1/24
     */
    public static String parseTime (LocalDateTime time, TimeFormat format) {
        return format.formatter.format(time);
    }

    /**
     * @Description: 获取指定格式的当前时间:yyyy-MM-dd HH:mm:ss
     * @param:
     * @return: java.lang.String
     * @Author: Mr.Cheng
     * @Date: 11:13 2019/1/24
     */
    public static String getCurrentDatetimeStr () {
        return DEFAULT_DATETIME_FORMATTER.format(LocalDateTime.now());
    }

    /**
     * @Description: 获取指定格式的当前时间:yyyy-MM-dd HH:mm:ss
     * @param:
     * @return: java.lang.String
     * @Author: Mr.Cheng
     * @Date: 11:13 2019/1/24
     */
    public static LocalDateTime getCurrentDatetime () {
        return LocalDateTime.parse(getCurrentDatetimeStr(),DEFAULT_DATETIME_FORMATTER);
    }

    /**
     * @Description: 获取当前自定义格式的时间
     * @param: format 时间格式
     * @return: java.lang.String
     * @Author: Mr.Cheng
     * @Date: 11:15 2019/1/24
     */
    public static String getCurrentDatetime (TimeFormat format) {
        return format.formatter.format(LocalDateTime.now());
    }

    /**
     * @Description: 获取时间间隔，并格式化为XXXX年XX月XX日
     * @param: lt   较小时间
     * @param: gt   较大时间
     * @return: java.lang.String
     * @Author: Mr.Cheng
     * @Date: 11:18 2019/1/24
     */
    public static String localDateDiffFormat (LocalDate lt, LocalDate gt) {
        Period p = Period.between(lt, gt);
        String str = String.format(" %d年 %d月 %d日", p.getYears(), p.getMonths(), p.getDays());
        return str;
    }

    /**
     * @Description: 获取Duration（持续时间）
     * @param: lt   较小时间
     * @param: gt   较大时间
     * @return: java.time.Duration
     * @Author: Mr.Cheng
     * @Date: 11:19 2019/1/24
     */
    public static Duration localTimeDiff (LocalTime lt, LocalTime gt) {
        Duration d = Duration.between(lt, gt);
        return d;
    }

    /**
     * @Description: 获取时间间隔（毫秒）
     * @param: lt   较小时间
     * @param: gt   较大时间
     * @return: long
     * @Author: Mr.Cheng
     * @Date: 11:20 2019/1/24
     */
    public static long millisDiff (LocalTime lt, LocalTime gt) {
        Duration d = Duration.between(lt, gt);
        return d.toMillis();
    }

    /**
     * @Description: 获取时间间隔（天）
     * @param: lt 较小时间
     * @param: gt 较大时间
     * @return: long
     * @Author: Mr.Cheng
     * @Date: 11:20 2019/1/24
     */
    public static long daysDiff (LocalDate lt, LocalDate gt) {
        long daysDiff = ChronoUnit.DAYS.between(lt, gt);
        return daysDiff;
    }

    /**
     * @Description: 创建一个新的日期，它的值为上月的第一天
     * @param: date
     * @return: java.time.LocalDate
     * @Author: Mr.Cheng
     * @Date: 11:21 2019/1/24
     */
    public static LocalDate getFirstDayOfLastMonth (LocalDate date) {
        return date.with((temporal) -> temporal.with(DAY_OF_MONTH, 1).plus(-1, MONTHS));
    }

    /**
     * @Description: 创建一个新的日期，它的值为上月的最后一天
     * @param: date
     * @return: java.time.LocalDate
     * @Author: Mr.Cheng
     * @Date: 11:21 2019/1/24
     */
    public static LocalDate getLastDayOfLastMonth (LocalDate date) {
        return date.with((temporal) -> temporal.with(DAY_OF_MONTH, temporal.range(DAY_OF_MONTH).getMaximum()).plus(-1, MONTHS));
    }

    /**
     * @Description: 创建一个新的日期，它的值为当月的第一天
     * @param: date
     * @return: java.time.LocalDate
     * @Author: Mr.Cheng
     * @Date: 11:21 2019/1/24
     */
    public static LocalDate getFirstDayOfMonth (LocalDate date) {
        return date.with(firstDayOfMonth());
    }

    /**
     * @Description: 创建一个新的日期，它的值为当月的最后一天
     * @param: date
     * @return: java.time.LocalDate
     * @Author: Mr.Cheng
     * @Date: 11:22 2019/1/24
     */
    public static LocalDate getLastDayOfMonth (LocalDate date) {
        return date.with(lastDayOfMonth());
    }

    /**
     * @Description: 创建一个新的日期，它的值为下月的第一天
     * @param: date
     * @return: java.time.LocalDate
     * @Author: Mr.Cheng
     * @Date: 11:22 2019/1/24
     */
    public static LocalDate getFirstDayOfNextMonth (LocalDate date) {
        return date.with(firstDayOfNextMonth());
    }

    /**
     * @Description: 创建一个新的日期，它的值为下月的最后一天
     * @param: date
     * @return: java.time.LocalDate
     * @Author: Mr.Cheng
     * @Date: 11:23 2019/1/24
     */
    public static LocalDate getLastDayOfNextMonth (LocalDate date) {
        return date.with((temporal) -> temporal.with(DAY_OF_MONTH, temporal.range(DAY_OF_MONTH).getMaximum()).plus(1, MONTHS));
    }

    /**
     * @Description: 创建一个新的日期，它的值为上年的第一天
     * @param: date
     * @return: java.time.LocalDate
     * @Author: Mr.Cheng
     * @Date: 11:23 2019/1/24
     */
    public static LocalDate getFirstDayOfLastYear (LocalDate date) {
        return date.with((temporal) -> temporal.with(DAY_OF_YEAR, 1).plus(-1, YEARS));
    }

    /**
     * @Description: 创建一个新的日期，它的值为上年的最后一天
     * @param: date
     * @return: java.time.LocalDate
     * @Author: Mr.Cheng
     * @Date: 11:23 2019/1/24
     */
    public static LocalDate getLastDayOfLastYear (LocalDate date) {
        return date.with((temporal) -> temporal.with(DAY_OF_YEAR, temporal.range(DAY_OF_YEAR).getMaximum()).plus(-1, YEARS));
    }

    /**
     * @Description: 创建一个新的日期，它的值为当年的第一天
     * @param: date
     * @return: java.time.LocalDate
     * @Author: Mr.Cheng
     * @Date: 11:23 2019/1/24
     */
    public static LocalDate getFirstDayOfYear (LocalDate date) {
        return date.with(firstDayOfYear());
    }

    /**
     * @Description: 创建一个新的日期，它的值为今年的最后一天
     * @param: date
     * @return: java.time.LocalDate
     * @Author: Mr.Cheng
     * @Date: 11:24 2019/1/24
     */
    public static LocalDate getLastDayOfYear (LocalDate date) {
        return date.with(lastDayOfYear());
    }

    /**
     * @Description: 创建一个新的日期，它的值为明年的第一天
     * @param: date
     * @return: java.time.LocalDate
     * @Author: Mr.Cheng
     * @Date: 11:24 2019/1/24
     */
    public static LocalDate getFirstDayOfNextYear (LocalDate date) {
        return date.with(firstDayOfNextYear());
    }

    /**
     * @Description: 创建一个新的日期，它的值为同一个月中，第一个符合星期几要求的值
     * @param: date
     * @param: dayOfWeek
     * @return: java.time.LocalDate
     * @Author: Mr.Cheng
     * @Date: 11:24 2019/1/24
     */
    public static LocalDate getFirstInMonth (LocalDate date, DayOfWeek dayOfWeek) {
        return date.with(firstInMonth(dayOfWeek));
    }

    /**
     * @Description: 创建一个新的日期，并将其值设定为日期调整后或者调整前，第一个符合指定星 期几要求的日期
     * @param: date
     * @param: dayOfWeek
     * @return: java.time.LocalDate
     * @Author: Mr.Cheng
     * @Date: 11:25 2019/1/24
     */
    public static LocalDate getNext (LocalDate date, DayOfWeek dayOfWeek) {
        return date.with(next(dayOfWeek));
    }

    /**
     * @Description: 创建一个新的日期，并将其值设定为日期调整后或者调整前，第一个符合指定星 期几要求的日期
     * @param: date
     * @param: dayOfWeek
     * @return: java.time.LocalDate
     * @Author: Mr.Cheng
     * @Date: 11:25 2019/1/24
     */
    public static LocalDate getPrevious (LocalDate date, DayOfWeek dayOfWeek) {
        return date.with(previous(dayOfWeek));
    }

    /**
     * @Description: 创建一个新的日期，并将其值设定为日期调整后或者调整前，
     * 第一个符合指定星 期几要求的日期，如果该日期已经符合要求，直接返回该对象
     * @param: date
     * @param: dayOfWeek
     * @return: java.time.LocalDate
     * @Author: Mr.Cheng
     * @Date: 11:25 2019/1/24
     */
    public static LocalDate getNextOrSame (LocalDate date, DayOfWeek dayOfWeek) {
        return date.with(nextOrSame(dayOfWeek));
    }

    /**
     * @Description: 时间格式
     * @Author: Mr.Cheng
     * @Date: 11:15 2019/1/24
     */
    public enum TimeFormat {
        //短时间格式
        SHORT_DATE_PATTERN_CHINESE("yyyy年MM月dd日"),
        SHORT_DATE_PATTERN_LINE("yyyy-MM-dd"),
        SHORT_DATE_PATTERN_SLASH("yyyy/MM/dd"),
        SHORT_DATE_PATTERN_DOUBLE_SLASH("yyyy\\MM\\dd"),
        SHORT_DATE_PATTERN_NONE("yyyyMMdd"),
        //长时间格式
        LONG_DATE_PATTERN_LINE("yyyy-MM-dd HH:mm:ss"),
        LONG_DATE_PATTERN_SLASH("yyyy/MM/dd HH:mm:ss"),
        LONG_DATE_PATTERN_DOUBLE_SLASH("yyyy\\MM\\dd HH:mm:ss"),
        LONG_DATE_PATTERN_NONE("yyyyMMdd HH:mm:ss"),
        //长时间格式 带毫秒
        LONG_DATE_PATTERN_WITH_MILSEC_LINE("yyyy-MM-dd HH:mm:ss.SSS"),
        LONG_DATE_PATTERN_WITH_MILSEC_SLASH("yyyy/MM/dd HH:mm:ss.SSS"),
        LONG_DATE_PATTERN_WITH_MILSEC_DOUBLE_SLASH("yyyy\\MM\\dd HH:mm:ss.SSS"),
        LONG_DATE_PATTERN_WITH_MILSEC_NONE("yyyyMMdd HH:mm:ss.SSS");
        private transient DateTimeFormatter formatter;

        TimeFormat (String pattern) {
            formatter = DateTimeFormatter.ofPattern(pattern);
        }
    }

}
