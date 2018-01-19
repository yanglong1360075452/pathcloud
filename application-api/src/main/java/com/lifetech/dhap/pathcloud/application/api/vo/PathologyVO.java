package com.lifetech.dhap.pathcloud.application.api.vo;

import com.lifetech.dhap.pathcloud.tracking.api.vo.BlockVO;
import com.lifetech.dhap.pathcloud.user.api.vo.UserSimpleVO;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by LuoMo on 2016-11-23.
 */
public class PathologyVO implements Serializable {

    private Long id;

    private String serialNumber;

    private Long applicationId;

    private Long createBy;

    private Long updateBy;

    private Date createTime;

    private Date updateTime;

    private String result;

    private String jujianNote;

    private String bingdongNote;

    private Integer status;
    private String statusName;

    private String patientName;

    private Integer departments;
    private String departmentsDesc;

    private String inspectionItem;

    private Boolean urgentFlag;

    private Boolean reGrossing;

    private List<SampleVO> samples;

    private List<BlockVO> blocks;

    private Integer inspectCategory;

    private UserSimpleVO grossingDoctor;

    private Long assignGrossing;

    private Long assignDiagnose;

    private Long applyId;

    private Boolean afterFrozen;

    private String note;

    private Boolean outConsult;

    private String outConsultResult;

    private List<String> frozenNumbers;

    private Integer label;

    public Integer getLabel() {
        return label;
    }

    public void setLabel(Integer label) {
        this.label = label;
    }

    public List<String> getFrozenNumbers() {
        return frozenNumbers;
    }

    public void setFrozenNumbers(List<String> frozenNumbers) {
        this.frozenNumbers = frozenNumbers;
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

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
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

    public UserSimpleVO getGrossingDoctor() {
        return grossingDoctor;
    }

    public void setGrossingDoctor(UserSimpleVO grossingDoctor) {
        this.grossingDoctor = grossingDoctor;
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

    public List<BlockVO> getBlocks() {
        return blocks;
    }

    public void setBlocks(List<BlockVO> blocks) {
        this.blocks = blocks;
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

    public Boolean getReGrossing() {
        return reGrossing;
    }

    public void setReGrossing(Boolean reGrossing) {
        this.reGrossing = reGrossing;
    }

    public Integer getInspectCategory() {
        return inspectCategory;
    }

    public void setInspectCategory(Integer inspectCategory) {
        this.inspectCategory = inspectCategory;
    }
}
