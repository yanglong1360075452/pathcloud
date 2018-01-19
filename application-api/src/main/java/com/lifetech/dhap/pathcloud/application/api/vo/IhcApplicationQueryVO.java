package com.lifetech.dhap.pathcloud.application.api.vo;

import com.lifetech.dhap.pathcloud.user.api.vo.UserSimpleVO;

import java.util.Date;
import java.util.List;

/**
 * Created by HP on 2017/4/6.
 */
public class IhcApplicationQueryVO {

    private Long id;

    private String number;

    private String serialNumber;

    private String subId;

    private Integer dyeCategory;
    private String dyeCategoryName;

    private List<String> ihcMarkers;

    private String Note;

    private String applyUser;

    private String phone;

    private Date applyTime;

    private Integer blockStatus;
    private String blockStatusDesc;

    private Integer status;
    private String statusName;

    private String archiveIndex; //存放位置

    private Date updateTime;

    private UserSimpleVO updateBy;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public UserSimpleVO getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(UserSimpleVO updateBy) {
        this.updateBy = updateBy;
    }

    public Integer getBlockStatus() {
        return blockStatus;
    }

    public void setBlockStatus(Integer blockStatus) {
        this.blockStatus = blockStatus;
    }

    public String getBlockStatusDesc() {
        return blockStatusDesc;
    }

    public void setBlockStatusDesc(String blockStatusDesc) {
        this.blockStatusDesc = blockStatusDesc;
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

    public String getDyeCategoryName() {
        return dyeCategoryName;
    }

    public void setDyeCategoryName(String dyeCategoryName) {
        this.dyeCategoryName = dyeCategoryName;
    }

    public List<String> getIhcMarkers() {
        return ihcMarkers;
    }

    public void setIhcMarkers(List<String> ihcMarkers) {
        this.ihcMarkers = ihcMarkers;
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

    public Integer getDyeCategory() {
        return dyeCategory;
    }

    public void setDyeCategory(Integer dyeCategory) {
        this.dyeCategory = dyeCategory;
    }
}
