package com.csk.epay.utils.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Hashtable;
import java.util.regex.Pattern;

/**
 * @program: Cheng
 * @description: 身份证号码的格式：610821-20061222-612-X
 * 由18位数字组成：前6位为地址码，第7至14位为出生日期码，第15至17位为顺序码，
 * 第18位为校验码。检验码分别是0-10共11个数字，当检验码为“10”时，为了保证公民身份证号码18位，所以用“X”表示。
 * 虽然校验码为“X”不能更换，但若需全用数字表示，只需将18位公民身份号码转换成15位居民身份证号码，去掉第7至8位和最后1位3个数码。
 * 当今的身份证号码有15位和18位之分。1985年我国实行居民身份证制度，当时签发的身份证号码是15位的，1999年签发的身份证由于年份的扩展（由两位变为四位）和末尾加了效验码，就成了18位。
 * （1）前1、2位数字表示：所在省份的代码；
 * （2）第3、4位数字表示：所在城市的代码；
 * （3）第5、6位数字表示：所在区县的代码；
 * （4）第7~14位数字表示：出生年、月、日；
 * （5）第15、16位数字表示：所在地的派出所的代码；
 * （6）第17位数字表示性别：奇数表示男性，偶数表示女性
 * （7）第18位数字是校检码：根据一定算法生成
 * @author: Mr.Cheng
 * @create: 2018-07-20 10:32
 **/
public class IdCardVerification {

    private static final Logger log = LoggerFactory.getLogger(IdCardVerification.class);

    public static final String VALIDITY = "该身份证有效!";
    public static final String LACKDIGITS = "身份证号码长度应该为15位或18位!";
    public static final String LASTOFNUMBER = "身份证15位号码都应为数字;18位号码除最后一位外,都应为数字!";
    public static final String INVALIDBIRTH = "身份证出生日期无效!";
    public static final String INVALIDSCOPE = "身份证生日不在有效范围!";
    public static final String INVALIDMONTH = "身份证月份无效!";
    public static final String INVALIDDAY = "身份证日期无效!";
    public static final String CODINGERROR = "身份证地区编码错误!";
    public static final String INVALIDCALIBRATION = "身份证校验码无效,不是合法的身份证号码!";
    
    public static final String ISDATE = "^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))?$";
    public static final String ISNUMERIC = "[0-9]*";
    public static final String ISCARD = "\\d{15}|\\d{17}[x,X,0-9]";

    /**
     * @Description: 检验身份证号码是否符合规范
     * @param: idCard  身份证号码
     * @return: java.lang.String 错误信息或成功信息
     * @Author: Mr.Cheng
     * @Date: 11:24 2018/7/20
     */
    public static String IDCardValidate (String idCard) {
        // 记录错误信息
        String tipInfo = VALIDITY;
        String Ai = "";
        // 判断号码的长度 15位或18位
        if (idCard.trim().length() != 15 && idCard.trim().length() != 18) {
            tipInfo = LACKDIGITS;
            return tipInfo;
        }
        // 18位身份证前17位位数字，如果是15位的身份证则所有号码都为数字
        if (idCard.trim().length() == 18) {
            Ai = idCard.substring(0, 17);
        } else if (idCard.trim().length() == 15) {
            //15位转18位，在第六位之后加上19，再在最后加上验证码。
            Ai = idCard.substring(0, 6) + "19" + idCard.substring(6, 15);
        }
        if (isNumeric(Ai) == false) {
            tipInfo = LASTOFNUMBER;
            return tipInfo;
        }
        // 判断出生年月是否有效
        String strYear = Ai.substring(6, 10);// 年份
        String strMonth = Ai.substring(10, 12);// 月份
        String strDay = Ai.substring(12, 14);// 日期
        if (isDate(strYear + "-" + strMonth + "-" + strDay) == false) {
            tipInfo = INVALIDBIRTH;
            return tipInfo;
        }
        GregorianCalendar gc = new GregorianCalendar();
        SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");
        try {
            //判断身份证上的年份和当前年份比较
            if ((gc.get(Calendar.YEAR) - Integer.parseInt(strYear)) > 150
                    || (gc.getTime().getTime() - s.parse(strYear + "-" + strMonth + "-" + strDay).getTime()) < 0) {
                tipInfo = INVALIDSCOPE;
                return tipInfo;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (Integer.parseInt(strMonth) > 12 || Integer.parseInt(strMonth) == 0) {
            tipInfo = INVALIDMONTH;
            return tipInfo;
        }
        if (Integer.parseInt(strDay) > 31 || Integer.parseInt(strDay) == 0) {
            tipInfo = INVALIDDAY;
            return tipInfo;
        }
        // 判断地区码是否有效
        Hashtable<String, String> areacode = GetAreaCode();
        // 如果身份证前两位的地区码不在Hashtable，则地区码有误
        if (areacode.get(Ai.substring(0, 2)) == null) {
            tipInfo = CODINGERROR;
            return tipInfo;
        }
        if (isVarifyCode(Ai, idCard) == false) {
            tipInfo = INVALIDCALIBRATION;
            log.info(INVALIDCALIBRATION);
            return tipInfo;
        }
        return tipInfo;
    }

    /**
     * @Description: 原始身份证号码。
     * @param: idCard
     * @return: boolean
     * @Author: Mr.Cheng
     * @Date: 11:11 2018/8/27
     */
    public static boolean checkIdentity (String idCard) {
        boolean isIDCard = false;
        // 可能是一个身份证
        if (Pattern.matches(ISCARD,idCard)) {
            isIDCard = true;
            // 如果是18的身份证，则校验18位的身份证。15位的身份证暂不校验
            if (idCard.length() == 18) {
                String idCard15 = id18to15(idCard);
                String idCard18 = id15to18(idCard15);
                if (!idCard.equalsIgnoreCase(idCard18)) {
                    isIDCard = false;
                }
            } else if (idCard.length() == 15) {
                isIDCard = true;
            } else {
                isIDCard = false;
            }
        }
        return isIDCard;
    }

    /**
     * @Description: 根据身份证获取出生日期
     * @param: idCard 身份证
     * @return: java.lang.String
     * @Author: Mr.Cheng
     * @Date: 11:14 2018/8/27
     */
    public static String getBrithday (String idCard) {
        idCard = id15to18(idCard);
        return idCard.substring(6, 14);
    }

    /**
     * @Description: 根据身份证获取性别; 1:男,0:女
     * @param: idCard 身份证
     * @return: int
     * @Author: Mr.Cheng
     * @Date: 11:15 2018/8/27
     */
    public static int getGender (String idCard) {
        int sex = 1;
        idCard = id15to18(idCard);
        String id17 = idCard.substring(16, 17);
        if (Integer.parseInt(id17) % 2 == 0) {
            sex = 0;
        }
        return sex;
    }

    /**
     * @Description: 根据身份证获取户籍地址:前2位是省,3、4位是地级市,5、6位是县区
     * @param: idCard 身份证
     * @return: java.lang.String[]
     * @Author: Mr.Cheng
     * @Date: 11:15 2018/8/27
     */
    public static String[] getAddressCode (String idCard) {
        idCard = id15to18(idCard);
        String address = idCard.substring(0, 6);
        // 完整的六位是县区
        String area = address;
        // 市:截取前四位,后两位补零
        String city = address.substring(0, 4).concat("00");
        // 省:截取前两位位,后四位补零
        String province = address.substring(0, 2).concat("0000");
        return new String[]{area, city, province};
    }

    /**
     * @Description: 15位身份证号码转化为18位的身份证。如果是18位的身份证则直接返回，不作任何变化。
     * @param: idCard 15位的有效身份证号码
     * @return: java.lang.String 返回18位的有效身份证
     * @Author: Mr.Cheng
     * @Date: 10:20 2018/8/27
     */
    public static String id15to18 (String idCard) {
        idCard = idCard.trim();
        StringBuffer idCard18 = new StringBuffer(idCard);
        // 校验码值
        char[] checkBit = {'1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2'};
        int sum = 0;
        // 15位的身份证
        if (idCard != null && idCard.length() == 15) {
            idCard18.insert(6, "19");
            for (int index = 0; index < idCard18.length(); index++) {
                char c = idCard18.charAt(index);
                int ai = Integer.parseInt(new Character(c).toString());
                // 加权因子的算法
                int wi = ((int) Math.pow(2, idCard18.length() - index)) % 11;
                sum = sum + ai * wi;
            }
            // 取模
            int indexOfCheckBit = sum % 11;
            idCard18.append(checkBit[indexOfCheckBit]);
        }
        return idCard18.toString();
    }

    /**
     * @Description: 转化18位身份证位15位身份证。如果输入的是15位的身份证则不做任何转化，直接返回。
     * @param: idCard  18位身份证号码
     * @return: java.lang.String
     * @Author: Mr.Cheng
     * @Date: 11:10 2018/8/27
     */
    public static String id18to15 (String idCard) {
        idCard = idCard.trim();
        StringBuffer idCard15 = new StringBuffer(idCard);
        if (idCard != null && idCard.length() == 18) {
            idCard15.delete(17, 18);
            idCard15.delete(6, 8);
        }
        return idCard15.toString();
    }

    /**
     * @Description:
     * 判断第18位校验码是否正确 第18位校验码的计算方式：
     * 1. 对前17位数字本体码加权求和 公式为：S = Sum(Ai * Wi), i =
     * 0, ... , 16 其中Ai表示第i个位置上的身份证号码数字值，Wi表示第i位置上的加权因子，其各位对应的值依次为：
     * 7 9 10 5 8 4 2 1 6 3 7 9 10 5 8 4 2
     * 2. 用11对计算结果取模 Y = mod(S, 11)
     * 3. 根据模的值得到对应的校验码
     * 对应关系为： Y值： 0 1 2 3 4 5 6 7 8 9 10 校验码： 1 0 X 9 8 7 6 5 4 3 2
     * @param: Ai
     * @param: idCard
     * @return: boolean
     * @Author: Mr.Cheng
     * @Date: 11:47 2019/1/28
     */
    private static boolean isVarifyCode (String Ai, String idCard) {
        String[] VarifyCode = {"1", "0", "X", "9", "8", "7", "6", "5", "4", "3", "2"};
        String[] Wi = {"7", "9", "10", "5", "8", "4", "2", "1", "6", "3", "7", "9", "10", "5", "8", "4", "2"};
        int sum = 0;
        for (int i = 0; i < 17; i++) {
            sum = sum + Integer.parseInt(String.valueOf(Ai.charAt(i))) * Integer.parseInt(Wi[i]);
        }
        int modValue = sum % 11;
        String strVerifyCode = VarifyCode[modValue];
        Ai = Ai + strVerifyCode;
        if (idCard.length() == 18) {
            if (Ai.equals(idCard) == false) {
                return false;
            }
        }
        return true;
    }

    /**
     * @Description: 将所有地址编码保存在一个Hashtable中
     * @param:
     * @return: java.util.Hashtable<java.lang.String,java.lang.String>
     * @Author: Mr.Cheng
     * @Date: 11:23 2018/7/20
     */
    private static Hashtable<String, String> GetAreaCode () {
        Hashtable<String, String> hashtable = new Hashtable<String, String>();
        hashtable.put("11", "北京");
        hashtable.put("12", "天津");
        hashtable.put("13", "河北");
        hashtable.put("14", "山西");
        hashtable.put("15", "内蒙古");
        hashtable.put("21", "辽宁");
        hashtable.put("22", "吉林");
        hashtable.put("23", "黑龙江");
        hashtable.put("31", "上海");
        hashtable.put("32", "江苏");
        hashtable.put("33", "浙江");
        hashtable.put("34", "安徽");
        hashtable.put("35", "福建");
        hashtable.put("36", "江西");
        hashtable.put("37", "山东");
        hashtable.put("41", "河南");
        hashtable.put("42", "湖北");
        hashtable.put("43", "湖南");
        hashtable.put("44", "广东");
        hashtable.put("45", "广西");
        hashtable.put("46", "海南");
        hashtable.put("50", "重庆");
        hashtable.put("51", "四川");
        hashtable.put("52", "贵州");
        hashtable.put("53", "云南");
        hashtable.put("54", "西藏");
        hashtable.put("61", "陕西");
        hashtable.put("62", "甘肃");
        hashtable.put("63", "青海");
        hashtable.put("64", "宁夏");
        hashtable.put("65", "新疆");
        hashtable.put("71", "台湾");
        hashtable.put("81", "香港");
        hashtable.put("82", "澳门");
        hashtable.put("91", "国外");
        return hashtable;
    }

    /**
     * @Description: 判断字符串是否为数字, 0-9重复0次或者多次
     * @param: strnum
     * @return: boolean true, 符合; false, 不符合。
     * @Author: Mr.Cheng
     * @Date: 11:22 2018/7/20
     */
    private static boolean isNumeric (String strNum) {
        if (Pattern.matches(ISNUMERIC,strNum)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * @Description: 判断字符串出生日期是否符合正则表达式：包括年月日，闰年、平年和每月31天、30天和闰月的28天或者29天
     * @param: strDate
     * @return: boolean true, 符合; false, 不符合。
     * @Author: Mr.Cheng
     * @Date: 11:22 2018/7/20
     */
    public static boolean isDate (String strDate) {
        if (Pattern.matches(ISDATE,strDate)) {
            return true;
        } else {
            return false;
        }
    }
}
