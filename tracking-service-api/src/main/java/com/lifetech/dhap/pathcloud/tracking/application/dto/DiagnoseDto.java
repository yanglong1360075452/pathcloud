package com.lifetech.dhap.pathcloud.tracking.application.dto;

/**
 * Created by LiuMei on 2017-01-12.
 */
public class DiagnoseDto {

    //病理ID
    private Long id;

    //病理号
    private String serialNumber;

    //特殊申请号
    private String number;

    //病理诊断
    private String result;

    //显微所见
    private String microDiagnose;

    //诊断描述
    private String diagnose;

    //指定诊断医生
    private Long assignDiagnose;

    private String note;

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

    public Long getAssignDiagnose() {
        return assignDiagnose;
    }

    public void setAssignDiagnose(Long assignDiagnose) {
        this.assignDiagnose = assignDiagnose;
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

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
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
