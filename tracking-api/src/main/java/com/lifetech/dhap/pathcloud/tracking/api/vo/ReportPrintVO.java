package com.lifetech.dhap.pathcloud.tracking.api.vo;

import java.util.List;

/**
 * Created by LiuMei on 2017-09-30.
 */
public class ReportPrintVO {

    private List<Long> pathIds;

    private List<Long> specialApplyIds;

    public List<Long> getSpecialApplyIds() {
        return specialApplyIds;
    }

    public void setSpecialApplyIds(List<Long> specialApplyIds) {
        this.specialApplyIds = specialApplyIds;
    }

    public List<Long> getPathIds() {
        return pathIds;
    }

    public void setPathIds(List<Long> pathIds) {
        this.pathIds = pathIds;
    }

}
