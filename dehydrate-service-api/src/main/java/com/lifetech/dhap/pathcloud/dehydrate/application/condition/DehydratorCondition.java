package com.lifetech.dhap.pathcloud.dehydrate.application.condition;

import com.lifetech.dhap.pathcloud.common.base.PageCondition;

/**
 * Created by LiuMei on 2016-12-15.
 */
public class DehydratorCondition extends PageCondition{

    private Boolean inUse;

    private Boolean disabled;

    private Integer status;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Boolean getInUse() {
        return inUse;
    }

    public void setInUse(Boolean inUse) {
        this.inUse = inUse;
    }

    public Boolean getDisabled() {
        return disabled;
    }

    public void setDisabled(Boolean disabled) {
        this.disabled = disabled;
    }
}
