package com.lifetech.dhap.pathcloud.dehydrate.domain.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by LiuMei on 2017-03-22.
 */
public class DehydratorErrorMsg implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long instrumentId;

    private String name;

    private String sn;

    private String errMsg;

    private Date occurTime;

    private boolean status;

    public Long getInstrumentId() {
        return instrumentId;
    }

    public void setInstrumentId(Long instrumentId) {
        this.instrumentId = instrumentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public Date getOccurTime() {
        return occurTime;
    }

    public void setOccurTime(Date occurTime) {
        this.occurTime = occurTime;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
