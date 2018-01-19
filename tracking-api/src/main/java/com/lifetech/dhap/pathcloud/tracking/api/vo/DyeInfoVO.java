package com.lifetech.dhap.pathcloud.tracking.api.vo;

import com.lifetech.dhap.pathcloud.user.api.vo.UserSimpleVO;

/**
 * Created by LiuMei on 2017-03-22.
 */
public class DyeInfoVO {

    private Long id;

    private Long parentId;

    private Long pathId;

    private String pathNo;

    private String serialNumber;

    private String subId;

    private String blockSerialNumber;

    private String blockSubId;

    private UserSimpleVO grossingUser;

    private String grossingBody;

    private Integer grossingCount;

    private Integer grossingUnit;
    private String grossingUnitDesc;

    private Integer biaoshi;
    private String biaoshiDesc;

    private Integer status;
    private String statusDesc;

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

    public UserSimpleVO getGrossingUser() {
        return grossingUser;
    }

    public void setGrossingUser(UserSimpleVO grossingUser) {
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
}
