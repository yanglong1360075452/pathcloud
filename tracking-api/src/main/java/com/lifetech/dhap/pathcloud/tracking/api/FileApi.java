package com.lifetech.dhap.pathcloud.tracking.api;

import com.lifetech.dhap.pathcloud.common.exception.BuzException;
import com.lifetech.dhap.pathcloud.common.response.ResponseVO;
import com.lifetech.dhap.pathcloud.tracking.api.vo.CheckFileVO;
import org.apache.cxf.jaxrs.ext.multipart.Attachment;
import org.apache.cxf.jaxrs.ext.multipart.Multipart;
import org.apache.cxf.jaxrs.ext.multipart.MultipartBody;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.File;
import java.io.FileNotFoundException;

/**
 * 文件接口
 *
 * @author eric.guo@ostengar.com
 * @version 1.0
 * @time 2016-12-21-13:23
 */
@Path("/")
public interface FileApi {

    /**
     * 图片上传
     *
     * @param operation
     * @param pathologyId
     * @param attachment
     * @return
     */
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Path("/file/{operation}/{pathologyId}")
    ResponseVO uploadFile(@PathParam("operation") Integer operation, @PathParam("pathologyId") Long pathologyId, @Multipart(value = "tag", required = false) String tag,
                          @Multipart(value = "file") Attachment attachment);

    /**
     * 取材确认上传照片
     *
     * @param basketNum
     * @param body
     * @return
     */
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Path("/file/grossingConfirm/{basketNum}")
    ResponseVO grossingConfirmUpload(@PathParam("basketNum") int basketNum, MultipartBody body);

    /**
     * 获取取材确认图片
     *
     * @param blockId
     * @return
     */
    @GET
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    @Path("/static/grossingConfirm/{blockId}")
    File getGrossingConfirmFile(@PathParam("blockId") long blockId) throws FileNotFoundException;

    /**
     * 图片编辑
     *
     * @param body
     * @return
     */
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Path("/file/{fileId}")
    ResponseVO editFile(@PathParam("fileId") Long fileId, MultipartBody body);

    /**
     * 修改图片tag
     *
     * @return
     */
    @PUT
    @Consumes("application/json")
    @Path("/file/tag/{fileId}/{tag}")
    ResponseVO editFileTag(@PathParam("fileId") Long fileId, @PathParam("tag") String tag) throws BuzException;

    /**
     * 文件下载
     *
     * @param name
     * @return
     */
    @GET
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    @Path("/static/{type}/{date}/{name}")
    File downloadFile(@PathParam("type") Integer type, @PathParam("date") String date, @PathParam("name") String name) throws FileNotFoundException;

    /**
     * 文件下载
     *
     * @param location
     * @param date
     * @param name
     * @return
     * @throws FileNotFoundException
     */
    @GET
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    @Path("/static/scanImage/{location}/{date}/{name}")
    File scanImage(@PathParam("location") String location, @PathParam("date") String date, @PathParam("name") String name) throws FileNotFoundException;

    @DELETE
    @Path("/file/{fileId}")
    ResponseVO deleteFile(@PathParam("fileId") long fileId) throws BuzException;

    /**
     * 是否选中照片
     *
     * @return
     * @throws BuzException
     */
    @PUT
    @Consumes("application/json")
    @Path("/file/check")
    ResponseVO checkFile(CheckFileVO checkFileVO) throws BuzException;
}
