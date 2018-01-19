package com.lifetech.dhap.pathcloud.application.api.impl;

import com.lifetech.dhap.pathcloud.application.api.PathologyApi;
import com.lifetech.dhap.pathcloud.application.api.vo.ConsultRegisterVO;
import com.lifetech.dhap.pathcloud.application.api.vo.PathologyVO;
import com.lifetech.dhap.pathcloud.application.api.vo.SampleVO;
import com.lifetech.dhap.pathcloud.application.application.ApplicationApplication;
import com.lifetech.dhap.pathcloud.application.application.ConsultApplication;
import com.lifetech.dhap.pathcloud.application.application.PathologyApplication;
import com.lifetech.dhap.pathcloud.application.application.condition.ApplicationCondition;
import com.lifetech.dhap.pathcloud.application.application.condition.PathologyCondition;
import com.lifetech.dhap.pathcloud.application.application.dto.*;
import com.lifetech.dhap.pathcloud.application.application.infrastructure.code.ApplicationStatus;
import com.lifetech.dhap.pathcloud.application.infrastructure.code.PathologySampleSortByEnum;
import com.lifetech.dhap.pathcloud.common.data.PathologyStatus;
import com.lifetech.dhap.pathcloud.common.exception.BuzException;
import com.lifetech.dhap.pathcloud.common.exception.BuzExceptionCode;
import com.lifetech.dhap.pathcloud.common.response.PageDataVO;
import com.lifetech.dhap.pathcloud.common.response.ResponseVO;
import com.lifetech.dhap.pathcloud.common.utils.CommonUtil;
import com.lifetech.dhap.pathcloud.common.utils.StringUtil;
import com.lifetech.dhap.pathcloud.security.UserContext;
import com.lifetech.dhap.pathcloud.setting.application.ParamSettingApplication;
import com.lifetech.dhap.pathcloud.setting.application.dto.InspectCategoryDto;
import com.lifetech.dhap.pathcloud.setting.application.infrastructure.code.SystemKey;
import com.lifetech.dhap.pathcloud.tracking.api.vo.BlockVO;
import com.lifetech.dhap.pathcloud.tracking.application.SpecialApplicationApplication;
import com.lifetech.dhap.pathcloud.tracking.application.dto.BlockDto;
import com.lifetech.dhap.pathcloud.user.api.vo.UserSimpleVO;
import com.lifetech.dhap.pathcloud.user.application.dto.UserSimpleDto;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.ws.rs.DefaultValue;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by LuoMo on 2016-11-23.
 */
@Component("pathologyApi")
public class PathologyApiImpl implements PathologyApi {

    private static final Logger logger = LoggerFactory.getLogger(PathologyApiImpl.class);

    @Autowired
    private ApplicationApplication applicationApplication;

    @Autowired
    private PathologyApplication pathologyApplication;

    @Autowired
    private ParamSettingApplication paramSettingApplication;

    @Autowired
    private ConsultApplication consultApplication;

    @Autowired
    private SpecialApplicationApplication specialApplicationApplication;

    @Override
    public ResponseVO createPathology(PathologyVO pathologyVO) throws BuzException {
        Long applicationId = pathologyVO.getApplicationId();
        Integer inspectCategory = pathologyVO.getInspectCategory();
        if (applicationId == null || inspectCategory == null) {
            throw new BuzException(BuzExceptionCode.ErrorParam);
        }
        InspectCategoryDto inspectCategoryDto = paramSettingApplication.getInspectCategoryByCode(inspectCategory);
        if (inspectCategoryDto == null) {
            throw new BuzException(BuzExceptionCode.ErrorParam);
        }
        Long pathId = pathologyVO.getId();
        ApplicationDto applicationDto = applicationApplication.getApplicationById(applicationId);
        if (pathologyVO.getId() != null) {
            PathologyDto pathologyDto = pathologyApplication.getSimplePathById(pathId);
            if (pathologyDto == null) {
                throw new BuzException(BuzExceptionCode.RecordNotExists);
            }
            if (!pathologyDto.getStatus().equals(PathologyStatus.PrepareRegister.toCode())) {
                throw new BuzException(BuzExceptionCode.StatusNotMatch);
            }
        } else {
            if (applicationDto == null) {
                throw new BuzException(BuzExceptionCode.ApplicationNotExists);
            }
            Integer status = applicationDto.getStatus();
            if (!status.equals(ApplicationStatus.PrepareRegister.toCode())) {
                throw new BuzException(BuzExceptionCode.ApplicationStatusChange);
            }
        }
        PathologyDto pathologyDto = new PathologyDto();
        pathologyDto.setId(pathId);
        pathologyDto.setApplicationId(applicationId);
        pathologyDto.setInspectCategory(inspectCategory);
        pathologyDto.setStatus(PathologyStatus.PrepareGrossing.toCode());
        pathologyDto.setCreateBy(UserContext.getLoginUserID());
        pathologyDto.setApplyType(applicationDto.getApplyType());
        pathologyDto.setAssignGrossing(pathologyVO.getAssignGrossing());
        pathologyDto.setPatientName(applicationDto.getPatientName());
        pathologyDto.setDepartments(applicationDto.getDepartments());
        pathologyDto.setAdmissionNo(applicationDto.getAdmissionNo());
        List<SampleVO> samples = pathologyVO.getSamples();
        pathologyDto.setSamples(ApplicationApiImpl.sampleVOsToDTOs(samples));
        //会诊目的和要求
        pathologyDto.setNote(pathologyVO.getNote());

        pathologyDto = pathologyApplication.register(pathologyDto, inspectCategoryDto.getLetter(),inspectCategoryDto.getCode());
        return new ResponseVO(pathologyDto);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseVO consultRegister(ConsultRegisterVO registerVO) throws BuzException {
        Long applicationId = registerVO.getApplicationId();
        String requirement = registerVO.getRequirement();
        if(applicationId == null || StringUtils.isBlank(requirement)){
            throw new BuzException(BuzExceptionCode.ErrorParam);
        }
        ApplicationDto applicationDto = applicationApplication.getApplicationById(applicationId);
        if(applicationDto == null){
            throw new BuzException(BuzExceptionCode.ErrorParam);
        }

        applicationDto.setStatus(ApplicationStatus.Register.toCode());
        applicationApplication.updateApplicationStatus(applicationDto);

        ConsultDto consultDto = new ConsultDto();
        consultDto.setHospital(applicationDto.getHospital());
        consultDto.setDepartments(applicationDto.getDepartments());
        consultDto.setPatientName(applicationDto.getPatientName());
        consultDto.setApplicationId(applicationId);
        consultDto.setRequirement(requirement);
        consultDto.setCreateBy(UserContext.getLoginUserID());
        consultApplication.add(consultDto);
        return new ResponseVO();
    }

    @Override
    public ResponseVO getPrePathology(Long createTime) throws BuzException {
        Long userId = UserContext.getLoginUserID();
        Date date  = new Date();
        if(createTime != null){
            date = new Date(createTime);
        }
        PathologyDto pathologyDto = pathologyApplication.getLastRegisterPathology(userId,date);
        if (pathologyDto != null) {
            ApplicationDto applicationDto = applicationApplication.getLastApplicationByPathNo(pathologyDto.getSerialNumber());
            return new ResponseVO(applicationDto);
        } else {
            return new ResponseVO();
        }
    }

    @Override
    public ResponseVO cancelPathology(long id) throws BuzException {
        PathologyDto pathologyDto = pathologyApplication.getSimplePathById(id);
        if (pathologyDto == null || pathologyDto.getId() == null) {
            throw new BuzException(BuzExceptionCode.RecordNotExists);
        }
        Integer status = pathologyDto.getStatus();
        if (!status.equals(PathologyStatus.PrepareGrossing.toCode())) {
            throw new BuzException(BuzExceptionCode.StatusNotMatch);
        }
        long userId = UserContext.getLoginUserID();
        if (!pathologyDto.getCreateBy().equals(userId)) {
            throw new BuzException(BuzExceptionCode.AccessDenied);
        }
        pathologyApplication.cancelPathology(pathologyDto);
        return new ResponseVO();
    }

    @Override
    public ResponseVO getPathologySamples(@DefaultValue("1") Integer page, @DefaultValue("10") Integer length,
                                          String filter, Integer status,Integer specialType, Long createTimeStart, Long createTimeEnd, Integer order, String sort) throws BuzException {
        if (page < 0 || length < 1) {
            throw new BuzException(BuzExceptionCode.ErrorParam);
        }
        if (status != null && status.equals(ApplicationStatus.Cancel.toCode())) {
            throw new BuzException(BuzExceptionCode.ErrorParam);
        }
        ApplicationCondition con = new ApplicationCondition();
        con.setSize(length);
        con.setStart((page - 1) * length);
        if (filter != null) {
            con.setFilter(StringUtil.escapeExprSpecialWord(filter));
        }
        con.setStatus(status);
        con.setCreateBy(UserContext.getLoginUserID());
        if (createTimeStart != null) {
            con.setCreateTimeStart(new Date(createTimeStart));
        }
        if (createTimeEnd != null) {
            con.setCreateTimeEnd(new Date(createTimeEnd));
        }

        if(sort == null){
            sort = "ASC";
        }

        if (order != null) {
            Integer pathStatus = PathologySampleSortByEnum.PathStatus.toCode();
            if(order.equals(pathStatus)){
                con.setOrder(PathologySampleSortByEnum.ApplicationStatus.toString()+" "+sort+","+PathologySampleSortByEnum.valueOf(pathStatus).toString()+" "+sort);
            }
            con.setOrder(PathologySampleSortByEnum.valueOf(order).toString() + " " + sort);
        }
        con.setSpecialType(specialType);
        List<ApplicationSampleDto> data = applicationApplication.getApplicationAndSamplesByCondition(con);
        Long total = applicationApplication.countApplicationAndSamplesByCondition(con);
        return new PageDataVO(page, length, total, data);
    }

    @Override
    public ResponseVO getGrossingPathologies(@DefaultValue("1") Integer page, @DefaultValue("10") Integer length, String filter, Integer status,
                                             Boolean reGrossing,Integer inspectCategory, Integer departments, Long createTimeStart,
                                             Long createTimeEnd, Integer order, String sort) throws BuzException {
        long startTime = System.currentTimeMillis();
        if (page < 0 || length < 1) {
            throw new BuzException(BuzExceptionCode.ErrorParam);
        }
        //状态只有是待取材或待取材确认
        if (status != null && !status.equals(PathologyStatus.PrepareGrossing.toCode()) && !status.equals(PathologyStatus.PrepareGrossingConfirm.toCode())) {
            throw new BuzException(BuzExceptionCode.ErrorParam);
        }

        PathologyCondition con = new PathologyCondition();
        Long loginUserID = UserContext.getLoginUserID();
        if (!CommonUtil.admin()) {
            con.setAssignGrossing(loginUserID);
        }
        con.setSize(length);
        con.setStart((page - 1) * length);
        if (filter != null) {
            if (filter.contains("-")) {
                String[] pathNos = filter.split("-");
                if (pathNos.length != 2) {
                    throw new BuzException(BuzExceptionCode.ErrorParam);
                }
                con.setPathNoStart(pathNos[0]);
                con.setPathNoEnd(pathNos[1]);
            } else {
                con.setFilter(StringUtil.escapeExprSpecialWord(filter.trim()));
            }
            con.setAssignGrossing(null);
        }
        con.setStatus(status);
        con.setReGrossing(reGrossing);
        con.setInspectCategory(inspectCategory);
        if(reGrossing != null && reGrossing){
            con.setStatus(PathologyStatus.PrepareGrossing.toCode());
        }
        con.setDepartments(departments);
        if (createTimeStart != null) {
            con.setCreateTimeStart(new Date(createTimeStart));
        }
        if (createTimeEnd != null) {
            con.setCreateTimeEnd(new Date(createTimeEnd));
        }
        String usingFrozen = paramSettingApplication.getContentByKey(SystemKey.UsingFrozen.toString());
        if(usingFrozen.equals("0")){
            con.setUsingFrozen(false);
        }else {
            con.setUsingFrozen(true);
        }

        long startTime1 = System.currentTimeMillis();

        List<PathologyDto> pathologyDtoList = pathologyApplication.getGrossingPathologiesByCondition(con);
        List<PathologyVO> pathologyVOS = null;
        if (CollectionUtils.isNotEmpty(pathologyDtoList)) {
            pathologyVOS = new ArrayList<>();
            for (PathologyDto pathologyDto : pathologyDtoList) {
                pathologyVOS.add(pathologyDtoToVO(pathologyDto));
            }
        }


        Long total = pathologyApplication.countGrossingPathologiesByCondition(con);

        long endTime = System.currentTimeMillis();
        if (endTime - startTime > 500) {
            logger.warn("getGrossingPathologies takes " + (endTime - startTime) + " ms. read db takes " + (endTime - startTime1));
        }
        return new PageDataVO(page, length, total, pathologyVOS);
    }

    @Override
    public ResponseVO getPathInfo(long id) {
        PathologyDto pathologyDto = pathologyApplication.getGrossingPathologiesById(id);
        return new ResponseVO(pathologyDtoToVO(pathologyDto));
    }

    private PathologyVO pathologyDtoToVO(PathologyDto pathologyDto){
        if(pathologyDto != null){
            PathologyVO pathologyVO = new PathologyVO();
            BeanUtils.copyProperties(pathologyDto, pathologyVO);
            if (pathologyDto.getReGrossing() && !pathologyDto.getStatus().equals(PathologyStatus.PrepareGrossingConfirm.toCode())) {
                pathologyVO.setStatusName("待重补取");
            }
            pathologyVO.setFrozenNumbers(specialApplicationApplication.getFrozenNumbersByPathNo(pathologyDto.getSerialNumber()));
            List<BlockDto> blocks = pathologyDto.getBlocks();
            if (CollectionUtils.isNotEmpty(blocks)) {
                List<BlockVO> blockVOS = new ArrayList<>();
                BlockVO blockVO;
                for (BlockDto block : blocks) {
                    blockVO = new BlockVO();
                    BeanUtils.copyProperties(block, blockVO);
                    blockVOS.add(blockVO);
                }
                pathologyVO.setBlocks(blockVOS);
            }

            List<SampleDto> samples = pathologyDto.getSamples();
            if (CollectionUtils.isNotEmpty(samples)) {
                List<SampleVO> sampleVOS = new ArrayList<>();
                SampleVO sampleVO;
                for (SampleDto sampleDto : samples) {
                    sampleVO = new SampleVO();
                    BeanUtils.copyProperties(sampleDto, sampleVO);
                    sampleVOS.add(sampleVO);
                }
                UserSimpleDto grossingDoctor = pathologyDto.getGrossingDoctor();
                if (grossingDoctor != null) {
                    UserSimpleVO userSimpleVO = new UserSimpleVO();
                    BeanUtils.copyProperties(grossingDoctor, userSimpleVO);
                    pathologyVO.setGrossingDoctor(userSimpleVO);
                }
                pathologyVO.setSamples(sampleVOS);
            }
            return pathologyVO;
        }
        return null;
    }
}
