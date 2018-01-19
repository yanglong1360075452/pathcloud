package com.lifetech.dhap.pathcloud.tracking.application.condition;

import com.lifetech.dhap.pathcloud.common.base.PageCondition;

import java.util.List;

/**
 * Created by HP on 2016/12/29.
 */
public class ProductionCon extends PageCondition {

    private List<String> blockSerialNumbers;

    private String pathologySerialNumber;

    private String subId;


    public String getSubId() {
        return subId;
    }

    public void setSubId(String subId) {
        this.subId = subId;
    }

    public String getPathologySerialNumber() {
        return pathologySerialNumber;
    }

    public void setPathologySerialNumber(String pathologySerialNumber) {
        this.pathologySerialNumber = pathologySerialNumber;
    }

    public List<String> getBlockSerialNumbers() {
        return blockSerialNumbers;
    }

    public void setBlockSerialNumbers(List<String> blockSerialNumbers) {
        this.blockSerialNumbers = blockSerialNumbers;
    }

}
