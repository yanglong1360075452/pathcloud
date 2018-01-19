package com.lifetech.dhap.pathcloud.tracking.application.condition;

import com.lifetech.dhap.pathcloud.common.base.PageCondition;

import java.util.Date;
import java.util.List;

/**
 * @author eric.guo@ostengar.com
 * @version 1.0
 * @time 2016-12-08-09:54
 */
public class GrossingCon extends PageCondition {

    private Date startTime;

    private Date endTime;

    private Integer blockStatus;

    private Integer departments;//送检科室

    private Integer operation;//操作

    private Long operator;//取材医生

    private Long secOperator;//取材记录员

    private String filter;//病人姓名或病理号

    private Long pathologyId;

    private String pathNoStart;

    private String pathNoEnd;

    private List<Integer> basketNumbers;

    private Boolean usingFrozen;

    public Boolean getUsingFrozen() {
        return usingFrozen;
    }

    public void setUsingFrozen(Boolean usingFrozen) {
        this.usingFrozen = usingFrozen;
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

    public Integer getBlockStatus() {
        return blockStatus;
    }

    public void setBlockStatus(Integer blockStatus) {
        this.blockStatus = blockStatus;
    }

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

    public Long getSecOperator() {
        return secOperator;
    }

    public void setSecOperator(Long secOperator) {
        this.secOperator = secOperator;
    }

    public String getFilter() {
        return filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }

    public Long getPathologyId() {
        return pathologyId;
    }

    public void setPathologyId(Long pathologyId) {
        this.pathologyId = pathologyId;
    }

    public Integer getOperation() {
        return operation;
    }

    public void setOperation(Integer operation) {
        this.operation = operation;
    }

    public List<Integer> getBasketNumbers() {
        return basketNumbers;
    }

    public void setBasketNumbers(List<Integer> basketNumbers) {
        this.basketNumbers = basketNumbers;
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
