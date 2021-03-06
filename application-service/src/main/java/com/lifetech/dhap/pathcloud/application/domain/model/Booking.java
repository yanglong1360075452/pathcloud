package com.lifetech.dhap.pathcloud.application.domain.model;

import java.io.Serializable;
import java.util.Date;

public class Booking implements Serializable {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column booking.id
     *
     * @mbggenerated Wed Mar 29 10:56:21 CST 2017
     */
    private Long id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column booking.start_time
     *
     * @mbggenerated Wed Mar 29 10:56:21 CST 2017
     */
    private Date startTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column booking.end_time
     *
     * @mbggenerated Wed Mar 29 10:56:21 CST 2017
     */
    private Date endTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column booking.time_flag
     *
     * @mbggenerated Wed Mar 29 10:56:21 CST 2017
     */
    private String timeFlag;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column booking.booking_pid
     *
     * @mbggenerated Wed Mar 29 10:56:21 CST 2017
     */
    private Long bookingPid;

    private Long applicationId;


    private Integer instrumentId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column booking.booking_person
     *
     * @mbggenerated Wed Mar 29 10:56:21 CST 2017
     */
    private String bookingPerson;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column booking.booking_phone
     *
     * @mbggenerated Wed Mar 29 10:56:21 CST 2017
     */
    private String bookingPhone;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column booking.create_time
     *
     * @mbggenerated Wed Mar 29 10:56:21 CST 2017
     */
    private Date createTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table booking
     *
     * @mbggenerated Wed Mar 29 10:56:21 CST 2017
     */
    private static final long serialVersionUID = 1L;

    public Long getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(Long applicationId) {
        this.applicationId = applicationId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column booking.id
     *
     * @return the value of booking.id
     *
     * @mbggenerated Wed Mar 29 10:56:21 CST 2017
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column booking.id
     *
     * @param id the value for booking.id
     *
     * @mbggenerated Wed Mar 29 10:56:21 CST 2017
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column booking.start_time
     *
     * @return the value of booking.start_time
     *
     * @mbggenerated Wed Mar 29 10:56:21 CST 2017
     */
    public Date getStartTime() {
        return startTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column booking.start_time
     *
     * @param startTime the value for booking.start_time
     *
     * @mbggenerated Wed Mar 29 10:56:21 CST 2017
     */
    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column booking.end_time
     *
     * @return the value of booking.end_time
     *
     * @mbggenerated Wed Mar 29 10:56:21 CST 2017
     */
    public Date getEndTime() {
        return endTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column booking.end_time
     *
     * @param endTime the value for booking.end_time
     *
     * @mbggenerated Wed Mar 29 10:56:21 CST 2017
     */
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column booking.time_flag
     *
     * @return the value of booking.time_flag
     *
     * @mbggenerated Wed Mar 29 10:56:21 CST 2017
     */
    public String getTimeFlag() {
        return timeFlag;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column booking.time_flag
     *
     * @param timeFlag the value for booking.time_flag
     *
     * @mbggenerated Wed Mar 29 10:56:21 CST 2017
     */
    public void setTimeFlag(String timeFlag) {
        this.timeFlag = timeFlag == null ? null : timeFlag.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column booking.booking_pid
     *
     * @return the value of booking.booking_pid
     *
     * @mbggenerated Wed Mar 29 10:56:21 CST 2017
     */
    public Long getBookingPid() {
        return bookingPid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column booking.booking_pid
     *
     * @param bookingPid the value for booking.booking_pid
     *
     * @mbggenerated Wed Mar 29 10:56:21 CST 2017
     */
    public void setBookingPid(Long bookingPid) {
        this.bookingPid = bookingPid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column booking.booking_person
     *
     * @return the value of booking.booking_person
     *
     * @mbggenerated Wed Mar 29 10:56:21 CST 2017
     */
    public String getBookingPerson() {
        return bookingPerson;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column booking.booking_person
     *
     * @param bookingPerson the value for booking.booking_person
     *
     * @mbggenerated Wed Mar 29 10:56:21 CST 2017
     */
    public void setBookingPerson(String bookingPerson) {
        this.bookingPerson = bookingPerson == null ? null : bookingPerson.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column booking.booking_phone
     *
     * @return the value of booking.booking_phone
     *
     * @mbggenerated Wed Mar 29 10:56:21 CST 2017
     */
    public String getBookingPhone() {
        return bookingPhone;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column booking.booking_phone
     *
     * @param bookingPhone the value for booking.booking_phone
     *
     * @mbggenerated Wed Mar 29 10:56:21 CST 2017
     */
    public void setBookingPhone(String bookingPhone) {
        this.bookingPhone = bookingPhone == null ? null : bookingPhone.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column booking.create_time
     *
     * @return the value of booking.create_time
     *
     * @mbggenerated Wed Mar 29 10:56:21 CST 2017
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column booking.create_time
     *
     * @param createTime the value for booking.create_time
     *
     * @mbggenerated Wed Mar 29 10:56:21 CST 2017
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getInstrumentId() {
        return instrumentId;
    }

    public void setInstrumentId(Integer instrumentId) {
        this.instrumentId = instrumentId;
    }
}