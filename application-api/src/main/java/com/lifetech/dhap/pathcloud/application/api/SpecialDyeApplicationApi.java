package com.lifetech.dhap.pathcloud.application.api;

import com.lifetech.dhap.pathcloud.application.api.vo.IhcApplicationVO;
import com.lifetech.dhap.pathcloud.common.exception.BuzException;
import com.lifetech.dhap.pathcloud.common.response.ResponseVO;

import javax.ws.rs.*;

/**
 * Created by LiuMei on 2017-04-01.
 *
 * 特检
 */
@Produces({"application/json"})
@Path("/specialDye")
public interface SpecialDyeApplicationApi {

    /**
     * 创建特检申请
     *
     * @param ihcApplicationVO
     * @return
     * @throws BuzException
     */
    @POST
    @Consumes("application/json")
    ResponseVO create(IhcApplicationVO ihcApplicationVO) throws BuzException;

    /**
     * 特检申请补充蜡块
     * @param ihcApplicationVO
     * @return
     * @throws BuzException
     */
    @PUT
    @Consumes("application/json")
    ResponseVO updateBlock(IhcApplicationVO ihcApplicationVO) throws BuzException;

    /**
     * 撤销申请
     * @param ihcBlockId
     * @return
     * @throws BuzException
     */
    @POST
    @Path("/cancel/{ihcBlockId}")
    ResponseVO cancel(@PathParam("ihcBlockId") Long ihcBlockId)throws BuzException;

    /**
     * 查询特检申请
     *
     * @param
     * @return
     * @throws BuzException
     */
    @GET
    @Path("/query")
    ResponseVO getApplicationIHCs(@QueryParam("page") @DefaultValue("1") Integer page ,
                                  @QueryParam("length") @DefaultValue("10") Integer length,
                                  @QueryParam("filter")String filter,
                                  @QueryParam("createTimeStart") Long createTimeStart,
                                  @QueryParam("createTimeend")Long createTimeEnd,
                                  @QueryParam("dyeCategory")Integer dyeCategory,
                                  @QueryParam("order") Integer order,
                                  @QueryParam("sort") String sort) throws BuzException;

    /**
     * 获取我的蜡块
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
     * 检查标记物
     * @param blockId
     * @param marker
     * @return
     * @throws BuzException
     */
    @GET
    @Path("/check/{blockId}/{marker}")
    ResponseVO checkMarker(@PathParam("blockId") long blockId,@PathParam("marker") String marker) throws BuzException;
}