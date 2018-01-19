package com.lifetech.dhap.pathcloud.tracking.api.vo;

/**
 * Created by LiuMei on 2017-01-10.
 */
public class NoteVO {

    private Long blockId;

    /**
     * 如果已经打印过玻片,需要传玻片ID
     */
    private Long slideId;

    /**
     * 备注
     */
    private String note;

    /**
    备注类别
     */
    private String noteType;

    /**
     * 标记物
     */
    private String slideMarker;

    /**
     * 染色类别
     */
    private Integer specialDye;

    private Long applyId;

    /**
     * 特染申请号
     */
    private String number;

    public Long getSlideId() {
        return slideId;
    }

    public void setSlideId(Long slideId) {
        this.slideId = slideId;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Long getApplyId() {
        return applyId;
    }

    public void setApplyId(Long applyId) {
        this.applyId = applyId;
    }

    public Integer getSpecialDye() {
        return specialDye;
    }

    public void setSpecialDye(Integer specialDye) {
        this.specialDye = specialDye;
    }

    public String getSlideMarker() {
        return slideMarker;
    }

    public void setSlideMarker(String slideMarker) {
        this.slideMarker = slideMarker;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getNoteType() {
        return noteType;
    }

    public void setNoteType(String noteType) {
        this.noteType = noteType;
    }

    public Long getBlockId() {
        return blockId;
    }

    public void setBlockId(Long blockId) {
        this.blockId = blockId;
    }
}
