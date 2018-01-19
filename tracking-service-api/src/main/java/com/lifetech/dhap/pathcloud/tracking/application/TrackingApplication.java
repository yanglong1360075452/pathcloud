package com.lifetech.dhap.pathcloud.tracking.application;

import com.lifetech.dhap.pathcloud.common.exception.BuzException;
import com.lifetech.dhap.pathcloud.tracking.application.condition.DistributeCondition;
import com.lifetech.dhap.pathcloud.tracking.application.condition.DoctorAdviceCondition;
import com.lifetech.dhap.pathcloud.tracking.application.condition.TrackingCondition;
import com.lifetech.dhap.pathcloud.tracking.application.dto.*;

import java.util.List;

/**
 * Track业务逻辑
 *
 * @author eric.guo@ostengar.com
 * @version 1.0
 * @time 2016-12-07-15:57
 */
public interface TrackingApplication {

    /**
     * 添加跟踪记录
     *
     * @param data
     */
    void addTracking(TrackingDto data);

    /**
     * 批量添加
     * @param data
     */
    void addTrackingBatch(List<TrackingDto> data);

    /**
     * 根据条件获取单条操作记录
     * @param con
     * @return
     */
    TrackingDto getTrackingByCondition(TrackingCondition con);

    /**
     * 根据ID获取Tracking记录
     * @param id
     * @return
     */
    TrackingDto getTrackingById(long id);

    /**
     * 根据条件获取多条操作记录
     * @param con
     * @return
     */
    List<TrackingDto> getTrackingsByCondition(TrackingCondition con);
    Long countTrackingByCondition(TrackingCondition condition);

    Long getBlockWaitTimeByCondition(TrackingCondition con);

    /**
     * 根据病理ID获取该病理制片跟踪信息
     * @param pathId
     * @return
     */
    List<TrackingDto> getBlockTracking(long pathId);

    /**
     * 获取特染病理的制片跟踪信息
     * @param pathId
     * @return
     */
    List<BlockTrackingDto> getIhcBlockTracking(long pathId, Integer specialDye,Long applyId);

    /**
     * 脱水篮是否被锁
     * @return
     */
    boolean getBasketNumberStatus(Long basketNumber);

    void deleteTrackingByCondition(TrackingCondition con);

    /**
     * 获取待派片列表
     * @return
     */
    List<PrepareDistributeDto> getPrepareDistribute(DistributeCondition condition);

    Long countPrepareDistribute(DistributeCondition con);

    /**
     * 根据病理ID查询带派片ID
     * @param pathId
     * @return
     */
    List<Long> getPrepareDistributeSlideIds(Long pathId);

    /**
     * 获取派片历史列表
     * @param condition
     * @return
     */
    List<DistributeHistoryDto> getDistributeHistory(DistributeCondition condition);

    Long countDistributeHistory(DistributeCondition con);

    /**
     * 根据病理号/用户查询已看玻片数
     * @param userId
     * @param slideIds
     * @return
     */
    long countWatchedSlide(long userId,List<Long> slideIds);

    /**
     * 根据蜡块ID获取最后一个切片操作者
     * @param blockId
     * @return
     */
    Long getSectionOperatorByBlockId(long blockId);

    Long getBlockWaitTimeByOperationAndTime(TrackingCondition condition);

    /**
     * 根据操作查询下一操作
     * @param operation
     * @return
     */
    Integer getPreOperationByOperation(int operation) throws BuzException;

    /**
     * 医嘱查询
     * @param con
     * @return
     */
    List<DoctorAdviceDto> getDoctorAdvice(DoctorAdviceCondition con);
    Long countDoctorAdvice(DoctorAdviceCondition con);


    List<BlockTrackingDto> getDoctorAdviceLog(Long applyId, Long blockId);

    TrackingDto getLastPauseEmbed(long blockId);

    /**
     * 根据蜡块特染申请ID查询对应tracing记录ID
     * @param blockIhcId
     * @return
     */
    Long getBlockIhcTrackingIdByBlockIhcId(long blockIhcId);

    /**
     * 根据Tracking ID查询蜡块特染申请ID
     * @param id
     * @return
     */
    Long getBlockIhcIdById(long id);
}
