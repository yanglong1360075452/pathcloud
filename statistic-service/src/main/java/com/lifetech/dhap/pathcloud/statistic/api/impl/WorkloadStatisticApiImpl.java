package com.lifetech.dhap.pathcloud.statistic.api.impl;

import com.lifetech.dhap.pathcloud.common.exception.BuzException;
import com.lifetech.dhap.pathcloud.common.exception.BuzExceptionCode;
import com.lifetech.dhap.pathcloud.common.response.PageDataVO;
import com.lifetech.dhap.pathcloud.common.response.ResponseVO;
import com.lifetech.dhap.pathcloud.common.utils.ExportExcel;
import com.lifetech.dhap.pathcloud.setting.application.ParamSettingApplication;
import com.lifetech.dhap.pathcloud.setting.application.dto.InspectCategoryDto;
import com.lifetech.dhap.pathcloud.setting.application.infrastructure.code.SystemKey;
import com.lifetech.dhap.pathcloud.statistic.api.WorkloadStatisticsApi;
import com.lifetech.dhap.pathcloud.statistic.application.WorkloadStatisticApplication;
import com.lifetech.dhap.pathcloud.statistic.application.condition.WorkloadCondition;
import com.lifetech.dhap.pathcloud.statistic.application.condition.WorkloadStatisticSortByEnum;
import com.lifetech.dhap.pathcloud.statistic.application.dto.Group;
import com.lifetech.dhap.pathcloud.statistic.application.dto.GroupInspectCategoryDto;
import com.lifetech.dhap.pathcloud.statistic.application.dto.MonthInspectCategoryDto;
import com.lifetech.dhap.pathcloud.statistic.application.dto.PersonInspectCategoryDto;
import com.lifetech.dhap.pathcloud.statistic.domain.model.PersonInspectCategory;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @author eric.guo@ostengar.com
 * @version 1.0
 * @time 2017-02-13-09:24
 */
@Component("workloadStatisticsApi")
public class WorkloadStatisticApiImpl implements WorkloadStatisticsApi {

    @Autowired
    private WorkloadStatisticApplication workloadStatisticApplication;

    @Autowired
    private ParamSettingApplication paramSettingApplication;

    @Override
    public ResponseVO monthInspectCategory(Long startTime, Long endTime, String hospital) throws BuzException {
        if(startTime == null || endTime == null){
            throw new BuzException(BuzExceptionCode.ErrorParam);
        }
        WorkloadCondition con = new WorkloadCondition();
        con.setStartTime(new Date(startTime));
        con.setEndTime(new Date(endTime));
        List<MonthInspectCategoryDto> data = workloadStatisticApplication.monthInspectCategory(con);
        return new ResponseVO(data);
    }

    @Override
    public ResponseVO personInspectCategory(@DefaultValue("1")Integer page, @DefaultValue("10")Integer length,
                                            Long startTime, Long endTime, Integer station,Integer order,String sort) throws BuzException {

        if (page <0 || length < 0){
            throw new BuzException(BuzExceptionCode.ErrorParam);
        }
        if (startTime == null || endTime == null){
            throw new BuzException(BuzExceptionCode.ErrorParam);
        }
        WorkloadCondition con = new WorkloadCondition();
       if (station != null){
           con.setFilter(station);
       }
        if (order != null){
            con.setOrder(WorkloadStatisticSortByEnum.valueOf(order).toString());
            if(sort != null){
                con.setOrder(con.getOrder()+" "+sort);
            }
        }
        con.setSize(length);
        con.setStart((page-1) * length);
        con.setStartTime(new Date(startTime));
        con.setEndTime(new Date(endTime));

        List<PersonInspectCategoryDto> data = workloadStatisticApplication.personInspectCategory(con);
        Long total = workloadStatisticApplication.personInspectCategoryTotal(con);
        return new PageDataVO(page,length,total,data);
    }

    @Override
    public ResponseVO groupInspectCategory(Long startTime, Long endTime, Integer filter) throws BuzException {
        if(startTime == null || endTime == null){
            throw new BuzException(BuzExceptionCode.ErrorParam);
        }
        WorkloadCondition con = new WorkloadCondition();
        con.setFilter(filter);
        con.setStartTime(new Date(startTime));
        con.setEndTime(new Date(endTime));
        List<GroupInspectCategoryDto> data = workloadStatisticApplication.groupInspectCategory(con);


        return new ResponseVO(data);
    }

    @Override
    public Response personInspectCategoryExport( Long startTime, Long endTime, Integer station) throws Exception {

        if (startTime == null || endTime == null) {
            throw new BuzException(BuzExceptionCode.ErrorParam);
        }
        WorkloadCondition con = new WorkloadCondition();
        if (station != null) {
            con.setFilter(station);
        }
        ;
        con.setStartTime(new Date(startTime));
        con.setEndTime(new Date(endTime));
        con.setStart(0);
        con.setSize(Integer.MAX_VALUE);
        List<String> lists = new ArrayList<>();
        lists.add("序号");
        lists.add("用户名");
        lists.add("姓名");
        lists.add("工位");
        List<PersonInspectCategoryDto> data = workloadStatisticApplication.personInspectCategory(con);
        List<InspectCategoryDto> inspectCategory = paramSettingApplication.getContentToListByKey(SystemKey.InspectCategory.toString());
        for (InspectCategoryDto inspectCategoryDto : inspectCategory) {
            String typeDesc = inspectCategoryDto.getTypeDesc();
            lists.add(typeDesc);
        }
        lists.add("合计");

        String[] rowsName = new String[lists.size()];
        lists.toArray(rowsName);


        String title = "工作量—按人统计";
        List<Object[]> dataList = new ArrayList<Object[]>();
        Object[] objs = null;
        PersonInspectCategoryDto dto = null;
        for (int i = 0; i < data.size(); i++) {
            dto = data.get(i);
            objs = new Object[rowsName.length];
            objs[0] = i + 1;
            objs[1] = dto.getUserName();
            objs[2] = dto.getFirstName();
            objs[3] = dto.getStation();
            List<PersonInspectCategory> items = dto.getItems();

            for (int k = 4; k < rowsName.length; k++) {
                for (int j = 0; j < items.size(); j++) {
                    if (rowsName[k].equals(items.get(j).getInspectCategoryName())) {
                        objs[k] = items.get(j).getNum();
                    }
                }
            }
            objs[rowsName.length - 1] = dto.getTotal2();
            dataList.add(objs);
        }
        ExportExcel ex = new ExportExcel(title, rowsName, dataList);


        String format = new SimpleDateFormat("yyMMddss").format(new Date());
        String fileName = "工作量_按人统计"+format+".xls";

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

    @Override
    public Response monthInspectCategoryExport(Long startTime, Long endTime, String hospital) throws Exception {

        if(startTime == null || endTime == null){
            throw new BuzException(BuzExceptionCode.ErrorParam);
        }
        WorkloadCondition con = new WorkloadCondition();
        con.setStartTime(new Date(startTime));
        con.setEndTime(new Date(endTime));
        List<MonthInspectCategoryDto> data = workloadStatisticApplication.monthInspectCategory(con);
        String[] rowsName = new String[]{"序号" ,"检查类别" ,"1月" ,"2月" ,"3月" ,"4月" ,"5月" ,"6月" , "7月" , "8月" , "9月"
        , "10月" ,"11月" ,"12月" , "合计"};

        String title = "工作量—按月统计";
        List<Object[]> dataList = new ArrayList<Object[]>();
        Object[] objs = null;
        MonthInspectCategoryDto dto=null;
        for (int i=0;i < data.size();i++){
            dto=data.get(i);
            objs = new Object[rowsName.length];
            objs[1] = dto.getInspectCategoryName();
            objs[2] = dto.getJanuary();
            objs[3] = dto.getFebruary();
            objs[4] = dto.getMarch();
            objs[5] = dto.getApril();
            objs[6] = dto.getMay();
            objs[7] = dto.getJune();
            objs[8] = dto.getJuly();
            objs[9] = dto.getAugust();
            objs[10] = dto.getSeptember();
            objs[11] = dto.getOctober();
            objs[12] = dto.getNovember();
            objs[13] = dto.getDecember();
            objs[14] = dto.getTotal();

            dataList.add(objs);

        }

        ExportExcel ex = new ExportExcel(title, rowsName, dataList);

        String format = new SimpleDateFormat("yyMMddss").format(new Date());
        String fileName = "工作量_按月统计"+format+".xls";

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

    @Override
    public Response groupInspectCategoryExport(Long startTime, Long endTime, Integer filter) throws Exception {

        if(startTime == null || endTime == null){
            throw new BuzException(BuzExceptionCode.ErrorParam);
        }
        WorkloadCondition con = new WorkloadCondition();
        if (filter != null && filter >= 0){
            con.setFilter(filter);
        }
        con.setStartTime(new Date(startTime));
        con.setEndTime(new Date(endTime));
        List<GroupInspectCategoryDto> data = workloadStatisticApplication.groupInspectCategory(con);

        String[] rowsName = new String[]{"检查类别","","1月" ,"2月" ,"3月" ,"4月" ,"5月" ,"6月" , "7月" , "8月" , "9月"
                , "10月" ,"11月" ,"12月" , "合计"};
        String title = "工作量—按组统计";
        List<Object[]> dataList = new ArrayList<Object[]>();
        Object[] objs = null;
        GroupInspectCategoryDto dto = null;
        for (int i=0;i < data.size();i++){
            dto=data.get(i);
            Group january = dto.getJanuary();
            Group february = dto.getFebruary();
            Group march = dto.getMarch();
            Group april = dto.getApril();
            Group may = dto.getMay();
            Group june = dto.getJune();
            Group july = dto.getJuly();
            Group august = dto.getAugust();
            Group september = dto.getSeptember();
            Group october = dto.getOctober();
            Group november = dto.getNovember();
            Group december = dto.getDecember();
            objs = new Object[rowsName.length];
            objs[0] = dto.getInspectCategoryName();
            objs[1] = "例数";
            objs[2] = january.getPathNum();
            objs[3] = february.getPathNum();
            objs[4] = march.getPathNum();
            objs[5] = april.getPathNum();
            objs[6] = may.getPathNum();
            objs[7] = june.getPathNum();
            objs[8] = july.getPathNum();
            objs[9] = august.getPathNum();
            objs[10] = september.getPathNum();
            objs[11] = october.getPathNum();
            objs[12] = november.getPathNum();
            objs[13] = december.getPathNum();
            objs[14] = dto.getPathologyTotal();
            dataList.add(objs);
            objs = new Object[rowsName.length];
            objs[0] = dto.getInspectCategoryName();
            objs[1] = "片数";
            objs[2] = january.getBlockNum();
            objs[3] = february.getBlockNum();
            objs[4] = march.getBlockNum();
            objs[5] = april.getBlockNum();
            objs[6] = may.getBlockNum();
            objs[7] = june.getBlockNum();
            objs[8] = july.getBlockNum();
            objs[9] = august.getBlockNum();
            objs[10] = september.getBlockNum();
            objs[11] = october.getBlockNum();
            objs[12] = november.getBlockNum();
            objs[13] = december.getBlockNum();
            objs[14] = dto.getBlockTotal();
            dataList.add(objs);
        }

        ExportExcel ex = new ExportExcel(title, rowsName, dataList);

        String format = new SimpleDateFormat("yyMMddss").format(new Date());
        String fileName = "工作量_按组统计"+format+".xls";

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

        File file = ex.mergeCell(UUID.randomUUID().toString()+".xls");
        Response.ResponseBuilder response = Response.ok(file);
        response.header("Content-Disposition", "attachment;filename=\"" + fileName + "\"");
        response.header("Content-Type", "application/octet-stream");
        response.header("Content-Length", file.length());

        return response.build();
    }
}

