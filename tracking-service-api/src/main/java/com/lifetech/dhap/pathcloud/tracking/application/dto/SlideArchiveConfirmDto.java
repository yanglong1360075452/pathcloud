package com.lifetech.dhap.pathcloud.tracking.application.dto;

import java.util.List;

/**
 * Created by LiuMei on 2017-06-07.
 */
public class SlideArchiveConfirmDto {

    private Integer archivingMethod; //存档方式 晾片-31 或 长期保存-14

    private String archivingNo; //晾片架编号或抽屉编号

    private List<Long> slideIds;

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

    public List<Long> getSlideIds() {
        return slideIds;
    }

    public void setSlideIds(List<Long> slideIds) {
        this.slideIds = slideIds;
    }
}
