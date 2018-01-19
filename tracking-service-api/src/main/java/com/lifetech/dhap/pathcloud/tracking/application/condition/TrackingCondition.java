package com.lifetech.dhap.pathcloud.tracking.application.condition;

import com.lifetech.dhap.pathcloud.common.base.PageCondition;

import java.util.Date;
import java.util.List;

/**
 * Created by LiuMei on 2017-01-10.
 */
public class TrackingCondition  extends PageCondition{

    private Long blockId;

    private Integer operation;

    private Integer nextOperation;

    private Long operatorId;

    private Date operationTime;

    private Long instrumentId;

    private List<Long> blockIdList;

    private Long secOperatorId;

    public Long getSecOperatorId() {
        return secOperatorId;
    }

    public void setSecOperatorId(Long secOperatorId) {
        this.secOperatorId = secOperatorId;
    }

    public List<Long> getBlockIdList() {
        return blockIdList;
    }

    public void setBlockIdList(List<Long> blockIdList) {
        this.blockIdList = blockIdList;
    }

    public Long getInstrumentId() {
        return instrumentId;
    }

    public void setInstrumentId(Long instrumentId) {
        this.instrumentId = instrumentId;
    }

    public Long getBlockId() {
        return blockId;
    }

    public void setBlockId(Long blockId) {
        this.blockId = blockId;
    }

    public Integer getOperation() {
        return operation;
    }

    public void setOperation(Integer operation) {
        this.operation = operation;
    }

    public Long getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(Long operatorId) {
        this.operatorId = operatorId;
    }

    public Date getOperationTime() {
        return operationTime;
    }

    public void setOperationTime(Date operationTime) {
        this.operationTime = operationTime;
    }

    public Integer getNextOperation() {
        return nextOperation;
    }

    public void setNextOperation(Integer nextOperation) {
        this.nextOperation = nextOperation;
    }
}
