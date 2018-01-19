package com.lifetech.dhap.pathcloud.application.api.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by LuoMo on 2016-11-22.
 */
public class ApplicationVO implements Serializable{

    private Long id;

    private String serialNumber;

    private String number;

    private String hisId;

    private String patientName;

    private String patientNo;

    private String admissionNo;

    private Integer status;
    private String statusName;

    private String rejectReason;
    private String reasonType;

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
    private String departmentsDesc;

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

    private ResearchVO research;

    private List<SampleVO> samples;

    private Integer applyType;
    private String applyTypeDesc;

    private String applicant;

    private Long pathologyId;

    private Integer pathologyStatus;
    private String pathologyStatusDesc;

    private String pathNo;

    private String note;

    private Integer inspectCategory;

    public Integer getInspectCategory() {
        return inspectCategory;
    }

    public void setInspectCategory(Integer inspectCategory) {
        this.inspectCategory = inspectCategory;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getPathNo() {
        return pathNo;
    }

    public void setPathNo(String pathNo) {
        this.pathNo = pathNo;
    }

    public Long getPathologyId() {
        return pathologyId;
    }

    public void setPathologyId(Long pathologyId) {
        this.pathologyId = pathologyId;
    }

    public Integer getPathologyStatus() {
        return pathologyStatus;
    }

    public void setPathologyStatus(Integer pathologyStatus) {
        this.pathologyStatus = pathologyStatus;
    }

    public String getPathologyStatusDesc() {
        return pathologyStatusDesc;
    }

    public void setPathologyStatusDesc(String pathologyStatusDesc) {
        this.pathologyStatusDesc = pathologyStatusDesc;
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

    public String getApplyTypeDesc() {
        return applyTypeDesc;
    }

    public void setApplyTypeDesc(String applyTypeDesc) {
        this.applyTypeDesc = applyTypeDesc;
    }

    public ResearchVO getResearch() {
        return research;
    }

    public void setResearch(ResearchVO research) {
        this.research = research;
    }

    public String getReasonType() {
        return reasonType;
    }

    public void setReasonType(String reasonType) {
        this.reasonType = reasonType;
    }

    public String getDepartmentsDesc() {
        return departmentsDesc;
    }

    public void setDepartmentsDesc(String departmentsDesc) {
        this.departmentsDesc = departmentsDesc;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public List<SampleVO> getSamples() {
        return samples;
    }

    public void setSamples(List<SampleVO> samples) {
        this.samples = samples;
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

    public String getHisId() {
        return hisId;
    }

    public void setHisId(String hisId) {
        this.hisId = hisId;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
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
        this.rejectReason = rejectReason;
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
        this.originPlace = originPlace;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getPatientTel() {
        return patientTel;
    }

    public void setPatientTel(String patientTel) {
        this.patientTel = patientTel;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getHospital() {
        return hospital;
    }

    public void setHospital(Integer hospital) {
        this.hospital = hospital;
    }

    public String getDoctor() {
        return doctor;
    }

    public void setDoctor(String doctor) {
        this.doctor = doctor;
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

    public void setDoctorTel(String doctorTel) {
        this.doctorTel = doctorTel;
    }

    public String getInspectionItem() {
        return inspectionItem;
    }

    public void setInspectionItem(String inspectionItem) {
        this.inspectionItem = inspectionItem;
    }

    public String getVisitCat() {
        return visitCat;
    }

    public void setVisitCat(String visitCat) {
        this.visitCat = visitCat;
    }

    public Boolean getGynaecology() {
        return gynaecology;
    }

    public void setGynaecology(Boolean gynaecology) {
        this.gynaecology = gynaecology;
    }

    public Boolean getMenopause() {
        return menopause;
    }

    public void setMenopause(Boolean menopause) {
        this.menopause = menopause;
    }

    public String getMenopauseTime() {
        return menopauseTime;
    }

    public void setMenopauseTime(String menopauseTime) {
        this.menopauseTime = menopauseTime;
    }

    public Date getMenopauseEnd() {
        return menopauseEnd;
    }

    public void setMenopauseEnd(Date menopauseEnd) {
        this.menopauseEnd = menopauseEnd;
    }

    public Float getHcg() {
        return hcg;
    }

    public void setHcg(Float hcg) {
        this.hcg = hcg;
    }

    public Boolean getUrgentFlag() {
        return urgentFlag;
    }

    public void setUrgentFlag(Boolean urgentFlag) {
        this.urgentFlag = urgentFlag;
    }

    public Long getCreateBy() {
        return createBy;
    }

    public void setCreateBy(Long createBy) {
        this.createBy = createBy;
    }

    public Long getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(Long updateBy) {
        this.updateBy = updateBy;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getGynaecologyRemark() {
        return gynaecologyRemark;
    }

    public void setGynaecologyRemark(String gynaecologyRemark) {
        this.gynaecologyRemark = gynaecologyRemark;
    }

    public String getClinicalDiagnosis() {
        return clinicalDiagnosis;
    }

    public void setClinicalDiagnosis(String clinicalDiagnosis) {
        this.clinicalDiagnosis = clinicalDiagnosis;
    }

    public String getMedicalSummary() {
        return medicalSummary;
    }

    public void setMedicalSummary(String medicalSummary) {
        this.medicalSummary = medicalSummary;
    }

    public String getClinicalFindings() {
        return clinicalFindings;
    }

    public void setClinicalFindings(String clinicalFindings) {
        this.clinicalFindings = clinicalFindings;
    }

    public String getImagingFindings() {
        return imagingFindings;
    }

    public void setImagingFindings(String imagingFindings) {
        this.imagingFindings = imagingFindings;
    }

    public String getOperationFindings() {
        return operationFindings;
    }

    public void setOperationFindings(String operationFindings) {
        this.operationFindings = operationFindings;
    }

    public String getMedicalHistory() {
        return medicalHistory;
    }

    public void setMedicalHistory(String medicalHistory) {
        this.medicalHistory = medicalHistory;
    }

    public String getHistoryMedicine() {
        return historyMedicine;
    }

    public void setHistoryMedicine(String historyMedicine) {
        this.historyMedicine = historyMedicine;
    }

    public String getHistoryPathology() {
        return historyPathology;
    }

    public void setHistoryPathology(String historyPathology) {
        this.historyPathology = historyPathology;
    }
}
