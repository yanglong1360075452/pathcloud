package com.lifetech.dhap.pathcloud.statistic.application;

import com.lifetech.dhap.pathcloud.statistic.application.condition.StatisticsCondition;
import com.lifetech.dhap.pathcloud.statistic.application.dto.QualityRankDto;
import com.lifetech.dhap.pathcloud.statistic.application.dto.SpecialDyeStatisticsDto;
import com.lifetech.dhap.pathcloud.statistic.application.dto.WorkloadDto;

import java.util.List;
import java.util.Map;

/**
 * Created by LiuMei on 2017-02-16.
 */
public interface StatisticApplication {

    /**
     * 按检查项目统计
     * @param condition
     * @return
     */
    List<Map<String,Object>> countPathGroupByInspectCategory(StatisticsCondition condition);

    /**
     * 所有蜡块数
     * @return
     */
    long countAllBlocks(StatisticsCondition condition);

    /**
     * 除异常终止蜡块数
     * @param condition
     * @return
     */
    long countNotErrorBlocks(StatisticsCondition condition);

    /**
     * 所有切片数
     * @param condition
     * @return
     */
    long countAllSlides(StatisticsCondition condition);

    /**
     * 重补取蜡块数
     * @return
     */
    long countReGrossingBlocks(StatisticsCondition condition);

    /**
     * 重切蜡块数
     * @param condition
     * @return
     */
    long countReSectionBlocks(StatisticsCondition condition);

    /**
     * 深切蜡块数
     * @param condition
     * @return
     */
    long countDeepSectionBlocks(StatisticsCondition condition);

    /**
     * 异常终止蜡块数
     * @param condition
     * @return
     */
    long countErrorBlocks(StatisticsCondition condition);

    /**
     * 异常终止切片数
     * @param condition
     * @return
     */
    long countErrorSlides(StatisticsCondition condition);

    /**
     * 特染/常规病例统计
     * @return
     */
    List<SpecialDyeStatisticsDto> getSpecialDyeStatistics();

    /**
     * 根据工位/时间 统计技术员工作量
     * @param condition
     * @return
     */
    List<WorkloadDto> getWorkload(StatisticsCondition condition);

    /**
     * 平均等待时间
     * @param condition
     * @return
     */
    Map<String,Long> getAvgWaitTime(StatisticsCondition condition);

    /**
     * 获取质控排行
     * @param condition
     * @return
     */
    List<QualityRankDto> getQualityRank(StatisticsCondition condition);

    /**
     * 不合格玻片
     * @param condition
     * @return
     */
    long countUnqualifiedSlide(StatisticsCondition condition);
}
