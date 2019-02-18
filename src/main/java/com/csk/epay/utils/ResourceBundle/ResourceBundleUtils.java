package com.csk.epay.utils.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * @program: Cheng
 * @description: 国际化语言工具类
 * @author: Mr.Cheng
 * @create: 2018-07-20 15:59
 **/
public class ResourceBundleUtils {

    public static Logger log = LoggerFactory.getLogger(ResourceBundleUtils.class);
    //国际化语言文件的名称,文件名称
    public static final String LANGUAGE_FILE_NAME = "timeUtil";

    public static Map<String, ResourceBundle> ResourceBundleConfigMap = new HashMap<String, ResourceBundle>();


    /**
     * 返回国际化语言
     *
     * @param language 用户当前的语言环境
     * @param key      语言配置文件的key
     * @return
     * @throws Exception
     */
    public static String getValue (String language, String key) {
        return getValue(language2Locale(language), key);
    }

    public static String getValue (Locale locale, String key) {
        String language = locale.getLanguage() + "_" + locale.getCountry();
        ResourceBundle config = ResourceBundleConfigMap.get(language);
        if (config == null) {
            ResourceBundleConfigMap.put(language, ResourceBundle.getBundle(LANGUAGE_FILE_NAME, locale));
        }
        try {
            return ResourceBundleConfigMap.get(language).getString(key).trim();
        } catch (MissingResourceException e) {
            log.error("用户语言环境参数language = " + language);
            log.error("无法找到key为 " + key + " 对应的value.", e);
            return key;
        }
    }

    public static Locale language2Locale (String language) {
        try {
            String[] locale = language.split("_");
            return new Locale(locale[0], locale[1]);
        } catch (Exception e) {
            log.error("语言转换成Locale失败，已设置成默认语言", e);
            return Locale.getDefault();
        }
    }

    //测试
    public static void main (String[] args) throws Exception {
        System.out.println(ResourceBundleUtils.getValue("zh_CN", "multi.year"));
    }

}
