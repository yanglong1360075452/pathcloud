package com.lifetech.dhap.pathcloud.statistic.domain.model;

/**
 * @author eric.guo@ostengar.com
 * @version 1.0
 * @time 2017-02-16-13:58
 */
public class QualityPersonal {

    private Long operatorId;

    private Integer operation;

    private Long pathologyTotal;//样本总数

    private Long blockTotal;//腊块总数

    private Long reGrossingTotal;//重取数

    private Long reSectionTotal;//重切腊块数

    private Long scoreTotal;//评分的数量

    private Long lowScoreTotal;//低于评分的数量

    private Integer score;//质控评分

    public Long getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(Long operatorId) {
        this.operatorId = operatorId;
    }

    public Long getPathologyTotal() {
        return pathologyTotal;
    }

    public void setPathologyTotal(Long pathologyTotal) {
        this.pathologyTotal = pathologyTotal;
    }

    public Long getBlockTotal() {
        return blockTotal;
    }

    public void setBlockTotal(Long blockTotal) {
        this.blockTotal = blockTotal;
    }

    public Long getReGrossingTotal() {
        return reGrossingTotal;
    }

    public void setReGrossingTotal(Long reGrossingTotal) {
        this.reGrossingTotal = reGrossingTotal;
    }

    public Long getReSectionTotal() {
        return reSectionTotal;
    }

    public void setReSectionTotal(Long reSectionTotal) {
        this.reSectionTotal = reSectionTotal;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Long getScoreTotal() {
        return scoreTotal;
    }

    public void setScoreTotal(Long scoreTotal) {
        this.scoreTotal = scoreTotal;
    }

    public Long getLowScoreTotal() {
        return lowScoreTotal;
    }

    public void setLowScoreTotal(Long lowScoreTotal) {
        this.lowScoreTotal = lowScoreTotal;
    }

    public Integer getOperation() {
        return operation;
    }

    public void setOperation(Integer operation) {
        this.operation = operation;
    }
}
