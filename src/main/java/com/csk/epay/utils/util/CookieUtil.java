package com.csk.epay.utils.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @program: Cheng
 * @description: cookie工具类
 * @author: Mr.Cheng
 * @create: 2018-09-05 17:03
 **/
public class CookieUtil implements Serializable {

    /**
     * @Description: 设置
     * @param: response
     * @param: name
     * @param: value
     * @param: maxAge
     * @return: void
     * @Author: Mr.Cheng
     * @Date: 14:41 2018/10/8
     */
    public static void set (HttpServletResponse response,String name,String value,int maxAge) {
        Cookie cookie = new Cookie(name, value);
        cookie.setPath("/");
        cookie.setMaxAge(maxAge);
        response.addCookie(cookie);
    }

    /**
     * @Description: 获取cookie
     * @param: request
     * @param: name
     * @return: javax.servlet.http.Cookie
     * @Author: Mr.Cheng
     * @Date: 14:41 2018/10/8
     */
    public static Cookie get (HttpServletRequest request, String name) {
        Map<String, Cookie> cookieMap = readCookieMap(request);
        if (cookieMap.containsKey(name)) {
            return cookieMap.get(name);
        } else {
            return null;
        }
    }

    /**
     * @Description: 将cookie封装成Map
     * @param: request
     * @return: java.util.Map<java.lang.String,javax.servlet.http.Cookie>
     * @Author: Mr.Cheng
     * @Date: 14:42 2018/10/8
     */
    private static Map<String, Cookie> readCookieMap (HttpServletRequest request) {
        Map<String, Cookie> cookieMap = new HashMap<>();
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                cookieMap.put(cookie.getName(), cookie);
            }
        }
        return cookieMap;
    }
}
