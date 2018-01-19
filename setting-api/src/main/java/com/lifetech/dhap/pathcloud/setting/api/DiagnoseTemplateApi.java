package com.lifetech.dhap.pathcloud.setting.api;

import com.lifetech.dhap.pathcloud.common.exception.BuzException;
import com.lifetech.dhap.pathcloud.common.response.ResponseVO;
import com.lifetech.dhap.pathcloud.setting.api.vo.TemplateVO;

import javax.ws.rs.*;

/**
 * Created by HP on 2017/1/9.
 */
@Produces({"application/json"})
@Path("/diagnoseTemplate")
public interface DiagnoseTemplateApi {

    /**
     * 创建模板
     * @param templateVO
     * @return
     * @throws BuzException
     */
    @POST
    @Consumes({"application/json"})
    ResponseVO createTemplate(TemplateVO templateVO)throws BuzException;


    /**
     * 模板搜索
     * @param parent
     * @return
     * @throws BuzException
     */
    @GET
    @Consumes({"application/json"})
    ResponseVO getTemplates(@QueryParam("parent") Integer parent,@QueryParam("position")String position)throws BuzException;


    /**
     * 重命名模板
     * @param templateVO
     * @return
     * @throws BuzException
     */
    @PUT
    @Consumes({"application/json"})
    @Path("/rename/{id}")
    ResponseVO renameTemplate(@PathParam("id")Integer id,TemplateVO templateVO)throws BuzException;

    /**
     * 删除模板
     * @param id
     * @return
     * @throws BuzException
     */
    @DELETE
    @Consumes({"application/json"})
    @Path("/{id}")
    ResponseVO deleteTemplate(@PathParam("id")Integer id)throws BuzException;


    /**
     * 编辑模板
     * @param templateVO
     * @return
     * @throws BuzException
     */
    @PUT
    @Consumes({"application/json"})
    @Path("/edit/{id}")
    ResponseVO editTemplate(@PathParam("id")Integer id,TemplateVO templateVO)throws BuzException;


}
