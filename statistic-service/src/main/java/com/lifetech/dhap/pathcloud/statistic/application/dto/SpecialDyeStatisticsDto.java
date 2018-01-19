package com.lifetech.dhap.pathcloud.statistic.application.dto;

/**
 * Created by LiuMei on 2017-02-16.
 */
public class SpecialDyeStatisticsDto {

    private int month;

    private long normal;

    private long special;

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public long getNormal() {
        return normal;
    }

    public void setNormal(long normal) {
        this.normal = normal;
    }

    public long getSpecial() {
        return special;
    }

    public void setSpecial(long special) {
        this.special = special;
    }
}
