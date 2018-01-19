package com.lifetech.dhap.pathcloud.dehydrate.api.impl;

import com.lifetech.dhap.pathcloud.application.application.PathologyApplication;
import com.lifetech.dhap.pathcloud.common.data.PathologyStatus;
import com.lifetech.dhap.pathcloud.common.exception.BuzException;
import com.lifetech.dhap.pathcloud.common.exception.BuzExceptionCode;
import com.lifetech.dhap.pathcloud.common.response.ResponseVO;
import com.lifetech.dhap.pathcloud.dehydrate.api.DehydrateApi;
import com.lifetech.dhap.pathcloud.dehydrate.api.vo.BlockDehydrateErrorVO;
import com.lifetech.dhap.pathcloud.dehydrate.api.vo.DehydrateVO;
import com.lifetech.dhap.pathcloud.dehydrate.api.vo.DehydratorVO;
import com.lifetech.dhap.pathcloud.dehydrate.application.DehydrateApplication;
import com.lifetech.dhap.pathcloud.dehydrate.application.DehydratorApplication;
import com.lifetech.dhap.pathcloud.dehydrate.application.dto.DehydrateDto;
import com.lifetech.dhap.pathcloud.localdaemon.device.constants.DehydratorStatus;
import com.lifetech.dhap.pathcloud.setting.application.ParamSettingApplication;
import com.lifetech.dhap.pathcloud.setting.application.infrastructure.code.ParamKey;
import com.lifetech.dhap.pathcloud.tracking.application.BlockApplication;
import com.lifetech.dhap.pathcloud.tracking.application.GrossingApplication;
import com.lifetech.dhap.pathcloud.tracking.application.condition.BlockCondition;
import com.lifetech.dhap.pathcloud.tracking.application.condition.GrossingCon;
import com.lifetech.dhap.pathcloud.tracking.application.dto.BlockDetailDto;
import com.lifetech.dhap.pathcloud.tracking.application.dto.BlockDto;
import com.lifetech.dhap.pathcloud.tracking.application.infrastructure.code.TrackingOperation;
import com.lifetech.dhap.pathcloud.user.api.vo.UserSimpleVO;
import com.lifetech.dhap.pathcloud.user.application.UserApplication;
import com.lifetech.dhap.pathcloud.user.application.dto.UserSimpleDto;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Created by LiuMei on 2016-12-13.
 */
@Component("dehydrateApi")
public class DehydrateApiImpl implements DehydrateApi {

    @Autowired
    private DehydrateApplication dehydrateApplication;

    @Autowired
    private GrossingApplication grossingApplication;

    @Autowired
    private BlockApplication blockApplication;

    @Autowired
    private PathologyApplication pathologyApplication;

    @Autowired
    private UserApplication userApplication;

    @Autowired
    private ParamSettingApplication paramSettingApplication;

    @Autowired
    private DehydratorApplication dehydratorApplication;

    @Override
    public ResponseVO startDehydrate(DehydrateVO vo) throws BuzException {
        Long instrumentId = vo.getInstrumentId();
        List<Integer> baskets = vo.getBaskets();

        if (instrumentId == null) {
            throw new BuzException(BuzExceptionCode.ErrorParam);
        }
        DehydratorVO dehydratorVO = dehydratorApplication.getDehydratorByInstrumentId(instrumentId);
        if(baskets == null || baskets.size() <= 0){
           if( dehydratorVO.getStatus().equals(DehydratorStatus.Pause.getCode())){
               dehydratorVO.setStatus(DehydratorStatus.Normal.getCode());
               dehydratorApplication.setInUse(instrumentId,true);
               dehydratorApplication.updateDehydratorInfo(dehydratorVO);
               return new ResponseVO();
           } else {
               throw new BuzException(BuzExceptionCode.ErrorParam);
           }
        }

        GrossingCon con = new GrossingCon();
        con.setSize(Integer.MAX_VALUE);
        con.setStart(0);
        con.setBasketNumbers(baskets);
        con.setBlockStatus(PathologyStatus.PrepareDehydrate.toCode());
        con.setOperation(TrackingOperation.grossingConfirm.toCode());
        List<BlockDetailDto> data = grossingApplication.getBlockByCondition(con);
        if ((data == null || data.size() <= 0) && dehydratorVO.getUsedBlock() == 0) {
            throw new BuzException(BuzExceptionCode.ErrorParam);
        }

        if(data == null || data.size() <= 0){
            return new ResponseVO();
        }

        Integer decalcify = paramSettingApplication.getCodeByKeyAndName(ParamKey.BlockBiaoshi.toString(), "脱钙");

        Boolean ignore = vo.getIgnore();
        if(ignore != null && ignore){
            DehydrateDto dto = new DehydrateDto();
            dto.setInstrumentId(instrumentId);
            dto.setBlocks(data);
            dehydrateApplication.startDehydrate(dto);
        }else {
            Map<Long, HashSet<Long>> pathBlock = new HashMap<>();
            for (BlockDetailDto blockDetailDto : data) {//所有蜡块号按病理号分好组
                Long pathId = blockDetailDto.getPathologyId();
                long blockId = blockDetailDto.getBlockId();
                Integer biaoshi = blockDetailDto.getBiaoshi();
                if(!biaoshi.equals(decalcify)){
                    if (pathBlock != null && pathBlock.size() > 0) {
                        Set<Map.Entry<Long, HashSet<Long>>> entries = pathBlock.entrySet();
                        boolean has = false;
                        for (Map.Entry<Long, HashSet<Long>> entry : entries) {
                            if (entry.getKey().equals(pathId)) {
                                has = true;
                                entry.getValue().add(blockId);
                            }
                        }
                        if (!has) {
                            HashSet<Long> blocks = new HashSet<>();
                            blocks.add(blockId);
                            pathBlock.put(pathId, blocks);
                        }
                    } else {
                        HashSet<Long> blocks = new HashSet<>();
                        blocks.add(blockId);
                        pathBlock.put(pathId, blocks);
                    }
                }
            }

            Set<Map.Entry<Long, HashSet<Long>>> entries = pathBlock.entrySet();
            BlockCondition blockCondition = new BlockCondition();
            List<Integer> statusList = new ArrayList<>();
            statusList.add(PathologyStatus.PrepareGrossingConfirm.toCode());
            statusList.add(PathologyStatus.PrepareDehydrate.toCode());
            blockCondition.setAgainstBiaoshi(decalcify);
            blockCondition.setStatusList(statusList);
            List<BlockDehydrateErrorVO> blockDehydrateErrorVOS = new ArrayList<>();
            BlockDehydrateErrorVO blockDehydrateErrorVO;
            for (Map.Entry<Long, HashSet<Long>> entry : entries) {
                blockCondition.setPathId(entry.getKey());
                long blocks = blockApplication.countBlockByCondition(blockCondition);
                HashSet<Long> value = entry.getValue();
                int size = value.size();
                if (blocks != size) {//有异常
                    List<BlockDto> blockDtos = blockApplication.getBlockByCondition(blockCondition);
                    List<BlockDehydrateErrorVO> blockError1 = new ArrayList<>();
                    for (BlockDto blockDto : blockDtos) {
                        long blockId = blockDto.getId();
                        boolean realError = true;
                        for (Long id : value) {
                            if (id.equals(blockId)) {
                                realError = false;
                            }
                        }
                        if (realError) {
                            blockDehydrateErrorVO = new BlockDehydrateErrorVO();
                            blockDehydrateErrorVO.setBlockId(blockId);
                            blockDehydrateErrorVO.setBlockSubId(blockDto.getSubId());
                            Integer status = blockDto.getStatus();
                            blockDehydrateErrorVO.setStatus(status);
                            blockDehydrateErrorVO.setStatusDesc(PathologyStatus.getNameByCode(status));
                            blockDehydrateErrorVO.setPathNo(pathologyApplication.getSimplePathById(blockDto.getPathId()).getSerialNumber());
                            UserSimpleDto userSimpleDto = userApplication.getUserSimpleInfoById(blockDto.getUpdateBy());
                            UserSimpleVO userSimpleVO = new UserSimpleVO();
                            BeanUtils.copyProperties(userSimpleDto, userSimpleVO);
                            blockDehydrateErrorVO.setLastOperator(userSimpleVO);
                            blockDehydrateErrorVO.setLastOperateTime(blockDto.getUpdateTime());
                            blockError1.add(blockDehydrateErrorVO);
                        }
                    }
                    blockDehydrateErrorVOS.addAll(blockError1);
                }
            }

            if (blockDehydrateErrorVOS != null && blockDehydrateErrorVOS.size() > 0) {
                return new ResponseVO(blockDehydrateErrorVOS);
            } else {
                DehydrateDto dto = new DehydrateDto();
                dto.setInstrumentId(instrumentId);
                dto.setBlocks(data);
                dehydrateApplication.startDehydrate(dto);
            }
        }
        return new ResponseVO();
    }

    @Override
    public ResponseVO endDehydrate(DehydrateVO vo) throws BuzException {
        List<Long> instrumentIds = vo.getInstrumentIds();
        if (instrumentIds == null || instrumentIds.size() <= 0) {
            throw new BuzException(BuzExceptionCode.ErrorParam);
        }
        dehydrateApplication.endDehydrate(instrumentIds);
        return new ResponseVO();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseVO pauseDehydrate(List<Long> instrumentIds) throws BuzException {
        if(instrumentIds == null || instrumentIds.size() <= 0){
            throw new BuzException(BuzExceptionCode.ErrorParam);
        }
        for(Long id : instrumentIds){
            DehydratorVO dehydratorVO = dehydratorApplication.getDehydratorByInstrumentId(id);
            Boolean inUse = dehydratorVO.getInUse();
            if(!inUse){
                throw new BuzException(BuzExceptionCode.ErrorParam);
            }
            dehydratorApplication.setInUse(id,false);
            dehydratorVO.setStatus(DehydratorStatus.Pause.getCode());
            dehydratorApplication.updateDehydratorInfo(dehydratorVO);
        }
        return new ResponseVO();
    }
}
