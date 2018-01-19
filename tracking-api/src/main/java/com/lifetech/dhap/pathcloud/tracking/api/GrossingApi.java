package com.lifetech.dhap.pathcloud.tracking.api;

import com.lifetech.dhap.pathcloud.common.exception.BuzException;
import com.lifetech.dhap.pathcloud.common.response.ResponseVO;
import com.lifetech.dhap.pathcloud.tracking.api.vo.GrossingPrintRequestVO;
import com.lifetech.dhap.pathcloud.tracking.api.vo.GrossingSaveVO;

import javax.ws.rs.*;
import java.util.List;

/**
 * 取材接口
 *
 * @author eric.guo@ostengar.com
 * @version 1.0
 * @time 2016-12-01-15:13
 */
@Produces({"application/json"})
@Path("/grossing")
public interface GrossingApi {

    /**
     * 取材记录
     * 打印或保存
     *
     * @param vo
     * @return
     * @throws BuzException
     */
    @POST
    @Consumes("application/json")
    @Path("/{id}")
    ResponseVO addGrossing(@PathParam("id") Long id, GrossingSaveVO vo) throws BuzException;

    /**
     * 取材信息查询
     *
     * @return
     * @throws BuzException
     */
    @GET
    ResponseVO getGrossing(@QueryParam("page") @DefaultValue("1") Integer page,
                           @QueryParam("length") @DefaultValue("10") Integer length,
                           @QueryParam("order") Integer order,
                           @QueryParam("sort") String sort,
                           @QueryParam("status") Integer status,
                           @QueryParam("filter") String filter,
                           @QueryParam("departments") Integer departments, //送检科室
                           @QueryParam("operator") Long operator, //取材医生
                           @QueryParam("secOperator") Long secOperator, //取材记录员
                           @QueryParam("timeStart") Long timeStart,
                           @QueryParam("timeEnd") Long timeEnd
    ) throws BuzException;


    /**
     * 待取材确认信息查询
     *
     * @return
     * @throws BuzException
     */
    @GET
    @Path("/forConfirm")
    ResponseVO getGrossingForConfirm(@QueryParam("page") @DefaultValue("1") Integer page,
                                     @QueryParam("length") @DefaultValue("10") Integer length,
                                     @QueryParam("order") Integer order,
                                     @QueryParam("sort") String sort,
                                     @QueryParam("secOperator") Long secOperator, //取材记录员
                                     @QueryParam("basketNumbers") String basketNumbers
    ) throws BuzException;

    /**
     * 取材确认
     *
     * @param basketNumber
     * @return
     * @throws BuzException
     */
    @POST
    @Consumes("application/json")
    @Path("/confirm")
    ResponseVO confirmGrossing(String basketNumber) throws BuzException;

    /**
     * 脱水确认列表查询
     *
     * @param page
     * @param length
     * @return
     * @throws BuzException
     */
    @GET
    @Path("/forDehydrate")
    ResponseVO getGrossingForDehydrate(@QueryParam("page") @DefaultValue("1") Integer page,
                                       @QueryParam("length") @DefaultValue("10") Integer length,
                                       @QueryParam("basketNumbers") String basketNumbers,
                                       @QueryParam("instrumentId") long instrumentId) throws BuzException;


    /**
     * 病理取材或冰冻取材的文件数据，图片和录像数据
     *
     * @param id
     * @param operation 1-取材 35-冰冻取材
     * @return
     * @throws BuzException
     */
    @GET
    @Path("/{id}/file/{operation}")
    ResponseVO getGrossingFile(@PathParam("id") Long id, @PathParam("operation") Integer operation,
                               @QueryParam("tag") String tag) throws BuzException;

    /**
     * 取材撤销
     *
     * @param id
     * @param note
     * @return
     * @throws BuzException
     */
    @PUT
    @Path("/{id}")
    ResponseVO grossingCancel(@PathParam("id") Long id, String note) throws BuzException;

    /**
     * 查询取材撤销原因
     *
     * @param id
     * @return
     * @throws BuzException
     */
    @GET
    @Path("/{id}")
    ResponseVO grossingCancelCause(@PathParam("id") Long id) throws BuzException;

    /**
     * 提前打印包埋盒
     *
     * @param grossingPrintVOS
     * @return
     * @throws BuzException
     */
    @POST
    @Path("/before/print")
    ResponseVO grossingBeforePrint(GrossingPrintRequestVO grossingPrintVOS) throws BuzException;

    /**
     * 重复打印包埋盒/玻片
     *
     * @param operate
     * @param ids
     * @return
     */
    @POST
    @Path("/print/{operate}")
    ResponseVO grossingPrint(@PathParam("operate") int operate, List<Long> ids);

}

