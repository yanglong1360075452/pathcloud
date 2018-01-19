package com.lifetech.dhap.pathcloud.tracking.application.condition;

import java.util.Date;

/**
 * Created by LiuMei on 2017-02-15.
 */
public class BlockScoreCondition {

    private Date startTime;

    private Date endTime;

    private Float average;

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

    public Float getAverage() {
        return average;
    }

    public void setAverage(Float average) {
        this.average = average;
    }
}
