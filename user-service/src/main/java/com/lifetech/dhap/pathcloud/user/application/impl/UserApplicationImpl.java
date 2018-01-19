package com.lifetech.dhap.pathcloud.user.application.impl;

import com.lifetech.dhap.pathcloud.common.data.Identity;
import com.lifetech.dhap.pathcloud.common.data.OrderByEnum;
import com.lifetech.dhap.pathcloud.common.data.TaskType;
import com.lifetech.dhap.pathcloud.common.exception.BuzException;
import com.lifetech.dhap.pathcloud.common.exception.BuzExceptionCode;
import com.lifetech.dhap.pathcloud.common.utils.EncryptUtil;
import com.lifetech.dhap.pathcloud.user.application.UserApplication;
import com.lifetech.dhap.pathcloud.user.application.condition.UserCondition;
import com.lifetech.dhap.pathcloud.user.application.dto.UserDto;
import com.lifetech.dhap.pathcloud.user.application.dto.UserSimpleDto;
import com.lifetech.dhap.pathcloud.user.domain.RoleRepository;
import com.lifetech.dhap.pathcloud.user.domain.UserRepository;
import com.lifetech.dhap.pathcloud.user.domain.model.Role;
import com.lifetech.dhap.pathcloud.user.domain.model.User;
import com.lifetech.dhap.pathcloud.user.infrastructure.code.UserStatusEnum;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gdw on 4/15/16.
 */
@Service
public class UserApplicationImpl implements UserApplication {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public List<UserDto> getUserByCondition(UserCondition con) throws BuzException {
        if (con.getOrder() == null) {
            con.setOrder("u.id " + OrderByEnum.Desc.toString());
        }
        List<UserDto> users = new ArrayList<>();
        List<User> data = userRepository.selectByCondition(con);

        UserDto dto;
        for (User user : data) {
            dto = userToDto(user);
            users.add(dto);
        }
        return users;
    }

    @Override
    public Long getUserTotalByCondition(UserCondition con) throws BuzException {
        return userRepository.countByCondition(con);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public UserDto createUser(UserDto user) throws BuzException {
        User userPO = new User();
        BeanUtils.copyProperties(user, userPO);
        user.setLockStatus(false);
        userPO.setPassword(passwordEncoder.encode(user.getPassword()));
        userPO.setQrCode(EncryptUtil.AESEncrypt(user.getPassword(), userPO.getPassword()));
        if (userRepository.insert(userPO) == 0) {
            throw new BuzException(BuzExceptionCode.UsernameExists);
        }
        return userToDto(userRepository.selectByPrimaryKey(userRepository.last()));
    }

    @Override
    public UserDto updateUser(UserDto user) throws BuzException {
        if (user.getId() == null) {
            throw new BuzException(BuzExceptionCode.ErrorParam);
        }
        User userPO = userRepository.selectByPrimaryKey(user.getId());
        if (userPO != null) {
            BeanUtils.copyProperties(user, userPO);
            if (user.getPhone() != null) {
                userPO.setPhone(user.getPhone());
            }
            if (user.getStatus() != null) {
                userPO.setStatus(user.getStatus());
            }
            if (user.getUpdateBy() != null) {
                userPO.setUpdateBy(user.getUpdateBy());
            }
            if (user.getLockStatus() != null) {
                userPO.setLockStatus(user.getLockStatus());
            }
            if (user.getFirstName() != null) {
                userPO.setFirstName(user.getFirstName());
            }
            if (user.getRoleId() != null) {
                userPO.setRoleId(user.getRoleId());
            }
            if (user.getPassword() != null) {
                userPO.setPassword(passwordEncoder.encode(user.getPassword()));
                userPO.setQrCode(EncryptUtil.AESEncrypt(user.getPassword(), userPO.getPassword()));
            }
            Integer department = user.getDepartments();
            if (department != null) {
                userPO.setDepartments(department);
            }
            Boolean lockStatus = user.getLockStatus();
            if (lockStatus != null) {
                userPO.setLockStatus(lockStatus);
            }
            userPO.setLockCount(user.getLockCount());
            userPO.setLockCountTime(user.getLockCountTime());
            userRepository.updateByPrimaryKey(userPO);

            return getUserByUserID(user.getId());
        }

        return user;
    }

    @Override
    public UserDto getUserByUserID(Long uid) throws BuzException {
        User user = userRepository.selectByPrimaryKey(uid);
        if (user != null) {
            UserDto dto = userToDto(user);
            return dto;
        } else {
            return null;
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateUserLoginTime(UserDto user) {
        return userRepository.updateLoginInfo(user.getId(), user.getLastLoginIp());
    }

    @Override
    public UserDto getUserByUsername(String username) {
        User user = userRepository.selectByUsername(username);
        if (user != null) {
            UserDto dto = userToDto(user);
            return dto;
        } else {
            return null;
        }
    }

    private UserDto userToDto(User user) {
        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(user, userDto);
        userDto.setStatusName(UserStatusEnum.valueOf(user.getStatus()).toString());
        if (user.getCreateBy() != null) {
            User u = userRepository.selectByPrimaryKey(user.getCreateBy());
            if (u != null) {
                userDto.setCreateByName(u.getUserName());
            }
        }
        if (user.getUpdateBy() != null) {
            User u = userRepository.selectByPrimaryKey(user.getUpdateBy());
            if (u != null) {
                userDto.setUpdateByName(u.getUserName());
            }
        }
        if (user.getRoleId() != null) {
            Role role = roleRepository.selectByPrimaryKey(user.getRoleId());
            if (role != null) {
                userDto.setRoleName(role.getName());
            } else {
                userDto.setRoleId(null);
            }
        }
        Integer identity = user.getIdentity();
        if (identity != null) {
            userDto.setIdentityDesc(Identity.getNameByCode(identity));
        }
        Integer taskType = user.getTaskType();
        if (taskType != null) {
            userDto.setTaskTypeDesc(TaskType.getNameByCode(taskType));
        }
        return userDto;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int resetPassword(String username, String password) {
        User user = userRepository.selectByUsername(username);
        if (user == null) {
            throw new BuzException(BuzExceptionCode.UserNotExists);
        }
        if (user.getLockStatus()) {
            user.setLockStatus(false);
            userRepository.updateByPrimaryKey(user);
        }
        String encryptPassword = passwordEncoder.encode(password);
        return userRepository.resetPassword(user.getId(), encryptPassword, EncryptUtil.AESEncrypt(password, encryptPassword));
    }

    @Override
    public List<Integer> getUserPermissionsCode(Long userId) {
        return userRepository.selectUserPermissionsCode(userId);
    }

    @Override
    public UserSimpleDto getUserSimpleInfoById(Long userId) {
        User user = userRepository.selectByPrimaryKey(userId);
        UserSimpleDto userSimpleDto = new UserSimpleDto();
        if (user != null) {
            BeanUtils.copyProperties(user, userSimpleDto);
        }
        return userSimpleDto;
    }

    @Override
    public List<UserSimpleDto> getSentReportUser() {
        List<Long> ids = userRepository.selectSentReport();
        List<UserSimpleDto> userSimpleDtos = new ArrayList<>();
        for (Long id : ids) {
            userSimpleDtos.add(getUserSimpleInfoById(id));
        }
        return userSimpleDtos;
    }

    @Override
    public List<Long> getUserIdByFirstName(String firstName) {
        return userRepository.selectIdByFirstName(firstName);
    }
}
