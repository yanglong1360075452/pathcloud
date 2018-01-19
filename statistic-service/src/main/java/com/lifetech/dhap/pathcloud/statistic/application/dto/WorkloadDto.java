package com.lifetech.dhap.pathcloud.statistic.application.dto;

import com.lifetech.dhap.pathcloud.user.application.dto.UserSimpleDto;

/**
 * Created by LiuMei on 2017-02-16.
 */
public class WorkloadDto {

    private UserSimpleDto operator;

    private long pathCount;

    private long blockCount;

    private long slideCount;

    public UserSimpleDto getOperator() {
        return operator;
    }

    public void setOperator(UserSimpleDto operator) {
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
