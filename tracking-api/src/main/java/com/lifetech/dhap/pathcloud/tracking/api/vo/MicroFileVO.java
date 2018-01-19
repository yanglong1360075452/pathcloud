package com.lifetech.dhap.pathcloud.tracking.api.vo;

/**
 * Created by LiuMei on 2017-01-18.
 */
public class MicroFileVO {

    private Long id;

    private Long pathologyId;

    private Integer type;

    private String url;

    private Boolean keepFlag;

    private String tag;

    private Boolean check;

    public Boolean getCheck() {
        return check;
    }

    public void setCheck(Boolean check) {
        this.check = check;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPathologyId() {
        return pathologyId;
    }

    public void setPathologyId(Long pathologyId) {
        this.pathologyId = pathologyId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Boolean getKeepFlag() {
        return keepFlag;
    }

    public void setKeepFlag(Boolean keepFlag) {
        this.keepFlag = keepFlag;
    }
}
