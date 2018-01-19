package com.lifetech.dhap.pathcloud.tracking.api;

import com.lifetech.dhap.pathcloud.common.exception.BuzException;
import com.lifetech.dhap.pathcloud.common.response.ResponseVO;
import com.lifetech.dhap.pathcloud.tracking.api.vo.NoteVO;

import javax.ws.rs.*;

/**
 * 包埋接口
 *
 * @author eric.guo@ostengar.com
 * @version 1.0
 * @time 2016-12-19-15:44
 */
@Produces({"application/json"})
@Path("/embed")
public interface EmbedApi {

    @GET
    @Path("/{blockSerialNumber}")
    ResponseVO getBlockBySerialNumber(@PathParam("blockSerialNumber") String blockSerialNumber,@QueryParam("prePathId") Long prePathId) throws BuzException;

    /**
     * 包埋确认
     * @param blockId
     * @param noteVO
     * @return
     * @throws BuzException
     */
    @POST
    @Path("/confirm/{blockId}")
    ResponseVO embedConfirm(@PathParam("blockId") Long blockId,NoteVO noteVO) throws BuzException;

    /**
     * 获取蜡块数包埋统计信息
     * @return
     * @throws BuzException
     */
    @GET
    @Path("/count")
    ResponseVO getBlocksCount() throws BuzException;


    /**
     * 包埋的信息查询
     * @return
     * @throws BuzException
     */
    @GET
    @Path("/query")
    ResponseVO getEmbedsQuery(@QueryParam("page") @DefaultValue("1") Integer page,
                              @QueryParam("length") @DefaultValue("10") Integer length,
                              @QueryParam("filter")String filter,
                              @QueryParam("startTime")Long startTime,
                              @QueryParam("endTime")Long endTime,
                              @QueryParam("status")Integer status,
                              @QueryParam("operatorId")Long operatorId,
                              @QueryParam("order") Integer order,
                              @QueryParam("sort") String sort) throws  BuzException;

    @GET
    @Path("/person")
    ResponseVO getEmbedPerson() throws BuzException;

    /**
     * 申请重补取
     * @return
     * @throws BuzException
     */
    @POST
    @Consumes("application/json")
    @Path("/reGrossing")
    ResponseVO applyReGrossing(NoteVO noteVO) throws BuzException;

    /**
     * 暂停包埋
     * @param noteVO
     * @return
     * @throws BuzException
     */
    @POST
    @Consumes("application/json")
    @Path("/pause")
    ResponseVO pause(NoteVO noteVO) throws BuzException;

    /**
     * 取消暂停包埋
     * @param noteVO
     * @return
     * @throws BuzException
     */
    @POST
    @Consumes("application/json")
    @Path("/pause/cancel")
    ResponseVO cancelPause(NoteVO noteVO) throws BuzException;
}
