package com.csk.epay.utils.util;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * @program: Cheng
 * @description: 以静态变量保存Spring ApplicationContext.工具类
 * @author: Mr.Cheng
 * @create: 2018-09-05 17:03
 **/
public class SpringContextUtils implements ApplicationContextAware {
    /**
     * 上下文
     */
    private static ApplicationContext applicationContext;

    /**
     * @Description: 实现ApplicationContextAware接口的context注入函数, 将其存入静态变量.
     * @param: context 上下文
     * @return: void
     * @Author: Mr.Cheng
     * @Date: 15:26 2018/10/8
     */
    @Override
    public void setApplicationContext(ApplicationContext context) {
        applicationContext = context;
    }

    /**
     * @Description:  取得存储在静态变量中的ApplicationContext.
     * @param:
     * @return: org.springframework.context.ApplicationContext
     * @Author: Mr.Cheng
     * @Date: 15:27 2018/10/8
     */
    public static ApplicationContext getApplicationContext() {
        if (applicationContext == null)
        {
            throw new IllegalStateException(
                    "applicaitonContext未注入,请在applicationContext.xml中定义SpringContextUtil");
        }
        return applicationContext;
    }

    public static Object getBean(String name) {
        return getApplicationContext().getBean(name);
    }

    public static <T> T getBean(Class<T> clazz) {
        return getApplicationContext().getBean(clazz);
    }

    public static <T> T getBean(String name, Class<T> clazz) {
        return getApplicationContext().getBean(name, clazz);
    }
}
