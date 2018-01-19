package com.lifetech.dhap.pathcloud.security;

import com.lifetech.dhap.pathcloud.common.domain.CommonRepository;
import com.lifetech.dhap.pathcloud.common.exception.BuzExceptionCode;
import com.lifetech.dhap.pathcloud.common.response.ResponseVO;
import com.lifetech.dhap.pathcloud.user.application.UserApplication;
import com.lifetech.dhap.pathcloud.user.application.dto.UserDto;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;

public class MyAjaxAuthenticationFailureHandler
        extends SimpleUrlAuthenticationFailureHandler {

    private String defaultFailureUrl;
    private boolean forwardToDestination;

    @Autowired
    private UserApplication userApplication;

    @Autowired
    private CommonRepository commonRepository;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {


        if ((request.getHeader("accept") != null && request.getHeader("accept").contains("application/json") || (request
                .getHeader("X-Requested-With") != null && request
                .getHeader("X-Requested-With").contains("XMLHttpRequest")))) {//AJAX
            response.setContentType("application/json");
            response.setHeader("Cache-Control", "no-cache, no-store");
            response.setHeader("Pragma", "no-cache");
            long time = System.currentTimeMillis();
            response.setDateHeader("Last-Modified", time);
            response.setDateHeader("Date", time);
            response.setDateHeader("Expires", time);

            String username = request.getParameter("username");
            UserDto userDto = userApplication.getUserByUsername(username);
            ResponseVO responseVO;
            if (userDto != null) {
                Date dbNow = commonRepository.getDBNow();
                Boolean lockStatus = userDto.getLockStatus();
                int waitHs = 1000*60*5; //容错间隔5分钟
                if(!lockStatus){
                    Integer lockCount = userDto.getLockCount();
                    if(lockCount == null){
                        userDto.setLockCount(1);
                        userDto.setLockCountTime(dbNow);
                    }else {
                        Date lockCountTime = userDto.getLockCountTime();
                        Date now = dbNow;
                        Long wait = now.getTime()-lockCountTime.getTime();
                        if(wait <= waitHs){
                            int count = lockCount++;
                            if(count >= 5){
                                lockStatus = true;
                                userDto.setLockStatus(true);
                                userDto.setLockCount(null);
                                userDto.setLockCountTime(null);
                            }else {
                                userDto.setLockCount(lockCount++);
                            }
                        }else if(wait > waitHs){
                            userDto.setLockCount(1);
                            userDto.setLockCountTime(now);
                        }
                    }
                }
                userDto.setPassword(null);
                userApplication.updateUser(userDto);
                if (!userDto.getStatus()) {
                    responseVO = new ResponseVO(BuzExceptionCode.UserForbidden, "账户被禁用,请联系系统管理员");
                } else if(lockStatus){
                    responseVO = new ResponseVO(BuzExceptionCode.UserLock, "账户被锁,请联系系统管理员");
                }else {
                    responseVO = new ResponseVO(BuzExceptionCode.UsernameOrPasswordError, "用户名或密码错误");
                }
            } else {
                responseVO = new ResponseVO(BuzExceptionCode.UsernameOrPasswordError, "用户名或密码错误");
            }
            try {
                response.getOutputStream().write(JSONObject.fromObject(responseVO).toString().getBytes("UTF-8"));
            } catch (UnsupportedEncodingException e) {
                logger.error(e.getMessage(), e);
            }
            response.getOutputStream().flush();
            response.getOutputStream().close();
        } else if (this.defaultFailureUrl == null) {
            this.logger.debug("No failure URL set, sending 401 Unauthorized error");
            response.sendError(401, "Authentication Failed: " + exception.getMessage());
        } else {
            this.saveException(request, exception);
            if (this.forwardToDestination) {
                this.logger.debug("Forwarding to " + this.defaultFailureUrl);
                request.getRequestDispatcher(this.defaultFailureUrl).forward(request, response);
            } else {
                this.logger.debug("Redirecting to " + this.defaultFailureUrl);
                super.getRedirectStrategy().sendRedirect(request, response, this.defaultFailureUrl);
            }
        }

    }

    public String getDefaultFailureUrl() {
        return defaultFailureUrl;
    }

    @Override
    public void setDefaultFailureUrl(String defaultFailureUrl) {
        this.defaultFailureUrl = defaultFailureUrl;
    }

    public boolean isForwardToDestination() {
        return forwardToDestination;
    }

    public void setForwardToDestination(boolean forwardToDestination) {
        this.forwardToDestination = forwardToDestination;
    }
}
