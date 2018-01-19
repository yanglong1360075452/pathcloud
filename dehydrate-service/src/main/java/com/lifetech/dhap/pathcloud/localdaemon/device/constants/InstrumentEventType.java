package com.lifetech.dhap.pathcloud.localdaemon.device.constants;

public enum InstrumentEventType {
	INFO(0, "info"),
	WARN(1, "warning"),
	ERR(2, "error");
	
	InstrumentEventType(int code, String name) {
		this.code = code;
		this.name = name;
	}
	
	public static InstrumentEventType getByCode(int code) {
		InstrumentEventType[] vals = InstrumentEventType.values();
		for (InstrumentEventType val: vals) {
			if (val.getCode() == code) {
				return val;
			}
		}
		
		return null;
	}
	
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	private int code;
	private String name;
}
