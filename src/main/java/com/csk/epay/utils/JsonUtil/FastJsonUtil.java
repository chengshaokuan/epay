package com.csk.epay.utils.JsonUtil;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.csk.epay.entity.Users;
import com.csk.epay.utils.timeUtil.NewTimeUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * @program: Cheng
 * @description: Fastjson工具类
 * 优势：数据量大（高于万）的时候速度有绝对优势，API简洁
 * @author: Mr.Cheng
 * @create: 2018-08-14 16:30
 **/
@Slf4j
public class FastJsonUtil {

    private static final SerializeConfig config;

    static {
        config = new SerializeConfig();
        //全局修改日期格式
        JSON.DEFFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
//         //Date时间格式调整
//        String dataFormat = "yyyy-MM-dd HH:mm:ss";
//        config.put(java.util.Date.class, new SimpleDateFormatSerializer(dataFormat));
//         //使用和json-lib兼容的日期输出格式
//        config.put(java.util.Date.class, new JSONLibDataFormatSerializer());
//        // 使用和json-lib兼容的日期输出格式
//        config.put(java.sql.Date.class, new JSONLibDataFormatSerializer());
    }

    private static final SerializerFeature[] features = {
            // 输出空置字段
            SerializerFeature.WriteMapNullValue,
            // list字段如果为null，输出为[]，而不是null
            SerializerFeature.WriteNullListAsEmpty,
            // 数值字段如果为null，输出为0，而不是null
            SerializerFeature.WriteNullNumberAsZero,
            // Boolean字段如果为null，输出为false，而不是null
            SerializerFeature.WriteNullBooleanAsFalse,
            // 字符类型字段如果为null，输出为""，而不是null
            SerializerFeature.WriteNullStringAsEmpty,

            //采用默认时间格式输出(与JSON.DEFFAULT_DATE_FORMAT配合使用)
            SerializerFeature.WriteDateUseDateFormat
            //使用ISO-8601日期格式
//           SerializerFeature.UseISO8601DateFormat
    };

    /**
     * @Description: 将object对象转成json字符串
     * @param: object
     * @return: java.lang.String
     * @Author: Mr.Cheng
     * @Date: 16:44 2018/8/14
     */
//    public static String beanToJson (Object object) {
//        return JSON.toJSONString(object, config, features);
//    }

    /**
     * @Description:
     * @param: object
     * @return: java.lang.String
     * @Author: Mr.Cheng
     * @Date: 10:59 2019/1/29
     */
    public static String beanToJson (Object object) {
        return JSON.toJSONString(object);
    }

    /**
     * @Description: 将object对象转成json字符串,自定义时间格式
     * @param: object
     * @param: TimeFormat
     * @return: java.lang.String
     * @Author: Mr.Cheng
     * @Date: 11:32 2018/9/28
     */
    public static String beanToJsonWithDateFormat (Object object,String timeFormat) {
        return JSON.toJSONStringWithDateFormat(object, timeFormat);
    }

    /**
     * @Description: 将map转化为string
     * @param: map
     * @return: java.lang.String
     * @Author: Mr.Cheng
     * @Date: 16:42 2018/8/14
     */
    public static String mapToJson (Map map) {
        return JSONObject.toJSONString(map);
    }

    /**
     * @Description:
     * @param: object
     * @return: java.lang.String
     * @Author: Mr.Cheng
     * @Date: 16:44 2018/8/14
     */
    public static String beanToJsonNoFeatures (Object object) {
        return JSON.toJSONString(object, config);
    }

    /**
     * @Description: 将string转化为类、序列化的json字符串
     * @param: text
     * @return: java.lang.Object
     * @Author: Mr.Cheng
     * @Date: 16:44 2018/8/14
     */
    public static Object jsonToBean (String string) {
        return JSON.parse(string);
    }

    /**
     * @Description:
     * @param: text
     * @param: clazz
     * @return: T
     * @Author: Mr.Cheng
     * @Date: 16:43 2018/8/14
     */
    public static <T> T jsonToBean (String string, Class<T> clazz) {
        return JSON.parseObject(string, clazz);
    }

    /**
     * @Description: JSON解析
     * @param: string
     * @param: typeReference
     * @return: T
     * @Author: Mr.Cheng
     * @Date: 18:26 2018/9/25
     */
    public static <T> T jsonToBean (String string, TypeReference<T> typeReference) {
        return JSON.parseObject(string, typeReference.getType());
    }

    /**
     * @Description: 转换为数组
     * @param: text
     * @param: clazz
     * @return: java.lang.Object[]
     * @Author: Mr.Cheng
     * @Date: 16:43 2018/8/14
     */
    public static <T> Object[] jsonToArray (String string, Class<T> clazz) {
        return JSON.parseArray(string, clazz).toArray();
    }

    /**
     * @Description: 转换为List
     * @param: text
     * @param: clazz
     * @return: java.util.List<T>
     * @Author: Mr.Cheng
     * @Date: 16:43 2018/8/14
     */
    public static <T> List<T> jsonToList (String string, Class<T> clazz) {
        return JSON.parseArray(string, clazz);
    }

    /**
     * @Description: json字符串转化为map
     * @param: string
     * @return: java.util.Map
     * @Author: Mr.Cheng
     * @Date: 16:42 2018/8/14
     */
    public static Map jsonToMap (String string) {
        return JSONObject.parseObject(string);
    }

    /**
     * @Description:  从json串中获取某个字段
     * @param: string
     * @param: key
     * @return: java.lang.String
     * @Author: Mr.Cheng
     * @Date: 18:48 2018/9/25
     */
    public static String getString (String string, String key) {
        if (StringUtils.isEmpty(string)) {
            return null;
        }
        JSONObject jsonObject = JSONObject.parseObject(string);
        if (jsonObject == null) {
            return null;
        }
        try {
            return jsonObject.getString(key);
        } catch (Exception e) {
            log.error("fastjson get string error, json: {}, key: {}", string, key, e);
            return null;
        }
    }

    /**
     * @Description:  从json串中获取某个字段
     * @param: json
     * @param: key
     * @return: java.lang.Integer
     * @Author: Mr.Cheng
     * @Date: 18:47 2018/9/25
     */
    public static Integer getInt (String string, String key) {
        if (StringUtils.isEmpty(string)) {
            return null;
        }
        JSONObject jsonObject = JSONObject.parseObject(string);
        if (jsonObject == null) {
            return null;
        }
        try {
            return jsonObject.getInteger(key);
        } catch (Exception e) {
            log.error("fastjson get int error, json: {}, key: {}", string, key, e);
            return null;
        }
    }

    /**
     * @Description:  从json串中获取某个字段
     * @param: string
     * @param: key
     * @param: clazz
     * @return: java.util.List<T> boolean, 默认为false
     * @Author: Mr.Cheng
     * @Date: 18:44 2018/9/25
     */
    public static <T> List<T> getList (String string, String key, Class<T> clazz) {
        if (StringUtils.isEmpty(string)) {
            return null;
        }
        JSONObject jsonObject = JSONObject.parseObject(string);
        List<T> list = null;
        if (jsonObject != null) {
            try {
                JSONArray jsonArray = jsonObject.getJSONArray(key);
                list = jsonArray.toJavaList(clazz);
            } catch (Exception e) {
                log.error("fastjson get list error, json: {}, key: {}", string, key, e);
            }
        }
        return list;
    }

    /**
     * @Description: 向json中添加属性
     * @param: string
     * @param: key
     * @param: value
     * @return: java.lang.String
     * @Author: Mr.Cheng
     * @Date: 18:42 2018/9/25
     */
    public static <T> String add (String string, String key, T value) {
        JSONObject jsonObject = JSONObject.parseObject(string);
        add(jsonObject, key, value);
        return jsonObject.toString();
    }

    /**
     * @Description: 向json中添加属性
     * @param: jsonObject
     * @param: key
     * @param: value
     * @return: void
     * @Author: Mr.Cheng
     * @Date: 18:41 2018/9/25
     */
    private static <T> void add (JSONObject jsonObject, String key, T value) {
        if (value instanceof String || value instanceof Number || value instanceof Boolean || value instanceof Byte[]) {
            jsonObject.put(key, value);
        } else {
            jsonObject.put(key, beanToJson(value));
        }
    }

    /**
     * @Description: 除去json中的某个属性
     * @param: string
     * @param: key
     * @return: java.lang.String
     * @Author: Mr.Cheng
     * @Date: 18:41 2018/9/25
     */
    public static String remove (String string, String key) {
        JSONObject jsonObject = JSONObject.parseObject(string);
        jsonObject.remove(key);
        return jsonObject.toString();
    }

    /**
     * @Description: 修改json中的属性
     * @param: string
     * @param: key
     * @param: value
     * @return: java.lang.String
     * @Author: Mr.Cheng
     * @Date: 18:40 2018/9/25
     */
    public static <T> String update (String string, String key, T value) {
        JSONObject jsonObject = JSONObject.parseObject(string);
        add(jsonObject, key, value);
        return jsonObject.toString();
    }

    /**
     * @Description: 格式化Json(美化)
     * @param: string
     * @return: java.lang.String
     * @Author: Mr.Cheng
     * @Date: 18:39 2018/9/25
     */
    public static String format (String string) {
        JSONObject jsonObject = JSONObject.parseObject(string);
        return JSON.toJSONString(jsonObject, SerializerFeature.PrettyFormat);
    }

    /**
     * @Description: 判断字符串是否是json
     * @param: string
     * @return: boolean
     * @Author: Mr.Cheng
     * @Date: 18:39 2018/9/25
     */
    public static boolean isJson (String string) {
        try {
            JSON.parse(string);
            return true;
        } catch (Exception e) {
            log.error("fastjson check json error, json: {}", string, e);
            return false;
        }
    }


    public static void main (String[] args) {
        Users user = new Users();
        user.setName("c//s");
        user.setAge(12);
        user.setCreateTime(NewTimeUtil.getCurrentDatetime());
        user.setBirthday(LocalDate.now());
        String string = "{\"name\":\"成/c\",\"age\":3,\"createTime\":\"2019/01/01 02:02:02\"}";

        Users o =  FastJsonUtil.jsonToBean(string,Users.class);

        System.out.println(o.toString());
        String s = FastJsonUtil.beanToJson(user);
        System.out.println(s);

    }
}
