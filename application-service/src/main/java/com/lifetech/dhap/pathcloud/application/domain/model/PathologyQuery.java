package com.lifetech.dhap.pathcloud.application.domain.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by LiuMei on 2017-02-06.
 */
public class PathologyQuery implements Serializable {

    private Long id;

    private String hisId;

    private String serialNumber;

    private String patientName;

    private Integer sex;

    private Integer age;

    private String admissionNo;//住院号

    private Integer departments;

    private Long inspectDoctor;//送检医生

    private String inspectHospital;//送检医院

    private Date receiveTime;

    private Date reportTime;

    private Integer status;

    private String research;

    private Date applyTime;

    private Long assignDiagnose;

    private Long applicationId;

    public Long getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(Long applicationId) {
        this.applicationId = applicationId;
    }

    public Long getAssignDiagnose() {
        return assignDiagnose;
    }

    public void setAssignDiagnose(Long assignDiagnose) {
        this.assignDiagnose = assignDiagnose;
    }

    public Date getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(Date applyTime) {
        this.applyTime = applyTime;
    }

    public String getResearch() {
        return research;
    }

    public void setResearch(String research) {
        this.research = research;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHisId() {
        return hisId;
    }

    public void setHisId(String hisId) {
        this.hisId = hisId;
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

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getAdmissionNo() {
        return admissionNo;
    }

    public void setAdmissionNo(String admissionNo) {
        this.admissionNo = admissionNo;
    }

    public Integer getDepartments() {
        return departments;
    }

    public void setDepartments(Integer departments) {
        this.departments = departments;
    }

    public Long getInspectDoctor() {
        return inspectDoctor;
    }

    public void setInspectDoctor(Long inspectDoctor) {
        this.inspectDoctor = inspectDoctor;
    }

    public String getInspectHospital() {
        return inspectHospital;
    }

    public void setInspectHospital(String inspectHospital) {
        this.inspectHospital = inspectHospital;
    }

    public Date getReceiveTime() {
        return receiveTime;
    }

    public void setReceiveTime(Date receiveTime) {
        this.receiveTime = receiveTime;
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
}
