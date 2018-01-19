package com.lifetech.dhap.pathcloud.wechat.api.vo;

import java.util.List;

/**
 * Created by LiuMei on 2017-04-01.
 */
public class IhcBlockVO {

    private Long id;

    private Long ihcId;

    private String serialNumber;

    private Long pathId;

    private String subId;

    private Long blockId;

    private Integer status;
    private String statusDesc;

    private Integer specialDye;
    private String specialDyeDesc;

    private List<String> ihcMarker;

    private String note;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIhcId() {
        return ihcId;
    }

    public void setIhcId(Long ihcId) {
        this.ihcId = ihcId;
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

    public Long getBlockId() {
        return blockId;
    }

    public void setBlockId(Long blockId) {
        this.blockId = blockId;
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

    public List<String> getIhcMarker() {
        return ihcMarker;
    }

    public void setIhcMarker(List<String> ihcMarker) {
        this.ihcMarker = ihcMarker;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
