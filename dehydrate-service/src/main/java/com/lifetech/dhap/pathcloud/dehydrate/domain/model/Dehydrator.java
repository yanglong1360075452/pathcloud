package com.lifetech.dhap.pathcloud.dehydrate.domain.model;

import java.io.Serializable;
import java.util.Date;

public class Dehydrator implements Serializable {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column dehydrator.id
     *
     * @mbggenerated Tue Dec 13 16:37:30 CST 2016
     */
    private Long id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column dehydrator.instrument_id
     *
     * @mbggenerated Tue Dec 13 16:37:30 CST 2016
     */
    private Long instrumentId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column dehydrator.status
     *
     * @mbggenerated Tue Dec 13 16:37:30 CST 2016
     */
    private Integer status;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column dehydrator.used_block
     *
     * @mbggenerated Tue Dec 13 16:37:30 CST 2016
     */
    private Integer usedBlock;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column dehydrator.capacity
     *
     * @mbggenerated Tue Dec 13 16:37:30 CST 2016
     */
    private Integer capacity;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column dehydrator.reset_time
     *
     * @mbggenerated Tue Dec 13 16:37:30 CST 2016
     */
    private Date resetTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column dehydrator.start_time
     *
     * @mbggenerated Tue Dec 13 16:37:30 CST 2016
     */
    private Date startTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column dehydrator.end_time
     *
     * @mbggenerated Tue Dec 13 16:37:30 CST 2016
     */
    private Date endTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column dehydrator.create_by
     *
     * @mbggenerated Tue Dec 13 16:37:30 CST 2016
     */
    private Long createBy;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column dehydrator.update_by
     *
     * @mbggenerated Tue Dec 13 16:37:30 CST 2016
     */
    private Long updateBy;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column dehydrator.create_time
     *
     * @mbggenerated Tue Dec 13 16:37:30 CST 2016
     */
    private Date createTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column dehydrator.update_time
     *
     * @mbggenerated Tue Dec 13 16:37:30 CST 2016
     */
    private Date updateTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table dehydrator
     *
     * @mbggenerated Tue Dec 13 16:37:30 CST 2016
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column dehydrator.id
     *
     * @return the value of dehydrator.id
     *
     * @mbggenerated Tue Dec 13 16:37:30 CST 2016
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column dehydrator.id
     *
     * @param id the value for dehydrator.id
     *
     * @mbggenerated Tue Dec 13 16:37:30 CST 2016
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column dehydrator.instrument_id
     *
     * @return the value of dehydrator.instrument_id
     *
     * @mbggenerated Tue Dec 13 16:37:30 CST 2016
     */
    public Long getInstrumentId() {
        return instrumentId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column dehydrator.instrument_id
     *
     * @param instrumentId the value for dehydrator.instrument_id
     *
     * @mbggenerated Tue Dec 13 16:37:30 CST 2016
     */
    public void setInstrumentId(Long instrumentId) {
        this.instrumentId = instrumentId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column dehydrator.status
     *
     * @return the value of dehydrator.status
     *
     * @mbggenerated Tue Dec 13 16:37:30 CST 2016
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column dehydrator.status
     *
     * @param status the value for dehydrator.status
     *
     * @mbggenerated Tue Dec 13 16:37:30 CST 2016
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column dehydrator.used_block
     *
     * @return the value of dehydrator.used_block
     *
     * @mbggenerated Tue Dec 13 16:37:30 CST 2016
     */
    public Integer getUsedBlock() {
        return usedBlock;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column dehydrator.used_block
     *
     * @param usedBlock the value for dehydrator.used_block
     *
     * @mbggenerated Tue Dec 13 16:37:30 CST 2016
     */
    public void setUsedBlock(Integer usedBlock) {
        this.usedBlock = usedBlock;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column dehydrator.capacity
     *
     * @return the value of dehydrator.capacity
     *
     * @mbggenerated Tue Dec 13 16:37:30 CST 2016
     */
    public Integer getCapacity() {
        return capacity;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column dehydrator.capacity
     *
     * @param capacity the value for dehydrator.capacity
     *
     * @mbggenerated Tue Dec 13 16:37:30 CST 2016
     */
    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column dehydrator.reset_time
     *
     * @return the value of dehydrator.reset_time
     *
     * @mbggenerated Tue Dec 13 16:37:30 CST 2016
     */
    public Date getResetTime() {
        return resetTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column dehydrator.reset_time
     *
     * @param resetTime the value for dehydrator.reset_time
     *
     * @mbggenerated Tue Dec 13 16:37:30 CST 2016
     */
    public void setResetTime(Date resetTime) {
        this.resetTime = resetTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column dehydrator.start_time
     *
     * @return the value of dehydrator.start_time
     *
     * @mbggenerated Tue Dec 13 16:37:30 CST 2016
     */
    public Date getStartTime() {
        return startTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column dehydrator.start_time
     *
     * @param startTime the value for dehydrator.start_time
     *
     * @mbggenerated Tue Dec 13 16:37:30 CST 2016
     */
    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column dehydrator.end_time
     *
     * @return the value of dehydrator.end_time
     *
     * @mbggenerated Tue Dec 13 16:37:30 CST 2016
     */
    public Date getEndTime() {
        return endTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column dehydrator.end_time
     *
     * @param endTime the value for dehydrator.end_time
     *
     * @mbggenerated Tue Dec 13 16:37:30 CST 2016
     */
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column dehydrator.create_by
     *
     * @return the value of dehydrator.create_by
     *
     * @mbggenerated Tue Dec 13 16:37:30 CST 2016
     */
    public Long getCreateBy() {
        return createBy;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column dehydrator.create_by
     *
     * @param createBy the value for dehydrator.create_by
     *
     * @mbggenerated Tue Dec 13 16:37:30 CST 2016
     */
    public void setCreateBy(Long createBy) {
        this.createBy = createBy;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column dehydrator.update_by
     *
     * @return the value of dehydrator.update_by
     *
     * @mbggenerated Tue Dec 13 16:37:30 CST 2016
     */
    public Long getUpdateBy() {
        return updateBy;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column dehydrator.update_by
     *
     * @param updateBy the value for dehydrator.update_by
     *
     * @mbggenerated Tue Dec 13 16:37:30 CST 2016
     */
    public void setUpdateBy(Long updateBy) {
        this.updateBy = updateBy;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column dehydrator.create_time
     *
     * @return the value of dehydrator.create_time
     *
     * @mbggenerated Tue Dec 13 16:37:30 CST 2016
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column dehydrator.create_time
     *
     * @param createTime the value for dehydrator.create_time
     *
     * @mbggenerated Tue Dec 13 16:37:30 CST 2016
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column dehydrator.update_time
     *
     * @return the value of dehydrator.update_time
     *
     * @mbggenerated Tue Dec 13 16:37:30 CST 2016
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column dehydrator.update_time
     *
     * @param updateTime the value for dehydrator.update_time
     *
     * @mbggenerated Tue Dec 13 16:37:30 CST 2016
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}