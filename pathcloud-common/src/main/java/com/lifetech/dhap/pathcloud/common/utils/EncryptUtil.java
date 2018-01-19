package com.lifetech.dhap.pathcloud.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.codec.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * Created by gdw on 6/2/16.
 */
public class EncryptUtil {
    /** md5加密 */
    public static String md5(String inputText) {
        return encryptWithAlgorithm(inputText, "md5");
    }

    /** sha加密 */
    public static String sha(String inputText) {
        return encryptWithAlgorithm(inputText, "sha-1");
    }

    private static Logger logger = LoggerFactory.getLogger(EncryptUtil.class);

    /**
     * md5或者sha-1加密
     *
     * @param inputText
     *            要加密的内容
     * @param algorithmName
     *            加密算法名称：md5或者sha-1，不区分大小写
     * @return 返回加密后的数据
     */
    private static String encryptWithAlgorithm(String inputText, String algorithmName) {
        if (inputText == null || "".equals(inputText.trim())) {
            throw new IllegalArgumentException("请输入要加密的内容");
        }
        if (algorithmName == null || "".equals(algorithmName.trim())) {
            algorithmName = "md5";
        }
        String encryptText = null;
        try {
            MessageDigest m = MessageDigest.getInstance(algorithmName);
            m.update(inputText.getBytes("UTF8"));
            byte s[] = m.digest();
            // m.digest(inputText.getBytes("UTF8"));
            return hex(s);
        } catch (NoSuchAlgorithmException e) {
            logger.error("No such algorithm:"+algorithmName, e);
        } catch (UnsupportedEncodingException e) {
            logger.error("UnsupportedEncoding:"+algorithmName, e);
        }
        return encryptText;
    }

    // 返回十六进制字符串
    private static String hex(byte[] arr) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < arr.length; ++i) {
            sb.append(Integer.toHexString((arr[i] & 0xFF) | 0x100).substring(1,
                    3));
        }
        return sb.toString();
    }


    /**
     * BASE64解密
     *
     * @param key 数据源
     * @return 返回解密后的原始数据
     */
    public static byte[] decryptBASE64(String key) {
        try {
            return Base64.decode(key.getBytes("UTF-8"));
        }catch (UnsupportedEncodingException e){
            throw new RuntimeException(e);
        }

    }

    /**
     * BASE64加密
     *
     * @param key 数据源
     * @return 返回加密后的数据
     */
    public static byte[] encryptBASE64(String key) {
        try {
            return Base64.encode(key.getBytes("UTF-8"));
        }catch (UnsupportedEncodingException e){
            throw new RuntimeException(e);
        }
    }

    /**
     * 加密
     * @param src 数据源
     * @param key 密钥，长度必须是8的倍数
     * @return    返回加密后的数据
     */
    public static byte[] DESEncrypt(byte[] src, byte[] key){
        try{
            //DES算法要求有一个可信任的随机数源
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
            random.setSeed(key);
            // 从原始密匙数据创建DESKeySpec对象
            DESKeySpec dks = new DESKeySpec(key);
            // 创建一个密匙工厂，然后用它把DESKeySpec转换成
            // 一个SecretKey对象
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey secretkey = keyFactory.generateSecret(dks);
            // Cipher对象实际完成加密操作
            Cipher cipher = Cipher.getInstance("DES");
            // 用密匙初始化Cipher对象
            cipher.init(Cipher.ENCRYPT_MODE, secretkey, random);
            // 现在，获取数据并加密
            // 正式执行加密操作
            return cipher.doFinal(src);
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }

    /**
     * 解密
     * @param src   数据源
     * @param key   密钥，长度必须是8的倍数
     * @return      返回解密后的原始数据
     */
    public static byte[] DESDecrypt(byte[] src, byte[] key){
        try{
            //DES算法要求有一个可信任的随机数源
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
            random.setSeed(key);
            // 从原始密匙数据创建一个DESKeySpec对象
            DESKeySpec dks = new DESKeySpec(key);
            // 创建一个密匙工厂，然后用它把DESKeySpec对象转换成
            // 一个SecretKey对象
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey secretkey = keyFactory.generateSecret(dks);
            // Cipher对象实际完成解密操作
            Cipher cipher = Cipher.getInstance("DES");
            // 用密匙初始化Cipher对象
            cipher.init(Cipher.DECRYPT_MODE, secretkey, random);
            // 现在，获取数据并解密
            // 正式执行解密操作
            return cipher.doFinal(src);
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }
    /**
     * 数据解密
     * @param data 数据源
     * @param key 密钥
     * @return 返回解密后的原始数据
     */
    public static String DESDecrypt(String data, String key){
        try {
            return new String(DESDecrypt(hex2byte(data.getBytes("UTF-8")), encryptBASE64(key)), "UTF-8");
        }catch (UnsupportedEncodingException e){
            throw new RuntimeException(e);
        }

    }
    /**
     * 数据加密
     * @param data 数据源
     * @param key 密钥
     * @return 返回加密后的数据
     */
    public static String DESEncrypt(String data, String key){
        if(data!=null) {
            try {
                return byte2hex(DESEncrypt(data.getBytes("UTF-8"), encryptBASE64(key)));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }

    /**
     * 加密
     * @param src 数据源
     * @param key 密钥，长度必须是8的倍数
     * @return    返回加密后的数据
     */
    public static byte[] AESEncrypt(byte[] src, byte[] key){
        try{
            KeyGenerator kgen = KeyGenerator.getInstance("AES");
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
            random.setSeed(key);
            kgen.init(128, random);
            SecretKey secretKey = kgen.generateKey();
            byte[] enCodeFormat = secretKey.getEncoded();
            SecretKeySpec secretKeySpec = new SecretKeySpec(enCodeFormat, "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
            return cipher.doFinal(src);
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }

    /**
     * 解密
     * @param src   数据源
     * @param key   密钥，长度必须是8的倍数
     * @return      返回解密后的原始数据
     */
    public static byte[] AESDecrypt(byte[] src, byte[] key){
        try{
            KeyGenerator kgen = KeyGenerator.getInstance("AES");

            SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
            random.setSeed(key);
            kgen.init(128, random);
            SecretKey secretKey = kgen.generateKey();
            byte[] enCodeFormat = secretKey.getEncoded();
            SecretKeySpec secretKeySpec = new SecretKeySpec(enCodeFormat, "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
            return cipher.doFinal(src);
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }

    /**
     * 数据解密
     * @param data 数据源
     * @param key 密钥
     * @return 返回解密后的原始数据
     */
    public static String AESDecrypt(String data, String key){
        try {
            return new String(AESDecrypt(hex2byte(data.getBytes("UTF-8")), encryptBASE64(key)), "UTF-8");
        }catch (UnsupportedEncodingException e){
            throw new RuntimeException(e);
        }

    }
    /**
     * 数据加密
     * @param data 数据源
     * @param key 密钥
     * @return 返回加密后的数据
     */
    public static String AESEncrypt(String data, String key){
        if(data!=null) {
            try {
                return byte2hex(AESEncrypt(data.getBytes("UTF-8"), encryptBASE64(key)));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }

    private static String byte2hex(byte[] b) {
        StringBuilder hs = new StringBuilder();
        String stmp;
        for (int n = 0; b!=null && n < b.length; n++) {
            stmp = Integer.toHexString(b[n] & 0XFF);
            if (stmp.length() == 1) {
                hs.append('0');
            }
            hs.append(stmp);
        }
        return hs.toString().toUpperCase();
    }

    private static byte[] hex2byte(byte[] b) throws UnsupportedEncodingException{
        if((b.length%2)!=0) {
            throw new IllegalArgumentException();
        }
        byte[] b2 = new byte[b.length/2];
        for (int n = 0; n < b.length; n+=2) {
            String item = new String(b,n,2, "UTF-8");
            b2[n/2] = (byte)Integer.parseInt(item,16);
        }
        return b2;
    }

}
