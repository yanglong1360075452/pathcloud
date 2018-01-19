package com.lifetech.dhap.pathcloud.tracking.application;

import com.lifetech.dhap.pathcloud.tracking.application.condition.GrossingCon;
import com.lifetech.dhap.pathcloud.tracking.application.dto.*;

import java.util.List;
import java.util.Map;

/**
 * 取材业务逻辑
 *
 * @author eric.guo@ostengar.com
 * @version 1.0
 * @time 2016-12-07-14:07
 */
public interface GrossingApplication {

    /**
     * 取材记录保存
     *
     * @param data
     */
    void grossingSave(GrossingSaveDto data);

    /**
     * 取材打印包埋盒
     * @param data
     */
    void grossingPrint(GrossingSaveDto data);

    /**
     * 取材确认
     * @param blocksId
     */
    void grossingConfirm(Map<Long,Integer> blocksId);

    /**
     * 取材记录查询
     * @param con
     * @return
     */
    List<BlockDetailDto> getBlockByCondition(GrossingCon con);

    Long countBlockByCondition(GrossingCon con);

    /**
     * 病理数统计
     * @param con
     * @return
     */
    Long countPathologyByCondition(GrossingCon con);

    /**
     * 获取取材相关的文件
     *
     * @param id 病理ID
     * @param operation
     * @return
     */
    GrossingFileDto getGrossingFile(Long id, Integer operation,String tag);

    /**
     * 取材工作站提前打印包埋盒
     * @param grossingPrintDtos
     */
    void grossingBeforePrint(List<GrossingPrintDto> grossingPrintDtos);

    /**
     * 重复打印包埋盒/玻片
     * @param operate 操作 打印包埋盒-34 打印玻片-37
     * @param ids operate-34(打印包埋盒)-蜡块ID  operate-37(打印玻片)-玻片ID
     */
    void print(Integer operate,List<Long> ids);

    /**
     * 冰冻取材记录保存
     * @param data
     */
    void frozenGrossing(GrossingSaveDto data);

    /**
     * 冰冻取材打印玻片
     * @param data
     */
    void frozenPrint(GrossingSaveDto data);

    List<BlockDetailDto> getFrozenBlockByCondition(GrossingCon con);

    Long countFrozenBlockByCondition(GrossingCon con);

    /**
     * 添加打印记录
     * @param blockDto
     * @param operate
     * @return
     */
    TrackingDto addPrint(BlockDto blockDto, Integer operate);

    /**
     * 记录免疫组化试剂使用记录
     * 扣除库存
     * @param marker
     * @param usage
     */
    void recordIHCReagent(String marker,Double usage);
}
