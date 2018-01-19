package com.lifetech.dhap.pathcloud.statistic.domain.model;

/**
 * Created by LiuMei on 2017-02-17.
 */
public class QualityRank {

    private long operator;

    private int operation;

    private float average;

    public long getOperator() {
        return operator;
    }

    public void setOperator(long operator) {
        this.operator = operator;
    }

    public int getOperation() {
        return operation;
    }

    public void setOperation(int operation) {
        this.operation = operation;
    }

    public float getAverage() {
        return average;
    }

    public void setAverage(float average) {
        this.average = average;
    }
}
