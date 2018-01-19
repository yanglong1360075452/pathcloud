package com.lifetech.dhap.pathcloud.tracking.application.condition;

import com.lifetech.dhap.pathcloud.common.base.PageCondition;

import java.util.Date;

/**
 * Created by HP on 2017/4/13.
 */
public class ReportCondition extends PageCondition {

    private String filter;

    private Date startTime;

    private Date endTime;

    private Boolean printStatus;

    private Long printUserId;

    private Integer departments;

    private Integer operation;

    private Boolean delay;

    private Integer reportDeadline;//常规报告期限-天数
    private Integer freezeReportDeadline;//冰冻报告期限-天数
    private Integer ihcReportDeadline;//免疫组化报告期限-天数
    private Integer specialDyeReportDeadline;//特染报告期限-天数
    private Integer consultReportDeadline;//会诊报告期限-天数
    private Integer decalcifyDeadline;//脱钙报告期限-天数
    private Integer difficultDeadline;//疑难病例报告期限-天数

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

    public Boolean getDelay() {
        return delay;
    }

    public void setDelay(Boolean delay) {
        this.delay = delay;
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

    public Integer getConsultReportDeadline() {
        return consultReportDeadline;
    }

    public void setConsultReportDeadline(Integer consultReportDeadline) {
        this.consultReportDeadline = consultReportDeadline;
    }

    public Integer getOperation() {
        return operation;
    }

    public void setOperation(Integer operation) {
        this.operation = operation;
    }

    public Integer getDepartments() {
        return departments;
    }

    public void setDepartments(Integer departments) {
        this.departments = departments;
    }

    public Long getPrintUserId() {
        return printUserId;
    }

    public void setPrintUserId(Long printUserId) {
        this.printUserId = printUserId;
    }

    public String getFilter() {
        return filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
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

    public Boolean getPrintStatus() {
        return printStatus;
    }

    public void setPrintStatus(Boolean printStatus) {
        this.printStatus = printStatus;
    }
}
