package com.csk.epay.utils.Encrypt;


import com.alibaba.fastjson.JSONObject;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


/**
 * @program: Cheng
 * @description:计算单位转换工具类
 * @author: Mr.Cheng
 * @create: 2018-09-06 15:12
 **/
public class AESUtil {

    public static final String KEY_ALGORITHM = "AES";

    public static final String CIPHER_ALGORITHM = "AES";

    public static byte[] initkey () throws Exception {
        //实例化密钥生成器
        KeyGenerator kg = KeyGenerator.getInstance(KEY_ALGORITHM);
        kg.init(256);
        kg.init(128);
        SecretKey secretKey = kg.generateKey();
        return secretKey.getEncoded();
    }


    public static byte[] initRootKey () throws Exception {
        return new byte[]{0x08, 0x08, 0x04, 0x0b, 0x02, 0x0f, 0x0b, 0x0c,
                0x01, 0x03, 0x09, 0x07, 0x0c, 0x03, 0x07, 0x0a, 0x04, 0x0f,
                0x06, 0x0f, 0x0e, 0x09, 0x05, 0x01, 0x0a, 0x0a, 0x01, 0x09,
                0x06, 0x07, 0x09, 0x0d};
    }


    public static Key toKey (byte[] key) throws Exception {
        SecretKey secretKey = new SecretKeySpec(key, KEY_ALGORITHM);
        return secretKey;
    }

    public static byte[] encrypt (byte[] data, String keyValue) throws Exception {
        byte[] key = MD5(keyValue).getBytes();
        Key k = toKey(key);
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, k);
        return cipher.doFinal(data);
    }


    public static byte[] decrypt (byte[] data, byte[] key) throws Exception {
        Key k = toKey(key);
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, k);
        return cipher.doFinal(data);
    }

    public static String MD5 (String sourceStr) {
        String result = "";
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(sourceStr.getBytes());
            byte b[] = md.digest();
            int i;
            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0) {
                    i += 256;
                }
                if (i < 16) {
                    buf.append("0");
                }
                buf.append(Integer.toHexString(i));
            }
            result = buf.toString();
        } catch (NoSuchAlgorithmException e) {
            System.out.println(e);
        }
        System.out.println("md5:" + result);
        return result;
    }


    private static byte[] MD52 (String sourceStr) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(sourceStr.getBytes());
            byte b[] = md.digest();
            return b;
        } catch (NoSuchAlgorithmException e) {
            System.out.println(e);
        }
        return null;
    }


    public static String bytesToHexString (byte[] src) {
        StringBuilder stringBuilder = new StringBuilder("");
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }


    public static byte[] hexStringToBytes (String hexString) {
        if (hexString == null || hexString.equals("")) {
            return null;
        }
        hexString = hexString.toUpperCase();
        int length = hexString.length() / 2;
        char[] hexChars = hexString.toCharArray();
        byte[] d = new byte[length];
        for (int i = 0; i < length; i++) {
            int pos = i * 2;
            d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
        }
        return d;
    }

    private static byte charToByte (char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }

    // 解密成一个对象
    public static <T> T decryptObject (String str, Class className, String keyValue) throws Exception {
        byte[] key = AESUtil.MD5(keyValue).getBytes();
        byte[] data = hexStringToBytes(str);
        Key k = toKey(key);
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, k);
        String decryptString = new String(cipher.doFinal(data), "utf-8");
        // byte[] temp=decryptString.getBytes("gbk");//这里写原编码方式
        // byte[] newtemp=new String(temp, "gbk").getBytes("utf-8");//这里写转换后的编码方式
        // String newStr=new String(newtemp,"utf-8");//这里写转换后的编码方式
        JSONObject obj = JSONObject.parseObject(decryptString);
        return (T) JSONObject.toJavaObject(obj, className);
    }

    // 解密成一个String
    public static String decryptStr (String str, String keyValue) throws Exception {
        byte[] key = AESUtil.MD5(keyValue).getBytes();
        byte[] data = hexStringToBytes(str);
        Key k = toKey(key);
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, k);
        String decryptString = new String(cipher.doFinal(data));
        return decryptString;
    }

    public static String addSecureToStr (String str, String keyValue) {
        byte[] data = null;
        try {
            data = AESUtil.encrypt(str.getBytes("utf-8"), keyValue);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return AESUtil.bytesToHexString(data);
    }

    public static void main (String[] args) throws Exception {
        System.out.println(addSecureToStr("123456", String.valueOf(initRootKey())));
//        System.err.println(decryptStr(addSecureToStr("123456", "[B@6483f5ae"), "[B@6483f5ae"));
        System.err.println(initkey());
    }
}
