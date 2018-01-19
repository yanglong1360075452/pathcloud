package com.lifetech.dhap.pathcloud.tracking.api.vo;

import java.util.List;

/**
 * Created by LiuMei on 2017-06-01.
 */
public class DyeConfirmVO {

    private Long instrumentId;

    private List<Long> slideIds;

    private Boolean ignore;

    /**
     * 是否是当前用户
     * true-是 false-全科
     */
    private Boolean currentUser;

    public Boolean getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(Boolean currentUser) {
        this.currentUser = currentUser;
    }

    public void setInstrumentId(Long instrumentId) {
        this.instrumentId = instrumentId;
    }

    public Long getInstrumentId() {
        return instrumentId;
    }

    public List<Long> getSlideIds() {
        return slideIds;
    }

    public void setSlideIds(List<Long> slideIds) {
        this.slideIds = slideIds;
    }

    public Boolean getIgnore() {
        return ignore;
    }

    public void setIgnore(Boolean ignore) {
        this.ignore = ignore;
    }
}
