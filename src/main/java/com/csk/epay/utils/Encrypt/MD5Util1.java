package com.csk.epay.utils.Encrypt;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @program: epay
 * @description:
 * @author: Mr.Cheng
 * @create: 2019-02-14 16:58
 **/
public class MD5Util1 {

    private static final String MD5 = "MD5";
    private static final String UTF_8 = "UTF-8";
    private static final String[] HEXDIGITS = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d",
            "e", "f"};

    public static String md5 (String signStr) {
        byte[] signInfo = null;
        try {
            MessageDigest md = MessageDigest.getInstance(MD5);
            signInfo = md.digest(signStr.getBytes(UTF_8));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return byteArray2HexString(signInfo);
    }

    private static String byteArray2HexString (byte[] b) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < b.length; i++) {
            sb.append(byte2HexString(b[i]));
        }
        return sb.toString();
    }

    private static String byte2HexString (byte b) {
        int n = b;
        if (n < 0) {
            n += 256;
        }
        int d1 = n / 16;
        int d2 = n % 16;
        return HEXDIGITS[d1] + HEXDIGITS[d2];
    }

    /**
     * 加密解密算法 执行一次加密，两次解密
     */
    public static String convertMD5 (String inStr) {

        char[] a = inStr.toCharArray();
        for (int i = 0; i < a.length; i++) {
            a[i] = (char) (a[i] ^ 't');
        }
        String s = new String(a);
        return s;
    }


}
