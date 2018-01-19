package com.lifetech.dhap.pathcloud.notification.application.impl;

import com.lifetech.dhap.pathcloud.application.application.PathologyApplication;
import com.lifetech.dhap.pathcloud.application.application.dto.PathologyDto;
import com.lifetech.dhap.pathcloud.application.application.dto.PathologyExpandDto;
import com.lifetech.dhap.pathcloud.common.data.PathologyStatus;
import com.lifetech.dhap.pathcloud.common.exception.BuzException;
import com.lifetech.dhap.pathcloud.common.exception.BuzExceptionCode;
import com.lifetech.dhap.pathcloud.notification.NotificationApplication;
import com.lifetech.dhap.pathcloud.notification.condition.NotificationCon;
import com.lifetech.dhap.pathcloud.notification.domain.NotificationReceiverRepository;
import com.lifetech.dhap.pathcloud.notification.domain.NotificationRepository;
import com.lifetech.dhap.pathcloud.notification.domain.model.Notification;
import com.lifetech.dhap.pathcloud.notification.domain.model.NotificationReceiver;
import com.lifetech.dhap.pathcloud.notification.dto.NotificationDealDto;
import com.lifetech.dhap.pathcloud.notification.dto.NotificationDetail;
import com.lifetech.dhap.pathcloud.notification.dto.NotificationDto;
import com.lifetech.dhap.pathcloud.notification.dto.NotificationReceiverDto;
import com.lifetech.dhap.pathcloud.notification.infrastructure.code.NotificationReceiverType;
import com.lifetech.dhap.pathcloud.notification.infrastructure.code.NotificationSource;
import com.lifetech.dhap.pathcloud.notification.infrastructure.code.NotificationType;
import com.lifetech.dhap.pathcloud.security.UserContext;
import com.lifetech.dhap.pathcloud.tracking.application.ArchiveApplication;
import com.lifetech.dhap.pathcloud.tracking.application.BlockApplication;
import com.lifetech.dhap.pathcloud.tracking.application.TrackingApplication;
import com.lifetech.dhap.pathcloud.tracking.application.condition.TrackingCondition;
import com.lifetech.dhap.pathcloud.tracking.application.dto.BlockDetailDto;
import com.lifetech.dhap.pathcloud.tracking.application.dto.BlockDto;
import com.lifetech.dhap.pathcloud.tracking.application.dto.DealNotificationDto;
import com.lifetech.dhap.pathcloud.tracking.application.dto.SlideArchiveInfoDto;
import com.lifetech.dhap.pathcloud.tracking.application.infrastructure.code.ArchiveStatus;
import com.lifetech.dhap.pathcloud.tracking.application.infrastructure.code.TrackingOperation;
import com.lifetech.dhap.pathcloud.user.application.UserApplication;
import com.lifetech.dhap.pathcloud.user.application.dto.UserDto;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author eric.guo@ostengar.com
 * @version 1.0
 * @time 2017-01-05-13:35
 */
@Service("notificationApplication")
public class NotificationApplicationImpl implements NotificationApplication {

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private BlockApplication blockApplication;

    @Autowired
    private UserApplication userApplication;

    @Autowired
    private PathologyApplication pathologyApplication;

    @Autowired
    private NotificationReceiverRepository notificationReceiverRepository;

    @Autowired
    private TrackingApplication trackingApplication;

    @Autowired
    private ArchiveApplication archiveApplication;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addNotification(NotificationDto dto) {
        if(dto == null || dto.getSubject() == null || dto.getReceiverId() == null || dto.getCreateBy() == null || dto.getSource() == null){
            throw new BuzException(BuzExceptionCode.ErrorParam);
        }

        Notification notification = new Notification();
        BeanUtils.copyProperties(dto, notification);
        notificationRepository.insert(notification);
        dto.setId(notificationRepository.last());

        if(dto.getReceiverType().equals(NotificationReceiverType.personal)){
            NotificationReceiver receiver = new NotificationReceiver();
            receiver.setNotificationId(dto.getId());
            receiver.setReceiverId(dto.getReceiverId());
            receiver.setReadFlag(false);
            notificationReceiverRepository.insert(receiver);
        }
    }

    @Override
    public List<NotificationDto> getNotificationByCondition(NotificationCon con) {
        List<Notification> notifications = notificationRepository.selectByCondition(con);

        ArrayList<NotificationDto> data = new ArrayList<>();
        for (Notification notification : notifications) {
            NotificationDto dto = new NotificationDto();
            BeanUtils.copyProperties(notification, dto);
            data.add(dto);
        }
        return data;
    }

    @Override
    public Long countNotificationByCondition(NotificationCon con) {
        return notificationRepository.countByCondition(con);
    }

    @Override
    public NotificationDetail getNotificationDetail(Long id) {
        Notification notification = notificationRepository.selectByPrimaryKey(id);

        NotificationDetail detail = new NotificationDetail();
        BeanUtils.copyProperties(notification, detail);
        detail.setSourceName(NotificationSource.getNameByCode(notification.getSource()));

        UserDto user = userApplication.getUserByUserID(notification.getCreateBy());
        if(user != null){
            detail.setSender(user.getFirstName());
        }
        if(notification.getBlockId() != null){
            BlockDetailDto block = blockApplication.getBlockDetail(notification.getBlockId());

            if (block.getOperatorId() != null) {
                UserDto operator = userApplication.getUserByUserID(block.getOperatorId());
                if (operator != null) {
                    block.setOperatorName(operator.getFirstName());
                }
            }
            if (block.getStatus() != null) {
                block.setStatusName(PathologyStatus.valueOf(block.getStatus()).toString());
            } else {
                block.setStatusName(PathologyStatus.PrepareGrossing.toString());
                block.setCountName("");
            }

            detail.setBlockId(block.getBlockId());
            detail.setPatientName(block.getName());
            detail.setPathologyNumber(block.getPathologyNumber());
            if(!block.getStatus().equals(notification.getBlockStatus())){
                detail.setType(NotificationType.withoutChoice.toCode());//不需要处理了
            }

            TrackingCondition trackCon = new TrackingCondition();
            trackCon.setBlockId(block.getBlockId());
            if(block.getParentId() != null){
                BlockDto dto = blockApplication.getBlockById(block.getParentId());
                block.setSlideId(block.getSubId());
                block.setSubId(dto.getSubId());
                detail.setBlockNumber(dto.getSubId());
            }else{
                detail.setBlockNumber(block.getSubId());
            }

            if(notification.getSubject().endsWith("重补取")){//TODO 按中文后缀判断下一个操作, 不是特别合理
                BlockDto b = blockApplication.getBlockForWaitTime(block.getPathologyId(), PathologyStatus.PrepareGrossing.toCode(), notification.getCreateTime());
                if(b == null){
                    block.setWaitTime(System.currentTimeMillis() - notification.getCreateTime().getTime());
                }else{
                    block.setWaitTime(b.getCreateTime().getTime() - notification.getCreateTime().getTime());
                }
            }else if(notification.getSubject().endsWith("重切") || notification.getSubject().endsWith("深切")){
                BlockDto b = blockApplication.getBlockForWaitTime(block.getBlockId(), PathologyStatus.PrepareSection.toCode(), notification.getCreateTime());
                if(b == null){
                    block.setWaitTime(System.currentTimeMillis() - notification.getCreateTime().getTime());
                }else{
                    block.setWaitTime(b.getCreateTime().getTime() - notification.getCreateTime().getTime());
                }
            }else if(notification.getBlockStatus().equals(PathologyStatus.PrepareCompletionConfirm.toCode())){
                trackCon.setNextOperation(TrackingOperation.completionConfirm.toCode());
                trackCon.setOperation(trackingApplication.getPreOperationByOperation(TrackingOperation.completionConfirm.toCode()));
            }else if(notification.getBlockStatus().equals(PathologyStatus.PrepareGrossingConfirm.toCode())){
                trackCon.setNextOperation(TrackingOperation.grossingConfirm.toCode());
                trackCon.setOperation(trackingApplication.getPreOperationByOperation(TrackingOperation.grossingConfirm.toCode()));
            }else if(notification.getBlockStatus().equals(PathologyStatus.PrepareDehydrate.toCode())) {
                trackCon.setNextOperation(TrackingOperation.dehydrate.toCode());
                trackCon.setOperation(trackingApplication.getPreOperationByOperation(TrackingOperation.dehydrate.toCode()));
            }else if(notification.getBlockStatus().equals(PathologyStatus.PrepareEmbed.toCode())) {
                trackCon.setNextOperation(TrackingOperation.embed.toCode());
                trackCon.setOperation(trackingApplication.getPreOperationByOperation(TrackingOperation.embed.toCode()));
            }else if(notification.getBlockStatus().equals(PathologyStatus.PrepareDye.toCode())) {
                trackCon.setNextOperation(TrackingOperation.dye.toCode());
                trackCon.setOperation(trackingApplication.getPreOperationByOperation(TrackingOperation.dye.toCode()));
            }else if(notification.getBlockStatus().equals(PathologyStatus.PrepareSection.toCode())) {
                trackCon.setNextOperation(TrackingOperation.section.toCode());
                trackCon.setOperation(trackingApplication.getPreOperationByOperation(TrackingOperation.section.toCode()));
            }else if(notification.getSubject().endsWith("待归还")){
                SlideArchiveInfoDto borrow = archiveApplication.getBlockBorrowDetail(notification.getReceiverId());
                if(borrow.getStatus().equals(ArchiveStatus.borrow.toCode())){
                    detail.setStatus(PathologyStatus.PrepareArchiving.toCode());
                    block.setStatusName(PathologyStatus.PrepareArchiving.toString());
                }else{
                    detail.setStatus(PathologyStatus.Archiving.toCode());
                    block.setStatusName(PathologyStatus.Archiving.toString());
                }
                block.setOperatorName(borrow.getBorrowName());
                block.setOperationTime(borrow.getCreateTime());
                block.setWaitTime(borrow.getOverdue() * 1000L);
            }
            if(block.getWaitTime() == null){
                Long waitTime = trackingApplication.getBlockWaitTimeByCondition(trackCon);
                if(waitTime != null){
                    block.setWaitTime(waitTime*1000);
                }
            }

            detail.setStatus(block.getStatus());
            detail.setDetail(block);
        }else{
            PathologyExpandDto pathologyDto = pathologyApplication.getPathologyExpandById(notification.getPathId());
            if(pathologyDto != null){
                detail.setPathologyNumber(pathologyDto.getSerialNumber());
                detail.setPatientName(pathologyDto.getPatientName());
                detail.setStatus(pathologyDto.getStatus());
                if(!pathologyDto.getStatus().equals(PathologyStatus.PrepareGrossing.toCode())){
                    detail.setType(2);
                }

//                BlockDto b = blockApplication.getBlockForWaitTime(pathologyDto.getId(), PathologyStatus.PrepareGrossing.toCode(), notification.getCreateTime());
//                if(b == null){
//                    block.setWaitTime(System.currentTimeMillis() - notification.getCreateTime().getTime());
//                }else{
//                    block.setWaitTime(b.getCreateTime().getTime() - notification.getCreateTime().getTime());
//                }
            }
        }
        NotificationReceiver receiver = new NotificationReceiver();
        receiver.setNotificationId(notification.getId());
        receiver.setReceiverId(UserContext.getLoginUserID());
        receiver.setReadFlag(true);
        notificationReceiverRepository.updateByPrimaryKey(receiver);
        return detail;
    }

    @Override
    public Boolean getNotificationForTaskCheck(Long pathologyId, Long blockId, Integer status) {
        Notification notification = notificationRepository.checkNotification(pathologyId, blockId, status);
        return notification != null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void dealNotification(NotificationDealDto dto) {
        DealNotificationDto data = new DealNotificationDto();
        BeanUtils.copyProperties(dto, data);
        if(dto.getBlockId() != null){
            BlockDetailDto block = blockApplication.getBlockDetail(dto.getBlockId());
            data.setSource(block.getStatus() - 9);//TODO， 按状态判断消息的来源工作站，目前的功能对应没问题，方式不是很合理
        }else{
            if(dto.getDealType().equals(3)){//异常终止，待取材的病理
                PathologyDto pathology = new PathologyDto();
                pathology.setId(dto.getPathId());
                pathology.setStatus(PathologyStatus.ErrorEnding.toCode());
                pathology.setUpdateBy(dto.getCreateBy());
                pathologyApplication.updatePathology(pathology);

                return;
            }
        }
        blockApplication.abnormalBlockDeal(data);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addNotificationReceiver(NotificationReceiverDto dto) {
        NotificationReceiver notificationReceiver = new NotificationReceiver();
        BeanUtils.copyProperties(dto, notificationReceiver);
        notificationReceiverRepository.insert(notificationReceiver);
    }
}
