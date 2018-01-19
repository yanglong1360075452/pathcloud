package com.lifetech.dhap.pathcloud.application.api;

import com.lifetech.dhap.pathcloud.application.api.vo.ApplicationVO;
import com.lifetech.dhap.pathcloud.application.api.vo.ConsultApplicationVO;
import com.lifetech.dhap.pathcloud.application.api.vo.RejectVO;
import com.lifetech.dhap.pathcloud.application.api.vo.ResearchVO;
import com.lifetech.dhap.pathcloud.common.exception.BuzException;
import com.lifetech.dhap.pathcloud.common.response.ResponseVO;

import javax.ws.rs.*;
import java.text.ParseException;

/**
 * Created by LuoMo on 2016-11-22.
 *
 * 病理申请
 */
@Produces({"application/json"})
@Path("/application")
public interface ApplicationApi {

    /**
     * 创建临床申请
     *
     * @param applicationVO
     * @return
     * @throws BuzException
     */
    @POST
    @Consumes("application/json")
    ResponseVO createApplication(ApplicationVO applicationVO) throws BuzException, IllegalAccessException;

    /**
     * 创建科研申请
     * @param researchVO
     * @return
     * @throws BuzException
     */
    @POST
    @Consumes("application/json")
    @Path("/research")
    ResponseVO createResearch(ResearchVO researchVO) throws BuzException, ParseException;

    /**
     * 创建会诊申请
     * @param consultApplicationVO
     * @return
     * @throws BuzException
     * @throws ParseException
     */
    @POST
    @Consumes("application/json")
    @Path("/consult")
    ResponseVO createConsult(ConsultApplicationVO consultApplicationVO) throws BuzException;

    /**
     * 申请查看
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
    ResponseVO getApplications(@QueryParam("page") @DefaultValue("1") Integer page,
                               @QueryParam("length") @DefaultValue("10") Integer length,
                               @QueryParam("filter") String filter,
                               @QueryParam("status") Integer status,
                               @QueryParam("createTimeStart") Long createTimeStart,
                               @QueryParam("createTimeEnd") Long createTimeEnd
                               ) throws BuzException;

    /**
     * 根据serialNumber(病理申请号)查询
     * @param serialNumber
     * @return
     * @throws BuzException
     */
    @GET
    @Path("/{serialNumber}")
    ResponseVO getApplicationBySerialNumber(@PathParam("serialNumber") String serialNumber) throws BuzException;

    /**
     * 根据病理号获取申请单信息
     * @param pathologyNo
     * @return
     * @throws BuzException
     */
    @GET
    @Path("/pathology/{pathologyNo}")
    ResponseVO getApplicationByPathologyNo(@PathParam("pathologyNo") String pathologyNo) throws BuzException;

    /**
     * 撤销申请
     *
     * @param id
     * @return
     * @throws BuzException
     */
    @PUT
    @Path("/cancel/{id}")
    ResponseVO cancelApplication(@PathParam("id")long id)throws BuzException;

    /**
     * 撤销冰冻预约
     * @param id
     * @return
     * @throws BuzException
     */
    @POST
    @Path("/cancel/freeze/{id}")
    ResponseVO cancelFreezeApplication(@PathParam("id")long id)throws BuzException;

    /**
     * 拒收申请
     * @param id
     * @param rejectVO
     * @return
     * @throws BuzException
     */
    @PUT
    @Path("/reject/{id}")
    ResponseVO rejectApplication(@PathParam("id")Long id,RejectVO rejectVO)throws BuzException;


    /**
     * 获取已预约数据
     * @param
     * @return
     * @throws BuzException
     */
    @GET
    @Path("/booking")
    ResponseVO getApplicationBooking(@QueryParam(value = "timeStart") Long timeStart,
                                     @QueryParam(value = "timeEnd") Long timeEnd
                                     ) throws BuzException;
}