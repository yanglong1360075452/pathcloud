package com.lifetech.dhap.pathcloud.statistic.api.impl;

import com.lifetech.dhap.pathcloud.common.constant.Config;
import com.lifetech.dhap.pathcloud.common.exception.BuzException;
import com.lifetech.dhap.pathcloud.common.exception.BuzExceptionCode;
import com.lifetech.dhap.pathcloud.common.response.ResponseVO;
import com.lifetech.dhap.pathcloud.setting.application.ParamSettingApplication;
import com.lifetech.dhap.pathcloud.statistic.api.StatisticsApi;
import com.lifetech.dhap.pathcloud.statistic.application.StatisticApplication;
import com.lifetech.dhap.pathcloud.statistic.application.condition.StatisticsCondition;
import com.lifetech.dhap.pathcloud.tracking.application.infrastructure.code.TrackingOperation;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by LiuMei on 2017-02-06.
 */
@Component("statisticsApi")
public class StatisticsApiImpl implements StatisticsApi {

    @Autowired
    private StatisticApplication statisticApplication;

    @Autowired
    ParamSettingApplication paramSettingApplication;

    @Override
    public ResponseVO getSlideQuality(Long startTime, Long endTime) throws BuzException {
        if (startTime == null || endTime == null || endTime < startTime) {
            throw new BuzException(BuzExceptionCode.ErrorParam);
        }
        StatisticsCondition condition = new StatisticsCondition();
        condition.setStartTime(new Date(startTime));
        condition.setEndTime(new Date(endTime));
        long total = statisticApplication.countAllSlides(condition);
        condition.setStep("grossing");
        Integer score = paramSettingApplication.getQualifiedScoreByWorkstation(TrackingOperation.grossing.toCode());
        condition.setScore(score == null ? Config.qualifiedScore : score);
        long grossing = statisticApplication.countUnqualifiedSlide(condition);

        condition.setStep("dehydrate");
        score = paramSettingApplication.getQualifiedScoreByWorkstation(TrackingOperation.dehydrate.toCode());
        condition.setScore(score == null ? Config.qualifiedScore : score);
        long dehydrate = statisticApplication.countUnqualifiedSlide(condition);

        condition.setStep("embedding");
        score = paramSettingApplication.getQualifiedScoreByWorkstation(TrackingOperation.embed.toCode());
        condition.setScore(score == null ? Config.qualifiedScore : score);
        long embedding = statisticApplication.countUnqualifiedSlide(condition);

        condition.setStep("sectioning");
        score = paramSettingApplication.getQualifiedScoreByWorkstation(TrackingOperation.section.toCode());
        condition.setScore(score == null ? Config.qualifiedScore : score);
        long sectioning = statisticApplication.countUnqualifiedSlide(condition);

        condition.setStep("staining");
        score = paramSettingApplication.getQualifiedScoreByWorkstation(TrackingOperation.dye.toCode());
        condition.setScore(score == null ? Config.qualifiedScore : score);
        long staining = statisticApplication.countUnqualifiedSlide(condition);

        condition.setStep("sealing");
        score = paramSettingApplication.getQualifiedScoreByWorkstation(TrackingOperation.mounting.toCode());
        condition.setScore(score == null ? Config.qualifiedScore : score);
        long sealing = statisticApplication.countUnqualifiedSlide(condition);

        Map<String, Long> result = new HashedMap();
        result.put("grossing", grossing);
        result.put("dehydrate", dehydrate);
        result.put("embedding", embedding);
        result.put("sectioning", sectioning);
        result.put("staining", staining);
        result.put("sealing", sealing);
        result.put("total", total);
        return new ResponseVO(result);
    }

    @Override
    public ResponseVO getInspectCategory(Long startTime, Long endTime) throws BuzException {
        if (startTime == null || endTime == null ||endTime < startTime) {
            throw new BuzException(BuzExceptionCode.ErrorParam);
        }
        StatisticsCondition condition = new StatisticsCondition();
        condition.setStartTime(new Date(startTime));
        condition.setEndTime(new Date(endTime));
        List<Map<String, Object>> data = statisticApplication.countPathGroupByInspectCategory(condition);
        List<Map<String, Object>> result = new ArrayList<>();
        for (Map<String, Object> map : data) {
            Integer inspectCategory = (Integer) map.get("inspectCategory");
            Long count = (Long) map.get("count");
            Map<String, Object> category = new HashedMap();
            category.put("inspectCategory", paramSettingApplication.getInspectCategoryByCode(inspectCategory));
            category.put("count", count);
            result.add(category);
        }
        return new ResponseVO(result);
    }

    @Override
    public ResponseVO getReGrossing(Long startTime, Long endTime) throws BuzException {
        if (startTime == null || endTime == null ||endTime < startTime) {
            throw new BuzException(BuzExceptionCode.ErrorParam);
        }
        StatisticsCondition condition = new StatisticsCondition();
        condition.setStartTime(new Date(startTime));
        condition.setEndTime(new Date(endTime));
        long total = statisticApplication.countNotErrorBlocks(condition);
        long reGrossing = statisticApplication.countReGrossingBlocks(condition);
        Map<String, Long> result = new HashedMap();
        result.put("reGrossing", reGrossing);
        result.put("total", total);
        return new ResponseVO(result);
    }

    @Override
    public ResponseVO getReSection(Long startTime, Long endTime) throws BuzException {
        if (startTime == null || endTime == null ||endTime < startTime) {
            throw new BuzException(BuzExceptionCode.ErrorParam);
        }
        StatisticsCondition condition = new StatisticsCondition();
        condition.setStartTime(new Date(startTime));
        condition.setEndTime(new Date(endTime));
        long total = statisticApplication.countNotErrorBlocks(condition);
        long reSection = statisticApplication.countReSectionBlocks(condition) + statisticApplication.countDeepSectionBlocks(condition);
        Map<String, Long> result = new HashedMap();
        result.put("reSection", reSection);
        result.put("total", total);
        return new ResponseVO(result);
    }

    @Override
    public ResponseVO getErrorBlocks(Long startTime, Long endTime) throws BuzException {
        if (startTime == null || endTime == null ||endTime < startTime) {
            throw new BuzException(BuzExceptionCode.ErrorParam);
        }
        StatisticsCondition condition = new StatisticsCondition();
        condition.setStartTime(new Date(startTime));
        condition.setEndTime(new Date(endTime));
        long total = statisticApplication.countAllBlocks(condition);
        long error = statisticApplication.countErrorBlocks(condition);
        Map<String, Long> result = new HashedMap();
        result.put("error", error);
        result.put("total", total);
        return new ResponseVO(result);
    }

    @Override
    public ResponseVO getErrorSlides(Long startTime, Long endTime) throws BuzException {
        if (startTime == null || endTime == null ||endTime < startTime) {
            throw new BuzException(BuzExceptionCode.ErrorParam);
        }
        StatisticsCondition condition = new StatisticsCondition();
        condition.setStartTime(new Date(startTime));
        condition.setEndTime(new Date(endTime));
        long total = statisticApplication.countAllSlides(condition);
        long error = statisticApplication.countErrorSlides(condition);
        Map<String, Long> result = new HashedMap();
        result.put("error", error);
        result.put("total", total);
        return new ResponseVO(result);
    }

    @Override
    public ResponseVO getSpecialDye() throws BuzException {
        return new ResponseVO(statisticApplication.getSpecialDyeStatistics());
    }

    @Override
    public ResponseVO getWorkload(Long startTime, Long endTime, Integer workStation) throws BuzException {
        if (startTime == null || endTime == null ||endTime < startTime ||  workStation == null ||
                (workStation != 1 && workStation != 3 && workStation != 5 && workStation != 6)) {
            throw new BuzException(BuzExceptionCode.ErrorParam);
        }
        StatisticsCondition condition = new StatisticsCondition();
        condition.setStartTime(new Date(startTime));
        condition.setEndTime(new Date(endTime));
        condition.setStation(workStation);
        return new ResponseVO(statisticApplication.getWorkload(condition));
    }

    @Override
    public ResponseVO getAvgWaitTime() throws BuzException {
        StatisticsCondition condition = new StatisticsCondition();
        Map<String, Long> avgWaitTime = statisticApplication.getAvgWaitTime(condition);
        return new ResponseVO(avgWaitTime);
    }

    @Override
    public ResponseVO getScore(Long startTime, Long endTime) throws BuzException {
        if (startTime == null || endTime == null ||endTime < startTime) {
            throw new BuzException(BuzExceptionCode.ErrorParam);
        }
        StatisticsCondition condition = new StatisticsCondition();
        condition.setEndTime(new Date(endTime));
        condition.setStartTime(new Date(startTime));
        return new ResponseVO(statisticApplication.getQualityRank(condition));
    }

}
