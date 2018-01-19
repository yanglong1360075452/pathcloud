package com.lifetech.dhap.pathcloud.application.application.dto;

import com.lifetech.dhap.pathcloud.user.application.dto.UserSimpleDto;

import java.util.Date;

/**
 * @author eric.guo@ostengar.com
 * @version 1.0
 * @time 2016-11-23-16:10
 */
public class ApplicationSampleDto {

    private Long sampleId;

    private Long applicationId;

    private String sampleNumber;

    private String name;

    private Integer category;

    private String note;

    private String pathologyNumber;

    private Long id;

    private String serialNumber;

    private String hisId;

    private String patientName;

    private Integer status;
    private String statusName;

    private Integer pathologyStatus;
    private String pathologyStatusName;

    private String rejectReason;

    private String doctor;

    private Integer departments;
    private String departmentsDesc;

    private String doctorTel;

    private String inspectionItem;

    private String visitCat;

    private Boolean urgentFlag;

    private Long createBy;

    private Date createTime;

    private UserSimpleDto sampleCreateBy;

    private Date sampleCreateTime;

    private UserSimpleDto sampleUpdateBy;

    private Date sampleUpdateTime;

    private UserSimpleDto assignGrossing;

    private Boolean delete;

    private Long pathologyId;

    private Long updateBy;
    private String updateByName;

    private Date updateTime;

    private String number;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getUpdateByName() {
        return updateByName;
    }

    public void setUpdateByName(String updateByName) {
        this.updateByName = updateByName;
    }

    public Long getPathologyId() {
        return pathologyId;
    }

    public void setPathologyId(Long pathologyId) {
        this.pathologyId = pathologyId;
    }

    public Long getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(Long updateBy) {
        this.updateBy = updateBy;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Boolean getDelete() {
        return delete;
    }

    public void setDelete(Boolean delete) {
        this.delete = delete;
    }

    public UserSimpleDto getSampleCreateBy() {
        return sampleCreateBy;
    }

    public void setSampleCreateBy(UserSimpleDto sampleCreateBy) {
        this.sampleCreateBy = sampleCreateBy;
    }

    public UserSimpleDto getSampleUpdateBy() {
        return sampleUpdateBy;
    }

    public void setSampleUpdateBy(UserSimpleDto sampleUpdateBy) {
        this.sampleUpdateBy = sampleUpdateBy;
    }

    public Date getSampleCreateTime() {
        return sampleCreateTime;
    }

    public void setSampleCreateTime(Date sampleCreateTime) {
        this.sampleCreateTime = sampleCreateTime;
    }

    public Date getSampleUpdateTime() {
        return sampleUpdateTime;
    }

    public void setSampleUpdateTime(Date sampleUpdateTime) {
        this.sampleUpdateTime = sampleUpdateTime;
    }

    public UserSimpleDto getAssignGrossing() {
        return assignGrossing;
    }

    public void setAssignGrossing(UserSimpleDto assignGrossing) {
        this.assignGrossing = assignGrossing;
    }

    public Integer getPathologyStatus() {
        return pathologyStatus;
    }

    public void setPathologyStatus(Integer pathologyStatus) {
        this.pathologyStatus = pathologyStatus;
    }

    public String getPathologyStatusName() {
        return pathologyStatusName;
    }

    public void setPathologyStatusName(String pathologyStatusName) {
        this.pathologyStatusName = pathologyStatusName;
    }

    public String getDepartmentsDesc() {
        return departmentsDesc;
    }

    public void setDepartmentsDesc(String departmentsDesc) {
        this.departmentsDesc = departmentsDesc;
    }

    public Long getSampleId() {
        return sampleId;
    }

    public void setSampleId(Long sampleId) {
        this.sampleId = sampleId;
    }

    public Long getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(Long applicationId) {
        this.applicationId = applicationId;
    }

    public String getSampleNumber() {
        return sampleNumber;
    }

    public void setSampleNumber(String sampleNumber) {
        this.sampleNumber = sampleNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCategory() {
        return category;
    }

    public void setCategory(Integer category) {
        this.category = category;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getPathologyNumber() {
        return pathologyNumber;
    }

    public void setPathologyNumber(String pathologyNumber) {
        this.pathologyNumber = pathologyNumber;
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

    public String getHisId() {
        return hisId;
    }

    public void setHisId(String hisId) {
        this.hisId = hisId;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
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

    public String getRejectReason() {
        return rejectReason;
    }

    public void setRejectReason(String rejectReason) {
        this.rejectReason = rejectReason;
    }

    public String getDoctor() {
        return doctor;
    }

    public void setDoctor(String doctor) {
        this.doctor = doctor;
    }

    public Integer getDepartments() {
        return departments;
    }

    public void setDepartments(Integer departments) {
        this.departments = departments;
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

    public Boolean getUrgentFlag() {
        return urgentFlag;
    }

    public void setUrgentFlag(Boolean urgentFlag) {
        this.urgentFlag = urgentFlag;
    }

    public Long getCreateBy() {
        return createBy;
    }

    public void setCreateBy(Long createBy) {
        this.createBy = createBy;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}

