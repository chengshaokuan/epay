package com.csk.epay.utils.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @program: Demo
 * @description: 读取Properties配置文件工具类
 * @author: Mr.Cheng
 * @create: 2018-07-19 14:26
 **/
public class PropertiesReader {

    private static final Logger logger = LoggerFactory.getLogger(PropertiesReader.class);

    /**
     * @Description: 根据路径获取配置文件
     * @param: location
     * @return: java.util.Properties
     * @Author: Mr.Cheng
     * @Date: 14:31 2018/7/19
     */
    public static Properties getProperties (String location) {
        Properties props = new Properties();
        InputStream is = null;
        try {
            if (location.startsWith("classpath:")) {
                location = location.substring("classpath:".length());
            }
            if (location.startsWith("/")) {
                location = location.substring(1);
            }
            is = Thread.currentThread().getContextClassLoader().getResourceAsStream(location);
            props.load(is);
        } catch (Exception e) {
            logger.info("配置文件{" + location + "}格式有误或不存在！");
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return props;
    }

}
