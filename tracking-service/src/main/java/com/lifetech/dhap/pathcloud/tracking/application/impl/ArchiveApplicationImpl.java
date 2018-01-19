package com.lifetech.dhap.pathcloud.tracking.application.impl;

import com.lifetech.dhap.pathcloud.application.application.PathologyApplication;
import com.lifetech.dhap.pathcloud.application.application.dto.PathologyDto;
import com.lifetech.dhap.pathcloud.common.data.PathologyStatus;
import com.lifetech.dhap.pathcloud.common.domain.CommonRepository;
import com.lifetech.dhap.pathcloud.common.exception.BuzException;
import com.lifetech.dhap.pathcloud.common.exception.BuzExceptionCode;
import com.lifetech.dhap.pathcloud.notification.NotificationApplication;
import com.lifetech.dhap.pathcloud.notification.dto.NotificationDto;
import com.lifetech.dhap.pathcloud.notification.dto.NotificationReceiverDto;
import com.lifetech.dhap.pathcloud.notification.infrastructure.code.NotificationReceiverType;
import com.lifetech.dhap.pathcloud.notification.infrastructure.code.NotificationSource;
import com.lifetech.dhap.pathcloud.notification.infrastructure.code.NotificationType;
import com.lifetech.dhap.pathcloud.security.UserContext;
import com.lifetech.dhap.pathcloud.setting.application.ParamSettingApplication;
import com.lifetech.dhap.pathcloud.setting.application.infrastructure.code.ParamKey;
import com.lifetech.dhap.pathcloud.tracking.application.ArchiveApplication;
import com.lifetech.dhap.pathcloud.tracking.application.TrackingApplication;
import com.lifetech.dhap.pathcloud.tracking.application.condition.ArchiveCondition;
import com.lifetech.dhap.pathcloud.tracking.application.condition.SlideArchivingCon;
import com.lifetech.dhap.pathcloud.tracking.application.dto.*;
import com.lifetech.dhap.pathcloud.tracking.application.infrastructure.code.ArchiveStatus;
import com.lifetech.dhap.pathcloud.tracking.application.infrastructure.code.BorrowStatus;
import com.lifetech.dhap.pathcloud.tracking.application.infrastructure.code.TrackingOperation;
import com.lifetech.dhap.pathcloud.tracking.domain.BlockArchiveRepository;
import com.lifetech.dhap.pathcloud.tracking.domain.BlockBorrowRepository;
import com.lifetech.dhap.pathcloud.tracking.domain.BlockRepository;
import com.lifetech.dhap.pathcloud.tracking.domain.BlockScanRepository;
import com.lifetech.dhap.pathcloud.tracking.domain.model.*;
import com.lifetech.dhap.pathcloud.tracking.infrastructure.code.AbnormalHandle;
import com.lifetech.dhap.pathcloud.user.application.PermissionApplication;
import com.lifetech.dhap.pathcloud.user.application.UserApplication;
import com.lifetech.dhap.pathcloud.user.application.condition.UserCondition;
import com.lifetech.dhap.pathcloud.user.application.dto.UserDto;
import com.lifetech.dhap.pathcloud.common.data.Permission;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by LiuMei on 2017-06-07.
 */
@Service
public class ArchiveApplicationImpl implements ArchiveApplication {

    @Autowired
    private ParamSettingApplication paramSettingApplication;

    @Autowired
    private BlockArchiveRepository blockArchiveRepository;

    @Autowired
    private TrackingApplication trackingApplication;

    @Autowired
    private CommonRepository commonRepository;

    @Autowired
    private BlockRepository blockRepository;

    @Autowired
    private PathologyApplication pathologyApplication;

    @Autowired
    private BlockBorrowRepository blockBorrowRepository;

    @Autowired
    private NotificationApplication notificationApplication;

    @Autowired
    private UserApplication userApplication;

    @Autowired
    private PermissionApplication permissionApplication;

    @Autowired
    private BlockScanRepository blockScanRepository;

    @Override
    public List<ArchiveDto> getInfoForArchive(ArchiveCondition condition) {
        List<Slide> slides = blockArchiveRepository.selectInfoForArchiveByCondition(condition);
        if (CollectionUtils.isNotEmpty(slides)) {
            List<ArchiveDto> archiveDtoList = new ArrayList<>();
            ArchiveDto archiveDto;
            for (Slide slide : slides) {
                archiveDto = new ArchiveDto();
                archiveDto.setMarker(slide.getMarker());
                archiveDto.setBlockSubId(slide.getBlockSubId());
                archiveDto.setSlideSubId(slide.getSubId());
                archiveDto.setPathNo(slide.getPathNo());
                archiveDto.setPathId(slide.getPathId());
                Integer biaoshi = slide.getBiaoshi();
                archiveDto.setBiaoshi(biaoshi);
                archiveDto.setBiaoshiDesc(paramSettingApplication.getNameByKeyAndCode(ParamKey.BlockBiaoshi.toString(), biaoshi));
                Integer status = slide.getStatus();
                archiveDto.setStatus(status);
                String statusDesc = PathologyStatus.getNameByCode(status);
                if (statusDesc == null) {
                    statusDesc = ArchiveStatus.getNameByCode(status);
                }
                archiveDto.setStatusDesc(statusDesc);
                archiveDto.setSlideId(slide.getId());
                archiveDtoList.add(archiveDto);
            }
            return archiveDtoList;
        } else {
            return null;
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void archiveConfirm(SlideArchiveConfirmDto slideArchiveConfirmDto) throws BuzException {
        Integer archivingMethod = slideArchiveConfirmDto.getArchivingMethod();
        String archivingNo = slideArchiveConfirmDto.getArchivingNo();
        List<Long> slideIds = slideArchiveConfirmDto.getSlideIds();
        long userId = UserContext.getLoginUserID();
        Date dbNow = commonRepository.getDBNow();
        Integer status;
        if (TrackingOperation.drying.toCode().equals(archivingMethod)) {
            status = ArchiveStatus.drying.toCode();
        } else if (TrackingOperation.archive.toCode().equals(archivingMethod)) {
            status = ArchiveStatus.archive.toCode();
        } else {
            throw new BuzException(BuzExceptionCode.ErrorParam);
        }
        if (CollectionUtils.isNotEmpty(slideIds)) {
            List<TrackingDto> trackingDtoList = new ArrayList<>();
            for (Long slideId : slideIds) {
                Block slide = blockRepository.selectByPrimaryKey(slideId);
                BlockArchive blockArchive;
                //未做过存档操作
                if (slide.getStatus().equals(PathologyStatus.PrepareArchiving.toCode())) {
                    slide.setStatus(PathologyStatus.Archiving.toCode());
                    slide.setUpdateBy(userId);
                    blockRepository.updateByPrimaryKey(slide);
                    blockArchive = new BlockArchive();
                    blockArchive.setSlideId(slideId);
                    blockArchive.setStatus(status);
                    blockArchive.setArchiveBox(archivingNo);
                    blockArchive.setCreateBy(userId);
                    blockArchive.setSlideSubId(slide.getSubId());
                    blockArchive.setIhcMarker(slide.getMarker());
                    long blockId = slide.getParentId();
                    long pathId = slide.getPathId();
                    if (blockId != 0) {
                        Block block = blockRepository.selectByPrimaryKey(blockId);
                        blockArchive.setBlockSubId(block.getSubId());
                    } else {
                        blockArchive.setBlockSubId("0");
                    }
                    blockArchive.setBlockId(blockId);
                    blockArchive.setPathId(pathId);
                    PathologyDto pathologyDto = pathologyApplication.getSimplePathById(pathId);
                    blockArchive.setSerialNumber(pathologyDto.getSerialNumber());
                    blockArchive.setPatientName(pathologyDto.getPatientName());
                    blockArchiveRepository.insert(blockArchive);
                } else {
                    blockArchive = blockArchiveRepository.selectBySlideId(slideId);
                    Integer archiveStatus = blockArchive.getStatus();
                    if (archiveStatus.equals(ArchiveStatus.borrow.toCode())) {
                        throw new BuzException(BuzExceptionCode.StatusNotMatch);
                    }
                    if (archiveStatus.equals(ArchiveStatus.archive.toCode()) && status.equals(ArchiveStatus.drying.toCode())) {
                        throw new BuzException(BuzExceptionCode.StatusNotMatch);
                    }
                    blockArchive.setStatus(status);
                    blockArchive.setArchiveBox(archivingNo);
                    blockArchive.setUpdateBy(userId);
                    blockArchiveRepository.updateByPrimaryKey(blockArchive);
                }
                TrackingDto track = new TrackingDto();
                track.setCreateBy(userId);
                track.setOperatorId(userId);
                track.setOperation(archivingMethod);
                track.setNote(archivingNo);
                track.setBlockId(slideId);
                track.setOperationTime(dbNow);
                trackingDtoList.add(track);
            }
            trackingApplication.addTrackingBatch(trackingDtoList);
            blockScanRepository.deleteBatch(slideIds);
        }
    }

    @Override
    public List<SlideArchiveInfoDto> getSlidesArchivingInfo(SlideArchivingCon con) {
        List<SlideArchiveInfoDto> dtos = blockArchiveRepository.getSlidesArchivingInfo(con);
        if (dtos != null) {
            if (dtos.size() > 0) {
                for (SlideArchiveInfoDto dto : dtos) {
                    if (dto.getStatus().equals(ArchiveStatus.drying.toCode())) {
                        dto.setArchiveBox(null);
                        dto.setArchiveCreateBy(null);
                        dto.setArchiveCreateTime(null);
                 /*       Long dryingCreateBy = dto.getDryingCreateBy();
                        UserDto user= userApplication.getUserByUserID(dryingCreateBy);
                        if (user != null){
                            dto.setDryingCreateByDesc(user.getFirstName());
                        }*/
                        dto.setStatusDesc(ArchiveStatus.getNameByCode(dto.getStatus()));
                    }

                    if (dto.getStatus().equals(ArchiveStatus.archive.toCode())) {
                        Long archiveCreateBy = dto.getArchiveCreateBy();
                        UserDto user = userApplication.getUserByUserID(archiveCreateBy);
                        if (user != null) {
                            dto.setArchiveCreateByDesc(user.getFirstName());
                        }
                        if (dto.getDryingCreateBy() != null) {
                            UserDto user1 = userApplication.getUserByUserID(dto.getDryingCreateBy());
                            dto.setDryingCreateByDesc(user1.getFirstName());
                        }
                        dto.setStatusDesc(ArchiveStatus.getNameByCode(dto.getStatus()));
                    }

                    if (dto.getStatus().equals(ArchiveStatus.borrow.toCode())) {
                        Integer overdue = dto.getOverdue();
                        if (overdue <= 0) {
                            dto.setOverdue(0);
                            dto.setStatusDesc(ArchiveStatus.borrow.toString());
                        } else {
                            dto.setStatusDesc("逾期");
                        }
                        if (dto.getArchiveStatus().equals(ArchiveStatus.drying.toCode())) {
                            dto.setArchiveBox(null);
                            dto.setArchiveCreateBy(null);
                            dto.setArchiveCreateTime(null);
                  /*              Long dryingCreateBy = dto.getDryingCreateBy();
                                UserDto user= userApplication.getUserByUserID(dryingCreateBy);
                                if (user != null){
                                    dto.setDryingCreateByDesc(user.getFirstName());
                                }*/
                            dto.setArchiveStatusDesc(ArchiveStatus.getNameByCode(dto.getArchiveStatus()));
                        } else if (dto.getArchiveStatus().equals(ArchiveStatus.archive.toCode())) {
                            Long archiveCreateBy = dto.getArchiveCreateBy();
                            UserDto user = userApplication.getUserByUserID(archiveCreateBy);
                            if (user != null) {
                                dto.setArchiveCreateByDesc(user.getFirstName());
                            }
                             /*   if (dto.getDryingCreateBy() != null){
                                    UserDto user1 = userApplication.getUserByUserID(dto.getDryingCreateBy());
                                    dto.setDryingCreateByDesc(user1.getFirstName());
                                }*/
                            dto.setArchiveStatusDesc(ArchiveStatus.getNameByCode(dto.getArchiveStatus()));
                        }

                    }


                }
            }
        }
        return dtos;
    }

    @Override
    public List<BlockArchiveDto> getInfoForBorrow(ArchiveCondition condition) throws BuzException {
        List<BlockArchive> blockArchives = blockArchiveRepository.selectInfoForBorrowByCondition(condition);
        if (blockArchives != null && blockArchives.size() > 0) {
            List<BlockArchiveDto> archiveDtoList = new ArrayList<>();
            BlockArchiveDto archiveDto;
            for (BlockArchive archive : blockArchives) {
                archiveDto = new BlockArchiveDto();
                BeanUtils.copyProperties(archive, archiveDto);
                archiveDto.setStatusDesc(ArchiveStatus.getNameByCode(archive.getStatus()));
                archiveDtoList.add(archiveDto);
            }
            return archiveDtoList;
        } else {
            return null;
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void borrowConfirm(List<BlockBorrowDto> blockBorrowDtos) throws BuzException {
        if (blockBorrowDtos != null && blockBorrowDtos.size() > 0) {
            BlockBorrow blockBorrow;
            for (BlockBorrowDto blockBorrowDto : blockBorrowDtos) {
                blockBorrow = new BlockBorrow();
                BeanUtils.copyProperties(blockBorrowDto, blockBorrow);
                Long archiveId = blockBorrow.getArchiveId();
                BlockArchive blockArchive = blockArchiveRepository.selectByPrimaryKey(archiveId);
                Integer status = blockArchive.getStatus();
                if (status.equals(ArchiveStatus.borrow.toCode())) {
                    throw new BuzException(BuzExceptionCode.StatusNotMatch);
                }
                blockBorrow.setArchiveStatus(status);
                blockBorrow.setStatus(BorrowStatus.Borrow.toCode());
                blockBorrowRepository.insert(blockBorrow);

                blockArchive.setStatus(ArchiveStatus.borrow.toCode());
                blockArchive.setUpdateBy(blockBorrow.getCreateBy());
                blockArchiveRepository.updateByPrimaryKey(blockArchive);
            }
        }
    }

    @Override
    public Long getSlidesArchivingInfoTotal(SlideArchivingCon con) {
        return blockArchiveRepository.getSlidesArchivingInfoTotal(con);
    }

    @Override
    public List<BrrowHistoryDto> getSlidesBrrowHistory(Long blockArchiveId) {

        List<BrrowHistoryDto> dtos = blockBorrowRepository.getSlidesBrrowHistory(blockArchiveId);
        List<BrrowHistoryDto> lists = new ArrayList();
        if (dtos != null && dtos.size() > 0) {
            for (BrrowHistoryDto dto : dtos) {
                Integer overdue = dto.getOverdue();
                if (overdue != null) {
                    if (overdue < 0) {
                        dto.setOverdue(0);
                    }

                }
                lists.add(dto);
            }

        }

        return lists;
    }

    @Override
    public List<BlockArchiveDto> getArchivingInfo(Long pid) {
        List<BlockArchiveDto> dtos = blockArchiveRepository.getArchivingInfo(pid);
        if (dtos != null && dtos.size() > 0) {
            for (BlockArchiveDto dto : dtos) {
                if (dto.getSlideId() == null) {
                    dto.setTypeDesc("蜡块");
                } else {
                    dto.setTypeDesc("玻片");
                }
                dto.setStatusDesc(ArchiveStatus.getNameByCode(dto.getStatus()));
            }
        }
        return dtos;
    }

    @Override
    public List<SlideArchiveInfoDto> getSlidesBorrowInfo(ArchiveCondition con) {
        List<SlideArchiveInfoDto> data = blockBorrowRepository.selectSlidesBorrowInfo(con);
        for (SlideArchiveInfoDto slideArchiveInfoDto : data) {
            slideArchiveInfoDto.setArchiveStatusDesc(ArchiveStatus.getNameByCode(slideArchiveInfoDto.getArchiveStatus()));
            if (slideArchiveInfoDto.getOverdue() < 0) {
                slideArchiveInfoDto.setOverdue(0);
            }
        }
        return data;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void slideBackConfirm(SlideBackConfirmDto dto) {
        if (dto.getBorrows() != null) {
            for (Long borrow : dto.getBorrows()) {
                BlockBorrow po = blockBorrowRepository.selectByPrimaryKey(borrow);
                if (po != null) {
                    BlockArchive archive = blockArchiveRepository.selectByPrimaryKey(po.getArchiveId());
                    archive.setStatus(po.getArchiveStatus());
                    archive.setUpdateBy(dto.getUpdateBy());
                    blockArchiveRepository.updateByPrimaryKey(archive);

                    po.setUpdateBy(dto.getUpdateBy());
                    po.setRealBack(new Date());
                    po.setStatus(BorrowStatus.Back.toCode());
                    blockBorrowRepository.updateByPrimaryKey(po);
                }
            }
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void archiveNotification() {
        UserCondition userCondition = new UserCondition();
        userCondition.setSize(Integer.MAX_VALUE);
        userCondition.setPermissionId(permissionApplication.getByCode(Permission.Archive.toCode()).getId());
        userCondition.setStatus(true);
        List<UserDto> archiveUsers = userApplication.getUserByCondition(userCondition);

        List<SlideArchiveInfoDto> overdue = blockBorrowRepository.selectOverdueBorrowInfo();
        for (SlideArchiveInfoDto borrow : overdue) {
            NotificationDto notification = new NotificationDto();
            notification.setPathId(borrow.getPathId());
            if (borrow.getSlideSubId() != null) {
                notification.setSubject(borrow.getSerialNumber() + "-" + borrow.getBlockSubId() + "-" + borrow.getSlideSubId() + "待归还");
            } else {
                notification.setSubject(borrow.getSerialNumber() + "-" + borrow.getBlockSubId() + "待归还");
            }
            notification.setSource(NotificationSource.archive.toCode());
            notification.setType(NotificationType.withoutChoice.toCode());
            notification.setBlockId(borrow.getBlockId());
            notification.setBlockStatus(PathologyStatus.PrepareArchiving.toCode());//TODO
            notification.setAbnormalHandle(AbnormalHandle.Notification.toCode());
            notification.setReadFlag(false);
            notification.setReceiverType(NotificationReceiverType.group);
            notification.setReceiverId(borrow.getBorrowID());
            notification.setCreateBy(1L);
            notification.setNote("");
            notificationApplication.addNotification(notification);

            for (UserDto archiveUser : archiveUsers) {
                NotificationReceiverDto receiver = new NotificationReceiverDto();
                receiver.setReadFlag(false);
                receiver.setReceiverId(archiveUser.getId());
                receiver.setNotificationId(notification.getId());
                notificationApplication.addNotificationReceiver(receiver);
            }
        }
    }

    @Override
    public SlideArchiveInfoDto getBlockBorrowDetail(Long borrowId) {
        return blockBorrowRepository.selectBlockBorrowDetail(borrowId);
    }
}
