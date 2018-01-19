package com.lifetech.dhap.pathcloud.application.domain.model;

import java.io.Serializable;
import java.util.Date;

public class Application implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private Long pathologyId;

    private String serialNumber;

    private String number;

    private String hisId;

    private String patientName;

    private String patientNo;

    private String admissionNo;

    private Integer status;

    private String rejectReason;

    private Integer age;

    private Integer sex;

    private Integer maritalStatus;

    private String originPlace;

    private String profession;

    private String patientTel;

    private String address;

    private Integer hospital;

    private String doctor;

    private Integer departments;

    private String doctorTel;

    private String inspectionItem;

    private String visitCat;

    private Boolean gynaecology;

    private Boolean menopause;

    private String menopauseTime;

    private Date menopauseEnd;

    private Float hcg;

    private Boolean urgentFlag;

    private Long createBy;

    private Long updateBy;

    private Date createTime;

    private Date updateTime;

    private String gynaecologyRemark;

    private String clinicalDiagnosis;

    private String medicalSummary;

    private String clinicalFindings;

    private String imagingFindings;

    private String operationFindings;

    private String medicalHistory;

    private String historyMedicine;

    private String historyPathology;

    private String reasonType;

    private String research;

    private Integer applyType;

    private String applicant;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Long getPathologyId() {
        return pathologyId;
    }

    public void setPathologyId(Long pathologyId) {
        this.pathologyId = pathologyId;
    }

    public String getApplicant() {
        return applicant;
    }

    public void setApplicant(String applicant) {
        this.applicant = applicant;
    }

    public Integer getApplyType() {
        return applyType;
    }

    public void setApplyType(Integer applyType) {
        this.applyType = applyType;
    }

    public String getReasonType() {
        return reasonType;
    }

    public void setReasonType(String reasonType) {
        this.reasonType = reasonType;
    }

    public String getResearch() {
        return research;
    }

    public void setResearch(String research) {
        this.research = research;
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
        this.serialNumber = serialNumber == null ? null : serialNumber.trim();
    }

    public String getHisId() {
        return hisId;
    }

    public void setHisId(String hisId) {
        this.hisId = hisId == null ? null : hisId.trim();
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName == null ? null : patientName.trim();
    }


    public String getPatientNo() {
        return patientNo;
    }

    public void setPatientNo(String patientNo) {
        this.patientNo = patientNo;
    }

    public String getAdmissionNo() {
        return admissionNo;
    }

    public void setAdmissionNo(String admissionNo) {
        this.admissionNo = admissionNo;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getRejectReason() {
        return rejectReason;
    }

    public void setRejectReason(String rejectReason) {
        this.rejectReason = rejectReason == null ? null : rejectReason.trim();
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public Integer getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(Integer maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public String getOriginPlace() {
        return originPlace;
    }

    public void setOriginPlace(String originPlace) {
        this.originPlace = originPlace == null ? null : originPlace.trim();
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession == null ? null : profession.trim();
    }

    public String getPatientTel() {
        return patientTel;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column application.patient_tel
     *
     * @param patientTel the value for application.patient_tel
     *
     * @mbggenerated Mon Nov 21 17:12:55 CST 2016
     */
    public void setPatientTel(String patientTel) {
        this.patientTel = patientTel == null ? null : patientTel.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column application.address
     *
     * @return the value of application.address
     *
     * @mbggenerated Mon Nov 21 17:12:55 CST 2016
     */
    public String getAddress() {
        return address;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column application.address
     *
     * @param address the value for application.address
     *
     * @mbggenerated Mon Nov 21 17:12:55 CST 2016
     */
    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }


    public Integer getHospital() {
        return hospital;
    }

    public void setHospital(Integer hospital) {
        this.hospital = hospital;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column application.doctor
     *
     * @return the value of application.doctor
     *
     * @mbggenerated Mon Nov 21 17:12:55 CST 2016
     */
    public String getDoctor() {
        return doctor;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column application.doctor
     *
     * @param doctor the value for application.doctor
     *
     * @mbggenerated Mon Nov 21 17:12:55 CST 2016
     */
    public void setDoctor(String doctor) {
        this.doctor = doctor == null ? null : doctor.trim();
    }

    public Integer getDepartments() {
        return departments;
    }

    public void setDepartments(Integer departments) {
        this.departments = departments;
    }

    public String getDoctorTel() {
        return doctorTel;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column application.doctor_tel
     *
     * @param doctorTel the value for application.doctor_tel
     *
     * @mbggenerated Mon Nov 21 17:12:55 CST 2016
     */
    public void setDoctorTel(String doctorTel) {
        this.doctorTel = doctorTel == null ? null : doctorTel.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column application.inspection_item
     *
     * @return the value of application.inspection_item
     *
     * @mbggenerated Mon Nov 21 17:12:55 CST 2016
     */
    public String getInspectionItem() {
        return inspectionItem;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column application.inspection_item
     *
     * @param inspectionItem the value for application.inspection_item
     *
     * @mbggenerated Mon Nov 21 17:12:55 CST 2016
     */
    public void setInspectionItem(String inspectionItem) {
        this.inspectionItem = inspectionItem == null ? null : inspectionItem.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column application.visit_cat
     *
     * @return the value of application.visit_cat
     *
     * @mbggenerated Mon Nov 21 17:12:55 CST 2016
     */
    public String getVisitCat() {
        return visitCat;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column application.visit_cat
     *
     * @param visitCat the value for application.visit_cat
     *
     * @mbggenerated Mon Nov 21 17:12:55 CST 2016
     */
    public void setVisitCat(String visitCat) {
        this.visitCat = visitCat == null ? null : visitCat.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column application.gynaecology
     *
     * @return the value of application.gynaecology
     *
     * @mbggenerated Mon Nov 21 17:12:55 CST 2016
     */
    public Boolean getGynaecology() {
        return gynaecology;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column application.gynaecology
     *
     * @param gynaecology the value for application.gynaecology
     *
     * @mbggenerated Mon Nov 21 17:12:55 CST 2016
     */
    public void setGynaecology(Boolean gynaecology) {
        this.gynaecology = gynaecology;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column application.menopause
     *
     * @return the value of application.menopause
     *
     * @mbggenerated Mon Nov 21 17:12:55 CST 2016
     */
    public Boolean getMenopause() {
        return menopause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column application.menopause
     *
     * @param menopause the value for application.menopause
     *
     * @mbggenerated Mon Nov 21 17:12:55 CST 2016
     */
    public void setMenopause(Boolean menopause) {
        this.menopause = menopause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column application.menopause_time
     *
     * @return the value of application.menopause_time
     *
     * @mbggenerated Mon Nov 21 17:12:55 CST 2016
     */
    public String getMenopauseTime() {
        return menopauseTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column application.menopause_time
     *
     * @param menopauseTime the value for application.menopause_time
     *
     * @mbggenerated Mon Nov 21 17:12:55 CST 2016
     */
    public void setMenopauseTime(String menopauseTime) {
        this.menopauseTime = menopauseTime == null ? null : menopauseTime.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column application.menopause_end
     *
     * @return the value of application.menopause_end
     *
     * @mbggenerated Mon Nov 21 17:12:55 CST 2016
     */
    public Date getMenopauseEnd() {
        return menopauseEnd;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column application.menopause_end
     *
     * @param menopauseEnd the value for application.menopause_end
     *
     * @mbggenerated Mon Nov 21 17:12:55 CST 2016
     */
    public void setMenopauseEnd(Date menopauseEnd) {
        this.menopauseEnd = menopauseEnd;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column application.hcg
     *
     * @return the value of application.hcg
     *
     * @mbggenerated Mon Nov 21 17:12:55 CST 2016
     */
    public Float getHcg() {
        return hcg;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column application.hcg
     *
     * @param hcg the value for application.hcg
     *
     * @mbggenerated Mon Nov 21 17:12:55 CST 2016
     */
    public void setHcg(Float hcg) {
        this.hcg = hcg;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column application.urgent_flag
     *
     * @return the value of application.urgent_flag
     *
     * @mbggenerated Mon Nov 21 17:12:55 CST 2016
     */
    public Boolean getUrgentFlag() {
        return urgentFlag;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column application.urgent_flag
     *
     * @param urgentFlag the value for application.urgent_flag
     *
     * @mbggenerated Mon Nov 21 17:12:55 CST 2016
     */
    public void setUrgentFlag(Boolean urgentFlag) {
        this.urgentFlag = urgentFlag;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column application.create_by
     *
     * @return the value of application.create_by
     *
     * @mbggenerated Mon Nov 21 17:12:55 CST 2016
     */
    public Long getCreateBy() {
        return createBy;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column application.create_by
     *
     * @param createBy the value for application.create_by
     *
     * @mbggenerated Mon Nov 21 17:12:55 CST 2016
     */
    public void setCreateBy(Long createBy) {
        this.createBy = createBy;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column application.update_by
     *
     * @return the value of application.update_by
     *
     * @mbggenerated Mon Nov 21 17:12:55 CST 2016
     */
    public Long getUpdateBy() {
        return updateBy;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column application.update_by
     *
     * @param updateBy the value for application.update_by
     *
     * @mbggenerated Mon Nov 21 17:12:55 CST 2016
     */
    public void setUpdateBy(Long updateBy) {
        this.updateBy = updateBy;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column application.create_time
     *
     * @return the value of application.create_time
     *
     * @mbggenerated Mon Nov 21 17:12:55 CST 2016
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column application.create_time
     *
     * @param createTime the value for application.create_time
     *
     * @mbggenerated Mon Nov 21 17:12:55 CST 2016
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column application.update_time
     *
     * @return the value of application.update_time
     *
     * @mbggenerated Mon Nov 21 17:12:55 CST 2016
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column application.update_time
     *
     * @param updateTime the value for application.update_time
     *
     * @mbggenerated Mon Nov 21 17:12:55 CST 2016
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column application.gynaecology_remark
     *
     * @return the value of application.gynaecology_remark
     *
     * @mbggenerated Mon Nov 21 17:12:55 CST 2016
     */
    public String getGynaecologyRemark() {
        return gynaecologyRemark;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column application.gynaecology_remark
     *
     * @param gynaecologyRemark the value for application.gynaecology_remark
     *
     * @mbggenerated Mon Nov 21 17:12:55 CST 2016
     */
    public void setGynaecologyRemark(String gynaecologyRemark) {
        this.gynaecologyRemark = gynaecologyRemark == null ? null : gynaecologyRemark.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column application.clinical_diagnosis
     *
     * @return the value of application.clinical_diagnosis
     *
     * @mbggenerated Mon Nov 21 17:12:55 CST 2016
     */
    public String getClinicalDiagnosis() {
        return clinicalDiagnosis;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column application.clinical_diagnosis
     *
     * @param clinicalDiagnosis the value for application.clinical_diagnosis
     *
     * @mbggenerated Mon Nov 21 17:12:55 CST 2016
     */
    public void setClinicalDiagnosis(String clinicalDiagnosis) {
        this.clinicalDiagnosis = clinicalDiagnosis == null ? null : clinicalDiagnosis.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column application.medical_summary
     *
     * @return the value of application.medical_summary
     *
     * @mbggenerated Mon Nov 21 17:12:55 CST 2016
     */
    public String getMedicalSummary() {
        return medicalSummary;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column application.medical_summary
     *
     * @param medicalSummary the value for application.medical_summary
     *
     * @mbggenerated Mon Nov 21 17:12:55 CST 2016
     */
    public void setMedicalSummary(String medicalSummary) {
        this.medicalSummary = medicalSummary == null ? null : medicalSummary.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column application.clinical_findings
     *
     * @return the value of application.clinical_findings
     *
     * @mbggenerated Mon Nov 21 17:12:55 CST 2016
     */
    public String getClinicalFindings() {
        return clinicalFindings;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column application.clinical_findings
     *
     * @param clinicalFindings the value for application.clinical_findings
     *
     * @mbggenerated Mon Nov 21 17:12:55 CST 2016
     */
    public void setClinicalFindings(String clinicalFindings) {
        this.clinicalFindings = clinicalFindings == null ? null : clinicalFindings.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column application.imaging_findings
     *
     * @return the value of application.imaging_findings
     *
     * @mbggenerated Mon Nov 21 17:12:55 CST 2016
     */
    public String getImagingFindings() {
        return imagingFindings;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column application.imaging_findings
     *
     * @param imagingFindings the value for application.imaging_findings
     *
     * @mbggenerated Mon Nov 21 17:12:55 CST 2016
     */
    public void setImagingFindings(String imagingFindings) {
        this.imagingFindings = imagingFindings == null ? null : imagingFindings.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column application.medical_history
     *
     * @return the value of application.medical_history
     *
     * @mbggenerated Mon Nov 21 17:12:55 CST 2016
     */
    public String getMedicalHistory() {
        return medicalHistory;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column application.medical_history
     *
     * @param medicalHistory the value for application.medical_history
     *
     * @mbggenerated Mon Nov 21 17:12:55 CST 2016
     */
    public void setMedicalHistory(String medicalHistory) {
        this.medicalHistory = medicalHistory == null ? null : medicalHistory.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column application.history_medicine
     *
     * @return the value of application.history_medicine
     *
     * @mbggenerated Mon Nov 21 17:12:55 CST 2016
     */
    public String getHistoryMedicine() {
        return historyMedicine;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column application.history_medicine
     *
     * @param historyMedicine the value for application.history_medicine
     *
     * @mbggenerated Mon Nov 21 17:12:55 CST 2016
     */
    public void setHistoryMedicine(String historyMedicine) {
        this.historyMedicine = historyMedicine == null ? null : historyMedicine.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column application.history_pathology
     *
     * @return the value of application.history_pathology
     *
     * @mbggenerated Mon Nov 21 17:12:55 CST 2016
     */
    public String getHistoryPathology() {
        return historyPathology;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column application.history_pathology
     *
     * @param historyPathology the value for application.history_pathology
     *
     * @mbggenerated Mon Nov 21 17:12:55 CST 2016
     */
    public void setHistoryPathology(String historyPathology) {
        this.historyPathology = historyPathology == null ? null : historyPathology.trim();
    }

    public String getOperationFindings() {
        return operationFindings;
    }

    public void setOperationFindings(String operationFindings) {
        this.operationFindings = operationFindings;
    }
}