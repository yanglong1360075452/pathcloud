package com.lifetech.dhap.pathcloud.setting.api;

import com.lifetech.dhap.pathcloud.common.exception.BuzException;
import com.lifetech.dhap.pathcloud.common.response.ResponseVO;
import com.lifetech.dhap.pathcloud.setting.api.vo.InspectCategoryVO;
import com.lifetech.dhap.pathcloud.setting.api.vo.ParamSettingVO;

import javax.ws.rs.*;

/**
 * Created by LiuMei on 2016-12-27.
 */
@Produces({"application/json"})
@Path("/systemSetting")
public interface SystemSettingApi {

    /**
     *常规染色
     * 不追踪某些项目
     * @param uncheckedList
     * @return
     * @throws BuzException
     */
    @PUT
    @Path("/trackingList")
    ResponseVO updateTrackingList(String uncheckedList) throws BuzException;

    /**
     *冰冻切片
     * 不追踪某些项目
     * @param uncheckedList
     * @return
     * @throws BuzException
     */
    @PUT
    @Path("/freezeTrackingList")
    ResponseVO updateFreezeTrackingList(String uncheckedList) throws BuzException;

    /**
     * 添加检查类别
     * @return
     * @throws BuzException
     */
    @POST
    @Path("/inspectCategory")
    ResponseVO addInspectCategory(InspectCategoryVO inspectCategoryVO) throws BuzException;

    @DELETE
    @Path("/inspectCategory/{code}")
    ResponseVO deleteInspectCategory(@PathParam("code") int code) throws BuzException;

    @DELETE
    @Path("/sampleCategory/{code}")
    ResponseVO deleteSampleCategory(@PathParam("code") int code) throws BuzException;

    @GET
    @Path("/{param}")
    ResponseVO getParam(@PathParam("param") String param) throws BuzException;

    /**
     * 添加正常参数
     * @param param
     * @param name
     * @return
     * @throws BuzException
     */
    @POST
    @Path("/{param}")
    ResponseVO addNormalParam(@PathParam("param") String param, String name) throws BuzException;

    /**
     * 修改正常参数
     * @param param
     * @param code
     * @return
     */
    @PUT
    @Path("/{param}/{code}")
    ResponseVO updateNormalParam(@PathParam("param") String param,@PathParam("code") String code);

    /**
     * 修改默认信息查询时间范围
     * @param code
     * @return
     */
    @PUT
    @Path("/queryTimeRange/{code}")
    ResponseVO updateQueryTimeRange(@PathParam("code") int code);


    /**
     * 添加数据
     * @param
     * @return
     */
    @POST
    @Consumes({"application/json"})
    ResponseVO addParam(ParamSettingVO paramSettingVO) throws BuzException;


    /**
    * 更改特染类别
    *
    * */
    @PUT
    @Path("/specialDye")
    ResponseVO updateSpecialDye(ParamSettingVO paramSettingVO) throws BuzException;

    /**
    * 删除数据
    *
    * */
    @DELETE
    @Path("/{param}/{code}")
    ResponseVO deleteParam(@PathParam("param") String param,@PathParam("code") Integer code) throws BuzException;
}
