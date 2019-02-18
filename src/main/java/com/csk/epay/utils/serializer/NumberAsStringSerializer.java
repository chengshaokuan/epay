/** 
 * Project Name:cn.liuyiyou.common 
 * File Name:NumberAsStringSerializer.java 
 * Package Name:cn.liuyiyou.common.utils 
 * Date:2017年7月25日下午4:46:18 
 * Copyright (c) 2017, liuyiyou.cn All Rights Reserved. 
 * 
 */ 
package com.csk.epay.utils.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

/**
 * @program: Cheng
 * @description: NumberAsString序列化工具类
 * @author: Mr.Cheng
 * @create: 2018-09-05 17:03
 **/
public class NumberAsStringSerializer extends JsonSerializer<Number> {

    @Override
    public void serialize(Number value, JsonGenerator gen, SerializerProvider serializers) throws IOException, JsonProcessingException {
        gen.writeString(String.valueOf(value));
    }

}
