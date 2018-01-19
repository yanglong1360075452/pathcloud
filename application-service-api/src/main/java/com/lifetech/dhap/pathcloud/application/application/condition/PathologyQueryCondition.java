package com.lifetech.dhap.pathcloud.application.application.condition;

import com.lifetech.dhap.pathcloud.common.base.PageCondition;

import java.util.Date;
import java.util.List;

/**
 * Created by LiuMei on 2017-02-06.
 */
public class PathologyQueryCondition extends PageCondition {

    private String filter;

    private String fieldType;

    private String fieldContain;

    private String fieldExclusive;

    private Boolean parserNgram;

    private String match;

    private String again;

    private Integer specialDye;

    private String inspectionItem;//检测项目

    private Integer inspectionCategory;//检测类别

    private Integer pathStatus;

    private Integer noPathStatus;

    private Integer departments;

    private Long inspectionDoctor;

    private Long diagnoseDoctor;

    private Date receiveTimeStart;

    private Date receiveTimeEnd;

    private Date reportTimeStart;

    private Date reportTimeEnd;

    private Integer applyType;

    private Date applyTimeStart;

    private Date applyTimeEnd;

    private List<Long> applyUser;

    public List<Long> getApplyUser() {
        return applyUser;
    }

    public void setApplyUser(List<Long> applyUser) {
        this.applyUser = applyUser;
    }

    public Date getApplyTimeStart() {
        return applyTimeStart;
    }

    public void setApplyTimeStart(Date applyTimeStart) {
        this.applyTimeStart = applyTimeStart;
    }

    public Date getApplyTimeEnd() {
        return applyTimeEnd;
    }

    public void setApplyTimeEnd(Date applyTimeEnd) {
        this.applyTimeEnd = applyTimeEnd;
    }

    public Integer getApplyType() {
        return applyType;
    }

    public void setApplyType(Integer applyType) {
        this.applyType = applyType;
    }

    public String getFilter() {
        return filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }

    public String getFieldType() {
        return fieldType;
    }

    public void setFieldType(String fieldType) {
        this.fieldType = fieldType;
    }

    public String getFieldContain() {
        return fieldContain;
    }

    public void setFieldContain(String fieldContain) {
        this.fieldContain = fieldContain;
    }

    public String getFieldExclusive() {
        return fieldExclusive;
    }

    public void setFieldExclusive(String fieldExclusive) {
        this.fieldExclusive = fieldExclusive;
    }

    public Integer getSpecialDye() {
        return specialDye;
    }

    public void setSpecialDye(Integer specialDye) {
        this.specialDye = specialDye;
    }

    public Integer getPathStatus() {
        return pathStatus;
    }

    public void setPathStatus(Integer pathStatus) {
        this.pathStatus = pathStatus;
    }

    public Integer getDepartments() {
        return departments;
    }

    public void setDepartments(Integer departments) {
        this.departments = departments;
    }

    public Long getInspectionDoctor() {
        return inspectionDoctor;
    }

    public void setInspectionDoctor(Long inspectionDoctor) {
        this.inspectionDoctor = inspectionDoctor;
    }

    public Long getDiagnoseDoctor() {
        return diagnoseDoctor;
    }

    public void setDiagnoseDoctor(Long diagnoseDoctor) {
        this.diagnoseDoctor = diagnoseDoctor;
    }

    public Date getReceiveTimeStart() {
        return receiveTimeStart;
    }

    public void setReceiveTimeStart(Date receiveTimeStart) {
        this.receiveTimeStart = receiveTimeStart;
    }

    public Date getReceiveTimeEnd() {
        return receiveTimeEnd;
    }

    public void setReceiveTimeEnd(Date receiveTimeEnd) {
        this.receiveTimeEnd = receiveTimeEnd;
    }

    public Date getReportTimeStart() {
        return reportTimeStart;
    }

    public void setReportTimeStart(Date reportTimeStart) {
        this.reportTimeStart = reportTimeStart;
    }

    public Date getReportTimeEnd() {
        return reportTimeEnd;
    }

    public void setReportTimeEnd(Date reportTimeEnd) {
        this.reportTimeEnd = reportTimeEnd;
    }

    public String getInspectionItem() {
        return inspectionItem;
    }

    public void setInspectionItem(String inspectionItem) {
        this.inspectionItem = inspectionItem;
    }

    public Integer getInspectionCategory() {
        return inspectionCategory;
    }

    public void setInspectionCategory(Integer inspectionCategory) {
        this.inspectionCategory = inspectionCategory;
    }

    public String getMatch() {
        return match;
    }

    public void setMatch(String match) {
        this.match = match;
    }

    public String getAgain() {
        return again;
    }

    public void setAgain(String again) {
        this.again = again;
    }

    public Boolean getParserNgram() {
        return parserNgram;
    }

    public void setParserNgram(Boolean parserNgram) {
        this.parserNgram = parserNgram;
    }

    public Integer getNoPathStatus() {
        return noPathStatus;
    }

    public void setNoPathStatus(Integer noPathStatus) {
        this.noPathStatus = noPathStatus;
    }
}
