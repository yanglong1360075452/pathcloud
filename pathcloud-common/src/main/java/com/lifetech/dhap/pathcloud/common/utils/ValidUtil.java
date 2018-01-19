package com.lifetech.dhap.pathcloud.common.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Virgil on 2015/5/28.
 */
public final class ValidUtil {

    public static boolean phoneNumber(String phoneNumber) {
        if (phoneNumber == null || "".equals(phoneNumber)) {
            return false;
        }
        phoneNumber = phoneNumber.trim();
        String regExp = "^1\\d{10}$|^(0\\d{2,3}-?|\\(0\\d{2,3}\\))?[1-9]\\d{4,7}(-\\d{1,8})?$";
        Pattern p = Pattern.compile(regExp);
        Matcher m = p.matcher(phoneNumber);
        boolean flag = m.matches();
        return flag;
    }

    public static boolean email(String email) {
        if (email == null || "".equals(email) || email.indexOf("@") == -1) {
            return false;
        }
        email = email.trim();
        String regExp = "^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$";
        Pattern p = Pattern.compile(regExp);
        Matcher m = p.matcher(email);
        boolean flag = m.matches();
        return flag;
    }


}
