package com.lifetech.dhap.pathcloud.user.api;

import com.lifetech.dhap.pathcloud.common.exception.BuzException;
import com.lifetech.dhap.pathcloud.common.response.ResponseVO;
import com.lifetech.dhap.pathcloud.user.api.request.RoleSettingReq;
import com.lifetech.dhap.pathcloud.user.api.vo.RoleVO;

import javax.ws.rs.*;

/**
 * Created by LuoMo on 2016-11-09.
 */
@Produces({"application/json"})
@Path("/role")
public interface RoleApi {

    /**
     * 创建角色
     * @param roleVO
     * @return
     * @throws BuzException
     */
    @POST
    @Consumes("application/json")
    ResponseVO createRole(RoleVO roleVO) throws BuzException;

    /**
     * 修改角色
     * @param roleVO
     * @return
     * @throws BuzException
     */
    @PUT
    @Path("/{id}")
    @Consumes("application/json")
    ResponseVO updateRole(@PathParam("id") Long id, RoleVO roleVO) throws BuzException;

    /**
     * 删除角色
     * @param id
     * @return
     * @throws BuzException
     */
    @DELETE
    @Path("/{id}")
    ResponseVO deleteRole(@PathParam("id") Long id) throws BuzException;
    /**
     * 获取角色列表
     * @param page
     * @param length
     * @param order
     * @param sort
     * @return
     * @throws BuzException
     */
    @GET
    ResponseVO getRoles(@QueryParam("page") @DefaultValue("1") int page,
                        @QueryParam("length") @DefaultValue("10") int length,
                        @QueryParam("order") Integer order,
                        @QueryParam("sort") String sort) throws BuzException;

    /**
     * 获取单个角色信息
     * @param id
     * @return
     * @throws BuzException
     */
    @GET
    @Path("/{id}")
    ResponseVO getRole(@PathParam("id") Long id) throws BuzException;

    /**
     * 角色设置
     * 新建角色/删除角色/角色权限更改
     * @param roleSettingReq
     * @return
     * @throws BuzException
     */
    @POST
    @Consumes("application/json")
    @Path("/setting")
    ResponseVO roleSetting(RoleSettingReq roleSettingReq)throws BuzException;

    /**
     * 校验用户名
     * @param roleName
     * @return
     * @throws BuzException
     */
    @GET
    @Path("/check/{roleName}")
    ResponseVO checkRoleName(@PathParam("roleName") String roleName) throws BuzException;
}
