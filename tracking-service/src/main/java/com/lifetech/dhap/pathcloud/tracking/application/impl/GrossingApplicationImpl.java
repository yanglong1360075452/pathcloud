package com.lifetech.dhap.pathcloud.tracking.application.impl;

import com.lifetech.dhap.pathcloud.application.application.PathologyApplication;
import com.lifetech.dhap.pathcloud.application.application.dto.PathologyDto;
import com.lifetech.dhap.pathcloud.common.constant.Config;
import com.lifetech.dhap.pathcloud.common.data.PathologyStatus;
import com.lifetech.dhap.pathcloud.common.data.SpecialDyeFix;
import com.lifetech.dhap.pathcloud.common.domain.CommonRepository;
import com.lifetech.dhap.pathcloud.common.exception.BuzException;
import com.lifetech.dhap.pathcloud.common.exception.BuzExceptionCode;
import com.lifetech.dhap.pathcloud.common.utils.ListUtil;
import com.lifetech.dhap.pathcloud.file.application.PathologyFileApplication;
import com.lifetech.dhap.pathcloud.file.application.dto.PathologyFileDto;
import com.lifetech.dhap.pathcloud.security.UserContext;
import com.lifetech.dhap.pathcloud.setting.application.ParamSettingApplication;
import com.lifetech.dhap.pathcloud.setting.application.infrastructure.code.SystemKey;
import com.lifetech.dhap.pathcloud.tracking.application.*;
import com.lifetech.dhap.pathcloud.tracking.application.condition.GrossingCon;
import com.lifetech.dhap.pathcloud.tracking.application.condition.TrackingCondition;
import com.lifetech.dhap.pathcloud.tracking.application.dto.*;
import com.lifetech.dhap.pathcloud.tracking.application.infrastructure.code.BlockScoreType;
import com.lifetech.dhap.pathcloud.tracking.application.infrastructure.code.TrackingOperation;
import com.lifetech.dhap.pathcloud.tracking.infrastructure.code.FileType;
import com.lifetech.dhap.pathcloud.user.application.UserApplication;
import com.lifetech.dnap.pathcloud.reagent.application.ReagentApplication;
import com.lifetech.dnap.pathcloud.reagent.application.StoreApplication;
import com.lifetech.dnap.pathcloud.reagent.application.condition.StoreCondition;
import com.lifetech.dnap.pathcloud.reagent.application.dto.ReagentDto;
import com.lifetech.dnap.pathcloud.reagent.application.dto.ReagentRecordDto;
import com.lifetech.dnap.pathcloud.reagent.application.dto.StoreDto;
import com.lifetech.dnap.pathcloud.reagent.application.infrastructure.code.ReagentCategory;
import com.lifetech.dnap.pathcloud.reagent.application.infrastructure.code.ReagentType;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @author eric.guo@ostengar.com
 * @version 1.0
 * @time 2016-12-07-14:09
 */
@Service("grossingApplication")
public class GrossingApplicationImpl implements GrossingApplication {

    @Autowired
    private PathologyApplication pathologyApplication;

    @Autowired
    private BlockApplication blockApplication;

    @Autowired
    private TrackingApplication trackingApplication;

    @Autowired
    private PathologyFileApplication pathologyFileApplication;

    @Autowired
    private ParamSettingApplication paramSettingApplication;

    @Autowired
    private CommonRepository commonRepository;

    @Autowired
    private UserApplication userApplication;

    @Autowired
    private SpecialApplicationApplication specialApplicationApplication;

    @Autowired
    private ReagentApplication reagentApplication;

    @Autowired
    private StoreApplication storeApplication;

    @Autowired
    private BlockScoreApplication blockScoreApplication;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void grossingSave(GrossingSaveDto data) {
        PathologyDto pathologyDto = pathologyApplication.getSimplePathById(data.getPathologyId());
        pathologyDto.setBingdongNote(data.getBingdongNote());
        pathologyDto.setJujianNote(data.getJujianNote());
        pathologyDto.setId(data.getPathologyId());
        List<BlockDto> blocks = data.getBlocks();
        boolean blockEmpty = CollectionUtils.isNotEmpty(blocks);
        pathologyDto.setUpdateBy(data.getUpdateBy());
        if (blockEmpty) {
            pathologyDto.setStatus(PathologyStatus.PrepareGrossingConfirm.toCode());
        }
        pathologyDto.setAssignGrossing(data.getOperatorId());
        pathologyApplication.updatePathology(pathologyDto);
        List<Long> blockIds = blockApplication.getNormalBlocksIdByPathId(data.getPathologyId());//查询该病理号是否已经取材
        List<Long> blockIdSave = new ArrayList<>();//保留的取材记录
        if (blockEmpty) {
            List<TrackingDto> trackingDtoList = new ArrayList<>();
            Date dbNow = commonRepository.getDBNow();
            Long userID = UserContext.getLoginUserID();
            for (BlockDto blockDto : blocks) {
                boolean locked = trackingApplication.getBasketNumberStatus(blockDto.getBasketNumber());
                if (locked) {
                    throw new BuzException(BuzExceptionCode.BasketLocked, "脱水篮" + blockDto.getBasketNumber() + "已被使用");
                }
                String grossingDoctor = userApplication.getUserSimpleInfoById(data.getOperatorId()).getFirstName();
                Long id = blockDto.getId();
                if (id == null) {//新增取材记录
                    blockDto.setStatus(PathologyStatus.PrepareGrossingConfirm.toCode());
                    blockDto.setSerialNumber(pathologyDto.getSerialNumber() + blockDto.getSubId());
                    /*
                     *蜡块的specialDye属性
                     * 0代表未申请染色,默认值
                     * 1代表申请了染色
                     * 具体染色申请查询blockIhc表
                     */
                    blockDto.setSpecialDye(Config.dyeType);
                    List<String> marker = new ArrayList<>();
                    marker.add(Config.dyeMarker);//默认标记物
                    blockDto.setMarker(marker);
                    blockDto.setEmbedPause(false);
                    blockDto.setUpdateBy(userID);
                    blockDto.setUpdateTime(dbNow);
                    blockApplication.addBlock(blockDto);
                    //添加tracking取材记录
                    TrackingDto track = new TrackingDto();
                    track.setCreateBy(userID);
                    track.setBlockId(blockDto.getId());
                    track.setOperatorId(data.getOperatorId());
                    track.setSecOperatorId(data.getSecOperatorId());
                    track.setOperation(TrackingOperation.grossing.toCode());
                    track.setOperationTime(dbNow);
                    track.setManualFlag(data.getManualFlag());
                    track.setOperatorName(grossingDoctor);
                    track.setInstrumentId(blockDto.getBasketNumber());
                    trackingDtoList.add(track);
                } else {//已有取材记录
                    Integer biaoshi = blockDto.getBiaoshi();
                    Long basketNumber = blockDto.getBasketNumber();
                    String bodyPart = blockDto.getBodyPart();
                    Integer count = blockDto.getCount();
                    String note = blockDto.getNote();
                    Integer unit = blockDto.getUnit();
                    String subId = blockDto.getSubId();
                    String number = blockDto.getNumber();
                    BlockDto blockDtoDB = blockApplication.getBlockById(id);
                    if (biaoshi == null || StringUtils.isBlank(subId) || basketNumber == null) {
                        throw new BuzException(BuzExceptionCode.ErrorParam);
                    }
                    if (!biaoshi.equals(blockDtoDB.getBiaoshi()) || !basketNumber.equals(blockDtoDB.getBasketNumber())
                            || !blockDtoDB.getBodyPart().equals(bodyPart) || !blockDtoDB.getCount().equals(count)
                            || !blockDtoDB.getNote().equals(note) || !blockDtoDB.getUnit().equals(unit) || !blockDtoDB.getSubId().equals(subId)
                            || !blockDtoDB.getNumber().equals(number)) {//原取材记录有修改
                        blockDto.setStatus(PathologyStatus.PrepareGrossingConfirm.toCode());
                        blockDto.setSerialNumber(pathologyDto.getSerialNumber() + subId);
                        blockDto.setUpdateBy(userID);
                        blockDto.setUpdateTime(dbNow);
                        blockApplication.updateBlock(blockDto);
                        //删除之前的取材记录tracing表数据
                        TrackingCondition trackingCondition = new TrackingCondition();
                        trackingCondition.setBlockId(id);
                        trackingCondition.setOperation(TrackingOperation.grossing.toCode());
                        trackingApplication.deleteTrackingByCondition(trackingCondition);

                        //添加tracking取材记录
                        TrackingDto track = new TrackingDto();
                        track.setCreateBy(userID);
                        track.setBlockId(blockDto.getId());
                        track.setOperatorId(data.getOperatorId());
                        track.setOperatorName(grossingDoctor);
                        track.setSecOperatorId(data.getSecOperatorId());
                        track.setOperation(TrackingOperation.grossing.toCode());
                        track.setOperationTime(dbNow);
                        track.setManualFlag(data.getManualFlag());
                        track.setInstrumentId(blockDto.getBasketNumber());
                        trackingDtoList.add(track);
                    }
                    blockIdSave.add(blockDto.getId());
                }
            }
            if (CollectionUtils.isNotEmpty(trackingDtoList)) {
                //批量添加tracking
                trackingApplication.addTrackingBatch(trackingDtoList);
            }
        }
        if (CollectionUtils.isNotEmpty(blockIds)) {
            List<Long> blocksDelete = ListUtil.minus(blockIds, blockIdSave);//删除的取材记录
            if (CollectionUtils.isNotEmpty(blocksDelete)) {
                for (Long blockId : blocksDelete) {
                    BlockDto blockDto = blockApplication.getBlockById(blockId);
                    Integer status = blockDto.getStatus();
                    if (PathologyStatus.PrepareGrossingConfirm.toCode().equals(status) || PathologyStatus.PrepareGrossing.toCode().equals(status)) {
                        blockApplication.deleteBlockById(blockId);
                    }
                }
            }
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void grossingPrint(GrossingSaveDto data) {
        Long userID = UserContext.getLoginUserID();
        Date dbNow = commonRepository.getDBNow();
        PathologyDto pathologyDto = pathologyApplication.getSimplePathById(data.getPathologyId());
        List<BlockDto> blocks = data.getBlocks();
        if (CollectionUtils.isNotEmpty(blocks)) {
            List<TrackingDto> trackingDtoList = new ArrayList<>();
            for (BlockDto blockDto : blocks) {
                Long id = blockDto.getId();
                blockDto.setUpdateBy(userID);
                blockDto.setUpdateTime(dbNow);
                if (id == null) {
                    //首次打印
                    blockDto.setStatus(PathologyStatus.PrepareGrossing.toCode());
                    blockDto.setEmbedPause(false);
                    blockDto.setSerialNumber(pathologyDto.getSerialNumber() + blockDto.getSubId());
                    /*
                     *蜡块的specialDye属性
                     * 0代表未申请染色,默认值
                     * 1代表申请了染色
                     * 具体染色申请查询blockIhc表
                     */
                    blockDto.setSpecialDye(Config.dyeType);
                    List<String> marker = new ArrayList<>();
                    marker.add(Config.dyeMarker);//默认标记物
                    blockDto.setMarker(marker);
                    blockDto.setPrint(1);
                    blockApplication.addSlide(blockDto);
                } else {
                    blockDto = getUpdatePrint(blockDto);
                    blockApplication.updateBlock(blockDto);
                }
                trackingDtoList.add(addPrint(blockDto, TrackingOperation.printEmbedBox.toCode()));
            }
            if (CollectionUtils.isNotEmpty(trackingDtoList)) {
                //批量添加tracking
                trackingApplication.addTrackingBatch(trackingDtoList);
            }
        }
    }

    private BlockDto getUpdatePrint(BlockDto blockDto) {
        BlockDto dbBlockDto = null;
        if (blockDto != null) {
            dbBlockDto = blockApplication.getBlockById(blockDto.getId());
            dbBlockDto.setPrint(blockDto.getPrint() + 1);
            dbBlockDto.setUpdateTime(blockDto.getUpdateTime());
            dbBlockDto.setUpdateBy(blockDto.getUpdateBy());
            dbBlockDto.setBodyPart(blockDto.getBodyPart());
            dbBlockDto.setNote(blockDto.getNote());
            dbBlockDto.setSubId(blockDto.getSubId());
            dbBlockDto.setUnit(blockDto.getUnit());
            dbBlockDto.setCount(blockDto.getCount());
        }
        return dbBlockDto;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public TrackingDto addPrint(BlockDto blockDto, Integer operate) {
        TrackingDto printTracking = null;
        if (blockDto != null) {
            Long userId = blockDto.getUpdateBy();
            Date now = blockDto.getUpdateTime();
            printTracking = new TrackingDto();
            printTracking.setCreateBy(userId);
            printTracking.setBlockId(blockDto.getId());
            printTracking.setOperatorId(userId);
            printTracking.setOperation(operate);
            printTracking.setOperationTime(now);

            Double consumableUsage = 1D;

            StoreCondition condition = new StoreCondition();
            condition.setCategory(ReagentCategory.Consumable.toCode());
            if (operate.equals(TrackingOperation.printEmbedBox.toCode())) {
                condition.setType(ReagentType.EmbeddingBox.toCode());
                StoreDto currentUse = storeApplication.getCurrentUse(condition);
                if (currentUse != null) {
                    //更新库存用量
                    Long id = currentUse.getId();
                    currentUse.setUsedCapacity(currentUse.getUsedCapacity() + consumableUsage);
                    currentUse.setUpdateBy(userId);
                    storeApplication.updateStore(currentUse);
                    //添加包埋盒使用记录
                    ReagentRecordDto reagentRecordDto = new ReagentRecordDto();
                    reagentRecordDto.setStoreId(id);
                    reagentRecordDto.setDosage(consumableUsage);
                    reagentRecordDto.setCreateBy(userId);
                    reagentApplication.addReagentRecord(reagentRecordDto);
                }
            } else if (operate.equals(TrackingOperation.printSlide.toCode())) {
                condition.setType(ReagentType.NormalSlide.toCode());
                StoreDto currentUse = storeApplication.getCurrentUse(condition);
                if (currentUse != null) {
                    Long id = currentUse.getId();
                    currentUse.setUsedCapacity(currentUse.getUsedCapacity() + consumableUsage);
                    currentUse.setUpdateBy(userId);
                    storeApplication.updateStore(currentUse);
                    //添加玻片使用记录
                    ReagentRecordDto reagentRecordDto = new ReagentRecordDto();
                    reagentRecordDto.setStoreId(id);
                    reagentRecordDto.setCreateBy(userId);
                    reagentRecordDto.setDosage(consumableUsage);
                    reagentApplication.addReagentRecord(reagentRecordDto);
                }
                Integer specialDye = blockDto.getSpecialDye();
                if (specialDye.equals(SpecialDyeFix.IHC.toCode())) {
                    String slideMarker = blockDto.getSlideMarker();
                    String usageStr = paramSettingApplication.getContentByKey(SystemKey.ReagentUsage.toString());
                    Double usage = Double.valueOf(usageStr);
                    recordIHCReagent(slideMarker, usage);
                }
            }
        }
        return printTracking;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void recordIHCReagent(String marker, Double usage) {
        ReagentDto reagent = reagentApplication.getReagentByTypeAndName(ReagentType.IHCReagent.toCode(), marker);
        if (reagent != null) {
            Integer category = reagent.getCategory();
            StoreCondition condition = new StoreCondition();
            condition.setCategory(category);
            condition.setType(ReagentType.IHCReagent.toCode());
            condition.setName(marker);
            StoreDto currentUse = storeApplication.getCurrentUse(condition);
            long userId = UserContext.getLoginUserID();
            if (currentUse != null) {
                Double usedCapacity = currentUse.getUsedCapacity();
                Long id = currentUse.getId();
                Double totalCapacity = currentUse.getTotalCapacity();
                Double thisUsed = usedCapacity + usage;
                if (thisUsed <= totalCapacity) {
                    //剩余试剂足够扣除此次用量
                    currentUse.setUpdateBy(userId);
                    currentUse.setUsedCapacity(thisUsed);
                    storeApplication.updateStore(currentUse);
                    //添加试剂使用记录
                    ReagentRecordDto reagentRecordDto = new ReagentRecordDto();
                    reagentRecordDto.setStoreId(id);
                    reagentRecordDto.setDosage(usage);
                    reagentRecordDto.setCreateBy(userId);
                    reagentApplication.addReagentRecord(reagentRecordDto);
                } else {
                    /*
                     * 剩余试剂不够扣除此次用量
                     * 先把当前查出的库存使用完
                     * 再重新查询下一库存,扣除剩余的用量
                     */
                    currentUse.setUsedCapacity(totalCapacity);
                    currentUse.setUpdateBy(userId);
                    storeApplication.updateStore(currentUse);
                    Double thisDosage = totalCapacity - usedCapacity;
                    //添加试剂使用记录
                    ReagentRecordDto reagentRecordDto = new ReagentRecordDto();
                    reagentRecordDto.setStoreId(id);
                    reagentRecordDto.setDosage(thisDosage);
                    reagentRecordDto.setCreateBy(userId);
                    reagentApplication.addReagentRecord(reagentRecordDto);
                    recordIHCReagent(marker, usage - thisDosage);
                }
            }
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void grossingConfirm(Map<Long, Integer> blocksId) {
        if (MapUtils.isEmpty(blocksId)) {
            throw new BuzException(BuzExceptionCode.ErrorParam);
        }
        Date dbNow = commonRepository.getDBNow();
        List<TrackingDto> trackingDtoList = new ArrayList<>();
        HashSet<Long> pathIds = new HashSet<>();
        Long userId = UserContext.getLoginUserID();
        TrackingDto track;
        List<BlockDto> blockDtoList = new ArrayList<>();
        for (Map.Entry<Long, Integer> entry : blocksId.entrySet()) {
            BlockDto blockDto = blockApplication.getSimpleBlockById(entry.getKey());
            if (blockDto.getStatus().equals(PathologyStatus.PrepareGrossingConfirm.toCode())) {
                blockDto.setUpdateBy(userId);
                blockDto.setStatus(paramSettingApplication.getNextStatusByStatusAndId(blockDto.getStatus(), entry.getKey()));
                blockDtoList.add(blockDto);

                track = new TrackingDto();
                track.setCreateBy(userId);
                track.setBlockId(blockDto.getId());
                track.setOperatorId(userId);
                track.setOperation(TrackingOperation.grossingConfirm.toCode());
                track.setOperationTime(dbNow);
                track.setManualFlag(true);
                track.setInstrumentId(Long.valueOf(entry.getValue()));
                trackingDtoList.add(track);
                Long pathId = blockDto.getPathId();
                pathIds.add(pathId);
            }
        }
        if (blockDtoList.size() > 0) {
            blockApplication.batchUpdateBlock(blockDtoList);
        }
        if (trackingDtoList.size() > 0) {
            trackingApplication.addTrackingBatch(trackingDtoList);
        }
        for (long pathId : pathIds) {
            PathologyDto pathologyDto = pathologyApplication.getSimplePathById(pathId);
            Integer pathStatus = pathologyDto.getStatus();
            Integer realStatus = blockApplication.getMinBlockStatusByPathId(pathId);
            if (!pathStatus.equals(realStatus) && !pathStatus.equals(PathologyStatus.PrepareGrossing.toCode())) {
                pathologyDto.setStatus(realStatus);
                pathologyApplication.updatePathology(pathologyDto);
            }
        }
    }

    @Override
    public List<BlockDetailDto> getBlockByCondition(GrossingCon con) {
        if (con.getFilter() != null && con.getFilter().length() > 8) {//病理号
            String num = con.getFilter().substring(1, 2);//第二位
            if (num.equals("1") || num.equals("2") || num.equals("3") || num.equals("4") || num.equals("5")
                    || num.equals("6") || num.equals("7") || num.equals("8") || num.equals("9") || num.equals("0")) {
                PathologyDto pathology = pathologyApplication.getPathologyBySerialNumber(con.getFilter());
                if (pathology != null) {
                    con.setPathologyId(pathology.getId());
                } else {
                    return new ArrayList<>();
                }
            }
        }
        return blockApplication.getGrossingBlockDetailByCondition(con);
    }

    @Override
    public Long countBlockByCondition(GrossingCon con) {
        //病理号
        if (con.getFilter() != null && con.getFilter().length() > 8) {
            String num = con.getFilter().substring(1, 2);//第二位
            if (num.equals("1") || num.equals("2") || num.equals("3") || num.equals("4") || num.equals("5")
                    || num.equals("6") || num.equals("7") || num.equals("8") || num.equals("9") || num.equals("0")) {
                PathologyDto pathology = pathologyApplication.getPathologyBySerialNumber(con.getFilter());
                if (pathology != null) {
                    con.setPathologyId(pathology.getId());
                } else {
                    return 0L;
                }
            }
        }
        return blockApplication.countGrossingBlockDetailByCondition(con);
    }

    @Override
    public Long countPathologyByCondition(GrossingCon con) {
        return blockApplication.countPathologyByCondition(con);
    }

    @Override
    public GrossingFileDto getGrossingFile(Long id, Integer operation,String tag) {
        List<PathologyFileDto> files = pathologyFileApplication.getFileByPathologyId(id, operation,tag);

        GrossingFileDto data = new GrossingFileDto();

        List<String> images = new ArrayList<>();
        for (PathologyFileDto file : files) {
            if (file.getType().equals(FileType.Image.toCode())) {
                images.add("api/static/" + file.getType() + "/" + file.getContent());
            } else if (file.getType().equals(FileType.Video.toCode())) {
                data.setVideo("api/static/" + file.getType() + "/" + file.getContent());
            }
        }
        data.setImages(images);
        return data;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void grossingBeforePrint(List<GrossingPrintDto> grossingPrintDtoList) {
        if (CollectionUtils.isNotEmpty(grossingPrintDtoList)) {
            long userId = UserContext.getLoginUserID();
            Date now = commonRepository.getDBNow();
            List<TrackingDto> trackingDtoList = new ArrayList<>();
            for (GrossingPrintDto grossingPrintDto : grossingPrintDtoList) {
                String pathNo = grossingPrintDto.getPathNo();
                List<String> blockSubIds = grossingPrintDto.getBlockSubIds();
                if (pathNo != null && CollectionUtils.isNotEmpty(blockSubIds)) {
                    BlockDto blockDto;
                    for (String subId : blockSubIds) {
                        blockDto = new BlockDto();
                        blockDto.setStatus(PathologyStatus.PrepareGrossingConfirm.toCode());
                        blockDto.setSerialNumber(pathNo + subId);
                        blockDto.setSubId(subId);
                        blockDto.setSpecialDye(Config.dyeType);
                        List<String> marker = new ArrayList<>();
                        marker.add(Config.dyeMarker);//默认标记物
                        blockDto.setMarker(marker);
                        blockDto.setEmbedPause(false);
                        blockDto.setCreateBy(userId);
                        blockDto.setCreateTime(now);
                        blockDto.setPrint(1);
                        blockDto.setStatus(PathologyStatus.PrepareGrossing.toCode());
                        blockDto.setUpdateBy(userId);
                        blockDto.setUpdateTime(now);
                        blockApplication.addBlock(blockDto);
                        //添加tracking打印记录
                        trackingDtoList.add(addPrint(blockDto, TrackingOperation.printEmbedBox.toCode()));
                    }
                }
            }
            trackingApplication.addTrackingBatch(trackingDtoList);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void print(Integer operate, List<Long> ids) {
        if (CollectionUtils.isNotEmpty(ids)) {
            List<TrackingDto> trackingDtoList = new ArrayList<>();
            long userId = UserContext.getLoginUserID();
            Date now = commonRepository.getDBNow();
            for (long id : ids) {
                BlockDto blockDto = blockApplication.getSimpleBlockById(id);
                blockDto.setPrint(blockDto.getPrint() + 1);
                blockDto.setUpdateBy(userId);
                blockDto.setUpdateTime(now);
                blockApplication.updateBlock(blockDto);
                trackingDtoList.add(addPrint(blockDto, operate));
            }
            trackingApplication.addTrackingBatch(trackingDtoList);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void frozenGrossing(GrossingSaveDto data) {
        Long userID = UserContext.getLoginUserID();
        Date dbNow = commonRepository.getDBNow();

        PathologyDto pathologyDto = pathologyApplication.getSimplePathById(data.getPathologyId());
        String frozenNumber = data.getNumber();
        SpecialApplicationDto specialApplicationDto = specialApplicationApplication.getByNumber(frozenNumber);
        if (specialApplicationDto != null) {
            specialApplicationDto.setAssignGrossing(data.getOperatorId());
            specialApplicationDto.setBingdongNote(data.getBingdongNote());
            specialApplicationDto.setStatus(PathologyStatus.PrepareFirstDiagnose.toCode());
            specialApplicationDto.setUpdateBy(userID);
            //更新特殊申请状态
            specialApplicationApplication.update(specialApplicationDto);
        }

        List<Long> deleteBlocks = data.getDeleteBlocks();
        if (CollectionUtils.isNotEmpty(deleteBlocks)) {
            for (Long blockId : deleteBlocks) {
                BlockDto blockDto = blockApplication.getBlockById(blockId);
                Integer status = blockDto.getStatus();
                if (PathologyStatus.PrepareGrossing.toCode().equals(status)) {
                    blockApplication.deleteBlockById(blockId);
                }
            }
        }

        List<BlockDto> blocks = data.getBlocks();
        if (CollectionUtils.isNotEmpty(blocks)) {
            List<TrackingDto> trackingDtoList = new ArrayList<>();
            for (BlockDto blockDto : blocks) {
                Long id = blockDto.getId();
                blockDto.setStatus(PathologyStatus.PrepareFirstDiagnose.toCode());
                if (id == null) {
                    //冰冻取材不产生包埋盒 蜡块号用0代替
                    blockDto.setSerialNumber(pathologyDto.getSerialNumber() + "0" + blockDto.getSubId());
                    //以此标识冰冻取材产生的玻片
                    blockDto.setParentId(0L);
                    blockDto.setSpecialDye(Config.dyeType);
                    //默认标记物
                    blockDto.setSlideMarker(Config.dyeMarker);
                    blockDto.setEmbedPause(false);
                    blockDto.setCreateBy(userID);
                    blockDto.setNumber(frozenNumber);
                    blockApplication.addSlide(blockDto);
                    id = blockDto.getId();
                } else {
                    blockDto = blockApplication.getBlockById(id);
                    blockDto.setUpdateBy(userID);
                    blockApplication.updateBlock(blockDto);
                }

                //添加tracking冰冻取材记录
                TrackingDto trackGrossing = new TrackingDto();
                trackGrossing.setCreateBy(userID);
                trackGrossing.setBlockId(id);
                trackGrossing.setOperatorId(data.getOperatorId());
                trackGrossing.setSecOperatorId(data.getSecOperatorId());
                trackGrossing.setOperation(TrackingOperation.frozenGrossing.toCode());
                trackGrossing.setOperationTime(dbNow);
                trackGrossing.setOperatorName(userApplication.getUserSimpleInfoById(data.getOperatorId()).getFirstName());
                trackGrossing.setInstrumentId(blockDto.getBasketNumber());
                trackGrossing.setManualFlag(data.getManualFlag());
                trackingDtoList.add(trackGrossing);

                //添加tracking冰冻切片记录
                TrackingDto track = new TrackingDto();
                track.setCreateBy(userID);
                track.setBlockId(id);
                track.setOperatorId(data.getSecOperatorId());
                track.setOperation(TrackingOperation.frozenSection.toCode());
                track.setOperationTime(dbNow);
                track.setManualFlag(data.getManualFlag());
                track.setInstrumentId(blockDto.getBasketNumber());//切片机ID
                trackingDtoList.add(track);

                //添加诊断玻片打分默认值
                float defaultScore = Config.qualifiedDefaultScore;
                BlockScoreDto blockScoreDto = new BlockScoreDto();
                blockScoreDto.setBlockId(id);
                blockScoreDto.setParentId(0L);
                blockScoreDto.setAverage(defaultScore);
                blockScoreDto.setGrossing(defaultScore);
                blockScoreDto.setDehydrate(defaultScore);
                blockScoreDto.setEmbedding(defaultScore);
                blockScoreDto.setSealing(defaultScore);
                blockScoreDto.setSectioning(defaultScore);
                blockScoreDto.setStaining(defaultScore);
                blockScoreDto.setCreateBy(userID);
                BlockScoreDto diagnoseScore = blockScoreApplication.getBlockScoreBySlideIdAndType(id, BlockScoreType.Diagnose.toCode());
                if (diagnoseScore == null) {
                    blockScoreDto.setType(BlockScoreType.Diagnose.toCode());
                    blockScoreApplication.addBlockScore(blockScoreDto);
                }
            }
            if (CollectionUtils.isNotEmpty(trackingDtoList)) {
                //批量添加tracking
                trackingApplication.addTrackingBatch(trackingDtoList);
            }
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void frozenPrint(GrossingSaveDto data) {
        Long userID = UserContext.getLoginUserID();
        Date dbNow = commonRepository.getDBNow();

        PathologyDto pathologyDto = pathologyApplication.getSimplePathById(data.getPathologyId());
        String frozenNumber = data.getNumber();
        List<BlockDto> blocks = data.getBlocks();
        if (CollectionUtils.isNotEmpty(blocks)) {
            List<TrackingDto> trackingDtoList = new ArrayList<>();
            for (BlockDto blockDto : blocks) {
                Long id = blockDto.getId();
                blockDto.setUpdateBy(userID);
                blockDto.setUpdateTime(dbNow);
                if (id == null) {
                    //首次打印
                    blockDto.setStatus(PathologyStatus.PrepareGrossing.toCode());
                    //冰冻取材不产生包埋盒 蜡块号用0代替
                    blockDto.setSerialNumber(pathologyDto.getSerialNumber() + "0" + blockDto.getSubId());
                    //以此标识冰冻取材产生的玻片
                    blockDto.setParentId(0L);
                    blockDto.setSpecialDye(Config.dyeType);
                    blockDto.setSlideMarker(Config.dyeMarker);
                    blockDto.setEmbedPause(false);
                    blockDto.setNumber(frozenNumber);
                    blockDto.setPrint(1);
                    blockApplication.addSlide(blockDto);
                } else {
                    blockDto = getUpdatePrint(blockDto);
                    blockApplication.updateBlock(blockDto);
                }
                trackingDtoList.add(addPrint(blockDto, TrackingOperation.printSlide.toCode()));
            }
            if (CollectionUtils.isNotEmpty(trackingDtoList)) {
                //批量添加tracking
                trackingApplication.addTrackingBatch(trackingDtoList);
            }
        }
    }

    @Override
    public List<BlockDetailDto> getFrozenBlockByCondition(GrossingCon con) {
        List<BlockDetailDto> blockDetails = blockApplication.getFrozenBlockByCondition(con);
        return blockDetails;
    }

    @Override
    public Long countFrozenBlockByCondition(GrossingCon con) {
        return blockApplication.countFrozenBlockByCondition(con);
    }
}
