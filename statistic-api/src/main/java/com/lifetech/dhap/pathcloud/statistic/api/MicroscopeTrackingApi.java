package com.lifetech.dhap.pathcloud.statistic.api;

import com.lifetech.dhap.pathcloud.common.exception.BuzException;
import com.lifetech.dhap.pathcloud.common.response.ResponseVO;
import com.lifetech.dhap.pathcloud.statistic.api.vo.MicroscopeVO;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

/**
 * @author eric.guo@ostengar.com
 * @version 1.0
 * @time 2017-06-22-10:36
 */
@Produces({"application/json"})
@Path("/microscopeTracking")
public interface MicroscopeTrackingApi {

    /**
     * 开始使用显微镜
     */
    @POST
    @Consumes("application/json")
    ResponseVO startUserMicroscope(MicroscopeVO vo) throws BuzException;


    /**
     * 显微镜使用记录
     * @param year
     * @param month
     * @return
     * @throws BuzException
     */
    @GET
    ResponseVO microscopeTracking(
            @QueryParam("page") @DefaultValue("1") Integer page,
            @QueryParam("length") @DefaultValue("10") Integer length,
            @QueryParam("year") Integer year,
            @QueryParam("month") Integer month,
            @QueryParam("instrumentId") Integer instrumentId
    ) throws BuzException;


    /**
     * 显微镜使用记录导出
     * @param year
     * @param month
     * @return
     * @throws BuzException
     */
    @GET
    @Path("/export")
    Response microscopeTrackingExport(@QueryParam("year") Integer year,
                                 @QueryParam("month") Integer month) throws Exception;
}
