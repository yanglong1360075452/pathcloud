package com.lifetech.dhap.pathcloud.dehydrate.application.condition;

import com.lifetech.dhap.pathcloud.common.base.PageCondition;

/**
 * Created by LiuMei on 2017-03-08.
 */
public class GetBlockCondition extends PageCondition {

    private long instrumentId;

    public long getInstrumentId() {
        return instrumentId;
    }

    public void setInstrumentId(long instrumentId) {
        this.instrumentId = instrumentId;
    }
}
