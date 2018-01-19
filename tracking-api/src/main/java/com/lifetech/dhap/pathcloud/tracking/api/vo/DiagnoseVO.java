package com.lifetech.dhap.pathcloud.tracking.api.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by LiuMei on 2017-01-06.
 */
public class DiagnoseVO implements Serializable {

    /**
     * 病理ID
     */
    private Long id;

    /**
     * 特殊申请ID
     */
    private Long specialApplyId;

    /**
     * 病理号
     */
    private String serialNumber;

    private Long applicationId;

    /**
     * 病理诊断
     */
    private String result;

    private String jujianNote;

    private String bingdongNote;

    /**
     * 病理状态
     */
    private Integer status;
    private String statusName;

    /**
     * 玻片状态
     */
    private Integer slideStatus;
    private String slideStatusDesc;

    private String patientName;

    private Integer sex;

    private Integer age;

    private Integer departments;
    private String departmentsDesc;

    private String inspectionItem;

    private Boolean urgentFlag;

    /**
     * 送检日期--application创建时间
     */
    private Date inspectionTime;

    /**
     * 预计报告日期
     */
    private Date estimateReportTime;

    /**
     * 已看玻片
     */
    private Long countWatchedSlide;
    /**
     * 病理号下总玻片数
     */
    private Long countSlides;

    /**
     * 巨检图像
     */
    private List<String> grossingImages;

    private Long slideId;

    private Long blockId;

    /**
     * 蜡块号
     */
    private String blockSubId;

    /**
     * 玻片号
     */
    private String slideSubId;

    private Integer biaoshi;
    private String biaoshiDesc;

    private Integer specialDye;
    private String specialDyeDesc;

    private String marker;

    /**
     * 质控评分
     */
    private BlockScoreVO score;

    private Integer inspectCategory;

    /**
     * 冰冻诊断结果
     */
    private String frozenResult;

    /**
     * 特检申请标记物
     */
    private List<String> markers;

    /**
     * 特检申请日期
     */
    private Date specialApplyTime;

    private Boolean collect;

    private String number;

    private String specialResult;

    /**
     * 会诊要求
     */
    private String note;

    private Integer type;

    private Boolean outConsult;

    private String outConsultResult;

    /**
     * 免疫组化申请ID
     */
    private Long ihcId;

    /**
     * 特染申请ID
     */
    private Long specialDyeId;

    /**
     * 是否可以申请免疫组化
     */
    private Boolean canApplyIhc;

    /**
     * 是否可以申请特染
     */
    private Boolean canApplySpecialDye;

    private Integer label;

    private List<FrozenGrossingVO> frozenGrossingData;

    public List<FrozenGrossingVO> getFrozenGrossingData() {
        return frozenGrossingData;
    }

    public void setFrozenGrossingData(List<FrozenGrossingVO> frozenGrossingData) {
        this.frozenGrossingData = frozenGrossingData;
    }

    public Integer getLabel() {
        return label;
    }

    public void setLabel(Integer label) {
        this.label = label;
    }

    public Boolean getCanApplySpecialDye() {
        return canApplySpecialDye;
    }

    public void setCanApplySpecialDye(Boolean canApplySpecialDye) {
        this.canApplySpecialDye = canApplySpecialDye;
    }

    public Boolean getCanApplyIhc() {
        return canApplyIhc;
    }

    public void setCanApplyIhc(Boolean canApplyIhc) {
        this.canApplyIhc = canApplyIhc;
    }

    public Long getIhcId() {
        return ihcId;
    }

    public void setIhcId(Long ihcId) {
        this.ihcId = ihcId;
    }

    public Long getSpecialDyeId() {
        return specialDyeId;
    }

    public void setSpecialDyeId(Long specialDyeId) {
        this.specialDyeId = specialDyeId;
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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getSpecialResult() {
        return specialResult;
    }

    public void setSpecialResult(String specialResult) {
        this.specialResult = specialResult;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Boolean getCollect() {
        return collect;
    }

    public void setCollect(Boolean collect) {
        this.collect = collect;
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

    public Date getSpecialApplyTime() {
        return specialApplyTime;
    }

    public void setSpecialApplyTime(Date specialApplyTime) {
        this.specialApplyTime = specialApplyTime;
    }

    public List<String> getMarkers() {
        return markers;
    }

    public void setMarkers(List<String> markers) {
        this.markers = markers;
    }

    public Long getSpecialApplyId() {
        return specialApplyId;
    }

    public void setSpecialApplyId(Long specialApplyId) {
        this.specialApplyId = specialApplyId;
    }

    public String getFrozenResult() {
        return frozenResult;
    }

    public void setFrozenResult(String frozenResult) {
        this.frozenResult = frozenResult;
    }

    public Integer getInspectCategory() {
        return inspectCategory;
    }

    public void setInspectCategory(Integer inspectCategory) {
        this.inspectCategory = inspectCategory;
    }

    public Date getEstimateReportTime() {
        return estimateReportTime;
    }

    public void setEstimateReportTime(Date estimateReportTime) {
        this.estimateReportTime = estimateReportTime;
    }

    public String getSlideSubId() {
        return slideSubId;
    }

    public void setSlideSubId(String slideSubId) {
        this.slideSubId = slideSubId;
    }

    public Integer getSlideStatus() {
        return slideStatus;
    }

    public void setSlideStatus(Integer slideStatus) {
        this.slideStatus = slideStatus;
    }

    public String getSlideStatusDesc() {
        return slideStatusDesc;
    }

    public void setSlideStatusDesc(String slideStatusDesc) {
        this.slideStatusDesc = slideStatusDesc;
    }

    public List<String> getGrossingImages() {
        return grossingImages;
    }

    public void setGrossingImages(List<String> grossingImages) {
        this.grossingImages = grossingImages;
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

    public Date getInspectionTime() {
        return inspectionTime;
    }

    public void setInspectionTime(Date inspectionTime) {
        this.inspectionTime = inspectionTime;
    }

    public Long getCountWatchedSlide() {
        return countWatchedSlide;
    }

    public void setCountWatchedSlide(Long countWatchedSlide) {
        this.countWatchedSlide = countWatchedSlide;
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

    public BlockScoreVO getScore() {
        return score;
    }

    public void setScore(BlockScoreVO score) {
        this.score = score;
    }

    public Integer getSpecialDye() {
        return specialDye;
    }

    public void setSpecialDye(Integer specialDye) {
        this.specialDye = specialDye;
    }

    public String getSpecialDyeDesc() {
        return specialDyeDesc;
    }

    public void setSpecialDyeDesc(String specialDyeDesc) {
        this.specialDyeDesc = specialDyeDesc;
    }

    public String getMarker() {
        return marker;
    }

    public void setMarker(String marker) {
        this.marker = marker;
    }

    public String getBlockSubId() {
        return blockSubId;
    }

    public void setBlockSubId(String blockSubId) {
        this.blockSubId = blockSubId;
    }

    public Long getCountSlides() {
        return countSlides;
    }

    public void setCountSlides(Long countSlides) {
        this.countSlides = countSlides;
    }

    public Long getSlideId() {
        return slideId;
    }

    public void setSlideId(Long slideId) {
        this.slideId = slideId;
    }

    public Long getBlockId() {
        return blockId;
    }

    public void setBlockId(Long blockId) {
        this.blockId = blockId;
    }
}
