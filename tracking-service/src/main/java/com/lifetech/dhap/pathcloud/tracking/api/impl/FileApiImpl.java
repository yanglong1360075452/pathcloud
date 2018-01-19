package com.lifetech.dhap.pathcloud.tracking.api.impl;

import com.lifetech.dhap.pathcloud.application.application.PathologyApplication;
import com.lifetech.dhap.pathcloud.common.constant.Config;
import com.lifetech.dhap.pathcloud.common.data.PathologyStatus;
import com.lifetech.dhap.pathcloud.common.exception.BuzException;
import com.lifetech.dhap.pathcloud.common.exception.BuzExceptionCode;
import com.lifetech.dhap.pathcloud.common.response.ResponseVO;
import com.lifetech.dhap.pathcloud.file.application.FileMappingApplication;
import com.lifetech.dhap.pathcloud.file.application.PathologyFileApplication;
import com.lifetech.dhap.pathcloud.file.application.dto.PathologyFileDto;
import com.lifetech.dhap.pathcloud.file.domain.model.FileMapping;
import com.lifetech.dhap.pathcloud.security.UserContext;
import com.lifetech.dhap.pathcloud.tracking.api.FileApi;
import com.lifetech.dhap.pathcloud.tracking.api.vo.CheckFileVO;
import com.lifetech.dhap.pathcloud.tracking.application.BlockApplication;
import com.lifetech.dhap.pathcloud.tracking.application.GrossingApplication;
import com.lifetech.dhap.pathcloud.tracking.application.condition.GrossingCon;
import com.lifetech.dhap.pathcloud.tracking.application.dto.BlockDetailDto;
import com.lifetech.dhap.pathcloud.tracking.application.dto.BlockScanImageDto;
import com.lifetech.dhap.pathcloud.tracking.application.infrastructure.code.TrackingOperation;
import com.lifetech.dhap.pathcloud.tracking.infrastructure.code.FileType;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.cxf.jaxrs.ext.multipart.Attachment;
import org.apache.cxf.jaxrs.ext.multipart.MultipartBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import sun.misc.BASE64Decoder;

import javax.activation.DataHandler;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author eric.guo@ostengar.com
 * @version 1.0
 * @time 2016-12-21-13:31
 */
@Component("imageApi")
public class FileApiImpl implements FileApi {

    Logger log = LoggerFactory.getLogger(FileApiImpl.class);

    @Autowired
    private PathologyFileApplication pathologyFileApplication;

    @Autowired
    private PathologyApplication pathologyApplication;

    @Autowired
    private BlockApplication blockApplication;

    @Autowired
    private GrossingApplication grossingApplication;

    @Autowired
    private FileMappingApplication fileMappingApplication;

    @Override
    public ResponseVO uploadFile(Integer operation, Long pathologyId, String tag, Attachment attachment) {
        try {
            List<Map<String, Object>> data = new ArrayList<>();
            //save file to location
            DataHandler handler = attachment.getDataHandler();
            String saveName = UUID.randomUUID().toString().replace("-", "") + ".png";

            String formatDate = new SimpleDateFormat("yyMMdd").format(new Date());
            String path = Config.nfsMntRw;
            Integer fileType;
            if (operation.equals(TrackingOperation.report.toCode())) {
                fileType = FileType.PDF.toCode();
                saveName = pathologyApplication.getSimplePathById(pathologyId).getSerialNumber() + ".pdf";
            } else {
                fileType = FileType.Image.toCode();
            }

            File f = new File(path + FileType.valueOf(fileType).toString() + File.separator + formatDate + File.separator + saveName);
            log.debug("file path:" + f.getAbsolutePath());
            InputStream inputStream = handler.getInputStream();
            if (inputStream.available() > 0) {
                BASE64Decoder decoder = new BASE64Decoder();
                byte[] image = decoder.decodeBuffer(inputStream);
                FileUtils.copyInputStreamToFile(new ByteArrayInputStream(image), f);
            }

            PathologyFileDto file = new PathologyFileDto();
            file.setCreateBy(UserContext.getLoginUserID());
            file.setOperation(operation);
            file.setKeepFlag(true);
            file.setType(fileType);
            file.setContent(formatDate + File.separator + saveName);
            file.setPathologyId(pathologyId);
            if (operation.equals(TrackingOperation.firstDiagnose.toCode())) {
                tag = "1";
                file.setTag(tag);//诊断图片默认tag
            }
            if (StringUtils.isNotBlank(tag)) {
                file.setTag(tag);
            }
            pathologyFileApplication.addPathologyFile(file);

            Map<String, Object> map = new HashMap<>();
            map.put("id", file.getId());
            map.put("type", fileType);
            map.put("saveName", saveName);
            map.put("tag", tag);
            map.put("url", "api" + File.separator + "static" + File.separator + fileType + File.separator + file.getContent());
            data.add(map);
            return new ResponseVO(data);
        } catch (Exception e) {
            log.error("pathId=" + pathologyId + " operation=" + operation, e);
        }

        return new ResponseVO(-1);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseVO grossingConfirmUpload(int basketNum, MultipartBody body) {
        for (Attachment a : body.getAllAttachments()) {
            //save file to location
            DataHandler handler = a.getDataHandler();
            String saveName = UUID.randomUUID().toString().replace("-", "") + ".png";

            String formatDate = new SimpleDateFormat("yyMMdd").format(new Date());
            String path = Config.nfsMntRw;
            Integer fileType = FileType.Image.toCode();

            File f = new File(path + FileType.valueOf(fileType).toString() + File.separator + formatDate + File.separator + saveName);
            log.debug("file path:" + f.getAbsolutePath());
            try {
                if (handler.getInputStream().available() > 0) {
                    BASE64Decoder decoder = new BASE64Decoder();
                    byte[] image = decoder.decodeBuffer(handler.getInputStream());
                    FileUtils.copyInputStreamToFile(new ByteArrayInputStream(image), f);
                }
            } catch (IOException e) {
                log.error(f.getAbsolutePath(), e);
            }
            GrossingCon con = new GrossingCon();
            con.setSize(Integer.MAX_VALUE);
            con.setBlockStatus(PathologyStatus.PrepareGrossingConfirm.toCode());
            con.setOperation(TrackingOperation.grossing.toCode());
            List<Integer> list = new ArrayList<>();
            list.add(basketNum);
            con.setBasketNumbers(list);
            List<BlockDetailDto> grossingBlocks = grossingApplication.getBlockByCondition(con);
            Long userId = UserContext.getLoginUserID();
            if (CollectionUtils.isNotEmpty(grossingBlocks)) {
                List<PathologyFileDto> fileDtoList = new ArrayList<>();
                List<PathologyFileDto> updateFiles = new ArrayList<>();
                PathologyFileDto file;
                PathologyFileDto updateFile;
                for (BlockDetailDto blockDetailDto : grossingBlocks) {
                    int operation = TrackingOperation.grossingConfirm.toCode();
                    Long blockId = blockDetailDto.getBlockId();
                    try {
                        File grossingConfirmFile = getGrossingConfirmFile(blockId);
                        boolean delete = grossingConfirmFile.delete();
                        if (delete) {
                            updateFile = pathologyFileApplication.getFileByBlockId(blockId);
                            updateFile.setContent(formatDate + File.separator + saveName);
                            updateFile.setUpdateBy(userId);
                            updateFiles.add(updateFile);
                        }
                    } catch (Exception e) {
                        file = new PathologyFileDto();
                        file.setCreateBy(userId);
                        file.setOperation(operation);
                        file.setKeepFlag(true);
                        file.setType(fileType);
                        file.setContent(formatDate + File.separator + saveName);
                        file.setPathologyId(blockId);//蜡块ID
                        fileDtoList.add(file);
                    }
                }
                if (fileDtoList.size() > 0) {
                    pathologyFileApplication.batchInsert(fileDtoList);
                }
                if (updateFiles.size() > 0) {
                    pathologyFileApplication.batchUpdate(updateFiles);
                }
            }
        }
        return new ResponseVO();
    }

    @Override
    public File getGrossingConfirmFile(long blockId) {
        PathologyFileDto file = pathologyFileApplication.getFileByBlockId(blockId);
        if (file != null) {
            String path;
            if (file.getCreateTime().before(new Date(System.currentTimeMillis() - Config.rsyncMaxMinutes * 60000L))) {
                if (Math.random() > 0.5) {
                    path = Config.nfsMntRw;
                } else {
                    path = Config.nfsMntRo;
                }
            } else {
                path = Config.nfsMntRw;
            }

            File f = new File(path + FileType.valueOf(file.getType()).toString() + File.separator + file.getContent());
            return f;
        } else {
            return null;
        }
    }

    @Override
    public ResponseVO editFile(Long fileId, MultipartBody body) {
        PathologyFileDto fileDto = pathologyFileApplication.getFileById(fileId);
        try {
            List<Map<String, String>> data = new ArrayList<>();
            for (Attachment a : body.getAllAttachments()) {
                //save file to location
                DataHandler handler = a.getDataHandler();
                String path = Config.nfsMntRw;

                File f = new File(path + FileType.Image.toString() + File.separator + fileDto.getContent());
                log.debug("file path:" + f.getAbsolutePath());
                if (handler.getInputStream().available() > 0) {
                    BASE64Decoder decoder = new BASE64Decoder();
                    byte[] image = decoder.decodeBuffer(handler.getInputStream());
                    FileUtils.copyInputStreamToFile(new ByteArrayInputStream(image), f);
                }

                PathologyFileDto file = new PathologyFileDto();
                file.setKeepFlag(true);
                file.setId(fileId);
                file.setUpdateBy(UserContext.getLoginUserID());
                pathologyFileApplication.updatePathologyFile(file);

                Map<String, String> map = new HashMap<>();
                map.put("saveName", fileDto.getContent());
                data.add(map);
            }
            return new ResponseVO(data);
        } catch (Exception e) {
            log.error("fileId=" + fileId, e);
        }

        return new ResponseVO(-1);
    }

    @Override
    public ResponseVO editFileTag(Long fileId, String tag) throws BuzException {
        if (fileId == null || tag == null) {
            throw new BuzException(BuzExceptionCode.ErrorParam);
        }
        PathologyFileDto pathologyFileDto = pathologyFileApplication.getFileById(fileId);
        if (pathologyFileDto == null) {
            throw new BuzException(BuzExceptionCode.RecordNotExists);
        }
        pathologyFileDto.setTag(tag);
        pathologyFileDto.setUpdateBy(UserContext.getLoginUserID());
        pathologyFileApplication.updatePathologyFile(pathologyFileDto);
        return new ResponseVO();
    }

    @Override
    public File downloadFile(Integer type, String date, String name) throws FileNotFoundException {
        PathologyFileDto file = pathologyFileApplication.getFileBySavename(date + File.separator + name);
        if (file == null) {
            throw new FileNotFoundException();
        }
        String path;
        if (file.getCreateTime().before(new Date(System.currentTimeMillis() - Config.rsyncMaxMinutes * 60000L))) {
            if (Math.random() > 0.5) {
                path = Config.nfsMntRw;
            } else {
                path = Config.nfsMntRo;
            }
        } else {
            path = Config.nfsMntRw;
        }

        File f = new File(path + FileType.valueOf(type).toString() + File.separator + file.getContent());
        return f;
    }

    @Override
    public File scanImage(String location, String date, String name) throws FileNotFoundException {
        BlockScanImageDto file = blockApplication.getScanImage("scanImage" + File.separator + location + File.separator + date + File.separator + name);
        if (file == null) {
            throw new FileNotFoundException();
        }
        String path;
        if (file.getCreateTime().before(new Date(System.currentTimeMillis() - Config.rsyncMaxMinutes * 60000L))) {
            if (Math.random() > 0.5) {
                path = Config.nfsMntRw;
            } else {
                path = Config.nfsMntRo;
            }
        } else {
            path = Config.nfsMntRw;
        }

        File f = new File(path + File.separator + file.getImagePath());
        return f;
    }

    @Override
    public ResponseVO deleteFile(long fileId) throws BuzException {
        PathologyFileDto fileDto = pathologyFileApplication.getFileById(fileId);
        if (fileDto == null || fileDto.getId() == null) {
            throw new BuzException(BuzExceptionCode.RecordNotExists);
        }
        String path;
        if (fileDto.getCreateTime().before(new Date(System.currentTimeMillis() - Config.rsyncMaxMinutes * 60000L))) {
            if (Math.random() > 0.5) {
                path = Config.nfsMntRw;
            } else {
                path = Config.nfsMntRo;
            }
        } else {
            path = Config.nfsMntRw;
        }
        File f = new File(path + FileType.valueOf(fileDto.getType()).toString() + File.separator + fileDto.getContent());
        if (f.exists()) {
            f.delete();
        }
        pathologyFileApplication.deleteFile(fileId);
        return new ResponseVO();
    }

    @Override
    public ResponseVO checkFile(CheckFileVO checkFileVO) throws BuzException {
        Long fileId = checkFileVO.getFileId();
        Long pathId = checkFileVO.getPathId();
        Long specialId = checkFileVO.getSpecialId();
        Boolean check = checkFileVO.getCheck();
        if (fileId == null || (pathId == null && specialId == null) || check == null) {
            throw new BuzException(BuzExceptionCode.ErrorParam);
        }
        FileMapping fileMapping = new FileMapping();
        fileMapping.setFileId(fileId);
        fileMapping.setPathId(pathId);
        fileMapping.setSpecialId(specialId);
        if (check) {
            fileMappingApplication.addMapping(fileMapping);
        } else {
            fileMappingApplication.deleteMapping(fileMapping);
        }
        return new ResponseVO();
    }
}
