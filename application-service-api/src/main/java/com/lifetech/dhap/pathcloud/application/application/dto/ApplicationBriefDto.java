package com.lifetech.dhap.pathcloud.application.application.dto;

import java.util.Date;
import java.util.List;

/**
 * Created by LuoMo on 2016-11-22.
 */
public class ApplicationBriefDto {

    private Long id;

    private String serialNumber;

    private String hisId;

    private String patientName;

    private Integer status;

    private String statusName;

    private String rejectReason;
    private String reasonType;

    private String inspectionItem;

    private String visitCat;

    private Integer age;

    private Integer sex;

    private Boolean urgentFlag;

    private Long createBy;

    private Date createTime;

    private List<SampleDto> samples;

    private Integer applyType;
    private String applyTypeDesc;

    private Integer researchType;
    private String researchTypeDesc;

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

    public Integer getResearchType() {
        return researchType;
    }

    public void setResearchType(Integer researchType) {
        this.researchType = researchType;
    }

    public String getResearchTypeDesc() {
        return researchTypeDesc;
    }

    public void setResearchTypeDesc(String researchTypeDesc) {
        this.researchTypeDesc = researchTypeDesc;
    }

    public Boolean getUrgentFlag() {
        return urgentFlag;
    }

    public String getReasonType() {
        return reasonType;
    }

    public void setReasonType(String reasonType) {
        this.reasonType = reasonType;
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

    public String getRejectReason() {
        return rejectReason;
    }

    public void setRejectReason(String rejectReason) {
        this.rejectReason = rejectReason;
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

    public Boolean getIsUrgentFlag() {
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

    public List<SampleDto> getSamples() {
        return samples;
    }

    public void setSamples(List<SampleDto> samples) {
        this.samples = samples;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }
}
