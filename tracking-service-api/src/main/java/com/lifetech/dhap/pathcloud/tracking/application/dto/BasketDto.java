package com.lifetech.dhap.pathcloud.tracking.application.dto;

/**
 * Created by LiuMei on 2016-12-09.
 */
public class BasketDto {

    private Integer basketNumber;

    private Long blockCount;

    private String recorder;

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

    public String getRecorder() {
        return recorder;
    }

    public void setRecorder(String recorder) {
        this.recorder = recorder;
    }
}
