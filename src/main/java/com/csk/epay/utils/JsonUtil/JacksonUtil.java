package com.csk.epay.utils.JsonUtil;

import com.csk.epay.entity.Users;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: Cheng
 * @description: Jackson工具类
 * 优势：解析场景支持最完善，API最完善，可定制性最强，数据量大的时候，速度和Fastjson相差很小
 * @author: Mr.Cheng
 * @create: 2018-07-20 10:32
 **/
@Slf4j
public class JacksonUtil {

    private final static ObjectMapper mapper;

    static {
        mapper = new ObjectMapper();
        DeserializationConfig cfg = mapper.getDeserializationConfig();
        initMapper(mapper);
    }

    private static void initMapper (ObjectMapper objectMapper) {
        //时间格式LocalDate和LocalDateTime调整
        JavaTimeModule timeModule = new JavaTimeModule();
        //反序列化
        timeModule.addDeserializer(LocalDate.class,new LocalDateDeserializer(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        timeModule.addDeserializer(LocalDateTime.class,new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        //序列化
        timeModule.addSerializer(LocalDate.class,new LocalDateSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        timeModule.addSerializer(LocalDateTime.class,new LocalDateTimeSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        objectMapper.registerModule(timeModule);

        //为空字符串的属性值不映射
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
        //为null的属性值不映射
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        //序列化BigDecimal时之间输出原始数字还是科学计数, 默认false, 即是否以toPlainString()科学计数方式来输出
        objectMapper.enable(JsonGenerator.Feature.WRITE_BIGDECIMAL_AS_PLAIN);
        //允许将JSON空字符串强制转换为null对象值
        objectMapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
        //允许单个数值当做数组处理
        objectMapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
        //禁止重复键, 抛出异常
        objectMapper.enable(DeserializationFeature.FAIL_ON_READING_DUP_TREE_KEY);
        //禁止使用int代表Enum的order()來反序列化Enum, 抛出异常
        objectMapper.enable(DeserializationFeature.FAIL_ON_NUMBERS_FOR_ENUMS);
        //有属性不能映射的时候不报错
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        //使用null表示集合类型字段是时不抛异常
        objectMapper.disable(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES);
        //对象为空时不抛异常
        objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);

        //允许在JSON中使用c/c++风格注释
        objectMapper.enable(JsonParser.Feature.ALLOW_COMMENTS);
        //强制转义非ascii字符
        objectMapper.disable(JsonGenerator.Feature.ESCAPE_NON_ASCII);
        //在JSON中允许未知字段名
        objectMapper.enable(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES);
        //解析器支持解析结束符
        objectMapper.enable(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS);
        //时间格式,jdk8之前的时间格式Date
        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        //识别单引号
        objectMapper.enable(JsonParser.Feature.ALLOW_SINGLE_QUOTES);
    }

    /**
     * @Description: 设置是否开启JSON格式美化
     * @param: isEnable  为true表示开启
     * @return: void
     * @Author: Mr.Cheng
     * @Date: 19:02 2018/9/25
     */
    public static void setIndentOutputEnable (boolean isEnable) {
        if (isEnable) {
            //是否缩放排列输出, 默认false, 有些场合为了便于排版阅读则需要对输出做缩放排列
            mapper.enable(SerializationFeature.INDENT_OUTPUT);
        } else {
            mapper.disable(SerializationFeature.INDENT_OUTPUT);
        }
    }

    /**
     * @Description: javaBean、列表数组转换为json字符串，默认忽略空值
     * @param: obj
     * @return: java.lang.String
     * @Author: Mr.Cheng
     * @Date: 16:57 2018/8/14
     */
    public static String beanToJson (Object obj) {
        try {
            return mapper.writeValueAsString(obj);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @Description: 将json string反序列化成对象
     * @param: jsonString
     * @param: clazz
     * @return: T
     * @Author: Mr.Cheng
     * @Date: 19:08 2018/9/25
     */
    public static <T> T jsonToBean (String json, Class<T> clazz) {
        try {
            return mapper.readValue(json, clazz);
        } catch (IOException e) {
            log.error("jackson from error, json: {}, class: {}", json, clazz, e);
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @Description: 将json array反序列化为对象
     * @param: json
     * @param: type
     * @return: T
     * @Author: Mr.Cheng
     * @Date: 19:08 2018/9/25
     */
    public static <T> T jsonToBean (String json, TypeReference<T> type) {
        try {
            return mapper.readValue(json, type);
        } catch (IOException e) {
            log.error("jackson from error, json: {}, type: {}", json, type, e);
            return null;
        }
    }

    /**
     * @Description: map转JavaBean
     * @param: obj
     * @param: clazz
     * @return: T
     * @Author: Mr.Cheng
     * @Date: 17:00 2018/8/14
     */
    public static <T> T jsonToBean (Object obj, Class<T> clazz) {
        return mapper.convertValue(obj, clazz);
    }

    /**
     * @Description: json字符串转换为map，默认忽略空值
     * @param: json
     * @return: java.util.Map<java.lang.String , java.lang.Object>
     * @Author: Mr.Cheng
     * @Date: 16:58 2018/8/14
     */
    public static <T> Map<String, Object> jsonToMap (String json) {
        try {
            return mapper.readValue(json, Map.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @Description: json字符串转换为map
     * @param: json
     * @param: clazz
     * @return: java.util.Map<java.lang.String ， T>
     * @Author: Mr.Cheng
     * @Date: 16:58 2018/8/14
     */
    public static <T> Map<String, T> jsonToMap (String json, Class<T> clazz) {
        Map<String, Map<String, Object>> map = null;
        try {
            map = mapper.readValue(json, new TypeReference<Map<String, T>>() {
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        Map<String, T> result = new HashMap<>();
        for (Map.Entry<String, Map<String, Object>> entry : map.entrySet()) {
            result.put(entry.getKey(), jsonToBean(entry.getValue(), clazz));
        }
        return result;
    }

    /**
     * @Description: 从json串中获取某个字段
     * @param: json
     * @param: key
     * @return: java.lang.String
     * @Author: Mr.Cheng
     * @Date: 16:01 2018/9/26
     */
    public static String getString (String json, String key) {
        if (StringUtils.isEmpty(json)) {
            return null;
        }
        try {
            JsonNode node = mapper.readTree(json);
            if (null != node) {
                return node.get(key).toString();
            } else {
                return null;
            }
        } catch (IOException e) {
            log.error("jackson get string error, json: {}, key: {}", json, key, e);
            return null;
        }
    }

    /**
     * @Description:  格式化Json(美化)
     * @param: json
     * @return: java.lang.String
     * @Author: Mr.Cheng
     * @Date: 16:01 2018/9/26
     */
    public static String format (String json) {
        try {
            JsonNode node = mapper.readTree(json);
            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(node);
        } catch (IOException e) {
            log.error("jackson format json error, json: {}", json, e);
            return json;
        }
    }

    /**
     * @Description: 深度转换json成map
     * @param: json
     * @return: java.util.Map<java.lang.String , java.lang.Object>
     * @Author: Mr.Cheng
     * @Date: 16:58 2018/8/14
     */
    public static Map<String, Object> jsonToMapDeeply (String json) {
        try {
            return jsonToMapRecursion(json, mapper);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @Description: 把json解析成list，如果list内部的元素存在json，继续解析
     * @param: json
     * @param: mapper 解析工具
     * @return: java.util.List<java.lang.Object>
     * @Author: Mr.Cheng
     * @Date: 16:58 2018/8/14
     */
    private static List<Object> jsonToListRecursion (String json, ObjectMapper mapper) {
        if (json == null) {
            return null;
        }
        List<Object> list = null;
        try {
            list = mapper.readValue(json, List.class);
            list.forEach((Object obj) -> {
                if (obj != null && obj instanceof String) {
                    String str = (String) obj;
                    if (str.startsWith("[")) {
                        obj = jsonToListRecursion(str, mapper);
                    } else if (obj.toString().startsWith("{")) {
                        try {
                            obj = jsonToMapRecursion(str, mapper);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * @Description: 把json解析成map，如果map内部的value存在json，继续解析
     * @param: json
     * @param: mapper
     * @return: java.util.Map<java.lang.String , java.lang.Object>
     * @Author: Mr.Cheng
     * @Date: 16:59 2018/8/14
     */
    private static Map<String, Object> jsonToMapRecursion (String json, ObjectMapper mapper) {
        if (json == null) {
            return null;
        }
        Map<String, Object> map = null;
        try {
            map = mapper.readValue(json, Map.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            Object obj = entry.getValue();
            if (obj != null && obj instanceof String) {
                String str = ((String) obj);
                if (str.startsWith("[")) {
                    List<?> list = jsonToListRecursion(str, mapper);
                    map.put(entry.getKey(), list);
                } else if (str.startsWith("{")) {
                    Map<String, Object> mapRecursion = jsonToMapRecursion(str, mapper);
                    map.put(entry.getKey(), mapRecursion);
                }
            }
        }
        return map;
    }

    /**
     * @Description:  判断字符串是否是json
     * @param: json
     * @return: boolean
     * @Author: Mr.Cheng
     * @Date: 16:01 2018/9/26
     */
    public static boolean isJson (String json) {
        try {
            mapper.readTree(json);
            return true;
        } catch (Exception e) {
            log.error("jackson check json error, json: {}", json, e);
            return false;
        }
    }

    /**
     * @Description: 与javaBean json数组字符串转换为列表
     * @param: jsonArrayStr
     * @param: clazz
     * @return: java.util.List<T>
     * @Author: Mr.Cheng
     * @Date: 16:59 2018/8/14
     */
    public static <T> List<T> jsonTolist (String jsonArrayStr, Class<T> clazz) {

        JavaType javaType = getCollectionType(ArrayList.class, clazz);
        List<T> list = null;
        try {
            list = (List<T>) mapper.readValue(jsonArrayStr, javaType);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * @Description: 获取泛型的Collection Type
     * @param: collectionClass 泛型的Collection
     * @param: elementClasses 元素类
     * @return: com.fasterxml.jackson.databind.JavaType Java类型
     * @Author: Mr.Cheng
     * @Date: 16:59 2018/8/14
     */
    public static JavaType getCollectionType (Class<?> collectionClass, Class<?>... elementClasses) {
        return mapper.getTypeFactory().constructParametricType(collectionClass, elementClasses);
    }
    public static void main (String[] args) throws IOException {
        Users user = new Users();
        user.setName("cs");
        user.setAge(12);
        user.setBirthday(LocalDate.now());
        String string = "{\"name\":\"l\",\"age\":3,\"birthday\":\"2019-01-31\"}";

        String s = JacksonUtil.beanToJson(user);
        System.out.println();
        Users users = JacksonUtil.jsonToBean(string, Users.class);
        System.out.println(users);


    }



}
