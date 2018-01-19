package com.lifetech.dnap.pathcloud.reagent.application.dto;

import java.util.Date;

/**
 * @author LiuMei
 * @date 2017-11-29.
 */
public class StoreDto {

    private Long id;

    private Long reagentId;

    private String reagentName;

    private Integer reagentCategory;
    private String reagentCategoryDesc;

    private Integer reagentType;
    private String reagentTypeDesc;

    private Integer status;
    private String statusDesc;

    private String orderNumber;

    private String batchNumber;

    private String productNumber;

    private String manufacturer;

    private String articleNumber;

    private Date produceTime;

    private Date expiryTime;

    private String manufacturerPhone;

    private String spec;

    private Integer receiveStatus;

    private String preparation;

    private Date preparationTime;

    private String preExperiment;

    private Date preExperimentTime;

    private String preExperimentResult;

    private Double specification;

    private Integer type;

    private Double dilutionRateFront;

    private Double dilutionRateRear;

    private Double realCapacity;

    private Integer count;

    private Integer countUnit;

    private Double totalCapacity;

    private Double usedCapacity;

    private Long createBy;

    private Long updateBy;

    private Date createTime;

    private Date updateTime;

    private Double adjust;

    private String note;

    public Double getAdjust() {
        return adjust;
    }

    public void setAdjust(Double adjust) {
        this.adjust = adjust;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Integer getReagentType() {
        return reagentType;
    }

    public void setReagentType(Integer reagentType) {
        this.reagentType = reagentType;
    }

    public String getReagentTypeDesc() {
        return reagentTypeDesc;
    }

    public void setReagentTypeDesc(String reagentTypeDesc) {
        this.reagentTypeDesc = reagentTypeDesc;
    }

    public String getReagentName() {
        return reagentName;
    }

    public void setReagentName(String reagentName) {
        this.reagentName = reagentName;
    }

    public Integer getReagentCategory() {
        return reagentCategory;
    }

    public void setReagentCategory(Integer reagentCategory) {
        this.reagentCategory = reagentCategory;
    }

    public String getReagentCategoryDesc() {
        return reagentCategoryDesc;
    }

    public void setReagentCategoryDesc(String reagentCategoryDesc) {
        this.reagentCategoryDesc = reagentCategoryDesc;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getReagentId() {
        return reagentId;
    }

    public void setReagentId(Long reagentId) {
        this.reagentId = reagentId;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getBatchNumber() {
        return batchNumber;
    }

    public void setBatchNumber(String batchNumber) {
        this.batchNumber = batchNumber;
    }

    public String getProductNumber() {
        return productNumber;
    }

    public void setProductNumber(String productNumber) {
        this.productNumber = productNumber;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getArticleNumber() {
        return articleNumber;
    }

    public void setArticleNumber(String articleNumber) {
        this.articleNumber = articleNumber;
    }

    public Date getProduceTime() {
        return produceTime;
    }

    public void setProduceTime(Date produceTime) {
        this.produceTime = produceTime;
    }

    public Date getExpiryTime() {
        return expiryTime;
    }

    public void setExpiryTime(Date expiryTime) {
        this.expiryTime = expiryTime;
    }

    public String getManufacturerPhone() {
        return manufacturerPhone;
    }

    public void setManufacturerPhone(String manufacturerPhone) {
        this.manufacturerPhone = manufacturerPhone;
    }

    public String getSpec() {
        return spec;
    }

    public void setSpec(String spec) {
        this.spec = spec;
    }

    public Integer getReceiveStatus() {
        return receiveStatus;
    }

    public void setReceiveStatus(Integer receiveStatus) {
        this.receiveStatus = receiveStatus;
    }

    public String getPreparation() {
        return preparation;
    }

    public void setPreparation(String preparation) {
        this.preparation = preparation;
    }

    public Date getPreparationTime() {
        return preparationTime;
    }

    public void setPreparationTime(Date preparationTime) {
        this.preparationTime = preparationTime;
    }

    public String getPreExperiment() {
        return preExperiment;
    }

    public void setPreExperiment(String preExperiment) {
        this.preExperiment = preExperiment;
    }

    public Date getPreExperimentTime() {
        return preExperimentTime;
    }

    public void setPreExperimentTime(Date preExperimentTime) {
        this.preExperimentTime = preExperimentTime;
    }

    public String getPreExperimentResult() {
        return preExperimentResult;
    }

    public void setPreExperimentResult(String preExperimentResult) {
        this.preExperimentResult = preExperimentResult;
    }

    public Double getSpecification() {
        return specification;
    }

    public void setSpecification(Double specification) {
        this.specification = specification;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Double getDilutionRateFront() {
        return dilutionRateFront;
    }

    public void setDilutionRateFront(Double dilutionRateFront) {
        this.dilutionRateFront = dilutionRateFront;
    }

    public Double getDilutionRateRear() {
        return dilutionRateRear;
    }

    public void setDilutionRateRear(Double dilutionRateRear) {
        this.dilutionRateRear = dilutionRateRear;
    }

    public Double getRealCapacity() {
        return realCapacity;
    }

    public void setRealCapacity(Double realCapacity) {
        this.realCapacity = realCapacity;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getCountUnit() {
        return countUnit;
    }

    public void setCountUnit(Integer countUnit) {
        this.countUnit = countUnit;
    }

    public Double getTotalCapacity() {
        return totalCapacity;
    }

    public void setTotalCapacity(Double totalCapacity) {
        this.totalCapacity = totalCapacity;
    }

    public Double getUsedCapacity() {
        return usedCapacity;
    }

    public void setUsedCapacity(Double usedCapacity) {
        this.usedCapacity = usedCapacity;
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
}
