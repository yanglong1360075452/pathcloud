package com.lifetech.dhap.pathcloud.application.application.condition;

import com.lifetech.dhap.pathcloud.common.base.PageCondition;

/**
 * Created by LiuMei on 2017-04-01.
 */
public class IhcBlockCondition extends PageCondition{

    private Long pathId;

    private Long blockId;

    private Integer specialDye;

    private Integer status;

    private Long ihcId;

    public Long getIhcId() {
        return ihcId;
    }

    public void setIhcId(Long ihcId) {
        this.ihcId = ihcId;
    }

    public Long getPathId() {
        return pathId;
    }

    public void setPathId(Long pathId) {
        this.pathId = pathId;
    }

    public Long getBlockId() {
        return blockId;
    }

    public void setBlockId(Long blockId) {
        this.blockId = blockId;
    }

    public Integer getStatus() {
        return status;
    }

    public Integer getSpecialDye() {
        return specialDye;
    }

    public void setSpecialDye(Integer specialDye) {
        this.specialDye = specialDye;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
