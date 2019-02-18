package com.csk.epay.utils.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.util.Date;

/**
 * @program: Cheng
 * @description: DateToLong序列化
 * @author: Mr.Cheng
 * @create: 2018-09-05 17:03
 **/
public class Date2LongSerializer extends JsonSerializer<Date> {

    @Override
    public void serialize (Date date, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeNumber(date.getTime() / 1000);
    }
}
