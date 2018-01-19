package com.lifetech.dhap.pathcloud.dehydrate.api;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import com.lifetech.dhap.pathcloud.common.exception.BuzException;
import com.lifetech.dhap.pathcloud.common.response.ResponseVO;

@Produces({"application/json"})
@Path("/dehydrator/local-msg")
public interface DehydratorMsgReceiverApi {

	@PUT
	@Path("/heartbreak/{sn}")
	ResponseVO heartBreak(@PathParam("sn") String sn, @FormParam("eventTimestamp") Long ts) throws BuzException;
	
	@POST
	@Path("/eventcode/{sn}")
	ResponseVO newEventByCode(@PathParam("sn") String sn, @FormParam("code") String code, @FormParam("eventTimestamp") Long ts) throws BuzException;
	
	@POST
	@Path("/eventmsg/{sn}")
	ResponseVO newEventByMsg(@PathParam("sn") String sn, @FormParam("msg") String msg, @FormParam("level") Integer level, @FormParam("eventTimestamp") Long ts) throws BuzException;
}
