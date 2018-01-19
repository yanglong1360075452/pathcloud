package com.lifetech.dhap.pathcloud.application.application.dto;

import com.lifetech.dhap.pathcloud.tracking.application.dto.BlockDto;
import com.lifetech.dhap.pathcloud.user.application.dto.UserSimpleDto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author eric.guo@ostengar.com
 * @version 1.0
 * @time 2016-11-24-08:58
 */
public class PathologyDto implements Serializable {

    private Long id;

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

    private Integer status;
    private String statusName;

    private String patientName;

    private Integer sex;

    private Integer age;

    private Integer departments;
    private String departmentsDesc;

    private String inspectionItem;

    private Boolean urgentFlag;

    private Boolean reGrossing;

    private Integer inspectCategory;

    private List<SampleDto> samples;

    private List<BlockDto> blocks;

    private UserSimpleDto grossingDoctor;

    private Date inspectionTime;//送检日期--application创建时间

    private Integer applyType;

    private Long assignGrossing;

    private Long assignDiagnose;

    private Boolean delay;

    private String note;

    private String reportPic;

    private Long applyId;

    private Boolean afterFrozen;

    private String number; //特殊申请号

    private Integer type; //特殊申请类型
    private String typeDesc;

    private Date estimateReportTime; //预计报告日期

    private Boolean outConsult;

    private String outConsultResult;

    private Long applicationId;

    /**
     * 住院号
     */
    private String admissionNo;

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

    public String getTypeDesc() {
        return typeDesc;
    }

    public void setTypeDesc(String typeDesc) {
        this.typeDesc = typeDesc;
    }

    public String getAdmissionNo() {
        return admissionNo;
    }

    public void setAdmissionNo(String admissionNo) {
        this.admissionNo = admissionNo;
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

    public Boolean getAfterFrozen() {
        return afterFrozen;
    }

    public void setAfterFrozen(Boolean afterFrozen) {
        this.afterFrozen = afterFrozen;
    }

    public Long getApplyId() {
        return applyId;
    }

    public void setApplyId(Long applyId) {
        this.applyId = applyId;
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

    public Boolean getDelay() {
        return delay;
    }

    public void setDelay(Boolean delay) {
        this.delay = delay;
    }

    public Long getAssignDiagnose() {
        return assignDiagnose;
    }

    public void setAssignDiagnose(Long assignDiagnose) {
        this.assignDiagnose = assignDiagnose;
    }

    public Long getAssignGrossing() {
        return assignGrossing;
    }

    public void setAssignGrossing(Long assignGrossing) {
        this.assignGrossing = assignGrossing;
    }

    public Integer getApplyType() {
        return applyType;
    }

    public void setApplyType(Integer applyType) {
        this.applyType = applyType;
    }

    public Integer getInspectCategory() {
        return inspectCategory;
    }

    public void setInspectCategory(Integer inspectCategory) {
        this.inspectCategory = inspectCategory;
    }

    public Date getInspectionTime() {
        return inspectionTime;
    }

    public void setInspectionTime(Date inspectionTime) {
        this.inspectionTime = inspectionTime;
    }

    public Boolean getReGrossing() {
        return reGrossing;
    }

    public void setReGrossing(Boolean reGrossing) {
        this.reGrossing = reGrossing;
    }

    public String getDepartmentsDesc() {
        return departmentsDesc;
    }

    public void setDepartmentsDesc(String departmentsDesc) {
        this.departmentsDesc = departmentsDesc;
    }

    public Integer getDepartments() {
        return departments;
    }

    public void setDepartments(Integer departments) {
        this.departments = departments;
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

    public List<BlockDto> getBlocks() {
        return blocks;
    }

    public void setBlocks(List<BlockDto> blocks) {
        this.blocks = blocks;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
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

    public Long getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(Long applicationId) {
        this.applicationId = applicationId;
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

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<SampleDto> getSamples() {
        return samples;
    }

    public void setSamples(List<SampleDto> samples) {
        this.samples = samples;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public UserSimpleDto getGrossingDoctor() {
        return grossingDoctor;
    }

    public void setGrossingDoctor(UserSimpleDto grossingDoctor) {
        this.grossingDoctor = grossingDoctor;
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
