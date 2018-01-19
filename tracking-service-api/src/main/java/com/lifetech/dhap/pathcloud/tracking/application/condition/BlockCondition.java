package com.lifetech.dhap.pathcloud.tracking.application.condition;

import java.util.List;

/**
 * Created by LiuMei on 2017-05-22.
 */
public class BlockCondition {

    private Long pathId;

    private Integer status;

    private List<Integer> statusList;

    private Integer againstBiaoshi;

    public List<Integer> getStatusList() {
        return statusList;
    }

    public void setStatusList(List<Integer> statusList) {
        this.statusList = statusList;
    }

    public Long getPathId() {
        return pathId;
    }

    public void setPathId(Long pathId) {
        this.pathId = pathId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getAgainstBiaoshi() {
        return againstBiaoshi;
    }

    public void setAgainstBiaoshi(Integer againstBiaoshi) {
        this.againstBiaoshi = againstBiaoshi;
    }
}
