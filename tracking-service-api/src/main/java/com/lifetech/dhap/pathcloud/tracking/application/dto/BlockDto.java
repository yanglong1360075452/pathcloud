package com.lifetech.dhap.pathcloud.tracking.application.dto;

import com.lifetech.dhap.pathcloud.user.application.dto.UserSimpleDto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by LuoMo on 2016-12-06.
 */
public class BlockDto implements Serializable{

    private Long id;

    private String serialNumber;

    private String number;

    private Long pathId;

    private String subId;

    private Integer biaoshi;

    private String bodyPart;

    private Integer count;

    private Integer unit;
    private String unitName;

    private Long basketNumber;

    private UserSimpleDto grossingUser;

    private Integer print;

    private Integer status;
    private String statusName;

    private Long createBy;

    private Long updateBy;

    private Date createTime;

    private Date updateTime;

    private String note;

    private boolean deepSection;

    private Integer specialDye;

    private Long parentId;

    private List<String> marker;

    private String slideMarker;

    private Long applyId;

    private Boolean embedPause;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Boolean getEmbedPause() {
        return embedPause;
    }

    public void setEmbedPause(Boolean embedPause) {
        this.embedPause = embedPause;
    }

    public Long getApplyId() {
        return applyId;
    }

    public void setApplyId(Long applyId) {
        this.applyId = applyId;
    }

    public String getSlideMarker() {
        return slideMarker;
    }

    public void setSlideMarker(String slideMarker) {
        this.slideMarker = slideMarker;
    }

    public UserSimpleDto getGrossingUser() {
        return grossingUser;
    }

    public void setGrossingUser(UserSimpleDto grossingUser) {
        this.grossingUser = grossingUser;
    }

    public boolean isDeepSection() {
        return deepSection;
    }

    public void setDeepSection(boolean deepSection) {
        this.deepSection = deepSection;
    }

    public Integer getSpecialDye() {
        return specialDye;
    }

    public void setSpecialDye(Integer specialDye) {
        this.specialDye = specialDye;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public List<String> getMarker() {
        return marker;
    }

    public void setMarker(List<String> marker) {
        this.marker = marker;
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

    public String getBodyPart() {
        return bodyPart;
    }

    public void setBodyPart(String bodyPart) {
        this.bodyPart = bodyPart;
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

    public Long getCreateBy() {
        return createBy;
    }

    public void setCreateBy(Long createBy) {
        this.createBy = createBy;
    }

    public Long getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(Long updateBy) {
        this.updateBy = updateBy;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Long getBasketNumber() {
        return basketNumber;
    }

    public void setBasketNumber(Long basketNumber) {
        this.basketNumber = basketNumber;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
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

    public Integer getPrint() {
        return print;
    }

    public void setPrint(Integer print) {
        this.print = print;
    }
}
