package com.lifetech.dhap.pathcloud.wechat.api.vo;

import java.util.List;

/**
 * Created by LiuMei on 2017-03-27.
 */
public class ResearchVO {

    private Long id;

    private String serialNumber;

    private String applicant;  //申请人

    private Integer identity;  //申请人身份

    private String tutor; //导师

    private String faculty; //院系

    private String specialty; //专业

    private String studentNumber;

    private String unit;

    private Integer departments;

    private String wno; //职工号

    private String taskName; //课题名称

    private Integer taskType; //课题类型

    private String funds; //经费来源

    private String phone;

    private Integer researchType;
    private String researchTypeDesc;

    private List<SampleVO> samples;

    private List<BookVO> books;

    public String getResearchTypeDesc() {
        return researchTypeDesc;
    }

    public void setResearchTypeDesc(String researchTypeDesc) {
        this.researchTypeDesc = researchTypeDesc;
    }

    public String getApplicant() {
        return applicant;
    }

    public void setApplicant(String applicant) {
        this.applicant = applicant;
    }

    public Integer getIdentity() {
        return identity;
    }

    public void setIdentity(Integer identity) {
        this.identity = identity;
    }

    public String getTutor() {
        return tutor;
    }

    public void setTutor(String tutor) {
        this.tutor = tutor;
    }

    public String getFaculty() {
        return faculty;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    public String getStudentNumber() {
        return studentNumber;
    }

    public void setStudentNumber(String studentNumber) {
        this.studentNumber = studentNumber;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Integer getDepartments() {
        return departments;
    }

    public void setDepartments(Integer departments) {
        this.departments = departments;
    }

    public String getWno() {
        return wno;
    }

    public void setWno(String wno) {
        this.wno = wno;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public Integer getTaskType() {
        return taskType;
    }

    public void setTaskType(Integer taskType) {
        this.taskType = taskType;
    }

    public String getFunds() {
        return funds;
    }

    public void setFunds(String funds) {
        this.funds = funds;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getResearchType() {
        return researchType;
    }

    public void setResearchType(Integer researchType) {
        this.researchType = researchType;
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

    public List<BookVO> getBooks() {
        return books;
    }

    public void setBooks(List<BookVO> books) {
        this.books = books;
    }
}
