package com.lifetech.dhap.pathcloud.common.utils;

import com.lifetech.dhap.pathcloud.common.data.Permission;
import com.lifetech.dhap.pathcloud.security.UserContext;
import org.apache.commons.collections4.CollectionUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * Created by gdw on 4/20/16.
 */
public class CommonUtil {

    /**
     * 获取请求客户端IP地址
     *
     * @param request
     * @return
     */
    public static String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    public static String resetPasswordSign(SortedMap<String, String> map) throws RuntimeException {
        Iterator<Map.Entry<String, String>> iterator = map.entrySet().iterator();
        StringBuilder encodedString = new StringBuilder();
        Map.Entry<String, String> entry;
        while (iterator.hasNext()) {
            entry = iterator.next();
            if (Objects.equals(entry.getKey(), "sign")) {
                continue;
            }
            encodedString.append(entry.getValue());
            encodedString.append("-");
            encodedString.append(entry.getKey());
        }
        encodedString.append("23fAvava88jjifaFAF@4fajf8ajpfajfiajfaslfj2@Eff2f8ajfa;fjaf;@");
        return EncryptUtil.md5(encodedString.toString()).toLowerCase();
    }

    public static Boolean admin(){
        Boolean admin = false;
        List<Integer> permissions = UserContext.getLoginUserPermissions();
        if (CollectionUtils.isNotEmpty(permissions)) {
            for (Integer permission : permissions) {
                if (permission.equals(Permission.Admin.toCode())) {
                    admin = true;
                }
            }
        }
        return admin;
    }
}
