package com.lifetech.dhap.pathcloud.statistic.application.dto;

import com.lifetech.dhap.pathcloud.user.application.dto.UserSimpleDto;

/**
 * Created by LiuMei on 2017-02-17.
 */
public class QualityRankDto {

    private UserSimpleDto operator;

    private int operation;
    private String operationDesc;

    private float average;

    public UserSimpleDto getOperator() {
        return operator;
    }

    public void setOperator(UserSimpleDto operator) {
        this.operator = operator;
    }

    public int getOperation() {
        return operation;
    }

    public void setOperation(int operation) {
        this.operation = operation;
    }

    public String getOperationDesc() {
        return operationDesc;
    }

    public void setOperationDesc(String operationDesc) {
        this.operationDesc = operationDesc;
    }

    public float getAverage() {
        return average;
    }

    public void setAverage(float average) {
        this.average = average;
    }
}
