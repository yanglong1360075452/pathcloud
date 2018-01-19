package com.lifetech.dhap.pathcloud.application.application.dto;

import java.util.Date;

/**
 * @author eric.guo@ostengar.com
 * @version 1.0
 * @time 2017-03-29-11:53
 */
public class BookingAddDto {

    private Long id;

    private Date startTime;

    private Date endTime;

    private String timeFlag;

    private Long bookingPid;

    private Long applicationId;

    private Integer instrumentId;

    private String bookingPerson;

    private String bookingPhone;

    private Date createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getTimeFlag() {
        return timeFlag;
    }

    public void setTimeFlag(String timeFlag) {
        this.timeFlag = timeFlag;
    }

    public Long getBookingPid() {
        return bookingPid;
    }

    public void setBookingPid(Long bookingPid) {
        this.bookingPid = bookingPid;
    }

    public String getBookingPerson() {
        return bookingPerson;
    }

    public void setBookingPerson(String bookingPerson) {
        this.bookingPerson = bookingPerson;
    }

    public String getBookingPhone() {
        return bookingPhone;
    }

    public void setBookingPhone(String bookingPhone) {
        this.bookingPhone = bookingPhone;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(Long applicationId) {
        this.applicationId = applicationId;
    }

    public Integer getInstrumentId() {
        return instrumentId;
    }

    public void setInstrumentId(Integer instrumentId) {
        this.instrumentId = instrumentId;
    }
}
