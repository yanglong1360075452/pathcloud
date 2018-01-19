package com.lifetech.dhap.pathcloud.reagent.api.vo;

import java.util.Date;

/**
 * @author LiuMei
 * @date 2017-11-30.
 */
public class StoreVO {

    private Long id;

    private Long reagentId;

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
}
