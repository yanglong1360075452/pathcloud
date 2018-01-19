package com.lifetech.dhap.pathcloud.tracking.application.dto;

/**
 * Created by HP on 2017/2/20.
 */
public class WaitTimeDto {

    private Integer operationStart;

    private Integer operationEnd;

    private Long blockId;

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

    public Long getBlockId() {
        return blockId;
    }

    public void setBlockId(Long blockId) {
        this.blockId = blockId;
    }
}
