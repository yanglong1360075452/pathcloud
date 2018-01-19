package com.lifetech.dhap.pathcloud.tracking.application.impl;

import com.lifetech.dhap.pathcloud.tracking.application.MicroscopeTrackingApplication;
import com.lifetech.dhap.pathcloud.tracking.application.condition.MicroscopeTrackingCon;
import com.lifetech.dhap.pathcloud.tracking.application.dto.MicroscopeTrackingDto;
import com.lifetech.dhap.pathcloud.tracking.domain.MicroscopeTrackingRepository;
import com.lifetech.dhap.pathcloud.tracking.domain.model.MicroscopeTracking;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author eric.guo@ostengar.com
 * @version 1.0
 * @time 2017-06-22-10:09
 */
@Service
public class MicroscopeTrackingApplicationImpl implements MicroscopeTrackingApplication {

    @Autowired
    private MicroscopeTrackingRepository microscopeTrackingRepository;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void addMicroscopeTracking(MicroscopeTrackingDto dto) {
        MicroscopeTracking microscopeTracking = microscopeTrackingRepository.selectUserMicroscopeTracking(dto.getBookingPid());
        if(microscopeTracking != null){
            return;
        }
        MicroscopeTracking tracking = new MicroscopeTracking();
        BeanUtils.copyProperties(dto, tracking);
        microscopeTrackingRepository.insert(tracking);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void endMicroscopeUse(MicroscopeTrackingDto dto) {
        MicroscopeTracking tracking = microscopeTrackingRepository.selectUserMicroscopeTracking(dto.getBookingPid());
        if(tracking != null){
            tracking.setEndTime(new Date());
            microscopeTrackingRepository.updateByPrimaryKey(tracking);
        }
    }

    @Override
    public List<MicroscopeTrackingDto> getMicroscopeByCondition(MicroscopeTrackingCon con) {
        List<MicroscopeTrackingDto> data = microscopeTrackingRepository.selectByCondition(con);
        return data;
    }

    @Override
    public Long microscopeTotal(MicroscopeTrackingCon con) {
        return microscopeTrackingRepository.countByCondition(con);
    }
}
