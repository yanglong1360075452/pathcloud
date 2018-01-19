package com.lifetech.dhap.pathcloud.dehydrate.application.dto;

import java.util.Date;

/**
 * 
 * @author yun.cao
 *
 * Create at 20161215
 */
public class DehydratorDto {

	private Long id;
	private Long instrumentId;
	private String name;
	private String sn;
	private Integer capacity;
	private String description;
	private Integer usedBlock;
	private Date lastClear;
	private Boolean inUse;
	private Boolean disabled;
	private Integer status;
	private String statusDesc;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getInstrumentId() {
		return instrumentId;
	}

	public void setInstrumentId(Long instrumentId) {
		this.instrumentId = instrumentId;
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

	public Integer getCapacity() {
		return capacity;
	}

	public void setCapacity(Integer capacity) {
		this.capacity = capacity;
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

	public Date getLastClear() {
		return lastClear;
	}

	public void setLastClear(Date lastClear) {
		this.lastClear = lastClear;
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
