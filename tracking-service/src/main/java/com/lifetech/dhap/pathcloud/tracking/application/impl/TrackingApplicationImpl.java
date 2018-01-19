package com.lifetech.dhap.pathcloud.tracking.application.impl;

import com.lifetech.dhap.pathcloud.common.data.PathologyStatus;
import com.lifetech.dhap.pathcloud.common.exception.BuzException;
import com.lifetech.dhap.pathcloud.security.UserContext;
import com.lifetech.dhap.pathcloud.setting.application.ParamSettingApplication;
import com.lifetech.dhap.pathcloud.tracking.application.TrackingApplication;
import com.lifetech.dhap.pathcloud.tracking.application.condition.DistributeCondition;
import com.lifetech.dhap.pathcloud.tracking.application.condition.DoctorAdviceCondition;
import com.lifetech.dhap.pathcloud.tracking.application.condition.TrackingCondition;
import com.lifetech.dhap.pathcloud.tracking.application.dto.*;
import com.lifetech.dhap.pathcloud.tracking.application.infrastructure.code.TrackingOperation;
import com.lifetech.dhap.pathcloud.tracking.domain.TrackingRepository;
import com.lifetech.dhap.pathcloud.tracking.domain.model.*;
import com.lifetech.dhap.pathcloud.user.application.UserApplication;
import com.lifetech.dhap.pathcloud.user.application.dto.UserDto;
import net.sf.json.JSONArray;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author eric.guo@ostengar.com
 * @version 1.0
 * @time 2016-12-07-16:00
 */
@Service("trackingApplication")
public class TrackingApplicationImpl implements TrackingApplication {

    @Autowired
    private TrackingRepository trackingRepository;

    @Autowired
    private UserApplication userApplication;

    @Autowired
    private ParamSettingApplication paramSettingApplication;

    /**
     * 除查看玻片/申请重补取/申请深切/申请重切/申请特染/特染确认/取消特染/打印
     * 其他操作校验block_id和operation联合唯一性
     *
     * @param data
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addTracking(TrackingDto data) {
        Boolean add = true;
        int operation = data.getOperation();
        if (!TrackingOperation.watchSlide.toCode().equals(operation) && !TrackingOperation.applyReSection.toCode().equals(operation) &&
                !TrackingOperation.applyDeepSection.toCode().equals(operation) && !TrackingOperation.applyReGrossing.toCode().equals(operation)
                && !TrackingOperation.dyeConfirm.toCode().equals(operation) && !TrackingOperation.specialDyeApply.toCode().equals(operation) &&
                !TrackingOperation.cancelDye.toCode().equals(operation) && !TrackingOperation.embedPause.toCode().equals(operation)
                && !TrackingOperation.printEmbedBox.toCode().equals(operation) && !TrackingOperation.printSlide.toCode().equals(operation)
                && !TrackingOperation.printSign.equals(operation) && !TrackingOperation.printReport.toCode().equals(operation) &&
                !TrackingOperation.saveDiagnose.toCode().equals(operation) && !TrackingOperation.embedPauseCancel.toCode().equals(operation)
                && !TrackingOperation.confirmSign.toCode().equals(operation)
                ) {
            TrackingCondition con = new TrackingCondition();
            con.setBlockId(data.getBlockId());
            con.setOperation(operation);
            TrackingDto trackingDto = getTrackingByCondition(con);
            if (trackingDto != null && trackingDto.getId() != null) {
                add = false;
            }
        }
        if (add) {
            Tracking track = new Tracking();
            BeanUtils.copyProperties(data, track);
            track.setOperatorName(UserContext.getLoginFirstName());
            trackingRepository.insert(track);
            data.setId(trackingRepository.last());
        }
    }

    @Override
    public void addTrackingBatch(List<TrackingDto> data) {
        if (!CollectionUtils.isEmpty(data)) {
            List<Tracking> trackingList = new ArrayList<>();
            for (TrackingDto trackingDto : data) {
                int operation = trackingDto.getOperation();
                if (!TrackingOperation.watchSlide.toCode().equals(operation) && !TrackingOperation.applyReSection.toCode().equals(operation) &&
                        !TrackingOperation.applyDeepSection.toCode().equals(operation) && !TrackingOperation.applyReGrossing.toCode().equals(operation)
                        && !TrackingOperation.dyeConfirm.toCode().equals(operation) && !TrackingOperation.specialDyeApply.toCode().equals(operation) &&
                        !TrackingOperation.cancelDye.toCode().equals(operation) && !TrackingOperation.embedPause.toCode().equals(operation)
                        && !TrackingOperation.printEmbedBox.toCode().equals(operation) && !TrackingOperation.printSlide.toCode().equals(operation)
                        && !TrackingOperation.printSign.equals(operation) && !TrackingOperation.printReport.toCode().equals(operation) &&
                        !TrackingOperation.saveDiagnose.toCode().equals(operation) && !TrackingOperation.embedPauseCancel.toCode().equals(operation)
                        && !TrackingOperation.confirmSign.toCode().equals(operation)
                        ) {
                    TrackingCondition con = new TrackingCondition();
                    con.setBlockId(trackingDto.getBlockId());
                    con.setOperation(operation);
                    TrackingDto trackingDto1 = getTrackingByCondition(con);
                    if (trackingDto1 == null || trackingDto1.getId() == null) {
                        trackingList.add(trackingTrans(trackingDto));
                    }
                } else {
                    trackingList.add(trackingTrans(trackingDto));
                }
            }
            if (!CollectionUtils.isEmpty(trackingList)) {
                trackingRepository.batchInsert(trackingList);
            }
        }
    }

    private Tracking trackingTrans(TrackingDto trackingDto) {
        Tracking tracking = null;
        if (trackingDto != null) {
            tracking = new Tracking();
            BeanUtils.copyProperties(trackingDto, tracking);
            String operatorName = tracking.getOperatorName();
            if (StringUtils.isBlank(operatorName)) {
                tracking.setOperatorName(UserContext.getLoginFirstName());
            }
        }
        return tracking;
    }

    @Override
    public TrackingDto getTrackingByCondition(TrackingCondition con) {
        Tracking track = trackingRepository.selectByCondition(con);
        return trackingToDto(track);
    }

    @Override
    public TrackingDto getTrackingById(long id) {
        Tracking tracking = trackingRepository.selectByPrimaryKey(id);
        return trackingToDto(tracking);
    }

    @Override
    public List<TrackingDto> getTrackingsByCondition(TrackingCondition con) {
        List<Tracking> track = trackingRepository.selectAllByCondition(con);
        if (CollectionUtils.isEmpty(track)) {
            return null;
        }
        List<TrackingDto> trackingDtoList = new ArrayList<>();
        TrackingDto dto;
        for (Tracking tracking : track) {
            dto = new TrackingDto();
            BeanUtils.copyProperties(tracking, dto);
            trackingDtoList.add(dto);
        }
        return trackingDtoList;
    }

    @Override
    public Long countTrackingByCondition(TrackingCondition condition) {
        return trackingRepository.countTrackingByCondition(condition);
    }

    @Override
    public Long getBlockWaitTimeByCondition(TrackingCondition con) {
        return trackingRepository.selectBlockWaitTimeByCondition(con);
    }

    @Override
    public List<TrackingDto> getBlockTracking(long pathId) {
        List<Tracking> trackingList = trackingRepository.selectTrackingByPathId(pathId);
        List<TrackingDto> trackingDtoList = new ArrayList<>();
        for (Tracking tracking : trackingList) {
            trackingDtoList.add(trackingToDto(tracking));
        }
        return trackingDtoList;
    }

    @Override
    public List<BlockTrackingDto> getIhcBlockTracking(long pathId, Integer specialDye,Long applyId) {
        List<BlockTracking> trackingList = trackingRepository.selectIhcTrackingByPathId(pathId, specialDye,applyId);
        List<BlockTrackingDto> trackingDtoList = null;
        if (!CollectionUtils.isEmpty(trackingList)) {
            trackingDtoList = new ArrayList<>();
            for (BlockTracking tracking : trackingList) {
                BlockTrackingDto dto = new BlockTrackingDto();
                BeanUtils.copyProperties(tracking, dto);
                dto.setOperationName(TrackingOperation.getNameByCode(tracking.getOperation()));
                if (tracking.getOperatorId() != null) {
                    UserDto user = userApplication.getUserByUserID(tracking.getOperatorId());
                    if (user != null) {
                        dto.setOperatorName(user.getFirstName());
                    }
                }
                String trackingMarker = tracking.getMarker();
                if (tracking.getOperation() == TrackingOperation.dyeConfirm.toCode() && trackingMarker != null && trackingMarker.length() > 0) {
                    String marker = "";
                    for (Object mark : JSONArray.fromObject(trackingMarker)) {
                        marker += mark + ";";
                    }
                    dto.setMarker(marker.substring(0, marker.length() - 1));
                }
                trackingDtoList.add(dto);
            }
        }
        return trackingDtoList;
    }

    @Override
    public boolean getBasketNumberStatus(Long basketNumber) {
        Tracking track = trackingRepository.selectTrackingByBasketNumberAndStatus(basketNumber, PathologyStatus.PrepareGrossingConfirm.toCode());
        if (track == null) {
            return false;
        }
        Long userId = track.getSecOperatorId();
        if (userId != null && userId.equals(UserContext.getLoginUserID())) {
            return false;
        }
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteTrackingByCondition(TrackingCondition con) {
        trackingRepository.deleteByCondition(con);
    }

    @Override
    public List<PrepareDistributeDto> getPrepareDistribute(DistributeCondition condition) {
        List<PrepareDistribute> slideDistributes = trackingRepository.selectPrepareDistribute(condition);
        List<PrepareDistributeDto> data = new ArrayList<>();
        for (PrepareDistribute slideDistribute : slideDistributes) {
            PrepareDistributeDto dto = new PrepareDistributeDto();
            BeanUtils.copyProperties(slideDistribute, dto);
            data.add(dto);
        }
        return data;
    }

    @Override
    public Long countPrepareDistribute(DistributeCondition con) {
        return trackingRepository.countPrepareDistribute(con);
    }

    @Override
    public List<Long> getPrepareDistributeSlideIds(Long pathId) {
        return trackingRepository.selectPrepareDistributeIdsByParam(pathId);
    }

    @Override
    public List<DistributeHistoryDto> getDistributeHistory(DistributeCondition condition) {
        List<DistributeHistory> slideDistributes = trackingRepository.selectDistributeHistory(condition);

        List<DistributeHistoryDto> data = new ArrayList<>();
        for (DistributeHistory slideDistribute : slideDistributes) {
            DistributeHistoryDto dto = new DistributeHistoryDto();
            BeanUtils.copyProperties(slideDistribute, dto);
 /*           if (userDto != null && userDto.getId().equals(slideDistribute.getGrossingOperator())) {
                dto.setGrossingOperatorName(userDto.getFirstName());
            } else {
                userDto = userApplication.getUserByUserID(slideDistribute.getGrossingOperator());
                dto.setGrossingOperatorName(userDto.getFirstName());
            }
            if (confirmUser != null && confirmUser.getId().equals(slideDistribute.getConfirmOperator())) {
                dto.setConfirmOperatorName(confirmUser.getFirstName());
            } else {
                confirmUser = userApplication.getUserByUserID(slideDistribute.getConfirmOperator());
                dto.setConfirmOperatorName(confirmUser.getFirstName());
            }
            if (distributeUser != null && distributeUser.getId().equals(slideDistribute.getDistributeOperator())) {
                dto.setDistributeOperatorName(distributeUser.getFirstName());
            } else {
                distributeUser = userApplication.getUserByUserID(slideDistribute.getDistributeOperator());
                dto.setDistributeOperatorName(distributeUser.getFirstName());
            }*/
            data.add(dto);
        }
        return data;
    }

    @Override
    public Long countDistributeHistory(DistributeCondition con) {
        return trackingRepository.countDistributeHistory(con);
    }

    @Override
    public long countWatchedSlide(long userId, List<Long> slideIds) {
        return trackingRepository.countWatchedSlide(userId, slideIds);
    }

    @Override
    public Long getSectionOperatorByBlockId(long blockId) {
        return trackingRepository.selectSectionOperatorByBlockId(blockId);
    }

    @Override
    public Long getBlockWaitTimeByOperationAndTime(TrackingCondition condition) {
        return trackingRepository.selectBlockWaitTime(condition);
    }

    private TrackingDto trackingToDto(Tracking tracking) {
        TrackingDto trackingDto = null;
        if (tracking != null) {
            trackingDto = new TrackingDto();
            BeanUtils.copyProperties(tracking, trackingDto);
            trackingDto.setOperationName(TrackingOperation.valueOf(tracking.getOperation()).toString());
            if (trackingDto.getOperatorId() != null) {
                UserDto user = userApplication.getUserByUserID(trackingDto.getOperatorId());
                if (user != null) {
                    trackingDto.setSecOperatorName(user.getFirstName());
                }
            }

            if (trackingDto.getSecOperatorId() != null) {
                UserDto user = userApplication.getUserByUserID(trackingDto.getSecOperatorId());
                if (user != null) {
                    trackingDto.setSecOperatorName(user.getFirstName());
                }
            }
        }
        return trackingDto;
    }

    @Override
    public Integer getPreOperationByOperation(int operation) throws BuzException {
        return paramSettingApplication.getPreOperationByOperation(operation);
    }

    @Override
    public List<DoctorAdviceDto> getDoctorAdvice(DoctorAdviceCondition con) {
        List<DoctorAdviceDto> data = trackingRepository.selectDoctorAdvice(con);

        UserDto user = null;
        for (DoctorAdviceDto doctorAdviceDto : data) {
            doctorAdviceDto.setOperatorName(TrackingOperation.getNameByCode(doctorAdviceDto.getOperation()));
            if (user != null && user.getId().equals(doctorAdviceDto.getOperatorId())) {
                doctorAdviceDto.setOperatorName(user.getFirstName());
            } else {
                user = userApplication.getUserByUserID(doctorAdviceDto.getOperatorId());
                doctorAdviceDto.setOperatorName(user.getFirstName());
            }
            if (con.getStatus() == 1 || doctorAdviceDto.getTrackId() != null) {
                doctorAdviceDto.setStatusName("制片完成");
            } else {
                doctorAdviceDto.setStatusName(PathologyStatus.getNameByCode(doctorAdviceDto.getStatus()));
            }
            doctorAdviceDto.setOperationName(TrackingOperation.getNameByCode(doctorAdviceDto.getOperation()));
            String markers = doctorAdviceDto.getNoteType();
            if (StringUtils.isNotBlank(markers)) {
                List<String> markerStr = JSONArray.fromObject(markers);
                String marker = "";
                for (String mark : markerStr) {
                    marker += mark + ";";
                }
                doctorAdviceDto.setIhcMarker(marker.substring(0, marker.length() - 1));
            }
        }
        return data;
    }

    @Override
    public Long countDoctorAdvice(DoctorAdviceCondition con) {
        return trackingRepository.countDoctorAdvice(con);
    }

    @Override
    public List<BlockTrackingDto> getDoctorAdviceLog(Long applyId, Long blockId) {
        return trackingRepository.getDoctorAdviceLog(applyId, blockId);
    }

    @Override
    public TrackingDto getLastPauseEmbed(long blockId) {
        Tracking tracking = trackingRepository.selectLastEmbedPause(blockId);
        if (tracking != null) {
            TrackingDto dto = new TrackingDto();
            BeanUtils.copyProperties(tracking, dto);
            return dto;
        } else {
            return null;
        }
    }

    @Override
    public Long getBlockIhcTrackingIdByBlockIhcId(long blockIhcId) {
        return trackingRepository.selectBlockIhcTrackingIdByBlockIhcId(blockIhcId);
    }

    @Override
    public Long getBlockIhcIdById(long id) {
        return trackingRepository.selectBlockIhcIdById(id);
    }
}
