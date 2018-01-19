package com.lifetech.dhap.pathcloud.setting.application.dto;

import java.util.Date;

/**
 * Created by HP on 2016/12/7.
 */
public class TemplateDto {
    private Integer id;

    private String name;

    private Integer category;

    private Integer parent;

    private Integer level;

    private String position;

    private Integer displayOrder;

    private Long createBy;

    private Long updateBy;

    private Date createTime;

    private Date updateTime;

    private String content;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public Integer getParent() {
        return parent;
    }


    public void setParent(Integer parent) {
        this.parent = parent;
    }


    public Integer getLevel() {
        return level;
    }


    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getPosition() {
        return position;
    }


    public void setPosition(String position) {
        this.position = position == null ? null : position.trim();
    }


    public Integer getDisplayOrder() {
        return displayOrder;
    }


    public void setDisplayOrder(Integer displayOrder) {
        this.displayOrder = displayOrder;
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


    public String getContent() {
        return content;
    }


    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }
}
