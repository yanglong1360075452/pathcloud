package com.lifetech.dhap.pathcloud.user.application;

import com.lifetech.dhap.pathcloud.common.exception.BuzException;
import com.lifetech.dhap.pathcloud.user.application.condition.UserCondition;
import com.lifetech.dhap.pathcloud.user.application.dto.UserDto;
import com.lifetech.dhap.pathcloud.user.application.dto.UserSimpleDto;

import java.util.List;

/**
 * Created by gdw on 4/15/16.
 */
public interface UserApplication {
    /**
     * 条件获取用户信息
     * @param con
     * @return
     */
    List<UserDto> getUserByCondition(UserCondition con) throws BuzException;

    /**
     * 条件获取用户数量
     * @param con
     * @return
     */
    Long getUserTotalByCondition(UserCondition con) throws BuzException;

    /**
     * 新建用户
     * @param user
     * @return
     * @throws BuzException
     */
    UserDto createUser(UserDto user) throws BuzException;

    /**
     * 更新用户信息
     * @param user
     * @return
     * @throws BuzException
     */
    UserDto updateUser(UserDto user) throws BuzException;

    /**
     * 获取用户信息
     * @param uid
     * @return
     * @throws BuzException
     */
    UserDto getUserByUserID(Long uid) throws BuzException;

    /**
     * 更新用户登录时间和IP
     * @param user
     * @return
     */
    int updateUserLoginTime(UserDto user);

    /**
     * 按用户名获取用户信息
     * @param username
     * @return
     */
    UserDto getUserByUsername(String username);

    /**
     * 密码重置
     * @param username
     * @param password
     * @return
     */
    int resetPassword(String username, String password);

    /**
     * 获取用户权限码
     * @param userId
     * @return
     */
    List<Integer> getUserPermissionsCode(Long userId);

    /**
     * 获取用户简单信息
     * @param userId
     * @return
     */
    UserSimpleDto getUserSimpleInfoById(Long userId);

    /**
     * 获取发过报告的医生列表
     * @return
     */
    List<UserSimpleDto> getSentReportUser();

    /**
     * 模糊查询姓名
     * @param firstName
     * @return
     */
    List<Long> getUserIdByFirstName(String firstName);

}
