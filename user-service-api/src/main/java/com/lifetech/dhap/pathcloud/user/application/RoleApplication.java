package com.lifetech.dhap.pathcloud.user.application;

import com.lifetech.dhap.pathcloud.common.exception.BuzException;
import com.lifetech.dhap.pathcloud.user.application.condition.RoleCondition;
import com.lifetech.dhap.pathcloud.user.application.dto.RoleDto;

import java.util.List;
import java.util.Map;

/**
 * Created by LuoMo on 2016-11-09.
 */
public interface RoleApplication {

    /**
     * 创建角色
     * @param roleDto
     * @return
     * @throws BuzException
     */
    RoleDto createRole(RoleDto roleDto) throws BuzException;

    /**
     * 修改角色
     * @param roleDto
     * @return
     * @throws BuzException
     */
    RoleDto updateRole(RoleDto roleDto) throws BuzException;

    /**
     * 删除角色
     * @param id
     * @throws BuzException
     */
    void deleteRole(Long id) throws BuzException;

    /**
     * 获取角色信息
     * @param id
     * @return
     * @throws BuzException
     */
    RoleDto getRole(Long id) throws BuzException;

    /**
     * 根据条件获取角色列表
     * @param condition
     * @return
     * @throws BuzException
     */
    List<RoleDto> getRoles(RoleCondition condition) throws BuzException;

    /**
     * 根据条件获取总记录数
     * @param condition
     * @return
     * @throws BuzException
     */
    Long getRoleTotalByCondition(RoleCondition condition) throws BuzException;

    /**
     * 根据角色名查询角色
     * @param name
     * @return
     * @throws BuzException
     */
    RoleDto getRoleByName(String name) throws  BuzException;

    /**
     * 角色设置
     * 新建角色/删除角色/角色权限更改
     * @param data
     * @throws BuzException
     */
    void roleSetting(Map<String,List> data) throws BuzException;
}
