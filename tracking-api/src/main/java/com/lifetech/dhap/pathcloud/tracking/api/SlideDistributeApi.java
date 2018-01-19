package com.lifetech.dhap.pathcloud.tracking.api;

import com.lifetech.dhap.pathcloud.common.exception.BuzException;
import com.lifetech.dhap.pathcloud.common.response.ResponseVO;
import com.lifetech.dhap.pathcloud.tracking.api.vo.NotificationVO;
import com.lifetech.dhap.pathcloud.tracking.api.vo.SlideDistributeVO;

import javax.ws.rs.*;

/**
 * Created by LiuMei on 2017-01-03.
 */
@Produces({"application/json"})
@Path("/distribute")
public interface SlideDistributeApi {

    /**
     * 待派片列表
     * @param page
     * @param length
     * @return
     * @throws BuzException
     */
    @GET
    ResponseVO getPrepareDistribute(@QueryParam("page") @DefaultValue("1") Integer page,
                                    @QueryParam("length") @DefaultValue("10") Integer length,
                                    @QueryParam("departments") Integer departments, //送检科室
                                    @QueryParam("operator") Long operator, //取材医生
                                    @QueryParam("order") Integer order,
                                    @QueryParam("sort") String sort
    ) throws BuzException;

    /**
     * 派片
     * @param slideDistributeVO
     * @return
     * @throws BuzException
     */
    @POST
    ResponseVO distribute(SlideDistributeVO slideDistributeVO) throws BuzException;

    /**
     * 派片历史
     * @param page
     * @param length
     * @return
     * @throws BuzException
     */
    @GET
    @Path("/history")
    ResponseVO distributeHistory(@QueryParam("page") @DefaultValue("1") Integer page,
                                 @QueryParam("length") @DefaultValue("10") Integer length,
                                 @QueryParam("filter") String filter,
                                 @QueryParam("departments") Integer departments, //送检科室
                                 @QueryParam("operator") Long operator, //取材医生
                                 @QueryParam("timeStart") Long timeStart,
                                 @QueryParam("timeEnd") Long timeEnd,
                                 @QueryParam("order") Integer order,
                                 @QueryParam("sort") String sort) throws BuzException;

    @GET
    @Path("/history/{pathNo}")
    ResponseVO getDistributeByPathNo(@PathParam("pathNo") String pathNo, @QueryParam("page") @DefaultValue("1") Integer page,
                                     @QueryParam("length") @DefaultValue("10") Integer length
    ) throws BuzException;

    /**
     * 异常样本统计
     *
     * @return
     */
    @GET
    @Path("statistic")
    ResponseVO blockStatusStatistic();

    /**
     * 异常样本详情
     *
     * @param page
     * @param length
     * @return
     */
    @GET
    @Path("abnormal/{status}")
    ResponseVO abnormalBlock(@QueryParam("page") @DefaultValue("1") Integer page,
                             @QueryParam("length") @DefaultValue("10") Integer length,
                             @PathParam("status") Integer status, @QueryParam("totalError") Long totalError);


    /**
     * 异常处理
     *
     * @param notification
     * @return
     * @throws BuzException
     */
    @POST
    @Path("abnormalBlockDeal")
    ResponseVO abnormalBlockDeal(NotificationVO notification) throws BuzException;
}
