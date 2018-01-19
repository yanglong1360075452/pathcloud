package com.lifetech.dhap.pathcloud.application.application.dto;

import java.util.Date;

/**
 * Created by LiuMei on 2017-10-09.
 */
public class ConsultDto {

    private Long id;

    private String number;

    private Long applicationId;

    private Integer status;

    private String patientName;

    private Integer departments;

    private Integer hospital;

    private Long createBy;

    private Long updateBy;

    private Date createTime;

    private Date updateTime;

    private String result;

    private String diagnose;

    private String requirement;

    private String note;

    private String consultResult;

    private String reportPic;

    private String outConsultResult;

    private Long assignDiagnose;

    private Boolean outConsult;

    public String getOutConsultResult() {
        return outConsultResult;
    }

    public void setOutConsultResult(String outConsultResult) {
        this.outConsultResult = outConsultResult;
    }

    public Long getAssignDiagnose() {
        return assignDiagnose;
    }

    public void setAssignDiagnose(Long assignDiagnose) {
        this.assignDiagnose = assignDiagnose;
    }

    public Boolean getOutConsult() {
        return outConsult;
    }

    public void setOutConsult(Boolean outConsult) {
        this.outConsult = outConsult;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Long getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(Long applicationId) {
        this.applicationId = applicationId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

    public Long getCreateBy() {
        return createBy;
    }

    public void setCreateBy(Long createBy) {
        this.createBy = createBy;
    }

    public Long getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(Long updateBy) {
        this.updateBy = updateBy;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getDiagnose() {
        return diagnose;
    }

    public void setDiagnose(String diagnose) {
        this.diagnose = diagnose;
    }

    public String getRequirement() {
        return requirement;
    }

    public void setRequirement(String requirement) {
        this.requirement = requirement;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Integer getHospital() {
        return hospital;
    }

    public void setHospital(Integer hospital) {
        this.hospital = hospital;
    }

    public String getConsultResult() {
        return consultResult;
    }

    public void setConsultResult(String consultResult) {
        this.consultResult = consultResult;
    }

    public String getReportPic() {
        return reportPic;
    }

    public void setReportPic(String reportPic) {
        this.reportPic = reportPic;
    }
}
