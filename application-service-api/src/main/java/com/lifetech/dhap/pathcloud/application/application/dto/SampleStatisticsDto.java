package com.lifetech.dhap.pathcloud.application.application.dto;

/**
 * Created by LiuMei on 2017-02-06.
 */
public class SampleStatisticsDto {

    private Integer category;
    private String categoryDesc;

    private Long count;

    public Integer getCategory() {
        return category;
    }

    public void setCategory(Integer category) {
        this.category = category;
    }

    public String getCategoryDesc() {
        return categoryDesc;
    }

    public void setCategoryDesc(String categoryDesc) {
        this.categoryDesc = categoryDesc;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }
}
