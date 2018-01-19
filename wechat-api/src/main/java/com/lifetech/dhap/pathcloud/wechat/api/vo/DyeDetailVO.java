package com.lifetech.dhap.pathcloud.wechat.api.vo;

import java.util.Date;

/**
 * Created by LiuMei on 2017-06-08.
 */
public class DyeDetailVO {
    private Long id;

    private String pathNo;

    private String blockSubId;

    private String slideSubId;

    private Integer dyeCategory;
    private String dyeCategoryDesc;

    private String ihcMarker;

    private Date operateTime;

    public String getBlockSubId() {
        return blockSubId;
    }

    public void setBlockSubId(String blockSubId) {
        this.blockSubId = blockSubId;
    }

    public String getSlideSubId() {
        return slideSubId;
    }

    public void setSlideSubId(String slideSubId) {
        this.slideSubId = slideSubId;
    }

    public String getDyeCategoryDesc() {
        return dyeCategoryDesc;
    }

    public void setDyeCategoryDesc(String dyeCategoryDesc) {
        this.dyeCategoryDesc = dyeCategoryDesc;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPathNo() {
        return pathNo;
    }

    public void setPathNo(String pathNo) {
        this.pathNo = pathNo;
    }

    public String getIhcMarker() {
        return ihcMarker;
    }

    public void setIhcMarker(String ihcMarker) {
        this.ihcMarker = ihcMarker;
    }

    public Date getOperateTime() {
        return operateTime;
    }

    public void setOperateTime(Date operateTime) {
        this.operateTime = operateTime;
    }

    public Integer getDyeCategory() {
        return dyeCategory;
    }

    public void setDyeCategory(Integer dyeCategory) {
        this.dyeCategory = dyeCategory;
    }
}
