package com.lifetech.dhap.pathcloud.user.api;

import com.lifetech.dhap.pathcloud.common.BaseTest;
import com.lifetech.dhap.pathcloud.common.response.ResponseVO;
import com.lifetech.dhap.pathcloud.user.api.request.UserCreateReq;
import com.lifetech.dhap.pathcloud.user.api.request.UserUpdateReq;
import com.lifetech.dhap.pathcloud.user.api.vo.UserVO;
import com.lifetech.dhap.pathcloud.user.application.UserApplication;
import com.lifetech.dhap.pathcloud.user.application.dto.UserDto;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author eric.guo@ostengar.com
 * @version 1.0
 * @time 2016-11-14-10:00
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:applicationContext-*.xml"})
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
@Transactional(rollbackFor = Exception.class)
@Ignore
public class UserApiTest extends BaseTest {

    @Autowired
    private UserApi userApi;

    @Autowired
    private UserApplication userApplication;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Before
    public void setup() throws Exception {
        SecurityContext context = new SecurityContextImpl();
        context.setAuthentication(this);
        SecurityContextHolder.setContext(context);
    }

    @After
    public void destroy() throws Exception {
    }

    @Test
    public void getUsersTest() {
        ResponseVO responseVO = userApi.getUsers(1, 1, "",null,null,null,null,null);
        Map data = (HashMap)responseVO.getData();
        List users = (ArrayList) data.get("data");
        UserVO user = (UserVO) users.get(0);
        assert (userApplication.getUserByUserID(user.getId()) != null);
    }

    @Test
    public void createUserTest(){
        UserCreateReq userCreateReq = new UserCreateReq();
        String email = System.currentTimeMillis() + "@thermofisher.com";
        userCreateReq.setEmail(email);
        userCreateReq.setRoleId(1L);
        userCreateReq.setUserName("test001");
        userCreateReq.setPassword("123456");
        userCreateReq.setStatus(true);
        userCreateReq.setPhone("13684595687");
        userApi.createUser(userCreateReq);
        UserDto user = userApplication.getUserByUsername(userCreateReq.getUserName());
        assert ( user != null);
    }

    @Test
    public void updateUserTest(){
        String email = System.currentTimeMillis()+"@thermofisher.com";
        UserCreateReq userCreateReq = new UserCreateReq();
        userCreateReq.setEmail(email);
        userCreateReq.setUserName(email);
        userCreateReq.setPassword("123456");
        userCreateReq.setRoleId(1L);
        userCreateReq.setStatus(true);
        userCreateReq.setPhone("13684595687");
        UserVO user = (UserVO) userApi.createUser(userCreateReq).getData();
        UserUpdateReq userUpdateReq = new UserUpdateReq();
        userUpdateReq.setId(user.getId());
        userUpdateReq.setPhone("14789875684");
        userCreateReq.setRoleId(1L);
        userUpdateReq.setStatus(true);
        userUpdateReq.setUserName("test004");
        userUpdateReq.setEmail(email);
        userApi.updateUser(userUpdateReq.getId(), userUpdateReq);
        UserDto updateUser = userApplication.getUserByUsername(email);
        assert (updateUser.getPhone().equals("14789875684"));
    }

}
