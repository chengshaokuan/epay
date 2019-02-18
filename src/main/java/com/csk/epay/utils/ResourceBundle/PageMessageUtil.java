package com.csk.epay.utils.ResourceBundle;

import java.io.IOException;
import java.util.*;

/**
 * @program: Cheng
 * @description: 思路：
 * 1.读取配置文件(包括本地文选项、哪些资源文件)
 * 2.根据配置文件读取对应本地语言所有的国际化资源文件
 * 3.将所有国际化资源文件存放到map中
 * 4.提供
 * @author: Mr.Cheng
 * @create: 2018-07-20 16:03
 **/
public class PageMessageUtil {

    //国际化配置文件的名称
    private static final String CONFIG_FILE_NAME = "timeUtil_zh_CN.properties";
    //国际化配置文件里面的的key、value
    private static final String RESOURCE_FILES = "multi.RESOURCE_FILES";
    //国际化配置文件里当前语言
    private static final String LOCALE_CODE = "multi.LOCALE";


    private static Map<String, String> i18nMap = null;

    //加载这个类的时候就执行该静态方法
    static {
        load();
    }

    private static void load () {
        //读取配置文件
        Properties property = new Properties();
        try {

            property.load(PageMessageUtil.class.getClassLoader().getResourceAsStream(CONFIG_FILE_NAME));
            //获取所有国际化资源的前缀,如：pageMessage、后缀:zh_CN
            String resourceFile = property.getProperty(RESOURCE_FILES).toString();
            System.out.println(resourceFile);

            String locale = property.getProperty(LOCALE_CODE).toString();
            System.out.println(locale);

            //将资源文件中的配置转化成map
            String[] resources = resourceFile.split(",");
            i18nMap = new HashMap<String, String>();
            ResourceBundle bundle = null;
            for (int i = 0; i < resources.length; i++) {
                //struts2中用于国际化的类,这里是初始化这个类的
                try {
                    ResourceBundle.clearCache();
                    //获取到某个资源文件里面的所有内容
                    bundle = ResourceBundle.getBundle(resources[i] + "_" + locale);
                    @SuppressWarnings("rawtypes")
                    Set set = bundle != null ? bundle.keySet() : null;
                    @SuppressWarnings("unchecked")
                    Iterator<String> iterator = (set != null) ? set.iterator() : null;
                    while (iterator != null && iterator.hasNext()) {
                        String key = iterator.next();
                        i18nMap.put(key, bundle.getString(key));
                    }
                    System.out.println(i18nMap.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static String get (String key) {
        if (i18nMap.containsKey(key)) {
            return i18nMap.get(key);
        } else {
            return key;
        }
    }

    public static Set<String> getKeys () {
        Set<String> set = null;
        if (i18nMap != null && i18nMap.size() > 0) {
            set = new HashSet<String>();
            for (Map.Entry<String, String> entry : i18nMap.entrySet()) {
                set.add(entry.getKey());
            }
            return set;
        } else {
            return set;
        }
    }


    public static void main (String[] args) {
        System.out.println(getKeys().toString());
    }

}
