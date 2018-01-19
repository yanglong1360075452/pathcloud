package com.lifetech.dhap.pathcloud.tracking.api;

import com.lifetech.dhap.pathcloud.common.exception.BuzException;
import com.lifetech.dhap.pathcloud.common.response.ResponseVO;
import com.lifetech.dhap.pathcloud.tracking.api.vo.BlockScoreVO;
import com.lifetech.dhap.pathcloud.tracking.api.vo.CollectVO;
import com.lifetech.dhap.pathcloud.tracking.api.vo.DiagnoseResultVO;
import com.lifetech.dhap.pathcloud.tracking.api.vo.OutConsultVO;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.text.ParseException;
import java.util.List;

/**
 * Created by LiuMei on 2017-01-05.
 */

@Produces({"application/json"})
@Path("/diagnose")
public interface DiagnoseApi {

    /**
     * 待诊断列表
     * @param page
     * @param length
     * @param order
     * @param sort
     * @param delay
     * @param filter
     * @param departments
     * @param specialType
     * @param delayTime 即将延期日期
     * @param createTimeStart
     * @param createTimeEnd
     * @return
     * @throws BuzException
     */
    @GET
    ResponseVO getList(@QueryParam("page") @DefaultValue("1") Integer page,
                       @QueryParam("length") @DefaultValue("10") Integer length,
                       @QueryParam("order") Integer order,
                       @QueryParam("sort") String sort,
                       @QueryParam("delay") Boolean delay,
                       @QueryParam("filter") String filter,
                       @QueryParam("departments") Integer departments,
                       @QueryParam("specialType") Integer specialType,
                       @QueryParam("delayTime") Long delayTime,
                       @QueryParam("createTimeStart") Long createTimeStart,
                       @QueryParam("createTimeEnd") Long createTimeEnd) throws BuzException;

    /**
     * 根据病理号或玻片号获取信息
     * @param serialNumber
     * @return
     * @throws BuzException
     */
    @GET
    @Path("/{serialNumber}")
    ResponseVO getInfo(@PathParam("serialNumber") String serialNumber,@QueryParam("special") Boolean special) throws BuzException, ParseException;

    /**
     * 获取病理显微图像
     * @param pathId
     * @return
     * @throws BuzException
     */
    @GET
    @Path("/file/{pathId}")
    ResponseVO getMicroFiles(@PathParam("pathId") long pathId,@QueryParam("specialId") Long specialId) throws BuzException;

    /**
     * 玻片评分
     * @param slideId
     * @param scoreVO
     * @return
     * @throws BuzException
     */
    @PUT
    @Path("/score/{slideId}")
    ResponseVO score(@PathParam("slideId") long slideId, BlockScoreVO scoreVO) throws BuzException;

    /**
     * 申请重补取
     * @param pathologyId
     * @return
     * @throws BuzException
     */
    @POST
    @Path("/regrossing/{pathologyId}")
    ResponseVO blockReGrossing(@PathParam("pathologyId") Long pathologyId, String note) throws BuzException;

    /**
     * 申请深切
     * @param blockId
     * @param note
     * @return
     * @throws BuzException
     */
    @POST
    @Path("/deep/{blockId}")
    ResponseVO deep(@PathParam("blockId") Long blockId, String note) throws BuzException;


    /**
     * 提交上级
     * 病理诊断操作
     * @param pathId
     * @param diagnoseResultVO
     * @return
     * @throws BuzException
     */
    @POST
    @Path("/confirm/{pathId}")
    ResponseVO diagnose(@PathParam("pathId") long pathId, DiagnoseResultVO diagnoseResultVO) throws BuzException;

    /**
     * 根据病理ID获取待归档蜡块信息
     * @param pathId
     * @return
     * @throws BuzException
     */
    @GET
    @Path("/{pathId}/block")
    ResponseVO getArchiveBlockInfo(@PathParam("pathId") long pathId) throws BuzException;

    /**
     * 根据病理ID获取申请染色的蜡块信息
     * @param pathId
     * @return
     * @throws BuzException
     */
    @GET
    @Path("/{pathId}/dye")
    ResponseVO getDyeBlockInfo(@PathParam("pathId") long pathId) throws BuzException;

    /**
     * 诊断信息查询
     * @return
     * @throws BuzException
     */
    @GET
    @Path("/query")
    ResponseVO getInfo(@QueryParam("page") @DefaultValue("1") Integer page,
                       @QueryParam("length") @DefaultValue("10") Integer length,
                       @QueryParam("order") Integer order,
                       @QueryParam("sort") String sort,
                       @QueryParam("status") Integer status,
                       @QueryParam("filter") String filter,
                       @QueryParam("outConsult") Boolean outConsult,
                       @QueryParam("departments") Integer departments, //送检科室
                       @QueryParam("diagnoseDoctor") Long diagnoseDoctor, //诊断医生
                       @QueryParam("reportDoctor") Long reportDoctor, //报告医生
                       @QueryParam("specialType") Integer specialType,
                       @QueryParam("createTimeStart") Long createTimeStart,
                       @QueryParam("createTimeEnd") Long createTimeEnd) throws BuzException;

    /**
     * 获取病理历史诊断
     * @param pathId
     * @return
     * @throws BuzException
     */
    @GET
    @Path("/history/{pathId}")
    ResponseVO getDiagnoseHistory(@PathParam("pathId") long pathId) throws BuzException;


    /**
     * 获取报告延期数量
     * @param delayTime 预计报告时间
     * @return
     * @throws BuzException
     */
    @GET
    @Path("/deadline")
    ResponseVO countDeadLine(@QueryParam("delayTime") Long delayTime) throws BuzException;

    /**
     * 获取发报告信息
     * @param pathId
     * @param specialApplyId
     * @return
     * @throws BuzException
     */
    @GET
    @Path("/report/{pathId}")
    ResponseVO getReportInfo(@PathParam("pathId") long pathId,@QueryParam("specialApplyId") Long specialApplyId) throws BuzException;

    /**
     * 发报告
     * @param pathId
     * @return
     * @throws BuzException
     */
    @POST
    @Path("/report/{pathId}")
    ResponseVO report(@PathParam("pathId") long pathId, DiagnoseResultVO diagnoseResultVO) throws BuzException;

    /**
     * 保存诊断结果
     * @param diagnoseResultVO
     * @return
     * @throws BuzException
     */
    @POST
    @Path("/save/{pathId}")
    ResponseVO save(@PathParam("pathId") long pathId,DiagnoseResultVO diagnoseResultVO) throws BuzException;

    /**
     * 获取特检历史结果
     * @param pathNo
     * @return
     */
    @GET
    @Path("/special/history/{pathNo}")
    ResponseVO getSpecialHistory(@PathParam("pathNo") String pathNo);

    /**
     * 添加病理收藏
     * @param
     * @param
     * @return
     */
    @POST
    @Path("/collect")
    ResponseVO createCollect(CollectVO collectVO)throws BuzException;


    /**
     * 查询病理收藏
     * @param
     * @param
     * @return
     */
    @GET
    @Path("/collect")
    ResponseVO getCollects(@QueryParam("page") @DefaultValue("1") Integer page,
                           @QueryParam("length") @DefaultValue("10") Integer length,
                           @QueryParam("filter") String filter,
                           @QueryParam("label") String label,
                           @QueryParam("collect") Integer collect,
                           @QueryParam("permission") Integer permission,
                           @QueryParam("startTime") Long startTime,
                           @QueryParam("endTime") Long endTime)throws BuzException;


    /**
     * 删除病理收藏
     * @param
     * @param
     * @return
     */
    @DELETE
    @Consumes({"application/json"})
    @Path("/collect")
    ResponseVO deleteCollect(List<Long> ids)throws BuzException;



    /**
     * 病理收藏导出
     * @param
     * @param
     * @return
     */
    @GET
    @Path("/collect/export")
    Response collectExport(
                             @QueryParam("filter") String filter,
                             @QueryParam("label") String label,
                             @QueryParam("collect") Integer collect,
                             @QueryParam("permission") Integer permission,
                             @QueryParam("startTime") Long startTime,
                             @QueryParam("endTime") Long endTime) throws Exception;


    /**
     * 申请外院会诊
     * @param id
     * @param outConsultVO
     * @return
     * @throws BuzException
     */
    @POST
    @Path("/out/{id}")
    ResponseVO outConsult(@PathParam("id") long id, OutConsultVO outConsultVO) throws BuzException;
}
