package com.lifetech.dhap.pathcloud.tracking.application.condition;

import java.util.Date;

/**
 * Created by LiuMei on 2017-03-06.
 */
public class HadOperatedCondition {

    private Long pathId;

    private Integer status;

    private Date startTime;

    private Date endTime;

    public Long getPathId() {
        return pathId;
    }

    public void setPathId(Long pathId) {
        this.pathId = pathId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
}
