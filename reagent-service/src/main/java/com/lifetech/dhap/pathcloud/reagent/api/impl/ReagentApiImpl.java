package com.lifetech.dhap.pathcloud.reagent.api.impl;

import com.lifetech.dhap.pathcloud.common.exception.BuzException;
import com.lifetech.dhap.pathcloud.common.exception.BuzExceptionCode;
import com.lifetech.dhap.pathcloud.common.response.PageDataVO;
import com.lifetech.dhap.pathcloud.common.response.ResponseVO;
import com.lifetech.dhap.pathcloud.common.utils.StringUtil;
import com.lifetech.dhap.pathcloud.reagent.api.ReagentApi;
import com.lifetech.dhap.pathcloud.reagent.api.vo.ReagentVO;
import com.lifetech.dhap.pathcloud.security.UserContext;
import com.lifetech.dnap.pathcloud.reagent.application.ReagentApplication;
import com.lifetech.dnap.pathcloud.reagent.application.condition.ReagentCondition;
import com.lifetech.dnap.pathcloud.reagent.application.dto.ReagentDto;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by HP on 2017/9/26.
 */

@Component("reagentApi")
public class ReagentApiImpl implements ReagentApi {

    @Autowired
    private ReagentApplication reagentApplication;

    @Override
    public ResponseVO createReagent(ReagentVO reagentVO) throws BuzException {
        String name = reagentVO.getName();
        Integer category = reagentVO.getCategory();
        Integer type = reagentVO.getType();
        if (StringUtils.isBlank(name) || category == null || type == null) {
            throw new BuzException(BuzExceptionCode.ErrorParam);
        }
        ReagentDto reagentDto = reagentApplication.getReagentByTypeAndName(null,name);
        if(reagentDto != null){
            throw new BuzException(BuzExceptionCode.RecordExists);
        }
        reagentDto = new ReagentDto();
        BeanUtils.copyProperties(reagentVO, reagentDto);
        reagentDto.setCreateBy(UserContext.getLoginUserID());
        reagentApplication.createReagent(reagentDto);
        return new ResponseVO(reagentDto.getId());
    }

    @Override
    public ResponseVO updateReagent(ReagentVO reagentVO) throws BuzException {
        Long id = reagentVO.getId();
        if(id == null){
            throw new BuzException(BuzExceptionCode.ErrorParam);
        }
        String name = reagentVO.getName();
        Integer category = reagentVO.getCategory();
        Integer type = reagentVO.getType();
        if (StringUtils.isBlank(name) || category == null || type == null) {
            throw new BuzException(BuzExceptionCode.ErrorParam);
        }
        ReagentDto reagentDto = new ReagentDto();
        BeanUtils.copyProperties(reagentVO, reagentDto);
        reagentDto.setUpdateBy(UserContext.getLoginUserID());
        reagentApplication.updateReagent(reagentDto);
        return new ResponseVO();
    }

    @Override
    public ResponseVO getReagentByTypeAndName(String name,Integer type) throws BuzException {
        if(StringUtils.isBlank(name)){
            throw new BuzException(BuzExceptionCode.ErrorParam);
        }
        return new ResponseVO(reagentApplication.getReagentByTypeAndName(type,name));
    }

    @Override
    public ResponseVO getReagentList(Integer page, Integer length, String filter,Integer category) throws BuzException {
        if (page < 1 || length < 0) {
            throw new BuzException(BuzExceptionCode.ErrorParam);
        }
        ReagentCondition condition = new ReagentCondition();
        if (filter != null) {
            condition.setFilter(StringUtil.escapeExprSpecialWord(filter));
        }
        condition.setSize(length);
        condition.setStart((page - 1) * length);
        condition.setCategory(category);
        List<ReagentDto> reagentDtoList = reagentApplication.getReagentList(condition);
        Long total = reagentApplication.countReagentByCondition(condition);
        return new PageDataVO(page, length, total, reagentDtoList);
    }
}
