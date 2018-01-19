package com.lifetech.dhap.pathcloud.tracking.api;

import com.lifetech.dhap.pathcloud.common.exception.BuzException;
import com.lifetech.dhap.pathcloud.common.response.ResponseVO;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

/**
 * Created by LiuMei on 2017-02-06.
 */
@Produces({"application/json"})
@Path("/query")
public interface QueryApi {

    /**
     * 查询病例信息
     * @param filter
     * @param fieldType
     * @param fieldContain
     * @param fieldExclusive
     * @param specialDye
     * @param inspectionCategory
     * @param pathStatus
     * @param departments
     * @param inspectionDoctor
     * @param diagnoseDoctor
     * @param receiveTimeStart
     * @param receiveTimeEnd
     * @param reportTimeStart
     * @param reportTimeEnd
     * @return
     * @throws BuzException
     */
    @GET
    ResponseVO query(
            @QueryParam("page") @DefaultValue("1") Integer page,
            @QueryParam("length") @DefaultValue("10") Integer length,
            @QueryParam("filter") String filter,
            @QueryParam("fieldType") String fieldType,
            @QueryParam("fieldContain") String fieldContain,
            @QueryParam("fieldExclusive") String fieldExclusive,
            @QueryParam("specialDye") Integer specialDye,
            @QueryParam("inspectionCategory") Integer inspectionCategory,
            @QueryParam("pathStatus") Integer pathStatus,
            @QueryParam("departments") Integer departments,
            @QueryParam("inspectionDoctor") Long inspectionDoctor,
            @QueryParam("diagnoseDoctor") Long diagnoseDoctor,
            @QueryParam("receiveTimeStart") Long receiveTimeStart,
            @QueryParam("receiveTimeEnd") Long receiveTimeEnd,
            @QueryParam("reportTimeStart") Long reportTimeStart,
            @QueryParam("reportTimeEnd") Long reportTimeEnd,
            @QueryParam("order") Integer order,
            @QueryParam("sort") String sort) throws BuzException;

    /**
     * 科研查询
     * @param page
     * @param length
     * @param filter
     * @param departments
     * @return
     * @throws BuzException
     */
    @GET
    @Path("/research")
    ResponseVO researchQuery(@QueryParam("page") @DefaultValue("1") Integer page,
                             @QueryParam("length") @DefaultValue("10") Integer length,
                             @QueryParam("filter") String filter,
                             @QueryParam("departments") Integer departments,
                             @QueryParam("applyTimeStart") Long applyTimeStart,
                             @QueryParam("applyTimeEnd") Long applyTimeEnd,
                             @QueryParam("order") Integer order,
                             @QueryParam("sort") String sort) throws BuzException;

    /**
     * 医嘱查询
     * @param page
     * @param length
     * @param filter
     * @param applyType
     * @return
     * @throws BuzException
     */
    @GET
    @Path("/advice")
    ResponseVO advice(@QueryParam("page") @DefaultValue("1") Integer page,
                      @QueryParam("length") @DefaultValue("10") Integer length,
                      @QueryParam("filter") String filter,
                      @QueryParam("applyType") Integer applyType,
                      @QueryParam("status") @DefaultValue("0") Integer status,
                      @QueryParam("inspectionDoctor") Long inspectionDoctor,
                      @QueryParam("applyTimeStart") Long applyTimeStart,
                      @QueryParam("applyTimeEnd") Long applyTimeEnd,
                      @QueryParam("order") Integer order,
                      @QueryParam("sort") String sort) throws BuzException;


    /**
     * 医嘱申请操作记录查询
     * @param applyId
     * @return
     * @throws BuzException
     */
    @GET
    @Path("/advice/{applyId}")
    ResponseVO adviceOperationLog(@PathParam("applyId") Long applyId,
                                  @QueryParam("blockId") Long blockId) throws BuzException;

    /**
     * 获取送检医生列表
     * @return
     * @throws BuzException
     */
    @GET
    @Path("/inspect")
    ResponseVO getInspectDoctor() throws BuzException;

    /**
     * 获取样本基本信息
     * @param pathId
     * @return
     * @throws BuzException
     */
    @GET
    @Path("/sample/{pathId}/base")
    ResponseVO getSampleBaseInfo(@PathParam("pathId") Long pathId) throws BuzException;

    /**
     * 获取制片信息
     * @param pathId
     * @return
     * @throws BuzException
     */
    @GET
    @Path("/sample/{pathId}/confirm")
    ResponseVO getConfirmInfo(@PathParam("pathId") Long pathId) throws BuzException;

    /**
     * 特染申请信息
     * @param pathId
     * @return
     * @throws BuzException
     */
    @GET
    @Path("/specialDye/{pathId}")
    ResponseVO getSpecialDyeInfo(@PathParam("pathId") long pathId,@QueryParam("specialDye") Integer specialDye) throws BuzException;

    /**
     * 获取样本取材信息
     * @param pathId
     * @return
     * @throws BuzException
     */
    @GET
    @Path("/sample/{pathId}/grossing")
    ResponseVO getSampleGrossingInfo(@PathParam("pathId") Long pathId,@QueryParam("number") String number) throws BuzException;


    /**
     * 获取样本特染信息
     * @param pathId 病理号
     * @param specialDye 特染类别 1-免疫组化 >1 特染
     * @return
     * @throws BuzException
     */
    @GET
    @Path("/specialDye/{pathId}/confirm")
    ResponseVO getSampleSpecialDye(@PathParam("pathId") Long pathId,
                                   @QueryParam("specialDye") Integer specialDye) throws BuzException;

    /**
     * 查询结果导出
     * @param filter
     * @param fieldType
     * @param fieldContain
     * @param fieldExclusive
     * @param specialDye
     * @param inspectionCategory
     * @param pathStatus
     * @param departments
     * @param inspectionDoctor
     * @param diagnoseDoctor
     * @param receiveTimeStart
     * @param receiveTimeEnd
     * @param reportTimeStart
     * @param reportTimeEnd
     * @return
     * @throws Exception
     */
    @GET
    @Path("/export")
    @Produces("text/plain")
    Response queryExport(
                           @QueryParam("filter") String filter,
                           @QueryParam("fieldType") String fieldType,
                           @QueryParam("fieldContain") String fieldContain,
                           @QueryParam("fieldExclusive") String fieldExclusive,
                           @QueryParam("specialDye") Integer specialDye,
                           @QueryParam("inspectionCategory") Integer inspectionCategory,
                           @QueryParam("pathStatus") Integer pathStatus,
                           @QueryParam("departments") Integer departments,
                           @QueryParam("inspectionDoctor") Long inspectionDoctor,
                           @QueryParam("diagnoseDoctor") Long diagnoseDoctor,
                           @QueryParam("receiveTimeStart") Long receiveTimeStart,
                           @QueryParam("receiveTimeEnd") Long receiveTimeEnd,
                           @QueryParam("reportTimeStart") Long reportTimeStart,
                           @QueryParam("reportTimeEnd") Long reportTimeEnd) throws Exception;

    @GET
    @Path("/research/export")
    @Produces("text/plain")
    Response researchQueryExport(@QueryParam("page") @DefaultValue("1") Integer page, @QueryParam("length") @DefaultValue("1000") Integer length,@QueryParam("filter") String filter,
                             @QueryParam("departments") Integer departments,@QueryParam("applyTimeStart") Long applyTimeStart,
                                 @QueryParam("applyTimeEnd") Long applyTimeEnd) throws Exception;

    @GET
    @Path("/archiving/{pid}")
    ResponseVO getArchivingInfo(@PathParam("pid") long pid)throws Exception;

    /**
     * 根据病理ID获取该病理号下报告相关信息
     * @param pathId
     * @return
     */
    @GET
    @Path("/report/summary/{pathId}")
    ResponseVO getReportSummary(@PathParam("pathId") long pathId) throws BuzException;

    /**
     * 根据病理号获取特殊编号
     * @param pathNo
     * @return
     * @throws BuzException
     */
    @GET
    @Path("/special/{pathNo}")
    ResponseVO getSpecialNumber(@PathParam("pathNo") String pathNo) throws BuzException;
}
