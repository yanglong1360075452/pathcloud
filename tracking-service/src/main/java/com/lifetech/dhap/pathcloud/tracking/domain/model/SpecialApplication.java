package com.lifetech.dhap.pathcloud.tracking.domain.model;

import java.io.Serializable;
import java.util.Date;

public class SpecialApplication implements Serializable {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column special_application.id
     *
     * @mbggenerated Mon Sep 25 15:51:40 CST 2017
     */
    private Long id;

    private Long causeId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column special_application.path_no
     *
     * @mbggenerated Mon Sep 25 15:51:40 CST 2017
     */
    private String pathNo;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column special_application.number
     *
     * @mbggenerated Mon Sep 25 15:51:40 CST 2017
     */
    private String number;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column special_application.type
     *
     * @mbggenerated Mon Sep 25 15:51:40 CST 2017
     */
    private Integer type;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column special_application.note
     *
     * @mbggenerated Mon Sep 25 15:51:40 CST 2017
     */
    private String note;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column special_application.status
     *
     * @mbggenerated Mon Sep 25 15:51:40 CST 2017
     */
    private Integer status;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column special_application.create_by
     *
     * @mbggenerated Mon Sep 25 15:51:40 CST 2017
     */
    private Long createBy;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column special_application.update_by
     *
     * @mbggenerated Mon Sep 25 15:51:40 CST 2017
     */
    private Long updateBy;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column special_application.create_time
     *
     * @mbggenerated Mon Sep 25 15:51:40 CST 2017
     */
    private Date createTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column special_application.update_time
     *
     * @mbggenerated Mon Sep 25 15:51:40 CST 2017
     */
    private Date updateTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column special_application.result
     *
     * @mbggenerated Mon Sep 25 15:51:40 CST 2017
     */
    private String result;

    private String diagnose;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column special_application.special_result
     *
     * @mbggenerated Mon Sep 25 15:51:40 CST 2017
     */
    private String specialResult;

    private Boolean outConsult;

    private String reportPic;

    private String outConsultResult;

    private Long assignDiagnose;

    private Long assignGrossing;

    private String bingdongNote;

    public Long getAssignGrossing() {
        return assignGrossing;
    }

    public void setAssignGrossing(Long assignGrossing) {
        this.assignGrossing = assignGrossing;
    }

    public String getBingdongNote() {
        return bingdongNote;
    }

    public void setBingdongNote(String bingdongNote) {
        this.bingdongNote = bingdongNote;
    }

    public Long getAssignDiagnose() {
        return assignDiagnose;
    }

    public void setAssignDiagnose(Long assignDiagnose) {
        this.assignDiagnose = assignDiagnose;
    }

    public String getOutConsultResult() {
        return outConsultResult;
    }

    public void setOutConsultResult(String outConsultResult) {
        this.outConsultResult = outConsultResult;
    }

    public String getReportPic() {
        return reportPic;
    }

    public void setReportPic(String reportPic) {
        this.reportPic = reportPic;
    }

    public Boolean getOutConsult() {
        return outConsult;
    }

    public void setOutConsult(Boolean outConsult) {
        this.outConsult = outConsult;
    }

    public String getDiagnose() {
        return diagnose;
    }

    public void setDiagnose(String diagnose) {
        this.diagnose = diagnose;
    }

    public Long getCauseId() {
        return causeId;
    }

    public void setCauseId(Long causeId) {
        this.causeId = causeId;
    }

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table special_application
     *
     * @mbggenerated Mon Sep 25 15:51:40 CST 2017
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column special_application.id
     *
     * @return the value of special_application.id
     *
     * @mbggenerated Mon Sep 25 15:51:40 CST 2017
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column special_application.id
     *
     * @param id the value for special_application.id
     *
     * @mbggenerated Mon Sep 25 15:51:40 CST 2017
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column special_application.path_no
     *
     * @return the value of special_application.path_no
     *
     * @mbggenerated Mon Sep 25 15:51:40 CST 2017
     */
    public String getPathNo() {
        return pathNo;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column special_application.path_no
     *
     * @param pathNo the value for special_application.path_no
     *
     * @mbggenerated Mon Sep 25 15:51:40 CST 2017
     */
    public void setPathNo(String pathNo) {
        this.pathNo = pathNo == null ? null : pathNo.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column special_application.number
     *
     * @return the value of special_application.number
     *
     * @mbggenerated Mon Sep 25 15:51:40 CST 2017
     */
    public String getNumber() {
        return number;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column special_application.number
     *
     * @param number the value for special_application.number
     *
     * @mbggenerated Mon Sep 25 15:51:40 CST 2017
     */
    public void setNumber(String number) {
        this.number = number == null ? null : number.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column special_application.type
     *
     * @return the value of special_application.type
     *
     * @mbggenerated Mon Sep 25 15:51:40 CST 2017
     */
    public Integer getType() {
        return type;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column special_application.type
     *
     * @param type the value for special_application.type
     *
     * @mbggenerated Mon Sep 25 15:51:40 CST 2017
     */
    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column special_application.note
     *
     * @return the value of special_application.note
     *
     * @mbggenerated Mon Sep 25 15:51:40 CST 2017
     */
    public String getNote() {
        return note;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column special_application.note
     *
     * @param note the value for special_application.note
     *
     * @mbggenerated Mon Sep 25 15:51:40 CST 2017
     */
    public void setNote(String note) {
        this.note = note == null ? null : note.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column special_application.status
     *
     * @return the value of special_application.status
     *
     * @mbggenerated Mon Sep 25 15:51:40 CST 2017
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column special_application.status
     *
     * @param status the value for special_application.status
     *
     * @mbggenerated Mon Sep 25 15:51:40 CST 2017
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column special_application.create_by
     *
     * @return the value of special_application.create_by
     *
     * @mbggenerated Mon Sep 25 15:51:40 CST 2017
     */
    public Long getCreateBy() {
        return createBy;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column special_application.create_by
     *
     * @param createBy the value for special_application.create_by
     *
     * @mbggenerated Mon Sep 25 15:51:40 CST 2017
     */
    public void setCreateBy(Long createBy) {
        this.createBy = createBy;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column special_application.update_by
     *
     * @return the value of special_application.update_by
     *
     * @mbggenerated Mon Sep 25 15:51:40 CST 2017
     */
    public Long getUpdateBy() {
        return updateBy;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column special_application.update_by
     *
     * @param updateBy the value for special_application.update_by
     *
     * @mbggenerated Mon Sep 25 15:51:40 CST 2017
     */
    public void setUpdateBy(Long updateBy) {
        this.updateBy = updateBy;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column special_application.create_time
     *
     * @return the value of special_application.create_time
     *
     * @mbggenerated Mon Sep 25 15:51:40 CST 2017
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column special_application.create_time
     *
     * @param createTime the value for special_application.create_time
     *
     * @mbggenerated Mon Sep 25 15:51:40 CST 2017
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column special_application.update_time
     *
     * @return the value of special_application.update_time
     *
     * @mbggenerated Mon Sep 25 15:51:40 CST 2017
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column special_application.update_time
     *
     * @param updateTime the value for special_application.update_time
     *
     * @mbggenerated Mon Sep 25 15:51:40 CST 2017
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column special_application.result
     *
     * @return the value of special_application.result
     *
     * @mbggenerated Mon Sep 25 15:51:40 CST 2017
     */
    public String getResult() {
        return result;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column special_application.result
     *
     * @param result the value for special_application.result
     *
     * @mbggenerated Mon Sep 25 15:51:40 CST 2017
     */
    public void setResult(String result) {
        this.result = result == null ? null : result.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column special_application.special_result
     *
     * @return the value of special_application.special_result
     *
     * @mbggenerated Mon Sep 25 15:51:40 CST 2017
     */
    public String getSpecialResult() {
        return specialResult;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column special_application.special_result
     *
     * @param specialResult the value for special_application.special_result
     *
     * @mbggenerated Mon Sep 25 15:51:40 CST 2017
     */
    public void setSpecialResult(String specialResult) {
        this.specialResult = specialResult == null ? null : specialResult.trim();
    }
}