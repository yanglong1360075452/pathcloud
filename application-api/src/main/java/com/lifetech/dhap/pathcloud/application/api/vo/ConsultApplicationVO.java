package com.lifetech.dhap.pathcloud.application.api.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by LuoMo on 2016-11-22.
 */
public class ConsultApplicationVO implements Serializable{

    private Long id;

    private String serialNumber;

    private String patientName;

    private String patientNo;

    private String admissionNo;

    private Integer status;
    private String statusName;

    private Integer age;

    private Integer sex;

    private Integer maritalStatus;

    private String originPlace;

    private String profession;

    private String patientTel;

    private Integer hospital;

    private String doctor;

    private String doctorTel;

    private String inspectionItem;

    private String visitCat;

    private Long createBy;

    private Long updateBy;

    private Date createTime;

    private Date updateTime;

    private String clinicalDiagnosis;

    private String medicalSummary;

    private Integer applyType;
    private String applyTypeDesc;

    private Integer departments;

    private List<OriginVO> origins; //原始蜡块/玻片信息

    public List<OriginVO> getOrigins() {
        return origins;
    }

    public void setOrigins(List<OriginVO> origins) {
        this.origins = origins;
    }

    public Integer getDepartments() {
        return departments;
    }

    public void setDepartments(Integer departments) {
        this.departments = departments;
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

    public String getPatientNo() {
        return patientNo;
    }

    public void setPatientNo(String patientNo) {
        this.patientNo = patientNo;
    }

    public String getAdmissionNo() {
        return admissionNo;
    }

    public void setAdmissionNo(String admissionNo) {
        this.admissionNo = admissionNo;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public Integer getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(Integer maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public String getOriginPlace() {
        return originPlace;
    }

    public void setOriginPlace(String originPlace) {
        this.originPlace = originPlace;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getPatientTel() {
        return patientTel;
    }

    public void setPatientTel(String patientTel) {
        this.patientTel = patientTel;
    }

    public Integer getHospital() {
        return hospital;
    }

    public void setHospital(Integer hospital) {
        this.hospital = hospital;
    }

    public String getDoctor() {
        return doctor;
    }

    public void setDoctor(String doctor) {
        this.doctor = doctor;
    }

    public String getDoctorTel() {
        return doctorTel;
    }

    public void setDoctorTel(String doctorTel) {
        this.doctorTel = doctorTel;
    }

    public String getInspectionItem() {
        return inspectionItem;
    }

    public void setInspectionItem(String inspectionItem) {
        this.inspectionItem = inspectionItem;
    }

    public String getVisitCat() {
        return visitCat;
    }

    public void setVisitCat(String visitCat) {
        this.visitCat = visitCat;
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

    public String getClinicalDiagnosis() {
        return clinicalDiagnosis;
    }

    public void setClinicalDiagnosis(String clinicalDiagnosis) {
        this.clinicalDiagnosis = clinicalDiagnosis;
    }

    public String getMedicalSummary() {
        return medicalSummary;
    }

    public void setMedicalSummary(String medicalSummary) {
        this.medicalSummary = medicalSummary;
    }

    public Integer getApplyType() {
        return applyType;
    }

    public void setApplyType(Integer applyType) {
        this.applyType = applyType;
    }

    public String getApplyTypeDesc() {
        return applyTypeDesc;
    }

    public void setApplyTypeDesc(String applyTypeDesc) {
        this.applyTypeDesc = applyTypeDesc;
    }
}
