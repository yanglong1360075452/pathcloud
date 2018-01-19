package com.lifetech.dhap.pathcloud.user.application.impl;

import com.lifetech.dhap.pathcloud.common.data.OrderByEnum;
import com.lifetech.dhap.pathcloud.common.exception.BuzException;
import com.lifetech.dhap.pathcloud.common.exception.BuzExceptionCode;
import com.lifetech.dhap.pathcloud.security.UserContext;
import com.lifetech.dhap.pathcloud.user.api.vo.RoleVO;
import com.lifetech.dhap.pathcloud.user.application.RoleApplication;
import com.lifetech.dhap.pathcloud.user.application.condition.RoleCondition;
import com.lifetech.dhap.pathcloud.user.application.dto.PermissionDto;
import com.lifetech.dhap.pathcloud.user.application.dto.RoleDto;
import com.lifetech.dhap.pathcloud.user.domain.PermissionRepository;
import com.lifetech.dhap.pathcloud.user.domain.RoleRepository;
import com.lifetech.dhap.pathcloud.user.domain.model.Permission;
import com.lifetech.dhap.pathcloud.user.domain.model.Role;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by LuoMo on 2016-11-09.
 */
@Service
public class RoleApplicationImpl implements RoleApplication {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PermissionRepository permissionRepository;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public RoleDto createRole(RoleDto roleDto) throws BuzException {
        Role rolePO = new Role();
        BeanUtils.copyProperties(roleDto, rolePO);
        if (roleRepository.insert(rolePO) == 0) {
            throw new BuzException(BuzExceptionCode.RoleNameExists);
        }
        roleDto.setId(roleRepository.last());
        List<Integer> ids = roleDto.getPermissionsId();
        if (ids != null && ids.size() > 0) {
            for (int id : ids) {
                roleRepository.insertMapping(roleDto.getId(), id);
            }
        }
        List<Permission> permissions = permissionRepository.selectByRoleId(roleDto.getId());
        if (permissions != null && permissions.size() > 0) {
            roleDto.setPermissions(permissionsToDto(permissions));
        }
        return roleDto;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public RoleDto updateRole(RoleDto roleDto) throws BuzException {
        if (roleDto.getId() == null) {
            throw new BuzException(BuzExceptionCode.ErrorParam);
        }
        Role rolePO = roleRepository.selectByPrimaryKey(roleDto.getId());
        if (rolePO != null) {
            if (roleDto.getName() != null) {
                rolePO.setName(roleDto.getName());
            }
            if (roleDto.getDescription() != null) {
                rolePO.setDescription(roleDto.getDescription());
            }
            List<Integer> permissionDtos = roleDto.getPermissionsId();
            roleRepository.deleteMapping(roleDto.getId(), null);
            if (permissionDtos != null && permissionDtos.size() > 0) {
                for (int i : permissionDtos) {
                    roleRepository.insertMapping(roleDto.getId(), i);
                }
            }
            rolePO.setUpdateBy(roleDto.getUpdateBy());
            rolePO.setUpdateTime(roleDto.getUpdateTime());
            roleRepository.updateByPrimaryKey(rolePO);
        }
        BeanUtils.copyProperties(rolePO, roleDto);
        List<Permission> permissions = permissionRepository.selectByRoleId(roleDto.getId());
        if (permissions != null && permissions.size() > 0) {
            roleDto.setPermissions(permissionsToDto(permissions));
        }
        return roleDto;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteRole(Long id) throws BuzException {
        roleRepository.deleteByPrimaryKey(id);
        roleRepository.deleteMapping(id, null);
    }

    @Override
    public RoleDto getRole(Long id) throws BuzException {
        Role role = roleRepository.selectByPrimaryKey(id);
        RoleDto roleDto = null;
        if (role != null) {
            roleDto = new RoleDto();
            BeanUtils.copyProperties(role, roleDto);
            List<Permission> permissions = permissionRepository.selectByRoleId(id);
            if (permissions != null && permissions.size() > 0) {
                roleDto.setPermissions(permissionsToDto(permissions));
            }
        }
        return roleDto;
    }

    @Override
    public List<RoleDto> getRoles(RoleCondition condition) throws BuzException {

        if (condition.getOrder() == null) {
            condition.setOrder("id " + OrderByEnum.Desc.toString());
        }
        List<RoleDto> roleDtos = new ArrayList<>();
        List<Role> roles = roleRepository.selectByCondition(condition);
        RoleDto roleDto;
        for (Role role : roles) {
            roleDto = new RoleDto();
            BeanUtils.copyProperties(role, roleDto);
            List<Permission> permissions = permissionRepository.selectByRoleId(role.getId());
            if (permissions != null && permissions.size() > 0) {
                roleDto.setPermissions(permissionsToDto(permissions));
            }
            roleDtos.add(roleDto);
        }
        return roleDtos;
    }

    @Override
    public Long getRoleTotalByCondition(RoleCondition condition) throws BuzException {
        return roleRepository.countByCondition(condition);
    }

    @Override
    public RoleDto getRoleByName(String name) throws BuzException {
        Role role = roleRepository.selectByName(name);
        RoleDto roleDto = null;
        if (role != null) {
            roleDto = new RoleDto();
            BeanUtils.copyProperties(role, roleDto);
        }
        return roleDto;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void roleSetting(Map<String, List> data) throws BuzException {

        List<Long> roleDelete = data.get("roleDelete");
        List<RoleVO> roleAdd = data.get("roleAdd");
        List<RoleVO> roleUpdate = data.get("roleUpdate");

        if (roleDelete != null && roleDelete.size() > 0) {
            for (Long id : roleDelete) {
                deleteRole(id);
            }
        }
        if (roleAdd != null && roleAdd.size() > 0) {
            RoleDto roleDto = null;
            for (RoleVO roleVO : roleAdd) {
                if (getRoleByName(roleVO.getName()) != null) {
                    throw new BuzException(BuzExceptionCode.RoleNameExists);
                }
                roleDto = new RoleDto();
                BeanUtils.copyProperties(roleVO, roleDto);
                roleDto.setCreateBy(UserContext.getUserDetails().getId());
                createRole(roleDto);
            }
        }
        if (roleUpdate != null && roleUpdate.size() > 0) {
            RoleDto roleDto = null;
            for (RoleVO roleVO : roleUpdate) {
                roleDto = new RoleDto();
                BeanUtils.copyProperties(roleVO, roleDto);
                updateRole(roleDto);
            }
        }
    }

    /**
     * permission传输实体转换
     *
     * @param permissions
     * @return
     */
    private List<PermissionDto> permissionsToDto(List<Permission> permissions) {
        List<PermissionDto> permissionDtos = null;
        if (permissions != null && permissions.size() > 0) {
            permissionDtos = new ArrayList<>();
            PermissionDto permissionDto = null;
            for (Permission p : permissions) {
                if (p != null) {
                    permissionDto = new PermissionDto();
                    BeanUtils.copyProperties(p, permissionDto);
                    permissionDtos.add(permissionDto);
                }
            }
        }
        return permissionDtos;
    }
}
