package com.lifetech.dhap.pathcloud.tracking.domain;

import com.lifetech.dhap.pathcloud.tracking.application.condition.DistributeCondition;
import com.lifetech.dhap.pathcloud.tracking.application.condition.DoctorAdviceCondition;
import com.lifetech.dhap.pathcloud.tracking.application.condition.InstrumentTrackingCon;
import com.lifetech.dhap.pathcloud.tracking.application.condition.TrackingCondition;
import com.lifetech.dhap.pathcloud.tracking.application.dto.BlockTrackingDto;
import com.lifetech.dhap.pathcloud.tracking.application.dto.DoctorAdviceDto;
import com.lifetech.dhap.pathcloud.tracking.application.dto.InstrumentTrackingDto;
import com.lifetech.dhap.pathcloud.tracking.domain.model.BlockTracking;
import com.lifetech.dhap.pathcloud.tracking.domain.model.DistributeHistory;
import com.lifetech.dhap.pathcloud.tracking.domain.model.PrepareDistribute;
import com.lifetech.dhap.pathcloud.tracking.domain.model.Tracking;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TrackingRepository {

    int deleteByPrimaryKey(Long id);

    int insert(Tracking record);

    /**
     * 批量插入
     * @param trackings
     * @return
     */
    int batchInsert(List<Tracking> trackings);

    Tracking selectByPrimaryKey(Long id);

    List<Tracking> selectAll();

    int updateByPrimaryKey(Tracking record);

    Long last();

    Tracking selectByCondition(TrackingCondition con);

    List<Tracking> selectAllByCondition(TrackingCondition con);

    Tracking selectTrackingByBasketNumberAndStatus(@Param("basketNumber") Long basketNumber, @Param("status") Integer status);

    int deleteByCondition(TrackingCondition con);

    /**
     * 获取待派片列表
     * @return
     */
    List<PrepareDistribute> selectPrepareDistribute(DistributeCondition con);

    Long countPrepareDistribute(DistributeCondition con);

    /**
     * 根据病理号查询block_id列表
     * @param pathId
     * @return
     */
    List<Long> selectPrepareDistributeIdsByParam(Long pathId);

    /**
     * 获取派片历史
     * @param con
     * @return
     */
    List<DistributeHistory> selectDistributeHistory(DistributeCondition con);

    Long countDistributeHistory(DistributeCondition con);

    Long countWatchedSlide(@Param("userId") long userId, @Param("slideIds") List<Long> slideIds);

    Long selectSectionOperatorByBlockId(@Param("blockId") long blockId);

    List<Tracking> selectTrackingByPathId(long pathId);

    Long selectBlockWaitTime(TrackingCondition condition);

    Long selectBlockWaitTimeByCondition(TrackingCondition condition);

    List<BlockTracking> selectIhcTrackingByPathId(@Param("pathId") Long pathId, @Param("specialDye")  Integer specialDye,@Param("applyId") Long applyId);

    /**
     * 医嘱查询
     * @return
     */
    List<DoctorAdviceDto> selectDoctorAdvice(DoctorAdviceCondition con);
    Long countDoctorAdvice(DoctorAdviceCondition con);

    /**
     * 医嘱执行操作记录
     * @param applyId
     * @return
     */
    List<BlockTrackingDto> getDoctorAdviceLog(@Param("applyId") Long applyId, @Param("blockId") Long blockId);

    /**
     * 查询最后一条暂停包埋或取消暂停包埋记录
     * @param blockId
     * @return
     */
    Tracking selectLastEmbedPause(long blockId);

    List<InstrumentTrackingDto> InstrumentTracking(InstrumentTrackingCon con);

    Long InstrumentTrackingTotal(InstrumentTrackingCon con);

    Long countTrackingByCondition(TrackingCondition condition);

    /**
     * 根据蜡块特检申请ID查询对应tracking记录ID
     * @param blockIhcId
     * @return
     */
    Long selectBlockIhcTrackingIdByBlockIhcId(long blockIhcId);

    /**
     * 根据tracing ID查询蜡块特检申请ID
     * @param id
     * @return
     */
    Long selectBlockIhcIdById(long id);
}