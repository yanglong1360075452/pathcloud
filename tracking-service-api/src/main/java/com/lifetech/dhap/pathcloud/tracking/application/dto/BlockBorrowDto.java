package com.lifetech.dhap.pathcloud.tracking.application.dto;

import java.util.Date;

/**
 * Created by LiuMei on 2017-06-08.
 */
public class BlockBorrowDto {

    private Long id;

    private Long archiveId;

    private String borrowName;

    private String borrowPhone;

    private String idNumber;

    private String cashPledge;

    private Date planBack;

    private Date realBack;

    private Integer borrowType;
    private String borrowTypeDesc;

    private String tutor;

    private String unit;

    private String note;

    private Integer departments;
    private String departmentDesc;

    private Integer status;
    private String statusDesc;

    private Integer archiveStatus;
    private String archiveStatusDesc;

    private Long createBy;

    private Long updateBy;

    private Date createTime;

    private Date updateTime;

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


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getArchiveId() {
        return archiveId;
    }

    public void setArchiveId(Long archiveId) {
        this.archiveId = archiveId;
    }

    public String getBorrowName() {
        return borrowName;
    }

    public void setBorrowName(String borrowName) {
        this.borrowName = borrowName;
    }

    public String getBorrowPhone() {
        return borrowPhone;
    }

    public void setBorrowPhone(String borrowPhone) {
        this.borrowPhone = borrowPhone;
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

    public Integer getBorrowType() {
        return borrowType;
    }

    public void setBorrowType(Integer borrowType) {
        this.borrowType = borrowType;
    }

    public String getBorrowTypeDesc() {
        return borrowTypeDesc;
    }

    public void setBorrowTypeDesc(String borrowTypeDesc) {
        this.borrowTypeDesc = borrowTypeDesc;
    }

    public String getTutor() {
        return tutor;
    }

    public void setTutor(String tutor) {
        this.tutor = tutor;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Integer getDepartments() {
        return departments;
    }

    public void setDepartments(Integer departments) {
        this.departments = departments;
    }

    public String getDepartmentDesc() {
        return departmentDesc;
    }

    public void setDepartmentDesc(String departmentDesc) {
        this.departmentDesc = departmentDesc;
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
}
