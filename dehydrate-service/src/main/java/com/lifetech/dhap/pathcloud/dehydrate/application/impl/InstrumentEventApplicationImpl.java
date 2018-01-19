package com.lifetech.dhap.pathcloud.dehydrate.application.impl;

import com.lifetech.dhap.pathcloud.common.exception.BuzException;
import com.lifetech.dhap.pathcloud.common.exception.BuzExceptionCode;
import com.lifetech.dhap.pathcloud.dehydrate.application.InstrumentEventApplication;
import com.lifetech.dhap.pathcloud.dehydrate.domain.InstrumentEventCodeRepository;
import com.lifetech.dhap.pathcloud.dehydrate.domain.InstrumentEventRepository;
import com.lifetech.dhap.pathcloud.dehydrate.domain.InstrumentRepository;
import com.lifetech.dhap.pathcloud.dehydrate.domain.model.Instrument;
import com.lifetech.dhap.pathcloud.dehydrate.domain.model.InstrumentEvent;
import com.lifetech.dhap.pathcloud.dehydrate.domain.model.InstrumentEventCode;
import com.lifetech.dhap.pathcloud.localdaemon.device.constants.InstrumentEventType;
import com.lifetech.dhap.pathcloud.security.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class InstrumentEventApplicationImpl implements InstrumentEventApplication {
	@Autowired
	private InstrumentRepository instrumentRepository;
	
	@Autowired
	private InstrumentEventRepository instrumentEventRepository;

	@Autowired
	private InstrumentEventCodeRepository instrumentEventCodeRepository;

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void insertEvenByCode(String sn, String code, Long ts) {
		Instrument instrument = instrumentRepository.selectAvailableBySerialNumber(sn);
		if (instrument == null)	{
			throw new BuzException(BuzExceptionCode.DehydratorNotExist);
		}
		
		String msg = null;
		int level = 0;
		InstrumentEventCode eventCodeIns = instrumentEventCodeRepository.selectByCode(instrument.getId(), code);
		if (eventCodeIns == null) {
			msg = "<Unmapped code> " + code + ".";
			level = InstrumentEventType.WARN.getCode();
		} else {
			msg = eventCodeIns.getMsg();
			level = eventCodeIns.getLevel();
		}

		saveEvent(instrument.getId(), level, msg, ts);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void insertEvenByMsg(String sn, String msg, int level, Long ts) {

		Instrument instrument = instrumentRepository.selectAvailableBySerialNumber(sn);
		if (instrument == null)	{
			throw new BuzException(BuzExceptionCode.DehydratorNotExist);
		}
		
		saveEvent(instrument.getId(), level, msg, ts);
	}
	
	private int saveEvent(long instrumentId, int level, String msg, long ts) {
		InstrumentEvent instrumentEvent = new InstrumentEvent();
		
		instrumentEvent.setInstrumentId(instrumentId);
		instrumentEvent.setLevel(level);
		instrumentEvent.setMsg(msg);
		instrumentEvent.setOccurTime(new Date(ts));
		instrumentEvent.setCreateBy(UserContext.getSystemUserID());
		instrumentEvent.setUpdateBy(UserContext.getSystemUserID());
		return instrumentEventRepository.insert(instrumentEvent);
	}
}
