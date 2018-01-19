package com.lifetech.dhap.pathcloud.setting.domain.model;

import java.io.Serializable;
import java.util.Date;

public class ParamSetting implements Serializable {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column param_setting.id
     *
     * @mbggenerated Wed Dec 07 13:28:36 CST 2016
     */
    private Integer id;

    private String position;


    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column param_setting.key
     *
     * @mbggenerated Wed Dec 07 13:28:36 CST 2016
     */
    private String key;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column param_setting.create_by
     *
     * @mbggenerated Wed Dec 07 13:28:36 CST 2016
     */
    private Long createBy;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column param_setting.update_by
     *
     * @mbggenerated Wed Dec 07 13:28:36 CST 2016
     */
    private Long updateBy;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column param_setting.create_time
     *
     * @mbggenerated Wed Dec 07 13:28:36 CST 2016
     */
    private Date createTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column param_setting.update_time
     *
     * @mbggenerated Wed Dec 07 13:28:36 CST 2016
     */
    private Date updateTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column param_setting.content
     *
     * @mbggenerated Wed Dec 07 13:28:36 CST 2016
     */
    private String content;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table param_setting
     *
     * @mbggenerated Wed Dec 07 13:28:36 CST 2016
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column param_setting.id
     *
     * @return the value of param_setting.id
     *
     * @mbggenerated Wed Dec 07 13:28:36 CST 2016
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column param_setting.id
     *
     * @param id the value for param_setting.id
     *
     * @mbggenerated Wed Dec 07 13:28:36 CST 2016
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column param_setting.key
     *
     * @return the value of param_setting.key
     *
     * @mbggenerated Wed Dec 07 13:28:36 CST 2016
     */
    public String getKey() {
        return key;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column param_setting.key
     *
     * @param key the value for param_setting.key
     *
     * @mbggenerated Wed Dec 07 13:28:36 CST 2016
     */
    public void setKey(String key) {
        this.key = key == null ? null : key.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column param_setting.create_by
     *
     * @return the value of param_setting.create_by
     *
     * @mbggenerated Wed Dec 07 13:28:36 CST 2016
     */
    public Long getCreateBy() {
        return createBy;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column param_setting.create_by
     *
     * @param createBy the value for param_setting.create_by
     *
     * @mbggenerated Wed Dec 07 13:28:36 CST 2016
     */
    public void setCreateBy(Long createBy) {
        this.createBy = createBy;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column param_setting.update_by
     *
     * @return the value of param_setting.update_by
     *
     * @mbggenerated Wed Dec 07 13:28:36 CST 2016
     */
    public Long getUpdateBy() {
        return updateBy;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column param_setting.update_by
     *
     * @param updateBy the value for param_setting.update_by
     *
     * @mbggenerated Wed Dec 07 13:28:36 CST 2016
     */
    public void setUpdateBy(Long updateBy) {
        this.updateBy = updateBy;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column param_setting.create_time
     *
     * @return the value of param_setting.create_time
     *
     * @mbggenerated Wed Dec 07 13:28:36 CST 2016
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column param_setting.create_time
     *
     * @param createTime the value for param_setting.create_time
     *
     * @mbggenerated Wed Dec 07 13:28:36 CST 2016
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column param_setting.update_time
     *
     * @return the value of param_setting.update_time
     *
     * @mbggenerated Wed Dec 07 13:28:36 CST 2016
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column param_setting.update_time
     *
     * @param updateTime the value for param_setting.update_time
     *
     * @mbggenerated Wed Dec 07 13:28:36 CST 2016
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column param_setting.content
     *
     * @return the value of param_setting.content
     *
     * @mbggenerated Wed Dec 07 13:28:36 CST 2016
     */
    public String getContent() {
        return content;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column param_setting.content
     *
     * @param content the value for param_setting.content
     *
     * @mbggenerated Wed Dec 07 13:28:36 CST 2016
     */
    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }
}