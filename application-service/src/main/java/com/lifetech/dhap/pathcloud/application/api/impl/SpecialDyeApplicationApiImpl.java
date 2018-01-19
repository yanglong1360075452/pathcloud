package com.lifetech.dhap.pathcloud.application.api.impl;

import com.lifetech.dhap.pathcloud.application.api.SpecialDyeApplicationApi;
import com.lifetech.dhap.pathcloud.application.api.vo.BlockVO;
import com.lifetech.dhap.pathcloud.application.api.vo.IhcApplicationQueryVO;
import com.lifetech.dhap.pathcloud.application.api.vo.IhcApplicationVO;
import com.lifetech.dhap.pathcloud.application.api.vo.IhcBlockVO;
import com.lifetech.dhap.pathcloud.application.application.ApplicationIhcApplication;
import com.lifetech.dhap.pathcloud.application.application.condition.ApplicationIhcCondition;
import com.lifetech.dhap.pathcloud.application.application.dto.IhcApplicationDto;
import com.lifetech.dhap.pathcloud.application.application.dto.IhcApplicationQueryDto;
import com.lifetech.dhap.pathcloud.application.application.dto.IhcBlockDto;
import com.lifetech.dhap.pathcloud.application.application.infrastructure.code.IhcApplicationStatus;
import com.lifetech.dhap.pathcloud.common.data.SpecialDyeFix;
import com.lifetech.dhap.pathcloud.application.infrastructure.code.DyeApplySortEnum;
import com.lifetech.dhap.pathcloud.common.data.PathologyStatus;
import com.lifetech.dhap.pathcloud.common.exception.BuzException;
import com.lifetech.dhap.pathcloud.common.exception.BuzExceptionCode;
import com.lifetech.dhap.pathcloud.common.response.PageDataVO;
import com.lifetech.dhap.pathcloud.common.response.ResponseVO;
import com.lifetech.dhap.pathcloud.common.utils.StringUtil;
import com.lifetech.dhap.pathcloud.security.UserContext;
import com.lifetech.dhap.pathcloud.setting.application.ParamSettingApplication;
import com.lifetech.dhap.pathcloud.setting.application.dto.ParamSettingDto;
import com.lifetech.dhap.pathcloud.setting.application.infrastructure.code.SystemKey;
import com.lifetech.dhap.pathcloud.tracking.application.BlockApplication;
import com.lifetech.dhap.pathcloud.tracking.application.condition.ApplicationCondition;
import com.lifetech.dhap.pathcloud.tracking.application.dto.BlockDto;
import com.lifetech.dhap.pathcloud.tracking.application.dto.BlockExtendDto;
import net.sf.json.JSONArray;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.DefaultValue;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by LiuMei on 2017-04-01.
 */
@Component("specialDyeApi")
public class SpecialDyeApplicationApiImpl implements SpecialDyeApplicationApi {

    @Autowired
    private ApplicationIhcApplication applicationIhcApplication;

    @Autowired
    private BlockApplication blockApplication;

    @Autowired
    private ParamSettingApplication paramSettingApplication;

    @Override
    public ResponseVO create(IhcApplicationVO ihcApplicationVO) throws BuzException {
        String applyUser = ihcApplicationVO.getApplyUser();
        String applyTel = ihcApplicationVO.getApplyTel();
        List<IhcBlockVO> ihcBlocks = ihcApplicationVO.getIhcBlocks();
        if (StringUtils.isBlank(applyUser) || StringUtils.isBlank(applyTel) || CollectionUtils.isEmpty(ihcBlocks)) {
            throw new BuzException(BuzExceptionCode.ErrorParam);
        }
        IhcApplicationDto ihcApplicationDto = new IhcApplicationDto();
        BeanUtils.copyProperties(ihcApplicationVO, ihcApplicationDto);
        ihcApplicationDto.setIhcBlocks(IhcBlockVOToDTO(ihcBlocks));
        applicationIhcApplication.createApplication(ihcApplicationDto);
        return new ResponseVO();
    }

    private List<IhcBlockDto> IhcBlockVOToDTO(List<IhcBlockVO> blockVOS) throws BuzException{
        List<IhcBlockDto> ihcBlockDtoList = null;
        if(CollectionUtils.isNotEmpty(blockVOS)){
            ihcBlockDtoList = new ArrayList<>();
            IhcBlockDto ihcBlockDto;
            for (IhcBlockVO ihcBlockVO : blockVOS) {
                String pathNo = ihcBlockVO.getSerialNumber();
                String subId = ihcBlockVO.getSubId();
                Integer specialDye = ihcBlockVO.getSpecialDye();
                List<String> ihcMarker = ihcBlockVO.getIhcMarker();
                if (StringUtils.isBlank(pathNo) || StringUtils.isBlank(subId) || specialDye == null) {
                    throw new BuzException(BuzExceptionCode.ErrorParam);
                }
                //免疫组化或特染
                if(specialDye >= SpecialDyeFix.IHC.toCode()){
                    if(CollectionUtils.isEmpty(ihcMarker)){
                        throw new BuzException(BuzExceptionCode.ErrorParam);
                    }
                }

                BlockDto blockDto = blockApplication.getBlockBySerialNumber(pathNo + subId);
                if (blockDto == null || blockDto.getId() == null) {
                    throw new BuzException(BuzExceptionCode.RecordNotExists);
                }
                ihcBlockDto = new IhcBlockDto();
                BeanUtils.copyProperties(ihcBlockVO, ihcBlockDto);
                ihcBlockDto.setBlockId(blockDto.getId());
                ihcBlockDto.setPathId(blockDto.getPathId());
                ihcBlockDtoList.add(ihcBlockDto);
            }
        }
        return ihcBlockDtoList;
    }

    @Override
    public ResponseVO updateBlock(IhcApplicationVO ihcApplicationVO) throws BuzException {
        if(ihcApplicationVO == null){
            throw new BuzException(BuzExceptionCode.ErrorParam);
        }
        Long id = ihcApplicationVO.getId();
        if(id == null){
            throw new BuzException(BuzExceptionCode.ErrorParam);
        }
        List<IhcBlockVO> ihcBlocks = ihcApplicationVO.getIhcBlocks();
        if(CollectionUtils.isEmpty(ihcBlocks)){
            throw new BuzException(BuzExceptionCode.ErrorParam);
        }
        IhcApplicationDto ihcApplicationDto = new IhcApplicationDto();
        ihcApplicationDto.setId(id);
        ihcApplicationDto.setIhcBlocks(IhcBlockVOToDTO(ihcBlocks));
        ihcApplicationDto.setSource(ihcApplicationVO.getSource());
        applicationIhcApplication.updateApplication(ihcApplicationDto);
        return new ResponseVO();
    }

    @Override
    public ResponseVO cancel(Long ihcBlockId) throws BuzException {
        IhcBlockDto ihcBlockDto = applicationIhcApplication.getIhcBlockById(ihcBlockId);
        if (ihcBlockDto == null || ihcBlockDto.getId() == null) {
            throw new BuzException(BuzExceptionCode.RecordNotExists);
        }
        Integer status = ihcBlockDto.getStatus();
        if (!status.equals(IhcApplicationStatus.PrepareConfirm.toCode())) {
            throw new BuzException(BuzExceptionCode.StatusNotMatch);
        }
        ihcBlockDto.setStatus(IhcApplicationStatus.Cancel.toCode());
        applicationIhcApplication.cancelApplication(ihcBlockDto);
        return new ResponseVO();
    }

    @Override
    public ResponseVO getApplicationIHCs(@DefaultValue("1") Integer page, @DefaultValue("10") Integer length,
                                         String filter, Long createTimeStart, Long createTimeEnd, Integer dyeCategory, Integer order, String sort) throws BuzException {
        if (page < 0 || length < 1) {
            throw new BuzException(BuzExceptionCode.ErrorParam);
        }
        ApplicationIhcCondition con = new ApplicationIhcCondition();
        con.setStart((page - 1) * length);
        con.setSize(length);
        if (filter != null) {
            con.setFilter(StringUtil.escapeExprSpecialWord(filter.trim()));
        }
        if (createTimeStart != null) {
            con.setCreateTimeStart(new Date(createTimeStart));
        }
        if (createTimeEnd != null) {
            con.setCreateTimeEnd(new Date(createTimeEnd));
        }
        con.setDyeCategory(dyeCategory);
        if (sort == null) {
            sort = " ASC";
        }

        if (order != null) {
            if (order.equals(DyeApplySortEnum.Status.toCode())) {
                con.setOrder(DyeApplySortEnum.Status.toString() + DyeApplySortEnum.BlockStatus.toString() + " " + sort);
            } else {
                con.setOrder(DyeApplySortEnum.valueOf(order).toString() + " " + sort);
            }
        } else {
            con.setOrder(DyeApplySortEnum.Default.toString() + " " + sort);
        }

        List<IhcApplicationQueryDto> data = applicationIhcApplication.getApplicationIHCs(con);
        List<IhcApplicationQueryVO> lists = new ArrayList<>();
        IhcApplicationQueryVO ihcApplicationQueryVO;
        List<ParamSettingDto> specialDyes = paramSettingApplication.getContentToListByKey(SystemKey.SpecialDye.toString());
        for (IhcApplicationQueryDto dto : data) {
            ihcApplicationQueryVO = new IhcApplicationQueryVO();
            BeanUtils.copyProperties(dto, ihcApplicationQueryVO);
            if (dto.getStatus().equals(IhcApplicationStatus.Confirm.toCode())) {
                ihcApplicationQueryVO.setStatus(dto.getBlockStatus());
                ihcApplicationQueryVO.setStatusName(PathologyStatus.getNameByCode(dto.getBlockStatus()));

            } else {
                ihcApplicationQueryVO.setStatusName(IhcApplicationStatus.valueOf(dto.getStatus()).toString());
            }

            ihcApplicationQueryVO.setIhcMarkers(JSONArray.fromObject(dto.getIhcMarker()));

            for (ParamSettingDto dto2 : specialDyes) {
                if (dto.getDyeCategory().equals(dto2.getCode())) {
                    ihcApplicationQueryVO.setDyeCategoryName(dto2.getName());
                    break;
                }
            }
            lists.add(ihcApplicationQueryVO);
        }
        Long total = applicationIhcApplication.getApplicationIHCsTotal(con);


        return new PageDataVO(page, length, total, lists);
    }

    @Override
    public ResponseVO getBlocks(@DefaultValue("1") Integer page, @DefaultValue("10") Integer length, Long createTimeStart,
                                Long createTimeEnd, String pathNo) {
        if (page < 0 || length < 1) {
            throw new BuzException(BuzExceptionCode.ErrorParam);
        }
        ApplicationCondition con = new ApplicationCondition();
        con.setSize(length);
        con.setStart((page - 1) * length);
        con.setCreateBy(UserContext.getLoginUserID());
        con.setPathNo(pathNo);
        if (createTimeStart != null) {
            con.setCreateTimeStart(new Date(createTimeStart));
        }
        if (createTimeEnd != null) {
            con.setCreateTimeEnd(new Date(createTimeEnd));
        }
        con.setCreateBy(UserContext.getLoginUserID());
        List<BlockExtendDto> blockExtendDTOs = blockApplication.getNotSpecialBlocksByCondition(con);
        List<BlockVO> blockVOS = new ArrayList<>();
        BlockVO blockVO;
        if (CollectionUtils.isNotEmpty(blockExtendDTOs)) {
            for (BlockExtendDto blockExtendDto : blockExtendDTOs) {
                blockVO = new BlockVO();
                BeanUtils.copyProperties(blockExtendDto, blockVO);
                blockVOS.add(blockVO);
            }
        }
        return new PageDataVO(page, length, blockApplication.countNotSpecialBlocksByCondition(con), blockVOS);
    }

    @Override
    public ResponseVO checkMarker(long blockId, String marker) throws BuzException {
        if (StringUtils.isBlank(marker)) {
            throw new BuzException(BuzExceptionCode.ErrorParam);
        }
        return new ResponseVO(applicationIhcApplication.checkRepeatMarkerByBlockId(blockId, marker));
    }
}
