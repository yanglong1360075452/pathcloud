package com.lifetech.dhap.pathcloud.tracking.application.dto;

import java.util.Date;

/**
 * Created by HP on 2017/3/6.
 */
public class InfoQueryDto {

    private Long id; //玻片id

    private String serialNumber;//病理号

    private String blockSerialNumber; //蜡块唯一号

    private String subId; //蜡块号

    private String slideId; //玻片号

    private String patientName; //病人姓名

    private Integer departments; //送检科室

    private Long grossingDoctorId;
    private String grossingDoctorName; //取材医生姓名

    private String bodyPart;//取材部位

    private Integer count;//组织数

    private Integer biaoShi;
    private String biaoShiName;

    private Long operatorDoctorId;//操作员
    private String operatorDoctorName;

    private Date operateTime;

    private String note;//包埋备注
    private String other;//包埋备注其他信息

    private Integer status;
    private String statusName;

    private Long dyeInstrumentId;
    private String dyeInstrumentName;


    private Long mountingId;
    private String mountingName;

    private Long mountingInstrumentId;
    private String mountingInstrumentName;

    private Date mountingTime;

    private Long blockId;  //蜡块ID

    private Integer print; //打印次数

    public Long getBlockId() {
        return blockId;
    }

    public void setBlockId(Long blockId) {
        this.blockId = blockId;
    }

    public Integer getPrint() {
        return print;
    }

    public void setPrint(Integer print) {
        this.print = print;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getBlockSerialNumber() {
        return blockSerialNumber;
    }

    public void setBlockSerialNumber(String blockSerialNumber) {
        this.blockSerialNumber = blockSerialNumber;
    }

    public String getSubId() {
        return subId;
    }

    public void setSubId(String subId) {
        this.subId = subId;
    }

    public String getSlideId() {
        return slideId;
    }

    public void setSlideId(String slideId) {
        this.slideId = slideId;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public Integer getDepartments() {
        return departments;
    }

    public void setDepartments(Integer departments) {
        this.departments = departments;
    }

    public Long getGrossingDoctorId() {
        return grossingDoctorId;
    }

    public void setGrossingDoctorId(Long grossingDoctorId) {
        this.grossingDoctorId = grossingDoctorId;
    }

    public String getGrossingDoctorName() {
        return grossingDoctorName;
    }

    public void setGrossingDoctorName(String grossingDoctorName) {
        this.grossingDoctorName = grossingDoctorName;
    }

    public String getBodyPart() {
        return bodyPart;
    }

    public void setBodyPart(String bodyPart) {
        this.bodyPart = bodyPart;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getBiaoShi() {
        return biaoShi;
    }

    public void setBiaoShi(Integer biaoShi) {
        this.biaoShi = biaoShi;
    }

    public String getBiaoShiName() {
        return biaoShiName;
    }

    public void setBiaoShiName(String biaoShiName) {
        this.biaoShiName = biaoShiName;
    }

    public Long getEmbedDoctorId() {
        return operatorDoctorId;
    }

    public void setEmbedDoctorId(Long operatorDoctorId) {
        this.operatorDoctorId = operatorDoctorId;
    }

    public String getEmbedDoctorName() {
        return operatorDoctorName;
    }

    public void setEmbedDoctorName(String operatorDoctorName) {
        this.operatorDoctorName = operatorDoctorName;
    }

    public Date getEmbedTime() {
        return operateTime;
    }

    public void setEmbedTime(Date operateTime) {
        this.operateTime = operateTime;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public Long getDyeInstrumentId() {
        return dyeInstrumentId;
    }

    public void setDyeInstrumentId(Long dyeInstrumentId) {
        this.dyeInstrumentId = dyeInstrumentId;
    }

    public String getDyeInstrumentName() {
        return dyeInstrumentName;
    }

    public void setDyeInstrumentName(String dyeInstrumentName) {
        this.dyeInstrumentName = dyeInstrumentName;
    }

    public Long getMountingId() {
        return mountingId;
    }

    public void setMountingId(Long mountingId) {
        this.mountingId = mountingId;
    }

    public String getMountingName() {
        return mountingName;
    }

    public void setMountingName(String mountingName) {
        this.mountingName = mountingName;
    }

    public Long getMountingInstrumentId() {
        return mountingInstrumentId;
    }

    public void setMountingInstrumentId(Long mountingInstrumentId) {
        this.mountingInstrumentId = mountingInstrumentId;
    }

    public String getMountingInstrumentName() {
        return mountingInstrumentName;
    }

    public void setMountingInstrumentName(String mountingInstrumentName) {
        this.mountingInstrumentName = mountingInstrumentName;
    }

    public Date getMountingTime() {
        return mountingTime;
    }

    public void setMountingTime(Date mountingTime) {
        this.mountingTime = mountingTime;
    }
}
