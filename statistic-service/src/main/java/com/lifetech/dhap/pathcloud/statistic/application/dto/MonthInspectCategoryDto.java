package com.lifetech.dhap.pathcloud.statistic.application.dto;

/**
 * @author eric.guo@ostengar.com
 * @version 1.0
 * @time 2017-02-13-10:17
 */
public class MonthInspectCategoryDto {

    private Integer inspectCategory;

    private String inspectCategoryName;

    private Long january;

    private Long february;

    private Long march;

    private Long april;

    private Long may;

    private Long june;

    private Long july;

    private Long august;

    private Long september;

    private Long october;

    private Long november;

    private Long december;

    private Long total;

    public MonthInspectCategoryDto(){
        this.january = 0L;
        this.february = 0L;
        this.march = 0L;
        this.april = 0L;
        this.may = 0L;
        this.june = 0L;
        this.july = 0L;
        this.august = 0L;
        this.september = 0L;
        this.october = 0L;
        this.november = 0L;
        this.december = 0L;
        this.total = 0L;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public Integer getInspectCategory() {
        return inspectCategory;
    }

    public void setInspectCategory(Integer inspectCategory) {
        this.inspectCategory = inspectCategory;
    }

    public String getInspectCategoryName() {
        return inspectCategoryName;
    }

    public void setInspectCategoryName(String inspectCategoryName) {
        this.inspectCategoryName = inspectCategoryName;
    }

    public Long getJanuary() {
        return january;
    }

    public void setJanuary(Long january) {
        this.january = january;
    }

    public Long getFebruary() {
        return february;
    }

    public void setFebruary(Long february) {
        this.february = february;
    }

    public Long getMarch() {
        return march;
    }

    public void setMarch(Long march) {
        this.march = march;
    }

    public Long getApril() {
        return april;
    }

    public void setApril(Long april) {
        this.april = april;
    }

    public Long getMay() {
        return may;
    }

    public void setMay(Long may) {
        this.may = may;
    }

    public Long getJune() {
        return june;
    }

    public void setJune(Long june) {
        this.june = june;
    }

    public Long getJuly() {
        return july;
    }

    public void setJuly(Long july) {
        this.july = july;
    }

    public Long getAugust() {
        return august;
    }

    public void setAugust(Long august) {
        this.august = august;
    }

    public Long getSeptember() {
        return september;
    }

    public void setSeptember(Long september) {
        this.september = september;
    }

    public Long getOctober() {
        return october;
    }

    public void setOctober(Long october) {
        this.october = october;
    }

    public Long getNovember() {
        return november;
    }

    public void setNovember(Long november) {
        this.november = november;
    }

    public Long getDecember() {
        return december;
    }

    public void setDecember(Long december) {
        this.december = december;
    }
}
