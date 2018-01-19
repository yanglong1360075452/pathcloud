package com.lifetech.dhap.pathcloud.tracking.application.impl;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lifetech.dhap.pathcloud.application.application.ApplicationIhcApplication;
import com.lifetech.dhap.pathcloud.application.application.PathologyApplication;
import com.lifetech.dhap.pathcloud.application.application.dto.IhcBlockDto;
import com.lifetech.dhap.pathcloud.application.application.dto.PathologyDto;
import com.lifetech.dhap.pathcloud.application.application.infrastructure.code.ApplyType;
import com.lifetech.dhap.pathcloud.application.application.infrastructure.code.IhcApplicationStatus;
import com.lifetech.dhap.pathcloud.common.constant.Config;
import com.lifetech.dhap.pathcloud.common.data.PathologyStatus;
import com.lifetech.dhap.pathcloud.common.data.Permission;
import com.lifetech.dhap.pathcloud.common.domain.CommonRepository;
import com.lifetech.dhap.pathcloud.common.exception.BuzException;
import com.lifetech.dhap.pathcloud.common.exception.BuzExceptionCode;
import com.lifetech.dhap.pathcloud.dehydrate.application.InstrumentApplication;
import com.lifetech.dhap.pathcloud.dehydrate.application.dto.InstrumentDto;
import com.lifetech.dhap.pathcloud.notification.NotificationApplication;
import com.lifetech.dhap.pathcloud.notification.dto.NotificationDto;
import com.lifetech.dhap.pathcloud.notification.dto.NotificationReceiverDto;
import com.lifetech.dhap.pathcloud.notification.infrastructure.code.NotificationReceiverType;
import com.lifetech.dhap.pathcloud.notification.infrastructure.code.NotificationSource;
import com.lifetech.dhap.pathcloud.notification.infrastructure.code.NotificationType;
import com.lifetech.dhap.pathcloud.security.UserContext;
import com.lifetech.dhap.pathcloud.setting.application.ParamSettingApplication;
import com.lifetech.dhap.pathcloud.setting.application.dto.ParamSettingDto;
import com.lifetech.dhap.pathcloud.setting.application.dto.TrackingListDto;
import com.lifetech.dhap.pathcloud.setting.application.infrastructure.code.ParamKey;
import com.lifetech.dhap.pathcloud.setting.application.infrastructure.code.SystemKey;
import com.lifetech.dhap.pathcloud.setting.application.infrastructure.code.TrackingList;
import com.lifetech.dhap.pathcloud.tracking.application.*;
import com.lifetech.dhap.pathcloud.tracking.application.condition.*;
import com.lifetech.dhap.pathcloud.tracking.application.dto.*;
import com.lifetech.dhap.pathcloud.tracking.application.infrastructure.code.BlockScoreType;
import com.lifetech.dhap.pathcloud.tracking.application.infrastructure.code.TrackingOperation;
import com.lifetech.dhap.pathcloud.tracking.domain.BlockRepository;
import com.lifetech.dhap.pathcloud.tracking.domain.BlockScanImageRepository;
import com.lifetech.dhap.pathcloud.tracking.domain.BlockScanRepository;
import com.lifetech.dhap.pathcloud.tracking.domain.model.*;
import com.lifetech.dhap.pathcloud.tracking.infrastructure.code.AbnormalHandle;
import com.lifetech.dhap.pathcloud.tracking.infrastructure.code.ScanLocation;
import com.lifetech.dhap.pathcloud.user.application.PermissionApplication;
import com.lifetech.dhap.pathcloud.user.application.UserApplication;
import com.lifetech.dhap.pathcloud.user.application.condition.UserCondition;
import com.lifetech.dhap.pathcloud.user.application.dto.UserDto;
import com.lifetech.dhap.pathcloud.user.application.dto.UserSimpleDto;
import net.sf.json.JSONArray;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * Created by LuoMo on 2016-12-06.
 */

@Service("blockApplication")
public class BlockApplicationImpl implements BlockApplication {

    Logger log = LoggerFactory.getLogger(BlockApplicationImpl.class);
    Gson gson = new Gson();

    @Autowired
    private BlockRepository blockRepository;

    @Autowired
    private TrackingApplication trackingApplication;

    @Autowired
    private UserApplication userApplication;

    @Autowired
    private ParamSettingApplication paramSettingApplication;

    @Autowired
    private PathologyApplication pathologyApplication;

    @Autowired
    private NotificationApplication notificationApplication;

    @Autowired
    private PathTrackingApplication pathTrackingApplication;

    @Autowired
    private PermissionApplication permissionApplication;

    @Autowired
    private CommonRepository commonRepository;

    @Autowired
    private BlockScanRepository blockScanRepository;

    @Autowired
    private BlockScanImageRepository blockScanImageRepository;

    @Autowired
    private ArchiveApplication archiveApplication;

    @Autowired
    private InstrumentApplication instrumentApplication;

    @Autowired
    private SpecialApplicationApplication specialApplicationApplication;

    @Autowired
    private ApplicationIhcApplication applicationIhcApplication;

    @Autowired
    private BlockScoreApplication blockScoreApplication;

    @Autowired
    private GrossingApplication grossingApplication;

    @Override
    public List<BlockDto> getAllBlocksByPathId(long pathId) {
        List<Block> blocks = blockRepository.selectAllBlockByPathId(pathId);
        return blocksToDto(blocks);
    }

    @Override
    public List<Long> getNormalBlocksIdByPathId(long pathId) throws BuzException {
        return blockRepository.selectNormalBlockIdsByPathId(pathId);
    }

    @Override
    public List<BlockDto> getNormalBlockByPathId(long pathId) {
        List<Block> blocks = blockRepository.selectNormalBlocksByPathId(pathId);
        return blocksToDto(blocks);
    }

    @Override
    public List<Long> getHadSectionBlocksIdByPathId(long pathId) {
        return blockRepository.selectHadSectionBlocksIdByPathId(pathId);
    }

    @Override
    public List<Long> getHadEmbedBlocksIdByPathId(long pathId) {
        return blockRepository.selectHadEmbedBlocksIdByPathId(pathId);
    }

    @Override
    public List<Long> getSlidesIdByPathId(long pathId) throws BuzException {
        return blockRepository.selectSlideIdsByPathId(pathId);
    }

    @Override
    public List<Long> getSlidesIdByBlockId(long blockId) {
        return blockRepository.selectSlideIdsByBlockId(blockId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addBlock(BlockDto block) {
        Block data = new Block();
        BeanUtils.copyProperties(block, data);
        List<String> marker = block.getMarker();
        if (marker != null && marker.size() > 0) {
            data.setMarker(gson.toJson(marker));
        }
        blockRepository.insert(data);
        block.setId(blockRepository.last());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addSlide(BlockDto block) {
        Block data = new Block();
        BeanUtils.copyProperties(block, data);
        data.setMarker(block.getSlideMarker());
        blockRepository.insert(data);
        block.setId(blockRepository.last());
    }

    @Override
    public List<BlockDetailDto> getGrossingBlockDetailByCondition(GrossingCon con) {
        List<BlockDetail> blockDetails;
        if (con.getBlockStatus() != null && con.getBlockStatus().equals(PathologyStatus.PrepareGrossing.toCode())) {
            blockDetails = blockRepository.selectNoGrossingBlockByCondition(con);
        } else {
            blockDetails = blockRepository.selectBlockDetailByCondition(con);
        }
        List<BlockDetailDto> data = new ArrayList<>();
        List<ParamSettingDto> blockBiaoshi = paramSettingApplication.getContentToListByKey(ParamKey.BlockBiaoshi.toString());
        List<ParamSettingDto> blockUnits = paramSettingApplication.getContentToListByKey(ParamKey.BlockUnit.toString());
        BlockDetailDto dto;
        for (BlockDetail blockDetail : blockDetails) {
            dto = new BlockDetailDto();
            BeanUtils.copyProperties(blockDetail, dto);
            if (blockDetail.getSecOperatorId() != null) {
                UserSimpleDto user = userApplication.getUserSimpleInfoById(blockDetail.getSecOperatorId());
                if (user != null) {
                    dto.setSecOperatorName(user.getFirstName());
                }
            }
            long pathologyId = dto.getPathologyId();
            PathologyDto pathologyDto = pathologyApplication.getSimplePathById(pathologyId);
            if (blockDetail.getStatus() != null) {

                if (pathologyDto != null && pathologyDto.getReGrossing() && pathologyDto.getStatus().equals(PathologyStatus.PrepareGrossing.toCode())) {
                    dto.setStatusName("待重补取");
                } else {
                    dto.setStatusName(PathologyStatus.valueOf(blockDetail.getStatus()).toString());
                }


                for (ParamSettingDto blockUnit : blockUnits) {
                    if (blockUnit.getCode().equals(blockDetail.getUnit())) {
                        dto.setCountName(blockDetail.getCount() + blockUnit.getName());
                        break;
                    }
                }
            } else {
                if (pathologyDto.getStatus().equals(PathologyStatus.Cancel.toCode())) {
                    dto.setStatusName(PathologyStatus.Cancel.toString());
                    dto.setStatus(PathologyStatus.Cancel.toCode());
                } else if (pathologyDto.getReGrossing() && !pathologyDto.getStatus().equals(PathologyStatus.PrepareGrossingConfirm.toCode())) {
                    dto.setStatusName("待重补取");
                } else {
                    dto.setStatusName(PathologyStatus.PrepareGrossing.toString());
                }

                dto.setCountName("");
            }
            for (ParamSettingDto biaoshi : blockBiaoshi) {
                if (biaoshi.getCode().equals(blockDetail.getBiaoshi())) {
                    dto.setBiaoshiName(biaoshi.getName());
                    break;
                }
            }
            data.add(dto);
        }

        return data;
    }

    @Override
    public Long countGrossingBlockDetailByCondition(GrossingCon con) {
        if (con.getBlockStatus() != null && con.getBlockStatus().equals(PathologyStatus.PrepareGrossing.toCode())) {
            return blockRepository.countNoGrossingBlockByCondition(con);
        } else {
            return blockRepository.countBlockDetailByCondition(con);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateBlock(BlockDto blockDto) {
        if (blockDto == null) {
            throw new BuzException(BuzExceptionCode.ErrorParam);
        }
        Long id = blockDto.getId();
        if (id == null) {
            throw new BuzException(BuzExceptionCode.ErrorParam);
        }
        Block block = blockRepository.selectByPrimaryKey(id);
        if (block == null) {
            throw new BuzException(BuzExceptionCode.ErrorParam);
        }
        Integer biaoshi = blockDto.getBiaoshi();
        String bodyPart = blockDto.getBodyPart();
        Integer count = blockDto.getCount();
        Integer unit = blockDto.getUnit();
        String note = blockDto.getNote();
        Integer status = blockDto.getStatus();
        String slideMarker = blockDto.getSlideMarker();
        String subId = blockDto.getSubId();
        String serialNumber = blockDto.getSerialNumber();
        List<String> marker = blockDto.getMarker();
        if (StringUtils.isNotEmpty(serialNumber)) {
            block.setSerialNumber(serialNumber);
        }
        if (biaoshi != null) {
            block.setBiaoshi(biaoshi);
        }
        if (StringUtils.isNotEmpty(bodyPart)) {
            block.setBodyPart(bodyPart);
        }
        block.setPrint(blockDto.getPrint());
        if (count != null) {
            block.setCount(count);
        }
        if (unit != null) {
            block.setUnit(unit);
        }
        if (note != null) {
            block.setNote(note);
        }
        if (status != null) {
            block.setStatus(status);
        }
        if (CollectionUtils.isNotEmpty(marker)) {
            block.setMarker(gson.toJson(marker));
        } else {
            block.setMarker(null);
        }
        if (slideMarker != null && slideMarker.length() > 0) {
            block.setMarker(slideMarker);
        }
        if (!StringUtils.isBlank(subId)) {
            block.setSubId(subId);
        }
        Integer specialDye = blockDto.getSpecialDye();
        if (specialDye != null) {
            block.setSpecialDye(specialDye);
        }
        block.setEmbedPause(blockDto.getEmbedPause());
        block.setUpdateBy(blockDto.getUpdateBy());
        block.setDeepSection(blockDto.isDeepSection());
        block.setNumber(blockDto.getNumber());
        blockRepository.updateByPrimaryKey(block);
    }

    @Override
    public BlockDto getBlockById(long id) throws BuzException {
        Block block = blockRepository.selectByPrimaryKey(id);
        BlockDto blockDto = new BlockDto();
        if (block != null && block.getId() != null) {
            BeanUtils.copyProperties(block, blockDto);
            TrackingCondition con = new TrackingCondition();
            con.setBlockId(block.getId());
            con.setOperation(TrackingOperation.grossing.toCode());
            TrackingDto dto = trackingApplication.getTrackingByCondition(con);
            if (dto != null) {
                blockDto.setBasketNumber(dto.getInstrumentId());
                blockDto.setGrossingUser(userApplication.getUserSimpleInfoById(dto.getSecOperatorId()));
            }
            blockDto.setUnitName(paramSettingApplication.getNameByKeyAndCode(ParamKey.BlockUnit.toString(), blockDto.getUnit()));
            String marker = block.getMarker();
            if (marker != null) {
                if (marker.indexOf('[') == -1) {
                    blockDto.setSlideMarker(marker);
                } else {
                    blockDto.setMarker((List<String>) gson.fromJson(marker, new TypeToken<List<String>>() {
                    }.getType()));
                }
            }
        }
        return blockDto;
    }

    @Override
    public Integer getMinBlockStatusByPathId(long pathId) throws BuzException {
        Integer status = blockRepository.selectMinStatusByPathId(pathId);
        return status;
    }

    @Override
    public Integer getMinSlideStatusByBlockId(long blockId) {
        return blockRepository.selectMinStatusByBlockId(blockId);
    }

    @Override
    public Long countBlockByBiaoshi(int biaoshi) throws BuzException {
        return blockRepository.countBlockByBiaoshi(biaoshi);
    }

    @Override
    public Long countBlockByUnit(int unit) throws BuzException {
        return blockRepository.countBlockByUnit(unit);
    }

    @Override
    public Long countPathologyByCondition(GrossingCon con) {
        return blockRepository.countPathologyByCondition(con);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteBlockById(long id) {
        blockRepository.deleteByPrimaryKey(id);
        TrackingCondition con = new TrackingCondition();
        con.setBlockId(id);
        trackingApplication.deleteTrackingByCondition(con);
    }

    @Override
    public Long getGrossingDoctorByPathId(long pathId) {
        return blockRepository.selectGrossingDoctorByPathId(pathId);
    }

    @Override
    public List<BlockDto> getBlocksByDehydratorId(long dehydratorId) throws BuzException {
        List<Block> blocks = blockRepository.selectByDehydratorId(dehydratorId);
        return blocksToDto(blocks);
    }

    @Override
    public BlockExtendDto getBlockExtendBySerialNumber(String blockSerialNumber) {
        BlockExtend blockExtend = blockRepository.selectBlockExtendBySerialNumber(blockSerialNumber);
        if (blockExtend == null) {
            return null;
        }
        BlockExtendDto blockExtendDto = new BlockExtendDto();
        Long blockId = blockExtend.getBlockId();
        BeanUtils.copyProperties(blockExtend, blockExtendDto);
        blockExtendDto.setStatusDesc(PathologyStatus.valueOf(blockExtendDto.getStatus()).toString());
        blockExtendDto.setSpecialDyeDesc(paramSettingApplication.getSpecialDyeDesc(blockExtendDto.getSpecialDye()));
        String marker = blockExtend.getMarker();
        if (marker != null && marker.length() > 0) {
            blockExtendDto.setMarker(JSONArray.fromObject(marker));
        }
        UserSimpleDto grossingDoctor = new UserSimpleDto();
        grossingDoctor.setId(blockExtend.getGrossingDoctor());
        grossingDoctor.setFirstName(blockExtend.getGrossingDoctorDesc());
        blockExtendDto.setGrossingDoctor(grossingDoctor);

        int status = blockExtend.getStatus();
        //待包埋
        if (PathologyStatus.PrepareEmbed.toCode().equals(status)) {
            //包括包埋暂停和取消包埋暂停
            TrackingDto trackingDto = trackingApplication.getLastPauseEmbed(blockId);
            //是否有暂停包埋备注
            if (trackingDto != null) {
                blockExtendDto.setEmbedRemark(trackingDto.getNote());
                blockExtendDto.setEmbedRemarkType(trackingDto.getNoteType());
            }
        }
        //已包埋
        if (status > PathologyStatus.PrepareEmbed.toCode()) {
            TrackingCondition con = new TrackingCondition();
            con.setBlockId(blockId);
            con.setOperation(TrackingOperation.embed.toCode());
            TrackingDto track = trackingApplication.getTrackingByCondition(con);
            if (track != null) {
                blockExtendDto.setEmbedRemark(track.getNote());
                blockExtendDto.setEmbedRemarkType(track.getNoteType());
                blockExtendDto.setEmbedOperator(track.getOperatorId());
                blockExtendDto.setEmbedOperatorDesc(track.getOperatorName());
                blockExtendDto.setEmbedOperateTime(track.getOperationTime());
            }
        }
        //已切片
        if (status > PathologyStatus.PrepareSection.toCode()) {
            Long slideId = blockRepository.selectLastSlideIdByBlockId(blockId);
            TrackingCondition con = new TrackingCondition();
            con.setBlockId(slideId);
            con.setOperation(TrackingOperation.section.toCode());
            TrackingDto track = trackingApplication.getTrackingByCondition(con);
            if (track != null) {
                blockExtendDto.setSectionRemark(track.getNote());
                blockExtendDto.setSectionRemarkType(track.getNoteType());
                blockExtendDto.setSectionOperator(track.getOperatorId());
                blockExtendDto.setSectionOperatorDesc(track.getOperatorName());
                blockExtendDto.setSectionOperateTime(track.getOperationTime());
            }
        }
        blockExtendDto.setBiaoshiDesc(paramSettingApplication.getNameByKeyAndCode(ParamKey.BlockBiaoshi.toString(), blockExtend.getBiaoshi()));
        blockExtendDto.setUnitDesc(paramSettingApplication.getNameByKeyAndCode(ParamKey.BlockUnit.toString(), blockExtend.getUnit()));
        return blockExtendDto;
    }

    @Override
    public BlockDto getBlockBySerialNumber(String blockSerialNumber) {
        Block block = blockRepository.selectBlockBySerialNumber(blockSerialNumber);
        BlockDto blockDto = new BlockDto();
        if (block != null) {
            BeanUtils.copyProperties(block, blockDto);
            return blockDto;
        }
        return null;
    }

    @Override
    public Long countBlockByStatus(int status) {
        return blockRepository.countBlockByStatus(status);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void embedConfirm(BlockExtendDto block) {
        long id = block.getBlockId();
        Date dbNow = commonRepository.getDBNow();
        BlockDto blockDto = getBlockById(id);
        blockDto.setUpdateBy(UserContext.getLoginUserID());
        blockDto.setStatus(paramSettingApplication.getNextStatusByStatusAndId(blockDto.getStatus(), id));
        updateBlock(blockDto);

        TrackingDto track = new TrackingDto();
        track.setCreateBy(blockDto.getUpdateBy());
        track.setBlockId(blockDto.getId());
        track.setOperatorId(blockDto.getUpdateBy());
        track.setOperation(TrackingOperation.embed.toCode());
        track.setOperationTime(dbNow);
        track.setManualFlag(true);
        track.setInstrumentId(0L);
        track.setNote(block.getEmbedRemark());
        track.setNoteType(block.getEmbedRemarkType());
        trackingApplication.addTracking(track);

        PathologyDto pathologyDto = pathologyApplication.getSimplePathById(blockDto.getPathId());
        Integer pathStatus = pathologyDto.getStatus();
        Integer realStatus = blockRepository.selectMinStatusByPathId(blockDto.getPathId());
        if (!pathStatus.equals(realStatus) && !pathStatus.equals(PathologyStatus.PrepareGrossing.toCode())) {
            pathologyDto.setStatus(realStatus);
            pathologyApplication.updatePathology(pathologyDto);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void sectionsConfirm(List<BlockExtendDto> blockExtendDTOs) {
        long userId = UserContext.getLoginUserID();
        Date dbNow = commonRepository.getDBNow();
        if (CollectionUtils.isNotEmpty(blockExtendDTOs)) {
            if (blockExtendDTOs.get(0) != null) {
                Long blockId = blockExtendDTOs.get(0).getBlockId();
                BlockDto blockDto = getBlockById(blockId);
                if (blockDto != null) {
                    PathologyDto pathologyDto = pathologyApplication.getSimplePathById(blockDto.getPathId());
                    Integer nextStatus = paramSettingApplication.getNextStatusByStatusAndId(PathologyStatus.PrepareSection.toCode(), blockId);
                    List<TrackingDto> trackingDTOs = new ArrayList<>();
                    for (BlockExtendDto blockExtendDto : blockExtendDTOs) {
                        String slideMarker = blockExtendDto.getSlideMarker();
                        Long applyId = blockExtendDto.getApplyId();
                        Long slideId = blockExtendDto.getSlideId();
                        BlockDto slideDto = null;
                        if (slideId != null) {
                            slideDto = getBlockById(slideId);
                        }
                        if (slideDto == null) {
                            //新增切片记录
                            Block block = new Block();
                            BeanUtils.copyProperties(blockDto, block);
                            block.setCreateBy(userId);
                            block.setParentId(blockId);
                            String slideSubId = String.valueOf(getLastSlideSubIdByBlockId(blockId) + 1);
                            block.setSubId(slideSubId);
                            block.setSerialNumber(blockDto.getSerialNumber() + slideSubId);
                            block.setStatus(nextStatus);
                            block.setSpecialDye(blockExtendDto.getSpecialDye());
                            block.setMarker(slideMarker);
                            if (applyId != null) {
                                Long trackingId = trackingApplication.getBlockIhcTrackingIdByBlockIhcId(applyId);
                                block.setApplyId(trackingId);
                            }
                            block.setPrint(0);
                            blockRepository.insert(block);
                            slideId = blockRepository.last();
                        } else {
                            //已打印玻片更新状态
                            slideId = slideDto.getId();
                            slideDto.setStatus(nextStatus);
                            slideDto.setUpdateBy(userId);
                            updateBlock(slideDto);
                        }
                        if (applyId != null) {
                            //查询是否是特检申请
                            Long blockIhcId = trackingApplication.getBlockIhcIdById(applyId);
                            if (blockIhcId != null) {
                                //更新block_ihc记录状态
                                Integer minSlideStatus = applicationIhcApplication.getMinSlideStatusByTrackingId(applyId);
                                if (minSlideStatus == null) {
                                    //未打印玻片
                                    throw new BuzException(BuzExceptionCode.CanNotOperate);
                                } else {
                                    if (nextStatus.equals(minSlideStatus)) {
                                        IhcBlockDto ihcBlockDto = applicationIhcApplication.getIhcBlockById(blockIhcId);
                                        ihcBlockDto.setStatus(IhcApplicationStatus.HadSection.toCode());
                                        ihcBlockDto.setUpdateBy(userId);
                                        applicationIhcApplication.updateIhcBlock(ihcBlockDto);

                                        String number = blockExtendDto.getNumber();
                                        if (StringUtils.isBlank(number)) {
                                            throw new BuzException(BuzExceptionCode.ErrorParam);
                                        }
                                        //更新特殊申请状态
                                        SpecialApplicationDto specialApplicationDto = specialApplicationApplication.getByNumber(number);
                                        Integer specialApplicationStatus = specialApplicationDto.getStatus();
                                        if (!specialApplicationStatus.equals(nextStatus)) {
                                            specialApplicationDto.setStatus(nextStatus);
                                            specialApplicationDto.setUpdateBy(userId);
                                            specialApplicationApplication.update(specialApplicationDto);
                                        }
                                    }
                                }
                            }
                        }

                        TrackingDto track = new TrackingDto();
                        track.setCreateBy(userId);
                        track.setBlockId(slideId);
                        track.setOperatorId(userId);
                        track.setOperation(TrackingOperation.section.toCode());
                        track.setOperationTime(dbNow);
                        track.setNote(blockExtendDto.getSectionRemark());
                        track.setNoteType(blockExtendDto.getSectionRemarkType());
                        trackingDTOs.add(track);
                    }
                    trackingApplication.addTrackingBatch(trackingDTOs);

                    Integer minSlideStatus = getMinSlideStatusByBlockId(blockId);
                    if (nextStatus.equals(minSlideStatus)) {
                        blockDto.setUpdateBy(userId);
                        Integer applyType = pathologyDto.getApplyType();
                        //查询是否追踪存档
                        Boolean archiveChecked = getArchiveChecked();
                        //科研申请直接结束
                        if (archiveChecked && !applyType.equals(ApplyType.Research.toCode())) {
                            blockDto.setStatus(PathologyStatus.PrepareArchiving.toCode());
                        } else {
                            blockDto.setStatus(PathologyStatus.Ending.toCode());
                        }
                        updateBlock(blockDto);
                    }

                    //更新病理状态
                    Object updatePathology = getUpdatePathology(pathologyDto);
                    if (updatePathology != null) {
                        PathologyDto data = (PathologyDto) updatePathology;
                        pathologyApplication.updatePathology(data);
                    }
                }
            }
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<SlidePrintDto> slidePrint(List<SlideDto> slideDTOs) throws BuzException {
        long userId = UserContext.getLoginUserID();
        Date dbNow = commonRepository.getDBNow();
        if (CollectionUtils.isNotEmpty(slideDTOs)) {
            List<SlidePrintDto> slidePrintDTOs = new ArrayList<>();
            List<TrackingDto> trackingDTOs = new ArrayList<>();
            SlidePrintDto slidePrintDto;
            for (SlideDto slideDto : slideDTOs) {
                Long blockId = slideDto.getParentId();
                Long applyId = slideDto.getApplyId();
                BlockDto blockDto = getBlockById(blockId);
                if (blockDto == null) {
                    throw new BuzException(BuzExceptionCode.RecordNotExists);
                }
                slidePrintDto = new SlidePrintDto();
                String blockSerialNumber = blockDto.getSerialNumber();
                String blockSubId = blockDto.getSubId();
                slidePrintDto.setPathNo(blockSerialNumber.substring(0, blockSerialNumber.lastIndexOf(blockSubId)));
                slidePrintDto.setBlockSubId(blockSubId);
                slidePrintDto.setGrossingBody(blockDto.getBodyPart());
                String marker = slideDto.getMarker();
                slidePrintDto.setMarker(marker);
                Long id = slideDto.getId();
                Integer specialDye = slideDto.getSpecialDye();
                if (id == null) {
                    //新增玻片记录
                    Block block = new Block();
                    BeanUtils.copyProperties(blockDto, block);
                    block.setCreateBy(userId);
                    block.setStatus(PathologyStatus.PrepareSection.toCode());
                    block.setParentId(blockId);
                    String slideSubId = String.valueOf(getLastSlideSubIdByBlockId(blockId) + 1);
                    block.setSubId(slideSubId);
                    block.setSerialNumber(blockSerialNumber + block.getSubId());
                    String number = slideDto.getNumber();
                    block.setSpecialDye(specialDye);
                    block.setMarker(marker);
                    block.setApplyId(applyId);
                    block.setNumber(number);
                    block.setPrint(1);
                    blockRepository.insert(block);
                    id = blockRepository.last();
                    slidePrintDto.setSlideSubId(slideSubId);
                    slidePrintDto.setSlideId(id);
                    slidePrintDto.setApplyId(applyId);
                    slidePrintDto.setSpecialDye(specialDye);
                    slidePrintDto.setNumber(number);
                } else {
                    BlockDto slide = getBlockById(id);
                    slide.setPrint(slide.getPrint() + 1);
                    slide.setUpdateBy(userId);
                    updateBlock(slide);
                    slidePrintDto.setSlideSubId(slide.getSubId());
                    slidePrintDto.setSlideId(id);
                    slidePrintDto.setApplyId(slide.getApplyId());
                    slidePrintDto.setSpecialDye(slide.getSpecialDye());
                    slidePrintDto.setNumber(slide.getNumber());
                }
                slidePrintDTOs.add(slidePrintDto);
                BlockDto printSlide = new BlockDto();
                printSlide.setUpdateBy(userId);
                printSlide.setUpdateTime(dbNow);
                printSlide.setId(id);
                printSlide.setSlideMarker(marker);
                printSlide.setSpecialDye(specialDye);
                trackingDTOs.add(grossingApplication.addPrint(printSlide, TrackingOperation.printSlide.toCode()));
            }
            trackingApplication.addTrackingBatch(trackingDTOs);
            return slidePrintDTOs;
        } else {
            return null;
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void dyeConfirm(List<Long> slideIds, long instrumentId) throws BuzException {
        long userId = UserContext.getLoginUserID();
        Date dbNow = commonRepository.getDBNow();
        if (CollectionUtils.isNotEmpty(slideIds)) {
            List<TrackingDto> trackingDTOs = new ArrayList<>();
            for (long id : slideIds) {
                BlockDto blockDto = getBlockById(id);
                blockDto.setUpdateBy(userId);
                int status = blockDto.getStatus();
                if (!PathologyStatus.PrepareDye.toCode().equals(status)) {
                    throw new BuzException(BuzExceptionCode.StatusNotMatch);
                }
                Integer slideStatus = paramSettingApplication.getNextStatusByStatusAndId(status, id);
                blockDto.setStatus(slideStatus);
                updateBlock(blockDto);

                //更新特殊申请状态
                Long applyId = blockDto.getApplyId();
                if (applyId != null) {
                    SpecialApplicationDto specialDto = updateSpecialApplication(applyId, userId);
                    if (specialDto != null) {
                        specialApplicationApplication.update(specialDto);
                    }
                }

                //记录染色tracking
                TrackingDto track = new TrackingDto();
                track.setCreateBy(userId);
                track.setBlockId(id);
                track.setOperatorId(userId);
                track.setOperation(TrackingOperation.dye.toCode());
                track.setOperationTime(dbNow);
                track.setManualFlag(true);
                track.setInstrumentId(instrumentId);
                trackingDTOs.add(track);

                //更新病理状态
                PathologyDto pathologyDto = pathologyApplication.getSimplePathById(blockDto.getPathId());
                Object updatePathology = getUpdatePathology(pathologyDto);
                if (updatePathology != null) {
                    PathologyDto data = (PathologyDto) updatePathology;
                    pathologyApplication.updatePathology(data);
                }
            }
            trackingApplication.addTrackingBatch(trackingDTOs);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void mountingConfirm(List<Long> slideIds, long instrumentId) throws BuzException {
        long userId = UserContext.getLoginUserID();
        Date dbNow = commonRepository.getDBNow();
        if (CollectionUtils.isNotEmpty(slideIds)) {
            List<TrackingDto> trackingDTOs = new ArrayList<>();
            for (long id : slideIds) {
                BlockDto blockDto = getBlockById(id);
                blockDto.setUpdateBy(userId);
                int status = blockDto.getStatus();
                if (!PathologyStatus.PrepareMounting.toCode().equals(status)) {
                    throw new BuzException(BuzExceptionCode.StatusNotMatch);
                }
                blockDto.setStatus(paramSettingApplication.getNextStatusByStatusAndId(status, id));
                updateBlock(blockDto);

                //更新特殊申请状态
                Long applyId = blockDto.getApplyId();
                if (applyId != null) {
                    SpecialApplicationDto specialDto = updateSpecialApplication(applyId, userId);
                    if (specialDto != null) {
                        specialApplicationApplication.update(specialDto);
                    }
                }

                //记录封片操作
                TrackingDto track = new TrackingDto();
                track.setCreateBy(userId);
                track.setBlockId(id);
                track.setOperatorId(userId);
                track.setOperation(TrackingOperation.mounting.toCode());
                track.setOperationTime(dbNow);
                track.setManualFlag(true);
                track.setInstrumentId(instrumentId);
                trackingDTOs.add(track);

                //更新病理状态
                PathologyDto pathologyDto = pathologyApplication.getSimplePathById(blockDto.getPathId());
                Object updatePathology = getUpdatePathology(pathologyDto);
                if (updatePathology != null) {
                    PathologyDto data = (PathologyDto) updatePathology;
                    pathologyApplication.updatePathology(data);
                }
            }
            trackingApplication.addTrackingBatch(trackingDTOs);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void slideDistribute(SlideDistributeDto slideDistributeDto) throws BuzException {
        Date operationTime = commonRepository.getDBNow();
        Long userId = UserContext.getLoginUserID();
        String distributeDoctor = slideDistributeDto.getDistributeDoctor();
        Long doctorId = slideDistributeDto.getDoctorId();
        for (Long distributeRecord : slideDistributeDto.getDistributeRecords()) {
            List<Long> blockIds = trackingApplication.getPrepareDistributeSlideIds(distributeRecord);
            for (Long id : blockIds) {
                BlockDto blockDto = getBlockById(id);
                blockDto.setUpdateBy(userId);
                int status = blockDto.getStatus();
                if (!PathologyStatus.PrepareCompletionDistribute.toCode().equals(status)) {
                    throw new BuzException(BuzExceptionCode.StatusNotMatch);
                }
                blockDto.setStatus(paramSettingApplication.getNextStatusByStatusAndId(status, id));
                updateBlock(blockDto);

                //更新特殊申请状态
                Long applyId = blockDto.getApplyId();
                SpecialApplicationDto specialDto = updateSpecialApplication(applyId, userId);
                if (specialDto != null) {
                    specialApplicationApplication.update(specialDto);
                }

                //记录派片tracking
                TrackingDto track = new TrackingDto();
                track.setCreateBy(blockDto.getUpdateBy());
                track.setBlockId(blockDto.getId());
                track.setOperatorId(userId);
                track.setOperation(TrackingOperation.slideDistribute.toCode());
                track.setOperationTime(operationTime);
                track.setNote(distributeDoctor);
                track.setNoteType(String.valueOf(doctorId));
                trackingApplication.addTracking(track);

                PathologyDto pathologyDto = pathologyApplication.getSimplePathById(blockDto.getPathId());
                //更新指定诊断医生
                if (applyId == null && doctorId != null) {
                    pathologyDto.setAssignDiagnose(doctorId);
                    pathologyApplication.updatePathology(pathologyDto);
                }
                //更新病理状态
                Object updatePathology = getUpdatePathology(pathologyDto);
                if (updatePathology != null) {
                    PathologyDto data = (PathologyDto) updatePathology;
                    pathologyApplication.updatePathology(data);
                }
            }
        }
    }


    private List<BlockDto> blocksToDto(List<Block> blocks) {
        List<BlockDto> blockDTOs = null;
        if (CollectionUtils.isNotEmpty(blocks)) {
            blockDTOs = new ArrayList<>();
            BlockDto blockDto;
            for (Block block : blocks) {
                blockDto = new BlockDto();
                BeanUtils.copyProperties(block, blockDto);
                TrackingCondition con = new TrackingCondition();
                con.setBlockId(block.getId());
                con.setOperation(TrackingOperation.grossing.toCode());
                TrackingDto track = trackingApplication.getTrackingByCondition(con);
                if (track != null) {
                    blockDto.setBasketNumber(track.getInstrumentId());
                }
                String marker = block.getMarker();
                if (block.getParentId() == null) {
                    if (marker != null && marker.length() > 0) {
                        blockDto.setMarker((List<String>) gson.fromJson(marker, new TypeToken<List<String>>() {
                        }.getType()));
                    }
                }
                blockDto.setSlideMarker(marker);
                blockDto.setStatusName(PathologyStatus.valueOf(block.getStatus()).toString());
                blockDTOs.add(blockDto);
            }
        }
        return blockDTOs;
    }

    private Boolean getArchiveChecked() {
        List<TrackingListDto> trackingList = paramSettingApplication.getContentToListByKey(SystemKey.TrackingList.toString());
        if (trackingList == null) {
            return true;
        }
        for (TrackingListDto trackingListDto : trackingList) {
            Integer code = trackingListDto.getCode();
            //存档
            if (code.equals(TrackingList.Archive.toCode())) {
                return trackingListDto.getChecked();
            }
        }
        return true;
    }

    @Override
    public List<SampleStaticsDto> sampleStatusStatistic() {
        List<SampleStaticsDto> data = new ArrayList<>();

        SampleStaticsDto dto = new SampleStaticsDto();
        Long total = blockRepository.countAbnormalBlock(PathologyStatus.PrepareGrossing.toCode(), null);
        dto.setStatus(PathologyStatus.PrepareGrossing.toCode());
        dto.setStatusName(PathologyStatus.PrepareGrossing.toString());
        dto.setTotal(total);
        dto.setErrorTotal(blockRepository.countAbnormalBlock(PathologyStatus.PrepareGrossing.toCode(),
                new Date(System.currentTimeMillis() - Integer.parseInt(paramSettingApplication.getContentByKey(SystemKey.PrepareGrossingAlarm.toString())) * 3600000L)));
        data.add(dto);

        List trackingStatus = paramSettingApplication.getContentToListByKey(SystemKey.TrackingStatus.toString());

        dto = new SampleStaticsDto();
        total = blockRepository.countBlockByStatus(PathologyStatus.PrepareGrossingConfirm.toCode());
        dto.setStatus(PathologyStatus.PrepareGrossingConfirm.toCode());
        dto.setStatusName(PathologyStatus.PrepareGrossingConfirm.toString());
        dto.setTotal(total);
        dto.setErrorTotal(blockRepository.countAbnormalBlockByStatusAndTime(PathologyStatus.PrepareGrossingConfirm.toCode(),
                new Date(System.currentTimeMillis() - Integer.parseInt(paramSettingApplication.getContentByKey(SystemKey.PrepareGrossingConfirmAlarm.toString())) * 3600000L)));
        data.add(dto);

        if (trackingStatus.indexOf(PathologyStatus.PrepareDehydrate.toCode()) != -1) {
            dto = new SampleStaticsDto();
            total = blockRepository.countBlockByStatus(PathologyStatus.PrepareDehydrate.toCode());
            dto.setStatus(PathologyStatus.PrepareDehydrate.toCode());
            dto.setStatusName(PathologyStatus.PrepareDehydrate.toString());
            dto.setTotal(total);
            dto.setErrorTotal(blockRepository.countAbnormalBlockByStatusAndTime(PathologyStatus.PrepareDehydrate.toCode(),
                    new Date(System.currentTimeMillis() - Integer.parseInt(paramSettingApplication.getContentByKey(SystemKey.PrepareDehydrateAlarm.toString())) * 3600000L)));
            data.add(dto);
        }

        int index = trackingStatus.indexOf(PathologyStatus.PrepareEmbed.toCode());
        if (index != -1) {
            dto = new SampleStaticsDto();
            total = blockRepository.countBlockByStatus(PathologyStatus.PrepareEmbed.toCode());
            dto.setStatus(PathologyStatus.PrepareEmbed.toCode());
            dto.setStatusName(PathologyStatus.PrepareEmbed.toString());
            dto.setTotal(total);
            dto.setErrorTotal(blockRepository.countAbnormalBlockByStatusAndTime(PathologyStatus.PrepareEmbed.toCode(),
                    new Date(System.currentTimeMillis() - Integer.parseInt(paramSettingApplication.getContentByKey(SystemKey.PrepareEmbedAlarm.toString())) * 3600000L)));
            data.add(dto);
        }

        index = trackingStatus.indexOf(PathologyStatus.PrepareSection.toCode());
        if (index != -1) {
            dto = new SampleStaticsDto();
            total = blockRepository.countBlockByStatus(PathologyStatus.PrepareSection.toCode());
            dto.setStatus(PathologyStatus.PrepareSection.toCode());
            dto.setStatusName(PathologyStatus.PrepareSection.toString());
            dto.setTotal(total);
            dto.setErrorTotal(blockRepository.countAbnormalBlockByStatusAndTime(PathologyStatus.PrepareSection.toCode(),
                    new Date(System.currentTimeMillis() - Integer.parseInt(paramSettingApplication.getContentByKey(SystemKey.PrepareSectionAlarm.toString())) * 3600000L)));
            data.add(dto);
        }

        index = trackingStatus.indexOf(PathologyStatus.PrepareDye.toCode());
        if (index != -1) {
            dto = new SampleStaticsDto();
            total = blockRepository.countBlockByStatus(PathologyStatus.PrepareDye.toCode());
            dto.setStatus(PathologyStatus.PrepareDye.toCode());
            dto.setStatusName(PathologyStatus.PrepareDye.toString());
            dto.setTotal(total);
            dto.setErrorTotal(blockRepository.countAbnormalBlockByStatusAndTime(PathologyStatus.PrepareDye.toCode(),
                    new Date(System.currentTimeMillis() - Integer.parseInt(paramSettingApplication.getContentByKey(SystemKey.PrepareDyeAlarm.toString())) * 3600000L)));
            data.add(dto);
        }

        index = trackingStatus.indexOf(PathologyStatus.PrepareCompletionConfirm.toCode());
        if (index != -1) {
            dto = new SampleStaticsDto();
            total = blockRepository.countBlockByStatus(PathologyStatus.PrepareCompletionConfirm.toCode());
            dto.setStatus(PathologyStatus.PrepareCompletionConfirm.toCode());
            dto.setStatusName(PathologyStatus.PrepareCompletionConfirm.toString());
            dto.setTotal(total);
            dto.setErrorTotal(blockRepository.countAbnormalBlockByStatusAndTime(PathologyStatus.PrepareCompletionConfirm.toCode(),
                    new Date(System.currentTimeMillis() - Integer.parseInt(paramSettingApplication.getContentByKey(SystemKey.PrepareConfirmAlarm.toString())) * 3600000L)));
            data.add(dto);
        }

        return data;
    }

    @Override
    public List<BlockDetailDto> getAbnormalBlock(GrossingCon con) {
        List<BlockDetail> blockDetails;
        Integer status = con.getBlockStatus();
        con.setEndTime(getAlarmDateByStatus(status));
        if ((PathologyStatus.PrepareGrossing.toCode()).equals(status)) {
            if (con.getOrder() == null) {
                con.setOrder("p.id desc");
            }
            blockDetails = blockRepository.selectAbnormalBlock(con);
        } else {
            if (con.getOrder() == null) {
                con.setOrder("t.id desc");
            }
            blockDetails = blockRepository.selectAbnormalBlockByStatusAndTime(con);
        }

        List<BlockDetailDto> data = new ArrayList<>();
        UserDto user = null;
        for (BlockDetail blockDetail : blockDetails) {
            BlockDetailDto dto = new BlockDetailDto();
            BeanUtils.copyProperties(blockDetail, dto);

            if (blockDetail.getOperatorId() != null) {
                if (user != null && user.getId().equals(blockDetail.getOperatorId())) {
                    dto.setOperatorName(user.getFirstName());
                } else {
                    user = userApplication.getUserByUserID(blockDetail.getOperatorId());
                    dto.setOperatorName(user.getFirstName());
                }
            }
            if (blockDetail.getStatus() != null) {
                dto.setStatusName(PathologyStatus.valueOf(blockDetail.getStatus()).toString());
            } else {
                dto.setStatusName(PathologyStatus.PrepareGrossing.toString());
                dto.setCountName("");
            }
            //查出的玻片，特殊处理
            if (con.getBlockStatus().compareTo(PathologyStatus.PrepareDye.toCode()) >= 0) {
                dto.setSubId(blockDetail.getSlideId());
                dto.setSlideId(blockDetail.getSubId());
            }
            data.add(dto);
        }
        return data;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int abnormalBlockNotification(GrossingCon con) {
        List<BlockDetail> blockDetails;
        Integer status = con.getBlockStatus();
        con.setEndTime(getAlarmDateByStatus(status));
        if ((PathologyStatus.PrepareGrossing.toCode()).equals(status)) {
            if (con.getOrder() == null) {
                con.setOrder("p.id desc");
            }
            blockDetails = blockRepository.selectAbnormalBlock(con);
        } else {
            if (con.getOrder() == null) {
                con.setOrder("t.id desc");
            }
            blockDetails = blockRepository.selectAbnormalBlockByStatusAndTime(con);
        }
        for (BlockDetail blockDetail : blockDetails) {
            Boolean find = notificationApplication.getNotificationForTaskCheck(blockDetail.getPathologyId(), blockDetail.getBlockId(), blockDetail.getStatus());
            if (!find) {
                NotificationDto notification = new NotificationDto();
                notification.setPathId(blockDetail.getPathologyId());
                notification.setBlockId(blockDetail.getBlockId());
                notification.setBlockStatus(blockDetail.getStatus());
                notification.setCreateBy(1L);
                notification.setNote("");
                if (blockDetail.getParentId() == null && con.getBlockStatus().equals(PathologyStatus.PrepareGrossing.toCode())) {
                    notification.setSubject(blockDetail.getPathologyNumber() + PathologyStatus.valueOf(blockDetail.getStatus()).toString());
                } else if (blockDetail.getParentId() == null) {
                    notification.setSubject(blockDetail.getPathologyNumber() + "-" + blockDetail.getSubId() + PathologyStatus.valueOf(blockDetail.getStatus()).toString());
                } else {
                    Block block = blockRepository.selectByPrimaryKey(blockDetail.getParentId());
                    if (block != null) {
                        notification.setSubject(blockDetail.getPathologyNumber() + "-" + block.getSubId() + "-" + blockDetail.getSubId() + PathologyStatus.valueOf(blockDetail.getStatus()).toString());
                    }
                }

                notification.setReceiverId(blockDetail.getOperatorId());
                notification.setReceiverType(NotificationReceiverType.personal);
                notification.setReadFlag(false);
                notification.setType(NotificationType.withChoice.toCode());
                //TODO
                notification.setSource(blockDetail.getStatus() - 9);
                notificationApplication.addNotification(notification);
            }
        }

        return blockDetails.size();
    }

    private Date getAlarmDateByStatus(Integer status) {
        Date date = null;
        if (PathologyStatus.PrepareGrossing.toCode().equals(status)) {
            date = new Date(System.currentTimeMillis() - Integer.parseInt(paramSettingApplication.getContentByKey(SystemKey.PrepareGrossingAlarm.toString())) * 3600000L);
        } else if (PathologyStatus.PrepareGrossingConfirm.toCode().equals(status)) {
            date = new Date(System.currentTimeMillis() - Integer.parseInt(paramSettingApplication.getContentByKey(SystemKey.PrepareGrossingConfirmAlarm.toString())) * 3600000L);
        } else if (PathologyStatus.PrepareDehydrate.toCode().equals(status)) {
            date = new Date(System.currentTimeMillis() - Integer.parseInt(paramSettingApplication.getContentByKey(SystemKey.PrepareDehydrateAlarm.toString())) * 3600000L);
        } else if (PathologyStatus.PrepareEmbed.toCode().equals(status)) {
            date = new Date(System.currentTimeMillis() - Integer.parseInt(paramSettingApplication.getContentByKey(SystemKey.PrepareEmbedAlarm.toString())) * 3600000L);
        } else if (PathologyStatus.PrepareSection.toCode().equals(status)) {
            date = new Date(System.currentTimeMillis() - Integer.parseInt(paramSettingApplication.getContentByKey(SystemKey.PrepareSectionAlarm.toString())) * 3600000L);
        } else if (PathologyStatus.PrepareDye.toCode().equals(status)) {
            date = new Date(System.currentTimeMillis() - Integer.parseInt(paramSettingApplication.getContentByKey(SystemKey.PrepareDyeAlarm.toString())) * 3600000L);
        } else if (PathologyStatus.PrepareCompletionConfirm.toCode().equals(status)) {
            date = new Date(System.currentTimeMillis() - Integer.parseInt(paramSettingApplication.getContentByKey(SystemKey.PrepareConfirmAlarm.toString())) * 3600000L);
        }
        return date;
    }

    @Override
    public List<SlideDto> getSlideDistributesByCondition(SlideCondition condition) {
        List<SlideDto> slideDTOs = new ArrayList<>();
        List<Slide> slides = blockRepository.selectSlideDistributeByCondition(condition);
        if (slides.size() > 0) {
            SlideDto slideDto;
            for (Slide slide : slides) {
                slideDto = new SlideDto();
                BeanUtils.copyProperties(slide, slideDto);
                slideDto.setConfirmUser(userApplication.getUserSimpleInfoById(slide.getConfirmUser()));
                slideDTOs.add(slideDto);
            }
        }
        return slideDTOs;
    }

    @Override
    public Long countSlideDistributesByCondition(SlideCondition condition) {
        return blockRepository.countSlideDistributeByCondition(condition);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void abnormalBlockDeal(DealNotificationDto dto) {
        if (dto.getDealType().equals(AbnormalHandle.Notification.toCode())) {//样本状态异常, 通知技术人员
            NotificationDto notification = new NotificationDto();
            Long receiver;
            if (dto.getBlockId() != null) {
                Block block = blockRepository.selectByPrimaryKey(dto.getBlockId());
                notification.setBlockStatus(block.getStatus());
                notification.setReceiverType(NotificationReceiverType.group);
                if (block.getStatus().equals(PathologyStatus.PrepareGrossingConfirm.toCode())) {
                    receiver = 4L;
                } else if (block.getStatus().equals(PathologyStatus.PrepareDehydrate.toCode())) {
                    receiver = 8L;
                } else if (block.getStatus().equals(PathologyStatus.PrepareEmbed.toCode())) {
                    receiver = 16L;
                } else if (block.getStatus().equals(PathologyStatus.PrepareSection.toCode())) {
                    receiver = 32L;
                } else if (block.getStatus().equals(PathologyStatus.PrepareDye.toCode())) {
                    receiver = 64L;
                } else if (block.getStatus().equals(PathologyStatus.PrepareCompletionConfirm.toCode())) {
                    receiver = 128L;
                } else {
                    throw new BuzException(BuzExceptionCode.ErrorParam);
                }
                if (block.getParentId() == null) {
                    notification.setSubject(block.getSerialNumber().substring(0, 9) + "-" + block.getSubId() + PathologyStatus.valueOf(block.getStatus()).toString());
                } else {
                    Block parent = blockRepository.selectByPrimaryKey(block.getParentId());
                    if (parent != null) {
                        notification.setSubject(parent.getSerialNumber().substring(0, 9) + "-" + parent.getSubId() + PathologyStatus.valueOf(block.getStatus()).toString());
                    }
                }
            } else {
                PathologyDto pathology = pathologyApplication.getPathologyExpandById(dto.getPathId());
                receiver = 256L;//登记权限的用户
                notification.setReceiverType(NotificationReceiverType.group);
                notification.setSubject(pathology.getSerialNumber() + PathologyStatus.valueOf(pathology.getStatus()).toString());
                notification.setBlockStatus(pathology.getStatus());
            }

            notification.setReceiverId(receiver);
            notification.setPathId(dto.getPathId());
            notification.setBlockId(dto.getBlockId());
            notification.setCreateBy(dto.getCreateBy());
            notification.setNote(dto.getNote());

            notification.setReadFlag(false);
            notification.setPathId(dto.getPathId());
            notification.setType(dto.getDealType());
            notification.setSource(dto.getSource());
            notification.setType(NotificationType.withChoice.toCode());
            notificationApplication.addNotification(notification);

            UserCondition userCondition = new UserCondition();
            userCondition.setSize(Integer.MAX_VALUE);
            List<Integer> permissions = new ArrayList<>();
            permissions.add(permissionApplication.getByCode(receiver.intValue()).getId());
            userCondition.setPermissionIds(permissions);
            userCondition.setStatus(true);
            List<UserDto> users = userApplication.getUserByCondition(userCondition);
            for (UserDto user : users) {
                NotificationReceiverDto receiverDto = new NotificationReceiverDto();
                receiverDto.setNotificationId(notification.getId());
                receiverDto.setReceiverId(user.getId());
                receiverDto.setReadFlag(false);
                notificationApplication.addNotificationReceiver(receiverDto);
            }
        } else {
            slideAbnormalHandle(dto.getBlockId(), dto.getDealType(), dto.getNote(), dto.getSource());
        }
    }

    @Override
    public SlideDto getSlideConfirmBySlideNoAndSubId(String slideNo, String subId) {
        Slide slide = blockRepository.selectSlideConfirmBySlideNoAndSubId(slideNo, subId);
        return slideToDto(slide);
    }

    @Override
    public List<SlideDto> getSlideConfirmByPathId(long pathId) {
        List<Slide> slides = blockRepository.selectSlideConfirmByPathId(pathId);
        return slidesToDTOs(slides);
    }

    @Override
    public Long countSlideConfirmByPathId(long pathId) {
        return blockRepository.countSlideConfirmByPathId(pathId);
    }

    @Override
    public List<Long> getSlideConfirmIdsByPathId(long pathId) {
        return blockRepository.selectSlideConfirmIdsByPathId(pathId);
    }

    @Override
    public List<SlideDto> getSlideConfirmByBlockId(long blockId) {
        List<Slide> slides = blockRepository.selectSlideConfirmByBlockId(blockId);
        return slidesToDTOs(slides);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void slideConfirm(List<Long> slideIds) throws BuzException {
        Date operationTime = commonRepository.getDBNow();
        if (slideIds.size() > 0) {
            List<TrackingDto> trackingDTOs = new ArrayList<>();
            for (Long id : slideIds) {
                BlockDto blockDto = getBlockById(id);
                int status = blockDto.getStatus();
                if (!PathologyStatus.PrepareCompletionConfirm.toCode().equals(status)) {
                    throw new BuzException(BuzExceptionCode.StatusNotMatch);
                }
                long userId = UserContext.getLoginUserID();
                blockDto.setUpdateBy(userId);
                PathologyDto pathologyDto = pathologyApplication.getSimplePathById(blockDto.getPathId());
                Integer applyType = pathologyDto.getApplyType();
                if (applyType.equals(ApplyType.Research.toCode())) {
                    blockDto.setStatus(PathologyStatus.Ending.toCode());
                } else {
                    blockDto.setStatus(paramSettingApplication.getNextStatusByStatusAndId(status, id));
                }
                updateBlock(blockDto);

                Long parentId = blockDto.getParentId();
                BlockDto block = getBlockById(parentId);
                block.setSpecialDye(Config.dyeType);
                String dyeMarker = Config.dyeMarker;
                if (dyeMarker != null) {
                    List<String> marker = new ArrayList<>();
                    marker.add(dyeMarker);
                    block.setMarker(marker);
                }
                updateBlock(block);

                //更新特殊申请状态
                Long applyId = blockDto.getApplyId();
                SpecialApplicationDto specialDto = updateSpecialApplication(applyId, userId);
                if (specialDto != null) {
                    specialApplicationApplication.update(specialDto);
                }

                TrackingDto track = new TrackingDto();
                track.setCreateBy(blockDto.getUpdateBy());
                track.setBlockId(blockDto.getId());
                track.setOperatorId(blockDto.getUpdateBy());
                track.setOperation(TrackingOperation.completionConfirm.toCode());
                track.setOperationTime(operationTime);
                track.setManualFlag(true);
                track.setInstrumentId(0L);
                trackingDTOs.add(track);

                Object updatePathology = getUpdatePathology(pathologyDto);
                if (updatePathology != null) {
                    PathologyDto data = (PathologyDto) updatePathology;
                    pathologyApplication.updatePathology(data);
                }

                //分别添加诊断/制片确认玻片打分默认值
                float defaultScore = Config.qualifiedDefaultScore;
                BlockScoreDto blockScoreDto = new BlockScoreDto();
                blockScoreDto.setBlockId(id);
                blockScoreDto.setParentId(parentId);
                blockScoreDto.setAverage(defaultScore);
                blockScoreDto.setGrossing(defaultScore);
                blockScoreDto.setDehydrate(defaultScore);
                blockScoreDto.setEmbedding(defaultScore);
                blockScoreDto.setSealing(defaultScore);
                blockScoreDto.setSectioning(defaultScore);
                blockScoreDto.setStaining(defaultScore);
                blockScoreDto.setCreateBy(userId);
                BlockScoreDto diagnoseScore = blockScoreApplication.getBlockScoreBySlideIdAndType(id, BlockScoreType.Diagnose.toCode());
                if (diagnoseScore == null) {
                    blockScoreDto.setType(BlockScoreType.Diagnose.toCode());
                    blockScoreApplication.addBlockScore(blockScoreDto);
                }
                BlockScoreDto confirmScore = blockScoreApplication.getBlockScoreBySlideIdAndType(id, BlockScoreType.Production.toCode());
                if (confirmScore == null) {
                    blockScoreDto.setType(BlockScoreType.Production.toCode());
                    blockScoreApplication.addBlockScore(blockScoreDto);
                }

            }
            trackingApplication.addTrackingBatch(trackingDTOs);
            blockScanRepository.deleteBatch(slideIds);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void slideAbnormalHandle(long slideId, int handle, String note, int source) throws BuzException {

        if (AbnormalHandle.valueOf(handle) == null) {
            throw new BuzException(BuzExceptionCode.ErrorParam);
        }

        long userId = UserContext.getLoginUserID();
        Date dbNow = commonRepository.getDBNow();
        BlockDto slideDto = getBlockById(slideId);
        slideDto.setUpdateBy(userId);
        slideDto.setStatus(PathologyStatus.ErrorEnding.toCode());
        updateBlock(slideDto);

        TrackingDto track = new TrackingDto();
        track.setCreateBy(userId);
        track.setBlockId(slideDto.getId());
        track.setOperatorId(userId);
        track.setOperation(TrackingOperation.errorEnd.toCode());
        track.setNote(note);
        track.setOperationTime(dbNow);
        track.setManualFlag(true);
        track.setInstrumentId(0L);
        trackingApplication.addTracking(track);

        long pathId = slideDto.getPathId();
        PathologyDto pathologyDto = pathologyApplication.getSimplePathById(slideDto.getPathId());
        if (AbnormalHandle.ReGrossing.toCode().equals(handle)) {
            pathologyDto.setStatus(PathologyStatus.PrepareGrossing.toCode());
            pathologyDto.setReGrossing(true);
            pathologyDto.setUpdateBy(userId);
            pathologyApplication.updatePathology(pathologyDto);

            PathTrackingDto pathTrackingDto = new PathTrackingDto();
            pathTrackingDto.setPathId(pathologyDto.getId());
            pathTrackingDto.setOperation(TrackingOperation.applyReGrossing.toCode());
            pathTrackingDto.setOperatorId(userId);
            pathTrackingDto.setNote(note);
            pathTrackingDto.setOperationTime(dbNow);
            pathTrackingDto.setCreateBy(userId);
            pathTrackingApplication.addPathTracking(pathTrackingDto);

            Long parentId = slideDto.getParentId();
            if (parentId != null) {
                BlockDto blockDto = getBlockById(parentId);
                blockDto.setUpdateBy(userId);
                blockDto.setStatus(PathologyStatus.ErrorEnding.toCode());
                updateBlock(blockDto);
            }

            //系统通知
            NotificationDto notification = new NotificationDto();
            notification.setCreateBy(userId);
            notification.setNote(note);

            TrackingCondition con = new TrackingCondition();
            if (parentId == null) {
                con.setBlockId(slideId);
            } else {
                con.setBlockId(slideDto.getParentId());
            }
            notification.setPathId(pathId);
            notification.setBlockId(con.getBlockId());
            notification.setBlockStatus(PathologyStatus.PrepareGrossing.toCode());
            notification.setType(NotificationType.withoutChoice.toCode());

            con.setOperation(TrackingOperation.grossing.toCode());
            TrackingDto trackingDto = trackingApplication.getTrackingByCondition(con);
            if (trackingDto.getOperatorId() == null) {
                notification.setSubject(pathologyDto.getSerialNumber() + "重补取");
                notification.setContent(notification.getSubject());
                notification.setReceiverType(NotificationReceiverType.group);
                notification.setReceiverId(4L);
                notification.setReadFlag(false);
                notification.setAbnormalHandle(handle);
                notification.setSource(source);
                notificationApplication.addNotification(notification);

                UserCondition userCondition = new UserCondition();
                userCondition.setSize(Integer.MAX_VALUE);
                List<Integer> permissions = new ArrayList<>();
                permissions.add(permissionApplication.getByCode(Permission.Grossing.toCode()).getId());
                userCondition.setPermissionIds(permissions);
                userCondition.setStatus(true);
                List<UserDto> users = userApplication.getUserByCondition(userCondition);
                for (UserDto user : users) {
                    NotificationReceiverDto receiverDto = new NotificationReceiverDto();
                    receiverDto.setNotificationId(notification.getId());
                    receiverDto.setReceiverId(user.getId());
                    receiverDto.setReadFlag(false);
                    notificationApplication.addNotificationReceiver(receiverDto);
                }
            } else {
                if (slideDto.getParentId() == null) {
                    notification.setSubject(pathologyDto.getSerialNumber() + "-" + slideDto.getSubId() + "重补取");
                } else {
                    BlockDto blockDto = getBlockById(slideDto.getParentId());
                    notification.setSubject(pathologyDto.getSerialNumber() + "-" + blockDto.getSubId() + "重补取");
                }
                notification.setContent(notification.getSubject());
                notification.setReceiverType(NotificationReceiverType.personal);
                notification.setReceiverId(trackingDto.getOperatorId());//取材医生
                notification.setReadFlag(false);
                notification.setAbnormalHandle(handle);
                notification.setSource(source);
                notificationApplication.addNotification(notification);
            }
        } else if (AbnormalHandle.ReSection.toCode().equals(handle)) {
            BlockDto blockDto = getBlockById(slideDto.getParentId());
            if (blockDto == null) {
                throw new BuzException(BuzExceptionCode.ErrorParam);
            }
            Integer status = blockDto.getStatus();
            if (status >= PathologyStatus.PrepareSection.toCode()) {
                blockDto.setUpdateBy(userId);
                blockDto.setStatus(PathologyStatus.PrepareSection.toCode());
                updateBlock(blockDto);
                pathologyDto.setStatus(PathologyStatus.PrepareSection.toCode());
                pathologyDto.setUpdateBy(userId);
                pathologyApplication.updatePathology(pathologyDto);
            }

            //记录申请重切
            track.setOperatorId(userId);
            track.setOperation(TrackingOperation.applyReSection.toCode());
            track.setNote(note);
            track.setOperationTime(dbNow);
            track.setManualFlag(true);
            track.setInstrumentId(0L);
            trackingApplication.addTracking(track);

            //系统通知
            NotificationDto notification = new NotificationDto();
            notification.setPathId(pathId);
            notification.setCreateBy(userId);
            notification.setNote(note);
            notification.setBlockId(blockDto.getId());
            notification.setBlockStatus(PathologyStatus.PrepareSection.toCode());
            notification.setType(NotificationType.withoutChoice.toCode());
            notification.setSubject(pathologyDto.getSerialNumber() + "-" + blockDto.getSubId() + "重切");
            notification.setContent(notification.getSubject());
            TrackingCondition con = new TrackingCondition();
            con.setBlockId(slideId);
            if (slideDto.getParentId() != null) {
                con.setBlockId(slideId);
            } else {
                con.setBlockId(blockRepository.selectLastSlideIdByBlockId(slideId));
            }
            con.setOperation(TrackingOperation.section.toCode());
            TrackingDto trackingDto = trackingApplication.getTrackingByCondition(con);
            if (trackingDto.getOperatorId() == null) {
                notification.setReceiverType(NotificationReceiverType.group);
                notification.setReceiverId(32L);
                notification.setReadFlag(false);
                notification.setAbnormalHandle(handle);
                notification.setSource(source);
                notificationApplication.addNotification(notification);

                UserCondition userCondition = new UserCondition();
                userCondition.setSize(Integer.MAX_VALUE);
                List<Integer> permissions = new ArrayList<>();
                permissions.add(permissionApplication.getByCode(Permission.Slice.toCode()).getId());
                userCondition.setPermissionIds(permissions);
                userCondition.setStatus(true);
                List<UserDto> users = userApplication.getUserByCondition(userCondition);
                for (UserDto user : users) {
                    NotificationReceiverDto receiverDto = new NotificationReceiverDto();
                    receiverDto.setReadFlag(false);
                    receiverDto.setNotificationId(notification.getId());
                    receiverDto.setReceiverId(user.getId());
                    notificationApplication.addNotificationReceiver(receiverDto);
                }
            } else {
                notification.setReceiverId(trackingDto.getOperatorId());//切片操作者
                notification.setReceiverType(NotificationReceiverType.personal);
                notification.setReadFlag(false);
                notification.setAbnormalHandle(handle);
                notification.setSource(source);
                notificationApplication.addNotification(notification);
            }
        } else if (AbnormalHandle.ErrorEnd.toCode().equals(handle)) {
            Integer pathStatus = pathologyDto.getStatus();
            Integer realStatus = blockRepository.selectMinStatusByPathId(pathId);
            if (realStatus.equals(PathologyStatus.ErrorEnding.toCode()) && !pathStatus.equals(PathologyStatus.PrepareGrossing.toCode())) {
                pathologyDto.setStatus(realStatus);
                pathologyDto.setUpdateBy(userId);
                pathologyApplication.updatePathology(pathologyDto);
            }
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void blockReGrossing(Long pathologyId, String note) {
        long userId = UserContext.getLoginUserID();
        Date dbNow = commonRepository.getDBNow();
        PathologyDto pathologyDto = pathologyApplication.getSimplePathById(pathologyId);
        if (pathologyDto == null || pathologyDto.getId() == null) {
            throw new BuzException(BuzExceptionCode.RecordNotExists);
        }

        BlockDetail blockDetail = blockRepository.selectPathologyGrossingOperator(pathologyId);

        if (blockDetail != null) {
            pathologyDto.setStatus(PathologyStatus.PrepareGrossing.toCode());
            pathologyDto.setReGrossing(true);
            pathologyDto.setUpdateBy(userId);
            pathologyDto.setAssignDiagnose(userId);
            pathologyDto.setId(pathologyId);
            pathologyApplication.updatePathology(pathologyDto);

            TrackingDto trackingDto = new TrackingDto();
            trackingDto.setBlockId(pathologyId);
            trackingDto.setOperation(TrackingOperation.applyReGrossing.toCode());
            trackingDto.setOperatorId(userId);
            trackingDto.setNote(note);
            trackingDto.setSecOperatorId(1L);//用此字段标识申请入口-诊断工作台申请的重补取
            trackingDto.setOperationTime(dbNow);
            trackingDto.setCreateBy(userId);
            trackingApplication.addTracking(trackingDto);

            NotificationDto notification = new NotificationDto();
            notification.setPathId(pathologyId);
            notification.setBlockStatus(PathologyStatus.PrepareGrossing.toCode());
            notification.setCreateBy(userId);
            notification.setNote(note);
            notification.setSubject(pathologyDto.getSerialNumber() + "重补取");
            notification.setContent(notification.getSubject());
            notification.setReceiverId(blockDetail.getOperatorId());
            notification.setReceiverType(NotificationReceiverType.personal);
            notification.setReadFlag(false);
            notification.setAbnormalHandle(AbnormalHandle.ReGrossing.toCode());
            notification.setSource(NotificationSource.completionConfirm.toCode());
            notification.setType(NotificationType.withoutChoice.toCode());
            notificationApplication.addNotification(notification);
        }
    }

    @Override
    public SlideDto getSlideBySlideNoAndSubId(String slideNo, String subId) {
        Slide slide = blockRepository.selectSlideBySlideNoAndSubId(slideNo, subId);
        if (slide != null) {
            return slideToDto(slide);
        }
        return null;
    }

    @Override
    public List<SlideDto> getSlideByCondition(SlideCondition condition) {
        return slidesToDTOs(blockRepository.selectSlideByCondition(condition));
    }

    @Override
    public List<Long> getSlideIdByCondition(SlideCondition condition) {
        return blockRepository.selectSlideIdByCondition(condition);
    }

    @Override
    public Long countSlideByCondition(SlideCondition condition) {
        return blockRepository.countSlideByCondition(condition);
    }

    @Override
    public BlockDetailDto getBlockDetail(Long blockId) {
        BlockDetail blockDetail = blockRepository.selectBlockDetailById(blockId);
        BlockDetailDto detailDto = new BlockDetailDto();
        if (blockDetail != null) {
            BeanUtils.copyProperties(blockDetail, detailDto);
        }
        return detailDto;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void diagnose(DiagnoseDto diagnoseDto) {
        long userId = UserContext.getLoginUserID();
        Date dbNow = commonRepository.getDBNow();
        long pathId = diagnoseDto.getId();
        PathologyDto pathologyDto = pathologyApplication.getSimplePathById(pathId);

        Integer operation;
        Integer nextStatus;

        int status = pathologyDto.getStatus();
        if (PathologyStatus.PrepareThirdDiagnose.toCode().equals(status)) {
            nextStatus = PathologyStatus.PrepareReport.toCode();
            operation = TrackingOperation.thirdDiagnose.toCode();
        } else if (PathologyStatus.PrepareConsult.toCode().equals(status)) {
            nextStatus = PathologyStatus.PrepareReport.toCode();
            operation = TrackingOperation.consult.toCode();
        } else {
            List<Integer> permissionsCode = UserContext.getLoginUserPermissions();
            if (permissionsCode.contains(Permission.ThirdDiagnose.toCode())) {
                operation = TrackingOperation.thirdDiagnose.toCode();
                nextStatus = PathologyStatus.PrepareReport.toCode();
            } else if (permissionsCode.contains(Permission.SecondDiagnose.toCode())) {
                operation = TrackingOperation.secondDiagnose.toCode();
                nextStatus = PathologyStatus.PrepareThirdDiagnose.toCode();
            } else {
                operation = TrackingOperation.firstDiagnose.toCode();
                nextStatus = PathologyStatus.PrepareSecondDiagnose.toCode();
            }
        }

        String result = diagnoseDto.getResult();
        PathTrackingDto pathTracking = new PathTrackingDto();
        pathTracking.setCreateBy(userId);
        pathTracking.setPathId(pathologyDto.getId());
        pathTracking.setOperatorId(userId);
        pathTracking.setOperation(operation);
        pathTracking.setOperationTime(dbNow);
        pathTracking.setNote(result);
        pathTrackingApplication.addPathTracking(pathTracking);

        pathologyDto.setStatus(nextStatus);
        pathologyDto.setResult(result);
        pathologyDto.setDiagnose(diagnoseDto.getDiagnose());
        pathologyDto.setMicroDiagnose(diagnoseDto.getMicroDiagnose());
        pathologyDto.setAssignDiagnose(diagnoseDto.getAssignDiagnose());
        pathologyDto.setNote(diagnoseDto.getNote());
        pathologyApplication.updatePathology(pathologyDto);

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deepSection(long blockId, String note) {
        long userId = UserContext.getLoginUserID();
        Date dbNow = commonRepository.getDBNow();
        BlockDto blockDto = getBlockById(blockId);
        if (blockDto != null && blockDto.getId() != null) {
            blockDto.setDeepSection(true);
            blockDto.setStatus(PathologyStatus.PrepareSection.toCode());
            updateBlock(blockDto);
            long pathId = blockDto.getPathId();
            PathologyDto pathologyDto = pathologyApplication.getSimplePathById(pathId);
            Integer pathStatus = pathologyDto.getStatus();
            Integer realStatus = blockRepository.selectMinStatusByPathId(pathId);
            pathologyDto.setAssignDiagnose(userId);
            if (!pathStatus.equals(realStatus) && !pathStatus.equals(PathologyStatus.PrepareGrossing.toCode())) {
                pathologyDto.setStatus(realStatus);
                pathologyApplication.updatePathology(pathologyDto);
            }

            //记录申请深切操作
            TrackingDto track = new TrackingDto();
            track.setCreateBy(userId);
            track.setBlockId(blockId);
            track.setOperatorId(userId);
            track.setOperation(TrackingOperation.applyDeepSection.toCode());
            track.setNote(note);
            track.setOperationTime(dbNow);
            track.setManualFlag(true);
            track.setInstrumentId(0L);
            track.setSecOperatorId(1L);//用此字段标识申请入口-诊断工作台申请
            trackingApplication.addTracking(track);

            NotificationDto notification = new NotificationDto();
            notification.setPathId(blockDto.getPathId());
            notification.setBlockId(blockId);
            notification.setCreateBy(userId);
            notification.setNote(note);
            notification.setSubject(pathologyDto.getSerialNumber() + "-" + blockDto.getSubId() + "深切");
            notification.setReceiverId(trackingApplication.getSectionOperatorByBlockId(blockId));//该蜡块最后切片操作者
            notification.setReceiverType(NotificationReceiverType.personal);
            notification.setReadFlag(false);
            notification.setSource(NotificationSource.diagnose.toCode());
            notification.setType(NotificationType.withoutChoice.toCode());
            notification.setBlockStatus(PathologyStatus.PrepareSection.toCode());
            notificationApplication.addNotification(notification);
        }
    }

    @Override
    public Integer getLastSlideSubIdByBlockId(long blockId) {
        return blockRepository.selectLastSlideSubIdByBlockId(blockId);
    }

    @Override
    public List<GrossingInfoDto> getGrossingInfo(Long pathId, String number) {
        List<GrossingInfo> grossingInfoList = blockRepository.selectGrossingInfo(pathId, number);
        List<GrossingInfoDto> lists = null;
        if (CollectionUtils.isNotEmpty(grossingInfoList)) {
            lists = new ArrayList<>();
            GrossingInfoDto grossingInfoDto;
            UserDto secUser = null;
            for (GrossingInfo grossingInfo : grossingInfoList) {
                grossingInfoDto = new GrossingInfoDto();
                BeanUtils.copyProperties(grossingInfo, grossingInfoDto);
                if (grossingInfo.getStatus() == 40) {
                    grossingInfoDto.setStop(TrackingOperation.errorEnd.toString());
                }
                if (grossingInfoDto.getSecOperatorId() != null) {
                    if (secUser != null && secUser.getId().equals(grossingInfoDto.getSecOperatorId())) {
                        grossingInfoDto.setSecOperatorName(secUser.getFirstName());
                    } else {
                        secUser = userApplication.getUserByUserID(grossingInfoDto.getSecOperatorId());
                        if (secUser != null) {
                            grossingInfoDto.setSecOperatorName(secUser.getFirstName());
                        }
                    }
                }
                grossingInfoDto.setBiaoshiDesc(paramSettingApplication.getNameByKeyAndCode(ParamKey.BlockBiaoshi.toString(), grossingInfoDto.getBiaoshi()));
                grossingInfoDto.setUnitName(paramSettingApplication.getNameByKeyAndCode(ParamKey.BlockUnit.toString(), grossingInfoDto.getUnit()));
                lists.add(grossingInfoDto);

            }
        }
        return lists;
    }

    @Override
    public BlockDto getBlockForWaitTime(Long blockId, Integer status, Date time) {
        Block block = blockRepository.selectBlockForWaitTime(blockId, status, time);
        if (block != null) {
            BlockDto dto = new BlockDto();
            BeanUtils.copyProperties(block, dto);
            return dto;
        }
        return null;
    }

    @Override
    public Long getRegrossingWaitTime(long blockId) {
        Long time = blockRepository.selectRegrossingWaitTime(blockId);
        if (time == null || time < 0) {
            time = blockRepository.selectRegrossingLessWaitTime(blockId);
        }
        return time;
    }

    @Override
    public long countHadOperatedBlocks(HadOperatedCondition condition) {
        return blockRepository.countHadOperatedBlocks(condition);
    }

    @Override
    public List<InfoQueryDto> getSectionsQuery(EmbedsCon con) {
        List<InfoQueryDto> dataList = blockRepository.getSectionsQuery(con);
        List<ParamSettingDto> BlockBiaoshis = paramSettingApplication.getContentToListByKey(ParamKey.BlockBiaoshi.toString());
        List<InfoQueryDto> lists = new ArrayList<>();
        for (InfoQueryDto data : dataList) {
            if (data.getStatus() == 15) {
                for (ParamSettingDto blockBiaoshi : BlockBiaoshis) {
                    if (blockBiaoshi.getCode().equals(data.getBiaoShi())) {
                        data.setBiaoShiName(blockBiaoshi.getName());
                        break;
                    }
                }
                embedsAndSection(data);
            } else {
                for (ParamSettingDto blockBiaoshi : BlockBiaoshis) {
                    if (blockBiaoshi.getCode().equals(data.getBiaoShi())) {
                        data.setBiaoShiName(blockBiaoshi.getName());
                        break;
                    }
                }
                embedsAndSectionQuery(data);
            }
            lists.add(data);
        }
        return lists;
    }

    @Override
    public Long getSectionsQueryTotal(EmbedsCon con) {
        return blockRepository.getSectionsQueryTotal(con);
    }

    @Override
    public List<UserDto> getEmbedPerson() {
        List<Long> ids = blockRepository.getEmbedPerson();
        List<UserDto> users = new ArrayList<>();
        for (Long id : ids) {
            UserDto user = userApplication.getUserByUserID(id);
            users.add(user);
        }

        return users;
    }

    @Override
    public List<UserDto> getSectionPerson() {
        List<Long> ids = blockRepository.getSectionPerson();
        List<UserDto> users = new ArrayList<>();
        for (Long id : ids) {
            UserDto user = userApplication.getUserByUserID(id);
            users.add(user);
        }

        return users;
    }

    @Override
    public List<InfoQueryDto> getDyeQuery(EmbedsCon con) {
        List<InfoQueryDto> dyeQuery = blockRepository.getDyeQuery(con);
        List<InfoQueryDto> lists = new ArrayList<>();
        List<ParamSettingDto> BlockBiaoshis = paramSettingApplication.getContentToListByKey(ParamKey.BlockBiaoshi.toString());

        for (InfoQueryDto dto : dyeQuery) {
            for (ParamSettingDto paramSettingDto : BlockBiaoshis) {
                if (paramSettingDto.getCode().equals(dto.getBiaoShi())) {
                    dto.setBiaoShiName(paramSettingDto.getName());
                    break;
                }
            }

            embedsAndSectionQuery(dto);
            lists.add(dto);
        }

        return lists;
    }

    @Override
    public Long getDyeQueryTotal(EmbedsCon con) {
        return blockRepository.getDyeQueryTotal(con);
    }

    @Override
    public List<UserDto> getDyePerson() {
        List<Long> ids = blockRepository.getDyePerson();
        List<UserDto> users = new ArrayList<>();
        for (Long id : ids) {
            UserDto user = userApplication.getUserByUserID(id);
            users.add(user);
        }

        return users;
    }

    @Override
    public List<BlockExtendDto> getNotSpecialBlocksByCondition(ApplicationCondition applicationCondition) {
        List<BlockExtend> blockExtends = blockRepository.selectNotSpecialBlockExtendByCondition(applicationCondition);
        if (blockExtends != null && blockExtends.size() > 0) {
            List<BlockExtendDto> blockExtendDTOs = new ArrayList<>();
            BlockExtendDto blockExtendDto;
            for (BlockExtend blockExtend : blockExtends) {
                blockExtendDto = new BlockExtendDto();
                BeanUtils.copyProperties(blockExtend, blockExtendDto);
                blockExtendDto.setStatusDesc(PathologyStatus.getNameByCode(blockExtend.getStatus()));
                blockExtendDto.setUnitDesc(paramSettingApplication.getNameByKeyAndCode(ParamKey.BlockUnit.toString(), blockExtend.getUnit()));
                blockExtendDto.setSpecialDyeDesc(paramSettingApplication.getSpecialDyeDesc(blockExtendDto.getSpecialDye()));
                blockExtendDto.setBiaoshiDesc(paramSettingApplication.getNameByKeyAndCode(ParamKey.BlockBiaoshi.toString(), blockExtend.getBiaoshi()));
                blockExtendDTOs.add(blockExtendDto);
            }
            return blockExtendDTOs;
        }
        return null;
    }

    @Override
    public Long countNotSpecialBlocksByCondition(ApplicationCondition applicationCondition) {
        return blockRepository.countNotSpecialBlockExtendByCondition(applicationCondition);
    }

    @Override
    public List<UserDto> getPrintPerson() {
        List<Long> ids = blockRepository.getPrintPerson();
        List<UserDto> users = new ArrayList<>();
        for (Long id : ids) {
            UserDto user = userApplication.getUserByUserID(id);
            users.add(user);
        }

        return users;
    }

    @Override
    public List<BlockDto> getBlockByCondition(BlockCondition blockCondition) {
        List<Block> blocks = blockRepository.selectBlockByCondition(blockCondition);
        return blocksToDto(blocks);
    }

    @Override
    public Long countBlockByCondition(BlockCondition condition) {
        return blockRepository.countBlockByCondition(condition);
    }

    @Override
    public List<SlideDto> getSlideInfoByIhcId(long ihcId) {
        List<Slide> slides = blockRepository.selectSlideByIhcId(ihcId);
        return slidesToDTOs(slides);
    }

    @Override
    public List<SlideDto> getSlideInfoByBlockIhcId(long blockIhcId) {
        List<Slide> slides = blockRepository.selectSlideByBlockIhcId(blockIhcId);
        return slidesToDTOs(slides);
    }

    @Override
    public BlockDto getSlideInfoByBlockIhcIdAndMarker(Long blochIhcId, String marker) {
        Block block = blockRepository.selectSlideByBlockIhcIdAndMarker(blochIhcId, marker);
        return blockToDto(block);
    }

    @Override
    public BlockDto getSlideInfoByBlockIdAndMarker(Long blockId, String marker) {
        Block block = blockRepository.selectSlideByBlockIdAndMarker(blockId, marker);
        return blockToDto(block);
    }

    public BlockDto blockToDto(Block block) {
        BlockDto blockDto = null;
        if (block != null) {
            blockDto = new BlockDto();
            BeanUtils.copyProperties(block, blockDto);
            blockDto.setSlideMarker(block.getMarker());
        }
        return blockDto;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void embedPause(TrackingDto trackingDto) {
        long userId = UserContext.getLoginUserID();
        Date dbNow = commonRepository.getDBNow();
        Long blockId = trackingDto.getBlockId();
        BlockDto blockDto = getBlockById(blockId);
        Integer operation = trackingDto.getOperation();
        if (operation.equals(TrackingOperation.embedPause.toCode())) {
            blockDto.setEmbedPause(true);
        } else {
            blockDto.setEmbedPause(false);
        }
        blockDto.setUpdateBy(userId);
        updateBlock(blockDto);
        trackingDto.setCreateBy(userId);
        trackingDto.setBlockId(blockId);
        trackingDto.setOperatorId(userId);
        trackingDto.setOperationTime(dbNow);
        trackingDto.setManualFlag(true);
        trackingDto.setInstrumentId(0L);
        trackingApplication.addTracking(trackingDto);
    }

    @Override
    public BlockDto getSimpleBlockById(long id) {
        BlockDto blockDto = null;
        Block block = blockRepository.selectByPrimaryKey(id);
        if (block != null) {
            blockDto = new BlockDto();
            BeanUtils.copyProperties(block, blockDto);
        }
        return blockDto;
    }

    @Override
    public void batchUpdateBlock(List<BlockDto> blockDTOs) {
        if (blockDTOs != null && blockDTOs.size() > 0) {
            List<Block> blocks = new ArrayList<>();
            Block block;
            for (BlockDto blockDto : blockDTOs) {
                if (blockDto != null) {
                    block = new Block();
                    BeanUtils.copyProperties(blockDto, block);
                    blocks.add(block);
                }
            }
            blockRepository.batchUpdate(blocks);
        }
    }

    @Override
    public List<BlockDetailDto> getFrozenBlockByCondition(GrossingCon con) {

        List<BlockDetail> blockDetails = blockRepository.getFrozenBlockByCondition(con);
        List<BlockDetailDto> data = new ArrayList<>();
        List<ParamSettingDto> blockBiaoshi = paramSettingApplication.getContentToListByKey(ParamKey.BlockBiaoshi.toString());
        List<ParamSettingDto> blockUnits = paramSettingApplication.getContentToListByKey(ParamKey.BlockUnit.toString());
        BlockDetailDto dto;
        for (BlockDetail blockDetail : blockDetails) {
            dto = new BlockDetailDto();
            BeanUtils.copyProperties(blockDetail, dto);
            if (blockDetail.getSecOperatorId() != null) {
                UserSimpleDto user = userApplication.getUserSimpleInfoById(blockDetail.getSecOperatorId());
                if (user != null) {
                    dto.setSecOperatorName(user.getFirstName());
                }
            }
            if (blockDetail.getStatus() != null) {
                dto.setStatusName(PathologyStatus.valueOf(blockDetail.getStatus()).toString());
                for (ParamSettingDto blockUnit : blockUnits) {
                    if (blockUnit.getCode().equals(blockDetail.getUnit())) {
                        dto.setCountName(blockDetail.getCount() + blockUnit.getName());
                        break;
                    }
                }
            } else {
                dto.setStatusName(PathologyStatus.PrepareGrossing.toString());
            }
            for (ParamSettingDto biaoshi : blockBiaoshi) {
                if (biaoshi.getCode().equals(blockDetail.getBiaoshi())) {
                    dto.setBiaoshiName(biaoshi.getName());
                    break;
                }
            }
            data.add(dto);
        }
        return data;
    }

    @Override
    public Long countFrozenBlockByCondition(GrossingCon con) {
        return blockRepository.countFrozenBlockByCondition(con);
    }

    @Override
    public List<InfoQueryDto> getEmbedsQuery(EmbedsCon con) {
        List<InfoQueryDto> dataList = blockRepository.getEmbedsQuery(con);
        List<InfoQueryDto> lists = new ArrayList<>();
        List<ParamSettingDto> BlockBiaoshis = paramSettingApplication.getContentToListByKey(ParamKey.BlockBiaoshi.toString());
        for (InfoQueryDto data : dataList) {
            if (data.getStatus() == 14) {
                for (ParamSettingDto blockBiaoshi : BlockBiaoshis) {
                    if (blockBiaoshi.getCode().equals(data.getBiaoShi())) {
                        data.setBiaoShiName(blockBiaoshi.getName());
                        break;
                    }
                }
                embedsAndSection(data);
            } else {
                for (ParamSettingDto blockBiaoshi : BlockBiaoshis) {
                    if (blockBiaoshi.getCode().equals(data.getBiaoShi())) {
                        data.setBiaoShiName(blockBiaoshi.getName());
                        break;
                    }
                }
                embedsAndSectionQuery(data);
            }
            lists.add(data);

        }
        return lists;
    }

    @Override
    public Long getEmbedsQueryTotal(EmbedsCon con) {
        Long embedsQueryTotal = blockRepository.getEmbedsQueryTotal(con);
        return embedsQueryTotal;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public List<String> addBlockScanResult(String blocks, BlockScanImageDto imagePath) {
        BlockScanImage image = new BlockScanImage();
        BeanUtils.copyProperties(imagePath, image);
        blockScanImageRepository.insert(image);
        if (blocks == null) {
            return new ArrayList<>();
        }
        String[] block = blocks.split(",");
        Long batchId = blockScanImageRepository.last();
        image.setBatchId(batchId);
        List<String> errorBlock = new ArrayList<>();
        for (String serialNumber : block) {
            String[] split = serialNumber.split(Config.serialNumberSeparator);
            if (split.length == 3) {
                serialNumber = serialNumber.replaceAll(Config.serialNumberSeparator, "");
                String pathNo = split[0];
                String blockSubId = split[1];
                String slideSubId = split[2];
                Integer scanLocation = imagePath.getScanLocation();
                if (scanLocation.equals(ScanLocation.Production.toCode())) {//制片确认批量扫描
                    SlideDto slideDto = this.getSlideConfirmBySlideNoAndSubId(serialNumber, slideSubId);
                    if (slideDto != null && slideDto.getPathId() != null) {
                        BlockScan blockScan = new BlockScan();
                        blockScan.setBatchId(batchId);
                        blockScan.setPathId(slideDto.getPathId());
                        blockScan.setBlockId(slideDto.getParentId());
                        blockScan.setSlideId(slideDto.getId());
                        blockScan.setBiaoshi(slideDto.getBiaoshi());
                        blockScan.setBlockSubId(slideDto.getBlockSubId());
                        blockScan.setMarker(slideDto.getMarker());
                        blockScan.setPatientName(ApplyType.getNameByCode(slideDto.getApplyType()));
                        blockScan.setSpecialDye(slideDto.getSpecialDye());
                        blockScan.setSubId(slideDto.getSubId());
                        blockScan.setPathNo(slideDto.getPathNo());
                        try {
                            blockScanRepository.insert(blockScan);
                        } catch (DuplicateKeyException e) {
                            log.warn("Record already exist " + serialNumber);
                        }
                    } else {
                        log.error("玻片号" + serialNumber + "不存在或不是待制片确认状态");
                        errorBlock.add(serialNumber);
                    }
                } else if (scanLocation.equals(ScanLocation.Archive.toCode())) {//存档批量扫描
                    ArchiveCondition archiveCondition = new ArchiveCondition();
                    archiveCondition.setPathNo(pathNo);
                    archiveCondition.setBlockSubId(blockSubId);
                    archiveCondition.setSlideSubId(slideSubId);
                    List<ArchiveDto> archiveDTOs = archiveApplication.getInfoForArchive(archiveCondition);
                    if (archiveDTOs != null && archiveDTOs.size() == 1) {
                        SlideDto slideDto = getSlideBySlideNoAndSubId(serialNumber, slideSubId);
                        BlockScan blockScan = new BlockScan();
                        blockScan.setBatchId(batchId);
                        blockScan.setPathId(slideDto.getPathId());
                        blockScan.setBlockId(slideDto.getParentId());
                        blockScan.setSlideId(slideDto.getId());
                        blockScan.setBiaoshi(slideDto.getBiaoshi());
                        blockScan.setBlockSubId(slideDto.getBlockSubId());
                        blockScan.setMarker(slideDto.getMarker());
                        blockScan.setPatientName(ApplyType.getNameByCode(slideDto.getApplyType()));
                        blockScan.setSpecialDye(slideDto.getSpecialDye());
                        blockScan.setSubId(slideDto.getSubId());
                        blockScan.setPathNo(slideDto.getPathNo());
                        try {
                            blockScanRepository.insert(blockScan);
                        } catch (DuplicateKeyException e) {
                            log.warn("Record already exist " + serialNumber);
                        }
                    } else {
                        log.error("玻片号" + serialNumber + "不存在或不是待存档状态");
                        errorBlock.add(serialNumber);
                    }
                }
            } else {
                log.error("Wrong block serialNumber " + serialNumber);
                errorBlock.add(serialNumber);
            }
        }
        if (!errorBlock.isEmpty()) {
            image.setErrorBlocks(errorBlock.toString());
            blockScanImageRepository.update(image);
        }

        return errorBlock;
    }

    @Override
    public List<BlockScanDto> getBlockScansByLocation(int location) {
        List<BlockScanDto> data = new ArrayList<>();
        List<BlockScan> blocks = blockScanRepository.selectByLocation(location);
        for (BlockScan block : blocks) {
            BlockScanDto dto = new BlockScanDto();
            BeanUtils.copyProperties(block, dto);
            dto.setId(block.getSlideId());
            dto.setSpecialDyeDesc(paramSettingApplication.getSpecialDyeDesc(block.getSpecialDye()));
            dto.setBiaoshiDesc(paramSettingApplication.getNameByKeyAndCode(ParamKey.BlockBiaoshi.toString(), dto.getBiaoshi()));
            dto.setApplyTypeDesc(block.getPatientName());
            dto.setStatus(PathologyStatus.PrepareCompletionConfirm.toCode());
            dto.setStatusDesc(PathologyStatus.PrepareCompletionConfirm.toString());
            data.add(dto);
        }
        return data;
    }

    @Override
    public BlockScanImageDto selectNewestImageByLocation(int location) {
        BlockScanImageDto image = new BlockScanImageDto();
        BlockScanImage data = blockScanImageRepository.selectNewestImageByLocation(location);
        if (data == null || data.getImagePath() == null) {
            return null;
        }
        BeanUtils.copyProperties(data, image);
        image.setImagePath("api/static/" + image.getImagePath());
        return image;
    }

    @Override
    public BlockScanImageDto getScanImage(String path) {
        BlockScanImageDto image = new BlockScanImageDto();
        BlockScanImage data = blockScanImageRepository.selectByPath(path);
        if (data == null) {
            return null;
        }
        BeanUtils.copyProperties(data, image);
        return image;
    }

    private void embedsAndSection(InfoQueryDto data) {
        if (data.getStatus() == null) {
            data.setStatusName(null);
        } else {
            data.setStatusName(PathologyStatus.valueOf(data.getStatus()).toString());
        }
    }

    private void embedsAndSectionQuery(InfoQueryDto data) {
        if (data.getStatus() == null) {
            data.setStatusName(null);
        } else {
            data.setStatusName(PathologyStatus.valueOf(data.getStatus()).toString());
        }

        if (data.getDyeInstrumentId() != null) {
            InstrumentDto dto = instrumentApplication.getInstrumentById(data.getDyeInstrumentId());
            if (dto != null) {
                data.setDyeInstrumentName(dto.getName());
            }
        }
        if (data.getMountingInstrumentId() != null) {
            InstrumentDto dto = instrumentApplication.getInstrumentById(data.getMountingInstrumentId());
            if (dto != null) {
                data.setMountingInstrumentName(dto.getName());
            }
        }
    }

    private SlideDto slideToDto(Slide slide) {
        SlideDto slideDto = null;
        if (slide != null) {
            slideDto = new SlideDto();
            BeanUtils.copyProperties(slide, slideDto);
            slideDto.setConfirmUser(userApplication.getUserSimpleInfoById(slide.getConfirmUser()));
            slideDto.setBiaoshiDesc(paramSettingApplication.getNameByKeyAndCode(ParamKey.BlockBiaoshi.toString(), slide.getBiaoshi()));
            slideDto.setSpecialDyeDesc(paramSettingApplication.getSpecialDyeDesc(slide.getSpecialDye()));
            PathologyDto pathologyDto = pathologyApplication.getSimplePathById(slide.getPathId());
            long parentId = slide.getParentId();
            BlockDto blockDto = getBlockById(parentId);
            slideDto.setPathNo(pathologyDto.getSerialNumber());
            slideDto.setPatientName(pathologyDto.getPatientName());
            slideDto.setStatusDesc(PathologyStatus.valueOf(slide.getStatus()).toString());
            slideDto.setBlockSubId(blockDto.getSubId());
            slideDto.setBlockSerialNumber(blockDto.getSerialNumber());
            slideDto.setGrossingUser(blockDto.getGrossingUser());
            slideDto.setGrossingBody(blockDto.getBodyPart());
            slideDto.setGrossingCount(blockDto.getCount());
            slideDto.setGrossingUnit(blockDto.getUnit());
            slideDto.setGrossingUnitDesc(blockDto.getUnitName());
            Integer applyType = pathologyDto.getApplyType();
            slideDto.setApplyType(applyType);
            slideDto.setApplyTypeDesc(ApplyType.getNameByCode(applyType));
            slideDto.setUpdateBy(slide.getUpdateBy());
            slideDto.setUpdateTime(slideDto.getUpdateTime());
        }
        return slideDto;
    }

    private List<SlideDto> slidesToDTOs(List<Slide> slides) {
        List<SlideDto> slideDTOs = null;
        if (CollectionUtils.isNotEmpty(slides)) {
            slideDTOs = new ArrayList<>();
            SlideDto slideDto;
            for (Slide slide : slides) {
                slideDto = slideToDto(slide);
                slideDTOs.add(slideDto);
            }
        }
        return slideDTOs;
    }

    /**
     * 查询是否需要更新特殊申请
     *
     * @param trackingId
     * @param userId
     * @return
     */
    private SpecialApplicationDto updateSpecialApplication(Long trackingId, Long userId) {
        SpecialApplicationDto specialApplicationDto = null;
        if (trackingId != null) {
            TrackingDto trackingDto = trackingApplication.getTrackingById(trackingId);
            if (trackingDto != null) {
                Integer operation = trackingDto.getOperation();
                if (operation.equals(TrackingOperation.specialDyeApply.toCode())) {
                    Long ihcBlockId = trackingDto.getInstrumentId();
                    IhcBlockDto ihcBlockDto = applicationIhcApplication.getIhcBlockById(ihcBlockId);
                    if (ihcBlockDto != null) {
                        String number = ihcBlockDto.getNumber();
                        if (!StringUtils.isBlank(number)) {
                            SpecialApplicationDto db = specialApplicationApplication.getByNumber(number);
                            if (db != null) {
                                Integer realStatus = specialApplicationApplication.getSlideMinStatusByTrackingId(trackingId);
                                if (!realStatus.equals(db.getStatus())) {
                                    db.setStatus(realStatus);
                                    db.setUpdateBy(userId);
                                    specialApplicationDto = db;
                                }
                            }
                        }
                    }
                }
            }
        }
        return specialApplicationDto;
    }

    @Override
    public Object getUpdatePathology(Object pathologyDto) {
        PathologyDto data = null;
        if (pathologyDto != null) {
            PathologyDto oldPath = (PathologyDto) pathologyDto;
            int pathStatus = oldPath.getStatus();
            if ((pathStatus != PathologyStatus.PrepareGrossing.toCode()) && (pathStatus < PathologyStatus.PrepareFirstDiagnose.toCode())) {
                Integer realStatus = getMinBlockStatusByPathId(oldPath.getId());
                if (pathStatus != realStatus) {
                    oldPath.setStatus(realStatus);
                    data = oldPath;
                }
            }

        }
        return data;
    }

    @Override
    public List<BlockDto> getBlockInfoByNumber(String number) {
        List<Block> blocks = blockRepository.selectBlockByNumber(number);
        return blocksToDto(blocks);
    }

    @Override
    public List<Long> getSlideIdByApplyId(Long applyId) {
        return blockRepository.selectSlideByApplyId(applyId);
    }
}
