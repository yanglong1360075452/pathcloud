package com.lifetech.dhap.pathcloud.role.api;

import com.lifetech.dhap.pathcloud.common.BaseTest;
import com.lifetech.dhap.pathcloud.security.UserContext;
import com.lifetech.dhap.pathcloud.user.api.RoleApi;
import com.lifetech.dhap.pathcloud.user.api.vo.PermissionVO;
import com.lifetech.dhap.pathcloud.user.api.vo.RoleVO;
import com.lifetech.dhap.pathcloud.user.application.RoleApplication;
import com.lifetech.dhap.pathcloud.user.application.dto.RoleDto;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by HP on 2016/11/17.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:applicationContext-*.xml"})
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
@Transactional(rollbackFor = Exception.class)
@Ignore
public class RoleApiTest extends BaseTest {

    @Autowired
    private RoleApi roleApi;

    @Autowired
    private RoleApplication roleApplication;


    //创建角色
    @Test
    public void createRoleTest(){
        RoleVO roleVO = new RoleVO();
        List<PermissionVO> permissions = new ArrayList<>();
        PermissionVO permission = new PermissionVO();
        permission.setId(1);
        permissions.add(permission);
        roleVO.setName("第三管理员");
        roleVO.setDescription("拥有很大的权限");
        roleVO.setCreateBy(UserContext.getUserDetails().getId());
        roleVO.setPermissions(permissions);
        roleApi.createRole(roleVO);
        RoleDto role = roleApplication.getRoleByName(roleVO.getName());
        assert (role.getDescription().equals(roleVO.getDescription()));
       assert (role.getName().equals(roleVO.getName()));


    }

    //更改角色测试
    @Test
    public void updateRoleTest(){

        RoleVO roleVO = new RoleVO();
        List<PermissionVO> permissions = new ArrayList<>();
        PermissionVO permission = new PermissionVO();
        permission.setId(1);
        permissions.add(permission);
        roleVO.setName("技术员");
        roleVO.setDescription("玩技术的人员");
        roleVO.setCreateBy(UserContext.getUserDetails().getId());
        roleVO.setPermissions(permissions);
        RoleVO roleVO1 = (RoleVO) roleApi.createRole(roleVO).getData();


        RoleVO roleVO2 = new RoleVO();
        roleVO2.setId(roleVO1.getId());
        roleVO2.setName("技术员2");
        roleVO2.setDescription("新的技术人员");
        roleVO2.setCreateBy(UserContext.getUserDetails().getId());
        roleVO2.setPermissions(permissions);
        roleApi.updateRole(roleVO2.getId(),roleVO2);

        RoleDto role = roleApplication.getRole(roleVO2.getId());

        assert (role.getName().equals(roleVO2.getName()));
        assert (role.getDescription().equals(roleVO2.getDescription()));
        assert (role.getId().equals(roleVO2.getId()));


    }
    //删除角色测试
    @Test
    public void deleteRoleTest(){

        RoleVO roleVO = new RoleVO();
        List<PermissionVO> permissions = new ArrayList<>();
        PermissionVO permission = new PermissionVO();
        permission.setId(1);
        permissions.add(permission);
        roleVO.setName("技术员");
        roleVO.setDescription("玩技术的人员");
        roleVO.setCreateBy(UserContext.getUserDetails().getId());
        roleVO.setPermissions(permissions);
        RoleVO roleVO1 = (RoleVO) roleApi.createRole(roleVO).getData();

        roleApi.deleteRole(roleVO1.getId());
        RoleDto role = roleApplication.getRole(roleVO1.getId());
        assert (role == null);
    }




}
