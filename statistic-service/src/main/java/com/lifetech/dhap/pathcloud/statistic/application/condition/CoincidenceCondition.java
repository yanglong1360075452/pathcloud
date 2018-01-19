package com.lifetech.dhap.pathcloud.statistic.application.condition;

import java.util.Date;

/**
 * @author LiuMei
 * @date 2017-11-09.
 */
public class CoincidenceCondition {

    private Date startTime;

    private Date endTime;

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
