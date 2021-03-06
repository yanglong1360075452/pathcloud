package com.lifetech.dhap.pathcloud.notification.domain;

import com.lifetech.dhap.pathcloud.notification.condition.NotificationCon;
import com.lifetech.dhap.pathcloud.notification.domain.model.Notification;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface NotificationRepository {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table system_notification
     *
     * @mbggenerated Thu Jan 05 12:03:38 CST 2017
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table system_notification
     *
     * @mbggenerated Thu Jan 05 12:03:38 CST 2017
     */
    int insert(Notification record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table system_notification
     *
     * @mbggenerated Thu Jan 05 12:03:38 CST 2017
     */
    Notification selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table system_notification
     *
     * @mbggenerated Thu Jan 05 12:03:38 CST 2017
     */
    List<Notification> selectAll();

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table system_notification
     *
     * @mbggenerated Thu Jan 05 12:03:38 CST 2017
     */
    int updateByPrimaryKey(Notification record);

    List<Notification> selectByCondition(NotificationCon con);

    long countByCondition(NotificationCon con);

    Notification checkNotification(@Param("pathId") Long pathId, @Param("blockId") Long blockId, @Param("status") Integer status);

    Long last();
}