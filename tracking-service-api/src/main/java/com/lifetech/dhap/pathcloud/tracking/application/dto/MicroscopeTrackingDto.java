package com.lifetech.dhap.pathcloud.tracking.application.dto;

import java.io.Serializable;
import java.util.Date;

public class MicroscopeTrackingDto implements Serializable {

    private Long id;

    private Date startTime;

    private Date endTime;

    private Long bookingPid;

    private Integer microscopeId;

    private String microscope;

    private String firstName;

    private String wno;

    private String phone;

    private Integer departments;

    private String departmentsName;

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

    public Long getBookingPid() {
        return bookingPid;
    }

    public void setBookingPid(Long bookingPid) {
        this.bookingPid = bookingPid;
    }

    public Integer getMicroscopeId() {
        return microscopeId;
    }

    public void setMicroscopeId(Integer microscopeId) {
        this.microscopeId = microscopeId;
    }

    public String getMicroscope() {
        return microscope;
    }

    public void setMicroscope(String microscope) {
        this.microscope = microscope;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getWno() {
        return wno;
    }

    public void setWno(String wno) {
        this.wno = wno;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDepartmentsName() {
        return departmentsName;
    }

    public void setDepartmentsName(String departmentsName) {
        this.departmentsName = departmentsName;
    }

    public Integer getDepartments() {
        return departments;
    }

    public void setDepartments(Integer departments) {
        this.departments = departments;
    }
}