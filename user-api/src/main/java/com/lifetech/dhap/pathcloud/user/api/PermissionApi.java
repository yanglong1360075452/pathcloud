package com.lifetech.dhap.pathcloud.user.api;

import com.lifetech.dhap.pathcloud.common.exception.BuzException;
import com.lifetech.dhap.pathcloud.common.response.ResponseVO;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

/**
 * Created by LuoMo on 2016-11-09.
 */
@Produces({"application/json"})
@Path("/permissions")
public interface PermissionApi {
    /**
     * 获取权限列表
     * @return
     * @throws BuzException
     */
    @GET
    ResponseVO getPermissions() throws BuzException;
}
