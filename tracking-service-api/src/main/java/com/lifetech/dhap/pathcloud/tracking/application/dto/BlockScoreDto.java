package com.lifetech.dhap.pathcloud.tracking.application.dto;

import java.util.Date;

/**
 * Created by LiuMei on 2017-01-11.
 */
public class BlockScoreDto {

    private Long blockId;

    private Long parentId;

    private Integer type;

    private float average;

    private float grossing;

    private float dehydrate;

    private float embedding;

    private float sectioning;

    private float staining;

    private float sealing;

    private String note;

    private Long createBy;

    private Long updateBy;

    private Date createTime;

    private Date updateTime;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Long getBlockId() {
        return blockId;
    }

    public void setBlockId(Long blockId) {
        this.blockId = blockId;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public float getAverage() {
        return average;
    }

    public void setAverage(float average) {
        this.average = average;
    }

    public float getGrossing() {
        return grossing;
    }

    public void setGrossing(float grossing) {
        this.grossing = grossing;
    }

    public float getDehydrate() {
        return dehydrate;
    }

    public void setDehydrate(float dehydrate) {
        this.dehydrate = dehydrate;
    }

    public float getEmbedding() {
        return embedding;
    }

    public void setEmbedding(float embedding) {
        this.embedding = embedding;
    }

    public float getSectioning() {
        return sectioning;
    }

    public void setSectioning(float sectioning) {
        this.sectioning = sectioning;
    }

    public float getStaining() {
        return staining;
    }

    public void setStaining(float staining) {
        this.staining = staining;
    }

    public float getSealing() {
        return sealing;
    }

    public void setSealing(float sealing) {
        this.sealing = sealing;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Long getCreateBy() {
        return createBy;
    }

    public void setCreateBy(Long createBy) {
        this.createBy = createBy;
    }

    public Long getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(Long updateBy) {
        this.updateBy = updateBy;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
