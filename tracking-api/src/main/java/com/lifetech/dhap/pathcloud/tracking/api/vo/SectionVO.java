package com.lifetech.dhap.pathcloud.tracking.api.vo;

import com.lifetech.dhap.pathcloud.user.api.vo.UserSimpleVO;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by LiuMei on 2016-12-21.
 */
public class SectionVO implements Serializable {

    private Long pathologyId;

    private Long blockId;

    private String applicationSerialNumber;

    private String pathologySerialNumber;

    /**
     * 特染申请号
     */
    private String number;

    private String blockSerialNumber;

    /**
     * 病人姓名
     */
    private String patientName;

    /**
     * 送检科室
     */
    private Integer departments;

    /**
     * 取材医生
     */
    private UserSimpleVO grossingDoctor;

    /**
     * 取材备注
     */
    private String grossingRemark;

    /**
     * 切片备注
     */
    private String sectionRemark;
    /**
     * 切片备注类别
     */
    private String sectionRemarkType;

    /**
     * 蜡块号
     */
    private String subId;

    /**
     * 取材部位
     */
    private String bodyPart;

    private Integer unit;
    private String unitDesc;

    private Integer count;

    private Integer biaoshi;
    private String biaoshiDesc;

    private Integer status;
    private String statusDesc;

    /**
     * 特殊切片申请操作
     */
    private Integer specialSectionOperation;

    /**
     * 特殊切片申请备注
     */
    private String specialSectionRemark;

    /**
     * 待切玻片详情
     */
    private List<DyeApplyVO> dyeApply;

    /**
     * 特殊切片申请人
     */
    private UserSimpleVO specialSectionApplicant;

    /**
     * 待切片蜡块总数
     */
    private Long prepareSectionCount;

    private Integer lastSlideSubId;

    private Integer specialDye;

    /**
     * 当前病理号已切片蜡块数
     */
    private Long  pathSectionCount;

    /**
     * 当前病理号总蜡块数
     */
    private Long  pathBlockCount;

    private List<String> otherSection;

    private Long sectionOperator;
    private String sectionOperatorDesc;

    private Date sectionOperateTime;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public long getSectionOperator() {
        return sectionOperator;
    }

    public void setSectionOperator(long sectionOperator) {
        this.sectionOperator = sectionOperator;
    }

    public String getSectionOperatorDesc() {
        return sectionOperatorDesc;
    }

    public void setSectionOperatorDesc(String sectionOperatorDesc) {
        this.sectionOperatorDesc = sectionOperatorDesc;
    }

    public Date getSectionOperateTime() {
        return sectionOperateTime;
    }

    public void setSectionOperateTime(Date sectionOperateTime) {
        this.sectionOperateTime = sectionOperateTime;
    }

    public List<String> getOtherSection() {
        return otherSection;
    }

    public void setOtherSection(List<String> otherSection) {
        this.otherSection = otherSection;
    }

    public Integer getSpecialSectionOperation() {
        return specialSectionOperation;
    }

    public void setSpecialSectionOperation(Integer specialSectionOperation) {
        this.specialSectionOperation = specialSectionOperation;
    }

    public Integer getSpecialDye() {
        return specialDye;
    }

    public void setSpecialDye(Integer specialDye) {
        this.specialDye = specialDye;
    }

    public long getPathSectionCount() {
        return pathSectionCount;
    }

    public void setPathSectionCount(long pathSectionCount) {
        this.pathSectionCount = pathSectionCount;
    }

    public long getPathBlockCount() {
        return pathBlockCount;
    }

    public void setPathBlockCount(long pathBlockCount) {
        this.pathBlockCount = pathBlockCount;
    }

    public Integer getLastSlideSubId() {
        return lastSlideSubId;
    }

    public void setLastSlideSubId(Integer lastSlideSubId) {
        this.lastSlideSubId = lastSlideSubId;
    }

    public void setPathSectionCount(Long pathSectionCount) {
        this.pathSectionCount = pathSectionCount;
    }

    public void setPathBlockCount(Long pathBlockCount) {
        this.pathBlockCount = pathBlockCount;
    }

    public void setSectionOperator(Long sectionOperator) {
        this.sectionOperator = sectionOperator;
    }

    public String getSectionRemarkType() {
        return sectionRemarkType;
    }

    public void setSectionRemarkType(String sectionRemarkType) {
        this.sectionRemarkType = sectionRemarkType;
    }

    public Long getPathologyId() {
        return pathologyId;
    }

    public void setPathologyId(Long pathologyId) {
        this.pathologyId = pathologyId;
    }

    public Long getBlockId() {
        return blockId;
    }

    public void setBlockId(Long blockId) {
        this.blockId = blockId;
    }

    public String getApplicationSerialNumber() {
        return applicationSerialNumber;
    }

    public void setApplicationSerialNumber(String applicationSerialNumber) {
        this.applicationSerialNumber = applicationSerialNumber;
    }

    public String getPathologySerialNumber() {
        return pathologySerialNumber;
    }

    public void setPathologySerialNumber(String pathologySerialNumber) {
        this.pathologySerialNumber = pathologySerialNumber;
    }

    public String getBlockSerialNumber() {
        return blockSerialNumber;
    }

    public void setBlockSerialNumber(String blockSerialNumber) {
        this.blockSerialNumber = blockSerialNumber;
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

    public UserSimpleVO getGrossingDoctor() {
        return grossingDoctor;
    }

    public void setGrossingDoctor(UserSimpleVO grossingDoctor) {
        this.grossingDoctor = grossingDoctor;
    }

    public String getGrossingRemark() {
        return grossingRemark;
    }

    public void setGrossingRemark(String grossingRemark) {
        this.grossingRemark = grossingRemark;
    }

    public String getSectionRemark() {
        return sectionRemark;
    }

    public void setSectionRemark(String sectionRemark) {
        this.sectionRemark = sectionRemark;
    }

    public String getSubId() {
        return subId;
    }

    public void setSubId(String subId) {
        this.subId = subId;
    }

    public String getBodyPart() {
        return bodyPart;
    }

    public void setBodyPart(String bodyPart) {
        this.bodyPart = bodyPart;
    }

    public Integer getUnit() {
        return unit;
    }

    public void setUnit(Integer unit) {
        this.unit = unit;
    }

    public String getUnitDesc() {
        return unitDesc;
    }

    public void setUnitDesc(String unitDesc) {
        this.unitDesc = unitDesc;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getBiaoshi() {
        return biaoshi;
    }

    public void setBiaoshi(Integer biaoshi) {
        this.biaoshi = biaoshi;
    }

    public String getBiaoshiDesc() {
        return biaoshiDesc;
    }

    public void setBiaoshiDesc(String biaoshiDesc) {
        this.biaoshiDesc = biaoshiDesc;
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

    public Long getPrepareSectionCount() {
        return prepareSectionCount;
    }

    public void setPrepareSectionCount(Long prepareSectionCount) {
        this.prepareSectionCount = prepareSectionCount;
    }

    public UserSimpleVO getSpecialSectionApplicant() {
        return specialSectionApplicant;
    }

    public void setSpecialSectionApplicant(UserSimpleVO specialSectionApplicant) {
        this.specialSectionApplicant = specialSectionApplicant;
    }

    public String getSpecialSectionRemark() {
        return specialSectionRemark;
    }

    public void setSpecialSectionRemark(String specialSectionRemark) {
        this.specialSectionRemark = specialSectionRemark;
    }

    public List<DyeApplyVO> getDyeApply() {
        return dyeApply;
    }

    public void setDyeApply(List<DyeApplyVO> dyeApply) {
        this.dyeApply = dyeApply;
    }
}
