package com.lifetech.dhap.pathcloud.statistic.application.condition;

import com.lifetech.dhap.pathcloud.common.base.PageCondition;

import java.util.Date;

/**
 * @author eric.guo@ostengar.com
 * @version 1.0
 * @time 2017-02-16-09:22
 */
public class QualityControlCon extends PageCondition {

    private Date startTime;

    private Date endTime;

    private Integer operation;

    private Integer inspectCategory;

    private Integer specialDye;

    private Integer grossingScore;

    private Integer dehydrateScore;

    private Integer embedScore;

    private Integer sectionScore;

    private Integer dyeScore;

    private Integer mountingScore;

    private Integer pathStatus;

    public Integer getPathStatus() {
        return pathStatus;
    }

    public void setPathStatus(Integer pathStatus) {
        this.pathStatus = pathStatus;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Integer getOperation() {
        return operation;
    }

    public void setOperation(Integer operation) {
        this.operation = operation;
    }

    public Integer getInspectCategory() {
        return inspectCategory;
    }

    public void setInspectCategory(Integer inspectCategory) {
        this.inspectCategory = inspectCategory;
    }

    public Integer getSpecialDye() {
        return specialDye;
    }

    public void setSpecialDye(Integer specialDye) {
        this.specialDye = specialDye;
    }

    public Integer getGrossingScore() {
        return grossingScore;
    }

    public void setGrossingScore(Integer grossingScore) {
        this.grossingScore = grossingScore;
    }

    public Integer getDehydrateScore() {
        return dehydrateScore;
    }

    public void setDehydrateScore(Integer dehydrateScore) {
        this.dehydrateScore = dehydrateScore;
    }

    public Integer getEmbedScore() {
        return embedScore;
    }

    public void setEmbedScore(Integer embedScore) {
        this.embedScore = embedScore;
    }

    public Integer getSectionScore() {
        return sectionScore;
    }

    public void setSectionScore(Integer sectionScore) {
        this.sectionScore = sectionScore;
    }

    public Integer getDyeScore() {
        return dyeScore;
    }

    public void setDyeScore(Integer dyeScore) {
        this.dyeScore = dyeScore;
    }

    public Integer getMountingScore() {
        return mountingScore;
    }

    public void setMountingScore(Integer mountingScore) {
        this.mountingScore = mountingScore;
    }
}
