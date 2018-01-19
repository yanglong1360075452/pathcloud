package com.lifetech.dhap.pathcloud.statistic.application.condition;

import java.util.Date;

/**
 * Created by LiuMei on 2017-09-05.
 */
public class ReportCondition {

    private Integer pathStatus;

    private Date startTime;

    private Date endTime;

    private Integer reportDeadline;//常规报告期限-天数
    private Integer freezeReportDeadline;//冰冻报告期限-天数
    private Integer ihcReportDeadline;//免疫组化报告期限-天数
    private Integer specialDyeReportDeadline;//特染报告期限-天数
    private Integer consultReportDeadline;//会诊报告期限-天数
    private Integer decalcifyDeadline;//脱钙报告期限-天数
    private Integer difficultDeadline;//疑难病例报告期限-天数

    public Integer getConsultReportDeadline() {
        return consultReportDeadline;
    }

    public void setConsultReportDeadline(Integer consultReportDeadline) {
        this.consultReportDeadline = consultReportDeadline;
    }

    public Integer getDecalcifyDeadline() {
        return decalcifyDeadline;
    }

    public void setDecalcifyDeadline(Integer decalcifyDeadline) {
        this.decalcifyDeadline = decalcifyDeadline;
    }

    public Integer getDifficultDeadline() {
        return difficultDeadline;
    }

    public void setDifficultDeadline(Integer difficultDeadline) {
        this.difficultDeadline = difficultDeadline;
    }

    public Integer getReportDeadline() {
        return reportDeadline;
    }

    public void setReportDeadline(Integer reportDeadline) {
        this.reportDeadline = reportDeadline;
    }

    public Integer getFreezeReportDeadline() {
        return freezeReportDeadline;
    }

    public void setFreezeReportDeadline(Integer freezeReportDeadline) {
        this.freezeReportDeadline = freezeReportDeadline;
    }

    public Integer getIhcReportDeadline() {
        return ihcReportDeadline;
    }

    public void setIhcReportDeadline(Integer ihcReportDeadline) {
        this.ihcReportDeadline = ihcReportDeadline;
    }

    public Integer getSpecialDyeReportDeadline() {
        return specialDyeReportDeadline;
    }

    public void setSpecialDyeReportDeadline(Integer specialDyeReportDeadline) {
        this.specialDyeReportDeadline = specialDyeReportDeadline;
    }

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
}
