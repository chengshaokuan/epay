package com.csk.epay.utils.JsonUtil.gson.adapter;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import org.apache.commons.lang.math.NumberUtils;

import java.io.IOException;
import java.math.BigDecimal;

/**
 * @program: Cheng
 * @description: Gson解析的Number类型的字段解析适配器
 * @author: Mr.Cheng
 * @create: 2018-07-20 10:32
 **/
public class NumberTypeAdapter extends TypeAdapter<Number> {
    private Class clas;

    public NumberTypeAdapter (Class clas) {
        this.clas = clas;
    }

    /**
     * @Description: 是将Pojo中值反序列化，处理为字符串
     * @param: jsonWriter
     * @param: number
     * @return: void
     * @Author: Mr.Cheng
     * @Date: 14:13 2019/1/30
     */
    @Override
    public void write (JsonWriter jsonWriter, Number number) throws IOException {
        jsonWriter.value(number.intValue());
    }

    @Override
    public Number read (JsonReader jsonReader) {
        try {
            String json = jsonReader.nextString();
            if (clas == short.class) {
                return NumberUtils.toShort(json);
            } else if (clas == Short.class) {
                return Short.parseShort(json);
            } else if (clas == int.class) {
                return NumberUtils.toInt(json);
            } else if (clas == Integer.class) {
                return Integer.parseInt(json);
            } else if (clas == long.class) {
                return NumberUtils.toLong(json);
            } else if (clas == Long.class) {
                return Long.parseLong(json);
            } else if (clas == float.class) {
                return NumberUtils.toFloat(json);
            } else if (clas == Float.class) {
                return Float.parseFloat(json);
            } else if (clas == double.class) {
                return NumberUtils.toDouble(json);
            } else if (clas == Double.class) {
                return Double.parseDouble(json);
            } else if (clas == BigDecimal.class) {
                return new BigDecimal(json);
            } else if (clas == String.class) {
                return new Integer(json);
            } else {
                return Integer.parseInt(json);
            }
        } catch (Exception e) {
            return null;
        }
    }
}
