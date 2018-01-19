package com.lifetech.dhap.pathcloud.dehydrate.api.impl;

import com.lifetech.dhap.pathcloud.common.exception.BuzException;
import com.lifetech.dhap.pathcloud.common.exception.BuzExceptionCode;
import com.lifetech.dhap.pathcloud.common.response.PageDataVO;
import com.lifetech.dhap.pathcloud.common.response.ResponseVO;
import com.lifetech.dhap.pathcloud.dehydrate.api.DehydratorSettingApi;
import com.lifetech.dhap.pathcloud.dehydrate.api.vo.DehydratorVO;
import com.lifetech.dhap.pathcloud.dehydrate.application.DehydratorApplication;
import com.lifetech.dhap.pathcloud.dehydrate.application.InstrumentApplication;
import com.lifetech.dhap.pathcloud.dehydrate.application.condition.DehydratorCondition;
import com.lifetech.dhap.pathcloud.dehydrate.application.condition.DehydratorErrorMsgCondition;
import com.lifetech.dhap.pathcloud.dehydrate.application.condition.GetBlockCondition;
import com.lifetech.dhap.pathcloud.dehydrate.application.dto.*;
import com.lifetech.dhap.pathcloud.localdaemon.device.constants.DehydratorStatus;
import com.lifetech.dhap.pathcloud.localdaemon.device.constants.InstrumentEventType;
import com.lifetech.dhap.pathcloud.localdaemon.device.constants.InstrumentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.DefaultValue;
import java.util.*;

@Component("dehydratorApi")
public class DehydratorSettingApiImpl implements DehydratorSettingApi {
    @Autowired
    private DehydratorApplication dehydratorApplication;

    @Autowired
    private InstrumentApplication instrumentApplication;

    @Override
    public ResponseVO addDehydrator(DehydratorVO dehydratorVO) throws BuzException {
        String name = dehydratorVO.getName();
        String sn = dehydratorVO.getSn();
        Integer capacity = dehydratorVO.getCapacity();
        Boolean disabled = dehydratorVO.getDisabled();

        if (name == null || name.trim().equals("")
                || sn == null || sn.trim().equals("")
                || capacity == null || capacity < 0
                || disabled == null) {
            throw new BuzException(BuzExceptionCode.ErrorParam);
        }

        String type = dehydratorApplication.addOrUpdateDehydrator(dehydratorVO);

        Map<String, String> resultData = new HashMap<>();
        resultData.put("result_type", type);
        ResponseVO responseVO = new ResponseVO(resultData);
        return responseVO;
    }

    @Override
    public ResponseVO removeDehydrator(Long instrumentId) throws BuzException {
        DehydratorVO dehydratorVO = dehydratorApplication.getDehydratorByInstrumentId(instrumentId);
        if(dehydratorVO != null){
            if(!dehydratorVO.getUsedBlock().equals(0)){
                throw new BuzException(BuzExceptionCode.DehydratorInUse);
            }
            dehydratorApplication.deleteDehydrator(instrumentId);
        }
        return new ResponseVO();
    }

    @Override
    public ResponseVO getDehydratorStatus() throws BuzException {
        List<DehydratorStatusDto> data = dehydratorApplication.getAllDehydratorStatus();
        List<Map<String, Object>> resultData = new ArrayList<>();

        for (DehydratorStatusDto dto : data) {
            Map<String, Object> unit = new HashMap<>();

            unit.put("instrumentId", dto.getInstrumentId());
            unit.put("name", dto.getName());
            unit.put("sn", dto.getSn());
            unit.put("capacity", dto.getCapacity());
            unit.put("description", dto.getDescription());
            unit.put("inUse", dto.getInUse());
            unit.put("usedBlock", dto.getUsedBlock());
            Integer status = dto.getStatus();
            if(status.equals(DehydratorStatus.Pause.getCode())){
                unit.put("pause",true);
            }else {
                unit.put("pause",false);
            }

            Date lastClearTime = dto.getLastClear();
            Date lastErrorTime = dto.getLatestErrTime();
            int errLevel = InstrumentEventType.ERR.getCode();
            boolean noWarning = lastErrorTime == null || (lastClearTime != null && lastClearTime.after(lastErrorTime));

            if (noWarning || dto.getErrLevel() < errLevel) {
                unit.put("status", DehydratorStatus.Normal.getCode());
                unit.put("errMsg", null);
                unit.put("latestErrTime", null);
            } else {
                unit.put("status", DehydratorStatus.Error.getCode());
                unit.put("errMsg", dto.getErrMsg());
                unit.put("latestErrTime", dto.getLatestErrTime());
            }
            unit.put("disabled", dto.getDisabled());

            resultData.add(unit);
        }

        return new ResponseVO(resultData);
    }

    @Override
    public ResponseVO getWarningMsg(Long instrumentId, @DefaultValue("1") Integer page, @DefaultValue("10") Integer length,
                                    Long startTime, Long endTime) throws BuzException {
        if (page < 0 || length < 1 || instrumentId == null) {
            throw new BuzException(BuzExceptionCode.ErrorParam);
        }
        DehydratorErrorMsgCondition condition = new DehydratorErrorMsgCondition();
        condition.setInstrumentId(instrumentId);
        condition.setSize(length);
        condition.setStart((page - 1) * length);
        if (startTime != null) {
            condition.setStartTime(new Date(startTime));
        }
        if (endTime != null) {
            condition.setEndTime(new Date(endTime));
        }
        condition.setLevel(InstrumentEventType.ERR.getCode());
        List<DehydratorErrorMsgDto> msgs = dehydratorApplication.getErrMsgByCondition(condition);
        long total = dehydratorApplication.countErrMsgByCondition(condition);
        return new PageDataVO(page, length, total, msgs);
    }

    @Override
    public ResponseVO getBlocksInfo(Long instrumentId, @DefaultValue("1") Integer page, @DefaultValue("10") Integer length) throws BuzException {
        if (page < 0 || length < 1 || instrumentId == null) {
            throw new BuzException(BuzExceptionCode.ErrorParam);
        }
        GetBlockCondition condition = new GetBlockCondition();
        condition.setSize(length);
        condition.setStart((page - 1) * length);
        condition.setInstrumentId(instrumentId);
        List<BlockInfoDto> blockInfoDtos = dehydratorApplication.getBlocksInfoByCondition(condition);
        Long total = Long.valueOf(dehydratorApplication.getDehydratorByInstrumentId(instrumentId).getUsedBlock());
        return new PageDataVO(page, length, total, blockInfoDtos);
    }

    @Override
    public ResponseVO clearWarningMsg(Long instrumentId) throws BuzException {
        dehydratorApplication.clearWarning(instrumentId);
        return new ResponseVO();
    }

    @Override
    public ResponseVO getDehydrators(Boolean inUse, Boolean disable, Integer page, Integer length,Integer status) throws BuzException {
        if (page < 0 || length < 1) {
            throw new BuzException(BuzExceptionCode.ErrorParam);
        }
        DehydratorCondition condition = new DehydratorCondition();
        condition.setSize(length);
        condition.setStart((page - 1) * length);
        condition.setInUse(inUse);
        condition.setDisabled(disable);
        condition.setStatus(status);
        List<DehydratorDto> dehydratorDtos = dehydratorApplication.getDehydratorByCondition(condition);
        if(dehydratorDtos != null && dehydratorDtos.size() > 0){
            for(DehydratorDto dehydratorDto : dehydratorDtos){
                dehydratorDto.setStatusDesc(DehydratorStatus.getNameByCode(dehydratorDto.getStatus()));
            }
        }
        Long total = dehydratorApplication.countDehydratorByCondition(condition);
        return new PageDataVO(page, length, total, dehydratorDtos);
    }

    @Override
    public ResponseVO getLastDehydrator() {
        DehydratorDto lastDehydrator = dehydratorApplication.getLastDehydrator();
        Map<String, Object> unit = new HashMap<>();

        unit.put("instrumentId", lastDehydrator.getInstrumentId());
        unit.put("name", lastDehydrator.getName());
        unit.put("sn", lastDehydrator.getSn());
        unit.put("capacity", lastDehydrator.getCapacity());
        unit.put("description", lastDehydrator.getDescription());
        unit.put("inUse", lastDehydrator.getInUse());
        unit.put("disabled", lastDehydrator.getDisabled());
        unit.put("id", lastDehydrator.getId());
        return new ResponseVO(unit);
    }

    @Override
    public ResponseVO checkSn(String sn) throws BuzException {
        InstrumentDto instrumentDto = instrumentApplication.getInstrumentBySnAndType(sn, InstrumentType.Dehydrator.getCode());
        if (instrumentDto != null) {
            return new ResponseVO(BuzExceptionCode.RecordExists, BuzExceptionCode.getReasonByCode(BuzExceptionCode.RecordExists.hashCode()));
        }
        return new ResponseVO();
    }

    @Override
    public ResponseVO checkName(String name) throws BuzException {
        InstrumentDto instrumentDto = instrumentApplication.getInstrumentByNameAndType(name, InstrumentType.Dehydrator.getCode());
        if (instrumentDto != null) {
            return new ResponseVO(BuzExceptionCode.RecordExists, BuzExceptionCode.getReasonByCode(BuzExceptionCode.RecordExists.hashCode()));
        }
        return new ResponseVO();
    }
}
