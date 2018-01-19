package com.lifetech.dhap.pathcloud.tracking.api.impl;

import com.lifetech.dhap.pathcloud.application.application.PathologyApplication;
import com.lifetech.dhap.pathcloud.application.application.dto.PathologyDto;
import com.lifetech.dhap.pathcloud.common.constant.Config;
import com.lifetech.dhap.pathcloud.common.data.PathologyStatus;
import com.lifetech.dhap.pathcloud.common.exception.BuzException;
import com.lifetech.dhap.pathcloud.common.exception.BuzExceptionCode;
import com.lifetech.dhap.pathcloud.common.response.ResponseVO;
import com.lifetech.dhap.pathcloud.security.UserContext;
import com.lifetech.dhap.pathcloud.setting.application.ParamSettingApplication;
import com.lifetech.dhap.pathcloud.setting.application.infrastructure.code.ParamKey;
import com.lifetech.dhap.pathcloud.tracking.api.SealingApi;
import com.lifetech.dhap.pathcloud.tracking.api.vo.DyeConfirmVO;
import com.lifetech.dhap.pathcloud.tracking.api.vo.HintVO;
import com.lifetech.dhap.pathcloud.tracking.api.vo.SealingInfoVO;
import com.lifetech.dhap.pathcloud.tracking.application.BlockApplication;
import com.lifetech.dhap.pathcloud.tracking.application.SlideApplication;
import com.lifetech.dhap.pathcloud.tracking.application.TrackingApplication;
import com.lifetech.dhap.pathcloud.tracking.application.condition.SlideCondition;
import com.lifetech.dhap.pathcloud.tracking.application.condition.TrackingCondition;
import com.lifetech.dhap.pathcloud.tracking.application.dto.BlockDto;
import com.lifetech.dhap.pathcloud.tracking.application.dto.SlideDto;
import com.lifetech.dhap.pathcloud.tracking.application.dto.SlideLostDto;
import com.lifetech.dhap.pathcloud.tracking.application.dto.TrackingDto;
import com.lifetech.dhap.pathcloud.tracking.application.infrastructure.code.TrackingOperation;
import com.lifetech.dhap.pathcloud.user.api.vo.UserSimpleVO;
import com.lifetech.dhap.pathcloud.user.application.dto.UserSimpleDto;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

/**
 * Created by LiuMei on 2017-08-31.
 */
@Service("sealingApi")
public class SealingApiImpl implements SealingApi {

    @Autowired
    private PathologyApplication pathologyApplication;

    @Autowired
    private BlockApplication blockApplication;

    @Autowired
    private TrackingApplication trackingApplication;

    @Autowired
    private ParamSettingApplication paramSettingApplication;

    @Autowired
    private SlideApplication slideApplication;

    @Override
    public ResponseVO getSlideBySerialNumber(String serialNumber) throws BuzException {
        if (StringUtils.isBlank(serialNumber) || serialNumber.length() < 9) {
            throw new BuzException(BuzExceptionCode.ErrorParam);
        }
        String pathNo = serialNumber.substring(0, 9);
        PathologyDto pathologyDto = pathologyApplication.getPathologyBySerialNumber(pathNo);
        if (pathologyDto == null) {
            throw new BuzException(BuzExceptionCode.RecordNotExists);
        }

        Object result = null;
        if (serialNumber.length() == 9) {//病理号
            SlideCondition condition = new SlideCondition();
            condition.setPathId(pathologyDto.getId());
            condition.setSize(Integer.MAX_VALUE);
            condition.setStatus(PathologyStatus.PrepareMounting.toCode());
            result = slideDtoListToMountingInfoVO(blockApplication.getSlideByCondition(condition));
            List<SealingInfoVO> data = (List<SealingInfoVO>) result;
            if (data == null || data.size() <= 0) {
                Integer status = pathologyDto.getStatus();
                if (!status.equals(PathologyStatus.PrepareMounting.toCode())) {
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
                SlideCondition condition = new SlideCondition();
                condition.setBlockId(blockDto.getId());
                condition.setStatus(PathologyStatus.PrepareMounting.toCode());
                result = slideDtoListToMountingInfoVO(blockApplication.getSlideByCondition(condition));
                List<SealingInfoVO> data = (List<SealingInfoVO>) result;
                if (data == null || data.size() <= 0) {
                    Integer blockStatus = blockDto.getStatus();
                    if (!blockStatus.equals(PathologyStatus.PrepareArchiving.toCode())) {
                        HintVO hintVO = new HintVO();
                        hintVO.setPathNo(split[0]);
                        hintVO.setBlockSubNo(split[1]);
                        hintVO.setStatusDesc(PathologyStatus.getNameByCode(blockStatus));
                        hintVO.setStatus(blockStatus);
                        result = hintVO;
                    }
                }
            } else if (split.length == 3) {//病理号-蜡块号-玻片号
                SlideDto slideDto = blockApplication.getSlideBySlideNoAndSubId(serialNumber, split[2]);
                if (slideDto == null) {
                    throw new BuzException(BuzExceptionCode.RecordNotExists);
                }
                Integer status = slideDto.getStatus();
                if (status > PathologyStatus.PrepareMounting.toCode()) {//已封片
                    TrackingCondition trackingCondition = new TrackingCondition();
                    trackingCondition.setBlockId(slideDto.getId());
                    trackingCondition.setOperation(TrackingOperation.mounting.toCode());
                    TrackingDto trackingDto = trackingApplication.getTrackingByCondition(trackingCondition);
                    if (trackingDto != null) {
                        HintVO hintVO = new HintVO();
                        hintVO.setPathNo(split[0]);
                        hintVO.setBlockSubNo(split[1]);
                        hintVO.setStatus(status);
                        hintVO.setOperator(trackingDto.getOperatorId());
                        hintVO.setStatusDesc(PathologyStatus.getNameByCode(status));
                        hintVO.setOperatorDesc(trackingDto.getOperatorName());
                        hintVO.setOperatorTime(trackingDto.getOperationTime());
                        result = hintVO;
                    }
                } else if (status.equals(PathologyStatus.PrepareMounting.toCode())) {
                    List<SealingInfoVO> sealingInfoVOS = new ArrayList<>();
                    SealingInfoVO sealingInfoVO = new SealingInfoVO();
                    BeanUtils.copyProperties(slideDto, sealingInfoVO);
                    UserSimpleDto grossingUser = slideDto.getGrossingUser();
                    if (grossingUser != null) {
                        UserSimpleVO user = new UserSimpleVO();
                        BeanUtils.copyProperties(grossingUser, user);
                        sealingInfoVO.setGrossingUser(user);
                    }
                    sealingInfoVOS.add(sealingInfoVO);
                    result = sealingInfoVOS;
                }
            } else {
                throw new BuzException(BuzExceptionCode.ErrorParam);
            }
        }
        return new ResponseVO(result);
    }

    @Override
    public ResponseVO mountingComplete(DyeConfirmVO dyeConfirmVO) throws BuzException {
        List<Long> slideIds = dyeConfirmVO.getSlideIds();
        Long instrumentId = dyeConfirmVO.getInstrumentId();
        if (CollectionUtils.isEmpty(slideIds) || instrumentId == null) {
            throw new BuzException(BuzExceptionCode.ErrorParam);
        }
        Integer decalcify = paramSettingApplication.getCodeByKeyAndName(ParamKey.BlockBiaoshi.toString(), "脱钙");
        Boolean ignore = dyeConfirmVO.getIgnore();
        if (ignore != null && ignore) {
            blockApplication.mountingConfirm(slideIds, instrumentId);
        } else {
            Map<Long, HashSet<Long>> pathSlide = slideApplication.slideGroupByPathNo(slideIds, decalcify);
            SlideCondition slideCondition = new SlideCondition();
            slideCondition.setStart(0);
            slideCondition.setSize(Integer.MAX_VALUE);
            slideCondition.setStatus(PathologyStatus.PrepareMounting.toCode());
            slideCondition.setAgainstBiaoshi(decalcify);
            List<SlideLostDto> slideLostDtoList = slideApplication.slideErrorCheck(slideCondition, pathSlide);
            if (CollectionUtils.isNotEmpty(slideLostDtoList)) {
                return new ResponseVO(slideLostDtoList);
            } else {
                blockApplication.mountingConfirm(slideIds, instrumentId);
            }
        }
        return new ResponseVO();
    }

    @Override
    public ResponseVO batch(DyeConfirmVO dyeConfirmVO) throws BuzException {
        Long instrumentId = dyeConfirmVO.getInstrumentId();
        Boolean currentUser = dyeConfirmVO.getCurrentUser();
        if(instrumentId == null || currentUser == null){
            throw new BuzException(BuzExceptionCode.ErrorParam);
        }
        SlideCondition condition = new SlideCondition();
        condition.setStatus(PathologyStatus.PrepareMounting.toCode());
        if(currentUser){
            condition.setSectionOperator(UserContext.getLoginUserID());
        }
        condition.setSize(null);
        condition.setStart(null);
        List<Long> slideIdList = blockApplication.getSlideIdByCondition(condition);
        if(CollectionUtils.isNotEmpty(slideIdList)){
            blockApplication.mountingConfirm(slideIdList, instrumentId);
        }
        return new ResponseVO();
    }

    private List<SealingInfoVO> slideDtoListToMountingInfoVO(List<SlideDto> slideDtoList) {
        List<SealingInfoVO> sealingInfoVOS = null;
        if (CollectionUtils.isNotEmpty(slideDtoList)) {
            sealingInfoVOS = new ArrayList<>();
            SealingInfoVO sealingInfoVO;
            for (SlideDto slideDto : slideDtoList) {
                if (slideDto.getStatus().equals(PathologyStatus.PrepareMounting.toCode())) {
                    sealingInfoVO = new SealingInfoVO();
                    BeanUtils.copyProperties(slideDto, sealingInfoVO);
                    UserSimpleDto grossingUser = slideDto.getGrossingUser();
                    if (grossingUser != null) {
                        UserSimpleVO user = new UserSimpleVO();
                        BeanUtils.copyProperties(grossingUser, user);
                        sealingInfoVO.setGrossingUser(user);
                    }
                    sealingInfoVOS.add(sealingInfoVO);
                }
            }
        }
        return sealingInfoVOS;
    }
}
