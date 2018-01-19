package com.lifetech.dhap.pathcloud.statistic.domain.model;

import java.io.Serializable;

/**
 * Created by HP on 2017/2/15.
 */
public class GroupInspectCategory implements Serializable {

    private Integer pathologyMonth;

    private Long pathologyNum;

    private Long blockNum;

    private Integer inspectCategory;

    public Integer getInspectCategory() {
        return inspectCategory;
    }

    public void setInspectCategory(Integer inspectCategory) {
        this.inspectCategory = inspectCategory;
    }

    public Long getPathologyNum() {
        return pathologyNum;
    }

    public void setPathologyNum(Long pathologyNum) {
        this.pathologyNum = pathologyNum;
    }

    public Long getBlockNum() {
        return blockNum;
    }

    public void setBlockNum(Long blockNum) {
        this.blockNum = blockNum;
    }

    public Integer getPathologyMonth() {
        return pathologyMonth;
    }

    public void setPathologyMonth(Integer pathologyMonth) {
        this.pathologyMonth = pathologyMonth;
    }
}
