package com.lifetech.dhap.pathcloud.dehydrate.api.vo;

import com.lifetech.dhap.pathcloud.user.api.vo.UserSimpleVO;

import java.util.Date;

/**
 * Created by LiuMei on 2017-05-24.
 */
public class BlockDehydrateErrorVO {

    private Long blockId;

    private String pathNo;

    private String blockSubId;

    private Integer status;
    private String statusDesc;

    private UserSimpleVO lastOperator;

    private Date lastOperateTime;

    public Long getBlockId() {
        return blockId;
    }

    public void setBlockId(Long blockId) {
        this.blockId = blockId;
    }

    public String getPathNo() {
        return pathNo;
    }

    public void setPathNo(String pathNo) {
        this.pathNo = pathNo;
    }

    public String getBlockSubId() {
        return blockSubId;
    }

    public void setBlockSubId(String blockSubId) {
        this.blockSubId = blockSubId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getStatusDesc() {
        return statusDesc;
    }

    public void setStatusDesc(String statusDesc) {
        this.statusDesc = statusDesc;
    }

    public UserSimpleVO getLastOperator() {
        return lastOperator;
    }

    public void setLastOperator(UserSimpleVO lastOperator) {
        this.lastOperator = lastOperator;
    }

    public Date getLastOperateTime() {
        return lastOperateTime;
    }

    public void setLastOperateTime(Date lastOperateTime) {
        this.lastOperateTime = lastOperateTime;
    }
}
