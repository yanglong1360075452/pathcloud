package com.lifetech.dhap.pathcloud.statistic.api;

import com.lifetech.dhap.pathcloud.common.exception.BuzException;
import com.lifetech.dhap.pathcloud.common.response.ResponseVO;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

/**
 * 冰冻切片
 */

@Produces({"application/json"})
@Path("/statistics/freeze")
public interface FreezeBookingApi {

    /**
     * 冰冻预约
     * @param year
     * @param month
     * @return
     * @throws BuzException
     */
    @GET
    @Path("/booking")
    ResponseVO  freezeBookingQuery(@QueryParam("year") Integer year,
                                   @QueryParam("month") Integer month,
                                   @QueryParam("instrumentId") Integer instrumentId
                                   ) throws BuzException;


    /**
     *冰冻预约导出
     * @param year
     * @param month
     * @return
     * @throws BuzException
     */
    @GET
    @Path("/booking/export")
    Response freezeBookingExport(@QueryParam("year") Integer year,
                                   @QueryParam("month") Integer month) throws Exception;




}
