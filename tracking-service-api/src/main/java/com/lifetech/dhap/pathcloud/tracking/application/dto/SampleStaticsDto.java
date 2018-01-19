package com.lifetech.dhap.pathcloud.tracking.application.dto;

/**
 * @author eric.guo@ostengar.com
 * @version 1.0
 * @time 2017-01-03-15:09
 */
public class SampleStaticsDto {

    private Integer status;

    private String statusName;

    private Long total;

    private Long errorTotal;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public Long getErrorTotal() {
        return errorTotal;
    }

    public void setErrorTotal(Long errorTotal) {
        this.errorTotal = errorTotal;
    }
}
