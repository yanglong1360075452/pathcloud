package com.lifetech.dhap.pathcloud.tracking.domain.model;

import java.io.Serializable;
import java.util.Date;

/**
 * @author eric.guo@ostengar.com
 * @version 1.0
 * @time 2017-05-22-13:57
 */
public class DistributeHistory implements Serializable {

    private String serialNumber;

    private String blockSubId;

    private String slideSubId;

    private Long grossingOperator;
    private String grossingOperatorName;

    private Integer departments;

    private Long confirmOperator;
    private String confirmOperatorName;

    private Date confirmTime;

    private String receiver;

    private Long distributeOperator;
    private String distributeOperatorName;

    private Date distributeTime;

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

    public String getDistributeOperatorName() {
        return distributeOperatorName;
    }

    public void setDistributeOperatorName(String distributeOperatorName) {
        this.distributeOperatorName = distributeOperatorName;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getBlockSubId() {
        return blockSubId;
    }

    public void setBlockSubId(String blockSubId) {
        this.blockSubId = blockSubId;
    }

    public String getSlideSubId() {
        return slideSubId;
    }

    public void setSlideSubId(String slideSubId) {
        this.slideSubId = slideSubId;
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

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public Long getDistributeOperator() {
        return distributeOperator;
    }

    public void setDistributeOperator(Long distributeOperator) {
        this.distributeOperator = distributeOperator;
    }

    public Date getDistributeTime() {
        return distributeTime;
    }

    public void setDistributeTime(Date distributeTime) {
        this.distributeTime = distributeTime;
    }
}
