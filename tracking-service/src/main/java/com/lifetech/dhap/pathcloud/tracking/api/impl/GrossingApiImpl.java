package com.lifetech.dhap.pathcloud.tracking.api.impl;

import com.lifetech.dhap.pathcloud.application.application.PathologyApplication;
import com.lifetech.dhap.pathcloud.application.application.dto.PathologyDto;
import com.lifetech.dhap.pathcloud.common.data.PathologyStatus;
import com.lifetech.dhap.pathcloud.common.domain.CommonRepository;
import com.lifetech.dhap.pathcloud.common.exception.BuzException;
import com.lifetech.dhap.pathcloud.common.exception.BuzExceptionCode;
import com.lifetech.dhap.pathcloud.common.response.PageDataVO;
import com.lifetech.dhap.pathcloud.common.response.ResponseVO;
import com.lifetech.dhap.pathcloud.common.utils.StringUtil;
import com.lifetech.dhap.pathcloud.dehydrate.application.DehydratorApplication;
import com.lifetech.dhap.pathcloud.dehydrate.application.condition.GetBlockCondition;
import com.lifetech.dhap.pathcloud.dehydrate.application.dto.BlockInfoDto;
import com.lifetech.dhap.pathcloud.file.application.PathologyFileApplication;
import com.lifetech.dhap.pathcloud.file.application.dto.PathologyFileDto;
import com.lifetech.dhap.pathcloud.security.UserContext;
import com.lifetech.dhap.pathcloud.setting.application.ParamSettingApplication;
import com.lifetech.dhap.pathcloud.setting.application.infrastructure.code.ParamKey;
import com.lifetech.dhap.pathcloud.setting.application.infrastructure.code.SystemKey;
import com.lifetech.dhap.pathcloud.tracking.api.GrossingApi;
import com.lifetech.dhap.pathcloud.tracking.api.vo.*;
import com.lifetech.dhap.pathcloud.tracking.application.GrossingApplication;
import com.lifetech.dhap.pathcloud.tracking.application.PathTrackingApplication;
import com.lifetech.dhap.pathcloud.tracking.application.condition.GrossingCon;
import com.lifetech.dhap.pathcloud.tracking.application.dto.*;
import com.lifetech.dhap.pathcloud.tracking.application.infrastructure.code.TrackingOperation;
import com.lifetech.dhap.pathcloud.tracking.infrastructure.code.GrossingSortByEnum;
import com.lifetech.dhap.pathcloud.common.data.Permission;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.DefaultValue;
import java.io.File;
import java.util.*;

import static com.lifetech.dhap.pathcloud.common.utils.StringUtil.stringSplitToList;

/**
 * @author eric.guo@ostengar.com
 * @version 1.0
 * @time 2016-12-06-13:05
 */
@Component("grossingApi")
public class GrossingApiImpl implements GrossingApi {

    @Autowired
    private GrossingApplication grossingApplication;

    @Autowired
    private PathologyApplication pathologyApplication;

    @Autowired
    private PathologyFileApplication pathologyFileApplication;

    @Autowired
    private ParamSettingApplication paramSettingApplication;

    @Autowired
    private DehydratorApplication dehydratorApplication;

    @Autowired
    private CommonRepository commonRepository;

    @Autowired
    private PathTrackingApplication pathTrackingApplication;

    @Override
    public ResponseVO addGrossing(Long id, GrossingSaveVO vo) throws BuzException {
        if (id <= 0 || vo == null) {
            throw new BuzException(BuzExceptionCode.ErrorParam);
        }
        PathologyDto pathology = pathologyApplication.getSimplePathById(id);
        if (pathology == null) {
            throw new BuzException(BuzExceptionCode.RecordNotExists);
        }
        if (!pathology.getStatus().equals(PathologyStatus.PrepareGrossing.toCode()) &&
                !pathology.getStatus().equals(PathologyStatus.PrepareGrossingConfirm.toCode())) {
            throw new BuzException(BuzExceptionCode.StatusNotMatch);
        }
        Long operatorId = vo.getOperatorId();
        Long secOperatorId = vo.getSecOperatorId();
        vo.setId(id);
        GrossingSaveDto data = new GrossingSaveDto();
        data.setPathologyId(id);
        data.setJujianNote(vo.getJujianNote());
        data.setBingdongNote(vo.getBingdongNote());
        data.setUpdateBy(UserContext.getLoginUserID());
        data.setOperatorId(operatorId);
        data.setSecOperatorId(secOperatorId);
        data.setManualFlag(vo.getManualFlag());
        if (vo.getBlocks() != null) {
            data.setBlocks(blocksVOToDto(id, vo.getBlocks()));
        }
        Boolean print = vo.getPrint();
        if (print != null && print) {
            grossingApplication.grossingPrint(data);
        } else {
            if (operatorId == null || secOperatorId == null) {
                throw new BuzException(BuzExceptionCode.ErrorParam);
            }
            grossingApplication.grossingSave(data);
        }

        return new ResponseVO();
    }

    public static List<BlockDto> blocksVOToDto(Long pathologyId, List<BlockVO> blockVOs) {
        if (CollectionUtils.isEmpty(blockVOs)) {
            return null;
        }
        List<BlockDto> blockDtoList = new ArrayList<>();
        for (BlockVO blockVO : blockVOs) {
            if (StringUtils.isBlank(blockVO.getSubId())) {
                throw new BuzException(BuzExceptionCode.ErrorParam);
            }
            BlockDto dto = new BlockDto();
            BeanUtils.copyProperties(blockVO, dto);
            dto.setCreateBy(UserContext.getLoginUserID());
            dto.setPathId(pathologyId);
            blockDtoList.add(dto);
        }
        return blockDtoList;
    }

    @Override
    public ResponseVO getGrossing(@DefaultValue("1") Integer page, @DefaultValue("10") Integer length,
                                  Integer order, String sort, Integer status, String filter,
                                  Integer departments, Long operator, Long secOperator,
                                  Long timeStart, Long timeEnd) throws BuzException {
        if (page < 0 || length < 1) {
            throw new BuzException(BuzExceptionCode.ErrorParam);
        }

        String usingFrozen = paramSettingApplication.getContentByKey(SystemKey.UsingFrozen.toString());
        GrossingCon con = new GrossingCon();
        if (usingFrozen.equals("0")) {
            con.setUsingFrozen(false);
        } else {
            con.setUsingFrozen(true);
        }

        if (order != null) {
            con.setOrder(GrossingSortByEnum.valueOf(order).toString());
            if (sort != null) {
                con.setOrder(con.getOrder() + " " + sort);
            }
        } else {
            con.setOrder(GrossingSortByEnum.valueOf(0).toString() + " desc");
        }
        con.setSize(length);
        con.setStart((page - 1) * length);
        if (filter != null && filter.contains("-") && filter.length() > 18) {
            String[] pathNos = filter.split("-");
            if (pathNos.length != 2) {
                throw new BuzException(BuzExceptionCode.ErrorParam);
            }
            con.setPathNoEnd(pathNos[1].trim());
            con.setPathNoStart(pathNos[0].trim());
        } else if (filter != null) {
            con.setFilter(StringUtil.escapeExprSpecialWord(filter.trim()));
        }

        con.setBlockStatus(status);

        if (timeStart != null) {
            con.setStartTime(new Date(timeStart));
        }
        if (timeEnd != null) {
            con.setEndTime(new Date(timeEnd));
        }
        con.setOperator(operator);
        con.setSecOperator(secOperator);
        con.setDepartments(departments);

        con.setOperation(TrackingOperation.grossing.toCode());
        List<BlockDetailDto> data = grossingApplication.getBlockByCondition(con);
        Long total = grossingApplication.countBlockByCondition(con);
        return new PageDataVO(page, length, total, data);
    }

    @Override
    public ResponseVO confirmGrossing(String basketNumber) throws BuzException {
        List<Integer> basketNos = new ArrayList<>();
        if (basketNumber != null && !(basketNumber.equals("{}") || basketNumber.equals(""))) {
            //接收脱水篮编号
            basketNos = StringUtil.stringSplitToList(basketNumber);
        }
        GrossingCon con = new GrossingCon();
        con.setSize(Integer.MAX_VALUE);
        con.setBlockStatus(PathologyStatus.PrepareGrossingConfirm.toCode());
        con.setOperation(TrackingOperation.grossing.toCode());
        List<BlockDetailDto> grossingBlocks = grossingApplication.getBlockByCondition(con);
        if (CollectionUtils.isEmpty(grossingBlocks)) {
            throw new BuzException(BuzExceptionCode.ErrorParam);
        }
        String content = paramSettingApplication.getContentByKey(ParamKey.GrossingConfirmPhoto.toString());
        Map<Long, Integer> blockWithBasket = new HashMap<>();
        for (BlockDetailDto block : grossingBlocks) {
            Long blockId = block.getBlockId();
            if (CollectionUtils.isNotEmpty(basketNos)) {//按脱水篮编号过滤
                Integer blockBasket = block.getBasketNumber();
                for (Integer i : basketNos) {
                    if (blockBasket.equals(i)) {
                        if (content.equals("1")) { //取材确认必须拍照
                            PathologyFileDto fileDto = pathologyFileApplication.getFileByBlockId(blockId);
                            if (fileDto == null) {
                                throw new BuzException(BuzExceptionCode.NoFile);
                            }
                        }
                        blockWithBasket.put(blockId, blockBasket);
                    }
                }
            }
        }
        grossingApplication.grossingConfirm(blockWithBasket);
        return new ResponseVO();
    }

    @Override
    public ResponseVO getGrossingForConfirm(@DefaultValue("1") Integer page, @DefaultValue("10") Integer length,
                                            Integer order, String sort, Long secOperator, String basketNumbers) throws BuzException {
        if (page < 0 || length < 1) {
            throw new BuzException(BuzExceptionCode.ErrorParam);
        }

        GrossingCon con = new GrossingCon();
        if (order != null) {
            con.setOrder(GrossingSortByEnum.valueOf(order).toString());
            if (sort != null) {
                con.setOrder(con.getOrder() + " " + sort);
            }
        } else {
            con.setOrder(GrossingSortByEnum.valueOf(0).toString() + " desc");
        }
        con.setSize(length);
        con.setStart((page - 1) * length);

        secOperator = UserContext.getLoginUserID();
        List<Integer> permissionsCode = UserContext.getLoginUserPermissions();
        if (CollectionUtils.isEmpty(permissionsCode)) {
            for (Integer permission : permissionsCode) {
                if (permission.equals(Permission.Admin.toCode())) {
                    secOperator = null;
                }
            }
        }
        con.setSecOperator(secOperator);
        if (basketNumbers != null && basketNumbers.isEmpty()) {
            con.setBasketNumbers(null);
        } else {
            con.setBasketNumbers(stringSplitToList(basketNumbers));
        }
        con.setBlockStatus(PathologyStatus.PrepareGrossingConfirm.toCode());
        con.setOperation(TrackingOperation.grossing.toCode());
        List<BlockDetailDto> data = grossingApplication.getBlockByCondition(con);
        Long total = grossingApplication.countBlockByCondition(con);
        Long totalPathology = grossingApplication.countPathologyByCondition(con);
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("page", page);
        dataMap.put("length", length);
        dataMap.put("total", total);
        dataMap.put("data", data);
        dataMap.put("totalPathology", totalPathology);
        ResponseVO vo = new ResponseVO();
        vo.setCode(0);
        vo.setData(dataMap);
        return vo;
    }

    @Override
    public ResponseVO getGrossingForDehydrate(@DefaultValue("1") Integer page, @DefaultValue("10") Integer length,
                                              String basketNumbers, long instrumentId) throws BuzException {
        if (page < 0 || length < 1) {
            throw new BuzException(BuzExceptionCode.ErrorParam);
        }
        if (basketNumbers == null || "".equals(basketNumbers.trim())) {
            throw new BuzException(BuzExceptionCode.ErrorParam);
        }
        GrossingCon con = new GrossingCon();
        con.setSize(length);
        con.setStart((page - 1) * length);
        con.setBasketNumbers(stringSplitToList(basketNumbers));
        con.setBlockStatus(PathologyStatus.PrepareDehydrate.toCode());
        con.setOperation(TrackingOperation.grossingConfirm.toCode());
        con.setOrder(" t.serial_number ");
        List<BlockDetailDto> data = grossingApplication.getBlockByCondition(con);
        GetBlockCondition dehydrateCon = new GetBlockCondition();
        dehydrateCon.setInstrumentId(instrumentId);
        dehydrateCon.setSize(Integer.MAX_VALUE);
        List<BlockInfoDto> blockInfoDtoList = dehydratorApplication.getBlocksInfoByCondition(dehydrateCon);
        Long total = grossingApplication.countBlockByCondition(con);
        if (CollectionUtils.isNotEmpty(blockInfoDtoList)) {
            total += blockInfoDtoList.size();
            BlockDetailDto blockDetailDto;
            for (BlockInfoDto blockInfoDto : blockInfoDtoList) {
                blockDetailDto = new BlockDetailDto();
                blockDetailDto.setPathologyNumber(blockInfoDto.getPathNo());
                blockDetailDto.setSubId(blockInfoDto.getSubId());
                blockDetailDto.setName(blockInfoDto.getPatientName());
                blockDetailDto.setBiaoshi(blockInfoDto.getBiaoshi());
                blockDetailDto.setBiaoshiName(blockInfoDto.getBiaoshiDesc());
                blockDetailDto.setUnit(blockInfoDto.getUnit());
                blockDetailDto.setCount(blockInfoDto.getCount());
                blockDetailDto.setStatus(blockInfoDto.getStatus());
                blockDetailDto.setStatusName(blockInfoDto.getStatusDesc());
                data.add(blockDetailDto);
            }
        }

        return new PageDataVO(page, length, total, data);
    }

    @Override
    public ResponseVO getGrossingFile(Long id, Integer operation, String tag) throws BuzException {
        if (id == null || id < 1) {
            throw new BuzException(BuzExceptionCode.ErrorParam);
        }
        List<PathologyFileDto> files = pathologyFileApplication.getFileByPathologyId(id, operation,tag);
        List<MicroFileVO> microFiles = new ArrayList<>();
        for (PathologyFileDto file : files) {
            MicroFileVO data = new MicroFileVO();
            data.setId(file.getId());
            data.setKeepFlag(file.getKeepFlag());
            data.setType(file.getType());
            data.setPathologyId(id);
            data.setUrl("api" + File.separator + "static" + File.separator + file.getType() + File.separator + file.getContent());
            microFiles.add(data);
        }

        return new ResponseVO(microFiles);
    }

    @Override
    public ResponseVO grossingCancel(Long id, String note) throws BuzException {

        if (id <= 0) {
            throw new BuzException(BuzExceptionCode.ErrorParam);
        }

        PathologyDto dto = pathologyApplication.getSimplePathById(id);
        if (dto == null) {
            throw new BuzException(BuzExceptionCode.PathologyNotExists);
        }
        if (!dto.getStatus().equals(PathologyStatus.PrepareGrossing.toCode())) {
            throw new BuzException(BuzExceptionCode.StatusNotMatch);
        }
        dto.setStatus(PathologyStatus.Cancel.toCode());
        pathologyApplication.updatePathology(dto);

        Date dbNow = commonRepository.getDBNow();
        PathTrackingDto pathTrackingDto = new PathTrackingDto();
        pathTrackingDto.setCreateBy(UserContext.getLoginUserID());
        pathTrackingDto.setOperation(TrackingOperation.grossingCancel.toCode());
        pathTrackingDto.setPathId(id);
        pathTrackingDto.setOperatorId(UserContext.getLoginUserID());
        pathTrackingDto.setOperationTime(dbNow);
        pathTrackingDto.setNote(note);
        pathTrackingApplication.addPathTracking(pathTrackingDto);
        return new ResponseVO();
    }

    @Override
    public ResponseVO grossingCancelCause(Long id) throws BuzException {
        if (id < 0) {
            throw new BuzException(BuzExceptionCode.ErrorParam);
        }
        PathTrackingDto pathTrackingDto = pathTrackingApplication.getPathTrackingByPathId(id);
        return new ResponseVO(pathTrackingDto);
    }

    @Override
    public ResponseVO grossingBeforePrint(GrossingPrintRequestVO grossingPrintVOS) throws BuzException {
        GrossingPrintResponseVO responseVO = new GrossingPrintResponseVO();
        if (grossingPrintVOS == null) {
            throw new BuzException(BuzExceptionCode.ErrorParam);
        }
        List<GrossingPrintVO> printData = grossingPrintVOS.getPrintData();
        if (CollectionUtils.isEmpty(printData)) {
            throw new BuzException(BuzExceptionCode.ErrorParam);
        }
        List<String> hadBlocksPathNos = new ArrayList<>();
        List<String> inExistencePathNos = new ArrayList<>();
        List<GrossingPrintDto> printDtoList = new ArrayList<>();
        List<GrossingPrintVO> printVOS = new ArrayList<>();
        GrossingPrintDto printDto;
        for (GrossingPrintVO grossingPrintVO : printData) {
            String pathNo = grossingPrintVO.getPathNo();
            List<String> blockSubIds = grossingPrintVO.getBlockSubIds();
            if (StringUtils.isBlank(pathNo) || CollectionUtils.isEmpty(blockSubIds)) {
                throw new BuzException(BuzExceptionCode.ErrorParam);
            }
            PathologyDto pathologyDto = pathologyApplication.getPathologyBySerialNumber(pathNo);
            if (pathologyDto != null) {
                List<BlockDto> blocks = pathologyDto.getBlocks();
                if (CollectionUtils.isNotEmpty(blocks)) {
                    hadBlocksPathNos.add(pathNo);
                } else {
                    printDto = new GrossingPrintDto();
                    BeanUtils.copyProperties(grossingPrintVO, printDto);
                    printDtoList.add(printDto);
                    printVOS.add(grossingPrintVO);
                }
            } else {
                inExistencePathNos.add(pathNo);
            }
        }
        if ((hadBlocksPathNos.size() > 0 || inExistencePathNos.size() > 0) && !grossingPrintVOS.getHandle()) {
            responseVO.setHadBlocksPathNos(hadBlocksPathNos);
            responseVO.setInExistencePathNos(inExistencePathNos);
        } else {
            responseVO.setPrintInfo(printVOS);
            grossingApplication.grossingBeforePrint(printDtoList);
        }
        return new ResponseVO(responseVO);
    }

    @Override
    public ResponseVO grossingPrint(int operate, List<Long> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            throw new BuzException(BuzExceptionCode.ErrorParam);
        }
        if (operate != (TrackingOperation.printEmbedBox.toCode()) && operate != (TrackingOperation.printSlide.toCode())) {
            throw new BuzException(BuzExceptionCode.ErrorParam);
        }
        grossingApplication.print(operate, ids);
        return new ResponseVO();
    }
}
