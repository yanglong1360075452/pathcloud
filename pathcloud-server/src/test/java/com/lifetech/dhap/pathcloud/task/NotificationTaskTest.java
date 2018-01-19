package com.lifetech.dhap.pathcloud.task;

import com.lifetech.dhap.pathcloud.common.BaseTest;
import com.lifetech.dhap.pathcloud.quartz.NotificationTask;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author eric.guo@ostengar.com
 * @version 1.0
 * @time 2017-01-12-14:39
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:applicationContext-*.xml"})
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = false)
@Transactional(rollbackFor = Exception.class)
@Ignore
public class NotificationTaskTest extends BaseTest {

    @Autowired
    private NotificationTask notificationTask;

    @Test
    public void sampleNotificationCheckTest(){
        notificationTask.sampleNotificationCheck();
    }
}
