package com.lifetech.dhap.pathcloud.user.application.condition;


import com.lifetech.dhap.pathcloud.common.base.PageCondition;

import java.util.List;

/**
 * Created by gdw on 4/18/16.
 */
public class UserCondition extends PageCondition {
    private Long roleId;

    private Integer permissionId;

    private Boolean status;

    private String filter;

    private List<Integer> permissionIds;

    public String getFilter() {
        return filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Integer getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(Integer permissionId) {
        this.permissionId = permissionId;
    }

    public List<Integer> getPermissionIds() {
        return permissionIds;
    }

    public void setPermissionIds(List<Integer> permissionIds) {
        this.permissionIds = permissionIds;
    }
}
