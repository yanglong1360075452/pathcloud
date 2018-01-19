package com.lifetech.dhap.pathcloud.dehydrate.api.impl;

import com.lifetech.dhap.pathcloud.common.domain.CommonRepository;
import com.lifetech.dhap.pathcloud.common.exception.BuzException;
import com.lifetech.dhap.pathcloud.common.exception.BuzExceptionCode;
import com.lifetech.dhap.pathcloud.common.response.PageDataVO;
import com.lifetech.dhap.pathcloud.common.response.ResponseVO;
import com.lifetech.dhap.pathcloud.dehydrate.api.InstrumentApi;
import com.lifetech.dhap.pathcloud.dehydrate.api.vo.InstrumentVO;
import com.lifetech.dhap.pathcloud.dehydrate.application.InstrumentApplication;
import com.lifetech.dhap.pathcloud.dehydrate.application.condition.InstrumentCondition;
import com.lifetech.dhap.pathcloud.dehydrate.application.dto.InstrumentDto;
import com.lifetech.dhap.pathcloud.localdaemon.device.constants.InstrumentStatus;
import com.lifetech.dhap.pathcloud.security.UserContext;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by LiuMei on 2017-08-31.
 */
@Service("instrumentApi")
public class InstrumentApiImpl implements InstrumentApi {

    @Autowired
    private InstrumentApplication instrumentApplication;

    @Autowired
    private CommonRepository commonRepository;

    @Override
    public ResponseVO addInstrument(InstrumentVO instrumentVO) throws BuzException {
        if (instrumentVO == null) {
            throw new BuzException(BuzExceptionCode.ErrorParam);
        }
        Integer type = instrumentVO.getType();
        String name = instrumentVO.getName();
        String sn = instrumentVO.getSn();
        Integer status = instrumentVO.getStatus();
        if (StringUtils.isBlank(name) || StringUtils.isBlank(sn) || type == null || status == null) {
            throw new BuzException(BuzExceptionCode.ErrorParam);
        }
        InstrumentDto db = instrumentApplication.getInstrumentByNameAndType(name, type);
        if(db != null){
            throw new BuzException(BuzExceptionCode.RecordExists);
        }
        InstrumentDto instrumentDto = new InstrumentDto();
        BeanUtils.copyProperties(instrumentVO, instrumentDto);
        if (status.equals(InstrumentStatus.Disabled.toCode())) {
            instrumentDto.setDisabled(true);
        } else {
            instrumentDto.setDisabled(false);
        }
        instrumentDto.setCreateBy(UserContext.getLoginUserID());
        instrumentDto.setCreateTime(commonRepository.getDBNow());
        instrumentApplication.addInstrument(instrumentDto);
        return new ResponseVO();
    }

    @Override
    public ResponseVO editInstrument(InstrumentVO instrumentVO) throws BuzException {
        Long id = instrumentVO.getId();
        if (id == null) {
            throw new BuzException(BuzExceptionCode.ErrorParam);
        }
        InstrumentDto instrumentDto = instrumentApplication.getInstrumentById(id);
        if (instrumentDto == null) {
            throw new BuzException(BuzExceptionCode.DehydratorNotExist);
        }
        InstrumentDto db = instrumentApplication.getInstrumentByNameAndType(instrumentVO.getName(), instrumentVO.getType());
        if(db != null && !db.getId().equals(id)){
            throw new BuzException(BuzExceptionCode.RecordExists);
        }
        Integer status = instrumentVO.getStatus();
        if (status.equals(InstrumentStatus.Disabled.toCode())) {
            instrumentDto.setDisabled(true);
        } else {
            instrumentDto.setDisabled(false);
        }
        instrumentDto.setStatus(instrumentVO.getStatus());
        instrumentDto.setName(instrumentVO.getName());
        instrumentDto.setDescription(instrumentVO.getDescription());
        instrumentDto.setSn(instrumentVO.getSn());
        instrumentApplication.updateInstrument(instrumentDto);
        return new ResponseVO();
    }

    @Override
    public ResponseVO delete(long id) throws BuzException {
        InstrumentDto instrumentDto = instrumentApplication.getInstrumentById(id);
        if (instrumentDto != null) {
            instrumentApplication.deleteInstrument(id);
        }
        return new ResponseVO();
    }

    @Override
    public ResponseVO getList(Integer page, Integer length, Integer status, Integer type) throws BuzException {
        if (page < 0 || length < 1) {
            throw new BuzException(BuzExceptionCode.ErrorParam);
        }
        InstrumentCondition con = new InstrumentCondition();
        con.setSize(length);
        con.setStart((page - 1) * length);
        con.setStatus(status);
        con.setType(type);
        List<InstrumentDto> data = instrumentApplication.getInstrumentsByCondition(con);
        Long total = instrumentApplication.countInstrumentsByCondition(con);
        return new PageDataVO(page, length, total, data);
    }

}
