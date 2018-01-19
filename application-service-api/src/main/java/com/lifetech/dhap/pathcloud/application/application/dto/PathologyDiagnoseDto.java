package com.lifetech.dhap.pathcloud.application.application.dto;

import com.lifetech.dhap.pathcloud.user.application.dto.UserSimpleDto;

import java.util.Date;

/**
 * Created by LiuMei on 2017-01-13.
 */
public class PathologyDiagnoseDto {

    private Long id;

    private String serialNumber;

    private String patientName;

    private Integer departments;
    private String departmentsDesc;

    private UserSimpleDto inspectionDoctor;//送检医生

    private UserSimpleDto firstDiagnoseDoctor;//一级诊断医生

    private UserSimpleDto secondDiagnoseDoctor;//二级诊断医生

    private UserSimpleDto thirdDiagnoseDoctor;//三级诊断医生

    private UserSimpleDto reportDoctor;//报告医生

    private UserSimpleDto assignDiagnoseDoctor;//指定诊断医生(收片医生)

    private Date reportTime;

    private Integer status;
    private String statusDesc;

    private String number;

    private Integer type;
    private String typeDesc;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getTypeDesc() {
        return typeDesc;
    }

    public void setTypeDesc(String typeDesc) {
        this.typeDesc = typeDesc;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public UserSimpleDto getAssignDiagnoseDoctor() {
        return assignDiagnoseDoctor;
    }

    public void setAssignDiagnoseDoctor(UserSimpleDto assignDiagnoseDoctor) {
        this.assignDiagnoseDoctor = assignDiagnoseDoctor;
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

    public String getDepartmentsDesc() {
        return departmentsDesc;
    }

    public void setDepartmentsDesc(String departmentsDesc) {
        this.departmentsDesc = departmentsDesc;
    }

    public UserSimpleDto getInspectionDoctor() {
        return inspectionDoctor;
    }

    public void setInspectionDoctor(UserSimpleDto inspectionDoctor) {
        this.inspectionDoctor = inspectionDoctor;
    }

    public UserSimpleDto getFirstDiagnoseDoctor() {
        return firstDiagnoseDoctor;
    }

    public void setFirstDiagnoseDoctor(UserSimpleDto firstDiagnoseDoctor) {
        this.firstDiagnoseDoctor = firstDiagnoseDoctor;
    }

    public UserSimpleDto getSecondDiagnoseDoctor() {
        return secondDiagnoseDoctor;
    }

    public void setSecondDiagnoseDoctor(UserSimpleDto secondDiagnoseDoctor) {
        this.secondDiagnoseDoctor = secondDiagnoseDoctor;
    }

    public UserSimpleDto getThirdDiagnoseDoctor() {
        return thirdDiagnoseDoctor;
    }

    public void setThirdDiagnoseDoctor(UserSimpleDto thirdDiagnoseDoctor) {
        this.thirdDiagnoseDoctor = thirdDiagnoseDoctor;
    }

    public UserSimpleDto getReportDoctor() {
        return reportDoctor;
    }

    public void setReportDoctor(UserSimpleDto reportDoctor) {
        this.reportDoctor = reportDoctor;
    }

    public Date getReportTime() {
        return reportTime;
    }

    public void setReportTime(Date reportTime) {
        this.reportTime = reportTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getStatusDesc() {
        return statusDesc;
    }

    public void setStatusDesc(String statusDesc) {
        this.statusDesc = statusDesc;
    }
}
