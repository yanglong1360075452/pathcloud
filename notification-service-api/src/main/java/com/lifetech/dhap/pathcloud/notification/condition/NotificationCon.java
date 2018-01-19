package com.lifetech.dhap.pathcloud.notification.condition;

import com.lifetech.dhap.pathcloud.common.base.PageCondition;

/**
 * @author eric.guo@ostengar.com
 * @version 1.0
 * @time 2017-01-09-11:06
 */
public class NotificationCon extends PageCondition {

    private Long receiverId;

    private Long receiverPermission;

    private String filter;

    private Boolean readFlag;

    private Boolean handle;

    public String getFilter() {
        return filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }

    public Boolean getReadFlag() {
        return readFlag;
    }

    public void setReadFlag(Boolean readFlag) {
        this.readFlag = readFlag;
    }

    public Long getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(Long receiverId) {
        this.receiverId = receiverId;
    }

    public Long getReceiverPermission() {
        return receiverPermission;
    }

    public void setReceiverPermission(Long receiverPermission) {
        this.receiverPermission = receiverPermission;
    }

    public Boolean getHandle() {
        return handle;
    }

    public void setHandle(Boolean handle) {
        this.handle = handle;
    }
}
