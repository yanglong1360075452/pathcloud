package com.lifetech.dhap.pathcloud.reagent.api;

import com.lifetech.dhap.pathcloud.common.exception.BuzException;
import com.lifetech.dhap.pathcloud.common.response.ResponseVO;
import com.lifetech.dhap.pathcloud.reagent.api.vo.ReagentVO;

import javax.ws.rs.*;

/**
 * Created by HP on 2017/9/26.
 */
@Produces({"application/json"})
@Path("/reagent")
public interface ReagentApi {

    /**
     * 新增试剂耗材
     *
     * @param
     * @return
     * @throws BuzException
     */
    @POST
    @Consumes({"application/json"})
    ResponseVO createReagent(ReagentVO reagentVO) throws BuzException;

    /**
     * 更新试剂耗材
     *
     * @param reagentVO
     * @return
     * @throws BuzException
     */
    @PUT
    @Consumes({"application/json"})
    ResponseVO updateReagent(ReagentVO reagentVO) throws BuzException;

    /**
     * 根据名称查询试剂耗材
     * @param name
     * @param type
     * @return
     * @throws BuzException
     */
    @GET
    @Path("/name")
    ResponseVO getReagentByTypeAndName(@QueryParam("name") String name,@QueryParam("type") Integer type) throws BuzException;

    /**
     * 获取试剂耗材列表
     * @param page
     * @param length
     * @param filter
     * @param category
     * @return
     * @throws BuzException
     */
    @GET
    ResponseVO getReagentList(@QueryParam("page") @DefaultValue("1") Integer page,
                              @QueryParam("length") @DefaultValue("10") Integer length,
                              @QueryParam("filter") String filter,
                              @QueryParam("category") Integer category) throws BuzException;
}
