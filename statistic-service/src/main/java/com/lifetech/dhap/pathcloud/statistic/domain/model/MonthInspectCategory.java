package com.lifetech.dhap.pathcloud.statistic.domain.model;

import java.io.Serializable;

/**
 * @author eric.guo@ostengar.com
 * @version 1.0
 * @time 2017-02-13-09:35
 */
public class MonthInspectCategory implements Serializable {

    private Integer pathologyMonth;

    private Long num;

    private Integer inspectCategory;

    public Integer getPathologyMonth() {
        return pathologyMonth;
    }

    public void setPathologyMonth(Integer pathologyMonth) {
        this.pathologyMonth = pathologyMonth;
    }

    public Integer getInspectCategory() {
        return inspectCategory;
    }

    public void setInspectCategory(Integer inspectCategory) {
        this.inspectCategory = inspectCategory;
    }

    public Long getNum() {
        return num;
    }

    public void setNum(Long num) {
        this.num = num;
    }
}
