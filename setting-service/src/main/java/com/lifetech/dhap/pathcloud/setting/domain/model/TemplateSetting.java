package com.lifetech.dhap.pathcloud.setting.domain.model;

import java.io.Serializable;
import java.util.Date;

public class TemplateSetting implements Serializable {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column template_setting.template_id
     *
     * @mbggenerated Mon Dec 12 13:54:41 CST 2016
     */
    private Integer templateId;

    private Integer position;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column template_setting.operator_id
     *
     * @mbggenerated Mon Dec 12 13:54:41 CST 2016
     */
    private Long operatorId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column template_setting.create_time
     *
     * @mbggenerated Mon Dec 12 13:54:41 CST 2016
     */
    private Date createTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table template_setting
     *
     * @mbggenerated Mon Dec 12 13:54:41 CST 2016
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column template_setting.template_id
     *
     * @return the value of template_setting.template_id
     *
     * @mbggenerated Mon Dec 12 13:54:41 CST 2016
     */
    public Integer getTemplateId() {
        return templateId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column template_setting.template_id
     *
     * @param templateId the value for template_setting.template_id
     *
     * @mbggenerated Mon Dec 12 13:54:41 CST 2016
     */
    public void setTemplateId(Integer templateId) {
        this.templateId = templateId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column template_setting.operator_id
     *
     * @return the value of template_setting.operator_id
     *
     * @mbggenerated Mon Dec 12 13:54:41 CST 2016
     */
    public Long getOperatorId() {
        return operatorId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column template_setting.operator_id
     *
     * @param operatorId the value for template_setting.operator_id
     *
     * @mbggenerated Mon Dec 12 13:54:41 CST 2016
     */
    public void setOperatorId(Long operatorId) {
        this.operatorId = operatorId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column template_setting.create_time
     *
     * @return the value of template_setting.create_time
     *
     * @mbggenerated Mon Dec 12 13:54:41 CST 2016
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column template_setting.create_time
     *
     * @param createTime the value for template_setting.create_time
     *
     * @mbggenerated Mon Dec 12 13:54:41 CST 2016
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }
}