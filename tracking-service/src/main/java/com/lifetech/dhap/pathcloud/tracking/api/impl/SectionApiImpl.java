package com.lifetech.dhap.pathcloud.tracking.api.impl;

import com.lifetech.dhap.pathcloud.application.application.ApplicationIhcApplication;
import com.lifetech.dhap.pathcloud.application.application.dto.IhcBlockDto;
import com.lifetech.dhap.pathcloud.common.constant.Config;
import com.lifetech.dhap.pathcloud.common.data.PathologyStatus;
import com.lifetech.dhap.pathcloud.common.exception.BuzException;
import com.lifetech.dhap.pathcloud.common.exception.BuzExceptionCode;
import com.lifetech.dhap.pathcloud.common.response.PageDataVO;
import com.lifetech.dhap.pathcloud.common.response.ResponseVO;
import com.lifetech.dhap.pathcloud.common.utils.StringUtil;
import com.lifetech.dhap.pathcloud.setting.application.ParamSettingApplication;
import com.lifetech.dhap.pathcloud.tracking.api.SectionApi;
import com.lifetech.dhap.pathcloud.tracking.api.vo.*;
import com.lifetech.dhap.pathcloud.tracking.application.BlockApplication;
import com.lifetech.dhap.pathcloud.tracking.application.TrackingApplication;
import com.lifetech.dhap.pathcloud.tracking.application.condition.*;
import com.lifetech.dhap.pathcloud.tracking.application.dto.*;
import com.lifetech.dhap.pathcloud.tracking.application.infrastructure.code.TrackingOperation;
import com.lifetech.dhap.pathcloud.tracking.infrastructure.code.SectionSortEnum;
import com.lifetech.dhap.pathcloud.user.api.vo.UserSimpleVO;
import com.lifetech.dhap.pathcloud.user.application.UserApplication;
import com.lifetech.dhap.pathcloud.user.application.dto.UserDto;
import com.lifetech.dhap.pathcloud.user.application.dto.UserSimpleDto;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.DefaultValue;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

/**
 * Created by LiuMei on 2016-12-21.
 */
@Component("sectionApi")
public class SectionApiImpl implements SectionApi {

    @Autowired
    private BlockApplication blockApplication;

    @Autowired
    private TrackingApplication trackingApplication;

    @Autowired
    private UserApplication userApplication;

    @Autowired
    private ApplicationIhcApplication applicationIhcApplication;

    @Autowired
    private ParamSettingApplication paramSettingApplication;

    @Override
    public ResponseVO getBlockBySerialNumber(String blockSerialNumber, Long prePathId, String slideSubId) throws BuzException {
        if (StringUtils.isBlank(blockSerialNumber)) {
            throw new BuzException(BuzExceptionCode.ErrorParam);
        }

        if (prePathId != null) {

            List<Long> blockIds = blockApplication.getNormalBlocksIdByPathId(prePathId);
            List<Long> hadSectionBlockIds = blockApplication.getHadSectionBlocksIdByPathId(prePathId);
            //上一个病理号有蜡块号未切片
            if (blockIds.size() > hadSectionBlockIds.size()) {
                blockIds.removeAll(hadSectionBlockIds);
                List<String> blockNos = new ArrayList<>();
                for (long blockId : blockIds) {
                    blockNos.add(blockApplication.getBlockById(blockId).getSubId());
                }
                return new ResponseVO(blockNos);
            }
        }
        BlockExtendDto dto = blockApplication.getBlockExtendBySerialNumber(blockSerialNumber);
        if (dto == null) {
            throw new BuzException(BuzExceptionCode.RecordNotExists);
        }
        SectionVO sectionVO = new SectionVO();
        if (dto != null) {
            BeanUtils.copyProperties(dto, sectionVO);
            UserSimpleDto grossingDoctor = dto.getGrossingDoctor();
            if (grossingDoctor != null) {
                UserSimpleVO userSimpleVO = new UserSimpleVO();
                BeanUtils.copyProperties(grossingDoctor, userSimpleVO);
                sectionVO.setGrossingDoctor(userSimpleVO);
            }
        }

        //玻片号
        if (StringUtils.isNotBlank(slideSubId)) {
            SlideDto slideDto = blockApplication.getSlideBySlideNoAndSubId(blockSerialNumber + slideSubId, slideSubId);
            if (slideDto == null) {
                throw new BuzException(BuzExceptionCode.RecordNotExists);
            }
            Long slideId = slideDto.getId();
            Integer slideDyeType = slideDto.getSpecialDye();
            //特检
            if (slideDyeType >= 1) {
                Long applyId = slideDto.getApplyId();
                TrackingDto trackingDto = trackingApplication.getTrackingById(applyId);
                IhcBlockDto ihcBlockDto = applicationIhcApplication.getIhcBlockById(trackingDto.getInstrumentId());
                List<DyeApplyVO> dyeApplyVOS = new ArrayList<>();
                DyeApplyVO dyeApplyVO = new DyeApplyVO();
                List<SectionSlideVO> sectionSlideVOS = new ArrayList<>();
                SectionSlideVO sectionSlideVO = new SectionSlideVO();
                sectionSlideVO.setMarker(slideDto.getMarker());
                sectionSlideVO.setSlideId(slideId);
                sectionSlideVOS.add(sectionSlideVO);
                dyeApplyVO.setSectionSlides(sectionSlideVOS);
                dyeApplyVO.setNote(ihcBlockDto.getNote());
                Integer specialDye = ihcBlockDto.getSpecialDye();
                dyeApplyVO.setSpecialDye(specialDye);
                dyeApplyVO.setSpecialDyeDesc(paramSettingApplication.getSpecialDyeDesc(specialDye));
                UserSimpleDto userSimpleDto = userApplication.getUserSimpleInfoById(ihcBlockDto.getCreateBy());
                if (userSimpleDto != null) {
                    UserSimpleVO userSimpleVO = new UserSimpleVO();
                    BeanUtils.copyProperties(userSimpleDto, userSimpleVO);
                    dyeApplyVO.setApplicant(userSimpleVO);
                }
                sectionVO.setNumber(ihcBlockDto.getNumber());
                dyeApplyVO.setApplyId(applyId);
                dyeApplyVOS.add(dyeApplyVO);
                sectionVO.setDyeApply(dyeApplyVOS);
            } else if (slideDyeType == 0) {
                List<DyeApplyVO> dyeApplyVOS = new ArrayList<>();
                DyeApplyVO dyeApplyVO = new DyeApplyVO();
                dyeApplyVO.setSpecialDye(Config.dyeType);
                dyeApplyVO.setSpecialDyeDesc(paramSettingApplication.getSpecialDyeDesc(Config.dyeType));
                String dyeMarker = Config.dyeMarker;
                if (dyeMarker != null && dyeMarker.length() > 0) {
                    List<SectionSlideVO> sectionSlideVOS = new ArrayList<>();
                    SectionSlideVO sectionSlideVO = new SectionSlideVO();
                    sectionSlideVO.setMarker(dyeMarker);
                    sectionSlideVO.setSlideId(slideId);
                    sectionSlideVOS.add(sectionSlideVO);
                    dyeApplyVO.setSectionSlides(sectionSlideVOS);
                }
                dyeApplyVOS.add(dyeApplyVO);
                sectionVO.setDyeApply(dyeApplyVOS);
            }
        } else {
            TrackingDto finalTrackingDto = null;
            long blockId = dto.getBlockId();
            TrackingCondition trackingCondition = new TrackingCondition();
            trackingCondition.setBlockId(blockId);

            //申请染色
            if (sectionVO.getSpecialDye().equals(1)) {
                List<Long> ihcIds = applicationIhcApplication.getCurrentIhcIdsByBlockId(blockId);
                if (CollectionUtils.isNotEmpty(ihcIds)) {
                    List<DyeApplyVO> dyeApplyVOS = new ArrayList<>();
                    for (Long ihcId : ihcIds) {
                        if (ihcId != null) {
                            trackingCondition.setOperation(TrackingOperation.dyeConfirm.toCode());
                            trackingCondition.setInstrumentId(ihcId);
                            List<TrackingDto> trackingDTOs = trackingApplication.getTrackingsByCondition(trackingCondition);
                            if (CollectionUtils.isNotEmpty(trackingDTOs)) {
                                DyeApplyVO dyeApplyVO;
                                for (TrackingDto trackingDto : trackingDTOs) {
                                    dyeApplyVO = new DyeApplyVO();
                                    Long operatorId = trackingDto.getOperatorId();
                                    UserSimpleDto user = userApplication.getUserSimpleInfoById(operatorId);
                                    UserSimpleVO userVO = new UserSimpleVO();
                                    BeanUtils.copyProperties(user, userVO);
                                    dyeApplyVO.setApplicant(userVO);
                                    Long ihcBlockId = trackingDto.getSecOperatorId();
                                    if (ihcBlockId != null) {
                                        IhcBlockDto ihcBlockDto = applicationIhcApplication.getIhcBlockById(ihcBlockId);
                                        if (ihcBlockDto != null) {
                                            List<String> ihcMarker = ihcBlockDto.getIhcMarker();
                                            if (CollectionUtils.isNotEmpty(ihcMarker)) {
                                                List<SectionSlideVO> sectionSlideVOS = new ArrayList<>();
                                                SectionSlideVO sectionSlideVO;
                                                for (String marker : ihcMarker) {
                                                    sectionSlideVO = new SectionSlideVO();
                                                    sectionSlideVO.setMarker(marker);
                                                    BlockDto slideInfo = blockApplication.getSlideInfoByBlockIhcIdAndMarker(ihcBlockId, marker);
                                                    if (slideInfo != null) {
                                                        sectionSlideVO.setSlideId(slideInfo.getId());
                                                    }
                                                    sectionSlideVOS.add(sectionSlideVO);
                                                }
                                                dyeApplyVO.setSectionSlides(sectionSlideVOS);
                                            }
                                            dyeApplyVO.setNote(ihcBlockDto.getNote());
                                            Integer specialDye = ihcBlockDto.getSpecialDye();
                                            dyeApplyVO.setSpecialDye(specialDye);
                                            dyeApplyVO.setSpecialDyeDesc(paramSettingApplication.getSpecialDyeDesc(specialDye));
                                            sectionVO.setNumber(ihcBlockDto.getNumber());
                                            dyeApplyVO.setApplyId(trackingApplication.getBlockIhcTrackingIdByBlockIhcId(ihcBlockId));
                                        }
                                    }
                                    dyeApplyVOS.add(dyeApplyVO);
                                }
                            }
                        }
                    }
                    sectionVO.setDyeApply(dyeApplyVOS);
                }
            } else {
                trackingCondition.setOperation(TrackingOperation.applyDeepSection.toCode());
                TrackingDto deepSectionTracking = trackingApplication.getTrackingByCondition(trackingCondition);
                List<Long> slideList = blockApplication.getSlidesIdByBlockId(blockId);
                TrackingDto reSectionTracking = null;
                if (CollectionUtils.isNotEmpty(slideList)) {
                    //重切是记录在切片上的
                    trackingCondition.setBlockId(null);
                    trackingCondition.setBlockIdList(slideList);
                    trackingCondition.setOperation(TrackingOperation.applyReSection.toCode());
                    reSectionTracking = trackingApplication.getTrackingByCondition(trackingCondition);
                }
                if (deepSectionTracking != null || reSectionTracking != null) {
                    if (deepSectionTracking != null && reSectionTracking != null) {
                        if (deepSectionTracking.getOperationTime().getTime() > reSectionTracking.getOperationTime().getTime()) {
                            finalTrackingDto = deepSectionTracking;
                        } else {
                            finalTrackingDto = reSectionTracking;
                        }
                    } else if (deepSectionTracking == null && reSectionTracking != null) {
                        finalTrackingDto = reSectionTracking;
                    } else if (reSectionTracking == null && deepSectionTracking != null) {
                        finalTrackingDto = deepSectionTracking;
                    }
                    Long operatorId = finalTrackingDto.getOperatorId();
                    UserSimpleDto user = userApplication.getUserSimpleInfoById(operatorId);
                    UserSimpleVO userVO = new UserSimpleVO();
                    BeanUtils.copyProperties(user, userVO);
                    sectionVO.setSpecialSectionApplicant(userVO);
                    sectionVO.setSpecialSectionRemark(finalTrackingDto.getNote());
                    sectionVO.setSpecialSectionOperation(finalTrackingDto.getOperation());
                }
            }

            trackingCondition.setBlockId(dto.getPathologyId());
            trackingCondition.setOperation(TrackingOperation.applyReGrossing.toCode());
            TrackingDto trackingDto = trackingApplication.getTrackingByCondition(trackingCondition);
            finalTrackingDto = getLastTracking(trackingDto, finalTrackingDto);
            Long finalTrackingId = null;
            if (finalTrackingDto != null) {
                finalTrackingId = finalTrackingDto.getId();
            }
            //默认--未申请染色
            if (sectionVO.getSpecialDye().equals(0)) {
                List<DyeApplyVO> dyeApplyVOS = new ArrayList<>();
                DyeApplyVO dyeApplyVO = new DyeApplyVO();
                dyeApplyVO.setSpecialDye(Config.dyeType);
                dyeApplyVO.setSpecialDyeDesc(paramSettingApplication.getSpecialDyeDesc(Config.dyeType));
                String dyeMarker = Config.dyeMarker;
                if (dyeMarker != null && dyeMarker.length() > 0) {
                    List<SectionSlideVO> sectionSlideVOS = new ArrayList<>();
                    SectionSlideVO sectionSlideVO = new SectionSlideVO();
                    sectionSlideVO.setMarker(dyeMarker);
                    if (finalTrackingId != null) {
                        List<Long> slideId = blockApplication.getSlideIdByApplyId(finalTrackingId);
                        if (CollectionUtils.isNotEmpty(slideId)) {
                            sectionSlideVO.setSlideId(slideId.get(0));
                        }
                    } else {
                        BlockDto slide = blockApplication.getSlideInfoByBlockIdAndMarker(blockId, Config.dyeMarker);
                        if (slide != null) {
                            Integer status = slide.getStatus();
                            if (status.equals(PathologyStatus.PrepareSection.toCode())) {
                                sectionSlideVO.setSlideId(slide.getId());
                            }
                        }
                    }
                    sectionSlideVOS.add(sectionSlideVO);
                    dyeApplyVO.setSectionSlides(sectionSlideVOS);
                    dyeApplyVO.setApplyId(finalTrackingId);
                }
                dyeApplyVOS.add(dyeApplyVO);
                sectionVO.setDyeApply(dyeApplyVOS);
            }
        }

        long pathId = dto.getPathologyId();
        sectionVO.setPrepareSectionCount(blockApplication.countBlockByStatus(PathologyStatus.PrepareSection.toCode()));
        sectionVO.setPathBlockCount(blockApplication.getNormalBlocksIdByPathId(pathId).size());
        HadOperatedCondition condition = new HadOperatedCondition();
        condition.setPathId(pathId);
        condition.setStatus(PathologyStatus.PrepareSection.toCode());
        sectionVO.setPathSectionCount(blockApplication.countHadOperatedBlocks(condition));

        BlockCondition blockCondition = new BlockCondition();
        blockCondition.setPathId(pathId);
        blockCondition.setStatus(PathologyStatus.PrepareSection.toCode());
        List<BlockDto> blockDTOs = blockApplication.getBlockByCondition(blockCondition);
        if (CollectionUtils.isNotEmpty(blockDTOs)) {
            List<String> otherSection = new ArrayList<>();
            for (BlockDto blockDto1 : blockDTOs) {
                otherSection.add(blockDto1.getSubId());
            }
            sectionVO.setOtherSection(otherSection);
        }
        return new ResponseVO(sectionVO);
    }

    @Override
    public ResponseVO sectionsConfirm(List<NoteVO> noteVOS) throws BuzException {
        if (CollectionUtils.isEmpty(noteVOS)) {
            throw new BuzException(BuzExceptionCode.ErrorParam);
        }
        List<BlockExtendDto> blockExtendDTOs = new ArrayList<>();
        for (NoteVO noteVO : noteVOS) {
            long blockId = noteVO.getBlockId();
            if (noteVO.getSpecialDye() == null) {
                throw new BuzException(BuzExceptionCode.ErrorParam);
            }
            BlockDto blockDto = blockApplication.getBlockById(blockId);
            if (blockDto == null) {
                throw new BuzException(BuzExceptionCode.RecordNotExists);
            }
            if (!blockDto.getStatus().equals(PathologyStatus.PrepareSection.toCode())) {
                throw new BuzException(BuzExceptionCode.StatusNotMatch);
            }
            BlockExtendDto extendDto = new BlockExtendDto();
            extendDto.setBlockId(blockId);
            extendDto.setApplyId(noteVO.getApplyId());
            extendDto.setSlideId(noteVO.getSlideId());
            String note = noteVO.getNote();
            String noteType = noteVO.getNoteType();
            if (StringUtils.isNotBlank(noteType)) {
                extendDto.setSectionRemarkType(noteType);
            }
            if (StringUtils.isNotBlank(note)) {
                if (StringUtils.isBlank(noteType)) {
                    extendDto.setSectionRemarkType("其他");
                }
                extendDto.setSectionRemark(note);
            }
            extendDto.setSpecialDye(noteVO.getSpecialDye());
            extendDto.setSlideMarker(noteVO.getSlideMarker());
            extendDto.setNumber(noteVO.getNumber());
            blockExtendDTOs.add(extendDto);
        }
        blockApplication.sectionsConfirm(blockExtendDTOs);
        return new ResponseVO();
    }

    @Override
    public ResponseVO getBlockCount() throws BuzException {
        Date date = new Date(System.currentTimeMillis() / (1000 * 3600 * 24) * (1000 * 3600 * 24) - TimeZone.getDefault().getRawOffset());
        BlockCountVO blockCount = new BlockCountVO();
        HadOperatedCondition condition = new HadOperatedCondition();
        //蜡块总数
        blockCount.setBlockTotal(blockApplication.countHadOperatedBlocks(condition));
        condition.setStatus(PathologyStatus.PrepareSection.toCode());
        //已切片蜡块数
        blockCount.setHadOperated(blockApplication.countHadOperatedBlocks(condition));
        HadOperatedCondition toCondition = new HadOperatedCondition();
        toCondition.setStartTime(date);
        //今日蜡块总数
        blockCount.setTodayTotal(blockApplication.countHadOperatedBlocks(toCondition));
        TrackingCondition con = new TrackingCondition();
        con.setOperation(TrackingOperation.section.toCode());
        con.setOperationTime(date);
        //今日已切片蜡块数
        blockCount.setTodayOperated(trackingApplication.countTrackingByCondition(con));
        blockCount.setPrepareOperate(blockApplication.countBlockByStatus(PathologyStatus.PrepareSection.toCode()));
        return new ResponseVO(blockCount);
    }

    @Override
    public ResponseVO getSectionsQuery(@DefaultValue("1") Integer page, @DefaultValue("10") Integer length,
                                       String filter, Long startTime, Long endTime,
                                       Integer status, Integer dyeCategory,
                                       Integer printCount, Long operatorId, Integer order, String sort) throws BuzException {
        if (page < 0 || length < 1) {
            throw new BuzException(BuzExceptionCode.ErrorParam);
        }

        EmbedsCon con = new EmbedsCon();

        con.setStart((page - 1) * length);
        con.setSize(length);
        con.setDyeCategory(dyeCategory);
        con.setPrintCount(printCount);

        if (filter != null) {
            if (filter.indexOf("-") != -1) {
                String[] pathNos = filter.split("-");
                if (pathNos.length != 2) {
                    throw new BuzException(BuzExceptionCode.ErrorParam);
                }
                con.setStartPathNo(pathNos[0].trim());
                con.setEndPathNo(pathNos[1].trim());
            } else {
                con.setFilter(StringUtil.escapeExprSpecialWord(filter.trim()));
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
        if (sort == null) {
            sort = "ASC";
        }
        con.setOrder(SectionSortEnum.valueOf(order).toString() + " " + sort);

        List<InfoQueryDto> data = blockApplication.getSectionsQuery(con);
        Long total = blockApplication.getSectionsQueryTotal(con);

        return new PageDataVO(page, length, total, data);
    }

    @Override
    public ResponseVO getSectionPerson() throws BuzException {
        List<UserDto> user = blockApplication.getSectionPerson();
        return new ResponseVO(user);
    }

    @Override
    public ResponseVO getSlideInfo(@DefaultValue("1") Integer page, @DefaultValue("10") Integer length, long blockId) throws BuzException {
        BlockDto blockDto = blockApplication.getBlockById(blockId);
        if (blockDto == null || blockDto.getId() == null) {
            throw new BuzException(BuzExceptionCode.RecordNotExists);
        }
        SlideCondition condition = new SlideCondition();
        condition.setBlockId(blockId);
        condition.setStart((page - 1) * length);
        condition.setSize(length);
        List<SlideDto> slideDTOs = blockApplication.getSlideByCondition(condition);
        return new PageDataVO(page, length, blockApplication.countSlideByCondition(condition), slideDTOs);
    }

    @Override
    public ResponseVO slidePrint(List<SlidePrintVO> slidePrintVOS) throws BuzException {
        if (CollectionUtils.isEmpty(slidePrintVOS)) {
            throw new BuzException(BuzExceptionCode.ErrorParam);
        }
        List<SlideDto> slideDTOList = new ArrayList<>();
        SlideDto slideDto;
        for (SlidePrintVO slidePrintVO : slidePrintVOS) {
            Long blockId = slidePrintVO.getBlockId();
            if (blockId == null) {
                throw new BuzException(BuzExceptionCode.ErrorParam);
            }
            BlockDto blockDto = blockApplication.getBlockById(blockId);
            if (blockDto == null) {
                throw new BuzException(BuzExceptionCode.ErrorParam);
            }
            slideDto = new SlideDto();
            slideDto.setParentId(blockId);
            slideDto.setId(slidePrintVO.getSlideId());
            slideDto.setMarker(Config.dyeMarker);
            slideDto.setSpecialDye(0);
            Long applyId = slidePrintVO.getApplyId();
            if (applyId == null) {
                slideDto.setApplyId(getSpecialApplyIdByBlock(blockDto));
            } else {
                slideDto.setApplyId(applyId);
            }
            slideDTOList.add(slideDto);
        }
        List<SlidePrintDto> slidePrintDtoList = blockApplication.slidePrint(slideDTOList);
        return new ResponseVO(slidePrintDtoList);
    }

    private Long getSpecialApplyIdByBlock(BlockDto blockDto) {
        TrackingDto finalTrackingDto = null;
        long blockId = blockDto.getId();
        TrackingCondition trackingCondition = new TrackingCondition();
        trackingCondition.setBlockId(blockId);
        trackingCondition.setOperation(TrackingOperation.applyDeepSection.toCode());
        TrackingDto deepSectionTracking = trackingApplication.getTrackingByCondition(trackingCondition);
        trackingCondition.setOperation(TrackingOperation.applyReSection.toCode());
        TrackingDto reSectionTracking = trackingApplication.getTrackingByCondition(trackingCondition);
        if (deepSectionTracking != null || reSectionTracking != null) {
            if (deepSectionTracking != null && reSectionTracking != null) {
                if (deepSectionTracking.getOperationTime().getTime() > reSectionTracking.getOperationTime().getTime()) {
                    finalTrackingDto = deepSectionTracking;
                } else {
                    finalTrackingDto = reSectionTracking;
                }
            } else if (deepSectionTracking == null && reSectionTracking != null) {
                finalTrackingDto = reSectionTracking;
            } else if (reSectionTracking == null && deepSectionTracking != null) {
                finalTrackingDto = deepSectionTracking;
            }
        }
        trackingCondition.setBlockId(blockDto.getPathId());
        trackingCondition.setOperation(TrackingOperation.applyReGrossing.toCode());
        TrackingDto trackingDto = trackingApplication.getTrackingByCondition(trackingCondition);
        finalTrackingDto = getLastTracking(trackingDto, finalTrackingDto);
        Long finalTrackingId = null;
        if (finalTrackingDto != null) {
            finalTrackingId = finalTrackingDto.getId();
        }
        return finalTrackingId;
    }

    private TrackingDto getLastTracking(TrackingDto trackingDto, TrackingDto finalTrackingDto) {
        if (trackingDto != null) {
            long trackingTime = trackingDto.getOperationTime().getTime();
            if (finalTrackingDto == null) {
                finalTrackingDto = trackingDto;
            } else {
                long finalTrackingTime = finalTrackingDto.getOperationTime().getTime();
                if (trackingTime > finalTrackingTime) {
                    finalTrackingDto = trackingDto;
                }
            }
        }
        return finalTrackingDto;
    }
}
