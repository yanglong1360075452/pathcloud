package com.lifetech.dhap.pathcloud.application.application.impl;

import com.lifetech.dhap.pathcloud.application.application.ApplicationApplication;
import com.lifetech.dhap.pathcloud.application.application.PathologyApplication;
import com.lifetech.dhap.pathcloud.application.application.condition.PathologyCondition;
import com.lifetech.dhap.pathcloud.application.application.condition.PathologyQueryCondition;
import com.lifetech.dhap.pathcloud.application.application.condition.SampleCondition;
import com.lifetech.dhap.pathcloud.application.application.condition.WechatCondition;
import com.lifetech.dhap.pathcloud.application.application.dto.*;
import com.lifetech.dhap.pathcloud.application.application.infrastructure.code.ApplicationStatus;
import com.lifetech.dhap.pathcloud.application.application.infrastructure.code.ApplyType;
import com.lifetech.dhap.pathcloud.application.application.infrastructure.code.IhcApplicationStatus;
import com.lifetech.dhap.pathcloud.application.domain.PathologyRepository;
import com.lifetech.dhap.pathcloud.application.domain.SampleRepository;
import com.lifetech.dhap.pathcloud.application.domain.model.*;
import com.lifetech.dhap.pathcloud.application.infrastructure.code.QueryFieldType;
import com.lifetech.dhap.pathcloud.common.constant.Config;
import com.lifetech.dhap.pathcloud.common.data.PathologyStatus;
import com.lifetech.dhap.pathcloud.common.data.SequenceName;
import com.lifetech.dhap.pathcloud.common.data.SpecialApplyType;
import com.lifetech.dhap.pathcloud.common.exception.BuzException;
import com.lifetech.dhap.pathcloud.common.exception.BuzExceptionCode;
import com.lifetech.dhap.pathcloud.common.utils.ListUtil;
import com.lifetech.dhap.pathcloud.security.UserContext;
import com.lifetech.dhap.pathcloud.setting.application.ParamSettingApplication;
import com.lifetech.dhap.pathcloud.setting.application.dto.PathNoRuleDto;
import com.lifetech.dhap.pathcloud.setting.application.infrastructure.code.SystemKey;
import com.lifetech.dhap.pathcloud.tracking.application.BlockApplication;
import com.lifetech.dhap.pathcloud.tracking.application.PathTrackingApplication;
import com.lifetech.dhap.pathcloud.tracking.application.SpecialApplicationApplication;
import com.lifetech.dhap.pathcloud.tracking.application.TrackingApplication;
import com.lifetech.dhap.pathcloud.tracking.application.condition.TrackingCondition;
import com.lifetech.dhap.pathcloud.tracking.application.dto.PathTrackingDto;
import com.lifetech.dhap.pathcloud.tracking.application.dto.SpecialApplicationDto;
import com.lifetech.dhap.pathcloud.tracking.application.dto.TrackingDto;
import com.lifetech.dhap.pathcloud.tracking.application.infrastructure.code.TrackingOperation;
import com.lifetech.dhap.pathcloud.user.application.UserApplication;
import com.lifetech.dhap.pathcloud.user.application.dto.UserSimpleDto;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by LuoMo on 2016-11-23.
 */
@Service
public class PathologyApplicationImpl implements PathologyApplication {


    @Autowired
    private PathologyRepository pathologyRepository;

    @Autowired
    private SampleRepository sampleRepository;

    @Autowired
    private BlockApplication blockApplication;

    @Autowired
    private UserApplication userApplication;

    @Autowired
    private ParamSettingApplication paramSettingApplication;

    @Autowired
    private TrackingApplication trackingApplication;

    @Autowired
    private PathTrackingApplication pathTrackingApplication;

    @Autowired
    private ApplicationApplication applicationApplication;

    @Autowired
    private SpecialApplicationApplication specialApplicationApplication;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public PathologyDto register(PathologyDto pathologyDto, String letter, Integer code) {
        long userId = UserContext.getLoginUserID();
        Long pathId = pathologyDto.getId();
        PathNoRuleDto pathNoRule = paramSettingApplication.getPathNoRule();
        if (pathNoRule == null) {
            throw new BuzException(BuzExceptionCode.SettingError);
        }
        String pathLetter = letter;
        /*
        0 代表前面补充0
        digit 代表长度
        d 代表参数为正数型
         */
        Integer letterDigit = pathNoRule.getLetter();
        if (letterDigit == 0) { //病理号生成不加字母
            pathLetter = "";
        }
        String number = "";
        String formatDigit = "%0" + pathNoRule.getDigit() + "d";
        String formatDate = new SimpleDateFormat(pathNoRule.getTime()).format(new Date());
        if (pathId != null) {
            Pathology pathology = pathologyRepository.selectByPrimaryKey(pathId);
            pathology.setStatus(PathologyStatus.PrepareGrossing.toCode());
            updatePathology(pathologyDto);
        } else {
            Integer applyType = pathologyDto.getApplyType();
            Pathology pathology = null;
            String pathNo = null;
            if (code.equals(3)) {
                String multiFrozen = paramSettingApplication.getContentByKey(SystemKey.MultiFrozen.toString());
                //一个病理可生成多个病理号
                if (multiFrozen.equals("1")) {
                    pathology = pathologyRepository.selectCurrentDayApplyByAdmissionNo(pathologyDto.getAdmissionNo());
                    if(pathology != null){
                        pathNo = pathology.getSerialNumber();
                        pathId = pathology.getId();
                    }
                }
            }
            if (pathology == null) {
                pathology = new Pathology();
                BeanUtils.copyProperties(pathologyDto, pathology);
                pathologyRepository.insert(pathology);
                pathId = pathologyRepository.last();
                pathology.setId(pathId);

                String endId = String.format(formatDigit, pathId);
                pathNo = pathLetter + formatDate + endId;
                pathology.setSerialNumber(pathNo);
            }
            pathology.setUpdateBy(userId);
            formatDigit = "%04d";
            if (code.equals(3)) {//冷冻类别
                String frozenNumber = Config.FROZENLETTER + formatDate + String.format(formatDigit, pathologyRepository.getNextNumber(SequenceName.FrozenNumber.toString()));
                //添加冰冻申请记录
                SpecialApplicationDto specialApplicationDto = new SpecialApplicationDto();
                specialApplicationDto.setPathNo(pathNo);
                specialApplicationDto.setType(SpecialApplyType.Frozen.toCode());
                specialApplicationDto.setNumber(frozenNumber);
                String usingFrozen = paramSettingApplication.getContentByKey(SystemKey.UsingFrozen.toString());
                if (usingFrozen.equals("0")) { //不使用冰冻取材
                    specialApplicationDto.setStatus(PathologyStatus.PrepareFirstDiagnose.toCode());
                } else if (usingFrozen.equals("1")) {
                    specialApplicationDto.setStatus(PathologyStatus.PrepareGrossing.toCode());
                }
                specialApplicationDto.setCreateBy(userId);
                specialApplicationDto.setCauseId(pathId);
                specialApplicationDto.setAssignGrossing(pathologyDto.getAssignGrossing());
                specialApplicationApplication.add(specialApplicationDto);
                number = frozenNumber;
            } else if (applyType.equals(ApplyType.Consult.toCode())) {
                String consultNumber = Config.CONSULTLETTER + formatDate + String.format(formatDigit, pathologyRepository.getNextNumber(SequenceName.ConsultNumber.toString()));
                //添加会诊申请记录
                SpecialApplicationDto specialApplicationDto = new SpecialApplicationDto();
                specialApplicationDto.setPathNo(pathNo);
                specialApplicationDto.setType(SpecialApplyType.Consult.toCode());
                specialApplicationDto.setNumber(consultNumber);
                specialApplicationDto.setStatus(PathologyStatus.PrepareFirstDiagnose.toCode());
                specialApplicationDto.setCreateBy(userId);
                specialApplicationDto.setCauseId(pathId);
                specialApplicationApplication.add(specialApplicationDto);
                pathology.setStatus(PathologyStatus.Ending.toCode());
                number = consultNumber;
            }
            pathologyRepository.updateByPrimaryKey(pathology);
        }

        long applicationId = pathologyDto.getApplicationId();
        ApplicationDto applicationDto = applicationApplication.getApplicationById(applicationId);
        applicationDto.setStatus(ApplicationStatus.Register.toCode());
        applicationDto.setPathologyId(pathId);
        applicationDto.setNumber(number);
        applicationApplication.updateApplicationStatus(applicationDto);

        PathologyExpand pathologyExpand = pathologyRepository.selectExpandByPrimaryKey(pathId);
        List<SampleDto> samples = pathologyDto.getSamples();
        if (CollectionUtils.isNotEmpty(samples)) {
            SampleCondition sampleCondition = new SampleCondition();
            sampleCondition.setApplicationId(applicationId);
            List<Long> sampleIds = sampleRepository.selectIdsByApplicationId(sampleCondition);
            List<Long> sampleSaves = new ArrayList<>();
            for (SampleDto sampleDto : samples) {
                Long sampleId = sampleDto.getId();
                Sample sample;
                if (sampleId == null) {//新增样本
                    sample = new Sample();
                    BeanUtils.copyProperties(sampleDto, sample);
                    sample.setApplicationId(applicationId);
                    sample.setDelete(false);
                    sample.setCreateBy(userId);
                    sampleRepository.insert(sample);
                    sampleId = sampleRepository.last();
                    sample = sampleRepository.selectByPrimaryKey(sampleId);
                    sample.setSerialNumber("SA" + formatDate + String.format(formatDigit, sampleId));
                    sample.setUpdateBy(userId);
                    sampleRepository.updateByPrimaryKey(sample);
                } else {//已有记录
                    Integer category = sampleDto.getCategory();
                    String name = sampleDto.getName();
                    if (category == null || StringUtils.isBlank(name)) {
                        throw new BuzException(BuzExceptionCode.ErrorParam);
                    }
                    Sample sampleOld = sampleRepository.selectByPrimaryKey(sampleId);
                    if (!sampleOld.getCategory().equals(category) || !sampleOld.getName().equals(name)) {//原记录有修改
                        sampleOld.setCategory(category);
                        sampleOld.setName(name);
                        sampleOld.setUpdateBy(userId);
                        sampleRepository.updateByPrimaryKey(sampleOld);
                    }
                    sampleSaves.add(sampleId);
                }
            }
            if (CollectionUtils.isNotEmpty(sampleIds)) {
                List<Long> samplesDelete = ListUtil.minus(sampleIds, sampleSaves);//删除的记录
                if (CollectionUtils.isNotEmpty(samplesDelete)) {
                    for (Long sampleId : samplesDelete) {
                        Sample sample = sampleRepository.selectByPrimaryKey(sampleId);
                        sample.setDelete(true);
                        sample.setUpdateBy(userId);
                        sampleRepository.updateByPrimaryKey(sample);
                    }
                }
            }
        }

        PathTrackingDto trackingDto = new PathTrackingDto();
        trackingDto.setCreateBy(userId);
        trackingDto.setOperation(TrackingOperation.register.toCode());
        trackingDto.setOperatorId(userId);
        trackingDto.setPathId(pathId);
        pathTrackingApplication.addPathTracking(trackingDto);
        pathologyExpand.setNumber(number);
        return pathologyExpandToDto(pathologyExpand);

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void cancelPathology(PathologyDto pathologyDto) {
        Integer status = pathologyDto.getStatus();
        if (!PathologyStatus.PrepareGrossing.toCode().equals(status)) {
            throw new BuzException(BuzExceptionCode.StatusNotMatch);
        }
        Long userId = UserContext.getLoginUserID();
        pathologyDto.setStatus(PathologyStatus.PrepareRegister.toCode());
        updatePathology(pathologyDto);
        PathTrackingDto trackingDto = new PathTrackingDto();
        trackingDto.setCreateBy(userId);
        trackingDto.setOperation(TrackingOperation.cancelRegister.toCode());
        trackingDto.setOperatorId(userId);
        trackingDto.setPathId(pathologyDto.getId());
        pathTrackingApplication.addPathTracking(trackingDto);
    }

    @Override
    public List<PathologyDto> getGrossingPathologiesByCondition(PathologyCondition con) {
        List<PathologyExpand> pathologies = pathologyRepository.selectGrossingExpandByCondition(con);
        if (CollectionUtils.isNotEmpty(pathologies)) {
            List<PathologyDto> pathologyDtoList = new ArrayList<>();
            PathologyDto pathologyDto;
            for (PathologyExpand pathologyExpand : pathologies) {
                pathologyDto = new PathologyDto();
                BeanUtils.copyProperties(pathologyExpand, pathologyDto);
                pathologyDto.setStatusName(PathologyStatus.valueOf(pathologyExpand.getStatus()).toString());
                pathologyDtoList.add(pathologyDto);
            }
            return pathologyDtoList;
        }
        return null;
    }

    @Override
    public Long countGrossingPathologiesByCondition(PathologyCondition con) {
        return pathologyRepository.countGrossingByCondition(con);
    }

    @Override
    public PathologyDto getGrossingPathologiesById(long id) {
        return pathologyExpandToDto(pathologyRepository.selectExpandByPrimaryKey(id));
    }

    @Override
    public PathologyDto getSimplePathById(long id) {
        return pathologySimpleToDto(pathologyRepository.selectByPrimaryKey(id));
    }

    @Override
    public PathologyDto getSimplePathByNo(String pathNo) {
        return pathologySimpleToDto(pathologyRepository.selectByNo(pathNo));
    }

    private PathologyDto pathologySimpleToDto(Pathology pathology){
        PathologyDto pathologyDto = null;
        if (pathology != null) {
            pathologyDto = new PathologyDto();
            BeanUtils.copyProperties(pathology, pathologyDto);
        }
        return pathologyDto;
    }

    @Override
    public PathologyDto getPathologyByApplicationId(long applicationId) throws BuzException {
        Pathology pathology = pathologyRepository.selectByApplicationId(applicationId);
        if (pathology != null) {
            PathologyDto pathologyDto = new PathologyDto();
            BeanUtils.copyProperties(pathology, pathologyDto);
            return pathologyDto;
        } else {
            return null;
        }
    }

    @Override
    public PathologyExpandDto getPathologyExpandById(Long id) {
        PathologyExpand pathologyExpand = pathologyRepository.selectExpandByPrimaryKey(id);
        if (pathologyExpand == null) {
            return null;
        }
        PathologyExpandDto dto = new PathologyExpandDto();
        BeanUtils.copyProperties(pathologyExpand, dto);
        return dto;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updatePathology(PathologyDto pathologyDto) throws BuzException {
        if (pathologyDto != null) {
            Long id = pathologyDto.getId();
            if (id == null) {
                throw new BuzException(BuzExceptionCode.ErrorParam);
            }
            Pathology pathology = pathologyRepository.selectByPrimaryKey(id);
            if (pathology == null) {
                throw new BuzException(BuzExceptionCode.ErrorParam);
            }
            Integer status = pathologyDto.getStatus();
            String bingdongNote = pathologyDto.getBingdongNote();
            String jujianNote = pathologyDto.getJujianNote();
            String result = pathologyDto.getResult();
            String serialNumber = pathologyDto.getSerialNumber();
            String diagnose = pathologyDto.getDiagnose();
            String microDiagnose = pathologyDto.getMicroDiagnose();
            String reportPic = pathologyDto.getReportPic();
            if (status != null) {
                pathology.setStatus(status);
            }
            if (bingdongNote != null) {
                pathology.setBingdongNote(bingdongNote);
            }
            if (jujianNote != null) {
                pathology.setJujianNote(jujianNote);
            }
            if (result != null) {
                pathology.setResult(result);
            }
            if (diagnose != null) {
                pathology.setDiagnose(diagnose);
            }
            if (microDiagnose != null) {
                pathology.setMicroDiagnose(microDiagnose);
            }
            if (!StringUtils.isBlank(serialNumber)) {
                pathology.setSerialNumber(serialNumber);
            }
            if (!StringUtils.isBlank(reportPic)) {
                pathology.setReportPic(reportPic);
            }

            pathology.setAssignDiagnose(pathologyDto.getAssignDiagnose());
            pathology.setAssignGrossing(pathologyDto.getAssignGrossing());
            pathology.setNote(pathologyDto.getNote());
            pathology.setReGrossing(pathologyDto.getReGrossing());
            pathology.setOutConsult(pathologyDto.getOutConsult());
            pathology.setUpdateBy(UserContext.getLoginUserID());
            pathology.setAfterFrozen(pathologyDto.getAfterFrozen());
            pathology.setCoincidence(pathologyDto.getCoincidence());
            pathology.setLabel(pathologyDto.getLabel());
            pathology.setOutConsultResult(pathologyDto.getOutConsultResult());
            pathologyRepository.updateByPrimaryKey(pathology);
        }
    }

    @Override
    public List<PathologyDiagnoseDto> getDiagnosePathologiesByCondition(PathologyCondition con) {
        List<PathologyExpand> pathologyExpands = pathologyRepository.selectDiagnoseExpandByCondition(con);
        List<PathologyDiagnoseDto> diagnoses = null;
        if (CollectionUtils.isNotEmpty(pathologyExpands)) {
            diagnoses = new ArrayList<>();
            for (PathologyExpand pathology : pathologyExpands) {
                diagnoses.add(pathologyExpandToDiagnoseDto(pathology));
            }
        }
        return diagnoses;
    }

    @Override
    public Long countDiagnosePathologiesByCondition(PathologyCondition con) {
        return pathologyRepository.countDiagnoseByCondition(con);
    }

    @Override
    public List<PathologyDto> getPrepareDiagnose(PathologyCondition condition) {
        List<PathologyExpand> pathologies = pathologyRepository.selectPrepareDiagnose(condition);
        List<PathologyDto> diagnoses = null;
        if (CollectionUtils.isNotEmpty(pathologies)) {
            diagnoses = new ArrayList<>();
            PathologyDto pathologyDto;
            for (PathologyExpand pathologyExpand : pathologies) {
                pathologyDto = new PathologyDto();
                BeanUtils.copyProperties(pathologyExpand, pathologyDto);
                pathologyDto.setStatusName(PathologyStatus.getNameByCode(pathologyDto.getStatus()));
                pathologyDto.setTypeDesc(SpecialApplyType.getNameByCode(pathologyDto.getType()));
                diagnoses.add(pathologyDto);
            }
        }
        return diagnoses;
    }

    @Override
    public Long countPrepareDiagnose(PathologyCondition condition) {
        return pathologyRepository.countPrepareDiagnose(condition);
    }

    @Override
    public ReportInfoDto getReportInfo(long pathId, Long specialApplyId) {
        ReportInfo reportInfo = pathologyRepository.selectReportInfoByPathId(pathId);
        ReportInfoDto reportInfoDto = new ReportInfoDto();
        BeanUtils.copyProperties(reportInfo, reportInfoDto);
        if (specialApplyId != null) {
            SpecialApplicationDto specialApplicationDto = specialApplicationApplication.getById(specialApplyId);
            if (specialApplicationDto != null) {
                reportInfoDto.setDiagnoseDoctor(userApplication.getUserSimpleInfoById(specialApplicationDto.getUpdateBy()));
                reportInfoDto.setDiagnoseTime(specialApplicationDto.getUpdateTime());
                reportInfoDto.setResultDom(specialApplicationDto.getResult());
                reportInfoDto.setSpecialResult(specialApplicationDto.getSpecialResult());
            }
        } else {
            ReportInfo diagnose = pathologyRepository.selectLastDiagnoseInfoByPathId(pathId);
            if (diagnose != null && diagnose.getPathId() != null) {
                reportInfoDto.setDiagnoseDoctor(userApplication.getUserSimpleInfoById(diagnose.getDiagnoseDoctor()));
                reportInfoDto.setDiagnoseTime(diagnose.getDiagnoseTime());
            }
        }
        Integer hospital = reportInfo.getHospital();
        reportInfoDto.setHospitalDesc(paramSettingApplication.getHospitalDescByCode(hospital));
        reportInfoDto.setDiagnoseDoctor(userApplication.getUserSimpleInfoById(reportInfo.getDiagnoseDoctor()));
        reportInfoDto.setApplyDoctor(userApplication.getUserSimpleInfoById(reportInfo.getApplyDoctor()));
        reportInfoDto.setFrozenResults(specialApplicationApplication.getFrozenResultByPathNo(reportInfo.getPathNo()));
        return reportInfoDto;
    }

    @Override
    public List<PathologyQueryDto> getPathologiesByCondition(PathologyQueryCondition condition) {
        if (condition.getFieldType() != null && (condition.getFieldType().equals(QueryFieldType.inspectionItem.toCode()))) {
            condition.setInspectionItem(condition.getFieldContain());
        }
        if (condition.getFieldType() != null
                && (condition.getFieldType().equals(QueryFieldType.diagnoseNote.toCode())
                || condition.getFieldType().equals(QueryFieldType.jujianNote.toCode())
                || condition.getFieldType().equals(QueryFieldType.mircoNote.toCode()))) {

            condition.setMatch(QueryFieldType.getNameByCode(condition.getFieldType()));
            StringBuilder against = new StringBuilder("");
            if (condition.getFieldContain() != null) {
                String[] contains = condition.getFieldContain().split(" ");
                for (String contain : contains) {
                    if (contain.length() == 1) {
                        against.append("+");
                        against.append(contain);
                        against.append("*");
                    } else {
                        against.append("+");
                        against.append(contain);
                    }
                }
            }
            if (condition.getFieldExclusive() != null) {
                against.append(" ");
                String[] contains = condition.getFieldExclusive().split(" ");
                for (String contain : contains) {
                    if (contain.length() == 1) {
                        against.append("-");
                        against.append(contain);
                        against.append("*");
                    } else {
                        against.append("-");
                        against.append(contain);
                    }
                }
            }
            condition.setAgain(against.toString());
            if (!condition.getAgain().equals("")) {
                condition.setParserNgram(true);//分词搜索
            }
        }
        if (condition.getFieldType() == null && (condition.getFieldContain() != null || condition.getFieldExclusive() != null)) {
            condition.setMatch(QueryFieldType.getNameByCode(QueryFieldType.any.toCode()));
            StringBuilder against = new StringBuilder("");
            if (condition.getFieldContain() != null) {
                String[] contains = condition.getFieldContain().split(" ");
                for (String contain : contains) {
                    if (contain.length() == 1) {
                        against.append("+");
                        against.append(contain);
                        against.append("*");
                    } else {
                        against.append("+");
                        against.append(contain);
                    }
                }
            }
            if (condition.getFieldExclusive() != null) {
                against.append(" ");
                String[] contains = condition.getFieldExclusive().split(" ");
                for (String contain : contains) {
                    if (contain.length() == 1) {
                        against.append("-");
                        against.append(contain);
                        against.append("*");
                    } else {
                        against.append("-");
                        against.append(contain);
                    }
                }
            }
            condition.setAgain(against.toString());
            if (condition.getAgain().equals("")) {
                condition.setAgain(null);
                condition.setParserNgram(false);//分词搜索
            }
        }

        List<PathologyQuery> pathologyQueries = pathologyRepository.selectQueryByCondition(condition);

        List<PathologyQueryDto> pathologyQueryDtoList = new ArrayList<>();
        PathologyQueryDto dto;
        for (PathologyQuery pathologyQuery : pathologyQueries) {
            dto = new PathologyQueryDto();
            BeanUtils.copyProperties(pathologyQuery, dto);
            dto.setInspectDoctor(userApplication.getUserSimpleInfoById(pathologyQuery.getInspectDoctor()));
            if (pathologyQuery.getStatus() == null) {
                dto.setStatus(PathologyStatus.PrepareRegister.toCode());
            }
            dto.setStatusName(PathologyStatus.getNameByCode(dto.getStatus()));
            pathologyQueryDtoList.add(dto);
        }
        return pathologyQueryDtoList;
    }

    @Override
    public Long countPathologiesByCondition(PathologyQueryCondition condition) {
        return pathologyRepository.countQueryByCondition(condition);
    }

    @Override
    public long countPathologyByInspectCategory(int code) {
        return pathologyRepository.countByInspectCategory(code);
    }

    @Override
    public List<PathologyDto> getReportPic(List<Long> pathIds) {
        List<Pathology> pathologies = pathologyRepository.selectReportPic(pathIds);
        List<PathologyDto> pathologyDtoList = null;
        if (CollectionUtils.isNotEmpty(pathologies)) {
            pathologyDtoList = new ArrayList<>();
            PathologyDto pathologyDto;
            for (Pathology pathology : pathologies) {
                pathologyDto = new PathologyDto();
                pathologyDto.setId(pathology.getId());
                pathologyDto.setReportPic(pathology.getReportPic());
                pathologyDtoList.add(pathologyDto);
            }
        }
        return pathologyDtoList;
    }

    @Override
    public PathologyDto getLastRegisterPathology(long userId, Date createTime) {
        Pathology pathology = pathologyRepository.selectLastRegisterPathology(userId, createTime);
        PathologyDto pathologyDto = null;
        if (pathology != null) {
            pathologyDto = new PathologyDto();
            BeanUtils.copyProperties(pathology, pathologyDto);
        }
        return pathologyDto;
    }

    @Override
    public List<WechatInfoDto> getMyApplications(WechatCondition con) {
        List<WechatInfoDto> wechatInfoDtos = pathologyRepository.getMyApplications(con);
        for (WechatInfoDto wechatInfoDto : wechatInfoDtos) {
            if (wechatInfoDto.getAiId() == null) {
                if (wechatInfoDto.getBookingId() == null) {
                    if (wechatInfoDto.getAstatus() == 1) {
                        wechatInfoDto.setPstatusDesc(ApplicationStatus.PrepareRegister.toString());
                    }
                    if (wechatInfoDto.getAstatus() == 2) {
                        if (wechatInfoDto.getPstatus() != 24) {
                            wechatInfoDto.setPstatusDesc("处理中");
                        } else {
                            wechatInfoDto.setPstatusDesc("已完成");
                        }
                    }
                    if (wechatInfoDto.getAstatus() == 3) {
                        wechatInfoDto.setPstatusDesc(ApplicationStatus.Reject.toString());
                    }
                    if (wechatInfoDto.getAstatus() == 4) {
                        wechatInfoDto.setPstatusDesc(ApplicationStatus.Cancel.toString());
                    }

                    if (wechatInfoDto.getAstatus() == 30) {
                        wechatInfoDto.setPstatusDesc(ApplicationStatus.Ending.toString());
                    }
                    wechatInfoDto.setAstatusDesc("常规染色");
                } else {
                    if (wechatInfoDto.getInstrumentId() != null) {
                        if (wechatInfoDto.getInstrumentId() == 1) {
                            wechatInfoDto.setInstrumentIdDesc("切片机一");
                        } else if (wechatInfoDto.getInstrumentId() == 2) {
                            wechatInfoDto.setInstrumentIdDesc("切片机二");
                        }
                    }
                    wechatInfoDto.setAstatusDesc("冰冻预约");
                }
            } else {
                List<WechatInfoDto> ihcs = pathologyRepository.getIhcs(wechatInfoDto.getAiId());
                List<String> list = new ArrayList();
                for (WechatInfoDto dto : ihcs) {
                    list.add(dto.getPserialNumber());
                }
                wechatInfoDto.setPserialNumbers(list);
                wechatInfoDto.setAstatusDesc("染色申请");
                if (wechatInfoDto.getSpecialDyeStatus() == 1) {
                    wechatInfoDto.setSpecialDyeStatusDesc(IhcApplicationStatus.PrepareConfirm.toString());
                }
                if (wechatInfoDto.getSpecialDyeStatus() == 2) {
                    if (wechatInfoDto.getPstatus() != 24) {
                        wechatInfoDto.setSpecialDyeStatusDesc("处理中");
                    } else {
                        wechatInfoDto.setSpecialDyeStatusDesc("已完成");
                    }
                }
                if (wechatInfoDto.getSpecialDyeStatus() == 3) {
                    wechatInfoDto.setSpecialDyeStatusDesc(IhcApplicationStatus.Cancel.toString());
                }
            }
        }
        return wechatInfoDtos;
    }

    @Override
    public Long getMyApplicationsTotal(WechatCondition con) {
        return pathologyRepository.getMyApplicationsTotal(con);
    }

    @Override
    public Integer getNextValue(String seqName) {
        return pathologyRepository.getNextNumber(seqName);
    }

    @Override
    public Boolean decalcify(long pathId) {
        return pathologyRepository.decalcify(pathId);
    }

    private PathologyDto pathologyExpandToDto(PathologyExpand pathologyExpand) {
        if (pathologyExpand == null) {
            return null;
        }
        PathologyDto pathologyDto = new PathologyDto();
        BeanUtils.copyProperties(pathologyExpand, pathologyDto);
        long id = pathologyDto.getId();
        pathologyDto.setSamples(applicationApplication.samplesToDtos(sampleRepository.selectByPathId(id)));
        pathologyDto.setStatusName(PathologyStatus.valueOf(pathologyExpand.getStatus()).toString());
        pathologyDto.setBlocks(blockApplication.getAllBlocksByPathId(id));
        UserSimpleDto userSimpleDto = userApplication.getUserSimpleInfoById(blockApplication.getGrossingDoctorByPathId(id));
        if (userSimpleDto != null) {
            pathologyDto.setGrossingDoctor(userSimpleDto);
        }
        Boolean reGrossing = pathologyDto.getReGrossing();
        if (reGrossing) {
            TrackingCondition condition = new TrackingCondition();
            condition.setBlockId(id);
            condition.setOperation(TrackingOperation.applyReGrossing.toCode());
            TrackingDto trackingDto = trackingApplication.getTrackingByCondition(condition);
            if (trackingDto != null) {
                pathologyDto.setApplyId(trackingDto.getId());//设置重补取申请tracking记录ID
            }
        }
        return pathologyDto;
    }

    private PathologyDiagnoseDto pathologyExpandToDiagnoseDto(PathologyExpand pathologyExpand) {
        PathologyDiagnoseDto diagnoseDto = null;
        if (pathologyExpand != null) {
            diagnoseDto = new PathologyDiagnoseDto();
            BeanUtils.copyProperties(pathologyExpand, diagnoseDto);
            diagnoseDto.setStatusDesc(PathologyStatus.valueOf(pathologyExpand.getStatus()).toString());
            diagnoseDto.setTypeDesc(SpecialApplyType.getNameByCode(pathologyExpand.getType()));
            Long inspectionDoctor = pathologyExpand.getInspectionDoctor();
            if (inspectionDoctor != null) {
                diagnoseDto.setInspectionDoctor(userApplication.getUserSimpleInfoById(inspectionDoctor));
            }
            Long firstDiagnoseDoctor = pathologyExpand.getFirstDiagnoseDoctor();
            if (firstDiagnoseDoctor != null) {
                diagnoseDto.setFirstDiagnoseDoctor(userApplication.getUserSimpleInfoById(firstDiagnoseDoctor));
            }
            Long secondDiagnoseDoctor = pathologyExpand.getSecondDiagnoseDoctor();
            if (secondDiagnoseDoctor != null) {
                diagnoseDto.setSecondDiagnoseDoctor(userApplication.getUserSimpleInfoById(secondDiagnoseDoctor));
            }
            Long thirdDiagnoseDoctor = pathologyExpand.getThirdDiagnoseDoctor();
            if (thirdDiagnoseDoctor != null) {
                diagnoseDto.setThirdDiagnoseDoctor(userApplication.getUserSimpleInfoById(thirdDiagnoseDoctor));
            }
            Long reportDoctor = pathologyExpand.getReportDoctor();
            if (reportDoctor != null) {
                diagnoseDto.setReportDoctor(userApplication.getUserSimpleInfoById(reportDoctor));
            }
            Long assignDiagnose = pathologyExpand.getAssignDiagnose();
            if (assignDiagnose != null) {
                diagnoseDto.setAssignDiagnoseDoctor(userApplication.getUserSimpleInfoById(assignDiagnose));
            }
        }
        return diagnoseDto;
    }

    @Override
    public PathologyDto getPathologyBySerialNumber(String serialNumber) {
        PathologyExpand pathology = pathologyRepository.selectExpandBySerialNumber(serialNumber);
        if (pathology == null) {
            return null;
        }
        return pathologyExpandToDto(pathology);
    }
}
