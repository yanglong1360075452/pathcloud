package com.lifetech.dhap.pathcloud.tracking.api.vo;

/**
 * Created by LiuMei on 2017-03-06.
 */
public class BlockCountVO {

    private long blockTotal; //蜡块总数

    private long hadOperated; //已操作总数

    private long todayTotal;    //今日登记总数

    private long todayOperated; //今日已操作总数

    private long prepareOperate; //待操作总数

    public long getBlockTotal() {
        return blockTotal;
    }

    public void setBlockTotal(long blockTotal) {
        this.blockTotal = blockTotal;
    }

    public long getHadOperated() {
        return hadOperated;
    }

    public void setHadOperated(long hadOperated) {
        this.hadOperated = hadOperated;
    }

    public long getTodayTotal() {
        return todayTotal;
    }

    public void setTodayTotal(long todayTotal) {
        this.todayTotal = todayTotal;
    }

    public long getTodayOperated() {
        return todayOperated;
    }

    public void setTodayOperated(long todayOperated) {
        this.todayOperated = todayOperated;
    }

    public long getPrepareOperate() {
        return prepareOperate;
    }

    public void setPrepareOperate(long prepareOperate) {
        this.prepareOperate = prepareOperate;
    }
}
