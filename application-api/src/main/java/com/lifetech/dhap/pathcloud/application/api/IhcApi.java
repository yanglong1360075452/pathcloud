package com.lifetech.dhap.pathcloud.application.api;

import com.lifetech.dhap.pathcloud.application.api.vo.IhcPrintVO;
import com.lifetech.dhap.pathcloud.common.exception.BuzException;
import com.lifetech.dhap.pathcloud.common.response.ResponseVO;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Created by LiuMei on 2017-04-06.
 *
 * 免疫组化
 */
@Produces({"application/json"})
@Path("/ihc")
public interface IhcApi {

    /**
     * 免疫组化多个执行
     * @param ihcBlockIds
     * @return
     * @throws BuzException
     */
    @Path("/confirm")
    @POST
    ResponseVO confirm(List<Long> ihcBlockIds) throws BuzException;

    /**
     * 延迟执行
     * @param ihcBlockIds
     * @return
     * @throws BuzException
     */
    @Path("/delay")
    @POST
    ResponseVO delay(List<Long> ihcBlockIds) throws BuzException;

    /**
     * 特检打印
     * @param ihcPrintVOS
     * @return
     * @throws BuzException
     */
    @POST
    @Path("/print")
    ResponseVO print(List<IhcPrintVO> ihcPrintVOS) throws BuzException;

    /**
     * 查询免疫组化的申请列表
     * @param page
     * @param length
     * @param filter
     * @param createTimeStart
     * @param createTimeEnd
     * @param status
     * @param blockStatus
     * @param dyeCategory
     * @param order
     * @param sort
     * @return
     * @throws BuzException
     */
    @GET
    ResponseVO getIHCs(@QueryParam("page") @DefaultValue("1") Integer page,
                       @QueryParam("length") @DefaultValue("10") Integer length,
                       @QueryParam("filter") String filter,
                       @QueryParam("createTimeStart") Long createTimeStart,
                       @QueryParam("createTimeEnd") Long createTimeEnd,
                       @QueryParam("status") Integer status,
                       @QueryParam("blockStatus") Integer blockStatus,
                       @QueryParam("dyeCategory") Integer dyeCategory,
                       @QueryParam("order") Integer order,
                       @QueryParam("sort") String sort
    ) throws BuzException;

    /**
     * 免疫组化列表导出
     * @param filter
     * @param createTimeStart
     * @param createTimeEnd
     * @param status
     * @param blockStatus
     * @param dyeCategory
     * @return
     * @throws Exception
     */
    @GET
    @Path("/export")
    Response getIHCsExport(
            @QueryParam("filter") String filter,
            @QueryParam("createTimeStart") Long createTimeStart,
            @QueryParam("createTimeEnd") Long createTimeEnd,
            @QueryParam("status") Integer status,
            @QueryParam("blockStatus") Integer blockStatus,
            @QueryParam("dyeCategory") Integer dyeCategory
    ) throws Exception;


    @GET
    @Path("/printIhcs")
    ResponseVO getPrintIhcs(@QueryParam("page") @DefaultValue("1") Integer page,
                            @QueryParam("length") @DefaultValue("10") Integer length,
                            @QueryParam("createTimeStart") Long createTimeStart,
                            @QueryParam("createTimeEnd") Long createTimeEnd,
                            @QueryParam(("filter"))String filter,
                            @QueryParam("specialDye")Integer specialDye,
                            @QueryParam("count")Integer count)throws Exception;

}
