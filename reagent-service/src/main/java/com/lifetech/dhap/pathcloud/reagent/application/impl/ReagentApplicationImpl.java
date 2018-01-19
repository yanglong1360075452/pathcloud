package com.lifetech.dhap.pathcloud.reagent.application.impl;

import com.lifetech.dhap.pathcloud.common.exception.BuzException;
import com.lifetech.dhap.pathcloud.reagent.domain.ReagentMapper;
import com.lifetech.dhap.pathcloud.reagent.domain.ReagentRecordMapper;
import com.lifetech.dhap.pathcloud.reagent.domain.model.Reagent;
import com.lifetech.dhap.pathcloud.reagent.domain.model.ReagentRecord;
import com.lifetech.dhap.pathcloud.user.application.UserApplication;
import com.lifetech.dnap.pathcloud.reagent.application.ReagentApplication;
import com.lifetech.dnap.pathcloud.reagent.application.condition.ReagentCondition;
import com.lifetech.dnap.pathcloud.reagent.application.condition.StoreCondition;
import com.lifetech.dnap.pathcloud.reagent.application.dto.ReagentDto;
import com.lifetech.dnap.pathcloud.reagent.application.dto.ReagentRecordDto;
import com.lifetech.dnap.pathcloud.reagent.application.infrastructure.code.PreProcess;
import com.lifetech.dnap.pathcloud.reagent.application.infrastructure.code.ReagentCategory;
import com.lifetech.dnap.pathcloud.reagent.application.infrastructure.code.ReagentType;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HP on 2017/9/26.
 */
@Service("reagentApplication")
public class ReagentApplicationImpl implements ReagentApplication {

    @Autowired
    private ReagentMapper reagentMapper;

    @Autowired
    private ReagentRecordMapper reagentRecordMapper;

    @Autowired
    private UserApplication userApplication;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createReagent(ReagentDto reagentDto) throws BuzException {
        Reagent reagent = new Reagent();
        BeanUtils.copyProperties(reagentDto, reagent);
        reagentMapper.insert(reagent);
        reagentDto.setId(reagentMapper.last());
    }

    @Override
    public void updateReagent(ReagentDto reagentDto) throws BuzException {
        Reagent reagent = new Reagent();
        BeanUtils.copyProperties(reagentDto, reagent);
        reagentMapper.updateByPrimaryKey(reagent);
    }

    @Override
    public List<ReagentDto> getReagentList(ReagentCondition con) {
        List<Reagent> reagents = reagentMapper.selectByCondition(con);
        List<ReagentDto> reagentDtoList = null;
        if (CollectionUtils.isNotEmpty(reagents)) {
            reagentDtoList = new ArrayList<>();
            for (Reagent reagent : reagents) {
                reagentDtoList.add(reagentToDto(reagent));
            }
        }
        return reagentDtoList;
    }

    @Override
    public ReagentDto getReagentByTypeAndName(Integer type,String name) {
        return reagentToDto(reagentMapper.selectByTypeAndName(type,name));
    }

    @Override
    public ReagentDto getReagentById(long id) {
        return reagentToDto(reagentMapper.selectByPrimaryKey(id));
    }

    private ReagentDto reagentToDto(Reagent reagent) {
        ReagentDto reagentDto = null;
        if (reagent != null) {
            reagentDto = new ReagentDto();
            BeanUtils.copyProperties(reagent, reagentDto);
            reagentDto.setTypeDesc(ReagentType.getNameByCode(reagent.getType()));
            reagentDto.setCategoryDesc(ReagentCategory.getNameByCode(reagent.getCategory()));
            reagentDto.setPreProcessDesc(PreProcess.getNameByCode(reagent.getPreProcess()));
        }
        return reagentDto;
    }

    @Override
    public Long countReagentByCondition(ReagentCondition con) {
        return reagentMapper.countByCondition(con);
    }

    @Override
    public void addReagentRecord(ReagentRecordDto reagentRecordDto) {
        if (reagentRecordDto != null) {
            reagentRecordMapper.insert(reagentRecordDtoToPo(reagentRecordDto));
        }
    }

    @Override
    public List<ReagentRecordDto> getRecordByCondition(StoreCondition condition) {
        List<ReagentRecord> reagentRecords = reagentRecordMapper.selectByCondition(condition);
        List<ReagentRecordDto> reagentRecordDtoList = null;
        if(CollectionUtils.isNotEmpty(reagentRecords)){
            reagentRecordDtoList = new ArrayList<>();
            for(ReagentRecord reagentRecord : reagentRecords){
                reagentRecordDtoList.add(reagentRecordToDto(reagentRecord));
            }
        }
        return reagentRecordDtoList;
    }

    @Override
    public Long countRecordByCondition(StoreCondition condition) {
        return reagentRecordMapper.countByCondition(condition);
    }

    private ReagentRecord reagentRecordDtoToPo(ReagentRecordDto reagentRecordDto) {
        ReagentRecord reagentRecord = null;
        if (reagentRecordDto != null) {
            reagentRecord = new ReagentRecord();
            BeanUtils.copyProperties(reagentRecordDto, reagentRecord);
        }
        return reagentRecord;
    }

    private ReagentRecordDto reagentRecordToDto(ReagentRecord reagentRecord) {
        ReagentRecordDto reagentRecordDto = null;
        if (reagentRecord != null) {
            reagentRecordDto = new ReagentRecordDto();
            BeanUtils.copyProperties(reagentRecord,reagentRecordDto);
            reagentRecordDto.setUpdateOperator(userApplication.getUserSimpleInfoById(reagentRecord.getUpdateBy()).getFirstName());
        }
        return reagentRecordDto;
    }
}
