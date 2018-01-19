package com.lifetech.dhap.pathcloud.statistic.api;

import com.lifetech.dhap.pathcloud.common.exception.BuzException;
import com.lifetech.dhap.pathcloud.common.response.ResponseVO;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.File;

/**
 * 工作量统计
 */
@Produces({"application/json"})
@Path("/statistics/workload")
public interface WorkloadStatisticsApi {

    /**
     * 按月统计
     * @param startTime
     * @param endTime
     * @return
     * @throws BuzException
     */
    @GET
    @Path("/monthInspectCategory")
    ResponseVO monthInspectCategory(@QueryParam("startTime") Long startTime,
        @QueryParam("endTime") Long endTime, @QueryParam("hospital") String hospital) throws BuzException;



    /**
     * 按人统计
     * @param startTime
     * @param endTime
     * @return
     * @throws BuzException
     */
    @GET
    @Path("/personInspectCategory")
    ResponseVO personInspectCategory(@QueryParam("page") @DefaultValue("1") Integer page,
                                     @QueryParam("length") @DefaultValue("10") Integer length,
                                     @QueryParam("startTime") Long startTime,
                                    @QueryParam("endTime") Long endTime,
                                     @QueryParam("station") Integer station,
                                     @QueryParam("order") Integer order,
                                     @QueryParam("sort") String sort) throws BuzException;


    /**
     * 按组统计
     * @param startTime
     * @param endTime
     * @return
     * @throws BuzException
     */
    @GET
    @Path("/groupInspectCategory")
    ResponseVO groupInspectCategory(@QueryParam("startTime") Long startTime,
                                     @QueryParam("endTime") Long endTime, @QueryParam("filter") Integer filter) throws BuzException;



    /**
     * 按人统计导出
     * @param
     * @param
     * @return
     * @throws BuzException
     */
    @GET
    @Produces("text/plain")
    @Path("/personInspectCategory/export")
    Response personInspectCategoryExport(
            @QueryParam("startTime") Long startTime,
            @QueryParam("endTime") Long endTime,
            @QueryParam("station") Integer station) throws Exception;


    /**
     * 按月统计导出
     * @param
     * @param
     * @return
     * @throws BuzException
     */
    @GET
    @Produces("text/plain")
    @Path("/monthInspectCategory/export")
    Response monthInspectCategoryExport(
            @QueryParam("startTime") Long startTime,
            @QueryParam("endTime") Long endTime, @QueryParam("hospital") String hospital) throws Exception;


    /**
     * 按组统计导出
     * @param
     * @param
     * @return
     * @throws BuzException
     */
    @GET
    @Produces("text/plain")
    @Path("/groupInspectCategory/export")
    Response groupInspectCategoryExport(
            @QueryParam("startTime") Long startTime,
            @QueryParam("endTime") Long endTime, @QueryParam("filter") Integer filter) throws Exception;
}
