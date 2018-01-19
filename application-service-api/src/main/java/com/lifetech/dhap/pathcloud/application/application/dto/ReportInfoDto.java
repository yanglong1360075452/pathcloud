package com.lifetech.dhap.pathcloud.application.application.dto;


import com.lifetech.dhap.pathcloud.user.application.dto.UserSimpleDto;

import java.util.Date;
import java.util.List;

/**
 * Created by LiuMei on 2017-01-19.
 */
public class ReportInfoDto {

    private Long pathId;

    private String hisId;

    //住院号
    private String admissionNo;

    //病理号
    private String pathNo;

    //门诊号
    private String patientNo;

    private String patientName;

    private Integer age;

    private Integer sex;

    //床号
    private String bedNo;

    private Integer departments;
    private String departmentsDesc;

    //样本接收时间
    private Date receiveTime;

    private UserSimpleDto applyDoctor;

    //住院患者位置
    private String patientPosition;

    private String jujianNote;

    private String bingdongNote;

    private UserSimpleDto diagnoseDoctor;

    private Date diagnoseTime;

    private String resultDom;

    private Integer hospital;
    private String hospitalDesc;

    private String specialResult;

    private List<String> frozenResults;

    public List<String> getFrozenResults() {
        return frozenResults;
    }

    public void setFrozenResults(List<String> frozenResults) {
        this.frozenResults = frozenResults;
    }

    public String getSpecialResult() {
        return specialResult;
    }

    public void setSpecialResult(String specialResult) {
        this.specialResult = specialResult;
    }

    public Integer getHospital() {
        return hospital;
    }

    public void setHospital(Integer hospital) {
        this.hospital = hospital;
    }

    public String getHospitalDesc() {
        return hospitalDesc;
    }

    public void setHospitalDesc(String hospitalDesc) {
        this.hospitalDesc = hospitalDesc;
    }

    public String getResultDom() {
        return resultDom;
    }

    public void setResultDom(String resultDom) {
        this.resultDom = resultDom;
    }

    public String getPathNo() {
        return pathNo;
    }

    public void setPathNo(String pathNo) {
        this.pathNo = pathNo;
    }

    public String getHisId() {
        return hisId;
    }

    public void setHisId(String hisId) {
        this.hisId = hisId;
    }

    public String getAdmissionNo() {
        return admissionNo;
    }

    public void setAdmissionNo(String admissionNo) {
        this.admissionNo = admissionNo;
    }

    public String getPatientNo() {
        return patientNo;
    }

    public void setPatientNo(String patientNo) {
        this.patientNo = patientNo;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
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

    public String getBedNo() {
        return bedNo;
    }

    public void setBedNo(String bedNo) {
        this.bedNo = bedNo;
    }

    public Integer getDepartments() {
        return departments;
    }

    public void setDepartments(Integer departments) {
        this.departments = departments;
    }

    public String getDepartmentsDesc() {
        return departmentsDesc;
    }

    public void setDepartmentsDesc(String departmentsDesc) {
        this.departmentsDesc = departmentsDesc;
    }

    public Date getReceiveTime() {
        return receiveTime;
    }

    public void setReceiveTime(Date receiveTime) {
        this.receiveTime = receiveTime;
    }

    public String getPatientPosition() {
        return patientPosition;
    }

    public void setPatientPosition(String patientPosition) {
        this.patientPosition = patientPosition;
    }

    public String getJujianNote() {
        return jujianNote;
    }

    public void setJujianNote(String jujianNote) {
        this.jujianNote = jujianNote;
    }

    public String getBingdongNote() {
        return bingdongNote;
    }

    public void setBingdongNote(String bingdongNote) {
        this.bingdongNote = bingdongNote;
    }

    public UserSimpleDto getApplyDoctor() {
        return applyDoctor;
    }

    public void setApplyDoctor(UserSimpleDto applyDoctor) {
        this.applyDoctor = applyDoctor;
    }

    public UserSimpleDto getDiagnoseDoctor() {
        return diagnoseDoctor;
    }

    public void setDiagnoseDoctor(UserSimpleDto diagnoseDoctor) {
        this.diagnoseDoctor = diagnoseDoctor;
    }

    public Date getDiagnoseTime() {
        return diagnoseTime;
    }

    public void setDiagnoseTime(Date diagnoseTime) {
        this.diagnoseTime = diagnoseTime;
    }

    public Long getPathId() {
        return pathId;
    }

    public void setPathId(Long pathId) {
        this.pathId = pathId;
    }
}
