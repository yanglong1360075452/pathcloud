package com.lifetech.dhap.pathcloud.application.api.impl;

import com.lifetech.dhap.pathcloud.application.api.IhcApi;
import com.lifetech.dhap.pathcloud.application.api.vo.IhcApplicationQueryVO;
import com.lifetech.dhap.pathcloud.application.api.vo.IhcPrintVO;
import com.lifetech.dhap.pathcloud.application.application.ApplicationIhcApplication;
import com.lifetech.dhap.pathcloud.application.application.condition.ApplicationIhcCondition;
import com.lifetech.dhap.pathcloud.application.application.condition.PrintIhcCondition;
import com.lifetech.dhap.pathcloud.application.application.dto.IhcApplicationQueryDto;
import com.lifetech.dhap.pathcloud.application.application.dto.IhcBlockDto;
import com.lifetech.dhap.pathcloud.application.application.dto.PrintIhcsDetailDto;
import com.lifetech.dhap.pathcloud.application.application.infrastructure.code.IhcApplicationStatus;
import com.lifetech.dhap.pathcloud.application.infrastructure.code.IhcSortByEnum;
import com.lifetech.dhap.pathcloud.common.data.PathologyStatus;
import com.lifetech.dhap.pathcloud.common.exception.BuzException;
import com.lifetech.dhap.pathcloud.common.exception.BuzExceptionCode;
import com.lifetech.dhap.pathcloud.common.response.PageDataVO;
import com.lifetech.dhap.pathcloud.common.response.ResponseVO;
import com.lifetech.dhap.pathcloud.common.utils.ExportExcel;
import com.lifetech.dhap.pathcloud.common.utils.StringUtil;
import com.lifetech.dhap.pathcloud.setting.application.ParamSettingApplication;
import com.lifetech.dhap.pathcloud.tracking.application.BlockApplication;
import com.lifetech.dhap.pathcloud.tracking.application.dto.SlideDto;
import com.lifetech.dhap.pathcloud.tracking.application.dto.SlidePrintDto;
import com.lifetech.dhap.pathcloud.user.api.vo.UserSimpleVO;
import com.lifetech.dhap.pathcloud.user.application.UserApplication;
import com.lifetech.dhap.pathcloud.user.application.dto.UserSimpleDto;
import net.sf.json.JSONArray;
import org.apache.commons.collections.CollectionUtils;
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
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by LiuMei on 2017-04-06.
 */
@Component("ihcApi")
public class IhcApiImpl implements IhcApi {

    @Autowired
    private ApplicationIhcApplication applicationIhcApplication;

    @Autowired
    private ParamSettingApplication paramSettingApplication;

    @Autowired
    private UserApplication userApplication;

    @Autowired
    private BlockApplication blockApplication;

    @Override
    public ResponseVO confirm(List<Long> ihcBlockIds) throws BuzException {
        if (CollectionUtils.isEmpty(ihcBlockIds)) {
            throw new BuzException(BuzExceptionCode.ErrorParam);
        }
        List<IhcBlockDto> ihcBlockDTOs = new ArrayList<>();
        for (Long ihcBlockId : ihcBlockIds) {
            IhcBlockDto ihcBlockDto = applicationIhcApplication.getIhcBlockById(ihcBlockId);
            if (ihcBlockDto == null || ihcBlockDto.getId() == null) {
                throw new BuzException(BuzExceptionCode.RecordNotExists);
            }
            Integer status = ihcBlockDto.getStatus();
            if (!status.equals(IhcApplicationStatus.PrepareConfirm.toCode()) && !status.equals(IhcApplicationStatus.Delay.toCode())) {
                throw new BuzException(BuzExceptionCode.StatusNotMatch);
            }
            ihcBlockDTOs.add(ihcBlockDto);
        }
        applicationIhcApplication.confirmApplication(ihcBlockDTOs);
        return new ResponseVO();
    }

    @Override
    public ResponseVO delay(List<Long> ihcBlockIds) throws BuzException {
        if (CollectionUtils.isEmpty(ihcBlockIds)) {
            throw new BuzException(BuzExceptionCode.ErrorParam);
        }
        applicationIhcApplication.delayApplication(ihcBlockIds);
        return new ResponseVO();
    }

    @Override
    public ResponseVO print(List<IhcPrintVO> ihcPrintVOS) throws BuzException {
        if (CollectionUtils.isEmpty(ihcPrintVOS)) {
            throw new BuzException(BuzExceptionCode.ErrorParam);
        }
        List<SlideDto> slideDTOs = new ArrayList<>();
        SlideDto slideDto;
        for (IhcPrintVO ihcPrintVO : ihcPrintVOS) {
            slideDto = new SlideDto();
            Long blockId = ihcPrintVO.getBlockId();
            Long applyId = ihcPrintVO.getApplyId();
            String number = ihcPrintVO.getNumber();
            if (blockId == null) {
                throw new BuzException(BuzExceptionCode.ErrorParam);
            }
            slideDto.setParentId(blockId);
            slideDto.setId(ihcPrintVO.getSlideId());
            slideDto.setMarker(ihcPrintVO.getSlideMarker());
            slideDto.setApplyId(applyId);
            slideDto.setSpecialDye(ihcPrintVO.getSpecialDye());
            slideDto.setNumber(number);
            slideDTOs.add(slideDto);
        }
        List<SlidePrintDto> slidePrintDTOs = blockApplication.slidePrint(slideDTOs);
        return new ResponseVO(slidePrintDTOs);
    }

    @Override
    public ResponseVO getIHCs(@DefaultValue("1") Integer page, @DefaultValue("10") Integer length,
                              String filter, Long createTimeStart, Long createTimeEnd, Integer status, Integer blockStatus, Integer dyeCategory,
                              Integer order, String sort) throws BuzException {
        if (page < 0 || length < 1) {
            throw new BuzException(BuzExceptionCode.ErrorParam);
        }
        ApplicationIhcCondition con = new ApplicationIhcCondition();
        con.setStart((page - 1) * length);
        con.setSize(length);

        if (filter != null) {
            con.setFilter(StringUtil.escapeExprSpecialWord(filter.trim()));
        }

        if (createTimeStart != null) {
            con.setCreateTimeStart(new Date(createTimeStart));
        }

        if (createTimeEnd != null) {
            con.setCreateTimeEnd(new Date(createTimeEnd));
        }
        if (order != null) {
            con.setOrder(IhcSortByEnum.valueOf(order).toString());
            if (sort != null) {
                con.setOrder(con.getOrder() + " " + sort);
            }
        } else {
            con.setOrder(IhcSortByEnum.Id.toString());
        }
        con.setStatus(status);
        con.setBlockStatus(blockStatus);
        con.setDyeCategory(dyeCategory);
        List<IhcApplicationQueryDto> data = applicationIhcApplication.getIHCs(con);
        List<IhcApplicationQueryVO> lists = ihcApplicationQueryDTOsToVOs(data);
        Long total = applicationIhcApplication.getIHCsTotal(con);
        return new PageDataVO(page, length, total, lists);
    }

    public List<IhcApplicationQueryVO> ihcApplicationQueryDTOsToVOs(List<IhcApplicationQueryDto> data) {
        List<IhcApplicationQueryVO> list = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(data)) {
            IhcApplicationQueryVO ihcApplicationQueryVO;
            for (IhcApplicationQueryDto dto : data) {
                ihcApplicationQueryVO = new IhcApplicationQueryVO();
                BeanUtils.copyProperties(dto, ihcApplicationQueryVO);
                ihcApplicationQueryVO.setIhcMarkers(JSONArray.fromObject(dto.getIhcMarker()));
                ihcApplicationQueryVO.setDyeCategoryName(paramSettingApplication.getSpecialDyeDesc(dto.getDyeCategory()));
                ihcApplicationQueryVO.setStatusName(IhcApplicationStatus.getNameByCode(dto.getStatus()));
                ihcApplicationQueryVO.setBlockStatusDesc(PathologyStatus.getNameByCode(dto.getBlockStatus()));
                UserSimpleDto userSimpleDto = userApplication.getUserSimpleInfoById(dto.getUpdateBy());
                if (userSimpleDto != null) {
                    UserSimpleVO userSimpleVO = new UserSimpleVO();
                    BeanUtils.copyProperties(userSimpleDto, userSimpleVO);
                    ihcApplicationQueryVO.setUpdateBy(userSimpleVO);
                }
                list.add(ihcApplicationQueryVO);
            }

        }
        return list;
    }

    @Override
    public Response getIHCsExport(String filter, Long createTimeStart, Long createTimeEnd, Integer status, Integer blockStatus,
                                  Integer dyeCategory) throws Exception {
        ApplicationIhcCondition con = new ApplicationIhcCondition();
        con.setStart(0);
        con.setSize(Integer.MAX_VALUE);
        if (filter != null) {
            con.setFilter(StringUtil.escapeExprSpecialWord(filter));
        }
        if (createTimeStart != null) {
            con.setCreateTimeStart(new Date(createTimeStart));
        }
        if (createTimeEnd != null) {
            con.setCreateTimeEnd(new Date(createTimeEnd));
        }
        con.setOrder(" ai.create_time desc,bi.serial_number,bi.sub_id asc");
        con.setStatus(status);
        con.setBlockStatus(blockStatus);
        con.setDyeCategory(dyeCategory);
        List<IhcApplicationQueryDto> data = applicationIhcApplication.getIHCs(con);
        List<IhcApplicationQueryVO> lists = ihcApplicationQueryDTOsToVOs(data);

        String[] rowsName = {"序号", "病理号", "蜡块号", "特染类别", "申请人", "申请时间"};
        String title = "免疫组化—申请列表";
        List<Object[]> dataList = new ArrayList<>();
        Object[] obj;
        IhcApplicationQueryVO vo;
        for (int i = 0; i < lists.size(); i++) {
            vo = lists.get(i);
            obj = new Object[rowsName.length];
            obj[1] = vo.getSerialNumber();
            obj[2] = vo.getSubId();
            obj[3] = vo.getDyeCategoryName();
            obj[4] = vo.getApplyUser();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            String applyTime = sdf.format(vo.getApplyTime());
            obj[5] = applyTime;
            dataList.add(obj);
        }

        ExportExcel ex = new ExportExcel(title, rowsName, dataList);

        String format = new SimpleDateFormat("yy-MM-dd").format(new Date());
        String fileName = "免疫组化—申请列表" + format + ".xls";

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
    public ResponseVO getPrintIhcs(@DefaultValue("1") Integer page, @DefaultValue("10") Integer length,
                                   Long createTimeStart,
                                   Long createTimeEnd, String filter,
                                   Integer specialDye, Integer count) throws Exception {
        PrintIhcCondition con = new PrintIhcCondition();
        con.setStart((page - 1) * length);
        con.setSize(length);
        if (createTimeStart != null) {
            con.setCreateTimeStart(new Date(createTimeStart));
        }
        if (createTimeEnd != null) {
            con.setCreateTimeEnd(new Date(createTimeEnd));
        }
        if (filter != null) {
            con.setFilter(filter.trim());
        }
        if (specialDye != null) {
            con.setSpecialDye(specialDye);
        }
        con.setCount(count);
        List<PrintIhcsDetailDto> dtoList = applicationIhcApplication.getPrintIhcs(con);
        Long total = applicationIhcApplication.getPrintIhcsTotal(con);
        return new PageDataVO(page, length, total, dtoList);
    }

}
