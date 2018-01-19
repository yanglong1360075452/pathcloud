package com.lifetech.dnap.pathcloud.reagent.application.condition;

import com.lifetech.dhap.pathcloud.common.base.PageCondition;

import java.util.Date;

/**
 * Created by HP on 2017/9/27.
 */
public class ReagentRecordCondition extends PageCondition {

    private String filter;

    private Integer category;

    private Date startTime;

    private Date endTime;

    public String getFilter() {
        return filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }

    public Integer getCategory() {
        return category;
    }

    public void setCategory(Integer category) {
        this.category = category;
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
