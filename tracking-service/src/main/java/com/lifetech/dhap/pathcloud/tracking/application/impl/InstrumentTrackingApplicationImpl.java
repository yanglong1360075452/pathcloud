package com.lifetech.dhap.pathcloud.tracking.application.impl;

import com.lifetech.dhap.pathcloud.dehydrate.application.InstrumentApplication;
import com.lifetech.dhap.pathcloud.tracking.application.InstrumentTrackingApplication;
import com.lifetech.dhap.pathcloud.tracking.application.condition.InstrumentTrackingCon;
import com.lifetech.dhap.pathcloud.tracking.application.dto.InstrumentTrackingDto;
import com.lifetech.dhap.pathcloud.tracking.domain.TrackingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by HP on 2017/9/7.
 */
@Service
public class InstrumentTrackingApplicationImpl implements InstrumentTrackingApplication {

    @Autowired
    private TrackingRepository trackingRepository;

    @Override
    public List<InstrumentTrackingDto> InstrumentTracking(InstrumentTrackingCon con) {
        List<InstrumentTrackingDto> dtoList=trackingRepository.InstrumentTracking(con);
        return dtoList;
    }

    @Override
    public Long InstrumentTrackingTotal(InstrumentTrackingCon con) {

        return trackingRepository.InstrumentTrackingTotal(con);
    }
}
