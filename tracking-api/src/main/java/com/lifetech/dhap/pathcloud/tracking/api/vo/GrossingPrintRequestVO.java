package com.lifetech.dhap.pathcloud.tracking.api.vo;

import java.util.List;

/**
 * Created by LiuMei on 2017-08-30.
 */
public class GrossingPrintRequestVO {

    private List<GrossingPrintVO> printData;

    private Boolean handle;

    public List<GrossingPrintVO> getPrintData() {
        return printData;
    }

    public void setPrintData(List<GrossingPrintVO> printData) {
        this.printData = printData;
    }

    public Boolean getHandle() {
        return handle;
    }

    public void setHandle(Boolean handle) {
        this.handle = handle;
    }
}
