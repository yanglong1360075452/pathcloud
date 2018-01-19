package com.lifetech.dhap.pathcloud.statistic.domain;

import com.lifetech.dhap.pathcloud.statistic.application.condition.CoincidenceCondition;
import com.lifetech.dhap.pathcloud.statistic.application.condition.QualityControlCon;
import com.lifetech.dhap.pathcloud.statistic.application.condition.ReportCondition;
import com.lifetech.dhap.pathcloud.statistic.application.condition.WorkloadCondition;
import com.lifetech.dhap.pathcloud.statistic.domain.model.*;

import java.util.List;

/**
 * @author eric.guo@ostengar.com
 * @version 1.0
 * @time 2017-02-13-09:33
 */
public interface WorkloadStatisticRepository {

    List<MonthInspectCategory> monthInspectCategory(WorkloadCondition con);

    List<PersonInspectCategory> personInspectCategory(WorkloadCondition con);
    Long personInspectCategoryTotal(WorkloadCondition con);

    List<QualityMonth> monthQualityControl(QualityControlCon con);

    List<QualityMonth> goodQualityControl(QualityControlCon con);

    List<QualityMonth> reportDelay(ReportCondition con);

    List<QualityMonth> coincidence(CoincidenceCondition con);

    List<QualityPersonal> personQualityControl(QualityControlCon con);
    Long countPersonQualityControl(QualityControlCon con);

    List<GroupInspectCategory> groupInspectCategory(WorkloadCondition con);


}
