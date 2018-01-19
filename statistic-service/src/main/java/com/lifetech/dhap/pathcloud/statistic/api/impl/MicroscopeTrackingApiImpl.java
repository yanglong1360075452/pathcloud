package com.lifetech.dhap.pathcloud.statistic.api.impl;

import com.lifetech.dhap.pathcloud.common.exception.BuzException;
import com.lifetech.dhap.pathcloud.common.exception.BuzExceptionCode;
import com.lifetech.dhap.pathcloud.common.response.PageDataVO;
import com.lifetech.dhap.pathcloud.common.response.ResponseVO;
import com.lifetech.dhap.pathcloud.common.utils.DateUtil;
import com.lifetech.dhap.pathcloud.common.utils.ExportExcel;
import com.lifetech.dhap.pathcloud.security.UserContext;
import com.lifetech.dhap.pathcloud.setting.application.ParamSettingApplication;
import com.lifetech.dhap.pathcloud.setting.application.infrastructure.code.ParamKey;
import com.lifetech.dhap.pathcloud.statistic.api.MicroscopeTrackingApi;
import com.lifetech.dhap.pathcloud.statistic.api.vo.MicroscopeVO;
import com.lifetech.dhap.pathcloud.tracking.application.MicroscopeTrackingApplication;
import com.lifetech.dhap.pathcloud.tracking.application.condition.MicroscopeTrackingCon;
import com.lifetech.dhap.pathcloud.tracking.application.dto.MicroscopeTrackingDto;
import org.apache.commons.lang.time.DateFormatUtils;
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
 * @author eric.guo@ostengar.com
 * @version 1.0
 * @time 2017-06-22-11:00
 */
@Component("microscopeTrackingApi")
public class MicroscopeTrackingApiImpl implements MicroscopeTrackingApi {

    @Autowired
    private MicroscopeTrackingApplication microscopeTrackingApplication;

    @Autowired
    private ParamSettingApplication paramSettingApplication;

    @Override
    public ResponseVO startUserMicroscope(MicroscopeVO vo) throws BuzException {
        MicroscopeTrackingDto dto = new MicroscopeTrackingDto();
        dto.setStartTime(new Date());
        dto.setBookingPid(UserContext.getLoginUserID());
        dto.setMicroscopeId(1);
        dto.setMicroscope("显微镜1");
        microscopeTrackingApplication.addMicroscopeTracking(dto);
        return new ResponseVO();
    }

    @Override
    public ResponseVO microscopeTracking(@DefaultValue("1") Integer page, @DefaultValue("10") Integer length,
                                         Integer year, Integer month, Integer instrumentId) throws BuzException {
        if (year == null || month==null || month <= 0){
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

        MicroscopeTrackingCon con = new MicroscopeTrackingCon();
        con.setStartTime(startTime);
        con.setEndTime(endTime);
        con.setSize(length);
        con.setStart((page - 1) * length);
        List<MicroscopeTrackingDto> dtos = microscopeTrackingApplication.getMicroscopeByCondition(con);

        return new PageDataVO(page, length, microscopeTrackingApplication.microscopeTotal(con), dtos);
    }

    @Override
    public Response microscopeTrackingExport(Integer year, Integer month) throws Exception {
        if (year == null || month==null || month <= 0){
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

        MicroscopeTrackingCon con = new MicroscopeTrackingCon();
        con.setStartTime(startTime);
        con.setEndTime(endTime);
        List<MicroscopeTrackingDto> dtos = microscopeTrackingApplication.getMicroscopeByCondition(con);

        String[] rowsName = new String[]{"序号", "使用者姓名", "员工号", "联系方式", "科室", "开始使用时间", "结束使用时间"};
        String title = "显微镜使用记录";
        List<Object[]> dataList = new ArrayList<>();
        Object[] objs;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (MicroscopeTrackingDto dto : dtos) {
            objs = new Object[rowsName.length];
            objs[1] = dto.getFirstName();
            objs[2] = dto.getWno();
            objs[3] = dto.getPhone();
            objs[4] = paramSettingApplication.getNameByKeyAndCode(ParamKey.Departments.toString(),dto.getDepartments());
            objs[5] = formatter.format(dto.getStartTime());
            if(dto.getEndTime() != null){
                objs[6] = formatter.format(dto.getEndTime());
            } else {
                objs[6] = "";
            }

            dataList.add(objs);
        }

        ExportExcel ex = new ExportExcel(title, rowsName, dataList);

        String format = new SimpleDateFormat("yyMMddss").format(new Date());
        String fileName = "显微镜使用记录" + format + ".xls";

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
