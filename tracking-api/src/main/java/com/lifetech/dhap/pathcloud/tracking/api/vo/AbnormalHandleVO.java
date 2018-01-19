package com.lifetech.dhap.pathcloud.tracking.api.vo;

/**
 * Created by LiuMei on 2017-01-09.
 */
public class AbnormalHandleVO {

    //异常玻片ID列表
    private Long abnormalId;

    //处理方式
    private Integer handle;

    //备注
    private String note;

    public Long getAbnormalId() {
        return abnormalId;
    }

    public void setAbnormalId(Long abnormalId) {
        this.abnormalId = abnormalId;
    }

    public Integer getHandle() {
        return handle;
    }

    public void setHandle(Integer handle) {
        this.handle = handle;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }


}
