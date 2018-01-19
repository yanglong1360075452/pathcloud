package com.lifetech.dhap.pathcloud.tracking.api.vo;

import com.lifetech.dhap.pathcloud.user.api.vo.UserSimpleVO;

import java.util.Date;

/**
 * Created by LiuMei on 2017-01-19.
 */
public class DiagnoseHistoryVO {

    private UserSimpleVO operator;

    private Date operationTime;

    private String diagnoseResult;

    private Integer operation;
    private String operationDesc;

    public UserSimpleVO getOperator() {
        return operator;
    }

    public void setOperator(UserSimpleVO operator) {
        this.operator = operator;
    }

    public Date getOperationTime() {
        return operationTime;
    }

    public void setOperationTime(Date operationTime) {
        this.operationTime = operationTime;
    }

    public Integer getOperation() {
        return operation;
    }

    public void setOperation(Integer operation) {
        this.operation = operation;
    }

    public String getOperationDesc() {
        return operationDesc;
    }

    public void setOperationDesc(String operationDesc) {
        this.operationDesc = operationDesc;
    }

    public String getDiagnoseResult() {
        return diagnoseResult;
    }

    public void setDiagnoseResult(String diagnoseResult) {
        this.diagnoseResult = diagnoseResult;
    }
}
