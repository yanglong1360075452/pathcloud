package com.lifetech.dhap.pathcloud.tracking.application.dto;

import com.lifetech.dhap.pathcloud.user.application.dto.UserSimpleDto;

import java.util.Date;

/**
 * Created by LiuMei on 2017-01-04.
 */
public class SlideDto {

    private Long id;

    private Long parentId;

    private Long pathId;

    private String patientName;

    private String pathNo;

    private String serialNumber;

    private String subId;

    private String blockSerialNumber;

    private String blockSubId;

    private UserSimpleDto confirmUser;

    private String receiver;

    private Date receiveTime;

    private Integer print;

    private UserSimpleDto grossingUser;

    private String grossingBody;

    private Integer grossingCount;

    private Integer grossingUnit;
    private String grossingUnitDesc;

    private Integer biaoshi;
    private String biaoshiDesc;

    private Integer status;
    private String statusDesc;

    private Integer specialDye;
    private String specialDyeDesc;

    private String marker;

    private Integer applyType;
    private String applyTypeDesc;

    private Long applyId;

    private Long updateBy;

    private Date updateTime;

    private String note;

    private String number;

    private String sectionOperator;

    public String getSectionOperator() {
        return sectionOperator;
    }

    public void setSectionOperator(String sectionOperator) {
        this.sectionOperator = sectionOperator;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Integer getPrint() {
        return print;
    }

    public void setPrint(Integer print) {
        this.print = print;
    }

    public Long getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(Long updateBy) {
        this.updateBy = updateBy;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Long getApplyId() {
        return applyId;
    }

    public void setApplyId(Long applyId) {
        this.applyId = applyId;
    }

    public Integer getApplyType() {
        return applyType;
    }

    public void setApplyType(Integer applyType) {
        this.applyType = applyType;
    }

    public String getApplyTypeDesc() {
        return applyTypeDesc;
    }

    public void setApplyTypeDesc(String applyTypeDesc) {
        this.applyTypeDesc = applyTypeDesc;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Long getPathId() {
        return pathId;
    }

    public void setPathId(Long pathId) {
        this.pathId = pathId;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getPathNo() {
        return pathNo;
    }

    public void setPathNo(String pathNo) {
        this.pathNo = pathNo;
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

    public UserSimpleDto getConfirmUser() {
        return confirmUser;
    }

    public void setConfirmUser(UserSimpleDto confirmUser) {
        this.confirmUser = confirmUser;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public Date getReceiveTime() {
        return receiveTime;
    }

    public void setReceiveTime(Date receiveTime) {
        this.receiveTime = receiveTime;
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

    public String getMarker() {
        return marker;
    }

    public void setMarker(String marker) {
        this.marker = marker;
    }

    public UserSimpleDto getGrossingUser() {
        return grossingUser;
    }

    public void setGrossingUser(UserSimpleDto grossingUser) {
        this.grossingUser = grossingUser;
    }

    public String getGrossingBody() {
        return grossingBody;
    }

    public void setGrossingBody(String grossingBody) {
        this.grossingBody = grossingBody;
    }

    public Integer getGrossingCount() {
        return grossingCount;
    }

    public void setGrossingCount(Integer grossingCount) {
        this.grossingCount = grossingCount;
    }

    public Integer getGrossingUnit() {
        return grossingUnit;
    }

    public void setGrossingUnit(Integer grossingUnit) {
        this.grossingUnit = grossingUnit;
    }

    public String getGrossingUnitDesc() {
        return grossingUnitDesc;
    }

    public void setGrossingUnitDesc(String grossingUnitDesc) {
        this.grossingUnitDesc = grossingUnitDesc;
    }
}
