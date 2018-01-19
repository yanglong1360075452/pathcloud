package com.lifetech.dhap.pathcloud.tracking.application;

import com.lifetech.dhap.pathcloud.common.exception.BuzException;
import com.lifetech.dhap.pathcloud.tracking.application.condition.ArchiveCondition;
import com.lifetech.dhap.pathcloud.tracking.application.condition.SlideArchivingCon;
import com.lifetech.dhap.pathcloud.tracking.application.dto.*;

import java.util.List;

/**
 * Created by LiuMei on 2017-06-07.
 */
public interface ArchiveApplication {

    /**
     * 存档查询信息
     * @param condition
     * @return
     */
    List<ArchiveDto> getInfoForArchive(ArchiveCondition condition) throws BuzException;

    /**
     * 玻片存档
     * @param slideArchiveConfirmDto
     */
    void archiveConfirm(SlideArchiveConfirmDto slideArchiveConfirmDto) throws BuzException;

    List<SlideArchiveInfoDto> getSlidesArchivingInfo(SlideArchivingCon con);

    /**
     * 查询玻片借阅信息
     * @param con
     * @return
     */
    List<SlideArchiveInfoDto> getSlidesBorrowInfo(ArchiveCondition con);

    /**
     * 借阅归还
     * @param dto
     */
    void slideBackConfirm(SlideBackConfirmDto dto);

    /**
     * 借阅查询信息
     * @param condition
     * @return
     * @throws BuzException
     */
    List<BlockArchiveDto> getInfoForBorrow(ArchiveCondition condition) throws BuzException;

    /**
     * 借阅
     * @param blockBorrowDtos
     * @throws BuzException
     */
    void borrowConfirm(List<BlockBorrowDto> blockBorrowDtos) throws BuzException;

    Long getSlidesArchivingInfoTotal(SlideArchivingCon con);

    /**
     * 借阅超时检查, 超时未归还发送消息通知给有归档权限的用户
     */
    void archiveNotification();

    SlideArchiveInfoDto getBlockBorrowDetail(Long borrowId);

    List<BrrowHistoryDto> getSlidesBrrowHistory(Long blockArchiveId);

    List<BlockArchiveDto> getArchivingInfo(Long pid);

}
