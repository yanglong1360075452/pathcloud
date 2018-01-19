package com.lifetech.dhap.pathcloud.tracking.api.vo;

import java.util.List;

/**
 * Created by LiuMei on 2017-08-30.
 */
public class GrossingPrintVO {

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
