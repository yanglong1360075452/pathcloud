package com.lifetech.dhap.pathcloud.reagent.domain.model;

import java.io.Serializable;
import java.util.Date;

public class Reagent implements Serializable {
    private Long id;

    private String name;

    private Integer category;

    private Integer type;

    private String cloneNumber;

    private Integer preProcess;

    private String positivePosition;

    private String diagnose;

    private Long createBy;

    private Long updateBy;

    private Date createTime;

    private Date updateTime;

    private static final long serialVersionUID = 1L;

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
        this.name = name == null ? null : name.trim();
    }

    public Integer getCategory() {
        return category;
    }

    public void setCategory(Integer category) {
        this.category = category;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getCloneNumber() {
        return cloneNumber;
    }

    public void setCloneNumber(String cloneNumber) {
        this.cloneNumber = cloneNumber == null ? null : cloneNumber.trim();
    }

    public Integer getPreProcess() {
        return preProcess;
    }

    public void setPreProcess(Integer preProcess) {
        this.preProcess = preProcess;
    }

    public String getpositivePosition() {
        return positivePosition;
    }

    public void setpositivePosition(String positivePosition) {
        this.positivePosition = positivePosition == null ? null : positivePosition.trim();
    }

    public String getDiagnose() {
        return diagnose;
    }

    public void setDiagnose(String diagnose) {
        this.diagnose = diagnose == null ? null : diagnose.trim();
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