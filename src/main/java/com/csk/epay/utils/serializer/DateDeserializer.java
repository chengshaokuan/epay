package com.csk.epay.utils.serializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;

/**
 * @program: Cheng
 * @description: @Description:  自定义 jackson 反序列化实现类：将日期字符串反序列化为 {@link Date} 对象。<br>
 * 除了支持 jackson 内置 {@link DateDeserializer} 支持的日期格式外，还支持以下日期格式：
 * <ul>
 * <li>yyyy-MM-dd HH:mm:ss.SSS</li>
 * <li>yyyy-MM-dd HH:mm:ss</li>
 * <li>yyyy-MM-dd HH:mm</li>
 * <li>yyyy-MM-dd</li>
 * <li>yyyy-MM-dd'T'HH:mm:ss.SSS</li>
 * <li>yyyy-MM-dd'T'HH:mm:ss</li>
 * <li>yyyy-MM-dd'T'HH:mm</li>
 * <li>yyyy/MM/dd HH:mm:ss.SSS</li>
 * <li>yyyy/MM/dd HH:mm:ss</li>
 * <li>yyyy/MM/dd HH:mm</li>
 * <li>yyyy/MM/dd</li>
 * <li>yyyy年MM月dd日 HH时mm分ss秒</li>
 * <li>yyyy年MM月dd日 HH时mm分</li>
 * <li>yyyy年MM月dd日</li>
 * <li>yyyyMMddHHmmssSSS</li>
 * <li>yyyyMMddHHmmss</li>
 * <li>yyyyMMddHHmm</li>
 * <li>yyyyMMdd</li>
 * </ul>
 *
 * <pre>
 * 示例代码：
 * <code>
 * public class Example {
 *     <b>&#64;JsonDeserialize(using = DaojiaDateDeserializer.class)</b>
 *     private Date createTime;
 *     // get, set
 * }
 * </code>
 * </pre>
 * @author: Mr.Cheng
 * @create: 2018-09-05 17:03
 **/
public class DateDeserializer extends JsonDeserializer<Date> {

    public static final String[] SUPPORT_FORMATS = new String[] {
        "yyyy-MM-dd HH:mm:ss.SSS",
        "yyyy-MM-dd HH:mm:ss",
        "yyyy-MM-dd HH:mm",
        "yyyy-MM-dd",
        "yyyy-MM-dd'T'HH:mm:ss.SSS",
        "yyyy-MM-dd'T'HH:mm:ss",
        "yyyy-MM-dd'T'HH:mm",
        "yyyy/MM/dd HH:mm:ss.SSS",
        "yyyy/MM/dd HH:mm:ss",
        "yyyy/MM/dd HH:mm",
        "yyyy/MM/dd",
        "yyyy年MM月dd日 HH时mm分ss秒",
        "yyyy年MM月dd日 HH时mm分",
        "yyyy年MM月dd日",
        "yyyyMMddHHmmssSSS",
        "yyyyMMddHHmmss",
        "yyyyMMddHHmm",
        "yyyyMMdd",
        "yyyy"
    };

    @Override
    public Date deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        try {
            String value = StringUtils.trim(jp.getValueAsString());
            return StringUtils.isBlank(value) ? null : DateUtils.parseDate(value, SUPPORT_FORMATS);
        } catch (ParseException e) {
            return new DateDeserializer().deserialize(jp, ctxt);
        }
    }

}