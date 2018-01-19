package com.lifetech.dhap.pathcloud.dehydrate.application.impl;

import com.lifetech.dhap.pathcloud.common.data.PathologyStatus;
import com.lifetech.dhap.pathcloud.common.domain.CommonRepository;
import com.lifetech.dhap.pathcloud.common.exception.BuzException;
import com.lifetech.dhap.pathcloud.common.exception.BuzExceptionCode;
import com.lifetech.dhap.pathcloud.dehydrate.api.vo.DehydratorVO;
import com.lifetech.dhap.pathcloud.dehydrate.application.DehydratorApplication;
import com.lifetech.dhap.pathcloud.dehydrate.application.condition.DehydratorCondition;
import com.lifetech.dhap.pathcloud.dehydrate.application.condition.DehydratorErrorMsgCondition;
import com.lifetech.dhap.pathcloud.dehydrate.application.condition.GetBlockCondition;
import com.lifetech.dhap.pathcloud.dehydrate.application.dto.BlockInfoDto;
import com.lifetech.dhap.pathcloud.dehydrate.application.dto.DehydratorDto;
import com.lifetech.dhap.pathcloud.dehydrate.application.dto.DehydratorErrorMsgDto;
import com.lifetech.dhap.pathcloud.dehydrate.application.dto.DehydratorStatusDto;
import com.lifetech.dhap.pathcloud.dehydrate.domain.DehydratorRepository;
import com.lifetech.dhap.pathcloud.dehydrate.domain.InstrumentEventRepository;
import com.lifetech.dhap.pathcloud.dehydrate.domain.InstrumentRepository;
import com.lifetech.dhap.pathcloud.dehydrate.domain.model.*;
import com.lifetech.dhap.pathcloud.localdaemon.device.constants.Code;
import com.lifetech.dhap.pathcloud.localdaemon.device.constants.DehydratorStatus;
import com.lifetech.dhap.pathcloud.localdaemon.device.constants.InstrumentStatus;
import com.lifetech.dhap.pathcloud.localdaemon.device.constants.InstrumentType;
import com.lifetech.dhap.pathcloud.security.UserContext;
import com.lifetech.dhap.pathcloud.setting.application.ParamSettingApplication;
import com.lifetech.dhap.pathcloud.setting.application.infrastructure.code.ParamKey;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class DehydratorApplicationImpl implements DehydratorApplication {

    @Autowired
    private InstrumentRepository instrumentRepository;

    @Autowired
    private DehydratorRepository dehydratorRepository;

    @Autowired
    private InstrumentEventRepository instrumentEventRepository;

    @Autowired
    private CommonRepository commonRepository;

    @Autowired
    private ParamSettingApplication paramSettingApplication;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public synchronized String addOrUpdateDehydrator(DehydratorVO dehydratorVO) {
        Instrument instrument = getInstrument(dehydratorVO);
        if (instrument == null) {
            addDehydrator(dehydratorVO);
            return Code.DEHYDRATOR_REC_TYPE_ADDED;
        } else {
            updateDehydratorInfo(dehydratorVO);
            return Code.DEHYDRATOR_REC_TYPE_UPDATED;
        }
    }

    @Override
    public DehydratorVO getDehydratorByInstrumentId(long instrumentId) throws BuzException {
        Dehydrator dehydrator = dehydratorRepository.selectByInstrumentId(instrumentId);
        if (dehydrator == null) {
            throw new BuzException(BuzExceptionCode.DehydratorNotExist);
        }
        Instrument instrument = instrumentRepository.selectByPrimaryKey(dehydrator.getInstrumentId());
        DehydratorVO dehydratorVO = new DehydratorVO();
        BeanUtils.copyProperties(dehydrator, dehydratorVO);
        dehydratorVO.setInUse(instrument.getInUse());
        dehydratorVO.setDisabled(instrument.getDisabled());
        dehydratorVO.setDescription(instrument.getDescription());
        dehydratorVO.setName(instrument.getName());
        dehydratorVO.setSn(instrument.getSn());
        return dehydratorVO;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public synchronized void addDehydrator(DehydratorVO dehydratorVO) {
        Instrument instrument = new Instrument();
        instrument.setName(dehydratorVO.getName());
        instrument.setSn(dehydratorVO.getSn());
        instrument.setType(InstrumentType.Dehydrator.getCode());
        instrument.setInUse(false);
        instrument.setDisabled(dehydratorVO.getDisabled());
        instrument.setDescription(dehydratorVO.getDescription());
        instrument.setCreateBy(UserContext.getSystemUserID());
        instrument.setUpdateBy(UserContext.getSystemUserID());
        if (instrument.getDisabled()) {
            instrument.setStatus(InstrumentStatus.Disabled.toCode());
        }else {
            instrument.setStatus(InstrumentStatus.Normal.toCode());
        }
        instrumentRepository.insert(instrument);

        long instrumentId = instrumentRepository.last();

        Dehydrator dehydrator = new Dehydrator();
        dehydrator.setInstrumentId(instrumentId);
        dehydrator.setCapacity(dehydratorVO.getCapacity());
        dehydrator.setUsedBlock(0);
        dehydrator.setStatus(DehydratorStatus.Normal.getCode());
        dehydrator.setCreateBy(UserContext.getSystemUserID());
        dehydrator.setUpdateBy(UserContext.getSystemUserID());
        dehydratorRepository.insert(dehydrator);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public synchronized int updateDehydratorInfo(DehydratorVO dehydratorVO) {
        Instrument instrument = getInstrument(dehydratorVO);
        if (instrument == null) {
            throw new BuzException(BuzExceptionCode.DehydratorNotExist);
        }
        Long instrumentId = instrument.getId();

        Dehydrator dehydrator = dehydratorRepository.selectByInstrumentId(instrumentId);
        if (dehydrator == null) {
            throw new BuzException(BuzExceptionCode.DehydratorNotExist);
        }

        Date dbNow = commonRepository.getDBNow();
        String name = dehydratorVO.getName();
        if (name != null) {
            instrument.setName(name);
        }
        String sn = dehydratorVO.getSn();
        if (sn != null) {
            instrument.setSn(sn);
        }
        Boolean disabled = dehydratorVO.getDisabled();
        if (disabled != null) {
            instrument.setDisabled(disabled);
        }
        if (instrument.getDisabled()) {
            instrument.setStatus(InstrumentStatus.Disabled.toCode());
            dehydrator.setStatus(InstrumentStatus.Disabled.toCode());
        }else {
            instrument.setStatus(InstrumentStatus.Normal.toCode());
            if(dehydrator.getStatus().equals(InstrumentStatus.Disabled.toCode())){
                dehydrator.setStatus(InstrumentStatus.Normal.toCode());
            }else {
                dehydrator.setStatus(dehydratorVO.getStatus());
            }
        }

        String description = dehydratorVO.getDescription();
        if (description != null) {
            instrument.setDescription(description);
        }
        instrument.setUpdateBy(UserContext.getSystemUserID());
        instrument.setUpdateTime(dbNow);
        instrumentRepository.updateByPrimaryKey(instrument);

        Integer capacity = dehydratorVO.getCapacity();
        if (capacity != null) {
            dehydrator.setCapacity(capacity);
        }
        Integer usedBlock = dehydratorVO.getUsedBlock();
        if (usedBlock != null) {
            dehydrator.setUsedBlock(usedBlock);
        }
        dehydrator.setUpdateBy(UserContext.getSystemUserID());
        dehydrator.setUpdateTime(dbNow);
        return dehydratorRepository.updateByPrimaryKey(dehydrator);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public synchronized void setInUse(long instrumentId, boolean inUse) {
        Instrument instrument = instrumentRepository.selectByPrimaryKey(instrumentId);
        if (instrument == null) {
            throw new BuzException(BuzExceptionCode.DehydratorNotExist);
        }
        Date dbNow = commonRepository.getDBNow();
        instrument.setInUse(inUse);
        instrument.setUpdateBy(UserContext.getSystemUserID());
        instrument.setUpdateTime(dbNow);
        instrumentRepository.updateByPrimaryKey(instrument);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public synchronized int deleteDehydrator(long instrumentId) {
        Instrument instrument = instrumentRepository.selectByPrimaryKey(instrumentId);
        if (instrument == null) {
            throw new BuzException(BuzExceptionCode.DehydratorNotExist);
        }

        if (instrument.getInUse()) {
            throw new BuzException(BuzExceptionCode.DehydratorInUse);
        }

        int instrumentDel = instrumentRepository.deleteByPrimaryKey(instrumentId);
        int dehydratorDel = dehydratorRepository.deleteByInstrumentId(instrumentId);

        if (instrumentDel != dehydratorDel) {
            throw new BuzException(BuzExceptionCode.DataError);
        }

        return dehydratorDel;
    }

    @Override
    public List<DehydratorStatusDto> getAllDehydratorStatus() {
        return dehydratorRepository.selectAllStatus();
    }

    private Instrument getInstrument(DehydratorVO dehydratorVO) {
        Instrument instrument = null;

        //select by id or sn
        Long instrumentId = dehydratorVO.getInstrumentId();
        String sn = dehydratorVO.getSn();
        if (instrumentId != null) {
            instrument = instrumentRepository.selectByPrimaryKey(instrumentId);
        } else if (sn != null) {
            instrument = instrumentRepository.selectAllBySerialNumber(sn);
        }

        return instrument;
    }

    @Override
    public List<DehydratorDto> getDehydratorByCondition(DehydratorCondition con) {
        List<DehydratorDto> dehydratorDtos = dehydratorRepository.selectInfoByCondition(con);
        return dehydratorDtos;
    }

    @Override
    public Long countDehydratorByCondition(DehydratorCondition con) {
        return dehydratorRepository.countInfoByCondition(con);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<DehydratorErrorMsgDto> getErrMsgByCondition(DehydratorErrorMsgCondition condition) {
        Dehydrator dehydrator = dehydratorRepository.selectByInstrumentId(condition.getInstrumentId());
        if (dehydrator == null) {
            throw new BuzException(BuzExceptionCode.DehydratorNotExist);
        }
        List<DehydratorErrorMsg> dehydratorErrorMsgs = instrumentEventRepository.selectMsgByCondition(condition);
        List<DehydratorErrorMsgDto> dehydratorErrorMsgDtos = new ArrayList<>();
        DehydratorErrorMsgDto dehydratorErrorMsgDto;
        for (DehydratorErrorMsg dehydratorErrorMsg : dehydratorErrorMsgs) {
            dehydratorErrorMsgDto = new DehydratorErrorMsgDto();
            if (dehydratorErrorMsg != null) {
                BeanUtils.copyProperties(dehydratorErrorMsg, dehydratorErrorMsgDto);
            }
            dehydratorErrorMsgDtos.add(dehydratorErrorMsgDto);
        }
        return dehydratorErrorMsgDtos;
    }

    @Override
    public Long countErrMsgByCondition(DehydratorErrorMsgCondition condition) {
        return instrumentEventRepository.countMsgByCondition(condition);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public synchronized void clearWarning(long instrumentId) {
        Dehydrator dehydrator = dehydratorRepository.selectByInstrumentId(instrumentId);
        if (dehydrator == null) {
            throw new BuzException(BuzExceptionCode.DehydratorNotExist);
        }
        Date dbNow = commonRepository.getDBNow();
        long userId = UserContext.getSystemUserID();
        dehydrator.setResetTime(dbNow);
        dehydrator.setUpdateBy(userId);
        dehydratorRepository.updateByPrimaryKey(dehydrator);

        InstrumentEvent instrumentEvent = new InstrumentEvent();
        instrumentEvent.setInstrumentId(instrumentId);
        instrumentEvent.setStatus(false);
        instrumentEvent.setUpdateBy(userId);
        instrumentEventRepository.updateByInstrumentId(instrumentEvent);
    }

    @Override
    public DehydratorDto getLastDehydrator() {
        Long id = dehydratorRepository.last();
        DehydratorDto lastDehydrator = dehydratorRepository.getLastDehydrator(id);
        return lastDehydrator;
    }

    @Override
    public List<BlockInfoDto> getBlocksInfoByCondition(GetBlockCondition condition) {
        List<BlockInfo> blockInfos = dehydratorRepository.selectBlockInfoByCondition(condition);
        List<BlockInfoDto> blockInfoDtos = new ArrayList<>();
        BlockInfoDto blockInfoDto;
        for (BlockInfo blockInfo : blockInfos) {
            blockInfoDto = new BlockInfoDto();
            BeanUtils.copyProperties(blockInfo, blockInfoDto);
            blockInfoDto.setBiaoshiDesc(paramSettingApplication.getNameByKeyAndCode(ParamKey.BlockBiaoshi.toString(), blockInfoDto.getBiaoshi()));
            blockInfoDto.setStatusDesc(PathologyStatus.valueOf(blockInfoDto.getStatus()).toString());
            blockInfoDto.setUnitDesc(paramSettingApplication.getNameByKeyAndCode(ParamKey.BlockUnit.toString(), blockInfoDto.getUnit()));
            blockInfoDtos.add(blockInfoDto);
        }
        return blockInfoDtos;
    }
}

