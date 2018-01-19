package com.lifetech.dhap.pathcloud.tracking.api.impl;

import com.lifetech.dhap.pathcloud.application.application.PathologyApplication;
import com.lifetech.dhap.pathcloud.application.application.dto.PathologyDto;
import com.lifetech.dhap.pathcloud.common.domain.CommonRepository;
import com.lifetech.dhap.pathcloud.common.exception.BuzException;
import com.lifetech.dhap.pathcloud.common.exception.BuzExceptionCode;
import com.lifetech.dhap.pathcloud.common.response.PageDataVO;
import com.lifetech.dhap.pathcloud.common.response.ResponseVO;
import com.lifetech.dhap.pathcloud.common.utils.ListUtil;
import com.lifetech.dhap.pathcloud.common.utils.StringUtil;
import com.lifetech.dhap.pathcloud.security.UserContext;
import com.lifetech.dhap.pathcloud.tracking.api.ReportApi;
import com.lifetech.dhap.pathcloud.tracking.api.vo.ReportIdVO;
import com.lifetech.dhap.pathcloud.tracking.api.vo.ReportPicVO;
import com.lifetech.dhap.pathcloud.tracking.application.BlockApplication;
import com.lifetech.dhap.pathcloud.tracking.application.PathTrackingApplication;
import com.lifetech.dhap.pathcloud.tracking.application.SpecialApplicationApplication;
import com.lifetech.dhap.pathcloud.tracking.application.condition.ReportCondition;
import com.lifetech.dhap.pathcloud.tracking.application.condition.SpecialApplicationCondition;
import com.lifetech.dhap.pathcloud.tracking.application.dto.PathTrackingDto;
import com.lifetech.dhap.pathcloud.tracking.application.dto.ReportQueryDto;
import com.lifetech.dhap.pathcloud.tracking.application.dto.SignQueryDto;
import com.lifetech.dhap.pathcloud.tracking.application.dto.SpecialApplicationDto;
import com.lifetech.dhap.pathcloud.tracking.infrastructure.code.ReportSignSortEnum;
import com.lifetech.dhap.pathcloud.tracking.infrastructure.code.ReportSortEnum;
import com.lifetech.dhap.pathcloud.user.application.dto.UserDto;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.ws.rs.DefaultValue;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by LiuMei on 2017-01-19.
 */
@Component("reportApi")
public class ReportApiImpl implements ReportApi {

    @Autowired
    private PathologyApplication pathologyApplication;

    @Autowired
    private BlockApplication blockApplication;

    @Autowired
    private PathTrackingApplication pathTrackingApplication;

    @Autowired
    private CommonRepository commonRepository;

    @Autowired
    private SpecialApplicationApplication specialApplicationApplication;

    @Override
    public ResponseVO reportQuery(@DefaultValue("1") Integer page, @DefaultValue("10") Integer length,
                                  String filter, Long startTime, Long endTime, Integer departments,
                                  Boolean printStatus, Boolean delay, Integer order, String sort
    ) throws BuzException {
        if (page < 0 || length < 1) {
            throw new BuzException(BuzExceptionCode.ErrorParam);
        }
        ReportCondition con = new ReportCondition();
        con.setSize(length);
        con.setStart((page - 1) * length);

        if (startTime != null) {
            con.setStartTime(new Date(startTime));
        }

        if (endTime != null) {
            con.setEndTime(new Date(endTime));
        }
        if (filter != null && !"".equals(filter)) {
            con.setFilter(StringUtil.escapeExprSpecialWord(filter.trim()));
        }
        if (printStatus != null) {
            con.setPrintStatus(printStatus);
        }
        con.setDelay(delay);
        con.setDepartments(departments);
        if (sort == null) {
            sort = "DESC";
        }
        con.setOrder(ReportSortEnum.valueOf(order).toString() + " " + sort);
        List<ReportQueryDto> dtoList = pathTrackingApplication.reportQuery(con);
        Long total = pathTrackingApplication.reportQueryTotal(con);
        return new PageDataVO(page, length, total, dtoList);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseVO addTracking(int operation, List<ReportIdVO> reportIdVOS) throws BuzException {
        if (CollectionUtils.isEmpty(reportIdVOS)) {
            throw new BuzException(BuzExceptionCode.ErrorParam);
        }
        Date dbNow = commonRepository.getDBNow();
        long userId = UserContext.getLoginUserID();
        for (ReportIdVO reportIdVO : reportIdVOS) {
            Long pathId = reportIdVO.getPathId();
            Long specialApplyId = reportIdVO.getSpecialApplyId();
            if (pathId == null) {
                throw new BuzException(BuzExceptionCode.ErrorParam);
            }
            PathTrackingDto pathTrackingDto = new PathTrackingDto();
            pathTrackingDto.setPathId(pathId);
            pathTrackingDto.setSecOperatorId(specialApplyId);
            pathTrackingDto.setOperation(operation);
            pathTrackingDto.setOperatorId(userId);
            pathTrackingDto.setOperationTime(dbNow);
            pathTrackingDto.setCreateBy(userId);
            pathTrackingApplication.addPathTracking(pathTrackingDto);
        }
        return new ResponseVO();
    }

    @Override
    public ResponseVO printRecordQuery(long id, Long specialApplyId) throws BuzException {
        List<ReportQueryDto> dtoList = pathTrackingApplication.printRecordQuery(id, specialApplyId);
        return new ResponseVO(dtoList);
    }

    @Override
    public ResponseVO getReports(String pathIds, String specialApplyIds) throws BuzException {
        if (StringUtils.isBlank(pathIds) && StringUtils.isBlank(specialApplyIds)) {
            throw new BuzException(BuzExceptionCode.ErrorParam);
        }
        List<ReportPicVO> reportPicVOS = new ArrayList<>();
        if (StringUtils.isNotBlank(pathIds)) {
            String[] pathId = pathIds.split(",");
            if (!ArrayUtils.isEmpty(pathId)) {
                List<PathologyDto> reportPics = pathologyApplication.getReportPic(ListUtil.arrStringToLong(pathId));
                if (CollectionUtils.isNotEmpty(reportPics)) {
                    ReportPicVO reportPicVO;
                    for (PathologyDto pathologyDto : reportPics) {
                        reportPicVO = new ReportPicVO();
                        reportPicVO.setId(pathologyDto.getId());
                        reportPicVO.setReportPic(pathologyDto.getReportPic());
                        reportPicVOS.add(reportPicVO);
                    }
                }
            }
        }
        if (StringUtils.isNotBlank(specialApplyIds)) {
            String[] specialApply = specialApplyIds.split(",");
            if (!ArrayUtils.isEmpty(specialApply)) {
                SpecialApplicationCondition condition = new SpecialApplicationCondition();
                condition.setIds(ListUtil.arrStringToLong(specialApply));
                List<SpecialApplicationDto> listByCondition = specialApplicationApplication.getListByCondition(condition);
                if (!CollectionUtils.isEmpty(listByCondition)) {
                    ReportPicVO reportPicVO;
                    for (SpecialApplicationDto dto : listByCondition) {
                        reportPicVO = new ReportPicVO();
                        reportPicVO.setId(dto.getId());
                        reportPicVO.setReportPic(dto.getReportPic());
                        reportPicVO.setSpecial(true);
                        reportPicVOS.add(reportPicVO);
                    }
                }
            }
        }
        return new ResponseVO(reportPicVOS);
    }

    @Override
    public ResponseVO getPrintPerson() throws BuzException {
        List<UserDto> user = blockApplication.getPrintPerson();
        return new ResponseVO(user);
    }

    @Override
    public ResponseVO signQuery(@DefaultValue("1") Integer page, @DefaultValue("10") Integer length,
                                String filter, Long startTime, Long endTime, Integer operation, Integer departments,
                                Boolean delay, Integer order, String sort) throws BuzException {
        if (page < 0 || length < 1) {
            throw new BuzException(BuzExceptionCode.ErrorParam);
        }
        ReportCondition con = new ReportCondition();
        con.setSize(length);
        con.setStart((page - 1) * length);

        if (startTime != null) {
            con.setStartTime(new Date(startTime));
        }

        if (endTime != null) {
            con.setEndTime(new Date(endTime));
        }
        if (StringUtils.isNotBlank(filter)) {
            con.setFilter(StringUtil.escapeExprSpecialWord(filter.trim()));
        }
        con.setDelay(delay);
        con.setDepartments(departments);
        con.setOperation(operation);
        if (sort == null) {
            sort = " ASC";
        }
        con.setOrder(ReportSignSortEnum.valueOf(order).toString() + " " + sort);
        List<SignQueryDto> dtoList = pathTrackingApplication.signQuery(con);
        Long total = pathTrackingApplication.signQueryTotal(con);
        return new PageDataVO(page, length, total, dtoList);
    }

}
