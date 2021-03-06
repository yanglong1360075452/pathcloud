package com.lifetech.dhap.pathcloud.tracking.domain.model;

import java.io.Serializable;
import java.util.Date;

public class MicroscopeTracking implements Serializable {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column microscope_tracking.id
     *
     * @mbggenerated Thu Jun 22 10:02:01 CST 2017
     */
    private Long id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column microscope_tracking.start_time
     *
     * @mbggenerated Thu Jun 22 10:02:01 CST 2017
     */
    private Date startTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column microscope_tracking.end_time
     *
     * @mbggenerated Thu Jun 22 10:02:01 CST 2017
     */
    private Date endTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column microscope_tracking.booking_pid
     *
     * @mbggenerated Thu Jun 22 10:02:01 CST 2017
     */
    private Long bookingPid;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column microscope_tracking.microscope_id
     *
     * @mbggenerated Thu Jun 22 10:02:01 CST 2017
     */
    private Integer microscopeId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column microscope_tracking.microscope
     *
     * @mbggenerated Thu Jun 22 10:02:01 CST 2017
     */
    private String microscope;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table microscope_tracking
     *
     * @mbggenerated Thu Jun 22 10:02:01 CST 2017
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column microscope_tracking.id
     *
     * @return the value of microscope_tracking.id
     *
     * @mbggenerated Thu Jun 22 10:02:01 CST 2017
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column microscope_tracking.id
     *
     * @param id the value for microscope_tracking.id
     *
     * @mbggenerated Thu Jun 22 10:02:01 CST 2017
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column microscope_tracking.start_time
     *
     * @return the value of microscope_tracking.start_time
     *
     * @mbggenerated Thu Jun 22 10:02:01 CST 2017
     */
    public Date getStartTime() {
        return startTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column microscope_tracking.start_time
     *
     * @param startTime the value for microscope_tracking.start_time
     *
     * @mbggenerated Thu Jun 22 10:02:01 CST 2017
     */
    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column microscope_tracking.end_time
     *
     * @return the value of microscope_tracking.end_time
     *
     * @mbggenerated Thu Jun 22 10:02:01 CST 2017
     */
    public Date getEndTime() {
        return endTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column microscope_tracking.end_time
     *
     * @param endTime the value for microscope_tracking.end_time
     *
     * @mbggenerated Thu Jun 22 10:02:01 CST 2017
     */
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column microscope_tracking.booking_pid
     *
     * @return the value of microscope_tracking.booking_pid
     *
     * @mbggenerated Thu Jun 22 10:02:01 CST 2017
     */
    public Long getBookingPid() {
        return bookingPid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column microscope_tracking.booking_pid
     *
     * @param bookingPid the value for microscope_tracking.booking_pid
     *
     * @mbggenerated Thu Jun 22 10:02:01 CST 2017
     */
    public void setBookingPid(Long bookingPid) {
        this.bookingPid = bookingPid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column microscope_tracking.microscope_id
     *
     * @return the value of microscope_tracking.microscope_id
     *
     * @mbggenerated Thu Jun 22 10:02:01 CST 2017
     */
    public Integer getMicroscopeId() {
        return microscopeId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column microscope_tracking.microscope_id
     *
     * @param microscopeId the value for microscope_tracking.microscope_id
     *
     * @mbggenerated Thu Jun 22 10:02:01 CST 2017
     */
    public void setMicroscopeId(Integer microscopeId) {
        this.microscopeId = microscopeId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column microscope_tracking.microscope
     *
     * @return the value of microscope_tracking.microscope
     *
     * @mbggenerated Thu Jun 22 10:02:01 CST 2017
     */
    public String getMicroscope() {
        return microscope;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column microscope_tracking.microscope
     *
     * @param microscope the value for microscope_tracking.microscope
     *
     * @mbggenerated Thu Jun 22 10:02:01 CST 2017
     */
    public void setMicroscope(String microscope) {
        this.microscope = microscope == null ? null : microscope.trim();
    }
}