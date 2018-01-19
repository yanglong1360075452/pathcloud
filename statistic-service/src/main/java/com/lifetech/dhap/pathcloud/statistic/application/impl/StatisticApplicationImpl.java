package com.lifetech.dhap.pathcloud.statistic.application.impl;

import com.lifetech.dhap.pathcloud.statistic.application.StatisticApplication;
import com.lifetech.dhap.pathcloud.statistic.application.condition.AvgWaitTimeCondition;
import com.lifetech.dhap.pathcloud.statistic.application.condition.StatisticsCondition;
import com.lifetech.dhap.pathcloud.statistic.application.dto.QualityRankDto;
import com.lifetech.dhap.pathcloud.statistic.application.dto.SpecialDyeStatisticsDto;
import com.lifetech.dhap.pathcloud.statistic.application.dto.WorkloadDto;
import com.lifetech.dhap.pathcloud.statistic.domain.StatisticsRepository;
import com.lifetech.dhap.pathcloud.statistic.domain.model.QualityRank;
import com.lifetech.dhap.pathcloud.statistic.domain.model.SpecialDyeStatistics;
import com.lifetech.dhap.pathcloud.statistic.domain.model.Workload;
import com.lifetech.dhap.pathcloud.tracking.application.BlockApplication;
import com.lifetech.dhap.pathcloud.tracking.application.infrastructure.code.TrackingOperation;
import com.lifetech.dhap.pathcloud.user.application.UserApplication;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by LiuMei on 2017-02-16.
 */
@Service
public class StatisticApplicationImpl implements StatisticApplication {

    @Autowired
    private StatisticsRepository statisticsRepository;

    @Autowired
    private UserApplication userApplication;

    @Autowired
    private BlockApplication blockApplication;

    @Override
    public List<Map<String, Object>> countPathGroupByInspectCategory(StatisticsCondition condition) {
        return statisticsRepository.countGroupByInspectCategory(condition);
    }

    @Override
    public long countAllBlocks(StatisticsCondition condition) {
        return statisticsRepository.countAllBlocks(condition);
    }

    @Override
    public long countNotErrorBlocks(StatisticsCondition condition) {
        return statisticsRepository.countNotErrorBlocks(condition);
    }

    @Override
    public long countAllSlides(StatisticsCondition condition) {
        return statisticsRepository.countAllSlides(condition);
    }

    @Override
    public long countReGrossingBlocks(StatisticsCondition condition) {
        return statisticsRepository.countReGrossingBlocks(condition);
    }

    @Override
    public long countReSectionBlocks(StatisticsCondition condition) {
        return statisticsRepository.countReSectionBlocks(condition);
    }

    @Override
    public long countDeepSectionBlocks(StatisticsCondition condition) {
        return statisticsRepository.countDeepSectionBlocks(condition);
    }

    @Override
    public long countErrorBlocks(StatisticsCondition condition) {
        return statisticsRepository.countErrorBlocks(condition);
    }

    @Override
    public long countErrorSlides(StatisticsCondition condition) {
        return statisticsRepository.countErrorSlides(condition);
    }

    @Override
    public List<SpecialDyeStatisticsDto> getSpecialDyeStatistics() {
        List<SpecialDyeStatistics> specialDyeStatisticsList = statisticsRepository.selectSpecialDyeStatistics();
        List<SpecialDyeStatisticsDto> specialDyeStatisticsDtoList = new ArrayList<>();
        SpecialDyeStatisticsDto specialDyeStatisticsDto;
        for (SpecialDyeStatistics specialDyeStatistics : specialDyeStatisticsList) {
            specialDyeStatisticsDto = new SpecialDyeStatisticsDto();
            specialDyeStatisticsDto.setMonth(specialDyeStatistics.getMonth());
            specialDyeStatisticsDto.setNormal(specialDyeStatistics.getTotal() - specialDyeStatistics.getSpecial());
            specialDyeStatisticsDto.setSpecial(specialDyeStatistics.getSpecial());
            specialDyeStatisticsDtoList.add(specialDyeStatisticsDto);
        }
        return specialDyeStatisticsDtoList;
    }

    @Override
    public List<WorkloadDto> getWorkload(StatisticsCondition condition) {
        List<Workload> workloads = statisticsRepository.selectWorkload(condition);
        List<WorkloadDto> workloadDtoList = new ArrayList<>();
        WorkloadDto workloadDto;
        for (Workload workload : workloads) {
            workloadDto = new WorkloadDto();
            BeanUtils.copyProperties(workload, workloadDto);
            workloadDto.setOperator(userApplication.getUserSimpleInfoById(workload.getOperator()));
            workloadDtoList.add(workloadDto);
        }
        return workloadDtoList;
    }

    @Override
    public Map<String, Long> getAvgWaitTime(StatisticsCondition condition) {
        Map<String, Long> data = new HashMap<>();
        Long registerTime = statisticsRepository.selectAvgRegisterWaitTime(condition);
        AvgWaitTimeCondition avgWaitTimeCondition = new AvgWaitTimeCondition();
        avgWaitTimeCondition.setStartTime(condition.getStartTime());
        avgWaitTimeCondition.setEndTime(condition.getEndTime());
        data.put("registerTime", registerTime);

        Long grossingTime = statisticsRepository.selectAvgNormalGrossingWaitTime(avgWaitTimeCondition);
        List<Long> blockIds = statisticsRepository.selectReGrossingBlockIds(condition);
        long size = blockIds.size();
        if (size > 0) {
            long reGrossingTime = 0;
            long count = 0;
            for (long blockId : blockIds) {
                Long time = blockApplication.getRegrossingWaitTime(blockId);
                if (time != null) {
                    reGrossingTime += time;
                    count++;
                }
            }
            if (count > 0) {
                grossingTime = (grossingTime + reGrossingTime / count) / 2;
            }
        }
        data.put("grossingTime", grossingTime);

        avgWaitTimeCondition.setOperationStart(TrackingOperation.grossing.toCode());
        avgWaitTimeCondition.setOperationEnd(TrackingOperation.grossingConfirm.toCode());
        Long grossingConfirmTime = statisticsRepository.selectAvgCommonOperationWaitTime(avgWaitTimeCondition);
        data.put("grossingConfirmTime", grossingConfirmTime);

        avgWaitTimeCondition.setOperationStart(TrackingOperation.grossingConfirm.toCode());
        avgWaitTimeCondition.setOperationEnd(TrackingOperation.dehydrate.toCode());
        Long dehydrateTime = statisticsRepository.selectAvgCommonOperationWaitTime(avgWaitTimeCondition);
        data.put("dehydrateTime", dehydrateTime);

        avgWaitTimeCondition.setOperationStart(TrackingOperation.dehydrateEnd.toCode());
        avgWaitTimeCondition.setOperationEnd(TrackingOperation.embed.toCode());
        Long embedTime = statisticsRepository.selectAvgCommonOperationWaitTime(avgWaitTimeCondition);
        data.put("embedTime", embedTime);

        Long sectionTime = statisticsRepository.selectAvgNormalSectionWaitTime(condition);
        List<Long> blocks = statisticsRepository.selectAbnormalSectionBlocksId(condition);
        if (blocks.size() > 0) {
            long count = 0;
            long totalTime = 0;
            for (long blockId : blocks) {
                List<Long> slideIds = statisticsRepository.selectAbnormalSectionIdsByBlockId(blockId);
                Long time = statisticsRepository.selectSectionWaitTimeByBlockIdAndSlideId(blockId, slideIds);
                if (time != null) {
                    totalTime += time;
                    count += slideIds.size();
                }
            }
            if (count > 0) {
                sectionTime = (sectionTime + totalTime / count) / 2;
            }
        }
        data.put("sectionTime", sectionTime);

        avgWaitTimeCondition.setOperationStart(TrackingOperation.section.toCode());
        avgWaitTimeCondition.setOperationEnd(TrackingOperation.dye.toCode());
        Long dyeTime = statisticsRepository.selectAvgCommonOperationWaitTime(avgWaitTimeCondition);
        data.put("dyeTime", dyeTime);

        avgWaitTimeCondition.setOperationStart(TrackingOperation.dye.toCode());
        avgWaitTimeCondition.setOperationEnd(TrackingOperation.completionConfirm.toCode());
        Long confirmTime = statisticsRepository.selectAvgCommonOperationWaitTime(avgWaitTimeCondition);
        data.put("confirmTime", confirmTime);

        Long diagnoseTime = statisticsRepository.selectAvgDiagnoseWaitTime(avgWaitTimeCondition);
        data.put("diagnoseTime", diagnoseTime);

        Long reportTime = statisticsRepository.selectAvgReportWaitTime(avgWaitTimeCondition);
        data.put("reportTime", reportTime);

        return data;
    }

    @Override
    public List<QualityRankDto> getQualityRank(StatisticsCondition condition) {
        List<QualityRank> qualityRanks = statisticsRepository.selectQualityRank(condition);
        List<QualityRankDto> qualityRankDtoList = new ArrayList<>();
        QualityRankDto qualityRankDto;
        for (QualityRank qualityRank : qualityRanks) {
            qualityRankDto = new QualityRankDto();
            int operation = qualityRank.getOperation();
            qualityRankDto.setAverage(qualityRank.getAverage());
            qualityRankDto.setOperation(operation);
            qualityRankDto.setOperationDesc(TrackingOperation.valueOf(operation).toString());
            qualityRankDto.setOperator(userApplication.getUserSimpleInfoById(qualityRank.getOperator()));
            qualityRankDtoList.add(qualityRankDto);
        }
        return qualityRankDtoList;
    }

    @Override
    public long countUnqualifiedSlide(StatisticsCondition condition) {
        return statisticsRepository.countUnqualifiedSlide(condition);
    }
}
