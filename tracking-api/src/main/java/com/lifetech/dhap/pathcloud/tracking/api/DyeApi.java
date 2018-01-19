package com.lifetech.dhap.pathcloud.tracking.api;

import com.lifetech.dhap.pathcloud.common.exception.BuzException;
import com.lifetech.dhap.pathcloud.common.response.ResponseVO;
import com.lifetech.dhap.pathcloud.tracking.api.vo.DyeConfirmVO;

import javax.ws.rs.*;

/**
 * Created by LiuMei on 2017-03-22.
 */

@Produces({"application/json"})
@Path("/dye")
public interface DyeApi {

    /**
     * 根据病理号或蜡块号或玻片号查询待染色玻片信息
     *
     * @param serialNumber
     * @return
     * @throws BuzException
     */
    @GET
    @Path("/{serialNumber}")
    ResponseVO getSlideBySerialNumber(@PathParam("serialNumber") String serialNumber) throws BuzException;

    /**
     * 确认染色
     *
     * @param dyeConfirmVO
     * @return
     * @throws BuzException
     */
    @POST
    @Path("/confirm")
    ResponseVO dyeConfirm(DyeConfirmVO dyeConfirmVO) throws BuzException;

    /**
     * 批量染色
     * @param dyeConfirmVO
     * @return
     * @throws BuzException
     */
    @POST
    @Path("/confirm/batch")
    ResponseVO batchDye(DyeConfirmVO dyeConfirmVO) throws BuzException;

    /**
     * 染色的信息查询
     */
    @GET
    @Path("/query")
    ResponseVO getDyeQuery(@QueryParam("page") @DefaultValue("1") Integer page,
                           @QueryParam("length") @DefaultValue("10") Integer length,
                           @QueryParam("filter") String filter,
                           @QueryParam("startTime") Long startTime,
                           @QueryParam("endTime") Long endTime,
                           @QueryParam("status") Integer status,
                           @QueryParam("operatorId") Long operatorId,
                           @QueryParam("mountingId") Long mountingId,
                           @QueryParam("dyeInstrumentId") Long dyeInstrumentId,
                           @QueryParam("mountingInstrumentId") Long mountingInstrumentId,
                           @QueryParam("order") Integer order,
                           @QueryParam("sort") String sort) throws BuzException;

    @GET
    @Path("/person")
    ResponseVO getDyePerson() throws BuzException;

    /**
     * 获取一些统计信息
     * 16-待染色  17-待封片
     *
     * @return
     * @throws BuzException
     */
    @GET
    @Path("/summary/{status}")
    ResponseVO getSummary(@PathParam("status") int status) throws BuzException;

    /**
     * 获取待染色/待封片列表
     *
     * @param status
     * @param currentUser
     * @return
     * @throws BuzException
     */
    @GET
    @Path("/list/{status}/{currentUser}")
    ResponseVO getSlideList(@PathParam("status") int status, @PathParam("currentUser") boolean currentUser,
                            @QueryParam("page") @DefaultValue("1") Integer page,
                            @QueryParam("length") @DefaultValue("10") Integer length, @QueryParam("order") Integer order,
                            @QueryParam("sort") String sort) throws BuzException;
}
