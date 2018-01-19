package com.lifetech.dhap.pathcloud.tracking.api.vo;

import java.util.Date;

/**
 * Created by LiuMei on 2017-10-09.
 */
public class SpecialHistoryVO {

    private Date createTime;

    private String specialResult;

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getSpecialResult() {
        return specialResult;
    }

    public void setSpecialResult(String specialResult) {
        this.specialResult = specialResult;
    }
}
