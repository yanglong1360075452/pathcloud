package com.lifetech.dhap.pathcloud.dehydrate.application.condition;

import com.lifetech.dhap.pathcloud.common.base.PageCondition;

/**
 * Created by LiuMei on 2017-08-31.
 */
public class InstrumentCondition extends PageCondition {

    private Integer status;

    private Integer type;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
