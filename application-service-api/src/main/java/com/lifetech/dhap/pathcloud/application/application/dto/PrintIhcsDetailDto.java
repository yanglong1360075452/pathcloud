package com.lifetech.dhap.pathcloud.application.application.dto;

/**
 * Created by HP on 2017/10/16.
 */
public class PrintIhcsDetailDto {

    private Long blockIhcId;

    /**
     * 玻片Id
     */
    private Long  slideId;

    /**
     * 蜡块ID
     */
    private Long blockId;

    private String number;

    private String serialNumber;

    private String subId;

    private Integer specialDye;
    private String specialDyeDesc;

    private String reagent;

    private String  applyDoctor;

    private String processMode;

    private String slideClaim;

    private String contrastClaim;

    private String note;

    private Integer count;

    private String marker;

    private  String bmarker;

    private Long applyId;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Long getApplyId() {
        return applyId;
    }

    public void setApplyId(Long applyId) {
        this.applyId = applyId;
    }

    public String getBmarker() {
        return bmarker;
    }

    public void setBmarker(String bmarker) {
        this.bmarker = bmarker;
    }

    public Long getSlideId() {
        return slideId;
    }

    public void setSlideId(Long slideId) {
        this.slideId = slideId;
    }

    public Integer getSpecialDye() {
        return specialDye;
    }

    public void setSpecialDye(Integer specialDye) {
        this.specialDye = specialDye;
    }

    public String getSpecialDyeDesc() {
        return specialDyeDesc;
    }

    public void setSpecialDyeDesc(String specialDyeDesc) {
        this.specialDyeDesc = specialDyeDesc;
    }

    public String getMarker() {
        return marker;
    }

    public void setMarker(String marker) {
        this.marker = marker;
    }


    public Long getBlockIhcId() {
        return blockIhcId;
    }

    public void setBlockIhcId(Long blockIhcId) {
        this.blockIhcId = blockIhcId;
    }

    public Long getBlockId() {
        return blockId;
    }

    public void setBlockId(Long blockId) {
        this.blockId = blockId;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getSubId() {
        return subId;
    }

    public void setSubId(String subId) {
        this.subId = subId;
    }

    public String getReagent() {
        return reagent;
    }

    public void setReagent(String reagent) {
        this.reagent = reagent;
    }

    public String getApplyDoctor() {
        return applyDoctor;
    }

    public void setApplyDoctor(String applyDoctor) {
        this.applyDoctor = applyDoctor;
    }

    public String getProcessMode() {
        return processMode;
    }

    public void setProcessMode(String processMode) {
        this.processMode = processMode;
    }

    public String getSlideClaim() {
        return slideClaim;
    }

    public void setSlideClaim(String slideClaim) {
        this.slideClaim = slideClaim;
    }

    public String getContrastClaim() {
        return contrastClaim;
    }

    public void setContrastClaim(String contrastClaim) {
        this.contrastClaim = contrastClaim;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
