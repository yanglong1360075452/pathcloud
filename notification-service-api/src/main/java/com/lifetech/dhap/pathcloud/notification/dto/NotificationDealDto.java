package com.lifetech.dhap.pathcloud.notification.dto;

/**
 * @author eric.guo@ostengar.com
 * @version 1.0
 * @time 2017-01-12-13:32
 */
public class NotificationDealDto {

    private Long pathId;

    private Long blockId;

    private Integer dealType;//处理方式

    private String note;//备注

    private Long createBy;

    public Long getCreateBy() {
        return createBy;
    }

    public void setCreateBy(Long createBy) {
        this.createBy = createBy;
    }

    public Long getPathId() {
        return pathId;
    }

    public void setPathId(Long pathId) {
        this.pathId = pathId;
    }

    public Long getBlockId() {
        return blockId;
    }

    public void setBlockId(Long blockId) {
        this.blockId = blockId;
    }

    public Integer getDealType() {
        return dealType;
    }

    public void setDealType(Integer dealType) {
        this.dealType = dealType;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
