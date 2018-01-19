package com.lifetech.dhap.pathcloud.tracking.api.impl;

import com.lifetech.dhap.pathcloud.application.application.PathologyApplication;
import com.lifetech.dhap.pathcloud.application.application.dto.PathologyDto;
import com.lifetech.dhap.pathcloud.common.constant.Config;
import com.lifetech.dhap.pathcloud.common.data.PathologyStatus;
import com.lifetech.dhap.pathcloud.common.exception.BuzException;
import com.lifetech.dhap.pathcloud.common.exception.BuzExceptionCode;
import com.lifetech.dhap.pathcloud.common.response.PageDataVO;
import com.lifetech.dhap.pathcloud.common.response.ResponseVO;
import com.lifetech.dhap.pathcloud.common.utils.StringUtil;
import com.lifetech.dhap.pathcloud.security.UserContext;
import com.lifetech.dhap.pathcloud.tracking.api.SlideArchivingApi;
import com.lifetech.dhap.pathcloud.tracking.api.vo.*;
import com.lifetech.dhap.pathcloud.tracking.application.ArchiveApplication;
import com.lifetech.dhap.pathcloud.tracking.application.BlockApplication;
import com.lifetech.dhap.pathcloud.tracking.application.condition.ArchiveCondition;
import com.lifetech.dhap.pathcloud.tracking.application.condition.SlideArchivingCon;
import com.lifetech.dhap.pathcloud.tracking.application.condition.SlideCondition;
import com.lifetech.dhap.pathcloud.tracking.application.dto.*;
import com.lifetech.dhap.pathcloud.tracking.application.infrastructure.code.ArchiveStatus;
import com.lifetech.dhap.pathcloud.tracking.infrastructure.code.FileType;
import com.lifetech.dhap.pathcloud.tracking.infrastructure.code.ScanLocation;
import com.lifetech.dhap.pathcloud.tracking.infrastructure.code.SlideArchivSortByEnum;
import com.lifetech.dhap.pathcloud.user.api.vo.UserSimpleVO;
import com.lifetech.dhap.pathcloud.user.application.UserApplication;
import com.lifetech.dhap.pathcloud.user.application.dto.UserSimpleDto;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.cxf.jaxrs.ext.multipart.Attachment;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.PhaseInterceptorChain;
import org.apache.cxf.transport.http.AbstractHTTPDestination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.DefaultValue;
import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by LiuMei on 2017-06-07.
 */
@Component("slideArchivingApi")
public class SlideArchivingApiImpl implements SlideArchivingApi {

    private static final Logger log = LoggerFactory.getLogger(SlideArchivingApiImpl.class);

    @Autowired
    private ArchiveApplication archiveApplication;

    @Autowired
    private PathologyApplication pathologyApplication;

    @Autowired
    private BlockApplication blockApplication;

    @Autowired
    private UserApplication userApplication;

    @Override
    public ResponseVO archiveConfirm(SlideArchiveConfirmVO slideArchiveConfirmVO) throws BuzException {
        if (slideArchiveConfirmVO == null) {
            throw new BuzException(BuzExceptionCode.ErrorParam);
        }
        Integer archivingMethod = slideArchiveConfirmVO.getArchivingMethod();
        String archivingNo = slideArchiveConfirmVO.getArchivingNo();
        List<ProductionSaveVO> archiveSlides = slideArchiveConfirmVO.getArchiveSlides();
        if (archivingMethod == null || StringUtils.isBlank(archivingNo) || CollectionUtils.isEmpty(archiveSlides)) {
            throw new BuzException(BuzExceptionCode.ErrorParam);
        }
        Boolean error = false;//用来标记有无异常
        List<SlideLostVO> slideLostVOs = new ArrayList<>();
        SlideLostVO slideLostVO;
        for (ProductionSaveVO productionSaveVO : archiveSlides) {
            long pathId = productionSaveVO.getPathId();
            List<Long> slideIds = productionSaveVO.getSlideIds();
            PathologyDto pathologyDto = pathologyApplication.getSimplePathById(pathId);
            String pathNo = pathologyDto.getSerialNumber();
            SlideCondition condition = new SlideCondition();
            condition.setPathId(pathId);
            condition.setStatus(PathologyStatus.PrepareArchiving.toCode());
            List<SlideDto> slideDtoList = blockApplication.getSlideByCondition(condition);
            if (CollectionUtils.isNotEmpty(slideDtoList)) {
                for (SlideDto slideDto : slideDtoList) {
                    if (!slideIds.contains(slideDto.getId())) {
                        if (!error) {
                            error = true;
                        }
                        slideLostVO = new SlideLostVO();
                        slideLostVO.setPathNo(pathNo);
                        slideLostVO.setBlockSubId(slideDto.getBlockSubId());
                        slideLostVO.setId(slideDto.getId());
                        slideLostVO.setSerialNumber(slideDto.getSerialNumber());
                        slideLostVO.setSubId(slideDto.getSubId());
                        slideLostVO.setStatus(slideDto.getStatus());
                        slideLostVO.setStatusDesc(PathologyStatus.valueOf(slideDto.getStatus()).toString());
                        slideLostVO.setLastOperateTime(slideDto.getUpdateTime());
                        UserSimpleDto userSimpleDto = userApplication.getUserSimpleInfoById(slideDto.getUpdateBy());
                        if (userSimpleDto != null) {
                            UserSimpleVO userSimpleVO = new UserSimpleVO();
                            BeanUtils.copyProperties(userSimpleDto, userSimpleVO);
                            slideLostVO.setLastOperator(userSimpleVO);
                        }
                        slideLostVOs.add(slideLostVO);
                    }
                }
            }
        }
        if (error) {
            return new ResponseVO(slideLostVOs);
        }
        if (!error) {//无异常情况
            List<Long> slideIds = new ArrayList<>();
            for (ProductionSaveVO productionSaveVO : archiveSlides) {
                List<Long> ids = productionSaveVO.getSlideIds();
                slideIds.addAll(ids);
            }
            SlideArchiveConfirmDto slideArchiveConfirmDto = new SlideArchiveConfirmDto();
            slideArchiveConfirmDto.setArchivingMethod(archivingMethod);
            slideArchiveConfirmDto.setArchivingNo(archivingNo);
            slideArchiveConfirmDto.setSlideIds(slideIds);
            archiveApplication.archiveConfirm(slideArchiveConfirmDto);
        }
        return new ResponseVO();
    }

    @Override
    public ResponseVO borrowQuery(String serialNumber, String marker) throws BuzException {
        if (serialNumber == null || "".equals(serialNumber.trim()) || serialNumber.length() < 9) {
            throw new BuzException(BuzExceptionCode.ErrorParam);
        }
        String pathNo = serialNumber.substring(0, 9);
        PathologyDto pathologyDto = pathologyApplication.getPathologyBySerialNumber(pathNo);
        if (pathologyDto == null) {
            throw new BuzException(BuzExceptionCode.RecordNotExists);
        }

        ArchiveCondition condition = new ArchiveCondition();
        if (marker != null && !"".equals(marker)) {
            condition.setMarker(marker);
        }

        List<BlockArchiveDto> archiveDtoList = null;
        if (serialNumber.length() == 9) {
            condition.setPathNo(serialNumber);
            archiveDtoList = archiveApplication.getInfoForBorrow(condition);
            if (archiveDtoList == null || archiveDtoList.size() == 0) {
                throw new BuzException(BuzExceptionCode.RecordNotExists);
            }
        } else {
            String[] split = serialNumber.split(Config.serialNumberSeparator);
            if (split.length == 2) {//病理号-蜡块号
                condition.setPathNo(split[0]);
                condition.setBlockSubId(split[1]);
                archiveDtoList = archiveApplication.getInfoForBorrow(condition);
                if (archiveDtoList == null) {
                    throw new BuzException(BuzExceptionCode.RecordNotExists);
                }
            } else if (split.length == 3) {//病理号-蜡块号-玻片号
                condition.setPathNo(split[0]);
                condition.setBlockSubId(split[1]);
                condition.setSlideSubId(split[2]);
                archiveDtoList = archiveApplication.getInfoForBorrow(condition);
                if (archiveDtoList == null) {
                    throw new BuzException(BuzExceptionCode.RecordNotExists);
                }
            } else {
                throw new BuzException(BuzExceptionCode.ErrorParam);
            }
        }
        return new ResponseVO(archiveDtoList);
    }

    @Override
    public ResponseVO borrow(BlockBorrowVO blockBorrowVO) throws BuzException {
        if (blockBorrowVO == null) {
            throw new BuzException(BuzExceptionCode.ErrorParam);
        }
        long userId = UserContext.getLoginUserID();
        List<Long> archiveIds = blockBorrowVO.getArchiveIds();
        String borrowName = blockBorrowVO.getBorrowName();
        String borrowPhone = blockBorrowVO.getBorrowPhone();
        Date planBack = blockBorrowVO.getPlanBack();
        String idNumber = blockBorrowVO.getIdNumber();
        if (archiveIds == null || archiveIds.size() <= 0 || StringUtils.isBlank(borrowName) || StringUtils.isBlank(borrowPhone)
                || planBack == null || StringUtils.isBlank(idNumber)) {
            throw new BuzException(BuzExceptionCode.ErrorParam);
        }
        List<BlockBorrowDto> blockBorrowDtos = new ArrayList<>();
        BlockBorrowDto blockBorrowDto;
        for (Long id : archiveIds) {
            if (id != null) {
                blockBorrowDto = new BlockBorrowDto();
                blockBorrowDto.setArchiveId(id);
                blockBorrowDto.setArchiveStatus(ArchiveStatus.borrow.toCode());
                blockBorrowDto.setBorrowName(borrowName);
                blockBorrowDto.setBorrowPhone(borrowPhone);
                blockBorrowDto.setPlanBack(planBack);
                blockBorrowDto.setBorrowType(blockBorrowVO.getBorrowType());
                blockBorrowDto.setCreateBy(userId);
                blockBorrowDto.setTutor(blockBorrowVO.getTutor());
                blockBorrowDto.setDepartments(blockBorrowVO.getDepartments());
                blockBorrowDto.setNote(blockBorrowVO.getNote());
                blockBorrowDto.setUnit(blockBorrowVO.getUnit());
                blockBorrowDto.setIdNumber(blockBorrowVO.getIdNumber());
                blockBorrowDto.setCashPledge(blockBorrowVO.getCashPledge());
                blockBorrowDtos.add(blockBorrowDto);
            }
        }
        archiveApplication.borrowConfirm(blockBorrowDtos);
        return new ResponseVO();
    }

    @Override
    public ResponseVO getInfoForArchiveBySerialNumber(String serialNumber, String marker) throws BuzException {

        if (StringUtils.isBlank(serialNumber) || serialNumber.length() < 9) {
            throw new BuzException(BuzExceptionCode.ErrorParam);
        }
        String pathNo = serialNumber.substring(0, 9);
        PathologyDto pathologyDto = pathologyApplication.getPathologyBySerialNumber(pathNo);
        if (pathologyDto == null) {
            throw new BuzException(BuzExceptionCode.RecordNotExists);
        }

        ArchiveCondition condition = new ArchiveCondition();
        if (StringUtils.isNotBlank(marker)) {
            condition.setMarker(marker);
        }

        List<ArchiveDto> archiveDtoList;
        if (serialNumber.length() == 9) {
            condition.setPathNo(serialNumber);
            archiveDtoList = archiveApplication.getInfoForArchive(condition);
        } else {
            String[] split = serialNumber.split(Config.serialNumberSeparator);
            if (split.length == 2) {//病理号-蜡块号
                condition.setPathNo(split[0]);
                condition.setBlockSubId(split[1]);
                archiveDtoList = archiveApplication.getInfoForArchive(condition);
            } else if (split.length == 3) {//病理号-蜡块号-玻片号
                condition.setPathNo(split[0]);
                condition.setBlockSubId(split[1]);
                condition.setSlideSubId(split[2]);
                archiveDtoList = archiveApplication.getInfoForArchive(condition);
            } else {
                throw new BuzException(BuzExceptionCode.ErrorParam);
            }
            if (archiveDtoList == null) {
                throw new BuzException(BuzExceptionCode.RecordNotExists);
            }
        }
        return new ResponseVO(archiveDtoList);
    }

    @Override
    public ResponseVO getSlidesArchivingInfo(@DefaultValue("1") Integer page, @DefaultValue("10") Integer length,
                                             String filter, Integer type, Integer status, Integer order, String sort) throws BuzException {
        if (page < 0 || length < 1) {
            throw new BuzException(BuzExceptionCode.ErrorParam);
        }
        SlideArchivingCon con = new SlideArchivingCon();
        con.setStart((page - 1) * length);
        con.setSize(length);

        if (filter != null) {
            con.setFilter(StringUtil.escapeExprSpecialWord(filter));
        }
        if (type != null) {
            con.setType(type);
        }

        if (status != null) {
            con.setStatus(status);
        }
        if (order != null) {
            con.setOrder(SlideArchivSortByEnum.valueOf(order).toString());
            if (sort != null) {
                con.setOrder(con.getOrder() + " " + sort);
            }
        }

        List<SlideArchiveInfoDto> dtos = archiveApplication.getSlidesArchivingInfo(con);
        Long total = archiveApplication.getSlidesArchivingInfoTotal(con);
        return new PageDataVO(page, length, total, dtos);
    }

    @Override
    public ResponseVO getSlidesInfoBySerialNumber(String serialNumber) throws BuzException {
        if (serialNumber == null || "".equals(serialNumber.trim()) || serialNumber.length() < 13) {
            throw new BuzException(BuzExceptionCode.ErrorParam);
        }
        String[] split = serialNumber.split(Config.serialNumberSeparator);
        if (split.length != 3) {
            return new ResponseVO(2, "玻片格式错误");
        }
        ArchiveCondition condition = new ArchiveCondition();
        condition.setPathNo(split[0]);
        condition.setBlockSubId(split[1]);
        condition.setSlideSubId(split[2]);
        return new ResponseVO(archiveApplication.getSlidesBorrowInfo(condition));
    }

    @Override
    public ResponseVO backConfirm(SlideBackConfirmVO vo) throws BuzException {
        if (vo == null) {
            return new ResponseVO(2, "参数错误");
        }
        vo.setUpdateBy(UserContext.getLoginUserID());
        SlideBackConfirmDto dto = new SlideBackConfirmDto();
        BeanUtils.copyProperties(vo, dto);
        archiveApplication.slideBackConfirm(dto);
        return new ResponseVO();
    }

    @Override
    public ResponseVO getSlidesBrrowHistory(Long blockArchiveId) throws BuzException {
        if (blockArchiveId == null || blockArchiveId <= 0) {
            throw new BuzException(BuzExceptionCode.ErrorParam);
        }
        List<BrrowHistoryDto> dtos = archiveApplication.getSlidesBrrowHistory(blockArchiveId);

        return new ResponseVO(dtos);
    }

    @Override
    public ResponseVO scanResult(String blocks, Attachment file) throws BuzException, IOException {
        if (file == null) {
            throw new BuzException(BuzExceptionCode.ErrorParam);
        }
        Message message = PhaseInterceptorChain.getCurrentMessage();
        HttpServletRequest request = (HttpServletRequest) message.get(AbstractHTTPDestination.HTTP_REQUEST);
        String token = request.getHeader("Authorization");
        String formatDate = new SimpleDateFormat("yyMMdd").format(new Date());
        String path = null;
        try {
            if (token == null || !token.equals(Config.token)) {
                HttpServletResponse response = (HttpServletResponse) message.get(AbstractHTTPDestination.HTTP_RESPONSE);
                response.setContentType("application/json");
                response.setHeader("Cache-Control", "no-cache, no-store");
                response.setHeader("Pragma", "no-cache");
                long time = System.currentTimeMillis();
                response.setDateHeader("Last-Modified", time);
                response.setDateHeader("Date", time);
                response.setDateHeader("Expires", time);
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

                response.setCharacterEncoding("UTF-8");
                response.getOutputStream().flush();
                response.getOutputStream().close();
                return new ResponseVO(-1, "401");
            }

            log.info("Slide Archiving Batch scans param: blocks=>" + blocks + "\n" +
                    "file=>[name=" + file.getContentDisposition().getFilename() +
                    ", size=" + file.getDataHandler().getInputStream().available() + "]");
            if (file.getDataHandler().getInputStream().available() > 0) {
                String saveName = UUID.randomUUID().toString().replace("-", "") + file.getContentDisposition().getFilename().substring(file.getContentDisposition().getFilename().lastIndexOf("."));
                path = FileType.ScanImage.toString() + File.separator + ScanLocation.Archive.toString() + File.separator + formatDate + File.separator + saveName;
                File f = new File(Config.nfsMntRw + path);
                log.debug("file path:" + f.getAbsolutePath());
                FileUtils.copyInputStreamToFile(file.getDataHandler().getInputStream(), f);
            } else {
                log.warn("Slide Archiving Batch scan upload Image size is 0");
            }
            if (blocks != null) {
                blocks = URLDecoder.decode(blocks, "UTF-8");
            }
            BlockScanImageDto image = new BlockScanImageDto();
            image.setScanLocation(ScanLocation.Archive.toCode());
            image.setImagePath(path);
            image.setImageName(file.getContentDisposition().getFilename());
            image.setImageSize(file.getDataHandler().getInputStream().available());
            List<String> errorBlocks = blockApplication.addBlockScanResult(blocks, image);
            if (!errorBlocks.isEmpty()) {
                throw new BuzException(BuzExceptionCode.ErrorParam, errorBlocks.toString());
            }
        } catch (IOException e) {
            log.error("Error reading file" + file.getContentDisposition().getFilename(), e);
            throw e;
        }
        return new ResponseVO("OK");
    }

    @Override
    public ResponseVO getScanResult() throws BuzException {
        Integer location = ScanLocation.Archive.toCode();
        List<BlockScanDto> blockScans = blockApplication.getBlockScansByLocation(location);
        BlockScanImageDto image = blockApplication.selectNewestImageByLocation(location);
        Map<String, Object> data = new HashMap<>();
        data.put("blocks", blockScans);
        data.put("image", image);
        return new ResponseVO(data);
    }
}
