package com.lifetech.dhap.pathcloud.application.application.dto;

import java.util.Date;
import java.util.List;

/**
 * Created by HP on 2017/5/31.
 */
public class WechatInfoDto {

    private Long pid; //病理ID

    private Long aid; //申请ID

    private Date applicationTime; //申请时间

    private  String aserialNumber; //申请号

    private String pserialNumber; //病理号

    private Date bookingStartTime; //预约的开始时间

    private Date bookingEndTime; //预约的结束时间


    private Integer instrumentId;
    private String instrumentIdDesc;

    private Integer pstatus;  //病理状态
    private String pstatusDesc;

    private Long bookingId;

    private Integer astatus; //申请状态
    private String astatusDesc;

    private Long aiId; //特殊染色id

    private Date aiApplicationTime;  //特染時間

    private List<String> pserialNumbers;

    private Integer specialDyeStatus;
    private String specialDyeStatusDesc;

    public Integer getSpecialDyeStatus() {
        return specialDyeStatus;
    }

    public void setSpecialDyeStatus(Integer specialDyeStatus) {
        this.specialDyeStatus = specialDyeStatus;
    }

    public String getSpecialDyeStatusDesc() {
        return specialDyeStatusDesc;
    }

    public void setSpecialDyeStatusDesc(String specialDyeStatusDesc) {
        this.specialDyeStatusDesc = specialDyeStatusDesc;
    }

    public List<String> getPserialNumbers() {
        return pserialNumbers;
    }

    public void setPserialNumbers(List<String> pserialNumbers) {
        this.pserialNumbers = pserialNumbers;
    }


    public Long getAiId() {
        return aiId;
    }

    public void setAiId(Long aiId) {
        this.aiId = aiId;
    }

    public Date getAiApplicationTime() {
        return aiApplicationTime;
    }

    public void setAiApplicationTime(Date aiApplicationTime) {
        this.aiApplicationTime = aiApplicationTime;
    }

    public String getAstatusDesc() {
        return astatusDesc;
    }

    public void setAstatusDesc(String astatusDesc) {
        this.astatusDesc = astatusDesc;
    }

    public Long getAid() {
        return aid;
    }

    public void setAid(Long aid) {
        this.aid = aid;
    }

    public Integer getPstatus() {
        return pstatus;
    }

    public void setPstatus(Integer pstatus) {
        this.pstatus = pstatus;
    }

    public String getPstatusDesc() {
        return pstatusDesc;
    }

    public void setPstatusDesc(String pstatusDesc) {
        this.pstatusDesc = pstatusDesc;
    }

    public Integer getAstatus() {
        return astatus;
    }

    public void setAstatus(Integer astatus) {
        this.astatus = astatus;
    }

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public Long getBookingId() {
        return bookingId;
    }

    public void setBookingId(Long bookingId) {
        this.bookingId = bookingId;
    }

    public Date getApplicationTime() {
        return applicationTime;
    }

    public void setApplicationTime(Date applicationTime) {
        this.applicationTime = applicationTime;
    }

    public String getAserialNumber() {
        return aserialNumber;
    }

    public void setAserialNumber(String aserialNumber) {
        this.aserialNumber = aserialNumber;
    }

    public String getPserialNumber() {
        return pserialNumber;
    }

    public void setPserialNumber(String pserialNumber) {
        this.pserialNumber = pserialNumber;
    }

    public Date getBookingStartTime() {
        return bookingStartTime;
    }

    public void setBookingStartTime(Date bookingStartTime) {
        this.bookingStartTime = bookingStartTime;
    }

    public Date getBookingEndTime() {
        return bookingEndTime;
    }

    public void setBookingEndTime(Date bookingEndTime) {
        this.bookingEndTime = bookingEndTime;
    }

    public Integer getInstrumentId() {
        return instrumentId;
    }

    public void setInstrumentId(Integer instrumentId) {
        this.instrumentId = instrumentId;
    }

    public String getInstrumentIdDesc() {
        return instrumentIdDesc;
    }

    public void setInstrumentIdDesc(String instrumentIdDesc) {
        this.instrumentIdDesc = instrumentIdDesc;
    }


}
