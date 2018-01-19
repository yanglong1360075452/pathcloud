package com.lifetech.dhap.pathcloud.dehydrate.application.impl;

import com.lifetech.dhap.pathcloud.application.application.PathologyApplication;
import com.lifetech.dhap.pathcloud.application.application.dto.PathologyDto;
import com.lifetech.dhap.pathcloud.common.data.PathologyStatus;
import com.lifetech.dhap.pathcloud.common.domain.CommonRepository;
import com.lifetech.dhap.pathcloud.common.exception.BuzException;
import com.lifetech.dhap.pathcloud.common.exception.BuzExceptionCode;
import com.lifetech.dhap.pathcloud.dehydrate.api.vo.DehydratorVO;
import com.lifetech.dhap.pathcloud.dehydrate.application.DehydrateApplication;
import com.lifetech.dhap.pathcloud.dehydrate.application.DehydratorApplication;
import com.lifetech.dhap.pathcloud.dehydrate.application.dto.DehydrateDto;
import com.lifetech.dhap.pathcloud.localdaemon.device.constants.DehydratorStatus;
import com.lifetech.dhap.pathcloud.security.UserContext;
import com.lifetech.dhap.pathcloud.setting.application.ParamSettingApplication;
import com.lifetech.dhap.pathcloud.tracking.application.BlockApplication;
import com.lifetech.dhap.pathcloud.tracking.application.TrackingApplication;
import com.lifetech.dhap.pathcloud.tracking.application.dto.BlockDetailDto;
import com.lifetech.dhap.pathcloud.tracking.application.dto.BlockDto;
import com.lifetech.dhap.pathcloud.tracking.application.dto.TrackingDto;
import com.lifetech.dhap.pathcloud.tracking.application.infrastructure.code.TrackingOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Date;
import java.util.Set;
import java.util.List;

/**
 * Created by LiuMei on 2016-12-13.
 */
@Service("dehydrateApplication")
public class DehydrateApplicationImpl implements DehydrateApplication {

    @Autowired
    private TrackingApplication trackingApplication;

    @Autowired
    private BlockApplication blockApplication;

    @Autowired
    private PathologyApplication pathologyApplication;

    @Autowired
    private DehydratorApplication dehydratorApplication;

    @Autowired
    private ParamSettingApplication paramSettingApplication;

    @Autowired
    private CommonRepository commonRepository;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void startDehydrate(DehydrateDto dto) throws BuzException {
        Long instrumentId = dto.getInstrumentId();
        Long userId = UserContext.getLoginUserID();
        Date dbNow = commonRepository.getDBNow();
        List<BlockDetailDto> data = dto.getBlocks();
        if (data == null || data.size() <= 0) {
            throw new BuzException(BuzExceptionCode.ErrorParam);
        }
        List<TrackingDto> trackingDtos = new ArrayList<>();
        HashSet<Long> pathIds = new HashSet<>();
        List<BlockDto> blockDtos = new ArrayList<>();
        for (BlockDetailDto blockDetailDto : data) {
            BlockDto blockDto = blockApplication.getSimpleBlockById(blockDetailDto.getBlockId());
            blockDto.setUpdateBy(userId);
            blockDto.setStatus(PathologyStatus.Dehydrating.toCode());
            blockDtos.add(blockDto);

            TrackingDto track = new TrackingDto();
            track.setCreateBy(userId);
            track.setBlockId(blockDto.getId());
            track.setOperatorId(userId);
            track.setOperation(TrackingOperation.dehydrate.toCode());
            track.setOperationTime(dbNow);
            track.setManualFlag(true);
            track.setInstrumentId(instrumentId);
            trackingDtos.add(track);

            Long pathId = blockDetailDto.getPathologyId();
            pathIds.add(pathId);
        }

        if (trackingDtos.size() > 0) {
            trackingApplication.addTrackingBatch(trackingDtos);
        }
        if(blockDtos.size() > 0){
            blockApplication.batchUpdateBlock(blockDtos);
        }
        batchUpdatePathology(pathIds);

        DehydratorVO dehydratorVO = dehydratorApplication.getDehydratorByInstrumentId(instrumentId);
        dehydratorVO.setUsedBlock(data.size()+dehydratorVO.getUsedBlock());
        dehydratorVO.setStatus(DehydratorStatus.Normal.getCode());
        dehydratorApplication.updateDehydratorInfo(dehydratorVO);
        dehydratorApplication.setInUse(instrumentId, true);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void endDehydrate(List<Long> instrumentIds) throws BuzException {
        if (instrumentIds == null || instrumentIds.size() <= 0) {
            throw new BuzException(BuzExceptionCode.ErrorParam);
        }
        List<TrackingDto> trackingDtos = new ArrayList<>();
        Set<Long> pathIds = new HashSet<>();
        Date dbNow = commonRepository.getDBNow();
        List<BlockDto> blockSave = new ArrayList<>();
        for (Long id : instrumentIds) {
            List<BlockDto> blockDtos = blockApplication.getBlocksByDehydratorId(id);
            if (blockDtos != null && blockDtos.size() > 0) {
                for (BlockDto blockDto : blockDtos) {
                    Long userId = UserContext.getLoginUserID();
                    blockDto.setUpdateBy(userId);
                    blockDto.setStatus(paramSettingApplication.getNextStatusByStatusAndId(blockDto.getStatus(),blockDto.getId()));
                    blockSave.add(blockDto);

                    TrackingDto track = new TrackingDto();
                    track.setCreateBy(userId);
                    track.setBlockId(blockDto.getId());
                    track.setOperatorId(userId);
                    track.setOperation(TrackingOperation.dehydrateEnd.toCode());
                    track.setOperationTime(dbNow);
                    track.setManualFlag(true);
                    track.setInstrumentId(id);
                    trackingDtos.add(track);

                    Long pathId = blockDto.getPathId();
                    pathIds.add(pathId);
                }
            }
            DehydratorVO dehydratorVO = dehydratorApplication.getDehydratorByInstrumentId(id);
            dehydratorApplication.setInUse(id, false);
            dehydratorVO.setUsedBlock(0);
            dehydratorApplication.updateDehydratorInfo(dehydratorVO);
        }
        if (trackingDtos.size() > 0) {
            trackingApplication.addTrackingBatch(trackingDtos);
        }
        if(blockSave.size() > 0){
            blockApplication.batchUpdateBlock(blockSave);
        }
        batchUpdatePathology(pathIds);
    }

    private void batchUpdatePathology(Set<Long> pathIds) {
        for (long pathId : pathIds) {
            PathologyDto pathologyDto = pathologyApplication.getSimplePathById(pathId);
            Integer pathStatus = pathologyDto.getStatus();
            Integer realStatus = blockApplication.getMinBlockStatusByPathId(pathId);
            if (!pathStatus.equals(realStatus)) {
                pathologyDto.setStatus(realStatus);
                pathologyApplication.updatePathology(pathologyDto);
            }
        }
    }
}
