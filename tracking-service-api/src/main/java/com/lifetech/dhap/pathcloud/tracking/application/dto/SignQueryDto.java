package com.lifetech.dhap.pathcloud.tracking.application.dto;

import java.util.Date;

/**
 * Created by HP on 2017/5/22.
 */
public class SignQueryDto {

    /**
     * 病理id
     */
    private Long pathId;

    /**
     * 病理号或特殊申请号
     */
    private String serialNumber;

    /**
     * 特殊申请ID
     */
    private Long specialApplyId;

    private String name; //病人姓名

    private Integer departments;

    private String doctor;

    private Long diagnoseDoctor;
    private String diagnoseDoctorDesc;

    private Date reportTime;

    private Boolean printStatus;

    /**
     * 报告是否延期
     */
    private Boolean delay;

    private Integer operation;

    public Boolean getPrintStatus() {
        return printStatus;
    }

    public void setPrintStatus(Boolean printStatus) {
        this.printStatus = printStatus;
    }

    public Long getSpecialApplyId() {
        return specialApplyId;
    }

    public void setSpecialApplyId(Long specialApplyId) {
        this.specialApplyId = specialApplyId;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getDepartments() {
        return departments;
    }

    public void setDepartments(Integer departments) {
        this.departments = departments;
    }

    public String getDoctor() {
        return doctor;
    }

    public void setDoctor(String doctor) {
        this.doctor = doctor;
    }

    public Date getReportTime() {
        return reportTime;
    }

    public void setReportTime(Date reportTime) {
        this.reportTime = reportTime;
    }

    public Boolean getDelay() {
        return delay;
    }

    public void setDelay(Boolean delay) {
        this.delay = delay;
    }

    public Integer getOperation() {
        return operation;
    }

    public void setOperation(Integer operation) {
        this.operation = operation;
    }

    public Long getPathId() {
        return pathId;
    }

    public void setPathId(Long pathId) {
        this.pathId = pathId;
    }

    public Long getDiagnoseDoctor() {
        return diagnoseDoctor;
    }

    public void setDiagnoseDoctor(Long diagnoseDoctor) {
        this.diagnoseDoctor = diagnoseDoctor;
    }

    public String getDiagnoseDoctorDesc() {
        return diagnoseDoctorDesc;
    }

    public void setDiagnoseDoctorDesc(String diagnoseDoctorDesc) {
        this.diagnoseDoctorDesc = diagnoseDoctorDesc;
    }
}
