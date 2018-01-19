package com.lifetech.dhap.pathcloud.dehydrate.api;

import com.lifetech.dhap.pathcloud.common.exception.BuzException;
import com.lifetech.dhap.pathcloud.common.response.ResponseVO;
import com.lifetech.dhap.pathcloud.dehydrate.api.vo.DehydratorVO;

import javax.ws.rs.*;

/**
 * 脱水机设置
 *
 * @author yun.cao
 *         <p>
 *         Create at 20161207
 */

@Produces({"application/json"})
@Path("/dehydrator")
public interface DehydratorSettingApi {
    /**
     * 添加脱水机
     *
     * @param dehydratorVO
     * @return
     * @throws BuzException
     */
    @POST
    @Consumes("application/json")
    ResponseVO addDehydrator(DehydratorVO dehydratorVO) throws BuzException;

    /**
     * 移除脱水机
     *
     * @param instrumentId instrument主键
     * @return
     * @throws BuzException
     */
    @DELETE
    @Path("/{instrumentId}")
    ResponseVO removeDehydrator(@PathParam("instrumentId") Long instrumentId) throws BuzException;

    @GET
    @Path("/status")
    ResponseVO getDehydratorStatus() throws BuzException;

    /**
     * 列出警告信息
     *
     * @param instrumentId instrument主键
     * @return
     * @throws BuzException
     */
    @GET
    @Path("/{instrumentId}/errmsg")
    ResponseVO getWarningMsg(@PathParam("instrumentId") Long instrumentId, @QueryParam("page") @DefaultValue("1") Integer page,
                             @QueryParam("length") @DefaultValue("10") Integer length, @QueryParam("startTime") Long startTime, @QueryParam("endTime") Long endTime) throws BuzException;

    /**
     * 列出脱水机内包埋盒信息
     *
     * @param instrumentId
     * @return
     * @throws BuzException
     */
    @GET
    @Path("/{instrumentId}/blocks")
    ResponseVO getBlocksInfo(@PathParam("instrumentId") Long instrumentId, @QueryParam("page") @DefaultValue("1") Integer page,
                             @QueryParam("length") @DefaultValue("10") Integer length) throws BuzException;

    /**
     * 清除警告信息
     *
     * @param instrumentId instrument主键
     * @return
     * @throws BuzException
     */
    @PUT
    @Path("/{instrumentId}/removealarm")
    ResponseVO clearWarningMsg(@PathParam("instrumentId") Long instrumentId) throws BuzException;

    @GET
    ResponseVO getDehydrators(@QueryParam("inUse") Boolean inUse, @QueryParam("disabled") Boolean disabled,
                              @QueryParam("page") @DefaultValue("1") Integer page,
                              @QueryParam("length") @DefaultValue("10") Integer length,
                              @QueryParam("status")  Integer status) throws BuzException;


    /**
     * 获取最后添加的脱水机
     *
     * @param
     * @return
     * @throws BuzException
     */
    ResponseVO getLastDehydrator();

    /**
     * 检查sn是否存在
     *
     * @param sn
     * @return
     * @throws BuzException
     */
    @GET
    @Path("check/sn/{sn}")
    ResponseVO checkSn(@PathParam("sn") String sn) throws BuzException;

    /**
     * 检查编号是否存在
     *
     * @param name
     * @return
     * @throws BuzException
     */
    @GET
    @Path("/check/name/{name}")
    ResponseVO checkName(@PathParam("name") String name) throws BuzException;


}
