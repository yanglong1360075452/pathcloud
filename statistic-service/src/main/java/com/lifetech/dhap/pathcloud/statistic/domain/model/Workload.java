package com.lifetech.dhap.pathcloud.statistic.domain.model;

/**
 * Created by LiuMei on 2017-02-16.
 */
public class Workload {

    private long operator;

    private long pathCount;

    private long blockCount;

    private long slideCount;

    public long getOperator() {
        return operator;
    }

    public void setOperator(long operator) {
        this.operator = operator;
    }

    public long getPathCount() {
        return pathCount;
    }

    public void setPathCount(long pathCount) {
        this.pathCount = pathCount;
    }

    public long getBlockCount() {
        return blockCount;
    }

    public void setBlockCount(long blockCount) {
        this.blockCount = blockCount;
    }

    public long getSlideCount() {
        return slideCount;
    }

    public void setSlideCount(long slideCount) {
        this.slideCount = slideCount;
    }
}
