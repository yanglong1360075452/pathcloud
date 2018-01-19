package com.lifetech.dhap.pathcloud.application.domain.model;

import java.util.Date;

/**
 * @author eric.guo@ostengar.com
 * @version 1.0
 * @time 2016-11-23-16:16
 */
public class ApplicationSample extends Application{

    private Long sampleId;

    private Long applicationId;

    private String sampleNumber;

    private String name;

    private Integer category;

    private String note;

    private String pathologyNumber;

    private Integer pathologyStatus;

    private Long assignGrossing;

    private Long sampleCreateBy;
    private Long sampleUpdateBy;

    private Date sampleCreateTime;
    private Date sampleUpdateTime;

    private Boolean delete;

    public Boolean getDelete() {
        return delete;
    }

    public void setDelete(Boolean delete) {
        this.delete = delete;
    }

    public Long getSampleCreateBy() {
        return sampleCreateBy;
    }

    public void setSampleCreateBy(Long sampleCreateBy) {
        this.sampleCreateBy = sampleCreateBy;
    }

    public Long getSampleUpdateBy() {
        return sampleUpdateBy;
    }

    public void setSampleUpdateBy(Long sampleUpdateBy) {
        this.sampleUpdateBy = sampleUpdateBy;
    }

    public Date getSampleCreateTime() {
        return sampleCreateTime;
    }

    public void setSampleCreateTime(Date sampleCreateTime) {
        this.sampleCreateTime = sampleCreateTime;
    }

    public Date getSampleUpdateTime() {
        return sampleUpdateTime;
    }

    public void setSampleUpdateTime(Date sampleUpdateTime) {
        this.sampleUpdateTime = sampleUpdateTime;
    }

    public Long getAssignGrossing() {
        return assignGrossing;
    }

    public void setAssignGrossing(Long assignGrossing) {
        this.assignGrossing = assignGrossing;
    }

    public Long getSampleId() {
        return sampleId;
    }

    public void setSampleId(Long sampleId) {
        this.sampleId = sampleId;
    }

    public Long getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(Long applicationId) {
        this.applicationId = applicationId;
    }

    public String getSampleNumber() {
        return sampleNumber;
    }

    public void setSampleNumber(String sampleNumber) {
        this.sampleNumber = sampleNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCategory() {
        return category;
    }

    public void setCategory(Integer category) {
        this.category = category;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getPathologyNumber() {
        return pathologyNumber;
    }

    public void setPathologyNumber(String pathologyNumber) {
        this.pathologyNumber = pathologyNumber;
    }

    public Integer getPathologyStatus() {
        return pathologyStatus;
    }

    public void setPathologyStatus(Integer pathologyStatus) {
        this.pathologyStatus = pathologyStatus;
    }
}
