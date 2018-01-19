package com.lifetech.dhap.pathcloud.tracking.application.dto;

import java.util.Date;

/**
 * Created by LiuMei on 2017-01-03.
 */
public class DistributeRecordDto {

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
