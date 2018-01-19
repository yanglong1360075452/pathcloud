package com.lifetech.dhap.pathcloud.dehydrate.api.vo;

/**
 * @author yun.cao
 *         <p>
 *         Create at 20161207
 */
public class InstrumentVO {

    private Long id;
    private String name;
    private String sn;
    private String description;
    private Integer usedBlock;
    private Boolean inUse;
    private Boolean disabled;
    private Integer status;
    private String statusDesc;
    private Integer type;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getUsedBlock() {
        return usedBlock;
    }

    public void setUsedBlock(Integer usedBlock) {
        this.usedBlock = usedBlock;
    }

    public Boolean getInUse() {
        return inUse;
    }

    public void setInUse(Boolean inUse) {
        this.inUse = inUse;
    }

    public Boolean getDisabled() {
        return disabled;
    }

    public void setDisabled(Boolean disabled) {
        this.disabled = disabled;
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
}
