package com.lifetech.dhap.pathcloud.tracking.api;

import com.lifetech.dhap.pathcloud.common.exception.BuzException;
import com.lifetech.dhap.pathcloud.common.response.ResponseVO;
import com.lifetech.dhap.pathcloud.tracking.api.vo.GrossingSaveVO;

import javax.ws.rs.*;

/**
 * Created by LiuMei on 2017-09-06.
 */
@Produces({"application/json"})
@Path("/frozen")
public interface FrozenApi {

    /**
     * 冰冻取材或打印
     * @param id
     * @param vo
     * @return
     * @throws BuzException
     */
    @POST
    @Consumes("application/json")
    @Path("/{id}")
    ResponseVO addFrozenGrossing(@PathParam("id") Long id, GrossingSaveVO vo) throws BuzException;

    /**
     * 待冰冻取材列表
     * @param page
     * @param length
     * @param filter
     * @param status
     * @param reGrossing
     * @param departments
     * @param createTimeStart
     * @param createTimeEnd
     * @param order
     * @param sort
     * @return
     * @throws BuzException
     */
    @GET
    @Path("/prepare")
    ResponseVO getGrossingPathologies(@QueryParam("page") @DefaultValue("1") Integer page,
                                      @QueryParam("length") @DefaultValue("10") Integer length,
                                      @QueryParam("filter") String filter,
                                      @QueryParam("status") Integer status,
                                      @QueryParam("reGrossing") Boolean reGrossing,
                                      @QueryParam("departments") Integer departments,
                                      @QueryParam("createTimeStart") Long createTimeStart,
                                      @QueryParam("createTimeEnd") Long createTimeEnd,
                                      @QueryParam("order") Integer order,
                                      @QueryParam("sort") String sort) throws BuzException;

    /**
     * 根据冰冻号获取冰冻玻片信息
     * @param number
     * @return
     */
    @GET
    @Path("/{number}")
    ResponseVO getSlidesInfo(@PathParam("number") String number);


    /**
     * 冰冻取材信息查询
     *
     * @return
     * @throws BuzException
     */
    @GET
    ResponseVO getFrozenGrossing(@QueryParam("page") @DefaultValue("1") Integer page,
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


}
