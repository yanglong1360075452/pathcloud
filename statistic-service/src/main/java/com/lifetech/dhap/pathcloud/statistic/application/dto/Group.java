package com.lifetech.dhap.pathcloud.statistic.application.dto;

/**
 * Created by HP on 2017/2/16.
 */
public class Group {

    private Long pathNum;

    private Long blockNum;

    public Group(){
        this.blockNum = 0L;
        this.pathNum = 0L;
    }

    public Long getPathNum() {
        return pathNum;
    }

    public void setPathNum(Long pathNum) {
        this.pathNum = pathNum;
    }

    public Long getBlockNum() {
        return blockNum;
    }

    public void setBlockNum(Long blockNum) {
        this.blockNum = blockNum;
    }
}
