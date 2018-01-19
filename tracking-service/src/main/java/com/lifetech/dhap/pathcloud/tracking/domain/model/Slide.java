package com.lifetech.dhap.pathcloud.tracking.domain.model;

import java.util.Date;

/**
 * Created by LiuMei on 2017-01-04.
 */
public class Slide extends Block {

    private String pathNo;

    private String blockSerialNumber;

    private String blockSubId;

    private Long confirmUser;

    private String receiver;

    private Date receiveTime;

    /**
     * 切片操作员
     */
    private String sectionOperator;

    public String getSectionOperator() {
        return sectionOperator;
    }

    public void setSectionOperator(String sectionOperator) {
        this.sectionOperator = sectionOperator;
    }

    public String getPathNo() {
        return pathNo;
    }

    public void setPathNo(String pathNo) {
        this.pathNo = pathNo;
    }

    public String getBlockSerialNumber() {
        return blockSerialNumber;
    }

    public void setBlockSerialNumber(String blockSerialNumber) {
        this.blockSerialNumber = blockSerialNumber;
    }

    public String getBlockSubId() {
        return blockSubId;
    }

    public void setBlockSubId(String blockSubId) {
        this.blockSubId = blockSubId;
    }

    public Long getConfirmUser() {
        return confirmUser;
    }

    public void setConfirmUser(Long confirmUser) {
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
