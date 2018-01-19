package com.lifetech.dhap.pathcloud.tracking.api.vo;

/**
 * @author LiuMei
 * @date 2017-11-02.
 */
public class ReportSummaryVO {

    private Integer type;
    private String typeDesc;

    private Long pathId;
    private Long specialApplyId;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getTypeDesc() {
        return typeDesc;
    }

    public void setTypeDesc(String typeDesc) {
        this.typeDesc = typeDesc;
    }

    public Long getPathId() {
        return pathId;
    }

    public void setPathId(Long pathId) {
        this.pathId = pathId;
    }

    public Long getSpecialApplyId() {
        return specialApplyId;
    }

    public void setSpecialApplyId(Long specialApplyId) {
        this.specialApplyId = specialApplyId;
    }
}
