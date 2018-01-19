package com.lifetech.dhap.pathcloud.statistic.api;

import com.lifetech.dhap.pathcloud.common.exception.BuzException;
import com.lifetech.dhap.pathcloud.common.response.ResponseVO;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

/**
 * Created by HP on 2017/9/6.
 */
@Produces({"application/json"})
@Path("/instrumentTracking")
public interface InstrumentTrackingApi {

    /**
     * 染色机和封片机使用记录
     * @param year
     * @param month
     * @return
     * @throws BuzException
     */
    @GET
    ResponseVO InstrumentTracking(
            @QueryParam("page") @DefaultValue("1") Integer page,
            @QueryParam("length") @DefaultValue("10") Integer length,
            @QueryParam("year") Integer year,
            @QueryParam("month") Integer month,
            @QueryParam("status") Integer status,
            @QueryParam("type") Integer type
    ) throws BuzException;



    /**
     * 染色机和封片机使用记录导出
     * @param year
     * @param month
     * @return
     * @throws BuzException
     */
    @GET
    @Path("/export")
    Response InstrumentTrackingExport(@QueryParam("year") Integer year,
                                      @QueryParam("month") Integer month,
                                      @QueryParam("status") Integer status,
                                      @QueryParam("type") Integer type) throws Exception;
}
