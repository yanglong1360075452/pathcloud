package com.lifetech.dhap.pathcloud.user.application;

import com.lifetech.dhap.pathcloud.common.exception.BuzException;
import com.lifetech.dhap.pathcloud.user.application.dto.PermissionDto;

import java.util.List;

/**
 * Created by LuoMo on 2016-11-09.
 */
public interface PermissionApplication {

    /**
     * 获取权限列表
     * @return
     * @throws BuzException
     */
    List<PermissionDto> getPermissions() throws BuzException;


    /**
     * 根据code获取权限
     * @param code
     * @return
     * @throws BuzException
     */
    PermissionDto getByCode(Integer code) throws BuzException;
}
