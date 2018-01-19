package com.lifetech.dhap.pathcloud.user.api.impl;

import com.lifetech.dhap.pathcloud.common.exception.BuzException;
import com.lifetech.dhap.pathcloud.common.response.ResponseVO;
import com.lifetech.dhap.pathcloud.user.api.PermissionApi;
import com.lifetech.dhap.pathcloud.user.api.vo.PermissionVO;
import com.lifetech.dhap.pathcloud.user.application.PermissionApplication;
import com.lifetech.dhap.pathcloud.user.application.dto.PermissionDto;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LuoMo on 2016-11-09.
 */
@Component("permissionApi")
public class PermissionApiImpl implements PermissionApi{

    @Autowired
    private PermissionApplication permissionApplication;

    @Override
    public ResponseVO getPermissions() throws BuzException {
        List<PermissionDto> permissionDtos = permissionApplication.getPermissions();
        List<PermissionVO> permissionVOs = new ArrayList<>();
        PermissionVO permissionVO = null;
        for(int i=0;i<permissionDtos.size();i++){
            permissionVO = new PermissionVO();
            BeanUtils.copyProperties(permissionDtos.get(i),permissionVO);
            permissionVOs.add(permissionVO);
        }
        return new ResponseVO(permissionVOs);
    }
}
