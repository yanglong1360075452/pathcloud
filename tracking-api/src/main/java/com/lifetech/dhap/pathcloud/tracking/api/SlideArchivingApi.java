package com.lifetech.dhap.pathcloud.tracking.api;

import com.lifetech.dhap.pathcloud.common.exception.BuzException;
import com.lifetech.dhap.pathcloud.common.response.ResponseVO;
import com.lifetech.dhap.pathcloud.tracking.api.vo.BlockBorrowVO;
import com.lifetech.dhap.pathcloud.tracking.api.vo.SlideArchiveConfirmVO;
import com.lifetech.dhap.pathcloud.tracking.api.vo.SlideBackConfirmVO;
import org.apache.cxf.jaxrs.ext.multipart.Attachment;
import org.apache.cxf.jaxrs.ext.multipart.Multipart;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.IOException;

/**
 * Created by LiuMei on 2017-06-07.
 */
@Produces({"application/json"})
@Path("/archiving/slide")
public interface SlideArchivingApi {

    /**
     * 玻片存档查询信息
     * @param serialNumber
     * @return
     * @throws BuzException
     */
    @GET
    @Path("/{serialNumber}")
    ResponseVO getInfoForArchiveBySerialNumber(@PathParam("serialNumber") String serialNumber,
                                           @QueryParam("marker") String marker) throws BuzException;

    /**
     *  存档
     * @param slideArchiveConfirmVO
     * @return
     * @throws BuzException
     */
    @POST
    @Consumes("application/json")
    @Path("/confirm")
    ResponseVO archiveConfirm( SlideArchiveConfirmVO slideArchiveConfirmVO) throws BuzException;

    /**
     * 玻片借阅信息查询
     * @param serialNumber
     * @param marker
     * @return
     * @throws BuzException
     */
    @GET
    @Path("/borrow/{serialNumber}")
    ResponseVO borrowQuery(@PathParam("serialNumber") String serialNumber,
                           @QueryParam("marker") String marker) throws BuzException;

    /**
     * 借阅
     * @param blockBorrowVO
     * @return
     * @throws BuzException
     */
    @POST
    @Path("/borrow")
    ResponseVO borrow(BlockBorrowVO blockBorrowVO) throws BuzException;


    /**
     * 玻片信息查询
     * @param
     * @return
     * @throws BuzException
     */
    @GET
    @Path("/slidesArchivingInfo")
    ResponseVO getSlidesArchivingInfo(@QueryParam("page") @DefaultValue("1") Integer page,
                                      @QueryParam("length") @DefaultValue("10") Integer length,
                                      @QueryParam("filter")String filter,
                                      @QueryParam("type")Integer type,
                                      @QueryParam("status")Integer status,
                                      @QueryParam("order") Integer order,
                                      @QueryParam("sort") String sort) throws BuzException;


    /**
     * 查询玻片借阅信息
     * @param serialNumber
     * @return
     * @throws BuzException
     */
    @GET
    @Path("/backConfirm/{serialNumber}")
    ResponseVO getSlidesInfoBySerialNumber(@PathParam("serialNumber") String serialNumber) throws BuzException;

    /**
     * 玻片归还登记
     * @param vo
     * @return
     * @throws BuzException
     */
    @POST
    @Consumes("application/json")
    @Path("/backConfirm")
    ResponseVO backConfirm(SlideBackConfirmVO vo) throws BuzException;


    /**
     * 查询玻片借阅历史
     * @param
     * @return
     * @throws BuzException
     */
    @GET
    @Path("/brrowHistory")
    ResponseVO getSlidesBrrowHistory(@QueryParam("blockArchiveId")Long blockArchiveId) throws BuzException;


    /**
     *  开始扫描玻片，获取扫码玻片信息和截图, 多个玻片英文逗号,分隔
     * @return
     * @throws BuzException, IOException
     */
    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Path("/scan/block")
    ResponseVO scanResult(@Multipart(value = "blocks", required = false) String blocks,
                          @Multipart(value = "file", required = false) Attachment file) throws BuzException, IOException;

    /**
     * 获取批量扫描的玻片数据
     * @return
     * @throws BuzException
     */
    @GET
    @Path("/scan/result")
    ResponseVO getScanResult() throws BuzException;
}
