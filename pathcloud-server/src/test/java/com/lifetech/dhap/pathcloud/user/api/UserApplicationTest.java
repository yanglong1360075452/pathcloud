package com.lifetech.dhap.pathcloud.user.api;

import com.lifetech.dhap.pathcloud.user.application.UserApplication;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author eric.guo@ostengar.com
 * @version 1.0
 * @time 2016-11-16-16:54
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:applicationContext-*.xml"})
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
@Transactional(rollbackFor = Exception.class)
@Ignore
public class UserApplicationTest extends AbstractJUnit4SpringContextTests {

    @Autowired
    private UserApplication userApplication;

    @Test
    public void getUserByUserIDTest(){
        userApplication.getUserByUserID(1L);
    }

}
