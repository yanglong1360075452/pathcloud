package com.lifetech.dhap.pathcloud.tracking.application.dto;

import java.util.Date;

/**
 * @author eric.guo@ostengar.com
 * @version 1.0
 * @time 2017-05-22-14:05
 */
public class DistributeHistoryDto {

    private String serialNumber;

    private String blockSubId;

    private String slideSubId;

    private Long grossingOperator;

    private String grossingOperatorName;

    private Integer departments;

    private String departmentsName;

    private Long confirmOperator;

    private String confirmOperatorName;

    private Date confirmTime;

    private String receiver;

    private Long distributeOperator;

    private String distributeOperatorName;

    private Date distributeTime;

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

    public String getGrossingOperatorName() {
        return grossingOperatorName;
    }

    public void setGrossingOperatorName(String grossingOperatorName) {
        this.grossingOperatorName = grossingOperatorName;
    }

    public Integer getDepartments() {
        return departments;
    }

    public void setDepartments(Integer departments) {
        this.departments = departments;
    }

    public String getDepartmentsName() {
        return departmentsName;
    }

    public void setDepartmentsName(String departmentsName) {
        this.departmentsName = departmentsName;
    }

    public Long getConfirmOperator() {
        return confirmOperator;
    }

    public void setConfirmOperator(Long confirmOperator) {
        this.confirmOperator = confirmOperator;
    }

    public String getConfirmOperatorName() {
        return confirmOperatorName;
    }

    public void setConfirmOperatorName(String confirmOperatorName) {
        this.confirmOperatorName = confirmOperatorName;
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

    public String getDistributeOperatorName() {
        return distributeOperatorName;
    }

    public void setDistributeOperatorName(String distributeOperatorName) {
        this.distributeOperatorName = distributeOperatorName;
    }

    public Date getDistributeTime() {
        return distributeTime;
    }

    public void setDistributeTime(Date distributeTime) {
        this.distributeTime = distributeTime;
    }
}
