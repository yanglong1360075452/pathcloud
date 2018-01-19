package com.lifetech.dhap.pathcloud.reagent.api.impl;

import com.lifetech.dhap.pathcloud.common.exception.BuzException;
import com.lifetech.dhap.pathcloud.common.exception.BuzExceptionCode;
import com.lifetech.dhap.pathcloud.common.response.PageDataVO;
import com.lifetech.dhap.pathcloud.common.response.ResponseVO;
import com.lifetech.dhap.pathcloud.common.utils.DateUtil;
import com.lifetech.dhap.pathcloud.common.utils.StringUtil;
import com.lifetech.dhap.pathcloud.reagent.api.StoreApi;
import com.lifetech.dhap.pathcloud.reagent.api.vo.AdjustVO;
import com.lifetech.dhap.pathcloud.reagent.api.vo.StoreVO;
import com.lifetech.dhap.pathcloud.security.UserContext;
import com.lifetech.dnap.pathcloud.reagent.application.ReagentApplication;
import com.lifetech.dnap.pathcloud.reagent.application.StoreApplication;
import com.lifetech.dnap.pathcloud.reagent.application.condition.StoreCondition;
import com.lifetech.dnap.pathcloud.reagent.application.dto.ReagentDto;
import com.lifetech.dnap.pathcloud.reagent.application.dto.StoreDto;
import com.lifetech.dnap.pathcloud.reagent.application.infrastructure.code.ReagentCategory;
import com.lifetech.dnap.pathcloud.reagent.application.infrastructure.code.ReagentLiquidType;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * @author LiuMei
 * @date 2017-11-30.
 */
@Component("storeApi")
public class StoreApiImpl implements StoreApi {

    @Autowired
    private StoreApplication storeApplication;

    @Autowired
    private ReagentApplication reagentApplication;

    @Override
    public ResponseVO inStore(StoreVO storeVO) throws BuzException {
        Long reagentId = storeVO.getReagentId();
        String batchNumber = storeVO.getBatchNumber();
        String orderNumber = storeVO.getOrderNumber();
        Double specification = storeVO.getSpecification();
        Integer count = storeVO.getCount();
        Integer countUnit = storeVO.getCountUnit();
        Date expiryTime = storeVO.getExpiryTime();
        Date produceTime = storeVO.getProduceTime();
        if (reagentId == null || StringUtils.isBlank(batchNumber) || StringUtils.isBlank(orderNumber) || specification == null ||
                count == null || countUnit == null || expiryTime == null || produceTime == null) {
            throw new BuzException(BuzExceptionCode.ErrorParam);
        }
        ReagentDto reagentDto = reagentApplication.getReagentById(reagentId);
        if (reagentDto == null) {
            throw new BuzException(BuzExceptionCode.RecordNotExists);
        }
        Integer category = reagentDto.getCategory();
        Double realCapacity = storeVO.getRealCapacity();
        //试剂入库 试剂类型必填
        if (ReagentCategory.Reagent.equals(category)) {
            Integer type = storeVO.getType();
            if (type == null) {
                throw new BuzException(BuzExceptionCode.ErrorParam);
            }
            if (ReagentLiquidType.ConcentratedLiquid.toCode().equals(type)) {
                //浓缩液 稀释比例/真正计算容量必填
                Double dilutionRateFront = storeVO.getDilutionRateFront();
                Double dilutionRateRear = storeVO.getDilutionRateRear();
                if (dilutionRateFront == null || dilutionRateRear == null || realCapacity == null) {
                    throw new BuzException(BuzExceptionCode.ErrorParam);
                }
            }
        }
        StoreDto storeDto = storeApplication.getByUnique(reagentId, batchNumber, orderNumber);
        if (storeDto != null) {
            throw new BuzException(BuzExceptionCode.RecordExists);
        }
        storeDto = new StoreDto();
        BeanUtils.copyProperties(storeVO, storeDto);
        //计算总量
        if (realCapacity == null) {
            storeDto.setTotalCapacity(specification * count);
        } else {
            storeDto.setTotalCapacity(realCapacity * count);
        }
        storeDto.setUsedCapacity(0D);
        storeDto.setCreateBy(UserContext.getLoginUserID());
        storeApplication.inStore(storeDto);
        return new ResponseVO();
    }

    @Override
    public ResponseVO updateStore(StoreVO storeVO) throws BuzException {
        Long id = storeVO.getId();
        if (id == null) {
            throw new BuzException(BuzExceptionCode.ErrorParam);
        }
        Long reagentId = storeVO.getReagentId();
        String batchNumber = storeVO.getBatchNumber();
        String orderNumber = storeVO.getOrderNumber();
        Double specification = storeVO.getSpecification();
        Integer count = storeVO.getCount();
        Integer countUnit = storeVO.getCountUnit();
        Date expiryTime = storeVO.getExpiryTime();
        Date produceTime = storeVO.getProduceTime();
        if (reagentId == null || StringUtils.isBlank(batchNumber) || StringUtils.isBlank(orderNumber) || specification == null ||
                count == null || countUnit == null || expiryTime == null || produceTime == null) {
            throw new BuzException(BuzExceptionCode.ErrorParam);
        }
        StoreDto storeDto = storeApplication.getById(id);
        if (storeDto == null) {
            throw new BuzException(BuzExceptionCode.RecordNotExists);
        }
        if (storeDto.getUsedCapacity() > 0) {
            throw new BuzException(BuzExceptionCode.CanNotOperate);
        }
        ReagentDto reagentDto = reagentApplication.getReagentById(reagentId);
        if (reagentDto == null) {
            throw new BuzException(BuzExceptionCode.RecordNotExists);
        }
        Integer category = reagentDto.getCategory();
        Double realCapacity = storeVO.getRealCapacity();
        //试剂入库 试剂类型必填
        if (ReagentCategory.Reagent.equals(category)) {
            Integer type = storeVO.getType();
            if (type == null) {
                throw new BuzException(BuzExceptionCode.ErrorParam);
            }
            if (ReagentLiquidType.ConcentratedLiquid.toCode().equals(type)) {
                //浓缩液 稀释比例/真正计算容量必填
                Double dilutionRateRear = storeVO.getDilutionRateRear();
                Double dilutionRateFront = storeVO.getDilutionRateFront();
                if (dilutionRateFront == null || dilutionRateRear == null || realCapacity == null) {
                    throw new BuzException(BuzExceptionCode.ErrorParam);
                }
            }
        }
        BeanUtils.copyProperties(storeVO, storeDto);
        //计算总量
        if (realCapacity == null) {
            storeDto.setTotalCapacity(specification * count);
        } else {
            storeDto.setTotalCapacity(realCapacity * count);
        }
        storeApplication.updateStore(storeDto);
        return new ResponseVO();
    }

    @Override
    public ResponseVO getStoreList(Integer page, Integer length, String filter, Integer status,
                                   Integer type, Integer category, Integer year, Long startTime, Long endTime) throws BuzException {
        if (page < 1 || length < 0) {
            throw new BuzException(BuzExceptionCode.ErrorParam);
        }
        StoreCondition con = new StoreCondition();
        if (year != null) {
            con.setStartTime(DateUtil.dateFormat(year + "-" + 01 + "-0" + 1 + " 00:00:00"));
            con.setEndTime(DateUtil.dateFormat(year + "-" + 12 + "-" + 31 + " 23:59:59"));
        }
        if (startTime != null) {
            con.setStartTime(new Date(startTime));
        }
        if (endTime != null) {
            con.setEndTime(new Date(endTime));
        }
        con.setSize(length);
        con.setStart((page - 1) * length);
        if (filter != null) {
            con.setFilter(StringUtil.escapeExprSpecialWord(filter));
        }
        con.setType(type);
        con.setStatus(status);
        con.setCategory(category);
        List<StoreDto> storeDtoList = storeApplication.getList(con);
        Long total = storeApplication.countByCondition(con);
        return new PageDataVO(page, length, total, storeDtoList);
    }

    @Override
    public ResponseVO getStoreRecord(long storeId, Integer page, Integer length) throws BuzException {
        StoreDto storeDto = storeApplication.getById(storeId);
        if (storeDto == null) {
            throw new BuzException(BuzExceptionCode.RecordNotExists);
        }
        StoreCondition con = new StoreCondition();
        con.setStoreId(storeId);
        con.setSize(length);
        con.setStart((page - 1) * length);
        return new PageDataVO(page, length, reagentApplication.countRecordByCondition(con), reagentApplication.getRecordByCondition(con));
    }

    @Override
    public ResponseVO adjust(AdjustVO adjustVO) throws BuzException {
        Long id = adjustVO.getId();
        Double adjust = adjustVO.getAdjust();
        String note = adjustVO.getNote();
        if (id == null || adjust == null) {
            throw new BuzException(BuzExceptionCode.ErrorParam);
        }
        StoreDto storeDto = storeApplication.getById(id);
        Double usedCapacity = storeDto.getUsedCapacity();
        Double totalCapacity = storeDto.getTotalCapacity();
        Double realUsed = usedCapacity + adjust;
        if (realUsed > totalCapacity) {
            throw new BuzException(BuzExceptionCode.ErrorParam);
        }
        storeDto.setUsedCapacity(realUsed);
        storeDto.setAdjust(adjust);
        storeDto.setNote(note);
        storeApplication.adjust(storeDto);
        return new ResponseVO();
    }
}
