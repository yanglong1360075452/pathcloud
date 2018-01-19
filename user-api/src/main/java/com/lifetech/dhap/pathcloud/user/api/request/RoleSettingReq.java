package com.lifetech.dhap.pathcloud.user.api.request;

import com.lifetech.dhap.pathcloud.user.api.vo.RoleVO;

import java.io.Serializable;
import java.util.List;

/**
 * Created by LuoMo on 2016-11-10.
 */
public class RoleSettingReq implements Serializable{

    private List<Long> roleDelete;

    private List<RoleVO> roleAdd;
    
    private List<RoleVO> roleUpdate;

    public List<Long> getRoleDelete() {
        return roleDelete;
    }

    public void setRoleDelete(List<Long> roleDelete) {
        this.roleDelete = roleDelete;
    }

    public List<RoleVO> getRoleAdd() {
        return roleAdd;
    }

    public void setRoleAdd(List<RoleVO> roleAdd) {
        this.roleAdd = roleAdd;
    }

    public List<RoleVO> getRoleUpdate() {
        return roleUpdate;
    }

    public void setRoleUpdate(List<RoleVO> roleUpdate) {
        this.roleUpdate = roleUpdate;
    }
}
