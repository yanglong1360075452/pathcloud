package com.lifetech.dhap.pathcloud.user.application.dto;

import java.util.Date;
import java.util.List;

/**
 * Created by LuoMo on 2016-11-09.
 */
public class RoleDto {

    private Long id;

    private String name;

    private String description;

    //查询返回权限数据
    private List<PermissionDto> permissions;

    //接收权限ID
    private List<Integer> permissionsId;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<PermissionDto> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<PermissionDto> permissions) {
        this.permissions = permissions;
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

    public List<Integer> getPermissionsId() {
        return permissionsId;
    }

    public void setPermissionsId(List<Integer> permissionsId) {
        this.permissionsId = permissionsId;
    }
}
