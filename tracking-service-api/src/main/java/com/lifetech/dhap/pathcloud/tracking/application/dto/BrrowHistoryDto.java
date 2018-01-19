package com.lifetech.dhap.pathcloud.tracking.application.dto;

import java.util.Date;

/**
 * Created by HP on 2017/6/12.
 */
public class BrrowHistoryDto {

    private  Long blockArchiveId; //蜡块存档ID

    private Long borrowID;

    private String borrowName; //借阅人

    private String unit; //单位

    private String borrowPhone;

    private Date createTime; //借阅日期

    private Date planBack; //计划归还日期

    private Date realBack; //实际归还日期

    private Integer overdue; //逾期

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

    public Long getBlockArchiveId() {
        return blockArchiveId;
    }

    public void setBlockArchiveId(Long blockArchiveId) {
        this.blockArchiveId = blockArchiveId;
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
