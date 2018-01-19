package com.lifetech.dhap.pathcloud.user.api.impl;

import com.lifetech.dhap.pathcloud.common.exception.BuzException;
import com.lifetech.dhap.pathcloud.common.exception.BuzExceptionCode;
import com.lifetech.dhap.pathcloud.common.response.PageDataVO;
import com.lifetech.dhap.pathcloud.common.response.ResponseVO;
import com.lifetech.dhap.pathcloud.common.utils.EncryptUtil;
import com.lifetech.dhap.pathcloud.common.utils.StringUtil;
import com.lifetech.dhap.pathcloud.common.utils.ValidUtil;
import com.lifetech.dhap.pathcloud.security.UserContext;
import com.lifetech.dhap.pathcloud.user.api.UserApi;
import com.lifetech.dhap.pathcloud.user.api.request.UserCreateReq;
import com.lifetech.dhap.pathcloud.user.api.request.UserUpdateReq;
import com.lifetech.dhap.pathcloud.user.api.vo.PermissionVO;
import com.lifetech.dhap.pathcloud.user.api.vo.UserSimpleVO;
import com.lifetech.dhap.pathcloud.user.api.vo.UserVO;
import com.lifetech.dhap.pathcloud.user.application.PermissionApplication;
import com.lifetech.dhap.pathcloud.user.application.RoleApplication;
import com.lifetech.dhap.pathcloud.user.application.UserApplication;
import com.lifetech.dhap.pathcloud.user.application.condition.UserCondition;
import com.lifetech.dhap.pathcloud.user.application.dto.PermissionDto;
import com.lifetech.dhap.pathcloud.user.application.dto.RoleDto;
import com.lifetech.dhap.pathcloud.user.application.dto.UserDto;
import com.lifetech.dhap.pathcloud.common.data.Permission;
import com.lifetech.dhap.pathcloud.user.infrastructure.code.UserSortByEnum;
import com.lifetech.dhap.pathcloud.user.infrastructure.code.UserStatusEnum;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.ws.rs.DefaultValue;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component("userApi")
public class UserApiImpl implements UserApi {
    private static org.slf4j.Logger log = LoggerFactory.getLogger(UserApiImpl.class);

    @Autowired
    private UserApplication userApplication;

    @Autowired
    private RoleApplication roleApplication;

    @Autowired
    private PermissionApplication permissionApplication;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public ResponseVO qrCodeDecode(String data) throws BuzException {
        String[] userdata = new String[0];
        try {
            userdata = new String(EncryptUtil.decryptBASE64(data),"UTF-8").split(":");
        } catch (UnsupportedEncodingException e) {
            log.error("Failed to decode " + data);
        }
        if(userdata.length != 2){
            return new ResponseVO(1, "错误的二维码数据");
        }
        UserDto user = userApplication.getUserByUsername(userdata[0]);
        if(user == null || !userdata[1].equals(user.getQrCode())){
            return new ResponseVO(1, "错误的二维码数据");
        }
        Map<String, String> qrData = new HashMap<>();
        qrData.put("username", user.getUserName());
        qrData.put("password", EncryptUtil.AESDecrypt(user.getQrCode(), user.getPassword()));
        return new ResponseVO(qrData);
    }

    @Override
    public ResponseVO createUser(UserCreateReq user) throws BuzException {
        String email = user.getEmail();
        String phone = user.getPhone();
        if (email != null && !"".equals(email) && !ValidUtil.email(email)) {
            throw new BuzException(BuzExceptionCode.WrongEmail);
        }
        if (phone != null && !"".equals(phone) && !ValidUtil.phoneNumber(phone)) {
            throw new BuzException(BuzExceptionCode.WrongPhoneNumber);
        }

        String username = user.getUserName();
        String firstName = user.getFirstName();
        String password = user.getPassword();
        /*
         * 1.用户名,姓名,用户状态,角色,密码必填
         * 2.用户名长度不超过30
         * 3.密码长度4
         */
        if (user.getStatus() == null || user.getRoleId() == null || user.getDepartments() == null ||
                StringUtils.isBlank(username) || username.length() > 30 || StringUtils.isBlank(password) || password.length() < 4 || StringUtils.isBlank(firstName)) {
            throw new BuzException(BuzExceptionCode.ErrorParam);
        }
        if (userApplication.getUserByUsername(username) != null) {
            throw new BuzException(BuzExceptionCode.UsernameExists);
        }
        if (email != null) {
            user.setEmail(email.trim());
        }
        if (phone != null) {
            user.setPhone(phone.trim());
        }
        user.setUserName(username);
        user.setFirstName(firstName);
        user.setPassword(password);
        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(user, userDto);
        userDto.setCreateBy(UserContext.getLoginUserID());
        userDto = userApplication.createUser(userDto);

        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(userDto, userVO);
        userVO.setPermissions(getUserPermission(userVO.getId()));
        return new ResponseVO(userVO);
    }

    @Override
    public ResponseVO updateUser(Long id, UserUpdateReq user) throws BuzException {
        String firstName = user.getFirstName();
        if (id == null || StringUtils.isBlank(firstName)|| user.getDepartments() == null) {
            throw new BuzException(BuzExceptionCode.ErrorParam);
        }
        UserDto userDto1 = userApplication.getUserByUserID(id);
        if (userDto1 == null) {
            throw new BuzException(BuzExceptionCode.UserNotExists);
        }

        String email = user.getEmail();
        String phone = user.getPhone();
        if (StringUtils.isNotBlank(email) && !ValidUtil.email(email)) {
            throw new BuzException(BuzExceptionCode.WrongEmail);
        }
        if (StringUtils.isNotBlank(phone) && !ValidUtil.phoneNumber(phone)) {
            throw new BuzException(BuzExceptionCode.WrongPhoneNumber);
        }

        String password = user.getPassword();
        Long roleId = user.getRoleId();
        Long currentUserId = UserContext.getLoginUserID();

        //修改非当前登录用户,或者修改用户姓名,密码,角色 校验系统管理权限
        if (password != null) {
            password = password.trim();
        }

        firstName = firstName.trim();

        Boolean lockStatus = user.getLockStatus();

        if (!id.equals(currentUserId) || StringUtils.isNotBlank(password) || (roleId != null && !userDto1.getRoleId().equals(roleId))
                || !firstName.equals(userDto1.getFirstName()) || (lockStatus != null && !lockStatus.equals(userDto1.getLockStatus()))) {
            List<Integer> permissions = UserContext.getLoginUserPermissions();
            if (CollectionUtils.isNotEmpty(permissions)) {
                //判断用户是否有系统管理权限
                if (permissions.indexOf(Permission.Admin.toCode()) == -1) {
                    throw new BuzException(BuzExceptionCode.AccessDenied);
                }
            } else {
                throw new BuzException(BuzExceptionCode.AccessDenied);
            }
        }

        if (email != null) {
            user.setEmail(email.trim());
        }
        if (phone != null) {
            user.setPhone(phone.trim());
        }
        user.setPassword(password);
        user.setFirstName(firstName);
        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(user, userDto);
        userDto.setUpdateBy(UserContext.getLoginUserID());
        userDto.setLockStatus(lockStatus);
        if(lockStatus != null && !lockStatus){
            userDto.setLockCount(null);
            userDto.setLockCountTime(null);
        }
        userDto = userApplication.updateUser(userDto);

        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(userDto, userVO);
        userVO.setPermissions(getUserPermission(userVO.getId()));
        return new ResponseVO(userVO);
    }


    @Override
    public ResponseVO getUsers(@DefaultValue("1") int page, @DefaultValue("10") int length, String filter, Integer order,
                               String sort, Boolean status, Integer permissionId, Long roleId) throws BuzException {
        if (page <= 0 || length <= 0) {
            throw new BuzException(BuzExceptionCode.ErrorParam);
        }
        UserCondition con = new UserCondition();
        con.setFilter(StringUtil.escapeExprSpecialWord(filter));
        con.setStart((page - 1) * length);
        con.setSize(length);
        con.setStatus(status);
        con.setRoleId(roleId);
        con.setPermissionId(permissionId);
        if (order != null) {
            con.setOrder(UserSortByEnum.valueOf(order).toString());
            if (sort != null) {
                con.setOrder(con.getOrder() + " " + sort);
            }
        }
        List<UserDto> data = userApplication.getUserByCondition(con);
        Long total = userApplication.getUserTotalByCondition(con);
        List<UserVO> users = userDtoToVos(data);
        return new PageDataVO(page, length, total, users);
    }

    @Override
    public ResponseVO resetPassword(Map<String, String> data) throws BuzException {
        String oldPassword = data.get("oldPassword");
        String newPassword = data.get("newPassword");
        if (StringUtils.isBlank(oldPassword) || StringUtils.isBlank(newPassword)) {
            throw new BuzException(BuzExceptionCode.ErrorParam);
        }
        if (newPassword.equals(oldPassword)) {
            throw new BuzException(BuzExceptionCode.PasswordSame);
        }
        String username = data.get("username");
        if (StringUtils.isBlank(username)) {
            username = UserContext.getLoginUserName();
        }
        boolean matches = bCryptPasswordEncoder.matches(oldPassword, userApplication.getUserByUsername(username).getPassword());
        if (!matches) {
            throw new BuzException(BuzExceptionCode.WrongPassword);
        }
        userApplication.resetPassword(username, newPassword);
        return new ResponseVO();
    }

    @Override
    public ResponseVO checkUsername(String username) throws BuzException {
        if (StringUtils.isBlank(username)) {
            throw new BuzException(BuzExceptionCode.ErrorParam);
        }
        UserDto userDto = userApplication.getUserByUsername(username);
        if (userDto == null) {
            return new ResponseVO();
        } else {
            return new ResponseVO(BuzExceptionCode.UsernameExists, "用户名已存在");
        }
    }

    @Override
    public ResponseVO getUser(Long id) throws BuzException {
        if (id == null) {
            throw new BuzException(BuzExceptionCode.ErrorParam);
        }
        Long currentUserId = UserContext.getLoginUserID();
        //获取非当前登录用户的信息校验系统管理权限
        if (!id.equals(currentUserId)) {
            List<Integer> permissions = UserContext.getLoginUserPermissions();
            if (CollectionUtils.isNotEmpty(permissions)) {
                //判断用户是否有系统管理权限
                if (permissions.indexOf(Permission.Admin.toCode()) == -1) {
                    throw new BuzException(BuzExceptionCode.AccessDenied);
                }
            } else {
                throw new BuzException(BuzExceptionCode.AccessDenied);
            }
        }
        UserDto userDto = userApplication.getUserByUserID(id);
        if (userDto == null) {
            throw new BuzException(BuzExceptionCode.UserNotExists);
        }
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(userDto, userVO);
        userVO.setPermissions(getUserPermission(userVO.getId()));
        try {
            userVO.setQrCode(new String(EncryptUtil.encryptBASE64(userDto.getUserName() + ":" + userDto.getQrCode()),"UTF-8"));
        } catch (UnsupportedEncodingException e) {
            log.error("Failed to encode " + userDto.getUserName() + ":" + userDto.getQrCode());
        }
        return new ResponseVO(userVO);
    }

    @Override
    public ResponseVO getGrossingUsers() throws BuzException {
        UserCondition userCondition = new UserCondition();
        userCondition.setSize(Integer.MAX_VALUE);
        userCondition.setPermissionId(permissionApplication.getByCode(Permission.Grossing.toCode()).getId());
        userCondition.setStatus(true);
        List<UserDto> users = userApplication.getUserByCondition(userCondition);
        return new ResponseVO(userDtoToSimpleVos(users));
    }

    @Override
    public ResponseVO getDiagnoseUsers() throws BuzException {
        UserCondition userCondition = new UserCondition();
        userCondition.setSize(Integer.MAX_VALUE);
        List<Integer> permissions = new ArrayList<>();
        permissions.add(permissionApplication.getByCode(Permission.FirstDiagnose.toCode()).getId());
        permissions.add(permissionApplication.getByCode(Permission.SecondDiagnose.toCode()).getId());
        permissions.add(permissionApplication.getByCode(Permission.ThirdDiagnose.toCode()).getId());
        userCondition.setPermissionIds(permissions);
        userCondition.setStatus(true);
        List<UserDto> users = userApplication.getUserByCondition(userCondition);
        return new ResponseVO(userDtoToSimpleVos(users));
    }

    @Override
    public ResponseVO getSuperDiagnoseUsers() throws BuzException {
        UserCondition userCondition = new UserCondition();
        userCondition.setSize(Integer.MAX_VALUE);
        List<Integer> permissions = new ArrayList<>();
        permissions.add(permissionApplication.getByCode(Permission.SecondDiagnose.toCode()).getId());
        permissions.add(permissionApplication.getByCode(Permission.ThirdDiagnose.toCode()).getId());
        userCondition.setPermissionIds(permissions);
        userCondition.setStatus(true);
        List<UserDto> users = userApplication.getUserByCondition(userCondition);
        return new ResponseVO(userDtoToSimpleVos(users));
    }

    @Override
    public ResponseVO getFirstDiagnoseUsers(String username) throws BuzException {
        UserCondition userCondition = new UserCondition();
        userCondition.setSize(Integer.MAX_VALUE);
        userCondition.setFilter(username);
        userCondition.setPermissionId(permissionApplication.getByCode(Permission.FirstDiagnose.toCode()).getId());
        userCondition.setStatus(true);
        List<UserDto> users = userApplication.getUserByCondition(userCondition);
        return new ResponseVO(userDtoToSimpleVos(users));
    }

    @Override
    public ResponseVO getThirdDiagnoseUsers() throws BuzException {
        UserCondition userCondition = new UserCondition();
        userCondition.setSize(Integer.MAX_VALUE);
        userCondition.setPermissionId(permissionApplication.getByCode(Permission.ThirdDiagnose.toCode()).getId());
        userCondition.setStatus(true);
        List<UserDto> users = userApplication.getUserByCondition(userCondition);
        return new ResponseVO(userDtoToSimpleVos(users));
    }

    @Override
    public ResponseVO getReportUsers() throws BuzException {
        return new ResponseVO(userApplication.getSentReportUser());
    }

    /**
     * 获取用户权限信息
     *
     * @param userId
     * @return
     */
    private List<PermissionVO> getUserPermission(Long userId) {
        List<Integer> permissions = userApplication.getUserPermissionsCode(userId);
        List<PermissionVO> permissionVOs = null;
        if (CollectionUtils.isNotEmpty(permissions)) {
            permissionVOs = new ArrayList<>();
            for (int i : permissions) {
                PermissionDto permissionDto = permissionApplication.getByCode(i);
                if (permissionDto != null) {
                    PermissionVO permissionVO = new PermissionVO();
                    BeanUtils.copyProperties(permissionDto, permissionVO);
                    permissionVOs.add(permissionVO);
                }
            }
        }
        return permissionVOs;
    }

    private List<UserVO> userDtoToVos(List<UserDto> userDtoList) {
        if (CollectionUtils.isEmpty(userDtoList)) {
            return null;
        }
        List<UserVO> userVOs = new ArrayList<>();
        UserVO vo;
        for (UserDto user : userDtoList) {
            vo = new UserVO();

            BeanUtils.copyProperties(user, vo);
            vo.setStatusName(UserStatusEnum.valueOf(user.getStatus()).toString());

            RoleDto role = roleApplication.getRole(user.getRoleId());
            if (role != null) {
                vo.setRoleName(role.getName());
            }
            vo.setPermissions(getUserPermission(vo.getId()));
            userVOs.add(vo);
        }
        return userVOs;
    }

    private List<UserSimpleVO> userDtoToSimpleVos(List<UserDto> userDtoList) {
        if (CollectionUtils.isEmpty(userDtoList)) {
            return null;
        }
        List<UserSimpleVO> userVOs = new ArrayList<>();
        UserSimpleVO vo;
        for (UserDto user : userDtoList) {
            vo = new UserSimpleVO();
            BeanUtils.copyProperties(user, vo);
            userVOs.add(vo);
        }
        return userVOs;
    }

}
