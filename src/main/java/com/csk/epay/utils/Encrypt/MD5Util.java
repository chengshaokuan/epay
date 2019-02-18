package com.csk.epay.utils.Encrypt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.MessageDigest;

/**
 * @program: epay
 * @description:
 * @author: Mr.Cheng
 * @create: 2019-02-14 16:58
 **/
public class MD5Util {

    private static final Logger logger = LoggerFactory.getLogger(MD5Util.class);

    private static final String MD5 = "MD5";
    private static final String UTF_8 = "UTF-8";

    /**
     * @Description:
     * @param: s 要加密字符串
     * @return: byte[]
     * @Author: Mr.Cheng
     * @Date: 17:21 2018/10/25
     */
    public static String md5 (String string) {
        MessageDigest md;
        try {
            String s = (string == null) ? "" : string;
            md = MessageDigest.getInstance(MD5);
            md.reset();
            //执行加密
            md.update(s.getBytes(UTF_8));
            //加密结果
            byte[] md5 = md.digest();
            byte[] bytes = toHex(md5).getBytes(UTF_8);
            return new String(bytes, UTF_8);
        } catch (Exception e) {
            logger.error("MD5 Error...{}", e);
            return string;
        }
    }

    /**
     * @Description: 数组转化为字符串
     * @param: hash
     * @return: java.lang.String
     * @Author: Mr.Cheng
     * @Date: 17:23 2018/10/25
     */
    private static final String toHex (byte hash[]) {
        if (hash == null) {
            return null;
        }
        StringBuffer buf = new StringBuffer(hash.length * 2);
        int i;
        for (i = 0; i < hash.length; i++) {
            if ((hash[i] & 0xff) < 0x10) {
                buf.append("0");
            }
//            buf.append(Long.toString(hash[i] & 0xff, 16));
            buf.append(Integer.toHexString(hash[i] & 0xff));
        }
        return buf.toString();
    }


    /**
     * @Description: 对密码按照用户名，密码，盐进行加密
     * @param: username 用户名
     * @param: password 密码
     * @param: salt 盐就是一个随机生成的字符串
     * @return: java.lang.String
     * @Author: Mr.Cheng
     * @Date: 17:25 2018/10/25
     */
    public static String encryptPassword (String username, String password, String salt) {
        return md5(username + password + salt);
    }


}
