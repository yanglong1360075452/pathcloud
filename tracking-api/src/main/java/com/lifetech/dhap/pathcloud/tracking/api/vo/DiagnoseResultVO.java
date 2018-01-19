package com.lifetech.dhap.pathcloud.tracking.api.vo;

/**
 * Created by LiuMei on 2017-01-18.
 */
public class DiagnoseResultVO {

    //诊断结果DOM结构
    private String resultDom;

    //显微所见
    private String microDiagnose;

    //诊断描述
    private String diagnose;

    //指定诊断医生
    private Long assignDiagnose;

    //备注
    private String note;

    //报告图片Base64编码
    private String reportPic;

    private Boolean again; //冰冻选择是否再走一遍常规的流程

    private Long specialApplyId;

    private String specialResult; //特殊申请结果

    private String outConsultResult; //外院会诊意见

    private Integer coincidence; //冰冻符合度

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

    public String getSpecialResult() {
        return specialResult;
    }

    public void setSpecialResult(String specialResult) {
        this.specialResult = specialResult;
    }

    public Long getSpecialApplyId() {
        return specialApplyId;
    }

    public void setSpecialApplyId(Long specialApplyId) {
        this.specialApplyId = specialApplyId;
    }

    public Boolean getAgain() {
        return again;
    }

    public void setAgain(Boolean again) {
        this.again = again;
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

    public String getResultDom() {
        return resultDom;
    }

    public void setResultDom(String resultDom) {
        this.resultDom = resultDom;
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
