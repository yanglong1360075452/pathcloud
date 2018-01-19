package com.lifetech.dhap.pathcloud.tracking.application.dto;

import java.util.Date;

/**
 * Created by HP on 2017/4/13.
 */
public class ReportQueryDto {

    //病理id
    private Long id;

    private Long specialApplyId;

    //病理号
    private String serialNumber;

    private String name; //病人姓名

    private Long diagnoseDoctor;

    private String diagnoseDoctorDesc;

    private Date reportTime;

    /**
     * 报告是否延期
     */
    private Boolean delay;

    private Date printTime;

    private Long printUserId;  //打印人

    private String printUserName;

    private String postponeCause;  //延期原因

    private Integer departments;
    private String departmentName;

    private String doctor;

    private Boolean printStatus;

    public Long getSpecialApplyId() {
        return specialApplyId;
    }

    public void setSpecialApplyId(Long specialApplyId) {
        this.specialApplyId = specialApplyId;
    }

    public Integer getDepartments() {
        return departments;
    }

    public void setDepartments(Integer departments) {
        this.departments = departments;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getDoctor() {
        return doctor;
    }

    public void setDoctor(String doctor) {
        this.doctor = doctor;
    }

    public String getPostponeCause() {
        return postponeCause;
    }

    public void setPostponeCause(String postponeCause) {
        this.postponeCause = postponeCause;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Boolean getPrintStatus() {
        return printStatus;
    }

    public void setPrintStatus(Boolean printStatus) {
        this.printStatus = printStatus;
    }

    public Date getPrintTime() {
        return printTime;
    }

    public void setPrintTime(Date printTime) {
        this.printTime = printTime;
    }

    public Long getPrintUserId() {
        return printUserId;
    }

    public void setPrintUserId(Long printUserId) {
        this.printUserId = printUserId;
    }

    public String getPrintUserName() {
        return printUserName;
    }

    public void setPrintUserName(String printUserName) {
        this.printUserName = printUserName;
    }

}
