package com.lifetech.dhap.pathcloud.application.application.impl;

import com.google.gson.Gson;
import com.lifetech.dhap.pathcloud.application.application.ApplicationIhcApplication;
import com.lifetech.dhap.pathcloud.application.application.PathologyApplication;
import com.lifetech.dhap.pathcloud.application.application.condition.ApplicationIhcCondition;
import com.lifetech.dhap.pathcloud.application.application.condition.IhcBlockCondition;
import com.lifetech.dhap.pathcloud.application.application.condition.PrintIhcCondition;
import com.lifetech.dhap.pathcloud.application.application.dto.*;
import com.lifetech.dhap.pathcloud.application.application.infrastructure.code.BlockSpecialDye;
import com.lifetech.dhap.pathcloud.application.application.infrastructure.code.IhcApplicationStatus;
import com.lifetech.dhap.pathcloud.application.application.infrastructure.code.SpecialApplySource;
import com.lifetech.dhap.pathcloud.common.data.SpecialDyeFix;
import com.lifetech.dhap.pathcloud.application.domain.ApplicationIhcRepository;
import com.lifetech.dhap.pathcloud.application.domain.BlockIhcRepository;
import com.lifetech.dhap.pathcloud.application.domain.model.ApplicationIhc;
import com.lifetech.dhap.pathcloud.application.domain.model.BlockIhc;
import com.lifetech.dhap.pathcloud.application.domain.model.BlockIhcExtend;
import com.lifetech.dhap.pathcloud.common.constant.Config;
import com.lifetech.dhap.pathcloud.common.data.PathologyStatus;
import com.lifetech.dhap.pathcloud.common.data.SequenceName;
import com.lifetech.dhap.pathcloud.common.data.SpecialApplyType;
import com.lifetech.dhap.pathcloud.common.domain.CommonRepository;
import com.lifetech.dhap.pathcloud.common.exception.BuzException;
import com.lifetech.dhap.pathcloud.common.exception.BuzExceptionCode;
import com.lifetech.dhap.pathcloud.common.utils.ListUtil;
import com.lifetech.dhap.pathcloud.security.UserContext;
import com.lifetech.dhap.pathcloud.setting.application.ParamSettingApplication;
import com.lifetech.dhap.pathcloud.setting.application.dto.PathNoRuleDto;
import com.lifetech.dhap.pathcloud.tracking.application.BlockApplication;
import com.lifetech.dhap.pathcloud.tracking.application.SpecialApplicationApplication;
import com.lifetech.dhap.pathcloud.tracking.application.TrackingApplication;
import com.lifetech.dhap.pathcloud.tracking.application.dto.BlockDto;
import com.lifetech.dhap.pathcloud.tracking.application.dto.SpecialApplicationDto;
import com.lifetech.dhap.pathcloud.tracking.application.dto.TrackingDto;
import com.lifetech.dhap.pathcloud.tracking.application.infrastructure.code.TrackingOperation;
import net.sf.json.JSONArray;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by LiuMei on 2017-04-01.
 */
@Service
public class ApplicationIhcApplicationImpl implements ApplicationIhcApplication {

    Gson gson = new Gson();

    @Autowired
    private ApplicationIhcRepository applicationIhcRepository;

    @Autowired
    private BlockIhcRepository blockIhcRepository;

    @Autowired
    private BlockApplication blockApplication;

    @Autowired
    private TrackingApplication trackingApplication;

    @Autowired
    private PathologyApplication pathologyApplication;

    @Autowired
    private ParamSettingApplication paramSettingApplication;

    @Autowired
    private CommonRepository commonRepository;

    @Autowired
    private SpecialApplicationApplication specialApplicationApplication;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createApplication(IhcApplicationDto ihcApplicationDto) throws BuzException {
        if (ihcApplicationDto != null) {
            ApplicationIhc applicationIhc = new ApplicationIhc();
            List<IhcBlockDto> ihcBlocks = ihcApplicationDto.getIhcBlocks();
            if (CollectionUtils.isNotEmpty(ihcBlocks)) {
                Date dbNow = commonRepository.getDBNow();
                long userId = UserContext.getLoginUserID();
                BeanUtils.copyProperties(ihcApplicationDto, applicationIhc);
                applicationIhc.setCreateBy(userId);
                applicationIhcRepository.insert(applicationIhc);
                long applicationId = applicationIhcRepository.last();
                ihcApplicationDto.setId(applicationId);
                ihcApplicationDto.setUpdateBy(userId);
                ihcApplicationDto.setUpdateTime(dbNow);
                addBlock(ihcApplicationDto, ihcBlocks);
            }
        }
    }

    @Transactional
    private void addBlock(IhcApplicationDto ihcApplicationDto, List<IhcBlockDto> ihcBlockDtoList) throws BuzException {
        if (ihcApplicationDto != null && CollectionUtils.isNotEmpty(ihcBlockDtoList)) {
            BlockIhc blockIhc;
            Long userId = ihcApplicationDto.getUpdateBy();
            Long id = ihcApplicationDto.getId();
            Date dbNow = ihcApplicationDto.getUpdateTime();
            for (IhcBlockDto ihcBlockDto : ihcBlockDtoList) {
                long blockId = ihcBlockDto.getBlockId();
                Integer specialDye = ihcBlockDto.getSpecialDye();
                List<String> ihcMarker = ihcBlockDto.getIhcMarker();
                if (CollectionUtils.isNotEmpty(ihcMarker)) {
                    if (ListUtil.checkStringRepeat(ihcMarker)) {
                        throw new BuzException(BuzExceptionCode.MarkerRepetition);
                    }
                    for (String marker : ihcMarker) {
                        Boolean repeat = checkRepeatMarkerByBlockId(blockId, marker);
                        if (repeat) {
                            throw new BuzException(BuzExceptionCode.MarkerRepetition);
                        }
                    }
                }
                if (specialDye == SpecialDyeFix.IHC.toCode()) {
                    //IHC
                    SpecialApplicationDto specialApplicationDto = specialApplicationApplication.getByPathNoAndType(ihcBlockDto.getSerialNumber(), SpecialApplyType.IHC.toCode());
                    if (specialApplicationDto != null && specialApplicationDto.getStatus() >= PathologyStatus.Report.toCode()) {
                        throw new BuzException(BuzExceptionCode.CanNotApply);
                    }
                } else if (specialDye > SpecialDyeFix.IHC.toCode()) {
                    //特染
                    SpecialApplicationDto specialApplicationDto = specialApplicationApplication.getByPathNoAndType(ihcBlockDto.getSerialNumber(), SpecialApplyType.Dye.toCode());
                    if (specialApplicationDto != null && specialApplicationDto.getStatus() >= PathologyStatus.Report.toCode()) {
                        throw new BuzException(BuzExceptionCode.CanNotApply);
                    }
                }
                blockIhc = new BlockIhc();
                BeanUtils.copyProperties(ihcBlockDto, blockIhc);
                if (ihcApplicationDto.getSource() == null) {
                    blockIhc.setSource(SpecialApplySource.SpecialApply.toCode());
                } else {
                    blockIhc.setSource(SpecialApplySource.Diagnose.toCode()); //诊断工作站申请
                }
                blockIhc.setCreateBy(userId);
                blockIhc.setStatus(IhcApplicationStatus.PrepareConfirm.toCode());
                blockIhc.setIhcId(id);
                blockIhc.setIhcMarker(gson.toJson(ihcMarker));
                blockIhcRepository.insert(blockIhc);
                long blockIhcId = blockIhcRepository.last();
                TrackingDto track = new TrackingDto();
                track.setCreateBy(userId);
                track.setBlockId(blockId);
                track.setOperatorId(userId);
                track.setOperation(TrackingOperation.specialDyeApply.toCode());
                track.setNote(ihcBlockDto.getNote());
                track.setNoteType(gson.toJson(ihcMarker));
                track.setInstrumentId(blockIhcId);//InstrumentId字段记录对应申请blockIhc表ID
                track.setOperationTime(dbNow);
                trackingApplication.addTracking(track);
            }
        }

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateApplication(IhcApplicationDto ihcApplicationDto) throws BuzException {
        if (ihcApplicationDto != null) {
            Long id = ihcApplicationDto.getId();
            List<IhcBlockDto> ihcBlockDtoList = ihcApplicationDto.getIhcBlocks();
            if (id != null && CollectionUtils.isNotEmpty(ihcBlockDtoList)) {
                Date dbNow = commonRepository.getDBNow();
                long userId = UserContext.getLoginUserID();
                ApplicationIhc applicationIhc = applicationIhcRepository.selectByPrimaryKey(id);
                if (applicationIhc == null) {
                    throw new BuzException(BuzExceptionCode.RecordNotExists);
                }
                applicationIhc.setUpdateBy(userId);
                applicationIhcRepository.updateByPrimaryKey(applicationIhc);
                ihcApplicationDto.setUpdateBy(userId);
                ihcApplicationDto.setUpdateTime(dbNow);
                addBlock(ihcApplicationDto, ihcBlockDtoList);
            }
        }
    }

    @Override
    public IhcApplicationDto getApplicationIhcByPathNoAndType(String pathNo, Integer type) {
        IhcApplicationDto ihcApplicationDto = null;
        if (StringUtils.isNotBlank(pathNo) && type != null) {
            ihcApplicationDto = new IhcApplicationDto();
            ApplicationIhc applicationIhc = null;
            if (type.equals(SpecialApplyType.IHC.toCode())) {
                applicationIhc = applicationIhcRepository.selectByPathNoAndSpecialDye(pathNo, SpecialDyeFix.IHC.toCode());
            } else if (type.equals(SpecialApplyType.Dye.toCode())) {
                applicationIhc = applicationIhcRepository.selectSpecialDyeByPathNo(pathNo);
            }
            if (applicationIhc != null) {
                BeanUtils.copyProperties(applicationIhc, ihcApplicationDto);
            }
        }
        return ihcApplicationDto;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void cancelApplication(IhcBlockDto ihcBlockDto) throws BuzException {
        if (ihcBlockDto != null) {
            long userId = UserContext.getLoginUserID();
            Date dbNow = commonRepository.getDBNow();
            BlockIhc blockIhc = new BlockIhc();
            BeanUtils.copyProperties(ihcBlockDto, blockIhc);
            blockIhc.setUpdateBy(userId);
            blockIhcRepository.updateByPrimaryKey(blockIhc);

            long blockId = ihcBlockDto.getBlockId();
            TrackingDto track = new TrackingDto();
            track.setCreateBy(userId);
            track.setBlockId(blockId);
            track.setOperatorId(userId);
            track.setOperation(TrackingOperation.cancelDye.toCode());
            track.setInstrumentId(ihcBlockDto.getId());//InstrumentId字段记录蜡块申请染色ID
            track.setOperationTime(dbNow);
            trackingApplication.addTracking(track);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void cancelApplication(long ihcId) {
        IhcBlockCondition condition = new IhcBlockCondition();
        condition.setIhcId(ihcId);
        List<IhcBlockDto> ihcBlockDtoList = getIhcBlockByCondition(condition);
        if (CollectionUtils.isNotEmpty(ihcBlockDtoList)) {
            for (IhcBlockDto ihcBlockDto : ihcBlockDtoList) {
                Integer status = ihcBlockDto.getStatus();
                if (!status.equals(IhcApplicationStatus.PrepareConfirm.toCode())) {
                    throw new BuzException(BuzExceptionCode.StatusNotMatch);
                }
                ihcBlockDto.setStatus(IhcApplicationStatus.Cancel.toCode());
                cancelApplication(ihcBlockDto);
            }
        }
    }

    @Override
    public IhcBlockDto getIhcBlockById(long ihcBlockId) {
        return ihcBlockToDto(blockIhcRepository.selectByPrimaryKey(ihcBlockId));
    }

    @Override
    public List<IhcBlockDto> getIhcBlocksByNumber(String number) {
        return ihcBlocksToDto(blockIhcRepository.selectByNumber(number));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void confirmApplication(List<IhcBlockDto> ihcBlockDtoList) {
        if (CollectionUtils.isNotEmpty(ihcBlockDtoList)) {
            for (IhcBlockDto ihcBlockDto : ihcBlockDtoList) {
                if (ihcBlockDto != null) {
                    PathNoRuleDto pathNoRule = paramSettingApplication.getPathNoRule();
                    if (pathNoRule == null) {
                        throw new BuzException(BuzExceptionCode.SettingError);
                    }
                    String formatDate = new SimpleDateFormat(pathNoRule.getTime()).format(new Date());

                    Date dbNow = commonRepository.getDBNow();
                    long userId = UserContext.getLoginUserID();

                    Long blockId = ihcBlockDto.getBlockId();
                    BlockDto blockDto = blockApplication.getBlockById(blockId);
                    if (blockDto != null) {
                /*
                 *蜡块的specialDye属性
                 * 0代表未申请染色,默认值
                 * 1代表申请了染色
                 * 具体染色申请查询blockIhc表
                 */
                        blockDto.setSpecialDye(BlockSpecialDye.HadApply.toCode());
                        blockDto.setMarker(null);
                        Integer status = blockDto.getStatus();
                        if (status.equals(PathologyStatus.PrepareArchiving.toCode()) || status.equals(PathologyStatus.Ending.toCode())) {
                            blockDto.setStatus(PathologyStatus.PrepareSection.toCode());
                        }
                        blockDto.setUpdateBy(userId);
                        blockApplication.updateBlock(blockDto);

                        TrackingDto track = new TrackingDto();
                        track.setCreateBy(userId);
                        track.setBlockId(blockId);
                        track.setOperatorId(userId);
                        track.setOperation(TrackingOperation.dyeConfirm.toCode());
                        track.setNote(ihcBlockDto.getNote());
                        track.setNoteType(gson.toJson(ihcBlockDto.getIhcMarker()));
                        Long ihcBlockId = ihcBlockDto.getId();
                        track.setSecOperatorId(ihcBlockId);//secOperatorId字段记录蜡块申请ID
                        track.setInstrumentId(ihcBlockDto.getIhcId());//instrumentId字段记录染色申请的ID
                        track.setOperationTime(dbNow);
                        trackingApplication.addTracking(track);

                        PathologyDto pathologyDto = pathologyApplication.getSimplePathById(blockDto.getPathId());
                        pathologyDto.setAssignDiagnose(ihcBlockDto.getCreateBy());
                        Object pathology = blockApplication.getUpdatePathology(pathologyDto); //查询需不需要更改病理状态
                        if (pathology != null) {
                            PathologyDto data = (PathologyDto) pathology;
                            pathologyDto.setStatus(data.getStatus());
                        }
                        pathologyApplication.updatePathology(pathologyDto);

                        BlockIhc blockIhc = new BlockIhc();
                        blockIhc.setId(ihcBlockId);
                        blockIhc.setStatus(IhcApplicationStatus.Confirm.toCode());
                        blockIhc.setUpdateBy(userId);
                        Integer source = ihcBlockDto.getSource();
                        if (source.equals(SpecialApplySource.Diagnose.toCode())) { //诊断工作站申请
                            String pathNo = pathologyDto.getSerialNumber();
                            Integer specialDye = ihcBlockDto.getSpecialDye();
                            Integer type = null;
                            if (specialDye != SpecialDyeFix.White.toCode() && specialDye != SpecialDyeFix.HE.toCode() && specialDye != SpecialDyeFix.IHC.toCode()) {
                                type = SpecialApplyType.Dye.toCode();
                            } else if (specialDye == SpecialDyeFix.IHC.toCode()) {
                                type = SpecialApplyType.IHC.toCode();
                            }
                            SpecialApplicationDto specialApplicationDto = specialApplicationApplication.getByPathNoAndType(pathNo, type);
                            String number = null;
                            if (specialApplicationDto == null) {
                                //添加特殊申请记录
                                specialApplicationDto = new SpecialApplicationDto();
                                specialApplicationDto.setPathNo(pathNo);
                                specialApplicationDto.setStatus(PathologyStatus.PrepareSection.toCode());
                                Long createBy = getIhcBlockById(ihcBlockId).getCreateBy();
                                specialApplicationDto.setCreateBy(createBy);
                                specialApplicationDto.setAssignDiagnose(createBy);
                                specialApplicationDto.setCauseId(pathologyDto.getId());
                                specialApplicationDto.setResult(ihcBlockDto.getResult());
                                String formatDigit = "%04d";
                                if (type.equals(SpecialApplyType.Dye.toCode())) {
                                    //非白片/HE/免疫组化(即为特染)
                                    specialApplicationDto.setType(SpecialApplyType.Dye.toCode());
                                    number = Config.DYELETTER + formatDate + String.format(formatDigit, pathologyApplication.getNextValue(SequenceName.DyeNumber.toString()));
                                    specialApplicationDto.setNumber(number);
                                } else if (type.equals(SpecialApplyType.IHC.toCode())) {
                                    //免疫组化
                                    specialApplicationDto.setType(SpecialApplyType.IHC.toCode());
                                    number = Config.IHCLETTER + formatDate + String.format(formatDigit, pathologyApplication.getNextValue(SequenceName.IhcNumber.toString()));
                                    specialApplicationDto.setNumber(number);
                                }
                                specialApplicationApplication.add(specialApplicationDto);
                            } else {
                                number = specialApplicationDto.getNumber();
                            }
                            blockIhc.setNumber(number);
                        }
                        blockIhcRepository.updateByPrimaryKey(blockIhc);
                    }
                }
            }
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delayApplication(List<Long> ihcBlockIds) {
        if (CollectionUtils.isNotEmpty(ihcBlockIds)) {
            blockIhcRepository.updateStatus(ihcBlockIds, UserContext.getLoginUserID(), IhcApplicationStatus.Delay.toCode());
        }
    }

    @Override
    public List<IhcBlockDto> getIhcBlockByCondition(IhcBlockCondition condition) {
        List<BlockIhcExtend> blockIHCs = blockIhcRepository.selectByCondition(condition);
        List<IhcBlockDto> ihcBlockDtoList = null;
        if (CollectionUtils.isNotEmpty(blockIHCs)) {
            ihcBlockDtoList = new ArrayList<>();
            for (BlockIhc blockIhc : blockIHCs) {
                ihcBlockDtoList.add(ihcBlockToDto(blockIhc));
            }
        }
        return ihcBlockDtoList;
    }

    @Override
    public List<Long> getCurrentIhcIdsByBlockId(long blockId) {
        return blockIhcRepository.selectCurrentIhcIdsByBlockId(blockId);
    }

    @Override
    public List<IhcApplicationQueryDto> getIHCs(ApplicationIhcCondition con) {
        List<IhcApplicationQueryDto> data = blockIhcRepository.getIHCs(con);
        return data;
    }

    @Override
    public Long getIHCsTotal(ApplicationIhcCondition con) {
        return blockIhcRepository.getIHCsTotal(con);
    }

    @Override
    public void updateIhcBlock(IhcBlockDto ihcBlockDto) {
        if (ihcBlockDto != null) {
            Long id = ihcBlockDto.getId();
            if (id != null) {
                BlockIhc blockIhc = blockIhcRepository.selectByPrimaryKey(id);
                blockIhc.setStatus(ihcBlockDto.getStatus());
                blockIhc.setUpdateBy(ihcBlockDto.getUpdateBy());
                blockIhcRepository.updateByPrimaryKey(blockIhc);
            }
        }
    }

    @Override
    public List<PrintIhcsDetailDto> getPrintIhcs(PrintIhcCondition con) {

        List<PrintIhcsDetailDto> dtoList = blockIhcRepository.getPrintIhcs(con);
        if (CollectionUtils.isNotEmpty(dtoList)) {
            for (PrintIhcsDetailDto detailDto : dtoList) {
                detailDto.setSpecialDyeDesc(paramSettingApplication.getSpecialDyeDesc(detailDto.getSpecialDye()));
                detailDto.setApplyId(trackingApplication.getBlockIhcTrackingIdByBlockIhcId(detailDto.getBlockIhcId()));
                detailDto.setMarker(detailDto.getReagent());
            }
        }
        return dtoList;
    }

    @Override
    public Long getPrintIhcsTotal(PrintIhcCondition con) {
        return blockIhcRepository.getPrintIhcsTotal(con);
    }

    @Override
    public Integer getMinSlideStatusByTrackingId(long trackingId) {
        return blockIhcRepository.selectMinSlideStatusByTracingId(trackingId);
    }

    @Override
    public Boolean checkRepeatMarkerByBlockId(long blockId, String marker) {
        Long id = blockIhcRepository.selectIdByMarkerAndBlockId(blockId, marker);
        if (id == null) {
            return false;
        } else {
            return true;
        }
    }


    private IhcBlockDto ihcBlockToDto(BlockIhc blockIhc) {
        if (blockIhc != null) {
            IhcBlockDto ihcBlockDto = new IhcBlockDto();
            BeanUtils.copyProperties(blockIhc, ihcBlockDto);
            ihcBlockDto.setSpecialDyeDesc(paramSettingApplication.getSpecialDyeDesc(ihcBlockDto.getSpecialDye()));
            String ihcMarker = blockIhc.getIhcMarker();
            if (ihcMarker != null && ihcMarker.length() > 0) {
                ihcBlockDto.setIhcMarker(JSONArray.fromObject(ihcMarker));
            }
            ihcBlockDto.setStatusDesc(IhcApplicationStatus.getNameByCode(ihcBlockDto.getStatus()));
            return ihcBlockDto;
        }
        return null;
    }

    private List<IhcBlockDto> ihcBlocksToDto(List<BlockIhc> blockIhcList) {
        List<IhcBlockDto> blockDtoList = null;
        if (CollectionUtils.isNotEmpty(blockIhcList)) {
            blockDtoList = new ArrayList<>();
            for (BlockIhc blockIhc : blockIhcList) {
                blockDtoList.add(ihcBlockToDto(blockIhc));
            }
        }
        return blockDtoList;
    }


    @Override
    public List<IhcApplicationQueryDto> getApplicationIHCs(ApplicationIhcCondition con) {
        List<IhcApplicationQueryDto> data = blockIhcRepository.getApplicationIHCs(con);
        return data;
    }

    @Override
    public Long getApplicationIHCsTotal(ApplicationIhcCondition con) {
        return blockIhcRepository.getApplicationIHCsTotal(con);
    }
}
