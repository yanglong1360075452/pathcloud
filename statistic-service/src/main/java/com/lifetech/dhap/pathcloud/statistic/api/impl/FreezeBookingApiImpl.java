package com.lifetech.dhap.pathcloud.statistic.api.impl;

import com.lifetech.dhap.pathcloud.application.domain.model.Booking;
import com.lifetech.dhap.pathcloud.common.exception.BuzException;
import com.lifetech.dhap.pathcloud.common.exception.BuzExceptionCode;
import com.lifetech.dhap.pathcloud.common.response.ResponseVO;
import com.lifetech.dhap.pathcloud.common.utils.DateUtil;
import com.lifetech.dhap.pathcloud.common.utils.ExportExcel;
import com.lifetech.dhap.pathcloud.statistic.api.FreezeBookingApi;
import com.lifetech.dhap.pathcloud.statistic.application.FreezeBookingApplication;
import com.lifetech.dhap.pathcloud.statistic.application.dto.FreezeBookingDto;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.PhaseInterceptorChain;
import org.apache.cxf.transport.http.AbstractHTTPDestination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Response;
import java.io.File;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by HP on 2017/3/29.
 */
@Component("freezeBookingApi")
public class FreezeBookingApiImpl implements FreezeBookingApi {

    @Autowired
    private FreezeBookingApplication freezeBookingApplication;


    @Override
    public ResponseVO freezeBookingQuery(Integer year, Integer month, Integer instrumentId) throws BuzException {

        if (year == null || month==null || month <= 0){
            throw new BuzException(BuzExceptionCode.ErrorParam);
        }

        Date starttime =null;
        Date endtime =null;

        Calendar cl = Calendar.getInstance();
        cl.set(Calendar.YEAR,year);
        cl.set(Calendar.MONTH,month-1,1);

        int lastDay = cl.getActualMaximum(Calendar.DAY_OF_MONTH);


        if (month<10){
             starttime = DateUtil.dateFormat(year + "-0" + month + "-0" + 1 + " 00:00:00");
             endtime = DateUtil.dateFormat(year + "-0" + month + "-" + lastDay + " 00:00:00");

        }else {
            starttime= DateUtil.dateFormat(year + "-" + month + "-0" + 1 + " 00:00:00");
            endtime= DateUtil.dateFormat(year + "-" + month + "-" + lastDay + " 00:00:00");
        }


        List<FreezeBookingDto> dtos = freezeBookingApplication.freezeBookingQuery(starttime, endtime, instrumentId);


        return new ResponseVO(dtos);
    }

    @Override
    public Response freezeBookingExport(Integer year, Integer month) throws Exception {

        if (year == null || month==null || month <= 0){
            throw new BuzException(BuzExceptionCode.ErrorParam);
        }

        Date starttime =null;
        Date endtime =null;

        Calendar cl = Calendar.getInstance();
        cl.set(Calendar.YEAR,year);
        cl.set(Calendar.MONTH,month-1,1);

        int lastDay = cl.getActualMaximum(Calendar.DAY_OF_MONTH);


        if (month<10){
            starttime = DateUtil.dateFormat(year + "-0" + month + "-0" + 1 + " 00:00:00");
            endtime = DateUtil.dateFormat(year + "-0" + month + "-" + lastDay + " 00:00:00");

        }else {
            starttime= DateUtil.dateFormat(year + "-" + month + "-0" + 1 + " 00:00:00");
            endtime= DateUtil.dateFormat(year + "-" + month + "-" + lastDay + " 00:00:00");
        }

        List<Booking> bookings = freezeBookingApplication.freezeBookingExport(starttime, endtime);

        String[] rowsName = new String[]{"序号","日期","时间","切片机", "预约人","联系电话"};

        String title = "冰冻预约";
        List<Object[]> dataList = new ArrayList<Object[]>();
        Object[] objs = null;
        Booking booking;

       for (int i=0;i<bookings.size();i++){
            objs=new Object[rowsName.length];
           booking=bookings.get(i);
           SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
           String date = sdf.format(booking.getStartTime());
           SimpleDateFormat sdf1 = new SimpleDateFormat("HH:mm");
           String time = sdf1.format(booking.getStartTime())+"-"+sdf1.format(booking.getEndTime());
           objs[1]=date;
           objs[2]=time;
           objs[3]="切片机"+booking.getInstrumentId();
           objs[4]=booking.getBookingPerson();
           objs[5]=booking.getBookingPhone();
           dataList.add(objs);
       }

        ExportExcel ex = new ExportExcel(title, rowsName, dataList);

        String format = new SimpleDateFormat("yyMMddss").format(new Date());
        String fileName = "冰冻预约"+format+".xls";

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

        File file = ex.export(UUID.randomUUID().toString()+".xls");
        Response.ResponseBuilder response = Response.ok(file);
        response.header("Content-Disposition", "attachment;filename=\"" + fileName + "\"");
        response.header("Content-Type", "application/octet-stream");
        response.header("Content-Length", file.length());
        return response.build();

    }


}
