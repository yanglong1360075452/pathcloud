package com.lifetech.dhap.pathcloud.notification;

import com.lifetech.dhap.pathcloud.notification.condition.NotificationCon;
import com.lifetech.dhap.pathcloud.notification.dto.NotificationDealDto;
import com.lifetech.dhap.pathcloud.notification.dto.NotificationDetail;
import com.lifetech.dhap.pathcloud.notification.dto.NotificationDto;
import com.lifetech.dhap.pathcloud.notification.dto.NotificationReceiverDto;

import java.util.List;

/**
 * @author eric.guo@ostengar.com
 * @version 1.0
 * @time 2017-01-05-12:08
 */
public interface NotificationApplication {

    void addNotification(NotificationDto dto);

    List<NotificationDto> getNotificationByCondition(NotificationCon con);

    Long countNotificationByCondition(NotificationCon con);

    NotificationDetail getNotificationDetail(Long id);

    /**
     * 消息是否已发送，定时任务发送消息时调用
     *
     * @param pathologyId
     * @param blockId
     * @return
     */
    Boolean getNotificationForTaskCheck(Long pathologyId, Long blockId, Integer status);

    /**
     * 消息处理
     *
     * @param dto
     */
    void dealNotification(NotificationDealDto dto);

    void addNotificationReceiver(NotificationReceiverDto dto);
}
