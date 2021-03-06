package com.lifetech.dhap.pathcloud.tracking.domain.model;

import java.io.Serializable;
import java.util.Date;

public class BlockScore implements Serializable {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column block_score.block_id
     *
     * @mbggenerated Wed Feb 15 14:54:36 CST 2017
     */
    private Long blockId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column block_score.parent_id
     *
     * @mbggenerated Wed Feb 15 14:54:36 CST 2017
     */
    private Long parentId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column block_score.average
     *
     * @mbggenerated Wed Feb 15 14:54:36 CST 2017
     */
    private Float average;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column block_score.grossing
     *
     * @mbggenerated Wed Feb 15 14:54:36 CST 2017
     */
    private Float grossing;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column block_score.dehydrate
     *
     * @mbggenerated Wed Feb 15 14:54:36 CST 2017
     */
    private Float dehydrate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column block_score.embedding
     *
     * @mbggenerated Wed Feb 15 14:54:36 CST 2017
     */
    private Float embedding;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column block_score.sectioning
     *
     * @mbggenerated Wed Feb 15 14:54:36 CST 2017
     */
    private Float sectioning;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column block_score.staining
     *
     * @mbggenerated Wed Feb 15 14:54:36 CST 2017
     */
    private Float staining;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column block_score.sealing
     *
     * @mbggenerated Wed Feb 15 14:54:36 CST 2017
     */
    private Float sealing;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column block_score.note
     *
     * @mbggenerated Wed Feb 15 14:54:36 CST 2017
     */
    private String note;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column block_score.create_by
     *
     * @mbggenerated Wed Feb 15 14:54:36 CST 2017
     */
    private Long createBy;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column block_score.update_by
     *
     * @mbggenerated Wed Feb 15 14:54:36 CST 2017
     */
    private Long updateBy;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column block_score.create_time
     *
     * @mbggenerated Wed Feb 15 14:54:36 CST 2017
     */
    private Date createTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column block_score.update_time
     *
     * @mbggenerated Wed Feb 15 14:54:36 CST 2017
     */
    private Date updateTime;

    private Integer type;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table block_score
     *
     * @mbggenerated Wed Feb 15 14:54:36 CST 2017
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column block_score.block_id
     *
     * @return the value of block_score.block_id
     *
     * @mbggenerated Wed Feb 15 14:54:36 CST 2017
     */
    public Long getBlockId() {
        return blockId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column block_score.block_id
     *
     * @param blockId the value for block_score.block_id
     *
     * @mbggenerated Wed Feb 15 14:54:36 CST 2017
     */
    public void setBlockId(Long blockId) {
        this.blockId = blockId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column block_score.parent_id
     *
     * @return the value of block_score.parent_id
     *
     * @mbggenerated Wed Feb 15 14:54:36 CST 2017
     */
    public Long getParentId() {
        return parentId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column block_score.parent_id
     *
     * @param parentId the value for block_score.parent_id
     *
     * @mbggenerated Wed Feb 15 14:54:36 CST 2017
     */
    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column block_score.average
     *
     * @return the value of block_score.average
     *
     * @mbggenerated Wed Feb 15 14:54:36 CST 2017
     */
    public Float getAverage() {
        return average;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column block_score.average
     *
     * @param average the value for block_score.average
     *
     * @mbggenerated Wed Feb 15 14:54:36 CST 2017
     */
    public void setAverage(Float average) {
        this.average = average;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column block_score.grossing
     *
     * @return the value of block_score.grossing
     *
     * @mbggenerated Wed Feb 15 14:54:36 CST 2017
     */
    public Float getGrossing() {
        return grossing;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column block_score.grossing
     *
     * @param grossing the value for block_score.grossing
     *
     * @mbggenerated Wed Feb 15 14:54:36 CST 2017
     */
    public void setGrossing(Float grossing) {
        this.grossing = grossing;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column block_score.dehydrate
     *
     * @return the value of block_score.dehydrate
     *
     * @mbggenerated Wed Feb 15 14:54:36 CST 2017
     */
    public Float getDehydrate() {
        return dehydrate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column block_score.dehydrate
     *
     * @param dehydrate the value for block_score.dehydrate
     *
     * @mbggenerated Wed Feb 15 14:54:36 CST 2017
     */
    public void setDehydrate(Float dehydrate) {
        this.dehydrate = dehydrate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column block_score.embedding
     *
     * @return the value of block_score.embedding
     *
     * @mbggenerated Wed Feb 15 14:54:36 CST 2017
     */
    public Float getEmbedding() {
        return embedding;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column block_score.embedding
     *
     * @param embedding the value for block_score.embedding
     *
     * @mbggenerated Wed Feb 15 14:54:36 CST 2017
     */
    public void setEmbedding(Float embedding) {
        this.embedding = embedding;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column block_score.sectioning
     *
     * @return the value of block_score.sectioning
     *
     * @mbggenerated Wed Feb 15 14:54:36 CST 2017
     */
    public Float getSectioning() {
        return sectioning;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column block_score.sectioning
     *
     * @param sectioning the value for block_score.sectioning
     *
     * @mbggenerated Wed Feb 15 14:54:36 CST 2017
     */
    public void setSectioning(Float sectioning) {
        this.sectioning = sectioning;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column block_score.staining
     *
     * @return the value of block_score.staining
     *
     * @mbggenerated Wed Feb 15 14:54:36 CST 2017
     */
    public Float getStaining() {
        return staining;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column block_score.staining
     *
     * @param staining the value for block_score.staining
     *
     * @mbggenerated Wed Feb 15 14:54:36 CST 2017
     */
    public void setStaining(Float staining) {
        this.staining = staining;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column block_score.sealing
     *
     * @return the value of block_score.sealing
     *
     * @mbggenerated Wed Feb 15 14:54:36 CST 2017
     */
    public Float getSealing() {
        return sealing;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column block_score.sealing
     *
     * @param sealing the value for block_score.sealing
     *
     * @mbggenerated Wed Feb 15 14:54:36 CST 2017
     */
    public void setSealing(Float sealing) {
        this.sealing = sealing;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column block_score.note
     *
     * @return the value of block_score.note
     *
     * @mbggenerated Wed Feb 15 14:54:36 CST 2017
     */
    public String getNote() {
        return note;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column block_score.note
     *
     * @param note the value for block_score.note
     *
     * @mbggenerated Wed Feb 15 14:54:36 CST 2017
     */
    public void setNote(String note) {
        this.note = note == null ? null : note.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column block_score.create_by
     *
     * @return the value of block_score.create_by
     *
     * @mbggenerated Wed Feb 15 14:54:36 CST 2017
     */
    public Long getCreateBy() {
        return createBy;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column block_score.create_by
     *
     * @param createBy the value for block_score.create_by
     *
     * @mbggenerated Wed Feb 15 14:54:36 CST 2017
     */
    public void setCreateBy(Long createBy) {
        this.createBy = createBy;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column block_score.update_by
     *
     * @return the value of block_score.update_by
     *
     * @mbggenerated Wed Feb 15 14:54:36 CST 2017
     */
    public Long getUpdateBy() {
        return updateBy;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column block_score.update_by
     *
     * @param updateBy the value for block_score.update_by
     *
     * @mbggenerated Wed Feb 15 14:54:36 CST 2017
     */
    public void setUpdateBy(Long updateBy) {
        this.updateBy = updateBy;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column block_score.create_time
     *
     * @return the value of block_score.create_time
     *
     * @mbggenerated Wed Feb 15 14:54:36 CST 2017
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column block_score.create_time
     *
     * @param createTime the value for block_score.create_time
     *
     * @mbggenerated Wed Feb 15 14:54:36 CST 2017
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column block_score.update_time
     *
     * @return the value of block_score.update_time
     *
     * @mbggenerated Wed Feb 15 14:54:36 CST 2017
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column block_score.update_time
     *
     * @param updateTime the value for block_score.update_time
     *
     * @mbggenerated Wed Feb 15 14:54:36 CST 2017
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}