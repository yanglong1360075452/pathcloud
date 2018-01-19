package com.lifetech.dhap.pathcloud.dehydrate.api;

import com.lifetech.dhap.pathcloud.common.exception.BuzException;
import com.lifetech.dhap.pathcloud.common.response.ResponseVO;
import com.lifetech.dhap.pathcloud.dehydrate.api.vo.InstrumentVO;

import javax.ws.rs.*;

/**
 *
 * Created by LiuMei on 2017-08-31.
 */
@Produces({"application/json"})
@Path("/instrument")
public interface InstrumentApi {

    /**
     * 添加设备
     *
     * @param instrumentVO
     * @return
     * @throws BuzException
     */
    @POST
    @Consumes("application/json")
    ResponseVO addInstrument(InstrumentVO instrumentVO) throws BuzException;


    /**
     * 编辑设备
     *
     * @param instrumentVO
     * @return
     * @throws BuzException
     */
    @PUT
    @Consumes("application/json")
    ResponseVO editInstrument(InstrumentVO instrumentVO) throws BuzException;

    @DELETE
    @Path("/{id}")
    ResponseVO delete(@PathParam("id") long id) throws BuzException;

    @GET
    ResponseVO getList(@QueryParam("page") @DefaultValue("1") Integer page,
                           @QueryParam("length") @DefaultValue("10") Integer length,
                           @QueryParam("status") Integer status,
                           @QueryParam("type") Integer type
    ) throws BuzException;
}
