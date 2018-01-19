package com.lifetech.dhap.pathcloud.wechat.api;

import com.lifetech.dhap.pathcloud.common.exception.BuzException;
import com.lifetech.dhap.pathcloud.common.response.ResponseVO;
import com.lifetech.dhap.pathcloud.wechat.api.vo.IhcApplicationVO;
import com.lifetech.dhap.pathcloud.wechat.api.vo.ResearchVO;
import com.lifetech.dhap.pathcloud.wechat.api.vo.UserUpdateReq;

import javax.ws.rs.*;

/**
 * Created by HP on 2017/5/24.
 */
@Produces({"application/json"})
@Path("/wechat")
public interface WechatApi {

    /**
     * 常规染色和冰冻预约
     *
     * @param researchVO
     * @return
     * @throws BuzException
     */
    @POST
    @Consumes("application/json")
    @Path("/application")
    ResponseVO createResearch(ResearchVO researchVO) throws BuzException;


    /**
     * 获取已预约数据
     *
     * @param
     * @return
     * @throws BuzException
     */
    @GET
    @Path("/booking")
    ResponseVO getApplicationBooking(@QueryParam(value = "timeStart") Long timeStart,
                                     @QueryParam(value = "timeEnd") Long timeEnd
    ) throws BuzException;

    /**
     * 创建染色申请
     *
     * @param ihcApplicationVO
     * @return
     * @throws BuzException
     */
    @POST
    @Consumes("application/json")
    @Path("/dye")
    ResponseVO create(IhcApplicationVO ihcApplicationVO) throws BuzException;

    /**
     * 获取我的蜡块
     *
     * @param page
     * @param length
     * @param createTimeStart
     * @param createTimeEnd
     * @return
     */
    @GET
    @Path("/blocks")
    ResponseVO getBlocks(@QueryParam("page") @DefaultValue("1") Integer page,
                         @QueryParam("length") @DefaultValue("10") Integer length,
                         @QueryParam("createTimeStart") Long createTimeStart,
                         @QueryParam("createTimeEnd") Long createTimeEnd,
                         @QueryParam("pathNo") String pathNo);

    /**
     * 获取科室列表
     *
     * @return
     * @throws BuzException
     */
    @GET
    @Path("/departments")
    ResponseVO getDepartments() throws BuzException;

    /**
     * 获取科室描述
     *
     * @param code
     * @return
     */
    @GET
    @Path("/departments/{code}")
    ResponseVO getDepartmentDesc(@PathParam("code") Integer code);

    /**
     * 获取用户信息
     * @return
     */
    @GET
    @Path("/user")
    ResponseVO getUserInfo();

    /**
     * 修改用户信息
     * @return
     */
    @POST
    @Path("/user")
    ResponseVO updateUserInfo(UserUpdateReq userUpdateReq) throws BuzException;

    /**
     * 获取染色类别
     * @return
     */
    @GET
    @Path("/dye/type")
    ResponseVO getDyeType() throws BuzException;

    /**
     * 撤销冰冻预约/常规申请
     * @param id
     * @return
     * @throws BuzException
     */
    @POST
    @Path("/application/cancel/{id}")
    ResponseVO cancelApplication(@PathParam("id") Long id)throws BuzException;

    /**
     * 撤销染色申请
     * @param id
     * @return
     * @throws BuzException
     */
    @POST
    @Path("/dye/cancel/{id}")
    ResponseVO cancelDye(@PathParam("id") Long id)throws BuzException;

    /**
     * 空接口，用于验证cookie是否过期
     * @return
     */
    @GET
    ResponseVO testCookie() throws BuzException;

    /**
     * 获取申请详细数据
     * @param id
     * @param type
     * @return
     * @throws BuzException
     */
    @GET
    @Path("/application/detail")
    ResponseVO getApplicationDetail(@QueryParam(value = "id") long id,@QueryParam("type") int type) throws BuzException;

    /**
     * 查看我的申请信息
     * @return
     */
    @GET
    @Path("/application")
    ResponseVO getMyApplications(@QueryParam("page") @DefaultValue("1") Integer page,
                                 @QueryParam("length") @DefaultValue("10") Integer length,
                                 @QueryParam("createTimeStart") Long createTimeStart,
                                 @QueryParam("createTimeEnd") Long createTimeEnd,
                                 @QueryParam("status")Integer status,
                                 @QueryParam("type")Integer type,
                                 @QueryParam("filter")String filter,
                                 @QueryParam("sort") Integer sort)throws BuzException;

    /**
     * 检查申请变动情况
     * @return
     * @throws BuzException
     */
    @GET
    @Path("/check")
    ResponseVO checkMessage() throws BuzException;
}
