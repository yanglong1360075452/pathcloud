package com.lifetech.dhap.pathcloud.statistic.application.condition;

/**
 * Created by LiuMei on 2017-02-17.
 */
public class AvgWaitTimeCondition extends StatisticsCondition {

    private Integer operationStart;

    private Integer operationEnd;

    public Integer getOperationStart() {
        return operationStart;
    }

    public void setOperationStart(Integer operationStart) {
        this.operationStart = operationStart;
    }

    public Integer getOperationEnd() {
        return operationEnd;
    }

    public void setOperationEnd(Integer operationEnd) {
        this.operationEnd = operationEnd;
    }
}
