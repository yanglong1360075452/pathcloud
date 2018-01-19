package com.lifetech.dhap.pathcloud.tracking.domain.model;

/**
 * Created by LiuMei on 2016-12-09.
 */
public class Basket {

    private Integer basketNumber;

    private Long blockCount;

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
}
