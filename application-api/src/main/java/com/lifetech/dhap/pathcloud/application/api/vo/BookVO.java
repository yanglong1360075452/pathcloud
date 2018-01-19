package com.lifetech.dhap.pathcloud.application.api.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author eric.guo@ostengar.com
 * @version 1.0
 * @time 2017-04-24-13:59
 */
public class BookVO implements Serializable {

    private Date freezeStartTime; //冰冻预约开始时间

    private List<Integer> cells;

    private Date freezeEndTime; //冰冻预约结束时间

    private Integer instrumentId;//设备ID

    public Date getFreezeStartTime() {
        return freezeStartTime;
    }

    public void setFreezeStartTime(Date freezeStartTime) {
        this.freezeStartTime = freezeStartTime;
    }

    public List<Integer> getCells() {
        return cells;
    }

    public void setCells(List<Integer> cells) {
        this.cells = cells;
    }

    public Date getFreezeEndTime() {
        return freezeEndTime;
    }

    public void setFreezeEndTime(Date freezeEndTime) {
        this.freezeEndTime = freezeEndTime;
    }

    public Integer getInstrumentId() {
        return instrumentId;
    }

    public void setInstrumentId(Integer instrumentId) {
        this.instrumentId = instrumentId;
    }
}
