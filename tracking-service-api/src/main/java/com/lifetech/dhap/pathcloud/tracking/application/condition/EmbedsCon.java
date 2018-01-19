package com.lifetech.dhap.pathcloud.tracking.application.condition;

import com.lifetech.dhap.pathcloud.common.base.PageCondition;

import java.util.Date;

/**
 * Created by HP on 2017/3/6.
 */
public class EmbedsCon extends PageCondition {

    private String filter;

    private String startPathNo;

    private String endPathNo;

    private Date startTime;

    private Date endTime;

    private Integer status;

    private Long operatorId;

    private Long mountingId;
    private  Long dyeInstrumentId;
    private Long mountingInstrumentId;

    private Integer dyeCategory;

    private Integer printCount;

    public Integer getDyeCategory() {
        return dyeCategory;
    }

    public void setDyeCategory(Integer dyeCategory) {
        this.dyeCategory = dyeCategory;
    }

    public Integer getPrintCount() {
        return printCount;
    }

    public void setPrintCount(Integer printCount) {
        this.printCount = printCount;
    }

    public Long getMountingId() {
        return mountingId;
    }

    public void setMountingId(Long mountingId) {
        this.mountingId = mountingId;
    }

    public Long getDyeInstrumentId() {
        return dyeInstrumentId;
    }

    public void setDyeInstrumentId(Long dyeInstrumentId) {
        this.dyeInstrumentId = dyeInstrumentId;
    }

    public Long getMountingInstrumentId() {
        return mountingInstrumentId;
    }

    public void setMountingInstrumentId(Long mountingInstrumentId) {
        this.mountingInstrumentId = mountingInstrumentId;
    }

    public String getFilter() {
        return filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }

    public String getStartPathNo() {
        return startPathNo;
    }

    public void setStartPathNo(String startPathNo) {
        this.startPathNo = startPathNo;
    }

    public String getEndPathNo() {
        return endPathNo;
    }

    public void setEndPathNo(String endPathNo) {
        this.endPathNo = endPathNo;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(Long operatorId) {
        this.operatorId = operatorId;
    }
}
