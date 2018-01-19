package com.lifetech.dhap.pathcloud.user.api.impl;

import com.lifetech.dhap.pathcloud.common.exception.BuzException;
import com.lifetech.dhap.pathcloud.common.exception.BuzExceptionCode;
import com.lifetech.dhap.pathcloud.common.response.PageDataVO;
import com.lifetech.dhap.pathcloud.common.response.ResponseVO;
import com.lifetech.dhap.pathcloud.security.UserContext;
import com.lifetech.dhap.pathcloud.user.api.RoleApi;
import com.lifetech.dhap.pathcloud.user.api.request.RoleSettingReq;
import com.lifetech.dhap.pathcloud.user.api.vo.RoleVO;
import com.lifetech.dhap.pathcloud.user.application.RoleApplication;
import com.lifetech.dhap.pathcloud.user.application.UserApplication;
import com.lifetech.dhap.pathcloud.user.application.condition.RoleCondition;
import com.lifetech.dhap.pathcloud.user.application.dto.RoleDto;
import com.lifetech.dhap.pathcloud.user.application.dto.UserDto;
import com.lifetech.dhap.pathcloud.user.infrastructure.code.RoleSortByEnum;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.DefaultValue;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by LuoMo on 2016-11-09.
 */
@Component("roleApi")
public class RoleApiImpl implements RoleApi {

    @Autowired
    private RoleApplication roleApplication;

    @Autowired
    private UserApplication userApplication;

    @Override
    public ResponseVO createRole(RoleVO roleVO) throws BuzException {
        String name = roleVO.getName();
        if (name == null || "".equals(name)) {
            throw new BuzException(BuzExceptionCode.ErrorParam);
        }
        if(roleApplication.getRoleByName(name) != null){
            throw new BuzException(BuzExceptionCode.RoleNameExists);
        }
        RoleDto roleDto = new RoleDto();
        BeanUtils.copyProperties(roleVO, roleDto);
        roleDto.setCreateBy(UserContext.getLoginUserID());
        RoleDto roleDto1 = roleApplication.createRole(roleDto);
        return new ResponseVO(RoleDtoToVO(roleDto1));
    }

    @Override
    public ResponseVO updateRole(Long id,RoleVO roleVO) throws BuzException {
        if (id == null) {
            throw new BuzException(BuzExceptionCode.ErrorParam);
        }
        if(id == 1){
            throw new BuzException(BuzExceptionCode.RoleCannotOperate);
        }
        if(roleApplication.getRole(id) == null){
            throw new BuzException(BuzExceptionCode.RoleNotExists);
        }
        String roleName = roleVO.getName();
        if(roleName != null && !"".equals(roleName)){
            RoleDto roleDto = roleApplication.getRoleByName(roleName);
            if(roleDto != null && !roleDto.getId().equals(roleVO.getId())){
                throw new BuzException(BuzExceptionCode.RoleNameExists);
            }
        }
        RoleDto roleDto = new RoleDto();
        BeanUtils.copyProperties(roleVO,roleDto);
        roleDto.setUpdateBy(UserContext.getLoginUserID());
        RoleDto roleDto1 = roleApplication.updateRole(roleDto);
        return new ResponseVO(RoleDtoToVO(roleDto1));
    }

    @Override
    public ResponseVO deleteRole(Long id) throws BuzException {
        if (id == null) {
            throw new BuzException(BuzExceptionCode.ErrorParam);
        }
        if(id == 1){
            throw new BuzException(BuzExceptionCode.RoleCannotOperate);
        }
        if(roleApplication.getRole(id) == null){
            throw new BuzException(BuzExceptionCode.RoleNotExists);
        }
        roleApplication.deleteRole(id);
        return new ResponseVO();
    }

    @Override
    public ResponseVO getRoles(@DefaultValue("1") int page, @DefaultValue("10") int length, Integer order, String sort) throws BuzException {
        RoleCondition roleCondition = new RoleCondition();
        roleCondition.setSize(length);
        roleCondition.setStart((page - 1) * length);

        if(order != null){
            roleCondition.setOrder(RoleSortByEnum.valueOf(order).toString());
            if(sort != null) {
                roleCondition.setOrder(roleCondition.getOrder() + " " + sort);
            }
        }

        List<RoleDto> roleDtos = roleApplication.getRoles(roleCondition);
        List<RoleVO> roleVOs = new ArrayList<>();
        RoleVO roleVO = null;
        for (RoleDto roleDto : roleDtos) {
            roleVO = RoleDtoToVO(roleDto);
            roleVOs.add(roleVO);
        }
        Long total = roleApplication.getRoleTotalByCondition(roleCondition);
        return new PageDataVO(page, length, total, roleVOs);
    }

    @Override
    public ResponseVO getRole(Long id) throws BuzException {
        if (id == null) {
            throw new BuzException(BuzExceptionCode.ErrorParam);
        }
        RoleDto roleDto = roleApplication.getRole(id);
        if (roleDto == null) {
            throw new BuzException(BuzExceptionCode.RoleNotExists);
        }
        return new ResponseVO(RoleDtoToVO(roleDto));
    }

    @Override
    public ResponseVO roleSetting(RoleSettingReq roleSettingReq) throws BuzException {
        List<Long> roleDelete = roleSettingReq.getRoleDelete();
        List<RoleVO> roleAdd = roleSettingReq.getRoleAdd();
        List<RoleVO> roleUpdate = roleSettingReq.getRoleUpdate();
        if (roleDelete == null && roleAdd == null && roleUpdate == null) {
            throw new BuzException(BuzExceptionCode.ErrorParam);
        }
        if(roleAdd != null && roleAdd.size() > 0){
            for(RoleVO roleVO : roleAdd){
                if(roleVO != null){
                    roleVO.setCreateBy(UserContext.getLoginUserID());
                }
            }
        }
        if(roleUpdate != null && roleUpdate.size() > 0){
            for(RoleVO roleVO : roleUpdate){
                if(roleVO != null){
                    roleVO.setUpdateBy(UserContext.getLoginUserID());
                }
            }
        }
        Map<String, List> data = new HashedMap();
        data.put("roleDelete", roleDelete);
        data.put("roleAdd", roleAdd);
        data.put("roleUpdate", roleUpdate);
        roleApplication.roleSetting(data);
        return new ResponseVO();
    }

    @Override
    public ResponseVO checkRoleName(String roleName) throws BuzException {
        if (roleName == null || "".equals(roleName)) {
            throw new BuzException(BuzExceptionCode.ErrorParam);
        }
        RoleDto roleDto = roleApplication.getRoleByName(roleName);
        if (roleDto == null) {
            return new ResponseVO();
        } else {
            return new ResponseVO(BuzExceptionCode.RoleNameExists, "角色名已存在");
        }
    }

    private RoleVO RoleDtoToVO(RoleDto dto) {
        if (dto == null || dto.getId() == null || dto.getName() == null || "".equals(dto.getName())) {
            throw new BuzException(BuzExceptionCode.ErrorParam);
        }
        RoleVO roleVO = new RoleVO();
        BeanUtils.copyProperties(dto, roleVO);
        if (dto.getCreateBy() != null) {
            UserDto userDto = userApplication.getUserByUserID(dto.getCreateBy());
            if (userDto != null) {
                roleVO.setCreateByName(userDto.getUserName());
            }
        }
        if (dto.getUpdateBy() != null) {
            UserDto userDto = userApplication.getUserByUserID(dto.getUpdateBy());
            if (userDto != null) {
                roleVO.setUpdateByName(userDto.getUserName());
            }
        }
        return roleVO;
    }
}
