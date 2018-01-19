package com.lifetech.dhap.pathcloud.tracking.api.vo;

import com.lifetech.dhap.pathcloud.user.api.vo.UserSimpleVO;

import java.util.Date;

/**
 * Created by LiuMei on 2017-01-06.
 */
public class SlideLostVO {

    private long id;

    private String serialNumber;

    private String subId;

    private String blockSerialNumber;

    private String blockSubId;

    private Integer status;
    private String statusDesc;

    private UserSimpleVO lastOperator;

    private Date lastOperateTime;

    private String pathNo;

    private Boolean specialApply;

    public Boolean getSpecialApply() {
        return specialApply;
    }

    public void setSpecialApply(Boolean specialApply) {
        this.specialApply = specialApply;
    }

    public String getPathNo() {
        return pathNo;
    }

    public void setPathNo(String pathNo) {
        this.pathNo = pathNo;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    public String getBlockSerialNumber() {
        return blockSerialNumber;
    }

    public void setBlockSerialNumber(String blockSerialNumber) {
        this.blockSerialNumber = blockSerialNumber;
    }

    public String getBlockSubId() {
        return blockSubId;
    }

    public void setBlockSubId(String blockSubId) {
        this.blockSubId = blockSubId;
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

    public UserSimpleVO getLastOperator() {
        return lastOperator;
    }

    public void setLastOperator(UserSimpleVO lastOperator) {
        this.lastOperator = lastOperator;
    }

    public Date getLastOperateTime() {
        return lastOperateTime;
    }

    public void setLastOperateTime(Date lastOperateTime) {
        this.lastOperateTime = lastOperateTime;
    }
}
