package com.lifetech.dhap.pathcloud.tracking.application;

import com.lifetech.dhap.pathcloud.common.exception.BuzException;
import com.lifetech.dhap.pathcloud.tracking.application.condition.*;
import com.lifetech.dhap.pathcloud.tracking.application.dto.*;
import com.lifetech.dhap.pathcloud.user.application.dto.UserDto;

import java.util.Date;
import java.util.List;

/**
 * Created by LuoMo on 2016-12-06.
 */
public interface BlockApplication {

    /**
     * 根据病理ID获取取材列表
     * @param pathId
     * @return
     */
    List<BlockDto> getAllBlocksByPathId(long pathId);

    /**
     * 根据病理ID获取正常蜡块Id列表
     * 不包含脱钙
     * @param pathId
     * @return
     */
    List<Long> getNormalBlocksIdByPathId(long pathId);

    /**
     * 根据病理ID获取正常蜡块信息
     * @param pathId
     * @return
     */
    List<BlockDto> getNormalBlockByPathId(long pathId);

    /**
     * 根据病理ID获取已切片蜡块ID
     * @param pathId
     * @return
     */
    List<Long> getHadSectionBlocksIdByPathId(long pathId);

    /**
     * 根据病理ID获取已包埋蜡块ID
     * @param pathId
     * @return
     */
    List<Long> getHadEmbedBlocksIdByPathId(long pathId);

    /**
     * 根据病理ID获取该病理下玻片ID列表
     * @param pathId
     * @return
     */
    List<Long> getSlidesIdByPathId(long pathId);

    /**
     * 根据蜡块ID查询玻片ID列表
     * @param blockId
     * @return
     */
    List<Long> getSlidesIdByBlockId(long blockId);

    /**
     * 取材记录添加
     *
     * @param block
     */
    void addBlock(BlockDto block);

    /**
     * 添加玻片
     * @param block
     */
    void addSlide(BlockDto block);

    /**
     * 分页获取信息，多条件查询，join多张表
     *
     * @param con
     * @return
     */
    List<BlockDetailDto> getGrossingBlockDetailByCondition(GrossingCon con);

    /**
     * 分页总计算
     * @param con
     * @return
     */
    Long countGrossingBlockDetailByCondition(GrossingCon con);

    /**
     * 修改取材记录
     * @param blockDto
     * @return
     */
    void updateBlock(BlockDto blockDto);

    /**
     * 根据ID查询蜡块信息
     * @param id
     * @return
     */
    BlockDto getBlockById(long id);

    /**
     * 根据病理ID查询蜡块的最小状态
     * @param pathId
     * @return
     */
    Integer getMinBlockStatusByPathId(long pathId);

    /**
     * 根据蜡块ID查询玻片的最小状态
     * @param blockId
     * @return
     */
    Integer getMinSlideStatusByBlockId(long blockId);

    /**
     * 根据取材标识查询数量
     * @param biaoshi
     * @return
     */
    Long countBlockByBiaoshi(int biaoshi);

    /**
     * 根据组织数单位查询数量
     * @param unit
     * @return
     */
    Long countBlockByUnit(int unit);

    /**
     * 按脱水篮查询脱水篮下的病理数
     * @param con
     * @return
     */
    Long countPathologyByCondition(GrossingCon con);

    /**
     * 根据ID删除蜡块
     * 同时删除蜡块Tracking记录
     * @param id
     */
    void  deleteBlockById(long id);

    /**
     * 根据病理号查询最后取材医生
     * @param pathId
     * @return
     */
    Long getGrossingDoctorByPathId(long pathId);

    /**
     * 根据脱水机ID查询block列表
     * @param dehydratorId
     * @return
     */
    List<BlockDto> getBlocksByDehydratorId(long dehydratorId);

    /**
     * 根据蜡块号获取block扩展信息
     * @param blockSerialNumber
     * @return
     */
    BlockExtendDto getBlockExtendBySerialNumber(String blockSerialNumber);

    /**
     *根据蜡块号查询block基本信息
     * @param blockSerialNumber
     * @return
     */
    BlockDto getBlockBySerialNumber(String blockSerialNumber);

    /**
     * 按照block状态计数
     * @param status
     * @return
     */
    Long countBlockByStatus(int status);

    /**
     * 包埋
     *
     * @param block
     */
    void embedConfirm(BlockExtendDto block);


    /**
     * 一个蜡块多个切片
     * @param blockExtendDTOs
     * @return
     */
    void sectionsConfirm(List<BlockExtendDto> blockExtendDTOs);

    /**
     * 蜡块打印玻片
     * @param slideDTOs
     * @return
     * @throws BuzException
     */
    List<SlidePrintDto> slidePrint(List<SlideDto> slideDTOs) throws BuzException;

    /**
     * 染色
     * @param slideIds
     * @param instrumentId
     * @throws BuzException
     */
    void dyeConfirm(List<Long> slideIds,long instrumentId) throws BuzException;

    /**
     * 封片
     * @param slideIds
     * @param instrumentId
     * @throws BuzException
     */
    void mountingConfirm(List<Long> slideIds,long instrumentId) throws BuzException;

    /**
     * 派片
     * @param slideDistributeDto
     * @throws BuzException
     */
    void slideDistribute(SlideDistributeDto slideDistributeDto) throws BuzException;

    /**
     * 样本状态统计
     *
     * @return
     */
    List<SampleStaticsDto> sampleStatusStatistic();

    /**
     * 异常样本数据
     *
     * @param con
     * @return
     */
    List<BlockDetailDto> getAbnormalBlock(GrossingCon con);

    /**
     * 异常样本消息自动发送
     * @param con
     * @return
     */
    int abnormalBlockNotification(GrossingCon con);

    /**
     * 获取玻片派片信息
     * @param condition
     * @return
     */
    List<SlideDto> getSlideDistributesByCondition(SlideCondition condition);
    Long countSlideDistributesByCondition(SlideCondition condition);

    /**
     * 样本状态异常处理
     * @param dto
     */
    void abnormalBlockDeal(DealNotificationDto dto);

    /**
     * 根据玻片号和玻片小号获取玻片制片确认信息
     * @param slideNo
     * @return
     */
    SlideDto getSlideConfirmBySlideNoAndSubId(String slideNo,String subId);

    /**
     * 根据病理ID获取玻片制片确认信息
     * @param pathId
     * @return
     */
    List<SlideDto> getSlideConfirmByPathId(long pathId);
    Long countSlideConfirmByPathId(long pathId);

    /**
     * 根据病理ID获取玻片制片确认玻片ID列表
     * @param pathId
     * @return
     */
    List<Long> getSlideConfirmIdsByPathId(long pathId);

    /**
     * 根据蜡块ID获取玻片制片确认信息
     * @param blockId
     * @return
     */
    List<SlideDto> getSlideConfirmByBlockId(long blockId);

    /**
     * 玻片制片确认
     * @param slideIds
     */
    void slideConfirm(List<Long> slideIds) throws BuzException;


    /**
     * 处理玻片异常
     * @param slideId 玻片ID
     * @param handle 处理方式
     * @param note 处理备注
     * @param source 来源
     */
    void slideAbnormalHandle(long slideId,int handle,String note,int source) throws BuzException;

    /**
     * 诊断重补取
     * @param blockId
     * @param note
     */
    void blockReGrossing(Long blockId, String note);

    /**
     * 根据玻片号加小号查询玻片信息
     * @param slideNo
     * @param subId
     * @return
     */
    SlideDto getSlideBySlideNoAndSubId(String slideNo,String subId);


    /**
     * 根据条件查询玻片信息
     * @param condition
     * @return
     */
    List<SlideDto> getSlideByCondition(SlideCondition condition);
    List<Long> getSlideIdByCondition(SlideCondition condition);
    Long countSlideByCondition(SlideCondition condition);

    BlockDetailDto getBlockDetail(Long blockId);

    /**
     * 提交诊断
     * @param diagnoseDto
     */
    void diagnose(DiagnoseDto diagnoseDto);

    /**
     * 申请深切
     * @param blockId
     * @param note
     */
    void deepSection(long blockId,String note);

    /**
     * 根据蜡块号获取最后一个玻片小号
     * @param blockId
     * @return
     */
    Integer getLastSlideSubIdByBlockId(long blockId);

    /**
     * 根据病理id，特殊编号 获取取材信息
     */
    List<GrossingInfoDto> getGrossingInfo(Long pathId,String number);

    /**
     * 获取生成的block
     * @param blockId
     * @param status
     * @param time
     * @return
     */
    BlockDto getBlockForWaitTime(Long blockId, Integer status, Date time);

    /**
     * 根据蜡块ID获取重补取蜡块取材等待时间
     * @param blockId
     * @return
     */
    Long getRegrossingWaitTime(long blockId);

    /**
     * 获取包埋信息
     * @param
     * @return
     */
    List<InfoQueryDto> getEmbedsQuery(EmbedsCon con);

    Long getEmbedsQueryTotal(EmbedsCon con);
    /**
     * 根据条件获取已做某个操作的蜡块数
     * @param condition
     * @return
     */
    long countHadOperatedBlocks(HadOperatedCondition condition);

    /**
     * 获取切片信息
     * @param
     * @return
     */
    List<InfoQueryDto> getSectionsQuery(EmbedsCon con);

    /**
     * 获取切片信息总数
     * @param
     * @return
     */
    Long getSectionsQueryTotal(EmbedsCon con);

    List<UserDto> getEmbedPerson();

    List<UserDto> getSectionPerson();

    /**
     * 批量扫玻片结果保存
     * @param blocks
     * @return 返回错误的异常玻片号
     */
    List<String> addBlockScanResult(String blocks, BlockScanImageDto imagePath);

    /**
     * 根据扫描位置获取批量扫结果
     * @return
     */
    List<BlockScanDto> getBlockScansByLocation(int location);

    /**
     * 根据扫描位置获取最新的批量扫图片
     * @return
     */
    BlockScanImageDto selectNewestImageByLocation(int location);


    BlockScanImageDto getScanImage(String path);

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

    List<UserDto> getDyePerson();

    List<BlockExtendDto> getNotSpecialBlocksByCondition(ApplicationCondition applicationCondition);
    Long countNotSpecialBlocksByCondition(ApplicationCondition applicationCondition);

    List<UserDto> getPrintPerson();

    List<BlockDto> getBlockByCondition(BlockCondition blockCondition);
    Long countBlockByCondition(BlockCondition condition);

    /**
     * 根据染色申请ID查询产生玻片信息
     * @param ihcId
     * @return
     */
    List<SlideDto> getSlideInfoByIhcId(long ihcId);

    /**
     * 根据蜡块染色申请ID查询产生玻片信息
     * @param blockIhcId
     * @return
     */
    List<SlideDto> getSlideInfoByBlockIhcId(long blockIhcId);

    /**
     * 根据蜡块染色申请ID和标记物查询玻片信息
     * @param blochIhcId
     * @param marker
     * @return
     */
    BlockDto getSlideInfoByBlockIhcIdAndMarker(Long blochIhcId,String marker);

    /**
     * 根据蜡块ID和标记物查询玻片信息
     * @param blockId
     * @param marker
     * @return
     */
    BlockDto getSlideInfoByBlockIdAndMarker(Long blockId,String marker);

    /**
     * 包埋暂停
     * @param trackingDto
     */
    void embedPause(TrackingDto trackingDto);

    /**
     * 获取蜡块简单信息
     * @param id
     * @return
     */
    BlockDto getSimpleBlockById(long id);

    /**
     * 批量更新蜡块
     * @param blockDTOs
     */
    void batchUpdateBlock(List<BlockDto> blockDTOs);


    List<BlockDetailDto> getFrozenBlockByCondition(GrossingCon con);

    Long countFrozenBlockByCondition(GrossingCon con);

    /**
     * 获取是否更新病理
     * 如果更新病理,返回病理信息
     * 如果不更新,返回NULL
     * @param pathologyDto
     * @return
     */
    Object getUpdatePathology(Object pathologyDto);

    /**
     * 根据冰冻号查询玻片信息
     * @param number
     * @return
     */
    List<BlockDto> getBlockInfoByNumber(String number);

    /**
     * 根据特殊操作ID查询产生蜡块
     * @param applyId
     * @return
     */
    List<Long> getSlideIdByApplyId(Long applyId);
}
