package com.lifetech.dhap.pathcloud.file.domain.model;

import java.io.Serializable;
import java.util.Date;

public class PathologyFile implements Serializable {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column pathology_file.id
     *
     * @mbggenerated Thu Dec 22 15:07:21 CST 2016
     */
    private Long id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column pathology_file.pathology_id
     *
     * @mbggenerated Thu Dec 22 15:07:21 CST 2016
     */
    private Long pathologyId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column pathology_file.operation
     *
     * @mbggenerated Thu Dec 22 15:07:21 CST 2016
     */
    private Integer operation;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column pathology_file.type
     *
     * @mbggenerated Thu Dec 22 15:07:21 CST 2016
     */
    private Integer type;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column pathology_file.content
     *
     * @mbggenerated Thu Dec 22 15:07:21 CST 2016
     */
    private String content;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column pathology_file.keep_flag
     *
     * @mbggenerated Thu Dec 22 15:07:21 CST 2016
     */
    private Boolean keepFlag;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column pathology_file.create_by
     *
     * @mbggenerated Thu Dec 22 15:07:21 CST 2016
     */
    private Long createBy;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column pathology_file.update_by
     *
     * @mbggenerated Thu Dec 22 15:07:21 CST 2016
     */
    private Long updateBy;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column pathology_file.create_time
     *
     * @mbggenerated Thu Dec 22 15:07:21 CST 2016
     */
    private Date createTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column pathology_file.update_time
     *
     * @mbggenerated Thu Dec 22 15:07:21 CST 2016
     */
    private Date updateTime;

    private String tag;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table pathology_file
     *
     * @mbggenerated Thu Dec 22 15:07:21 CST 2016
     */
    private static final long serialVersionUID = 1L;

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column pathology_file.id
     *
     * @return the value of pathology_file.id
     *
     * @mbggenerated Thu Dec 22 15:07:21 CST 2016
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column pathology_file.id
     *
     * @param id the value for pathology_file.id
     *
     * @mbggenerated Thu Dec 22 15:07:21 CST 2016
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column pathology_file.pathology_id
     *
     * @return the value of pathology_file.pathology_id
     *
     * @mbggenerated Thu Dec 22 15:07:21 CST 2016
     */
    public Long getPathologyId() {
        return pathologyId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column pathology_file.pathology_id
     *
     * @param pathologyId the value for pathology_file.pathology_id
     *
     * @mbggenerated Thu Dec 22 15:07:21 CST 2016
     */
    public void setPathologyId(Long pathologyId) {
        this.pathologyId = pathologyId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column pathology_file.operation
     *
     * @return the value of pathology_file.operation
     *
     * @mbggenerated Thu Dec 22 15:07:21 CST 2016
     */
    public Integer getOperation() {
        return operation;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column pathology_file.operation
     *
     * @param operation the value for pathology_file.operation
     *
     * @mbggenerated Thu Dec 22 15:07:21 CST 2016
     */
    public void setOperation(Integer operation) {
        this.operation = operation;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column pathology_file.type
     *
     * @return the value of pathology_file.type
     *
     * @mbggenerated Thu Dec 22 15:07:21 CST 2016
     */
    public Integer getType() {
        return type;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column pathology_file.type
     *
     * @param type the value for pathology_file.type
     *
     * @mbggenerated Thu Dec 22 15:07:21 CST 2016
     */
    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column pathology_file.content
     *
     * @return the value of pathology_file.content
     *
     * @mbggenerated Thu Dec 22 15:07:21 CST 2016
     */
    public String getContent() {
        return content;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column pathology_file.content
     *
     * @param content the value for pathology_file.content
     *
     * @mbggenerated Thu Dec 22 15:07:21 CST 2016
     */
    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column pathology_file.keep_flag
     *
     * @return the value of pathology_file.keep_flag
     *
     * @mbggenerated Thu Dec 22 15:07:21 CST 2016
     */
    public Boolean getKeepFlag() {
        return keepFlag;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column pathology_file.keep_flag
     *
     * @param keepFlag the value for pathology_file.keep_flag
     *
     * @mbggenerated Thu Dec 22 15:07:21 CST 2016
     */
    public void setKeepFlag(Boolean keepFlag) {
        this.keepFlag = keepFlag;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column pathology_file.create_by
     *
     * @return the value of pathology_file.create_by
     *
     * @mbggenerated Thu Dec 22 15:07:21 CST 2016
     */
    public Long getCreateBy() {
        return createBy;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column pathology_file.create_by
     *
     * @param createBy the value for pathology_file.create_by
     *
     * @mbggenerated Thu Dec 22 15:07:21 CST 2016
     */
    public void setCreateBy(Long createBy) {
        this.createBy = createBy;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column pathology_file.update_by
     *
     * @return the value of pathology_file.update_by
     *
     * @mbggenerated Thu Dec 22 15:07:21 CST 2016
     */
    public Long getUpdateBy() {
        return updateBy;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column pathology_file.update_by
     *
     * @param updateBy the value for pathology_file.update_by
     *
     * @mbggenerated Thu Dec 22 15:07:21 CST 2016
     */
    public void setUpdateBy(Long updateBy) {
        this.updateBy = updateBy;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column pathology_file.create_time
     *
     * @return the value of pathology_file.create_time
     *
     * @mbggenerated Thu Dec 22 15:07:21 CST 2016
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column pathology_file.create_time
     *
     * @param createTime the value for pathology_file.create_time
     *
     * @mbggenerated Thu Dec 22 15:07:21 CST 2016
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column pathology_file.update_time
     *
     * @return the value of pathology_file.update_time
     *
     * @mbggenerated Thu Dec 22 15:07:21 CST 2016
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column pathology_file.update_time
     *
     * @param updateTime the value for pathology_file.update_time
     *
     * @mbggenerated Thu Dec 22 15:07:21 CST 2016
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}