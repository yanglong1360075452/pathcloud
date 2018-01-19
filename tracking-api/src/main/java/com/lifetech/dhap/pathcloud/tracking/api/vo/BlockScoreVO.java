package com.lifetech.dhap.pathcloud.tracking.api.vo;

import java.io.Serializable;

/**
 * Created by LiuMei on 2017-01-06.
 */
public class BlockScoreVO implements Serializable{

    private Long blockId;

    private Long parentId;

    private float average;

    private float grossing;

    private float dehydrate;

    private float embedding;

    private float sectioning;

    private float staining;

    private float sealing;

    private String note;

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
}
