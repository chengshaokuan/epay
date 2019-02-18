package com.csk.epay.utils.JsonUtil.gson;

import com.csk.epay.utils.JsonUtil.gson.adapter.NumberTypeAdapter;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.StringReader;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.*;

/**
 * @program: Cheng
 * @description: Gson工具类解析
 * 优势：数据量小（低于万）的时候速度有绝对优势
 * @author: Mr.Cheng
 * @create: 2018-07-20 10:32
 **/
@Slf4j
public class GsonUtil {



    private static Gson gson;

    static {
        GsonBuilder gsonBuilder = new GsonBuilder();
        //当使用GsonBuilder方式时属性为空的时候输出来的json字符串是有键值key的,显示形式是"key":null，而直接new出来的就没有"key":null的
        gsonBuilder.setPrettyPrinting().setDateFormat("yyyy-MM-dd HH:mm:ss");
        //禁止将部分特殊字符转义为unicode编码
        gsonBuilder.disableHtmlEscaping();
        registTypeAdapter(gsonBuilder);
        gson = gsonBuilder.create();
    }

    private static void registTypeAdapter (GsonBuilder gsonBuilder) {
        gsonBuilder.registerTypeAdapter(short.class, new NumberTypeAdapter(short.class));
        gsonBuilder.registerTypeAdapter(Short.class, new NumberTypeAdapter(Short.class));
        gsonBuilder.registerTypeAdapter(int.class, new NumberTypeAdapter(int.class));
        gsonBuilder.registerTypeAdapter(Integer.class, new NumberTypeAdapter(Integer.class));
        gsonBuilder.registerTypeAdapter(long.class, new NumberTypeAdapter(long.class));
        gsonBuilder.registerTypeAdapter(Long.class, new NumberTypeAdapter(Long.class));
        gsonBuilder.registerTypeAdapter(float.class, new NumberTypeAdapter(float.class));
        gsonBuilder.registerTypeAdapter(Float.class, new NumberTypeAdapter(Float.class));
        gsonBuilder.registerTypeAdapter(double.class, new NumberTypeAdapter(double.class));
        gsonBuilder.registerTypeAdapter(Double.class, new NumberTypeAdapter(Double.class));
        gsonBuilder.registerTypeAdapter(BigDecimal.class, new NumberTypeAdapter(BigDecimal.class));
        gsonBuilder.registerTypeAdapter(String.class, new NumberTypeAdapter(String.class));
    }

    /**
     * @Description: 将object对象转成json字符串
     * @param: object
     * @return: java.lang.String
     * @Author: Mr.Cheng
     * @Date: 16:09 2018/8/14
     */
    public static String beanToJson (Object object) {
        String json = null;
        if (gson != null) {
            json = gson.toJson(object);
        }
        return json;
    }

    /**
     * @Description: 将json转成特定的clazz的对象
     * @param: json
     * @param: clazz
     * @return: T
     * @Author: Mr.Cheng
     * @Date: 16:08 2018/8/14
     */
    public static <T> T gsonToBean (String json, Class<T> clazz) {
        T t = null;
        if (gson != null) {
            t = gson.fromJson(json, clazz);
        }
        return t;
    }

    /**
     * @Description:
     * @param: json
     * @param: type
     * @return: T
     * @Author: Mr.Cheng
     * @Date: 18:37 2018/9/26
     */
    public static <T> T gsonToBean (String json, Type type) {
        T t = null;
        if (gson != null) {
            t = gson.fromJson(json, type);
        }
        return t;
    }

    /**
     * @Description:
     * @param: json
     * @param: typeToken
     * @return: T
     * @Author: Mr.Cheng
     * @Date: 18:38 2018/9/26
     */
    public static <T> T gsonToBean (String json, TypeToken<T> typeToken) {
        T t = null;
        if (gson != null) {
            t = gson.fromJson(json, typeToken.getType());
        }
        return t;
    }

    /**
     * @Description: json字符串转成list
     * 泛型在编译期类型被擦除导致报错
     * @param: json
     * @param: cls
     * @return: java.util.List<T>
     * @Author: Mr.Cheng
     * @Date: 16:08 2018/8/14
     */
    public static <T> List<T> gsonToList (String json, Class<T> clazz) {
        List<T> list = null;
        if (gson != null) {
            //根据泛型返回解析指定的类型,TypeToken<List<T>>{}.getType()获取返回类型
            list = gson.fromJson(json, new TypeToken<List<T>>() {
            }.getType());
        }
        return list;
    }

    /**
     * @Description: 转成list
     * 解决泛型问题
     * @param: json
     * @param: cls
     * @return: java.util.List<T>
     * @Author: Mr.Cheng
     * @Date: 16:07 2018/8/14
     */
    public static <T> List<T> jsonToList (String json, Class<T> clazz) {
        List<T> list = null;
        if (gson != null) {
            JsonArray array = new JsonParser().parse(json).getAsJsonArray();
            for (final JsonElement elem : array) {
                list.add(gson.fromJson(elem, clazz));
            }
        }
        return list;
    }

    /**
     * @Description: json字符串转成list中有map的
     * @param: json
     * @return: java.util.List<Map <String,T>>
     * @Author: Mr.Cheng
     * @Date: 16:06 2018/8/14
     */
    public static <T> List<Map<String, T>> gsonToListMaps (String json) {
        List<Map<String, T>> list = null;
        if (gson != null) {
            list = gson.fromJson(json,
                    new TypeToken<List<Map<String, T>>>() {
                    }.getType());
        }
        return list;
    }

    /**
     * @Description: json字符串转成map的
     * @param: json
     * @return: Map<String                               ,                               T>
     * @Author: Mr.Cheng
     * @Date: 16:05 2018/8/14
     */
    public static <T> Map<String, T> gsonToMaps (String json) {
        Map<String, T> map = null;
        if (gson != null) {
            map = gson.fromJson(json, new TypeToken<Map<String, T>>() {
            }.getType());
        }
        return map;
    }

    /**
     * @Description: 不标准json解析
     * @param: jsonObj
     * @param: c
     * @return: T
     * @Author: Mr.Cheng
     * @Date: 10:43 2018/9/27
     */
    public static <T> T lenientFrom (Object jsonObj, Class<T> c) {
        JsonReader reader = new JsonReader(new StringReader(jsonObj.toString()));
        reader.setLenient(true);
        return gson.fromJson(reader, c);
    }

    /**
     * @Description: 不标准json解析
     * @param: jsonObj
     * @param: c
     * @return: T
     * @Author: Mr.Cheng
     * @Date: 10:43 2018/9/27
     */
    public static <T> T lenientFrom (String json, Type type) {
        JsonReader reader = new JsonReader(new StringReader(json));
        reader.setLenient(true);
        return gson.fromJson(reader, type);
    }

    /**
     * @Description: 不标准json解析
     * @param: jsonObj
     * @param: c
     * @return: T
     * @Author: Mr.Cheng
     * @Date: 10:43 2018/9/27
     */
    public static <T> T lenientFrom (String json, TypeToken<T> typeToken) {
        JsonReader reader = new JsonReader(new StringReader(json));
        reader.setLenient(true);
        return gson.fromJson(reader, typeToken.getType());
    }

    /**
     * @Description: 从json串中获取某个字段
     * @param: json
     * @param: key
     * @return: java.lang.String
     * @Author: Mr.Cheng
     * @Date: 10:50 2018/9/27
     */
    public static String getString (String json, String key) {
        if (StringUtils.isEmpty(json)) {
            return null;
        }
        String propertyValue;
        JsonElement jsonByKey = getJsonByKey(json, key);
        if (jsonByKey == null) {
            return null;
        }
        try {
            propertyValue = jsonByKey.getAsString();
        } catch (Exception e) {
            propertyValue = jsonByKey.toString();
        }
        return propertyValue;
    }

    /**
     * @Description: 从json串中获取某个数字
     * @param: json
     * @param: key
     * @return: java.lang.String
     * @Author: Mr.Cheng
     * @Date: 10:50 2018/9/27
     */
    public static Integer getInt (String json, String key) {
        if (StringUtils.isEmpty(json)) {
            return null;
        }
        JsonElement jsonByKey = getJsonByKey(json, key);
        if (jsonByKey == null) {
            return null;
        }
        try {
            return jsonByKey.getAsInt();
        } catch (Exception e) {
            log.error("gson get int error, json: {}, key: {}", json, key, e);
            return null;
        }
    }

    /**
     * @Description: 从json串中获取某个字段
     * @param: json
     * @param: key
     * @return: java.lang.String
     * @Author: Mr.Cheng
     * @Date: 10:50 2018/9/27
     */
    public static <T> ArrayList<T> getList (String json, String key) {
        if (StringUtils.isEmpty(json)) {
            return null;
        }
        JsonElement jsonByKey = getJsonByKey(json, key);
        ArrayList<T> ts = null;
        if (jsonByKey != null) {
            try {
                JsonArray jsonArray = jsonByKey.getAsJsonArray();
                ts = gsonToBean(jsonArray.toString(), new TypeToken<ArrayList<T>>() {
                });
            } catch (Exception e) {
                log.error("gson get list error, json: {}, key: {}", json, key, e);
            }
        }
        return ts;
    }

    private static JsonElement getJsonByKey (String json, String key) {
        JsonParser jsonParser = new JsonParser();
        JsonElement element;
        try {
            element = jsonParser.parse(json);
        } catch (JsonSyntaxException e) {
            log.error("gson get key from json error, json: {}, key: {}", json, key, e);
            return null;
        }
        JsonObject jsonObj = element.getAsJsonObject();
        return jsonObj.get(key);
    }

    /**
     * @Description: 向json中添加属性
     * @param: json
     * @param: key
     * @param: value
     * @return: java.lang.String
     * @Author: Mr.Cheng
     * @Date: 10:51 2018/9/27
     */
    public static <T> String add (String json, String key, T value) {
        JsonParser jsonParser = new JsonParser();
        JsonElement element = jsonParser.parse(json);
        JsonObject jsonObject = element.getAsJsonObject();
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
     * @Date: 10:51 2018/9/27
     */
    private static <T> void add (JsonObject jsonObject, String key, T value) {
        if (value instanceof String) {
            jsonObject.addProperty(key, (String) value);
        } else if (value instanceof Number) {
            jsonObject.addProperty(key, (Number) value);
        } else {
            jsonObject.addProperty(key, beanToJson(value));
        }
    }

    /**
     * @Description: 除去json中的某个属性
     * @param: json
     * @param: key
     * @return: java.lang.String
     * @Author: Mr.Cheng
     * @Date: 10:51 2018/9/27
     */
    public static String remove (String json, String key) {
        JsonParser jsonParser = new JsonParser();
        JsonElement element = jsonParser.parse(json);
        JsonObject jsonObj = element.getAsJsonObject();
        jsonObj.remove(key);
        return jsonObj.toString();
    }

    /**
     * @Description: 修改json中的属性
     * @param: json
     * @param: key
     * @param: value
     * @return: java.lang.String
     * @Author: Mr.Cheng
     * @Date: 10:59 2018/9/27
     */
    public static <T> String update (String json, String key, T value) {
        JsonParser jsonParser = new JsonParser();
        JsonElement element = jsonParser.parse(json);
        JsonObject jsonObject = element.getAsJsonObject();
        jsonObject.remove(key);
        add(jsonObject, key, value);
        return jsonObject.toString();
    }

    /**
     * @Description: 格式化Json(美化)
     * @param: json
     * @return: java.lang.String
     * @Author: Mr.Cheng
     * @Date: 10:59 2018/9/27
     */
    public static String format (String json) {
        JsonParser jp = new JsonParser();
        JsonElement je = jp.parse(json);
        return gson.toJson(je);
    }

    /**
     * @Description: 判断字符串是否是json
     * @param: json
     * @return: boolean
     * @Author: Mr.Cheng
     * @Date: 11:00 2018/9/27
     */
    public static boolean isJson (String json) {
        try {
            return new JsonParser().parse(json).isJsonObject();
        } catch (Exception e) {
            log.error("gson check json error, json: {}", json, e);
            return false;
        }
    }

}








