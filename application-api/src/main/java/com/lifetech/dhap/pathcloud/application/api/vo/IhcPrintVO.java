package com.lifetech.dhap.pathcloud.application.api.vo;

/**
 * Created by LiuMei on 2017-10-16.
 */
public class IhcPrintVO {

    private Long slideId;

    private Long blockId;

    /**
     * 标记物
     */
    private String slideMarker;

    /**
     * 染色类别
     */
    private Integer specialDye;

    private Long applyId;

    private String number;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Long getSlideId() {
        return slideId;
    }

    public void setSlideId(Long slideId) {
        this.slideId = slideId;
    }

    public Long getBlockId() {
        return blockId;
    }

    public void setBlockId(Long blockId) {
        this.blockId = blockId;
    }

    public String getSlideMarker() {
        return slideMarker;
    }

    public void setSlideMarker(String slideMarker) {
        this.slideMarker = slideMarker;
    }

    public Integer getSpecialDye() {
        return specialDye;
    }

    public void setSpecialDye(Integer specialDye) {
        this.specialDye = specialDye;
    }

    public Long getApplyId() {
        return applyId;
    }

    public void setApplyId(Long applyId) {
        this.applyId = applyId;
    }
}
