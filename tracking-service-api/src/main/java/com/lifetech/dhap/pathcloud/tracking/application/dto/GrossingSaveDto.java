package com.lifetech.dhap.pathcloud.tracking.application.dto;

import java.util.List;

/**
 * @author eric.guo@ostengar.com
 * @version 1.0
 * @time 2016-12-07-14:08
 */
public class GrossingSaveDto {

    private Long pathologyId;

    private String jujianNote;

    private String bingdongNote;

    private Long updateBy;

    private Long operatorId;

    private Long secOperatorId;

    private Boolean manualFlag;

    private List<BlockDto> blocks;

    private String number;

    private List<Long> deleteBlocks;

    public List<Long> getDeleteBlocks() {
        return deleteBlocks;
    }

    public void setDeleteBlocks(List<Long> deleteBlocks) {
        this.deleteBlocks = deleteBlocks;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Long getPathologyId() {
        return pathologyId;
    }

    public void setPathologyId(Long pathologyId) {
        this.pathologyId = pathologyId;
    }

    public String getJujianNote() {
        return jujianNote;
    }

    public void setJujianNote(String jujianNote) {
        this.jujianNote = jujianNote;
    }

    public List<BlockDto> getBlocks() {
        return blocks;
    }

    public void setBlocks(List<BlockDto> blocks) {
        this.blocks = blocks;
    }

    public String getBingdongNote() {
        return bingdongNote;
    }

    public void setBingdongNote(String bingdongNote) {
        this.bingdongNote = bingdongNote;
    }

    public Long getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(Long updateBy) {
        this.updateBy = updateBy;
    }

    public Long getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(Long operatorId) {
        this.operatorId = operatorId;
    }

    public Long getSecOperatorId() {
        return secOperatorId;
    }

    public void setSecOperatorId(Long secOperatorId) {
        this.secOperatorId = secOperatorId;
    }

    public Boolean getManualFlag() {
        return manualFlag;
    }

    public void setManualFlag(Boolean manualFlag) {
        this.manualFlag = manualFlag;
    }
}
