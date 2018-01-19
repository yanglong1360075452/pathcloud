package com.lifetech.dhap.pathcloud.setting.api;

import com.lifetech.dhap.pathcloud.common.exception.BuzException;
import com.lifetech.dhap.pathcloud.common.response.ResponseVO;
import com.lifetech.dhap.pathcloud.setting.api.vo.TemplateVO;

import javax.ws.rs.*;

/**
 * Created by HP on 2016/12/8.
 */
@Produces({"application/json"})
@Path("/template")
public interface TemplateApi {

    /**
     * 模板搜索
     *
     * @param parent
     * @return
     * @throws BuzException
     */
    @GET
    @Consumes({"application/json"})
    ResponseVO getTemplates(@QueryParam("parent") Integer parent, @QueryParam("position") String position,
                            @QueryParam("category") Integer category) throws BuzException;

    /**
     * 创建模板
     *
     * @param templateVO
     * @return
     * @throws BuzException
     */
    @POST
    @Consumes({"application/json"})
    ResponseVO createTemplate(TemplateVO templateVO) throws BuzException;


    /**
     * 重命名模板
     *
     * @param templateVO
     * @return
     * @throws BuzException
     */
    @PUT
    @Consumes({"application/json"})
    @Path("/rename/{id}")
    ResponseVO renameTemplate(@PathParam("id") Integer id, TemplateVO templateVO) throws BuzException;


    /**
     * 编辑模板
     *
     * @param templateVO
     * @return
     * @throws BuzException
     */
    @PUT
    @Consumes({"application/json"})
    @Path("/edit/{id}")
    ResponseVO editTemplate(@PathParam("id") Integer id, TemplateVO templateVO) throws BuzException;


    /**
     * 删除模板
     *
     * @param id
     * @return
     * @throws BuzException
     */
    @DELETE
    @Consumes({"application/json"})
    @Path("/{id}")
    ResponseVO deleteTemplate(@PathParam("id") Integer id) throws BuzException;

    /**
     * 设置常用模板
     *
     * @param id
     * @return
     * @throws BuzException
     */
    @PUT
    @Path("/setting/{id}")
    ResponseVO templateSetting(@PathParam("id") Integer id, Integer position) throws BuzException;

    /**
     * 最新使用模板
     *
     * @return
     * @throws BuzException
     */
    @GET
    @Path("/used")
    ResponseVO used(@QueryParam("position") Integer position) throws BuzException;
}
