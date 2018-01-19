package com.lifetech.dhap.pathcloud.tracking.api.vo;

import com.lifetech.dhap.pathcloud.user.api.vo.UserSimpleVO;

import java.util.Date;

/**
 * Created by LiuMei on 2017-01-12.
 */
public class DiagnoseInfoVO {

    private Long id;

    private String pathNo;

    private String patientName;

    private Integer departments;
    private String departmentsDesc;

    private UserSimpleVO inspectionDoctor;//送检医生

    private UserSimpleVO firstDiagnoseDoctor;//一级诊断医生

    private UserSimpleVO secondDiagnoseDoctor;//二级诊断医生

    private UserSimpleVO thirdDiagnoseDoctor;//三级诊断医生

    private UserSimpleVO reportDoctor;//报告医生

    private Date reportTime;

    private Integer status;
    private String statusDesc;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPathNo() {
        return pathNo;
    }

    public void setPathNo(String pathNo) {
        this.pathNo = pathNo;
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

    public String getDepartmentsDesc() {
        return departmentsDesc;
    }

    public void setDepartmentsDesc(String departmentsDesc) {
        this.departmentsDesc = departmentsDesc;
    }

    public UserSimpleVO getInspectionDoctor() {
        return inspectionDoctor;
    }

    public void setInspectionDoctor(UserSimpleVO inspectionDoctor) {
        this.inspectionDoctor = inspectionDoctor;
    }

    public UserSimpleVO getFirstDiagnoseDoctor() {
        return firstDiagnoseDoctor;
    }

    public void setFirstDiagnoseDoctor(UserSimpleVO firstDiagnoseDoctor) {
        this.firstDiagnoseDoctor = firstDiagnoseDoctor;
    }

    public UserSimpleVO getSecondDiagnoseDoctor() {
        return secondDiagnoseDoctor;
    }

    public void setSecondDiagnoseDoctor(UserSimpleVO secondDiagnoseDoctor) {
        this.secondDiagnoseDoctor = secondDiagnoseDoctor;
    }

    public UserSimpleVO getThirdDiagnoseDoctor() {
        return thirdDiagnoseDoctor;
    }

    public void setThirdDiagnoseDoctor(UserSimpleVO thirdDiagnoseDoctor) {
        this.thirdDiagnoseDoctor = thirdDiagnoseDoctor;
    }

    public UserSimpleVO getReportDoctor() {
        return reportDoctor;
    }

    public void setReportDoctor(UserSimpleVO reportDoctor) {
        this.reportDoctor = reportDoctor;
    }

    public Date getReportTime() {
        return reportTime;
    }

    public void setReportTime(Date reportTime) {
        this.reportTime = reportTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getStatusDesc() {
        return statusDesc;
    }

    public void setStatusDesc(String statusDesc) {
        this.statusDesc = statusDesc;
    }
}
