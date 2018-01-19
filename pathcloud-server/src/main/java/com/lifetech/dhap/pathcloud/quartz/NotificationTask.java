package com.lifetech.dhap.pathcloud.quartz;

import com.lifetech.dhap.pathcloud.common.data.PathologyStatus;
import com.lifetech.dhap.pathcloud.tracking.application.ArchiveApplication;
import com.lifetech.dhap.pathcloud.tracking.application.BlockApplication;
import com.lifetech.dhap.pathcloud.tracking.application.condition.GrossingCon;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Random;

/**
 * @author eric.guo@ostengar.com
 * @version 1.0
 * @time 2017-01-12-11:02
 */
public class NotificationTask {

    private static final long MAX_WAIT = 2 * 60 * 1000;
    private Random random = new Random();

    @Autowired
    private BlockApplication blockApplication;

    @Autowired
    private ArchiveApplication archiveApplication;

    /**
     * 异常样本检查, 超时未处理发送消息通知
     */
    public void sampleNotificationCheck(){
        try {
            Thread.sleep((long) (MAX_WAIT * random.nextDouble()));
        } catch (InterruptedException e) {
            //Will not happen
        }

        GrossingCon con = new GrossingCon();
        con.setStart(0);
        con.setSize(10);
        con.setBlockStatus(PathologyStatus.PrepareGrossing.toCode());
        int size = blockApplication.abnormalBlockNotification(con);
        while(size != 0){
            size = blockApplication.abnormalBlockNotification(con);
            con.setStart(con.getStart() + con.getSize());
        }

        con.setStart(0);
        con.setSize(10);
        con.setBlockStatus(PathologyStatus.PrepareGrossingConfirm.toCode());
        size = blockApplication.abnormalBlockNotification(con);
        while(size != 0){
            size = blockApplication.abnormalBlockNotification(con);
            con.setStart(con.getStart() + con.getSize());
        }

        con.setStart(0);
        con.setSize(10);
        con.setBlockStatus(PathologyStatus.PrepareDehydrate.toCode());
        size = blockApplication.abnormalBlockNotification(con);
        while(size != 0){
            size = blockApplication.abnormalBlockNotification(con);
            con.setStart(con.getStart() + con.getSize());
        }

        con.setStart(0);
        con.setSize(10);
        con.setBlockStatus(PathologyStatus.PrepareEmbed.toCode());
        size = blockApplication.abnormalBlockNotification(con);
        while(size != 0){
            size = blockApplication.abnormalBlockNotification(con);
            con.setStart(con.getStart() + con.getSize());
        }

        con.setStart(0);
        con.setSize(10);
        con.setBlockStatus(PathologyStatus.PrepareSection.toCode());
        size = blockApplication.abnormalBlockNotification(con);
        while(size != 0){
            size = blockApplication.abnormalBlockNotification(con);
            con.setStart(con.getStart() + con.getSize());
        }

        con.setStart(0);
        con.setSize(10);
        con.setBlockStatus(PathologyStatus.PrepareDye.toCode());
        size = blockApplication.abnormalBlockNotification(con);
        while(size != 0){
            size = blockApplication.abnormalBlockNotification(con);
            con.setStart(con.getStart() + con.getSize());
        }

        con.setStart(0);
        con.setSize(10);
        con.setBlockStatus(PathologyStatus.PrepareCompletionConfirm.toCode());
        size = blockApplication.abnormalBlockNotification(con);
        while(size != 0){
            size = blockApplication.abnormalBlockNotification(con);
            con.setStart(con.getStart() + con.getSize());
        }
    }

    /**
     * 借阅超时检查, 超时未归还发送消息通知给有归档权限的用户
     */
    public void archiveNotificationCheck() {
        try {
            Thread.sleep((long) (MAX_WAIT * random.nextDouble()));
        } catch (InterruptedException e) {
            //Will not happen
        }

        archiveApplication.archiveNotification();
    }

}
