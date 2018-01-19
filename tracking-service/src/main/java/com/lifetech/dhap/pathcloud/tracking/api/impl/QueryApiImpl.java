package com.lifetech.dhap.pathcloud.tracking.api.impl;

import com.google.gson.Gson;
import com.lifetech.dhap.pathcloud.application.application.ApplicationApplication;
import com.lifetech.dhap.pathcloud.application.application.ApplicationIhcApplication;
import com.lifetech.dhap.pathcloud.application.application.PathologyApplication;
import com.lifetech.dhap.pathcloud.application.application.SampleApplication;
import com.lifetech.dhap.pathcloud.application.application.condition.IhcBlockCondition;
import com.lifetech.dhap.pathcloud.application.application.condition.PathologyQueryCondition;
import com.lifetech.dhap.pathcloud.application.application.dto.IhcBlockDto;
import com.lifetech.dhap.pathcloud.application.application.dto.PathologyDto;
import com.lifetech.dhap.pathcloud.application.application.dto.PathologyQueryDto;
import com.lifetech.dhap.pathcloud.application.application.infrastructure.code.ApplyType;
import com.lifetech.dhap.pathcloud.common.constant.Config;
import com.lifetech.dhap.pathcloud.common.data.Identity;
import com.lifetech.dhap.pathcloud.common.data.PathologyStatus;
import com.lifetech.dhap.pathcloud.common.data.SpecialApplyType;
import com.lifetech.dhap.pathcloud.common.data.TaskType;
import com.lifetech.dhap.pathcloud.common.exception.BuzException;
import com.lifetech.dhap.pathcloud.common.exception.BuzExceptionCode;
import com.lifetech.dhap.pathcloud.common.response.PageDataVO;
import com.lifetech.dhap.pathcloud.common.response.ResponseVO;
import com.lifetech.dhap.pathcloud.common.utils.ExportExcel;
import com.lifetech.dhap.pathcloud.common.utils.FileHelper;
import com.lifetech.dhap.pathcloud.common.utils.StringUtil;
import com.lifetech.dhap.pathcloud.file.application.PathologyFileApplication;
import com.lifetech.dhap.pathcloud.file.application.dto.PathologyFileDto;
import com.lifetech.dhap.pathcloud.setting.application.ParamSettingApplication;
import com.lifetech.dhap.pathcloud.tracking.api.QueryApi;
import com.lifetech.dhap.pathcloud.tracking.api.vo.MicroFileVO;
import com.lifetech.dhap.pathcloud.tracking.api.vo.QueryConfirmVO;
import com.lifetech.dhap.pathcloud.tracking.api.vo.ReportSummaryVO;
import com.lifetech.dhap.pathcloud.tracking.api.vo.ResearchVO;
import com.lifetech.dhap.pathcloud.tracking.application.ArchiveApplication;
import com.lifetech.dhap.pathcloud.tracking.application.BlockApplication;
import com.lifetech.dhap.pathcloud.tracking.application.SpecialApplicationApplication;
import com.lifetech.dhap.pathcloud.tracking.application.TrackingApplication;
import com.lifetech.dhap.pathcloud.tracking.application.condition.DoctorAdviceCondition;
import com.lifetech.dhap.pathcloud.tracking.application.condition.SpecialApplicationCondition;
import com.lifetech.dhap.pathcloud.tracking.application.condition.TrackingCondition;
import com.lifetech.dhap.pathcloud.tracking.application.dto.*;
import com.lifetech.dhap.pathcloud.tracking.application.infrastructure.code.TrackingOperation;
import com.lifetech.dhap.pathcloud.tracking.infrastructure.code.AdviceSortByEnum;
import com.lifetech.dhap.pathcloud.tracking.infrastructure.code.CaseSortByEnum;
import com.lifetech.dhap.pathcloud.tracking.infrastructure.code.FileType;
import com.lifetech.dhap.pathcloud.tracking.infrastructure.code.ResearchSortByEnum;
import com.lifetech.dhap.pathcloud.user.api.vo.UserSimpleVO;
import com.lifetech.dhap.pathcloud.user.application.UserApplication;
import com.lifetech.dhap.pathcloud.user.application.dto.UserSimpleDto;
import net.sf.json.JSONArray;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang.StringUtils;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.PhaseInterceptorChain;
import org.apache.cxf.transport.http.AbstractHTTPDestination;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.core.Response;
import java.io.File;
import java.io.FileOutputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by LiuMei on 2017-02-06.
 */
@Component("queryApi")
public class QueryApiImpl implements QueryApi {

    private Gson gson = new Gson();

    @Autowired
    private PathologyApplication pathologyApplication;

    @Autowired
    private ApplicationApplication applicationApplication;

    @Autowired
    private ApplicationIhcApplication applicationIhcApplication;

    @Autowired
    private SampleApplication sampleApplication;

    @Autowired
    private TrackingApplication trackingApplication;

    @Autowired
    private BlockApplication blockApplication;

    @Autowired
    private PathologyFileApplication pathologyFileApplication;

    @Autowired
    private UserApplication userApplication;

    @Autowired
    private ParamSettingApplication paramSettingApplication;

    @Autowired
    private ArchiveApplication archiveApplication;

    @Autowired
    private SpecialApplicationApplication specialApplicationApplication;


    @Override
    public ResponseVO query(@DefaultValue("1") Integer page, @DefaultValue("10") Integer length,
                            String filter, String fieldType, String fieldContain, String fieldExclusive, Integer specialDye, Integer inspectionCategory, Integer pathStatus,
                            Integer departments, Long inspectionDoctor, Long diagnoseDoctor, Long receiveTimeStart, Long receiveTimeEnd, Long reportTimeStart,
                            Long reportTimeEnd, Integer order, String sort) throws BuzException {
        PathologyQueryCondition condition = new PathologyQueryCondition();
        if (page < 0 || length < 1 || (filter == null && receiveTimeStart == null && reportTimeStart == null)) {
            throw new BuzException(BuzExceptionCode.ErrorParam);
        }
        condition.setSize(length);
        condition.setStart((page - 1) * length);
        if (order != null) {
            condition.setOrder(CaseSortByEnum.valueOf(order).toString());
            if (sort != null) {
                condition.setOrder(condition.getOrder() + " " + sort);
            }
        }

        if (receiveTimeStart != null) {
            condition.setReceiveTimeStart(new Date(receiveTimeStart));
        }
        if (receiveTimeEnd != null) {
            condition.setReceiveTimeEnd(new Date(receiveTimeEnd));
        }
        if (reportTimeEnd != null) {
            condition.setReportTimeEnd(new Date(reportTimeEnd));
        }
        if (reportTimeStart != null) {
            condition.setReportTimeStart(new Date(reportTimeStart));
        }
        if (filter != null && !filter.equals("")) {
            condition.setFilter(StringUtil.escapeExprSpecialWord(filter));
        } else {
            condition.setFieldType(fieldType);
            if (fieldContain != null && !fieldContain.equals("")) {
                condition.setFieldContain(fieldContain);
            }
            if (fieldExclusive != null && !fieldExclusive.equals("")) {
                condition.setFieldExclusive(fieldExclusive);
            }
            condition.setSpecialDye(specialDye);
            condition.setInspectionCategory(inspectionCategory);
            if (pathStatus != null && pathStatus == PathologyStatus.Report.toCode()) {//已发报告
                condition.setPathStatus(pathStatus);
            } else if (pathStatus != null) {
                condition.setNoPathStatus(PathologyStatus.Report.toCode());//未发报告
            }

            condition.setDepartments(departments);
            condition.setInspectionDoctor(inspectionDoctor);
            condition.setDiagnoseDoctor(diagnoseDoctor);
        }
        condition.setApplyType(ApplyType.Clinic.toCode());
        return new PageDataVO(page, length, pathologyApplication.countPathologiesByCondition(condition),
                pathologyApplication.getPathologiesByCondition(condition));
    }

    @Override
    public ResponseVO researchQuery(@DefaultValue("1") Integer page,
                                    @DefaultValue("10") Integer length,
                                    String filter, Integer departments,
                                    Long applyTimeStart,
                                    Long applyTimeEnd, Integer order, String sort) throws BuzException {
        PathologyQueryCondition condition = new PathologyQueryCondition();
        condition.setSize(length);
        condition.setStart((page - 1) * length);
        if (filter != null && !filter.trim().equals("")) {
            condition.setFilter(StringUtil.escapeExprSpecialWord(filter));
        }
        condition.setDepartments(departments);
        condition.setApplyType(ApplyType.Research.toCode());
        if (applyTimeStart != null) {
            condition.setApplyTimeStart(new Date(applyTimeStart));
        }

        if (applyTimeEnd != null) {
            condition.setApplyTimeEnd(new Date(applyTimeEnd));
        }
        if (sort == null) {
            sort = "DESC";
        }
        condition.setOrder(ResearchSortByEnum.valueOf(order).toString() + " " + sort);
        List<PathologyQueryDto> pathologyQueryDtoList = pathologyApplication.getPathologiesByCondition(condition);
        List<ResearchVO> researchVOS = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(pathologyQueryDtoList)) {
            ResearchVO researchVO;
            for (PathologyQueryDto queryDto : pathologyQueryDtoList) {
                String research = queryDto.getResearch();
                if (research != null) {
                    researchVO = gson.fromJson(research, ResearchVO.class);
                    researchVO.setApplyTime(queryDto.getApplyTime());
                    researchVO.setPathNo(queryDto.getSerialNumber());
                    researchVO.setId(queryDto.getId());
                    researchVO.setStatus(queryDto.getStatus());
                    researchVO.setStatusDesc(queryDto.getStatusName());
                    researchVO.setIdentityDesc(Identity.getNameByCode(researchVO.getIdentity()));
                    researchVO.setTaskTypeDesc(TaskType.getNameByCode(researchVO.getTaskType()));
                    researchVOS.add(researchVO);
                }
            }
        }
        return new PageDataVO(page, length, pathologyApplication.countPathologiesByCondition(condition), researchVOS);
    }

    @Override
    public ResponseVO advice(@DefaultValue("1") Integer page, @DefaultValue("10") Integer length, String filter,
                             Integer applyType, Integer status, Long inspectionDoctor, Long applyTimeStart, Long applyTimeEnd,
                             Integer order, String sort) throws BuzException {
        DoctorAdviceCondition condition = new DoctorAdviceCondition();
        condition.setSize(length);
        condition.setStart((page - 1) * length);
        if (StringUtils.isNotBlank(filter)) {
            condition.setFilter(StringUtil.escapeExprSpecialWord(filter));
        }
        condition.setInspectionDoctor(inspectionDoctor);
        condition.setApplyType(applyType);
        condition.setStatus(status);
        if (applyTimeStart != null) {
            condition.setApplyTimeStart(new Date(applyTimeStart));
        }
        if (applyTimeEnd != null) {
            condition.setApplyTimeEnd(new Date(applyTimeEnd));
        }
        if (order != null) {
            condition.setOrder(AdviceSortByEnum.valueOf(order).toString());
            if (sort != null) {
                condition.setOrder(condition.getOrder() + " " + sort);
            }
        }
        List<DoctorAdviceDto> data = trackingApplication.getDoctorAdvice(condition);
        return new PageDataVO(page, length, trackingApplication.countDoctorAdvice(condition), data);
    }

    @Override
    public ResponseVO adviceOperationLog(Long applyId, Long blockId) throws BuzException {
        List<BlockTrackingDto> data = trackingApplication.getDoctorAdviceLog(applyId, blockId);

        for (BlockTrackingDto trackingDto : data) {
            trackingDto.setOperationName(TrackingOperation.getNameByCode(trackingDto.getOperation()));
        }
        return new ResponseVO(data);
    }

    @Override
    public ResponseVO getInspectDoctor() throws BuzException {
        return new ResponseVO(applicationApplication.getInspectDoctor());
    }

    @Override
    public ResponseVO getSampleBaseInfo(Long pathId) throws BuzException {
        PathologyDto pathologyDto = pathologyApplication.getSimplePathById(pathId);
        if (pathologyDto == null || pathologyDto.getId() == null) {
            throw new BuzException(BuzExceptionCode.RecordNotExists);
        }
        return new ResponseVO(sampleApplication.getSampleInfo(pathId));
    }

    @Override
    public ResponseVO getConfirmInfo(Long pathId) throws BuzException {
        PathologyDto pathologyDto = pathologyApplication.getSimplePathById(pathId);
        if (pathologyDto == null || pathologyDto.getId() == null) {
            throw new BuzException(BuzExceptionCode.RecordNotExists);
        }
        List<QueryConfirmVO> queryConfirmVOs = new ArrayList<>();
        List<TrackingDto> blockTracking = trackingApplication.getBlockTracking(pathId);
        for (TrackingDto trackingDto : blockTracking) {
            Long blockId = trackingDto.getBlockId();
            BlockDto blockDto = blockApplication.getBlockById(blockId);
            if (blockDto.getParentId() != null) {
                if (blockDto.getApplyId() == null) {
                    queryConfirmVOs.add(trackingDtoToVO(trackingDto));
                }
            } else {
                queryConfirmVOs.add(trackingDtoToVO(trackingDto));
            }
        }
        Collections.sort(queryConfirmVOs);
        return new ResponseVO(queryConfirmVOs);
    }

    @Override
    public ResponseVO getSpecialDyeInfo(long pathId, Integer specialDye) throws BuzException {
        PathologyDto pathologyDto = pathologyApplication.getSimplePathById(pathId);
        if (pathologyDto == null || pathologyDto.getId() == null) {
            throw new BuzException(BuzExceptionCode.RecordNotExists);
        }
        IhcBlockCondition condition = new IhcBlockCondition();
        condition.setPathId(pathId);
        condition.setSpecialDye(specialDye);
        return new ResponseVO(applicationIhcApplication.getIhcBlockByCondition(condition));
    }

    private QueryConfirmVO trackingDtoToVO(TrackingDto trackingDto) {
        QueryConfirmVO queryConfirmVO = new QueryConfirmVO();
        if (trackingDto != null) {
            long blockId = trackingDto.getBlockId();
            BlockDto blockDto = blockApplication.getBlockById(blockId);
            String subId;
            Long parentId = blockDto.getParentId();
            if (parentId == null) {
                subId = blockDto.getSubId();
            } else {
                subId = blockApplication.getBlockById(parentId).getSubId();
                queryConfirmVO.setSlideSubId(blockDto.getSubId());
            }
            BeanUtils.copyProperties(trackingDto, queryConfirmVO);

            Integer operation = trackingDto.getOperation();
            if (!TrackingOperation.errorEnd.toCode().equals(operation)) {
                if (TrackingOperation.grossing.toCode().equals(operation)) {
                    if (blockDto.getBiaoshi().equals(2)) {
                        queryConfirmVO.setWaitTime(blockApplication.getRegrossingWaitTime(blockId));
                    } else {
                        queryConfirmVO.setWaitTime((trackingDto.getOperationTime().getTime()
                                - pathologyApplication.getSimplePathById(blockDto.getPathId()).getCreateTime().getTime()) / 1000);
                    }
                } else if (TrackingOperation.dyeConfirm.toCode().equals(operation)) {
                    IhcBlockCondition con = new IhcBlockCondition();
                    con.setPathId(blockDto.getPathId());
                    con.setSpecialDye(trackingDto.getSpecialDye());
                    con.setOrder(" bi.id desc");
                    String markerS = queryConfirmVO.getMarker();
                    if (markerS != null) {
                        List<String> markerStr = JSONArray.fromObject(queryConfirmVO.getMarker());
                        String marker = "";
                        for (String mark : markerStr) {
                            marker += mark + ";";
                        }
                        queryConfirmVO.setMarker(marker.substring(0, marker.length() - 1));
                    }

                    List<IhcBlockDto> data = applicationIhcApplication.getIhcBlockByCondition(con);
                    for (IhcBlockDto ihcBlockDto : data) {
                        if (ihcBlockDto.getPathId().equals(blockDto.getPathId()) && ihcBlockDto.getCreateTime().before(trackingDto.getOperationTime())) {
                            queryConfirmVO.setWaitTime((trackingDto.getOperationTime().getTime() - ihcBlockDto.getCreateTime().getTime()) / 1000);
                            break;
                        }
                    }
                } else {
                    Integer preOperation = null;
                    TrackingCondition condition = new TrackingCondition();
                    condition.setBlockId(blockId);
                    if (TrackingOperation.section.toCode().equals(operation)) {
                        condition.setBlockId(parentId);
                    }
                    if (TrackingOperation.completionConfirm.toCode().equals(operation)) {
                        preOperation = TrackingOperation.section.toCode();
                    } else {
                        preOperation = paramSettingApplication.getPreOperationByOperation(operation);
                    }
                    condition.setOperation(preOperation);
                    condition.setOperationTime(trackingDto.getOperationTime());
                    queryConfirmVO.setWaitTime(trackingApplication.getBlockWaitTimeByOperationAndTime(condition));
                }
            }

            queryConfirmVO.setBlockSubId(subId);
            queryConfirmVO.setOperationDesc(trackingDto.getOperationName());
            UserSimpleDto userSimpleDto = userApplication.getUserSimpleInfoById(trackingDto.getOperatorId());
            UserSimpleVO userSimpleVO = new UserSimpleVO();
            BeanUtils.copyProperties(userSimpleDto, userSimpleVO);
            queryConfirmVO.setOperator(userSimpleVO);
        }
        return queryConfirmVO;
    }

    @Override
    public ResponseVO getSampleGrossingInfo(Long pathId, String number) throws BuzException {
        PathologyDto pathologyDto = pathologyApplication.getSimplePathById(pathId);
        if (pathologyDto == null || pathologyDto.getId() == null) {
            throw new BuzException(BuzExceptionCode.RecordNotExists);
        }

        List<GrossingInfoDto> grossingInfo;
        if (StringUtils.isBlank(number)) {
            grossingInfo = blockApplication.getGrossingInfo(pathId, null);
        } else {
            grossingInfo = blockApplication.getGrossingInfo(null, number);
        }

        List<PathologyFileDto> files = pathologyFileApplication.getFileByPathologyId(pathId, TrackingOperation.grossing.toCode(),null);
        List<MicroFileVO> microFiles = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(files)) {
            for (PathologyFileDto file : files) {
                MicroFileVO data = new MicroFileVO();
                data.setId(file.getId());
                data.setKeepFlag(file.getKeepFlag());
                data.setPathologyId(pathId);
                data.setType(file.getType());
                data.setUrl("api" + File.separator + "static" + File.separator + file.getType() + File.separator + file.getContent());
                microFiles.add(data);
            }
        }
        List<PathologyFileDto> fileDtoList = pathologyFileApplication.getConfirmFileByBlockIds(blockApplication.getNormalBlocksIdByPathId(pathId));
        List<String> urls = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(fileDtoList)) {
            for (PathologyFileDto file : fileDtoList) {
                urls.add("api" + File.separator + "static" + File.separator + file.getType() + File.separator + file.getContent());
            }
        }
        Map<String, Object> map = new HashedMap();
        map.put("grossingInfo", grossingInfo);
        map.put("microFiles", microFiles);
        map.put("grossingConfirmFile", urls);
        return new ResponseVO(map);
    }

    @Override
    public ResponseVO getSampleSpecialDye(Long pathId, Integer specialDye) throws BuzException {
        IhcBlockCondition con = new IhcBlockCondition();
        con.setPathId(pathId);
        con.setSpecialDye(specialDye);

        List<QueryConfirmVO> queryConfirmVOs = new ArrayList<>();
        List<IhcBlockDto> data = applicationIhcApplication.getIhcBlockByCondition(con);

        if (CollectionUtils.isNotEmpty(data)) {
            String number = data.get(0).getNumber();
            QueryConfirmVO vo;
            for (IhcBlockDto ihcBlockDto : data) {
                Long blockId = ihcBlockDto.getBlockId();

                TrackingCondition trackingCondition = new TrackingCondition();
                trackingCondition.setBlockId(blockId);
                trackingCondition.setOperation(TrackingOperation.specialDyeApply.toCode());
                trackingCondition.setInstrumentId(ihcBlockDto.getId());
                TrackingDto tracking = trackingApplication.getTrackingByCondition(trackingCondition);
                if (tracking != null) {
                    long applyId = tracking.getId();
                    vo = new QueryConfirmVO();
                    vo.setNumber(number);
                    vo.setBlockSubId(ihcBlockDto.getSubId());
                    vo.setOperation(TrackingOperation.specialDyeApply.toCode());
                    vo.setOperationDesc(TrackingOperation.specialDyeApply.toString());
                    vo.setNote(ihcBlockDto.getNote());
                    String marker = "";
                    for (String mark : ihcBlockDto.getIhcMarker()) {
                        marker += mark + ";";
                    }
                    vo.setMarker(marker.substring(0, marker.length() - 1));
                    vo.setOperationTime(ihcBlockDto.getCreateTime());
                    UserSimpleDto userSimpleDto = userApplication.getUserSimpleInfoById(ihcBlockDto.getCreateBy());
                    UserSimpleVO userSimpleVO = new UserSimpleVO();
                    BeanUtils.copyProperties(userSimpleDto, userSimpleVO);
                    vo.setOperator(userSimpleVO);
                    queryConfirmVOs.add(vo);
                    trackingCondition.setOperation(TrackingOperation.dyeConfirm.toCode());
                    trackingCondition.setSecOperatorId(ihcBlockDto.getId());
                    tracking = trackingApplication.getTrackingByCondition(trackingCondition);
                    if (tracking != null) {
                        vo = new QueryConfirmVO();
                        vo.setNumber(number);
                        vo.setBlockSubId(ihcBlockDto.getSubId());
                        vo.setOperation(TrackingOperation.dyeConfirm.toCode());
                        vo.setOperationDesc(TrackingOperation.dyeConfirm.toString());
                        vo.setNote(ihcBlockDto.getNote());
                        vo.setMarker(marker.substring(0, marker.length() - 1));
                        vo.setOperationTime(tracking.getOperationTime());
                        UserSimpleDto user = userApplication.getUserSimpleInfoById(tracking.getOperatorId());
                        UserSimpleVO userVO = new UserSimpleVO();
                        BeanUtils.copyProperties(user, userVO);
                        vo.setOperator(userVO);
                        queryConfirmVOs.add(vo);

                        List<BlockTrackingDto> blockTracking = trackingApplication.getIhcBlockTracking(pathId, specialDye, applyId);
                        if (CollectionUtils.isNotEmpty(blockTracking)) {
                            UserSimpleVO userSimple;
                            for (BlockTrackingDto trackingDto : blockTracking) {
                                vo = new QueryConfirmVO();
                                vo.setMarker(trackingDto.getMarker());
                                userSimple = new UserSimpleVO();
                                userSimple.setId(trackingDto.getOperatorId());
                                userSimple.setFirstName(trackingDto.getOperatorName());
                                vo.setOperator(userSimple);
                                vo.setOperationTime(trackingDto.getOperationTime());
                                vo.setOperation(trackingDto.getOperation());
                                vo.setOperationDesc(trackingDto.getOperationName());
                                vo.setNote(trackingDto.getNote());
                                vo.setNoteType(trackingDto.getNoteType());
                                vo.setBlockSubId(trackingDto.getBlockSubId());
                                vo.setSlideSubId(trackingDto.getSlideSubId());
                                vo.setNumber(number);
                                queryConfirmVOs.add(vo);
                            }
                        }
                    }
                }
            }
            QueryConfirmVO preTracking = null;
            QueryConfirmVO confirmVO = null;
            if (CollectionUtils.isNotEmpty(queryConfirmVOs)) {
                for (QueryConfirmVO queryConfirmVO : queryConfirmVOs) {
                    if (queryConfirmVO.getOperation().equals(TrackingOperation.dyeConfirm.toCode())) {
                        confirmVO = queryConfirmVO;
                    }
                    if (!TrackingOperation.specialDyeApply.toCode().equals(queryConfirmVO.getOperation())) {
                        if (queryConfirmVO.getOperation().equals(TrackingOperation.section.toCode()) && confirmVO != null) {
                            queryConfirmVO.setWaitTime((queryConfirmVO.getOperationTime().getTime() - confirmVO.getOperationTime().getTime()) / 1000);
                        } else if (preTracking != null) {
                            queryConfirmVO.setWaitTime((queryConfirmVO.getOperationTime().getTime() - preTracking.getOperationTime().getTime()) / 1000);
                        }
                    }
                    preTracking = queryConfirmVO;
                }
            }
        }

        return new ResponseVO(queryConfirmVOs);
    }

    @Override
    public Response queryExport(String filter, String fieldType, String fieldContain, String fieldExclusive, Integer specialDye, Integer inspectionCategory,
                                Integer pathStatus, Integer departments, Long inspectionDoctor, Long diagnoseDoctor, Long receiveTimeStart, Long receiveTimeEnd,
                                Long reportTimeStart, Long reportTimeEnd) throws Exception {
        ResponseVO responseVO = query(1, Integer.MAX_VALUE, filter, fieldType, fieldContain, fieldExclusive, specialDye, inspectionCategory,
                pathStatus, departments, inspectionDoctor, diagnoseDoctor, receiveTimeStart, receiveTimeEnd, reportTimeStart, reportTimeEnd, null, null);
        HashMap<String, Object> result = (HashMap<String, Object>) responseVO.getData();
        List<PathologyQueryDto> pathologyQueryDtos = (List<PathologyQueryDto>) result.get("data");
        if (pathologyQueryDtos == null || pathologyQueryDtos.size() <= 0) {
            throw new BuzException(BuzExceptionCode.RecordNotExists);
        }

        String title = "病例信息";
        String[] rowsName = new String[]{"序号", "病人ID", "病理号", "姓名", "性别", "年龄", "住院号", "送检科室", "送检医生", "送检医院", "接收日期", "报告日期", "状态"};
        List<Object[]> dataList = new ArrayList<>();
        List<File> reportFiles = new ArrayList<>();
        Object[] objs = null;
        PathologyQueryDto dto = null;
        for (int i = 0; i < pathologyQueryDtos.size(); i++) {
            dto = pathologyQueryDtos.get(i);
            objs = new Object[rowsName.length];
            objs[0] = i + 1;
            objs[1] = dto.getHisId();
            objs[2] = dto.getSerialNumber();
            objs[3] = dto.getPatientName();
            Integer sex = dto.getSex();
            if (sex == null) {
                objs[4] = "";
            } else if (sex.equals(1)) {
                objs[4] = "男";
            } else if (sex.equals(2)) {
                objs[4] = "女";
            } else if (sex.equals(0)) {
                objs[4] = "未知";
            }
            objs[5] = dto.getAge();
            objs[6] = dto.getAdmissionNo();
            objs[7] = paramSettingApplication.getDepartmentDescByCode(dto.getDepartments());
            objs[8] = dto.getInspectDoctor().getFirstName();
            objs[9] = dto.getInspectHospital();
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date receive = dto.getReceiveTime();
            if (receive != null) {
                String receiveDate = df.format(receive);
                objs[10] = receiveDate;
            } else {
                objs[10] = null;
            }
            Date report = dto.getReportTime();
            if (report != null) {
                String reportDate = df.format(report);
                objs[11] = reportDate;
            } else {
                objs[11] = null;
            }
            objs[12] = dto.getStatusName();
            dataList.add(objs);

            List<PathologyFileDto> pathologyFiles = pathologyFileApplication.getFileByPathologyId(dto.getId(), TrackingOperation.report.toCode(),null);
            if (pathologyFiles.size() > 0) {
                PathologyFileDto file = pathologyFiles.get(0);
                String path;
                if (file.getCreateTime().before(new Date(System.currentTimeMillis() - Config.rsyncMaxMinutes * 60000L))) {
                    if (Math.random() > 0.5) {
                        path = Config.nfsMntRw;
                    } else {
                        path = Config.nfsMntRo;
                    }
                } else {
                    path = Config.nfsMntRw;
                }
                File f = new File(path + FileType.PDF.toString() + File.separator + file.getContent());
                reportFiles.add(f);
            }
        }
        ExportExcel ex = new ExportExcel(title, rowsName, dataList);
        String formatDate = new SimpleDateFormat("yy-MM-dd").format(new Date());
        String excelFileName = "病例查询_" + formatDate + ".xls";
        File export = ex.export(excelFileName);
        String resultName = "";
        File file = null;
        if (reportFiles.size() > 0) {
            String zipFileName = "病例查询_" + formatDate + ".zip";
            reportFiles.add(export);
            FileHelper.zip(new FileOutputStream(System.getProperty("java.io.tmpdir") + File.separator + zipFileName), reportFiles);
            file = new File(System.getProperty("java.io.tmpdir") + File.separator + zipFileName);
            resultName = zipFileName;
        } else {
            file = export;
            resultName = excelFileName;
        }

        //解决中文文件名乱码
        Message message = PhaseInterceptorChain.getCurrentMessage();
        HttpServletRequest request = (HttpServletRequest) message.get(AbstractHTTPDestination.HTTP_REQUEST);
        String agent = request.getHeader("USER-AGENT").toLowerCase();
        if (agent != null && (agent.indexOf("msie") != -1 ||
                (agent.indexOf("rv") != -1 && agent.indexOf("firefox") == -1))) {
            resultName = URLEncoder.encode(resultName, "utf-8");
            resultName = resultName.replace("+", "%20");
        } else {
            resultName = new String(resultName.getBytes("utf-8"), "iso8859-1");
        }

        Response.ResponseBuilder response = Response.ok(file);
        response.header("Content-Disposition", "attachment;filename=\"" + resultName + "\"");
        response.header("Content-Type", "application/octet-stream");
        response.header("Content-Length", file.length());
        return response.build();

    }

    @Override
    public Response researchQueryExport(@DefaultValue("1") Integer page, @DefaultValue("1000") Integer length, String filter, Integer departments,
                                        Long applyTimeStart,
                                        Long applyTimeEnd) throws Exception {
        length = Integer.MAX_VALUE;
        ResponseVO responseVO = researchQuery(page, length, filter, departments, applyTimeStart, applyTimeEnd, null, null);
        HashMap<String, Object> result = (HashMap<String, Object>) responseVO.getData();
        List<ResearchVO> researchVOS = (List<ResearchVO>) result.get("data");
        if (CollectionUtils.isEmpty(researchVOS)) {
            throw new BuzException(BuzExceptionCode.RecordNotExists);
        }
        String title = "病例信息";
        String[] rowsName = new String[]{"序号", "病理号", "申请人", "联系电话", "申请者身份", "导师", "送检科室", "课题类型", "项目代码", "申请日期", "状态"};
        List<Object[]> dataList = new ArrayList<>();
        Object[] objs;
        ResearchVO dto;
        for (int i = 0; i < researchVOS.size(); i++) {
            dto = researchVOS.get(i);
            objs = new Object[rowsName.length];
            objs[0] = i + 1;
            objs[1] = dto.getPathNo();
            objs[2] = dto.getApplicant();
            objs[3] = dto.getPhone();
            objs[4] = dto.getIdentityDesc();
            objs[5] = dto.getTutor();
            objs[6] = dto.getDepartmentsDesc();
            objs[7] = dto.getTaskTypeDesc();
            objs[8] = dto.getFunds();
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date applyTime = dto.getApplyTime();
            if (applyTime != null) {
                String receiveDate = df.format(applyTime);
                objs[9] = receiveDate;
            } else {
                objs[9] = null;
            }
            objs[10] = dto.getStatusDesc();
            dataList.add(objs);
        }
        ExportExcel ex = new ExportExcel(title, rowsName, dataList);
        String formatDate = new SimpleDateFormat("yy-MM-dd").format(new Date());
        String resultName = "病例查询_" + formatDate + ".xls";
        File file = ex.export(resultName);

        //解决中文文件名乱码
        Message message = PhaseInterceptorChain.getCurrentMessage();
        HttpServletRequest request = (HttpServletRequest) message.get(AbstractHTTPDestination.HTTP_REQUEST);
        String agent = request.getHeader("USER-AGENT").toLowerCase();
        if (agent != null && (agent.indexOf("msie") != -1 ||
                (agent.indexOf("rv") != -1 && agent.indexOf("firefox") == -1))) {
            resultName = URLEncoder.encode(resultName, "utf-8");
            resultName = resultName.replace("+", "%20");
        } else {
            resultName = new String(resultName.getBytes("utf-8"), "iso8859-1");
        }

        Response.ResponseBuilder response = Response.ok(file);
        response.header("Content-Disposition", "attachment;filename=\"" + resultName + "\"");
        response.header("Content-Type", "application/octet-stream");
        response.header("Content-Length", file.length());
        return response.build();
    }

    @Override
    public ResponseVO getArchivingInfo(long pid) throws Exception {
        if (pid <= 0) {
            throw new BuzException(BuzExceptionCode.ErrorParam);
        }
        List<BlockArchiveDto> dtoList = archiveApplication.getArchivingInfo(pid);
        return new ResponseVO(dtoList);
    }

    @Override
    public ResponseVO getReportSummary(long pathId) throws BuzException {
        PathologyDto pathologyDto = pathologyApplication.getSimplePathById(pathId);
        if (pathologyDto == null) {
            throw new BuzException(BuzExceptionCode.RecordNotExists);
        }
        List<ReportSummaryVO> reportSummaryVOS = new ArrayList<>();
        Integer status = pathologyDto.getStatus();
        if (status >= PathologyStatus.Report.toCode()) {
            ReportSummaryVO reportSummaryVO = new ReportSummaryVO();
            reportSummaryVO.setPathId(pathId);
            reportSummaryVO.setType(SpecialApplyType.Normal.toCode());
            reportSummaryVO.setTypeDesc(SpecialApplyType.getNameByCode(SpecialApplyType.Normal.toCode()));
            reportSummaryVOS.add(reportSummaryVO);
        }
        SpecialApplicationCondition condition = new SpecialApplicationCondition();
        condition.setPathNo(pathologyDto.getSerialNumber());
        condition.setStatus(PathologyStatus.Report.toCode());
        List<SpecialApplicationDto> specialApplicationDtoList = specialApplicationApplication.getListByCondition(condition);
        if (CollectionUtils.isNotEmpty(specialApplicationDtoList)) {
            ReportSummaryVO reportSummaryVO;
            for (SpecialApplicationDto specialApplicationDto : specialApplicationDtoList) {
                reportSummaryVO = new ReportSummaryVO();
                reportSummaryVO.setPathId(pathId);
                Integer type = specialApplicationDto.getType();
                reportSummaryVO.setType(type);
                reportSummaryVO.setTypeDesc(SpecialApplyType.getNameByCode(type));
                reportSummaryVO.setSpecialApplyId(specialApplicationDto.getId());
                reportSummaryVOS.add(reportSummaryVO);
            }
        }
        return new ResponseVO(reportSummaryVOS);
    }

    @Override
    public ResponseVO getSpecialNumber(String pathNo) throws BuzException {
        if (StringUtils.isBlank(pathNo)) {
            throw new BuzException(BuzExceptionCode.ErrorParam);
        }
        return new ResponseVO(specialApplicationApplication.getFrozenNumbersByPathNo(pathNo));
    }
}
