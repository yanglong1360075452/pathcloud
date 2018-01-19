package com.lifetech.dhap.pathcloud.statistic.domain;

import com.lifetech.dhap.pathcloud.statistic.application.condition.AvgWaitTimeCondition;
import com.lifetech.dhap.pathcloud.statistic.application.condition.StatisticsCondition;
import com.lifetech.dhap.pathcloud.statistic.domain.model.QualityRank;
import com.lifetech.dhap.pathcloud.statistic.domain.model.SpecialDyeStatistics;
import com.lifetech.dhap.pathcloud.statistic.domain.model.Workload;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by LiuMei on 2017-02-16.
 */
public interface StatisticsRepository {

    List<Map<String,Object>> countGroupByInspectCategory(StatisticsCondition condition);

    Long countAllBlocks(StatisticsCondition condition);

    Long countNotErrorBlocks(StatisticsCondition condition);

    Long countAllSlides(StatisticsCondition condition);

    Long countReGrossingBlocks(StatisticsCondition condition);

    Long countDeepSectionBlocks(StatisticsCondition condition);

    Long countReSectionBlocks(StatisticsCondition condition);

    Long countErrorBlocks(StatisticsCondition condition);

    Long countErrorSlides(StatisticsCondition condition);

    List<SpecialDyeStatistics> selectSpecialDyeStatistics();

    List<Workload> selectWorkload(StatisticsCondition condition);

    Long selectAvgRegisterWaitTime(StatisticsCondition condition);

    /**
     * 正常取材平均等待时间
     * @param condition
     * @return
     */
    Long selectAvgNormalGrossingWaitTime(StatisticsCondition condition);

    List<Long> selectReGrossingBlockIds(StatisticsCondition condition);

    Long selectAvgCommonOperationWaitTime(AvgWaitTimeCondition condition);

    /**
     * 正常切片平均等待时间
     * @param condition
     * @return
     */
    Long selectAvgNormalSectionWaitTime(StatisticsCondition condition);

    /**
     * 申请重切或深切的蜡块ID
     * @param condition
     * @return
     */
    List<Long> selectAbnormalSectionBlocksId(StatisticsCondition condition);

    /**
     * 根据蜡块ID查询重切或深切玻片ID
     * @param blockId
     * @return
     */
    List<Long> selectAbnormalSectionIdsByBlockId(long blockId);

    Long selectSectionWaitTimeByBlockIdAndSlideId(@Param("blockId") long blockId, @Param("slideIds") List<Long> slideIds);

    Long selectAvgDiagnoseWaitTime(StatisticsCondition condition);

    Long selectAvgReportWaitTime(StatisticsCondition condition);

    List<QualityRank> selectQualityRank(StatisticsCondition condition);

    Long countUnqualifiedSlide(StatisticsCondition condition);
}
