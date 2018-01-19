package com.lifetech.dhap.pathcloud.tracking.application.impl;

import com.lifetech.dhap.pathcloud.setting.application.ParamSettingApplication;
import com.lifetech.dhap.pathcloud.setting.application.infrastructure.code.SystemKey;
import com.lifetech.dhap.pathcloud.tracking.application.PathTrackingApplication;
import com.lifetech.dhap.pathcloud.tracking.application.condition.PathTrackingCondition;
import com.lifetech.dhap.pathcloud.tracking.application.condition.ReportCondition;
import com.lifetech.dhap.pathcloud.tracking.application.dto.PathTrackingDto;
import com.lifetech.dhap.pathcloud.tracking.application.dto.ReportQueryDto;
import com.lifetech.dhap.pathcloud.tracking.application.dto.SignQueryDto;
import com.lifetech.dhap.pathcloud.tracking.domain.PathTrackingRepository;
import com.lifetech.dhap.pathcloud.tracking.domain.model.PathTracking;
import com.lifetech.dhap.pathcloud.user.application.UserApplication;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LiuMei on 2017-01-16.
 */
@Service
public class PathTrackingApplicationImpl implements PathTrackingApplication {

    @Autowired
    private PathTrackingRepository pathTrackingRepository;

    @Autowired
    private UserApplication userApplication;

    @Autowired
    private ParamSettingApplication paramSettingApplication;

    @Override
    public void addPathTracking(PathTrackingDto pathTrackingDto) {
        PathTracking pathTracking = new PathTracking();
        BeanUtils.copyProperties(pathTrackingDto, pathTracking);
        pathTrackingRepository.insert(pathTracking);
        pathTrackingDto.setId(pathTrackingRepository.last());
    }

    @Override
    public List<PathTrackingDto> getPathTrackingByCondition(PathTrackingCondition condition) {
        List<PathTrackingDto> pathTrackingDtos = new ArrayList<>();
        List<PathTracking> pathTrackings = pathTrackingRepository.selectByCondition(condition);
        if (pathTrackings != null && pathTrackings.size() > 0) {
            PathTrackingDto dto;
            for (PathTracking pathTracking : pathTrackings) {
                dto = new PathTrackingDto();
                BeanUtils.copyProperties(pathTracking, dto);
                pathTrackingDtos.add(dto);
            }
        }
        return pathTrackingDtos;
    }

    @Override
    public List<ReportQueryDto> reportQuery(ReportCondition con) {
        Integer normalDay = Integer.parseInt(paramSettingApplication.getContentByKey(SystemKey.ReportDeadline.toString()));
        Integer frozenDay = Integer.parseInt(paramSettingApplication.getContentByKey(SystemKey.FreezeReportDeadline.toString()));
        Integer ihcDay = Integer.parseInt(paramSettingApplication.getContentByKey(SystemKey.IhcReportDeadline.toString()));
        Integer dyeDay = Integer.parseInt(paramSettingApplication.getContentByKey(SystemKey.SpecialDyeReportDeadline.toString()));
        Integer consultDay = Integer.parseInt(paramSettingApplication.getContentByKey(SystemKey.ConsultReportDeadline.toString()));
        Integer difficultDay = Integer.parseInt(paramSettingApplication.getContentByKey(SystemKey.DifficultDeadline.toString()));
        Integer decalcifyDay = Integer.parseInt(paramSettingApplication.getContentByKey(SystemKey.DecalcifyDeadline.toString()));
        con.setReportDeadline(normalDay);
        con.setFreezeReportDeadline(frozenDay);
        con.setConsultReportDeadline(consultDay);
        con.setIhcReportDeadline(ihcDay);
        con.setSpecialDyeReportDeadline(dyeDay);
        con.setDifficultDeadline(difficultDay);
        con.setDecalcifyDeadline(decalcifyDay);
        List<ReportQueryDto> data = pathTrackingRepository.reportQuery(con);
        List<ReportQueryDto> lists = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(data)) {
            for (ReportQueryDto dto : data) {
                dto.setDiagnoseDoctorDesc(userApplication.getUserSimpleInfoById(dto.getDiagnoseDoctor()).getFirstName());
                if (dto.getPrintUserId() != null) {
                    dto.setPrintUserName(userApplication.getUserSimpleInfoById(dto.getPrintUserId()).getFirstName());
                }
                lists.add(dto);
            }
        }
        return lists;

    }

    @Override
    public Long reportQueryTotal(ReportCondition con) {
        return pathTrackingRepository.reportQueryTotal(con);
    }

    @Override
    public List<ReportQueryDto> printRecordQuery(long id, Long specialApplyId) {
        List<ReportQueryDto> data;
        if (specialApplyId == null) {
            data = pathTrackingRepository.printRecordQuery(id);
        } else {
            data = pathTrackingRepository.specialPrintRecordQuery(specialApplyId);
        }
        List<ReportQueryDto> list = null;
        if (CollectionUtils.isNotEmpty(data)) {
            list = new ArrayList<>();
            for (ReportQueryDto dto : data) {
                dto.setPrintUserName(userApplication.getUserSimpleInfoById(dto.getPrintUserId()).getFirstName());
                list.add(dto);
            }
        }
        return list;
    }

    @Override
    public List<SignQueryDto> signQuery(ReportCondition con) {

        Integer normalDay = Integer.parseInt(paramSettingApplication.getContentByKey(SystemKey.ReportDeadline.toString()));
        Integer frozenDay = Integer.parseInt(paramSettingApplication.getContentByKey(SystemKey.FreezeReportDeadline.toString()));
        Integer ihcDay = Integer.parseInt(paramSettingApplication.getContentByKey(SystemKey.IhcReportDeadline.toString()));
        Integer dyeDay = Integer.parseInt(paramSettingApplication.getContentByKey(SystemKey.SpecialDyeReportDeadline.toString()));
        Integer consultDay = Integer.parseInt(paramSettingApplication.getContentByKey(SystemKey.ConsultReportDeadline.toString()));
        Integer decalcifyDay = Integer.parseInt(paramSettingApplication.getContentByKey(SystemKey.DecalcifyDeadline.toString()));
        Integer difficultDay = Integer.parseInt(paramSettingApplication.getContentByKey(SystemKey.DifficultDeadline.toString()));
        con.setReportDeadline(normalDay);
        con.setFreezeReportDeadline(frozenDay);
        con.setConsultReportDeadline(consultDay);
        con.setIhcReportDeadline(ihcDay);
        con.setSpecialDyeReportDeadline(dyeDay);
        con.setDecalcifyDeadline(decalcifyDay);
        con.setDifficultDeadline(difficultDay);
        List<SignQueryDto> data = pathTrackingRepository.signQuery(con);
        List<SignQueryDto> list = null;
        if (CollectionUtils.isNotEmpty(data)) {
            list = new ArrayList<>();
            for (SignQueryDto dto : data) {
                dto.setDiagnoseDoctorDesc(userApplication.getUserSimpleInfoById(dto.getDiagnoseDoctor()).getFirstName());
                list.add(dto);
            }
        }
        return list;
    }

    @Override
    public Long signQueryTotal(ReportCondition con) {
        return pathTrackingRepository.signQueryTotal(con);
    }

    @Override
    public PathTrackingDto getPathTrackingByPathId(Long id) {
        PathTracking pathTracking = pathTrackingRepository.getPathTrackingByPathId(id);
        PathTrackingDto pathTrackingDto = new PathTrackingDto();
        BeanUtils.copyProperties(pathTracking, pathTrackingDto);
        if (pathTrackingDto.getOperatorId() != null) {
            pathTrackingDto.setOperatorName(userApplication.getUserSimpleInfoById(pathTrackingDto.getOperatorId()).getFirstName());
        }
        return pathTrackingDto;
    }

    @Override
    public List<PathTrackingDto> getPathTrackingByPathIdAndPrintSign(long pathId, Integer printSign) {
        List<PathTracking> pathTrackingList = pathTrackingRepository.getPathTrackingByPathIdAndPrintSign(pathId, printSign);
        List<PathTrackingDto> PathTrackingDtoList = new ArrayList();
        PathTrackingDto pathTrackingDto;
        for (PathTracking pathTracking : pathTrackingList) {
            pathTrackingDto = new PathTrackingDto();
            BeanUtils.copyProperties(pathTracking, pathTrackingDto);
            PathTrackingDtoList.add(pathTrackingDto);
        }
        return PathTrackingDtoList;
    }
}
