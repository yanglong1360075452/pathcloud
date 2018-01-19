package com.lifetech.dhap.pathcloud.reagent.api;

import com.lifetech.dhap.pathcloud.common.exception.BuzException;
import com.lifetech.dhap.pathcloud.common.response.ResponseVO;
import com.lifetech.dhap.pathcloud.reagent.api.vo.AdjustVO;
import com.lifetech.dhap.pathcloud.reagent.api.vo.StoreVO;

import javax.ws.rs.*;

/**
 * @author LiuMei
 * @date 2017-11-30.
 */
@Produces({"application/json"})
@Path("/store")
public interface StoreApi {

    /**
     * 入库
     *
     * @param storeVO
     * @return
     * @throws BuzException
     */
    @POST
    @Consumes({"application/json"})
    ResponseVO inStore(StoreVO storeVO) throws BuzException;

    /**
     * 修改库存
     *
     * @param storeVO
     * @return
     * @throws BuzException
     */
    @PUT
    @Consumes({"application/json"})
    ResponseVO updateStore(StoreVO storeVO) throws BuzException;

    /**
     * 获取库存
     *
     * @param
     * @return
     * @throws BuzException
     */
    @GET
    ResponseVO getStoreList(@QueryParam("page") @DefaultValue("1") Integer page,
                            @QueryParam("length") @DefaultValue("10") Integer length,
                            @QueryParam("filter") String filter,
                            @QueryParam("status") Integer status,
                            @QueryParam("type") Integer type,
                            @QueryParam("category") Integer category,
                            @QueryParam("year") Integer year,
                            @QueryParam("startTime") Long startTime,
                            @QueryParam("endTime") Long endTime) throws BuzException;

    /**
     * 获取库存使用详情
     * @param storeId
     * @return
     * @throws BuzException
     */
    @GET
    @Path("/record/{storeId}")
    ResponseVO getStoreRecord(@PathParam("storeId") long storeId,
                              @QueryParam("page") @DefaultValue("1") Integer page,
                              @QueryParam("length") @DefaultValue("10") Integer length) throws BuzException;

    /**
     * 调整库存
     * @param
     * @return
     * @throws BuzException
     */
    @POST
    @Consumes({"application/json"})
    @Path("/record")
    ResponseVO adjust(AdjustVO adjustVO) throws BuzException;
}
