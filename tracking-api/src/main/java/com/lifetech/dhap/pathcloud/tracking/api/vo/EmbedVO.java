package com.lifetech.dhap.pathcloud.tracking.api.vo;

import com.lifetech.dhap.pathcloud.user.api.vo.UserSimpleVO;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by LiuMei on 2016-12-20.
 */
public class EmbedVO implements Serializable {
    private Long applicationId;

    private Long pathologyId;

    private Long blockId;

    private String applicationSerialNumber;

    private String pathologySerialNumber;

    private String blockSerialNumber;

    private String patientName;

    private Integer departments;
    private String departmentsDesc;

    /**
     * 取材医生
     */
    private UserSimpleVO grossingDoctor;

    /**
     * 取材备注
     */
    private String grossingRemark;

    /**
     * 包埋备注
     */
    private String embedRemark;

    /**
     * 包埋备注类别
     */
    private String embedRemarkType;

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
     * 待包埋蜡块总数
     */
    private Long prepareEmbedCount;

    /**
     * 当前病理号已包埋蜡块数
     */
    private Long  pathEmbedCount;

    /**
     * 当前病理号总蜡块数
     */
    private Long  pathBlockCount;

    private Boolean embedPause;

    private Long embedOperator;
    private String embedOperatorDesc;

    private Date embedOperateTime;

    public long getEmbedOperator() {
        return embedOperator;
    }

    public void setEmbedOperator(long embedOperator) {
        this.embedOperator = embedOperator;
    }

    public String getEmbedOperatorDesc() {
        return embedOperatorDesc;
    }

    public void setEmbedOperatorDesc(String embedOperatorDesc) {
        this.embedOperatorDesc = embedOperatorDesc;
    }

    public Date getEmbedOperateTime() {
        return embedOperateTime;
    }

    public void setEmbedOperateTime(Date embedOperateTime) {
        this.embedOperateTime = embedOperateTime;
    }

    public Boolean getEmbedPause() {
        return embedPause;
    }

    public void setEmbedPause(Boolean embedPause) {
        this.embedPause = embedPause;
    }

    private List<String> otherEmbed;

    public List<String> getOtherEmbed() {
        return otherEmbed;
    }

    public void setOtherEmbed(List<String> otherEmbed) {
        this.otherEmbed = otherEmbed;
    }

    public String getEmbedRemarkType() {
        return embedRemarkType;
    }

    public void setEmbedRemarkType(String embedRemarkType) {
        this.embedRemarkType = embedRemarkType;
    }

    public Long getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(Long applicationId) {
        this.applicationId = applicationId;
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

    public String getDepartmentsDesc() {
        return departmentsDesc;
    }

    public void setDepartmentsDesc(String departmentsDesc) {
        this.departmentsDesc = departmentsDesc;
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

    public String getEmbedRemark() {
        return embedRemark;
    }

    public void setEmbedRemark(String embedRemark) {
        this.embedRemark = embedRemark;
    }

    public String getSubId() {
        return subId;
    }

    public void setSubId(String subId) {
        this.subId = subId;
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

    public Long getPrepareEmbedCount() {
        return prepareEmbedCount;
    }

    public void setPrepareEmbedCount(Long prepareEmbedCount) {
        this.prepareEmbedCount = prepareEmbedCount;
    }

    public String getBodyPart() {
        return bodyPart;
    }

    public void setBodyPart(String bodyPart) {
        this.bodyPart = bodyPart;
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

    public long getPathEmbedCount() {
        return pathEmbedCount;
    }

    public void setPathEmbedCount(long pathEmbedCount) {
        this.pathEmbedCount = pathEmbedCount;
    }

    public long getPathBlockCount() {
        return pathBlockCount;
    }

    public void setPathBlockCount(long pathBlockCount) {
        this.pathBlockCount = pathBlockCount;
    }
}
