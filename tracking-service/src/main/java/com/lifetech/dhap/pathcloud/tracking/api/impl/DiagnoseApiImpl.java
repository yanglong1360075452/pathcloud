package com.lifetech.dhap.pathcloud.tracking.api.impl;

import com.lifetech.dhap.pathcloud.application.application.ApplicationIhcApplication;
import com.lifetech.dhap.pathcloud.application.application.PathologyApplication;
import com.lifetech.dhap.pathcloud.application.application.condition.PathologyCondition;
import com.lifetech.dhap.pathcloud.application.application.dto.IhcBlockDto;
import com.lifetech.dhap.pathcloud.application.application.dto.PathologyDto;
import com.lifetech.dhap.pathcloud.common.constant.Config;
import com.lifetech.dhap.pathcloud.common.data.PathologyStatus;
import com.lifetech.dhap.pathcloud.common.data.SpecialApplyType;
import com.lifetech.dhap.pathcloud.common.domain.CommonRepository;
import com.lifetech.dhap.pathcloud.common.exception.BuzException;
import com.lifetech.dhap.pathcloud.common.exception.BuzExceptionCode;
import com.lifetech.dhap.pathcloud.common.response.PageDataVO;
import com.lifetech.dhap.pathcloud.common.response.ResponseVO;
import com.lifetech.dhap.pathcloud.common.utils.ExportExcel;
import com.lifetech.dhap.pathcloud.common.utils.StringUtil;
import com.lifetech.dhap.pathcloud.file.application.FileMappingApplication;
import com.lifetech.dhap.pathcloud.file.application.PathologyFileApplication;
import com.lifetech.dhap.pathcloud.file.application.dto.PathologyFileDto;
import com.lifetech.dhap.pathcloud.file.domain.model.FileMapping;
import com.lifetech.dhap.pathcloud.security.UserContext;
import com.lifetech.dhap.pathcloud.setting.application.ParamSettingApplication;
import com.lifetech.dhap.pathcloud.setting.application.infrastructure.code.SystemKey;
import com.lifetech.dhap.pathcloud.tracking.api.DiagnoseApi;
import com.lifetech.dhap.pathcloud.tracking.api.vo.*;
import com.lifetech.dhap.pathcloud.tracking.application.*;
import com.lifetech.dhap.pathcloud.tracking.application.condition.CollectCondition;
import com.lifetech.dhap.pathcloud.tracking.application.condition.PathTrackingCondition;
import com.lifetech.dhap.pathcloud.tracking.application.condition.SpecialApplicationCondition;
import com.lifetech.dhap.pathcloud.tracking.application.dto.*;
import com.lifetech.dhap.pathcloud.tracking.application.infrastructure.code.BlockScoreType;
import com.lifetech.dhap.pathcloud.tracking.application.infrastructure.code.TrackingOperation;
import com.lifetech.dhap.pathcloud.tracking.infrastructure.code.DiagnoseSortEnum;
import com.lifetech.dhap.pathcloud.user.api.vo.UserSimpleVO;
import com.lifetech.dhap.pathcloud.user.application.UserApplication;
import com.lifetech.dhap.pathcloud.common.data.Permission;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.PhaseInterceptorChain;
import org.apache.cxf.transport.http.AbstractHTTPDestination;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.core.Response;
import java.io.File;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by LiuMei on 2017-01-05.
 */
@Component("diagnoseApi")
public class DiagnoseApiImpl implements DiagnoseApi {

    @Autowired
    private PathologyApplication pathologyApplication;

    @Autowired
    private GrossingApplication grossingApplication;

    @Autowired
    private TrackingApplication trackingApplication;

    @Autowired
    private BlockApplication blockApplication;

    @Autowired
    private BlockScoreApplication blockScoreApplication;

    @Autowired
    private PathologyFileApplication pathologyFileApplication;

    @Autowired
    private UserApplication userApplication;

    @Autowired
    private PathTrackingApplication pathTrackingApplication;

    @Autowired
    private ParamSettingApplication paramSettingApplication;

    @Autowired
    private CommonRepository commonRepository;

    @Autowired
    private SpecialApplicationApplication specialApplicationApplication;

    @Autowired
    private ApplicationIhcApplication applicationIhcApplication;

    @Autowired
    private CollectApplication collectApplication;

    @Autowired
    private FileMappingApplication fileMappingApplication;

    @Override
    public ResponseVO getList(@DefaultValue("1") Integer page, @DefaultValue("10") Integer length, Integer order, String sort, Boolean delay,
                              String filter, Integer departments, Integer specialType, Long delayTime, Long createTimeStart, Long createTimeEnd) throws BuzException {
        if (page < 0 || length < 1 || createTimeStart == null) {
            throw new BuzException(BuzExceptionCode.ErrorParam);
        }
        PathologyCondition con = new PathologyCondition();
        con.setSize(length);
        con.setStart((page - 1) * length);
        if (filter != null) {
            con.setFilter(StringUtil.escapeExprSpecialWord(filter.trim()));
        }
        con.setDelay(delay);
        con.setSpecialType(specialType);
        con.setCreateTimeStart(new Date(createTimeStart));
        if (createTimeEnd != null) {
            con.setCreateTimeEnd(new Date(createTimeEnd));
        }
        con.setDepartments(departments);
        if (delayTime != null) {
            con.setEstimateReportTime(new Date(delayTime));
        }

        long userId = UserContext.getLoginUserID();
        con.setAssignDiagnose(userId);
        con.setStatusList(getLoginUserAuthStatus());
        int reportDeadLine = Integer.parseInt(paramSettingApplication.getContentByKey(SystemKey.ReportDeadline.toString()));
        int ihcReportDeadLine = Integer.parseInt(paramSettingApplication.getContentByKey(SystemKey.IhcReportDeadline.toString()));
        int freezeReportDeadLine = Integer.parseInt(paramSettingApplication.getContentByKey(SystemKey.FreezeReportDeadline.toString()));
        int specialDyeReportDeadLine = Integer.parseInt(paramSettingApplication.getContentByKey(SystemKey.SpecialDyeReportDeadline.toString()));
        int consultReportDeadLine = Integer.parseInt(paramSettingApplication.getContentByKey(SystemKey.ConsultReportDeadline.toString()));
        int decalcifyDeadLine = Integer.parseInt(paramSettingApplication.getContentByKey(SystemKey.DecalcifyDeadline.toString()));
        int difficultDeadLine = Integer.parseInt(paramSettingApplication.getContentByKey(SystemKey.DifficultDeadline.toString()));
        con.setReportDeadline(reportDeadLine);
        con.setIhcReportDeadline(ihcReportDeadLine);
        con.setFreezeReportDeadline(freezeReportDeadLine);
        con.setSpecialDyeReportDeadline(specialDyeReportDeadLine);
        con.setConsultReportDeadline(consultReportDeadLine);
        con.setDecalcifyDeadline(decalcifyDeadLine);
        con.setDifficultDeadline(difficultDeadLine);
        List<PathologyDto> pathologyDtoList = pathologyApplication.getPrepareDiagnose(con);
        List<DiagnosePrepareVO> diagnosePrepareVOS = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(pathologyDtoList)) {
            DiagnosePrepareVO diagnosePrepareVO;
            for (PathologyDto pathologyDto : pathologyDtoList) {
                diagnosePrepareVO = new DiagnosePrepareVO();
                BeanUtils.copyProperties(pathologyDto, diagnosePrepareVO);
                diagnosePrepareVOS.add(diagnosePrepareVO);
            }
        }
        return new PageDataVO(page, length, pathologyApplication.countPrepareDiagnose(con), diagnosePrepareVOS);
    }

    @Override
    public ResponseVO getInfo(String serialNumber, Boolean special) throws BuzException, ParseException {
        if (serialNumber == null || "".equals(serialNumber.trim()) || serialNumber.length() < 9) {
            throw new BuzException(BuzExceptionCode.ErrorParam);
        }
        String pathNo = serialNumber.substring(0, 9);
        String note = null;
        SpecialApplicationDto specialApplication = null;
        String outConsultResult = null;
        if (Boolean.TRUE.equals(special)) {
            specialApplication = specialApplicationApplication.getByNumber(serialNumber);
            if (specialApplication == null) {
                throw new BuzException(BuzExceptionCode.RecordNotExists);
            }
            pathNo = specialApplication.getPathNo();
            note = specialApplication.getNote();
            outConsultResult = specialApplication.getOutConsultResult();
        }

        PathologyDto pathologyDto = pathologyApplication.getPathologyBySerialNumber(pathNo);
        if (pathologyDto == null) {
            throw new BuzException(BuzExceptionCode.RecordNotExists);
        }

        if (!Boolean.TRUE.equals(special)) {
            note = pathologyDto.getNote();
            outConsultResult = pathologyDto.getOutConsultResult();
        }

        Long userId = UserContext.getLoginUserID();
        Long assignDiagnose = pathologyDto.getAssignDiagnose();
        Date dbNow = commonRepository.getDBNow();
        if (!Boolean.TRUE.equals(special) && assignDiagnose != null && !assignDiagnose.equals(userId)) {//已分配给其他医生
            return new ResponseVO(BuzExceptionCode.AccessDenied, userApplication.getUserSimpleInfoById(assignDiagnose));
        }
        DiagnoseVO diagnoseVO = new DiagnoseVO();
        BeanUtils.copyProperties(pathologyDto, diagnoseVO);

        long pathId = pathologyDto.getId();
        List<String> grossingImages = grossingApplication.getGrossingFile(pathId, TrackingOperation.grossing.toCode(), null).getImages();
        if (CollectionUtils.isNotEmpty(grossingImages)) {
            diagnoseVO.setGrossingImages(grossingImages);
        }
        String result = pathologyDto.getResult();
        if (StringUtils.isNotEmpty(result)) {
            diagnoseVO.setResult(result);
        }
        if (serialNumber.length() > 9) {//玻片号
            String[] split = serialNumber.split(Config.serialNumberSeparator);
            if (split.length != 3) {
                throw new BuzException(BuzExceptionCode.ErrorParam);
            }
            serialNumber = serialNumber.replaceAll(Config.serialNumberSeparator, "");
            SlideDto slideDto = blockApplication.getSlideBySlideNoAndSubId(serialNumber, split[2]);
            if (slideDto == null) {
                throw new BuzException(BuzExceptionCode.RecordNotExists);
            }
            int status = slideDto.getStatus();
            int pathStatus = pathologyDto.getStatus();
            diagnoseVO.setStatus(pathStatus);
            diagnoseVO.setStatusName(PathologyStatus.valueOf(pathStatus).toString());
            diagnoseVO.setSlideStatus(status);
            diagnoseVO.setSlideStatusDesc(PathologyStatus.valueOf(status).toString());
            diagnoseVO.setSlideId(slideDto.getId());
            diagnoseVO.setBlockId(slideDto.getParentId());
            diagnoseVO.setBlockSubId(slideDto.getBlockSubId());
            diagnoseVO.setSpecialDye(slideDto.getSpecialDye());
            diagnoseVO.setSpecialDyeDesc(slideDto.getSpecialDyeDesc());
            diagnoseVO.setBiaoshi(slideDto.getBiaoshi());
            diagnoseVO.setBiaoshiDesc(slideDto.getBiaoshiDesc());
            diagnoseVO.setSlideSubId(slideDto.getSubId());

            BlockScoreDto blockScoreDto = blockScoreApplication.getBlockScoreBySlideIdAndType(slideDto.getId(), BlockScoreType.Diagnose.toCode());
            if (blockScoreDto != null) {
                BlockScoreVO blockScoreVO = new BlockScoreVO();
                BeanUtils.copyProperties(blockScoreDto, blockScoreVO);
                diagnoseVO.setScore(blockScoreVO);
            }

            TrackingDto trackingDto = new TrackingDto();
            trackingDto.setCreateBy(userId);
            trackingDto.setBlockId(slideDto.getId());
            trackingDto.setOperatorId(userId);
            trackingDto.setOperation(TrackingOperation.watchSlide.toCode());
            trackingDto.setOperationTime(dbNow);
            trackingDto.setManualFlag(true);
            trackingDto.setInstrumentId(0L);
            trackingApplication.addTracking(trackingDto);
        }
        List<Long> slidesId = blockApplication.getSlidesIdByPathId(pathId);
        diagnoseVO.setCountWatchedSlide(trackingApplication.countWatchedSlide(userId, slidesId));
        diagnoseVO.setCountSlides((long) slidesId.size());

        Integer deadLine = 0;
        int reportDeadLine = Integer.parseInt(paramSettingApplication.getContentByKey(SystemKey.ReportDeadline.toString()));
        int decalcifyDeadline = Integer.parseInt(paramSettingApplication.getContentByKey(SystemKey.DecalcifyDeadline.toString()));
        int difficultDeadline = Integer.parseInt(paramSettingApplication.getContentByKey(SystemKey.DifficultDeadline.toString()));
        Integer type = null;
        if (specialApplication != null) {
            Integer status = specialApplication.getStatus();
            diagnoseVO.setOutConsult(specialApplication.getOutConsult());
            diagnoseVO.setStatus(status);
            diagnoseVO.setStatusName(PathologyStatus.getNameByCode(status));
            diagnoseVO.setResult(specialApplication.getResult());
            diagnoseVO.setSpecialApplyId(specialApplication.getId());
            diagnoseVO.setSpecialResult(specialApplication.getSpecialResult());
            diagnoseVO.setNumber(specialApplication.getNumber());
            diagnoseVO.setBingdongNote(specialApplication.getBingdongNote());
            List<IhcBlockDto> ihcBlocks = applicationIhcApplication.getIhcBlocksByNumber(specialApplication.getNumber());
            if (CollectionUtils.isNotEmpty(ihcBlocks)) {
                List<String> markers = new ArrayList<>();
                for (IhcBlockDto ihcBlockDto : ihcBlocks) {
                    if (ihcBlockDto != null) {
                        markers.addAll(ihcBlockDto.getIhcMarker());
                        diagnoseVO.setSpecialApplyTime(ihcBlockDto.getCreateTime());
                    }
                }
                diagnoseVO.setMarkers(markers);
            }

            type = specialApplication.getType();
            int ihcReportDeadLine = Integer.parseInt(paramSettingApplication.getContentByKey(SystemKey.IhcReportDeadline.toString()));
            int freezeReportDeadLine = Integer.parseInt(paramSettingApplication.getContentByKey(SystemKey.FreezeReportDeadline.toString()));
            int specialDyeReportDeadLine = Integer.parseInt(paramSettingApplication.getContentByKey(SystemKey.SpecialDyeReportDeadline.toString()));
            int consultReportDeadLine = Integer.parseInt(paramSettingApplication.getContentByKey(SystemKey.ConsultReportDeadline.toString()));

            if (type.equals(SpecialApplyType.Frozen.toCode())) {
                deadLine = freezeReportDeadLine;
            } else if (type.equals(SpecialApplyType.IHC.toCode())) {
                deadLine = ihcReportDeadLine;
            } else if (type.equals(SpecialApplyType.Dye.toCode())) {
                deadLine = specialDyeReportDeadLine;
            } else if (type.equals(SpecialApplyType.Consult.toCode())) {
                deadLine = consultReportDeadLine;
            }
            diagnoseVO.setType(type);
        } else {
            diagnoseVO.setType(SpecialApplyType.Normal.toCode());
            deadLine = reportDeadLine;
        }
        Integer label = pathologyDto.getLabel();
        Boolean decalcify = pathologyApplication.decalcify(pathId);
        if (label == null || label != 1) {
            difficultDeadline = 0;
        }
        if (!decalcify) {
            decalcifyDeadline = 0;
        }
        Integer max = deadLine > difficultDeadline ? deadLine : difficultDeadline;
        deadLine = max > decalcifyDeadline ? max : decalcifyDeadline;

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Calendar calendar = new GregorianCalendar();
        String format;
        if (specialApplication != null) {
            format = sdf.format(specialApplication.getCreateTime());
        } else {
            format = sdf.format(pathologyDto.getCreateTime());
        }
        calendar.setTime((sdf.parse(format)));
        //把创建日期往后增加报告期限的天数.整数往后推,负数往前移动
        if (deadLine != null) {
            calendar.add(Calendar.DATE, deadLine);
            Date estimateReport = calendar.getTime();
            diagnoseVO.setEstimateReportTime(estimateReport);
        }

        CollectCondition collectCondition = new CollectCondition();
        collectCondition.setCreateBy(userId);
        if (specialApplication != null) {
            //特殊申请
            collectCondition.setCategory(2);
            collectCondition.setTargetId(specialApplication.getId());
        } else {
            collectCondition.setCategory(1);
            collectCondition.setTargetId(pathId);
        }
        CollectDto collectDto = collectApplication.getCollectByCondition(collectCondition);
        if (collectDto != null) {
            diagnoseVO.setCollect(true);
        } else {
            diagnoseVO.setCollect(false);
        }
        diagnoseVO.setNote(note);
        diagnoseVO.setOutConsultResult(outConsultResult);

        SpecialApplicationDto specialApplicationDto = specialApplicationApplication.getByPathNoAndType(pathNo, SpecialApplyType.IHC.toCode());
        if (specialApplicationDto != null && specialApplicationDto.getStatus() >= PathologyStatus.Report.toCode()) {
            diagnoseVO.setCanApplyIhc(false);
        } else {
            diagnoseVO.setIhcId(applicationIhcApplication.getApplicationIhcByPathNoAndType(pathNo, SpecialApplyType.IHC.toCode()).getId());
            diagnoseVO.setCanApplyIhc(true);
        }
        specialApplicationDto = specialApplicationApplication.getByPathNoAndType(pathNo, SpecialApplyType.Dye.toCode());
        if (specialApplicationDto != null && specialApplicationDto.getStatus() >= PathologyStatus.Report.toCode()) {
            diagnoseVO.setCanApplySpecialDye(false);
        } else {
            diagnoseVO.setCanApplySpecialDye(true);
            diagnoseVO.setSpecialDyeId(applicationIhcApplication.getApplicationIhcByPathNoAndType(pathNo, SpecialApplyType.Dye.toCode()).getId());
        }

        //冻后常规流程或冰冻流程
        Boolean afterFrozen = pathologyDto.getAfterFrozen();
        if (Boolean.TRUE.equals(afterFrozen) || (Boolean.TRUE.equals(special) && SpecialApplyType.Frozen.toCode().equals(type))) {
            List<String> frozenNumbers = specialApplicationApplication.getFrozenNumbersByPathNo(pathNo);
            if(CollectionUtils.isNotEmpty(frozenNumbers)){
                List<FrozenGrossingVO> frozenGrossingVOData = new ArrayList<>();
                FrozenGrossingVO frozenGrossingVO;
                for(String frozenNumber : frozenNumbers){
                    SpecialApplicationDto applicationDto = specialApplicationApplication.getByNumber(frozenNumber);
                    String bingdongNote = applicationDto.getBingdongNote();
                    List<String> images = grossingApplication.getGrossingFile(pathId, TrackingOperation.frozenGrossing.toCode(), frozenNumber).getImages();
                    frozenGrossingVO = new FrozenGrossingVO();
                    frozenGrossingVO.setFrozenNumber(frozenNumber);
                    frozenGrossingVO.setBingdongNote(bingdongNote);
                    frozenGrossingVO.setImages(images);
                    frozenGrossingVOData.add(frozenGrossingVO);
                }
                diagnoseVO.setFrozenGrossingData(frozenGrossingVOData);
            }
        }

        return new ResponseVO(diagnoseVO);
    }

    @Override
    public ResponseVO getMicroFiles(long pathId, Long specialId) throws BuzException {
        List<PathologyFileDto> files = pathologyFileApplication.getFileByPathologyId(pathId, TrackingOperation.firstDiagnose.toCode(), null);
        List<MicroFileVO> microFiles = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(files)) {
            for (PathologyFileDto file : files) {
                MicroFileVO data = new MicroFileVO();
                Long fileId = file.getId();
                data.setId(fileId);
                data.setKeepFlag(file.getKeepFlag());
                data.setPathologyId(pathId);
                data.setType(file.getType());
                data.setUrl("api" + File.separator + "static" + File.separator + file.getType() + File.separator + file.getContent());
                data.setTag(file.getTag());
                FileMapping fileMapping = fileMappingApplication.selectByUnique(pathId, specialId, fileId);
                if (fileMapping == null) {
                    data.setCheck(false);
                } else {
                    data.setCheck(true);
                }
                microFiles.add(data);
            }
        }
        return new ResponseVO(microFiles);
    }

    @Override
    public ResponseVO score(long slideId, BlockScoreVO scoreVO) throws BuzException {
        BlockDto blockDto = blockApplication.getBlockById(slideId);
        if (blockDto == null) {
            throw new BuzException(BuzExceptionCode.RecordNotExists);
        }
        if (scoreVO == null) {
            throw new BuzException(BuzExceptionCode.ErrorParam);
        }
        if (!authCheck(blockDto.getPathId())) {
            throw new BuzException(BuzExceptionCode.AccessDenied);
        }
        Long blockId = blockDto.getParentId();
        BlockScoreDto blockScoreDto = new BlockScoreDto();
        blockScoreDto.setBlockId(slideId);
        blockScoreDto.setParentId(blockId);
        blockScoreDto.setAverage(scoreVO.getAverage());
        blockScoreDto.setGrossing(scoreVO.getGrossing());
        blockScoreDto.setDehydrate(scoreVO.getDehydrate());
        blockScoreDto.setEmbedding(scoreVO.getEmbedding());
        blockScoreDto.setSealing(scoreVO.getSealing());
        blockScoreDto.setSectioning(scoreVO.getSectioning());
        blockScoreDto.setStaining(scoreVO.getStaining());
        blockScoreDto.setNote(scoreVO.getNote());
        blockScoreDto.setType(BlockScoreType.Diagnose.toCode());
        blockScoreDto.setUpdateBy(UserContext.getLoginUserID());
        blockScoreApplication.updateBlockScore(blockScoreDto);
        return new ResponseVO();
    }

    @Override
    public ResponseVO blockReGrossing(Long pathologyId, String note) throws BuzException {
        if (!authCheck(pathologyId)) {
            throw new BuzException(BuzExceptionCode.AccessDenied);
        }
        blockApplication.blockReGrossing(pathologyId, note);
        return new ResponseVO();
    }

    @Override
    public ResponseVO deep(Long blockId, String note) throws BuzException {
        if (blockId == null) {
            throw new BuzException(BuzExceptionCode.ErrorParam);
        }
        BlockDto blockDto = blockApplication.getBlockById(blockId);
        if (blockDto == null || blockDto.getId() == null) {
            throw new BuzException(BuzExceptionCode.RecordNotExists);
        }
        if (!authCheck(blockDto.getPathId())) {
            throw new BuzException(BuzExceptionCode.AccessDenied);
        }
        blockApplication.deepSection(blockId, note);
        return new ResponseVO();
    }

    @Override
    public ResponseVO diagnose(long pathId, DiagnoseResultVO diagnoseResultVO) throws BuzException {
        if (diagnoseResultVO == null) {
            throw new BuzException(BuzExceptionCode.ErrorParam);
        }
        Long assignDiagnose = diagnoseResultVO.getAssignDiagnose();
        if (assignDiagnose == null) {
            throw new BuzException(BuzExceptionCode.ErrorParam);
        }
        PathologyDto pathologyDto = pathologyApplication.getSimplePathById(pathId);
        if (pathologyDto == null || pathologyDto.getId() == null) {
            throw new BuzException(BuzExceptionCode.RecordNotExists);
        }
        if (!authCheck(pathId)) {
            throw new BuzException(BuzExceptionCode.AccessDenied);
        }
        int status = pathologyDto.getStatus();

        //病理状态不是待一级诊断/待二级诊断
        if (!PathologyStatus.PrepareFirstDiagnose.toCode().equals(status) && !PathologyStatus.PrepareSecondDiagnose.toCode().equals(status)) {
            throw new BuzException(BuzExceptionCode.StatusNotMatch);
        }

        /*
        只能提交给上级
         */
        List<Integer> assignPermission = userApplication.getUserPermissionsCode(assignDiagnose);
        List<Integer> permissionsCode = UserContext.getLoginUserPermissions();
        //三级诊断权限不可以提交上级
        if (permissionsCode.contains(Permission.ThirdDiagnose.toCode())) {
            throw new BuzException(BuzExceptionCode.CanNotOperate);
        }
        //诊断的病理状态为二级诊断
        if (PathologyStatus.PrepareSecondDiagnose.toCode().equals(status)) {
            //用户必须有二级诊断权限
            if (!permissionsCode.contains(Permission.SecondDiagnose.toCode())) {
                throw new BuzException(BuzExceptionCode.AccessDenied);
            }
            //指定的用户必须是有三级诊断权限
            if (!assignPermission.contains(Permission.ThirdDiagnose.toCode())) {
                throw new BuzException(BuzExceptionCode.CanNotOperate);
            }
        }
        //诊断的病理状态为一级诊断
        if (PathologyStatus.PrepareFirstDiagnose.toCode().equals(status)) {
            //当前用户有二级诊断权限
            if (permissionsCode.contains(Permission.SecondDiagnose.toCode())) {
                //指定的用户必须是有三级诊断权限
                if (!assignPermission.contains(Permission.ThirdDiagnose.toCode())) {
                    throw new BuzException(BuzExceptionCode.CanNotOperate);
                }
            } else {
                //当前用户只有一级诊断权限
                //指定的用户必须是有二级或三级诊断权限
                if (!assignPermission.contains(Permission.ThirdDiagnose.toCode()) && !assignPermission.contains(Permission.SecondDiagnose.toCode())) {
                    throw new BuzException(BuzExceptionCode.CanNotOperate);
                }
            }
        }

        DiagnoseDto diagnoseDto = new DiagnoseDto();
        diagnoseDto.setId(pathId);
        diagnoseDto.setSerialNumber(pathologyDto.getSerialNumber());
        diagnoseDto.setResult(diagnoseResultVO.getResultDom());
        diagnoseDto.setMicroDiagnose(diagnoseResultVO.getMicroDiagnose());
        diagnoseDto.setDiagnose(diagnoseResultVO.getDiagnose());
        diagnoseDto.setAssignDiagnose(assignDiagnose);
        diagnoseDto.setNote(diagnoseResultVO.getNote());
        blockApplication.diagnose(diagnoseDto);
        return new ResponseVO();
    }

    @Override
    public ResponseVO getArchiveBlockInfo(long pathId) throws BuzException {
        List<BlockDto> blockDtoList = blockApplication.getAllBlocksByPathId(pathId);
        List<BlockDto> result = null;
        if (CollectionUtils.isNotEmpty(blockDtoList)) {
            result = new ArrayList<>();
            for (BlockDto blockDto : blockDtoList) {
                if (blockDto.getStatus().equals(PathologyStatus.PrepareArchiving.toCode())) {
                    result.add(blockDto);
                }
            }
        }
        return new ResponseVO(result);
    }

    @Override
    public ResponseVO getDyeBlockInfo(long pathId) throws BuzException {
        List<BlockDto> blocks = blockApplication.getNormalBlockByPathId(pathId);
        return new ResponseVO(blocks);
    }

    @Override
    public ResponseVO getInfo(@DefaultValue("1") Integer page, @DefaultValue("10") Integer length, Integer order, String sort, Integer status,
                              String filter, Boolean outConsult, Integer departments, Long diagnoseDoctor, Long reportDoctor, Integer specialType, Long createTimeStart, Long createTimeEnd) throws BuzException {

        if (page < 0 || length < 1 || createTimeStart == null) {
            throw new BuzException(BuzExceptionCode.ErrorParam);
        }
        PathologyCondition con = new PathologyCondition();
        con.setSize(length);
        con.setStart((page - 1) * length);
        con.setOutConsult(outConsult);
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

        con.setStatus(status);

        con.setCreateTimeStart(new Date(createTimeStart));
        if (createTimeEnd != null) {
            con.setCreateTimeEnd(new Date(createTimeEnd));
        }
        con.setDiagnoseDoctor(diagnoseDoctor);
        con.setReportDoctor(reportDoctor);
        con.setDepartments(departments);
        con.setSpecialType(specialType);
        if (sort == null) {
            sort = " ASC";
        }
        con.setOrder(DiagnoseSortEnum.valueOf(order).toString() + " " + sort);
        Long total = pathologyApplication.countDiagnosePathologiesByCondition(con);
        return new PageDataVO(page, length, total, pathologyApplication.getDiagnosePathologiesByCondition(con));
    }

    @Override
    public ResponseVO getDiagnoseHistory(long pathId) throws BuzException {
        PathologyDto pathologyDto = pathologyApplication.getSimplePathById(pathId);
        if (pathologyDto == null || pathologyDto.getId() == null) {
            throw new BuzException(BuzExceptionCode.RecordNotExists);
        }
        PathTrackingCondition condition = new PathTrackingCondition();
        condition.setPathId(pathId);
        List<Integer> operations = new ArrayList<>();
        operations.add(TrackingOperation.firstDiagnose.toCode());
        operations.add(TrackingOperation.secondDiagnose.toCode());
        operations.add(TrackingOperation.thirdDiagnose.toCode());
        operations.add(TrackingOperation.consult.toCode());
        operations.add(TrackingOperation.report.toCode());
        condition.setOperations(operations);
        List<PathTrackingDto> pathTrackingDtoList = pathTrackingApplication.getPathTrackingByCondition(condition);
        List<DiagnoseHistoryVO> diagnoseHistoryVOs = new ArrayList<>();
        DiagnoseHistoryVO diagnoseHistoryVO;
        for (PathTrackingDto dto : pathTrackingDtoList) {
            diagnoseHistoryVO = new DiagnoseHistoryVO();
            UserSimpleVO userSimpleVO = new UserSimpleVO();
            BeanUtils.copyProperties(userApplication.getUserSimpleInfoById(dto.getOperatorId()), userSimpleVO);
            diagnoseHistoryVO.setOperator(userSimpleVO);
            int operation = dto.getOperation();
            diagnoseHistoryVO.setOperation(operation);
            diagnoseHistoryVO.setOperationDesc(TrackingOperation.valueOf(operation).toString());
            diagnoseHistoryVO.setOperationTime(dto.getOperationTime());
            diagnoseHistoryVO.setDiagnoseResult(dto.getNote());
            diagnoseHistoryVOs.add(diagnoseHistoryVO);
        }
        return new ResponseVO(diagnoseHistoryVOs);
    }

    @Override
    public ResponseVO countDeadLine(Long delayTime) throws BuzException {
        long userId = UserContext.getLoginUserID();
        PathologyCondition condition = new PathologyCondition();
        condition.setDelay(true);
        condition.setStatusList(getLoginUserAuthStatus());
        condition.setReportDeadline(Integer.parseInt(paramSettingApplication.getContentByKey(SystemKey.ReportDeadline.toString())));
        condition.setIhcReportDeadline(Integer.parseInt(paramSettingApplication.getContentByKey(SystemKey.IhcReportDeadline.toString())));
        condition.setFreezeReportDeadline(Integer.parseInt(paramSettingApplication.getContentByKey(SystemKey.FreezeReportDeadline.toString())));
        condition.setSpecialDyeReportDeadline(Integer.parseInt(paramSettingApplication.getContentByKey(SystemKey.SpecialDyeReportDeadline.toString())));
        condition.setConsultReportDeadline(Integer.parseInt(paramSettingApplication.getContentByKey(SystemKey.ConsultReportDeadline.toString())));
        if (delayTime != null) {
            condition.setEstimateReportTime(new Date(delayTime));
            condition.setDelay(false);
        }
        condition.setAssignDiagnose(userId);
        return new ResponseVO(pathologyApplication.countPrepareDiagnose(condition));
    }

    @Override
    public ResponseVO getReportInfo(long pathId, Long specialApplyId) throws BuzException {

        PathologyDto pathologyDto = pathologyApplication.getSimplePathById(pathId);
        if (pathologyDto == null || pathologyDto.getId() == null) {
            throw new BuzException(BuzExceptionCode.RecordNotExists);
        }

        if (specialApplyId != null) {
            SpecialApplicationDto specialApplicationDto = specialApplicationApplication.getById(specialApplyId);
            if (specialApplicationDto == null) {
                throw new BuzException(BuzExceptionCode.RecordNotExists);
            }
            Integer status = specialApplicationDto.getStatus();
            if ((!status.equals(PathologyStatus.PrepareFirstDiagnose.toCode()) &&
                    !status.equals(PathologyStatus.PrepareReport.toCode()) &&
                    !status.equals(PathologyStatus.Report.toCode()))) {
                throw new BuzException(BuzExceptionCode.StatusNotMatch);
            }
        } else {
            Integer status = pathologyDto.getStatus();
            if ((!status.equals(PathologyStatus.PrepareFirstDiagnose.toCode()) &&
                    !status.equals(PathologyStatus.PrepareSecondDiagnose.toCode()) &&
                    !status.equals(PathologyStatus.PrepareThirdDiagnose.toCode())) &&
                    !status.equals(PathologyStatus.PrepareConsult.toCode()) &&
                    !status.equals(PathologyStatus.PrepareReport.toCode()) &&
                    !status.equals(PathologyStatus.Report.toCode())) {
                throw new BuzException(BuzExceptionCode.StatusNotMatch);
            }
        }
        //todo 玻片查看检查 暂时注释
//        List<Long> slidesId = blockApplication.getSlidesIdByPathId(pathId);
//        if (!CollectionUtils.isEmpty(slidesId)) {
//            long watchedSlide = trackingApplication.countWatchedSlide(UserContext.getLoginUserID(), slidesId);
//            int slides = slidesId.size();
//            if (watchedSlide != slides && !status.equals(PathologyStatus.Report.toCode())) {
//                throw new BuzException(BuzExceptionCode.WatchedCountNotMatch);
//            }
//        }
        return new ResponseVO(pathologyApplication.getReportInfo(pathId, specialApplyId));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseVO report(long pathId, DiagnoseResultVO diagnoseResultVO) throws BuzException {
        if (diagnoseResultVO == null) {
            throw new BuzException(BuzExceptionCode.ErrorParam);
        }

        PathologyDto pathologyDto = pathologyApplication.getSimplePathById(pathId);
        if (pathologyDto == null || pathologyDto.getId() == null) {
            throw new BuzException(BuzExceptionCode.RecordNotExists);
        }

        long userId = UserContext.getLoginUserID();
        Date dbNow = commonRepository.getDBNow();
        String result = diagnoseResultVO.getResultDom();
        String diagnose = diagnoseResultVO.getDiagnose();
        String reportPic = diagnoseResultVO.getReportPic();
        String specialResult = diagnoseResultVO.getSpecialResult();
        String outConsultResult = diagnoseResultVO.getOutConsultResult();
        if (StringUtils.isBlank(reportPic)) {
            throw new BuzException(BuzExceptionCode.ErrorParam);
        }

        Long specialApplyId = diagnoseResultVO.getSpecialApplyId();
        SpecialApplicationDto specialApplicationDto = null;
        if (specialApplyId != null) { //特殊申请
            specialApplicationDto = specialApplicationApplication.getById(specialApplyId);
            if (specialApplicationDto == null) {
                throw new BuzException(BuzExceptionCode.RecordNotExists);
            }
            Integer type = specialApplicationDto.getType();

            if (!type.equals(SpecialApplyType.Consult.toCode()) && (StringUtils.isBlank(diagnose) || StringUtils.isBlank(result))) { //会诊可以不填写诊断结果
                throw new BuzException(BuzExceptionCode.ErrorParam);
            }

            if (!type.equals(SpecialApplyType.Frozen.toCode()) && StringUtils.isBlank(specialResult)) { //特殊申请除冰冻,都需要填写特殊结果
                throw new BuzException(BuzExceptionCode.ErrorParam);
            }
            Boolean outConsult = specialApplicationDto.getOutConsult();
            if (outConsult) {
                if (StringUtils.isBlank(outConsultResult)) {
                    throw new BuzException(BuzExceptionCode.ErrorParam);
                }
            }

            if (type.equals(SpecialApplyType.Frozen.toCode())) {
                Boolean again = diagnoseResultVO.getAgain();
                if (again != null && again) {
                    pathologyDto.setAfterFrozen(true);
                    pathologyDto.setStatus(PathologyStatus.PrepareGrossing.toCode());
                    pathologyApplication.updatePathology(pathologyDto);
                }
            }

        } else { //常规病理
            Boolean outConsult = pathologyDto.getOutConsult();
            if (outConsult) {
                if (StringUtils.isBlank(outConsultResult)) {
                    throw new BuzException(BuzExceptionCode.ErrorParam);
                }
                pathologyDto.setOutConsultResult(outConsultResult);
            } else {
                if (StringUtils.isBlank(diagnose) || StringUtils.isBlank(result)) {
                    throw new BuzException(BuzExceptionCode.ErrorParam);
                }
            }
        }

        Integer status = pathologyDto.getStatus();
        if (specialApplicationDto != null) {
            status = specialApplicationDto.getStatus();
        }
        if ((!status.equals(PathologyStatus.PrepareFirstDiagnose.toCode()) &&
                !status.equals(PathologyStatus.PrepareSecondDiagnose.toCode()) &&
                !status.equals(PathologyStatus.PrepareThirdDiagnose.toCode())) &&
                !status.equals(PathologyStatus.PrepareConsult.toCode()) &&
                !status.equals(PathologyStatus.PrepareReport.toCode())) {
            throw new BuzException(BuzExceptionCode.StatusNotMatch);
        }
        if (specialApplyId == null) { //常规流程

            pathologyDto.setStatus(PathologyStatus.Report.toCode());
            Integer operation = null;
            if (PathologyStatus.PrepareThirdDiagnose.toCode().equals(status)) {
                operation = TrackingOperation.thirdDiagnose.toCode();
            } else {
                List<Integer> permissionsCode = UserContext.getLoginUserPermissions();
                if (permissionsCode.contains(Permission.ThirdDiagnose.toCode())) {
                    operation = TrackingOperation.thirdDiagnose.toCode();
                } else if (permissionsCode.contains(Permission.SecondDiagnose.toCode())) {
                    operation = TrackingOperation.secondDiagnose.toCode();
                }
            }

            //记录诊断操作
            PathTrackingDto pathTracking = new PathTrackingDto();
            pathTracking.setCreateBy(userId);
            pathTracking.setPathId(pathologyDto.getId());
            pathTracking.setOperatorId(userId);
            pathTracking.setOperation(operation);
            pathTracking.setOperationTime(dbNow);
            pathTracking.setNote(result);
            pathTrackingApplication.addPathTracking(pathTracking);

            //记录报告操作
            PathTrackingDto pathTrackingDto = new PathTrackingDto();
            pathTrackingDto.setPathId(pathId);
            pathTrackingDto.setOperation(TrackingOperation.report.toCode());
            pathTrackingDto.setOperatorId(userId);
            pathTrackingDto.setOperationTime(dbNow);
            pathTrackingDto.setCreateBy(userId);
            pathTrackingDto.setNote(result);
            pathTrackingApplication.addPathTracking(pathTrackingDto);

            pathologyDto.setUpdateBy(userId);
            pathologyDto.setResult(result);
            pathologyDto.setDiagnose(diagnose);
            pathologyDto.setMicroDiagnose(diagnoseResultVO.getMicroDiagnose());
            pathologyDto.setReportPic(reportPic);
            pathologyDto.setOutConsultResult(outConsultResult);
            List<String> frozenNumber = specialApplicationApplication.getFrozenNumbersByPathNo(pathologyDto.getSerialNumber());
            if (CollectionUtils.isNotEmpty(frozenNumber)) {
                Integer coincidence = diagnoseResultVO.getCoincidence();
                if (coincidence == null) {
                    throw new BuzException(BuzExceptionCode.ErrorParam);
                }
                pathologyDto.setCoincidence(coincidence);
            }
            pathologyApplication.updatePathology(pathologyDto);
        } else { //特殊申请
            specialApplicationDto.setStatus(PathologyStatus.Report.toCode());
            specialApplicationDto.setUpdateBy(userId);
            specialApplicationDto.setResult(result);
            specialApplicationDto.setDiagnose(diagnose);
            specialApplicationDto.setSpecialResult(specialResult);
            specialApplicationDto.setReportPic(reportPic);
            specialApplicationDto.setOutConsultResult(outConsultResult);
            specialApplicationApplication.update(specialApplicationDto);
        }
        //更新玻片状态
        List<Long> slidesId = blockApplication.getSlidesIdByPathId(pathId);
        for (Long slideId : slidesId) {
            BlockDto blockDto = blockApplication.getBlockById(slideId);
            blockDto.setStatus(PathologyStatus.PrepareArchiving.toCode());
            blockDto.setUpdateBy(userId);
            blockApplication.updateBlock(blockDto);
        }
        return new ResponseVO();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseVO save(long pathId, DiagnoseResultVO diagnoseResultVO) throws BuzException {
        String resultDom = diagnoseResultVO.getResultDom();
        String diagnose = diagnoseResultVO.getDiagnose();
        String outConsultResult = diagnoseResultVO.getOutConsultResult();
        Long userId = UserContext.getLoginUserID();
        Long specialApplyId = diagnoseResultVO.getSpecialApplyId();
        Integer label = diagnoseResultVO.getLabel();

        PathologyDto pathologyDto = pathologyApplication.getSimplePathById(pathId);
        if (pathologyDto == null) {
            throw new BuzException(BuzExceptionCode.RecordNotExists);
        }
        if (specialApplyId == null) { //常规病理
            pathologyDto.setResult(resultDom);
            pathologyDto.setDiagnose(diagnose);
            pathologyDto.setOutConsultResult(outConsultResult);
            pathologyDto.setLabel(label);
            pathologyDto.setUpdateBy(userId);
            pathologyApplication.updatePathology(pathologyDto);
        } else {
            SpecialApplicationDto specialApplicationDto = specialApplicationApplication.getById(specialApplyId);
            if (specialApplicationDto == null) {
                throw new BuzException(BuzExceptionCode.ErrorParam);
            }
            specialApplicationDto.setResult(resultDom);
            specialApplicationDto.setDiagnose(diagnose);
            specialApplicationDto.setSpecialResult(diagnoseResultVO.getSpecialResult());
            specialApplicationDto.setOutConsultResult(outConsultResult);
            specialApplicationDto.setUpdateBy(userId);
            specialApplicationApplication.update(specialApplicationDto);
            if (label != null) {
                pathologyDto.setLabel(label);
                pathologyDto.setUpdateBy(userId);
                pathologyApplication.updatePathology(pathologyDto);
            }
        }
        return new ResponseVO();
    }

    @Override
    public ResponseVO getSpecialHistory(String pathNo) {
        SpecialApplicationCondition con = new SpecialApplicationCondition();
        con.setPathNo(pathNo);
        List<Integer> types = new ArrayList<>();
        types.add(SpecialApplyType.IHC.toCode());
        types.add(SpecialApplyType.Dye.toCode());
        con.setTypes(types);
        con.setStatus(PathologyStatus.Report.toCode());
        List<SpecialApplicationDto> specialApplicationDtoList = specialApplicationApplication.getListByCondition(con);
        List<SpecialHistoryVO> specialHistoryVOS = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(specialApplicationDtoList)) {
            SpecialHistoryVO specialHistoryVO;
            for (SpecialApplicationDto dto : specialApplicationDtoList) {
                if (dto != null) {
                    specialHistoryVO = new SpecialHistoryVO();
                    specialHistoryVO.setCreateTime(dto.getCreateTime());
                    specialHistoryVO.setSpecialResult(dto.getSpecialResult());
                    specialHistoryVOS.add(specialHistoryVO);
                }
            }
        }
        return new ResponseVO(specialHistoryVOS);
    }

    @Override
    public ResponseVO createCollect(CollectVO collectVO) throws BuzException {
        Long targetId = collectVO.getTargetId();
        Integer category = collectVO.getCategory();
        Integer permission = collectVO.getPermission();
        if (targetId == null || category == null || permission == null) {
            throw new BuzException(BuzExceptionCode.ErrorParam);
        }
        List<String> labels = collectVO.getLabels();
        if (CollectionUtils.isEmpty(labels)) {
            throw new BuzException(BuzExceptionCode.ErrorParam);
        }
        collectVO.setCreateBy(UserContext.getLoginUserID());
        collectVO.setUpdateBy(collectVO.getCreateBy());

        CollectCondition con = new CollectCondition();
        con.setTargetId(targetId);
        con.setCategory(category);
        con.setCreateBy(collectVO.getCreateBy());
        if (collectApplication.getCollectByCondition(con) != null) {
            throw new BuzException(BuzExceptionCode.CollectExists);
        }

        collectVO.setCreateTime(new Date());
        collectVO.setUpdateTime(collectVO.getCreateTime());
        CollectDto collectDto = new CollectDto();
        BeanUtils.copyProperties(collectVO, collectDto);
        collectApplication.createCollect(collectDto);

        return new ResponseVO();
    }

    @Override
    public ResponseVO getCollects(@DefaultValue("1") Integer page, @DefaultValue("10") Integer length,
                                  String filter, String label, Integer collect,
                                  Integer permission, Long startTime, Long endTime) throws BuzException {

        if (page < 0 || length < 1) {
            throw new BuzException(BuzExceptionCode.ErrorParam);
        }
        CollectCondition con = new CollectCondition();
        con.setStart((page - 1) * 10);
        con.setSize(length);

        if (filter != null) {
            con.setFilter(filter.trim());
        }
        con.setLabel(label);
        con.setPermission(permission);
        if (startTime != null) {
            con.setStartTime(new Date(startTime));
        }
        if (endTime != null) {
            con.setEndTime(new Date(endTime));
        }
        con.setCollect(collect);
        con.setCreateBy(UserContext.getLoginUserID());
        List<CollectDetailDto> data = collectApplication.getCollects(con);
        Long total = collectApplication.getCollectsTotal(con);


        return new PageDataVO(page, length, total, data);
    }

    @Override
    public ResponseVO deleteCollect(List<Long> ids) throws BuzException {
        if (CollectionUtils.isEmpty(ids)) {
            throw new BuzException(BuzExceptionCode.ErrorParam);
        }
        Long createById = UserContext.getLoginUserID();
        for (Long id : ids) {
            CollectDto dto = collectApplication.getCollectByIdAndCreateBy(id, createById);
            if (dto != null) {
                collectApplication.deleteCollectById(id);
            } else {
                throw new BuzException(BuzExceptionCode.ErrorParam);
            }

        }
        return new ResponseVO();
    }

    @Override
    public Response collectExport(String filter, String label, Integer collect, Integer permission, Long startTime, Long endTime) throws Exception {


        CollectCondition con = new CollectCondition();
        con.setStart(0);
        con.setSize(Integer.MAX_VALUE);

        if (filter != null) {
            con.setFilter(filter.trim());
        }
        con.setLabel(label);
        con.setPermission(permission);
        if (startTime != null) {
            con.setStartTime(new Date(startTime));
        }
        if (endTime != null) {
            con.setEndTime(new Date(endTime));
        }
        con.setCollect(collect);
        con.setCreateBy(UserContext.getLoginUserID());
        List<CollectDetailDto> data = collectApplication.getCollects(con);

        String[] rowsName = new String[]{"序号", "编号", "姓名", "性别", "年龄", "收藏标签", "查看权限", "收藏日期", "收藏备注"};

        String title = "病例收藏";
        List<Object[]> dataList = new ArrayList<Object[]>();
        Object[] objs = null;
        CollectDetailDto dto;

        for (int i = 0; i < data.size(); i++) {
            objs = new Object[rowsName.length];
            dto = data.get(i);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String date = sdf.format(dto.getCollectDate());
            objs[1] = dto.getSerialNumber();
            objs[2] = dto.getPatientName();
            objs[3] = dto.getSex() == 1 ? "男" : "女";
            objs[4] = dto.getAge();
            objs[5] = dto.getLabelsDesc();
            objs[6] = dto.getPermissionDesc();
            objs[7] = date;
            objs[8] = dto.getNote();
            dataList.add(objs);
        }

        ExportExcel ex = new ExportExcel(title, rowsName, dataList);

        String format = new SimpleDateFormat("yyMMddss").format(new Date());
        String fileName = "病例收藏" + format + ".xls";

        //解决中文文件名乱码
        Message message = PhaseInterceptorChain.getCurrentMessage();
        HttpServletRequest request = (HttpServletRequest) message.get(AbstractHTTPDestination.HTTP_REQUEST);
        String agent = request.getHeader("USER-AGENT").toLowerCase();
        if (agent != null && (agent.indexOf("msie") != -1 ||
                (agent.indexOf("rv") != -1 && agent.indexOf("firefox") == -1))) {
            fileName = URLEncoder.encode(fileName, "utf-8");
        } else {
            fileName = new String(fileName.getBytes("utf-8"), "iso8859-1");
        }

        File file = ex.export(UUID.randomUUID().toString() + ".xls");
        Response.ResponseBuilder response = Response.ok(file);
        response.header("Content-Disposition", "attachment;filename=\"" + fileName + "\"");
        response.header("Content-Type", "application/octet-stream");
        response.header("Content-Length", file.length());
        return response.build();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseVO outConsult(long id, OutConsultVO outConsultVO) throws BuzException {
        Boolean special = outConsultVO.getSpecial();
        String note = outConsultVO.getNote();
        if (special) { //特殊申请
            SpecialApplicationDto specialApplicationDto = specialApplicationApplication.getById(id);
            if (specialApplicationApplication == null) {
                throw new BuzException(BuzExceptionCode.RecordNotExists);
            }
            specialApplicationDto.setOutConsult(true);
            specialApplicationDto.setNote(note);
            specialApplicationApplication.update(specialApplicationDto);
        } else {
            PathologyDto pathologyDto = pathologyApplication.getSimplePathById(id);
            if (pathologyDto == null) {
                throw new BuzException(BuzExceptionCode.RecordNotExists);
            }
            pathologyDto.setOutConsult(true);
            pathologyDto.setNote(note);
            pathologyApplication.updatePathology(pathologyDto);
        }
        return new ResponseVO();
    }

    /**
     * 登录用户对指定病理号权限检查
     *
     * @param pathId
     * @return
     */
    private boolean authCheck(long pathId) {
        Boolean auth = true;
        PathologyDto pathologyDto = pathologyApplication.getSimplePathById(pathId);
        if (pathologyDto != null && pathologyDto.getId() != null) {
            Long assignDiagnose = pathologyDto.getAssignDiagnose();
            if (assignDiagnose != null && !assignDiagnose.equals(UserContext.getLoginUserID())) {
                auth = false;
            }
        }
        return auth;
    }

    private List<Integer> getLoginUserAuthStatus() {
        List<Integer> statusList = new ArrayList<>();
        List<Integer> permissionsCode = UserContext.getLoginUserPermissions();
        if (permissionsCode.contains(Permission.ThirdDiagnose.toCode())) {
            statusList.add(PathologyStatus.PrepareReport.toCode());
            statusList.add(PathologyStatus.PrepareFirstDiagnose.toCode());
            statusList.add(PathologyStatus.PrepareSecondDiagnose.toCode());
            statusList.add(PathologyStatus.PrepareThirdDiagnose.toCode());
        } else if (permissionsCode.contains(Permission.SecondDiagnose.toCode())) {
            statusList.add(PathologyStatus.PrepareReport.toCode());
            statusList.add(PathologyStatus.PrepareFirstDiagnose.toCode());
            statusList.add(PathologyStatus.PrepareSecondDiagnose.toCode());
        } else if (permissionsCode.contains(Permission.FirstDiagnose.toCode())) {
            statusList.add(PathologyStatus.PrepareFirstDiagnose.toCode());
        }
        return statusList;
    }
}
