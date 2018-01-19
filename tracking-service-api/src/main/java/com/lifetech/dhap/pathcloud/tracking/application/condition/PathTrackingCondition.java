package com.lifetech.dhap.pathcloud.tracking.application.condition;

import com.lifetech.dhap.pathcloud.common.base.PageCondition;

import java.util.List;

/**
 * Created by LiuMei on 2017-01-19.
 */
public class PathTrackingCondition extends PageCondition {

    private long pathId;

    private List<Integer> operations;

    public long getPathId() {
        return pathId;
    }

    public void setPathId(long pathId) {
        this.pathId = pathId;
    }

    public List<Integer> getOperations() {
        return operations;
    }

    public void setOperations(List<Integer> operations) {
        this.operations = operations;
    }
}
