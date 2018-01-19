package com.lifetech.dhap.pathcloud.tracking.api.vo;

import java.util.List;

/**
 * Created by LiuMei on 2017-08-30.
 */
public class GrossingPrintResponseVO {

    List<String> hadBlocksPathNos;

    List<String> inExistencePathNos;

    List<GrossingPrintVO> printInfo;

    public List<String> getHadBlocksPathNos() {
        return hadBlocksPathNos;
    }

    public void setHadBlocksPathNos(List<String> hadBlocksPathNos) {
        this.hadBlocksPathNos = hadBlocksPathNos;
    }

    public List<String> getInExistencePathNos() {
        return inExistencePathNos;
    }

    public void setInExistencePathNos(List<String> inExistencePathNos) {
        this.inExistencePathNos = inExistencePathNos;
    }

    public List<GrossingPrintVO> getPrintInfo() {
        return printInfo;
    }

    public void setPrintInfo(List<GrossingPrintVO> printInfo) {
        this.printInfo = printInfo;
    }
}
