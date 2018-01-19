package com.lifetech.dhap.pathcloud.security;


import com.lifetech.dhap.pathcloud.common.response.ResponseVO;
import com.lifetech.dhap.pathcloud.common.utils.CommonUtil;
import com.lifetech.dhap.pathcloud.user.application.UserApplication;
import com.lifetech.dhap.pathcloud.user.application.dto.UserDto;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class MyAjaxAuthenticationSuccessHandler
        extends SimpleUrlAuthenticationSuccessHandler {

    @Autowired
    private UserApplication userApplication;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws ServletException, IOException {

        if((request.getHeader("accept") != null && request.getHeader("accept").contains("application/json") || ( request
                .getHeader("X-Requested-With") != null && request
                .getHeader("X-Requested-With").contains("XMLHttpRequest")) )){//AJAX
            response.setContentType("application/json");
            response.setHeader("Cache-Control", "no-cache, no-store");
            response.setHeader("Pragma", "no-cache");
            long time = System.currentTimeMillis();
            response.setDateHeader("Last-Modified", time);
            response.setDateHeader("Date", time);
            response.setDateHeader("Expires", time);

            SecurityUserDetails user = UserContext.getUserDetails();

            String path = request.getSession().getServletContext().getRealPath("");
            File file=new File(path + File.separator + "index.html");

            Map<String, Object> data = new HashMap<>();
            long id  = user.getId();
            data.put("id", id);
            data.put("email", user.getEmail());
            data.put("username", user.getUsername());
            data.put("firstName", user.getFirstName());
            data.put("permissionCodes",user.getPermission());
            data.put("phone",user.getPhone());
            data.put("version", file.lastModified());

            UserDto userDto = userApplication.getUserByUserID(id);
            Integer lockCount = userDto.getLockCount();
            Date lockCountTime = userDto.getLockCountTime();
            if(lockCount != null || lockCountTime != null){
                userDto.setLockCountTime(null);
                userDto.setLockCount(null);
                userDto.setPassword(null);
                userApplication.updateUser(userDto);
            }

            try {
                response.getOutputStream().write(JSONObject.fromObject(new ResponseVO(data)).toString().getBytes("UTF-8"));
            }catch (UnsupportedEncodingException e){
                logger.error(e.getMessage(), e);
            }
            response.getOutputStream().flush();
            response.getOutputStream().close();
        }else if (!response.isCommitted()) {
            handle(request, response, authentication);
        }

        SecurityUserDetails su = UserContext.getUserDetails();
        if (su != null) {
            UserDto user = userApplication.getUserByUserID(su.getId());
            if(user != null){
                user.setLastLoginTime(new Date());
                user.setLastLoginIp(CommonUtil.getIpAddr(request));
                userApplication.updateUserLoginTime(user);
            }
        }
        clearAuthenticationAttributes(request);
    }

}
