package com.csk.epay.utils.JsonUtil;

import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @program: Cheng
 * @description: 处理工具类
 * @author: Mr.Cheng
 * @create: 2018-07-20 10:32
 **/
@Slf4j
public class JsonUtil {

    /**
     * JSONObject对象转JavaBean
     *
     * @param /JSONObject
     * @param /JavaBean的class
     * @return 转换结果（异常情况下返回null）
     */
    public static Object jsonToBean (JSONObject json, Class cls) {
        Object obj = null;
        try {
            obj = cls.newInstance();
            // 取出Bean里面的所有方法
            Method[] methods = cls.getMethods();
            for (int i = 0; i < methods.length; i++) {
                // 取出方法名  
                String methodName = methods[i].getName();
                // 取出方法的类型  
                Class[] clss = methods[i].getParameterTypes();
                if (clss.length != 1) {
                    continue;
                }
                // 若是方法名不是以set开始的则退出本次循环
                if (methodName.indexOf("set") < 0) {
                    continue;
                }
                // 类型
                String type = clss[0].getSimpleName();
                String key = methodName.substring(3, 4).toLowerCase() + methodName.substring(4);
                // 如果map里有该key
                if (json.containsKey(key) && json.get(key) != null) {
                    setValue(type, json.get(key), methods[i], obj);
                }
            }
        } catch (Exception ex) {
            log.info("JSONObject转JavaBean失败", ex);
        }
        return obj;
    }

    /**
     * 根据key从JSONObject对象中取得对应值
     *
     * @param json
     * @param key
     * @return
     * @throws JSONException
     */
    public static String getString (JSONObject json, String key) throws JSONException {
        String retVal = null;
        if (json.isEmpty()) {
            retVal = "";
        } else {
            retVal = json.getString(key);
        }
        return retVal;
    }

    /**
     * 给JavaBean的每个属性设值
     *
     * @param type
     * @param value
     * @param method
     * @param bean
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws ParseException
     */
    private static void setValue (String type, Object value, Method method, Object bean) {
        if (value != null && !"".equals(value)) {
            try {
                if ("String".equals(type)) {
                    method.invoke(bean, new Object[]{value});
                } else if ("int".equals(type) || "Integer".equals(type)) {
                    method.invoke(bean, new Object[]{new Integer("" + value)});
                } else if ("double".equals(type) || "Double".equals(type)) {
                    method.invoke(bean, new Object[]{new Double("" + value)});
                } else if ("float".equals(type) || "Float".equals(type)) {
                    method.invoke(bean, new Object[]{new Float("" + value)});
                } else if ("long".equals(type) || "Long".equals(type)) {
                    method.invoke(bean, new Object[]{new Long("" + value)});
                } else if ("int".equals(type) || "Integer".equals(type)) {
                    method.invoke(bean, new Object[]{new Integer("" + value)});
                } else if ("boolean".equals(type) || "Boolean".equals(type)) {
                    method.invoke(bean, new Object[]{Boolean.valueOf("" + value)});
                } else if ("BigDecimal".equals(type)) {
                    method.invoke(bean, new Object[]{new BigDecimal("" + value)});
                } else if ("Date".equals(type)) {
                    Class dateType = method.getParameterTypes()[0];
                    if ("java.util.Date".equals(dateType.getName())) {
                        java.util.Date date = null;
                        if ("String".equals(value.getClass().getSimpleName())) {
                            String time = String.valueOf(value);
                            String format = null;
                            if (time.indexOf(":") > 0) {
                                if (time.indexOf(":") == time.lastIndexOf(":")) {
                                    format = "yyyy-MM-dd H:mm";
                                } else {
                                    format = "yyyy-MM-dd H:mm:ss";
                                }
                            } else {
                                format = "yyyy-MM-dd";
                            }
                            SimpleDateFormat sf = new SimpleDateFormat();
                            sf.applyPattern(format);
                            date = sf.parse(time);
                        } else {
                            date = (java.util.Date) value;
                        }
                        if (date != null) {
                            method.invoke(bean, new Object[]{date});
                        }
                    } else if ("java.sql.Date".equals(dateType.getName())) {
                        Date date = null;
                        if ("String".equals(value.getClass().getSimpleName())) {
                            String time = String.valueOf(value);
                            String format = null;
                            if (time.indexOf(":") > 0) {
                                if (time.indexOf(":") == time.lastIndexOf(":")) {
                                    format = "yyyy-MM-dd H:mm";
                                } else {
                                    format = "yyyy-MM-dd H:mm:ss";
                                }
                            } else {
                                format = "yyyy-MM-dd";
                            }
                            SimpleDateFormat sf = new SimpleDateFormat();
                            sf.applyPattern(format);
                            date = new Date(sf.parse(time).getTime());
                        } else {
                            date = (Date) value;
                        }
                        if (date != null) {
                            method.invoke(bean, new Object[]{date});
                        }
                    }
                } else if ("Timestamp".equals(type)) {
                    Timestamp timestamp = null;
                    if ("String".equals(value.getClass().getSimpleName())) {
                        String time = String.valueOf(value);
                        String format = null;
                        if (time.indexOf(":") > 0) {
                            if (time.indexOf(":") == time.lastIndexOf(":")) {
                                format = "yyyy-MM-dd H:mm";
                            } else {
                                format = "yyyy-MM-dd H:mm:ss";
                            }
                        } else {
                            format = "yyyy-MM-dd";
                        }
                        SimpleDateFormat sf = new SimpleDateFormat();
                        sf.applyPattern(format);
                        timestamp = new Timestamp(sf.parse(time).getTime());
                    } else {
                        timestamp = (Timestamp) value;
                    }
                    if (timestamp != null) {
                        method.invoke(bean, new Object[]{timestamp});
                    }
                } else if ("byte[]".equals(type)) {
                    method.invoke(bean, new Object[]{new String("" + value).getBytes()});
                }
            } catch (Exception ex) {
                log.info("JSONObject赋值给JavaBean失败", ex);
            }
        }
    }


    /**
     * 将Model转换成JSONObject
     */
    @SuppressWarnings("unchecked")
    public static JSONObject coverModelToJSONObject (Object o) throws Exception {
        JSONObject json = new JSONObject();
        Class clazz = o.getClass();
        Field[] fields = clazz.getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            Field f = fields[i];
            json.put(f.getName(), invokeMethod(clazz, f.getName(), o, f.getGenericType()));
        }
        return json;
    }


    /**
     * 将list转换成JSONArray
     */
    public static JSONArray coverModelToJSONArray (List list) throws Exception {
        JSONArray array = null;
        if (list.isEmpty()) {
            return array;
        }
        array = new JSONArray();
        for (Object o : list) {
            array.add(coverModelToJSONObject(o));
        }
        return array;
    }

    @SuppressWarnings("unchecked")
    private static Object invokeMethod (Class c, String fieldName, Object o, Object type) {
        String methodName = fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
        Method method = null;
        try {
            method = c.getMethod("get" + methodName);
            if (String.valueOf(type).contains("Date")) {
                return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format((java.util.Date) method.invoke(o));
            }
            return method.invoke(o);
        } catch (Exception e) {
            e.printStackTrace();
            log.equals(e);
            return "";
        }
    }

    /**
     * Description:
     * json转map
     *
     * @param
     * @return Map<String                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               ,                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               Object>
     * @throws Exception if has error
     * @Author Administrator
     * @Create Date: 2014-7-14 上午10:28:12
     */
    public static Map<String, Object> parseJSON2Map (String jsonStr) {
        Map<String, Object> map = new HashMap<String, Object>();
        //最外层解析  
        JSONObject json = JSONObject.fromObject(jsonStr);
        for (Object k : json.keySet()) {
            Object v = json.get(k);
            //如果内层还是数组的话，继续解析  
            if (v instanceof JSONArray) {
                List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
                Iterator<JSONObject> it = ((JSONArray) v).iterator();
                while (it.hasNext()) {
                    JSONObject json2 = it.next();
                    list.add(parseJSON2Map(json2.toString()));
                }
                map.put(k.toString(), list);
            } else {
                map.put(k.toString(), v);
            }
        }
        return map;
    }

    /**
     * @param list list对象
     * @return String
     * @throws Exception
     */
    public static String listToJson (List<?> list) throws Exception {
        StringBuilder json = new StringBuilder();
        json.append("[");
        if (list != null && list.size() > 0) {
            for (Object obj : list) {
                json.append(coverModelToJSONObject(obj));
                json.append(",");
            }
            json.setCharAt(json.length() - 1, ']');
        } else {
            json.append("]");
        }
        return json.toString();
    }

    /**
     * @param /Json json转map
     * @return List<T>
     * @throws Exception
     */
    public static <T> List JsonToList (String json, Class<T> cla) throws Exception {

        if (json == null || "".equals(json)) {
            return new ArrayList();
        }
        JSONArray jsonArray = JSONArray.fromObject(json);
        List list = (List) JSONArray.toCollection(jsonArray, cla);
        return list;
    }


}
