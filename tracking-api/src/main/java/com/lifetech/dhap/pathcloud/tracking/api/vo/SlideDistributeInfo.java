package com.lifetech.dhap.pathcloud.tracking.api.vo;

import com.lifetech.dhap.pathcloud.user.api.vo.UserSimpleVO;

import java.util.Date;

/**
 * Created by LiuMei on 2017-01-06.
 */
public class SlideDistributeInfo {

    private long id;

    private String pathNo;

    private String subId;

    private String blockSubId;

    private UserSimpleVO confirmUser;

    private String receiver;

    private Date receiveTime;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPathNo() {
        return pathNo;
    }

    public void setPathNo(String pathNo) {
        this.pathNo = pathNo;
    }

    public String getSubId() {
        return subId;
    }

    public void setSubId(String subId) {
        this.subId = subId;
    }

    public String getBlockSubId() {
        return blockSubId;
    }

    public void setBlockSubId(String blockSubId) {
        this.blockSubId = blockSubId;
    }

    public UserSimpleVO getConfirmUser() {
        return confirmUser;
    }

    public void setConfirmUser(UserSimpleVO confirmUser) {
        this.confirmUser = confirmUser;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public Date getReceiveTime() {
        return receiveTime;
    }

    public void setReceiveTime(Date receiveTime) {
        this.receiveTime = receiveTime;
    }
}
