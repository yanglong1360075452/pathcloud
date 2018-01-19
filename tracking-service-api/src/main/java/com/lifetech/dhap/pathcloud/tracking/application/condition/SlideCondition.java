package com.lifetech.dhap.pathcloud.tracking.application.condition;

import com.lifetech.dhap.pathcloud.common.base.PageCondition;

/**
 * Created by LiuMei on 2017-01-04.
 */
public class SlideCondition extends PageCondition {

    private String pathNo;

    private String startPathNo;

    private String endPathNo;

    private Long blockId;

    private Long pathId;

    private Integer status;

    private Integer againstBiaoshi;

    private String number;

    /**
     * 切片操作者
     */
    private Long sectionOperator;

    public Long getSectionOperator() {
        return sectionOperator;
    }

    public void setSectionOperator(Long sectionOperator) {
        this.sectionOperator = sectionOperator;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Integer getAgainstBiaoshi() {
        return againstBiaoshi;
    }

    public void setAgainstBiaoshi(Integer againstBiaoshi) {
        this.againstBiaoshi = againstBiaoshi;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

    public String getPathNo() {
        return pathNo;
    }

    public void setPathNo(String pathNo) {
        this.pathNo = pathNo;
    }

    public String getStartPathNo() {
        return startPathNo;
    }

    public void setStartPathNo(String startPathNo) {
        this.startPathNo = startPathNo;
    }

    public String getEndPathNo() {
        return endPathNo;
    }

    public void setEndPathNo(String endPathNo) {
        this.endPathNo = endPathNo;
    }
}
