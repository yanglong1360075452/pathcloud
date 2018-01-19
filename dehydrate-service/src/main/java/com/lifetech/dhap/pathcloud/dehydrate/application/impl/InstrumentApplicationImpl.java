package com.lifetech.dhap.pathcloud.dehydrate.application.impl;

import com.lifetech.dhap.pathcloud.common.domain.CommonRepository;
import com.lifetech.dhap.pathcloud.common.exception.BuzException;
import com.lifetech.dhap.pathcloud.common.exception.BuzExceptionCode;
import com.lifetech.dhap.pathcloud.dehydrate.application.InstrumentApplication;
import com.lifetech.dhap.pathcloud.dehydrate.application.condition.InstrumentCondition;
import com.lifetech.dhap.pathcloud.dehydrate.application.dto.InstrumentDto;
import com.lifetech.dhap.pathcloud.dehydrate.domain.InstrumentEventRepository;
import com.lifetech.dhap.pathcloud.dehydrate.domain.InstrumentRepository;
import com.lifetech.dhap.pathcloud.dehydrate.domain.model.Instrument;
import com.lifetech.dhap.pathcloud.dehydrate.domain.model.InstrumentEvent;
import com.lifetech.dhap.pathcloud.localdaemon.device.constants.InstrumentEventType;
import com.lifetech.dhap.pathcloud.localdaemon.device.constants.InstrumentStatus;
import com.lifetech.dhap.pathcloud.security.UserContext;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class InstrumentApplicationImpl implements InstrumentApplication {
    private static final Logger LOGGER = LoggerFactory.getLogger(InstrumentApplicationImpl.class);

    private static final long HEARTBREAK_MAX = 2 * 60 * 1000;

    @Autowired
    private InstrumentRepository instrumentRepository;

    @Autowired
    private InstrumentEventRepository instrumentEventRepository;

    @Autowired
    private CommonRepository commonRepository;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addInstrument(InstrumentDto instrumentDto) {
        if (instrumentDto != null) {
            Instrument instrument = new Instrument();
            BeanUtils.copyProperties(instrumentDto, instrument);
            instrumentRepository.insert(instrument);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateHeartBreak(String sn, Long ts) {
        Instrument instrument = instrumentRepository.selectAvailableBySerialNumber(sn);
        if (instrument == null) {
            throw new BuzException(BuzExceptionCode.DehydratorNotExist);
        }

        instrument.setLastHeartbreak(ts);
        instrument.setUpdateBy(UserContext.getSystemUserID());
        instrumentRepository.updateByPrimaryKey(instrument);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void checkHeartBreak() {
        List<Instrument> instruments = instrumentRepository.selectAvailable();

        long currTime = System.currentTimeMillis();
        for (Instrument inst : instruments) {
            Long lastHT = inst.getLastHeartbreak();
            if (lastHT == null || currTime - lastHT > HEARTBREAK_MAX) {
                try {
                    InstrumentEvent event = new InstrumentEvent();
                    event.setInstrumentId(inst.getId());
                    event.setLevel(InstrumentEventType.ERR.getCode());
                    event.setOccurTime(new Date(currTime));
                    event.setCreateBy(UserContext.getSystemUserID());
                    event.setUpdateBy(UserContext.getSystemUserID());
                    event.setStatus(true);
                    String msg = "Dehydrator no response. Latest heartbreak: " + lastHT;
                    event.setMsg(msg);
                    instrumentEventRepository.insert(event);
                } catch (Exception e) {
                    LOGGER.error("Failed to insert instrument event. Instrument id: " + inst.getId()
                            + ", latest heartbreak: " + lastHT, e);
                }
            }
        }
    }

    @Override
    public InstrumentDto getInstrumentBySnAndType(String sn, int type) {
        Instrument instrument = instrumentRepository.selectBySnAndType(sn, type);
        if (instrument != null) {
            InstrumentDto instrumentDto = new InstrumentDto();
            BeanUtils.copyProperties(instrument, instrumentDto);
            return instrumentDto;
        }
        return null;
    }

    @Override
    public InstrumentDto getInstrumentByNameAndType(String name, int type) {
        Instrument instrument = instrumentRepository.selectByNameAndType(name, type);
        if (instrument != null) {
            InstrumentDto instrumentDto = new InstrumentDto();
            BeanUtils.copyProperties(instrument, instrumentDto);
            return instrumentDto;
        }
        return null;
    }

    @Override
    public InstrumentDto getInstrumentById(long id) {
        InstrumentDto instrumentDto = null;
        Instrument instrument = instrumentRepository.selectByPrimaryKey(id);
        if (instrument != null) {
            instrumentDto = new InstrumentDto();
            BeanUtils.copyProperties(instrument, instrumentDto);
        }
        return instrumentDto;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateInstrument(InstrumentDto instrumentDto) {
        Long id = instrumentDto.getId();
        if (id != null) {
            Instrument instrument = new Instrument();
            String name = instrumentDto.getName();
            if (name != null) {
                instrument.setName(name);
            }
            String sn = instrumentDto.getSn();
            if (sn != null) {
                instrument.setSn(sn);
            }
            Integer status = instrumentDto.getStatus();
            if(status != null){
                instrument.setStatus(status);
            }
            instrument.setId(id);
            instrument.setDescription(instrumentDto.getDescription());
            instrument.setUpdateBy(UserContext.getLoginUserID());
            instrument.setUpdateTime(commonRepository.getDBNow());
            instrument.setDisabled(instrumentDto.getDisabled());
            instrumentRepository.updateByPrimaryKey(instrument);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteInstrument(long id) {
        instrumentRepository.deleteByPrimaryKey(id);
    }

    @Override
    public List<InstrumentDto> getInstrumentsByCondition(InstrumentCondition instrumentCondition) {
        List<InstrumentDto> instrumentDTOs = null;
        if (instrumentCondition != null){
            List<Instrument> instruments = instrumentRepository.selectByCondition(instrumentCondition);
            if(CollectionUtils.isNotEmpty(instruments)){
                instrumentDTOs = new ArrayList<>();
                InstrumentDto instrumentDto;
                for(Instrument instrument: instruments){
                    instrumentDto = new InstrumentDto();
                    BeanUtils.copyProperties(instrument,instrumentDto);
                    instrumentDto.setStatusDesc(InstrumentStatus.getNameByCode(instrument.getStatus()));
                    instrumentDTOs.add(instrumentDto);
                }
            }
        }
        return instrumentDTOs;
    }

    @Override
    public long countInstrumentsByCondition(InstrumentCondition instrumentCondition) {
        return instrumentRepository.countByCondition(instrumentCondition);
    }

    @Override
    public List<InstrumentDto> getInstrument(Integer type) {
        List<Instrument> instruments = instrumentRepository.getInstrument(type);
        List<InstrumentDto> dTos = new ArrayList<>();
        InstrumentDto instrumentDto;
        for (Instrument instrument:instruments){
            instrumentDto=new InstrumentDto();
            BeanUtils.copyProperties(instrument,instrumentDto);
            dTos.add(instrumentDto);
        }

        return dTos;
    }
}
