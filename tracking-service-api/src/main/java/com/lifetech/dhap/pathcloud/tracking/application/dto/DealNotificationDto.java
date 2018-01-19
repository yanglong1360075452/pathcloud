package com.lifetech.dhap.pathcloud.tracking.application.dto;

/**
 * @author eric.guo@ostengar.com
 * @version 1.0
 * @time 2017-01-05-14:45
 */
public class DealNotificationDto {

    private Long pathId;

    private Long blockId;

    private Integer dealType;

    private String note;

    private Long createBy;

    private Integer source;

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

    public Integer getSource() {
        return source;
    }

    public void setSource(Integer source) {
        this.source = source;
    }
}
