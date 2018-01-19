package com.lifetech.dhap.pathcloud.tracking.application.dto;

import com.lifetech.dhap.pathcloud.user.application.dto.UserSimpleDto;

import java.util.Date;
import java.util.List;

/**
 * Created by LiuMei on 2017-01-03.
 */
public class SlideDistributeDto {

    private Date confirmTime;

    private int slideCount;

    private UserSimpleDto confirmUser;

    private List<Long> distributeRecords;

    private String distributeDoctor;

    private Long doctorId;

    private Date distributeTime;

    public Date getConfirmTime() {
        return confirmTime;
    }

    public void setConfirmTime(Date confirmTime) {
        this.confirmTime = confirmTime;
    }

    public int getSlideCount() {
        return slideCount;
    }

    public void setSlideCount(int slideCount) {
        this.slideCount = slideCount;
    }

    public UserSimpleDto getConfirmUser() {
        return confirmUser;
    }

    public void setConfirmUser(UserSimpleDto confirmUser) {
        this.confirmUser = confirmUser;
    }

    public List<Long> getDistributeRecords() {
        return distributeRecords;
    }

    public void setDistributeRecords(List<Long> distributeRecords) {
        this.distributeRecords = distributeRecords;
    }

    public String getDistributeDoctor() {
        return distributeDoctor;
    }

    public void setDistributeDoctor(String distributeDoctor) {
        this.distributeDoctor = distributeDoctor;
    }

    public Date getDistributeTime() {
        return distributeTime;
    }

    public void setDistributeTime(Date distributeTime) {
        this.distributeTime = distributeTime;
    }

    public Long getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Long doctorId) {
        this.doctorId = doctorId;
    }
}
