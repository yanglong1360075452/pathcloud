package com.lifetech.dhap.pathcloud.localdaemon.device.constants;

import java.util.HashMap;
import java.util.Map;

public enum DehydratorEvent {
	
	POWER_ON(InstrumentEventType.INFO, "POWER_ON"),
	POWER_FAIL(InstrumentEventType.ERR, "POWER_FAIL"),
	BATTERY_LOW(InstrumentEventType.WARN, "BATTERY_LOW"),
	SHUTTING_DOWN(InstrumentEventType.INFO, "SHUTTING_DOWN"),
	//TOUCH_PRESS(DehydratorEventType.INFO, "//TOUCH_PRESS"),
	LID_OPEN(InstrumentEventType.INFO, "LID_OPEN"),
	FILL_INSUFF(InstrumentEventType.WARN, "FILL_INSUFF"),
	DEVICE_ERR(InstrumentEventType.ERR, "DEVICE_ERR"),
	//VISIT_CODE(DehydratorEventType.INFO, "//VISIT_CODE"),
	PROGRAM_START(InstrumentEventType.INFO, "PROGRAM_START"),
	FLUSH_START(InstrumentEventType.INFO, "FLUSH_START"),
	TEMP_OUT_OF_RANGE(InstrumentEventType.WARN, "TEMP_OUT_OF_RANGE"),
	FILTER_CHANGED(InstrumentEventType.INFO, "FILTER_CHANGED"),
	FILTER_DATED(InstrumentEventType.WARN, "FILTER_DATED"),
	ALCHOL_LIMIT(InstrumentEventType.WARN, "ALCHOL_LIMIT");

	private static Map<Character, DehydratorEvent> CODE_REF;
	static {
		CODE_REF = new HashMap<>();
		CODE_REF.put('A', POWER_ON);
		CODE_REF.put('B', POWER_FAIL);
		CODE_REF.put('C', BATTERY_LOW);
		CODE_REF.put('D', SHUTTING_DOWN);
//		CODE_REF.put('E', TOUCH_PRESS);
		CODE_REF.put('F', LID_OPEN);
		CODE_REF.put('G', FILL_INSUFF);
		CODE_REF.put('H', DEVICE_ERR);
//		CODE_REF.put('I', VISIT_CODE);
		CODE_REF.put('J', PROGRAM_START);
		CODE_REF.put('K', FLUSH_START);
		CODE_REF.put('L', TEMP_OUT_OF_RANGE);
		CODE_REF.put('M', FILTER_CHANGED);
		CODE_REF.put('N', FILTER_DATED);
		CODE_REF.put('O', ALCHOL_LIMIT);
	}
	
	DehydratorEvent(InstrumentEventType severe, String msg) {
		this.severe = severe;
		this.msg = msg;
	}
	
	public static DehydratorEvent getInstance(char c) {
		return CODE_REF.get(c);
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public InstrumentEventType getSevere() {
		return severe;
	}

	public void setSevere(InstrumentEventType severe) {
		this.severe = severe;
	}

	private String msg;
	private InstrumentEventType severe;
}
