package com.lifetech.dhap.pathcloud.user.api;

import com.lifetech.dhap.pathcloud.common.exception.BuzException;
import com.lifetech.dhap.pathcloud.common.response.ResponseVO;
import com.lifetech.dhap.pathcloud.user.api.request.UserCreateReq;
import com.lifetech.dhap.pathcloud.user.api.request.UserUpdateReq;

import javax.ws.rs.*;
import java.util.Map;

/**
 * Created by gdw on 4/15/16.
 */
@Produces({"application/json"})
@Path("/user")
public interface UserApi {

    /**
     * 二维码数据解码，用户二维码登录
     * @param data 用户信息
     * @return
     * @throws BuzException
     */
    @POST
    @Path("/qrcode/{data}")
    @Consumes("application/json")
    ResponseVO qrCodeDecode(@PathParam("data") String data) throws BuzException;

    /**
     * 创建用户
     * @param user 用户信息
     * @return
     * @throws BuzException
     */
    @POST
    @Consumes("application/json")
    ResponseVO createUser(UserCreateReq user) throws BuzException;

    /**
     * 用户信息更新
     * @param user
     * @return
     * @throws BuzException
     */
    @PUT
    @Consumes("application/json")
    @Path("/{id}")
    ResponseVO updateUser(@PathParam("id") Long id,UserUpdateReq user) throws BuzException;


    /**
     * 获取用户列表
     * @param page
     * @param length
     * @param filter
     * @param order 排序字段
     * @param sort 排序方式(正序/倒序)
     * @param status
     * @param permissionId
     * @param roleId
     * @return
     * @throws BuzException
     */
    @GET
    ResponseVO getUsers(@QueryParam("page") @DefaultValue("1") int page,
                        @QueryParam("length") @DefaultValue("10") int length,
                        @QueryParam("filter") String filter,
                        @QueryParam("order") Integer order,
                        @QueryParam("sort") String sort,
                        @QueryParam("status") Boolean status,
                        @QueryParam("permissionId") Integer permissionId,
                        @QueryParam("roleId") Long roleId) throws BuzException;

    /**
     * 重置密码
     * @param data
     * @return
     * @throws BuzException
     */
    @PUT
    @Consumes("application/json")
    @Path("/resetPassword")
    ResponseVO resetPassword(Map<String,String> data) throws BuzException;

    /**
     * 校验用户名
     * @param username
     * @return
     * @throws BuzException
     */
    @GET
    @Path("/check/{username}")
    ResponseVO checkUsername(@PathParam("username") String username) throws BuzException;

    /**
     * 根据ID获取用户信息
     * @param id
     * @return
     * @throws BuzException
     */
    @GET
    @Path("/{id}")
    ResponseVO getUser(@PathParam("id") Long id) throws BuzException;

    /**
     * 获取取材医生列表
     * @return
     * @throws BuzException
     */
    @GET
    @Path("/grossing")
    ResponseVO getGrossingUsers() throws BuzException;

    /**
     * 获取有诊断权限医生列表
     * @return
     * @throws BuzException
     */
    @GET
    @Path("/diagnose")
    ResponseVO getDiagnoseUsers() throws BuzException;

    /**
     * 获取发过报告医生列表
     * @return
     * @throws BuzException
     */
    @GET
    @Path("/report")
    ResponseVO getReportUsers() throws BuzException;

    /**
     * 获取有二级、三级诊断权限医生列表
     * @return
     * @throws BuzException
     */
    @GET
    @Path("/superDiagnose")
    ResponseVO getSuperDiagnoseUsers() throws BuzException;


    /**
     * 获取有一级诊断权限医生列表
     * @return
     * @throws BuzException
     */
    @GET
    @Path("/firstDiagnose")
    ResponseVO getFirstDiagnoseUsers(@QueryParam("username") String username) throws BuzException;

    /**
     * 获取三级诊断医生
     * @return
     * @throws BuzException
     */
    @GET
    @Path("/thirdDiagnose")
    ResponseVO getThirdDiagnoseUsers() throws BuzException;
}
