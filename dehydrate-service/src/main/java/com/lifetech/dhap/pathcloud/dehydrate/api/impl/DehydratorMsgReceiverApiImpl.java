package com.lifetech.dhap.pathcloud.dehydrate.api.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lifetech.dhap.pathcloud.common.exception.BuzException;
import com.lifetech.dhap.pathcloud.common.exception.BuzExceptionCode;
import com.lifetech.dhap.pathcloud.common.response.ResponseVO;
import com.lifetech.dhap.pathcloud.dehydrate.api.DehydratorMsgReceiverApi;
import com.lifetech.dhap.pathcloud.dehydrate.application.InstrumentApplication;
import com.lifetech.dhap.pathcloud.dehydrate.application.InstrumentEventApplication;
import com.lifetech.dhap.pathcloud.localdaemon.device.constants.InstrumentEventType;

@Component("localDehydratorApi")
public class DehydratorMsgReceiverApiImpl implements DehydratorMsgReceiverApi {
	@Autowired
	private InstrumentApplication instrumentApplication;

	@Autowired
	private InstrumentEventApplication instrumentEventApplication;
	
	@Override
	public ResponseVO heartBreak(String sn, Long ts) throws BuzException {
		if (ts == null || ts < 0) {
			throw new BuzException(BuzExceptionCode.ErrorParam);
		}
		
		instrumentApplication.updateHeartBreak(sn, ts);
		
		return new ResponseVO();
	}

	@Override
	public ResponseVO newEventByCode(String sn, String code, Long ts) throws BuzException {
		if (code == null || code.trim().equals("") || ts == null || ts < 0) {
			throw new BuzException(BuzExceptionCode.ErrorParam);
		}

		instrumentEventApplication.insertEvenByCode(sn, code, ts);
		
		return new ResponseVO();
	}

	@Override
	public ResponseVO newEventByMsg(String sn, String msg, Integer level, Long ts) throws BuzException {
		if (msg == null || msg.trim().equals("") || ts == null || ts < 0
				|| level == 0 || level < 0 || InstrumentEventType.getByCode(level) == null) {
			
			throw new BuzException(BuzExceptionCode.ErrorParam);
		}
		
		instrumentEventApplication.insertEvenByMsg(sn, msg, level, ts);

		return new ResponseVO();
	}
}
