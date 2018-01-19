package com.lifetech.dhap.pathcloud.dehydrate.api;

import com.lifetech.dhap.pathcloud.common.exception.BuzException;
import com.lifetech.dhap.pathcloud.common.response.ResponseVO;
import com.lifetech.dhap.pathcloud.dehydrate.api.vo.DehydrateVO;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.util.List;

/**
 * Created by LiuMei on 2016-12-13.
 *
 * 脱水
 */
@Produces({"application/json"})
@Path("/dehydrate")
public interface DehydrateApi {

    /**
     * 开始脱水
     * @param vo
     * @return
     * @throws BuzException
     */
    @POST
    @Path("/start")
    @Consumes("application/json")
    ResponseVO startDehydrate(DehydrateVO vo) throws BuzException;

    /**
     * 结束脱水
     * @param vo
     * @return
     * @throws BuzException
     */
    @POST
    @Path("/end")
    @Consumes("application/json")
    ResponseVO endDehydrate(DehydrateVO vo) throws BuzException;

    /**
     * 暂停脱水
     * @param instrumentIds
     * @return
     * @throws BuzException
     */
    @POST
    @Path("/pause")
    @Consumes("application/json")
    ResponseVO pauseDehydrate(List<Long> instrumentIds) throws BuzException;
}
