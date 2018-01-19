package com.lifetech.dhap.pathcloud.tracking.api;

import com.lifetech.dhap.pathcloud.common.exception.BuzException;
import com.lifetech.dhap.pathcloud.common.response.ResponseVO;
import com.lifetech.dhap.pathcloud.tracking.api.vo.NoteVO;
import com.lifetech.dhap.pathcloud.tracking.api.vo.SlidePrintVO;

import javax.ws.rs.*;
import java.util.List;

/**
 * 切片接口
 *
 * @author eric.guo@ostengar.com
 * @version 1.0
 * @time 2016-12-19-15:44
 */

@Produces({"application/json"})
@Path("/section")
public interface SectionApi {

    /**
     * 获取蜡块/玻片信息
     * @param blockSerialNumber
     * @param prePathId
     * @param slideSubId
     * @return
     * @throws BuzException
     */
    @GET
    @Path("/{blockSerialNumber}")
    ResponseVO getBlockBySerialNumber(@PathParam("blockSerialNumber") String blockSerialNumber,
                                      @QueryParam("prePathId") Long prePathId,@QueryParam("slideSubId") String slideSubId) throws BuzException;

    /**
     * 一个蜡块一次切多个玻片
     * @param noteVOS
     * @return
     * @throws BuzException
     */
    @POST
    @Path("/confirm")
    ResponseVO sectionsConfirm(List<NoteVO>noteVOS) throws BuzException;


    /**
     * 获取蜡块数切片统计信息
     * @return
     * @throws BuzException
     */
    @GET
    @Path("/count")
    ResponseVO getBlockCount() throws BuzException;

    /**
     * 获取切片搜索
     * @param page
     * @param length
     * @param filter
     * @param startTime
     * @param endTime
     * @param status
     * @param operatorId
     * @param order
     * @param sort
     * @return
     * @throws BuzException
     */
    @GET
    @Path("/query")
    ResponseVO getSectionsQuery(
                                @QueryParam("page") @DefaultValue("1") Integer page,
                                @QueryParam("length") @DefaultValue("10") Integer length,
                                @QueryParam("filter")String filter,
                                @QueryParam("startTime")Long startTime,
                                @QueryParam("endTime")Long endTime,
                                @QueryParam("status")Integer status,
                                @QueryParam("dyeCategory")Integer dyeCategory,
                                @QueryParam("printCount")Integer printCount,
                                @QueryParam("operatorId")Long operatorId,
                                @QueryParam("order") Integer order,
                                @QueryParam("sort") String sort) throws BuzException;


    @GET
    @Path("/person")
    ResponseVO getSectionPerson() throws BuzException;


    @GET
    @Path("/{blockId}/slides")
    ResponseVO getSlideInfo(@QueryParam("page") @DefaultValue("1") Integer page,
                                                    @QueryParam("length") @DefaultValue("10") Integer length,
                                                    @PathParam("blockId") long blockId) throws BuzException;

    /**
     * 蜡块打印常规玻片
     * @param slidePrintVOS
     * @return
     * @throws BuzException
     */
    @POST
    @Path("/print")
    ResponseVO slidePrint(List<SlidePrintVO> slidePrintVOS) throws BuzException;

}
