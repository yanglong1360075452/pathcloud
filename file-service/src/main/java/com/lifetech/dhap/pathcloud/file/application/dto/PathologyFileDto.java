package com.lifetech.dhap.pathcloud.file.application.dto;

import java.util.Date;

/**
 * @author eric.guo@ostengar.com
 * @version 1.0
 * @time 2016-12-22-13:21
 */
public class PathologyFileDto {

    private Long id;

    private Long pathologyId;

    private Integer operation;

    private Integer type;

    private String content;

    private Boolean keepFlag;

    private Long createBy;

    private Long updateBy;

    private Date createTime;

    private Date updateTime;

    private String tag;

    private Boolean check;

    public Boolean getCheck() {
        return check;
    }

    public void setCheck(Boolean check) {
        this.check = check;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPathologyId() {
        return pathologyId;
    }

    public void setPathologyId(Long pathologyId) {
        this.pathologyId = pathologyId;
    }

    public Integer getOperation() {
        return operation;
    }

    public void setOperation(Integer operation) {
        this.operation = operation;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Boolean getKeepFlag() {
        return keepFlag;
    }

    public void setKeepFlag(Boolean keepFlag) {
        this.keepFlag = keepFlag;
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
