package com.lifetech.dhap.pathcloud.tracking.domain.model;

import java.util.Date;

/**
 * Created by LiuMei on 2016-12-20.
 */
public class BlockExtend {

    private Long applicationId;

    private Long pathologyId;

    private Long blockId;

    private String applicationSerialNumber;

    private String pathologySerialNumber;

    private String blockSerialNumber;

    private String patientName; //病人姓名

    private Integer departments;//送检科室

    private Long grossingDoctor;//取材医生
    private String grossingDoctorDesc;//取材医生

    private String grossingRemark;//取材备注

    private String subId;//蜡块号

    private String bodyPart;//取材部位

    private Integer unit;

    private Integer count;

    private Integer biaoshi;

    private Integer status;

    private Integer specialDye;

    private String marker;

    private Long applyId;

    private Boolean embedPause;

    private long updateBy;

    private Date updateTime;

    public String getGrossingDoctorDesc() {
        return grossingDoctorDesc;
    }

    public void setGrossingDoctorDesc(String grossingDoctorDesc) {
        this.grossingDoctorDesc = grossingDoctorDesc;
    }

    public long getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(long updateBy) {
        this.updateBy = updateBy;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Boolean getEmbedPause() {
        return embedPause;
    }

    public void setEmbedPause(Boolean embedPause) {
        this.embedPause = embedPause;
    }

    public Long getApplyId() {
        return applyId;
    }

    public void setApplyId(Long applyId) {
        this.applyId = applyId;
    }

    public String getMarker() {
        return marker;
    }

    public void setMarker(String marker) {
        this.marker = marker;
    }

    public Integer getSpecialDye() {
        return specialDye;
    }

    public void setSpecialDye(Integer specialDye) {
        this.specialDye = specialDye;
    }

    public Long getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(Long applicationId) {
        this.applicationId = applicationId;
    }

    public Long getPathologyId() {
        return pathologyId;
    }

    public void setPathologyId(Long pathologyId) {
        this.pathologyId = pathologyId;
    }

    public Long getBlockId() {
        return blockId;
    }

    public void setBlockId(Long blockId) {
        this.blockId = blockId;
    }

    public String getApplicationSerialNumber() {
        return applicationSerialNumber;
    }

    public void setApplicationSerialNumber(String applicationSerialNumber) {
        this.applicationSerialNumber = applicationSerialNumber;
    }

    public String getPathologySerialNumber() {
        return pathologySerialNumber;
    }

    public void setPathologySerialNumber(String pathologySerialNumber) {
        this.pathologySerialNumber = pathologySerialNumber;
    }

    public String getBlockSerialNumber() {
        return blockSerialNumber;
    }

    public void setBlockSerialNumber(String blockSerialNumber) {
        this.blockSerialNumber = blockSerialNumber;
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

    public String getGrossingRemark() {
        return grossingRemark;
    }

    public void setGrossingRemark(String grossingRemark) {
        this.grossingRemark = grossingRemark;
    }

    public String getSubId() {
        return subId;
    }

    public void setSubId(String subId) {
        this.subId = subId;
    }

    public String getBodyPart() {
        return bodyPart;
    }

    public void setBodyPart(String bodyPart) {
        this.bodyPart = bodyPart;
    }

    public Integer getUnit() {
        return unit;
    }

    public void setUnit(Integer unit) {
        this.unit = unit;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getBiaoshi() {
        return biaoshi;
    }

    public void setBiaoshi(Integer biaoshi) {
        this.biaoshi = biaoshi;
    }

    public Long getGrossingDoctor() {
        return grossingDoctor;
    }

    public void setGrossingDoctor(Long grossingDoctor) {
        this.grossingDoctor = grossingDoctor;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
