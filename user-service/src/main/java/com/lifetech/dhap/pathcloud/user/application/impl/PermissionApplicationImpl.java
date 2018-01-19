package com.lifetech.dhap.pathcloud.user.application.impl;

import com.lifetech.dhap.pathcloud.common.exception.BuzException;
import com.lifetech.dhap.pathcloud.common.exception.BuzExceptionCode;
import com.lifetech.dhap.pathcloud.user.application.PermissionApplication;
import com.lifetech.dhap.pathcloud.user.application.dto.PermissionDto;
import com.lifetech.dhap.pathcloud.user.domain.PermissionRepository;
import com.lifetech.dhap.pathcloud.user.domain.model.Permission;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LuoMo on 2016-11-09.
 */
@Service
public class PermissionApplicationImpl implements PermissionApplication {

    @Autowired
    private PermissionRepository permissionRepository;

    @Override
    public List<PermissionDto> getPermissions() throws BuzException {
        List<Permission> permissions = permissionRepository.selectAll();
        List<PermissionDto> permissionDtos = new ArrayList<>();
        PermissionDto permissionDto;
        for (int i = 0; i < permissions.size(); i++) {
            permissionDto = new PermissionDto();
            BeanUtils.copyProperties(permissions.get(i), permissionDto);
            permissionDtos.add(permissionDto);
        }
        return permissionDtos;
    }

    @Override
    public PermissionDto getByCode(Integer code) throws BuzException {
        if (code == null) {
            throw new BuzException(BuzExceptionCode.ErrorParam);
        }
        Permission permission = permissionRepository.selectByCode(code);
        PermissionDto permissionDto = null;
        if (permission != null) {
            permissionDto = new PermissionDto();
            BeanUtils.copyProperties(permission, permissionDto);
        }
        return permissionDto;
    }
}
