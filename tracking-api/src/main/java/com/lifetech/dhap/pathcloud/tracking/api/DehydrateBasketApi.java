package com.lifetech.dhap.pathcloud.tracking.api;

import com.lifetech.dhap.pathcloud.common.exception.BuzException;
import com.lifetech.dhap.pathcloud.common.response.ResponseVO;

import javax.ws.rs.*;

/**
 * Created by LiuMei on 2016-12-09.
 */
@Produces({"application/json"})
@Path("/basket")
public interface DehydrateBasketApi {

    /**
     * 脱水工作台调用
     *
     * @param status
     * @return
     * @throws BuzException
     */
    @GET
    @Path("/dehydrate/{status}")
    ResponseVO getDehydrateBaskets(@PathParam("status") Integer status, @QueryParam("page") @DefaultValue("1") Integer page,
                          @QueryParam("length") @DefaultValue("10") Integer length) throws BuzException;

    /**
     * 待取材确认脱水篮
     *
     * @return
     * @throws BuzException
     */
    @GET
    @Path("/grossing")
    ResponseVO grossingGetBaskets() throws BuzException;
}
