package com.lifetech.dhap.pathcloud.tracking.application.dto;

import java.util.Date;

/**
 * @author eric.guo@ostengar.com
 * @version 1.0
 * @time 2017-01-03-14:17
 */
public class ConfirmBlockDto {

    private Date confirmTime;

    private Long total;

    private Long operator;

    private String operatorName;

    private String receiver;

    public Date getConfirmTime() {
        return confirmTime;
    }

    public void setConfirmTime(Date confirmTime) {
        this.confirmTime = confirmTime;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public Long getOperator() {
        return operator;
    }

    public void setOperator(Long operator) {
        this.operator = operator;
    }

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }
}
