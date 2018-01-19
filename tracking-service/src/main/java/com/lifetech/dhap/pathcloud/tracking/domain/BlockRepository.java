package com.lifetech.dhap.pathcloud.tracking.domain;

import com.lifetech.dhap.pathcloud.tracking.application.condition.*;
import com.lifetech.dhap.pathcloud.tracking.application.dto.InfoQueryDto;
import com.lifetech.dhap.pathcloud.tracking.domain.model.*;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * Created by LuoMo on 2016-12-06.
 */
public interface BlockRepository {

    int deleteByPrimaryKey(Long id);

    int insert(Block record);

    Block selectByPrimaryKey(Long id);

    int batchUpdate(List<Block> list);

    List<Block> selectAll();

    int updateByPrimaryKey(Block record);

    List<Block> selectAllBlockByPathId(Long pathId);

    List<Long> selectNormalBlockIdsByPathId(Long pathId);

    List<Block> selectNormalBlocksByPathId(Long pathId);

    List<Long> selectSlideIdsByPathId(long pathId);

    List<Long> selectSlideIdsByBlockId(long blockId);

    Long last();

    List<BlockDetail> selectBlockDetailByCondition(GrossingCon con);

    List<BlockDetail> selectNoGrossingBlockByCondition(GrossingCon con);

    long countBlockDetailByCondition(GrossingCon con);

    long countNoGrossingBlockByCondition(GrossingCon con);

    /**
     * 根据病理ID查询蜡块最小状态
     *
     * @param pathId
     * @return
     */
    int selectMinStatusByPathId(Long pathId);

    /**
     * 根据蜡块ID查询玻片最小状态
     * @param blockId
     * @return
     */
    Integer selectMinStatusByBlockId(Long blockId);

    /**
     * 根据取材标识查询数量
     *
     * @param biaoshi
     * @return
     */
    Long countBlockByBiaoshi(Integer biaoshi);

    /**
     * 根据单位查询数量
     *
     * @param unit
     * @return
     */
    Long countBlockByUnit(Integer unit);

    /**
     * 根据状态查询待脱水--脱水篮列表
     *
     * @return
     */
    List<Basket> selectBasketsInfoByCondition(BasketCon con);

    Long countBasketsInfoByCondition(BasketCon con);

    /**
     * 根据脱水篮编号和状态查询最后使用的记录员
     *
     * @return
     */
    Long selectRecorderByBasket(@Param("instrumentId") Integer instrumentId, @Param("status") Integer status, @Param("userId") Long userId);

    /**
     * 根据病理号查询最后取材医生
     * @param pathId
     * @return
     */
    Long selectGrossingDoctorByPathId(Long pathId);

    Long countPathologyByCondition(GrossingCon con);

    /**
     * 根据脱水机ID查询可以脱水结束的block
     * @param dehydratorId
     * @return
     */
    List<Block> selectByDehydratorId(Long dehydratorId);

    BlockExtend selectBlockExtendBySerialNumber(String serialNumber);

    Long countBlockByStatus(Integer status);

    Block selectBlockBySerialNumber(String serialNumber);

    /**
     * 统计超时的样本数
     *
     * @return
     */
    Long countAbnormalBlockByStatusAndTime(@Param("status") Integer status, @Param("time") Date time);
    List<BlockDetail> selectAbnormalBlockByStatusAndTime(GrossingCon con);

    /**
     * 超时取材记录统计
     *
     * @param status
     * @param time
     * @return
     */
    Long countAbnormalBlock(@Param("status") Integer status, @Param("time") Date time);

    /**
     * 异常取材记录
     * @param con
     * @return
     */
    List<BlockDetail> selectAbnormalBlock(GrossingCon con);


    /**
     * 根据条件查询玻片派片信息
     * @param con
     * @return
     */
    List<Slide> selectSlideDistributeByCondition(SlideCondition con);
    Long countSlideDistributeByCondition(SlideCondition con);

    /**
     * 通过玻片号和玻片小号获取制片确认信息
     * @param slideNo
     * @return
     */
    Slide selectSlideConfirmBySlideNoAndSubId(@Param("slideNo") String slideNo,@Param("subId") String subId);

    /**
     * 通过病理ID获取制片确认信息
     * @param pathId
     * @return
     */
    List<Slide> selectSlideConfirmByPathId(long pathId);
    Long countSlideConfirmByPathId(long pathId);

    List<Long> selectSlideConfirmIdsByPathId(long pathId);

    /**
     * 通过蜡块ID查询制片确认信息
     * @param blockId
     * @return
     */
    List<Slide> selectSlideConfirmByBlockId(long blockId);

    /**
     * 根据玻片号和玻片小号查询玻片信息
     * @param slideNo
     * @return
     */
    Slide selectSlideBySlideNoAndSubId(@Param("slideNo") String slideNo,@Param("subId") String subId);

    List<Slide> selectSlideByCondition(SlideCondition condition);
    List<Long> selectSlideIdByCondition(SlideCondition condition);
    Long countSlideByCondition(SlideCondition condition);

    BlockDetail selectBlockDetailById(Long blockId);

    /**
     * 查询病理的取材操作人
     *
     * @param pathologyId
     * @return
     */
    BlockDetail selectPathologyGrossingOperator(Long pathologyId);

    /**
     * 根据蜡块ID查询最后一个玻片号
     * @param blockId
     * @return
     */
    Integer selectLastSlideSubIdByBlockId(long blockId);

    /**
     * 查询已切片蜡块ID
     * @param pathId
     * @return
     */
    List<Long> selectHadSectionBlocksIdByPathId(long pathId);

    /**
     * 查询已包埋蜡块ID
     * @param pathId
     * @return
     */
    List<Long> selectHadEmbedBlocksIdByPathId(long pathId);

    /**
     * 查询取材信息
     * @param pathId
     * @return
     */
    List<GrossingInfo> selectGrossingInfo(@Param("pathId") Long pathId,@Param("number") String number);

    /**
     * 根据蜡块ID查询最新玻片ID
     * @param blockId
     * @return
     */
    Long selectLastSlideIdByBlockId(long blockId);

    Block selectBlockForWaitTime(@Param("blockId") Long blockId, @Param("status") Integer status, @Param("time") Date time);

    /**
     * 查询重补取蜡块取材等待时间
     *
     * 申请重补取时间减去取材时间
     * 默认第一次重补取申请对应第一个重补取蜡块
     * @param blockId
     * @return
     */
    Long selectRegrossingWaitTime(long blockId);

    /**
     * 若有一次申请重补取多个情况,按最近一次申请算等待时间
     * @param blockId
     * @return
     */
    Long selectRegrossingLessWaitTime(long blockId);

    /**
     * 根据条件查询已操作蜡块数
     * @param condition
     * @return
     */
    long countHadOperatedBlocks(HadOperatedCondition condition);

    /**
     * 获取包埋信息
     * @param
     * @return
     */
    List<InfoQueryDto> getEmbedsQuery(EmbedsCon con);

    Long getEmbedsQueryTotal(EmbedsCon con);

    /**
     * 获取切片信息
     * @param
     * @return
     */
    List<InfoQueryDto> getSectionsQuery(EmbedsCon con);

    Long getSectionsQueryTotal(EmbedsCon con);

    /**
     * 获取包埋技术员
     * @param
     * @return
     */
    List<Long> getEmbedPerson();

    /**
     * 获取切片技术员
     * @param
     * @return
     */
    List<Long> getSectionPerson();

    /**
     * 获取深切信息
     * @param
     * @return
     */
    List<InfoQueryDto> getDeepSection();

    /**
     * 获取染色信息
     * @param
     * @return
     */
    List<InfoQueryDto> getDyeQuery(EmbedsCon con);

    /**
     * 获取染色信息总数
     * @param
     * @return
     */
    Long getDyeQueryTotal(EmbedsCon con);

    /**
     * 获取染色技术员
     * @param
     * @return
     */
    List<Long> getDyePerson();

    /**
     * 查询可以申请特染的蜡块
     * @param condition
     * @return
     */
    List<BlockExtend> selectNotSpecialBlockExtendByCondition(ApplicationCondition condition);
    Long countNotSpecialBlockExtendByCondition(ApplicationCondition condition);

    List<Long> getPrintPerson();

    List<Block> selectBlockByCondition(BlockCondition condition);
    Long countBlockByCondition(BlockCondition condition);

    List<Slide> selectSlideByIhcId(long ihcId);

    List<Slide> selectSlideByBlockIhcId(long blockIhcId);

    Block selectSlideByBlockIhcIdAndMarker(@Param("blockIhcId") Long blockIhcId, @Param("marker") String marker);

    Block selectSlideByBlockIdAndMarker(@Param("blockId") Long blockId, @Param("marker") String marker);

    List<BlockDetail> getFrozenBlockByCondition(GrossingCon con);

    Long countFrozenBlockByCondition(GrossingCon con);

    List<Block> selectBlockByNumber(String number);

    List<Long> selectSlideByApplyId(long applyId);
}
