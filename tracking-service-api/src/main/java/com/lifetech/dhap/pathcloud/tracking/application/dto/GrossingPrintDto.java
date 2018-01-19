package com.lifetech.dhap.pathcloud.tracking.application.dto;

import java.util.List;

/**
 * Created by LiuMei on 2017-08-30.
 */
public class GrossingPrintDto {

    private String pathNo;

    private List<String> blockSubIds;

    public String getPathNo() {
        return pathNo;
    }

    public void setPathNo(String pathNo) {
        this.pathNo = pathNo;
    }

    public List<String> getBlockSubIds() {
        return blockSubIds;
    }

    public void setBlockSubIds(List<String> blockSubIds) {
        this.blockSubIds = blockSubIds;
    }

}
