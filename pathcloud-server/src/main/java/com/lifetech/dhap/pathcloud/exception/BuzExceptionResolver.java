package com.lifetech.dhap.pathcloud.exception;

import com.lifetech.dhap.pathcloud.common.exception.BuzException;
import com.lifetech.dhap.pathcloud.common.exception.BuzExceptionCode;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;

import javax.ws.rs.ClientErrorException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.Locale;

@Provider
public class BuzExceptionResolver implements ExceptionMapper {

    private static final Logger logger = LoggerFactory.getLogger(BuzExceptionResolver.class);

    public Response toResponse(Throwable ex) {
        Response.ResponseBuilder rb = Response.status(Response.Status.OK);
        rb.type("application/json;charset=UTF-8");

        JSONObject jsonObj = new JSONObject();
        if (ex instanceof BuzException) {
            BuzException e = (BuzException) ex;
            jsonObj.put("code", e.getCode());
            if(e.getReason() != null){
                jsonObj.put("reason", e.getReason());
            }else{
                jsonObj.put("reason", BuzExceptionCode.getReasonByCode(e.getCode(), "未知错误"));
            }
            logger.debug(ex.getMessage(), ex);
        }else if(ex instanceof AccessDeniedException){
            jsonObj.put("code", 1);
            jsonObj.put("reason", "权限不足");
            rb.status(Response.Status.UNAUTHORIZED);
            logger.warn(ex.getMessage(), ex);
        }else if(ex instanceof ClientErrorException){
            rb.status(Response.Status.BAD_REQUEST);
            logger.warn(ex.getMessage(), ex);
        }else{
            jsonObj.put("code", -1);
            jsonObj.put("reason", "服务器内部错误");
            jsonObj.put("errorMessage",ex.getMessage());
            rb.status(Response.Status.INTERNAL_SERVER_ERROR);
            logger.error(ex.getMessage(), ex);
        }

        rb.entity(jsonObj.toString());
        rb.language(Locale.SIMPLIFIED_CHINESE);
        Response r = rb.build();
        return r;
    }

}
