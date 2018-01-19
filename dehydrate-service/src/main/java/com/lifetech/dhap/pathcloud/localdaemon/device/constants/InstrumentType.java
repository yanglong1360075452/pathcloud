package com.lifetech.dhap.pathcloud.localdaemon.device.constants;

public enum InstrumentType {
	Dehydrator(0, "脱水机"),
	DyeingMachine(1, "染色机"),
	SealingMachine(2, "封片机"),
	SectionMachine(3, "冰冻切片机"),
			;

	InstrumentType(int code, String name) {
		this.code = code;
		this.name = name;
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
