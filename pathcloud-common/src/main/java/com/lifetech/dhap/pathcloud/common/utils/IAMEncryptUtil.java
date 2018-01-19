package com.lifetech.dhap.pathcloud.common.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by HP on 2016/8/9.
 */
public class IAMEncryptUtil {
    public static Map<String,String> iamEncrypt(String accessKey,String secretKey){
        String secretKeyEncrypt = EncryptUtil.AESEncrypt(secretKey,accessKey);
        String accessKeyEncrypt = EncryptUtil.AESEncrypt(accessKey,secretKeyEncrypt);

        Map<String, String> data = new HashMap<String, String>();
        data.put("secretKey",secretKeyEncrypt);
        data.put("accessKey",accessKeyEncrypt);
        return data;
    }

    public static Map<String,String> iamDecrypt(String accessKeyEncrypt,String secretKeyEncrypt){
        String accessKey = EncryptUtil.AESDecrypt(accessKeyEncrypt,secretKeyEncrypt);
        String secretKey = EncryptUtil.AESDecrypt(secretKeyEncrypt,accessKey);

        Map<String, String> data = new HashMap<String, String>();
        data.put("secretKey",secretKey);
        data.put("accessKey",accessKey);
        return data;
    }

    public static void main(String[] args){
        String accessKey = "AKIAPWXKQRVJDTTUSGYA";
        String secretKey = "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";

        Map<String,String> data = iamEncrypt(accessKey,secretKey);
        System.out.println("accessKey:"+data.get("accessKey"));
        System.out.println("secretKey:"+data.get("secretKey"));

        secretKey =  data.get("secretKey");
        accessKey = data.get("accessKey");
        data = iamDecrypt(accessKey,secretKey);
        System.out.println("accessKey:"+data.get("accessKey"));
        System.out.println("secretKey:"+data.get("secretKey"));

    }
}
