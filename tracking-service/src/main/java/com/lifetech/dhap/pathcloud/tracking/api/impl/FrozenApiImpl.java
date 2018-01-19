package com.lifetech.dhap.pathcloud.tracking.api.impl;

import com.lifetech.dhap.pathcloud.common.data.PathologyStatus;
import com.lifetech.dhap.pathcloud.common.data.SpecialApplyType;
import com.lifetech.dhap.pathcloud.common.exception.BuzException;
import com.lifetech.dhap.pathcloud.common.exception.BuzExceptionCode;
import com.lifetech.dhap.pathcloud.common.response.PageDataVO;
import com.lifetech.dhap.pathcloud.common.response.ResponseVO;
import com.lifetech.dhap.pathcloud.common.utils.CommonUtil;
import com.lifetech.dhap.pathcloud.common.utils.StringUtil;
import com.lifetech.dhap.pathcloud.security.UserContext;
import com.lifetech.dhap.pathcloud.setting.application.ParamSettingApplication;
import com.lifetech.dhap.pathcloud.setting.application.infrastructure.code.SystemKey;
import com.lifetech.dhap.pathcloud.tracking.api.FrozenApi;
import com.lifetech.dhap.pathcloud.tracking.api.vo.BlockVO;
import com.lifetech.dhap.pathcloud.tracking.api.vo.GrossingSaveVO;
import com.lifetech.dhap.pathcloud.tracking.application.BlockApplication;
import com.lifetech.dhap.pathcloud.tracking.application.GrossingApplication;
import com.lifetech.dhap.pathcloud.tracking.application.SpecialApplicationApplication;
import com.lifetech.dhap.pathcloud.tracking.application.condition.GrossingCon;
import com.lifetech.dhap.pathcloud.tracking.application.condition.SpecialApplicationCondition;
import com.lifetech.dhap.pathcloud.tracking.application.dto.BlockDetailDto;
import com.lifetech.dhap.pathcloud.tracking.application.dto.BlockDto;
import com.lifetech.dhap.pathcloud.tracking.application.dto.GrossingSaveDto;
import com.lifetech.dhap.pathcloud.tracking.application.dto.SpecialApplicationDto;
import com.lifetech.dhap.pathcloud.tracking.application.infrastructure.code.TrackingOperation;
import com.lifetech.dhap.pathcloud.tracking.infrastructure.code.GrossingSortByEnum;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.DefaultValue;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by LiuMei on 2017-09-06.
 */
@Component("frozenApi")
public class FrozenApiImpl implements FrozenApi {

    @Autowired
    private BlockApplication blockApplication;

    @Autowired
    private GrossingApplication grossingApplication;

    @Autowired
    private ParamSettingApplication paramSettingApplication;

    @Autowired
    private SpecialApplicationApplication specialApplicationApplication;

    @Override
    public ResponseVO addFrozenGrossing(Long id, GrossingSaveVO vo) throws BuzException {
        checkSetting();
        if (id <= 0 || vo == null) {
            throw new BuzException(BuzExceptionCode.ErrorParam);
        }
        List<BlockVO> blocks = vo.getBlocks();
        String frozenNumber = vo.getNumber();
        if(StringUtils.isBlank(frozenNumber)){
            throw new BuzException(BuzExceptionCode.ErrorParam);
        }
        vo.setId(id);
        GrossingSaveDto data = new GrossingSaveDto();
        data.setPathologyId(id);
        data.setJujianNote(vo.getJujianNote());
        data.setBingdongNote(vo.getBingdongNote());
        data.setUpdateBy(UserContext.getLoginUserID());
        Long operatorId = vo.getOperatorId();
        data.setOperatorId(operatorId);
        Long secOperatorId = vo.getSecOperatorId();
        data.setSecOperatorId(secOperatorId);
        data.setManualFlag(vo.getManualFlag());
        data.setNumber(frozenNumber);
        data.setDeleteBlocks(vo.getDeleteBlocks());
        if (CollectionUtils.isNotEmpty(blocks)) {
            data.setBlocks(GrossingApiImpl.blocksVOToDto(id, blocks));
        }
        Boolean print = vo.getPrint();
        if (print) {
            grossingApplication.frozenPrint(data);
        } else {
            if(operatorId == null || secOperatorId == null){
                throw new BuzException(BuzExceptionCode.ErrorParam);
            }
            grossingApplication.frozenGrossing(data);
        }
        return new ResponseVO();
    }

    @Override
    public ResponseVO getGrossingPathologies(Integer page, Integer length, String filter, Integer status, Boolean reGrossing, Integer departments,
                                             Long createTimeStart, Long createTimeEnd, Integer order, String sort) throws BuzException {
        SpecialApplicationCondition condition = new SpecialApplicationCondition();
        condition.setStatus(PathologyStatus.PrepareGrossing.toCode());
        List<Integer> types = new ArrayList<>();
        types.add(SpecialApplyType.Frozen.toCode());
        if (createTimeStart != null) {
            condition.setCreateTimeStart(new Date(createTimeStart));
        }
        if (createTimeEnd != null) {
            condition.setCreateTimeEnd(new Date(createTimeEnd));
        }
        if (!CommonUtil.admin()) {
            condition.setAssignGrossing(UserContext.getLoginUserID());
        }
        if (StringUtils.isNotBlank(filter)) {
            condition.setFilter(StringUtil.escapeExprSpecialWord(filter));
            condition.setAssignGrossing(null);
        }
        condition.setSize(length);
        condition.setStart((page - 1) * length);
        List<SpecialApplicationDto> specialApplicationDtoList = specialApplicationApplication.getListByCondition(condition);
        Long total = specialApplicationApplication.countByCondition(condition);
        return new PageDataVO(page, length, total, specialApplicationDtoList);
    }

    @Override
    public ResponseVO getSlidesInfo(String number) {
        if(StringUtils.isBlank(number)){
            throw new BuzException(BuzExceptionCode.ErrorParam);
        }
        List<BlockDto> blockDtoList = blockApplication.getBlockInfoByNumber(number);
        return new ResponseVO(blockDtoList);
    }

    @Override
    public ResponseVO getFrozenGrossing(@DefaultValue("1") Integer page, @DefaultValue("10") Integer length,
                                        Integer order, String sort, Integer status, String filter,
                                        Integer departments, Long operator, Long secOperator,
                                        Long timeStart, Long timeEnd) throws BuzException {
        checkSetting();
        if (page < 0 || length < 1) {
            throw new BuzException(BuzExceptionCode.ErrorParam);
        }
        GrossingCon con = new GrossingCon();

        if (order != null) {
            con.setOrder(GrossingSortByEnum.valueOf(order).toString());
            if (sort != null) {
                con.setOrder(con.getOrder() + " " + sort);
            }
        } else {
            con.setOrder(GrossingSortByEnum.valueOf(0).toString() + " desc");
        }
        con.setSize(length);
        con.setStart((page - 1) * length);
        if (filter != null && filter.contains("-") && filter.length() > 18) {
            String[] pathNos = filter.split("-");
            if (pathNos.length != 2) {
                throw new BuzException(BuzExceptionCode.ErrorParam);
            }
            con.setPathNoStart(pathNos[0].trim());
            con.setPathNoEnd(pathNos[1].trim());
        } else if (filter != null) {
            con.setFilter(StringUtil.escapeExprSpecialWord(filter.trim()));
        }

        con.setBlockStatus(status);

        if (timeStart != null) {
            con.setStartTime(new Date(timeStart));
        }
        if (timeEnd != null) {
            con.setEndTime(new Date(timeEnd));
        }
        con.setOperator(operator);
        con.setSecOperator(secOperator);
        con.setDepartments(departments);

        con.setOperation(TrackingOperation.frozenGrossing.toCode());
        List<BlockDetailDto> data = grossingApplication.getFrozenBlockByCondition(con);
        Long total = grossingApplication.countFrozenBlockByCondition(con);
        return new PageDataVO(page, length, total, data);

    }

    /**
     * 查询是否使用冰冻取材工作站
     *
     * @throws BuzException
     */
    public void checkSetting() throws BuzException {
        String usingFrozen = paramSettingApplication.getContentByKey(SystemKey.UsingFrozen.toString());
        if (usingFrozen.equals("0")) {
            throw new BuzException(BuzExceptionCode.UsingFrozen);
        }
    }

}
