package com.lifetech.dhap.pathcloud.tracking.api;

import com.lifetech.dhap.pathcloud.common.exception.BuzException;
import com.lifetech.dhap.pathcloud.common.response.ResponseVO;
import com.lifetech.dhap.pathcloud.tracking.api.vo.DyeConfirmVO;

import javax.ws.rs.*;

/**
 * Created by LiuMei on 2017-08-31.
 */

@Produces({"application/json"})
@Path("/sealing")
public interface SealingApi {

    /**
     * 根据病理号或蜡块号或玻片号查询待封片玻片信息
     * @param serialNumber
     * @return
     * @throws BuzException
     */
    @GET
    @Path("/{serialNumber}")
    ResponseVO getSlideBySerialNumber(@PathParam("serialNumber") String serialNumber) throws BuzException;

    /**
     * 完成封片
     * @param dyeConfirmVO
     * @return
     * @throws BuzException
     */
    @POST
    @Path("/confirm")
    ResponseVO mountingComplete(DyeConfirmVO dyeConfirmVO) throws BuzException;

    /**
     * 批量完成封片
     * @param dyeConfirmVO
     * @return
     * @throws BuzException
     */
    @POST
    @Path("/confirm/batch")
    ResponseVO batch(DyeConfirmVO dyeConfirmVO) throws BuzException;
}
