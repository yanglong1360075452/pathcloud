package com.lifetech.dhap.pathcloud.tracking.application.condition;

import com.lifetech.dhap.pathcloud.common.base.PageCondition;

import java.util.Date;

/**
 * @author eric.guo@ostengar.com
 * @version 1.0
 * @time 2017-06-22-10:08
 */
public class MicroscopeTrackingCon extends PageCondition {

    private Date startTime;

    private Date endTime;

    private Integer microscopeId;

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

    public Integer getMicroscopeId() {
        return microscopeId;
    }

    public void setMicroscopeId(Integer microscopeId) {
        this.microscopeId = microscopeId;
    }
}
