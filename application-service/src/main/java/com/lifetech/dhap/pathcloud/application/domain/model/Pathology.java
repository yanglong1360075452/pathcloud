package com.lifetech.dhap.pathcloud.application.domain.model;

import java.io.Serializable;
import java.util.Date;

public class Pathology implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private Integer status;

    private String serialNumber;

    private Long createBy;

    private Long updateBy;

    private Date createTime;

    private Date updateTime;

    private String result;

    //显微所见
    private String microDiagnose;

    //诊断描述
    private String diagnose;

    private String jujianNote;

    private String bingdongNote;

    private Boolean reGrossing;

    private Integer inspectCategory;

    private String patientName;

    private Integer departments;

    private Integer applyType;

    private Long assignGrossing;

    private Long assignDiagnose;

    private String reportPic;

    private String note;

    private Boolean afterFrozen;

    private Boolean outConsult;

    private String outConsultResult;

    private Integer coincidence;

    private Integer label;

    public Integer getLabel() {
        return label;
    }

    public void setLabel(Integer label) {
        this.label = label;
    }

    public Integer getCoincidence() {
        return coincidence;
    }

    public void setCoincidence(Integer coincidence) {
        this.coincidence = coincidence;
    }

    public String getOutConsultResult() {
        return outConsultResult;
    }

    public void setOutConsultResult(String outConsultResult) {
        this.outConsultResult = outConsultResult;
    }

    public Boolean getOutConsult() {
        return outConsult;
    }

    public void setOutConsult(Boolean outConsult) {
        this.outConsult = outConsult;
    }

    public Boolean getAfterFrozen() {
        return afterFrozen;
    }

    public void setAfterFrozen(Boolean afterFrozen) {
        this.afterFrozen = afterFrozen;
    }

    public String getReportPic() {
        return reportPic;
    }

    public void setReportPic(String reportPic) {
        this.reportPic = reportPic;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Long getAssignDiagnose() {
        return assignDiagnose;
    }

    public void setAssignDiagnose(Long assignDiagnose) {
        this.assignDiagnose = assignDiagnose;
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

    public Integer getApplyType() {
        return applyType;
    }

    public void setApplyType(Integer applyType) {
        this.applyType = applyType;
    }

    public Long getAssignGrossing() {
        return assignGrossing;
    }

    public void setAssignGrossing(Long assignGrossing) {
        this.assignGrossing = assignGrossing;
    }

    public Integer getInspectCategory() {
        return inspectCategory;
    }

    public void setInspectCategory(Integer inspectCategory) {
        this.inspectCategory = inspectCategory;
    }

    public Boolean getReGrossing() {
        return reGrossing;
    }

    public void setReGrossing(Boolean reGrossing) {
        this.reGrossing = reGrossing;
    }

    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column pathology.id
     *
     * @param id the value for pathology.id
     *
     * @mbggenerated Mon Nov 21 17:13:40 CST 2016
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column pathology.serial_number
     *
     * @return the value of pathology.serial_number
     *
     * @mbggenerated Mon Nov 21 17:13:40 CST 2016
     */
    public String getSerialNumber() {
        return serialNumber;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column pathology.serial_number
     *
     * @param serialNumber the value for pathology.serial_number
     *
     * @mbggenerated Mon Nov 21 17:13:40 CST 2016
     */
    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber == null ? null : serialNumber.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column pathology.create_by
     *
     * @return the value of pathology.create_by
     *
     * @mbggenerated Mon Nov 21 17:13:40 CST 2016
     */
    public Long getCreateBy() {
        return createBy;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column pathology.create_by
     *
     * @param createBy the value for pathology.create_by
     *
     * @mbggenerated Mon Nov 21 17:13:40 CST 2016
     */
    public void setCreateBy(Long createBy) {
        this.createBy = createBy;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column pathology.update_by
     *
     * @return the value of pathology.update_by
     *
     * @mbggenerated Mon Nov 21 17:13:40 CST 2016
     */
    public Long getUpdateBy() {
        return updateBy;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column pathology.update_by
     *
     * @param updateBy the value for pathology.update_by
     *
     * @mbggenerated Mon Nov 21 17:13:40 CST 2016
     */
    public void setUpdateBy(Long updateBy) {
        this.updateBy = updateBy;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column pathology.create_time
     *
     * @return the value of pathology.create_time
     *
     * @mbggenerated Mon Nov 21 17:13:40 CST 2016
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column pathology.create_time
     *
     * @param createTime the value for pathology.create_time
     *
     * @mbggenerated Mon Nov 21 17:13:40 CST 2016
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column pathology.update_time
     *
     * @return the value of pathology.update_time
     *
     * @mbggenerated Mon Nov 21 17:13:40 CST 2016
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column pathology.update_time
     *
     * @param updateTime the value for pathology.update_time
     *
     * @mbggenerated Mon Nov 21 17:13:40 CST 2016
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column pathology.result
     *
     * @return the value of pathology.result
     *
     * @mbggenerated Mon Nov 21 17:13:40 CST 2016
     */
    public String getResult() {
        return result;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column pathology.result
     *
     * @param result the value for pathology.result
     *
     * @mbggenerated Mon Nov 21 17:13:40 CST 2016
     */
    public void setResult(String result) {
        this.result = result == null ? null : result.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column pathology.jujian_note
     *
     * @return the value of pathology.jujian_note
     *
     * @mbggenerated Mon Nov 21 17:13:40 CST 2016
     */
    public String getJujianNote() {
        return jujianNote;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column pathology.jujian_note
     *
     * @param jujianNote the value for pathology.jujian_note
     *
     * @mbggenerated Mon Nov 21 17:13:40 CST 2016
     */
    public void setJujianNote(String jujianNote) {
        this.jujianNote = jujianNote == null ? null : jujianNote.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column pathology.bingdong_note
     *
     * @return the value of pathology.bingdong_note
     *
     * @mbggenerated Mon Nov 21 17:13:40 CST 2016
     */
    public String getBingdongNote() {
        return bingdongNote;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column pathology.bingdong_note
     *
     * @param bingdongNote the value for pathology.bingdong_note
     *
     * @mbggenerated Mon Nov 21 17:13:40 CST 2016
     */
    public void setBingdongNote(String bingdongNote) {
        this.bingdongNote = bingdongNote == null ? null : bingdongNote.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
    public String getMicroDiagnose() {
        return microDiagnose;
    }

    public void setMicroDiagnose(String microDiagnose) {
        this.microDiagnose = microDiagnose;
    }

    public String getDiagnose() {
        return diagnose;
    }

    public void setDiagnose(String diagnose) {
        this.diagnose = diagnose;
    }
}