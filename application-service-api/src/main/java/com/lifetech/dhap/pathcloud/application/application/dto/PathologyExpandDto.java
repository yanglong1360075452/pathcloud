package com.lifetech.dhap.pathcloud.application.application.dto;

import java.util.Date;

/**
 * Created by LuoMo on 2016-11-30.
 */
public class PathologyExpandDto extends PathologyDto {

    private String patientName;

    private Integer departments;

    private String inspectionItem;

    private Boolean urgentFlag;

    private Date inspectionTime;

    private Long inspectionDoctor;//送检医生

    private Long firstDiagnoseDoctor;//一级诊断医生

    private Long secondDiagnoseDoctor;//二级诊断医生

    private Long thirdDiagnoseDoctor;//三级诊断医生

    private Long reportDoctor;//报告医生

    private Date reportTime;

    public Date getInspectionTime() {
        return inspectionTime;
    }

    public void setInspectionTime(Date inspectionTime) {
        this.inspectionTime = inspectionTime;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public Integer getDepartments() {
        return departments;
    }

    public void setDepartments(Integer departments) {
        this.departments = departments;
    }

    public String getInspectionItem() {
        return inspectionItem;
    }

    public void setInspectionItem(String inspectionItem) {
        this.inspectionItem = inspectionItem;
    }

    public Boolean getUrgentFlag() {
        return urgentFlag;
    }

    public void setUrgentFlag(Boolean urgentFlag) {
        this.urgentFlag = urgentFlag;
    }

    public Long getInspectionDoctor() {
        return inspectionDoctor;
    }

    public void setInspectionDoctor(Long inspectionDoctor) {
        this.inspectionDoctor = inspectionDoctor;
    }

    public Long getFirstDiagnoseDoctor() {
        return firstDiagnoseDoctor;
    }

    public void setFirstDiagnoseDoctor(Long firstDiagnoseDoctor) {
        this.firstDiagnoseDoctor = firstDiagnoseDoctor;
    }

    public Long getSecondDiagnoseDoctor() {
        return secondDiagnoseDoctor;
    }

    public void setSecondDiagnoseDoctor(Long secondDiagnoseDoctor) {
        this.secondDiagnoseDoctor = secondDiagnoseDoctor;
    }

    public Long getThirdDiagnoseDoctor() {
        return thirdDiagnoseDoctor;
    }

    public void setThirdDiagnoseDoctor(Long thirdDiagnoseDoctor) {
        this.thirdDiagnoseDoctor = thirdDiagnoseDoctor;
    }

    public Long getReportDoctor() {
        return reportDoctor;
    }

    public void setReportDoctor(Long reportDoctor) {
        this.reportDoctor = reportDoctor;
    }

    public Date getReportTime() {
        return reportTime;
    }

    public void setReportTime(Date reportTime) {
        this.reportTime = reportTime;
    }
}
