package com.lifetech.dhap.pathcloud.tracking.application.dto;

import java.util.List;

/**
 * @author eric.guo@ostengar.com
 * @version 1.0
 * @time 2016-12-26-10:45
 */
public class GrossingFileDto {

    private List<String> images;//图片列表

    private String video;//录像文件

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }
}
