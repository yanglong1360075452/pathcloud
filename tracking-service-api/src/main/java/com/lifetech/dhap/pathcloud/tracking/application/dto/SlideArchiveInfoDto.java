package com.lifetech.dhap.pathcloud.tracking.application.dto;

import java.util.Date;

/**
 * Created by HP on 2017/6/7.
 */
public class SlideArchiveInfoDto {

    private  Long blockArchiveId; //蜡块存档ID

    private String serialNumber; //病理号

    private Long pathId; //病理ID

    private String blockSubId; //蜡块号

    private Long blockId; //蜡块ID

    private String slideSubId; //玻片号

    private Long slideId; //玻片ID

    private String ihcMarker; //试剂抗体

    private String patientName; //患者姓名

    private String dryingBox; //晾片编号

    private Long dryingCreateBy; //晾片技术员
    private String dryingCreateByDesc;

    private Date dryingCreateTime; //晾片日期


    private String archiveBox; //抽屉编号

    private Long archiveCreateBy;  //存档技术员
    private String archiveCreateByDesc;

    private Date archiveCreateTime;  //存档日期

    private Long borrowID;
    private String borrowName; //借阅人

    private String unit; //单位

    private String borrowPhone;

    private Date createTime; //借阅日期

    private Date planBack; //计划归还日期

    private Date realBack; //实际归还日期

    private Integer overdue; //逾期

    private  Integer applyType; //申请类别

    private Integer status;  //2-借出/0-晾片/1-存档 玻片状态
    private String statusDesc;

    private Integer archiveStatus; //借出时玻片的存档状态(晾片/存档)
    private String archiveStatusDesc;

    private String idNumber;

    private String cashPledge;

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getCashPledge() {
        return cashPledge;
    }

    public void setCashPledge(String cashPledge) {
        this.cashPledge = cashPledge;
    }

    public String getDryingBox() {
        return dryingBox;
    }

    public void setDryingBox(String dryingBox) {
        this.dryingBox = dryingBox;
    }

    public Long getDryingCreateBy() {
        return dryingCreateBy;
    }

    public void setDryingCreateBy(Long dryingCreateBy) {
        this.dryingCreateBy = dryingCreateBy;
    }

    public String getDryingCreateByDesc() {
        return dryingCreateByDesc;
    }

    public void setDryingCreateByDesc(String dryingCreateByDesc) {
        this.dryingCreateByDesc = dryingCreateByDesc;
    }

    public Date getDryingCreateTime() {
        return dryingCreateTime;
    }

    public void setDryingCreateTime(Date dryingCreateTime) {
        this.dryingCreateTime = dryingCreateTime;
    }

    public Integer getArchiveStatus() {
        return archiveStatus;
    }

    public void setArchiveStatus(Integer archiveStatus) {
        this.archiveStatus = archiveStatus;
    }

    public String getArchiveStatusDesc() {
        return archiveStatusDesc;
    }

    public void setArchiveStatusDesc(String archiveStatusDesc) {
        this.archiveStatusDesc = archiveStatusDesc;
    }

    public Long getArchiveCreateBy() {
        return archiveCreateBy;
    }

    public void setArchiveCreateBy(Long archiveCreateBy) {
        this.archiveCreateBy = archiveCreateBy;
    }

    public String getArchiveCreateByDesc() {
        return archiveCreateByDesc;
    }

    public void setArchiveCreateByDesc(String archiveCreateByDesc) {
        this.archiveCreateByDesc = archiveCreateByDesc;
    }

    public Date getArchiveCreateTime() {
        return archiveCreateTime;
    }

    public void setArchiveCreateTime(Date archiveCreateTime) {
        this.archiveCreateTime = archiveCreateTime;
    }

    public String getStatusDesc() {
        return statusDesc;
    }

    public void setStatusDesc(String statusDesc) {
        this.statusDesc = statusDesc;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getApplyType() {
        return applyType;
    }

    public void setApplyType(Integer applyType) {
        this.applyType = applyType;
    }

    public Long getBlockArchiveId() {
        return blockArchiveId;
    }

    public void setBlockArchiveId(Long blockArchiveId) {
        this.blockArchiveId = blockArchiveId;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public Long getPathId() {
        return pathId;
    }

    public void setPathId(Long pathId) {
        this.pathId = pathId;
    }

    public String getBlockSubId() {
        return blockSubId;
    }

    public void setBlockSubId(String blockSubId) {
        this.blockSubId = blockSubId;
    }

    public Long getBlockId() {
        return blockId;
    }

    public void setBlockId(Long blockId) {
        this.blockId = blockId;
    }

    public String getSlideSubId() {
        return slideSubId;
    }

    public void setSlideSubId(String slideSubId) {
        this.slideSubId = slideSubId;
    }

    public Long getSlideId() {
        return slideId;
    }

    public void setSlideId(Long slideId) {
        this.slideId = slideId;
    }

    public String getIhcMarker() {
        return ihcMarker;
    }

    public void setIhcMarker(String ihcMarker) {
        this.ihcMarker = ihcMarker;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getArchiveBox() {
        return archiveBox;
    }

    public void setArchiveBox(String archiveBox) {
        this.archiveBox = archiveBox;
    }

    public Long getBorrowID() {
        return borrowID;
    }

    public void setBorrowID(Long borrowID) {
        this.borrowID = borrowID;
    }

    public String getBorrowName() {
        return borrowName;
    }

    public void setBorrowName(String borrowName) {
        this.borrowName = borrowName;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getBorrowPhone() {
        return borrowPhone;
    }

    public void setBorrowPhone(String borrowPhone) {
        this.borrowPhone = borrowPhone;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getPlanBack() {
        return planBack;
    }

    public void setPlanBack(Date planBack) {
        this.planBack = planBack;
    }

    public Date getRealBack() {
        return realBack;
    }

    public void setRealBack(Date realBack) {
        this.realBack = realBack;
    }

    public Integer getOverdue() {
        return overdue;
    }

    public void setOverdue(Integer overdue) {
        this.overdue = overdue;
    }
}
