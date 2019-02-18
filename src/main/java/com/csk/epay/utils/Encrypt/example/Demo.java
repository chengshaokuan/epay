package com.csk.epay.utils.Encrypt.example;

import com.csk.epay.utils.Encrypt.factory.SecureFactory;
import com.csk.epay.utils.Encrypt.other.SecureType;
import com.csk.epay.utils.Encrypt.secure.*;
import org.junit.Test;


public class Demo {
    private static final String inputStr = "chengshaokuan";

    @Test
    public void md5Test () {
        System.out.println("======= MD5 ========");
        try {
            byte[] data = inputStr.getBytes();
            MD5Codec codec = (MD5Codec) SecureFactory.getCodec(SecureType.MD5, null);
            System.out.println("md5:" + codec.getEncryptForHex(data));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void shaTest () {
        System.out.println("======== SHA ========");
        try {
            byte[] data = inputStr.getBytes();
            SHACodec codec = (SHACodec) SecureFactory.getCodec(SecureType.SHA, null);
            System.out.println("sha:" + codec.getEncryptForHex(data));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void desTest () {
        System.out.println("========= DES ========");
        try {
            byte[] data = inputStr.getBytes();
            DESCodec codecA = (DESCodec) SecureFactory.getCodec(SecureType.DES, null);
            String secretKey = codecA.getSecretKey();
            byte[] encryptData = codecA.encrypt(data);
            DESCodec codecB = (DESCodec) SecureFactory.getCodec(SecureType.DES, secretKey);
            byte[] decryptData = codecB.decrypt(encryptData);
            System.out.println("in:" + inputStr + " , out:" + new String(decryptData));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void aesTest () {
        System.out.println("=========== AES ===========");
        try {
            byte[] data = inputStr.getBytes();
            AESCodec codecA = (AESCodec) SecureFactory.getCodec(SecureType.AES, null);
            String secretKey = codecA.getSecretKey();
            byte[] encryptData = codecA.encrypt(data);
            AESCodec codecB = (AESCodec) SecureFactory.getCodec(SecureType.AES, secretKey);
            byte[] decryptData = codecB.decrypt(encryptData);
            System.out.println("in:" + inputStr + " , out:" + new String(decryptData));
            String encryptHex = codecA.parseByteArray2HexStr(encryptData);
            byte[] decryptData2 = codecB.decrypt(codecB.parseHexStr2ByteArray(encryptHex));
            System.out.println("encryptHex:" + encryptHex + " , out:" + new String(decryptData2));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void rsaTest () {
        System.out.println("=========== RSA ============");
        try {
            byte[] data = inputStr.getBytes();
            RSAForPrivateCodec codecA = (RSAForPrivateCodec) SecureFactory.getCodec(SecureType.RSA_PRIVATE, null);
            String publicKey = codecA.getPublicKey();
            byte[] encryptData = codecA.encrypt(data);
            String sign = codecA.sign(data);
            RSAForPublicCodec codecB = (RSAForPublicCodec) SecureFactory.getCodec(SecureType.RSA_PUBLIC, publicKey);
            byte[] decryptData = codecB.decrypt(encryptData);
            System.out.println("in:" + inputStr + " , out:" + new String(decryptData) + " , verifySign:" + codecB.verifySign(decryptData, sign));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
