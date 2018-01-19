package com.lifetech.dhap.pathcloud.application.application.dto;

import java.util.Date;

/**
 * Created by HP on 2017/4/1.
 */
public class IhcApplicationQueryDto {

    private Long id;

    private String serialNumber;

    private String subId;

    private Integer dyeCategory;

    private String  ihcMarker;

    private String Note;

    private String applyUser;

    private String phone;

    private Date applyTime;

    private Integer blockStatus;

    private Integer status;

    private String archiveIndex; //存放位置

    private Date updateTime;

    private Long updateBy;

    private String number;

    private String result;

    public Long getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(Long updateBy) {
        this.updateBy = updateBy;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getArchiveIndex() {
        return archiveIndex;
    }

    public void setArchiveIndex(String archiveIndex) {
        this.archiveIndex = archiveIndex;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
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

    public String getSubId() {
        return subId;
    }

    public void setSubId(String subId) {
        this.subId = subId;
    }

    public Integer getDyeCategory() {
        return dyeCategory;
    }

    public void setDyeCategory(Integer dyeCategory) {
        this.dyeCategory = dyeCategory;
    }


    public String getIhcMarker() {
        return ihcMarker;
    }

    public void setIhcMarker(String ihcMarker) {
        this.ihcMarker = ihcMarker;
    }

    public String getNote() {
        return Note;
    }

    public void setNote(String note) {
        Note = note;
    }

    public String getApplyUser() {
        return applyUser;
    }

    public void setApplyUser(String applyUser) {
        this.applyUser = applyUser;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(Date applyTime) {
        this.applyTime = applyTime;
    }

    public Integer getBlockStatus() {
        return blockStatus;
    }

    public void setBlockStatus(Integer blockStatus) {
        this.blockStatus = blockStatus;
    }
}
