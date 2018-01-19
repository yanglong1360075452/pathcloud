package com.lifetech.dhap.pathcloud.tracking.application.condition;

import com.lifetech.dhap.pathcloud.common.base.PageCondition;

import java.util.Date;

/**
 * @author eric.guo@ostengar.com
 * @version 1.0
 * @time 2017-05-03-16:04
 */
public class DoctorAdviceCondition extends PageCondition {

    private String filter;

    private Long inspectionDoctor;

    private Date applyTimeStart;

    private Date applyTimeEnd;

    private Integer applyType;

    private Integer status;

    public String getFilter() {
        return filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }

    public Long getInspectionDoctor() {
        return inspectionDoctor;
    }

    public void setInspectionDoctor(Long inspectionDoctor) {
        this.inspectionDoctor = inspectionDoctor;
    }

    public Date getApplyTimeStart() {
        return applyTimeStart;
    }

    public void setApplyTimeStart(Date applyTimeStart) {
        this.applyTimeStart = applyTimeStart;
    }

    public Date getApplyTimeEnd() {
        return applyTimeEnd;
    }

    public void setApplyTimeEnd(Date applyTimeEnd) {
        this.applyTimeEnd = applyTimeEnd;
    }

    public Integer getApplyType() {
        return applyType;
    }

    public void setApplyType(Integer applyType) {
        this.applyType = applyType;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
