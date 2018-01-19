package com.lifetech.dhap.pathcloud.statistic.application.dto;

/**
 * Created by HP on 2017/2/15.
 */
public class GroupInspectCategoryDto {

    private Integer inspectCategory;

    private String inspectCategoryName;

    private Group january;

    private Group february;

    private Group march;

    private Group april;

    private Group may;

    private Group june;

    private Group july;

    private Group august;

    private Group september;

    private Group october;

    private Group november;

    private Group december;

    private Long pathologyTotal;

    private Long blockTotal;

    public GroupInspectCategoryDto(){
        this.january = new Group();
        this.february = this.january;
        this.march = this.january;
        this.april = this.january;
        this.may = this.january;
        this.june = this.january;
        this.july = this.january;
        this.august = this.january;
        this.september = this.january;
        this.october = this.january;
        this.november = this.january;
        this.december = this.january;
        this.pathologyTotal = 0L;
        this.blockTotal = 0L;
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


    public Group getJanuary() {
        return january;
    }

    public void setJanuary(Group january) {
        this.january = january;
    }

    public Group getFebruary() {
        return february;
    }

    public void setFebruary(Group february) {
        this.february = february;
    }

    public Group getMarch() {
        return march;
    }

    public void setMarch(Group march) {
        this.march = march;
    }

    public Group getApril() {
        return april;
    }

    public void setApril(Group april) {
        this.april = april;
    }

    public Group getMay() {
        return may;
    }

    public void setMay(Group may) {
        this.may = may;
    }

    public Group getJune() {
        return june;
    }

    public void setJune(Group june) {
        this.june = june;
    }

    public Group getJuly() {
        return july;
    }

    public void setJuly(Group july) {
        this.july = july;
    }

    public Group getAugust() {
        return august;
    }

    public void setAugust(Group august) {
        this.august = august;
    }

    public Group getSeptember() {
        return september;
    }

    public void setSeptember(Group september) {
        this.september = september;
    }

    public Group getOctober() {
        return october;
    }

    public void setOctober(Group october) {
        this.october = october;
    }

    public Group getNovember() {
        return november;
    }

    public void setNovember(Group november) {
        this.november = november;
    }

    public Group getDecember() {
        return december;
    }

    public void setDecember(Group december) {
        this.december = december;
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
}
