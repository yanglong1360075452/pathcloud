package com.lifetech.dhap.pathcloud.application.application.condition;

import com.lifetech.dhap.pathcloud.common.base.PageCondition;

import java.util.Date;
import java.util.List;

/**
 * Created by LuoMo on 2016-11-30.
 */
public class PathologyCondition extends PageCondition {

    private Long pathId;

    private Long specialApplyId;

    private Long assignGrossing; //指定取材医生

    private Long assignDiagnose; //指定诊断医生

    private String filter;

    private Date createTimeStart;

    private Date createTimeEnd;

    private Integer status;

    private Integer departments;

    private Integer inspectCategory; //检查类别

    //用于病理号段搜索 例P17010100001-P17010100100
    private String pathNoStart;

    private String pathNoEnd;

    private Long diagnoseDoctor;//诊断医生

    private Long reportDoctor;//报告医生

    private List<Integer> statusList;

    private Boolean reGrossing;//重补取

    private Integer reportDeadline;//常规报告期限-天数
    private Integer freezeReportDeadline;//冰冻报告期限-天数
    private Integer ihcReportDeadline;//免疫组化报告期限-天数
    private Integer specialDyeReportDeadline;//特染报告期限-天数
    private Integer consultReportDeadline;//会诊报告期限-天数
    private Integer decalcifyDeadline;//脱钙报告期限-天数
    private Integer difficultDeadline;//疑难病例报告期限-天数

    private Boolean delay;

    private Boolean usingFrozen;

    private Integer specialType; //特殊申请类别

    private Date estimateReportTime; //预计报告日期

    private Boolean outConsult;

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

    public Boolean getOutConsult() {
        return outConsult;
    }

    public void setOutConsult(Boolean outConsult) {
        this.outConsult = outConsult;
    }

    public Date getEstimateReportTime() {
        return estimateReportTime;
    }

    public void setEstimateReportTime(Date estimateReportTime) {
        this.estimateReportTime = estimateReportTime;
    }

    public Integer getSpecialType() {
        return specialType;
    }

    public void setSpecialType(Integer specialType) {
        this.specialType = specialType;
    }

    public Long getSpecialApplyId() {
        return specialApplyId;
    }

    public void setSpecialApplyId(Long specialApplyId) {
        this.specialApplyId = specialApplyId;
    }

    public Integer getConsultReportDeadline() {
        return consultReportDeadline;
    }

    public void setConsultReportDeadline(Integer consultReportDeadline) {
        this.consultReportDeadline = consultReportDeadline;
    }

    public Boolean getUsingFrozen() {
        return usingFrozen;
    }

    public void setUsingFrozen(Boolean usingFrozen) {
        this.usingFrozen = usingFrozen;
    }

    public Integer getInspectCategory() {
        return inspectCategory;
    }

    public void setInspectCategory(Integer inspectCategory) {
        this.inspectCategory = inspectCategory;
    }

    public Long getPathId() {
        return pathId;
    }

    public void setPathId(Long pathId) {
        this.pathId = pathId;
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

    public List<Integer> getStatusList() {
        return statusList;
    }

    public void setStatusList(List<Integer> statusList) {
        this.statusList = statusList;
    }

    public Long getAssignGrossing() {
        return assignGrossing;
    }

    public void setAssignGrossing(Long assignGrossing) {
        this.assignGrossing = assignGrossing;
    }

    public String getFilter() {
        return filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }

    public Date getCreateTimeStart() {
        return createTimeStart;
    }

    public void setCreateTimeStart(Date createTimeStart) {
        this.createTimeStart = createTimeStart;
    }

    public Date getCreateTimeEnd() {
        return createTimeEnd;
    }

    public void setCreateTimeEnd(Date createTimeEnd) {
        this.createTimeEnd = createTimeEnd;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getDepartments() {
        return departments;
    }

    public void setDepartments(Integer departments) {
        this.departments = departments;
    }

    public String getPathNoStart() {
        return pathNoStart;
    }

    public void setPathNoStart(String pathNoStart) {
        this.pathNoStart = pathNoStart;
    }

    public String getPathNoEnd() {
        return pathNoEnd;
    }

    public void setPathNoEnd(String pathNoEnd) {
        this.pathNoEnd = pathNoEnd;
    }

    public Long getDiagnoseDoctor() {
        return diagnoseDoctor;
    }

    public void setDiagnoseDoctor(Long diagnoseDoctor) {
        this.diagnoseDoctor = diagnoseDoctor;
    }

    public Long getReportDoctor() {
        return reportDoctor;
    }

    public void setReportDoctor(Long reportDoctor) {
        this.reportDoctor = reportDoctor;
    }

    public Long getAssignDiagnose() {
        return assignDiagnose;
    }

    public void setAssignDiagnose(Long assignDiagnose) {
        this.assignDiagnose = assignDiagnose;
    }

    public Boolean getReGrossing() {
        return reGrossing;
    }

    public void setReGrossing(Boolean reGrossing) {
        this.reGrossing = reGrossing;
    }
}
