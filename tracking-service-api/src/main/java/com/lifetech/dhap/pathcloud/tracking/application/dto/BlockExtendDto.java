package com.lifetech.dhap.pathcloud.tracking.application.dto;

import com.lifetech.dhap.pathcloud.user.application.dto.UserSimpleDto;

import java.util.Date;
import java.util.List;

/**
 * Created by LiuMei on 2016-12-20.
 */
public class BlockExtendDto {

    private Long applicationId;

    private Long pathologyId;

    private Long blockId;

    private String applicationSerialNumber;

    private String pathologySerialNumber;

    private String blockSerialNumber;

    /**
     * 病人姓名
     */
    private String patientName;

    /**
     * 送检科室
     */
    private Integer departments;
    private String departmentsDesc;

    /**
     * 取材医生
     */
    private UserSimpleDto grossingDoctor;

    /**
     * 取材备注
     */
    private String grossingRemark;

    /**
     * 包埋备注
     */
    private String embedRemark;

    /**
     * 包埋备注类别
     */
    private String embedRemarkType;

    /**
     * 切片备注
     */
    private String sectionRemark;
    private String sectionRemarkType;

    /**
     * 蜡块号
     */
    private String subId;

    /**
     * 取材部位
     */
    private String bodyPart;

    private Integer unit;
    private String unitDesc;

    private Integer count;

    private Integer biaoshi;
    private String biaoshiDesc;

    private Integer status;
    private String statusDesc;

    private List<String> marker;

    private Integer specialDye;
    private String specialDyeDesc;

    private String slideMarker;

    private Long slideId;

    private Long applyId;

    private Boolean embedPause;

    private long embedOperator;
    private String embedOperatorDesc;

    private long sectionOperator;
    private String sectionOperatorDesc;

    private Date embedOperateTime;
    private Date sectionOperateTime;

    private Integer print;

    /**
     * 特殊申请号
     */
    private String number;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Long getSlideId() {
        return slideId;
    }

    public void setSlideId(Long slideId) {
        this.slideId = slideId;
    }

    public Integer getPrint() {
        return print;
    }

    public void setPrint(Integer print) {
        this.print = print;
    }

    public long getEmbedOperator() {
        return embedOperator;
    }

    public void setEmbedOperator(long embedOperator) {
        this.embedOperator = embedOperator;
    }

    public String getEmbedOperatorDesc() {
        return embedOperatorDesc;
    }

    public void setEmbedOperatorDesc(String embedOperatorDesc) {
        this.embedOperatorDesc = embedOperatorDesc;
    }

    public long getSectionOperator() {
        return sectionOperator;
    }

    public void setSectionOperator(long sectionOperator) {
        this.sectionOperator = sectionOperator;
    }

    public String getSectionOperatorDesc() {
        return sectionOperatorDesc;
    }

    public void setSectionOperatorDesc(String sectionOperatorDesc) {
        this.sectionOperatorDesc = sectionOperatorDesc;
    }

    public Date getEmbedOperateTime() {
        return embedOperateTime;
    }

    public void setEmbedOperateTime(Date embedOperateTime) {
        this.embedOperateTime = embedOperateTime;
    }

    public Date getSectionOperateTime() {
        return sectionOperateTime;
    }

    public void setSectionOperateTime(Date sectionOperateTime) {
        this.sectionOperateTime = sectionOperateTime;
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

    public String getSlideMarker() {
        return slideMarker;
    }

    public void setSlideMarker(String slideMarker) {
        this.slideMarker = slideMarker;
    }

    public Integer getSpecialDye() {
        return specialDye;
    }

    public void setSpecialDye(Integer specialDye) {
        this.specialDye = specialDye;
    }

    public String getSpecialDyeDesc() {
        return specialDyeDesc;
    }

    public void setSpecialDyeDesc(String specialDyeDesc) {
        this.specialDyeDesc = specialDyeDesc;
    }

    public List<String> getMarker() {
        return marker;
    }

    public void setMarker(List<String> marker) {
        this.marker = marker;
    }

    public String getEmbedRemarkType() {
        return embedRemarkType;
    }

    public void setEmbedRemarkType(String embedRemarkType) {
        this.embedRemarkType = embedRemarkType;
    }

    public String getSectionRemarkType() {
        return sectionRemarkType;
    }

    public void setSectionRemarkType(String sectionRemarkType) {
        this.sectionRemarkType = sectionRemarkType;
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

    public String getDepartmentsDesc() {
        return departmentsDesc;
    }

    public void setDepartmentsDesc(String departmentsDesc) {
        this.departmentsDesc = departmentsDesc;
    }

    public UserSimpleDto getGrossingDoctor() {
        return grossingDoctor;
    }

    public void setGrossingDoctor(UserSimpleDto grossingDoctor) {
        this.grossingDoctor = grossingDoctor;
    }

    public String getGrossingRemark() {
        return grossingRemark;
    }

    public void setGrossingRemark(String grossingRemark) {
        this.grossingRemark = grossingRemark;
    }

    public String getEmbedRemark() {
        return embedRemark;
    }

    public void setEmbedRemark(String embedRemark) {
        this.embedRemark = embedRemark;
    }

    public String getSectionRemark() {
        return sectionRemark;
    }

    public void setSectionRemark(String sectionRemark) {
        this.sectionRemark = sectionRemark;
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

    public String getUnitDesc() {
        return unitDesc;
    }

    public void setUnitDesc(String unitDesc) {
        this.unitDesc = unitDesc;
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

    public String getBiaoshiDesc() {
        return biaoshiDesc;
    }

    public void setBiaoshiDesc(String biaoshiDesc) {
        this.biaoshiDesc = biaoshiDesc;
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
