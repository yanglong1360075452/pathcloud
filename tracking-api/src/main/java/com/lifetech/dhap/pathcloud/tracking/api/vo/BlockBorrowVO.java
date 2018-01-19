package com.lifetech.dhap.pathcloud.tracking.api.vo;

import java.util.Date;
import java.util.List;

/**
 * Created by LiuMei on 2017-06-08.
 */
public class BlockBorrowVO {

    private List<Long> archiveIds;

    private String borrowName;

    private String borrowPhone;

    private Date planBack;

    private Integer borrowType;

    private String tutor;

    private String unit;

    private String note;

    private Integer departments;

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

    public List<Long> getArchiveIds() {
        return archiveIds;
    }

    public void setArchiveIds(List<Long> archiveIds) {
        this.archiveIds = archiveIds;
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

    public Integer getBorrowType() {
        return borrowType;
    }

    public void setBorrowType(Integer borrowType) {
        this.borrowType = borrowType;
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
}
