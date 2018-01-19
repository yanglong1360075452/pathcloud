package com.lifetech.dhap.pathcloud.statistic.application.condition;

import java.util.Date;

/**
 * Created by LiuMei on 2017-02-16.
 */
public class StatisticsCondition {

    private Date startTime;

    private Date endTime;

    private Integer station;

    private String step;

    private Integer score;

    public String getStep() {
        return step;
    }

    public void setStep(String step) {
        this.step = step;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
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

    public Integer getStation() {
        return station;
    }

    public void setStation(Integer station) {
        this.station = station;
    }
}
