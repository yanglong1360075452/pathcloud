package com.lifetech.dhap.pathcloud.tracking.api.vo;

import java.util.List;

/**
 * Created by LiuMei on 2017-06-07.
 */
public class SlideArchiveConfirmVO {

    private Integer archivingMethod; //存档方式 晾片或长期保存

    private String archivingNo; //晾片架编号或抽屉编号

    private List<ProductionSaveVO> archiveSlides;

    public Integer getArchivingMethod() {
        return archivingMethod;
    }

    public void setArchivingMethod(Integer archivingMethod) {
        this.archivingMethod = archivingMethod;
    }

    public String getArchivingNo() {
        return archivingNo;
    }

    public void setArchivingNo(String archivingNo) {
        this.archivingNo = archivingNo;
    }

    public List<ProductionSaveVO> getArchiveSlides() {
        return archiveSlides;
    }

    public void setArchiveSlides(List<ProductionSaveVO> archiveSlides) {
        this.archiveSlides = archiveSlides;
    }
}
