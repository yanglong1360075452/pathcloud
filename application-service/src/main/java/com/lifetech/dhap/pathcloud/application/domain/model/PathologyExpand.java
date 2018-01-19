package com.lifetech.dhap.pathcloud.application.domain.model;

import java.util.Date;

/**
 * Created by LuoMo on 2016-11-30.
 */
public class PathologyExpand extends Pathology {

    private Integer sex;

    private Integer age;

    private String inspectionItem;

    private Boolean urgentFlag;

    private Date inspectionTime;

    private Long inspectionDoctor;//送检医生

    private Long firstDiagnoseDoctor;//一级诊断医生

    private Long secondDiagnoseDoctor;//二级诊断医生

    private Long thirdDiagnoseDoctor;//三级诊断医生

    private Long reportDoctor;//报告医生

    private Date reportTime;

    private Boolean delay; //是否延期

    private String number; //特殊申请号

    private Integer type; // 特殊申请类型

    private Date estimateReportTime; //预计报告时间

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Date getEstimateReportTime() {
        return estimateReportTime;
    }

    public void setEstimateReportTime(Date estimateReportTime) {
        this.estimateReportTime = estimateReportTime;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Boolean getDelay() {
        return delay;
    }

    public void setDelay(Boolean delay) {
        this.delay = delay;
    }

    public Date getInspectionTime() {
        return inspectionTime;
    }

    public void setInspectionTime(Date inspectionTime) {
        this.inspectionTime = inspectionTime;
    }

    public String getInspectionItem() {
        return inspectionItem;
    }

    public void setInspectionItem(String inspectionItem) {
        this.inspectionItem = inspectionItem;
    }

    public Boolean getUrgentFlag() {
        return urgentFlag;
    }

    public void setUrgentFlag(Boolean urgentFlag) {
        this.urgentFlag = urgentFlag;
    }

    public Long getInspectionDoctor() {
        return inspectionDoctor;
    }

    public void setInspectionDoctor(Long inspectionDoctor) {
        this.inspectionDoctor = inspectionDoctor;
    }

    public Long getFirstDiagnoseDoctor() {
        return firstDiagnoseDoctor;
    }

    public void setFirstDiagnoseDoctor(Long firstDiagnoseDoctor) {
        this.firstDiagnoseDoctor = firstDiagnoseDoctor;
    }

    public Long getSecondDiagnoseDoctor() {
        return secondDiagnoseDoctor;
    }

    public void setSecondDiagnoseDoctor(Long secondDiagnoseDoctor) {
        this.secondDiagnoseDoctor = secondDiagnoseDoctor;
    }

    public Long getThirdDiagnoseDoctor() {
        return thirdDiagnoseDoctor;
    }

    public void setThirdDiagnoseDoctor(Long thirdDiagnoseDoctor) {
        this.thirdDiagnoseDoctor = thirdDiagnoseDoctor;
    }

    public Long getReportDoctor() {
        return reportDoctor;
    }

    public void setReportDoctor(Long reportDoctor) {
        this.reportDoctor = reportDoctor;
    }

    public Date getReportTime() {
        return reportTime;
    }

    public void setReportTime(Date reportTime) {
        this.reportTime = reportTime;
    }
}
