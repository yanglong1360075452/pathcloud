package com.lifetech.dhap.pathcloud.notification.api;

import com.lifetech.dhap.pathcloud.common.exception.BuzException;
import com.lifetech.dhap.pathcloud.common.response.ResponseVO;
import com.lifetech.dhap.pathcloud.notification.api.vo.NotificationDeal;

import javax.ws.rs.*;

/**
 * @author eric.guo@ostengar.com
 * @version 1.0
 * @time 2017-01-09-10:43
 */
@Produces({"application/json"})
@Path("/notification")
public interface NotificationApi {

    /**
     * 系统消息
     * @param page
     * @param length
     * @param order
     * @param sort
     * @param handle 是否已处理（true已处理，false未处理）
     * @param readFlag 是否已读
     * @param filter
     * @return
     * @throws BuzException
     */
    @GET
    ResponseVO getNotification(@QueryParam("page") @DefaultValue("1") Integer page,
                           @QueryParam("length") @DefaultValue("10") Integer length,
                           @QueryParam("order") Integer order,
                           @QueryParam("sort") String sort,
                           @QueryParam("handle") Boolean handle,
                           @QueryParam("readFlag") Boolean readFlag,
                           @QueryParam("filter") String filter
    ) throws BuzException;

    /**
     * 消息详细
     * @param id
     * @return
     * @throws BuzException
     */
    @GET
    @Path("/{id}")
    ResponseVO notificationDetail(@PathParam("id") Long id) throws BuzException;


    /**
     * 消息数量
     * @return
     * @throws BuzException
     */
    @GET
    @Path("/sum")
    ResponseVO notificationSum() throws BuzException;


    /**
     * 消息处理
     *
     * @return
     * @throws BuzException
     */
    @PUT
    ResponseVO notificationDeal(NotificationDeal notification) throws BuzException;
}
