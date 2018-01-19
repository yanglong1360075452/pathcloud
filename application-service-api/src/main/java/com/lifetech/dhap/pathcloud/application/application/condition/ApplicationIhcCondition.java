package com.lifetech.dhap.pathcloud.application.application.condition;

import com.lifetech.dhap.pathcloud.common.base.PageCondition;

import java.util.Date;

/**
 * Created by HP on 2017/4/1.
 */
public class ApplicationIhcCondition extends PageCondition {

    private Long applicationIhcId;

    private String filter;

    private Date createTimeStart;

    private Date createTimeEnd;

    private Integer dyeCategory;

    private Integer status;

    private Integer blockStatus;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getBlockStatus() {
        return blockStatus;
    }

    public void setBlockStatus(Integer blockStatus) {
        this.blockStatus = blockStatus;
    }

    public Long getApplicationIhcId() {
        return applicationIhcId;
    }

    public void setApplicationIhcId(Long applicationIhcId) {
        this.applicationIhcId = applicationIhcId;
    }

    public String getFilter() {
        return filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }

    public Date getCreateTimeStart() {
        return createTimeStart;
    }

    public void setCreateTimeStart(Date createTimeStart) {
        this.createTimeStart = createTimeStart;
    }

    public Date getCreateTimeEnd() {
        return createTimeEnd;
    }

    public void setCreateTimeEnd(Date createTimeEnd) {
        this.createTimeEnd = createTimeEnd;
    }

    public Integer getDyeCategory() {
        return dyeCategory;
    }

    public void setDyeCategory(Integer dyeCategory) {
        this.dyeCategory = dyeCategory;
    }
}
