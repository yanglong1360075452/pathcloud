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
import com.lifetech.dhap.pathcloud.setting.application.ParamSettingApplication;
import com.lifetech.dhap.pathcloud.setting.application.infrastructure.code.ParamKey;
import com.lifetech.dhap.pathcloud.tracking.api.DyeApi;
import com.lifetech.dhap.pathcloud.tracking.api.vo.DyeConfirmVO;
import com.lifetech.dhap.pathcloud.tracking.api.vo.DyeInfoVO;
import com.lifetech.dhap.pathcloud.tracking.api.vo.HintVO;
import com.lifetech.dhap.pathcloud.tracking.api.vo.SummaryVO;
import com.lifetech.dhap.pathcloud.tracking.application.BlockApplication;
import com.lifetech.dhap.pathcloud.tracking.application.SlideApplication;
import com.lifetech.dhap.pathcloud.tracking.application.TrackingApplication;
import com.lifetech.dhap.pathcloud.tracking.application.condition.EmbedsCon;
import com.lifetech.dhap.pathcloud.tracking.application.condition.SlideCondition;
import com.lifetech.dhap.pathcloud.tracking.application.condition.TrackingCondition;
import com.lifetech.dhap.pathcloud.tracking.application.dto.*;
import com.lifetech.dhap.pathcloud.tracking.application.infrastructure.code.TrackingOperation;
import com.lifetech.dhap.pathcloud.tracking.infrastructure.code.DyeSortEnum;
import com.lifetech.dhap.pathcloud.user.api.vo.UserSimpleVO;
import com.lifetech.dhap.pathcloud.user.application.dto.UserDto;
import com.lifetech.dhap.pathcloud.user.application.dto.UserSimpleDto;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.ws.rs.DefaultValue;
import java.util.*;

/**
 * Created by LiuMei on 2017-03-22.
 */
@Service("dyeApi")
public class DyeApiImpl implements DyeApi {

    @Autowired
    private BlockApplication blockApplication;

    @Autowired
    private PathologyApplication pathologyApplication;

    @Autowired
    private ParamSettingApplication paramSettingApplication;

    @Autowired
    private TrackingApplication trackingApplication;

    @Autowired
    private SlideApplication slideApplication;

    @Override
    public ResponseVO getSlideBySerialNumber(String serialNumber) throws BuzException {

        if (serialNumber == null || "".equals(serialNumber.trim()) || serialNumber.length() < 9) {
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
            condition.setStatus(PathologyStatus.PrepareDye.toCode());
            result = slideDtoListToDyeInfoVO(blockApplication.getSlideByCondition(condition));
            List<DyeInfoVO> data = (List<DyeInfoVO>) result;
            if (CollectionUtils.isEmpty(data)) {
                Integer status = pathologyDto.getStatus();
                if (!status.equals(PathologyStatus.PrepareDye.toCode())) {
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
                condition.setStatus(PathologyStatus.PrepareDye.toCode());
                result = slideDtoListToDyeInfoVO(blockApplication.getSlideByCondition(condition));
                List<DyeInfoVO> data = (List<DyeInfoVO>) result;
                if (CollectionUtils.isEmpty(data)) {
                    Integer blockStatus = blockDto.getStatus();
                    if (!blockStatus.equals(PathologyStatus.PrepareArchiving.toCode())) {
                        HintVO hintVO = new HintVO();
                        hintVO.setPathNo(split[0]);
                        hintVO.setBlockSubNo(split[1]);
                        hintVO.setStatus(blockStatus);
                        hintVO.setStatusDesc(PathologyStatus.getNameByCode(blockStatus));
                        result = hintVO;
                    }
                }
            } else if (split.length == 3) {//病理号-蜡块号-玻片号
                SlideDto slideDto = blockApplication.getSlideBySlideNoAndSubId(serialNumber, split[2]);
                if (slideDto == null) {
                    throw new BuzException(BuzExceptionCode.RecordNotExists);
                }
                Integer status = slideDto.getStatus();
                if (status > PathologyStatus.PrepareDye.toCode()) {//已染色
                    TrackingCondition trackingCondition = new TrackingCondition();
                    trackingCondition.setBlockId(slideDto.getId());
                    trackingCondition.setOperation(TrackingOperation.dye.toCode());
                    TrackingDto trackingDto = trackingApplication.getTrackingByCondition(trackingCondition);
                    if (trackingDto != null) {
                        HintVO hintVO = new HintVO();
                        hintVO.setPathNo(split[0]);
                        hintVO.setBlockSubNo(split[1]);
                        hintVO.setStatus(status);
                        hintVO.setStatusDesc(PathologyStatus.getNameByCode(status));
                        hintVO.setOperator(trackingDto.getOperatorId());
                        hintVO.setOperatorDesc(trackingDto.getOperatorName());
                        hintVO.setOperatorTime(trackingDto.getOperationTime());
                        result = hintVO;
                    }
                } else if (status.equals(PathologyStatus.PrepareDye.toCode())) {
                    List<DyeInfoVO> dyeInfoVOS = new ArrayList<>();
                    DyeInfoVO dyeInfoVO = new DyeInfoVO();
                    BeanUtils.copyProperties(slideDto, dyeInfoVO);
                    UserSimpleDto grossingUser = slideDto.getGrossingUser();
                    if (grossingUser != null) {
                        UserSimpleVO user = new UserSimpleVO();
                        BeanUtils.copyProperties(grossingUser, user);
                        dyeInfoVO.setGrossingUser(user);
                    }
                    dyeInfoVOS.add(dyeInfoVO);
                    result = dyeInfoVOS;
                }
            } else {
                throw new BuzException(BuzExceptionCode.ErrorParam);
            }
        }
        return new ResponseVO(result);
    }

    private List<DyeInfoVO> slideDtoListToDyeInfoVO(List<SlideDto> slideDtoList) {
        List<DyeInfoVO> dyeInfoVOS = null;
        if (CollectionUtils.isNotEmpty(slideDtoList)) {
            dyeInfoVOS = new ArrayList<>();
            DyeInfoVO dyeInfoVO;
            for (SlideDto slideDto : slideDtoList) {
                if (slideDto.getStatus().equals(PathologyStatus.PrepareDye.toCode())) {
                    dyeInfoVO = new DyeInfoVO();
                    BeanUtils.copyProperties(slideDto, dyeInfoVO);
                    UserSimpleDto grossingUser = slideDto.getGrossingUser();
                    if (grossingUser != null) {
                        UserSimpleVO user = new UserSimpleVO();
                        BeanUtils.copyProperties(grossingUser, user);
                        dyeInfoVO.setGrossingUser(user);
                    }
                    dyeInfoVOS.add(dyeInfoVO);
                }
            }
        }
        return dyeInfoVOS;
    }

    @Override
    public ResponseVO dyeConfirm(DyeConfirmVO dyeConfirmVO) throws BuzException {
        List<Long> slideIds = dyeConfirmVO.getSlideIds();
        Long instrumentId = dyeConfirmVO.getInstrumentId();
        if (CollectionUtils.isEmpty(slideIds) || instrumentId == null) {
            throw new BuzException(BuzExceptionCode.ErrorParam);
        }
        Integer decalcify = paramSettingApplication.getCodeByKeyAndName(ParamKey.BlockBiaoshi.toString(), "脱钙");
        Boolean ignore = dyeConfirmVO.getIgnore();
        if (ignore != null && ignore) {
            blockApplication.dyeConfirm(slideIds, instrumentId);
        } else {
            Map<Long, HashSet<Long>> pathSlide = slideApplication.slideGroupByPathNo(slideIds, decalcify);
            SlideCondition slideCondition = new SlideCondition();
            slideCondition.setStart(0);
            slideCondition.setSize(Integer.MAX_VALUE);
            slideCondition.setStatus(PathologyStatus.PrepareDye.toCode());
            slideCondition.setAgainstBiaoshi(decalcify);
            List<SlideLostDto> slideLostDtoList = slideApplication.slideErrorCheck(slideCondition, pathSlide);
            if (CollectionUtils.isNotEmpty(slideLostDtoList)) {
                return new ResponseVO(slideLostDtoList);
            } else {
                blockApplication.dyeConfirm(slideIds, instrumentId);
            }
        }
        return new ResponseVO();
    }

    @Override
    public ResponseVO batchDye(DyeConfirmVO dyeConfirmVO) throws BuzException {
        Long instrumentId = dyeConfirmVO.getInstrumentId();
        Boolean currentUser = dyeConfirmVO.getCurrentUser();
        if(instrumentId == null || currentUser == null){
            throw new BuzException(BuzExceptionCode.ErrorParam);
        }
        SlideCondition condition = new SlideCondition();
        condition.setStatus(PathologyStatus.PrepareDye.toCode());
        if(currentUser){
            condition.setSectionOperator(UserContext.getLoginUserID());
        }
        condition.setSize(null);
        condition.setStart(null);
        List<Long> slideIdList = blockApplication.getSlideIdByCondition(condition);
        if(CollectionUtils.isNotEmpty(slideIdList)){
            blockApplication.dyeConfirm(slideIdList, instrumentId);
        }
        return new ResponseVO();
    }

    @Override
    public ResponseVO getDyeQuery(@DefaultValue("1") Integer page, @DefaultValue("10") Integer length,
                                  String filter, Long startTime, Long endTime, Integer status,
                                  Long operatorId, Long mountingId,
                                  Long dyeInstrumentId,
                                  Long mountingInstrumentId, Integer order, String sort) throws BuzException {

        if (page <= 0 || length < 1) {
            throw new BuzException(BuzExceptionCode.ErrorParam);
        }
        EmbedsCon con = new EmbedsCon();
        con.setStart((page - 1) * length);
        con.setSize(length);
        if (sort == null) {
            sort = "ASC";
        }
        con.setOrder(DyeSortEnum.valueOf(order).toString() + " " + sort);

        if (filter == null || "".equals(filter.trim())) {
            con.setFilter(null);
        } else {
            if (filter.length() == 9) { //病理号
                con.setFilter(StringUtil.escapeExprSpecialWord(filter));
            } else {
                String[] split = filter.split(Config.serialNumberSeparator);
                if (split.length == 1) {
                    con.setFilter(split[0]);
                } else if (split.length == 2) { //病理号加蜡块号
                    con.setFilter(split[0]);
                    con.setStartPathNo(split[1]);
                } else if (split.length == 3) { //病理号+蜡块号+玻片号
                    con.setFilter(split[0]);
                    con.setStartPathNo(split[1]);
                    con.setEndPathNo(split[2]);
                } else {
                    throw new BuzException(BuzExceptionCode.ErrorParam);
                }
            }
        }

        if (startTime != null) {
            con.setStartTime(new Date(startTime));
        }
        if (endTime != null) {
            con.setEndTime(new Date(endTime));
        }
        con.setStatus(status);
        con.setOperatorId(operatorId);
        con.setMountingId(mountingId);
        con.setDyeInstrumentId(dyeInstrumentId);
        con.setMountingInstrumentId(mountingInstrumentId);

        List<InfoQueryDto> dyeQuery = blockApplication.getDyeQuery(con);
        Long total = blockApplication.getDyeQueryTotal(con);

        return new PageDataVO(page, length, total, dyeQuery);
    }

    @Override
    public ResponseVO getDyePerson() throws BuzException {
        List<UserDto> user = blockApplication.getDyePerson();
        return new ResponseVO(user);
    }

    @Override
    public ResponseVO getSummary(int status) throws BuzException {
        if (!PathologyStatus.PrepareDye.toCode().equals(status) && !PathologyStatus.PrepareMounting.toCode().equals(status)) {
            throw new BuzException(BuzExceptionCode.ErrorParam);
        }
        SlideCondition condition = new SlideCondition();
        condition.setStatus(status);
        Long totalCount = blockApplication.countSlideByCondition(condition);
        condition.setSectionOperator(UserContext.getLoginUserID());
        Long currentUserCount = blockApplication.countSlideByCondition(condition);
        SummaryVO summaryVO = new SummaryVO();
        summaryVO.setCurrentUserCount(currentUserCount);
        summaryVO.setTotalCount(totalCount);
        return new ResponseVO(summaryVO);
    }

    @Override
    public ResponseVO getSlideList(int status, boolean currentUser, Integer page, Integer length, Integer order, String sort) throws BuzException {
        if (!PathologyStatus.PrepareDye.toCode().equals(status) && !PathologyStatus.PrepareMounting.toCode().equals(status)) {
            throw new BuzException(BuzExceptionCode.ErrorParam);
        }
        SlideCondition condition = new SlideCondition();
        condition.setStatus(status);
        if (currentUser) {
            condition.setSectionOperator(UserContext.getLoginUserID());
        }
        condition.setStart((page - 1) * length);
        condition.setSize(length);
        List<SlideDto> slideDtoList = blockApplication.getSlideByCondition(condition);
        return new PageDataVO(page,length,null,slideDtoList);
    }


}
