package com.lifetech.dhap.pathcloud.reagent.api.vo;

/**
 * Created by LiuMei on 2017-12-05.
 */
public class AdjustVO {

    /**
     * 库存ID
     */
    private Long id;

    /**
     * 调整量
     */
    private Double adjust;

    private String note;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getAdjust() {
        return adjust;
    }

    public void setAdjust(Double adjust) {
        this.adjust = adjust;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
