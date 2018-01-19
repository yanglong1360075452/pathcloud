package com.lifetech.dhap.pathcloud.tracking.api.impl;

import com.lifetech.dhap.pathcloud.application.application.ApplicationIhcApplication;
import com.lifetech.dhap.pathcloud.application.application.PathologyApplication;
import com.lifetech.dhap.pathcloud.application.application.dto.IhcBlockDto;
import com.lifetech.dhap.pathcloud.application.application.dto.PathologyDto;
import com.lifetech.dhap.pathcloud.common.constant.Config;
import com.lifetech.dhap.pathcloud.common.data.PathologyStatus;
import com.lifetech.dhap.pathcloud.common.exception.BuzException;
import com.lifetech.dhap.pathcloud.common.exception.BuzExceptionCode;
import com.lifetech.dhap.pathcloud.common.response.ResponseVO;
import com.lifetech.dhap.pathcloud.notification.infrastructure.code.NotificationSource;
import com.lifetech.dhap.pathcloud.security.UserContext;
import com.lifetech.dhap.pathcloud.tracking.api.ProductionApi;
import com.lifetech.dhap.pathcloud.tracking.api.vo.*;
import com.lifetech.dhap.pathcloud.tracking.application.BlockApplication;
import com.lifetech.dhap.pathcloud.tracking.application.BlockScoreApplication;
import com.lifetech.dhap.pathcloud.tracking.application.TrackingApplication;
import com.lifetech.dhap.pathcloud.tracking.application.condition.BlockCondition;
import com.lifetech.dhap.pathcloud.tracking.application.condition.TrackingCondition;
import com.lifetech.dhap.pathcloud.tracking.application.dto.*;
import com.lifetech.dhap.pathcloud.tracking.application.infrastructure.code.BlockScoreType;
import com.lifetech.dhap.pathcloud.tracking.application.infrastructure.code.TrackingOperation;
import com.lifetech.dhap.pathcloud.tracking.infrastructure.code.FileType;
import com.lifetech.dhap.pathcloud.tracking.infrastructure.code.ScanLocation;
import com.lifetech.dhap.pathcloud.user.api.vo.UserSimpleVO;
import com.lifetech.dhap.pathcloud.user.application.UserApplication;
import com.lifetech.dhap.pathcloud.user.application.dto.UserSimpleDto;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.cxf.jaxrs.ext.multipart.Attachment;
import org.apache.cxf.jaxrs.ext.multipart.Multipart;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.PhaseInterceptorChain;
import org.apache.cxf.transport.http.AbstractHTTPDestination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by HP on 2016/12/28.
 */
@Component("productionApi")
public class ProductionApiImpl implements ProductionApi {

    Logger log = LoggerFactory.getLogger(ProductionApiImpl.class);
    @Autowired
    private BlockApplication blockApplication;

    @Autowired
    private PathologyApplication pathologyApplication;

    @Autowired
    private UserApplication userApplication;

    @Autowired
    private TrackingApplication trackingApplication;

    @Autowired
    private ApplicationIhcApplication applicationIhcApplication;

    @Autowired
    private BlockScoreApplication blockScoreApplication;

    @Override
    public ResponseVO getSlidesInfoByScan(List<String> slideNos) throws BuzException {

        if (slideNos == null || slideNos.size() <= 0) {
            throw new BuzException(BuzExceptionCode.ErrorParam);
        }

        List<ProductionVO> productionVOs = new ArrayList<>();

        ProductionVO productionVO;
        for (String serialNumber : slideNos) {
            String[] split = serialNumber.split(Config.serialNumberSeparator);
            if (split.length == 3) {
                serialNumber = serialNumber.replaceAll(Config.serialNumberSeparator, "");
                productionVO = new ProductionVO();
                SlideDto slideDto = blockApplication.getSlideConfirmBySlideNoAndSubId(serialNumber, split[2]);
                if (slideDto != null) {
                    BeanUtils.copyProperties(slideDto, productionVO);
                    productionVOs.add(productionVO);
                }
            }
        }
        return new ResponseVO(productionVOs);
    }

    @Override
    public ResponseVO getSlidesInfoBySerialNumber(String serialNumber) throws BuzException {

        if (StringUtils.isBlank(serialNumber) || serialNumber.length() < 9) {
            throw new BuzException(BuzExceptionCode.ErrorParam);
        }
        String pathNo = serialNumber.substring(0, 9);
        PathologyDto pathologyDto = pathologyApplication.getPathologyBySerialNumber(pathNo);
        if (pathologyDto == null) {
            throw new BuzException(BuzExceptionCode.RecordNotExists);
        }

        Object result;
        if (serialNumber.length() == 9) {
            List<SlideDto> slideDTOs = blockApplication.getSlideConfirmByPathId(pathologyDto.getId());
            result = slideDTOsToProductionVO(slideDTOs);
            List<ProductionVO> data = (List<ProductionVO>) result;
            if (data == null || data.size() <= 0) {
                Integer status = pathologyDto.getStatus();
                if (!status.equals(PathologyStatus.PrepareCompletionConfirm.toCode())) {
                    HintVO hintVO = new HintVO();
                    hintVO.setPathNo(serialNumber);
                    hintVO.setStatus(status);
                    hintVO.setStatusDesc(PathologyStatus.getNameByCode(status));
                    result = hintVO;
                }
            }
        } else {
            String[] split = serialNumber.split(Config.serialNumberSeparator);
            serialNumber = serialNumber.replaceAll(Config.serialNumberSeparator, "");
            if (split.length == 2) {//病理号-蜡块号
                BlockDto blockDto = blockApplication.getBlockBySerialNumber(serialNumber);
                if (blockDto == null) {
                    throw new BuzException(BuzExceptionCode.RecordNotExists);
                }
                List<SlideDto> slideDTOs = blockApplication.getSlideConfirmByBlockId(blockDto.getId());
                result = slideDTOsToProductionVO(slideDTOs);
                List<ProductionVO> data = (List<ProductionVO>) result;
                if (data == null || data.size() <= 0) {
                    Integer blockStatus = blockDto.getStatus();
                    if (!blockStatus.equals(PathologyStatus.PrepareArchiving.toCode())) {
                        HintVO hintVO = new HintVO();
                        hintVO.setBlockSubNo(split[1]);
                        hintVO.setPathNo(split[0]);
                        hintVO.setStatus(blockStatus);
                        hintVO.setStatusDesc(PathologyStatus.getNameByCode(blockStatus));
                        result = hintVO;
                    }
                }
            } else if (split.length == 3) {//病理号-蜡块号-玻片号
                SlideDto slideDto = blockApplication.getSlideConfirmBySlideNoAndSubId(serialNumber, split[2]);
                if (slideDto == null) {
                    SlideDto slide = blockApplication.getSlideBySlideNoAndSubId(serialNumber, split[2]);
                    if (slide != null) {
                        Integer status = slide.getStatus();
                        TrackingCondition trackingCondition = new TrackingCondition();
                        trackingCondition.setBlockId(slide.getId());
                        trackingCondition.setOperation(TrackingOperation.completionConfirm.toCode());
                        TrackingDto trackingDto = trackingApplication.getTrackingByCondition(trackingCondition);
                        if (trackingDto != null) {
                            HintVO hintVO = new HintVO();
                            hintVO.setPathNo(split[0]);
                            hintVO.setBlockSubNo(split[1]);
                            hintVO.setStatus(status);
                            hintVO.setOperator(trackingDto.getOperatorId());
                            hintVO.setOperatorDesc(trackingDto.getOperatorName());
                            hintVO.setStatusDesc(PathologyStatus.getNameByCode(status));
                            hintVO.setOperatorTime(trackingDto.getOperationTime());
                            return new ResponseVO(hintVO);
                        }
                    } else {
                        throw new BuzException(BuzExceptionCode.RecordNotExists);
                    }

                }
                List<ProductionVO> productionVOS = new ArrayList<>();
                ProductionVO productionVO = new ProductionVO();
                BeanUtils.copyProperties(slideDto, productionVO);
                productionVOS.add(productionVO);
                result = productionVOS;
            } else {
                throw new BuzException(BuzExceptionCode.ErrorParam);
            }
        }
        return new ResponseVO(result);
    }

    private List<ProductionVO> slideDTOsToProductionVO(List<SlideDto> slideDTOs) {
        List<ProductionVO> productionVOs = new ArrayList<>();
        if (slideDTOs != null) {
            if (slideDTOs.size() > 0) {
                ProductionVO productionVO;
                for (SlideDto slideDto : slideDTOs) {
                    productionVO = new ProductionVO();
                    BeanUtils.copyProperties(slideDto, productionVO);
                    productionVOs.add(productionVO);
                }
            }
        }
        return productionVOs;
    }

    @Override
    public ResponseVO productionConfirm(List<ProductionSaveVO> productionSaveVOs) throws BuzException {

        if (CollectionUtils.isEmpty(productionSaveVOs)) {
            throw new BuzException(BuzExceptionCode.ErrorParam);
        }

        Boolean error = false;//用来标记有无异常

        List<SlideConfirmErrorVO> slideConfirmErrorVOs = new ArrayList<>();
        SlideConfirmErrorVO slideConfirmErrorVO;
        for (ProductionSaveVO productionSaveVO : productionSaveVOs) {
            long pathId = productionSaveVO.getPathId();
            List<Long> slideIds = productionSaveVO.getSlideIds();

            PathologyDto pathologyDto = pathologyApplication.getSimplePathById(pathId);
            Integer status = pathologyDto.getStatus();
            String pathNo = pathologyDto.getSerialNumber();
            slideConfirmErrorVO = new SlideConfirmErrorVO();
            slideConfirmErrorVO.setPathNo(pathNo);
            slideConfirmErrorVO.setPathId(pathId);
            slideConfirmErrorVO.setStatus(status);
            slideConfirmErrorVO.setReGrossing(pathologyDto.getReGrossing());
            slideConfirmErrorVO.setStatusDesc(PathologyStatus.getNameByCode(status));
            slideConfirmErrorVO.setLastOperateTime(pathologyDto.getUpdateTime());
            UserSimpleDto userSimpleDto = userApplication.getUserSimpleInfoById(pathologyDto.getUpdateBy());
            if (userSimpleDto != null) {
                UserSimpleVO userSimpleVO = new UserSimpleVO();
                BeanUtils.copyProperties(userSimpleDto, userSimpleVO);
                slideConfirmErrorVO.setLastOperator(userSimpleVO);
            }

            Boolean diagnoseSpecial = false;

            Map<Long, List<Long>> groupByApplyId = new HashMap<>();
            for (Long slideId : slideIds) {//判断有没有诊断申请的特检
                BlockDto blockDto = blockApplication.getBlockById(slideId);
                Integer specialDye = blockDto.getSpecialDye();
                if (!specialDye.equals(0) && !specialDye.equals(-1)) { //特染类别不是白片和HE
                    Long applyId = blockDto.getApplyId();
                    IhcBlockDto ihcBlockDto = applicationIhcApplication.getIhcBlockById(applyId);
                    if (ihcBlockDto != null) {
                        String number = ihcBlockDto.getNumber();
                        if (!StringUtils.isBlank(number)) {
                            diagnoseSpecial = true;
                        }
                        List<Long> longs = groupByApplyId.get(applyId);
                        if (CollectionUtils.isEmpty(longs)) {
                            longs = new ArrayList<>();
                            longs.add(slideId);
                            groupByApplyId.put(applyId, longs);
                        } else {
                            longs.add(slideId);
                        }
                    }
                }
            }

            if (diagnoseSpecial) { //诊断工作台申请的特染产出的玻片制片确认
                if (!MapUtils.isEmpty(groupByApplyId)) {
                    List<SlideLostVO> slideLostVOs = new ArrayList<>();
                    for (Map.Entry entry : groupByApplyId.entrySet()) {
                        Long applyId = (Long) entry.getKey();
                        List<Long> value = (List<Long>) entry.getValue();
                        List<SlideDto> slides = blockApplication.getSlideInfoByBlockIhcId(applyId);
                        if (value.size() != slides.size()) {
                            SlideLostVO slideLostVO;
                            for (SlideDto slideDto : slides) {
                                Long id = slideDto.getId();
                                if (!value.contains(id)) {
                                    error = true;
                                    slideLostVO = new SlideLostVO();
                                    Long parentId = slideDto.getParentId();
                                    BlockDto blockInfo = blockApplication.getBlockById(parentId);
                                    slideLostVO.setBlockSerialNumber(blockInfo.getSerialNumber());
                                    slideLostVO.setBlockSubId(blockInfo.getSubId());
                                    slideLostVO.setId(slideDto.getId());
                                    slideLostVO.setSerialNumber(slideDto.getSerialNumber());
                                    slideLostVO.setSubId(slideDto.getSubId());
                                    slideLostVO.setStatus(slideDto.getStatus());
                                    slideLostVO.setStatusDesc(PathologyStatus.valueOf(slideDto.getStatus()).toString());
                                    slideLostVO.setLastOperateTime(slideDto.getUpdateTime());
                                    slideLostVO.setSpecialApply(true);
                                    UserSimpleDto user = userApplication.getUserSimpleInfoById(slideDto.getUpdateBy());
                                    UserSimpleVO userVO = new UserSimpleVO();
                                    BeanUtils.copyProperties(user, userVO);
                                    slideLostVO.setLastOperator(userVO);
                                    slideLostVOs.add(slideLostVO);
                                }
                            }
                        }
                    }
                    slideConfirmErrorVO.setLostSlides(slideLostVOs);
                    slideConfirmErrorVOs.add(slideConfirmErrorVO);
                }
            } else {
                BlockCondition condition = new BlockCondition();
                condition.setPathId(pathId);
                List<Integer> statusList = new ArrayList<>();
                statusList.add(PathologyStatus.PrepareGrossingConfirm.toCode());
                statusList.add(PathologyStatus.PrepareDehydrate.toCode());
                statusList.add(PathologyStatus.Dehydrating.toCode());
                statusList.add(PathologyStatus.PrepareEmbed.toCode());
                statusList.add(PathologyStatus.PrepareSection.toCode());
                condition.setStatusList(statusList);
                List<BlockDto> blockDTOs = blockApplication.getBlockByCondition(condition);
                if (CollectionUtils.isNotEmpty(blockDTOs)) {
                    error = true;
                    List<SlideLostVO> blockLostVOS = new ArrayList<>();
                    SlideLostVO blockLostVO;
                    for (BlockDto blockDto : blockDTOs) {
                        blockLostVO = new SlideLostVO();
                        blockLostVO.setId(blockDto.getId());
                        blockLostVO.setBlockSerialNumber(blockDto.getSerialNumber());
                        blockLostVO.setBlockSubId(blockDto.getSubId());
                        blockLostVO.setLastOperateTime(blockDto.getUpdateTime());
                        UserSimpleDto user = userApplication.getUserSimpleInfoById(blockDto.getUpdateBy());
                        if (user != null) {
                            UserSimpleVO userSimpleVO = new UserSimpleVO();
                            BeanUtils.copyProperties(user, userSimpleVO);
                            blockLostVO.setLastOperator(userSimpleVO);
                        }
                        blockLostVO.setPathNo(pathNo);
                        blockLostVO.setStatus(blockDto.getStatus());
                        blockLostVO.setStatusDesc(blockDto.getStatusName());
                        blockLostVOS.add(blockLostVO);
                    }
                    slideConfirmErrorVO.setLostBlocks(blockLostVOS);
                }
                long prepareConfirmCount = blockApplication.countSlideConfirmByPathId(pathId);
                if (prepareConfirmCount != slideIds.size()) {//玻片有缺失
                    List<Long> slideConfirmIds = blockApplication.getSlideConfirmIdsByPathId(pathId);
                    slideConfirmErrorVO.setSlidePrepareConfirmCount(slideConfirmIds.size());
                    error = true;
                    if (slideConfirmIds.removeAll(slideIds)) {
                        slideConfirmErrorVO.setLostSlideCount(slideConfirmIds.size());
                        List<SlideLostVO> slideLostVOs = new ArrayList<>();
                        SlideLostVO slideLostVO;
                        for (long id : slideConfirmIds) {
                            slideLostVO = new SlideLostVO();
                            BlockDto slideInfo = blockApplication.getBlockById(id);
                            Long parentId = slideInfo.getParentId();
                            BlockDto blockInfo = blockApplication.getBlockById(parentId);
                            slideLostVO.setBlockSerialNumber(blockInfo.getSerialNumber());
                            slideLostVO.setBlockSubId(blockInfo.getSubId());
                            slideLostVO.setId(slideInfo.getId());
                            slideLostVO.setSerialNumber(slideInfo.getSerialNumber());
                            slideLostVO.setSubId(slideInfo.getSubId());
                            slideLostVO.setStatus(slideInfo.getStatus());
                            slideLostVO.setStatusDesc(PathologyStatus.valueOf(slideInfo.getStatus()).toString());
                            slideLostVO.setLastOperateTime(slideInfo.getUpdateTime());
                            UserSimpleDto user = userApplication.getUserSimpleInfoById(slideInfo.getUpdateBy());
                            UserSimpleVO userVO = new UserSimpleVO();
                            BeanUtils.copyProperties(user, userVO);
                            slideLostVO.setLastOperator(userVO);
                            slideLostVOs.add(slideLostVO);
                        }
                        slideConfirmErrorVO.setLostSlides(slideLostVOs);
                    }
                }
                List<SlideLostVO> lostSlides = slideConfirmErrorVO.getLostSlides();
                List<SlideLostVO> lostBlocks = slideConfirmErrorVO.getLostBlocks();
                if (CollectionUtils.isNotEmpty(lostSlides) || CollectionUtils.isNotEmpty(lostBlocks)) {
                    slideConfirmErrorVOs.add(slideConfirmErrorVO);
                }
            }
        }
        if (error) {
            return new ResponseVO(slideConfirmErrorVOs);
        }

        if (!error) {//无异常情况
            List<Long> slideIds = new ArrayList<>();
            for (ProductionSaveVO productionSaveVO : productionSaveVOs) {
                List<Long> ids = productionSaveVO.getSlideIds();
                slideIds.addAll(ids);
            }
            blockApplication.slideConfirm(slideIds);
        }
        return new ResponseVO();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseVO abnormalHandle(List<AbnormalHandleVO> abnormalVO) throws BuzException {
        if (CollectionUtils.isEmpty(abnormalVO)) {
            throw new BuzException(BuzExceptionCode.ErrorParam);
        }
        for (AbnormalHandleVO abnormalHandleVO : abnormalVO) {
            Integer handle = abnormalHandleVO.getHandle();
            String note = abnormalHandleVO.getNote();
            Long abnormalId = abnormalHandleVO.getAbnormalId();
            if (abnormalId == null || handle == null) {
                throw new BuzException(BuzExceptionCode.ErrorParam);
            }
            blockApplication.slideAbnormalHandle(abnormalId, handle, note, NotificationSource.completionConfirm.toCode());
        }
        return new ResponseVO();
    }

    @Override
    public ResponseVO scanResult(@Multipart(value = "blocks", required = false) String blocks,
                                 @Multipart(value = "file", required = false) Attachment file) throws BuzException, IOException {
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

            log.info("Batch scans param: blocks=>" + blocks + "\n" +
                    "file=>[name=" + file.getContentDisposition().getFilename() +
                    ", size=" + file.getDataHandler().getInputStream().available() + "]");
            if (file.getDataHandler().getInputStream().available() > 0) {
                String saveName = UUID.randomUUID().toString().replace("-", "") + file.getContentDisposition().getFilename().substring(file.getContentDisposition().getFilename().lastIndexOf("."));
                path = FileType.ScanImage.toString() + File.separator + ScanLocation.Production.toString() + File.separator + formatDate + File.separator + saveName;
                File f = new File(Config.nfsMntRw + path);
                log.debug("file path:" + f.getAbsolutePath());
                FileUtils.copyInputStreamToFile(file.getDataHandler().getInputStream(), f);
            } else {
                log.warn("Batch scan upload Image size is 0");
            }
            if (blocks != null) {
                blocks = URLDecoder.decode(blocks, "UTF-8");
            }
            BlockScanImageDto image = new BlockScanImageDto();
            image.setScanLocation(ScanLocation.Production.toCode());
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
        Integer location = ScanLocation.Production.toCode();
        List<BlockScanDto> blockScans = blockApplication.getBlockScansByLocation(location);
        BlockScanImageDto image = blockApplication.selectNewestImageByLocation(location);
        Map<String, Object> data = new HashMap<>();
        data.put("blocks", blockScans);
        data.put("image", image);
        return new ResponseVO(data);
    }

    @Override
    public ResponseVO score(long slideId, BlockScoreVO scoreVO) throws BuzException {
        if (scoreVO == null) {
            throw new BuzException(BuzExceptionCode.ErrorParam);
        }
        BlockDto blockDto = blockApplication.getBlockById(slideId);
        if(blockDto == null){
            throw new BuzException(BuzExceptionCode.ErrorParam);
        }
        Long blockId = blockDto.getParentId();
        if(blockId == null){
            throw new BuzException(BuzExceptionCode.ErrorParam);
        }
        if (!blockDto.getStatus().equals(PathologyStatus.PrepareCompletionDistribute.toCode())) {
            throw new BuzException(BuzExceptionCode.StatusNotMatch);
        }
        BlockScoreDto blockScoreDto = new BlockScoreDto();
        blockScoreDto.setBlockId(slideId);
        blockScoreDto.setParentId(blockId);
        blockScoreDto.setAverage(scoreVO.getAverage());
        blockScoreDto.setGrossing(scoreVO.getGrossing());
        blockScoreDto.setDehydrate(scoreVO.getDehydrate());
        blockScoreDto.setEmbedding(scoreVO.getEmbedding());
        blockScoreDto.setSealing(scoreVO.getSealing());
        blockScoreDto.setSectioning(scoreVO.getSectioning());
        blockScoreDto.setStaining(scoreVO.getStaining());
        blockScoreDto.setNote(scoreVO.getNote());
        blockScoreDto.setType(BlockScoreType.Production.toCode());
        blockScoreDto.setUpdateBy(UserContext.getLoginUserID());
        blockScoreApplication.updateBlockScore(blockScoreDto);
        return new ResponseVO();
    }

    @Override
    public ResponseVO getScore(String slideNo,String slideSubId) throws BuzException {
        if(StringUtils.isBlank(slideNo) || StringUtils.isBlank(slideSubId)){
            throw new BuzException(BuzExceptionCode.ErrorParam);
        }
        SlideDto slideDto = blockApplication.getSlideBySlideNoAndSubId(slideNo, slideSubId);
        if(slideDto == null){
            throw new BuzException(BuzExceptionCode.RecordNotExists);
        }
        if (!slideDto.getStatus().equals(PathologyStatus.PrepareCompletionDistribute.toCode())) {
            throw new BuzException(BuzExceptionCode.StatusNotMatch);
        }
        return new ResponseVO(blockScoreApplication.getBlockScoreBySlideIdAndType(slideDto.getId(),BlockScoreType.Production.toCode()));
    }

}
