package com.lifetech.dhap.pathcloud.application.application.dto;

import com.lifetech.dhap.pathcloud.user.application.dto.UserSimpleDto;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by LuoMo on 2016-11-22.
 */
public class SampleDto implements Serializable{
    private Long id;

    private Long applicationId;

    private String serialNumber;

    private String name;

    private Integer category;

    private String categoryName;

    private UserSimpleDto createBy;

    private UserSimpleDto updateBy;

    private Date createTime;

    private Date updateTime;

    private UserSimpleDto registerUser;

    private Date registerTime;

    private Boolean delete;

    public Boolean getDelete() {
        return delete;
    }

    public void setDelete(Boolean delete) {
        this.delete = delete;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(Long applicationId) {
        this.applicationId = applicationId;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
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

    public UserSimpleDto getCreateBy() {
        return createBy;
    }

    public void setCreateBy(UserSimpleDto createBy) {
        this.createBy = createBy;
    }

    public UserSimpleDto getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(UserSimpleDto updateBy) {
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

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public UserSimpleDto getRegisterUser() {
        return registerUser;
    }

    public void setRegisterUser(UserSimpleDto registerUser) {
        this.registerUser = registerUser;
    }

    public Date getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(Date registerTime) {
        this.registerTime = registerTime;
    }
}
