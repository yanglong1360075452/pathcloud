package com.lifetech.dnap.pathcloud.reagent.application.dto;

import java.util.Date;

/**
 * Created by HP on 2017/9/26.
 */
public class ReagentDto {

    private Long id;

    private String name;

    private Integer category;
    private String categoryDesc;

    private Integer type;
    private String typeDesc;

    private String cloneNumber;

    private Integer preProcess;
    private String preProcessDesc;

    private String positivePosition;

    private String diagnose;

    private Long createBy;

    private Long updateBy;

    private Date createTime;

    private Date updateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getCategoryDesc() {
        return categoryDesc;
    }

    public void setCategoryDesc(String categoryDesc) {
        this.categoryDesc = categoryDesc;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getTypeDesc() {
        return typeDesc;
    }

    public void setTypeDesc(String typeDesc) {
        this.typeDesc = typeDesc;
    }

    public String getCloneNumber() {
        return cloneNumber;
    }

    public void setCloneNumber(String cloneNumber) {
        this.cloneNumber = cloneNumber;
    }

    public Integer getPreProcess() {
        return preProcess;
    }

    public void setPreProcess(Integer preProcess) {
        this.preProcess = preProcess;
    }

    public String getPreProcessDesc() {
        return preProcessDesc;
    }

    public void setPreProcessDesc(String preProcessDesc) {
        this.preProcessDesc = preProcessDesc;
    }

    public String getPositivePosition() {
        return positivePosition;
    }

    public void setPositivePosition(String positivePosition) {
        this.positivePosition = positivePosition;
    }

    public String getDiagnose() {
        return diagnose;
    }

    public void setDiagnose(String diagnose) {
        this.diagnose = diagnose;
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
