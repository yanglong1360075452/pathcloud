package com.lifetech.dhap.pathcloud.notification.api.impl;

import com.lifetech.dhap.pathcloud.common.exception.BuzException;
import com.lifetech.dhap.pathcloud.common.exception.BuzExceptionCode;
import com.lifetech.dhap.pathcloud.common.response.PageDataVO;
import com.lifetech.dhap.pathcloud.common.response.ResponseVO;
import com.lifetech.dhap.pathcloud.common.utils.StringUtil;
import com.lifetech.dhap.pathcloud.notification.NotificationApplication;
import com.lifetech.dhap.pathcloud.notification.api.NotificationApi;
import com.lifetech.dhap.pathcloud.notification.api.vo.NotificationDeal;
import com.lifetech.dhap.pathcloud.notification.condition.NotificationCon;
import com.lifetech.dhap.pathcloud.notification.dto.NotificationDealDto;
import com.lifetech.dhap.pathcloud.notification.dto.NotificationDetail;
import com.lifetech.dhap.pathcloud.notification.dto.NotificationDto;
import com.lifetech.dhap.pathcloud.security.UserContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.DefaultValue;
import java.util.List;

/**
 * @author eric.guo@ostengar.com
 * @version 1.0
 * @time 2017-01-09-10:59
 */
@Component("notificationApi")
public class NotificationApiImpl implements NotificationApi {

    private static final Logger logger = LoggerFactory.getLogger(NotificationApiImpl.class);

    @Autowired
    private NotificationApplication notificationApplication;

    @Override
    public ResponseVO getNotification(@DefaultValue("1") Integer page, @DefaultValue("10") Integer length,
        Integer order, String sort, Boolean handle, Boolean readFlag, String filter) throws BuzException {
        if (page < 0 || length < 1) {
            throw new BuzException(BuzExceptionCode.ErrorParam);
        }
        NotificationCon con = new NotificationCon();
        con.setReadFlag(readFlag);
        con.setHandle(handle);
        if(filter != null && !filter.equals("")){
            con.setFilter(StringUtil.escapeExprSpecialWord(filter));
        }
        con.setSize(length);
        con.setStart((page - 1) * length);
        con.setReceiverId(UserContext.getLoginUserID());

        Long receiverPermission  = 0L;
        for (Integer permission : UserContext.getUserDetails().getPermission()) {
            receiverPermission += permission;
        }
        con.setReceiverPermission(receiverPermission);
        con.setOrder("id desc");
        List<NotificationDto> data = notificationApplication.getNotificationByCondition(con);
        Long total = notificationApplication.countNotificationByCondition(con);
        return new PageDataVO(page, length, total, data);
    }

    @Override
    public ResponseVO notificationDetail(Long id) throws BuzException {
        NotificationDetail detail = notificationApplication.getNotificationDetail(id);
        if(detail.getAbnormalHandle() != null){
            detail.setType(2);
        }
        return new ResponseVO(detail);
    }

    @Override
    public ResponseVO notificationSum() throws BuzException {
        long startTime = System.currentTimeMillis();

        NotificationCon con = new NotificationCon();
        con.setHandle(false);
        con.setReceiverId(UserContext.getLoginUserID());

        Long receiverPermission  = 0L;
        for (Integer permission : UserContext.getUserDetails().getPermission()) {
            receiverPermission += permission;
        }
        con.setReceiverPermission(receiverPermission);

        long startTime1 = System.currentTimeMillis();
        Long total = notificationApplication.countNotificationByCondition(con);

        long endTime = System.currentTimeMillis();
        if (endTime - startTime > 500) {
            logger.warn("notificationSum takes " + (endTime - startTime) + " ms. read db takes " + (endTime - startTime1));
        }
        return new ResponseVO(total);
    }

    @Override
    public ResponseVO notificationDeal(NotificationDeal notification) throws BuzException {
        if((notification.getBlockId() == null && notification.getPathId() == null ) || notification.getDealType() == null){
            throw new BuzException(BuzExceptionCode.ErrorParam);
        }
        NotificationDealDto dto = new NotificationDealDto();
        BeanUtils.copyProperties(notification, dto);
        dto.setCreateBy(UserContext.getLoginUserID());
        notificationApplication.dealNotification(dto);
        return new ResponseVO();
    }
}
