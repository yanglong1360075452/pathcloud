package com.lifetech.dhap.pathcloud.tracking.api.vo;

import java.util.List;

/**
 * @author LiuMei
 * @date 2017-12-22.
 */
public class FrozenGrossingVO {

    private String frozenNumber;

    private String bingdongNote;

    private List<String> images;

    public String getFrozenNumber() {
        return frozenNumber;
    }

    public void setFrozenNumber(String frozenNumber) {
        this.frozenNumber = frozenNumber;
    }

    public String getBingdongNote() {
        return bingdongNote;
    }

    public void setBingdongNote(String bingdongNote) {
        this.bingdongNote = bingdongNote;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }
}
