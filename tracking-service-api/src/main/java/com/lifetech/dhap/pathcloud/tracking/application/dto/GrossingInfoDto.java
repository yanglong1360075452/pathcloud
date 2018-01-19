package com.lifetech.dhap.pathcloud.tracking.application.dto;

import java.util.Date;

/**
 * Created by HP on 2017/2/9.
 */
public class GrossingInfoDto {

    private Long id;

    private String serialNumber;

    private Long pathId;

    private String subId;

    private Integer biaoshi;

    private String biaoshiDesc;

    private String bodypart;

    private Integer count;

    private Integer unit;

    private String unitName;

    private String note;

    private String stop;

    private Long operatorId;

    private String operatorName;

    private Long secOperatorId;

    private String secOperatorName;

    private Date grossingTime;

    private Long applyId;

    public Long getApplyId() {
        return applyId;
    }

    public void setApplyId(Long applyId) {
        this.applyId = applyId;
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

    public Long getPathId() {
        return pathId;
    }

    public void setPathId(Long pathId) {
        this.pathId = pathId;
    }

    public String getSubId() {
        return subId;
    }

    public void setSubId(String subId) {
        this.subId = subId;
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

    public String getBodypart() {
        return bodypart;
    }

    public void setBodypart(String bodypart) {
        this.bodypart = bodypart;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getUnit() {
        return unit;
    }

    public void setUnit(Integer unit) {
        this.unit = unit;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Long getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(Long operatorId) {
        this.operatorId = operatorId;
    }

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    public Long getSecOperatorId() {
        return secOperatorId;
    }

    public void setSecOperatorId(Long secOperatorId) {
        this.secOperatorId = secOperatorId;
    }

    public String getSecOperatorName() {
        return secOperatorName;
    }

    public void setSecOperatorName(String secOperatorName) {
        this.secOperatorName = secOperatorName;
    }

    public Date getGrossingTime() {
        return grossingTime;
    }

    public void setGrossingTime(Date grossingTime) {
        this.grossingTime = grossingTime;
    }

    public String getStop() {
        return stop;
    }

    public void setStop(String stop) {
        this.stop = stop;
    }
}
