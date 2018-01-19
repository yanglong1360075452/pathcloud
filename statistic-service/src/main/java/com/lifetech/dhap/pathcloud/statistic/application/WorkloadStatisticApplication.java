package com.lifetech.dhap.pathcloud.statistic.application;

import com.lifetech.dhap.pathcloud.statistic.application.condition.CoincidenceCondition;
import com.lifetech.dhap.pathcloud.statistic.application.condition.QualityControlCon;
import com.lifetech.dhap.pathcloud.statistic.application.condition.ReportCondition;
import com.lifetech.dhap.pathcloud.statistic.application.condition.WorkloadCondition;
import com.lifetech.dhap.pathcloud.statistic.application.dto.GroupInspectCategoryDto;
import com.lifetech.dhap.pathcloud.statistic.application.dto.MonthInspectCategoryDto;
import com.lifetech.dhap.pathcloud.statistic.application.dto.PersonInspectCategoryDto;
import com.lifetech.dhap.pathcloud.statistic.application.dto.QualityMonthDto;
import com.lifetech.dhap.pathcloud.statistic.application.dto.QualityPersonalDto;

import java.util.List;

/**
 * @author eric.guo@ostengar.com
 * @version 1.0
 * @time 2017-02-13-09:29
 */
public interface WorkloadStatisticApplication {

    /**
     * 工作量按月统计
     * @param con
     * @return
     */
    List<MonthInspectCategoryDto> monthInspectCategory(WorkloadCondition con);

    /**
     * 工作量按人统计
     * @param con
     * @return
     */
    List<PersonInspectCategoryDto> personInspectCategory(WorkloadCondition con);
    Long personInspectCategoryTotal(WorkloadCondition con);

    /**
     * 工作量按组统计
     * @param con
     * @return
     */
    List<GroupInspectCategoryDto> groupInspectCategory(WorkloadCondition con);

    /**
     * 质控评分按人统计
     * @return
     */
    List<QualityPersonalDto> personalQualityControl(QualityControlCon con);
    Long personalQualityControlTotal(QualityControlCon con);

    /**
     * 质控评分按月统计
     * @return
     */
    List<QualityMonthDto> monthQualityControl(QualityControlCon con);

    /**
     * 质控评分优良率
     * @param con
     * @return
     */
    List<QualityMonthDto> goodQualityControl(QualityControlCon con);

    /**
     * 报告延时统计
     * @param con
     * @return
     */
    List<QualityMonthDto> reportDelay(ReportCondition con);

    /**
     * 冰冻符合率统计
     * @param con
     * @return
     */
    List<QualityMonthDto> coincidence(CoincidenceCondition con);
}
