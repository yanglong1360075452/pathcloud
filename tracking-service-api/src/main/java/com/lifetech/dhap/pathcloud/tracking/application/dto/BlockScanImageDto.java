package com.lifetech.dhap.pathcloud.tracking.application.dto;

import java.io.Serializable;
import java.util.Date;

public class BlockScanImageDto implements Serializable {

    private Long batchId;

    private String imagePath;

    private Integer imageSize;

    private String imageName;

    private String errorBlocks;

    private Date createTime;

    private Integer scanLocation;

    public Integer getScanLocation() {
        return scanLocation;
    }

    public void setScanLocation(Integer scanLocation) {
        this.scanLocation = scanLocation;
    }

    public Long getBatchId() {
        return batchId;
    }

    public void setBatchId(Long batchId) {
        this.batchId = batchId;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public Integer getImageSize() {
        return imageSize;
    }

    public void setImageSize(Integer imageSize) {
        this.imageSize = imageSize;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getErrorBlocks() {
        return errorBlocks;
    }

    public void setErrorBlocks(String errorBlocks) {
        this.errorBlocks = errorBlocks;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}