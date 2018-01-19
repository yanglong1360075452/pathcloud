package com.lifetech.dhap.pathcloud.statistic.api.impl;

import com.lifetech.dhap.pathcloud.common.exception.BuzException;
import com.lifetech.dhap.pathcloud.common.exception.BuzExceptionCode;
import com.lifetech.dhap.pathcloud.common.response.PageDataVO;
import com.lifetech.dhap.pathcloud.common.response.ResponseVO;
import com.lifetech.dhap.pathcloud.common.utils.DateUtil;
import com.lifetech.dhap.pathcloud.common.utils.ExportExcel;
import com.lifetech.dhap.pathcloud.localdaemon.device.constants.InstrumentStatus;
import com.lifetech.dhap.pathcloud.localdaemon.device.constants.InstrumentType;
import com.lifetech.dhap.pathcloud.statistic.api.InstrumentTrackingApi;
import com.lifetech.dhap.pathcloud.tracking.application.InstrumentTrackingApplication;
import com.lifetech.dhap.pathcloud.tracking.application.condition.InstrumentTrackingCon;
import com.lifetech.dhap.pathcloud.tracking.application.dto.InstrumentTrackingDto;
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
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by HP on 2017/9/7.
 */

@Component("instrumentTrackingApi")
public class InstrumentTrackingApiImpl  implements InstrumentTrackingApi{

    @Autowired
    private InstrumentTrackingApplication instrumentTrackingApplication;

    @Override
    public ResponseVO InstrumentTracking(@DefaultValue("1") Integer page, @DefaultValue("10") Integer length, Integer year,
                                         Integer month, Integer status, Integer type) throws BuzException {

        if (year == null || month==null || month <= 0){
            throw new BuzException(BuzExceptionCode.ErrorParam);
        }
        if (type == null ||  type < 0){
            throw new BuzException(BuzExceptionCode.ErrorParam);
        }
        Date startTime;
        Date endTime;
        Calendar cl = Calendar.getInstance();
        cl.set(Calendar.YEAR,year);
        cl.set(Calendar.MONTH,month);
        int lastDay = cl.getActualMaximum(Calendar.DAY_OF_MONTH);
        if (month<10){
            endTime = DateUtil.dateFormat(year + "-0" + month + "-" + lastDay + " 00:00:00");
            startTime = DateUtil.dateFormat(year + "-0" + month + "-0" + 1 + " 00:00:00");
        }else {
            startTime= DateUtil.dateFormat(year + "-" + month + "-0" + 1 + " 00:00:00");
            endTime= DateUtil.dateFormat(year + "-" + month + "-" + lastDay + " 00:00:00");
        }
        InstrumentTrackingCon con = new InstrumentTrackingCon();
        con.setStartTime(startTime);
        con.setEndTime(endTime);
        con.setSize(length);
        con.setStart((page - 1) * length);
        if (status != null){
            con.setStatus(status);
        }
        con.setType(type);
        if(type.equals(InstrumentType.DyeingMachine.getCode())){
            con.setOperation(TrackingOperation.dye.toCode());
        }else if(type.equals(InstrumentType.SealingMachine.getCode())){
            con.setOperation(TrackingOperation.mounting.toCode());
        }
        List<InstrumentTrackingDto> InstrumentTrackingDtoList=instrumentTrackingApplication.InstrumentTracking(con);
        for (InstrumentTrackingDto dto:InstrumentTrackingDtoList){
            dto.setStatusDesc(InstrumentStatus.getNameByCode(dto.getStatus()));
        }
        Long total=instrumentTrackingApplication.InstrumentTrackingTotal(con);
        return new PageDataVO(page,length,total,InstrumentTrackingDtoList);
    }

    @Override
    public Response InstrumentTrackingExport(Integer year, Integer month, Integer status, Integer type) throws Exception {

        if (year == null || month==null || month <= 0){
            throw new BuzException(BuzExceptionCode.ErrorParam);
        }

        if (type == null ||  type < 0){
            throw new BuzException(BuzExceptionCode.ErrorParam);
        }


        Date startTime;
        Date endTime;

        Calendar cl = Calendar.getInstance();
        cl.set(Calendar.YEAR,year);
        cl.set(Calendar.MONTH,month);

        int lastDay = cl.getActualMaximum(Calendar.DAY_OF_MONTH);

        if (month<10){
            startTime = DateUtil.dateFormat(year + "-0" + month + "-0" + 1 + " 00:00:00");
            endTime = DateUtil.dateFormat(year + "-0" + month + "-" + lastDay + " 00:00:00");
        }else {
            startTime= DateUtil.dateFormat(year + "-" + month + "-0" + 1 + " 00:00:00");
            endTime= DateUtil.dateFormat(year + "-" + month + "-" + lastDay + " 00:00:00");
        }

        InstrumentTrackingCon con = new InstrumentTrackingCon();
        con.setStartTime(startTime);
        con.setEndTime(endTime);
        con.setStart(0);
        con.setSize(Integer.MAX_VALUE);
        if (status != null){
            con.setStart(status);
        }
        con.setType(type);
        List<InstrumentTrackingDto> dtos=instrumentTrackingApplication.InstrumentTracking(con);
        for (InstrumentTrackingDto dto:dtos){
            dto.setStatusDesc(InstrumentStatus.getNameByCode(dto.getStatus()));
        }

        String[] rowsName = new String[]{"序号", "染色机编号", "使用人", "使用时间", "设备状态", "备注"};
        String title =null;
        if (type == 1){
             title = "染色机使用记录";
        }else {
             title = "封片机使用记录";
        }

        List<Object[]> dataList = new ArrayList<>();
        Object[] objs;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (InstrumentTrackingDto dto : dtos) {
            objs = new Object[rowsName.length];
            objs[1] = dto.getInstrumentName();
            objs[2] = dto.getOperatorName();
            objs[3] = formatter.format(dto.getOperatorTime());
            objs[4] = dto.getStatusDesc();
            objs[5] = dto.getDescription();


            dataList.add(objs);
        }

        ExportExcel ex = new ExportExcel(title, rowsName, dataList);

        String format = new SimpleDateFormat("yyMMddss").format(new Date());
        String fileName =null;
        if (type == 1){
            fileName = "染色机使用记录" + format + ".xls";
        }else {
            fileName = "封片机使用记录" + format + ".xls";
        }


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
}
