package com.lifetech.dhap.pathcloud.tracking.api.vo;

import java.util.Date;

/**
 * Created by LiuMei on 2017-09-11.
 */
public class HintVO {

    private String pathNo;

    private String blockSubNo;

    private Integer status;
    private String statusDesc;

    private Long operator;
    private String operatorDesc;

    private Date operatorTime;

    public Long getOperator() {
        return operator;
    }

    public void setOperator(Long operator) {
        this.operator = operator;
    }

    public String getOperatorDesc() {
        return operatorDesc;
    }

    public void setOperatorDesc(String operatorDesc) {
        this.operatorDesc = operatorDesc;
    }

    public Date getOperatorTime() {
        return operatorTime;
    }

    public void setOperatorTime(Date operatorTime) {
        this.operatorTime = operatorTime;
    }

    public String getStatusDesc() {
        return statusDesc;
    }

    public void setStatusDesc(String statusDesc) {
        this.statusDesc = statusDesc;
    }

    public String getPathNo() {
        return pathNo;
    }

    public void setPathNo(String pathNo) {
        this.pathNo = pathNo;
    }

    public String getBlockSubNo() {
        return blockSubNo;
    }

    public void setBlockSubNo(String blockSubNo) {
        this.blockSubNo = blockSubNo;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
