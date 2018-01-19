package com.lifetech.dhap.pathcloud.tracking.application.dto;

import com.lifetech.dhap.pathcloud.user.application.dto.UserSimpleDto;

import java.util.Date;

/**
 * Created by LiuMei on 2017-08-31.
 */
public class SlideLostDto {

    private Long id;

    private String serialNumber;

    private String subId;

    private String blockSerialNumber;

    private String blockSubId;

    private Integer status;
    private String statusDesc;

    private UserSimpleDto lastOperator;

    private Date lastOperateTime;

    private String pathNo;

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

    public UserSimpleDto getLastOperator() {
        return lastOperator;
    }

    public void setLastOperator(UserSimpleDto lastOperator) {
        this.lastOperator = lastOperator;
    }

    public Date getLastOperateTime() {
        return lastOperateTime;
    }

    public void setLastOperateTime(Date lastOperateTime) {
        this.lastOperateTime = lastOperateTime;
    }

    public String getPathNo() {
        return pathNo;
    }

    public void setPathNo(String pathNo) {
        this.pathNo = pathNo;
    }
}
