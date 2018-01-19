package com.lifetech.dhap.pathcloud.application.application.dto;

import java.util.Date;
import java.util.List;

/**
 * Created by LiuMei on 2017-04-01.
 */
public class IhcApplicationDto {

    private Long id;

    private String applyTel;

    private String applyUser;

    private Integer departments;
    private String departmentsDesc;

    private List<IhcBlockDto> ihcBlocks;

    private Integer source; //用来标识调用入口

    private Long updateBy;

    private Date updateTime;

    public Long getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(Long updateBy) {
        this.updateBy = updateBy;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getSource() {
        return source;
    }

    public void setSource(Integer source) {
        this.source = source;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getApplyTel() {
        return applyTel;
    }

    public void setApplyTel(String applyTel) {
        this.applyTel = applyTel;
    }

    public String getApplyUser() {
        return applyUser;
    }

    public void setApplyUser(String applyUser) {
        this.applyUser = applyUser;
    }

    public Integer getDepartments() {
        return departments;
    }

    public void setDepartments(Integer departments) {
        this.departments = departments;
    }

    public String getDepartmentsDesc() {
        return departmentsDesc;
    }

    public void setDepartmentsDesc(String departmentsDesc) {
        this.departmentsDesc = departmentsDesc;
    }

    public List<IhcBlockDto> getIhcBlocks() {
        return ihcBlocks;
    }

    public void setIhcBlocks(List<IhcBlockDto> ihcBlocks) {
        this.ihcBlocks = ihcBlocks;
    }
}
