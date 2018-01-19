package com.lifetech.dhap.pathcloud.tracking.api.vo;

import java.io.Serializable;
import java.util.List;

/**
 * Created by LiuMei on 2017-01-03.
 */
public class SlideDistributeVO implements Serializable {

    private List<Long> distributeRecords;

    private String distributeDoctor;

    private Long doctorId;

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

    public Long getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Long doctorId) {
        this.doctorId = doctorId;
    }
}
