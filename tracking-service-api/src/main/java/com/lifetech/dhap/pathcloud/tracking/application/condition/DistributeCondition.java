package com.lifetech.dhap.pathcloud.tracking.application.condition;

import com.lifetech.dhap.pathcloud.common.base.PageCondition;

import java.util.Date;

/**
 * @author eric.guo@ostengar.com
 * @version 1.0
 * @time 2017-05-22-10:53
 */
public class DistributeCondition extends PageCondition {

    private Integer departments;

    private Long operator;

    private String filter;

    private Date timeStart;

    private Date timeEnd;

    private String pathNoStart;

    private String pathNoEnd;

    public Integer getDepartments() {
        return departments;
    }

    public void setDepartments(Integer departments) {
        this.departments = departments;
    }

    public Long getOperator() {
        return operator;
    }

    public void setOperator(Long operator) {
        this.operator = operator;
    }

    public String getFilter() {
        return filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }

    public Date getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(Date timeStart) {
        this.timeStart = timeStart;
    }

    public Date getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(Date timeEnd) {
        this.timeEnd = timeEnd;
    }

    public String getPathNoStart() {
        return pathNoStart;
    }

    public void setPathNoStart(String pathNoStart) {
        this.pathNoStart = pathNoStart;
    }

    public String getPathNoEnd() {
        return pathNoEnd;
    }

    public void setPathNoEnd(String pathNoEnd) {
        this.pathNoEnd = pathNoEnd;
    }
}
