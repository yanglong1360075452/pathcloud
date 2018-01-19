package com.lifetech.dhap.pathcloud.tracking.api;

import com.lifetech.dhap.pathcloud.common.exception.BuzException;
import com.lifetech.dhap.pathcloud.common.response.ResponseVO;
import com.lifetech.dhap.pathcloud.tracking.api.vo.AbnormalHandleVO;
import com.lifetech.dhap.pathcloud.tracking.api.vo.BlockScoreVO;
import com.lifetech.dhap.pathcloud.tracking.api.vo.ProductionSaveVO;
import org.apache.cxf.jaxrs.ext.multipart.Attachment;
import org.apache.cxf.jaxrs.ext.multipart.Multipart;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.util.List;

/**制片确认接口
 * Created by HP on 2016/12/28.
 */
@Produces({"application/json"})
@Path("/production")
public interface ProductionApi {

    /**
     * 批量扫描获取制片确认的信息
     */
    @POST
    @Consumes("application/json")
    ResponseVO getSlidesInfoByScan(List<String> slideNos) throws BuzException;

    /**
     * 根据病理号或病理号-蜡块号查或病理号-蜡块号-玻片号询制片确认信息
     * @param serialNumber
     * @return
     * @throws BuzException
     */
    @GET
    @Path("/{serialNumber}")
    ResponseVO getSlidesInfoBySerialNumber(@PathParam("serialNumber") String serialNumber ) throws BuzException;

    /**
     * 制片确认
     */
    @POST
    @Consumes("application/json")
    @Path("/confirm")
    ResponseVO productionConfirm(List<ProductionSaveVO> productionSaveVOs) throws BuzException;

    /**
     *  异常处理
     * @return
     * @throws BuzException
     */
    @POST
    @Consumes("application/json")
    @Path("/abnormal")
    ResponseVO abnormalHandle(List<AbnormalHandleVO> abnormalVO) throws BuzException;


    /**
     *  开始扫描拨片，获取扫码玻片信息和截图, 多个玻片英文逗号,分隔
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

    /**
     * 制片确认玻片评分
     * @param slideId
     * @param scoreVO
     * @return
     * @throws BuzException
     */
    @PUT
    @Path("/score/{slideId}")
    ResponseVO score(@PathParam("slideId") long slideId, BlockScoreVO scoreVO) throws BuzException;

    /**
     * 根据玻片号获取评分结果
     * @param slideNo
     * @return
     * @throws BuzException
     */
    @GET
    @Path("/score/{slideNo}/{slideSubId}")
    ResponseVO getScore(@PathParam("slideNo") String slideNo,@PathParam("slideSubId") String slideSubId) throws BuzException;
}
