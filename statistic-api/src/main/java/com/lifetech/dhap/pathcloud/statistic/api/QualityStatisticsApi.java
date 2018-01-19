package com.lifetech.dhap.pathcloud.statistic.api;

import com.lifetech.dhap.pathcloud.common.exception.BuzException;
import com.lifetech.dhap.pathcloud.common.response.ResponseVO;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

/**
 * 质控评分统计
 *
 */
@Produces({"application/json"})
@Path("/statistics/quality")
public interface QualityStatisticsApi {

    /**
     * 按月统计
     * @param year
     * @param specialDye
     * @return
     * @throws BuzException
     */
    @GET
    @Path("/month")
    ResponseVO monthQualityControl(@QueryParam("year") Integer year,
                                    @QueryParam("specialDye") Integer specialDye) throws BuzException;

    /**
     * 按人统计
     * @param page
     * @param length
     * @param year
     * @param month
     * @param station
     * @param specialDye
     * @return
     * @throws BuzException
     */
    @GET
    @Path("/person")
    ResponseVO personQualityControl(@QueryParam("page") @DefaultValue("1") Integer page,
                                     @QueryParam("length") @DefaultValue("10") Integer length,
                                     @QueryParam("order") Integer order,
                                     @QueryParam("sort")  String sort,
                                     @QueryParam("year") Integer year,
                                     @QueryParam("month") Integer month,
                                     @QueryParam("station") Integer station,
                                     @QueryParam("specialDye") Integer specialDye) throws BuzException;

    /**
     * 优良率统计
     * @param year
     * @return
     * @throws BuzException
     */
    @GET
    @Path("/good")
    ResponseVO goodRate(@QueryParam("year") Integer year) throws BuzException;

    /**
     * 符合率
     * @param year
     * @return
     * @throws BuzException
     */
    @GET
    @Path("/coincidence")
    ResponseVO getCoincidence(@QueryParam("year") Integer year) throws BuzException;

    /**
     * 报告及时率统计
     * @param year
     * @return
     * @throws BuzException
     */
    @GET
    @Path("/report")
    ResponseVO report(@QueryParam("year") Integer year) throws BuzException;

    /**
     * 按月统计导出
     * @param year
     * @param specialDye
     * @return
     * @throws BuzException
     */
    @GET
    @Path("/month/export")
    Response monthQualityControlExport(@QueryParam("year") Integer year,
                                   @QueryParam("specialDye") Integer specialDye) throws Exception;



    /**
     * 按人统计导出
     *
     * @param year
     * @param month
     * @param station
     * @param specialDye
     * @return
     * @throws BuzException
     */
    @GET
    @Path("/person/export")
    Response personQualityControlExport(
                                    @QueryParam("order") String order,
                                    @QueryParam("year") Integer year,
                                    @QueryParam("month") Integer month,
                                    @QueryParam("station") Integer station,
                                    @QueryParam("specialDye") Integer specialDye) throws Exception;

}
