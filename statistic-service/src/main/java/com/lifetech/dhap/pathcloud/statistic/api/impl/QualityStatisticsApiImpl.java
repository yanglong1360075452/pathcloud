package com.lifetech.dhap.pathcloud.statistic.api.impl;

import com.lifetech.dhap.pathcloud.common.constant.Config;
import com.lifetech.dhap.pathcloud.common.data.PathologyStatus;
import com.lifetech.dhap.pathcloud.common.data.SpecialDyeFix;
import com.lifetech.dhap.pathcloud.common.exception.BuzException;
import com.lifetech.dhap.pathcloud.common.exception.BuzExceptionCode;
import com.lifetech.dhap.pathcloud.common.response.PageDataVO;
import com.lifetech.dhap.pathcloud.common.response.ResponseVO;
import com.lifetech.dhap.pathcloud.common.utils.DateUtil;
import com.lifetech.dhap.pathcloud.common.utils.ExportExcel;
import com.lifetech.dhap.pathcloud.setting.application.ParamSettingApplication;
import com.lifetech.dhap.pathcloud.setting.application.infrastructure.code.SystemKey;
import com.lifetech.dhap.pathcloud.statistic.api.QualityStatisticsApi;
import com.lifetech.dhap.pathcloud.statistic.application.WorkloadStatisticApplication;
import com.lifetech.dhap.pathcloud.statistic.application.condition.CoincidenceCondition;
import com.lifetech.dhap.pathcloud.statistic.application.condition.QualityControlCon;
import com.lifetech.dhap.pathcloud.statistic.application.condition.QualityStatisticsSortByEnum;
import com.lifetech.dhap.pathcloud.statistic.application.condition.ReportCondition;
import com.lifetech.dhap.pathcloud.statistic.application.dto.QualityMonthDto;
import com.lifetech.dhap.pathcloud.statistic.application.dto.QualityPersonalDto;
import com.lifetech.dhap.pathcloud.tracking.application.infrastructure.code.TrackingOperation;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.PhaseInterceptorChain;
import org.apache.cxf.transport.http.AbstractHTTPDestination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.core.Response;
import java.io.File;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @author eric.guo@ostengar.com
 * @version 1.0
 * @time 2017-02-15-15:50
 */
@Component("qualityStatisticsApi")
public class QualityStatisticsApiImpl implements QualityStatisticsApi {

    @Autowired
    private WorkloadStatisticApplication workloadStatisticApplication;

    @Autowired
    private ParamSettingApplication paramSettingApplication;

    @Override
    public ResponseVO monthQualityControl(Integer year, Integer specialDye) throws BuzException {
        if (year == null || specialDye == null) {
            throw new BuzException(BuzExceptionCode.ErrorParam);
        }
        QualityControlCon con = new QualityControlCon();
        con.setSpecialDye(specialDye);
        con.setStartTime(DateUtil.dateFormat(year + "-01-01 00:00:00"));
        year++;
        con.setEndTime(DateUtil.dateFormat(year + "-01-01 00:00:00"));
        Integer score = paramSettingApplication.getQualifiedScoreByWorkstation(TrackingOperation.grossing.toCode());
        con.setGrossingScore(score == null ? Config.qualifiedScore : score);
        score = paramSettingApplication.getQualifiedScoreByWorkstation(TrackingOperation.dehydrate.toCode());
        con.setDehydrateScore(score == null ? Config.qualifiedScore : score);
        score = paramSettingApplication.getQualifiedScoreByWorkstation(TrackingOperation.embed.toCode());
        con.setEmbedScore(score == null ? Config.qualifiedScore : score);
        score = paramSettingApplication.getQualifiedScoreByWorkstation(TrackingOperation.section.toCode());
        con.setSectionScore(score == null ? Config.qualifiedScore : score);
        score = paramSettingApplication.getQualifiedScoreByWorkstation(TrackingOperation.dye.toCode());
        con.setDyeScore(score == null ? Config.qualifiedScore : score);
        score = paramSettingApplication.getQualifiedScoreByWorkstation(TrackingOperation.mounting.toCode());
        con.setMountingScore(score == null ? Config.qualifiedScore : score);
        return new ResponseVO(workloadStatisticApplication.monthQualityControl(con));
    }

    @Override
    public ResponseVO personQualityControl(@DefaultValue("1") Integer page, @DefaultValue("10") Integer length,
                                           Integer order, String sort, Integer year, Integer month, Integer station, Integer specialDye) throws BuzException {
        if (year == null || specialDye == null) {
            throw new BuzException(BuzExceptionCode.ErrorParam);
        }
        if (page < 0 || length < 0) {
            throw new BuzException(BuzExceptionCode.ErrorParam);
        }
        QualityControlCon con = new QualityControlCon();
        setDate(con,year,month);
        if (order != null) {
            con.setOrder(QualityStatisticsSortByEnum.valueOf(order).toString());
            if (sort != null) {
                con.setOrder(con.getOrder() + " " + sort);
            }
        }
        con.setSpecialDye(specialDye);
        con.setOperation(station);
        con.setSize(length);
        con.setStart((page - 1) * length);
        if (station != null) {
            Integer score = paramSettingApplication.getQualifiedScoreByWorkstation(station);
            score = score == null ? Config.qualifiedScore : score;
            if (station.equals(TrackingOperation.grossing.toCode())) {
                con.setGrossingScore(score);
            } else if (station.equals(TrackingOperation.dehydrate.toCode())) {
                con.setDehydrateScore(score);
            } else if (station.equals(TrackingOperation.embed.toCode())) {
                con.setEmbedScore(score);
            } else if (station.equals(TrackingOperation.section.toCode())) {
                con.setSectionScore(score);
            }
        } else {
            Integer score = paramSettingApplication.getQualifiedScoreByWorkstation(TrackingOperation.grossing.toCode());
            con.setGrossingScore(score == null ? Config.qualifiedScore : score);
            score = paramSettingApplication.getQualifiedScoreByWorkstation(TrackingOperation.dehydrate.toCode());
            con.setDehydrateScore(score == null ? Config.qualifiedScore : score);
            score = paramSettingApplication.getQualifiedScoreByWorkstation(TrackingOperation.embed.toCode());
            con.setEmbedScore(score == null ? Config.qualifiedScore : score);
            score = paramSettingApplication.getQualifiedScoreByWorkstation(TrackingOperation.section.toCode());
            con.setSectionScore(score == null ? Config.qualifiedScore : score);
        }
        List<QualityPersonalDto> data = workloadStatisticApplication.personalQualityControl(con);
        return new PageDataVO(page, length, workloadStatisticApplication.personalQualityControlTotal(con), data);
    }

    @Override
    public ResponseVO goodRate(Integer year) throws BuzException {
        if (year == null) {
            throw new BuzException(BuzExceptionCode.ErrorParam);
        }
        QualityControlCon con = new QualityControlCon();
        con.setStartTime(DateUtil.dateFormat(year + "-01-01 00:00:00"));
        year++;
        con.setEndTime(DateUtil.dateFormat(year + "-01-01 00:00:00"));
        Integer score = paramSettingApplication.getQualifiedScoreByWorkstation(TrackingOperation.section.toCode());
        con.setSectionScore(score == null ? Config.qualifiedScore : score);
        con.setSpecialDye(SpecialDyeFix.HE.toCode());
        return new ResponseVO(workloadStatisticApplication.goodQualityControl(con));
    }

    @Override
    public ResponseVO getCoincidence(Integer year) throws BuzException {
        if (year == null) {
            throw new BuzException(BuzExceptionCode.ErrorParam);
        }
        CoincidenceCondition con = new CoincidenceCondition();
        con.setStartTime(DateUtil.dateFormat(year + "-01-01 00:00:00"));
        year++;
        con.setEndTime(DateUtil.dateFormat(year + "-01-01 00:00:00"));
        return new ResponseVO(workloadStatisticApplication.coincidence(con));
    }

    @Override
    public ResponseVO report(Integer year) throws BuzException {
        if (year == null) {
            throw new BuzException(BuzExceptionCode.ErrorParam);
        }
        ReportCondition con = new ReportCondition();
        con.setStartTime(DateUtil.dateFormat(year + "-01-01 00:00:00"));
        year++;
        con.setEndTime(DateUtil.dateFormat(year + "-01-01 00:00:00"));
        con.setPathStatus(PathologyStatus.Report.toCode());
        con.setReportDeadline(Integer.parseInt(paramSettingApplication.getContentByKey(SystemKey.ReportDeadline.toString())));
        con.setIhcReportDeadline(Integer.parseInt(paramSettingApplication.getContentByKey(SystemKey.IhcReportDeadline.toString())));
        con.setFreezeReportDeadline(Integer.parseInt(paramSettingApplication.getContentByKey(SystemKey.FreezeReportDeadline.toString())));
        con.setSpecialDyeReportDeadline(Integer.parseInt(paramSettingApplication.getContentByKey(SystemKey.SpecialDyeReportDeadline.toString())));
        con.setConsultReportDeadline(Integer.parseInt(paramSettingApplication.getContentByKey(SystemKey.ConsultReportDeadline.toString())));
        con.setDecalcifyDeadline(Integer.parseInt(paramSettingApplication.getContentByKey(SystemKey.DecalcifyDeadline.toString())));
        con.setDifficultDeadline(Integer.parseInt(paramSettingApplication.getContentByKey(SystemKey.DifficultDeadline.toString())));
        return new ResponseVO(workloadStatisticApplication.reportDelay(con));
    }

    @Override
    public Response monthQualityControlExport(Integer year, Integer specialDye) throws Exception {

        if (year == null || specialDye == null) {
            throw new BuzException(BuzExceptionCode.ErrorParam);
        }
        QualityControlCon con = new QualityControlCon();
        con.setSpecialDye(specialDye);
        con.setStartTime(DateUtil.dateFormat(year + "-01-01 00:00:00"));
        year++;
        con.setEndTime(DateUtil.dateFormat(year + "-01-01 00:00:00"));
        List<QualityMonthDto> data = workloadStatisticApplication.monthQualityControl(con);

        String[] rowsName = new String[]{"月份", "蜡块总数", "重取数", "重取率", "重切数", "重切率", "不合格数", "不合格率"};
        String title = "质控评分—按月统计";
        List<Object[]> dataList = new ArrayList<Object[]>();
        Object[] objs;
        QualityMonthDto dto;

        DecimalFormat df = new DecimalFormat("0.00");

        for (int i = 0; i < data.size(); i++) {
            dto = data.get(i);
            objs = new Object[rowsName.length];
            objs[0] = dto.getPathologyMonth();
            objs[1] = dto.getBlockTotal();
            objs[2] = dto.getReGrossingTotal();
            if (dto.getBlockTotal() == 0) {
                objs[3] = df.format(0) + "%";
            } else {
                objs[3] = df.format(((double) dto.getReGrossingTotal() / (double) dto.getBlockTotal() * 100)) + "%";
            }

            objs[4] = dto.getReSectionTotal();
            if (dto.getBlockTotal() == 0) {
                objs[5] = df.format(0) + "%";
            } else {
                objs[5] = df.format(((double) dto.getReSectionTotal() / (double) dto.getBlockTotal() * 100)) + "%";
            }

            objs[6] = dto.getLowScoreTotal();
            if (dto.getSlideTotal() == 0) {
                objs[7] = df.format(0) + "%";
            } else {
                objs[7] = df.format(((double) dto.getLowScoreTotal() / (double) dto.getBlockTotal() * 100)) + "%";
            }
            dataList.add(objs);
        }

        ExportExcel ex = new ExportExcel(title, rowsName, dataList);

        String format = new SimpleDateFormat("yyMMddss").format(new Date());
        String fileName = "质控评分_按月统计" + format + ".xls";

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
    public Response personQualityControlExport(String order, Integer year, Integer month, Integer station, Integer specialDye) throws Exception {

        if (year == null || specialDye == null) {
            throw new BuzException(BuzExceptionCode.ErrorParam);
        }

        QualityControlCon con = new QualityControlCon();
        setDate(con,year,month);
        con.setOrder(order);
        con.setSpecialDye(specialDye);
        con.setOperation(station);
        con.setStart(0);
        con.setSize(Integer.MAX_VALUE);
        List<QualityPersonalDto> data = workloadStatisticApplication.personalQualityControl(con);

        String[] rowsName = new String[]{"序号", "用户名", "姓名", "工位", "蜡块数", "重取数", "重取率", "重切数", "重切率", "不合格数"
                , "不合格率", "质控评分"};

        String title = "质控评分—按人统计";
        List<Object[]> dataList = new ArrayList<Object[]>();
        Object[] objs;
        QualityPersonalDto dto;
        DecimalFormat df = new DecimalFormat("0.00");
        for (int i = 0; i < data.size(); i++) {
            dto = data.get(i);
            objs = new Object[rowsName.length];
            objs[1] = dto.getUserName();
            objs[2] = dto.getFirstName();
            objs[3] = dto.getOperationName();
            objs[4] = dto.getBlockTotal();
            objs[5] = dto.getReGrossingTotal();
            if (dto.getBlockTotal() == 0) {
                objs[6] = df.format(0) + "%";
            } else {
                objs[6] = df.format(((double) dto.getReGrossingTotal() / (double) dto.getBlockTotal() * 100)) + "%";
            }
            objs[7] = dto.getReSectionTotal();
            if (dto.getBlockTotal() == 0) {
                objs[8] = df.format(0) + "%";
            } else {
                objs[8] = df.format(((double) dto.getReSectionTotal() / (double) dto.getBlockTotal() * 100)) + "%";
            }
            objs[9] = dto.getLowScoreTotal();
            if (dto.getBlockTotal() == 0) {
                objs[10] = df.format(0) + "%";
            } else {
                objs[10] = df.format(((double) dto.getLowScoreTotal() / (double) dto.getBlockTotal() * 100)) + "%";
            }
            if (dto.getScoreTotal() == 0) {
                objs[11] = 0;
            } else {
                objs[11] = df.format((double) dto.getScore() / (double) dto.getScoreTotal());
            }

            dataList.add(objs);
        }

        ExportExcel ex = new ExportExcel(title, rowsName, dataList);

        String format = new SimpleDateFormat("yyMMddss").format(new Date());
        String fileName = "质控评分_按人统计" + format + ".xls";

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

    private void setDate(QualityControlCon con,Integer year, Integer month) {
        if (year != null) {
            if (month < 10) {
                con.setStartTime(DateUtil.dateFormat(year + "-0" + month + "-01 00:00:00"));
            } else {
                con.setStartTime(DateUtil.dateFormat(year + "-" + month + "-01 00:00:00"));
            }
            if (month == 12) {
                year++;
               con.setEndTime(DateUtil.dateFormat(year + "-01-01 00:00:00"));
            } else {
                month++;
                if (month < 10) {
                    con.setEndTime(DateUtil.dateFormat(year + "-0" + month + "-01 00:00:00"));
                } else {
                    con.setEndTime(DateUtil.dateFormat(year + "-" + month + "-01 00:00:00"));
                }
            }
        }
    }
}
