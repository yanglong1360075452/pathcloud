package com.lifetech.dhap.pathcloud.dehydrate.api.vo;

import java.io.Serializable;
import java.util.List;

/**
 * Created by LiuMei on 2016-12-13.
 */
public class DehydrateVO implements Serializable {

    //脱水篮编号列表
    private List<Integer> baskets;

    private Long instrumentId;

    private List<Long> instrumentIds;//结束脱水脱水机列表

    private Boolean ignore;//标识是否忽略同一病理号蜡块缺失

    public Boolean getIgnore() {
        return ignore;
    }

    public void setIgnore(Boolean ignore) {
        this.ignore = ignore;
    }

    public List<Integer> getBaskets() {
        return baskets;
    }

    public void setBaskets(List<Integer> baskets) {
        this.baskets = baskets;
    }

    public Long getInstrumentId() {
        return instrumentId;
    }

    public void setInstrumentId(Long instrumentId) {
        this.instrumentId = instrumentId;
    }

    public List<Long> getInstrumentIds() {
        return instrumentIds;
    }

    public void setInstrumentIds(List<Long> instrumentIds) {
        this.instrumentIds = instrumentIds;
    }
}
