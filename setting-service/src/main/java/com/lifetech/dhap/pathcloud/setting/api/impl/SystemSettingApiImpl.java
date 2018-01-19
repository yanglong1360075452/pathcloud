package com.lifetech.dhap.pathcloud.setting.api.impl;

import com.lifetech.dhap.pathcloud.application.application.PathologyApplication;
import com.lifetech.dhap.pathcloud.application.application.SampleApplication;
import com.lifetech.dhap.pathcloud.common.data.PathologyStatus;
import com.lifetech.dhap.pathcloud.common.exception.BuzException;
import com.lifetech.dhap.pathcloud.common.exception.BuzExceptionCode;
import com.lifetech.dhap.pathcloud.common.response.ResponseVO;
import com.lifetech.dhap.pathcloud.common.utils.ListUtil;
import com.lifetech.dhap.pathcloud.common.utils.StringUtil;
import com.lifetech.dhap.pathcloud.setting.api.SystemSettingApi;
import com.lifetech.dhap.pathcloud.setting.api.vo.InspectCategoryVO;
import com.lifetech.dhap.pathcloud.setting.api.vo.ParamSettingVO;
import com.lifetech.dhap.pathcloud.setting.application.ParamSettingApplication;
import com.lifetech.dhap.pathcloud.setting.application.dto.InspectCategoryDto;
import com.lifetech.dhap.pathcloud.setting.application.dto.ParamSettingDto;
import com.lifetech.dhap.pathcloud.setting.application.dto.TrackingListDto;
import com.lifetech.dhap.pathcloud.setting.application.infrastructure.code.SystemKey;
import net.sf.json.JSONArray;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Created by LiuMei on 2016-12-27.
 */
@Component("systemSettingApi")
public class SystemSettingApiImpl implements SystemSettingApi {

    @Autowired
    private ParamSettingApplication paramSettingApplication;

    @Autowired
    private PathologyApplication pathologyApplication;

    @Autowired
    private SampleApplication sampleApplication;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseVO updateTrackingList(String uncheckedList) throws BuzException {
        List<Integer> uncheckedCodes = new ArrayList<>();
        if (uncheckedList != null && !(uncheckedList.equals("{}") || ("").equals(uncheckedList.trim()))) {
            //不追踪项目
            uncheckedCodes = StringUtil.stringSplitToList(uncheckedList);
            uncheckedCodes = trackingLimit(uncheckedCodes);
        }

        Map<String, List> listMap = modTracking(uncheckedCodes);
        List trackingStatus = listMap.get("trackingStatus");
        List trackingList = listMap.get("trackingList");
        List trackingOperation = listMap.get("trackingOperation");

        if (!trackingStatus.contains(PathologyStatus.Ending.toCode())) {
            trackingStatus.add(PathologyStatus.Ending.toCode());
        }
        String result = JSONArray.fromObject(trackingList).toString();
        paramSettingApplication.updateContentByKey(SystemKey.TrackingList.toString(), result);

        TreeSet statusResult = new TreeSet();
        statusResult.addAll(trackingStatus);
        String statusString = JSONArray.fromObject(statusResult).toString();
        paramSettingApplication.updateContentByKey(SystemKey.TrackingStatus.toString(), statusString);

        String operationString = JSONArray.fromObject(trackingOperation).toString();
        paramSettingApplication.updateContentByKey(SystemKey.TrackingOperation.toString(), operationString);
        return new ResponseVO();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseVO updateFreezeTrackingList(String uncheckedList) throws BuzException {
        List<Integer> uncheckedCodes = new ArrayList<>();
        if (uncheckedList != null && !(uncheckedList.equals("{}") || ("").equals(uncheckedList.trim()))) {
            //不追踪项目
            uncheckedCodes = StringUtil.stringSplitToList(uncheckedList);
            uncheckedCodes = trackingLimit(uncheckedCodes);
        }
        Map<String, List> listMap = modTracking(uncheckedCodes);
        List trackingStatus = listMap.get("trackingStatus");
        List trackingList = listMap.get("trackingList");
        List trackingOperation = listMap.get("trackingOperation");

        if (!trackingStatus.contains(PathologyStatus.Ending.toCode())) {
            trackingStatus.add(PathologyStatus.Ending.toCode());
        }
        String result = JSONArray.fromObject(trackingList).toString();
        paramSettingApplication.updateContentByKey(SystemKey.FreezeTrackingList.toString(), result);

        TreeSet statusResult = new TreeSet();
        statusResult.addAll(trackingStatus);
        String statusString = JSONArray.fromObject(statusResult).toString();
        paramSettingApplication.updateContentByKey(SystemKey.FreezeTrackingStatus.toString(), statusString);

        String operationString = JSONArray.fromObject(trackingOperation).toString();
        paramSettingApplication.updateContentByKey(SystemKey.FreezeTrackingOperation.toString(), operationString);
        return new ResponseVO();
    }

    private List<Integer> trackingLimit(List<Integer> uncheckedCodes) throws BuzException {
        if (CollectionUtils.isNotEmpty(uncheckedCodes)) {
            /**
             * 必须追踪的项目
             * 申请/登记/取材
             */
            List<Integer> mustChecked = new ArrayList();
            mustChecked.add(1);
            mustChecked.add(2);
            mustChecked.add(3);

            if (uncheckedCodes.contains(1) || uncheckedCodes.contains(2) || uncheckedCodes.contains(3)) {
                throw new BuzException(BuzExceptionCode.MustTracking);
            }

            /**
             * 6-切片
             * 7-染色 8-制片 9-诊断 10-报告
             * 如果不是切片后面的操作(染色/制片/诊断/报告)
             *  都不追踪,切片必须追踪
             *
             */
            List<Integer> sectionAfter = new ArrayList<>();
            sectionAfter.add(7);
            sectionAfter.add(8);
            sectionAfter.add(9);
            sectionAfter.add(10);
            if (!uncheckedCodes.containsAll(sectionAfter)) {
                mustChecked.add(6);
                if (uncheckedCodes.contains(6)) {
                    throw new BuzException(BuzExceptionCode.MustTrackingSection);
                }
            }

            /**
             * 如果追踪报告,诊断工作站必须追踪
             */
            if (!uncheckedCodes.contains(10)) {//报告
                mustChecked.add(9);
                if (uncheckedCodes.contains(9)) {
                    throw new BuzException(BuzExceptionCode.MustTrackingDiagnose);
                }
            }

            //去除必须追踪的项目
            uncheckedCodes = ListUtil.minusParam(uncheckedCodes, mustChecked);
        }
        return uncheckedCodes;
    }

    private Map<String, List> modTracking(List<Integer> uncheckedCodes) {
        List<TrackingListDto> trackingList = paramSettingApplication.getContentToListByKey(SystemKey.TrackingList.toString());
        List trackingStatus = new ArrayList();
        List trackingOperation = new ArrayList();

        if (CollectionUtils.isEmpty(uncheckedCodes)) {
            for (TrackingListDto trackingListDto : trackingList) {
                trackingListDto.setChecked(true);
                trackingOperation.addAll(trackingListDto.getOperation());
                trackingStatus.addAll(trackingListDto.getStatus());
            }
        } else {
            for (TrackingListDto trackingListDto : trackingList) {
                Integer dbCode = trackingListDto.getCode();
                for (Integer code : uncheckedCodes) {
                    if (code.equals(dbCode)) {
                        trackingListDto.setChecked(false);
                        break;
                    } else {
                        trackingListDto.setChecked(true);
                        trackingOperation.addAll(trackingListDto.getOperation());
                        trackingStatus.addAll(trackingListDto.getStatus());
                    }
                }
            }
        }
        Map result = new HashMap();
        result.put("trackingList", trackingList);
        result.put("trackingStatus", trackingStatus);
        result.put("trackingOperation", trackingOperation);
        return result;
    }

    @Override
    public ResponseVO addInspectCategory(InspectCategoryVO inspectCategoryVO) throws BuzException {
        String typeDesc = inspectCategoryVO.getTypeDesc();
        String letter = inspectCategoryVO.getLetter();
        if (StringUtils.isBlank(typeDesc)) {
            throw new BuzException(BuzExceptionCode.ErrorParam);
        }
        List<InspectCategoryDto> inspectCategoryDTOs = paramSettingApplication.getContentToListByKey(SystemKey.InspectCategory.toString());
        for (InspectCategoryDto inspectCategoryDto : inspectCategoryDTOs) {
            if (typeDesc.equals(inspectCategoryDto.getTypeDesc()) || (!StringUtils.isBlank(letter) && inspectCategoryDto.getLetter().equals(letter))) {
                throw new BuzException(BuzExceptionCode.RecordExists);
            }
        }
        InspectCategoryDto inspectCategoryDto = new InspectCategoryDto();
        BeanUtils.copyProperties(inspectCategoryVO, inspectCategoryDto);
        paramSettingApplication.addInspectCategory(inspectCategoryDto);
        return new ResponseVO();
    }

    @Override
    public ResponseVO deleteInspectCategory(int code) throws BuzException {
        long total = pathologyApplication.countPathologyByInspectCategory(code);
        if (total > 0) {
            throw new BuzException(BuzExceptionCode.LabelCannotDelete);
        }
        paramSettingApplication.deleteInspectCategory(code);
        return new ResponseVO();
    }

    @Override
    public ResponseVO deleteSampleCategory(int code) throws BuzException {
        long total = sampleApplication.countByCategory(code);
        if (total > 0) {
            throw new BuzException(BuzExceptionCode.LabelCannotDelete);
        }
        paramSettingApplication.deleteParam(SystemKey.SampleCategory.toString(), code);
        return new ResponseVO();
    }

    @Override
    public ResponseVO getParam(String param) throws BuzException {
        SystemKey systemKey = SystemKey.getKeyByParam(param);
        if (systemKey == null) {
            throw new BuzException(BuzExceptionCode.ErrorParam);
        }
        if (param.equals(SystemKey.DifficultDeadline.toParam()) ||param.equals(SystemKey.DecalcifyDeadline.toParam()) ||
                param.equals(SystemKey.ReportDeadline.toParam()) || param.equals(SystemKey.PrepareGrossingAlarm.toParam()) ||
                param.equals(SystemKey.PrepareGrossingConfirmAlarm.toParam()) || param.equals(SystemKey.PrepareDehydrateAlarm.toParam()) ||
                param.equals(SystemKey.PrepareEmbedAlarm.toParam()) || param.equals(SystemKey.PrepareSectionAlarm.toParam()) ||
                param.equals(SystemKey.PrepareDyeAlarm.toParam()) || param.equals(SystemKey.PrepareConfirmAlarm.toParam()) ||
                param.equals(SystemKey.FreezeReportDeadline.toParam()) || param.equals(systemKey.SpecialDyeReportDeadline.toParam()) ||
                param.equals(SystemKey.IhcReportDeadline.toParam()) || param.equals(SystemKey.Hospital.toParam()) || param.equals(SystemKey.ConsultReportDeadline.toParam())
                || param.equals(SystemKey.UsingFrozen.toParam()) || param.equals(SystemKey.PrintFrozen.toParam()) ||
                param.equals(SystemKey.SpecialNumberPrint.toParam()) || param.equals(SystemKey.ReagentUsage.toParam())) {
            return new ResponseVO(paramSettingApplication.getContentByKey(systemKey.toString()));
        } else if(param.equals(SystemKey.PathNoRule.toParam())){
            return new ResponseVO(paramSettingApplication.getPathNoRule());
        }else {
            return new ResponseVO(paramSettingApplication.getContentToListByKey(systemKey.toString()));
        }
    }

    @Override
    public ResponseVO addNormalParam(String param, String name) throws BuzException {
        if (StringUtils.isBlank(param) || StringUtils.isBlank(name)) {
            throw new BuzException(BuzExceptionCode.ErrorParam);
        }
        SystemKey paramKey = SystemKey.getKeyByParam(param);
        if (paramKey == null) {
            throw new BuzException(BuzExceptionCode.ErrorParam);
        }

        List<ParamSettingDto> content = paramSettingApplication.getContentToListByKey(paramKey.toString());
        for (ParamSettingDto paramSettingDto : content) {
            if (paramSettingDto.getName().equals(name)) {
                throw new BuzException(BuzExceptionCode.ErrorParam);
            }
        }
        ParamSettingDto paramSettingDto = new ParamSettingDto();
        paramSettingDto.setName(name);
        paramSettingApplication.addParam(paramKey.toString(), paramSettingDto);
        return new ResponseVO();
    }

    @Override
    public ResponseVO updateNormalParam(String param, String code) {
        SystemKey systemKey = SystemKey.getKeyByParam(param);
        if (systemKey == null) {
            throw new BuzException(BuzExceptionCode.ErrorParam);
        }
        paramSettingApplication.updateContentByKey(SystemKey.getKeyByParam(param).toString(), code);
        return new ResponseVO();
    }

    @Override
    public ResponseVO updateQueryTimeRange(int code) {
        paramSettingApplication.updateQueryTimeRange(code);
        return new ResponseVO();
    }

    @Override
    public ResponseVO addParam(ParamSettingVO paramSettingVO) throws BuzException {
        String param = paramSettingVO.getParam();
        String name = paramSettingVO.getName();
        if (param == null || "".equals(param.trim()) || name == null || "".equals(name.trim())) {
            throw new BuzException(BuzExceptionCode.ErrorParam);
        }
        SystemKey systemKey = SystemKey.getKeyByParam(param);
        if (systemKey == null) {
            throw new BuzException(BuzExceptionCode.ErrorParam);
        }

        List<ParamSettingDto> content = paramSettingApplication.getContentToListByKey(systemKey.toString());
        for (ParamSettingDto paramSettingDto : content) {
            if (paramSettingDto.getName().equals(name)) {
                throw new BuzException(BuzExceptionCode.ErrorParam);
            }
        }
        ParamSettingDto paramSettingDto = new ParamSettingDto();
        BeanUtils.copyProperties(paramSettingVO, paramSettingDto);
        paramSettingDto = paramSettingApplication.addParam(systemKey.toString(), paramSettingDto);
        return new ResponseVO(paramSettingDto);
    }

    @Override
    public ResponseVO updateSpecialDye(ParamSettingVO paramSettingVO) throws BuzException {
        Integer code = paramSettingVO.getCode();
        String param = paramSettingVO.getParam();
        String name = paramSettingVO.getName();
        if (param == null || "".equals(param.trim()) || name == null || "".equals(name.trim())) {
            throw new BuzException(BuzExceptionCode.ErrorParam);
        }
        SystemKey systemKey = SystemKey.getKeyByParam(param);
        if (systemKey == null) {
            throw new BuzException(BuzExceptionCode.ErrorParam);
        }

        if (code == null || code <= 0) {
            throw new BuzException(BuzExceptionCode.ErrorParam);
        }


        ParamSettingDto paramSettingDto = new ParamSettingDto();
        BeanUtils.copyProperties(paramSettingVO, paramSettingDto);


        paramSettingApplication.updateDyeType(systemKey.toString(),paramSettingDto);
        return new ResponseVO();
    }

    @Override
    public ResponseVO deleteParam(String param, Integer code) throws BuzException {
        if (param == null || "".equals(param.trim()) || code == null) {
            throw new BuzException(BuzExceptionCode.ErrorParam);
        }
        SystemKey systemKey = SystemKey.getKeyByParam(param);
        if (systemKey == null) {
            throw new BuzException(BuzExceptionCode.ErrorParam);
        }

            paramSettingApplication.deleteParam(systemKey.toString(), code);

        return new ResponseVO();
    }


}
