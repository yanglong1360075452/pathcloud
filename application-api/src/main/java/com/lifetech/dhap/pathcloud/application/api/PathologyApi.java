package com.lifetech.dhap.pathcloud.application.api;

import com.lifetech.dhap.pathcloud.application.api.vo.ConsultRegisterVO;
import com.lifetech.dhap.pathcloud.application.api.vo.PathologyVO;
import com.lifetech.dhap.pathcloud.common.exception.BuzException;
import com.lifetech.dhap.pathcloud.common.response.ResponseVO;

import javax.ws.rs.*;

/**
 * Created by LiuMei on 2016-11-23.
 *
 * 登记
 */
@Produces({"application/json"})
@Path("/pathology")
public interface PathologyApi {

    /**
     * 登记
     * 创建病理信息
     *
     * @param pathologyVO
     * @return
     * @throws BuzException
     */
    @POST
    @Consumes("application/json")
    ResponseVO createPathology(PathologyVO pathologyVO) throws BuzException;

    /**
     * 会诊登记
     * @param registerVO
     * @return
     */
    @POST
    @Consumes("application/json")
    @Path("/consult")
    ResponseVO consultRegister(ConsultRegisterVO registerVO) throws BuzException;

    /**
     * 获取当前用户登记的上一个病理号申请信息
     * @return
     * @throws BuzException
     */
    @GET
    @Path("/pre/{createTime}")
    ResponseVO getPrePathology(@PathParam("createTime") Long createTime) throws BuzException;

    /**
     *  撤销登记
     * @param id
     * @return
     * @throws BuzException
     */
    @POST
    @Consumes("application/json")
    @Path("/cancel/{id}")
    ResponseVO cancelPathology(@PathParam("id") long id) throws BuzException;

    /**
     * 病理登记信息查询
     *
     * @param page
     * @param length
     * @param filter
     * @param status
     * @param createTimeStart
     * @param createTimeEnd
     * @return
     * @throws BuzException
     */
    @GET
    @Path("/sample")
    ResponseVO getPathologySamples(@QueryParam("page") @DefaultValue("1") Integer page,
                                   @QueryParam("length") @DefaultValue("10") Integer length,
                                   @QueryParam("filter") String filter,
                                   @QueryParam("status") Integer status,
                                   @QueryParam("specialType") Integer specialType,
                                   @QueryParam("createTimeStart") Long createTimeStart,
                                   @QueryParam("createTimeEnd") Long createTimeEnd,
                                   @QueryParam("order") Integer order,
                                   @QueryParam("sort") String sort
    ) throws BuzException;

    /**
     * 病理取材搜索
     * @param page
     * @param length
     * @param filter
     * @param status
     * @param reGrossing
     * @param inspectCategory
     * @param departments
     * @param createTimeStart
     * @param createTimeEnd
     * @param order
     * @param sort
     * @return
     * @throws BuzException
     */
    @GET
    ResponseVO getGrossingPathologies(@QueryParam("page") @DefaultValue("1") Integer page,
                              @QueryParam("length") @DefaultValue("10") Integer length,
                              @QueryParam("filter") String filter,
                              @QueryParam("status") Integer status,
                              @QueryParam("reGrossing") Boolean reGrossing,
                              @QueryParam("inspectCategory") Integer inspectCategory,
                              @QueryParam("departments") Integer departments,
                              @QueryParam("createTimeStart") Long createTimeStart,
                              @QueryParam("createTimeEnd") Long createTimeEnd,
                              @QueryParam("order") Integer order,
                              @QueryParam("sort") String sort) throws BuzException;

    /**
     * 取材工作台
     * 获取病理相关信息
     * @param id
     * @return
     */
    @GET
    @Path("/{id}")
    ResponseVO getPathInfo(@PathParam("id") long id);

}
