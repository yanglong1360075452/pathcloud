package com.lifetech.dhap.pathcloud.statistic.api;

import com.lifetech.dhap.pathcloud.common.exception.BuzException;
import com.lifetech.dhap.pathcloud.common.response.ResponseVO;

import javax.ws.rs.*;

/**
 * Created by LiuMei on 2017-02-06.
 */
@Produces({"application/json"})
@Path("/statistics")
public interface StatisticsApi {

    /**
     * 制片质量
     *
     * @param startTime
     * @param endTime
     * @return
     * @throws BuzException
     */
    @GET
    @Path("/slideQuality")
    ResponseVO getSlideQuality(@QueryParam("startTime") Long startTime,
                               @QueryParam("endTime") Long endTime) throws BuzException;

    /**
     * 检查项目
     *
     * @param startTime
     * @param endTime
     * @return
     * @throws BuzException
     */
    @GET
    @Path("/inspectCategory")
    ResponseVO getInspectCategory(@QueryParam("startTime") Long startTime,
                                  @QueryParam("endTime") Long endTime) throws BuzException;

    /**
     * 重补取
     *
     * @param startTime
     * @param endTime
     * @return
     * @throws BuzException
     */
    @GET
    @Path("/reGrossing")
    ResponseVO getReGrossing(@QueryParam("startTime") Long startTime,
                             @QueryParam("endTime") Long endTime) throws BuzException;

    /**
     * 重切率
     * 包括重切和深切
     *
     * @param startTime
     * @param endTime
     * @return
     * @throws BuzException
     */
    @GET
    @Path("/reSection")
    ResponseVO getReSection(@QueryParam("startTime") Long startTime,
                             @QueryParam("endTime") Long endTime) throws BuzException;

    /**
     *异常蜡块
     * @param startTime
     * @param endTime
     * @return
     * @throws BuzException
     */
    @GET
    @Path("/errorBlock")
    ResponseVO getErrorBlocks(@QueryParam("startTime") Long startTime,
                            @QueryParam("endTime") Long endTime) throws BuzException;

    /**
     *异常切片
     * @param startTime
     * @param endTime
     * @return
     * @throws BuzException
     */
    @GET
    @Path("/errorSlide")
    ResponseVO getErrorSlides(@QueryParam("startTime") Long startTime,
                        @QueryParam("endTime") Long endTime) throws BuzException;

    /**
     * 特染/常规统计
     *
     * @return
     * @throws BuzException
     */
    @GET
    @Path("/specialDye")
    ResponseVO getSpecialDye() throws BuzException;

    /**
     * 按照工位/时间统计工作量
     *
     * @param startTime
     * @param endTime
     * @param workStation
     * @return
     * @throws BuzException
     */
    @GET
    @Path("/workLoad")
    ResponseVO getWorkload(@QueryParam("startTime") Long startTime,
                           @QueryParam("endTime") Long endTime,
                           @QueryParam("workStation") Integer workStation) throws BuzException;

    /**
     * 平均等待时间
     * @return
     * @throws BuzException
     */
    @GET
    @Path("/waitTime")
    ResponseVO getAvgWaitTime() throws BuzException;

    /**
     * 质控评分
     * @param startTime
     * @param endTime
     * @return
     * @throws BuzException
     */
    @GET
    @Path("/score")
    ResponseVO getScore(@QueryParam("startTime") Long startTime,
                        @QueryParam("endTime") Long endTime) throws BuzException;

}
