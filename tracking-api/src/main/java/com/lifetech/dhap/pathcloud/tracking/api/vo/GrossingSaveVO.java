package com.lifetech.dhap.pathcloud.tracking.api.vo;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author eric.guo@ostengar.com
 * @version 1.0
 * @time 2016-12-01-14:20
 */
public class GrossingSaveVO implements Serializable {

    private Long id;

    private String jujianNote;

    private String bingdongNote;

    private Long operatorId;

    private Long secOperatorId;

    private Boolean manualFlag;

    private String number;

    /**
     * 删除蜡块列表
     */
    private List<Long> deleteBlocks;

    private List<BlockVO> blocks;

    private Boolean print;

    public List<Long> getDeleteBlocks() {
        return deleteBlocks;
    }

    public void setDeleteBlocks(List<Long> deleteBlocks) {
        this.deleteBlocks = deleteBlocks;
    }

    public Boolean getPrint() {
        return print;
    }

    public void setPrint(Boolean print) {
        this.print = print;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getJujianNote() {
        return jujianNote;
    }

    public void setJujianNote(String jujianNote) {
        this.jujianNote = jujianNote;
    }

    public String getBingdongNote() {
        return bingdongNote;
    }

    public void setBingdongNote(String bingdongNote) {
        this.bingdongNote = bingdongNote;
    }

    public List<BlockVO> getBlocks() {
        return blocks;
    }

    public void setBlocks(List<BlockVO> blocks) {
        this.blocks = blocks;
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
