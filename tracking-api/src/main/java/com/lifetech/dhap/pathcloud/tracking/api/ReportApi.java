package com.lifetech.dhap.pathcloud.tracking.api;

import com.lifetech.dhap.pathcloud.common.exception.BuzException;
import com.lifetech.dhap.pathcloud.common.response.ResponseVO;
import com.lifetech.dhap.pathcloud.tracking.api.vo.ReportIdVO;

import javax.ws.rs.*;
import java.util.List;

/**
 * Created by LiuMei on 2017-01-19.
 */
@Produces({"application/json"})
@Path("/report")
public interface ReportApi {

    /**
     * 查询报告
     * @param
     * @return
     * @throws BuzException
     */
    @GET
    @Path("/query")
    ResponseVO reportQuery(@QueryParam("page") @DefaultValue("1") Integer page,
                           @QueryParam("length") @DefaultValue("10") Integer length,
                           @QueryParam("filter") String filter,
                           @QueryParam("startTime")Long startTime,
                           @QueryParam("endTime") Long endTime,
                           @QueryParam("departments") Integer departments,
                           @QueryParam("printStatus") Boolean printStatus,
                           @QueryParam("delay") Boolean delay,
                           @QueryParam("order") Integer order,
                           @QueryParam("sort") String sort) throws BuzException;

    /**
     * 添加操作记录
     * @param operation 打印报告-23  打印签收单-25 签收确认-26
     * @param reportIdVOS
     * @return
     * @throws BuzException
     */
    @POST
    @Path("/operate/{operation}")
    ResponseVO addTracking(@PathParam("operation") int operation,List<ReportIdVO> reportIdVOS)throws BuzException;


    /**
     * 打印报告记录查询
     * @param
     * @return
     * @throws BuzException
     */
    @GET
    @Path("/printRecordQuery")
    ResponseVO printRecordQuery(@QueryParam("id")long id,@QueryParam("specialApplyId") Long specialApplyId) throws BuzException;

    /**
     * 获取报告图片
     * @param
     * @return
     * @throws BuzException
     */
    @GET
    @Path("/pic")
    ResponseVO getReports(@QueryParam("pathIds") String pathIds,@QueryParam("specialApplyIds") String specialApplyIds) throws BuzException;


    /**
     * 查询打印人员
     * @param
     * @return
     * @throws BuzException
     */
    @GET
    @Path("/person")
    ResponseVO getPrintPerson() throws BuzException;

    /**
     * 签收确认查询
     * @param
     * @return
     * @throws BuzException
     */
    @GET
    @Path("/signQuery")
    ResponseVO signQuery(@QueryParam("page") @DefaultValue("1") Integer page,
                           @QueryParam("length") @DefaultValue("10") Integer length,
                           @QueryParam("filter") String filter,
                           @QueryParam("startTime")Long startTime,
                           @QueryParam("endTime") Long endTime,
                            @QueryParam("operation")Integer operation,
                           @QueryParam("departments") Integer departments,
                           @QueryParam("delay") Boolean delay,
                         @QueryParam("order") Integer order,
                         @QueryParam("sort") String sort) throws BuzException;

}
