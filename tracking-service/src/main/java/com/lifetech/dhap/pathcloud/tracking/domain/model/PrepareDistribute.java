package com.lifetech.dhap.pathcloud.tracking.domain.model;

import java.util.Date;

/**
 * @author eric.guo@ostengar.com
 * @version 1.0
 * @time 2017-05-22-14:43
 */
public class PrepareDistribute {

    private Long pathId;

    private String serialNumber;

    private Long slideTotal;

    private Long grossingOperator;
    private String grossingOperatorName;

    private Integer departments;

    private Long confirmOperator;
    private String confirmOperatorName;

    private Date confirmTime;

    private Integer status;

    public String getGrossingOperatorName() {
        return grossingOperatorName;
    }

    public void setGrossingOperatorName(String grossingOperatorName) {
        this.grossingOperatorName = grossingOperatorName;
    }

    public String getConfirmOperatorName() {
        return confirmOperatorName;
    }

    public void setConfirmOperatorName(String confirmOperatorName) {
        this.confirmOperatorName = confirmOperatorName;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getPathId() {
        return pathId;
    }

    public void setPathId(Long pathId) {
        this.pathId = pathId;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public Long getSlideTotal() {
        return slideTotal;
    }

    public void setSlideTotal(Long slideTotal) {
        this.slideTotal = slideTotal;
    }

    public Long getGrossingOperator() {
        return grossingOperator;
    }

    public void setGrossingOperator(Long grossingOperator) {
        this.grossingOperator = grossingOperator;
    }

    public Integer getDepartments() {
        return departments;
    }

    public void setDepartments(Integer departments) {
        this.departments = departments;
    }

    public Long getConfirmOperator() {
        return confirmOperator;
    }

    public void setConfirmOperator(Long confirmOperator) {
        this.confirmOperator = confirmOperator;
    }

    public Date getConfirmTime() {
        return confirmTime;
    }

    public void setConfirmTime(Date confirmTime) {
        this.confirmTime = confirmTime;
    }
}
