package com.lifetech.dhap.pathcloud.tracking.api.vo;

import java.util.Date;

/**
 * Created by LiuMei on 2017-01-03.
 */
public class DistributeRecordVO {

    private Date confirmTime;

    private Long confirmUser;

    public Date getConfirmTime() {
        return confirmTime;
    }

    public void setConfirmTime(Date confirmTime) {
        this.confirmTime = confirmTime;
    }

    public Long getConfirmUser() {
        return confirmUser;
    }

    public void setConfirmUser(Long confirmUser) {
        this.confirmUser = confirmUser;
    }
}
