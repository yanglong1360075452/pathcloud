package com.lifetech.dhap.pathcloud.dehydrate.application;

import com.lifetech.dhap.pathcloud.dehydrate.application.condition.InstrumentCondition;
import com.lifetech.dhap.pathcloud.dehydrate.application.dto.InstrumentDto;

import java.util.List;

public interface InstrumentApplication {

	void addInstrument(InstrumentDto instrumentDto);

	void updateHeartBreak(String sn, Long ts);

	void checkHeartBreak();

	InstrumentDto getInstrumentBySnAndType(String sn,int type);

	InstrumentDto getInstrumentByNameAndType(String name,int type);

	InstrumentDto getInstrumentById(long id);

	void updateInstrument(InstrumentDto instrumentDto);

	void deleteInstrument(long id);

	List<InstrumentDto> getInstrumentsByCondition(InstrumentCondition instrumentCondition);

	long countInstrumentsByCondition(InstrumentCondition instrumentCondition);

    List<InstrumentDto> getInstrument(Integer type);
}
