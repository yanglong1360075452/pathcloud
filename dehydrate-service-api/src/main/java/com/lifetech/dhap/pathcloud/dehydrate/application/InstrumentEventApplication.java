package com.lifetech.dhap.pathcloud.dehydrate.application;

public interface InstrumentEventApplication {

	void insertEvenByCode(String sn, String code, Long ts);

	void insertEvenByMsg(String sn, String msg, int level, Long ts);

}
