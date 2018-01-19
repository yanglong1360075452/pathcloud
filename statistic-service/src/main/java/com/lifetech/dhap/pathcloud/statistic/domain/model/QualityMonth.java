package com.lifetech.dhap.pathcloud.statistic.domain.model;

/**
 * @author eric.guo@ostengar.com
 * @version 1.0
 * @time 2017-02-15-17:14
 */
public class QualityMonth {

    private Integer pathologyMonth;//月份

    private Long pathologyTotal;//病理总数

    private Long reGrossingTotal;//重取蜡块数

    private Long blockTotal;//腊块数

    private Long reSectionTotal;//重切腊块数

    private Long slideTotal;//玻片数

    private Long lowScoreTotal;//不合格数

    private Long goodScoreTotal;//切片优良数

    private Long delayTotal;//延时报告数

    private Long coincidenceTotal;//冰冻符合数

    public QualityMonth(){
        this.pathologyTotal = 0L;
        this.reGrossingTotal = 0L;
        this.blockTotal = 0L;
        this.reSectionTotal = 0L;
        this.slideTotal = 0L;
        this.lowScoreTotal = 0L;
        this.goodScoreTotal = 0L;
        this.delayTotal = 0L;
        this.coincidenceTotal = 0L;
    }

    public Long getCoincidenceTotal() {
        return coincidenceTotal;
    }

    public void setCoincidenceTotal(Long coincidenceTotal) {
        this.coincidenceTotal = coincidenceTotal;
    }

    public Long getDelayTotal() {
        return delayTotal;
    }

    public void setDelayTotal(Long delayTotal) {
        this.delayTotal = delayTotal;
    }

    public Long getGoodScoreTotal() {
        return goodScoreTotal;
    }

    public void setGoodScoreTotal(Long goodScoreTotal) {
        this.goodScoreTotal = goodScoreTotal;
    }

    public Integer getPathologyMonth() {
        return pathologyMonth;
    }

    public void setPathologyMonth(Integer pathologyMonth) {
        this.pathologyMonth = pathologyMonth;
    }

    public Long getPathologyTotal() {
        return pathologyTotal;
    }

    public void setPathologyTotal(Long pathologyTotal) {
        this.pathologyTotal = pathologyTotal;
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

    public Long getBlockTotal() {
        return blockTotal;
    }

    public void setBlockTotal(Long blockTotal) {
        this.blockTotal = blockTotal;
    }

    public Long getSlideTotal() {
        return slideTotal;
    }

    public void setSlideTotal(Long slideTotal) {
        this.slideTotal = slideTotal;
    }

    public Long getLowScoreTotal() {
        return lowScoreTotal;
    }

    public void setLowScoreTotal(Long lowScoreTotal) {
        this.lowScoreTotal = lowScoreTotal;
    }
}
