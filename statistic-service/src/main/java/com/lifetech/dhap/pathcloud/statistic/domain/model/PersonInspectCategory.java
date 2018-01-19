package com.lifetech.dhap.pathcloud.statistic.domain.model;

import java.io.Serializable;

/**
 * Created by HP on 2017/2/13.
 */
public class PersonInspectCategory implements Serializable{

    private Long operatorId;

    private Integer operation;

    private Long pathologyTotal;

    private Long num;

    private Integer inspectCategory;

    private String InspectCategoryName;

    public Long getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(Long operatorId) {
        this.operatorId = operatorId;
    }

    public Long getNum() {
        return num;
    }

    public void setNum(Long num) {
        this.num = num;
    }

    public Integer getInspectCategory() {
        return inspectCategory;
    }

    public void setInspectCategory(Integer inspectCategory) {
        this.inspectCategory = inspectCategory;
    }

    public String getInspectCategoryName() {
        return InspectCategoryName;
    }

    public void setInspectCategoryName(String inspectCategoryName) {
        InspectCategoryName = inspectCategoryName;
    }

    public Integer getOperation() {
        return operation;
    }

    public void setOperation(Integer operation) {
        this.operation = operation;
    }

    public Long getPathologyTotal() {
        return pathologyTotal;
    }

    public void setPathologyTotal(Long pathologyTotal) {
        this.pathologyTotal = pathologyTotal;
    }
}
