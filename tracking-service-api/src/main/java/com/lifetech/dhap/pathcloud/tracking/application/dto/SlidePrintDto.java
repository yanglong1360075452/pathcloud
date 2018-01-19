package com.lifetech.dhap.pathcloud.tracking.application.dto;

/**
 * @author LiuMei
 * @date 2017-10-17.
 */
public class SlidePrintDto {

    private String pathNo;

    private String blockSubId;

    private String slideSubId;

    private String marker;

    private String grossingBody;

    private Long slideId;

    private Long applyId;

    private Integer specialDye;

    private String number;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Integer getSpecialDye() {
        return specialDye;
    }

    public void setSpecialDye(Integer specialDye) {
        this.specialDye = specialDye;
    }

    public Long getApplyId() {
        return applyId;
    }

    public void setApplyId(Long applyId) {
        this.applyId = applyId;
    }

    public Long getSlideId() {
        return slideId;
    }

    public void setSlideId(Long slideId) {
        this.slideId = slideId;
    }

    public String getPathNo() {
        return pathNo;
    }

    public void setPathNo(String pathNo) {
        this.pathNo = pathNo;
    }

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

    public String getMarker() {
        return marker;
    }

    public void setMarker(String marker) {
        this.marker = marker;
    }

    public String getGrossingBody() {
        return grossingBody;
    }

    public void setGrossingBody(String grossingBody) {
        this.grossingBody = grossingBody;
    }

    @Override
    public String toString() {
        return "SlidePrintDto{" +
                "pathNo='" + pathNo + '\'' +
                ", blockSubId='" + blockSubId + '\'' +
                ", slideSubId='" + slideSubId + '\'' +
                ", marker='" + marker + '\'' +
                ", grossingBody='" + grossingBody + '\'' +
                '}';
    }
}
