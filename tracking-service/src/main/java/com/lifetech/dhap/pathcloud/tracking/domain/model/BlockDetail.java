package com.lifetech.dhap.pathcloud.tracking.domain.model;

import java.util.Date;

/**
 * @author eric.guo@ostengar.com
 * @version 1.0
 * @time 2016-12-08-10:48
 */
public class BlockDetail {

    private Long applicationId;

    private Long pathologyId;

    private Long blockId;

    private String pathologyNumber;//病理号

    private String name; //病人姓名

    private Integer departments;

    private String subId;

    private String slideId;

    private Long parentId;

    private String bodyPart;

    private Integer unit;

    private Integer count;

    private Integer biaoshi;

    private Long operatorId;
    private String operatorName;

    private Long secOperatorId;

    private Integer basketNumber;

    private Integer print;

    private String note;

    private Date operationTime;

    private Integer status;

    private Long applyId;

    private String number;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    public Long getApplyId() {
        return applyId;
    }

    public void setApplyId(Long applyId) {
        this.applyId = applyId;
    }

    public Long getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(Long applicationId) {
        this.applicationId = applicationId;
    }

    public Long getPathologyId() {
        return pathologyId;
    }

    public void setPathologyId(Long pathologyId) {
        this.pathologyId = pathologyId;
    }

    public Long getBlockId() {
        return blockId;
    }

    public void setBlockId(Long blockId) {
        this.blockId = blockId;
    }

    public String getPathologyNumber() {
        return pathologyNumber;
    }

    public void setPathologyNumber(String pathologyNumber) {
        this.pathologyNumber = pathologyNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getDepartments() {
        return departments;
    }

    public void setDepartments(Integer departments) {
        this.departments = departments;
    }

    public String getSubId() {
        return subId;
    }

    public void setSubId(String subId) {
        this.subId = subId;
    }

    public String getBodyPart() {
        return bodyPart;
    }

    public void setBodyPart(String bodyPart) {
        this.bodyPart = bodyPart;
    }

    public Integer getUnit() {
        return unit;
    }

    public void setUnit(Integer unit) {
        this.unit = unit;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getBiaoshi() {
        return biaoshi;
    }

    public void setBiaoshi(Integer biaoshi) {
        this.biaoshi = biaoshi;
    }

    public Long getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(Long operatorId) {
        this.operatorId = operatorId;
    }

    public Long getSecOperatorId() {
        return secOperatorId;
    }

    public void setSecOperatorId(Long secOperatorId) {
        this.secOperatorId = secOperatorId;
    }

    public Integer getBasketNumber() {
        return basketNumber;
    }

    public void setBasketNumber(Integer basketNumber) {
        this.basketNumber = basketNumber;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Date getOperationTime() {
        return operationTime;
    }

    public void setOperationTime(Date operationTime) {
        this.operationTime = operationTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getPrint() {
        return print;
    }

    public void setPrint(Integer print) {
        this.print = print;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getSlideId() {
        return slideId;
    }

    public void setSlideId(String slideId) {
        this.slideId = slideId;
    }
}
