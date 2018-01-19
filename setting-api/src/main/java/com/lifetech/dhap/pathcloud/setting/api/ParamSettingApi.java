package com.lifetech.dhap.pathcloud.setting.api;

import com.lifetech.dhap.pathcloud.common.exception.BuzException;
import com.lifetech.dhap.pathcloud.common.response.ResponseVO;
import com.lifetech.dhap.pathcloud.setting.api.vo.DepartmentSettingVO;
import com.lifetech.dhap.pathcloud.setting.api.vo.InspectHospitalVO;
import com.lifetech.dhap.pathcloud.setting.api.vo.ParamSettingVO;
import com.lifetech.dhap.pathcloud.setting.api.vo.QualityScoreVO;

import javax.ws.rs.*;

/**
 * @author eric.guo@ostengar.com
 * @version 1.0
 * @time 2016-12-07-13:32
 */
@Produces({"application/json"})
@Path("/paramSetting")
public interface ParamSettingApi {

    @GET
    @Path("/departments")
    ResponseVO getDepartments(@QueryParam("filter") String filter) throws BuzException;

    @GET
    @Path("/{param}")
    ResponseVO paramSetting(@PathParam("param") String param) throws BuzException;


    @POST
    @Consumes({"application/json"})
    ResponseVO addParam(ParamSettingVO paramSettingVO) throws BuzException;

    @DELETE
    @Path("/{param}/{code}")
    ResponseVO deleteParam(@PathParam("param") String param, @PathParam("code") Integer code) throws BuzException;

    @PUT
    @Path("/{param}/{code}")
    ResponseVO updateParam(@PathParam("param") String param, @PathParam("code") Integer code);

    @POST
    @Consumes({"application/json"})
    @Path("/qualityScore")
    ResponseVO addQualityScore(QualityScoreVO qualityScoreVO) throws BuzException;

    @PUT
    @Path("/qualityScore")
    ResponseVO updateQualityScore(QualityScoreVO qualityScoreVO) throws BuzException;

    /**
     * 添加科室类别
     */
    @POST
    @Consumes({"application/json"})
    @Path("/departmentSetting")
    ResponseVO addDepartment(DepartmentSettingVO dsv) throws BuzException;

    /**
     * 科室查询
     */
    @GET
    @Path("/dc")
    ResponseVO getDepartmentsByCategory() throws BuzException;


    /**
     * 删除科室类别
     */
    @DELETE
    @Path("/department/{id}")
    ResponseVO deleteDepartmentsCategory(@PathParam("id") Integer id) throws BuzException;


    /**
     * 科室类别重命名
     */
    @PUT
    @Path("/rename")
    ResponseVO renameCategory(DepartmentSettingVO dsv) throws BuzException;


    /**
     * 编辑科室名字
     */
    @PUT
    @Path("/edit")
    ResponseVO editDepartmentName(DepartmentSettingVO dsv) throws BuzException;


    /**
     * 更改特染类别
     */
    @PUT
    @Path("/dyeType")
    ResponseVO updateDyeType(ParamSettingVO paramSettingVO) throws BuzException;


    /**
     * 添加送检医院
     */
    @POST
    @Consumes({"application/json"})
    @Path("/inspectHospital")
    ResponseVO addInspectHospital(InspectHospitalVO inspectHospitalVO) throws BuzException;


    /**
     * 查询送检医院
     */
    @GET
    @Path("/inspectHospital")
    ResponseVO getInspectHospitals() throws BuzException;


    /**
     * 删除送检医院
     */
    @DELETE
    @Path("/inspectHospital/{code}")
    ResponseVO deleteInspectHospitals(@PathParam("code") Integer code) throws BuzException;

}
