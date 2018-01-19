package com.lifetech.dhap.pathcloud.tracking.api.vo;

import java.io.Serializable;
import java.util.List;

/**
 * Created by HP on 2017/1/4.
 */
public class ProductionSaveVO implements Serializable{

    //病理ID
    private Long pathId;

    //玻片ID
    private List<Long> slideIds;

    public Long getPathId() {
        return pathId;
    }

    public void setPathId(Long pathId) {
        this.pathId = pathId;
    }

    public List<Long> getSlideIds() {
        return slideIds;
    }

    public void setSlideIds(List<Long> slideIds) {
        this.slideIds = slideIds;
    }
}
