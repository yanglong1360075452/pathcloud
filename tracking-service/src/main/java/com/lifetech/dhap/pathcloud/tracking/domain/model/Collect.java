package com.lifetech.dhap.pathcloud.tracking.domain.model;

import java.io.Serializable;
import java.util.Date;

public class Collect implements Serializable {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column collect.id
     *
     * @mbggenerated Wed Sep 27 14:59:55 CST 2017
     */
    private Long id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column collect.target_id
     *
     * @mbggenerated Wed Sep 27 14:59:55 CST 2017
     */
    private Long targetId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column collect.category
     *
     * @mbggenerated Wed Sep 27 14:59:55 CST 2017
     */
    private Integer category;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column collect.abel
     *
     * @mbggenerated Wed Sep 27 14:59:55 CST 2017
     */
    private String label;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column collect.permission
     *
     * @mbggenerated Wed Sep 27 14:59:55 CST 2017
     */
    private Integer permission;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column collect.create_by
     *
     * @mbggenerated Wed Sep 27 14:59:55 CST 2017
     */
    private Long createBy;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column collect.update_by
     *
     * @mbggenerated Wed Sep 27 14:59:55 CST 2017
     */
    private Long updateBy;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column collect.create_time
     *
     * @mbggenerated Wed Sep 27 14:59:55 CST 2017
     */
    private Date createTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column collect.update_time
     *
     * @mbggenerated Wed Sep 27 14:59:55 CST 2017
     */
    private Date updateTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column collect.note
     *
     * @mbggenerated Wed Sep 27 14:59:55 CST 2017
     */
    private String note;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table collect
     *
     * @mbggenerated Wed Sep 27 14:59:55 CST 2017
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column collect.id
     *
     * @return the value of collect.id
     *
     * @mbggenerated Wed Sep 27 14:59:55 CST 2017
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column collect.id
     *
     * @param id the value for collect.id
     *
     * @mbggenerated Wed Sep 27 14:59:55 CST 2017
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column collect.target_id
     *
     * @return the value of collect.target_id
     *
     * @mbggenerated Wed Sep 27 14:59:55 CST 2017
     */
    public Long getTargetId() {
        return targetId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column collect.target_id
     *
     * @param targetId the value for collect.target_id
     *
     * @mbggenerated Wed Sep 27 14:59:55 CST 2017
     */
    public void setTargetId(Long targetId) {
        this.targetId = targetId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column collect.category
     *
     * @return the value of collect.category
     *
     * @mbggenerated Wed Sep 27 14:59:55 CST 2017
     */
    public Integer getCategory() {
        return category;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column collect.category
     *
     * @param category the value for collect.category
     *
     * @mbggenerated Wed Sep 27 14:59:55 CST 2017
     */
    public void setCategory(Integer category) {
        this.category = category;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column collect.abel
     *
     * @return the value of collect.abel
     *
     * @mbggenerated Wed Sep 27 14:59:55 CST 2017
     */
    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column collect.permission
     *
     * @return the value of collect.permission
     *
     * @mbggenerated Wed Sep 27 14:59:55 CST 2017
     */
    public Integer getPermission() {
        return permission;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column collect.permission
     *
     * @param permission the value for collect.permission
     *
     * @mbggenerated Wed Sep 27 14:59:55 CST 2017
     */
    public void setPermission(Integer permission) {
        this.permission = permission;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column collect.create_by
     *
     * @return the value of collect.create_by
     *
     * @mbggenerated Wed Sep 27 14:59:55 CST 2017
     */
    public Long getCreateBy() {
        return createBy;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column collect.create_by
     *
     * @param createBy the value for collect.create_by
     *
     * @mbggenerated Wed Sep 27 14:59:55 CST 2017
     */
    public void setCreateBy(Long createBy) {
        this.createBy = createBy;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column collect.update_by
     *
     * @return the value of collect.update_by
     *
     * @mbggenerated Wed Sep 27 14:59:55 CST 2017
     */
    public Long getUpdateBy() {
        return updateBy;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column collect.update_by
     *
     * @param updateBy the value for collect.update_by
     *
     * @mbggenerated Wed Sep 27 14:59:55 CST 2017
     */
    public void setUpdateBy(Long updateBy) {
        this.updateBy = updateBy;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column collect.create_time
     *
     * @return the value of collect.create_time
     *
     * @mbggenerated Wed Sep 27 14:59:55 CST 2017
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column collect.create_time
     *
     * @param createTime the value for collect.create_time
     *
     * @mbggenerated Wed Sep 27 14:59:55 CST 2017
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column collect.update_time
     *
     * @return the value of collect.update_time
     *
     * @mbggenerated Wed Sep 27 14:59:55 CST 2017
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column collect.update_time
     *
     * @param updateTime the value for collect.update_time
     *
     * @mbggenerated Wed Sep 27 14:59:55 CST 2017
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column collect.note
     *
     * @return the value of collect.note
     *
     * @mbggenerated Wed Sep 27 14:59:55 CST 2017
     */
    public String getNote() {
        return note;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column collect.note
     *
     * @param note the value for collect.note
     *
     * @mbggenerated Wed Sep 27 14:59:55 CST 2017
     */
    public void setNote(String note) {
        this.note = note == null ? null : note.trim();
    }
}