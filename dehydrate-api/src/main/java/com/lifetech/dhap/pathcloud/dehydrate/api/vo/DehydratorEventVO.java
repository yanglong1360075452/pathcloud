package com.lifetech.dhap.pathcloud.dehydrate.api.vo;

public class DehydratorEventVO {
	public String getSn() {
		return sn;
	}
	public void setSn(String sn) {
		this.sn = sn;
	}
	public Integer getSevere() {
		return severe;
	}
	public void setSevere(Integer severe) {
		this.severe = severe;
	}
	public Long getEventTimestamp() {
		return eventTimestamp;
	}
	public void setEventTimestamp(Long eventTimestamp) {
		this.eventTimestamp = eventTimestamp;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}

	private String sn;
	private Integer severe;
	private Long eventTimestamp;
	private String code;
	private String msg;
}
