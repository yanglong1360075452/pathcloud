package com.lifetech.dhap.pathcloud.tracking.api.vo;

import java.io.Serializable;
import java.util.List;

/**
 * Created by LiuMei on 2016-12-09.
 */
public class BasketVO implements Serializable{
    private Integer basketNumber;

    private Long blockCount;

    private List<String> recorders;

    public Integer getBasketNumber() {
        return basketNumber;
    }

    public void setBasketNumber(Integer basketNumber) {
        this.basketNumber = basketNumber;
    }

    public Long getBlockCount() {
        return blockCount;
    }

    public void setBlockCount(Long blockCount) {
        this.blockCount = blockCount;
    }

    public List<String> getRecorders() {
        return recorders;
    }

    public void setRecorders(List<String> recorders) {
        this.recorders = recorders;
    }
}
